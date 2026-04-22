package tool;
// Package chứa các lớp tiện ích dùng chung cho toàn hệ thống

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
// Các import cần thiết cho Filter

@WebFilter(urlPatterns = { "/*" })
// Áp dụng filter cho TẤT CẢ request trong ứng dụng (/*)

public class EncodingFilter implements Filter {

    /**
     * doFilterメソッド フィルター処理を記述
     * // Phương thức chính của Filter – xử lý trước và sau khi request đi qua
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // 文字コードをセット
        // Thiết lập encoding cho request (để đọc tiếng Nhật, tiếng Việt không lỗi)
        request.setCharacterEncoding("UTF-8");

        // Thiết lập encoding cho response (để hiển thị đúng UTF-8)
        response.setContentType("text/html; charset=UTF-8");

        // System.out.println("フィルタの前処理");
        // (Đã comment) – xử lý trước khi request đi tiếp

        // Tiếp tục chuyển request sang Filter tiếp theo hoặc Servlet
        chain.doFilter(request, response);

        // System.out.println("フィルタの後処理");
        // (Đã comment) – xử lý sau khi Servlet trả về response
    }

    public void init(FilterConfig filterConfig) {
        // Không cần xử lý gì khi khởi tạo filter
    }

    public void destroy() {
        // Không cần xử lý gì khi filter bị hủy
    }
}
