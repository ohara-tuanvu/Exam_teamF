package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentMultiInsertExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        /* ============================================================
           VI: Lấy thông tin giáo viên đang đăng nhập và trường tương ứng
           JP: ログイン中の教師情報と所属学校を取得
        ============================================================ */
        Teacher teacher = (Teacher) req.getSession().getAttribute("user");
        School school = teacher.getSchool();

        StudentDao dao = new StudentDao();

        /* ============================================================
           VI: Lấy danh sách học sinh tạm từ session
           JP: セッションから一時学生リストを取得
        ============================================================ */
        List<Student> tempList = (List<Student>) req.getSession().getAttribute("temp_students");

        /* ============================================================
           ⭐ Trường hợp 1: Không có danh sách tạm → đăng ký 1 học sinh
           ⭐ ケース1：一時リストが空 → 1件のみ登録
        ============================================================ */
        if (tempList == null || tempList.isEmpty()) {

            /* ---------------------------------------------
               VI: Lấy dữ liệu từ form
               JP: フォーム入力値を取得
            --------------------------------------------- */
            String entYearStr = req.getParameter("entYear");
            String no = req.getParameter("no");
            String name = req.getParameter("name");
            String classNum = req.getParameter("classNum");
            String isAttendStr = req.getParameter("isAttend");

            /* ---------------------------------------------
               VI: Kiểm tra dữ liệu bắt buộc
               JP: 必須項目の簡易チェック
            --------------------------------------------- */
            if (entYearStr == null || entYearStr.isEmpty()
                    || no == null || no.isEmpty()
                    || name == null || name.isEmpty()
                    || classNum == null || classNum.isEmpty()) {

                req.setAttribute("error", "入力内容に不備があります。");
                req.getRequestDispatcher("/scoremanager/student_insert.jsp").forward(req, res);
                return;
            }

            int entYear = Integer.parseInt(entYearStr);

            /* ---------------------------------------------
               VI: Kiểm tra trùng trong DB
               JP: DB内の学生番号重複チェック
            --------------------------------------------- */
            Student exist = dao.get(no, school);
            if (exist != null) {

                req.setAttribute("error", "学生番号「" + no + "」は既に登録されています。");

                // Giữ lại dữ liệu nhập
                req.setAttribute("entYear", entYearStr);
                req.setAttribute("no", no);
                req.setAttribute("name", name);
                req.setAttribute("classNum", classNum);
                req.setAttribute("isAttend", isAttendStr);

                // Load lại danh sách select
                ClassNumDao cDao = new ClassNumDao();
                req.setAttribute("class_num_set", cDao.filter(school));

                req.getRequestDispatcher("/scoremanager/student_insert.jsp").forward(req, res);
                return;
            }

            /* ---------------------------------------------
               VI: Không trùng → tạo student và lưu vào DB
               JP: 重複なし → 学生情報を作成しDBへ保存
            --------------------------------------------- */
            Student s = new Student();
            s.setEntYear(entYear);
            s.setNo(no);
            s.setName(name);
            s.setClassNum(classNum);
            s.setAttend(isAttendStr != null);
            s.setSchool(school);

            dao.save(s);

            req.setAttribute("message", "学生の登録が完了しました。");
            req.getRequestDispatcher("/scoremanager/student_insert.jsp").forward(req, res);
            return;
        }

        /* ============================================================
           ⭐ Trường hợp 2: Có danh sách tạm → đăng ký nhiều học sinh
           ⭐ ケース2：一時リストあり → 複数学生を登録
        ============================================================ */
        for (Student s : tempList) {

            /* ---------------------------------------------
               VI: Kiểm tra trùng trong DB
               JP: DB内の学生番号重複チェック
            --------------------------------------------- */
            Student exist = dao.get(s.getNo(), s.getSchool());
            if (exist != null) {

                req.setAttribute("error", "学生番号「" + s.getNo() + "」は既に登録されています。");
                req.setAttribute("temp_students", tempList);

                req.getRequestDispatcher("/scoremanager/student_insert.jsp").forward(req, res);
                return;
            }

            /* ---------------------------------------------
               VI: Không trùng → lưu vào DB
               JP: 重複なし → DBへ保存
            --------------------------------------------- */
            dao.save(s);
        }

        /* ============================================================
           VI: Xóa danh sách tạm sau khi đăng ký xong
           JP: 登録完了後、一時リストをセッションから削除
        ============================================================ */
        req.getSession().removeAttribute("temp_students");

        req.setAttribute("message", "複数学生の登録が完了しました。");
        req.getRequestDispatcher("/scoremanager/student_insert.jsp").forward(req, res);
    }
}