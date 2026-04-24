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

public class SubjectUpdateAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // 1. ローカル変数の宣言
        // Khai báo biến cục bộ
        // セッション・教員情報・DAO を準備
        HttpSession session = req.getSession(); // セッションを取得 (Lấy session)
        Teacher teacher = (Teacher) session.getAttribute("user"); // ログインユーザーを取得 (Lấy giáo viên đăng nhập)
        SubjectDao sDao = new SubjectDao(); // 科目DAOを初期化 (Khởi tạo DAO môn học)

        // 2. リクエストパラメータ―の取得
        // Lấy tham số từ Request
        // JSP のリンク (?cd=${subject.cd}) から科目コードを取得
        String cd = req.getParameter("cd");

        // 3. DBからデータ取得
        // Lấy dữ liệu từ DB
        // 取得したコードと学校コードで科目を1件検索
        Subject subject = sDao.get(cd, teacher.getSchool());

        // 4. ビジネスロジック（なし）
        // Không có xử lý logic đặc biệt
        // 特別な業務ロジックなし

        // 5. DBへデータ保存（なし）
        // Không lưu gì vào DB ở bước này
        // DB 更新処理なし

        // 6. レスポンス値をセット
        // Gửi dữ liệu sang JSP để hiển thị form update
        // JSP で表示するために科目情報をセット
        req.setAttribute("subject", subject);

        // 7. JSPへフォワード
        // Chuyển đến trang nhập liệu chỉnh sửa
        // 変更入力画面（subject_update.jsp）へフォワード
        req.getRequestDispatcher("subject_update.jsp").forward(req, res);
    }
}
