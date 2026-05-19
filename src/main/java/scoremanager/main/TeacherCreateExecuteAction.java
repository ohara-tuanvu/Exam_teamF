package scoremanager.main;

import bean.Teacher;
import dao.TeacherDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TeacherCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        req.setCharacterEncoding("UTF-8");
        // Thiết lập mã hóa UTF-8 cho request
        // リクエストの文字コードを UTF-8 に設定

        // Lấy user đang đăng nhập
        // ログイン中の教員を取得
        Teacher loginUser = (Teacher) req.getSession().getAttribute("user");

        if (loginUser == null) {
            // Nếu chưa đăng nhập → chuyển về login
            // 未ログインの場合 → login.jsp へリダイレクト
            res.sendRedirect("login.jsp");
            return;
        }

        // Lấy dữ liệu từ form
        // フォームからデータを取得
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        int role = Integer.parseInt(req.getParameter("role"));

        String schoolCd;

        // SuperAdmin → lấy từ form
        // superadmin → フォームから school_cd を取得
        if (loginUser.getRole() == 2) {
            schoolCd = req.getParameter("school_cd");
        }
        // Admin chi nhánh → luôn dùng school_cd của mình
        // 支部管理者(role=1) → 自分の school_cd を使用
        else if (loginUser.getRole() == 1) {
            schoolCd = loginUser.getSchoolCd();
        }
        // Giáo viên → không có quyền
        // 教員(role=0) → 権限なし
        else {
            req.setAttribute("message", "権限がありません。");
            // Thông báo lỗi không đủ quyền
            // 権限エラーメッセージを設定

            req.getRequestDispatcher("/error.jsp").forward(req, res);
            // error.jsp にフォワード
            return;
        }

        TeacherDao dao = new TeacherDao();
        // Tạo DAO để thao tác DB
        // DB操作のために TeacherDao を生成

        // ⭐ Kiểm tra ID trùng
        // ID 重複チェック
        if (dao.get(id) != null) {
            req.setAttribute("error", "このIDは既に存在しています。");
            // Gửi thông báo lỗi ID đã tồn tại
            // 既に存在するIDのエラーメッセージを設定

            // ⭐ Quan trọng: phải forward về Action, không phải JSP
            // 重要：JSP ではなく Action にフォワードする必要がある
            req.getRequestDispatcher("TeacherCreate.action")
               .forward(req, res);
            return;
        }

        // Tạo đối tượng Teacher
        // Teacher オブジェクトを生成
        Teacher t = new Teacher();
        t.setId(id);               // Gán ID
        // ID をセット
        t.setName(name);           // Gán tên
        // 名前 をセット
        t.setPassword(password);   // Gán mật khẩu
        // パスワード をセット
        t.setRole(role);           // Gán role
        // 権限(role) をセット
        t.setSchoolCd(schoolCd);   // Gán mã trường
        // 学校コード をセット

        // Insert vào DB
        // DB に登録
        dao.insert(t);

        // Thông báo hoàn tất
        // 登録完了メッセージを設定
        req.setAttribute("message", "登録完了しました。");

        req.getRequestDispatcher("/scoremanager/main/teacher_create_done.jsp")
           .forward(req, res);
        // Chuyển đến trang hoàn tất
        // 完了ページにフォワード
    }
}
