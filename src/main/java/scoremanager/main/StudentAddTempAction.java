package scoremanager.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentAddTempAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        req.setCharacterEncoding("UTF-8");
        // VI: Thiết lập mã hóa UTF-8 để nhận dữ liệu tiếng Nhật/Việt chính xác
        // JP: 日本語・ベトナム語の入力を正しく扱うためUTF-8を設定

        Teacher teacher = (Teacher) req.getSession().getAttribute("user");
        // VI: Lấy thông tin giáo viên đang đăng nhập từ session
        // JP: セッションからログイン中の教師情報を取得

        School school = teacher.getSchool();
        // VI: Lấy trường mà giáo viên thuộc về
        // JP: 教師が所属する学校を取得

        List<Student> tempList = (List<Student>) req.getSession().getAttribute("temp_students");
        // VI: Lấy danh sách học sinh tạm đang lưu trong session
        // JP: セッションに保存されている一時学生リストを取得

        if (tempList == null) {
            tempList = new ArrayList<>();
            // VI: Nếu chưa có danh sách thì tạo mới
            // JP: リストが存在しない場合は新しく作成
        }

        // ---------------------------------------------
        // Lấy dữ liệu từ form / フォーム入力の取得
        // ---------------------------------------------
        String entYearStr = req.getParameter("entYear");   
        String no = req.getParameter("no");
        String name = req.getParameter("name");
        String classNum = req.getParameter("classNum");
        String isAttendStr = req.getParameter("isAttend");

        Map<String, String> errors = new HashMap<>();
        // VI: Map chứa lỗi theo từng trường nhập
        // JP: 入力項目ごとのエラーメッセージを保持するMap

        /* ----------------------------------------------------
           Validate dữ liệu nhập
           入力データのチェック処理
           ---------------------------------------------------- */

        int entYear = 0;
        if (entYearStr == null || entYearStr.isEmpty()) {
            errors.put("entYear", "入学年度を選択してください。");
            // VI: Chưa chọn năm nhập học → báo lỗi
            // JP: 入学年度が未選択 → エラー
        } else {
            entYear = Integer.parseInt(entYearStr);
            // VI: Chuyển chuỗi sang số
            // JP: 文字列を数値に変換
        }

        if (no == null || no.isEmpty()) {
            errors.put("no", "学生番号を入力してください。");
            // VI: Mã số sinh viên trống → lỗi
            // JP: 学生番号未入力 → エラー
        }

        if (name == null || name.isEmpty()) {
            errors.put("name", "氏名を入力してください。");
            // VI: Tên sinh viên trống → lỗi
            // JP: 氏名未入力 → エラー
        }

        if (classNum == null || classNum.isEmpty()) {
            errors.put("classNum", "クラスを選択してください。");
            // VI: Chưa chọn lớp → lỗi
            // JP: クラス未選択 → エラー
        }

        /* ----------------------------------------------------
           Nếu có lỗi → quay lại form
           エラーがある場合 → 入力画面へ戻す
           ---------------------------------------------------- */
        if (!errors.isEmpty()) {

            ClassNumDao cDao = new ClassNumDao();
            req.setAttribute("class_num_set", cDao.filter(school));
            // VI: Tải lại danh sách lớp
            // JP: クラス一覧を再設定

            List<Integer> entYearList = new ArrayList<>();
            int year = Calendar.getInstance().get(Calendar.YEAR);
            for (int i = year - 10; i <= year + 1; i++) {
                entYearList.add(i);
            }
            req.setAttribute("ent_year_set", entYearList);
            // VI: Tải lại danh sách năm nhập học
            // JP: 入学年度リストを再設定

            // VI: Giữ lại dữ liệu người dùng đã nhập
            // JP: 入力値を保持してフォームに戻す
            req.setAttribute("entYear", entYearStr);
            req.setAttribute("no", no);
            req.setAttribute("name", name);
            req.setAttribute("classNum", classNum);
            req.setAttribute("isAttend", isAttendStr);

            req.setAttribute("errors", errors);
            // VI: Gửi lỗi về JSP
            // JP: JSPにエラー情報を渡す

            req.setAttribute("temp_students", tempList);
            // VI: Giữ lại danh sách tạm
            // JP: 一時リストを保持

            req.getRequestDispatcher("/scoremanager/student_insert.jsp").forward(req, res);
            // VI: Quay lại trang nhập liệu
            // JP: 入力画面に戻る
            return;
        }

        /* ----------------------------------------------------
           Không có lỗi → thêm vào danh sách tạm
           エラーなし → 一時リストに学生を追加
           ---------------------------------------------------- */

        Student s = new Student();
        s.setEntYear(entYear);                 
        // VI: Gán năm nhập học
        // JP: 入学年度をセット

        s.setNo(no);                           
        // VI: Gán mã số sinh viên
        // JP: 学生番号をセット

        s.setName(name);                       
        // VI: Gán tên sinh viên
        // JP: 氏名をセット

        s.setClassNum(classNum);               
        // VI: Gán lớp
        // JP: クラス番号をセット

        s.setAttend(isAttendStr != null);      
        // VI: Gán trạng thái đang học
        // JP: 在学フラグをセット

        s.setSchool(school);                   
        // VI: Gán trường
        // JP: 学校をセット

        tempList.add(s);
        // VI: Thêm sinh viên vào danh sách tạm
        // JP: 学生を一時リストに追加

        req.getSession().setAttribute("temp_students", tempList);
        // VI: Lưu danh sách tạm vào session
        // JP: 一時リストをセッションに保存

        /* ----------------------------------------------------
           Reset form sau khi thêm
           追加後のフォームリセット処理
           ---------------------------------------------------- */

        ClassNumDao cDao = new ClassNumDao();
        req.setAttribute("class_num_set", cDao.filter(school));
        // VI: Tải lại danh sách lớp
        // JP: クラス一覧を再設定

        List<Integer> entYearList = new ArrayList<>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = year - 10; i <= year + 1; i++) {
            entYearList.add(i);
        }
        req.setAttribute("ent_year_set", entYearList);
        // VI: Tải lại danh sách năm nhập học
        // JP: 入学年度リストを再設定

        // VI: Reset toàn bộ trường nhập
        // JP: フォーム入力をリセット
        req.setAttribute("entYear", "");
        req.setAttribute("no", "");
        req.setAttribute("name", "");
        req.setAttribute("classNum", "");
        req.setAttribute("isAttend", null);

        req.setAttribute("temp_students", tempList);
        // VI: Gửi lại danh sách tạm
        // JP: 一時リストを再設定

        req.getRequestDispatcher("/scoremanager/student_insert.jsp").forward(req, res);
        // VI: Quay lại trang nhập liệu
        // JP: 入力画面に戻る
    }
}
