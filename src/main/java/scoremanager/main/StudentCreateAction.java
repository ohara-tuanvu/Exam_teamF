package scoremanager.main;
// Package chứa các Action thuộc module scoremanager/main
// scoremanager/main モジュールに属する Action クラスをまとめるパッケージ

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bean.School;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;
// Action là lớp cha dùng để xử lý request theo mô hình MVC
// Action は MVC パターンでリクエスト処理を行う基底クラス

public class StudentCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            // セッションからユーザ（教員）を取得
            // Lấy đối tượng Teacher từ session (người đang đăng nhập)
            // セッションに保存されているログイン中の教員情報を取得
            Teacher teacher = (Teacher) req.getSession().getAttribute("user");

            // 教員から学校情報を取得
            // Lấy thông tin trường mà giáo viên thuộc về
            // 教員が所属する学校情報を取得
            School school = teacher.getSchool();

            // クラス番号一覧を取得
            // Lấy danh sách class_num theo school từ DB
            // 学校に紐づくクラス番号一覧を DB から取得
            ClassNumDao cDao = new ClassNumDao();
            List<String> classNumList = cDao.filter(school);

            // 入学年度リストを作成（10年前〜1年後）
            // Tạo danh sách năm nhập học: từ 10 năm trước đến 1 năm sau
            // 現在の年から「10年前〜翌年」までの入学年度リストを作成
            List<Integer> entYearList = new ArrayList<>();
            int year = Calendar.getInstance().get(Calendar.YEAR);
            for (int i = year - 10; i <= year + 1; i++) {
                entYearList.add(i);
            }

            // リクエストにセット
            // Gắn dữ liệu vào request để JSP hiển thị
            // JSP で表示するためにリクエスト属性へセット
            req.setAttribute("class_num_set", classNumList);
            req.setAttribute("ent_year_set", entYearList);

            // JSPへフォワード
            // Chuyển sang trang student_insert.jsp để hiển thị form đăng ký học sinh
            // 学生登録フォーム（student_insert.jsp）へフォワード
            req.getRequestDispatcher("/scoremanager/student_insert.jsp").forward(req, res);

        } catch (Exception e) {
            // Nếu có lỗi → in log và chuyển sang trang error.jsp
            // エラー発生時 → ログ出力して error.jsp へフォワード
            e.printStackTrace();
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}
