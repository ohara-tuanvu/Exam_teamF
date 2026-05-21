package scoremanager.main;
// VI: Package chứa các Action thuộc module scoremanager/main
// JP: scoremanager/main モジュールに属する Action クラスをまとめるパッケージ

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

public class StudentUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            req.setCharacterEncoding("UTF-8");
            // VI: Thiết lập UTF-8 để xử lý tiếng Nhật/Việt
            // JP: 日本語・ベトナム語対応のため UTF-8 を設定

            // ---------------------------------------------
            // 1) Lấy dữ liệu từ form
            // 1) 更新フォームから送信された値を取得
            // ---------------------------------------------
            String no = req.getParameter("no");
            String name = req.getParameter("name");
            String entYearStr = req.getParameter("ent_year");
            String classNum = req.getParameter("class_num");
            String isAttendStr = req.getParameter("is_attend");

            // ---------------------------------------------
            // 2) Lấy giáo viên từ session
            // 2) セッションからログイン中の教員を取得
            // ---------------------------------------------
            Teacher teacher = (Teacher) req.getSession().getAttribute("user");
            School school = teacher.getSchool();

            // ---------------------------------------------
            // 3) Map chứa lỗi
            // 3) 入力チェック用エラーマップ
            // ---------------------------------------------
            Map<String, String> errors = new HashMap<>();

            // ---------------------------------------------
            // 4) Validate 氏名
            // 4) 氏名の未入力チェック
            // ---------------------------------------------
            if (name == null || name.isEmpty()) {
                errors.put("name", "氏名を入力してください。");
            }

            // ---------------------------------------------
            // 5) Validate 入学年度
            // 5) 入学年度の数値チェック
            // ---------------------------------------------
            int entYear = 0;
            try {
                entYear = Integer.parseInt(entYearStr);
            } catch (Exception e) {
                errors.put("ent_year", "入学年度が不正です。");
            }

            // ---------------------------------------------
            // 6) Chuyển checkbox → boolean
            // 6) 在学フラグ（チェックボックス）の変換
            // ---------------------------------------------
            boolean isAttend = (isAttendStr != null);

            // ---------------------------------------------
            // 7) Nếu có lỗi → quay lại form update
            // 7) エラーがある場合 → 更新画面へ戻す
            // ---------------------------------------------
            if (!errors.isEmpty()) {

                ClassNumDao cDao = new ClassNumDao();
                List<String> classNumList;

                // ⭐ SUPERADMIN → lấy toàn bộ class
                // ⭐ SUPERADMIN → 全クラス取得
                if (teacher.getRole() == 2) {
                    classNumList = new ArrayList<>();
                    cDao.findAll().forEach(c -> classNumList.add(c.getClass_num()));
                } else {
                    classNumList = cDao.filter(school);
                }

                // 入学年度リスト
                List<Integer> entYearList = new ArrayList<>();
                int year = Calendar.getInstance().get(Calendar.YEAR);
                for (int i = year - 10; i <= year + 1; i++) {
                    entYearList.add(i);
                }

                // 入力値を保持した Student を作成
                Student student = new Student();
                student.setNo(no);
                student.setName(name);
                student.setEntYear(entYear);
                student.setClassNum(classNum);
                student.setAttend(isAttend);
                student.setSchool(school);

                req.setAttribute("student", student);
                req.setAttribute("class_num_set", classNumList);
                req.setAttribute("ent_year_set", entYearList);
                req.setAttribute("errors", errors);

                req.getRequestDispatcher("/scoremanager/main/student_update.jsp").forward(req, res);
                return;
            }

            // ---------------------------------------------
            // 8) Tạo Student hợp lệ để update DB
            // 8) DB 更新用 Student オブジェクトを作成
            // ---------------------------------------------
            Student student = new Student();
            student.setNo(no);
            student.setName(name);
            student.setEntYear(entYear);
            student.setClassNum(classNum);
            student.setAttend(isAttend);
            student.setSchool(school);

            // ---------------------------------------------
            // 9) UPDATE DB
            // 9) DB 更新処理
            // ---------------------------------------------
            StudentDao sDao = new StudentDao();
            sDao.save(student);

            // ---------------------------------------------
            // 10) Hiển thị thông báo hoàn tất
            // 10) 完了メッセージを表示
            // ---------------------------------------------
            req.setAttribute("message", "変更しました。");

            // ⭐ Load lại danh sách class cho SuperAdmin
            ClassNumDao cDao = new ClassNumDao();
            List<String> classNumList;

            if (teacher.getRole() == 2) {
                classNumList = new ArrayList<>();
                cDao.findAll().forEach(c -> classNumList.add(c.getClass_num()));
            } else {
                classNumList = cDao.filter(school);
            }

            // 入学年度リスト
            List<Integer> entYearList = new ArrayList<>();
            int year = Calendar.getInstance().get(Calendar.YEAR);
            for (int i = year - 10; i <= year + 1; i++) {
                entYearList.add(i);
            }

            req.setAttribute("student", student);
            req.setAttribute("class_num_set", classNumList);
            req.setAttribute("ent_year_set", entYearList);

            req.getRequestDispatcher("/scoremanager/main/student_update.jsp").forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}
