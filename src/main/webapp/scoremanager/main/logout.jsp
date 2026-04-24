<%-- ログアウト画面（Trang logout） --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> <%-- JSPの文字コード設定 UTF-8（Cấu hình JSP UTF-8） --%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%> <%-- JSTLコアタグライブラリをインポート（Import JSTL core） --%>

<c:import url="/common/base.jsp"> <%-- 共通レイアウト base.jsp を読み込む（Nhúng layout base.jsp） --%>

    <c:param name="title"> <%-- タイトルをbase.jspに渡す（Gửi tiêu đề trang sang base.jsp） --%>
        得点管理システム
    </c:param>

    <c:param name="content"> <%-- ページのメインコンテンツ（Nội dung chính của trang logout） --%>

        <div id="wrap_box"> <%-- 全体レイアウト枠（Khung bao toàn bộ nội dung） --%>

            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2">
                ログアウト
            </h2>
            <%-- 画面タイトル表示（Hiển thị tiêu đề logout） --%>

            <div id="wrap_box"> <%-- メッセージ表示エリア（Khung hiển thị thông báo） --%>

                <p class="text-center" style="background-color:#66CC99">
                    ログアウトしました
                </p>
                <%-- ログアウト完了メッセージ（Thông báo đăng xuất thành công） --%>

                <a href="${pageContext.request.contextPath}/scoremanager/main/login.jsp">
                    ログイン
                </a>
                <%-- ログイン画面へ遷移（Chuyển về trang login） --%>
                <%-- contextPathを使ってプロジェクト名に依存しないURLを生成（Tạo URL không phụ thuộc tên project） --%>

            </div>
        </div>

    </c:param>
</c:import>