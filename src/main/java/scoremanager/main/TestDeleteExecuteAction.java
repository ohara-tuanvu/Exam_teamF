package scoremanager.main;

import bean.School;
import bean.Teacher;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestDeleteExecuteAction extends Action {

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

        // 3. DB削除
        new TestDao().delete(studentNo, subjectCd, school, no);

        // 4. 一覧へリダイレクト
        res.sendRedirect(req.getContextPath() + "/scoremanager/main/TestList.action");
    }
}
