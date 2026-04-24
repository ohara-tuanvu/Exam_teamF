package scoremanager.main;
// scoremanager/main モジュールに属する Action クラスをまとめるパッケージ
// Package chứa các Action thuộc module scoremanager/main

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;
// Action はリクエスト処理を行う基底クラス（自作フレームワーク）
// Action là lớp cha (framework tự xây) dùng để xử lý request

public class MenuAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ローカル変数の宣言（1）
        // ローカル変数は特に使用しない
        // Không có biến cục bộ cần khai báo

        // リクエストパラメータの取得（2）
        // メニュー画面ではパラメータ取得なし
        // Không lấy request parameter nào

        // DBからデータ取得（3）
        // メニュー表示のためDBアクセス不要
        // Không truy vấn DB ở màn hình menu

        // ビジネスロジック（4）
        // 特別なビジネスロジックなし
        // Không xử lý logic gì ở đây

        // DBへデータ保存（5）
        // 保存処理なし
        // Không lưu dữ liệu vào DB

        // レスポンス値をセット（6）
        // リクエスト属性の設定なし
        // Không set attribute nào cho request

        // JSPへフォワード（7）
        // メインメニュー画面（menu.jsp）へフォワード
        // Chuyển hướng sang menu.jsp để hiển thị menu chính
        req.getRequestDispatcher("menu.jsp").forward(req, res);
    }
}