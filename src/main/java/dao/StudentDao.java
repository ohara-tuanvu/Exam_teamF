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
import bean.Student;

public class StudentDao extends Dao {

    // SQL cơ bản: lọc theo school_cd
    // 基本SQL：school_cd を条件に検索する
    private String baseSql = "select * from student where school_cd=?";

    // ============================
    // 1. Lấy 1 học sinh theo mã số
    // ============================
    public Student get(String no) throws Exception {

        Student student = new Student();
        // Tạo đối tượng Student để chứa kết quả
        // 結果を格納する Student インスタンスを生成

        Connection connection = getConnection();
        // Lấy kết nối DB
        // DB コネクションを取得

        PreparedStatement statement = null;

        try {
            // SQL: tìm học sinh theo mã số
            // SQL：学生番号で検索
            statement = connection.prepareStatement(
                "select * from student where no=?"
            );

            // Gán giá trị vào ?
            // ? に値をバインド
            statement.setString(1, no);

            ResultSet rSet = statement.executeQuery();
            SchoolDao schoolDao = new SchoolDao();

            if (rSet.next()) {
                // Nếu có dữ liệu
                // データが存在する場合

                student.setNo(rSet.getString("no"));
                student.setName(rSet.getString("name"));
                student.setEntYear(rSet.getInt("ent_year"));
                student.setClassNum(rSet.getString("class_num"));
                student.setAttend(rSet.getBoolean("is_attend"));
                student.setSchool(schoolDao.get(rSet.getString("school_cd")));

            } else {
                // Không có dữ liệu → null
                // データなし → null
                student = null;
            }

        } finally {
            // Đóng statement & connection
            // ステートメントとコネクションをクローズ
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return student;
    }

    // ==========================================================
    // 2. Hàm xử lý chung: chuyển ResultSet → List<Student>
    // ==========================================================
    private List<Student> postFilter(ResultSet rSet, School school) throws Exception {

        List<Student> list = new ArrayList<>();
        // Danh sách kết quả
        // 結果リスト

        try {
            while (rSet.next()) {

                Student student = new Student();
                // Tạo đối tượng Student cho từng dòng
                // 各行ごとに Student インスタンスを生成

                student.setNo(rSet.getString("no"));
                student.setName(rSet.getString("name"));
                student.setEntYear(rSet.getInt("ent_year"));
                student.setClassNum(rSet.getString("class_num"));
                student.setAttend(rSet.getBoolean("is_attend"));
                student.setSchool(school);

                list.add(student);
            }

        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ==========================================================
    // 3. Lọc theo trường + năm nhập học + lớp + trạng thái
    // ==========================================================
    public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {

        List<Student> list = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rSet = null;

        // Điều kiện lọc
        // 検索条件
        String condition = " and ent_year=? and class_num=?";
        String order = " order by no asc";

        // Nếu chỉ lấy học sinh đang theo học
        // 在籍中のみ取得する場合
        String conditionIsAttend = isAttend ? " and is_attend=true" : "";

        try {
            connection = getConnection();

            // Ghép SQL hoàn chỉnh
            // 完成した SQL を作成
            statement = connection.prepareStatement(
                baseSql + condition + conditionIsAttend + order
            );

            // Bind tham số
            // パラメータをバインド
            statement.setString(1, school.getCd());
            statement.setInt(2, entYear);
            statement.setString(3, classNum);

            rSet = statement.executeQuery();

            // Chuyển ResultSet → List<Student>
            // ResultSet を Student リストに変換
            list = postFilter(rSet, school);

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }

    // ==========================================================
    // 4. Lọc theo trường + năm nhập học
    // ==========================================================
    public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception {

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

    // ==========================================================
    // 5. Lọc theo trường + trạng thái
    // ==========================================================
    public List<Student> filter(School school, boolean isAttend) throws Exception {

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

    // ==========================================================
    // 6. Lưu học sinh (INSERT hoặc UPDATE)
    // ==========================================================
    public boolean save(Student student) throws Exception {

        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            // Kiểm tra xem học sinh đã tồn tại chưa
            // 既存データの有無を確認
            Student old = get(student.getNo());

            if (old == null) {
                // INSERT
                // 新規登録
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
                // UPDATE
                // 更新処理
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

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return count > 0;
    }

    // ==========================================================
    // 7. Lấy học sinh theo no + school_cd
    // ==========================================================
    public Student get(String no, School school) throws Exception {

        Student student = null;

        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "select * from student where TRIM(no)=? and TRIM(school_cd)=?"
            );

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
            }

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return student;
    }

}
