package scoremanager.main;
// Package chứa các Action thuộc module scoremanager/main

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;
// Action là lớp cha (framework tự xây) dùng để xử lý request

public class LoginAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        // ローカル変数の宣言 1
        // なし
        // Không có biến cục bộ cần khai báo

        // リクエストパラメータ―の取得 2
        // なし
        // Không lấy request parameter nào ở màn hình login (chỉ hiển thị form)

        // DBからデータ取得 3
        // なし
        // Không truy vấn DB ở bước này

        // ビジネスロジック 4
        // なし
        // Không xử lý logic gì ở màn hình login

        // DBへデータ保存 5
        // なし
        // Không lưu gì vào DB

        // レスポンス値をセット 6
        // なし
        // Không set attribute nào cho request

        // JSPへフォワード 7
        // Chuyển hướng sang trang login.jsp để hiển thị form đăng nhập
        req.getRequestDispatcher("/scoremanager/main/login.jsp").forward(req, res);
    }
}
