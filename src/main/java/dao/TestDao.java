package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

    // ResultSet → Test オブジェクトに変換（Admin/Teacher 用）
    private Test mapRow(ResultSet rSet, School school) throws Exception {
        Test test = new Test();

        String studentNo = rSet.getString("student_no");
        String subjectCd = rSet.getString("subject_cd");
        int no = rSet.getInt("no");
        int point = rSet.getInt("point");
        String classNum = rSet.getString("class_num");

        // 学生情報
        StudentDao studentDao = new StudentDao();
        Student student = studentDao.get(studentNo);

        // 科目情報
        SubjectDao subjectDao = new SubjectDao();
        Subject subject = subjectDao.get(subjectCd, school);

        test.setStudent(student);
        test.setSubject(subject);
        test.setSchool(school);
        test.setNo(no);
        test.setPoint(point);
        test.setClassNum(classNum);

        return test;
    }

    // SuperAdmin 用：学校を含めて取得
    private Test mapRowForSuperAdmin(ResultSet rSet) throws Exception {
        Test test = new Test();

        String studentNo = rSet.getString("student_no");
        String subjectCd = rSet.getString("subject_cd");
        int no = rSet.getInt("no");
        int point = rSet.getInt("point");
        String classNum = rSet.getString("class_num");
        String schoolCd = rSet.getString("school_cd");

        // 学校情報
        SchoolDao schoolDao = new SchoolDao();
        School school = schoolDao.get(schoolCd);

        // 学生情報
        StudentDao studentDao = new StudentDao();
        Student student = studentDao.get(studentNo);

        // 科目情報
        SubjectDao subjectDao = new SubjectDao();
        Subject subject = subjectDao.get(subjectCd, school);

        test.setStudent(student);
        test.setSubject(subject);
        test.setSchool(school);
        test.setNo(no);
        test.setPoint(point);
        test.setClassNum(classNum);

        return test;
    }

    // 学校コードで全件取得（成績管理一覧用）
    public List<Test> filter(School school) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(
                "SELECT * FROM TEST WHERE TRIM(SCHOOL_CD) = ? ORDER BY STUDENT_NO, SUBJECT_CD, NO ASC"
            );
            statement.setString(1, school.getCd());
            ResultSet rSet = statement.executeQuery();
            while (rSet.next()) {
                list.add(mapRow(rSet, school));
            }
        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException e) { throw e; }
            if (connection != null) try { connection.close(); } catch (SQLException e) { throw e; }
        }
        return list;
    }

    // 科目コードで絞り込み（Admin/Teacher 用）
    public List<Test> filterBySubject(School school, String subjectCd) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(
                "SELECT * FROM TEST WHERE TRIM(SCHOOL_CD) = ? AND TRIM(SUBJECT_CD) = ? ORDER BY STUDENT_NO, NO ASC"
            );
            statement.setString(1, school.getCd());
            statement.setString(2, subjectCd);
            ResultSet rSet = statement.executeQuery();
            while (rSet.next()) {
                list.add(mapRow(rSet, school));
            }
        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException e) { throw e; }
            if (connection != null) try { connection.close(); } catch (SQLException e) { throw e; }
        }
        return list;
    }

    // SuperAdmin 用：科目コードで全学校から取得
    public List<Test> filterBySubject(String subjectCd) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(
                "SELECT * FROM TEST WHERE TRIM(SUBJECT_CD) = ? ORDER BY SCHOOL_CD, STUDENT_NO, NO ASC"
            );
            statement.setString(1, subjectCd.trim());
            ResultSet rSet = statement.executeQuery();
            while (rSet.next()) {
                list.add(mapRowForSuperAdmin(rSet));
            }
        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException e) { throw e; }
            if (connection != null) try { connection.close(); } catch (SQLException e) { throw e; }
        }
        return list;
    }

    // 学生番号で絞り込み（学生別成績一覧用）
    public List<Test> filterByStudent(School school, String studentNo) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            String sql = "SELECT * FROM TEST WHERE TRIM(SCHOOL_CD) = ? AND TRIM(STUDENT_NO) = ? ORDER BY SUBJECT_CD, NO ASC";
            statement = connection.prepareStatement(sql);
            statement.setString(1, school.getCd().trim());
            statement.setString(2, studentNo.trim());
            ResultSet rSet = statement.executeQuery();
            while (rSet.next()) {
                list.add(mapRow(rSet, school));
            }
        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException e) { throw e; }
            if (connection != null) try { connection.close(); } catch (SQLException e) { throw e; }
        }
        return list;
    }

    // 1件取得（変更・削除用）
    public Test get(String studentNo, String subjectCd, School school, int no) throws Exception {
        Test test = null;
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(
                "SELECT * FROM TEST WHERE STUDENT_NO = ? AND SUBJECT_CD = ? AND SCHOOL_CD = ? AND NO = ?"
            );
            statement.setString(1, studentNo);
            statement.setString(2, subjectCd);
            statement.setString(3, school.getCd());
            statement.setInt(4, no);
            ResultSet rSet = statement.executeQuery();
            if (rSet.next()) {
                test = mapRow(rSet, school);
            }
        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException e) { throw e; }
            if (connection != null) try { connection.close(); } catch (SQLException e) { throw e; }
        }
        return test;
    }

    // 新規登録
    public boolean insert(Test test) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        int count = 0;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(
                "INSERT INTO TEST (STUDENT_NO, SUBJECT_CD, SCHOOL_CD, NO, POINT, CLASS_NUM) VALUES (?, ?, ?, ?, ?, ?)"
            );
            statement.setString(1, test.getStudent().getNo());
            statement.setString(2, test.getSubject().getCd());
            statement.setString(3, test.getSchool().getCd());
            statement.setInt(4, test.getNo());
            statement.setInt(5, test.getPoint());
            statement.setString(6, test.getClassNum());
            count = statement.executeUpdate();
        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException e) { throw e; }
            if (connection != null) try { connection.close(); } catch (SQLException e) { throw e; }
        }
        return count > 0;
    }

    // 点数を更新
    public boolean update(Test test) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        int count = 0;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(
                "UPDATE TEST SET POINT = ? WHERE STUDENT_NO = ? AND SUBJECT_CD = ? AND SCHOOL_CD = ? AND NO = ?"
            );
            statement.setInt(1, test.getPoint());
            statement.setString(2, test.getStudent().getNo());
            statement.setString(3, test.getSubject().getCd());
            statement.setString(4, test.getSchool().getCd());
            statement.setInt(5, test.getNo());
            count = statement.executeUpdate();
        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException e) { throw e; }
            if (connection != null) try { connection.close(); } catch (SQLException e) { throw e; }
        }
        return count > 0;
    }

    // 削除
    public boolean delete(String studentNo, String subjectCd, School school, int no) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        int count = 0;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(
                "DELETE FROM TEST WHERE STUDENT_NO = ? AND SUBJECT_CD = ? AND SCHOOL_CD = ? AND NO = ?"
            );
            statement.setString(1, studentNo);
            statement.setString(2, subjectCd);
            statement.setString(3, school.getCd());
            statement.setInt(4, no);
            count = statement.executeUpdate();
        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException e) { throw e; }
            if (connection != null) try { connection.close(); } catch (SQLException e) { throw e; }
        }
        return count > 0;
    }

    // 入学年度+クラス+科目コードで成績一覧取得（成績登録検索用）
    public List<Test> filterBySubjectDetail(School school, int entYear, String classNum, String subjectCd, int no) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(
                "SELECT t.* FROM TEST t " +
                "INNER JOIN STUDENT s ON t.STUDENT_NO = s.NO AND t.SCHOOL_CD = s.SCHOOL_CD " +
                "WHERE t.SCHOOL_CD = ? AND s.ENT_YEAR = ? AND t.CLASS_NUM = ? AND t.SUBJECT_CD = ? AND t.NO = ? " +
                "ORDER BY t.STUDENT_NO"
            );
            statement.setString(1, school.getCd());
            statement.setInt(2, entYear);
            statement.setString(3, classNum);
            statement.setString(4, subjectCd);
            statement.setInt(5, no);
            ResultSet rSet = statement.executeQuery();
            while (rSet.next()) list.add(mapRow(rSet, school));
        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException e) { throw e; }
            if (connection != null) try { connection.close(); } catch (SQLException e) { throw e; }
        }
        return list;
    }

    // 入学年度+クラス+科目コードで成績一覧取得（成績参照科目別用）
    public List<Test> filterBySubjectAndClass(School school, int entYear, String classNum, String subjectCd) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(
                "SELECT t.* FROM TEST t " +
                "INNER JOIN STUDENT s ON t.STUDENT_NO = s.NO AND t.SCHOOL_CD = s.SCHOOL_CD " +
                "WHERE t.SCHOOL_CD = ? AND s.ENT_YEAR = ? AND t.CLASS_NUM = ? AND t.SUBJECT_CD = ? " +
                "ORDER BY t.STUDENT_NO, t.NO"
            );
            statement.setString(1, school.getCd());
            statement.setInt(2, entYear);
            statement.setString(3, classNum);
            statement.setString(4, subjectCd);
            ResultSet rSet = statement.executeQuery();
            while (rSet.next()) list.add(mapRow(rSet, school));
        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException e) { throw e; }
            if (connection != null) try { connection.close(); } catch (SQLException e) { throw e; }
        }
        return list;
    }
    
 // SuperAdmin 用：学生番号で全学校から成績取得
    public List<Test> filterByStudent(String studentNo) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(
                "SELECT * FROM TEST WHERE TRIM(STUDENT_NO) = ? ORDER BY SCHOOL_CD, SUBJECT_CD, NO ASC"
            );
            statement.setString(1, studentNo.trim());
            ResultSet rSet = statement.executeQuery();
            while (rSet.next()) {
                list.add(mapRowForSuperAdmin(rSet));
            }
        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException e) { throw e; }
            if (connection != null) try { connection.close(); } catch (SQLException e) { throw e; }
        }
        return list;
    }
    
    public Test getForSuperAdmin(String studentNo, String subjectCd, int no) throws Exception {
        Test test = null;
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(
                "SELECT * FROM TEST WHERE STUDENT_NO = ? AND SUBJECT_CD = ? AND NO = ?"
            );
            statement.setString(1, studentNo);
            statement.setString(2, subjectCd);
            statement.setInt(3, no);

            ResultSet rSet = statement.executeQuery();
            if (rSet.next()) {
                test = mapRowForSuperAdmin(rSet);
            }
        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException e) { throw e; }
            if (connection != null) try { connection.close(); } catch (SQLException e) { throw e; }
        }
        return test;
    }
    
 // SuperAdmin 用：学校を限定せず削除
    public boolean deleteForSuperAdmin(String studentNo, String subjectCd, int no) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        int count = 0;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(
                "DELETE FROM TEST WHERE STUDENT_NO = ? AND SUBJECT_CD = ? AND NO = ?"
            );
            statement.setString(1, studentNo);
            statement.setString(2, subjectCd);
            statement.setInt(3, no);

            count = statement.executeUpdate();
        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException e) { throw e; }
            if (connection != null) try { connection.close(); } catch (SQLException e) { throw e; }
        }

        return count > 0;
    }
    
 // SuperAdmin 用：入学年度 + クラス + 科目コードで全学校から成績取得
    public List<Test> filterBySubjectAndClassForSuperAdmin(int entYear, String classNum, String subjectCd) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(
                "SELECT t.* FROM TEST t " +
                "INNER JOIN STUDENT s ON t.STUDENT_NO = s.NO AND t.SCHOOL_CD = s.SCHOOL_CD " +
                "WHERE s.ENT_YEAR = ? AND t.CLASS_NUM = ? AND t.SUBJECT_CD = ? " +
                "ORDER BY t.SCHOOL_CD, t.STUDENT_NO, t.NO"
            );

            statement.setInt(1, entYear);
            statement.setString(2, classNum);
            statement.setString(3, subjectCd);

            ResultSet rSet = statement.executeQuery();
            while (rSet.next()) {
                list.add(mapRowForSuperAdmin(rSet));
            }

        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException e) { throw e; }
            if (connection != null) try { connection.close(); } catch (SQLException e) { throw e; }
        }

        return list;
    }
    
 // SuperAdmin 用：全学校のクラス番号一覧取得
    public List<String> findAllClassNum() throws Exception {
        List<String> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(
                "SELECT DISTINCT CLASS_NUM FROM STUDENT ORDER BY CLASS_NUM"
            );

            ResultSet rSet = statement.executeQuery();
            while (rSet.next()) {
                list.add(rSet.getString("CLASS_NUM"));
            }

        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException e) { throw e; }
            if (connection != null) try { connection.close(); } catch (SQLException e) { throw e; }
        }

        return list;
    }


    
}
