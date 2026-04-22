package scoremanager.main;
// Package chứa các Action thuộc module scoremanager/main

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
// Action là lớp cha dùng để xử lý request theo mô hình MVC

public class StudentUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            // パラメータ（学生番号）取得
            // Lấy student number từ URL (?no=xxxx)
            String no = req.getParameter("no");

            // セッションから教員を取得
            // Lấy giáo viên đang đăng nhập từ session
            Teacher teacher = (Teacher) req.getSession().getAttribute("user");
            School school = teacher.getSchool(); // Lấy trường của giáo viên

            // 学生情報を取得
            // Lấy thông tin học sinh theo no + school_cd
            StudentDao sDao = new StudentDao();
            Student student = sDao.get(no, school);

            // 学生が存在しない場合
            // Nếu không tìm thấy học sinh → chuyển sang error.jsp
            if (student == null) {
                req.setAttribute("error", "学生情報が存在しません。");
                req.getRequestDispatcher("/error.jsp").forward(req, res);
                return;
            }

            // クラス番号一覧を取得
            // Lấy danh sách class_num theo trường
            ClassNumDao cDao = new ClassNumDao();
            List<String> classNumList = cDao.filter(school);

            // 入学年度リストを作成（10年前〜1年後）
            // Tạo danh sách năm nhập học từ (năm hiện tại -10) → (năm hiện tại +1)
            List<Integer> entYearList = new ArrayList<>();
            int year = Calendar.getInstance().get(Calendar.YEAR);
            for (int i = year - 10; i <= year + 1; i++) {
                entYearList.add(i);
            }

            // リクエストにセット
            // Gửi dữ liệu sang JSP để hiển thị form update
            req.setAttribute("student", student);
            req.setAttribute("class_num_set", classNumList);
            req.setAttribute("ent_year_set", entYearList);

            // JSPへフォワード
            // Chuyển sang trang student_update.jsp
            req.getRequestDispatcher("/scoremanager/main/student_update.jsp").forward(req, res);

        } catch (Exception e) {
            // Nếu có lỗi → chuyển sang error.jsp
            e.printStackTrace();
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}
