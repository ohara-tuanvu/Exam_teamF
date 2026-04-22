<%-- ログアウトJSP: Trang logout --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> <%-- Cấu hình JSP UTF-8 --%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%> <%-- Import JSTL core --%>

<c:import url="/common/base.jsp"> <%-- Nhúng layout base.jsp --%>

    <c:param name="title"> <%-- Gửi tiêu đề trang sang base.jsp --%>
        得点管理システム
    </c:param>

    <c:param name="content"> <%-- Nội dung chính của trang logout --%>

        <div id="wrap_box"> <%-- Khung bao toàn bộ nội dung --%>

            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2">ログアウト</h2>
            <%-- Tiêu đề "ログアウト" --%>

            <div id="wrap_box"> <%-- Khung hiển thị thông báo --%>

                <p class="text-center" style="background-color:#66CC99">
                    ログアウトしました
                </p>
                <%-- Thông báo: Đã logout thành công --%>

                <a href="${pageContext.request.contextPath}/scoremanager/main/login.jsp">ログイン</a>
                <%--${pageContext.request.contextPath}:tạo đường link tuyệt đối nhưng không phụ thuộc vào tên project. --%>
                <%-- Link quay lại trang login --%>

            </div>
        </div>

    </c:param>
</c:import>
