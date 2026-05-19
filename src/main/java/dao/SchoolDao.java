package dao;
// Package chứa các lớp DAO – chuyên xử lý truy cập dữ liệu (database)
// DB アクセス処理を行う DAO クラスをまとめるパッケージ

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    
    /**
     * getAllメソッド 全ての学校を取得する
     *
     * @return List<School>
     * 全学校のリスト
     * @throws Exception
     */
    public List<School> getAll() throws Exception {

        List<School> list = new ArrayList<>();
     // Tạo danh sách để chứa các trường
        // 学校一覧を格納するリストを作成

        // DB コネクションを取得
        // Lấy kết nối đến DB
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
        	// 全学校を取得する SQL
            // Lệnh SQL lấy toàn bộ danh sách trường
            statement = connection.prepareStatement(
                "select * from school order by cd"
            );
            
            // SQL を実行して結果を取得
            // Thực thi SQL và lấy kết quả
            ResultSet rSet = statement.executeQuery();
            
            // カラム cd をセット
            // Gán mã trường (cd)
            while (rSet.next()) {
            	// 1 dòng dữ liệu → tạo đối tượng School
                // 1 レコードごとに School オブジェクトを生成
                School school = new School();
                
                // カラム cd をセット
                // Gán mã trường (cd)
                school.setCd(rSet.getString("cd"));
                
                // カラム name をセット
                // Gán tên trường (name)
                school.setName(rSet.getString("name"));
                
                // リストに追加
                // Thêm vào danh sách
                list.add(school);
            }

        } finally {
        	// PreparedStatement を閉じる
            // Đóng PreparedStatement
            if (statement != null) statement.close();
            
            // コネクションを閉じる
            // Đóng kết nối DB
            if (connection != null) connection.close();
        }
        
        // 学校一覧を返す
        // Trả về danh sách trường
        return list;
    }
    
    public void insert(School school) throws Exception {

        Connection con = null;
        PreparedStatement ps = null;

        try {
        	 // DB コネクションを取得
            // Lấy kết nối đến DB
            con = getConnection();
            
            // INSERT 文を準備
            // Chuẩn bị câu SQL thêm trường mới
            String sql = "INSERT INTO school (cd, name) VALUES (?, ?)";
            
            // ? に学校コードをバインド
            // Gán mã trường vào ?
            ps = con.prepareStatement(sql);
            
            // ? に学校コードをバインド
            // Gán mã trường vào?
            ps.setString(1, school.getCd());
            
            // ? に学校名をバインド
            // Gán tên trường vào ?
            ps.setString(2, school.getName());
            
            // SQL を実行（データ追加）
            // Thực thi SQL (thêm dữ liệu)
            ps.executeUpdate();

        } catch (Exception e) {
        	// エラーを上位に投げる
            // Ném lỗi lên tầng trên xử lý
            throw e;
        } finally {
            if (ps != null) ps.close();
            
            // コネクションを閉じる
            // Đóng kết nối DB
            if (con != null) con.close();
        }
    }

    
    

}
