<%-- 科目削除確認画面（Trang xác nhận xóa môn học） --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- JSPの文字コード設定 UTF-8（Cấu hình JSP UTF-8） --%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%-- JSTLコアタグライブラリをインポート（Import JSTL core） --%>

<c:import url="/common/base.jsp">
<%-- 共通レイアウト base.jsp を読み込む（Nhúng layout base.jsp） --%>

    <c:param name="title">科目情報削除 - 得点管理システム</c:param>
    <%-- ページタイトル設定（Tiêu đề trang） --%>

    <c:param name="content">
    <%-- メインコンテンツ（Nội dung chính） --%>

        <section class="me-4">

            <!-- タイトル -->
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4 text-danger">
                科目情報削除
            </h2>
            <%-- 削除画面タイトル（Tiêu đề trang xóa） --%>

            <div class="mt-4 px-4">

                <!-- 確認メッセージ -->
                <div class="alert alert-warning">
                    <p class="mb-0">
                        以下の科目を削除します。よろしいですか？
                    </p>
                </div>
                <%-- 削除確認メッセージ（Thông báo xác nhận xóa） --%>

                <!-- 科目情報表示 -->
                <table class="table table-bordered w-50 mt-4">
                    <tr>
                        <th class="table-light w-25">科目コード</th>
                        <td>${subject.cd}</td>
                    </tr>
                    <tr>
                        <th class="table-light w-25">科目名</th>
                        <td>${subject.name}</td>
                    </tr>
                </table>
                <%-- 削除対象の科目情報表示（Hiển thị thông tin môn học sẽ bị xóa） --%>

                <!-- 削除実行フォーム -->
                <form action="SubjectDeleteExecute.action" method="post" class="mt-4">
                <%-- 削除処理へPOST送信（Gửi request xóa dữ liệu） --%>

                    <!-- hidden: 科目コード -->
                    <input type="hidden" name="cd" value="${subject.cd}">
                    <%-- 削除対象IDを送信（Truyền mã môn học để xóa） --%>

                    <!-- ボタン -->
                    <button type="submit" class="btn btn-danger px-4">削除</button>
                    <%-- 削除実行ボタン（Thực hiện xóa） --%>

                    <a href="SubjectList.action" class="btn btn-outline-secondary ms-2 px-4">
                        キャンセル
                    </a>
                    <%-- 一覧へ戻る（Hủy và quay lại danh sách） --%>

                </form>

            </div>
        </section>

    </c:param>
</c:import>