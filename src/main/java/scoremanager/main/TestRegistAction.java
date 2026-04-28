package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // プルダウン用データ
        List<String> classNumList = new ClassNumDao().filter(school);
        List<Subject> subjectList = new SubjectDao().filter(school);

        int currentYear = LocalDate.now().getYear();
        List<Integer> entYearList = new ArrayList<>();
        for (int i = currentYear; i >= currentYear - 5; i--) entYearList.add(i);
        List<Integer> noList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) noList.add(i);

        req.setAttribute("classNumList", classNumList);
        req.setAttribute("subjectList", subjectList);
        req.setAttribute("entYearList", entYearList);
        req.setAttribute("noList", noList);

        // 検索条件が指定されている場合は学生一覧を取得
        String f1 = req.getParameter("f1");
        String f2 = req.getParameter("f2");
        String f3 = req.getParameter("f3");
        String f4 = req.getParameter("f4");

        req.setAttribute("f1", f1);
        req.setAttribute("f2", f2);
        req.setAttribute("f3", f3);
        req.setAttribute("f4", f4);

        if (f1 != null && !f1.isEmpty() && f2 != null && !f2.isEmpty()
                && f3 != null && !f3.isEmpty() && f4 != null && !f4.isEmpty()) {
            int entYear = Integer.parseInt(f1);
            int no = Integer.parseInt(f4);
            List<Student> studentList = new StudentDao().filter(school, entYear, f2, true);
            if (studentList.isEmpty()) {
                req.setAttribute("searchError", "該当する学生情報が存在しませんでした");
            } else {
                req.setAttribute("studentList", studentList);
                // 既存の点数をMapにセット（学生番号 → 点数）
                TestDao testDao = new TestDao();
                java.util.Map<String, Integer> pointMap = new java.util.HashMap<>();
                for (Student s : studentList) {
                    Test existing = testDao.get(s.getNo(), f3, school, no);
                    if (existing != null) {
                        pointMap.put(s.getNo(), existing.getPoint());
                    }
                }
                req.setAttribute("pointMap", pointMap);
            }
        }

        req.getRequestDispatcher("test_regist.jsp").forward(req, res);
    }
}
