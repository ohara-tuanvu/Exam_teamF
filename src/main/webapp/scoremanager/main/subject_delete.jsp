<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">科目情報削除 - 得点管理システム</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4 text-danger">科目情報削除</h2>

            <div class="mt-4 px-4">
                <div class="alert alert-warning">
                    <p class="mb-0">以下の科目を削除します。よろしいですか？</p>
                </div>

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

                <%-- Gửi mã cd đến SubjectDeleteExecuteAction để thực hiện lệnh xóa --%>
                <form action="SubjectDeleteExecute.action" method="post" class="mt-4">
                    <%-- Input ẩn để truyền mã CD đi mà không cần hiển thị --%>
                    <input type="hidden" name="cd" value="${subject.cd}">
                    
                    <button type="submit" class="btn btn-danger px-4">削除</button>
                    <a href="SubjectList.action" class="btn btn-outline-secondary ms-2 px-4">キャンセル</a>
                </form>
            </div>
        </section>
    </c:param>
</c:import>