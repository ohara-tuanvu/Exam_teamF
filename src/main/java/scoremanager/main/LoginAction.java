package scoremanager.main;
// scoremanager/main モジュールに属する Action クラスをまとめるパッケージ
// Package chứa các Action thuộc module scoremanager/main

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;
// Action はリクエスト処理を行うための基底クラス（自作フレームワーク）
// Action là lớp cha (framework tự xây) dùng để xử lý request

public class LoginAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        // ローカル変数の宣言（1）
        // Không có biến cục bộ cần khai báo

        // リクエストパラメータの取得（2）
        // Màn hình login chỉ hiển thị form → không lấy request parameter

        // DBからデータ取得（3）
        // Không truy vấn DB ở bước hiển thị form login

        // ビジネスロジック（4）
        // Không xử lý logic gì ở màn hình login

        // DBへデータ保存（5）
        // Không lưu dữ liệu vào DB

        // レスポンス値をセット（6）
        // Không set attribute nào cho request

        // JSPへフォワード（7）
        // ログイン画面（login.jsp）へフォワードしてフォームを表示
        // Chuyển hướng sang login.jsp để hiển thị form đăng nhập
        req.getRequestDispatcher("/scoremanager/main/login.jsp").forward(req, res);
    }
}