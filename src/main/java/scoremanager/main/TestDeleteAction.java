package scoremanager.main;

import bean.School;
import bean.Teacher;
import bean.Test;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // 1. セッションから教員・学校情報取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // 2. リクエストパラメータ取得
        String studentNo = req.getParameter("studentNo");
        String subjectCd = req.getParameter("subjectCd");
        int no = Integer.parseInt(req.getParameter("no"));

        // 3. DBから対象成績を取得
        Test test = new TestDao().get(studentNo, subjectCd, school, no);

        if (test == null) {
            req.getRequestDispatcher("/error.jsp").forward(req, res);
            return;
        }

        // 4. リクエストにセット（確認画面用）
        req.setAttribute("test", test);

        // 5. JSPへフォワード
        req.getRequestDispatcher("test_delete.jsp").forward(req, res);
    }
}
