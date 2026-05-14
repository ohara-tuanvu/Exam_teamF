package scoremanager.main;
// VI: Package chứa các Action của module scoremanager
// JP: scoremanager モジュールの Action クラスをまとめるパッケージ

import java.util.List;
// VI: Import List để chứa danh sách
// JP: List を使用するためのインポート

import bean.School;
import bean.Student;
import bean.Teacher;
import bean.Test;
// VI: Import các Bean cần dùng
// JP: 必要な Bean クラスをインポート
import dao.StudentDao;
import dao.TestDao;
// VI: Import DAO để truy vấn DB
// JP: DB 操作用の DAO クラスをインポート
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
// VI: Import Servlet API
// JP: Servlet API をインポート
import tool.Action;
// VI: Action là lớp cha theo mô hình MVC
// JP: MVC パターンの基底 Action クラス

public class TestByStudentAction extends Action {
// VI: Action xử lý màn hình “学生別成績一覧”
// JP: 「学生別成績一覧」画面の処理を行う Action

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
    // VI: Hàm chính xử lý request
    // JP: リクエスト処理のメインメソッド

        // 1. セッションから教員・学校情報取得
        HttpSession session = req.getSession();
        // VI: Lấy session hiện tại
        // JP: 現在のセッションを取得

        Teacher teacher = (Teacher) session.getAttribute("user");
        // VI: Lấy giáo viên đang đăng nhập
        // JP: ログイン中の教員を取得

        School school = teacher.getSchool();
        // VI: Lấy trường mà giáo viên thuộc về
        // JP: 教員が所属する学校を取得

        // 2. 学生一覧取得（プルダウン用）
        List<Student> studentList = new StudentDao().filter(school, true);
        // VI: Lấy danh sách sinh viên đang học (isAttend = true) để hiển thị trong dropdown
        // JP: 在籍中の学生一覧を取得し、プルダウンに表示する

        req.setAttribute("studentList", studentList);
        // VI: Gửi danh sách sinh viên sang JSP
        // JP: 学生一覧を JSP に渡す

        // 3. 学生番号が指定されていれば成績一覧を取得
        String studentNo = req.getParameter("studentNo");
        // VI: Lấy studentNo được chọn từ form
        // JP: フォームから選択された学生番号を取得

        if (studentNo != null && !studentNo.trim().isEmpty()) {
            // VI: Nếu người dùng đã chọn sinh viên
            // JP: 学生番号が指定されている場合

            List<Test> testList = new TestDao().filterByStudent(school, studentNo.trim());
            // VI: Lấy danh sách điểm theo studentNo
            // JP: 学生番号で成績一覧を取得

            req.setAttribute("testList", testList);
            // VI: Gửi danh sách điểm sang JSP
            // JP: 成績一覧を JSP に渡す

            req.setAttribute("selectedStudentNo", studentNo);
            // VI: Giữ lại studentNo đã chọn để hiển thị lại trên màn hình
            // JP: 選択された学生番号を画面に保持する
        }

        // 4. JSPへフォワード
        req.getRequestDispatcher("test_by_student.jsp").forward(req, res);
        // VI: Chuyển sang JSP hiển thị kết quả
        // JP: JSP にフォワードして画面を表示
    }
}
