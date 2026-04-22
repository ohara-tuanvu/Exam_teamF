package bean;
// Nhóm class vào package 'bean' – nơi chứa các JavaBean dùng làm dữ liệu

public class User {
    /**
     * 認証済みフラグ:boolean true:認証済み
     * // Cờ xác thực (authentication flag)
     * // true  = đã đăng nhập hợp lệ
     * // false = chưa xác thực
     */
    private boolean isAuthenticated;

    /**
     * ゲッター、セッター
     * // Getter & Setter cho thuộc tính isAuthenticated
     */
    public boolean isAuthenticated() {
        // Trả về trạng thái xác thực (true/false)
        return isAuthenticated;
    }

    public void setAuthenticated(boolean isAuthenticated) {
        // Gán trạng thái xác thực
        this.isAuthenticated = isAuthenticated;
    }
}
