package tool;
// Package chứa các lớp tiện ích (framework tự xây) dùng chung cho toàn hệ thống
// システム全体で共通利用する自作フレームワークのユーティリティパッケージ

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// Các import cần thiết cho Servlet
// Servlet 実装に必要なクラスをインポート

@WebServlet(urlPatterns = { "*.action" })
// Servlet này sẽ xử lý TẤT CẢ URL kết thúc bằng .action
// 例: /scoremanager/main/Login.action → このサーブレットが処理する
// *.action をフロントコントローラとして一括処理

public class FrontController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            // パスを取得
            // Lấy servlet path, ví dụ: "/scoremanager/main/Login.action"
            // リクエストされたパスを取得
            String path = req.getServletPath().substring(1); 
            // substring(1) để bỏ dấu "/" đầu → "scoremanager/main/Login.action"
            // 先頭の "/" を除去してパス文字列を整形

            // ファイル名を取得しクラス名に変換
            // Chuyển path thành tên class Action
            // "scoremanager/main/Login.action"
            // → "scoremanager.main.LoginAction"
            // パスをパッケージ形式に変換し、Action クラス名を生成
            String name = path.replace('/', '.').replace(".action", "Action");

            System.out.println("★ servlet path → " + req.getServletPath());
            System.out.println("★ class name → " + name);

            // アクションクラスのインスタンスを返却
            // Tạo instance của class Action bằng Reflection
            // リフレクションを使って Action クラスのインスタンスを生成
            Action action = (Action) Class.forName(name).getDeclaredConstructor().newInstance();

            // 遷移先URLを取得
            // Gọi execute() của Action tương ứng
            // Action の処理を実行（画面遷移やロジックは各 Action に委譲）
            action.execute(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            // エラーページへリダイレクト
            // Nếu có lỗi → chuyển sang error.jsp
            // 例外発生時は error.jsp へフォワード
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // POST cũng xử lý giống GET
        // POST リクエストも GET と同じ処理を行う
        doGet(req, res);
    }
}
