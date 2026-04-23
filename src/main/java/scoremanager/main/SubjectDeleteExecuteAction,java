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
        // 1. ローカル変数の宣言 (Khai báo biến cục bộ)
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        SubjectDao sDao = new SubjectDao();

        // 2. リクエストパラメータ―の取得 (Lấy tham số từ Request)
        // 削除する科目のコードを取得 (Lấy mã môn học cần xóa)
        String cd = req.getParameter("cd");

        // 3. DBからデータ取得 - なし

        // 4. ビジネスロジック (Logic nghiệp vụ)
        // 削除対象を指定するためのSubjectオブジェクトを作成
        // Tạo đối tượng Subject để xác định mục tiêu cần xóa
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setSchool(teacher.getSchool());

        // 5. DBへデータ保存 (Xử lý xóa trong DB)
        // DAOのdeleteメソッドを呼び出して削除を実行する
        // Gọi phương thức delete của DAO để thực hiện xóa
        sDao.delete(subject);

        // 6. レスポンス値をセット - なし

        // 7. JSPへフォワード (Điều hướng)
        // 削除完了後、一覧画面へ戻る (Quay lại trang danh sách sau khi xóa xong)
        res.sendRedirect("SubjectList.action");
    }
}