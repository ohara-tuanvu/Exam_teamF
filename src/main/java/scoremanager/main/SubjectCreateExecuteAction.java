package scoremanager.main;
// Package chứa các Action thuộc module scoremanager/main
// scoremanager/main モジュールに属する Action クラスをまとめるパッケージ

import java.util.HashMap;
import java.util.Map;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ローカル変数の宣言 1
        // Khai báo biến cục bộ
        // ローカル変数（セッション・入力値・DAO・エラーMap）を準備
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        String cd = req.getParameter("cd");
        String name = req.getParameter("name");
        SubjectDao sDao = new SubjectDao();
        Map<String, String> errors = new HashMap<>();

        // リクエストパラメータ―の取得 2 (取得済み)
        // Đã lấy xong tham số từ request
        // リクエストパラメータはすでに取得済み

        // DBからデータ取得 3 (なし)
        // Không cần lấy dữ liệu từ DB ở bước này
        // DB 取得処理なし

        // ビジネスロジック 4
        // Xử lý kiểm tra dữ liệu nhập
        // 入力チェック（バリデーション）
        if (cd == null || cd.isEmpty() || cd.length() != 3) {
            // Kiểm tra mã môn học phải đúng 3 ký tự
            // 科目コードは3文字必須
            errors.put("cd", "科目コードは3文字で入力してください");
        }
        
        if (name == null || name.isEmpty()) {
            // Kiểm tra tên môn học không được để trống
            // 科目名の未入力チェック
            errors.put("name", "科目名を入力してください");
        }

        // DBへデータ保存 5
        // Nếu không có lỗi → tiến hành lưu vào DB
        // エラーがなければ DB 登録処理へ
        if (errors.isEmpty()) {
            Subject subject = new Subject();
            subject.setCd(cd);
            subject.setName(name);
            subject.setSchool(teacher.getSchool());
            // 教員が所属する学校コードをセット

            try {
                sDao.create(subject);
                // Tạo mới môn học trong DB
                // DB に科目を新規登録
            } catch (Exception e) {
                // Lỗi trùng mã môn học
                // 科目コード重複エラー
                errors.put("cd", "科目コードが重複しています");
            }
        }

        // レスポンス値をセット 6 & JSPへフォワード 7
        // Xử lý điều hướng sau khi kiểm tra lỗi
        // エラーの有無で画面遷移を分岐
        if (!errors.isEmpty()) {
            // Có lỗi: Quay lại trang nhập liệu
            // エラーあり → 入力画面へ戻す
            req.setAttribute("errors", errors);
            req.setAttribute("cd", cd);
            req.setAttribute("name", name);
            req.getRequestDispatcher("subject_create.jsp").forward(req, res);
        } else {
            // Thành công: Chuyển đến trang hoàn tất
            // 登録成功 → 完了画面へフォワード
            req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
        }
    }
}
