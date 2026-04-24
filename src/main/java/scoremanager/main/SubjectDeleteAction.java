package scoremanager.main;
// Package chứa các Action thuộc module scoremanager/main
// scoremanager/main モジュールに属する Action クラスをまとめるパッケージ

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

        // 1. ローカル変数の宣言
        // Khai báo biến cục bộ
        // セッション・教員情報・DAO を準備
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        SubjectDao sDao = new SubjectDao();

        // 2. リクエストパラメータ―の取得
        // Lấy tham số từ Request
        // 削除対象の科目コードを取得（URL の cd パラメータ）
        String cd = req.getParameter("cd");

        // 3. DBからデータ取得
        // Lấy dữ liệu từ DB
        // 削除対象の科目情報を取得（確認画面で表示するため）
        Subject subject = sDao.get(cd, teacher.getSchool());

        // 4. ビジネスロジック（なし）
        // Không có xử lý logic đặc biệt
        // 特別な業務ロジックなし

        // 5. DBへデータ保存（なし）
        // Không lưu gì vào DB ở bước này
        // DB 更新処理なし（削除はまだ行わない）

        // 6. レスポンス値をセット
        // Gửi dữ liệu sang JSP
        // JSP に科目情報を渡す
        req.setAttribute("subject", subject);

        // 7. JSPへフォワード
        // Chuyển hướng đến trang xác nhận xóa
        // 削除確認画面（subject_delete.jsp）へフォワード
        req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
    }
}
