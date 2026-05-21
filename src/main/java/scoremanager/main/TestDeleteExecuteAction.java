package scoremanager.main;

import java.io.IOException;

import bean.School;
import bean.Teacher;
import dao.TestDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            // ================================
            // A. Lấy tham số từ URL
            // ================================
            String studentNo = req.getParameter("studentNo");
            String subjectCd = req.getParameter("subjectCd");
            String noStr = req.getParameter("no");

            int no = Integer.parseInt(noStr);

            // ================================
            // B. Lấy thông tin user
            // ================================
            Teacher teacher = (Teacher) req.getSession().getAttribute("user");
            School school = teacher.getSchool();

            TestDao tDao = new TestDao();
            boolean deleted = false;

            // ================================
            // C. Phân quyền SuperAdmin
            // ================================
            if (teacher.getRole() == 2) {
                // SUPERADMIN → xóa không giới hạn school
                deleted = tDao.deleteForSuperAdmin(studentNo, subjectCd, no);
            } else {
                // Admin / Teacher → chỉ xóa trong trường của họ
                deleted = tDao.delete(studentNo, subjectCd, school, no);
            }

            // Nếu xóa thất bại → báo lỗi
            if (!deleted) {
                req.setAttribute("message", "成績情報の削除に失敗しました。");
                req.getRequestDispatcher("/error.jsp").forward(req, res);
                return;
            }

            // ================================
            // D. Xóa thành công → chuyển sang màn hình 完了
            // ================================
            req.getRequestDispatcher("/scoremanager/main/test_delete_done.jsp").forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}
