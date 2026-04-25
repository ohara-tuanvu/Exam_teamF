package dao;  
// VI: Khai báo package chứa lớp DAO  
// JP: DAOクラスが所属するパッケージ宣言

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
// VI: Import các lớp cần thiết cho JDBC và danh sách  
// JP: JDBC処理とリスト操作に必要なクラスをインポート

import bean.School;
import bean.Student;
// VI: Import các bean Student và School  
// JP: Student と School のBeanクラスをインポート

public class StudentDao extends Dao {
// VI: StudentDao kế thừa lớp Dao (chứa hàm getConnection)  
// JP: Daoクラスを継承し、DB接続メソッドを利用する

    private String baseSql = "select * from student where school_cd=?";
    // VI: Câu SQL cơ bản lọc theo school_cd  
    // JP: school_cdで絞り込む基本SQL文

    public Student get(String no) throws Exception {
    // VI: Lấy 1 sinh viên theo mã số (no)  
    // JP: 学籍番号(no)で学生1件を取得するメソッド

        Student student = new Student();
        // VI: Tạo đối tượng Student rỗng  
        // JP: 空のStudentオブジェクトを生成

        Connection connection = getConnection();
        // VI: Lấy kết nối DB  
        // JP: DB接続を取得

        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "select * from student where no=?"
            );
            // VI: Chuẩn bị câu SQL lấy sinh viên theo no  
            // JP: 学籍番号で学生を取得するSQLを準備

            statement.setString(1, no);
            // VI: Gán giá trị no vào câu SQL  
            // JP: SQLのパラメータにnoをセット

            ResultSet rSet = statement.executeQuery();
            // VI: Thực thi truy vấn  
            // JP: クエリを実行

            SchoolDao schoolDao = new SchoolDao();
            // VI: Tạo SchoolDao để lấy thông tin trường  
            // JP: 学校情報取得のためSchoolDaoを生成

