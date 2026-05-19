package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Teacher;
import dao.SchoolDao;
import dao.TeacherDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TeacherUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        String id = req.getParameter("id");
        // Lấy ID giáo viên từ URL
        // URL から教員IDを取得

        TeacherDao tDao = new TeacherDao();
        Teacher teacher = tDao.get(id);
        // Lấy thông tin giáo viên theo ID
        // ID に対応する教員情報を取得

        req.setAttribute("teacher", teacher);
        // Gửi đối tượng teacher sang JSP
        // JSP に教員オブジェクトを渡す

        // Lấy user đang đăng nhập
        // ログイン中の教員を取得
        Teacher loginUser = (Teacher) req.getSession().getAttribute("user");

        // ⭐ SuperAdmin → load danh sách trường
        // superadmin(role=2) → 学校一覧を読み込む
        if (loginUser.getRole() == 2) {
            SchoolDao sDao = new SchoolDao();
            List<School> schoolList = sDao.getAll();
            // Lấy toàn bộ danh sách trường
            // 全学校一覧を取得

            req.setAttribute("school_list", schoolList);
            // Gửi danh sách trường sang JSP
            // JSP に学校一覧を渡す
        }

        // ⭐ Admin chi nhánh → KHÔNG load school_list
        // vì họ không được đổi trường
        // 支部管理者(role=1) → 学校変更不可のため school_list を読み込まない

        // ⭐ Giáo viên → không có quyền
        // 教員(role=0) → 権限なし
        if (loginUser.getRole() == 0) {
            req.setAttribute("message", "権限がありません。");
            // Gửi thông báo lỗi
            // 権限エラーメッセージを設定

            req.getRequestDispatcher("/error.jsp").forward(req, res);
            // error.jsp にフォワード
            return;
        }

        req.getRequestDispatcher("/scoremanager/main/teacher_update.jsp")
           .forward(req, res);
        // Chuyển đến trang cập nhật giáo viên
        // 教員更新ページにフォワード
    }
}
