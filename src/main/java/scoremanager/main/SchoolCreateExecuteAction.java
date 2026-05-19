package scoremanager.main;

import bean.School;
import bean.Teacher;
import dao.SchoolDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SchoolCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        Teacher loginUser = (Teacher) req.getSession().getAttribute("user");
        // Lấy giáo viên đang đăng nhập từ session
        // セッションからログイン中の教員情報を取得

        if (loginUser.getRole() != 2) {
            // Nếu không phải superadmin → không có quyền
            // superadmin 以外は権限なし
            req.setAttribute("message", "権限がありません。");
            // Gửi thông báo lỗi
            // エラーメッセージを設定

            req.getRequestDispatcher("/error.jsp").forward(req, res);
            // Chuyển hướng đến trang lỗi
            // error.jsp にフォワード

            return;
            // Dừng xử lý
            // 処理を終了
        }

        String cd = req.getParameter("cd");
        // Lấy mã trường từ form
        // フォームから学校コードを取得

        String name = req.getParameter("name");
        // Lấy tên trường từ form
        // フォームから学校名を取得

        School s = new School();
        // Tạo đối tượng School mới
        // 新しい School オブジェクトを生成

        s.setCd(cd);
        // Gán mã trường
        // 学校コードをセット

        s.setName(name);
        // Gán tên trường
        // 学校名をセット

        SchoolDao dao = new SchoolDao();
        // Tạo DAO để thao tác DB
        // DB操作のために SchoolDao を生成

        dao.insert(s);
        // Thêm trường vào DB
        // 学校をDBに登録

        req.setAttribute("message", "登録完了しました。");
        // Gửi thông báo hoàn tất
        // 登録完了メッセージを設定

        req.getRequestDispatcher("/scoremanager/main/school_create_done.jsp")
           .forward(req, res);
        // Chuyển đến trang hoàn tất
        // 完了ページにフォワード
    }
}