            if (rSet.next()) {
                // VI: Nếu có dữ liệu → gán vào đối tượng student  
                // JP: データが存在する場合、studentに値をセット

                student.setNo(rSet.getString("no"));
                // JP: 学籍番号をセット
                // VI: Gán mã số sinh viên

                student.setName(rSet.getString("name"));
                // JP: 名前をセット
                // VI: Gán tên

                student.setEntYear(rSet.getInt("ent_year"));
                // JP: 入学年度をセット
                // VI: Gán năm nhập học

                student.setClassNum(rSet.getString("class_num"));
                // JP: クラス番号をセット
                // VI: Gán lớp

                student.setAttend(rSet.getBoolean("is_attend"));
                // JP: 在籍フラグをセット
                // VI: Gán trạng thái đi học

                student.setSchool(schoolDao.get(rSet.getString("school_cd")));
                // JP: school_cdからSchool情報を取得してセット
                // VI: Lấy thông tin trường từ school_cd
            } else {
                student = null;
                // JP: 見つからない場合はnull
                // VI: Không tìm thấy → trả về null
            }

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
            // VI: Đóng statement và connection  
            // JP: ステートメントと接続をクローズ
        }

        return student;
        // VI: Trả về đối tượng student  
        // JP: studentを返す
    }

    private List<Student> postFilter(ResultSet rSet, School school) throws Exception {
    // VI: Chuyển ResultSet → List<Student>  
    // JP: ResultSet を List<Student> に変換するメソッド

        List<Student> list = new ArrayList<>();

        try {
            while (rSet.next()) {
                // VI: Lặp qua từng dòng kết quả  
                // JP: 結果セットを1行ずつ処理

                Student student = new Student();
                // JP: 新しいStudentオブジェクトを作成
                // VI: Tạo đối tượng Student mới

                student.setNo(rSet.getString("no"));
                student.setName(rSet.getString("name"));
                student.setEntYear(rSet.getInt("ent_year"));
                student.setClassNum(rSet.getString("class_num"));
                student.setAttend(rSet.getBoolean("is_attend"));
                // VI: Gán các thuộc tính từ DB  
                // JP: DBの値を各フィールドにセット

                student.setSchool(school);
                // VI: Trường được truyền từ tham số  
                // JP: Schoolは引数のschoolをそのままセット

                list.add(student);
                // VI: Thêm vào danh sách  
                // JP: リストに追加
            }

        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            // VI: In lỗi nếu có  
            // JP: エラー発生時はスタックトレースを表示
        }

        return list;
        // JP: リストを返す
        // VI: Trả về danh sách
    }

    public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {
    // VI: Lọc theo trường, năm nhập học, lớp, trạng thái  
    // JP: 学校・入学年度・クラス・在籍状態で絞り込み

        List<Student> list = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rSet = null;

        String condition = " and ent_year=? and class_num=?";
        // JP: 入学年度とクラス番号の条件
        // VI: Điều kiện lọc theo năm và lớp

        String order = " order by no asc";
        // JP: 学籍番号昇順で並べ替え
        // VI: Sắp xếp theo mã số tăng dần

        String conditionIsAttend = isAttend ? " and is_attend=true" : "";
        // JP: 在籍のみを絞る場合の条件
        // VI: Nếu chỉ lấy sinh viên đang học → thêm điều kiện

        try {
            connection = getConnection();

            statement = connection.prepareStatement(
                baseSql + condition + conditionIsAttend + order
            );
            // JP: SQL文を組み立てて準備
            // VI: Ghép SQL và chuẩn bị statement

            statement.setString(1, school.getCd());
            statement.setInt(2, entYear);
            statement.setString(3, classNum);
            // JP: パラメータをセット
            // VI: Gán tham số vào SQL

            rSet = statement.executeQuery();
            list = postFilter(rSet, school);
            // JP: 結果をList<Student>に変換
            // VI: Chuyển ResultSet thành danh sách

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }

    public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception {
    // VI: Lọc theo trường + năm nhập học + trạng thái  
    // JP: 学校・入学年度・在籍状態で絞り込み

        List<Student> list = new ArrayList<>();

        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;

        String condition = " and ent_year=?";
        String order = " order by no asc";
        String conditionIsAttend = isAttend ? " and is_attend=true" : "";

        try {
            statement = connection.prepareStatement(
                baseSql + condition + conditionIsAttend + order
            );

            statement.setString(1, school.getCd());
            statement.setInt(2, entYear);

            rSet = statement.executeQuery();
            list = postFilter(rSet, school);

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }

    public List<Student> filter(School school, boolean isAttend) throws Exception {
    // VI: Lọc theo trường + trạng thái  
    // JP: 学校・在籍状態で絞り込み

        List<Student> list = new ArrayList<>();

        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;

        String order = " order by no asc";
        String conditionIsAttend = isAttend ? " and is_attend=true" : "";

        try {
            statement = connection.prepareStatement(
                baseSql + conditionIsAttend + order
            );

            statement.setString(1, school.getCd());

            rSet = statement.executeQuery();
            list = postFilter(rSet, school);

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }

    public boolean save(Student student) throws Exception {
    // VI: Lưu sinh viên (insert hoặc update)  
    // JP: 学生情報を保存（新規 or 更新）

        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            Student old = get(student.getNo());
            // JP: 既存データを取得
            // VI: Kiểm tra xem sinh viên đã tồn tại chưa

            if (old == null) {
                // JP: 存在しない → 新規登録
                // VI: Không tồn tại → thêm mới

                statement = connection.prepareStatement(
                    "insert into student (no, name, ent_year, class_num, is_attend, school_cd) values(?, ?, ?, ?, ?, ?)"
                );

                statement.setString(1, student.getNo());
                statement.setString(2, student.getName());
                statement.setInt(3, student.getEntYear());
                statement.setString(4, student.getClassNum());
                statement.setBoolean(5, student.isAttend());
                statement.setString(6, student.getSchool().getCd());

            } else {
                // JP: 存在する → 更新
                // VI: Đã tồn tại → cập nhật

                statement = connection.prepareStatement(
                    "update student set name=?, ent_year=?, class_num=?, is_attend=? where no=?"
                );

                statement.setString(1, student.getName());
                statement.setInt(2, student.getEntYear());
                statement.setString(3, student.getClassNum());
                statement.setBoolean(4, student.isAttend());
                statement.setString(5, student.getNo());
            }

            count = statement.executeUpdate();
            // JP: 実行して更新件数を取得
            // VI: Thực thi và lấy số dòng ảnh hưởng

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return count > 0;
        // JP: 1件以上更新されたらtrue
        // VI: Nếu có dòng bị ảnh hưởng → trả về true
    }

    // ============================
    // ⭐ FIX CHÍNH — KHÔNG DÙNG TRIM()
    // ============================
    public Student get(String no, School school) throws Exception {
    // VI: Lấy sinh viên theo no + school_cd  
    // JP: 学籍番号 + school_cd で学生を取得

        Student student = null;

        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "select * from student where no=? and school_cd=?"
            );
            // JP: no と school_cd の両方で検索
            // VI: Tìm theo cả no và school_cd

            statement.setString(1, no);
            statement.setString(2, school.getCd());

            ResultSet rSet = statement.executeQuery();

            if (rSet.next()) {
                student = new Student();

                student.setNo(rSet.getString("no"));
                student.setName(rSet.getString("name"));
                student.setEntYear(rSet.getInt("ent_year"));
                student.setClassNum(rSet.getString("class_num"));
                student.setAttend(rSet.getBoolean("is_attend"));
                student.setSchool(school);
                // JP: schoolは引数のschoolをセット
                // VI: Gán school từ tham số truyền vào
            }

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return student;
        // JP: 見つかった学生を返す
        // VI: Trả về sinh viên tìm được
    }

}
