package scoremanager.main;
// VI: Package chứa các Action của module scoremanager
// JP: scoremanager モジュールの Action クラスをまとめるパッケージ

import java.io.IOException;
// VI: Import xử lý IO
// JP: IO 処理のためのインポート

import bean.School;
import bean.Teacher;
// VI: Import Bean School và Teacher
// JP: School と Teacher の Bean をインポート
import dao.TestDao;
// VI: Import DAO để thao tác bảng TEST
// JP: TEST テーブル操作用の DAO をインポート
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// VI: Import Servlet API
// JP: Servlet API をインポート
import tool.Action;
// VI: Action là lớp cha theo mô hình MVC
// JP: MVC パターンの基底 Action クラス

public class TestDeleteExecuteAction extends Action {
// VI: Action xử lý khi nhấn nút “削除実行” (xóa điểm)
// JP: 成績削除実行ボタン押下時の処理を行う Action

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
    // VI: Hàm chính xử lý request
    // JP: リクエスト処理のメインメソッド

        try {
            // ================================
            // A. Lấy tham số từ URL
            // URL ví dụ:
            // TestDeleteExecute.action?studentNo=139&subjectCd=A01&no=1
            // ================================
            String studentNo = req.getParameter("studentNo");
            // VI: Lấy mã số sinh viên từ URL
            // JP: URL から学生番号を取得

            String subjectCd = req.getParameter("subjectCd");
            // VI: Lấy mã môn học
            // JP: URL から科目コードを取得

            String noStr = req.getParameter("no");
            // VI: Lấy số lần kiểm tra (1回, 2回…)
            // JP: URL から回数（no）を取得

            int no = Integer.parseInt(noStr); // no = 回数
            // VI: Chuyển chuỗi → số nguyên
            // JP: 文字列を整数に変換（回数）

            // ================================
            // B. Lấy school từ session
            // ================================
            Teacher teacher = (Teacher) req.getSession().getAttribute("user");
            // VI: Lấy giáo viên đang đăng nhập
            // JP: ログイン中の教員を取得

            School school = teacher.getSchool();
            // VI: Lấy trường mà giáo viên thuộc về
            // JP: 教員が所属する学校を取得

            // ================================
            // C. Gọi DAO để xóa dữ liệu
            // ================================
            TestDao tDao = new TestDao();
            // VI: Tạo TestDao để thao tác DB
            // JP: DB 操作用の TestDao を生成

            boolean deleted = tDao.delete(studentNo, subjectCd, school, no);
            // VI: Gọi hàm delete → trả về true nếu xóa thành công
            // JP: delete メソッドを実行 → 成功すれば true

            // Nếu xóa thất bại → báo lỗi
            if (!deleted) {
                req.setAttribute("message", "成績情報の削除に失敗しました。");
                // VI: Gửi thông báo lỗi
                // JP: エラーメッセージをセット

                req.getRequestDispatcher("/error.jsp").forward(req, res);
                // VI: Chuyển sang trang lỗi
                // JP: エラー画面へフォワード

                return;
            }

            // ================================
            // D. Xóa thành công → chuyển sang màn hình 完了
            // ================================
            req.getRequestDispatcher("/scoremanager/main/test_delete_done.jsp").forward(req, res);
            // VI: Chuyển sang màn hình “削除完了”
            // JP: 成績削除完了画面へフォワード

        } catch (Exception e) {
            e.printStackTrace();
            // VI: In lỗi ra console
            // JP: エラー内容を出力

            req.getRequestDispatcher("/error.jsp").forward(req, res);
            // VI: Chuyển sang trang lỗi
            // JP: エラー画面へフォワード
        }
    }
}
