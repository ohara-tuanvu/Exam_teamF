<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- JSPの文字コード設定 UTF-8（Cấu hình JSP UTF-8） --%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%-- JSTLコアタグライブラリをインポート（Import JSTL core） --%>

<c:import url="/common/base.jsp">
<%-- 共通レイアウト base.jsp を読み込む（Nhúng layout base.jsp） --%>

    <c:param name="title">科目情報変更 - 得点管理システム</c:param>
    <%-- ページタイトル（Tiêu đề trang） --%>

    <c:param name="content">
    <%-- メインコンテンツ（Nội dung chính） --%>

        <section class="me-4">

            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                科目情報変更
            </h2>
            <%-- 画面タイトル（Tiêu đề thay đổi môn học） --%>

            <%-- データをSubjectUpdateExecuteActionに送信してDBに保存（Gửi dữ liệu đến SubjectUpdateExecuteAction để lưu vào DB） --%>
            <form action="SubjectUpdateExecute.action" method="post" class="mt-4 px-4">
                
                <div class="mb-3 w-50">
                    <label class="form-label">科目コード</label>
                    <%-- 科目コードは通常変更不可のためreadonly（Mã môn học thường không cho phép sửa nên để readonly） --%>
                    <input class="form-control bg-light" type="text" name="cd" 
                           value="${subject.cd}" readonly>
                </div>

                <div class="mb-3 w-50">
                    <label class="form-label" for="subject-name-input">科目名</label>
                    <%-- ActionでsetAttributeされたsubjectから既存データを表示（Hiển thị dữ liệu cũ từ biến subject đã setAttribute ở Action） --%>
                    <input class="form-control" type="text" id="subject-name-input" name="name" 
                           value="${subject.name}" required placeholder="科目名を入力してください">
                    <%-- バリデーションエラー表示（Hiển thị lỗi validation） --%>
                    <div class="text-danger small">${errors.get("name")}</div>
                </div>

                <div class="mt-4">
                    <button type="submit" class="btn btn-primary px-4">変更</button>
                    <%-- 更新ボタン（Nút cập nhật） --%>
                </div>
                
                <div class="mt-3">
                    <a href="SubjectList.action">戻る</a>
                    <%-- 一覧画面へ戻る（Quay lại danh sách môn học） --%>
                </div>

            </form>
        </section>

    </c:param>
</c:import>