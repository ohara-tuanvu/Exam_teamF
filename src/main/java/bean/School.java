package bean;
// Khai báo package để nhóm các class thuộc phần "bean" (dùng làm dữ liệu)

import java.io.Serializable;
// Cho phép đối tượng School được tuần tự hóa (serialize) — cần khi lưu session, truyền dữ liệu

public class School implements Serializable {
    // Class School đại diện cho thông tin một trường học

    /**
     * 学校コード:String
     * // Mã trường (school code)
     */
    private String cd;

    /**
     * 学校名:String
     * // Tên trường
     */
    private String name;

    /**
     * ゲッター・セッター
     * // Getter & Setter cho các thuộc tính
     */
    public String getCd() {
        // Trả về mã trường
        return cd;
    }

    public void setCd(String cd) {
        // Gán giá trị cho mã trường
        this.cd = cd;
    }

    public String getName() {
        // Trả về tên trường
        return name;
    }

    public void setName(String name) {
        // Gán giá trị cho tên trường
        this.name = name;
    }
}
