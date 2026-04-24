package scoremanager.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestRegistAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            // ============================
            // 1. 学校情報（ログイン中）
            // ============================
            School school = (School) req.getSession().getAttribute("school");

            // ============================
            // 2. 入学年度リスト（10年前〜10年後）
            // ============================
            List<Integer> entYearList = new ArrayList<>();
            int year = java.time.LocalDate.now().getYear();
            for (int i = year - 10; i <= year + 10; i++) {
                entYearList.add(i);
            }
            req.setAttribute("entYearList", entYearList);

            // ============================
            // 3. クラス一覧
            // ============================
            ClassNumDao cDao = new ClassNumDao();
            req.setAttribute("classList", cDao.filter(school));

            // ============================
            // 4. 科目一覧
            // ============================
            SubjectDao subDao = new SubjectDao();
            req.setAttribute("subjectList", subDao.filter(school));

            // ============================
            // 5. 回数一覧（1〜10）
            // ============================
            List<Integer> noList = new ArrayList<>();
            for (int i = 1; i <= 10; i++) noList.add(i);
            req.setAttribute("noList", noList);

            // ============================
            // 6. 検索条件取得
            // ============================
            String entYearStr = req.getParameter("ent_year");
            String classNum = req.getParameter("class_num");
            String subjectCd = req.getParameter("subject_cd");
            String noStr = req.getParameter("no");

            List<Test> testList = null;

            // ============================
            // 7. 検索実行
            // ============================
            if (entYearStr != null && !entYearStr.isEmpty()
                    && classNum != null && !classNum.isEmpty()
                    && subjectCd != null && !subjectCd.isEmpty()
                    && noStr != null && !noStr.isEmpty()) {

                int entYear = Integer.parseInt(entYearStr);
                int no = Integer.parseInt(noStr);

                // 科目取得
                Subject subject = subDao.get(subjectCd, school);

                // 成績一覧取得（あなたのDAOに合わせる）
                TestDao tDao = new TestDao();
                testList = tDao.filter(entYear, classNum, subject, no, school);

                // 科目名をJSPに渡す
                req.setAttribute("subject_name", subject.getName());
            }

            // ============================
            // 8. JSPへ渡す
            // ============================
            req.setAttribute("testList", testList);

            // ============================
            // 9. 画面へ遷移
            // ============================
            req.getRequestDispatcher("/scoremanager/main/test_regist.jsp")
               .forward(req, res);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
