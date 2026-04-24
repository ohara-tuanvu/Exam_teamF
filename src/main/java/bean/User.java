package bean;
// Nhóm class vào package 'bean' – nơi chứa các JavaBean dùng làm dữ liệu
// データ用クラス（JavaBean）をまとめるためのパッケージ宣言（bean グループ）

public class User {

    /**
     * 認証済みフラグ:boolean true:認証済み
     * // Cờ xác thực (authentication flag)
     * // true  = đã đăng nhập hợp lệ
     * // false = chưa xác thực
     * // 認証状態を表すフラグ（true＝認証済み、false＝未認証）
     */
    private boolean isAuthenticated;

    /**
     * ゲッター、セッター
     * // Getter & Setter cho thuộc tính isAuthenticated
     * // isAuthenticated フィールドの getter / setter
     */
    public boolean isAuthenticated() {
        // Trả về trạng thái xác thực (true/false)
        // 認証状態（true/false）を返す
        return isAuthenticated;
    }

    public void setAuthenticated(boolean isAuthenticated) {
        // Gán trạng thái xác thực
        // 認証状態をセットする
        this.isAuthenticated = isAuthenticated;
    }
}
