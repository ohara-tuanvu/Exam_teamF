package scoremanager.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SchoolDao;
import dao.StudentDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            // VI: Lấy mã học sinh từ URL (?no=xxxx)
            // JP: URL パラメータ (?no=xxxx) から学生番号を取得
            String no = req.getParameter("no");

            // VI: Lấy giáo viên đang đăng nhập từ session
            // JP: セッションからログイン中の教員情報を取得
            Teacher teacher = (Teacher) req.getSession().getAttribute("user");
            School school = teacher.getSchool();

            // VI: Lấy thông tin học sinh theo quyền của người dùng
            // JP: 権限に応じて学生情報を取得
            StudentDao sDao = new StudentDao();
            Student student;

            if (teacher.getRole() == 2) {
                // VI: SUPERADMIN → lấy không giới hạn trường
                // JP: SUPERADMIN → 学校制限なしで取得
                student = sDao.get(no);
            } else {
                // VI: Admin/Teacher → chỉ lấy học sinh thuộc trường mình
                // JP: Admin/Teacher → 自校の学生のみ取得
                student = sDao.get(no, school);
            }

            // VI: Không tìm thấy học sinh → chuyển sang error.jsp
            // JP: 学生が見つからない場合 → error.jsp へフォワード
            if (student == null) {
                req.setAttribute("error", "学生情報が存在しません。");
                req.getRequestDispatcher("/error.jsp").forward(req, res);
                return;
            }

            // VI: Lấy danh sách lớp theo quyền
            // JP: 権限に応じてクラス一覧を取得
            ClassNumDao cDao = new ClassNumDao();
            List<String> classNumList;

            if (teacher.getRole() == 2) {
                // VI: SUPERADMIN → lấy toàn bộ lớp
                // JP: SUPERADMIN → 全クラスを取得
                classNumList = new ArrayList<>();
                cDao.findAll().forEach(c -> classNumList.add(c.getClass_num()));
            } else {
                // VI: Admin/Teacher → chỉ lấy lớp thuộc trường
                // JP: Admin/Teacher → 自校のクラスのみ取得
                classNumList = cDao.filter(school);
            }

            // VI: SUPERADMIN → Lấy danh sách trường để cho phép thay đổi
            // JP: SUPERADMIN → 学校変更用に学校一覧を取得
            if (teacher.getRole() == 2) {
                SchoolDao schoolDao = new SchoolDao();
                req.setAttribute("school_list", schoolDao.findAll());
            }

            // VI: Tạo danh sách năm nhập học (10 năm trước → 1 năm sau)
            // JP: 入学年度リストを作成（10年前〜翌年）
            List<Integer> entYearList = new ArrayList<>();
            int year = Calendar.getInstance().get(Calendar.YEAR);
            for (int i = year - 10; i <= year + 1; i++) {
                entYearList.add(i);
            }

            // VI: Gửi dữ liệu sang JSP để hiển thị form cập nhật
            // JP: 更新フォーム表示用データを JSP に渡す
            req.setAttribute("student", student);
            req.setAttribute("class_num_set", classNumList);
            req.setAttribute("ent_year_set", entYearList);

            // VI: Gửi thông tin trường hiện tại của học sinh
            // JP: 学生が所属している学校情報をセット
            req.setAttribute("school", student.getSchool());

            // VI: Chuyển sang trang cập nhật học sinh
            // JP: student_update.jsp へフォワード
            req.getRequestDispatcher("/scoremanager/main/student_update.jsp").forward(req, res);

        } catch (Exception e) {
            // VI: Lỗi hệ thống → chuyển sang error.jsp
            // JP: 例外発生時 → error.jsp へフォワード
            e.printStackTrace();
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}
