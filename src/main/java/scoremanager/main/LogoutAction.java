package scoremanager.main;
// Package chứa các Action thuộc module scoremanager/main

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
// HttpSession dùng để quản lý phiên đăng nhập của người dùng
import tool.Action;
// Action là lớp cha (framework tự xây) dùng để xử lý request

public class LogoutAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        // ローカル変数の宣言 1
        // Khai báo biến cục bộ
        String url = "";
        HttpSession session = req.getSession();  
        // Lấy session hiện tại của người dùng

        // リクエストパラメータ―の取得 2
        // なし
        // Không nhận tham số nào từ request

        // DBからデータ取得 3
        // なし
        // Không truy vấn DB khi logout

        // ビジネスロジック 4
        // Xử lý logic đăng xuất
        if (session.getAttribute("user") != null) {
            // Nếu session có attribute "user" → người dùng đang đăng nhập
            session.invalidate();  
            // Hủy toàn bộ session → đăng xuất hoàn toàn
        }

        // DBへデータ保存 5
        // なし
        // Không lưu gì vào DB

        // レスポンス値をセット 6
        // なし
        // Không set attribute nào cho request

        // JSPへフォワード 7
        // Chuyển hướng sang trang logout.jsp để thông báo đăng xuất thành công
        url = "logout.jsp";
        req.getRequestDispatcher(url).forward(req, res);
    }
}
