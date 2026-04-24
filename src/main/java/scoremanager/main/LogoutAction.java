package scoremanager.main;
// scoremanager/main モジュールに属する Action クラスをまとめるパッケージ
// Package chứa các Action thuộc module scoremanager/main

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
// HttpSession はユーザーのログイン状態を管理する仕組み
// HttpSession dùng để quản lý phiên đăng nhập của người dùng
import tool.Action;
// Action はリクエスト処理を行う基底クラス（自作フレームワーク）
// Action là lớp cha (framework tự xây) dùng để xử lý request

public class LogoutAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        // ローカル変数の宣言（1）
        // Khai báo biến cục bộ
        String url = "";
        HttpSession session = req.getSession();
        // 現在のユーザーセッションを取得
        // Lấy session hiện tại của người dùng

        // リクエストパラメータの取得（2）
        // ログアウト画面ではパラメータ取得なし
        // Không nhận tham số nào từ request

        // DBからデータ取得（3）
        // ログアウト処理ではDBアクセス不要
        // Không truy vấn DB khi logout

        // ビジネスロジック（4）
        // ログアウト処理（セッション破棄）
        // Xử lý logic đăng xuất (hủy session)
        if (session.getAttribute("user") != null) {
            // セッションに"user"がある → ログイン中
            // Nếu session có "user" → người dùng đang đăng nhập
            session.invalidate();
            // セッションを無効化 → 完全ログアウト
            // Hủy toàn bộ session → đăng xuất hoàn toàn
        }

        // DBへデータ保存（5）
        // 保存処理なし
        // Không lưu dữ liệu vào DB

        // レスポンス値をセット（6）
        // 特にセットしない
        // Không set attribute nào cho request

        // JSPへフォワード（7）
        // logout.jsp へフォワードしてログアウト完了画面を表示
        // Chuyển sang logout.jsp để hiển thị màn hình logout
        url = "logout.jsp";
        req.getRequestDispatcher(url).forward(req, res);
    }
}