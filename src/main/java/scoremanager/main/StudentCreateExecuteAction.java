package scoremanager.main;
// Package chứa các Action thuộc module scoremanager/main
// scoremanager/main モジュールに属する Action クラスをまとめるパッケージ

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;
// Action là lớp cha dùng để xử lý request theo mô hình MVC
// Action は MVC パターンでリクエスト処理を行う基底クラス

public class StudentCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            // セッションから教員を取得
            // Lấy giáo viên đang đăng nhập từ session
            // セッションに保存されているログイン中の教員情報を取得
            Teacher teacher = (Teacher) req.getSession().getAttribute("user");
            School school = teacher.getSchool(); // Lấy trường của giáo viên
            // 教員が所属する学校情報を取得

            // リクエストパラメータ取得
            // Lấy dữ liệu người dùng nhập từ form
            // 入力フォームから送信された値を取得
            String entYearStr = req.getParameter("entYear");
            String no = req.getParameter("no");
            String name = req.getParameter("name");
            String classNum = req.getParameter("classNum");
            String isAttendStr = req.getParameter("isAttend");

            // エラーリスト
            // Danh sách lỗi để hiển thị lại trên form
            // 入力チェック用のエラーメッセージリスト
            List<String> errors = new ArrayList<>();

            // 入学年度チェック
            // Kiểm tra năm nhập học
            // 入学年度の妥当性チェック
            int entYear = 0;
            if (entYearStr == null || entYearStr.isEmpty()) {
                errors.add("入学年度を選択してください。");
            } else {
                try {
                    entYear = Integer.parseInt(entYearStr);

                    int year = Calendar.getInstance().get(Calendar.YEAR);
                    if (entYear < year - 10 || entYear > year + 1) {
                        errors.add("入学年度が不正です。");
                    }
                } catch (NumberFormatException e) {
                    errors.add("入学年度は数値で入力してください。");
                }
            }

            // 学生番号チェック
            // Kiểm tra mã số học sinh
            // 学生番号の未入力チェック
            if (no == null || no.isEmpty()) {
                errors.add("学生番号を入力してください。");
            }

            // 氏名チェック
            // Kiểm tra tên học sinh
            // 氏名の未入力チェック
            if (name == null || name.isEmpty()) {
                errors.add("氏名を入力してください。");
            }

            // クラス番号チェック
            // Kiểm tra lớp học
            // クラス番号の未選択チェック
            if (classNum == null || classNum.isEmpty()) {
                errors.add("クラスを選択してください。");
            }

            // 在学フラグ
            // Checkbox → nếu có giá trị thì true
            // 在学中チェックボックスの値を boolean に変換
            boolean isAttend = (isAttendStr != null);

            // エラーがある場合 → 入力画面へ戻す
            // Nếu có lỗi → quay lại form nhập
            // エラーが存在する場合は入力画面へ戻す
            if (errors.size() > 0) {

                // クラス番号一覧を再取得
                // Lấy lại danh sách class_num để hiển thị
                // クラス番号一覧を再取得してセット
                ClassNumDao cDao = new ClassNumDao();
                req.setAttribute("class_num_set", cDao.filter(school));

                // 入学年度一覧を再作成
                // Tạo lại danh sách năm nhập học
                // 入学年度リストを再生成してセット
                List<Integer> entYearList = new ArrayList<>();
                int year = Calendar.getInstance().get(Calendar.YEAR);
                for (int i = year - 10; i <= year + 1; i++) {
                    entYearList.add(i);
                }
                req.setAttribute("ent_year_set", entYearList);

                // 入力値を保持
                // Giữ lại giá trị người dùng đã nhập
                // 入力値をリクエスト属性に保持
                req.setAttribute("entYear", entYearStr);
                req.setAttribute("no", no);
                req.setAttribute("name", name);
                req.setAttribute("classNum", classNum);
                req.setAttribute("isAttend", isAttendStr);

                // エラーメッセージをセット
                // Gửi danh sách lỗi sang JSP
                // エラー内容を JSP に渡す
                req.setAttribute("errors", errors);

                req.setAttribute("message", "登録完了しました。");
                req.getRequestDispatcher("/scoremanager/student_insert.jsp").forward(req, res);
                return;
            }

            // Studentインスタンス作成
            // Tạo đối tượng Student từ dữ liệu hợp lệ
            // 入力値から Student オブジェクトを生成
            Student student = new Student();
            student.setEntYear(entYear);
            student.setNo(no);
            student.setName(name);
            student.setClassNum(classNum);
            student.setAttend(isAttend);
            student.setSchool(school);

            // DB保存
            // Lưu vào DB (insert hoặc update)
            // DB に学生情報を保存（新規登録）
            StudentDao sDao = new StudentDao();
            sDao.save(student);
            
            req.setAttribute("message", "登録完了しました。");
            req.getRequestDispatcher("/scoremanager/student_insert.jsp").forward(req, res);

            // 一覧へリダイレクト
            // Chuyển hướng sang danh sách học sinh
            // 学生一覧画面へリダイレクト
            res.sendRedirect("StudentList.action");

        } catch (Exception e) {
            // Nếu có lỗi → chuyển sang error.jsp
            // エラー発生時 → error.jsp へフォワード
            e.printStackTrace();
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}
