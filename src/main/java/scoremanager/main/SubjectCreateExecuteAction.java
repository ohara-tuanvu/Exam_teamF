package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SchoolDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String cd = req.getParameter("cd");
        String name = req.getParameter("name");
        String schoolCd = req.getParameter("school_cd"); // SUPERADMIN 用

        SubjectDao sDao = new SubjectDao();
        Map<String, String> errors = new HashMap<>();

        // 入力チェック
        if (cd == null || cd.isEmpty() || cd.length() != 3) {
            errors.put("cd", "科目コードは3文字で入力してください");
        }

        if (name == null || name.isEmpty()) {
            errors.put("name", "科目名を入力してください");
        }

        // SUPERADMIN → school 必須
        School school = null;

        if (teacher.getRole() == 2) {
            if (schoolCd == null || schoolCd.isEmpty()) {
                errors.put("school", "学校を選択してください");
            } else {
                SchoolDao schoolDao = new SchoolDao();
                school = schoolDao.get(schoolCd);

                if (school == null) {
                    errors.put("school", "学校が存在しません");
                }
            }
        } else {
            school = teacher.getSchool();
        }

        // ★ ここで school が null のまま進ませない
        if (!errors.isEmpty()) {

            if (teacher.getRole() == 2) {
                SchoolDao schoolDao = new SchoolDao();
                req.setAttribute("school_list", schoolDao.findAll());
                req.setAttribute("school_cd", schoolCd);
            }

            req.setAttribute("errors", errors);
            req.setAttribute("cd", cd);
            req.setAttribute("name", name);

            req.getRequestDispatcher("subject_create.jsp").forward(req, res);
            return;
        }

        // 重複チェック（CD）
        if (sDao.get(cd, school) != null) {
            errors.put("cd", "科目コードが重複しています");
        }

        // 重複チェック（NAME）
        if (sDao.getByName(name, school) != null) {
            errors.put("name", "科目名が重複しています");
        }

        // エラーあり → 入力画面へ戻す
        if (!errors.isEmpty()) {

            if (teacher.getRole() == 2) {
                SchoolDao schoolDao = new SchoolDao();
                req.setAttribute("school_list", schoolDao.findAll());
                req.setAttribute("school_cd", schoolCd);
            }

            req.setAttribute("errors", errors);
            req.setAttribute("cd", cd);
            req.setAttribute("name", name);

            req.getRequestDispatcher("subject_create.jsp").forward(req, res);
            return;
        }

        // 登録処理
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(school);

        sDao.create(subject);

        // 完了画面へ
        req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
    }
}
