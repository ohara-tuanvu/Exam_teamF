package dao;
// VI: Khai báo package chứa các lớp DAO (Data Access Object)
// JP: DAO（データアクセスオブジェクト）クラスをまとめるパッケージ宣言

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// VI: Import các lớp JDBC để kết nối DB, thực thi SQL và xử lý kết quả
// JP: DB接続・SQL実行・結果取得のためのJDBC関連クラスをインポート

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
}
