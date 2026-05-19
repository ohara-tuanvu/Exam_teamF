package scoremanager.main;

import java.util.List;

import bean.Student;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentTempDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        req.setCharacterEncoding("UTF-8");
        // VI: Thiết lập UTF-8 để xử lý tiếng Nhật/Việt chính xác
        // JP: 日本語・ベトナム語を正しく扱うためUTF-8を設定

        /* ----------------------------------------------------
           Lấy index của học sinh cần xóa
           削除対象の学生インデックスを取得する
           ---------------------------------------------------- */
        String indexStr = req.getParameter("index");
        int index = Integer.parseInt(indexStr);
        // VI: Chuyển index từ chuỗi sang số
        // JP: インデックス文字列を数値に変換

        /* ----------------------------------------------------
           Lấy danh sách học sinh tạm từ session
           セッションから一時学生リストを取得する
           ---------------------------------------------------- */
        List<Student> tempList = (List<Student>) req.getSession().getAttribute("temp_students");

        if (tempList != null && index >= 0 && index < tempList.size()) {
            tempList.remove(index);
            // VI: Xóa học sinh tại vị trí index
            // JP: 指定されたインデックスの学生を削除
        }

        /* ----------------------------------------------------
           Lưu danh sách tạm đã cập nhật vào session
           更新後の一時リストをセッションに保存する
           ---------------------------------------------------- */
        req.getSession().setAttribute("temp_students", tempList);
        // VI: Ghi đè danh sách tạm trong session
        // JP: セッション内の一時リストを更新

        /* ----------------------------------------------------
           Quay lại trang nhập liệu
           入力画面へ戻る
           ---------------------------------------------------- */
        req.getRequestDispatcher("/scoremanager/student_insert.jsp").forward(req, res);
        // VI: Forward về trang thêm học sinh
        // JP: 学生追加画面へフォワード
    }
}
