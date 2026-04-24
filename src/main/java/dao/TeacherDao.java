package dao;

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
        Teacher teacher = new Teacher();

        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("select * from teacher where id=?");
            statement.setString(1, id);

            ResultSet resultSet = statement.executeQuery();

            SchoolDao schoolDao = new SchoolDao();

            if (resultSet.next()) {

                teacher.setId(resultSet.getString("id"));
                teacher.setPassword(resultSet.getString("password"));
                teacher.setName(resultSet.getString("name"));

                // ★★★ 追加：school_cd を Teacher にセット ★★★
                teacher.setSchoolCd(resultSet.getString("school_cd"));
                // ★★★ ここまで追加 ★★★

                teacher.setSchool(schoolDao.get(resultSet.getString("school_cd")));

            } else {
                teacher = null;
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

        return teacher;
    }

    /**
     * loginメソッド 教員IDとパスワードで認証する
     * // Đăng nhập bằng id + password
     */
    public Teacher login(String id, String password) throws Exception {
        Teacher teacher = get(id);

        if (teacher == null || !teacher.getPassword().equals(password)) {
            return null;
        }

        return teacher;
    }
}
