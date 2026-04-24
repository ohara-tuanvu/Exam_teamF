package tool;
// Package chứa các lớp tiện ích (framework tự xây) dùng chung cho toàn project
// プロジェクト全体で共通利用する自作フレームワークのユーティリティクラスをまとめるパッケージ

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// Hai class này đại diện cho request và response trong Servlet
// Servlet のリクエスト・レスポンスを表すクラス

public abstract class Action {
    // Đây là lớp abstract (trừu tượng)
    // Tất cả các Action khác (LoginAction, MenuAction, StudentListAction, ...) đều kế thừa từ đây
    // このクラスは抽象クラスであり、すべての Action クラスの親クラスとなる

    public abstract void execute(
            HttpServletRequest req, HttpServletResponse res
        ) throws Exception;
    // Phương thức execute() là abstract → bắt buộc các lớp con phải override
    // Đây chính là "entry point" mà FrontController sẽ gọi khi xử lý *.action
    //
    // execute() は抽象メソッド → サブクラスで必ず実装する必要がある
    // FrontController が *.action を処理する際に必ず呼び出される「入口」メソッド
    //
    // Ý nghĩa / 意味:
    // - Mỗi Action tương ứng với 1 chức năng (màn hình)
    //   各 Action は 1 つの画面・機能に対応する
    // - FrontController sẽ tạo instance của Action và gọi execute()
    //   FrontController が Action のインスタンスを生成し execute() を呼び出す
    // - Trong execute(), bạn viết logic: lấy request param, gọi DAO, set attribute, forward/redirect
    //   execute() 内でリクエスト取得・DAO 呼び出し・属性設定・画面遷移などの処理を行う
}
