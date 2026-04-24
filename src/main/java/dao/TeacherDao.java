package dao;
// Package chứa các lớp DAO – chuyên xử lý truy cập dữ liệu (database)
// データベースアクセスを行うDAOクラスをまとめるパッケージ

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
        // Chuẩn bị câu lệnh SQL
        PreparedStatement statement = null;

        try {
            // SQL lấy giáo viên theo id
            // ID を条件に教員情報を取得するSQL
            statement = connection.prepareStatement("select * from teacher where id=?");

            // Gán id vào ?
            // プレースホルダーにIDをセット
            statement.setString(1, id);

            // Thực thi SQL
            // SQL を実行して結果を取得
            ResultSet resultSet = statement.executeQuery();

            // Tạo SchoolDao để lấy thông tin trường
            // 学校情報取得のために SchoolDao を生成
            SchoolDao schoolDao = new SchoolDao();

            if (resultSet.next()) {
                // Nếu có dữ liệu → gán vào teacher
                // データが存在する場合 → Teacher に値をセット
                teacher.setId(resultSet.getString("id"));
                teacher.setPassword(resultSet.getString("password"));
                teacher.setName(resultSet.getString("name"));

                // Lấy trường theo school_cd
                // school_cd を使って学校情報を取得
                teacher.setSchool(schoolDao.get(resultSet.getString("school_cd")));
            } else {
                // Không có dữ liệu → trả về null
                // データがない場合 → null を返す
                teacher = null;
            }
        } catch (Exception e) {
            // Ném lỗi ra ngoài để Action xử lý
            // 例外は上位層に投げる
            throw e;
        } finally {
            // Đóng PreparedStatement
            // PreparedStatement をクローズ
            if (statement != null) {
                try { statement.close(); } catch (SQLException sqle) { throw sqle; }
            }

            // Đóng Connection
            // コネクションをクローズ
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
        // ID を使って教員情報を取得
        Teacher teacher = get(id);

        // Nếu không tồn tại hoặc password sai → trả về null
        // 教員が存在しない、またはパスワード不一致 → null を返す
        if (teacher == null || !teacher.getPassword().equals(password)) {
            return null;
        }

        // Đăng nhập thành công → trả về đối tượng Teacher
        // 認証成功 → Teacher オブジェクトを返す
        return teacher;
    }
}
