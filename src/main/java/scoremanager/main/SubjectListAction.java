package scoremanager.main;

import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// ローカル変数の宣言 1
		HttpSession session = req.getSession(); // セッション
		Teacher teacher = (Teacher) session.getAttribute("user"); // ログインユーザー
		SubjectDao sDao = new SubjectDao(); // 科目DAO

		// リクエストパラメータ―の取得 2
		// なし

		// DBからデータ取得 3
		// ログインユーザーの学校に紐づく科目一覧を取得
		List<Subject> subjects = sDao.filter(teacher.getSchool());

		// ビジネスロジック 4
		// なし

		// DBへデータ保存 5
		// なし

		// レスポンス値をセット 6
		req.setAttribute("subjects", subjects);

		// JSPへフォワード 7
		req.getRequestDispatcher("subject_list.jsp").forward(req, res);
	}
}