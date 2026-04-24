package scoremanager;
// Package chứa các Action thuộc module scoremanager (màn hình login chính)
// scoremanager モジュールのログイン画面を扱う Action クラス

import java.util.ArrayList;
import java.util.List;

import bean.Teacher;
import dao.TeacherDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;
// Action là lớp cha dùng để xử lý request theo mô hình MVC
// Action は MVC パターンでリクエスト処理を行う基底クラス

public class LoginExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ローカル変数の宣言 1
        // Khai báo biến cục bộ
        // ログイン処理に必要な変数を準備
        String url = "";
        String id = "";
        String password = "";
        TeacherDao teacherDao = new TeacherDao();
        Teacher teacher = null;

        // リクエストパラメータ―の取得 2
        // Lấy ID và password từ form login.jsp
        // ログインフォームから教員IDとパスワードを取得
        id = req.getParameter("id");          // 教員ID
        password = req.getParameter("password"); // パスワード

        // DBからデータ取得 3
        // Gọi DAO để kiểm tra đăng nhập
        // DAO の login メソッドで認証チェック
        teacher = teacherDao.login(id, password);

        // ビジネスロジック 4〜7
        // Phân nhánh theo kết quả đăng nhập
        // 認証結果に応じて処理を分岐
        if (teacher != null) { // 認証成功の場合

            // セッション情報を取得
            // Lấy session hiện tại (true = tạo mới nếu chưa có)
            // セッションを取得（true = なければ新規作成）
            HttpSession session = req.getSession(true);

            // 認証済みフラグを立てる
            // Đánh dấu giáo viên đã đăng nhập
            // 認証済みフラグを true に設定
            teacher.setAuthenticated(true);

            // セッションにログイン情報を保存
            // Lưu thông tin giáo viên vào session
            // 教員情報をセッションへ保存
            session.setAttribute("user", teacher);

            // リダイレクト
            // Chuyển hướng sang Menu.action (không dùng forward)
            // メニュー画面へリダイレクト（forward ではない）
            res.sendRedirect(req.getContextPath() + "/scoremanager/main/Menu.action");

        } else {
            // 認証失敗の場合
            // Nếu đăng nhập thất bại
            // 認証に失敗した場合の処理

            // エラーメッセージをセット
            // Tạo danh sách lỗi để hiển thị trên login.jsp
            // エラーメッセージをリストに追加して JSP へ渡す
            List<String> errors = new ArrayList<>();
            errors.add("IDまたはパスワードが確認できませんでした");
            req.setAttribute("errors", errors);

            // 入力された教員IDをセット
            // Giữ lại ID người dùng đã nhập
            // 入力された ID を保持して再表示
            req.setAttribute("id", id);

            // フォワード
            // Quay lại trang login.jsp
            // ログイン画面へフォワード
            url = "/scoremanager/main/login.jsp";
            req.getRequestDispatcher(url).forward(req, res);
        }
    }
}
