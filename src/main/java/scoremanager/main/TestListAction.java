package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SchoolDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        School school = teacher.getSchool();

        ClassNumDao classDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();
        StudentDao studentDao = new StudentDao();
        TestDao testDao = new TestDao();
        SchoolDao schoolDao = new SchoolDao();

        // ================================
        // ⭐ SUPERADMIN → load danh sách trường
        // ================================
        if (teacher.getRole() == 2) {
            req.setAttribute("schoolList", schoolDao.findAll());
        }

        // ================================
        // ⭐ Load dropdown class + subject
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

        req.setAttribute("classNumList", classNumList);
        req.setAttribute("subjectList", subjectList);
        req.setAttribute("entYearList", entYearList);

        // ================================
        // ⭐ Lấy loại tìm kiếm
        // ================================
        String searchType = req.getParameter("f");

        if (searchType == null) {
            req.getRequestDispatcher("test_list.jsp").forward(req, res);
            return;
        }

        // ================================
        // ⭐ Lấy school_cd nếu SUPERADMIN
        // ================================
        String schoolCd = req.getParameter("school_cd");
        req.setAttribute("school_cd", schoolCd);

        if (teacher.getRole() == 2) {
            if (schoolCd == null || schoolCd.isEmpty()) {
                req.setAttribute("errorSj", "学校を選択してください。");
                req.getRequestDispatcher("test_list.jsp").forward(req, res);
                return;
            }
            school = schoolDao.get(schoolCd);
        }

        // ================================
        // ⭐ 科目別検索
        // ================================
        if ("sj".equals(searchType)) {

            String f1 = req.getParameter("f1");
            String f2 = req.getParameter("f2");
            String f3 = req.getParameter("f3");

            req.setAttribute("f1", f1);
            req.setAttribute("f2", f2);
            req.setAttribute("f3", f3);
            req.setAttribute("searchType", "sj");

            List<String> missing = new ArrayList<>();
            if (f1 == null || f1.isEmpty()) missing.add("入学年度");
            if (f2 == null || f2.isEmpty()) missing.add("クラス");
            if (f3 == null || f3.isEmpty()) missing.add("科目");

            if (!missing.isEmpty()) {
                req.setAttribute("errorSj", String.join("と", missing) + "を選択してください。");
                req.getRequestDispatcher("test_list.jsp").forward(req, res);
                return;
            }

            int entYear = Integer.parseInt(f1);

            List<bean.Test> testList;

            if (teacher.getRole() == 2) {
                testList = testDao.filterBySubjectAndClassForSuperAdmin(entYear, f2, f3);
            } else {
                testList = testDao.filterBySubjectAndClass(school, entYear, f2, f3);
            }

            if (testList.isEmpty()) {
                req.setAttribute("errorSj", "学生情報が存在しませんでした");
            } else {
                req.setAttribute("testList", testList);
            }
        }

        // ================================
        // ⭐ 学生別検索
        // ================================
        else if ("st".equals(searchType)) {

            String f4 = req.getParameter("f4");
            req.setAttribute("searchType", "st");
            req.setAttribute("f4", f4);

            if (f4 == null || f4.trim().isEmpty()) {
                req.setAttribute("errorSt", "学生番号を入力してください");
                req.getRequestDispatcher("test_list.jsp").forward(req, res);
                return;
            }

            Student student;

            if (teacher.getRole() == 2) {
                student = studentDao.get(f4.trim());
            } else {
                student = studentDao.get(f4.trim(), school);
            }

            if (student == null) {
                req.setAttribute("errorSt", "学生情報が存在しませんでした");
                req.getRequestDispatcher("test_list.jsp").forward(req, res);
                return;
            }

            List<bean.Test> testList;

            if (teacher.getRole() == 2) {
                testList = testDao.filterByStudent(f4.trim());
            } else {
                testList = testDao.filterByStudent(school, f4.trim());
            }

            req.setAttribute("student", student);

            if (testList.isEmpty()) {
                req.setAttribute("errorSt", "成績情報が存在しませんでした");
            } else {
                req.setAttribute("testList", testList);
            }
        }

        req.getRequestDispatcher("test_list.jsp").forward(req, res);
    }
}
