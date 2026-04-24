package bean;

// 得点（テスト結果）を表すBeanクラス
// Bean đại diện cho bảng TEST (điểm số)

public class Test {

    // 学生情報
    // Thông tin học sinh
    private Student student;

    // クラス番号
    // Lớp học
    private String classNum;

    // 科目情報
    // Môn học
    private Subject subject;

    // 学校情報
    // Trường họcgg
    private School school;

    // 回数（テスト番号）
    // Lần thi (test number)
    private int no;

    // 点数
    // Điểm số
    private int point;

    // ============================
    // getter / setter
    // ============================

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}