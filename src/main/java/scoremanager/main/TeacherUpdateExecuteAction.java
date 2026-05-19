package scoremanager.main;

import bean.Teacher;
import dao.TeacherDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TeacherUpdateExecuteAction extends Action {

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
            // Nếu chưa đăng nhập → quay về login
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

        TeacherDao dao = new TeacherDao();
        // Tạo DAO để thao tác DB
        // DB操作のために TeacherDao を生成

        // ⭐ Kiểm tra teacher có tồn tại không
        // 教員が存在するかチェック
        Teacher oldTeacher = dao.get(id);
        if (oldTeacher == null) {
            req.setAttribute("message", "教員が存在しません。");
            // Không tìm thấy giáo viên
            // 教員が見つからない場合

            req.getRequestDispatcher("/error.jsp").forward(req, res);
            // エラーページにフォワード
            return;
        }

        String schoolCd;

        // ⭐ SuperAdmin → được đổi trường
        // superadmin(role=2) → 学校変更可能
        if (loginUser.getRole() == 2) {
            schoolCd = req.getParameter("school_cd");
        }

        // ⭐ Admin chi nhánh → KHÔNG được đổi trường
        // 支部管理者(role=1) → 学校変更不可
        else if (loginUser.getRole() == 1) {
            schoolCd = loginUser.getSchoolCd();
        }

        // ⭐ Giáo viên → không có quyền
        // 教員(role=0) → 権限なし
        else {
            req.setAttribute("message", "権限がありません。");
            // 権限エラーメッセージを設定

            req.getRequestDispatcher("/error.jsp").forward(req, res);
            // エラーページにフォワード
            return;
        }

        // ⭐ Tạo đối tượng Teacher mới để update
        // 更新用の Teacher オブジェクトを作成
        Teacher t = new Teacher();
        t.setId(id);               // ID không đổi / ID は変更不可
        t.setName(name);           // Tên mới / 新しい名前
        t.setPassword(password);   // Mật khẩu mới / 新しいパスワード
        t.setRole(role);           // Role mới / 新しい権限
        t.setSchoolCd(schoolCd);   // Trường (superadmin được đổi) / 学校コード

        // ⭐ Update DB
        // DB を更新
        dao.update(t);

        // ⭐ Thông báo hoàn tất
        // 更新完了メッセージを設定
        req.setAttribute("message", "更新が完了しました。");

        req.getRequestDispatcher("/scoremanager/main/teacher_update_done.jsp")
           .forward(req, res);
        // 完了ページにフォワード
    }
}
