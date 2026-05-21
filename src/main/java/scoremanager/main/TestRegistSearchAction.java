package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistSearchAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        String f1 = req.getParameter("f1"); // 入学年度
        String f2 = req.getParameter("f2"); // クラス
        String f3 = req.getParameter("f3"); // 科目CD
        String f4 = req.getParameter("f4"); // 回数

        ClassNumDao classDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();
        StudentDao studentDao = new StudentDao();

        List<String> classNumList;
        List<Subject> subjectList;

        // ================================
        // ⭐ プルダウンデータ（権限分岐）
        // ================================
        if (teacher.getRole() == 2) {
            // SUPERADMIN → 全学校
            classNumList = classDao.findAllClassNum();
            subjectList = subjectDao.findAll();
        } else {
            // Admin / Teacher → 自分の学校のみ
            classNumList = classDao.filter(school);
            subjectList = subjectDao.filter(school);
        }

        // 入学年度リスト
        int currentYear = LocalDate.now().getYear();
        List<Integer> entYearList = new ArrayList<>();
        for (int i = currentYear; i >= currentYear - 5; i--) entYearList.add(i);

        // 回数リスト
        List<Integer> noList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) noList.add(i);

        req.setAttribute("classNumList", classNumList);
        req.setAttribute("subjectList", subjectList);
        req.setAttribute("entYearList", entYearList);
        req.setAttribute("noList", noList);

        req.setAttribute("f1", f1);
        req.setAttribute("f2", f2);
        req.setAttribute("f3", f3);
        req.setAttribute("f4", f4);

        // ================================
        // ⭐ 学生一覧取得（権限分岐）
        // ================================
        if (f1 != null && !f1.isEmpty() && f2 != null && !f2.isEmpty()) {

            List<Student> studentList;

            if (teacher.getRole() == 2) {
                // SUPERADMIN → 全学校から取得
                studentList = studentDao.findAll();
            } else {
                // Admin / Teacher → 自分の学校のみ
                int entYear = Integer.parseInt(f1);
                studentList = studentDao.filter(school, entYear, f2, true);
            }

            req.setAttribute("studentList", studentList);
        }

        req.getRequestDispatcher("test_regist.jsp").forward(req, res);
    }
}
