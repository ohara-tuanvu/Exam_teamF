package scoremanager.main;
// scoremanager/main モジュールに属する Action クラスをまとめるパッケージ
// Package chứa các Action thuộc module scoremanager/main

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
// Action は MVC パターンでリクエスト処理を行う基底クラス
// Action là lớp cha dùng để xử lý request theo mô hình MVC

public class StudentCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            // セッションから教員を取得（1）
            // セッションに保存されているログイン中の教員情報を取得
            // Lấy giáo viên đang đăng nhập từ session
            Teacher teacher = (Teacher) req.getSession().getAttribute("user");

            // 学校情報を取得（2）
            // 教員が所属する学校情報を取得
            // Lấy trường của giáo viên
            School school = teacher.getSchool();

            // リクエストパラメータ取得（3）
            // 入力フォームから送信された値を取得
            // Lấy dữ liệu người dùng nhập từ form
            String entYearStr = req.getParameter("entYear");
            String no = req.getParameter("no");
            String name = req.getParameter("name");
            String classNum = req.getParameter("classNum");
            String isAttendStr = req.getParameter("isAttend");

            // エラーリスト（4）
            // 入力チェック用のエラーメッセージリスト
            // Danh sách lỗi để hiển thị lại trên form
            List<String> errors = new ArrayList<>();

            // 入学年度チェック（5）
            // 入学年度の妥当性チェック
            // Kiểm tra năm nhập học
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

            // 学生番号チェック（6）
            // 学生番号の未入力チェック
            // Kiểm tra mã số học sinh
            if (no == null || no.isEmpty()) {
                errors.add("学生番号を入力してください。");
            }

            // 氏名チェック（7）
            // 氏名の未入力チェック
            // Kiểm tra tên học sinh
            if (name == null || name.isEmpty()) {
                errors.add("氏名を入力してください。");
            }

            // クラス番号チェック（8）
            // クラス番号の未選択チェック
            // Kiểm tra lớp học
            if (classNum == null || classNum.isEmpty()) {
                errors.add("クラスを選択してください。");
            }

            // 在学フラグ（9）
            // 在学中チェックボックスの値を boolean に変換
            // Checkbox → nếu có giá trị thì true
            boolean isAttend = (isAttendStr != null);

            // エラーがある場合（10）
            // エラーが存在する場合は入力画面へ戻す
            // Nếu có lỗi → quay lại form nhập
            if (errors.size() > 0) {

                // クラス番号一覧を再取得
                // クラス番号一覧を再取得してセット
                // Lấy lại danh sách class_num để hiển thị
                ClassNumDao cDao = new ClassNumDao();
                req.setAttribute("class_num_set", cDao.filter(school));

                // 入学年度一覧を再作成
                // 入学年度リストを再生成してセット
                // Tạo lại danh sách năm nhập học
                List<Integer> entYearList = new ArrayList<>();
                int year = Calendar.getInstance().get(Calendar.YEAR);
                for (int i = year - 10; i <= year + 1; i++) {
                    entYearList.add(i);
                }
                req.setAttribute("ent_year_set", entYearList);

                // 入力値を保持
                // 入力値をリクエスト属性に保持
                // Giữ lại giá trị người dùng đã nhập
                req.setAttribute("entYear", entYearStr);
                req.setAttribute("no", no);
                req.setAttribute("name", name);
                req.setAttribute("classNum", classNum);
                req.setAttribute("isAttend", isAttendStr);

                // エラーメッセージをセット
                // エラー内容を JSP に渡す
                // Gửi danh sách lỗi sang JSP
                req.setAttribute("errors", errors);

                req.setAttribute("message", "登録完了しました。");
                req.getRequestDispatcher("/scoremanager/student_insert.jsp").forward(req, res);
                return;
            }

            // Studentインスタンス作成（11）
            // 入力値から Student オブジェクトを生成
            // Tạo đối tượng Student từ dữ liệu hợp lệ
            Student student = new Student();
            student.setEntYear(entYear);
            student.setNo(no);
            student.setName(name);
            student.setClassNum(classNum);
            student.setAttend(isAttend);
            student.setSchool(school);

            // DB保存（12）
            // DB に学生情報を保存（新規登録）
            // Lưu vào DB (insert hoặc update)
            StudentDao sDao = new StudentDao();
            sDao.save(student);

            req.setAttribute("message", "登録完了しました。");
            req.getRequestDispatcher("/scoremanager/student_insert.jsp").forward(req, res);

            // 一覧へリダイレクト（13）
            // 学生一覧画面へリダイレクト
            // Chuyển hướng sang danh sách học sinh
            res.sendRedirect("StudentList.action");

        } catch (Exception e) {
            // エラー発生時（14）
            // error.jsp へフォワード
            // Nếu có lỗi → chuyển sang error.jsp
            e.printStackTrace();
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}