package scoremanager.main;

import bean.Teacher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SchoolCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        Teacher loginUser = (Teacher) req.getSession().getAttribute("user");
        // Lấy thông tin giáo viên đang đăng nhập từ session
        // セッションからログイン中の教員情報を取得

        // Chỉ superadmin được tạo trường
        // 学校作成は superadmin のみ許可
        if (loginUser.getRole() != 2) {
            req.setAttribute("message", "権限がありません。");
            // Gửi thông báo lỗi không đủ quyền
            // 権限エラーのメッセージを設定

            req.getRequestDispatcher("/error.jsp").forward(req, res);
            // Chuyển hướng đến trang lỗi
            // error.jsp にフォワード

            return;
            // Dừng xử lý
            // 処理を終了
        }

        req.getRequestDispatcher("/scoremanager/main/school_create.jsp")
           .forward(req, res);
        // Nếu là superadmin → chuyển đến trang tạo trường
        // superadmin の場合 → school_create.jsp にフォワード
    }
}
