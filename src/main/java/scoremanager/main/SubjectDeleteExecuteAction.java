package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // 1. ローカル変数の宣言
        // Khai báo biến cục bộ
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        SubjectDao sDao = new SubjectDao();

        // 2. リクエストパラメータ―の取得
        // Lấy mã môn học cần xóa
        String cd = req.getParameter("cd");

        // 3. DB取得（なし）

        // 4. ビジネスロジック
        // Tạo đối tượng Subject để xác định mục tiêu cần xóa
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setSchool(teacher.getSchool());

        // 5. DBへデータ保存（削除処理）
        sDao.delete(subject);

        // 6. レスポンス値をセット
        // Gửi flag để JSP biết là đã xóa xong
        req.setAttribute("done", true);

        // 7. JSPへフォワード
        // Không redirect → forward lại trang xác nhận để hiển thị thông báo
        req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
    }
}
