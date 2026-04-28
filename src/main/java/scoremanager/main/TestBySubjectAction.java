package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestBySubjectAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // 1. セッションから教員・学校情報取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // 2. 科目一覧取得（プルダウン用）
        List<Subject> subjectList = new SubjectDao().filter(school);
        req.setAttribute("subjectList", subjectList);

        // 3. 科目コードが指定されていれば成績一覧を取得
        String subjectCd = req.getParameter("subjectCd");
        if (subjectCd != null && !subjectCd.trim().isEmpty()) {
            List<Test> testList = new TestDao().filterBySubject(school, subjectCd.trim());
            req.setAttribute("testList", testList);
            req.setAttribute("selectedSubjectCd", subjectCd);
        }

        // 4. JSPへフォワード
        req.getRequestDispatcher("test_by_subject.jsp").forward(req, res);
    }
}
