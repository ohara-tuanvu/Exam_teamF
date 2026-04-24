package tool;
// Package chứa các lớp tiện ích dùng chung cho toàn hệ thống
// システム全体で共通利用するユーティリティクラスをまとめるパッケージ

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
// Các import cần thiết cho Filter
// Filter 実装に必要なクラスをインポート

@WebFilter(urlPatterns = { "/*" })
// Áp dụng filter cho TẤT CẢ request trong ứng dụng (/*)
// アプリケーション内のすべてのリクエストに適用されるフィルタ

public class EncodingFilter implements Filter {

    /**
     * doFilterメソッド フィルター処理を記述
     * Phương thức chính của Filter – xử lý trước và sau khi request đi qua
     * リクエスト前後の処理を行うメインメソッド
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // 文字コードをセット
        // Thiết lập encoding cho request (để đọc tiếng Nhật, tiếng Việt không lỗi)
        // リクエストの文字コードを UTF-8 に設定（日本語・ベトナム語の文字化け防止）
        request.setCharacterEncoding("UTF-8");

        // Thiết lập encoding cho response (để hiển thị đúng UTF-8)
        // レスポンスの Content-Type を UTF-8 に設定
        response.setContentType("text/html; charset=UTF-8");

        // System.out.println("フィルタの前処理");
        // (Đã comment) – xử lý trước khi request đi tiếp
        // 前処理（コメントアウト済み）

        // Tiếp tục chuyển request sang Filter tiếp theo hoặc Servlet
        // 次のフィルタまたは Servlet へ処理を渡す
        chain.doFilter(request, response);

        // System.out.println("フィルタの後処理");
        // (Đã comment) – xử lý sau khi Servlet trả về response
        // 後処理（コメントアウト済み）
    }

    public void init(FilterConfig filterConfig) {
        // Không cần xử lý gì khi khởi tạo filter
        // フィルタ初期化時の特別な処理は不要
    }

    public void destroy() {
        // Không cần xử lý gì khi filter bị hủy
        // フィルタ破棄時の特別な処理は不要
    }
}
