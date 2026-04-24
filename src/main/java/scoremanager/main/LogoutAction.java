package scoremanager.main;
// Package chứa các Action thuộc module scoremanager/main
// scoremanager/main モジュールに属する Action クラスをまとめるパッケージ

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
// HttpSession dùng để quản lý phiên đăng nhập của người dùng
// HttpSession はユーザーのログイン状態を管理するための仕組み
import tool.Action;
// Action là lớp cha (framework tự xây) dùng để xử lý request
// Action はリクエスト処理を行う基底クラス（自作フレームワーク）

public class LogoutAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        // ローカル変数の宣言 1
        // Khai báo biến cục bộ
        // ローカル変数 url を宣言
        String url = "";
        HttpSession session = req.getSession();  
        // Lấy session hiện tại của người dùng
        // 現在のユーザーセッションを取得

        // リクエストパラメータ―の取得 2
        // なし
        // Không nhận tham số nào từ request
        // ログアウト画面ではパラメータ取得なし

        // DBからデータ取得 3
        // なし
        // Không truy vấn DB khi logout
        // ログアウト処理ではDBアクセス不要

        // ビジネスロジック 4
        // Xử lý logic đăng xuất
        // ログアウト処理（セッション破棄）
        if (session.getAttribute("user") != null) {
            // Nếu session có attribute "user" → người dùng đang đăng nhập
            // セッションに"user"がある → ログイン中
            session.invalidate();  
            // Hủy toàn bộ session → đăng xuất hoàn toàn
            // セッションを無効化 → 完全ログアウト
        }

        // DBへデータ保存 5
        // なし
        // Không lưu gì vào DB
        // 保存処理なし

        // レスポンス値をセット 6
        // なし
        // Không set attribute nào cho request
        // 特にレスポンス値をセットしない

        // JSPへフォワード 7
        // Chuyển hướng sang trang logout.jsp để thông báo đăng xuất thành công
        // logout.jsp へフォワードしてログアウト完了画面を表示
        url = "logout.jsp";
        req.getRequestDispatcher(url).forward(req, res);
    }
}
