package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // 1. セッションから教員・学校情報取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // 2. プルダウン用データ取得
        List<String> classNumList = new ClassNumDao().filter(school);
        List<Subject> subjectList = new SubjectDao().filter(school);

        // 3. 入学年度リスト生成
        int currentYear = LocalDate.now().getYear();
        List<Integer> entYearList = new ArrayList<>();
        for (int i = currentYear; i >= currentYear - 5; i--) entYearList.add(i);

        // 4. リクエストにセット
        req.setAttribute("classNumList", classNumList);
        req.setAttribute("subjectList", subjectList);
        req.setAttribute("entYearList", entYearList);

        // 5. 検索処理（f=sj: 科目別, f=st: 学生別）
        String searchType = req.getParameter("f");

        if ("sj".equals(searchType)) {
            // 科目別検索
            String f1 = req.getParameter("f1"); // 入学年度
            String f2 = req.getParameter("f2"); // クラス
            String f3 = req.getParameter("f3"); // 科目CD

            req.setAttribute("f1", f1);
            req.setAttribute("f2", f2);
            req.setAttribute("f3", f3);
            req.setAttribute("searchType", "sj");

            if (f1 == null || f1.isEmpty() || f2 == null || f2.isEmpty() || f3 == null || f3.isEmpty()) {
                req.setAttribute("errorSj", "入学年度とクラスと科目を選択してください");
            } else {
                int entYear = Integer.parseInt(f1);
                dao.TestDao testDao = new dao.TestDao();
                List<bean.Test> testList = testDao.filterBySubjectAndClass(school, entYear, f2, f3);
                if (testList.isEmpty()) {
                    req.setAttribute("errorSj", "学生情報が存在しませんでした");
                } else {
                    req.setAttribute("testList", testList);
                }
            }

        } else if ("st".equals(searchType)) {
            // 学生別検索
            String f4 = req.getParameter("f4"); // 学生番号
            req.setAttribute("searchType", "st");

            if (f4 == null || f4.trim().isEmpty()) {
                req.setAttribute("errorSt", "学生番号を入力してください");
            } else {
                dao.StudentDao studentDao = new dao.StudentDao();
                bean.Student student = studentDao.get(f4.trim());
                if (student == null) {
                    req.setAttribute("f4", f4);
                    req.setAttribute("errorSt", "学生情報が存在しませんでした");
                } else {
                    dao.TestDao testDao = new dao.TestDao();
                    List<bean.Test> testList = testDao.filterByStudent(school, f4.trim());
                    req.setAttribute("student", student);
                    req.setAttribute("f4", f4);
                    if (testList.isEmpty()) {
                        req.setAttribute("errorSt", "成績情報が存在しませんでした");
                    } else {
                        req.setAttribute("testList", testList);
                    }
                }
            }
        }

        // 6. JSPへフォワード
        req.getRequestDispatcher("test_list.jsp").forward(req, res);
    }
}
