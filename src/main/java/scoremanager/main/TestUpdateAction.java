package scoremanager.main;

import bean.School;
import bean.Teacher;
import bean.Test;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestUpdateAction extends Action {

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

        TestDao testDao = new TestDao();
        Test test;

        // ================================
        // ⭐ 権限分岐（SuperAdmin / Admin）
        // ================================
        if (teacher.getRole() == 2) {
            // SUPERADMIN → 学校制限なし
            test = testDao.getForSuperAdmin(studentNo, subjectCd, no);
        } else {
            // Admin / Teacher → 自分の学校のみ
            test = testDao.get(studentNo, subjectCd, school, no);
        }

        // 3. 該当データがない場合
        if (test == null) {
            req.getRequestDispatcher("/error.jsp").forward(req, res);
            return;
        }

        // 4. リクエストにセット
        req.setAttribute("test", test);

        // 5. JSPへフォワード
        req.getRequestDispatcher("test_update.jsp").forward(req, res);
    }
}
