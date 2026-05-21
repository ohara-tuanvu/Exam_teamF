package scoremanager.main;

import java.io.IOException;

import bean.School;
import bean.Teacher;
import bean.Test;
import dao.TestDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestDeleteAction extends Action {

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
            Test test = null;

            // ================================
            // C. Phân quyền SuperAdmin
            // ================================
            if (teacher.getRole() == 2) {
                // SUPERADMIN → lấy dữ liệu không giới hạn school
                test = tDao.getForSuperAdmin(studentNo, subjectCd, no);
            } else {
                // Admin / Teacher → chỉ lấy trong trường của họ
                test = tDao.get(studentNo, subjectCd, school, no);
            }

            // Không tìm thấy dữ liệu
            if (test == null) {
                req.setAttribute("message", "成績情報が見つかりません。");
                req.getRequestDispatcher("/error.jsp").forward(req, res);
                return;
            }

            // Gửi dữ liệu sang JSP xác nhận削除
            req.setAttribute("test", test);

            // ================================
            // D. Forward sang màn hình xác nhận削除
            // ================================
            req.getRequestDispatcher("/scoremanager/main/test_delete.jsp").forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}
