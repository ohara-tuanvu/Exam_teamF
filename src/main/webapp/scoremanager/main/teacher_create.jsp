<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

    <c:param name="title">教員新規登録</c:param>
    <%-- Tiêu đề trang: Đăng ký giáo viên mới --%>
    <%-- ページタイトル：教員新規登録 --%>

    <c:param name="content">

        <section class="me-4">

            <h2 class="mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                教員新規登録
                <%-- Tiêu đề lớn của trang --%>
                <%-- 画面の見出し --%>
            </h2>

            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
                <%-- Hiển thị lỗi nếu ID bị trùng hoặc lỗi khác --%>
                <%-- ID 重複などのエラーがある場合に表示 --%>
            </c:if>
            
            <form action="TeacherCreateExecute.action" method="post" class="px-4">
                <%-- Form gửi dữ liệu sang TeacherCreateExecute.action --%>
                <%-- TeacherCreateExecute.action にデータを送信するフォーム --%>

                <div class="mb-3">
                    <label class="form-label">教員ID</label>
                    <input type="text" name="id" class="form-control" required>
                    <%-- Nhập ID giáo viên --%>
                    <%-- 教員ID入力欄 --%>
                </div>

                <div class="mb-3">
                    <label class="form-label">氏名</label>
                    <input type="text" name="name" class="form-control" required>
                    <%-- Nhập tên giáo viên --%>
                    <%-- 教員氏名入力欄 --%>
                </div>

                <div class="mb-3">
                    <label class="form-label">パスワード</label>
                    <input type="password" name="password" class="form-control" required>
                    <%-- Nhập mật khẩu --%>
                    <%-- パスワード入力欄 --%>
                </div>

                <div class="mb-3">
                    <label class="form-label">権限</label>
                    <select name="role" class="form-select">
                        <option value="0">教員</option>
                        <option value="1">管理者</option>
                    </select>
                    <%-- Chọn quyền: 0 = giáo viên, 1 = admin chi nhánh --%>
                    <%-- 権限選択：0=教員、1=管理者 --%>
                </div>

                <%-- Lấy user đang đăng nhập --%>
                <%-- ログイン中のユーザーを取得 --%>
                <%
                    bean.Teacher loginUser = (bean.Teacher) session.getAttribute("user");
                %>

                <c:choose>

                    <%-- ⭐ SuperAdmin → được chọn trường --%>
                    <%-- superadmin(role=2) → 学校選択が可能 --%>
                    <c:when test="${user.role == 2}">
                        <div class="mb-3">
                            <label class="form-label">学校</label>
                            <select name="school_cd" class="form-select" required>
                                <option value="">選択してください</option>
                                <c:forEach var="s" items="${school_list}">
                                    <option value="${s.cd}">${s.name}</option>
                                </c:forEach>
                            </select>
                            <%-- SuperAdmin có thể chọn bất kỳ trường nào --%>
                            <%-- superadmin は任意の学校を選択可能 --%>
                        </div>
                    </c:when>

                    <%-- ⭐ Admin chi nhánh → KHÔNG được chọn trường --%>
                    <%-- ⭐ ただし school_cd を hidden で送る必要がある --%>
                    <%-- 支部管理者(role=1) → 学校変更不可、hidden で送信 --%>
                    <c:otherwise>
                        <input type="hidden" name="school_cd" value="${user.schoolCd}">
                        <%-- Gửi school_cd của admin chi nhánh lên backend --%>
                        <%-- 支部管理者の school_cd をバックエンドに送信 --%>

                        <p class="text-muted">学校: ${user.school.name}</p>
                        <%-- Hiển thị tên trường của admin chi nhánh --%>
                        <%-- 支部管理者が所属する学校名を表示 --%>
                    </c:otherwise>

                </c:choose>

                <div class="text-end mt-4">
                    <button class="btn btn-primary px-4">登録</button>
                    <%-- Nút đăng ký giáo viên mới --%>
                    <%-- 新規教員登録ボタン --%>
                </div>

            </form>

        </section>

    </c:param>

</c:import>
