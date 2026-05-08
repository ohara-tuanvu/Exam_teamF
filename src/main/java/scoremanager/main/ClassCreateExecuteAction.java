package scoremanager.main;
// scoremanager/main モジュールに属する Action クラスをまとめるパッケージ
// Package chứa các Action thuộc module scoremanager/main

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;
// Action は MVC パターンでリクエスト処理を行う基底クラス
// Action là lớp cha dùng để xử lý request theo mô hình MVC

public class ClassCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            // セッションから教員を取得（1）
            // セッションに保存されているログイン中の教員情報を取得
            // Lấy giáo viên đang đăng nhập từ session
            Teacher teacher = (Teacher) req.getSession().getAttribute("user");

            // 学校情報を取得（2）
            // 教員が所属する学校情報を取得
            // Lấy trường của giáo viên
            School school = teacher.getSchool();

            // リクエストパラメータ取得（3）
            // 入力フォームから送信された値を取得
            // Lấy dữ liệu người dùng nhập từ form
            String classNumStr = req.getParameter("classNum");

            // エラーリスト（4）
            // 入力チェック用のエラーメッセージリスト
            // Danh sách lỗi để hiển thị lại trên form
            List<String> errors = new ArrayList<>();

            // クラス番号チェック（5）
            // クラス番号の未入力チェック
            // Kiểm tra class_num
            if (classNumStr == null || classNumStr.isEmpty()) {
                errors.add("クラス番号を入力してください。");
            }

            // エラーがある場合（6）
            // エラーが存在する場合は入力画面へ戻す
            // Nếu có lỗi → quay lại form nhập
            if (errors.size() > 0) {

                // 入力値を保持
                // 入力値をリクエスト属性に保持
                // Giữ lại giá trị người dùng đã nhập
                req.setAttribute("classNum", classNumStr);

                // エラーメッセージをセット
                // エラー内容を JSP に渡す
                // Gửi danh sách lỗi sang JSP
                req.setAttribute("errors", errors);

                // 入力画面へ戻す
                req.getRequestDispatcher("/scoremanager/main/class_create.jsp").forward(req, res);
                return;
            }

            // ClassNum インスタンス作成（7）
            // 入力値から ClassNum オブジェクトを生成
            // Tạo đối tượng ClassNum từ dữ liệu hợp lệ
            ClassNum cn = new ClassNum();
            cn.setClassNum(classNumStr);
            cn.setSchool(school);

            // DB保存（8）
            // DB にクラス情報を保存（新規登録）
            // Lưu vào DB
            ClassNumDao dao = new ClassNumDao();
            dao.insert(cn);

            // 完了メッセージ（9）
            // 登録完了メッセージをセット
            // Gửi thông báo hoàn tất
            req.setAttribute("message", "登録完了しました。");

            // 完了画面へフォワード（10）
            // Chuyển sang màn hình hoàn tất
            req.getRequestDispatcher("/scoremanager/main/class_create_done.jsp").forward(req, res);

            // 一覧へリダイレクト（11）
            // クラス一覧画面へリダイレクト
            // Chuyển hướng sang danh sách lớp
            res.sendRedirect("ClassList.action");

        } catch (Exception e) {
            // エラー発生時（12）
            // error.jsp へフォワード
            // Nếu có lỗi → chuyển sang error.jsp
            e.printStackTrace();
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}
