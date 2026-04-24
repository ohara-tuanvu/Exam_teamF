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

public class SubjectCreateAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        // Lấy session hiện tại
        // 現在のセッションを取得

        Teacher teacher = (Teacher) session.getAttribute("user");
        // Lấy giáo viên đang đăng nhập
        // セッションからログイン中の教員情報を取得

        String cd = req.getParameter("cd");
        String name = req.getParameter("name");
        // Lấy dữ liệu người dùng nhập từ form
        // 入力フォームから科目コード・科目名を取得

        Map<String, String> errors = new HashMap<>();
        // Map chứa lỗi để hiển thị lại trên form
        // 入力エラーを保持するための Map

        // Hiển thị trang nhập liệu lần đầu
        // 初回アクセス（cd が null の場合）は入力画面を表示
        if (cd == null) {
            req.getRequestDispatcher("subject_create.jsp").forward(req, res);
            return;
        }

        // Kiểm tra dữ liệu trống
        // 未入力チェック
        if (cd.isEmpty()) {
            errors.put("cd", "科目コードを入力してください");
        }
        if (name.isEmpty()) {
            errors.put("name", "科目名を入力してください");
        }

        // Nếu không có lỗi → tiến hành tạo Subject
        // エラーがなければ科目登録処理へ
        if (errors.isEmpty()) {

            Subject subject = new Subject();
            subject.setCd(cd);
            subject.setName(name);
            subject.setSchool(teacher.getSchool());
            // 教員が所属する学校コードをセット

            SubjectDao sDao = new SubjectDao();
            try {
                sDao.create(subject);
                // Tạo mới môn học trong DB
                // DB に科目を新規登録

                res.sendRedirect("SubjectCreate.action");
                // Redirect để reset form
                // リダイレクトして画面をリセット

            } catch (Exception e) {
                // Lỗi trùng mã môn học
                // 科目コード重複エラー
                errors.put("cd", "科目コードが重複しています");
                req.setAttribute("errors", errors);
                req.getRequestDispatcher("subject_create.jsp").forward(req, res);
            }

        } else {
            // Có lỗi → giữ lại dữ liệu và quay lại form
            // エラーがある場合 → 入力値を保持して再表示
            req.setAttribute("errors", errors);
            req.setAttribute("cd", cd);
            req.setAttribute("name", name);
            req.getRequestDispatcher("subject_create.jsp").forward(req, res);
        }
    }
}
