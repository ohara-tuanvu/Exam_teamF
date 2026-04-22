package scoremanager.main;
// Package chứa các Action thuộc module scoremanager/main

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;
// Action là lớp cha (framework tự xây) dùng để xử lý request

public class MenuAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ローカル変数の宣言 1
        // なし
        // Không có biến cục bộ cần khai báo

        // リクエストパラメータ―の取得 2
        // なし
        // Không lấy request parameter nào

        // DBからデータ取得 3
        // なし
        // Không truy vấn DB ở màn hình menu

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
        // Chuyển hướng sang trang menu.jsp để hiển thị menu chính
        req.getRequestDispatcher("menu.jsp").forward(req, res);
    }
}
