package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {

    // ============================================================
    // 1) Lấy toàn bộ môn học (SUPERADMIN)
    // VI: Lấy tất cả môn học của tất cả trường
    // JP: 全学校の科目をすべて取得（SUPERADMIN 用）
    // ============================================================
    public List<Subject> findAll() throws Exception {
        List<Subject> list = new ArrayList<>();

        Connection con = getConnection(); 
        // VI: Kết nối DB
        // JP: DB 接続

        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM SUBJECT ORDER BY SCHOOL_CD ASC, CD ASC"
        );
        // VI: Lấy tất cả môn học, sắp xếp theo trường → mã môn
        // JP: 全科目を学校コード→科目コード順で取得

        ResultSet rs = st.executeQuery();
        // VI: Thực thi câu SQL
        // JP: SQL を実行

        while (rs.next()) {
            Subject subject = new Subject();
            // VI: Tạo đối tượng Subject
            // JP: Subject オブジェクト生成

            subject.setCd(rs.getString("CD"));
            // VI: Gán mã môn
            // JP: 科目コードをセット

            subject.setName(rs.getString("NAME"));
            // VI: Gán tên môn
            // JP: 科目名をセット

            School school = new School();
            school.setCd(rs.getString("SCHOOL_CD"));
            subject.setSchool(school);
            // VI: Gán trường tương ứng
            // JP: 所属学校をセット

            list.add(subject);
        }

        st.close();
        con.close();
        return list;
    }

    // ============================================================
    // 2) Lấy môn học theo school_cd (Admin/Teacher)
    // VI: Lấy danh sách môn học theo trường
    // JP: 学校コードで科目一覧を取得（Admin/Teacher 用）
    // ============================================================
    public List<Subject> filter(School school) throws Exception {

        List<Subject> list = new ArrayList<>();

        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM SUBJECT WHERE SCHOOL_CD = ? ORDER BY CD ASC"
        );
        // VI: Lọc môn học theo trường
        // JP: 学校コードで絞り込み

        st.setString(1, school.getCd());
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Subject subject = new Subject();
            subject.setCd(rs.getString("CD"));
            subject.setName(rs.getString("NAME"));
            subject.setSchool(school);
            list.add(subject);
        }

        st.close();
        con.close();
        return list;
    }

    // ============================================================
    // 3) Lấy môn học theo CD (SUPERADMIN)
    // VI: Lấy môn học theo mã môn (không giới hạn trường)
    // JP: 科目コードで科目取得（学校制限なし）
    // ============================================================
    public Subject get(String cd) throws Exception {

        Subject subject = null;

        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM SUBJECT WHERE CD = ?"
        );

        st.setString(1, cd);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            subject = new Subject();
            subject.setCd(rs.getString("CD"));
            subject.setName(rs.getString("NAME"));

            School school = new School();
            school.setCd(rs.getString("SCHOOL_CD"));
            subject.setSchool(school);
        }

        st.close();
        con.close();
        return subject;
    }

    // ============================================================
    // 4) Lấy môn học theo CD + school_cd (Admin/Teacher)
    // VI: Lấy môn học theo mã môn + trường
    // JP: 科目コード + 学校コードで取得
    // ============================================================
    public Subject get(String cd, School school) throws Exception {

        Subject subject = null;

        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM SUBJECT WHERE CD = ? AND SCHOOL_CD = ?"
        );

        st.setString(1, cd);
        st.setString(2, school.getCd());
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            subject = new Subject();
            subject.setCd(rs.getString("CD"));
            subject.setName(rs.getString("NAME"));
            subject.setSchool(school);
        }

        st.close();
        con.close();
        return subject;
    }

    // ============================================================
    // 5) Lấy môn học theo tên (SUPERADMIN)
    // VI: Lấy môn học theo tên (không giới hạn trường)
    // JP: 科目名で取得（学校制限なし）
    // ============================================================
    public Subject getByName(String name) throws Exception {

        Subject subject = null;

        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM SUBJECT WHERE NAME = ?"
        );

        st.setString(1, name);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            subject = new Subject();
            subject.setCd(rs.getString("CD"));
            subject.setName(rs.getString("NAME"));

            School school = new School();
            school.setCd(rs.getString("SCHOOL_CD"));
            subject.setSchool(school);
        }

        st.close();
        con.close();
        return subject;
    }

    // ============================================================
    // 6) Lấy môn học theo tên + school_cd (Admin/Teacher)
    // VI: Lấy môn học theo tên + trường
    // JP: 科目名 + 学校コードで取得
    // ============================================================
    public Subject getByName(String name, School school) throws Exception {

        Subject subject = null;

        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM SUBJECT WHERE NAME = ? AND SCHOOL_CD = ?"
        );

        st.setString(1, name);
        st.setString(2, school.getCd());
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            subject = new Subject();
            subject.setCd(rs.getString("CD"));
            subject.setName(rs.getString("NAME"));
            subject.setSchool(school);
        }

        st.close();
        con.close();
        return subject;
    }

    // ============================================================
    // 7) CREATE
    // VI: Thêm môn học mới
    // JP: 科目を新規登録
    // ============================================================
    public boolean create(Subject subject) throws Exception {

        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement(
            "INSERT INTO SUBJECT (SCHOOL_CD, CD, NAME) VALUES (?, ?, ?)"
        );

        st.setString(1, subject.getSchool().getCd());
        st.setString(2, subject.getCd());
        st.setString(3, subject.getName());

        int count = st.executeUpdate();

        st.close();
        con.close();
        return count > 0;
    }

    // ============================================================
    // 8) UPDATE
    // VI: Cập nhật tên môn học
    // JP: 科目名を更新
    // ============================================================
    public boolean update(Subject subject) throws Exception {

        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement(
            "UPDATE SUBJECT SET NAME = ? WHERE CD = ? AND SCHOOL_CD = ?"
        );

        st.setString(1, subject.getName());
        st.setString(2, subject.getCd());
        st.setString(3, subject.getSchool().getCd());

        int count = st.executeUpdate();

        st.close();
        con.close();
        return count > 0;
    }

    // ============================================================
    // 9) DELETE (Admin/Teacher)
    // VI: Xóa môn học theo trường
    // JP: 科目削除（学校限定）
    // ============================================================
    public boolean delete(Subject subject) throws Exception {

        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement(
            "DELETE FROM SUBJECT WHERE CD = ? AND SCHOOL_CD = ?"
        );

        st.setString(1, subject.getCd());
        st.setString(2, subject.getSchool().getCd());

        int count = st.executeUpdate();

        st.close();
        con.close();
        return count > 0;
    }

    // ============================================================
    // 9-2) DELETE (SUPERADMIN)
    // VI: Xóa môn học không giới hạn trường
    // JP: 学校制限なしで科目削除（SUPERADMIN）
    // ============================================================
    public boolean delete(String cd) throws Exception {

        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement(
            "DELETE FROM SUBJECT WHERE CD = ?"
        );

        st.setString(1, cd);

        int count = st.executeUpdate();

        st.close();
        con.close();
        return count > 0;
    }
}
