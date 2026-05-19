package scoremanager.main;

import java.io.IOException;
import java.util.List;

import bean.Teacher;
import dao.TeacherDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TeacherListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            // Lấy user đang đăng nhập
            // ログイン中の教員を取得
            Teacher loginUser = (Teacher) req.getSession().getAttribute("user");

            TeacherDao dao = new TeacherDao();
            // Tạo DAO để thao tác DB
            // DB操作のために TeacherDao を生成

            List<Teacher> list;

            // ⭐ SUPER ADMIN → xem toàn bộ giáo viên
            // superadmin(role=2) → 全教員を閲覧可能
            if (loginUser.getRole() == 2) {
                list = dao.getAll();
            }
            // ⭐ ADMIN CHI NHÁNH → xem giáo viên thuộc school_cd của mình
            // 支部管理者(role=1) → 自分の school_cd の教員のみ閲覧可能
            else {
                String schoolCd = loginUser.getSchoolCd();
                // Lấy mã trường của admin chi nhánh
                // 支部管理者の school_cd を取得

                list = dao.findAll(schoolCd);
                // Lấy danh sách giáo viên theo school_cd
                // school_cd に基づいて教員一覧を取得
            }

            req.setAttribute("teacher_list", list);
            // Gửi danh sách giáo viên sang JSP
            // JSP に教員一覧を渡す

            req.getRequestDispatcher("/scoremanager/main/teacher_list.jsp")
                .forward(req, res);
            // Chuyển đến trang danh sách giáo viên
            // 教員一覧ページにフォワード

        } catch (Exception e) {
            e.printStackTrace();
            // In lỗi ra console
            // コンソールにエラーを出力

            req.getRequestDispatcher("/error.jsp").forward(req, res);
            // Chuyển đến trang lỗi
            // エラーページにフォワード
        }
    }
}
