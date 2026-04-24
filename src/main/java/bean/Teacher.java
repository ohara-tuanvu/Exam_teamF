package bean;
// Nhóm class vào package 'bean' – nơi chứa các JavaBean dùng làm dữ liệu
// データ用クラス（JavaBean）をまとめるためのパッケージ宣言（bean グループ）

import java.io.Serializable;
// Cho phép đối tượng Teacher được tuần tự hóa (serialize) – cần khi lưu session hoặc truyền dữ liệu
// Teacher オブジェクトを保存・読み込みできるようにするための Serializable をインポート

public class Teacher extends User implements Serializable {
    // Class Teacher kế thừa từ User và đại diện cho thông tin một giáo viên
    // Teacher クラスは User を継承し、教員情報を表す

    /**
     * 教員ID:String
     * // Mã giáo viên (teacher ID)
     * // 教員ID（teacher ID）
     */
    private String id;

    /**
     * パスワード:String
     * // Mật khẩu đăng nhập
     * // ログイン用パスワード
     */
    private String password;

    /**
     * 教員名:String
     * // Tên giáo viên
     * // 教員名
     */
    private String name;

    /**
     * 所属校:School
     * // Trường mà giáo viên thuộc về
     * // 所属している学校（School オブジェクト）
     */
    private School school;

    /**
     * ゲッター・セッター
     * // Getter & Setter cho các thuộc tính
     * // 各フィールドの getter / setter
     */
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
}
