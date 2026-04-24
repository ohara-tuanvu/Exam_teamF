package scoremanager.main;

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
            School school = (School) req.getSession().getAttribute("school");

            String subjectCd = req.getParameter("subject_cd");
            String noStr = req.getParameter("no");

            SubjectDao subDao = new SubjectDao();
            Subject subject = subDao.get(subjectCd, school);

            int no = Integer.parseInt(noStr);

            TestDao tDao = new TestDao();
            StudentDao sDao = new StudentDao();

            List<Test> testList = new ArrayList<>();

            int errorIndex = -1;
            String errorMessage = null;

            // ============================
            // 1. ループで学生ごとの点数を処理
            // ============================
            int index = 0;

            for (String param : req.getParameterMap().keySet()) {

                if (param.startsWith("student_no_")) {

                    String studentNo = req.getParameter(param);
                    String pointStr = req.getParameter("point_" + studentNo);

                    // ----------------------------
                    // Student取得（null対策追加）
                    // ----------------------------
                    Student stu = null;
                    try {
                        stu = sDao.get(studentNo, school);
                    } catch (Exception e) {
                        throw new ServletException(e);
                    }

                    if (stu == null) {
                        throw new ServletException("Student not found: " + studentNo);
                    }

                    // ----------------------------
                    // 点数バリデーション
                    // ----------------------------
                    int point = 0;

                    if (pointStr == null || pointStr.isEmpty()) {
                        errorIndex = index;
                        errorMessage = "点数を入力してください";
                        break;
                    }

                    if (!pointStr.matches("^[0-9]{1,3}$")) {
                        errorIndex = index;
                        errorMessage = "0～100の範囲で入力してください";
                        break;
                    }

                    point = Integer.parseInt(pointStr);

                    if (point < 0 || point > 100) {
                        errorIndex = index;
                        errorMessage = "0～100の範囲で入力してください";
                        break;
                    }

                    // ----------------------------
                    // Testオブジェクト作成
                    // ----------------------------
                    Test t = new Test();
                    t.setStudent(stu);
                    t.setSubject(subject);
                    t.setNo(no);
                    t.setPoint(point);
                    t.setClassNum(stu.getClassNum());

                    testList.add(t);
                    index++;
                }
            }

            // ============================
            // 2. エラーがあれば再表示
            // ============================
            if (errorIndex != -1) {

                List<Test> rebuildList = rebuildList(req, school, subject, no);

                req.setAttribute("testList", rebuildList);
                req.setAttribute("errorIndex", errorIndex);
                req.setAttribute("errorMessage", errorMessage);
                req.setAttribute("subject_name", subject.getName());

                req.getRequestDispatcher("/scoremanager/main/test_regist.jsp")
                        .forward(req, res);
                return;
            }

            // ============================
            // 3. DB登録
            // ============================
            tDao.save(testList, school);

            // ============================
            // 4. 完了画面へ
            // ============================
            req.getRequestDispatcher("/scoremanager/main/test_regist_done.jsp")
                    .forward(req, res);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // ==========================================================
    // 入力エラー時の再表示用リスト再構築（null対策追加）
    // ==========================================================
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
