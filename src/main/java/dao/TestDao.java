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

    // ResultSet → Test オブジェクトに変換する共通処理
    private Test mapRow(ResultSet rSet, School school) throws Exception {
        Test test = new Test();

        // 学生情報を取得
        StudentDao studentDao = new StudentDao();
        Student student = studentDao.get(rSet.getString("student_no"), school);
        test.setStudent(student);

        // 科目情報を取得
        SubjectDao subjectDao = new SubjectDao();
        Subject subject = subjectDao.get(rSet.getString("subject_cd"), school);
        test.setSubject(subject);

        test.setSchool(school);
        test.setNo(rSet.getInt("no"));
        test.setPoint(rSet.getInt("point"));
        test.setClassNum(rSet.getString("class_num"));

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
                "SELECT * FROM TEST WHERE SCHOOL_CD = ? ORDER BY STUDENT_NO, SUBJECT_CD, NO ASC"
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

    // 科目コードで絞り込み（科目別成績一覧用）
    public List<Test> filterBySubject(School school, String subjectCd) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(
                "SELECT * FROM TEST WHERE SCHOOL_CD = ? AND SUBJECT_CD = ? ORDER BY STUDENT_NO, NO ASC"
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

    // 学生番号で絞り込み（学生別成績一覧用）
    public List<Test> filterByStudent(School school, String studentNo) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(
                "SELECT * FROM TEST WHERE SCHOOL_CD = ? AND STUDENT_NO = ? ORDER BY SUBJECT_CD, NO ASC"
            );
            statement.setString(1, school.getCd());
            statement.setString(2, studentNo);
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

    // 入学年度+クラス+科目コードで成績一覧取得（成績参照科目別用 - 1回と2回をまとめて）
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
}