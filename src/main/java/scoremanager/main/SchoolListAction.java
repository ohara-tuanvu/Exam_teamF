package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Teacher;
import dao.SchoolDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SchoolListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        Teacher loginUser = (Teacher) req.getSession().getAttribute("user");
        // Lấy giáo viên đang đăng nhập từ session
        // セッションからログイン中の教員情報を取得

        // Chỉ superadmin được xem danh sách trường
        // 学校一覧の閲覧は superadmin のみ許可
        if (loginUser.getRole() != 2) {
            req.setAttribute("message", "権限がありません。");
            // Gửi thông báo lỗi không đủ quyền
            // 権限エラーメッセージを設定

            req.getRequestDispatcher("/error.jsp").forward(req, res);
            // Chuyển hướng đến trang lỗi
            // error.jsp にフォワード

            return;
            // Dừng xử lý
            // 処理を終了
        }

        SchoolDao dao = new SchoolDao();
        // Tạo DAO để thao tác DB
        // DB操作のために SchoolDao を生成

        List<School> list = dao.getAll();
        // Lấy toàn bộ danh sách trường từ DB
        // DBから学校一覧を取得

        req.setAttribute("school_list", list);
        // Gửi danh sách trường sang JSP
        // JSP に学校一覧を渡す

        req.getRequestDispatcher("/scoremanager/main/school_list.jsp")
           .forward(req, res);
        // Chuyển đến trang hiển thị danh sách trường
        // school_list.jsp にフォワード
    }
}
