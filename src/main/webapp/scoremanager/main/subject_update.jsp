<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <%-- Tiêu đề trang gửi sang base.jsp --%>
    <c:param name="title">科目情報変更 - 得点管理システム</c:param>

    <%-- Nội dung chính của trang --%>
    <c:param name="content">
        <section class="me-4">

            <%-- Tiêu đề phần nội dung --%>
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>
            
            <%-- Nếu có message (tức là update thành công) thì hiển thị thông báo + nút 戻る --%>
            <c:if test="${not empty message}">
                <div class="alert alert-success mx-4 mt-2">${message}</div>

                <div class="mt-3 text-center">
                    <%-- Nút quay về danh sách môn học --%>
                    <a href="${pageContext.request.contextPath}/scoremanager/main/SubjectList.action"
                       class="btn btn-secondary w-25">戻る</a>
                </div>
            </c:if>

            <%-- Chỉ hiển thị form khi KHÔNG có message (tức là chưa update hoặc đang sửa lại) --%>
            <c:if test="${empty message}">
            
            <%-- Form gửi dữ liệu sang SubjectUpdateExecute.action để update DB --%>
            <form action="SubjectUpdateExecute.action" method="post" class="mt-4 px-4">
                
                <%-- Mã môn học: không cho sửa nên readonly --%>
                <div class="mb-3 w-50">
                    <label class="form-label">科目コード</label>
                    <input class="form-control bg-light" type="text" name="cd" 
                           value="${subject.cd}" readonly>
                </div>

                <%-- Tên môn học: cho phép sửa --%>
                <div class="mb-3 w-50">
                    <label class="form-label" for="subject-name-input">科目名</label>

                    <%-- Hiển thị tên môn học hiện tại --%>
                    <input class="form-control" type="text" id="subject-name-input" name="name" 
                           value="${subject.name}" required placeholder="科目名を入力してください">

                    <%-- Hiển thị lỗi validate nếu có --%>
                    <div class="text-danger small">${errors.get("name")}</div>
                </div>

                <%-- Nút submit để cập nhật --%>
                <div class="mt-4">
                    <button type="submit" class="btn btn-primary px-4">変更</button>
                </div>
                
                <%-- Nút quay lại danh sách môn học --%>
                <div class="mt-3">
                    <a href="SubjectList.action">戻る</a>
                </div>

            </form>
            </c:if>  <%-- Kết thúc phần hiển thị form --%>

        </section>
    </c:param>
</c:import>
