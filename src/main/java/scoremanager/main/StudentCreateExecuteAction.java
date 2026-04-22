package scoremanager.main;
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
// Action là lớp cha dùng để xử lý request theo mô hình MVC

public class StudentCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            // セッションから教員を取得
            // Lấy giáo viên đang đăng nhập từ session
            Teacher teacher = (Teacher) req.getSession().getAttribute("user");
            School school = teacher.getSchool(); // Lấy trường của giáo viên

            // リクエストパラメータ取得
            // Lấy dữ liệu người dùng nhập từ form
            String entYearStr = req.getParameter("entYear");
            String no = req.getParameter("no");
            String name = req.getParameter("name");
            String classNum = req.getParameter("classNum");
            String isAttendStr = req.getParameter("isAttend");

            // エラーリスト
            // Danh sách lỗi để hiển thị lại trên form
            List<String> errors = new ArrayList<>();

            // 入学年度チェック
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

            // 学生番号チェック
            if (no == null || no.isEmpty()) {
                errors.add("学生番号を入力してください。");
            }

            // 氏名チェック
            if (name == null || name.isEmpty()) {
                errors.add("氏名を入力してください。");
            }

            // クラス番号チェック
            if (classNum == null || classNum.isEmpty()) {
                errors.add("クラスを選択してください。");
            }

            // 在学フラグ
            // Checkbox → nếu có giá trị thì true
            boolean isAttend = (isAttendStr != null);

            // エラーがある場合 → 入力画面へ戻す
            // Nếu có lỗi → quay lại form nhập
            if (errors.size() > 0) {

                // クラス番号一覧を再取得
                // Lấy lại danh sách class_num để hiển thị
                ClassNumDao cDao = new ClassNumDao();
                req.setAttribute("class_num_set", cDao.filter(school));

                // 入学年度一覧を再作成
                // Tạo lại danh sách năm nhập học
                List<Integer> entYearList = new ArrayList<>();
                int year = Calendar.getInstance().get(Calendar.YEAR);
                for (int i = year - 10; i <= year + 1; i++) {
                    entYearList.add(i);
                }
                req.setAttribute("ent_year_set", entYearList);

                // 入力値を保持
                // Giữ lại giá trị người dùng đã nhập
                req.setAttribute("entYear", entYearStr);
                req.setAttribute("no", no);
                req.setAttribute("name", name);
                req.setAttribute("classNum", classNum);
                req.setAttribute("isAttend", isAttendStr);

                // Gửi danh sách lỗi sang JSP
                req.setAttribute("errors", errors);

                req.setAttribute("message", "登録完了しました。");
                req.getRequestDispatcher("/scoremanager/student_insert.jsp").forward(req, res);
                return;
            }

            // Studentインスタンス作成
            // Tạo đối tượng Student từ dữ liệu hợp lệ
            Student student = new Student();
            student.setEntYear(entYear);
            student.setNo(no);
            student.setName(name);
            student.setClassNum(classNum);
            student.setAttend(isAttend);
            student.setSchool(school);

            // DB保存
            // Lưu vào DB (insert hoặc update)
            StudentDao sDao = new StudentDao();
            sDao.save(student);
            
            req.setAttribute("message", "登録完了しました。");
            req.getRequestDispatcher("/scoremanager/student_insert.jsp").forward(req, res);

            // 一覧へリダイレクト
            // Chuyển hướng sang danh sách học sinh
            res.sendRedirect("StudentList.action");

        } catch (Exception e) {
            // Nếu có lỗi → chuyển sang error.jsp
            e.printStackTrace();
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}
