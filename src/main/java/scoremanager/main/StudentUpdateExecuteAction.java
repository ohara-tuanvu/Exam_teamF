package scoremanager.main;
// Package chứa các Action thuộc module scoremanager/main

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
// Action là lớp cha dùng để xử lý request theo mô hình MVC

public class StudentUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            req.setCharacterEncoding("UTF-8");
            // Đảm bảo request đọc đúng tiếng Nhật / UTF-8

            // パラメータ取得
            // Lấy dữ liệu người dùng nhập từ form update
            String no = req.getParameter("no");
            String name = req.getParameter("name");
            String entYearStr = req.getParameter("ent_year");
            String classNum = req.getParameter("class_num");
            String isAttendStr = req.getParameter("is_attend");

            // セッションから教員を取得
            // Lấy giáo viên đang đăng nhập từ session
            Teacher teacher = (Teacher) req.getSession().getAttribute("user");
            School school = teacher.getSchool(); // Lấy trường của giáo viên

            // エラー格納用
            // Map chứa lỗi để hiển thị lại trên form
            Map<String, String> errors = new HashMap<>();

            // バリデーション
            // Kiểm tra tên
            if (name == null || name.isEmpty()) {
                errors.put("name", "氏名を入力してください。");
            }

            // Kiểm tra năm nhập học
            int entYear = 0;
            try {
                entYear = Integer.parseInt(entYearStr);
            } catch (Exception e) {
                errors.put("ent_year", "入学年度が不正です。");
            }

            // Checkbox → nếu có giá trị thì true
            boolean isAttend = (isAttendStr != null);

            // エラーがある場合 → 変更画面に戻す
            // Nếu có lỗi → quay lại form update
            if (!errors.isEmpty()) {

                // クラス番号一覧
                // Lấy lại danh sách class_num
                ClassNumDao cDao = new ClassNumDao();
                List<String> classNumList = cDao.filter(school);

                // 入学年度一覧
                // Tạo lại danh sách năm nhập học
                List<Integer> entYearList = new ArrayList<>();
                int year = Calendar.getInstance().get(Calendar.YEAR);
                for (int i = year - 10; i <= year + 1; i++) {
                    entYearList.add(i);
                }

                // 入力値を student に詰め直す
                // Tạo lại đối tượng Student từ dữ liệu người dùng nhập
                Student student = new Student();
                student.setNo(no);
                student.setName(name);
                student.setEntYear(entYear);
                student.setClassNum(classNum);
                student.setAttend(isAttend);
                student.setSchool(school);

                // Gửi dữ liệu sang JSP
                req.setAttribute("student", student);
                req.setAttribute("class_num_set", classNumList);
                req.setAttribute("ent_year_set", entYearList);
                req.setAttribute("errors", errors);

                // Quay lại trang update
                req.getRequestDispatcher("/scoremanager/main/student_update.jsp").forward(req, res);
                return;
            }

            // 更新処理
            // Tạo đối tượng Student hợp lệ để update DB
            Student student = new Student();
            student.setNo(no);
            student.setName(name);
            student.setEntYear(entYear);
            student.setClassNum(classNum);
            student.setAttend(isAttend);
            student.setSchool(school);

            // DB更新
            StudentDao sDao = new StudentDao();
            sDao.save(student); // save() sẽ tự động UPDATE vì student đã tồn tại
            
            req.setAttribute("message", "変更しました。");
            req.getRequestDispatcher("/scoremanager/main/student_update.jsp").forward(req, res);

            // 一覧へリダイレクト
            // Chuyển hướng về danh sách học sinh
            res.sendRedirect(req.getContextPath() + "/scoremanager/main/StudentList.action");

        } catch (Exception e) {
            // Nếu có lỗi → chuyển sang error.jsp
            e.printStackTrace();
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
        
    }
    
}

