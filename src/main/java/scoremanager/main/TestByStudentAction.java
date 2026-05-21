package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Student;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestByStudentAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // 1. セッションから教員情報取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        StudentDao studentDao = new StudentDao();
        TestDao testDao = new TestDao();

        List<Student> studentList;

        // 2. 学生一覧取得（権限による分岐）
        if (teacher.getRole() == 2) {
            // SUPERADMIN → 全学校の学生一覧
            studentList = studentDao.findAll();
        } else {
            // Admin / Teacher → 自分の学校のみ
            studentList = studentDao.filter(school, true);
        }

        req.setAttribute("studentList", studentList);

        // 3. 学生番号が指定されていれば成績一覧を取得
        String studentNo = req.getParameter("studentNo");

        if (studentNo != null && !studentNo.trim().isEmpty()) {

            List<Test> testList;

            if (teacher.getRole() == 2) {
                // SUPERADMIN → 全学校の成績
                testList = testDao.filterByStudent(studentNo.trim());
            } else {
                // Admin / Teacher → 自分の学校のみ
                testList = testDao.filterByStudent(school, studentNo.trim());
            }

            req.setAttribute("testList", testList);
            req.setAttribute("selectedStudentNo", studentNo);
        }

        // 4. JSPへフォワード
        req.getRequestDispatcher("test_by_student.jsp").forward(req, res);
    }
}
