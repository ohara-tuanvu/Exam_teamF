package scoremanager.main;
// JP: scoremanager/main モジュールに属する Action クラスをまとめるパッケージ
// VN: Các Action thuộc module scoremanager/main

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bean.School;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SchoolDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            // ---------------------------------------------------------
            // (1) セッションからログイン中の教師情報を取得
            // (1) Lấy thông tin giáo viên đang đăng nhập từ session
            // ---------------------------------------------------------
            Teacher teacher = (Teacher) req.getSession().getAttribute("user");

            // ---------------------------------------------------------
            // (2) SUPERADMIN の場合 → 学校一覧をロードして JSP に渡す
            // (2) Nếu SUPERADMIN → load danh sách trường và gửi sang JSP
            // ---------------------------------------------------------
            if (teacher.getRole() == 2) {
                SchoolDao schoolDao = new SchoolDao();
                req.setAttribute("school_list", schoolDao.findAll());
            }

            // ---------------------------------------------------------
            // (3) 教師に紐づく学校情報を取得（Admin/Teacher 用）
            // (3) Lấy trường của giáo viên (dành cho Admin/Teacher)
            // ---------------------------------------------------------
            School school = teacher.getSchool();

            // ---------------------------------------------------------
            // (4) クラス番号一覧を取得
            // (4) Lấy danh sách lớp
            // ---------------------------------------------------------
            ClassNumDao cDao = new ClassNumDao();
            List<String> classNumList;

            if (teacher.getRole() == 2) {
                // JP: SUPERADMIN → 全クラス取得
                // VN: SUPERADMIN → lấy toàn bộ lớp
                classNumList = new ArrayList<>();
                cDao.findAll().forEach(c -> classNumList.add(c.getClass_num()));
            } else {
                // JP: Admin/Teacher → 自分の学校のクラスのみ取得
                // VN: Admin/Teacher → chỉ lấy lớp thuộc trường của mình
                classNumList = cDao.filter(school);
            }

            // ---------------------------------------------------------
            // (5) 入学年度リストを作成（現在年 ±10 年）
            // (5) Tạo danh sách năm nhập học (năm hiện tại ±10)
            // ---------------------------------------------------------
            List<Integer> entYearList = new ArrayList<>();
            int year = Calendar.getInstance().get(Calendar.YEAR);

            for (int i = year - 10; i <= year + 1; i++) {
                entYearList.add(i);
            }

            // ---------------------------------------------------------
            // (6) JSP 表示用にデータをリクエストへセット
            // (6) Gắn dữ liệu vào request để JSP hiển thị
            // ---------------------------------------------------------
            req.setAttribute("class_num_set", classNumList);
            req.setAttribute("ent_year_set", entYearList);

            // ---------------------------------------------------------
            // (7) student_insert.jsp へフォワード
            // (7) Chuyển sang màn hình student_insert.jsp
            // ---------------------------------------------------------
            req.getRequestDispatcher("/scoremanager/student_insert.jsp").forward(req, res);

        } catch (Exception e) {
            // ---------------------------------------------------------
            // (8) エラー発生時 → error.jsp へフォワード
            // (8) Nếu có lỗi → chuyển sang error.jsp
            // ---------------------------------------------------------
            e.printStackTrace();
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}
