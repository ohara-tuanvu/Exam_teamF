package dao;
// Package chứa các lớp DAO – chuyên xử lý truy cập dữ liệu (database)
// DB アクセス処理を行う DAO クラスをまとめるパッケージ

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
     * // 取得したい学校コード
     * @return 学校クラスのインスタンス 存在しない場合はnull
     * // Trả về đối tượng School, nếu không có thì trả về null
     * // School オブジェクトを返す。存在しない場合は null
     * @throws Exception
     */
    public School get(String cd) throws Exception {

        // 学校インスタンスを初期化
        // Tạo đối tượng School để chứa kết quả
        // 結果を格納するための School インスタンスを生成
        School school = new School();

        // データベースのコネクションを確保
        // Lấy kết nối đến database
        // DB コネクションを取得
        Connection connection = getConnection();

        // プリペアドステートメント
        // Chuẩn bị đối tượng PreparedStatement để chạy SQL
        // SQL 実行用の PreparedStatement を準備
        PreparedStatement statement = null;

        try {
            // プリペアードステートメントにSQLをセット
            // Chuẩn bị câu SQL tìm trường theo cd
            // 学校コードで検索する SQL をセット
            statement = connection.prepareStatement(
                "select * from school where cd = ?"
            );

            // プリペアードステートメントに学校コードをバインド
            // Gán giá trị cd vào dấu ?
            // SQL の ? に学校コードをバインド
            statement.setString(1, cd);

            // プリペアードステートメントを実行
            // Thực thi SQL và lấy kết quả
            // SQL を実行し、結果セットを取得
            ResultSet rSet = statement.executeQuery();

            if (rSet.next()) {
                // リザルトセットが存在する場合
                // Nếu có dữ liệu trả về
                // データが存在する場合

                // 学校コードと学校名をセット
                // Gán mã trường và tên trường vào đối tượng school
                // 取得した学校コードと学校名を school にセット
                school.setCd(rSet.getString("cd"));
                school.setName(rSet.getString("name"));
            } else {
                // 存在しない場合はnullをセット
                // Nếu không tìm thấy → trả về null
                // データが存在しない場合 → null を返す
                school = null;
            }
        } catch (Exception e) {
            // Ném lỗi ra ngoài để xử lý ở tầng trên
            // 上位層で処理するために例外をスロー
            throw e;
        } finally {
            // プリペアードステートメントを閉じる
            // Đóng PreparedStatement
            // PreparedStatement をクローズ
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }

            // コネクションを閉じる
            // Đóng kết nối DB
            // DB コネクションをクローズ
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }

        // Trả về đối tượng School (hoặc null)
        // School オブジェクト（または null）を返す
        return school;
    }
}
