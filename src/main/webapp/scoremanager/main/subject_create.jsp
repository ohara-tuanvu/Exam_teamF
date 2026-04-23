<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">科目情報登録 - 得点管理システム</c:param>
    <c:param name="content">
        <section class="me-4">
            <%-- Tiêu đề trang --%>
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報登録</h2>

            <%-- Action trỏ đến file xử lý lưu DB --%>
            <form action="SubjectCreateExecute.action" method="post" class="mt-4 px-4">
                
                <div class="mb-3 w-50">
                    <label class="form-label" for="subject-cd-input">科目コード</label>
                    <%-- Dùng value="${cd}" để giữ lại giá trị cũ nếu có lỗi xảy ra --%>
                    <input class="form-control" type="text" id="subject-cd-input" name="cd" 
                           value="${cd}" placeholder="科目コードを入力してください">
                    <%-- Hiển thị lỗi từ Map errors --%>
                    <div class="text-danger small">${errors.get("cd")}</div>
                </div>

                <div class="mb-3 w-50">
                    <label class="form-label" for="subject-name-input">科目名</label>
                    <input class="form-control" type="text" id="subject-name-input" name="name" 
                           value="${name}" placeholder="科目名を入力してください">
                    <div class="text-danger small">${errors.get("name")}</div>
                </div>

                <div class="mt-4">
                    <%-- Nút submit để gửi form --%>
                    <button type="submit" class="btn btn-primary">登録</button>
                </div>
                
                <div class="mt-3">
                    <%-- SỬA TẠI ĐÂY: Quay về trang danh sách môn học --%>
                    <a href="SubjectList.action">戻る</a>
                </div>
            </form>
        </section>
    </c:param>
</c:import>