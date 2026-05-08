package scoremanager.main;

import java.util.List;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassListAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // ===== Lấy session hiện tại =====
        // ===== 現在のセッションを取得 =====
        HttpSession session = request.getSession();

        // ===== Lấy giáo viên đang đăng nhập =====
        // ===== ログイン中の教員情報を取得 =====
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ===== DAO =====
        // ===== DAO の準備 =====
        ClassNumDao cNumDao = new ClassNumDao();

        // ===== Lấy danh sách class theo school =====
        // ===== 学校に紐づくクラス一覧を取得 =====
        List<ClassNum> classList = cNumDao.findBySchool(teacher.getSchool());

        // ===== Gửi dữ liệu sang JSP =====
        // ===== JSP へデータを渡す =====
        request.setAttribute("class_list", classList);

        // ===== Chuyển sang trang class_list.jsp =====
        // ===== クラス一覧画面へフォワード =====
        request.getRequestDispatcher("class_list.jsp").forward(request, response);
    }
}
