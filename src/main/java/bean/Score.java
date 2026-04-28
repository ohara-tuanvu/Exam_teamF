package bean;

import java.io.Serializable;

public class Score implements Serializable {

    // 学生番号
    private String studentNo;

    // 科目コード
    private String subjectCd;

    // 学校コード
    private String schoolCd;

    // 試験番号
    private int no;

    // 点数
    private int point;

    // クラス番号
    private String classNum;

    public String getStudentNo() { return studentNo; }
    public void setStudentNo(String studentNo) { this.studentNo = studentNo; }

    public String getSubjectCd() { return subjectCd; }
    public void setSubjectCd(String subjectCd) { this.subjectCd = subjectCd; }

    public String getSchoolCd() { return schoolCd; }
    public void setSchoolCd(String schoolCd) { this.schoolCd = schoolCd; }

    public int getNo() { return no; }
    public void setNo(int no) { this.no = no; }

    public int getPoint() { return point; }
    public void setPoint(int point) { this.point = point; }

    public String getClassNum() { return classNum; }
    public void setClassNum(String classNum) { this.classNum = classNum; }
}
