package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // 1. ローカル変数の宣言 (Khai báo biến cục bộ)
        HttpSession session = req.getSession(); // セッションを取得 (Lấy session)
        Teacher teacher = (Teacher) session.getAttribute("user"); // ログインユーザーを取得 (Lấy giáo viên đăng nhập)
        SubjectDao sDao = new SubjectDao(); // 科目DAOを初期化 (Khởi tạo DAO môn học)

        // 2. リクエストパラメータ―の取得 (Lấy tham số từ Request)
        // JSPのリンク (?cd=${subject.cd}) から科目コードを取得
        // Lấy mã môn học từ tham số 'cd' trên URL
        String cd = req.getParameter("cd");

        // 3. DBからデータ取得 (Lấy dữ liệu từ DB)
        // 取得したコードと学校コードで科目を1件検索
        // Tìm kiếm 1 môn học dựa trên mã CD và mã trường của giáo viên
        Subject subject = sDao.get(cd, teacher.getSchool());

        // 4. ビジネスロジック (Logic nghiệp vụ) - なし

        // 5. DBへデータ保存 (Lưu dữ liệu vào DB) - なし

        // 6. レスポンス値をセット (Thiết lập giá trị phản hồi)
        // 検索結果をJSPへ渡すために属性をセット (Đưa đối tượng môn học vào request để hiển thị trên form)
        req.setAttribute("subject", subject);

        // 7. JSPへフォワード (Chuyển hướng đến JSP)
        // 変更入力画面へ進む (Chuyển đến trang nhập liệu chỉnh sửa)
        req.getRequestDispatcher("subject_update.jsp").forward(req, res);
    }
}