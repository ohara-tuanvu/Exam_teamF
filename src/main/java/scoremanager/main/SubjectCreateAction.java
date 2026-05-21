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

public class SubjectCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String cd = req.getParameter("cd");
        String name = req.getParameter("name");
        String schoolCd = req.getParameter("school_cd");

        Map<String, String> errors = new HashMap<>();

        // 初回アクセス
        if (cd == null) {

            if (teacher.getRole() == 2) {
                SchoolDao schoolDao = new SchoolDao();
                req.setAttribute("school_list", schoolDao.findAll());
            }

            req.getRequestDispatcher("subject_create.jsp").forward(req, res);
            return;
        }

        // 入力チェック
        if (cd.isEmpty()) {
            errors.put("cd", "科目コードを入力してください");
        }
        if (name.isEmpty()) {
            errors.put("name", "科目名を入力してください");
        }

        if (teacher.getRole() == 2 && (schoolCd == null || schoolCd.isEmpty())) {
            errors.put("school", "学校を選択してください");
        }

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

        // School の取得
        School school;

        if (teacher.getRole() == 2) {
            SchoolDao schoolDao = new SchoolDao();
            school = schoolDao.get(schoolCd);
        } else {
            school = teacher.getSchool();
        }

        // 重複チェック
        SubjectDao sDao = new SubjectDao();

        if (sDao.get(cd, school) != null) {
            errors.put("cd", "科目コードが重複しています");
        }

        if (sDao.getByName(name, school) != null) {
            errors.put("name", "科目名が既に存在しています");
        }

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

        // 完了 → リダイレクト
        res.sendRedirect("SubjectCreate.action");
    }
}
