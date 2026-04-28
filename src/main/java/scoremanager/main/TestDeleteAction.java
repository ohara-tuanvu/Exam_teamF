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
            // URL ví dụ:
            // TestDelete.action?studentNo=139&subjectCd=A01&no=1
            // ================================
            String studentNo = req.getParameter("studentNo");
            String subjectCd = req.getParameter("subjectCd");
            String noStr = req.getParameter("no");

            int no = Integer.parseInt(noStr); // no = 回数

            // ================================
            // B. Lấy thông tin school từ session
            // ================================
            Teacher teacher = (Teacher) req.getSession().getAttribute("user");
            School school = teacher.getSchool();

            // ================================
            // C. Lấy dữ liệu Test để hiển thị màn hình xác nhận
            // ※ LƯU Ý: TestDao.get() có thứ tự tham số:
            // (studentNo, subjectCd, school, no)
            // ================================
            TestDao tDao = new TestDao();
            Test test = tDao.get(studentNo, subjectCd, school, no);

            // Nếu không tìm thấy dữ liệu → chuyển sang error.jsp
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
