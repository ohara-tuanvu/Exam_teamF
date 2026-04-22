package tool;
// Package chứa các lớp tiện ích (framework tự xây) dùng chung cho toàn project

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// Hai class này đại diện cho request và response trong Servlet

public abstract class Action {
    // Đây là lớp abstract (trừu tượng)
    // Tất cả các Action khác (LoginAction, MenuAction, StudentListAction, ...) đều kế thừa từ đây

    public abstract void execute(
            HttpServletRequest req, HttpServletResponse res
        ) throws Exception;
    // Phương thức execute() là abstract → bắt buộc các lớp con phải override
    // Đây chính là "entry point" mà FrontController sẽ gọi khi xử lý *.action
    //
    // Ý nghĩa:
    // - Mỗi Action tương ứng với 1 chức năng (màn hình)
    // - FrontController sẽ tạo instance của Action và gọi execute()
    // - Trong execute(), bạn viết logic: lấy request param, gọi DAO, set attribute, forward/redirect
}
