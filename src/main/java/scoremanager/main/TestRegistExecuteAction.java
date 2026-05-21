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

public class TestRegistExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        String entYearStr = req.getParameter("f1");
        String classNum   = req.getParameter("f2");
        String subjectCd  = req.getParameter("f3");
        String noStr      = req.getParameter("f4");

        int entYear = Integer.parseInt(entYearStr);
        int no = Integer.parseInt(noStr);

        StudentDao studentDao = new StudentDao();
        SubjectDao subjectDao = new SubjectDao();
        TestDao testDao = new TestDao();

        List<Student> studentList;

        // ================================
        // ⭐ 学生一覧取得（権限分岐）
        // ================================
        if (teacher.getRole() == 2) {
            // SUPERADMIN → 全学校から取得
            studentList = studentDao.findAll();
        } else {
            // Admin / Teacher → 自分の学校のみ
            studentList = studentDao.filter(school, entYear, classNum, true);
        }

        // 科目取得（SuperAdmin は学校制限なし）
        Subject subject;
        if (teacher.getRole() == 2) {
            subject = subjectDao.get(subjectCd); 
        } else {
            subject = subjectDao.get(subjectCd, school);
        }

        List<String> errors = new ArrayList<>();

        // ================================
        // ⭐ 点数登録処理（権限分岐）
        // ================================
        for (Student student : studentList) {

            String pointStr = req.getParameter("point_" + student.getNo());
            if (pointStr == null || pointStr.trim().isEmpty()) continue;

            int point;
            try {
                point = Integer.parseInt(pointStr.trim());
                if (point < 0 || point > 100) {
                    errors.add(student.getName() + "の点数は0〜100の範囲で入力してください");
                    continue;
                }
            } catch (NumberFormatException e) {
                errors.add(student.getName() + "の点数は数字で入力してください");
                continue;
            }

            Test existing;

            if (teacher.getRole() == 2) {
                // SUPERADMIN → 学校制限なし
                existing = testDao.getForSuperAdmin(student.getNo(), subjectCd, no);
            } else {
                // Admin / Teacher
                existing = testDao.get(student.getNo(), subjectCd, school, no);
            }

            Test test = new Test();
            test.setStudent(student);
            test.setSubject(subject);
            test.setNo(no);
            test.setPoint(point);
            test.setClassNum(classNum);

            // SUPERADMIN は学校を student から取得
            if (teacher.getRole() == 2) {
                test.setSchool(student.getSchool());
            } else {
                test.setSchool(school);
            }

            if (existing != null) {
                testDao.update(test);
            } else {
                testDao.insert(test);
            }
        }

        // ================================
        // ⭐ エラー時は戻る
        // ================================
        if (!errors.isEmpty()) {

            int currentYear = LocalDate.now().getYear();
            List<Integer> entYearList = new ArrayList<>();
            for (int i = currentYear; i >= currentYear - 5; i--) entYearList.add(i);

            List<Integer> noList = new ArrayList<>();
            for (int i = 1; i <= 5; i++) noList.add(i);

            req.setAttribute("errors", errors);

            if (teacher.getRole() == 2) {
                req.setAttribute("classNumList", new ClassNumDao().findAllClassNum());
                req.setAttribute("subjectList", subjectDao.findAll());
            } else {
                req.setAttribute("classNumList", new ClassNumDao().filter(school));
                req.setAttribute("subjectList", subjectDao.filter(school));
            }

            req.setAttribute("entYearList", entYearList);
            req.setAttribute("noList", noList);
            req.setAttribute("studentList", studentList);

            req.setAttribute("f1", entYearStr);
            req.setAttribute("f2", classNum);
            req.setAttribute("f3", subjectCd);
            req.setAttribute("f4", noStr);

            req.getRequestDispatcher("test_regist.jsp").forward(req, res);
            return;
        }

        // ================================
        // ⭐ 完了画面へ
        // ================================
        req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
    }
}
