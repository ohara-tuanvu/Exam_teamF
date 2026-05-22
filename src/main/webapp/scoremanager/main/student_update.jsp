<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">

    <c:param name="title">学生変更</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">

        <!-- VI: CSS tạo hiệu ứng nền mờ cho form -->
        <!-- JP: フォームを見やすくするガラス風デザインCSS -->
        <style>
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

            .form-control, .form-select {
                background: rgba(255,255,255,0.85);
                border: 1px solid #ccc;
            }

            .form-check-input {
                transform: scale(1.2);
            }
        </style>

        <section class="me-4">

            <h2 class="mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                学生変更
            </h2>

            <!-- VI: Hiển thị thông báo thành công -->
            <!-- JP: 更新成功メッセージ表示 -->
            <c:if test="${not empty message}">
                <div class="alert alert-success mx-4 mt-2">${message}</div>

                <div class="mt-3 text-center">
                    <a href="${pageContext.request.contextPath}/scoremanager/main/StudentList.action"
                       class="btn btn-secondary w-25">戻る</a>
                </div>
            </c:if>

            <!-- VI: Hiển thị lỗi nhập liệu -->
            <!-- JP: 入力エラーメッセージ表示 -->
            <c:if test="${not empty errors}">
                <div class="alert alert-danger mx-4">
                    <c:forEach var="msg" items="${errors.values()}">
                        <div>${msg}</div>
                    </c:forEach>
                </div>
            </c:if>

            <!-- VI: Form cập nhật (ẩn khi đã cập nhật thành công) -->
            <!-- JP: 更新フォーム（成功時は非表示） -->
            <c:if test="${empty message}">
            <form action="${pageContext.request.contextPath}/scoremanager/main/StudentUpdateExecute.action"
                  method="post" class="px-4 glass-block">

                <!-- VI: Năm nhập học -->
                <!-- JP: 入学年度 -->
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

                <!-- VI: Mã số sinh viên (không cho sửa) -->
                <!-- JP: 学生番号（編集不可） -->
                <div class="mb-3">
                    <label class="form-label">学生番号</label>
                    <input type="text" name="no" class="form-control"
                           value="${student.no}" readonly>
                </div>

                <!-- VI: Tên sinh viên -->
                <!-- JP: 氏名 -->
                <div class="mb-3">
                    <label class="form-label">氏名</label>
                    <input type="text" name="name" class="form-control"
                           value="${student.name}">
                </div>

                <!-- VI: Lớp -->
                <!-- JP: クラス -->
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

                <!-- VI: SUPERADMIN được phép đổi trường -->
                <!-- JP: SUPERADMIN のみ学校変更が可能 -->
                <c:if test="${user.role == 2}">
                    <div class="mb-3">
                        <label class="form-label">学校</label>
                        <select name="school_cd" class="form-select">
                            <c:forEach var="sc" items="${school_list}">
                                <option value="${sc.cd}"
                                    <c:if test="${sc.cd == student.school.cd}">selected</c:if>>
                                    ${sc.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </c:if>

                <!-- VI: Trạng thái đang học -->
                <!-- JP: 在学中フラグ -->
                <div class="form-check mb-3">
                    <input type="checkbox" name="is_attend" value="true"
                           class="form-check-input"
                           <c:if test="${student.attend}">checked</c:if>>
                    <label class="form-check-label">在学中</label>
                </div>

                <!-- VI: Nút cập nhật và quay lại -->
                <!-- JP: 更新ボタン・戻るボタン -->
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
