package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// ローカル変数の宣言 1
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
		String cd = req.getParameter("cd");
		String name = req.getParameter("name");
		SubjectDao sDao = new SubjectDao();
		Map<String, String> errors = new HashMap<>();

		// リクエストパラメータ―の取得 2 (取得済み)

		// DBからデータ取得 3 (なし)

		// ビジネスロジック 4
		if (cd == null || cd.isEmpty() || cd.length() != 3) {
			errors.put("cd", "科目コードは3文字で入力してください");
		}
		
		if (name == null || name.isEmpty()) {
			errors.put("name", "科目名を入力してください");
		}

		// DBへデータ保存 5
		if (errors.isEmpty()) {
			Subject subject = new Subject();
			subject.setCd(cd);
			subject.setName(name);
			subject.setSchool(teacher.getSchool());

			try {
				sDao.create(subject);
			} catch (Exception e) {
				errors.put("cd", "科目コードが重複しています");
			}
		}

		// レスポンス値をセット 6 & JSPへフォワード 7
		if (!errors.isEmpty()) {
			// Có lỗi: Quay lại trang nhập liệu
			req.setAttribute("errors", errors);
			req.setAttribute("cd", cd);
			req.setAttribute("name", name);
			req.getRequestDispatcher("subject_create.jsp").forward(req, res);
		} else {
			// Thành công: Chuyển đến trang hoàn tất
			req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
		}
	}
}