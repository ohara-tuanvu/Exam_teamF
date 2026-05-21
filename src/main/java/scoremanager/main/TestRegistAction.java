package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.SchoolDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        SchoolDao schoolDao = new SchoolDao();
        ClassNumDao classDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();
        StudentDao studentDao = new StudentDao();
        TestDao testDao = new TestDao();

        School school = teacher.getSchool();

        // ================================
        // ⭐ SUPERADMIN → load danh sách trường
        // ================================
        if (teacher.getRole() == 2) {
            req.setAttribute("schoolList", schoolDao.findAll());
        }

        // ================================
        // ⭐ Load dropdown (phân quyền)
        // ================================
        List<String> classNumList;
        List<Subject> subjectList;

        if (teacher.getRole() == 2) {
            classNumList = classDao.findAllClassNum();
            subjectList = subjectDao.findAll();
        } else {
            classNumList = classDao.filter(school);
            subjectList = subjectDao.filter(school);
        }

        // 入学年度
        int currentYear = LocalDate.now().getYear();
        List<Integer> entYearList = new ArrayList<>();
        for (int i = currentYear; i >= currentYear - 10; i--) entYearList.add(i);

        // 回数
        List<Integer> noList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) noList.add(i);

        req.setAttribute("classNumList", classNumList);
        req.setAttribute("subjectList", subjectList);
        req.setAttribute("entYearList", entYearList);
        req.setAttribute("noList", noList);

        // ================================
        // ⭐ Lấy điều kiện tìm kiếm
        // ================================
        String f1 = req.getParameter("f1");
        String f2 = req.getParameter("f2");
        String f3 = req.getParameter("f3");
        String f4 = req.getParameter("f4");
        String schoolCd = req.getParameter("school_cd");

        req.setAttribute("f1", f1);
        req.setAttribute("f2", f2);
        req.setAttribute("f3", f3);
        req.setAttribute("f4", f4);
        req.setAttribute("school_cd", schoolCd);

        // 初期表示
        if (f1 == null && f2 == null && f3 == null && f4 == null && schoolCd == null) {
            req.getRequestDispatcher("test_regist.jsp").forward(req, res);
            return;
        }

        // ================================
        // ⭐ SUPERADMIN → lấy trường từ school_cd
        // ================================
        if (teacher.getRole() == 2) {
            if (schoolCd == null || schoolCd.isEmpty()) {
                req.setAttribute("errors", List.of("学校を選択してください。"));
                req.getRequestDispatcher("test_regist.jsp").forward(req, res);
                return;
            }
            school = schoolDao.get(schoolCd);
        }

        // ================================
        // ⭐ Kiểm tra input
        // ================================
        List<String> missing = new ArrayList<>();
        if (f1 == null || f1.isEmpty()) missing.add("入学年度");
        if (f2 == null || f2.isEmpty()) missing.add("クラス");
        if (f3 == null || f3.isEmpty()) missing.add("科目");
        if (f4 == null || f4.isEmpty()) missing.add("回数");

        if (!missing.isEmpty()) {
            req.setAttribute("errors",
                List.of(String.join("と", missing) + "を選択してください。"));
            req.getRequestDispatcher("test_regist.jsp").forward(req, res);
            return;
        }

        int entYear = Integer.parseInt(f1);
        int no = Integer.parseInt(f4);

        // ================================
        // ⭐ Lấy danh sách học sinh (dựa đúng TestDao)
        // ================================
        List<Student> studentList;

        if (teacher.getRole() == 2) {
            // SUPERADMIN → lấy tất cả học sinh rồi lọc theo trường + entYear + classNum
            List<Student> all = studentDao.findAll();
            studentList = new ArrayList<>();

            for (Student s : all) {
                if (s.getSchool().getCd().equals(schoolCd)
                    && s.getEntYear() == entYear
                    && s.getClassNum().equals(f2)) {
                    studentList.add(s);
                }
            }

        } else {
            // Admin/Teacher
            studentList = studentDao.filter(school, entYear, f2, true);
        }

        if (studentList.isEmpty()) {
            req.setAttribute("searchError", "該当する学生情報が存在しませんでした");
        } else {

            req.setAttribute("studentList", studentList);

            // ================================
            // ⭐ Lấy điểm theo từng học sinh
            // ================================
            java.util.Map<String, Integer> pointMap = new java.util.HashMap<>();

            for (Student s : studentList) {

                Test existing;

                if (teacher.getRole() == 2) {
                    existing = testDao.getForSuperAdmin(s.getNo(), f3, no);
                } else {
                    existing = testDao.get(s.getNo(), f3, school, no);
                }

                if (existing != null) {
                    pointMap.put(s.getNo(), existing.getPoint());
                }
            }

            req.setAttribute("pointMap", pointMap);
        }

        req.getRequestDispatcher("test_regist.jsp").forward(req, res);
    }
}
