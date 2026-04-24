<%-- 学生更新画面（Trang cập nhật sinh viên） --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- JSPの文字コード設定 UTF-8（Cấu hình JSP UTF-8） --%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%-- JSTLコアタグライブラリをインポート（Import JSTL core） --%>

<c:import url="/common/base.jsp">
<%-- 共通レイアウト base.jsp を読み込む（Nhúng layout base.jsp） --%>

    <c:param name="title">学生変更</c:param>
    <%-- タイトル設定（Tiêu đề trang） --%>

    <c:param name="scripts"></c:param>
    <%-- スクリプトなし（Không có script riêng） --%>

    <c:param name="content">
    <%-- ページのメインコンテンツ（Nội dung chính của trang） --%>

        <section class="me-4">

            <h2 class="mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                学生変更
            </h2>
            <%-- 画面タイトル（Tiêu đề trang） --%>

            <!-- 成功メッセージ表示 -->
            <%-- 更新成功時メッセージ表示（Hiển thị message khi update thành công） --%>
            <c:if test="${not empty message}">
                <div class="alert alert-success mx-4 mt-2">${message}</div>

                <div class="mt-3 text-center">
                    <a href="${pageContext.request.contextPath}/scoremanager/main/StudentList.action"
                       class="btn btn-secondary w-25">戻る</a>
                </div>
            </c:if>

            <!-- エラーメッセージ表示 -->
            <%-- バリデーションエラー表示（Hiển thị lỗi validate） --%>
            <c:if test="${not empty errors}">
                <div class="alert alert-danger mx-4">
                    <c:forEach var="msg" items="${errors.values()}">
                        <%-- エラー一覧ループ（Lặp danh sách lỗi） --%>
                        <div>${msg}</div>
                    </c:forEach>
                </div>
            </c:if>

            <!-- 入力フォーム -->
            <%-- メッセージがない場合のみフォーム表示（Chỉ hiển thị form khi KHÔNG có message） --%>
            <c:if test="${empty message}">
            <form action="${pageContext.request.contextPath}/scoremanager/main/StudentUpdateExecute.action"
                  method="post" class="px-4">
            <%-- 更新処理へPOST送信（Submit đến StudentUpdateExecute.action） --%>

                <!-- 入学年度 -->
                <div class="mb-3">
                    <label class="form-label">入学年度</label>
                    <%-- 入学年度選択（Chọn năm nhập học） --%>

                    <select name="ent_year" class="form-select">
                        <c:forEach var="year" items="${ent_year_set}">
                            <%-- 年度一覧ループ（Lặp danh sách năm） --%>

                            <option value="${year}"
                                <c:if test="${year == student.entYear}">selected</c:if>>
                                <%-- 選択値保持（Giữ lại giá trị đã chọn） --%>
                                ${year}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <!-- 学生番号 -->
                <div class="mb-3">
                    <label class="form-label">学生番号</label>
                    <%-- 学生番号表示（Mã sinh viên - chỉ đọc） --%>

                    <input type="text" name="no" class="form-control"
                           value="${student.no}" readonly>
                </div>

                <!-- 氏名 -->
                <div class="mb-3">
                    <label class="form-label">氏名</label>
                    <%-- 氏名入力（Nhập tên sinh viên） --%>

                    <input type="text" name="name" class="form-control"
                           value="${student.name}">
                </div>

                <!-- クラス -->
                <div class="mb-3">
                    <label class="form-label">クラス</label>
                    <%-- クラス選択（Chọn lớp） --%>

                    <select name="class_num" class="form-select">
                        <c:forEach var="num" items="${class_num_set}">
                            <%-- クラス一覧ループ（Lặp danh sách lớp） --%>

                            <option value="${num}"
                                <c:if test="${num == student.classNum}">selected</c:if>>
                                <%-- 選択値保持（Giữ lại lớp đã chọn） --%>
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
                    <%-- 在学中フラグ（Checkbox trạng thái đang học） --%>

                    <label class="form-check-label">在学中</label>
                </div>

                <!-- ボタン -->
                <div class="mt-4">
                    <button type="submit" class="btn btn-primary">更新</button>
                    <%-- 更新実行（Thực hiện cập nhật） --%>

                    <a href="${pageContext.request.contextPath}/scoremanager/main/StudentList.action"
                       class="btn btn-secondary ms-2">戻る</a>
                    <%-- 一覧画面へ戻る（Quay lại danh sách） --%>
                </div>

            </form>
            </c:if>

        </section>

    </c:param>
</c:import>