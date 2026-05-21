package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // 1. セッション・教員情報・DAO
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        SubjectDao sDao = new SubjectDao();

        // 2. パラメータ取得
        String cd = req.getParameter("cd");

        Subject subject = null;

        // ⭐ SUPERADMIN → 学校制限なしで取得
        if (teacher.getRole() == 2) {
            subject = sDao.get(cd);   // ← ★ get(cd) を使う（学校なし）
        } 
        // ⭐ ADMIN / TEACHER → 自分の学校のみ
        else {
            subject = sDao.get(cd, teacher.getSchool());
        }

        // 取得できなかった場合（不正アクセス or データなし）
        if (subject == null) {
            req.setAttribute("error", "科目情報が見つかりません。");
            req.getRequestDispatcher("subject_list.jsp").forward(req, res);
            return;
        }

        // 6. JSPへセット
        req.setAttribute("subject", subject);

        // 7. 削除確認画面へ
        req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
    }
}
