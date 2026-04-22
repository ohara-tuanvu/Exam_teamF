package dao;
// Package chứa các lớp DAO – chuyên xử lý truy cập dữ liệu (database)

import java.sql.Connection;
// Lớp Connection dùng để tạo kết nối tới database

import javax.naming.InitialContext;
// InitialContext dùng để tra cứu tài nguyên (JNDI lookup)
import javax.sql.DataSource;
// DataSource đại diện cho nguồn kết nối (connection pool)

public class Dao {
    /**
     * データソース:DataSource:クラスフィールド
     * // DataSource dùng chung cho tất cả DAO (biến static)
     * // Lưu connection pool để tái sử dụng
     */
    static DataSource ds;

    /**
     * getConnectionメソッド データベースへのコネクションを返す
     *
     * @return データベースへのコネクション:Connection
     * // Trả về một kết nối đến database
     * @throws Exception
     */
    public Connection getConnection() throws Exception {
        // データソースがnullの場合
        // Nếu DataSource chưa được khởi tạo
        if (ds == null) {
            // InitialContextを初期化
            // Tạo InitialContext để lookup tài nguyên JNDI
            InitialContext ic = new InitialContext();

            // データベースへ接続
            // Lookup DataSource đã cấu hình trong context.xml
            ds = (DataSource) ic.lookup("java:/comp/env/jdbc/exam");
        }

        // データベースへのコネクションを返却
        // Trả về một Connection từ DataSource
        return ds.getConnection();
    }
}
