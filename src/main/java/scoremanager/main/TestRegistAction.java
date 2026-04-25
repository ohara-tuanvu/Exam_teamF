package scoremanager.main;
// VI: Package chứa Action của chức năng quản lý điểm  
// JP: 成績管理機能のActionクラスが入るパッケージ

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestRegistAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            // ============================
            // 1. 学校情報（ログイン中）
            // ============================
            // VI: Lấy thông tin trường từ session (đã đăng nhập)
            // JP: セッションからログイン中の学校情報を取得
            School school = (School) req.getSession().getAttribute("school");

            // ============================
            // 2. 入学年度リスト（10年前〜10年後）
            // ============================
            // VI: Tạo danh sách năm nhập học từ (năm hiện tại -10) đến (năm hiện tại +10)
            // JP: 現在年の前後10年分の入学年度リストを作成
            List<Integer> entYearList = new ArrayList<>();
            int year = java.time.LocalDate.now().getYear();
            for (int i = year - 10; i <= year + 10; i++) {
                entYearList.add(i);
            }
            req.setAttribute("entYearList", entYearList);

            // ============================
            // 3. クラス一覧
            // ============================
            // VI: Lấy danh sách lớp theo trường
            // JP: 学校に紐づくクラス一覧を取得
            ClassNumDao cDao = new ClassNumDao();
            req.setAttribute("classList", cDao.filter(school));

            // ============================
            // 4. 科目一覧
            // ============================
            // VI: Lấy danh sách môn học theo trường
            // JP: 学校に紐づく科目一覧を取得
            SubjectDao subDao = new SubjectDao();
            req.setAttribute("subjectList", subDao.filter(school));

            // ============================
            // 5. 回数一覧（1〜10）
            // ============================
            // VI: Tạo danh sách số lần kiểm tra (1–10)
            // JP: テスト回数（1〜10）をリスト化
            List<Integer> noList = new ArrayList<>();
            for (int i = 1; i <= 10; i++) noList.add(i);
            req.setAttribute("noList", noList);

            // ============================
            // 6. 検索条件取得
            // ============================
            // VI: Lấy các tham số tìm kiếm từ request
            // JP: リクエストから検索条件を取得
            String entYearStr = req.getParameter("ent_year");
            String classNum = req.getParameter("class_num");
            String subjectCd = req.getParameter("subject_cd");
            String noStr = req.getParameter("no");

            List<Test> testList = null;

            // ============================
            // 7. 検索実行
            // ============================
            // VI: Chỉ thực hiện tìm kiếm khi tất cả điều kiện đều được chọn
            // JP: 全ての検索条件が揃った場合のみ検索を実行
            if (entYearStr != null && !entYearStr.isEmpty()
                    && classNum != null && !classNum.isEmpty()
                    && subjectCd != null && !subjectCd.isEmpty()
                    && noStr != null && !noStr.isEmpty()) {

                int entYear = Integer.parseInt(entYearStr);
                int no = Integer.parseInt(noStr);

                // 科目取得
                // VI: Lấy đối tượng môn học theo subject_cd
                // JP: subject_cd から科目情報を取得
                Subject subject = subDao.get(subjectCd, school);

                // 成績一覧取得（あなたのDAOに合わせる）
                // VI: Lấy danh sách điểm theo DAO TestDao
                // JP: TestDao を使って成績一覧を取得
                TestDao tDao = new TestDao();
                testList = tDao.filter(entYear, classNum, subject, no, school);

                // 科目名をJSPに渡す
                // VI: Gửi tên môn học sang JSP để hiển thị
                // JP: 科目名をJSPへ渡す
                req.setAttribute("subject_name", subject.getName());
            }

            // ============================
            // 8. JSPへ渡す
            // ============================
            // VI: Gửi danh sách điểm (có thể null) sang JSP
            // JP: 成績リスト（null の可能性あり）をJSPへ渡す
            req.setAttribute("testList", testList);

            // ============================
            // 9. 画面へ遷移
            // ============================
            // VI: Chuyển sang trang test_regist.jsp
            // JP: test_regist.jsp へフォワード
            req.getRequestDispatcher("/scoremanager/main/test_regist.jsp")
               .forward(req, res);

        } catch (Exception e) {
            // VI: Nếu lỗi → ném ServletException
            // JP: エラー発生時は ServletException を投げる
            throw new ServletException(e);
        }
    }
}
