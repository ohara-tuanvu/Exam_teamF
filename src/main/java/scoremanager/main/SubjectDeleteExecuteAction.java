package scoremanager.main;
// Package chứa các Action thuộc module scoremanager/main
// scoremanager/main モジュールに属する Action クラスをまとめるパッケージ

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // 1. ローカル変数の宣言
        // Khai báo biến cục bộ
        // セッション・教員情報・DAO を準備
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        SubjectDao sDao = new SubjectDao();

        // 2. リクエストパラメータ―の取得
        // Lấy tham số từ Request
        // 削除対象の科目コードを取得（URL の cd パラメータ）
        String cd = req.getParameter("cd");

        // 3. DBからデータ取得（なし）
        // Không cần lấy dữ liệu từ DB ở bước này
        // DB 取得処理なし（削除対象はコードで特定）

        // 4. ビジネスロジック
        // Tạo đối tượng Subject để xác định mục tiêu cần xóa
        // 削除対象を指定するため Subject オブジェクトを作成
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setSchool(teacher.getSchool());
        // 教員が所属する学校コードをセット

        // 5. DBへデータ保存（削除処理）
        // Gọi phương thức delete của DAO để thực hiện xóa
        // DAO の delete メソッドを呼び出して削除実行
        sDao.delete(subject);

        // 6. レスポンス値をセット（なし）
        // Không cần set attribute nào
        // 特にレスポンス値の設定なし

        // 7. JSPへフォワード（リダイレクト）
        // Xóa xong → quay lại danh sách môn học
        // 削除完了後 → 科目一覧画面へリダイレクト
        res.sendRedirect("SubjectList.action");
    }
}
