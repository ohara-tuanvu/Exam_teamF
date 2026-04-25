package dao;
// VI: Khai báo package chứa lớp DAO  
// JP: DAOクラスが所属するパッケージ宣言

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
// VI: Import các lớp JDBC và List  
// JP: JDBC処理とリスト操作に必要なクラスをインポート

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;
// VI: Import các bean cần thiết  
// JP: 必要なBeanクラスをインポート

public class TestDao extends Dao {
// VI: TestDao kế thừa Dao để dùng getConnection()  
// JP: Daoクラスを継承し、DB接続メソッドを利用する

    private static final String BASE_SQL =
            "SELECT * FROM TEST WHERE SCHOOL_CD=?";
    // VI: SQL cơ bản lọc theo SCHOOL_CD  
    // JP: SCHOOL_CDで絞り込む基本SQL文

    public Test get(Student student, Subject subject, School school, int no)
            throws Exception {
    // VI: Lấy 1 bản ghi Test theo student + subject + school + no  
    // JP: student + subject + school + no でテスト1件を取得

        Connection con = getConnection();
        // VI: Lấy kết nối DB  
        // JP: DB接続を取得

        try {
            return get(student, subject, school, no, con);
            // VI: Gọi hàm get nội bộ dùng chung connection  
            // JP: 同じ接続を使う内部getメソッドを呼び出す
        } finally {
            if (con != null) con.close();
            // VI: Đóng kết nối  
            // JP: 接続をクローズ
        }
    }

    private Test get(Student student, Subject subject,
                     School school, int no, Connection con)
            throws Exception {
    // VI: Hàm get thực sự, dùng connection truyền vào  
    // JP: 実際の取得処理。外部から渡された接続を使用

        Test test = null;
        PreparedStatement st = null;

        try {
            st = con.prepareStatement(
                BASE_SQL + " AND STUDENT_NO=? AND SUBJECT_CD=? AND NO=?"
            );
            // VI: Chuẩn bị SQL tìm theo student_no + subject_cd + no  
            // JP: student_no + subject_cd + no で検索するSQLを準備

            st.setString(1, school.getCd());
            st.setString(2, student.getNo());
            st.setString(3, subject.getCd());
            st.setInt(4, no);
            // VI: Gán tham số vào SQL  
            // JP: SQLパラメータをセット

            ResultSet rs = st.executeQuery();
            // VI: Thực thi truy vấn  
            // JP: クエリを実行

            List<Test> list = postFilter(rs, school);
            // VI: Chuyển ResultSet → List<Test>  
            // JP: ResultSet を List<Test> に変換

            if (!list.isEmpty()) {
                test = list.get(0);
                // VI: Nếu có dữ liệu → lấy phần tử đầu  
                // JP: データがあれば最初の1件を返す
            }

        } finally {
            if (st != null) st.close();
            // VI: Đóng statement  
            // JP: ステートメントをクローズ
        }

        return test;
        // VI: Trả về kết quả  
        // JP: 結果を返す
    }

    private List<Test> postFilter(ResultSet rs, School school)
            throws Exception {
    // VI: Chuyển ResultSet thành danh sách Test  
    // JP: ResultSet を Testリストに変換する処理

        List<Test> list = new ArrayList<>();

        StudentDao sDao = new StudentDao();
        SubjectDao subDao = new SubjectDao();
        // VI: Tạo DAO để lấy Student và Subject  
        // JP: Student と Subject を取得するためのDAOを生成

        while (rs.next()) {
            Test t = new Test();
            // VI: Tạo đối tượng Test mới  
            // JP: 新しいTestオブジェクトを作成

            Student student =
                sDao.get(rs.getString("STUDENT_NO"), school);
            // VI: Lấy Student theo student_no + school  
            // JP: student_no + school でStudentを取得

            Subject subject =
                subDao.get(rs.getString("SUBJECT_CD"), school);
            // VI: Lấy Subject theo subject_cd + school  
            // JP: subject_cd + school でSubjectを取得

            t.setStudent(student);
            t.setSubject(subject);
            t.setSchool(school);
            t.setClassNum(rs.getString("CLASS_NUM"));
            t.setNo(rs.getInt("NO"));
            t.setPoint(rs.getInt("POINT"));
            // VI: Gán các thuộc tính cho Test  
            // JP: Testの各フィールドに値をセット

            list.add(t);
            // VI: Thêm vào danh sách  
            // JP: リストに追加
        }

        return list;
        // VI: Trả về danh sách  
        // JP: リストを返す
    }

    public List<Test> filter(
            int entYear,
            String classNum,
            Subject subject,
            int no,
            School school) throws Exception {
    // VI: Lọc Test theo năm nhập học + lớp + môn + số lần kiểm tra  
    // JP: 入学年度 + クラス + 科目 + 回数 でテストを絞り込み

        List<Test> list = new ArrayList<>();
        Connection con = getConnection();
        PreparedStatement st = null;

        try {
            String sql =
                "SELECT t.* FROM TEST t " +
                "JOIN STUDENT s ON t.STUDENT_NO = s.NO " +
                "WHERE t.SCHOOL_CD=? " +
                "AND s.ENT_YEAR=? " +
                "AND t.CLASS_NUM=? " +
                "AND t.SUBJECT_CD=?";
            // VI: JOIN với STUDENT để lọc theo ENT_YEAR  
            // JP: STUDENTとJOINして入学年度で絞り込む

            if (no != 0) {
                sql += " AND t.NO=?";
                // VI: Nếu no != 0 → thêm điều kiện  
                // JP: no が0でなければ条件追加
            }

            st = con.prepareStatement(sql);

            st.setString(1, school.getCd());
            st.setInt(2, entYear);
            st.setString(3, classNum);
            st.setString(4, subject.getCd());

            if (no != 0) {
                st.setInt(5, no);
            }
            // VI: Gán tham số  
            // JP: パラメータをセット

            ResultSet rs = st.executeQuery();
            list = postFilter(rs, school);
            // VI: Chuyển kết quả thành danh sách  
            // JP: 結果をリストに変換

        } finally {
            if (st != null) st.close();
            if (con != null) con.close();
        }

        return list;
    }

