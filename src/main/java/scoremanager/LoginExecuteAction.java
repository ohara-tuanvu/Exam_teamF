package scoremanager;
// Package chứa các Action thuộc module scoremanager (màn hình login chính)
// scoremanager モジュールのログイン画面を扱う Action クラス

import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Teacher;
import dao.SchoolDao;
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
        String url = "";
        String id = "";
        String password = "";
        TeacherDao teacherDao = new TeacherDao();
        Teacher teacher = null;

        // リクエストパラメータ―の取得 2
        // Lấy tham số từ request (ID & password)
        id = req.getParameter("id");          
        password = req.getParameter("password");

        // DBからデータ取得 3
        // Lấy dữ liệu giáo viên từ DB theo ID & password
        teacher = teacherDao.login(id, password);

        // ビジネスロジック 4〜7
        // Xử lý nghiệp vụ đăng nhập
        if (teacher != null) { // 認証成功の場合
            // Trường hợp đăng nhập thành công

            HttpSession session = req.getSession(true);
            // Lấy session (tạo mới nếu chưa có)
            // セッションを取得（なければ新規作成）

            teacher.setAuthenticated(true);
            // Đánh dấu giáo viên đã đăng nhập
            // 認証済みフラグをセット

            session.setAttribute("user", teacher);
            // Lưu thông tin giáo viên vào session
            // 教員情報をセッションに保存

            //追加：学校情報をセッションに保存
            // Thêm: Lưu thông tin trường vào session
            SchoolDao sDao = new SchoolDao();
            School school = sDao.get(teacher.getSchoolCd());
            session.setAttribute("school", school);
            //ここまで追加

            //追加：school_cd をセッションに保存
            // Thêm: Lưu school_cd vào session
            session.setAttribute("school_cd", teacher.getSchoolCd());
            //ここまで追加

            //追加：role（権限）をセッションに保存
            // Thêm: Lưu role (quyền hạn) vào session
            session.setAttribute("role", teacher.getRole());
            //ここまで追加

            // リダイレクト
            // Chuyển hướng sang Menu.action
            res.sendRedirect(req.getContextPath() + "/scoremanager/main/Menu.action");

        } else {
            // 認証失敗の場合
            // Trường hợp đăng nhập thất bại

            List<String> errors = new ArrayList<>();
            errors.add("IDまたはパスワードが確認できませんでした");
            // Thông báo lỗi: ID hoặc mật khẩu không đúng
            // エラー文言を追加

            req.setAttribute("errors", errors);
            // Gửi danh sách lỗi sang JSP
            // JSP にエラーリストを渡す

            req.setAttribute("id", id);
            // Giữ lại ID đã nhập để hiển thị lại
            // 入力されたIDを保持

            url = "/scoremanager/main/login.jsp";
            req.getRequestDispatcher(url).forward(req, res);
            // Quay lại màn hình login
            // ログイン画面にフォワード
        }
    }
}
