package scoremanager;
// Package chứa các Action thuộc module scoremanager (màn hình login chính)

import java.util.ArrayList;
import java.util.List;

import bean.Teacher;
import dao.TeacherDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;
// Action là lớp cha dùng để xử lý request theo mô hình MVC

public class LoginExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ローカル変数の宣言 1
        // Khai báo biến cục bộ
        String url = "";
        String id = "";
        String password = "";
        TeacherDao teacherDao = new TeacherDao();
        Teacher teacher = null;

        // リクエストパラメータ―の取得 2
        // Lấy ID và password từ form login.jsp
        id = req.getParameter("id");          // 教員ID
        password = req.getParameter("password"); // パスワード

        // DBからデータ取得 3
        // Gọi DAO để kiểm tra đăng nhập
        teacher = teacherDao.login(id, password);

        // ビジネスロジック 4〜7
        // Phân nhánh theo kết quả đăng nhập
        if (teacher != null) { // 認証成功の場合

            // セッション情報を取得
            // Lấy session hiện tại (true = tạo mới nếu chưa có)
            HttpSession session = req.getSession(true);

            // 認証済みフラグを立てる
            // Đánh dấu giáo viên đã đăng nhập
            teacher.setAuthenticated(true);

            // セッションにログイン情報を保存
            // Lưu thông tin giáo viên vào session
            session.setAttribute("user", teacher);

            // リダイレクト
            // Chuyển hướng sang Menu.action (không dùng forward)
            res.sendRedirect(req.getContextPath() + "/scoremanager/main/Menu.action");

        } else {
            // 認証失敗の場合
            // Nếu đăng nhập thất bại

            // エラーメッセージをセット
            // Tạo danh sách lỗi để hiển thị trên login.jsp
            List<String> errors = new ArrayList<>();
            errors.add("IDまたはパスワードが確認できませんでした");
            req.setAttribute("errors", errors);

            // 入力された教員IDをセット
            // Giữ lại ID người dùng đã nhập
            req.setAttribute("id", id);

            // フォワード
            // Quay lại trang login.jsp
            url = "/scoremanager/main/login.jsp";
            req.getRequestDispatcher(url).forward(req, res);
        }
    }
}
