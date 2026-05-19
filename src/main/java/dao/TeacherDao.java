package dao;
// VI: Khai báo package chứa các lớp DAO (Data Access Object)
// JP: DAO（データアクセスオブジェクト）クラスをまとめるパッケージ宣言

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// VI: Import các lớp JDBC để kết nối DB, thực thi SQL và xử lý kết quả
// JP: DB接続・SQL実行・結果取得のためのJDBC関連クラスをインポート
import java.util.ArrayList;
import java.util.List;

import bean.Teacher;
// VI: Import Bean Teacher để ánh xạ dữ liệu từ DB
// JP: DBデータをマッピングするためのTeacher Beanをインポート

public class TeacherDao extends Dao {
// VI: TeacherDao kế thừa Dao để sử dụng getConnection()
// JP: Daoクラスを継承し、getConnection()メソッドを利用する

    /**
     * getメソッド 教員IDを指定して教員インスタンスを1件取得する
     * // VI: Lấy thông tin 1 giáo viên theo ID
     */
    public Teacher get(String id) throws Exception {
        Teacher teacher = new Teacher();
        // VI: Tạo đối tượng Teacher rỗng để chứa dữ liệu
        // JP: DBデータを格納するための空のTeacherオブジェクトを生成

        Connection connection = getConnection();
        // VI: Lấy kết nối DB
        // JP: DB接続を取得

        PreparedStatement statement = null;
        // VI: Khai báo statement để chuẩn bị SQL
        // JP: SQL実行用のPreparedStatementを宣言

        try {
            statement = connection.prepareStatement("select * from teacher where id=?");
            // VI: Chuẩn bị SQL lấy giáo viên theo id
            // JP: idで教員を取得するSQL文を準備

            statement.setString(1, id);
            // VI: Gán id vào tham số SQL
            // JP: SQLパラメータにidをセット

            ResultSet resultSet = statement.executeQuery();
            // VI: Thực thi truy vấn
            // JP: クエリを実行し、結果セットを取得

            SchoolDao schoolDao = new SchoolDao();
            // VI: Tạo SchoolDao để lấy thông tin trường
            // JP: 学校情報を取得するためSchoolDaoを生成

            if (resultSet.next()) {
                // VI: Nếu có dữ liệu → ánh xạ vào đối tượng teacher
                // JP: データが存在する場合、teacherに値をセット

                teacher.setId(resultSet.getString("id"));
                // VI: Gán ID giáo viên
                // JP: 教員IDをセット

                teacher.setPassword(resultSet.getString("password"));
                // VI: Gán mật khẩu
                // JP: パスワードをセット

                teacher.setName(resultSet.getString("name"));
                // VI: Gán tên giáo viên
                // JP: 教員名をセット
                
                teacher.setRole(resultSet.getInt("role"));

                //  追加：school_cd を Teacher にセット 
                teacher.setSchoolCd(resultSet.getString("school_cd"));
                // VI: Gán school_cd vào Teacher (bổ sung mới)
                // JP: school_cdをTeacherにセット（追加部分）
                //  ここまで追加

                teacher.setSchool(schoolDao.get(resultSet.getString("school_cd")));
                // VI: Lấy thông tin School từ school_cd và gán vào teacher
                // JP: school_cdからSchool情報を取得してセット

            } else {
                teacher = null;
                // VI: Không tìm thấy giáo viên → trả về null
                // JP: 該当データなし → nullを返す
            }

        } catch (Exception e) {
            throw e;
            // VI: Ném lỗi lên tầng trên
            // JP: 例外を上位へスロー

        } finally {
            if (statement != null) {
                try { statement.close(); } catch (SQLException sqle) { throw sqle; }
                // VI: Đóng statement
                // JP: PreparedStatementをクローズ
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException sqle) { throw sqle; }
                // VI: Đóng kết nối DB
                // JP: DB接続をクローズ
            }
        }

