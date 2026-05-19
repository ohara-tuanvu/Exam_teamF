package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Teacher;
import dao.SchoolDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TeacherCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        // Lấy user đang đăng nhập
        // ログイン中のユーザー（教員）を取得
        Teacher loginUser = (Teacher) req.getSession().getAttribute("user");

        // Tạo DAO
        // DAO を生成
        SchoolDao sDao = new SchoolDao();

        // TẠM THỜI: role = 1 và role = 2 đều được chọn trường
        // 一時的に：role=1 と role=2 は学校選択が可能
        if (loginUser.getRole() == 1 || loginUser.getRole() == 2) {
            List<School> schoolList = sDao.getAll();
            // Lấy toàn bộ danh sách trường
            // 全学校一覧を取得

            req.setAttribute("school_list", schoolList);
            // Gửi danh sách trường sang JSP
            // JSP に学校一覧を渡す
        }

        // Forward sang JSP
        // JSP にフォワード
        req.getRequestDispatcher("/scoremanager/main/teacher_create.jsp")
           .forward(req, res);
    }
}
