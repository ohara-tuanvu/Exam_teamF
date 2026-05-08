package scoremanager.main;
// scoremanager/main モジュールに属する Action クラスをまとめるパッケージ
// Package chứa các Action thuộc module scoremanager/main

import java.io.IOException;

import bean.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;
// Action は MVC パターンでリクエスト処理を行う基底クラス
// Action là lớp cha dùng để xử lý request theo mô hình MVC

public class ClassCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            // セッションからユーザ（教員）を取得（1）
            // セッションに保存されているログイン中の教員情報を取得
            // Lấy đối tượng Teacher từ session (người đang đăng nhập)
            Teacher teacher = (Teacher) req.getSession().getAttribute("user");

            // ※ クラス登録画面では学校情報やリスト取得は不要
            // ※ Màn hình đăng ký クラス không cần lấy danh sách gì thêm

            // JSPへフォワード（2）
            // クラス登録フォーム（class_create.jsp）へフォワード
            // Chuyển sang class_create.jsp để hiển thị form đăng ký lớp
            req.getRequestDispatcher("/scoremanager/main/class_create.jsp").forward(req, res);

        } catch (Exception e) {
            // エラー発生時（3）
            // ログ出力して error.jsp へフォワード
            // Nếu có lỗi → in log và chuyển sang error.jsp
            e.printStackTrace();
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}
