package dao;
// VI: Khai báo package chứa các lớp DAO (Data Access Object)
// JP: DAO（データアクセスオブジェクト）クラスをまとめるパッケージ宣言

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
// VI: Import các lớp JDBC để kết nối DB, thực thi SQL, xử lý kết quả và danh sách
// JP: DB接続・SQL実行・結果取得・リスト操作のためのJDBC関連クラスをインポート

import bean.School;
import bean.Subject;
// VI: Import các Bean School và Subject để ánh xạ dữ liệu từ DB
// JP: DBデータをマッピングするためのSchoolとSubjectのBeanクラスをインポート

public class SubjectDao extends Dao {
// VI: SubjectDao kế thừa Dao để sử dụng getConnection()
// JP: Daoクラスを継承し、getConnection()メソッドを利用する

    public List<Subject> filter(School school) throws Exception {
    // VI: Lấy danh sách môn học theo school_cd
    // JP: school_cdに紐づく科目一覧を取得するメソッド

        List<Subject> list = new ArrayList<>();
        // VI: Tạo danh sách rỗng để chứa kết quả
        // JP: 結果を格納する空のリストを生成

        Connection con = getConnection();
        // VI: Lấy kết nối DB
        // JP: DB接続を取得

        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM SUBJECT WHERE SCHOOL_CD = ? ORDER BY CD ASC"
        );
        // VI: Chuẩn bị SQL lấy tất cả môn học theo school_cd, sắp xếp theo CD tăng dần
        // JP: school_cdで科目一覧を取得し、CD昇順で並べ替えるSQLを準備

        st.setString(1, school.getCd());
        // VI: Gán school_cd vào tham số SQL
        // JP: SQLパラメータにschool_cdをセット

        ResultSet rs = st.executeQuery();
        // VI: Thực thi truy vấn
        // JP: クエリを実行

        while (rs.next()) {
            // VI: Lặp qua từng dòng kết quả
            // JP: 結果セットを1行ずつ処理

            Subject subject = new Subject();
            // VI: Tạo đối tượng Subject mới
            // JP: 新しいSubjectオブジェクトを生成

            subject.setCd(rs.getString("CD"));
            // VI: Gán mã môn học (CD)
            // JP: 科目コード（CD）をセット

            subject.setName(rs.getString("NAME"));
            // VI: Gán tên môn học
            // JP: 科目名（NAME）をセット

            list.add(subject);
            // VI: Thêm vào danh sách
            // JP: リストに追加
        }

        st.close();
        con.close();
        // VI: Đóng statement và connection
        // JP: ステートメントと接続をクローズ

