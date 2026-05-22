package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDao extends Dao {

    // SQL cơ bản: lấy học sinh theo mã trường
    // 基本SQL：学校コードで学生を取得
    private String baseSql = "select * from student where school_cd=?";

    // ============================================================
    // Lấy 1 học sinh theo mã số (no)
    // 学生番号で1件取得
    // ============================================================
    public Student get(String no) throws Exception {

        Student student = new Student();

        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            // Lấy dữ liệu theo mã số học sinh
            // 学生番号で検索
            statement = connection.prepareStatement(
                "select * from student where no=?"
            );

            statement.setString(1, no);

            ResultSet rSet = statement.executeQuery();

            SchoolDao schoolDao = new SchoolDao();

            if (rSet.next()) {
                student.setNo(rSet.getString("no"));
                student.setName(rSet.getString("name"));
                student.setEntYear(rSet.getInt("ent_year"));
                student.setClassNum(rSet.getString("class_num"));
                student.setAttend(rSet.getBoolean("is_attend"));
                student.setSchool(schoolDao.get(rSet.getString("school_cd")));
            } else {
                student = null;
            }

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return student;
    }

    // ============================================================
    // Chuyển ResultSet → List<Student>
    // ResultSet を List<Student> に変換
    // ============================================================
    private List<Student> postFilter(ResultSet rSet, School school) throws Exception {

        List<Student> list = new ArrayList<>();

        try {
            while (rSet.next()) {

                Student student = new Student();

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

    // ============================================================
    // Lọc theo: trường + năm nhập học + lớp + trạng thái
    // フィルター：学校 + 入学年度 + クラス + 在籍状態
    // ============================================================
    public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {

        List<Student> list = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rSet = null;

        String condition = " and ent_year=? and class_num=?";
        String order = " order by no asc";
        String conditionIsAttend = isAttend ? " and is_attend=true" : "";

        try {
            connection = getConnection();

            statement = connection.prepareStatement(
                baseSql + condition + conditionIsAttend + order
            );

            statement.setString(1, school.getCd());
            statement.setInt(2, entYear);
            statement.setString(3, classNum);

            rSet = statement.executeQuery();
            list = postFilter(rSet, school);

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }

    // ============================================================
    // Lọc theo: trường + năm nhập học
    // フィルター：学校 + 入学年度
    // ============================================================
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

    // ============================================================
    // Lọc theo: trường
    // フィルター：学校のみ
    // ============================================================
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

    // ============================================================
    // Lưu học sinh (insert/update)
    // 学生情報の保存（新規/更新）
    // ============================================================
    public boolean save(Student student) throws Exception {

        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            // Kiểm tra tồn tại
            // 既存データ確認
            Student old = get(student.getNo());

            if (old == null) {

                // Thêm mới
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

                // Cập nhật
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

    // ============================================================
    // Lấy học sinh theo mã số + trường
    // 学生番号 + 学校コードで取得
    // ============================================================
    public Student get(String no, School school) throws Exception {

        Student student = null;

        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "select * from student where no=? and school_cd=?"
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

    // ============================================================
    // SUPERADMIN: Lấy toàn bộ học sinh
    // SUPERADMIN：全学生取得
    // ============================================================
    public List<Student> findAll() throws Exception {

        List<Student> list = new ArrayList<>();

        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "select * from student order by school_cd, class_num, no"
            );

            ResultSet rSet = statement.executeQuery();

            SchoolDao sDao = new SchoolDao();

            while (rSet.next()) {
                Student student = new Student();

                student.setNo(rSet.getString("no"));
                student.setName(rSet.getString("name"));
                student.setEntYear(rSet.getInt("ent_year"));
                student.setClassNum(rSet.getString("class_num"));
                student.setAttend(rSet.getBoolean("is_attend"));
                student.setSchool(sDao.get(rSet.getString("school_cd")));

                list.add(student);
            }

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }

    // ============================================================
    // SUPERADMIN: Lọc toàn bộ học sinh theo điều kiện
    // SUPERADMIN：全条件フィルター
    // ============================================================
    public List<Student> filterAll(int entYear, String classNum, boolean isAttend) throws Exception {

        List<Student> list = new ArrayList<>();

        Connection connection = getConnection();
        PreparedStatement statement = null;

        // SQL động theo điều kiện
        // 条件に応じてSQLを組み立てる
        String sql = "select * from student where 1=1";

        if (entYear != 0) sql += " and ent_year=?";
        if (!classNum.equals("0")) sql += " and class_num=?";
        if (isAttend) sql += " and is_attend=true";

        sql += " order by school_cd, class_num, no";

        try {
            statement = connection.prepareStatement(sql);

            int idx = 1;
            if (entYear != 0) statement.setInt(idx++, entYear);
            if (!classNum.equals("0")) statement.setString(idx++, classNum);

            ResultSet rSet = statement.executeQuery();

            SchoolDao sDao = new SchoolDao();

            while (rSet.next()) {
                Student st = new Student();
                st.setNo(rSet.getString("no"));
                st.setName(rSet.getString("name"));
                st.setEntYear(rSet.getInt("ent_year"));
                st.setClassNum(rSet.getString("class_num"));
                st.setAttend(rSet.getBoolean("is_attend"));
                st.setSchool(sDao.get(rSet.getString("school_cd")));
                list.add(st);
            }

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }
}
