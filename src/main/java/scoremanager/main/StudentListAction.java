package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SchoolDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentListAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // SUPERADMIN: tải danh sách trường
        // SUPERADMIN：学校一覧を読み込む
        if (teacher.getRole() == 2) {
            SchoolDao schoolDao = new SchoolDao();
            request.setAttribute("school_list", schoolDao.findAll());
        }

        // Lấy tham số lọc từ request
        // リクエストから検索条件を取得
        String entYearStr = request.getParameter("f1");
        String classNum = request.getParameter("f2");
        String isAttendStr = request.getParameter("f3");
        String schoolCd = request.getParameter("school_cd");

        int entYear = 0;
        boolean isAttend = (isAttendStr != null);

        if (entYearStr != null && !entYearStr.equals("")) {
            entYear = Integer.parseInt(entYearStr);
        }

        if (classNum == null || classNum.equals("")) {
            classNum = "0";
        }

        // Tạo danh sách năm nhập học (10 năm gần nhất)
        // 入学年度リストを作成（直近10年間）
        LocalDate todaysDate = LocalDate.now();
        int year = todaysDate.getYear();

        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year; i++) {
            entYearSet.add(i);
        }

        // Khởi tạo DAO
        // DAO の初期化
        StudentDao sDao = new StudentDao();
        ClassNumDao cNumDao = new ClassNumDao();
        SchoolDao schoolDao = new SchoolDao();

        List<String> classNumList;

        // SUPERADMIN: danh sách lớp theo trường được chọn
        // SUPERADMIN：選択された学校に応じてクラス一覧を取得
        if (teacher.getRole() == 2) {

            if (schoolCd != null && !schoolCd.equals("")) {
                School selectedSchool = schoolDao.get(schoolCd);
                classNumList = cNumDao.filter(selectedSchool);
            } else {
                classNumList = new ArrayList<>();
                cNumDao.findAll().forEach(c -> classNumList.add(c.getClass_num()));
            }

        } else {
            // Admin/Teacher: chỉ lấy lớp của trường mình
            // Admin/Teacher：自分の学校のクラスのみ取得
            classNumList = cNumDao.filter(teacher.getSchool());
        }

        Map<String, String> errors = new HashMap<>();
        List<Student> students = null;

        // SUPERADMIN xử lý tìm kiếm
        // SUPERADMIN：検索処理
        if (teacher.getRole() == 2) {

            // Lọc theo năm / lớp / trạng thái
            // 年度・クラス・在籍状態でフィルター
            students = sDao.filterAll(entYear, classNum, isAttend);

            // Nếu chọn trường → lọc thêm theo trường
            // 学校を選択した場合 → 学校コードでさらに絞り込み
            if (schoolCd != null && !schoolCd.equals("")) {
                students.removeIf(st -> !st.getSchool().getCd().equals(schoolCd));
            }

            // Nếu chỉ chọn lớp mà không chọn năm → báo lỗi
            // クラスのみ指定して年度未指定 → エラー
            if (entYear == 0 && !classNum.equals("0")) {
                errors.put("f1", "クラス指定する場合は入学年度も指定してください");
                students = new ArrayList<>();
            }
        }

        // Admin/Teacher xử lý tìm kiếm
        // Admin/Teacher：検索処理
        else {

            if (entYear == 0 && classNum.equals("0")) {
                // Không chọn gì → lấy toàn bộ theo trường
                // 条件なし → 学校内の全学生
                students = sDao.filter(teacher.getSchool(), isAttend);
            }
            else if (entYear != 0 && classNum.equals("0")) {
                // Chỉ chọn năm
                // 年度のみ指定
                students = sDao.filter(teacher.getSchool(), entYear, isAttend);
            }
            else if (entYear == 0 && !classNum.equals("0")) {
                // Chọn lớp nhưng không chọn năm → lỗi
                // クラスのみ指定 → エラー
                errors.put("f1", "クラス指定する場合は入学年度も指定してください");
                students = new ArrayList<>();
            }
            else {
                // Chọn đầy đủ năm + lớp
                // 年度 + クラス指定
                students = sDao.filter(teacher.getSchool(), entYear, classNum, isAttend);
            }
        }

        // Lưu lại school_cd để JSP hiển thị đúng
        // JSP 表示用に school_cd を保持
        request.setAttribute("school_cd", schoolCd);

        // Gửi dữ liệu sang JSP
        // JSP へデータを渡す
        request.setAttribute("f1", entYear);
        request.setAttribute("f2", classNum);
        request.setAttribute("f3", isAttendStr);

        request.setAttribute("students", students);
        request.setAttribute("class_num_set", classNumList);
        request.setAttribute("ent_year_set", entYearSet);
        request.setAttribute("errors", errors);

        request.getRequestDispatcher("student_list.jsp").forward(request, response);
    }
}
