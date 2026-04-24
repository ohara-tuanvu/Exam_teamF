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
     * // 学校に紐づく科目一覧を取得（一覧画面用）
     */
    public List<Subject> filter(School school) throws Exception {

        List<Subject> list = new ArrayList<>();
        // Danh sách kết quả
        // 結果リスト

        Connection con = getConnection();
        // Lấy kết nối DB
        // DB コネクションを取得

        // SQL: Lấy tất cả môn học thuộc trường hiện tại, sắp xếp theo mã môn
        // SQL：指定された学校の科目を科目コード順で取得
        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM SUBJECT WHERE SCHOOL_CD = ? ORDER BY CD ASC"
        );

        st.setString(1, school.getCd());
        // Gán school_cd vào ?
        // ? に school_cd をバインド

        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Subject subject = new Subject();
            // Tạo đối tượng Subject cho từng dòng
            // 各行ごとに Subject インスタンスを生成

            subject.setCd(rs.getString("CD"));
            subject.setName(rs.getString("NAME"));
            // Không cần setSchool vì trang List không dùng
            // List 画面では school をセットする必要なし

            list.add(subject);
        }

        st.close();
        con.close();
        return list;
    }

    /**
     * 2. Lấy 1 môn học cụ thể (Dùng cho trang Update/Delete)
     * // 科目1件を取得（更新・削除画面用）
     * Dòng này sẽ sửa lỗi ở file SubjectUpdateAction của bạn
     * // UpdateAction のエラーを解消するためのメソッド
     */
    public Subject get(String cd, School school) throws Exception {

        Subject subject = new Subject();
        // Đối tượng chứa kết quả
        // 結果を格納する Subject インスタンス

        Connection con = getConnection();

        // SQL: Tìm môn học khớp cả Mã môn và Mã trường để đảm bảo an toàn
        // SQL：科目コード + 学校コード で1件を特定（安全のため）
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
            // Set luôn school để dùng cho Update/Delete
            // 更新・削除処理で必要なため school をセット
        } else {
            subject = null;
            // Không tìm thấy → trả về null
            // 該当なし → null を返す
        }

        st.close();
        con.close();
        return subject;
    }

    /**
     * 3. Thêm môn học mới (Dùng cho trang Create)
     * // 科目を新規登録（作成画面用）
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
        // count > 0 nghĩa là INSERT thành công
        // count > 0 なら INSERT 成功

        st.close();
        con.close();
        return count > 0;
    }

    /**
     * 4. Cập nhật thông tin môn học (Dùng cho trang UpdateExecute)
     * // 科目情報を更新（更新実行画面用）
     * Dòng này sẽ sửa lỗi gạch đỏ sDao.update(subject)
     * // UpdateAction の赤線エラーを解消するメソッド
     */
    public boolean update(Subject subject) throws Exception {

        Connection con = getConnection();

        // SQL: Cập nhật tên môn học dựa trên CD và SCHOOL_CD
        // SQL：科目コード + 学校コード を条件に科目名を更新
        PreparedStatement st = con.prepareStatement(
            "UPDATE SUBJECT SET NAME = ? WHERE CD = ? AND SCHOOL_CD = ?"
        );

        st.setString(1, subject.getName());
        st.setString(2, subject.getCd());
        st.setString(3, subject.getSchool().getCd());

        int count = st.executeUpdate();
        // count > 0 nghĩa là UPDATE thành công
        // count > 0 なら UPDATE 成功

        st.close();
        con.close();
        return count > 0;
    }

    /**
     * 5. Xóa môn học (Dùng cho trang DeleteExecute)
     * // 科目を削除（削除実行画面用）
     */
    public boolean delete(Subject subject) throws Exception {

        Connection con = getConnection();

        // SQL: Xóa môn học khỏi bảng SUBJECT
        // SQL：科目コード + 学校コード を条件に削除
        PreparedStatement st = con.prepareStatement(
            "DELETE FROM SUBJECT WHERE CD = ? AND SCHOOL_CD = ?"
        );

        st.setString(1, subject.getCd());
        st.setString(2, subject.getSchool().getCd());

        int count = st.executeUpdate();
        // count > 0 nghĩa là DELETE thành công
        // count > 0 なら DELETE 成功

        st.close();
        con.close();
        return count > 0;
    }
}
