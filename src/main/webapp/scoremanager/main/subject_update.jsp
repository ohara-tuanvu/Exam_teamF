<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<<<<<<< HEAD
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">科目情報変更 - 得点管理システム</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>

            <%-- Gửi dữ liệu đến SubjectUpdateExecuteAction để lưu vào DB --%>
            <form action="SubjectUpdateExecute.action" method="post" class="mt-4 px-4">
                
                <div class="mb-3 w-50">
                    <label class="form-label">科目コード</label>
                    <%-- Mã môn học (CD) thường không được phép sửa nên để readonly --%>
                    <input class="form-control bg-light" type="text" name="cd" 
                           value="${subject.cd}" readonly>
                </div>

                <div class="mb-3 w-50">
                    <label class="form-label" for="subject-name-input">科目名</label>
                    <%-- Hiển thị tên cũ từ biến 'subject' đã setAttribute ở Action --%>
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
=======
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

>>>>>>> branch 'master' of https://github.com/ohara-tuanvu/Exam_teamF.git
    </c:param>
</c:import>