package scoremanager.main;
// Package chứa các Action thuộc module scoremanager/main
// scoremanager/main モジュールに属する Action クラスをまとめるパッケージ

import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ローカル変数の宣言 1
        // Khai báo biến cục bộ
        // セッション・教員情報・DAO を準備
        HttpSession session = req.getSession(); // セッション
        Teacher teacher = (Teacher) session.getAttribute("user"); // ログインユーザー
        SubjectDao sDao = new SubjectDao(); // 科目DAO

        // リクエストパラメータ―の取得 2
        // なし
        // Không có tham số nào cần lấy từ request
        // パラメータ取得なし

        // DBからデータ取得 3
        // Lấy danh sách môn học theo trường của giáo viên
        // ログインユーザーの学校に紐づく科目一覧を取得
        List<Subject> subjects = sDao.filter(teacher.getSchool());

        // ビジネスロジック 4
        // なし
        // Không có xử lý logic đặc biệt
        // 特別な業務ロジックなし

        // DBへデータ保存 5
        // なし
        // Không lưu gì vào DB
        // DB 更新処理なし

        // レスポンス値をセット 6
        // Gửi danh sách môn học sang JSP
        // JSP へ科目一覧を渡す
        req.setAttribute("subjects", subjects);

        // JSPへフォワード 7
        // Chuyển sang trang danh sách môn học
        // 科目一覧画面（subject_list.jsp）へフォワード
        req.getRequestDispatcher("subject_list.jsp").forward(req, res);
    }
}
