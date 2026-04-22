package scoremanager;
// Package chứa các Action thuộc module scoremanager (màn hình login chính)

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;
// Action là lớp cha dùng để xử lý request theo mô hình MVC

public class LoginAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        // ローカル変数の宣言 1
        // なし
        // Không có biến cục bộ cần khai báo

        // リクエストパラメータ―の取得 2
        // なし
        // Màn hình login chỉ hiển thị form → không lấy tham số nào

        // DBからデータ取得 3
        // なし
        // Không truy vấn DB ở bước hiển thị form login

        // ビジネスロジック 4
        // なし
        // Không xử lý logic gì ở đây

        // DBへデータ保存 5
        // なし
        // Không lưu gì vào DB

        // レスポンス値をセット 6
        // なし
        // Không set attribute nào cho request

        // JSPへフォワード 7
        // Chuyển hướng sang trang login.jsp để hiển thị form đăng nhập
        req.getRequestDispatcher("login.jsp").forward(req, res);
    }
}
