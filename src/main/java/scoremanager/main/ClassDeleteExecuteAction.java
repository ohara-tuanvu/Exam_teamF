package scoremanager.main;
// scoremanager/main モジュールに属する Action クラスをまとめるパッケージ
// Package chứa các Action thuộc module scoremanager/main

import java.io.IOException;

import bean.School;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;
// Action は MVC パターンでリクエスト処理を行う基底クラス
// Action là lớp cha dùng để xử lý request theo mô hình MVC

public class ClassDeleteExecuteAction extends Action {

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
            // Lấy class_num cần xóa
            String classNumStr = req.getParameter("classNum");

            // DB削除（4）
            // Xóa class khỏi DB
            ClassNumDao dao = new ClassNumDao();
            dao.delete(classNumStr, school);

            // 完了メッセージ（5）
            req.setAttribute("message", "削除が完了しました。");

            // 完了画面へフォワード（6）
            req.getRequestDispatcher("/scoremanager/main/class_delete_done.jsp").forward(req, res);


        } catch (Exception e) {
            // エラー発生時（8）
            e.printStackTrace();
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}