    public List<Test> filter(School school, String classNum, String subjectCd)
            throws Exception {
    // VI: Lọc theo trường + lớp + môn  
    // JP: 学校 + クラス + 科目 で絞り込み

        List<Test> list = new ArrayList<>();
        Connection con = getConnection();
        PreparedStatement st = null;

        try {
            String sql =
                "SELECT t.* FROM TEST t " +
                "WHERE t.SCHOOL_CD=? " +
                "AND t.CLASS_NUM=? " +
                "AND t.SUBJECT_CD=?";
            // VI: SQL lọc đơn giản  
            // JP: シンプルな絞り込みSQL

            st = con.prepareStatement(sql);
            st.setString(1, school.getCd());
            st.setString(2, classNum);
            st.setString(3, subjectCd);

            ResultSet rs = st.executeQuery();
            list = postFilter(rs, school);

        } finally {
            if (st != null) st.close();
            if (con != null) con.close();
        }

        return list;
    }

    public boolean save(List<Test> list) throws Exception {
    // VI: Lưu danh sách Test (insert/update từng cái)  
    // JP: Testリストを保存（1件ずつinsert/update）

        Connection con = getConnection();
        try {
            for (Test t : list) {
                save(t, con);
                // VI: Gọi save từng bản ghi  
                // JP: 各Testを保存
            }
        } finally {
            if (con != null) con.close();
        }

        return true;
    }

    private boolean save(Test test, Connection con)
            throws Exception {
    // VI: Lưu 1 bản ghi Test (insert hoặc update)  
    // JP: Test1件を保存（新規 or 更新）

        PreparedStatement st = null;
        int count;

        try {
            Test old = get(
                test.getStudent(),
                test.getSubject(),
                test.getSchool(),
                test.getNo(),
                con
            );
            // VI: Kiểm tra xem bản ghi đã tồn tại chưa  
            // JP: 既存データがあるか確認

            if (old == null) {
                st = con.prepareStatement(
                    "INSERT INTO TEST " +
                    "(STUDENT_NO, SUBJECT_CD, SCHOOL_CD, NO, POINT, CLASS_NUM) " +
                    "VALUES (?, ?, ?, ?, ?, ?)"
                );
                // VI: Nếu chưa có → INSERT  
                // JP: 存在しない → INSERT

                st.setString(1, test.getStudent().getNo());
                st.setString(2, test.getSubject().getCd());
                st.setString(3, test.getSchool().getCd());
                st.setInt(4, test.getNo());
                st.setInt(5, test.getPoint());
                st.setString(6, test.getClassNum());

            } else {
                st = con.prepareStatement(
                    "UPDATE TEST SET POINT=? " +
                    "WHERE STUDENT_NO=? AND SUBJECT_CD=? AND SCHOOL_CD=? AND NO=?"
                );
                // VI: Nếu đã có → UPDATE điểm  
                // JP: 既存 → POINTのみ更新

                st.setInt(1, test.getPoint());
                st.setString(2, test.getStudent().getNo());
                st.setString(3, test.getSubject().getCd());
                st.setString(4, test.getSchool().getCd());
                st.setInt(5, test.getNo());
            }

            count = st.executeUpdate();
            // VI: Thực thi SQL  
            // JP: SQLを実行

        } finally {
            if (st != null) st.close();
        }

        return count > 0;
        // VI: Nếu có dòng bị ảnh hưởng → true  
        // JP: 更新件数が1以上 → true
    }

    public boolean save(List<Test> list, School school) throws Exception {
    // VI: Lưu danh sách Test theo trường (xoá hết → insert lại)  
    // JP: 学校単位でテスト一覧を保存（削除→再登録）

        if (list == null || list.isEmpty()) {
            return false;
            // VI: Danh sách rỗng → không làm gì  
            // JP: 空リストなら処理しない
        }

        Connection con = getConnection();
        PreparedStatement st = null;

        try {
            String sqlDelete =
                "DELETE FROM TEST WHERE SCHOOL_CD=? AND SUBJECT_CD=? AND NO=?";
            // VI: Xoá toàn bộ bản ghi cũ theo school + subject + no  
            // JP: school + subject + no の既存データを全削除

            st = con.prepareStatement(sqlDelete);
            st.setString(1, school.getCd());
            st.setString(2, list.get(0).getSubject().getCd());
            st.setInt(3, list.get(0).getNo());
            st.executeUpdate();
            st.close();

            String sqlInsert =
                "INSERT INTO TEST (STUDENT_NO, SUBJECT_CD, SCHOOL_CD, NO, POINT, CLASS_NUM) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
            // VI: Chuẩn bị INSERT lại toàn bộ  
            // JP: 全件INSERTし直す

            st = con.prepareStatement(sqlInsert);

            for (Test t : list) {
                st.setString(1, t.getStudent().getNo());
                st.setString(2, t.getSubject().getCd());
                st.setString(3, school.getCd());
                st.setInt(4, t.getNo());
                st.setInt(5, t.getPoint());
                st.setString(6, t.getClassNum());
                st.executeUpdate();
                // VI: Insert từng bản ghi  
                // JP: 1件ずつINSERT
            }

        } finally {
            if (st != null) st.close();
            if (con != null) con.close();
        }

        return true;
        // VI: Lưu thành công  
        // JP: 保存成功
    }
}
