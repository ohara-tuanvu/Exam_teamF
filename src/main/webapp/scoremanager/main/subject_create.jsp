<%-- 科目登録画面（Trang đăng ký môn học） --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- JSPの文字コード設定 UTF-8（Cấu hình JSP UTF-8） --%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%-- JSTLコアタグライブラリをインポート（Import JSTL core） --%>

<c:import url="/common/base.jsp">
<%-- 共通レイアウト base.jsp を読み込む（Nhúng layout base.jsp） --%>

    <c:param name="title">科目情報登録 - 得点管理システム</c:param>
    <%-- タイトル設定（Tiêu đề trang） --%>

    <c:param name="content">
    <%-- ページのメインコンテンツ（Nội dung chính của trang） --%>

        <section class="me-4">

            <!-- タイトル -->
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                科目情報登録
            </h2>
            <%-- 画面タイトル（Tiêu đề trang） --%>

            <!-- 入力フォーム -->
            <form action="SubjectCreateExecute.action" method="post" class="mt-4 px-4">
            <%-- 登録処理へPOST送信（Action xử lý lưu DB） --%>

                <!-- 科目コード -->
                <div class="mb-3 w-50">
                    <label class="form-label" for="subject-cd-input">科目コード</label>
                    <%-- 科目コード入力（Nhập mã môn học） --%>

                    <input class="form-control" type="text" id="subject-cd-input" name="cd" 
                           value="${cd}" placeholder="科目コードを入力してください">
                    <%-- 入力値保持（Giữ lại giá trị khi có lỗi） --%>

                    <div class="text-danger small">${errors.get("cd")}</div>
                    <%-- エラーメッセージ表示（Hiển thị lỗi） --%>
                </div>

                <!-- 科目名 -->
                <div class="mb-3 w-50">
                    <label class="form-label" for="subject-name-input">科目名</label>
                    <%-- 科目名入力（Nhập tên môn học） --%>

                    <input class="form-control" type="text" id="subject-name-input" name="name" 
                           value="${name}" placeholder="科目名を入力してください">

                    <div class="text-danger small">${errors.get("name")}</div>
                    <%-- エラーメッセージ表示（Hiển thị lỗi） --%>
                </div>

                <!-- 登録ボタン -->
                <div class="mt-4">
                    <button type="submit" class="btn btn-primary">登録</button>
                    <%-- 登録実行（Submit form） --%>
                </div>
                
                <!-- 戻るリンク -->
                <div class="mt-3">
                    <a href="SubjectList.action">戻る</a>
                    <%-- 科目一覧画面へ遷移（Quay lại danh sách môn học） --%>
                </div>

            </form>
        </section>

    </c:param>
</c:import>