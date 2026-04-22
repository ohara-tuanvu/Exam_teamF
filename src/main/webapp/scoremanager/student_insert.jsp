<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Cấu hình JSP UTF-8 --%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%-- Import JSTL core --%>

<c:import url="/common/base.jsp">
    <%-- Nhúng layout base.jsp --%>

    <c:param name="title">学生登録</c:param>
    <%-- Tiêu đề trang --%>

    <c:param name="scripts"></c:param>
    <%-- Không có script riêng --%>

    <c:param name="content">
        <%-- Nội dung chính --%>

        <section class="w-75 m-auto border pb-3">
            <%-- Khung giao diện --%>

            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2">学生登録</h2>
            <%-- Tiêu đề --%>

            <%-- Nếu có message → chỉ hiển thị message + nút 戻る --%>
            <c:if test="${not empty message}">
                <div class="alert alert-success mx-5 mt-2">${message}</div>

                <div class="mt-3 text-center">
                    <a href="StudentList.action" class="btn btn-secondary w-25">戻る</a>
                </div>
            </c:if>

            <%-- Nếu KHÔNG có message → hiển thị form --%>
            <c:if test="${empty message}">

                <%-- Hiển thị lỗi validate nếu có --%>
                <c:if test="${errors != null}">
                    <ul class="text-danger">
                        <c:forEach var="err" items="${errors}">
                            <li>${err}</li>
                        </c:forEach>
                    </ul>
                </c:if>

                <form action="StudentCreateExecute.action" method="post">
                    <%-- Form gửi dữ liệu đăng ký --%>

                    <%-- 入学年度 --%>
                    <div class="form-floating mx-5 mt-3">
                        <select class="form-select" id="entYear" name="entYear">
                            <option value=""></option>

                            <c:forEach var="year" items="${ent_year_set}">
                                <option value="${year}"
                                    <c:if test="${year == entYear}">selected</c:if>>
                                    ${year}
                                </option>
                            </c:forEach>
                        </select>
                        <label>入学年度</label>
                    </div>

                    <%-- 学生番号 --%>
                    <div class="form-floating mx-5 mt-3">
                        <input class="form-control" type="text" name="no" maxlength="10"
                               value="${no}" placeholder="学生番号を入力してください" required />
                        <label>学生番号</label>
                    </div>

                    <%-- 氏名 --%>
                    <div class="form-floating mx-5 mt-3">
                        <input class="form-control" type="text" name="name" maxlength="20"
                               value="${name}" placeholder="氏名を入力してください" required />
                        <label>氏名</label>
                    </div>

                    <%-- クラス --%>
                    <div class="form-floating mx-5 mt-3">
                        <select class="form-select" name="classNum">
                            <option value=""></option>

                            <c:forEach var="num" items="${class_num_set}">
                                <option value="${num}"
                                    <c:if test="${num == classNum}">selected</c:if>>
                                    ${num}
                                </option>
                            </c:forEach>
                        </select>
                        <label>クラス</label>
                    </div>

                    <%-- 在学フラグ --%>
                    <div class="form-check mx-5 mt-3">
                        <input class="form-check-input" type="checkbox" name="isAttend" value="t"
                               <c:if test="${isAttend == 't'}">checked</c:if> />
                        <label class="form-check-label">在学中</label>
                    </div>

                    <%-- ボタン --%>
                    <div class="mt-4 text-center">
                        <input class="btn btn-primary w-25" type="submit" value="登録" />
                    </div>

                    <div class="mt-3 text-center">
                        <a href="StudentList.action">戻る</a>
                    </div>

                </form>

            </c:if>

        </section>

    </c:param>
</c:import>
