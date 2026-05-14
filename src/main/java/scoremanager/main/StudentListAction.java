package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentListAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ===== Lấy tham số lọc =====
        String entYearStr = request.getParameter("f1");  // 入学年度
        String classNum = request.getParameter("f2");    // クラス番号
        String isAttendStr = request.getParameter("f3"); // 在学フラグ

        int entYear = 0;
        boolean isAttend = (isAttendStr != null);

        if (entYearStr != null && !entYearStr.equals("")) {
            entYear = Integer.parseInt(entYearStr);
        }

        // ⭐ FIX QUAN TRỌNG — xử lý f2 = "" thành "0"
        if (classNum == null || classNum.equals("")) {
            classNum = "0";
        }

        // ===== Tạo danh sách năm nhập học =====
        LocalDate todaysDate = LocalDate.now();
        int year = todaysDate.getYear();

        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year; i++) {
            entYearSet.add(i);
        }

        // ===== DAO =====
        StudentDao sDao = new StudentDao();
        ClassNumDao cNumDao = new ClassNumDao();

        List<String> classNumList = cNumDao.filter(teacher.getSchool());

        Map<String, String> errors = new HashMap<>();
        List<Student> students = null;

        // ================================
        // ⭐ Điều kiện lọc 
        // ================================

        // ① Không nhập gì → lấy toàn bộ
        if (entYear == 0 && classNum.equals("0")) {
            students = sDao.filter(teacher.getSchool(), isAttend);
        }

        // ② Chỉ nhập 入学年度 → lọc theo năm
        else if (entYear != 0 && classNum.equals("0")) {
            students = sDao.filter(teacher.getSchool(), entYear, isAttend);
        }

        // ③ Chỉ nhập クラス → báo lỗi
        else if (entYear == 0 && !classNum.equals("0")) {
            errors.put("f1", "クラス指定する場合は入学年度も指定してください");
            request.setAttribute("errors", errors);
            students = new ArrayList<>();
        }

        // ④ Nhập cả năm + lớp → lọc theo cả 2
        else {
            students = sDao.filter(teacher.getSchool(), entYear, classNum, isAttend);
        }

        // ===== Gửi dữ liệu sang JSP =====
        request.setAttribute("f1", entYear);
        request.setAttribute("f2", classNum);
        request.setAttribute("f3", isAttendStr);

        request.setAttribute("students", students);
        request.setAttribute("class_num_set", classNumList);
        request.setAttribute("ent_year_set", entYearSet);

        request.getRequestDispatcher("student_list.jsp").forward(request, response);
    }
}
