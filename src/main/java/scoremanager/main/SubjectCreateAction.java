package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectCreateAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String cd = req.getParameter("cd");
        String name = req.getParameter("name");
        Map<String, String> errors = new HashMap<>();

        // Hiển thị trang nhập liệu lần đầu
        if (cd == null) {
            req.getRequestDispatcher("subject_create.jsp").forward(req, res);
            return;
        }

        // Kiểm tra dữ liệu trống
        if (cd.isEmpty()) {
            errors.put("cd", "科目コードを入力してください");
        }
        if (name.isEmpty()) {
            errors.put("name", "科目名を入力してください");
        }

        if (errors.isEmpty()) {
            Subject subject = new Subject();
            subject.setCd(cd);
            subject.setName(name);
            subject.setSchool(teacher.getSchool());

            SubjectDao sDao = new SubjectDao();
            try {
                sDao.create(subject);
                res.sendRedirect("SubjectCreate.action");
            } catch (Exception e) {
                errors.put("cd", "科目コードが重複しています");
                req.setAttribute("errors", errors);
                req.getRequestDispatcher("subject_create.jsp").forward(req, res);
            }
        } else {
            req.setAttribute("errors", errors);
            req.setAttribute("cd", cd);
            req.setAttribute("name", name);
            req.getRequestDispatcher("subject_create.jsp").forward(req, res);
        }
    }
}