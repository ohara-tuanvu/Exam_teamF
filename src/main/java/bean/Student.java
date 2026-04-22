package bean;
// Nhóm class vào package 'bean' – nơi chứa các lớp dữ liệu (JavaBean)

import java.io.Serializable;
// Cho phép đối tượng Student được tuần tự hóa (serialize) – cần khi lưu session hoặc truyền dữ liệu

public class Student implements Serializable {
    // Class Student đại diện cho thông tin một học sinh

    private String no;
    // Mã số học sinh (student number)

    private String name;
    // Tên học sinh

    private int entYear;
    // Năm nhập học (entry year)

    private String classNum;
    // Mã lớp mà học sinh thuộc về

    private boolean isAttend;
    // Trạng thái đi học (true = đang theo học, false = nghỉ học)

    private School school;
    // Trường mà học sinh thuộc về (đối tượng School)

    public String getNo() {
        // Trả về mã số học sinh
        return no;
    }

    public void setNo(String no) {
        // Gán mã số học sinh
        this.no = no;
    }

    public String getName() {
        // Trả về tên học sinh
        return name;
    }

    public void setName(String name) {
        // Gán tên học sinh
        this.name = name;
    }

    public int getEntYear() {
        // Trả về năm nhập học
        return entYear;
    }

    public void setEntYear(int entYear) {
        // Gán năm nhập học
        this.entYear = entYear;
    }

    public String getClassNum() {
        // Trả về mã lớp
        return classNum;
    }

    public void setClassNum(String classNum) {
        // Gán mã lớp
        this.classNum = classNum;
    }

    public boolean isAttend() {
        // Trả về trạng thái đi học (true/false)
        return isAttend;
    }

    public void setAttend(boolean isAttend) {
        // Gán trạng thái đi học
        this.isAttend = isAttend;
    }

    public School getSchool() {
        // Trả về đối tượng School của học sinh
        return school;
    }

    public void setSchool(School school) {
        // Gán trường học cho học sinh
        this.school = school;
    }
}
