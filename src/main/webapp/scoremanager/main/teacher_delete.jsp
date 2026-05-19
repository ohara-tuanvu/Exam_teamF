<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

    <c:param name="title">教員削除確認</c:param>
    <%-- Tiêu đề trang: Xác nhận xóa giáo viên --%>
    <%-- ページタイトル：教員削除確認 --%>

    <c:param name="content">

        <section class="me-4">

            <h2 class="mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                教員削除確認
                <%-- Tiêu đề lớn của trang --%>
                <%-- 画面の見出し --%>
            </h2>

            <div class="alert alert-danger mx-5 mt-4 text-center">
                以下の教員情報を削除します。よろしいですか？
                <%-- Cảnh báo: Bạn có chắc muốn xóa giáo viên này không? --%>
                <%-- 警告：この教員を削除してもよろしいですか？ --%>
            </div>

            <div class="mx-5 mt-4">

                <p><strong>教員ID：</strong> ${teacher.id}</p>
                <%-- Hiển thị ID giáo viên --%>
                <%-- 教員ID を表示 --%>

                <p><strong>氏名：</strong> ${teacher.name}</p>
                <%-- Hiển thị tên giáo viên --%>
                <%-- 教員氏名 を表示 --%>

                <p><strong>権限：</strong>
                    <c:choose>
                        <c:when test="${teacher.role == 1}">管理者</c:when>
                        <c:otherwise>教員</c:otherwise>
                    </c:choose>
                    <%-- Hiển thị quyền: 1 = quản trị, 0 = giáo viên --%>
                    <%-- 権限表示：1=管理者、0=教員 --%>
                </p>

            </div>

            <form action="TeacherDeleteExecute.action" method="post" class="text-center mt-4">
                <%-- Form gửi yêu cầu xóa giáo viên --%>
                <%-- 教員削除を実行するフォーム --%>

                <input type="hidden" name="id" value="${teacher.id}">
                <%-- Gửi ID giáo viên cần xóa (hidden) --%>
                <%-- 削除対象の教員IDを hidden で送信 --%>

                <button class="btn btn-danger px-4 me-3">削除</button>
                <%-- Nút xác nhận xóa --%>
                <%-- 削除ボタン --%>

                <a href="TeacherList.action" class="btn btn-secondary px-4">戻る</a>
                <%-- Quay lại danh sách giáo viên --%>
                <%-- 教員一覧に戻る --%>
            </form>

        </section>

    </c:param>

</c:import>
