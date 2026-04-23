package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // 1. ローカル変数の宣言 (Khai báo biến cục bộ)
        HttpSession session = req.getSession(); // セッションを取得 (Lấy session)
        Teacher teacher = (Teacher) session.getAttribute("user"); // ログインユーザーを取得 (Lấy user)
        SubjectDao sDao = new SubjectDao(); // DAOの初期化 (Khởi tạo DAO)

        // 2. リクエストパラメータ―の取得 (Lấy tham số từ Request)
        String cd = req.getParameter("cd"); // 科目コード (Mã môn học - không đổi)
        String name = req.getParameter("name"); // 新しい科目名 (Tên môn học mới)

        // 3. DBからデータ取得 - なし (Không cần lấy thêm dữ liệu)

        // 4. ビジネスロジック (Logic nghiệp vụ)
        // 更新用のSubjectオブジェクトを作成し、値をセットする
        // Tạo đối tượng Subject mới và thiết lập các giá trị để cập nhật
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(teacher.getSchool());

        // 5. DBへデータ保存 (Lưu dữ liệu vào DB)
        // DAOのupdateメソッドを呼び出して、データベースを更新する
        // Gọi hàm update của DAO để cập nhật cơ sở dữ liệu
        sDao.update(subject);
        
        req.setAttribute("message", "変更しました。");
        req.getRequestDispatcher("/scoremanager/main/subject_update.jsp").forward(req, res);
        // 6. レスポンス値をセット - なし

        // 7. JSPへフォワード (Điều hướng)
        // 更新完了後、科目一覧画面へリダイレクトする
        // Sau khi cập nhật xong, chuyển hướng về trang danh sách môn học
        res.sendRedirect("SubjectList.action");
    }
}