<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

    <c:param name="title">学校新規登録</c:param>

    <c:param name="content">

        <section class="me-4">

            <h2 class="mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                学校新規登録
            </h2>

            <form action="SchoolCreateExecute.action" method="post" class="px-4">

                <div class="mb-3">
                    <label class="form-label">学校コード</label>
                    <input type="text" name="cd" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">学校名</label>
                    <input type="text" name="name" class="form-control" required>
                </div>

                <div class="text-end mt-4">
                    <button class="btn btn-primary px-4">登録</button>
                </div>

            </form>

        </section>

    </c:param>

</c:import>
