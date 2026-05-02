<%-- 学生更新画面（Trang cập nhật sinh viên） --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">

    <c:param name="title">学生変更</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">

        <!-- ⭐ CSS làm form nổi rõ nhưng vẫn thấy background -->
        <style>
            /* Khối form glass */
            .glass-block {
                background: rgba(255,255,255,0.65);
                backdrop-filter: blur(6px);
                border: 1px solid #e5e5e5;
                border-radius: 8px;
                padding: 20px 25px;
                box-shadow: 0 2px 6px rgba(0,0,0,0.05);
            }

            /* Header form */
            h2 {
                background: rgba(255,255,255,0.55) !important;
                backdrop-filter: blur(4px);
                border-radius: 6px;
            }

            /* Input rõ hơn */
            .form-control, .form-select {
                background: rgba(255,255,255,0.85);
                border: 1px solid #ccc;
            }

            /* Checkbox rõ hơn */
            .form-check-input {
                transform: scale(1.2);
            }
        </style>

        <section class="me-4">

            <h2 class="mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                学生変更
            </h2>

            <!-- 成功メッセージ表示 -->
            <c:if test="${not empty message}">
                <div class="alert alert-success mx-4 mt-2">${message}</div>

                <div class="mt-3 text-center">
                    <a href="${pageContext.request.contextPath}/scoremanager/main/StudentList.action"
                       class="btn btn-secondary w-25">戻る</a>
                </div>
            </c:if>

            <!-- エラーメッセージ表示 -->
            <c:if test="${not empty errors}">
                <div class="alert alert-danger mx-4">
                    <c:forEach var="msg" items="${errors.values()}">
                        <div>${msg}</div>
                    </c:forEach>
                </div>
            </c:if>

            <!-- 入力フォーム -->
            <c:if test="${empty message}">
            <form action="${pageContext.request.contextPath}/scoremanager/main/StudentUpdateExecute.action"
                  method="post" class="px-4 glass-block">

                <!-- 入学年度 -->
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

                <!-- 学生番号 -->
                <div class="mb-3">
                    <label class="form-label">学生番号</label>
                    <input type="text" name="no" class="form-control"
                           value="${student.no}" readonly>
                </div>

                <!-- 氏名 -->
                <div class="mb-3">
                    <label class="form-label">氏名</label>
                    <input type="text" name="name" class="form-control"
                           value="${student.name}">
                </div>

                <!-- クラス -->
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

                <!-- 在学中 -->
                <div class="form-check mb-3">
                    <input type="checkbox" name="is_attend" value="true"
                           class="form-check-input"
                           <c:if test="${student.attend}">checked</c:if>>
                    <label class="form-check-label">在学中</label>
                </div>

                <!-- ボタン -->
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
