package scoremanager.main;

import java.io.IOException;
import java.util.List;

import bean.ClassNum;
import bean.School;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class ClassListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            // セッションから教員を取得
        	Teacher teacher = (Teacher) req.getSession().getAttribute("user");


            // 学校情報取得
            School school = teacher.getSchool();

            // クラス一覧取得
            ClassNumDao dao = new ClassNumDao();
            List<ClassNum> list = dao.findBySchool(school);

            // JSPへ渡す
            req.setAttribute("class_list", list);

            // JSPへフォワード
            req.getRequestDispatcher("/scoremanager/main/class_list.jsp").forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}
