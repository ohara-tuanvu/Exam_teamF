<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">科目情報変更 - 得点管理システム</c:param>
    <c:param name="content">

        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>

            <%-- Gửi dữ liệu đến SubjectUpdateExecuteAction để lưu vào DB --%>
            <%-- SubjectUpdateExecuteAction にデータを送信して DB に保存する --%>
            <form action="SubjectUpdateExecute.action" method="post" class="mt-4 px-4">

                <div class="mb-3 w-50">
                    <label class="form-label">科目コード</label>
                    <%-- Mã môn học (CD) thường không được phép sửa nên để readonly --%>
                    <%-- 科目コード（CD）は通常編集不可のため readonly に設定 --%>
                    <input class="form-control bg-light" type="text" name="cd" value="${subject.cd}" readonly>
                </div>

                <div class="mb-3 w-50">
                    <label class="form-label" for="subject-name-input">科目名</label>
                    <%-- Hiển thị tên cũ từ biến 'subject' đã setAttribute ở Action --%>
                    <%-- Action で setAttribute された subject から既存の科目名を表示 --%>
                    <input class="form-control" type="text" id="subject-name-input" name="name"
                           value="${subject.name}" required placeholder="科目名を入力してください">
                    <div class="text-danger small">${errors.get("name")}</div>
                </div>

                <div class="mt-4">
                    <button type="submit" class="btn btn-primary px-4">変更</button>
                </div>

                <div class="mt-3">
                    <a href="SubjectList.action">戻る</a>
                </div>

            </form>
        </section>

    </c:param>
</c:import>