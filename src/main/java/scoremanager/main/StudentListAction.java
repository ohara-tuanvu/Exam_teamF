package scoremanager.main;
// VI: Package chứa các Action của module scoremanager
// JP: scoremanager モジュールの Action クラスをまとめるパッケージ

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentListAction extends Action {
// VI: Action xử lý màn hình danh sách sinh viên
// JP: 学生一覧画面の処理を行う Action

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        // VI: Lấy session hiện tại
        // JP: 現在のセッションを取得

        Teacher teacher = (Teacher) session.getAttribute("user");
        // VI: Lấy giáo viên đang đăng nhập
        // JP: ログイン中の教師を取得

        // ================================
        // Lấy tham số lọc / フィルター値取得
        // ================================
        String entYearStr = request.getParameter("f1");  // 入学年度
        String classNum = request.getParameter("f2");    // クラス番号
        String isAttendStr = request.getParameter("f3"); // 在学フラグ

        int entYear = 0;
        boolean isAttend = (isAttendStr != null);

        if (entYearStr != null && !entYearStr.equals("")) {
            entYear = Integer.parseInt(entYearStr);
        }

        if (classNum == null || classNum.equals("")) {
            classNum = "0"; // 未選択扱い
        }

        // ================================
        // Tạo danh sách năm nhập học
        // 入学年度リスト作成
        // ================================
        LocalDate todaysDate = LocalDate.now();
        int year = todaysDate.getYear();

        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year; i++) {
            entYearSet.add(i);
        }

        // ================================
        // DAO
        // ================================
        StudentDao sDao = new StudentDao();
        ClassNumDao cNumDao = new ClassNumDao();

        List<String> classNumList;

        // ⭐ SUPERADMIN → lấy toàn bộ class
        // ⭐ SUPERADMIN → 全クラス取得
        if (teacher.getRole() == 2) {
            classNumList = new ArrayList<>();
            cNumDao.findAll().forEach(c -> classNumList.add(c.getClass_num()));
        } else {
            // Admin/Teacher → chỉ lấy class của trường
            // Admin/Teacher → 学校に紐づくクラスのみ取得
            classNumList = cNumDao.filter(teacher.getSchool());
        }

        Map<String, String> errors = new HashMap<>();
        List<Student> students = null;

        // ================================
        // ⭐ Điều kiện lọc / フィルター条件
        // ================================

        // ⭐ SUPERADMIN → bỏ toàn bộ filter theo school
        // ⭐ SUPERADMIN → school_cd による絞り込みを無効化
        if (teacher.getRole() == 2) {

            // ① Không nhập gì → lấy toàn bộ
            // ① 入学年度もクラスも未選択 → 全学生取得
            if (entYear == 0 && classNum.equals("0")) {
                students = sDao.findAll();
            }

            // ② Chỉ nhập năm
            // ② 入学年度のみ指定 → 年度で絞り込み
            else if (entYear != 0 && classNum.equals("0")) {
                students = new ArrayList<>();
                for (Student st : sDao.findAll()) {
                    if (st.getEntYear() == entYear && (!isAttend || st.isAttend())) {
                        students.add(st);
                    }
                }
            }

            // ③ Chỉ nhập lớp → lỗi
            // ③ クラスのみ指定 → 入学年度が必要（エラー）
            else if (entYear == 0 && !classNum.equals("0")) {
                errors.put("f1", "クラス指定する場合は入学年度も指定してください");
                students = new ArrayList<>();
            }

            // ④ Nhập cả năm + lớp
            // ④ 入学年度 + クラス番号で絞り込み
            else {
                students = new ArrayList<>();
                for (Student st : sDao.findAll()) {
                    if (st.getEntYear() == entYear &&
                        st.getClassNum().equals(classNum) &&
                        (!isAttend || st.isAttend())) {
                        students.add(st);
                    }
                }
            }

        } else {

            // ⭐ Admin/Teacher → giữ logic cũ
            // ⭐ Admin/Teacher → 既存ロジックのまま school_cd で絞り込み
            if (entYear == 0 && classNum.equals("0")) {
                students = sDao.filter(teacher.getSchool(), isAttend);
            }
            else if (entYear != 0 && classNum.equals("0")) {
                students = sDao.filter(teacher.getSchool(), entYear, isAttend);
            }
            else if (entYear == 0 && !classNum.equals("0")) {
                errors.put("f1", "クラス指定する場合は入学年度も指定してください");
                students = new ArrayList<>();
            }
            else {
                students = sDao.filter(teacher.getSchool(), entYear, classNum, isAttend);
            }
        }

        // ================================
        // Gửi dữ liệu sang JSP
        // JSP へデータを渡す
        // ================================
        request.setAttribute("f1", entYear);
        request.setAttribute("f2", classNum);
        request.setAttribute("f3", isAttendStr);

        request.setAttribute("students", students);
        request.setAttribute("class_num_set", classNumList);
        request.setAttribute("ent_year_set", entYearSet);
        request.setAttribute("errors", errors);

        request.getRequestDispatcher("student_list.jsp").forward(request, response);
        // VI: Chuyển sang JSP hiển thị danh sách
        // JP: 学生一覧 JSP にフォワード
    }
}
