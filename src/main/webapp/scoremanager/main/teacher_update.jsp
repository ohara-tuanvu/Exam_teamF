<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

    <c:param name="title">教員情報変更</c:param>
    <%-- Tiêu đề trang: Thay đổi thông tin giáo viên --%>
    <%-- ページタイトル：教員情報変更 --%>

    <c:param name="content">

        <section class="me-4">

            <h2 class="mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                教員情報変更
                <%-- Tiêu đề lớn của trang --%>
                <%-- 画面の見出し --%>
            </h2>

            <form action="TeacherUpdateExecute.action" method="post" class="px-4">
                <%-- Form gửi dữ liệu cập nhật sang TeacherUpdateExecute.action --%>
                <%-- 教員更新データを TeacherUpdateExecute.action に送信するフォーム --%>

                <!-- ID không cho sửa -->
                <!-- ID は変更不可 -->
                <div class="mb-3">
                    <label class="form-label">教員ID</label>
                    <input type="text" class="form-control" value="${teacher.id}" disabled>
                    <%-- Hiển thị ID nhưng không cho sửa --%>
                    <%-- 表示のみ、編集不可 --%>

                    <input type="hidden" name="id" value="${teacher.id}">
                    <%-- Gửi ID thật bằng hidden --%>
                    <%-- 実際のIDは hidden で送信 --%>
                </div>

                <div class="mb-3">
                    <label class="form-label">氏名</label>
                    <input type="text" name="name" class="form-control" value="${teacher.name}" required>
                    <%-- Nhập tên giáo viên --%>
                    <%-- 教員氏名入力欄 --%>
                </div>

                <div class="mb-3">
                    <label class="form-label">パスワード</label>
                    <input type="password" name="password" class="form-control" value="${teacher.password}" required>
                    <%-- Nhập mật khẩu mới --%>
                    <%-- 新しいパスワード入力欄 --%>
                </div>

                <div class="mb-3">
                    <label class="form-label">権限</label>
                    <select name="role" class="form-select">
                        <option value="0" ${teacher.role == 0 ? "selected" : ""}>教員</option>
                        <option value="1" ${teacher.role == 1 ? "selected" : ""}>管理者</option>
                    </select>
                    <%-- Chọn quyền: 0 = giáo viên, 1 = admin chi nhánh --%>
                    <%-- 権限選択：0=教員、1=管理者 --%>
                </div>

                <%-- ⭐ Lấy user đang đăng nhập --%>
                <%-- ⭐ ログイン中のユーザーを取得 --%>
                <%
                    bean.Teacher loginUser = (bean.Teacher) session.getAttribute("user");
                %>

                <%-- ⭐ SuperAdmin (role 2) → được chọn trường --%>
                <%-- ⭐ superadmin(role=2) → 学校選択可能 --%>
                <% if (loginUser.getRole() == 2) { %>
                    <div class="mb-3">
                        <label class="form-label">学校</label>
                        <select name="school_cd" class="form-select" required>
                            <c:forEach var="s" items="${school_list}">
                                <option value="${s.cd}"
                                    ${teacher.schoolCd == s.cd ? "selected" : ""}>
                                    ${s.name}
                                </option>
                            </c:forEach>
                        </select>
                        <%-- SuperAdmin có thể đổi trường của giáo viên --%>
                        <%-- superadmin は教員の学校を変更可能 --%>
                    </div>

                <%-- ⭐ Admin chi nhánh (role 1) → KHÔNG được chọn trường --%>
                <%-- ⭐ 支部管理者(role=1) → 学校変更不可 --%>
                <% } else if (loginUser.getRole() == 1) { %>
                    <input type="hidden" name="school_cd" value="${teacher.schoolCd}">
                    <%-- Gửi school_cd hiện tại lên backend --%>
                    <%-- 現在の school_cd を hidden で送信 --%>

                    <p class="text-muted">学校: ${teacher.school.name}</p>
                    <%-- Hiển thị tên trường nhưng không cho sửa --%>
                    <%-- 学校名を表示（編集不可） --%>
                <% } %>

                <div class="text-end mt-4">
                    <button class="btn btn-primary px-4">変更</button>
                    <%-- Nút cập nhật thông tin giáo viên --%>
                    <%-- 教員情報更新ボタン --%>
                </div>

            </form>

        </section>

    </c:param>

</c:import>
