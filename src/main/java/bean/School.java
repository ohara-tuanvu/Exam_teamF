package bean;
// Khai báo package để nhóm các class thuộc phần "bean" (dùng làm dữ liệu)
// データ用クラスをまとめるためのパッケージ宣言（bean グループ）

import java.io.Serializable;
// Cho phép đối tượng School được tuần tự hóa (serialize) — cần khi lưu session, truyền dữ liệu
// School オブジェクトを保存・読み込みできるようにするための Serializable をインポート

public class School implements Serializable {
    // Class School đại diện cho thông tin một trường học
    // School クラスは学校情報を表す

    /**
     * 学校コード:String
     * // Mã trường (school code)
     * // 学校コード（school code）
     */
    private String cd;

    /**
     * 学校名:String
     * // Tên trường
     * // 学校名
     */
    private String name;

    /**
     * ゲッター・セッター
     * // Getter & Setter cho các thuộc tính
     * // 各フィールドの getter / setter
     */
    public String getCd() {
        // Trả về mã trường
        // cd（学校コード）を返す
        return cd;
    }

    public void setCd(String cd) {
        // Gán giá trị cho mã trường
        // cd（学校コード）に値をセットする
        this.cd = cd;
    }

    public String getName() {
        // Trả về tên trường
        // name（学校名）を返す
        return name;
    }

    public void setName(String name) {
        // Gán giá trị cho tên trường
        // name（学校名）に値をセットする
        this.name = name;
    }
}
