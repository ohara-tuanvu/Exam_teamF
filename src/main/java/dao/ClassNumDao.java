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
        // Tạo đối tượng ClassNum để chứa kết quả
        // 結果を格納するための ClassNum インスタンスを生成

        Connection connection = getConnection();
        // Lấy kết nối DB
        // DB コネクションを取得

        PreparedStatement statement = null;
        // Chuẩn bị đối tượng thực thi SQL
        // SQL 実行用の PreparedStatement

        try {
            statement = connection.prepareStatement(
                "select * from class_num where class_num = ? and school_cd = ?"
            );
            // Chuẩn bị SQL có điều kiện class_num + school_cd
            // class_num と school_cd を条件にした SQL を準備

            statement.setString(1, class_num);
            // Gán giá trị class_num vào ?
            // ? に class_num をセット

            statement.setString(2, school.getCd());
            // Gán school_cd vào ?
            // ? に school_cd をセット

            ResultSet rSet = statement.executeQuery();
            // Thực thi SQL và lấy kết quả
            // SQL を実行し、結果を取得

            SchoolDao sDao = new SchoolDao();
            // Tạo SchoolDao để lấy thông tin trường
            // 学校情報取得のため SchoolDao を生成

            if (rSet.next()) {
                // Nếu có dữ liệu trả về
                // データが存在する場合

                classNum.setClass_num(rSet.getString("class_num"));
                // Gán class_num
                // class_num をセット

                classNum.setSchool(sDao.get(rSet.getString("school_cd")));
                // Lấy School từ school_cd và gán vào
                // school_cd から School を取得してセット

            } else {
                classNum = null;
                // Không có dữ liệu → trả về null
                // データなし → null を返す
            }

        } catch (Exception e) {
            throw e;
            // Ném lỗi lên tầng trên
            // 例外を上位へスロー

        } finally {
            if (statement != null) {
                try { statement.close(); } catch (SQLException sqle) { throw sqle; }
                // Đóng PreparedStatement
                // PreparedStatement をクローズ
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException sqle) { throw sqle; }
                // Đóng kết nối DB
                // DB コネクションをクローズ
            }
        }

        return classNum;
        // Trả về kết quả
        // 結果を返す
    }

    // ============================================================
    // 2) クラス番号一覧取得（String）filter()
    // ============================================================
    public List<String> filter(School school) throws Exception {
        List<String> list = new ArrayList<>();
        // Tạo list để chứa class_num
        // class_num を格納するリストを生成

        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "select class_num from class_num where school_cd=? order by class_num"
            );
            // Lấy danh sách class_num theo school_cd
            // school_cd に紐づく class_num 一覧を取得

            statement.setString(1, school.getCd());
            // Gán school_cd
            // school_cd をセット

            ResultSet rSet = statement.executeQuery();
            // Thực thi SQL
            // SQL を実行

            while (rSet.next()) {
                list.add(rSet.getString("class_num"));
                // Thêm class_num vào list
                // class_num をリストに追加
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
        // Trả về danh sách class_num
        // class_num のリストを返す
    }

    // ============================================================
    // 3) クラス一覧取得（ClassNum）findBySchool()
    // ============================================================
    public List<ClassNum> findBySchool(School school) throws Exception {

        List<ClassNum> list = new ArrayList<>();
        // List chứa các đối tượng ClassNum
        // ClassNum オブジェクトのリスト

        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "select * from class_num where school_cd=? order by class_num"
            );
            // Lấy toàn bộ class_num theo school_cd
            // school_cd に紐づく class_num 全件取得

            statement.setString(1, school.getCd());

            ResultSet rSet = statement.executeQuery();

            SchoolDao sDao = new SchoolDao();

            while (rSet.next()) {
                ClassNum cn = new ClassNum();
                // Tạo đối tượng ClassNum mới
                // 新しい ClassNum オブジェクトを生成

                cn.setClass_num(rSet.getString("class_num"));
                // Gán class_num
                // class_num をセット

                cn.setSchool(sDao.get(rSet.getString("school_cd")));
                // Gán School
                // school_cd から School を取得してセット

                list.add(cn);
                // Thêm vào list
                // リストに追加
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
        // Trả về danh sách ClassNum
        // ClassNum のリストを返す
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
            // SQL thêm mới class_num
            // class_num を新規登録する SQL

            statement.setString(1, classNum.getClass_num());
            statement.setString(2, classNum.getSchool().getCd());

            int result = statement.executeUpdate();
            // Thực thi insert
            // insert を実行

            return result > 0;
            // Trả về true nếu thêm thành công
            // 成功したら true を返す

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
            // SQL cập nhật class_num
            // class_num を更新する SQL

            statement.setString(1, classNum.getClass_num());
            statement.setString(2, classNum.getSchool().getCd());
            statement.setString(3, oldClassNum);

            int result = statement.executeUpdate();
            // Thực thi update
            // update を実行

            return result > 0;
            // Trả về true nếu cập nhật thành công
            // 成功したら true を返す

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
            // SQL xóa class_num
            // class_num を削除する SQL

            statement.setString(1, classNum);
            statement.setString(2, school.getCd());

            int result = statement.executeUpdate();
            // Thực thi delete
            // delete を実行

            return result > 0;
            // Trả về true nếu xóa thành công
            // 成功したら true を返す

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
