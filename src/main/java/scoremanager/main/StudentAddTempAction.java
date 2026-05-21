package scoremanager.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentAddTempAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        req.setCharacterEncoding("UTF-8");
        // VI: Thiết lập mã hóa UTF-8
        // JP: 日本語入力対応のためUTF-8を設定

        Teacher teacher = (Teacher) req.getSession().getAttribute("user");
        // VI: Lấy thông tin giáo viên đang đăng nhập
        // JP: ログイン中の教師情報を取得

        School school = teacher.getSchool();
        // VI: Lấy trường của giáo viên
        // JP: 教師に紐づく学校を取得

        List<Student> tempList = (List<Student>) req.getSession().getAttribute("temp_students");
        // VI: Lấy danh sách tạm từ session
        // JP: セッションから一時学生リストを取得

        if (tempList == null) {
            tempList = new ArrayList<>();
            // VI: Nếu chưa có → tạo mới
            // JP: 存在しない場合 → 新規作成
        }

        // ----------------------------------------------------
        // Lấy dữ liệu từ form / フォーム入力の取得
        // ----------------------------------------------------
        String entYearStr = req.getParameter("entYear");
        String no = req.getParameter("no");
        String name = req.getParameter("name");
        String classNum = req.getParameter("classNum");
        String isAttendStr = req.getParameter("isAttend");

        Map<String, String> errors = new HashMap<>();
        // VI: Map chứa lỗi
        // JP: エラーメッセージを格納するMap

        // ----------------------------------------------------
        // Validate 入力チェック
        // ----------------------------------------------------
        int entYear = 0;
        if (entYearStr == null || entYearStr.isEmpty()) {
            errors.put("entYear", "入学年度を選択してください。");
        } else {
            entYear = Integer.parseInt(entYearStr);
        }

        if (no == null || no.isEmpty()) {
            errors.put("no", "学生番号を入力してください。");
        }

        if (name == null || name.isEmpty()) {
            errors.put("name", "氏名を入力してください。");
        }

        if (classNum == null || classNum.isEmpty()) {
            errors.put("classNum", "クラスを選択してください。");
        }

        // ----------------------------------------------------
        // Kiểm tra trùng trong danh sách tạm
        // 一時リスト内の重複チェック
        // ----------------------------------------------------
        for (Student st : tempList) {
            if (st.getNo().equals(no)) {
                errors.put("no", "学生番号「" + no + "」は既に追加されています。");
                break;
            }
        }

        // ----------------------------------------------------
        // Kiểm tra trùng trong DB
        // DB内の重複チェック
        // ----------------------------------------------------
        if (!errors.containsKey("no")) {
            StudentDao sDao = new StudentDao();
            Student exist = sDao.get(no, school);

            if (exist != null) {
                errors.put("no", "学生番号「" + no + "」は既に登録されています。");
            }
        }

        // ----------------------------------------------------
        // Nếu có lỗi → quay lại form
        // エラーがある場合 → 入力画面へ戻す
        // ----------------------------------------------------
        if (!errors.isEmpty()) {

            ClassNumDao cDao = new ClassNumDao();
            // VI: DAO lấy danh sách lớp
            // JP: クラス一覧取得DAO

            // ⭐ SUPERADMIN → lấy toàn bộ class
            // ⭐ SUPERADMIN → 全クラス取得
            if (teacher.getRole() == 2) {
                req.setAttribute("class_num_set", cDao.findAll());
            } else {
                req.setAttribute("class_num_set", cDao.filter(school));
            }

            // VI: Tạo danh sách năm nhập học
            // JP: 入学年度リストを作成
            List<Integer> entYearList = new ArrayList<>();
            int year = Calendar.getInstance().get(Calendar.YEAR);
            for (int i = year - 10; i <= year + 1; i++) {
                entYearList.add(i);
            }
            req.setAttribute("ent_year_set", entYearList);

            // VI: Giữ lại dữ liệu nhập
            // JP: 入力値を保持
            req.setAttribute("entYear", entYearStr);
            req.setAttribute("no", no);
            req.setAttribute("name", name);
            req.setAttribute("classNum", classNum);
            req.setAttribute("isAttend", isAttendStr);

            req.setAttribute("errors", errors);
            req.setAttribute("temp_students", tempList);

            req.getRequestDispatcher("/scoremanager/student_insert.jsp").forward(req, res);
            return;
        }

        // ----------------------------------------------------
        // Không có lỗi → thêm vào danh sách tạm
        // エラーなし → 一時リストに追加
        // ----------------------------------------------------
        Student s = new Student();
        s.setEntYear(entYear);
        s.setNo(no);
        s.setName(name);
        s.setClassNum(classNum);
        s.setAttend(isAttendStr != null);
        s.setSchool(school);

        tempList.add(s);
        req.getSession().setAttribute("temp_students", tempList);

        // ----------------------------------------------------
        // Reset form / フォームリセット
        // ----------------------------------------------------
        ClassNumDao cDao = new ClassNumDao();

        // ⭐ SUPERADMIN → dùng findAll()
        // ⭐ SUPERADMIN → findAll() を使用
        if (teacher.getRole() == 2) {
            req.setAttribute("class_num_set", cDao.findAll());
        } else {
            req.setAttribute("class_num_set", cDao.filter(school));
        }

        List<Integer> entYearList = new ArrayList<>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = year - 10; i <= year + 1; i++) {
            entYearList.add(i);
        }
        req.setAttribute("ent_year_set", entYearList);

        // VI: Reset giá trị form
        // JP: フォーム値をリセット
        req.setAttribute("entYear", "");
        req.setAttribute("no", "");
        req.setAttribute("name", "");
        req.setAttribute("classNum", "");
        req.setAttribute("isAttend", null);

        req.setAttribute("temp_students", tempList);

        req.getRequestDispatcher("/scoremanager/student_insert.jsp").forward(req, res);
    }
}
