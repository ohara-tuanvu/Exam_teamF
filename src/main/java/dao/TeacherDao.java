package dao;
// Package chứa các lớp DAO – chuyên xử lý truy cập dữ liệu (database)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Teacher;

public class TeacherDao extends Dao {

    /**
     * getメソッド 教員IDを指定して教員インスタンスを1件取得する
     * // Lấy thông tin 1 giáo viên theo ID
     */
    public Teacher get(String id) throws Exception {
        // 教員インスタンスを初期化
        // Tạo đối tượng Teacher để chứa kết quả
        Teacher teacher = new Teacher();

        // コネクションを確立
        // Lấy kết nối DB
        Connection connection = getConnection();

        // プリペアードステートメント
        PreparedStatement statement = null;

        try {
            // SQL lấy giáo viên theo id
            statement = connection.prepareStatement("select * from teacher where id=?");

            // Gán id vào ?
            statement.setString(1, id);

            // Thực thi SQL
            ResultSet resultSet = statement.executeQuery();

            // Tạo SchoolDao để lấy thông tin trường
            SchoolDao schoolDao = new SchoolDao();

            if (resultSet.next()) {
                // Nếu có dữ liệu → gán vào teacher
                teacher.setId(resultSet.getString("id"));
                teacher.setPassword(resultSet.getString("password"));
                teacher.setName(resultSet.getString("name"));

                // Lấy trường theo school_cd
                teacher.setSchool(schoolDao.get(resultSet.getString("school_cd")));
            } else {
                // Không có dữ liệu → trả về null
                teacher = null;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            // Đóng PreparedStatement
            if (statement != null) {
                try { statement.close(); } catch (SQLException sqle) { throw sqle; }
            }

            // Đóng Connection
            if (connection != null) {
                try { connection.close(); } catch (SQLException sqle) { throw sqle; }
            }
        }

        return teacher;
    }

    /**
     * loginメソッド 教員IDとパスワードで認証する
     * // Đăng nhập bằng id + password
     */
    public Teacher login(String id, String password) throws Exception {
        // Lấy thông tin giáo viên theo id
        Teacher teacher = get(id);

        // Nếu không tồn tại hoặc password sai → trả về null
        if (teacher == null || !teacher.getPassword().equals(password)) {
            return null;
        }

        // Đăng nhập thành công → trả về đối tượng Teacher
        return teacher;
    }
}