        return list;
        // VI: Trả về danh sách môn học
        // JP: 科目リストを返す
    }

    public Subject get(String cd, School school) throws Exception {
    // VI: Lấy môn học theo CD + school_cd
    // JP: 科目コードCD + school_cdで科目を取得するメソッド

        Subject subject = new Subject();
        // VI: Tạo đối tượng Subject rỗng
        // JP: 空のSubjectオブジェクトを生成

        Connection con = getConnection();
        // VI: Lấy kết nối DB
        // JP: DB接続を取得

        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM SUBJECT WHERE CD = ? AND SCHOOL_CD = ?"
        );
        // VI: Chuẩn bị SQL lấy môn học theo CD và school_cd
        // JP: CDとschool_cdで科目を取得するSQLを準備

        st.setString(1, cd);
        st.setString(2, school.getCd());
        // VI: Gán tham số vào SQL
        // JP: SQLパラメータをセット

        ResultSet rs = st.executeQuery();
        // VI: Thực thi truy vấn
        // JP: クエリを実行

        if (rs.next()) {
            // VI: Nếu tìm thấy dữ liệu
            // JP: データが存在する場合

            subject.setCd(rs.getString("CD"));
            // VI: Gán mã môn học
            // JP: 科目コードをセット

            subject.setName(rs.getString("NAME"));
            // VI: Gán tên môn học
            // JP: 科目名をセット

            subject.setSchool(school);
            // VI: Gán trường từ tham số truyền vào
            // JP: 引数のschoolをセット

        } else {
            subject = null;
            // VI: Không tìm thấy → trả về null
            // JP: 見つからない場合はnull
        }

        st.close();
        con.close();
        // VI: Đóng kết nối
        // JP: 接続をクローズ

        return subject;
        // VI: Trả về môn học
        // JP: 科目を返す
    }

    public Subject getByName(String name, School school) throws Exception {
    // VI: Lấy môn học theo tên + school_cd
    // JP: 科目名 + school_cd で科目を取得するメソッド

        Subject subject = null;
        // VI: Khởi tạo null để kiểm tra tồn tại
        // JP: 見つからない場合に備えてnullで初期化

        Connection con = getConnection();
        // VI: Lấy kết nối DB
        // JP: DB接続を取得

        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM SUBJECT WHERE NAME = ? AND SCHOOL_CD = ?"
        );
        // VI: Chuẩn bị SQL tìm môn học theo tên
        // JP: 科目名で科目を検索するSQLを準備

        st.setString(1, name);
        st.setString(2, school.getCd());
        // VI: Gán tham số
        // JP: パラメータをセット

        ResultSet rs = st.executeQuery();
        // VI: Thực thi truy vấn
        // JP: クエリを実行

        if (rs.next()) {
            // VI: Nếu tìm thấy môn học
            // JP: データが存在する場合

            subject = new Subject();
            // VI: Tạo đối tượng Subject mới
            // JP: 新しいSubjectオブジェクトを生成

            subject.setCd(rs.getString("CD"));
            subject.setName(rs.getString("NAME"));
            subject.setSchool(school);
            // VI: Gán dữ liệu vào đối tượng
            // JP: 各フィールドに値をセット
        }

        st.close();
        con.close();
        // VI: Đóng kết nối
        // JP: 接続をクローズ

        return subject;
        // VI: Trả về môn học (hoặc null)
        // JP: 科目（またはnull）を返す
    }

    public boolean create(Subject subject) throws Exception {
    // VI: Thêm mới môn học
    // JP: 科目を新規登録するメソッド

        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement(
            "INSERT INTO SUBJECT (SCHOOL_CD, CD, NAME) VALUES (?, ?, ?)"
        );
        // VI: Chuẩn bị SQL insert
        // JP: INSERT文を準備

        st.setString(1, subject.getSchool().getCd());
        st.setString(2, subject.getCd());
        st.setString(3, subject.getName());
        // VI: Gán tham số
        // JP: パラメータをセット

        int count = st.executeUpdate();
        // VI: Thực thi insert
        // JP: INSERTを実行

        st.close();
        con.close();
        // VI: Đóng kết nối
        // JP: 接続をクローズ

        return count > 0;
        // VI: Trả về true nếu thêm thành công
        // JP: 1件以上登録されたらtrue
    }

    public boolean update(Subject subject) throws Exception {
    // VI: Cập nhật môn học
    // JP: 科目情報を更新するメソッド

        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement(
            "UPDATE SUBJECT SET NAME = ? WHERE CD = ? AND SCHOOL_CD = ?"
        );
        // VI: Chuẩn bị SQL update
        // JP: UPDATE文を準備

        st.setString(1, subject.getName());
        st.setString(2, subject.getCd());
        st.setString(3, subject.getSchool().getCd());
        // VI: Gán tham số
        // JP: パラメータをセット

        int count = st.executeUpdate();
        // VI: Thực thi update
        // JP: UPDATEを実行

        st.close();
        con.close();
        // VI: Đóng kết nối
        // JP: 接続をクローズ

        return count > 0;
        // VI: Trả về true nếu cập nhật thành công
        // JP: 更新件数が1件以上ならtrue
    }

    public boolean delete(Subject subject) throws Exception {
    // VI: Xóa môn học
    // JP: 科目を削除するメソッド

        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement(
            "DELETE FROM SUBJECT WHERE CD = ? AND SCHOOL_CD = ?"
        );
        // VI: Chuẩn bị SQL delete
        // JP: DELETE文を準備

        st.setString(1, subject.getCd());
        st.setString(2, subject.getSchool().getCd());
        // VI: Gán tham số
        // JP: パラメータをセット

        int count = st.executeUpdate();
        // VI: Thực thi delete
        // JP: DELETEを実行

        st.close();
        con.close();
        // VI: Đóng kết nối
        // JP: 接続をクローズ

        return count > 0;
        // VI: Trả về true nếu xóa thành công
        // JP: 削除件数が1件以上ならtrue
    }
}
