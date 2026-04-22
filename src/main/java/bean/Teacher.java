package bean;
// Nhóm class vào package 'bean' – nơi chứa các JavaBean dùng làm dữ liệu

import java.io.Serializable;
// Cho phép đối tượng Teacher được tuần tự hóa (serialize) – cần khi lưu session hoặc truyền dữ liệu

public class Teacher extends User implements Serializable {
    // Class Teacher kế thừa từ User và đại diện cho thông tin một giáo viên

    /**
     * 教員ID:String
     * // Mã giáo viên (teacher ID)
     */
    private String id;

    /**
     * パスワード:String
     * // Mật khẩu đăng nhập
     */
    private String password;

    /**
     * 教員名:String
     * // Tên giáo viên
     */
    private String name;

    /**
     * 所属校:School
     * // Trường mà giáo viên thuộc về
     */
    private School school;

    /**
     * ゲッター・セッター
     * // Getter & Setter cho các thuộc tính
     */
    public String getId() {
        // Trả về mã giáo viên
        return id;
    }

    public void setId(String id) {
        // Gán mã giáo viên
        this.id = id;
    }

    public String getPassword() {
        // Trả về mật khẩu
        return password;
    }

    public void setPassword(String password) {
        // Gán mật khẩu
        this.password = password;
    }

    public String getName() {
        // Trả về tên giáo viên
        return name;
    }

    public void setName(String name) {
        // Gán tên giáo viên
        this.name = name;
    }

    public School getSchool() {
        // Trả về trường mà giáo viên thuộc về
        return school;
    }

    public void setSchool(School school) {
        // Gán trường cho giáo viên
        this.school = school;
    }
}
