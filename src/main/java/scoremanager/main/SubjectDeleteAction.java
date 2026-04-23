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
        // 1. ローカル変数の宣言 (Khai báo biến cục bộ)
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        SubjectDao sDao = new SubjectDao();

        // 2. リクエストパラメータ―の取得 (Lấy tham số từ Request)
        // 削除対象の科目コードを取得 (Lấy mã môn học cần xóa từ URL)
        String cd = req.getParameter("cd");

        // 3. DBからデータ取得 (Lấy dữ liệu từ DB)
        // 削除する科目の情報を取得 (Lấy thông tin môn học để hiển thị xác nhận)
        Subject subject = sDao.get(cd, teacher.getSchool());

        // 4. ビジネスロジック (Logic nghiệp vụ) - なし

        // 5. DBへデータ保存 (Lưu dữ liệu vào DB) - なし

        // 6. レスポンス値をセット (Thiết lập giá trị phản hồi)
        req.setAttribute("subject", subject);

        // 7. JSPへフォワード (Chuyển hướng đến JSP)
        // 削除確認画面へ進む (Chuyển đến trang xác nhận xóa)
        req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
    }
}