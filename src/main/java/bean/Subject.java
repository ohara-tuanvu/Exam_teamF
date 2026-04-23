package bean;

import java.io.Serializable;

public class Subject implements Serializable {
    private String cd;       // Mã môn học (CD)
    private String name;     // Tên môn học (NAME)
    private School school;   // Đối tượng chứa SCHOOL_CD

    public Subject() {}

    public String getCd() { return cd; }
    public void setCd(String cd) { this.cd = cd; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public School getSchool() { return school; }
    public void setSchool(School school) { this.school = school; }
}