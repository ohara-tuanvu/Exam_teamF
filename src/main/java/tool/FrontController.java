package tool;
// Package chứa các lớp tiện ích (framework tự xây) dùng chung cho toàn hệ thống

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// Các import cần thiết cho Servlet

@WebServlet(urlPatterns = { "*.action" })
// Servlet này sẽ xử lý TẤT CẢ URL kết thúc bằng .action
// Ví dụ: /scoremanager/main/Login.action → vào đây

public class FrontController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            // パスを取得
            // Lấy servlet path, ví dụ: "/scoremanager/main/Login.action"
            String path = req.getServletPath().substring(1); 
            // substring(1) để bỏ dấu "/" đầu → "scoremanager/main/Login.action"

            // ファイル名を取得しクラス名に変換
            // Chuyển path thành tên class Action
            // "scoremanager/main/Login.action"
            // → "scoremanager.main.LoginAction"
            String name = path.replace('/', '.').replace(".action", "Action");

            System.out.println("★ servlet path → " + req.getServletPath());
            System.out.println("★ class name → " + name);

            // アクションクラスのインスタンスを返却
            // Tạo instance của class Action bằng Reflection
            Action action = (Action) Class.forName(name).getDeclaredConstructor().newInstance();

            // 遷移先URLを取得
            // Gọi execute() của Action tương ứng
            action.execute(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            // エラーページへリダイレクト
            // Nếu có lỗi → chuyển sang error.jsp
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // POST cũng xử lý giống GET
        doGet(req, res);
    }
}
