package scoremanager.main;

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

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        // Lấy session hiện tại
        // 現在のセッションを取得

        Teacher teacher = (Teacher) session.getAttribute("user");
        // Lấy giáo viên đang đăng nhập từ session
        // セッションからログイン中の教員情報を取得

        // ===== Lấy tham số lọc từ form =====
        // ===== フォームからの検索条件を取得 =====
        String entYearStr = request.getParameter("f1");  // 入学年度
        String classNum = request.getParameter("f2");    // クラス番号
        String isAttendStr = request.getParameter("f3"); // 在学フラグ（checkbox）

        // ===== Giá trị mặc định =====
        // ===== デフォルト値の設定 =====
        int entYear = 0;
        boolean isAttend = (isAttendStr != null); 
        // Checkbox: có giá trị → true
        // チェックボックスは値があれば true

        // Xử lý năm nhập học (tránh lỗi chuỗi rỗng)
        // 入学年度の空文字対策
        if (entYearStr != null && !entYearStr.equals("")) {
            entYear = Integer.parseInt(entYearStr);
        }

        // Xử lý classNum null → gán "0" (tương đương “chưa chọn”)
        // クラス番号が null の場合 → "0"（未選択扱い）
        if (classNum == null) {
            classNum = "0";
        }

        // ===== Tạo danh sách năm nhập học (10 năm trước → năm hiện tại) =====
        // ===== 入学年度リスト作成（10年前〜今年） =====
        LocalDate todaysDate = LocalDate.now();
        int year = todaysDate.getYear();

        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year; i++) {
            entYearSet.add(i);
        }

        // ===== DAO =====
        // ===== DAO の準備 =====
        StudentDao sDao = new StudentDao();
        ClassNumDao cNumDao = new ClassNumDao();

        // Lấy danh sách class_num theo trường của giáo viên
        // 教員が所属する学校のクラス番号一覧を取得
        List<String> classNumList = cNumDao.filter(teacher.getSchool());

        // Map chứa lỗi hiển thị trên màn hình
        // 画面に表示するエラーを保持する Map
        Map<String, String> errors = new HashMap<>();

        // Danh sách sinh viên sau khi lọc
        // 検索後の学生一覧
        List<Student> students = null;

        // ===== Điều kiện lọc =====
        // ===== 検索条件の分岐 =====
        if (entYear != 0 && !classNum.equals("0")) {
            // Có năm nhập học + có class
            // 入学年度あり + クラス指定あり
            students = sDao.filter(teacher.getSchool(), entYear, classNum, isAttend);

        } else if (entYear != 0 && classNum.equals("0")) {
            // Có năm nhập học + không chọn class
            // 入学年度あり + クラス未選択
            students = sDao.filter(teacher.getSchool(), entYear, isAttend);

        } else if (entYear == 0) {
            // Không chọn năm nhập học → lấy toàn bộ theo isAttend
            // 入学年度未選択 → 在学フラグのみで検索
            students = sDao.filter(teacher.getSchool(), isAttend);

        } else {
            // Trường hợp không hợp lệ
            // 不正な条件
            errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
            request.setAttribute("errors", errors);
            students = sDao.filter(teacher.getSchool(), isAttend);
        }

        // ===== Gửi dữ liệu sang JSP =====
        // ===== JSP へデータを渡す =====
        request.setAttribute("f1", entYear);
        request.setAttribute("f2", classNum);
        request.setAttribute("f3", isAttendStr);

        request.setAttribute("students", students);
        request.setAttribute("class_num_set", classNumList);
        request.setAttribute("ent_year_set", entYearSet);

        // Chuyển sang trang JSP hiển thị danh sách
        // 学生一覧画面（student_list.jsp）へフォワード
        request.getRequestDispatcher("student_list.jsp").forward(request, response);
    }
}
