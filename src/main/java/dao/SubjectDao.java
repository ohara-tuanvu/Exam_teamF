package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {

    public List<Subject> filter(School school) throws Exception {
        List<Subject> list = new ArrayList<>();
        Connection con = getConnection();
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

    public Subject get(String cd, School school) throws Exception {
        Subject subject = new Subject();
        Connection con = getConnection();
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
}
