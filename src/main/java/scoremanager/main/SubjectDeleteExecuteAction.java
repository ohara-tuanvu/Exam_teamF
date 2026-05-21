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
        // Không cần lấy từ DB ở bước này

        // 4. ビジネスロジック
        // SUPERADMIN → xóa không cần SCHOOL_CD
        // SUPERADMIN → 学校制限なしで削除
        if (teacher.getRole() == 2) {
            sDao.delete(cd);
        }
        // ADMIN / TEACHER → chỉ xóa môn thuộc trường của họ
        // ADMIN / TEACHER → 自分の学校の科目のみ削除
        else {
            Subject subject = new Subject();
            subject.setCd(cd);
            subject.setSchool(teacher.getSchool());
            sDao.delete(subject);
        }

        // 5. レスポンス値をセット
        // Gửi flag để JSP biết là đã xóa xong
        // JSP に削除完了フラグを渡す
        req.setAttribute("done", true);

        // 6. JSPへフォワード
        // Không redirect → forward lại trang xác nhận để hiển thị thông báo
        // 削除確認画面にフォワードして完了メッセージを表示
        req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
    }
}
