<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">

    <c:param name="title">科目変更</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">

        <section class="me-4">

            <!-- ① タイトル -->
            <h2 class="mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                科目情報変更
            </h2>

            <!-- 成功メッセージ -->
            <c:if test="${not empty message}">
                <div class="alert alert-success mx-4 mt-2">
                    ${message}
                </div>

                <div class="mt-3 text-center">
                    <a href="${pageContext.request.contextPath}/scoremanager/main/SubjectList.action"
                       class="btn btn-secondary w-25">戻る</a>
                </div>
            </c:if>

            <!-- エラーメッセージ -->
            <c:if test="${not empty error}">
                <div class="alert alert-danger mx-4">
                    ${error}
                </div>
            </c:if>

            <!-- フォーム -->
            <c:if test="${empty message}">
            <form action="${pageContext.request.contextPath}/scoremanager/main/SubjectUpdateExecute.action"
                  method="post" class="px-4">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">

    <c:param name="title">科目変更</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">

        <section class="me-4">

            <!-- ① タイトル -->
            <h2 class="mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                科目情報変更
            </h2>

            <!-- 成功メッセージ -->
            <c:if test="${not empty message}">
                <div class="alert alert-success mx-4 mt-2">
                    ${message}
                </div>

                <div class="mt-3 text-center">
                    <a href="${pageContext.request.contextPath}/scoremanager/main/SubjectList.action"
                       class="btn btn-secondary w-25">戻る</a>
                </div>
            </c:if>

            <!-- エラーメッセージ -->
            <c:if test="${not empty error}">
                <div class="alert alert-danger mx-4">
                    ${error}
                </div>
            </c:if>

            <!-- フォーム -->
            <c:if test="${empty message}">
            <form action="${pageContext.request.contextPath}/scoremanager/main/SubjectUpdateExecute.action"
                  method="post" class="px-4">

                <!-- ②③ 科目コード -->
                <div class="mb-3">
                    <label class="form-label">科目コード</label>
                    <input type="text" name="cd" class="form-control"
                           value="${subject.cd}" readonly>
                </div>

                <!-- ④⑤ 科目名 -->
                <div class="mb-3">
                    <label class="form-label">科目名</label>
                    <input type="text" name="name" class="form-control"
                           value="${subject.name}" required>
                </div>

                <!-- ⑥ ボタン -->
                <div class="mt-4">
                    <button type="submit" class="btn btn-primary">変更</button>

                    <!-- ⑦ 戻る -->
                    <a href="${pageContext.request.contextPath}/scoremanager/main/SubjectList.action"
                       class="btn btn-secondary ms-2">戻る</a>
                </div>

            </form>
            </c:if>

        </section>

    </c:param>
</c:import>
                <!-- ②③ 科目コード -->
                <div class="mb-3">
                    <label class="form-label">科目コード</label>
                    <input type="text" name="cd" class="form-control"
                           value="${subject.cd}" readonly>
                </div>

                <!-- ④⑤ 科目名 -->
                <div class="mb-3">
                    <label class="form-label">科目名</label>
                    <input type="text" name="name" class="form-control"
                           value="${subject.name}" required>
                </div>

                <!-- ⑥ ボタン -->
                <div class="mt-4">
                    <button type="submit" class="btn btn-primary">変更</button>

                    <!-- ⑦ 戻る -->
                    <a href="${pageContext.request.contextPath}/scoremanager/main/SubjectList.action"
                       class="btn btn-secondary ms-2">戻る</a>
                </div>

            </form>
            </c:if>

        </section>

    </c:param>
</c:import>