package scoremanager.main;
// VI: Package chứa Action xử lý đăng ký điểm  
// JP: 成績登録処理を行うActionクラスのパッケージ

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestRegistExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            // VI: Lấy thông tin trường từ session  
            // JP: セッションから学校情報を取得
            School school = (School) req.getSession().getAttribute("school");

            // VI: Lấy mã môn học và số lần kiểm tra  
            // JP: 科目コードと回数を取得
            String subjectCd = req.getParameter("subject_cd");
            String noStr = req.getParameter("no");

            SubjectDao subDao = new SubjectDao();
            Subject subject = subDao.get(subjectCd, school);
            // VI: Lấy đối tượng môn học  
            // JP: 科目情報を取得

            int no = Integer.parseInt(noStr);
            // VI: Chuyển số lần kiểm tra sang int  
            // JP: 回数を整数に変換

            TestDao tDao = new TestDao();
            StudentDao sDao = new StudentDao();

            List<Test> testList = new ArrayList<>();

            int errorIndex = -1;
            String errorMessage = null;

            // ⭐ Danh sách lỗi để hiển thị nhiều lỗi  
            // JP: JSPで複数エラーを表示するためのリスト
            List<String> errorList = new ArrayList<>();

            // ============================
            // 1. ループで学生ごとの点数を処理
            // ============================
            // VI: Lặp qua từng student_no_xxx trong form  
            // JP: フォーム内の student_no_xxx を順番に処理
            int index = 0;

            for (String param : req.getParameterMap().keySet()) {

                if (param.startsWith("student_no_")) {

                    String studentNo = req.getParameter(param);
                    String pointStr = req.getParameter("point_" + studentNo);

                    // ----------------------------
                    // Student取得（null対策追加）
                    // ----------------------------
                    // VI: Lấy thông tin sinh viên theo mã  
                    // JP: 学生番号からStudentを取得（null対策あり）
                    Student stu = null;
                    try {
                        stu = sDao.get(studentNo, school);
                    } catch (Exception e) {
                        throw new ServletException(e);
                    }

                    if (stu == null) {
                        // VI: Nếu không tìm thấy sinh viên → lỗi  
                        // JP: 学生が存在しない場合はエラー
                        throw new ServletException("Student not found: " + studentNo);
                    }

                    // ----------------------------
                    // 点数バリデーション
                    // ----------------------------
                    // VI: Kiểm tra hợp lệ của điểm  
                    // JP: 点数のバリデーション
                    int point = 0;

                    if (pointStr == null || pointStr.isEmpty()) {
                        errorIndex = index;
                        errorMessage = "点数を入力してください"; // VI: Hãy nhập điểm
                        errorList.add(errorMessage);
                        break;
                    }

                    if (!pointStr.matches("^[0-9]{1,3}$")) {
                        errorIndex = index;
                        errorMessage = "0～100の範囲で入力してください"; // VI: Nhập từ 0–100
                        errorList.add(errorMessage);
                        break;
                    }

                    point = Integer.parseInt(pointStr);

                    if (point < 0 || point > 100) {
                        errorIndex = index;
                        errorMessage = "0～100の範囲で入力してください";
                        errorList.add(errorMessage);
                        break;
                    }

                    // ----------------------------
                    // Testオブジェクト作成
                    // ----------------------------
                    // VI: Tạo đối tượng Test để lưu vào DB  
                    // JP: DB登録用のTestオブジェクトを作成
                    Test t = new Test();
                    t.setStudent(stu);
                    t.setSubject(subject);
                    t.setNo(no);
                    t.setPoint(point);
                    t.setClassNum(stu.getClassNum());

                    // ⭐ BẮT BUỘC: thêm school vào Test  
                    // JP: 必須：Testにschoolをセット
                    t.setSchool(school);

                    testList.add(t);
                    index++;
                }
            }

            // ============================
            // 2. エラーがあれば再表示
            // ============================
            // VI: Nếu có lỗi → quay lại trang nhập  
            // JP: エラーがある場合は入力画面に戻す
            if (errorIndex != -1) {

                List<Test> rebuildList = rebuildList(req, school, subject, no);
                // VI: Tạo lại danh sách Test để hiển thị lại  
                // JP: 再表示用にTestリストを再構築

                req.setAttribute("testList", rebuildList);
                req.setAttribute("errorIndex", errorIndex);
                req.setAttribute("errorMessage", errorMessage);

                // ⭐ Gửi danh sách lỗi sang JSP  
                // JP: エラーリストをJSPへ渡す
                req.setAttribute("errorList", errorList);

                req.setAttribute("subject_name", subject.getName());

                req.getRequestDispatcher("/scoremanager/main/test_regist.jsp")
                        .forward(req, res);
                return;
            }

            // ============================
            // 3. DB登録
            // ============================
            // VI: Lưu toàn bộ danh sách điểm vào DB  
            // JP: 成績リストをDBへ登録
            tDao.save(testList, school);

            // ============================
            // 4. 完了画面へ
            // ============================
            // VI: Chuyển sang trang hoàn tất  
            // JP: 完了画面へフォワード
            req.getRequestDispatcher("/scoremanager/main/test_regist_done.jsp")
                    .forward(req, res);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // ==========================================================
    // 入力エラー時の再表示用リスト再構築（null対策追加）
    // ==========================================================
    // VI: Hàm tạo lại danh sách Test khi có lỗi nhập liệu  
    // JP: 入力エラー時に再表示するためのTestリストを再構築
    private List<Test> rebuildList(HttpServletRequest req, School school, Subject subject, int no)
            throws Exception {

        StudentDao sDao = new StudentDao();
        List<Test> list = new ArrayList<>();

        for (String param : req.getParameterMap().keySet()) {

            if (param.startsWith("student_no_")) {

                String stuNo = req.getParameter(param);
                String pointStr = req.getParameter("point_" + stuNo);

                Student stu = null;
                try {
                    stu = sDao.get(stuNo, school);
                } catch (Exception e) {
                    continue;
                }

                if (stu == null) {
                    continue;
                }

                Test t = new Test();
                t.setStudent(stu);
                t.setSubject(subject);
                t.setNo(no);
                t.setClassNum(stu.getClassNum());

                // ⭐ BẮT BUỘC: thêm school vào Test  
                // JP: 必須：Testにschoolをセット
                t.setSchool(school);

                if (pointStr != null && pointStr.matches("^[0-9]{1,3}$")) {
                    t.setPoint(Integer.parseInt(pointStr));
                } else {
                    t.setPoint(0);
                }

                list.add(t);
            }
        }

        return list;
    }
}
