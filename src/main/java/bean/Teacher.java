package bean;

import java.io.Serializable;

public class Teacher extends User implements Serializable {

    private String id;
    private String password;
    private String name;
    private School school;

    // ★★★ 追加：学校コード（school_cd） ★★★
    private String schoolCd;

    public String getSchoolCd() {
        return schoolCd;
    }

    public void setSchoolCd(String schoolCd) {
        this.schoolCd = schoolCd;
    }
    // ★★★ ここまで追加 ★★★

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
