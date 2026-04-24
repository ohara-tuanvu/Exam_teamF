package scoremanager;
// Package chứa các Action thuộc module scoremanager (màn hình login chính)
// scoremanager モジュールのログイン画面を扱う Action クラス

import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Teacher;
import dao.SchoolDao;
import dao.TeacherDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;
// Action là lớp cha dùng để xử lý request theo mô hình MVC
// Action は MVC パターンでリクエスト処理を行う基底クラス

public class LoginExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ローカル変数の宣言 1
        String url = "";
        String id = "";
        String password = "";
        TeacherDao teacherDao = new TeacherDao();
        Teacher teacher = null;

        // リクエストパラメータ―の取得 2
        id = req.getParameter("id");          
        password = req.getParameter("password");

        // DBからデータ取得 3
        teacher = teacherDao.login(id, password);

        // ビジネスロジック 4〜7
        if (teacher != null) { // 認証成功の場合

            HttpSession session = req.getSession(true);

            teacher.setAuthenticated(true);
            session.setAttribute("user", teacher);

            // ★★★★★ 追加：学校情報をセッションに保存 ★★★★★
            SchoolDao sDao = new SchoolDao();
            School school = sDao.get(teacher.getSchoolCd());
            session.setAttribute("school", school);
            // ★★★★★ ここまで追加 ★★★★★

            // リダイレクト
            res.sendRedirect(req.getContextPath() + "/scoremanager/main/Menu.action");

        } else {
            // 認証失敗の場合
            List<String> errors = new ArrayList<>();
            errors.add("IDまたはパスワードが確認できませんでした");
            req.setAttribute("errors", errors);

            req.setAttribute("id", id);

            url = "/scoremanager/main/login.jsp";
            req.getRequestDispatcher(url).forward(req, res);
        }
    }
}
