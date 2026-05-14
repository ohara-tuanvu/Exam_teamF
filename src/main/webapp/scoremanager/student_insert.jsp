<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">学生登録</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">

        <style>
            h2 {
                background: rgba(255,255,255,0.55) !important;
                backdrop-filter: blur(4px);
                border-radius: 6px;
            }

            .glass-success {
                background: rgba(212, 237, 218, 0.75);
                backdrop-filter: blur(4px);
                border: 1px solid #c3e6cb;
                border-radius: 8px;
                padding: 12px 20px;
                color: #155724;
            }

            .glass-block {
                background: rgba(255,255,255,0.65);
                backdrop-filter: blur(6px);
                border: 1px solid #e5e5e5;
                border-radius: 10px;
                padding: 20px 25px;
                box-shadow: 0 2px 6px rgba(0,0,0,0.05);
            }

            .form-control, .form-select {
                background: rgba(255,255,255,0.85);
            }

            /* ⭐ Lỗi dưới input */
            .input-error {
                color: #ff8c00; /* cam */
                font-size: 0.85rem;
                margin-top: 4px;
                margin-left: 4px;
            }
        </style>

        <section class="w-75 m-auto border pb-3">

            <h2 class="h3 mb-3 fw-normal py-2">学生登録</h2>

            <c:if test="${not empty message}">
                <div class="glass-success mx-5 mt-2">
                    ${message}
                </div>

                <div class="mt-3 text-center">
                    <a href="StudentList.action" class="btn btn-secondary w-25">戻る</a>
                </div>
            </c:if>

            <c:if test="${empty message}">

                <!-- ⭐ KHÔNG còn danh sách lỗi ở trên -->

                <form action="StudentCreateExecute.action" method="post" class="glass-block mx-5">

                    <!-- 入学年度 -->
                    <div class="form-floating mt-3">
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

                        <c:if test="${not empty errors.entYear}">
                            <div class="input-error">${errors.entYear}</div>
                        </c:if>
                    </div>

                    <!-- 学生番号 -->
                    <div class="form-floating mt-3">
                        <input class="form-control" type="text" name="no" maxlength="10"
                               value="${no}" placeholder="学生番号を入力してください" />
                        <label>学生番号</label>

                        <c:if test="${not empty errors.no}">
                            <div class="input-error">${errors.no}</div>
                        </c:if>
                    </div>

                    <!-- 氏名 -->
                    <div class="form-floating mt-3">
                        <input class="form-control" type="text" name="name" maxlength="20"
                               value="${name}" placeholder="氏名を入力してください" />
                        <label>氏名</label>

                        <c:if test="${not empty errors.name}">
                            <div class="input-error">${errors.name}</div>
                        </c:if>
                    </div>

                    <!-- クラス -->
                    <div class="form-floating mt-3">
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

                        <c:if test="${not empty errors.classNum}">
                            <div class="input-error">${errors.classNum}</div>
                        </c:if>
                    </div>

                    <!-- 在学中 -->
                    <div class="form-check mt-3">
                        <input class="form-check-input" type="checkbox" name="isAttend" value="t"
                               <c:if test="${isAttend == 't'}">checked</c:if> />
                        <label class="form-check-label">在学中</label>
                    </div>

                    <div class="mt-4 text-center">
                        <input class="btn btn-primary w-25" type="submit" value="登録" />
                    </div>

                    <div class="mt-3 text-center">
                        <a href="StudentList.action" class="btn btn-outline-secondary ms-1 px-4">戻る</a>
                    </div>

                </form>

            </c:if>

        </section>

    </c:param>
</c:import>
