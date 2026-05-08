<%-- クラス更新画面 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">

    <c:param name="title">クラス変更</c:param>

    <c:param name="scripts">

        <style>

            /* Glass style */
            .glass-block {
                background: rgba(255,255,255,0.65);
                backdrop-filter: blur(6px);
                border: 1px solid #e5e5e5;
                border-radius: 8px;
                padding: 20px 25px;
                box-shadow: 0 2px 6px rgba(0,0,0,0.05);
            }

            h2 {
                background: rgba(255,255,255,0.55) !important;
                backdrop-filter: blur(4px);
                border-radius: 6px;
            }

            .form-control,
            .form-select {
                background: rgba(255,255,255,0.85);
                border: 1px solid #ccc;
            }

        </style>

    </c:param>

    <c:param name="content">

        <section class="me-4">

            <!-- Title -->
            <h2 class="mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                クラス変更
            </h2>

            <!-- Success message -->
            <c:if test="${not empty message}">

                <div class="alert alert-success mx-4 mt-2">
                    ${message}
                </div>

                <div class="mt-3 text-center">

                    <a href="${pageContext.request.contextPath}/scoremanager/main/ClassList.action"
                       class="btn btn-secondary w-25">

                        戻る

                    </a>

                </div>

            </c:if>

            <!-- Error messages -->
            <c:if test="${not empty errors}">

                <div class="alert alert-danger mx-4">

                    <c:forEach var="msg" items="${errors.values()}">
                        <div>${msg}</div>
                    </c:forEach>

                </div>

            </c:if>

            <!-- Form -->
            <c:if test="${empty message}">

                <form action="${pageContext.request.contextPath}/scoremanager/main/ClassUpdateExecute.action"
                      method="post"
                      class="px-4 glass-block">

                    <!-- 入学年度 -->
                    <div class="mb-3">

                        <label class="form-label">
                            入学年度
                        </label>

                        <select name="ent_year" class="form-select">

                            <c:forEach var="year" items="${ent_year_set}">

                                <option value="${year}"
                                    <c:if test="${year == cls.entYear}">
                                        selected
                                    </c:if>>

                                    ${year}

                                </option>

                            </c:forEach>

                        </select>

                    </div>

                    <!-- クラス番号 -->
                    <div class="mb-3">

                        <label class="form-label">
                            クラス番号
                        </label>

                        <!-- readonly recommended -->
                        <input type="text"
                               name="class_num"
                               class="form-control"
                               value="${cls.classNum}"
                               readonly>

                    </div>

                    <!-- Buttons -->
                    <div class="mt-4">

                        <button type="submit"
                                class="btn btn-primary">

                            更新

                        </button>

                        <a href="${pageContext.request.contextPath}/scoremanager/main/ClassList.action"
                           class="btn btn-secondary ms-2">

                            戻る

                        </a>

                    </div>

                </form>

            </c:if>

        </section>

    </c:param>

</c:import>