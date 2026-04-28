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
        // 1. セッションから教員・学校情報取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // 2. リクエストパラメータ取得
        String entYearStr = req.getParameter("f1");
        String classNum   = req.getParameter("f2");
        String subjectCd  = req.getParameter("f3");
        String noStr      = req.getParameter("f4");

        // 3. 検索条件で対象学生リストを取得
        int entYear = Integer.parseInt(entYearStr);
        int no = Integer.parseInt(noStr);

        StudentDao studentDao = new StudentDao();
        List<Student> studentList = studentDao.filter(school, entYear, classNum, true);

        TestDao testDao = new TestDao();
        SubjectDao subjectDao = new SubjectDao();
        Subject subject = subjectDao.get(subjectCd, school);

        // 4. 各学生の点数を保存
        List<String> errors = new ArrayList<>();
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

            // 既存チェック → あればupdate、なければinsert
            Test existing = testDao.get(student.getNo(), subjectCd, school, no);
            Test test = new Test();
            test.setStudent(student);
            test.setSubject(subject);
            test.setSchool(school);
            test.setNo(no);
            test.setPoint(point);
            test.setClassNum(classNum);

            if (existing != null) {
                testDao.update(test);
            } else {
                testDao.insert(test);
            }
        }

        if (!errors.isEmpty()) {
            // エラーがある場合は登録画面へ戻る
            int currentYear = LocalDate.now().getYear();
            List<Integer> entYearList = new ArrayList<>();
            for (int i = currentYear; i >= currentYear - 5; i--) entYearList.add(i);
            List<Integer> noList = new ArrayList<>();
            for (int i = 1; i <= 5; i++) noList.add(i);

            req.setAttribute("errors", errors);
            req.setAttribute("classNumList", new ClassNumDao().filter(school));
            req.setAttribute("subjectList", subjectDao.filter(school));
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

        // 5. 完了画面へフォワード
        req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
    }
}
