package bean;
// Nhóm class vào package 'bean' – nơi chứa các JavaBean dùng làm dữ liệu
// データ用クラス（JavaBean）をまとめるためのパッケージ宣言

import java.io.Serializable;
// Cho phép đối tượng Teacher được tuần tự hóa (serialize) – cần khi lưu session hoặc truyền dữ liệu
// Teacher オブジェクトを保存・読み込みできるようにするための Serializable

public class Teacher extends User implements Serializable {
    // Class Teacher kế thừa từ User và đại diện cho thông tin một giáo viên
    // Teacher クラスは User を継承し、教員情報を表す

    private String id;
    // Mã giáo viên (ID đăng nhập)
    // 教員ID（ログインID）

    private String password;
    // Mật khẩu đăng nhập
    // ログイン用パスワード

    private String name;
    // Tên giáo viên
    // 教員名

    private School school;
    // Trường mà giáo viên thuộc về (đối tượng School)
    // 所属している学校（School オブジェクト）

    // ★★★ 追加：学校コード（school_cd） ★★★
    private String schoolCd;
    // Mã trường (school_cd) – dùng để truy vấn DB
    // 学校コード（school_cd）– DB 取得用
    
    private int role;


    public String getSchoolCd() {
        // Trả về mã trường
        // 学校コードを返す
        return schoolCd;
    }

    public void setSchoolCd(String schoolCd) {
        // Gán mã trường
        // 学校コードをセットする
        this.schoolCd = schoolCd;
    }
    // ★★★ ここまで追加 ★★★

    public String getId() {
        // Trả về mã giáo viên
        // 教員ID を返す
        return id;
    }

    public void setId(String id) {
        // Gán mã giáo viên
        // 教員ID をセットする
        this.id = id;
    }

    public String getPassword() {
        // Trả về mật khẩu
        // パスワードを返す
        return password;
    }

    public void setPassword(String password) {
        // Gán mật khẩu
        // パスワードをセットする
        this.password = password;
    }

    public String getName() {
        // Trả về tên giáo viên
        // 教員名を返す
        return name;
    }

    public void setName(String name) {
        // Gán tên giáo viên
        // 教員名をセットする
        this.name = name;
    }

    public School getSchool() {
        // Trả về trường mà giáo viên thuộc về
        // 所属学校（School オブジェクト）を返す
        return school;
    }

    public void setSchool(School school) {
        // Gán trường cho giáo viên
        // 所属学校をセットする
        this.school = school;
    }
    
    public int getRole() {
    	// Trả về giá trị role của giáo viên  
        // 教員のrole値を返す  
        return role;
    }

    public void setRole(int role) {
    	// Thay đổi (gán) giá trị role cho giáo viên  
        // 教員のrole値を設定する 
        this.role = role;
    }
    
    

}
