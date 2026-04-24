package scoremanager.main;
// Package chứa các Action thuộc module scoremanager/main
// scoremanager/main モジュールに属する Action クラスをまとめるパッケージ

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;
// Action là lớp cha (framework tự xây) dùng để xử lý request
// Action はリクエスト処理を行う基底クラス（自作フレームワーク）

public class MenuAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ローカル変数の宣言 1
        // なし
        // Không có biến cục bộ cần khai báo
        // ローカル変数は特に使用しない

        // リクエストパラメータ―の取得 2
        // なし
        // Không lấy request parameter nào
        // メニュー画面ではパラメータ取得なし

        // DBからデータ取得 3
        // なし
        // Không truy vấn DB ở màn hình menu
        // メニュー表示のためDBアクセス不要

        // ビジネスロジック 4
        // なし
        // Không xử lý logic gì ở đây
        // 特別なビジネスロジックなし

        // DBへデータ保存 5
        // なし
        // Không lưu gì vào DB
        // 保存処理なし

        // レスポンス値をセット 6
        // なし
        // Không set attribute nào cho request
        // リクエスト属性の設定なし

        // JSPへフォワード 7
        // Chuyển hướng sang trang menu.jsp để hiển thị menu chính
        // メインメニュー画面（menu.jsp）へフォワード
        req.getRequestDispatcher("menu.jsp").forward(req, res);
    }
}
