package scoremanager.main;
// VI: Package chứa các Action thuộc module scoremanager/main
// JP: scoremanager/main モジュールに属する Action クラスをまとめるパッケージ

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;
// VI: Action là lớp cha dùng để xử lý request theo mô hình MVC
// JP: MVC パターンでリクエスト処理を行う基底クラス

public class StudentUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            // ---------------------------------------------
            // 1) Lấy student number từ URL (?no=xxxx)
            // 1) URL パラメータから学生番号を取得
            // ---------------------------------------------
            String no = req.getParameter("no");

            // ---------------------------------------------
            // 2) Lấy giáo viên đang đăng nhập từ session
            // 2) セッションからログイン中の教員を取得
            // ---------------------------------------------
            Teacher teacher = (Teacher) req.getSession().getAttribute("user");
            School school = teacher.getSchool();

            // ---------------------------------------------
            // 3) Lấy thông tin học sinh
            //    SUPERADMIN → get(no)
            //    Admin/Teacher → get(no, school)
            //
            // 3) 学生情報を取得
            //    SUPERADMIN → get(no)
            //    Admin/Teacher → get(no, school)
            // ---------------------------------------------
            StudentDao sDao = new StudentDao();
            Student student;

            if (teacher.getRole() == 2) {
                // SUPERADMIN → không lọc theo school_cd
                // SUPERADMIN → 学校制限なし
                student = sDao.get(no);
            } else {
                // Admin/Teacher → lọc theo school_cd
                // Admin/Teacher → 自分の学校のみ
                student = sDao.get(no, school);
            }

            // ---------------------------------------------
            // 4) Nếu không tìm thấy học sinh → error.jsp
            // 4) 学生情報が存在しない場合 → error.jsp へ
            // ---------------------------------------------
            if (student == null) {
                req.setAttribute("error", "学生情報が存在しません。");
                req.getRequestDispatcher("/error.jsp").forward(req, res);
                return;
            }

            // ---------------------------------------------
            // 5) Lấy danh sách lớp
            // 5) クラス番号一覧を取得
            // ---------------------------------------------
            ClassNumDao cDao = new ClassNumDao();
            List<String> classNumList;

            if (teacher.getRole() == 2) {
                // SUPERADMIN → lấy toàn bộ class
                // SUPERADMIN → 全クラス取得
                classNumList = new ArrayList<>();
                cDao.findAll().forEach(c -> classNumList.add(c.getClass_num()));
            } else {
                // Admin/Teacher → chỉ lấy class của trường
                // Admin/Teacher → 学校に紐づくクラスのみ取得
                classNumList = cDao.filter(school);
            }

            // ---------------------------------------------
            // 6) Tạo danh sách năm nhập học (10 năm trước → 1 năm sau)
            // 6) 入学年度リスト作成（10年前〜翌年）
            // ---------------------------------------------
            List<Integer> entYearList = new ArrayList<>();
            int year = Calendar.getInstance().get(Calendar.YEAR);
            for (int i = year - 10; i <= year + 1; i++) {
                entYearList.add(i);
            }

            // ---------------------------------------------
            // 7) Gửi dữ liệu sang JSP để hiển thị form update
            // 7) JSP 表示用にリクエストへセット
            // ---------------------------------------------
            req.setAttribute("student", student);
            req.setAttribute("class_num_set", classNumList);
            req.setAttribute("ent_year_set", entYearList);

            // ---------------------------------------------
            // 8) Chuyển sang trang student_update.jsp
            // 8) student_update.jsp へフォワード
            // ---------------------------------------------
            req.getRequestDispatcher("/scoremanager/main/student_update.jsp").forward(req, res);

        } catch (Exception e) {
            // ---------------------------------------------
            // Lỗi → chuyển sang error.jsp
            // エラー発生時 → error.jsp へフォワード
            // ---------------------------------------------
            e.printStackTrace();
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}
