package scoremanager.main;  
// JP: 成績管理機能のアクションをまとめるパッケージ  
// VI: Package chứa các Action của chức năng quản lý điểm

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // -----------------------------
        // 1. セッションから教員情報取得
        // JP: セッションからログイン中の教員を取得
        // VI: Lấy thông tin giáo viên đang đăng nhập từ session
        // -----------------------------
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // JP: 教員が所属する学校を取得
        // VI: Lấy trường mà giáo viên thuộc về
        School school = teacher.getSchool();

        // -----------------------------
        // 2. プルダウン用データ準備
        // JP: クラス一覧を学校で絞り込み取得
        // VI: Lấy danh sách lớp theo trường
        // -----------------------------
        List<String> classNumList = new ClassNumDao().filter(school);

        // JP: 科目一覧を学校で絞り込み取得
        // VI: Lấy danh sách môn học theo trường
        List<Subject> subjectList = new SubjectDao().filter(school);

        // JP: 現在の西暦から過去10年分の入学年度を生成
        // VI: Tạo danh sách năm nhập học từ năm hiện tại lùi về 10 năm
        int currentYear = LocalDate.now().getYear();
        List<Integer> entYearList = new ArrayList<>();
        for (int i = currentYear; i >= currentYear - 10; i--) entYearList.add(i);

        // JP: 回数（1〜10）を生成
        // VI: Tạo danh sách số lần (1–10)
        List<Integer> noList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) noList.add(i);

        // JP: JSP にプルダウンデータを渡す
        // VI: Gửi dữ liệu select box sang JSP
        req.setAttribute("classNumList", classNumList);
        req.setAttribute("subjectList", subjectList);
        req.setAttribute("entYearList", entYearList);
        req.setAttribute("noList", noList);

        // -----------------------------
        // 3. 検索条件取得
        // JP: フォームから送信された検索条件を取得
        // VI: Lấy 4 điều kiện tìm kiếm từ form
        // -----------------------------
        String f1 = req.getParameter("f1"); // 入学年度
        String f2 = req.getParameter("f2"); // クラス
        String f3 = req.getParameter("f3"); // 科目
        String f4 = req.getParameter("f4"); // 回数

        // JP: 入力値を画面に保持するためセット
        // VI: Giữ lại giá trị đã nhập để hiển thị lại trên form
        req.setAttribute("f1", f1);
        req.setAttribute("f2", f2);
        req.setAttribute("f3", f3);
        req.setAttribute("f4", f4);

        // -----------------------------
        // ⭐ 初回表示判定
        // JP: f1〜f4 がすべて null → 初回表示なのでエラーを出さない
        // VI: Nếu cả 4 ô đều null → lần đầu mở trang → KHÔNG báo lỗi
        // -----------------------------
        if (f1 == null && f2 == null && f3 == null && f4 == null) {
            req.getRequestDispatcher("test_regist.jsp").forward(req, res);
            return;
        }

        // -----------------------------
        // 4. 入力チェック
        // JP: 未入力項目を missing リストに追加
        // VI: Kiểm tra ô nào bị thiếu và đưa vào danh sách missing
        // -----------------------------
        List<String> missing = new ArrayList<>();

        if (f1 == null || f1.isEmpty()) missing.add("入学年度");
        if (f2 == null || f2.isEmpty()) missing.add("クラス");
        if (f3 == null || f3.isEmpty()) missing.add("科目");
        if (f4 == null || f4.isEmpty()) missing.add("回数");

        // JP: エラー表示用リスト
        // VI: Danh sách chứa lỗi để gửi sang JSP
        List<String> errors = new ArrayList<>();

        // JP: 4つ全部未入力 → 総合メッセージ
        // VI: Nếu thiếu cả 4 ô → báo lỗi tổng hợp
        if (missing.size() == 4) {
            errors.add("入学年度とクラスと科目と回数を選択してください。");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("test_regist.jsp").forward(req, res);
            return;
        }

        // JP: 1〜3個未入力 → 欠けている項目だけを1行にまとめる
        // VI: Nếu thiếu 1–3 ô → ghép tên các ô còn thiếu thành 1 câu lỗi
        if (missing.size() > 0) {
            String msg = String.join("と", missing) + "を選択してください。";
            errors.add(msg);
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("test_regist.jsp").forward(req, res);
            return;
        }

        // -----------------------------
        // 5. 学生一覧取得
        // JP: 入力された条件で学生を検索
        // VI: Lấy danh sách học sinh theo điều kiện tìm kiếm
        // -----------------------------
        int entYear = Integer.parseInt(f1);
        int no = Integer.parseInt(f4);

        List<Student> studentList = new StudentDao().filter(school, entYear, f2, true);

        // JP: 該当学生がいない場合
        // VI: Nếu không tìm thấy học sinh phù hợp
        if (studentList.isEmpty()) {
            req.setAttribute("searchError", "該当する学生情報が存在しませんでした");
        } else {

            // JP: 学生一覧を JSP に渡す
            // VI: Gửi danh sách học sinh sang JSP
            req.setAttribute("studentList", studentList);

            // JP: 既存の点数を取得するための Map
            // VI: Map chứa điểm đã đăng ký trước đó
            TestDao testDao = new TestDao();
            java.util.Map<String, Integer> pointMap = new java.util.HashMap<>();

            // JP: 各学生の既存点数を取得して Map に格納
            // VI: Lấy điểm của từng học sinh và đưa vào Map
            for (Student s : studentList) {
                Test existing = testDao.get(s.getNo(), f3, school, no);
                if (existing != null) {
                    pointMap.put(s.getNo(), existing.getPoint());
                }
            }

            // JP: JSP に点数マップを渡す
            // VI: Gửi Map điểm sang JSP
            req.setAttribute("pointMap", pointMap);
        }

        // -----------------------------
        // 6. JSP へフォワード
        // JP: 最終的に JSP に制御を渡す
        // VI: Chuyển sang JSP để hiển thị kết quả
        // -----------------------------
        req.getRequestDispatcher("test_regist.jsp").forward(req, res);
    }
}
