package scoremanager.main;

import bean.Teacher;
import dao.TeacherDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TeacherDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        String id = req.getParameter("id");
        // Lấy ID giáo viên từ URL
        // URL から教員IDを取得

        TeacherDao dao = new TeacherDao();
        // Tạo DAO để thao tác DB
        // DB操作のために TeacherDao を生成

        Teacher teacher = dao.get(id);
        // Lấy thông tin giáo viên theo ID
        // ID に対応する教員情報を取得

        req.setAttribute("teacher", teacher);
        // Gửi đối tượng teacher sang JSP để hiển thị xác nhận
        // JSP に教員オブジェクトを渡して確認画面に表示

        req.getRequestDispatcher("/scoremanager/main/teacher_delete.jsp")
           .forward(req, res);
        // Chuyển đến trang xác nhận xóa
        // 削除確認ページにフォワード
    }
}
