package dao;
// Package chứa các lớp DAO – chuyên xử lý truy cập dữ liệu (database)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.School;

public class SchoolDao extends Dao {
    /**
     * getメソッド 学校コードを指定して学校インスタンスを1件取得する
     *
     * @param cd String
     * 学校コード
     * // Mã trường cần tìm
     * @return 学校クラスのインスタンス 存在しない場合はnull
     * // Trả về đối tượng School, nếu không có thì trả về null
     * @throws Exception
     */
    public School get(String cd) throws Exception {
        // 学校インスタンスを初期化
        // Tạo đối tượng School để chứa kết quả
        School school = new School();

        // データベースのコネクションを確保
        // Lấy kết nối đến database
        Connection connection = getConnection();

        // プリペアドステートメント
        // Chuẩn bị đối tượng PreparedStatement để chạy SQL
        PreparedStatement statement = null;

        try {
            // プリペアードステートメントにSQLをセット
            // Chuẩn bị câu SQL tìm trường theo cd
            statement = connection.prepareStatement("select * from school where cd = ?");

            // プリペアードステートメントに学校コードをバインド
            // Gán giá trị cd vào dấu ?
            statement.setString(1, cd);

            // プリペアードステートメントを実行
            // Thực thi SQL và lấy kết quả
            ResultSet rSet = statement.executeQuery();

            if (rSet.next()) {
                // リザルトセットが存在する場合
                // Nếu có dữ liệu trả về

                // 学校コードと学校名をセット
                // Gán mã trường và tên trường vào đối tượng school
                school.setCd(rSet.getString("cd"));
                school.setName(rSet.getString("name"));
            } else {
                // 存在しない場合はnullをセット
                // Nếu không tìm thấy → trả về null
                school = null;
            }
        } catch (Exception e) {
            // Ném lỗi ra ngoài để xử lý ở tầng trên
            throw e;
        } finally {
            // プリペアードステートメントを閉じる
            // Đóng PreparedStatement
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }

            // コネクションを閉じる
            // Đóng kết nối DB
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }

        // Trả về đối tượng School (hoặc null)
        return school;
    }
}
