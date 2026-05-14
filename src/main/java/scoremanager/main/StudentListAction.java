package scoremanager.main;
// VI: Package chứa các Action của module scoremanager
// JP: scoremanager モジュールの Action クラスをまとめるパッケージ

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// VI: Import các lớp tiện ích Java
// JP: Java のユーティリティクラスをインポート

import bean.Student;
import bean.Teacher;
// VI: Import Bean Student và Teacher
// JP: Student と Teacher の Bean をインポート
import dao.ClassNumDao;
import dao.StudentDao;
// VI: Import DAO để thao tác DB
// JP: DB 操作用の DAO クラスをインポート
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
// VI: Import Servlet API
// JP: Servlet API をインポート
import tool.Action;
// VI: Action là lớp cha theo mô hình MVC
// JP: MVC パターンの基底 Action クラス

public class StudentListAction extends Action {
// VI: Action xử lý màn hình danh sách sinh viên
// JP: 学生一覧画面の処理を行う Action

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    // VI: Hàm chính xử lý request
    // JP: リクエスト処理のメインメソッド

        HttpSession session = request.getSession();
        // VI: Lấy session hiện tại
        // JP: 現在のセッションを取得

        Teacher teacher = (Teacher) session.getAttribute("user");
        // VI: Lấy giáo viên đang đăng nhập từ session
        // JP: セッションからログイン中の教員を取得

        // ===== Lấy tham số lọc =====
        String entYearStr = request.getParameter("f1");  // 入学年度
        String classNum = request.getParameter("f2");    // クラス番号
        String isAttendStr = request.getParameter("f3"); // 在学フラグ
        // VI: Lấy giá trị filter từ form
        // JP: フォームからフィルター値を取得

        int entYear = 0;
        // VI: Năm nhập học mặc định = 0 (tức là chưa chọn)
        // JP: 入学年度の初期値 = 0（未選択）

        boolean isAttend = (isAttendStr != null);
        // VI: Checkbox → nếu có giá trị thì true
        // JP: チェックボックス → 値があれば true

        if (entYearStr != null && !entYearStr.equals("")) {
            entYear = Integer.parseInt(entYearStr);
            // VI: Nếu có nhập năm → chuyển sang số
            // JP: 入学年度が入力されていれば数値に変換
        }

        
        if (classNum == null || classNum.equals("")) {
            classNum = "0";
            // VI: Nếu không chọn lớp → gán "0" (tương đương chưa chọn)
            // JP: クラス未選択の場合 → "0" をセット（未選択扱い）
        }

        // ===== Tạo danh sách năm nhập học =====
        LocalDate todaysDate = LocalDate.now();
        int year = todaysDate.getYear();
        // VI: Lấy năm hiện tại
        // JP: 現在の西暦を取得

        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year; i++) {
            entYearSet.add(i);
            // VI: Tạo danh sách 10 năm trước → năm hiện tại
            // JP: 現在から過去10年間の入学年度リストを作成
        }

        // ===== DAO =====
        StudentDao sDao = new StudentDao();
        ClassNumDao cNumDao = new ClassNumDao();
        // VI: Tạo DAO để truy vấn DB
        // JP: DBアクセス用 DAO を生成

        List<String> classNumList = cNumDao.filter(teacher.getSchool());
        // VI: Lấy danh sách lớp theo trường
        // JP: 学校に紐づくクラス一覧を取得

        Map<String, String> errors = new HashMap<>();
        // VI: Map chứa lỗi
        // JP: エラーメッセージを保持する Map

        List<Student> students = null;
        // VI: Danh sách sinh viên sau khi lọc
        // JP: フィルター後の学生リスト

        // ================================
        // ⭐ Điều kiện lọc 
        // ================================

        // ① Không nhập gì → lấy toàn bộ
        if (entYear == 0 && classNum.equals("0")) {
            students = sDao.filter(teacher.getSchool(), isAttend);
            // VI: Không chọn năm + không chọn lớp → lấy toàn bộ theo trạng thái
            // JP: 入学年度未選択 + クラス未選択 → 在籍状態のみで全件取得
        }

        // ② Chỉ nhập 入学年度 → lọc theo năm
        else if (entYear != 0 && classNum.equals("0")) {
            students = sDao.filter(teacher.getSchool(), entYear, isAttend);
            // VI: Chỉ chọn năm → lọc theo năm
            // JP: 入学年度のみ指定 → 年度で絞り込み
        }

        // ③ Chỉ nhập クラス → báo lỗi
        else if (entYear == 0 && !classNum.equals("0")) {
            errors.put("f1", "クラス指定する場合は入学年度も指定してください");
            // VI: Chọn lớp nhưng không chọn năm → lỗi
            // JP: クラスのみ指定 → 入学年度も必要

            request.setAttribute("errors", errors);
            students = new ArrayList<>();
            // VI: Trả về danh sách rỗng
            // JP: 空リストを返す
        }

        // ④ Nhập cả năm + lớp → lọc theo cả 2
        else {
            students = sDao.filter(teacher.getSchool(), entYear, classNum, isAttend);
            // VI: Lọc theo cả năm + lớp
            // JP: 入学年度 + クラス番号で絞り込み
        }

        // ===== Gửi dữ liệu sang JSP =====
        request.setAttribute("f1", entYear);
        request.setAttribute("f2", classNum);
        request.setAttribute("f3", isAttendStr);
        // VI: Giữ lại giá trị filter
        // JP: フィルター値を画面に戻す

        request.setAttribute("students", students);
        // VI: Gửi danh sách sinh viên
        // JP: 学生リストをセット

        request.setAttribute("class_num_set", classNumList);
        // VI: Gửi danh sách lớp
        // JP: クラス一覧をセット

        request.setAttribute("ent_year_set", entYearSet);
        // VI: Gửi danh sách năm nhập học
        // JP: 入学年度一覧をセット

        request.getRequestDispatcher("student_list.jsp").forward(request, response);
        // VI: Chuyển sang JSP hiển thị danh sách
        // JP: 学生一覧 JSP にフォワード
    }
}
