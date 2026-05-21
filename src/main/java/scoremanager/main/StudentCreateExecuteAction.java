package scoremanager.main;
// VI: Package chứa các Action thuộc module scoremanager/main
// JP: scoremanager/main モジュールに属する Action クラスのパッケージ宣言

import java.io.IOException;
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
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            // ================================
            // 1) Lấy giáo viên từ session
            // 1) セッションからログイン中の教師を取得
            // ================================
            Teacher teacher = (Teacher) req.getSession().getAttribute("user");
            School school = teacher.getSchool();

            // ================================
            // 2) Lấy dữ liệu từ form
            // 2) フォーム入力値を取得
            // ================================
            String entYearStr = req.getParameter("entYear");
            String no = req.getParameter("no");
            String name = req.getParameter("name");
            String classNum = req.getParameter("classNum");
            String isAttendStr = req.getParameter("isAttend");

            Map<String, String> errors = new HashMap<>();

            // ================================
            // 3) Kiểm tra 入学年度
            // 3) 入学年度のチェック
            // ================================
            int entYear = 0;

            if (entYearStr == null || entYearStr.isEmpty()) {
                errors.put("entYear", "入学年度を選択してください。");
            } else {
                try {
                    entYear = Integer.parseInt(entYearStr);
                    int year = Calendar.getInstance().get(Calendar.YEAR);

                    if (entYear < year - 10 || entYear > year + 1) {
                        errors.put("entYear", "入学年度が不正です。");
                    }
                } catch (NumberFormatException e) {
                    errors.put("entYear", "入学年度は数値で入力してください。");
                }
            }

            // ================================
            // 4) Kiểm tra 学生番号
            // 4) 学生番号のチェック
            // ================================
            if (no == null || no.isEmpty()) {
                errors.put("no", "学生番号を入力してください。");
            } else if (!no.matches("\\d{3}")) {
                errors.put("no", "学生番号は3桁の数字で入力してください。");
            }

            // ================================
            // 5) Kiểm tra 氏名
            // 5) 氏名のチェック
            // ================================
            if (name == null || name.isEmpty()) {
                errors.put("name", "氏名を入力してください。");
            }

            // ================================
            // 6) Kiểm tra クラス番号
            // 6) クラス番号のチェック
            // ================================
            if (classNum == null || classNum.isEmpty()) {
                errors.put("classNum", "クラスを選択してください。");
            }

            boolean isAttend = (isAttendStr != null);

            // ================================
            // 7) Nếu có lỗi → quay lại form
            // 7) エラーがある場合 → 入力画面へ戻す
            // ================================
            if (!errors.isEmpty()) {
                setFormData(req, teacher, school, entYearStr, no, name, classNum, isAttendStr, errors);
                req.getRequestDispatcher("/scoremanager/student_insert.jsp").forward(req, res);
                return;
            }

            // ================================
            // 8) Kiểm tra trùng 学生番号 trong DB
            // 8) 学生番号の重複チェック
            // ================================
            StudentDao sDao = new StudentDao();
            Student exist = sDao.get(no, school);

            if (exist != null) {
                errors.put("no", "学生番号が重複しています。");

                setFormData(req, teacher, school, entYearStr, no, name, classNum, isAttendStr, errors);
                req.getRequestDispatcher("/scoremanager/student_insert.jsp").forward(req, res);
                return;
            }

            // ================================
            // 9) Tạo đối tượng Student
            // 9) Student オブジェクトを生成
            // ================================
            Student student = new Student();
            student.setEntYear(entYear);
            student.setNo(no);
            student.setName(name);
            student.setClassNum(classNum);
            student.setAttend(isAttend);
            student.setSchool(school);

            // ================================
            // 10) Lưu vào DB
            // 10) DB に保存
            // ================================
            sDao.save(student);

            // ================================
            // 11) Hiển thị thông báo hoàn tất
            // 11) 完了メッセージを表示
            // ================================
            req.setAttribute("message", "登録完了しました。");

            setFormData(req, teacher, school, entYearStr, "", "", "", null, null);
            req.getRequestDispatcher("/scoremanager/student_insert.jsp").forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }

    // ==========================================================
    // ⭐ Hàm tiện ích: Set lại dữ liệu form + danh sách năm/lớp
    // ⭐ 入力値・入学年度リスト・クラス一覧を再セットするユーティリティ
    // ==========================================================
    private void setFormData(
            HttpServletRequest req,
            Teacher teacher,
            School school,
            String entYearStr,
            String no,
            String name,
            String classNum,
            String isAttendStr,
            Map<String, String> errors) {

        try {
            ClassNumDao cDao = new ClassNumDao();

            // ⭐ SUPERADMIN → lấy toàn bộ class
            // ⭐ SUPERADMIN → 全クラス取得
            if (teacher.getRole() == 2) {
                List<String> classList = new ArrayList<>();
                cDao.findAll().forEach(c -> classList.add(c.getClass_num()));
                req.setAttribute("class_num_set", classList);
            } else {
                req.setAttribute("class_num_set", cDao.filter(school));
            }

            // 入学年度リスト
            List<Integer> entYearList = new ArrayList<>();
            int year = Calendar.getInstance().get(Calendar.YEAR);
            for (int i = year - 10; i <= year + 1; i++) {
                entYearList.add(i);
            }
            req.setAttribute("ent_year_set", entYearList);

            // 入力値を保持
            req.setAttribute("entYear", entYearStr);
            req.setAttribute("no", no);
            req.setAttribute("name", name);
            req.setAttribute("classNum", classNum);
            req.setAttribute("isAttend", isAttendStr);

            if (errors != null) {
                req.setAttribute("errors", errors);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
