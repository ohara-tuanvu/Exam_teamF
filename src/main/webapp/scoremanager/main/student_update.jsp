<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">

    <c:param name="title">学生変更</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">

        <section class="me-4">

            <h2 class="mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生変更</h2>

            <!-- Hiển thị message khi update thành công -->
            <c:if test="${not empty message}">
                <div class="alert alert-success mx-4 mt-2">${message}</div>

                <div class="mt-3 text-center">
                    <a href="${pageContext.request.contextPath}/scoremanager/main/StudentList.action"
                       class="btn btn-secondary w-25">戻る</a>
                </div>
            </c:if>

            <!-- Hiển thị lỗi validate -->
            <c:if test="${not empty errors}">
                <div class="alert alert-danger mx-4">
                    <c:forEach var="msg" items="${errors.values()}">
                        <div>${msg}</div>
                    </c:forEach>
                </div>
            </c:if>

            <!-- Chỉ hiển thị form khi KHÔNG có message -->
            <c:if test="${empty message}">
            <form action="${pageContext.request.contextPath}/scoremanager/main/StudentUpdateExecute.action"
                  method="post" class="px-4">

                <div class="mb-3">
                    <label class="form-label">入学年度</label>
                    <select name="ent_year" class="form-select">
                        <c:forEach var="year" items="${ent_year_set}">
                            <option value="${year}"
                                <c:if test="${year == student.entYear}">selected</c:if>>
                                ${year}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-3">
                    <label class="form-label">学生番号</label>
                    <input type="text" name="no" class="form-control"
                           value="${student.no}" readonly>
                </div>

                <div class="mb-3">
                    <label class="form-label">氏名</label>
                    <input type="text" name="name" class="form-control"
                           value="${student.name}">
                </div>

                <div class="mb-3">
                    <label class="form-label">クラス</label>
                    <select name="class_num" class="form-select">
                        <c:forEach var="num" items="${class_num_set}">
                            <option value="${num}"
                                <c:if test="${num == student.classNum}">selected</c:if>>
                                ${num}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-check mb-3">
                    <input type="checkbox" name="is_attend" value="true"
                           class="form-check-input"
                           <c:if test="${student.attend}">checked</c:if>>
                    <label class="form-check-label">在学中</label>
                </div>

                <div class="mt-4">
                    <button type="submit" class="btn btn-primary">更新</button>

                    <a href="${pageContext.request.contextPath}/scoremanager/main/StudentList.action"
                       class="btn btn-secondary ms-2">戻る</a>
                </div>

            </form>
            </c:if>

        </section>

    </c:param>
</c:import>
