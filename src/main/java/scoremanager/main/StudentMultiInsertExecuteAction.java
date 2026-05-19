package scoremanager.main;

import java.util.List;

import bean.Student;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentMultiInsertExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        /* ----------------------------------------------------
           Lấy danh sách học sinh tạm từ session
           セッションから一時学生リストを取得する
           ---------------------------------------------------- */
        List<Student> tempList = (List<Student>) req.getSession().getAttribute("temp_students");

        if (tempList == null || tempList.isEmpty()) {
            // VI: Không có dữ liệu để đăng ký → báo lỗi
            // JP: 登録する学生が存在しない場合 → エラー画面へ
            req.setAttribute("message", "学生が追加されていません。");
            req.getRequestDispatcher("/error.jsp").forward(req, res);
            return;
        }

        StudentDao dao = new StudentDao();
        // VI: Tạo DAO để thao tác với DB
        // JP: DB操作のためのDAOを作成

        /* ----------------------------------------------------
           Lưu từng học sinh trong danh sách tạm vào DB
           一時リスト内の学生を1件ずつDBへ保存する
           ---------------------------------------------------- */
        for (Student s : tempList) {
            dao.save(s);
            // VI: Lưu một student vào DB
            // JP: 学生1件をDBへ保存
        }

        /* ----------------------------------------------------
           Xóa danh sách tạm sau khi lưu xong
           保存完了後、一時リストをセッションから削除する
           ---------------------------------------------------- */
        req.getSession().removeAttribute("temp_students");
        // VI: Xóa khỏi session để tránh đăng ký trùng
        // JP: 重複登録を防ぐためセッションから削除

        /* ----------------------------------------------------
           Chuyển về trang thông báo hoàn tất
           完了メッセージを表示する画面へ遷移
           ---------------------------------------------------- */
        req.setAttribute("message", "複数学生の登録が完了しました。");
        // VI: Gửi thông báo hoàn tất đăng ký
        // JP: 登録完了メッセージを設定

        req.getRequestDispatcher("/scoremanager/student_insert.jsp").forward(req, res);
        // VI: Quay lại trang nhập liệu
        // JP: 入力画面へ戻る
    }
}
