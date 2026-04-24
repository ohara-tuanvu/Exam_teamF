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

public class SubjectUpdateExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // 1. ローカル変数の宣言
        // Khai báo biến cục bộ
        // セッション・教員情報・DAO を準備
        HttpSession session = req.getSession(); // セッションを取得 (Lấy session)
        Teacher teacher = (Teacher) session.getAttribute("user"); // ログインユーザーを取得 (Lấy user)
        SubjectDao sDao = new SubjectDao(); // DAOの初期化 (Khởi tạo DAO)

        // 2. リクエストパラメータ―の取得
        // Lấy tham số từ Request
        String cd = req.getParameter("cd");   // 科目コード (Mã môn học - không đổi)
        String name = req.getParameter("name"); // 新しい科目名 (Tên môn học mới)

        // 3. DBからデータ取得（なし）
        // Không cần lấy thêm dữ liệu từ DB
        // DB 取得処理なし

        // 4. ビジネスロジック
        // Tạo đối tượng Subject mới và thiết lập các giá trị để cập nhật
        // 更新対象の Subject オブジェクトを作成し、値をセット
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(teacher.getSchool());

        // 5. DBへデータ保存
        // Gọi hàm update của DAO để cập nhật cơ sở dữ liệu
        // DAO の update メソッドを呼び出して DB 更新
        sDao.update(subject);
        
        // メッセージをセットして JSP へフォワード
        // Gửi thông báo sang JSP
        req.setAttribute("message", "変更しました。");
        req.getRequestDispatcher("/scoremanager/main/subject_update.jsp").forward(req, res);

        // 6. レスポンス値をセット（なし）
        // Không cần set thêm giá trị nào

        // 7. JSPへフォワード（リダイレクト）
        // Sau khi cập nhật xong, chuyển hướng về trang danh sách môn học
        // 更新完了後、科目一覧画面へリダイレクト
        res.sendRedirect("SubjectList.action");
    }
}
