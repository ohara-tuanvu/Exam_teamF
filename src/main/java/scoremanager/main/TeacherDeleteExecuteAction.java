package scoremanager.main;

import dao.TeacherDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TeacherDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        String id = req.getParameter("id");
        // Lấy ID giáo viên từ form (hidden input)
        // フォーム（hidden）から教員IDを取得

        TeacherDao dao = new TeacherDao();
        // Tạo DAO để thao tác DB
        // DB操作のために TeacherDao を生成

        dao.delete(id);
        // Xóa giáo viên theo ID
        // ID に対応する教員を削除

        req.setAttribute("message", "削除完了しました。");
        // Gửi thông báo hoàn tất xóa
        // 削除完了メッセージを設定

        req.getRequestDispatcher("/scoremanager/main/teacher_delete_done.jsp")
           .forward(req, res);
        // Chuyển đến trang hoàn tất
        // 完了ページにフォワード
    }
}
