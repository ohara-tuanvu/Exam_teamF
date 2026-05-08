package dao;
// Package chứa các lớp DAO (Data Access Object) – chuyên làm việc với DB
// DB 操作を行う DAO（Data Access Object）クラスをまとめるパッケージ

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;

public class ClassNumDao extends Dao {
    // Class DAO dùng để truy xuất dữ liệu bảng class_num
    // class_num テーブルへアクセスするための DAO クラス

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

        } catch (Exception e) {
            throw e;

        } finally {
            if (statement != null) {
                try { statement.close(); } catch (SQLException sqle) { throw sqle; }
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException sqle) { throw sqle; }
            }
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

        } catch (Exception e) {
            throw e;

        } finally {
            if (statement != null) {
                try { statement.close(); } catch (SQLException sqle) { throw sqle; }
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException sqle) { throw sqle; }
            }
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

        } catch (Exception e) {
            throw e;

        } finally {
            if (statement != null) {
                try { statement.close(); } catch (SQLException sqle) { throw sqle; }
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException sqle) { throw sqle; }
            }
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

        } catch (Exception e) {
            throw e;

        } finally {
            if (statement != null) {
                try { statement.close(); } catch (SQLException sqle) { throw sqle; }
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException sqle) { throw sqle; }
            }
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

        } catch (Exception e) {
            throw e;

        } finally {
            if (statement != null) {
                try { statement.close(); } catch (SQLException sqle) { throw sqle; }
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException sqle) { throw sqle; }
            }
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

        } catch (Exception e) {
            throw e;

        } finally {
            if (statement != null) {
                try { statement.close(); } catch (SQLException sqle) { throw sqle; }
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException sqle) { throw sqle; }
            }
        }
    }
}
