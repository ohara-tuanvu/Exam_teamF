package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {

    /**
     * 1. Lấy danh sách môn học theo trường (Dùng cho trang List)
     */
    public List<Subject> filter(School school) throws Exception {
        List<Subject> list = new ArrayList<>();
        Connection con = getConnection();
        // SQL: Lấy tất cả môn học thuộc trường hiện tại, sắp xếp theo mã môn
        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM SUBJECT WHERE SCHOOL_CD = ? ORDER BY CD ASC"
        );
        st.setString(1, school.getCd());
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Subject subject = new Subject();
            subject.setCd(rs.getString("CD"));
            subject.setName(rs.getString("NAME"));
            list.add(subject);
        }
        st.close();
        con.close();
        return list;
    }

    /**
     * 2. Lấy 1 môn học cụ thể (Dùng cho trang Update/Delete)
     * Dòng này sẽ sửa lỗi ở file SubjectUpdateAction của bạn
     */
    public Subject get(String cd, School school) throws Exception {
        Subject subject = new Subject();
        Connection con = getConnection();
        // SQL: Tìm môn học khớp cả Mã môn và Mã trường để đảm bảo an toàn
        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM SUBJECT WHERE CD = ? AND SCHOOL_CD = ?"
        );
        st.setString(1, cd);
        st.setString(2, school.getCd());
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            subject.setCd(rs.getString("CD"));
            subject.setName(rs.getString("NAME"));
            subject.setSchool(school);
        } else {
            subject = null;
        }
        st.close();
        con.close();
        return subject;
    }

    /**
     * 3. Thêm môn học mới (Dùng cho trang Create)
     */
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

    /**
     * 4. Cập nhật thông tin môn học (Dùng cho trang UpdateExecute)
     * Dòng này sẽ sửa lỗi gạch đỏ sDao.update(subject) trong ảnh bạn gửi
     */
    public boolean update(Subject subject) throws Exception {
        Connection con = getConnection();
        // SQL: Cập nhật tên môn học dựa trên CD và SCHOOL_CD
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

    /**
     * 5. Xóa môn học (Dùng cho trang DeleteExecute)
     */
    public boolean delete(Subject subject) throws Exception {
        Connection con = getConnection();
        // SQL: Xóa môn học khỏi bảng SUBJECT
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
}