        return teacher;
        // VI: Trả về đối tượng Teacher (hoặc null)
        // JP: Teacherオブジェクト（またはnull）を返す
    }

    /**
     * loginメソッド 教員IDとパスワードで認証する
     * // VI: Đăng nhập bằng id + password
     */
    public Teacher login(String id, String password) throws Exception {
        Teacher teacher = get(id);
        // VI: Lấy thông tin giáo viên theo id
        // JP: idで教員情報を取得

        if (teacher == null || !teacher.getPassword().equals(password)) {
            // VI: Nếu không tồn tại hoặc mật khẩu sai → trả về null
            // JP: 教員が存在しない、またはパスワード不一致 → nullを返す
            return null;
        }

        return teacher;
        // VI: Đăng nhập thành công → trả về Teacher
        // JP: 認証成功 → Teacherを返す
    }
    
    public List<Teacher> findAll(String schoolCd) throws Exception {

        List<Teacher> list = new ArrayList<>();
        // Tạo danh sách chứa giáo viên
        // 教員一覧を格納するリストを作成

        Connection connection = getConnection();
        // Lấy kết nối DB
        // DBコネクションを取得

        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "SELECT * FROM teacher WHERE school_cd=? ORDER BY id"
            );
            // Chuẩn bị SQL lấy giáo viên theo school_cd
            // school_cd で教員を取得するSQLを準備

            statement.setString(1, schoolCd);
            // Gán giá trị school_cd vào ?
            // ? に school_cd をバインド

            ResultSet rs = statement.executeQuery();
            // Thực thi SQL và lấy kết quả
            // SQLを実行して結果を取得

            SchoolDao sDao = new SchoolDao();
            // Tạo SchoolDao để lấy thông tin trường
            // 学校情報取得のために SchoolDao を生成

            while (rs.next()) {
                Teacher t = new Teacher();
                // Tạo đối tượng Teacher cho mỗi dòng
                // 各レコードごとに Teacher オブジェクトを生成

                t.setId(rs.getString("id"));               // Gán ID
                // ID をセット

                t.setPassword(rs.getString("password"));   // Gán password
                // パスワードをセット

                t.setName(rs.getString("name"));           // Gán tên
                // 名前をセット

                t.setSchoolCd(rs.getString("school_cd"));  // Gán mã trường
                // 学校コードをセット

                t.setRole(rs.getInt("role"));              // Gán role
                // 権限(role)をセット

                // Lấy đối tượng School
                // 学校オブジェクトを取得
                t.setSchool(sDao.get(rs.getString("school_cd")));

                list.add(t);
                // Thêm vào danh sách
                // リストに追加
            }

        } finally {
            if (statement != null) statement.close();   // Đóng statement
            // statement を閉じる

            if (connection != null) connection.close(); // Đóng kết nối
            // コネクションを閉じる
        }

        return list;
        // Trả về danh sách giáo viên
        // 教員一覧を返す
    }
    
    public void insert(Teacher t) throws Exception {

        Connection con = getConnection();
        // Lấy kết nối DB
        // DBコネクションを取得

        PreparedStatement st = null;

        try {
            st = con.prepareStatement(
                "INSERT INTO teacher(id, password, name, school_cd, role) VALUES (?, ?, ?, ?, ?)"
            );
            // Chuẩn bị SQL thêm giáo viên
            // 教員を追加するINSERT文を準備

            st.setString(1, t.getId());         // Gán ID
            // ID をセット

            st.setString(2, t.getPassword());   // Gán password
            // パスワードをセット

            st.setString(3, t.getName());       // Gán tên
            // 名前をセット

            st.setString(4, t.getSchoolCd());   // Gán mã trường
            // 学校コードをセット

            st.setInt(5, t.getRole());          // Gán role
            // 権限(role)をセット

            st.executeUpdate();
            // Thực thi INSERT
            // INSERT を実行

        } finally {
            if (st != null) st.close();   // Đóng statement
            // statement を閉じる

            if (con != null) con.close(); // Đóng kết nối
            // コネクションを閉じる
        }
    }
    
    public void update(Teacher t) throws Exception {

        Connection con = getConnection();
        // Lấy kết nối DB
        // DBコネクションを取得

        PreparedStatement st = null;

        try {
            st = con.prepareStatement(
                "UPDATE teacher SET name=?, password=?, role=? WHERE id=?"
            );
            // Chuẩn bị SQL cập nhật giáo viên
            // 教員情報を更新するUPDATE文を準備

            st.setString(1, t.getName());       // Gán tên mới
            // 新しい名前をセット

            st.setString(2, t.getPassword());   // Gán password mới
            // 新しいパスワードをセット

            st.setInt(3, t.getRole());          // Gán role mới
            // 新しい権限(role)をセット

            st.setString(4, t.getId());         // Điều kiện WHERE id=?
            // WHERE id=? の条件をセット

            st.executeUpdate();
            // Thực thi UPDATE
            // UPDATE を実行

        } finally {
            if (st != null) st.close();   // Đóng statement
            // statement を閉じる

            if (con != null) con.close(); // Đóng kết nối
            // コネクションを閉じる
        }
    }
    
    public void delete(String id) throws Exception {

        Connection con = getConnection();
        // Lấy kết nối DB
        // DBコネクションを取得

        PreparedStatement st = null;

        try {
            st = con.prepareStatement("DELETE FROM teacher WHERE id=?");
            // Chuẩn bị SQL xóa giáo viên
            // 教員を削除するDELETE文を準備

            st.setString(1, id);
            // Gán ID vào ?
            // ? に ID をセット

            st.executeUpdate();
            // Thực thi DELETE
            // DELETE を実行

        } finally {
            if (st != null) st.close();   // Đóng statement
            // statement を閉じる

            if (con != null) con.close(); // Đóng kết nối
            // コネクションを閉じる
        }
    }
    
    public List<Teacher> getAll() throws Exception {
        List<Teacher> list = new ArrayList<>();
        // Tạo danh sách chứa giáo viên
        // 教員一覧を格納するリストを作成

        Teacher t = null;

        Connection con = getConnection();
        // Lấy kết nối DB
        // DBコネクションを取得

        PreparedStatement ps = con.prepareStatement(
            "SELECT * FROM teacher " +
            "ORDER BY CASE WHEN id = 'superadmin' THEN 0 ELSE 1 END, id"
        );
        // SQL lấy toàn bộ giáo viên, ưu tiên superadmin lên đầu
        // 全教員を取得し、superadmin を最優先で並べるSQL

        ResultSet rs = ps.executeQuery();
        // Thực thi SQL
        // SQLを実行

        while (rs.next()) {
            t = new Teacher();
            // Tạo đối tượng Teacher
            // Teacher オブジェクトを生成

            t.setId(rs.getString("id"));           // Gán ID
            // ID をセット

            t.setName(rs.getString("name"));       // Gán tên
            // 名前 をセット

            t.setPassword(rs.getString("password"));// Gán password
            // パスワード をセット

            t.setRole(rs.getInt("role"));          // Gán role
            // 権限(role) をセット

            t.setSchoolCd(rs.getString("school_cd"));// Gán mã trường
            // 学校コード をセット

            list.add(t);
            // Thêm vào danh sách
            // リストに追加
        }

        rs.close();  // Đóng ResultSet
        // ResultSet を閉じる

        ps.close();  // Đóng PreparedStatement
        // PreparedStatement を閉じる

        con.close(); // Đóng kết nối
        // コネクション を閉じる

        return list;
        // Trả về danh sách giáo viên
        // 教員一覧を返す
    }
}




