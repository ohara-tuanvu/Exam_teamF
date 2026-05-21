package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // 1. セッションから教員・学校情報取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // 2. リクエストパラメータ取得
        String studentNo = req.getParameter("studentNo");
        String subjectCd = req.getParameter("subjectCd");
        String noStr     = req.getParameter("no");
        String pointStr  = req.getParameter("point");

        List<String> errors = new ArrayList<>();
        int no = 0, point = 0;

        try { 
            no = Integer.parseInt(noStr); 
        } catch (NumberFormatException e) { 
            errors.add("回数が不正です"); 
        }

        try {
            point = Integer.parseInt(pointStr);
            if (point < 0 || point > 100) {
                errors.add("点数は0〜100で入力してください");
            }
        } catch (NumberFormatException e) {
            errors.add("点数は数字で入力してください");
        }

        TestDao testDao = new TestDao();
        Test test;

        // ================================
        // ⭐ 権限分岐（SuperAdmin / Admin）
        // ================================
        if (teacher.getRole() == 2) {
            // SUPERADMIN → 学校制限なし
            test = testDao.getForSuperAdmin(studentNo, subjectCd, no);
        } else {
            // Admin / Teacher
            test = testDao.get(studentNo, subjectCd, school, no);
        }

        // 4. エラーがある場合は編集画面へ戻る
        if (!errors.isEmpty()) {
            req.setAttribute("test", test);
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("test_update.jsp").forward(req, res);
            return;
        }

        // 5. DB更新
        if (test != null) {
            test.setPoint(point);

            // SUPERADMIN は学校を学生から取得
            if (teacher.getRole() == 2) {
                Student student = new StudentDao().get(studentNo);
                test.setSchool(student.getSchool());
            }

            testDao.update(test);
        }

        // 6. 一覧へリダイレクト
        res.sendRedirect(req.getContextPath() + "/scoremanager/main/TestList.action");
    }
}
