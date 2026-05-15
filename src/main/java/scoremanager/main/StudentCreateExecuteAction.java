package scoremanager.main;
// VI: Package chứa các Action thuộc module scoremanager/main
// JP: scoremanager/main モジュールに属する Action クラスのパッケージ宣言

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// VI: Import các lớp tiện ích Java
// JP: Java のユーティリティクラスをインポート

import bean.School;
import bean.Student;
import bean.Teacher;
// VI: Import các Bean dùng để chứa dữ liệu
// JP: データ保持用の Bean クラスをインポート
import dao.ClassNumDao;
import dao.StudentDao;
// VI: Import DAO để thao tác với DB
// JP: DB 操作用の DAO クラスをインポート
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// VI: Import Servlet API để xử lý request/response
// JP: リクエスト・レスポンス処理のため Servlet API をインポート
import tool.Action;
// VI: Action là lớp cha theo mô hình MVC
// JP: MVC パターンの基底クラス Action

public class StudentCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
    // VI: Hàm xử lý chính khi người dùng nhấn nút "登録"
    // JP: 「登録」ボタン押下時に実行されるメイン処理

        try {

            // ================================
            // 1) Lấy thông tin giáo viên từ session
            // VI: Lấy đối tượng Teacher đang đăng nhập từ session.
            // JP: セッションからログイン中の教員情報を取得する。
            // ================================
            Teacher teacher = (Teacher) req.getSession().getAttribute("user");
            School school = teacher.getSchool();

            // ================================
            // 2) Lấy dữ liệu người dùng nhập từ form
            // VI: Lấy toàn bộ giá trị người dùng nhập trong form gửi lên.
            // JP: フォームから送信された入力値を取得する。
            // ================================
            String entYearStr = req.getParameter("entYear");
            String no = req.getParameter("no");
            String name = req.getParameter("name");
            String classNum = req.getParameter("classNum");
            String isAttendStr = req.getParameter("isAttend");

            Map<String, String> errors = new HashMap<>();

            // ================================
            // 3) Kiểm tra 入学年度 (entYear)
            // VI: Kiểm tra tính hợp lệ của năm nhập học (phải là số và trong phạm vi cho phép).
            // JP: 入学年度が正しいか（数値・許容範囲内か）をチェックする。
            // ================================
            int entYear = 0;

            if (entYearStr == null || entYearStr.isEmpty()) {
                errors.put("entYear", "入学年度を選択してください。");
            } else {
                try {
                    entYear = Integer.parseInt(entYearStr);
                    int year = Calendar.getInstance().get(Calendar.YEAR);

                    if (entYear < year - 10 || entYear > year + 1) {
                        errors.put("entYear", "入学年度が不正です。");
                    }
                } catch (NumberFormatException e) {
                    errors.put("entYear", "入学年度は数値で入力してください。");
                }
            }

            // ================================
            // 4) Kiểm tra 学生番号 (no)
            // VI: Kiểm tra mã số sinh viên (phải là 3 chữ số).
            // JP: 学生番号が正しい形式（3桁の数字）かをチェックする。
            // ================================
            if (no == null || no.isEmpty()) {
                errors.put("no", "学生番号を入力してください。");
            } else if (!no.matches("\\d{3}")) {
                errors.put("no", "学生番号は3桁の数字で入力してください。");
            }

            // ================================
            // 5) Kiểm tra 氏名 (name)
            // VI: Kiểm tra xem người dùng đã nhập tên hay chưa.
            // JP: 氏名が入力されているかをチェックする。
            // ================================
            if (name == null || name.isEmpty()) {
                errors.put("name", "氏名を入力してください。");
            }

            // ================================
            // 6) Kiểm tra クラス番号 (classNum)
            // VI: Kiểm tra xem người dùng đã chọn lớp hay chưa.
            // JP: クラス番号が選択されているかをチェックする。
            // ================================
            if (classNum == null || classNum.isEmpty()) {
                errors.put("classNum", "クラスを選択してください。");
            }

            boolean isAttend = (isAttendStr != null);
            // VI: Checkbox → nếu có giá trị thì true
            // JP: チェックボックスは値があれば true

            // ================================
            // 7) Nếu có lỗi → quay lại form
            // VI: Nếu tồn tại lỗi nhập liệu, trả dữ liệu + lỗi về lại form.
            // JP: 入力エラーがある場合、入力値とエラーを保持して画面に戻す。
            // ================================
            if (!errors.isEmpty()) {
                setFormData(req, school, entYearStr, no, name, classNum, isAttendStr, errors);
                req.getRequestDispatcher("/scoremanager/student_insert.jsp").forward(req, res);
                return;
            }

            // ================================
            // ⭐ 8) Kiểm tra trùng 学生番号 trong DB
            // VI: Kiểm tra xem mã số sinh viên đã tồn tại trong DB hay chưa.
            // JP: 学生番号が既に DB に存在するかどうかをチェックする。
            // ================================
            StudentDao sDao = new StudentDao();
            Student exist = sDao.get(no, school);

            if (exist != null) {
                errors.put("no", "学生番号が重複しています。");

                setFormData(req, school, entYearStr, no, name, classNum, isAttendStr, errors);
                req.getRequestDispatcher("/scoremanager/student_insert.jsp").forward(req, res);
                return;
            }

            // ================================
            // 9) Tạo đối tượng Student hợp lệ
            // VI: Tạo object Student và gán toàn bộ dữ liệu hợp lệ vào.
            // JP: Student オブジェクトを生成し、入力値をセットする。
            // ================================
            Student student = new Student();
            student.setEntYear(entYear);
            student.setNo(no);
            student.setName(name);
            student.setClassNum(classNum);
            student.setAttend(isAttend);
            student.setSchool(school);

            // ================================
            // 10) Lưu vào DB
            // VI: Gọi DAO để lưu dữ liệu sinh viên vào database.
            // JP: DAO を使って学生情報を DB に保存する。
            // ================================
            sDao.save(student);

            // ================================
            // 11) Hiển thị thông báo hoàn tất
            // VI: Gửi thông báo “登録完了しました” về JSP để hiển thị.
            // JP: JSP に「登録完了しました」メッセージをセットして表示する。
            // ================================
            req.setAttribute("message", "登録完了しました。");

            setFormData(req, school, entYearStr, "", "", "", null, null);
            req.getRequestDispatcher("/scoremanager/student_insert.jsp").forward(req, res);
            return;

        } catch (Exception e) {
            e.printStackTrace();
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }

    // ==========================================================
    // ⭐ Hàm tiện ích: Set lại dữ liệu form + danh sách năm/lớp
    // VI: Hàm tiện ích dùng để gắn lại dữ liệu người dùng nhập,
    //     đồng thời tạo lại danh sách năm nhập học và danh sách lớp.
    // JP: 入力値を再セットし、入学年度リストとクラス一覧を再生成するための
    //     ユーティリティメソッド。
    // ==========================================================
    private void setFormData(
            HttpServletRequest req,
            School school,
            String entYearStr,
            String no,
            String name,
            String classNum,
            String isAttendStr,
            Map<String, String> errors) {

        try {
            ClassNumDao cDao = new ClassNumDao();
            req.setAttribute("class_num_set", cDao.filter(school));
            // VI: Lấy danh sách lớp theo trường
            // JP: 学校に紐づくクラス一覧をセット

            List<Integer> entYearList = new ArrayList<>();
            int year = Calendar.getInstance().get(Calendar.YEAR);
            for (int i = year - 10; i <= year + 1; i++) {
                entYearList.add(i);
            }
            req.setAttribute("ent_year_set", entYearList);
            // VI: Tạo danh sách năm nhập học
            // JP: 入学年度一覧をセット

            req.setAttribute("entYear", entYearStr);
            req.setAttribute("no", no);
            req.setAttribute("name", name);
            req.setAttribute("classNum", classNum);
            req.setAttribute("isAttend", isAttendStr);
            // VI: Giữ lại dữ liệu người dùng nhập
            // JP: 入力値を保持して画面に戻す

            if (errors != null) {
                req.setAttribute("errors", errors);
                // VI: Gửi lỗi sang JSP
                // JP: エラーを JSP に渡す
            }

        } catch (Exception e) {
            e.printStackTrace();
            // VI: In lỗi nếu có
            // JP: エラー発生時はスタックトレースを表示
        }
    }
}
