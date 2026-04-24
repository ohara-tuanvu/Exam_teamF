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

    public ClassNum get(String class_num, School school) throws Exception {
        ClassNum classNum = new ClassNum();
        // Tạo đối tượng ClassNum để chứa kết quả trả về
        // 取得結果を格納するための ClassNum インスタンスを生成

        // データベースへのコネクションを確立
        // Lấy kết nối đến database
        // DB コネクションを取得
        Connection connection = getConnection();

        // プリペアードステートメント
        // Chuẩn bị đối tượng PreparedStatement để chạy SQL
        // SQL 実行用の PreparedStatement を準備
        PreparedStatement statement = null;

        try {
            // プリペアードステートメントにSQL文をセット
            // Chuẩn bị câu SQL có điều kiện class_num và school_cd
            // class_num と school_cd を条件にした SQL をセット
            statement = connection.prepareStatement(
                "select * from class_num where class_num = ? and school_cd = ?"
            );

            // プリペアードステートメントに値をバインド
            // Gán giá trị vào dấu ? trong SQL
            // SQL の ? に値をバインド
            statement.setString(1, class_num);
            statement.setString(2, school.getCd());

            // SQL実行 → 結果取得
            // Thực thi SQL và lấy kết quả
            // SQL を実行し、結果セットを取得
            ResultSet rSet = statement.executeQuery();

            // 学校Daoを初期化
            // Tạo SchoolDao để lấy thông tin trường
            // School 情報取得のために SchoolDao を生成
            SchoolDao sDao = new SchoolDao();

            if (rSet.next()) {
                // リザルトセットが存在する場合
                // Nếu có dữ liệu trả về
                // データが存在する場合

                // クラス番号インスタンスに検索結果をセット
                // Gán giá trị vào đối tượng classNum
                // 取得した値を classNum にセット
                classNum.setClass_num(rSet.getString("class_num"));
                classNum.setSchool(sDao.get(rSet.getString("school_cd")));
            } else {
                // リザルトセットが存在しない場合
                // Nếu không có dữ liệu → trả về null
                // データが存在しない場合 → null を返す
                classNum = null;
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

        return classNum;
    }

    public List<String> filter(School school) throws Exception {
        List<String> list = new ArrayList<>();
        // Tạo list để chứa danh sách class_num
        // class_num の一覧を格納するリストを作成

        // データベースへのコネクションを確立
        // Lấy kết nối DB
        // DB コネクションを取得
        Connection connection = getConnection();

        // プリペアードステートメント
        // Chuẩn bị câu SQL
        // SQL 実行用の PreparedStatement を準備
        PreparedStatement statement = null;

        try {
            // SQL: Lấy tất cả class_num theo school_cd, sắp xếp tăng dần
            // school_cd に一致する class_num を昇順で取得する SQL
            statement = connection.prepareStatement(
                "select class_num from class_num where school_cd=? order by class_num"
            );

            // Gán school_cd vào ?
            // school_cd を ? にバインド
            statement.setString(1, school.getCd());

            // Thực thi SQL
            // SQL を実行
            ResultSet rSet = statement.executeQuery();

            // リザルトセットを全件走査
            // Duyệt toàn bộ kết quả
            // 結果セットをすべて走査
            while (rSet.next()) {
                // リストにクラス番号を追加
                // Thêm class_num vào list
                // class_num をリストに追加
                list.add(rSet.getString("class_num"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            // Đóng PreparedStatement
            // PreparedStatement をクローズ
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }

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

        return list;
    }

    public boolean save(ClassNum classNum) throws Exception {
        // Chưa implement → luôn trả về false
        // 未実装 → 常に false を返す
        return false;
    }

    public boolean save(ClassNum classNum, String newClassNum) throws Exception {
        // Chưa implement → luôn trả về false
        // 未実装 → 常に false を返す
        return false;
    }
}
