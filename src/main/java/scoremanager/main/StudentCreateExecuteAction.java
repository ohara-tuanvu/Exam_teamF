package scoremanager.main;
// VI: Package chứa các Action thuộc module scoremanager/main
// JP: scoremanager/main モジュールの Action クラスをまとめるパッケージ

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
// VI: Import các Bean cần dùng
// JP: 必要な Bean クラスをインポート
import dao.ClassNumDao;
import dao.StudentDao;
// VI: Import DAO để thao tác DB
// JP: DB 操作用の DAO クラスをインポート
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// VI: Import Servlet API
// JP: Servlet API をインポート
import tool.Action;
// VI: Action là lớp cha theo mô hình MVC
// JP: MVC パターンの基底 Action クラス

public class StudentCreateExecuteAction extends Action {
// VI: Action xử lý khi nhấn nút “登録”
// JP: 「登録」ボタン押下時の処理を行う Action

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
    // VI: Hàm chính xử lý request
    // JP: リクエスト処理のメインメソッド

        try {
            Teacher teacher = (Teacher) req.getSession().getAttribute("user");
            // VI: Lấy giáo viên đang đăng nhập từ session
            // JP: セッションからログイン中の教員を取得

            School school = teacher.getSchool();
            // VI: Lấy trường của giáo viên
            // JP: 教員が所属する学校を取得

            String entYearStr = req.getParameter("entYear");
            String no = req.getParameter("no");
            String name = req.getParameter("name");
            String classNum = req.getParameter("classNum");
            String isAttendStr = req.getParameter("isAttend");
            // VI: Lấy dữ liệu người dùng nhập từ form
            // JP: フォームから送信された値を取得

            // ⭐ Map lỗi theo từng ô
            Map<String, String> errors = new HashMap<>();
            // VI: Map chứa lỗi theo từng field
            // JP: 各入力項目ごとのエラーメッセージを保持する Map

            // 入学年度チェック
            int entYear = 0;
            // VI: Biến lưu năm nhập học sau khi parse
            // JP: 入学年度の数値を保持する変数

            if (entYearStr == null || entYearStr.isEmpty()) {
                errors.put("entYear", "入学年度を選択してください。");
                // VI: Không chọn năm → lỗi
                // JP: 入学年度未選択 → エラー
            } else {
                try {
                    entYear = Integer.parseInt(entYearStr);
                    // VI: Chuyển chuỗi → số
                    // JP: 文字列を数値に変換

                    int year = Calendar.getInstance().get(Calendar.YEAR);
                    // VI: Lấy năm hiện tại
                    // JP: 現在の西暦を取得

                    if (entYear < year - 10 || entYear > year + 1) {
                        errors.put("entYear", "入学年度が不正です。");
                        // VI: Năm nhập học không hợp lệ
                        // JP: 入学年度が範囲外 → エラー
                    }
                } catch (NumberFormatException e) {
                    errors.put("entYear", "入学年度は数値で入力してください。");
                    // VI: Không phải số → lỗi
                    // JP: 数値変換失敗 → エラー
                }
            }

            // ⭐ 学生番号チェック（3桁の数字）
            if (no == null || no.isEmpty()) {
                errors.put("no", "学生番号を入力してください。");
                // VI: Không nhập → lỗi
                // JP: 未入力 → エラー
            } else if (!no.matches("\\d{3}")) {
                errors.put("no", "学生番号は間違いました。");
                // VI: Không đúng 3 chữ số → lỗi
                // JP: 3桁の数字でない → エラー
            }

            // 氏名チェック
            if (name == null || name.isEmpty()) {
                errors.put("name", "氏名を入力してください。");
                // VI: Không nhập tên → lỗi
                // JP: 氏名未入力 → エラー
            }

            // クラス番号チェック
            if (classNum == null || classNum.isEmpty()) {
                errors.put("classNum", "クラスを選択してください。");
                // VI: Không chọn lớp → lỗi
                // JP: クラス未選択 → エラー
            }

            boolean isAttend = (isAttendStr != null);
            // VI: Checkbox → nếu có giá trị thì true
            // JP: チェックボックス → 値があれば true

            // ⭐ Nếu có lỗi → quay lại form
            if (!errors.isEmpty()) {

                ClassNumDao cDao = new ClassNumDao();
                req.setAttribute("class_num_set", cDao.filter(school));
                // VI: Lấy lại danh sách lớp
                // JP: クラス一覧を再取得してセット

                List<Integer> entYearList = new ArrayList<>();
                int year = Calendar.getInstance().get(Calendar.YEAR);
                for (int i = year - 10; i <= year + 1; i++) {
                    entYearList.add(i);
                }
                req.setAttribute("ent_year_set", entYearList);
                // VI: Tạo lại danh sách năm nhập học
                // JP: 入学年度一覧を再生成してセット

                // Giữ lại giá trị nhập
                req.setAttribute("entYear", entYearStr);
                req.setAttribute("no", no);
                req.setAttribute("name", name);
                req.setAttribute("classNum", classNum);
                req.setAttribute("isAttend", isAttendStr);
                // VI: Giữ lại input để hiển thị lại
                // JP: 入力値を保持して画面に戻す

                // Gửi lỗi
                req.setAttribute("errors", errors);
                // VI: Gửi Map lỗi sang JSP
                // JP: エラーメッセージを JSP に渡す

                req.getRequestDispatcher("/scoremanager/student_insert.jsp").forward(req, res);
                // VI: Quay lại màn hình nhập
                // JP: 入力画面へフォワード

                return;
            }

            // ⭐ Không có lỗi → tạo Student
            Student student = new Student();
            student.setEntYear(entYear);
            student.setNo(no);
            student.setName(name);
            student.setClassNum(classNum);
            student.setAttend(isAttend);
            student.setSchool(school);
            // VI: Gán toàn bộ dữ liệu hợp lệ vào Student
            // JP: 入力値を Student オブジェクトにセット

            StudentDao sDao = new StudentDao();
            sDao.save(student);
            // VI: Lưu vào DB
            // JP: DB に保存

            req.setAttribute("message", "登録完了しました。");
            // VI: Gửi thông báo hoàn tất
            // JP: 登録完了メッセージをセット

            req.getRequestDispatcher("/scoremanager/student_insert.jsp").forward(req, res);
            // VI: Quay lại màn hình nhập (để hiển thị message)
            // JP: メッセージ表示のため入力画面へフォワード

            res.sendRedirect("StudentList.action");
            // ⚠️ VI: Dòng này sẽ không chạy vì đã forward trước đó
            // ⚠️ JP: すでに forward 済みのため、この行は実行されない

        } catch (Exception e) {
            e.printStackTrace();
            // VI: In lỗi ra console
            // JP: エラー内容を出力

            req.getRequestDispatcher("/error.jsp").forward(req, res);
            // VI: Chuyển sang trang lỗi
            // JP: エラー画面へフォワード
        }
    }
}
