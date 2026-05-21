package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;

public class ClassNumDao extends Dao {

    // ============================================================
    // 1) 取得（1件）get()
    // ============================================================
    public ClassNum get(String class_num, School school) throws Exception {
        ClassNum classNum = new ClassNum();

        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "select * from class_num where class_num = ? and school_cd = ?"
            );

            statement.setString(1, class_num);
            statement.setString(2, school.getCd());

            ResultSet rSet = statement.executeQuery();

            SchoolDao sDao = new SchoolDao();

            if (rSet.next()) {
                classNum.setClass_num(rSet.getString("class_num"));
                classNum.setSchool(sDao.get(rSet.getString("school_cd")));
            } else {
                classNum = null;
            }

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return classNum;
    }

    // ============================================================
    // 2) クラス番号一覧取得（String）filter()
    // ============================================================
    public List<String> filter(School school) throws Exception {
        List<String> list = new ArrayList<>();

        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "select class_num from class_num where school_cd=? order by class_num"
            );

            statement.setString(1, school.getCd());

            ResultSet rSet = statement.executeQuery();

            while (rSet.next()) {
                list.add(rSet.getString("class_num"));
            }

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }

    // ============================================================
    // 3) クラス一覧取得（ClassNum）findBySchool()
    // ============================================================
    public List<ClassNum> findBySchool(School school) throws Exception {

        List<ClassNum> list = new ArrayList<>();

        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "select * from class_num where school_cd=? order by class_num"
            );

            statement.setString(1, school.getCd());

            ResultSet rSet = statement.executeQuery();

            SchoolDao sDao = new SchoolDao();

            while (rSet.next()) {
                ClassNum cn = new ClassNum();
                cn.setClass_num(rSet.getString("class_num"));
                cn.setSchool(sDao.get(rSet.getString("school_cd")));
                list.add(cn);
            }

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }

    // ============================================================
    // ⭐ SuperAdmin 用：全クラス取得 findAll()
    // ============================================================
    public List<ClassNum> findAll() throws Exception {

        List<ClassNum> list = new ArrayList<>();

        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "select * from class_num order by school_cd, class_num"
            );

            ResultSet rSet = statement.executeQuery();

            SchoolDao sDao = new SchoolDao();

            while (rSet.next()) {
                ClassNum cn = new ClassNum();
                cn.setClass_num(rSet.getString("class_num"));
                cn.setSchool(sDao.get(rSet.getString("school_cd")));
                list.add(cn);
            }

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }

    // ============================================================
    // 4) 新規登録 insert()
    // ============================================================
    public boolean insert(ClassNum classNum) throws Exception {

        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "insert into class_num (class_num, school_cd) values (?, ?)"
            );

            statement.setString(1, classNum.getClass_num());
            statement.setString(2, classNum.getSchool().getCd());

            int result = statement.executeUpdate();

            return result > 0;

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    // ============================================================
    // 5) 更新 update()
    // ============================================================
    public boolean update(ClassNum classNum, String oldClassNum) throws Exception {

        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "update class_num set class_num=? where school_cd=? and class_num=?"
            );

            statement.setString(1, classNum.getClass_num());
            statement.setString(2, classNum.getSchool().getCd());
            statement.setString(3, oldClassNum);

            int result = statement.executeUpdate();

            return result > 0;

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    // ============================================================
    // 6) 削除 delete()
    // ============================================================
    public boolean delete(String classNum, School school) throws Exception {

        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "delete from class_num where class_num=? and school_cd=?"
            );

            statement.setString(1, classNum);
            statement.setString(2, school.getCd());

            int result = statement.executeUpdate();

            return result > 0;

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }
    
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
