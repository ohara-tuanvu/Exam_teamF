package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

    // 基本SQL（schoolで絞る）
    // SQL cơ bản lọc theo school
    private String baseSql = "SELECT * FROM TEST WHERE SCHOOL_CD=?";

    // ============================
    // 1. 1件取得
    // ============================
    public Test get(Student student, Subject subject, School school, int no) throws Exception {

        Test test = null;

        Connection con = getConnection();
        PreparedStatement st = null;

        try {
            st = con.prepareStatement(
                baseSql + " AND STUDENT_NO=? AND SUBJECT_CD=? AND NO=?"
            );

            st.setString(1, school.getCd());
            st.setString(2, student.getNo());
            st.setString(3, subject.getCd());
            st.setInt(4, no);

            ResultSet rs = st.executeQuery();

            List<Test> list = postFilter(rs, school);

            if (list.size() > 0) {
                test = list.get(0);
            }

        } finally {
            if (st != null) st.close();
            if (con != null) con.close();
        }

        return test;
    }

    // ============================
    // 2. ResultSet → List<Test>
    // ============================
    private List<Test> postFilter(ResultSet rs, School school) throws Exception {

        List<Test> list = new ArrayList<>();

        StudentDao sDao = new StudentDao();
        SubjectDao subDao = new SubjectDao();

        while (rs.next()) {

            Test t = new Test();

            Student student = sDao.get(rs.getString("STUDENT_NO"), school);
            Subject subject = subDao.get(rs.getString("SUBJECT_CD"), school);

            t.setStudent(student);
            t.setSubject(subject);
            t.setSchool(school);
            t.setClassNum(rs.getString("CLASS_NUM"));
            t.setNo(rs.getInt("NO"));
            t.setPoint(rs.getInt("POINT"));

            list.add(t);
        }

        return list;
    }

    // ============================
    // 3. filter
    // ============================
    public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {

        List<Test> list = new ArrayList<>();

        Connection con = getConnection();
        PreparedStatement st = null;

        try {
            String sql =
                "SELECT t.* FROM TEST t " +
                "JOIN STUDENT s ON t.STUDENT_NO = s.NO " +
                "WHERE t.SCHOOL_CD=? AND s.ENT_YEAR=? AND t.CLASS_NUM=? AND t.SUBJECT_CD=?";

            if (num != 0) {
                sql += " AND t.NO=?";
            }

            st = con.prepareStatement(sql);

            st.setString(1, school.getCd());
            st.setInt(2, entYear);
            st.setString(3, classNum);
            st.setString(4, subject.getCd());

            if (num != 0) {
                st.setInt(5, num);
            }

            ResultSet rs = st.executeQuery();

            list = postFilter(rs, school);

        } finally {
            if (st != null) st.close();
            if (con != null) con.close();
        }

        return list;
    }

    // ============================
    // 4. save list
    // ============================
    public boolean save(List<Test> list) throws Exception {

        Connection con = getConnection();

        try {
            for (Test t : list) {
                save(t, con);
            }
        } finally {
            if (con != null) con.close();
        }

        return true;
    }

    // ============================
    // 5. save 1件
    // ============================
    public boolean save(Test test, Connection con) throws Exception {

        PreparedStatement st = null;
        int count = 0;

        try {
            // 既存チェック
            Test old = get(test.getStudent(), test.getSubject(), test.getSchool(), test.getNo());

            if (old == null) {
                // INSERT
                st = con.prepareStatement(
                    "INSERT INTO TEST (STUDENT_NO, SUBJECT_CD, SCHOOL_CD, NO, POINT, CLASS_NUM) VALUES (?, ?, ?, ?, ?, ?)"
                );

                st.setString(1, test.getStudent().getNo());
                st.setString(2, test.getSubject().getCd());
                st.setString(3, test.getSchool().getCd());
                st.setInt(4, test.getNo());
                st.setInt(5, test.getPoint());
                st.setString(6, test.getClassNum());

            } else {
                // UPDATE
                st = con.prepareStatement(
                    "UPDATE TEST SET POINT=? WHERE STUDENT_NO=? AND SUBJECT_CD=? AND SCHOOL_CD=? AND NO=?"
                );

                st.setInt(1, test.getPoint());
                st.setString(2, test.getStudent().getNo());
                st.setString(3, test.getSubject().getCd());
                st.setString(4, test.getSchool().getCd());
                st.setInt(5, test.getNo());
            }

            count = st.executeUpdate();

        } finally {
            if (st != null) st.close();
        }

        return count > 0;
    }
}