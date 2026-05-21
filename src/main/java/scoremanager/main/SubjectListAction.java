package scoremanager.main;
// VI: Package chứa các Action thuộc module scoremanager/main
// JP: scoremanager/main モジュールに属する Action クラスをまとめるパッケージ

import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ---------------------------------------------
        // 1) Lấy session + giáo viên đăng nhập
        // 1) セッションとログイン中の教員を取得
        // ---------------------------------------------
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ---------------------------------------------
        // 2) Tạo DAO
        // 2) DAO を生成
        // ---------------------------------------------
        SubjectDao sDao = new SubjectDao();

        // ---------------------------------------------
        // 3) Lấy danh sách môn học
        //    SUPERADMIN → findAll()
        //    Admin/Teacher → filter(school)
        //
        // 3) 科目一覧を取得
        //    SUPERADMIN → 全科目取得
        //    Admin/Teacher → 学校に紐づく科目のみ取得
        // ---------------------------------------------
        List<Subject> subjects;

        if (teacher.getRole() == 2) {
            // SUPERADMIN
            subjects = sDao.findAll();
        } else {
            // Admin/Teacher
            subjects = sDao.filter(teacher.getSchool());
        }

        // ---------------------------------------------
        // 4) Gửi dữ liệu sang JSP
        // 4) JSP へ科目一覧を渡す
        // ---------------------------------------------
        req.setAttribute("subjects", subjects);

        // ---------------------------------------------
        // 5) Forward sang subject_list.jsp
        // 5) subject_list.jsp へフォワード
        // ---------------------------------------------
        req.getRequestDispatcher("subject_list.jsp").forward(req, res);
    }
}
