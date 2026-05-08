package scoremanager.main;
// scoremanager/main モジュールに属する Action クラスをまとめるパッケージ
// Package chứa các Action thuộc module scoremanager/main

import java.io.IOException;

import bean.ClassNum;
import bean.School;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;
// Action は MVC パターンでリクエスト処理を行う基底クラス
// Action là lớp cha dùng để xử lý request theo mô hình MVC

public class ClassDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            // セッションから教員を取得（1）
            // Lấy giáo viên đang đăng nhập
            Teacher teacher = (Teacher) req.getSession().getAttribute("user");

            // 学校情報を取得（2）
            // Lấy trường
            School school = teacher.getSchool();

            // リクエストパラメータ取得（3）
            // Lấy class_num từ URL
            String classNumStr = req.getParameter("classNum");

            // クラス情報取得（4）
            // Lấy thông tin class từ DB
            ClassNumDao dao = new ClassNumDao();
            ClassNum cn = dao.get(classNumStr, school);

            // 画面へ渡す（5）
            // Gửi dữ liệu sang JSP
            req.setAttribute("classNum", cn);

            // JSPへフォワード（6）
            // Chuyển sang màn hình xác nhận xóa
            req.getRequestDispatcher("/scoremanager/main/class_delete.jsp").forward(req, res);

        } catch (Exception e) {
            // エラー発生時（7）
            e.printStackTrace();
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}
