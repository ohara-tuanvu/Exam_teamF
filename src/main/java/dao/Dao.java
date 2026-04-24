package dao;
// Package chứa các lớp DAO – chuyên xử lý truy cập dữ liệu (database)
// DB アクセス処理を行う DAO クラスをまとめるパッケージ

import java.sql.Connection;
// Lớp Connection dùng để tạo kết nối tới database
// DB への接続を表す Connection クラス

import javax.naming.InitialContext;
// InitialContext dùng để tra cứu tài nguyên (JNDI lookup)
// JNDI リソースを検索するための InitialContext
import javax.sql.DataSource;
// DataSource đại diện cho nguồn kết nối (connection pool)
// コネクションプールを表す DataSource

public class Dao {

    /**
     * データソース:DataSource:クラスフィールド
     * // DataSource dùng chung cho tất cả DAO (biến static)
     * // Lưu connection pool để tái sử dụng
     * // 全 DAO で共有する DataSource（static フィールド）
     * // コネクションプールを保持し再利用する
     */
    static DataSource ds;

    /**
     * getConnectionメソッド データベースへのコネクションを返す
     *
     * @return データベースへのコネクション:Connection
     * // Trả về một kết nối đến database
     * // DB への Connection を返す
     * @throws Exception
     */
    public Connection getConnection() throws Exception {

        // データソースがnullの場合
        // Nếu DataSource chưa được khởi tạo
        // DataSource が未初期化の場合
        if (ds == null) {

            // InitialContextを初期化
            // Tạo InitialContext để lookup tài nguyên JNDI
            // JNDI リソース検索のため InitialContext を生成
            InitialContext ic = new InitialContext();

            // データベースへ接続
            // Lookup DataSource đã cấu hình trong context.xml
            // context.xml に設定された DataSource を検索
            ds = (DataSource) ic.lookup("java:/comp/env/jdbc/exam");
        }

        // データベースへのコネクションを返却
        // Trả về một Connection từ DataSource
        // DataSource から Connection を取得して返す
        return ds.getConnection();
    }
}
