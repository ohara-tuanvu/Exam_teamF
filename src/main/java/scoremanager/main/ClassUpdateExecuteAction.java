package scoremanager.main;
// scoremanager/main モジュールに属する Action クラスをまとめるパッケージ
// Package chứa các Action thuộc module scoremanager/main

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

public class ClassUpdateExecuteAction extends Action {

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
            // Lấy dữ liệu từ form
            String oldClassNum = req.getParameter("old_class_num");
            String newClassNum = req.getParameter("class_num");

            // エラーリスト（4）
            List<String> errors = new ArrayList<>();

            // クラス番号チェック（5）
            if (newClassNum == null || newClassNum.trim().isEmpty()) {
                errors.add("クラス番号を入力してください。");
            }

            // エラーがある場合（6）
            if (errors.size() > 0) {

                // 入力値保持
                req.setAttribute("classNum", newClassNum);
                req.setAttribute("oldClassNum", oldClassNum);

                // エラーセット
                req.setAttribute("errors", errors);

                // 入力画面へ戻す
                req.getRequestDispatcher("/scoremanager/main/class_update.jsp").forward(req, res);
                return;
            }

            // ClassNum インスタンス作成（7）
            ClassNum cn = new ClassNum();
            cn.setClass_num(newClassNum);
            cn.setSchool(school);

            // DB更新（8）
            ClassNumDao dao = new ClassNumDao();
            dao.update(cn, oldClassNum);

            // 完了メッセージ（9）
            req.setAttribute("message", "変更が完了しました。");

            // 完了画面へフォワード（10）
            req.getRequestDispatcher("/scoremanager/main/class_update_done.jsp").forward(req, res);

        } catch (Exception e) {
            // エラー発生時（12）
            e.printStackTrace();
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}
