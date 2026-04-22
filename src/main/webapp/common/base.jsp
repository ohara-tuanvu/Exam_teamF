<%-- 共通テンプレート --%>
<%-- Đây là template chung dùng cho tất cả các trang JSP trong hệ thống --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- Thiết lập JSP sử dụng UTF-8 để hiển thị tiếng Nhật / tiếng Việt đúng --%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%-- Sử dụng JSTL core tag (c:if, c:forEach, c:choose, ...) --%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%-- Khai báo meta để trình duyệt hiểu UTF-8 --%>

<meta name="viewport" content="width=device-width, initial-scale=1">
<%-- Responsive cho mobile --%>

<!-- Bootstrap CSS -->
<link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
    rel="stylesheet"
    integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
    crossorigin="anonymous">
<%-- Import Bootstrap 5 từ CDN --%>

<title>${param.title}</title>
<%-- Tiêu đề trang được truyền từ JSP con qua <c:param name="title"> --%>

<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<%-- Import jQuery (nếu trang con cần dùng) --%>

${param.scripts}
<%-- Cho phép trang con truyền thêm script nếu cần --%>
</head>

<body>
    <div id="wrapper" class="container">
        <header
            class="d-flex flex-wrap justify-content-center py-3 px-5 mb-4 border-bottom border-2 bg-primary bg-opacity-10 bg-gradient">
            <c:import url="/common/header.jsp" />
            <%-- Import header chung (logo, tên hệ thống, v.v.) --%>
        </header>

        <div class="row justify-content-center">
            <c:choose>
                <%-- ログイン済みの場合 --%>
                <%-- Nếu đã đăng nhập (user.isAuthenticated() == true) --%>
                <c:when test="${user.isAuthenticated()}">
                    <nav class="col-3" style="height:40rem;">
                        <c:import url="/common/navigation.jsp" />
                        <%-- Hiển thị menu bên trái (navigation) --%>
                    </nav>

                    <main class="col-9 border-start">
                        ${param.content}
                        <%-- Nội dung chính của trang (JSP con truyền vào) --%>
                    </main>
                </c:when>

                <%-- 未ログインの場合 --%>
                <%-- Nếu chưa đăng nhập → không hiển thị menu trái --%>
                <c:otherwise>
                    <main class="col-8">
                        ${param.content}
                    </main>
                </c:otherwise>
            </c:choose>
        </div>

        <footer class="py-2 my-4 bg-dark bg-opacity-10 border-top border-3 align-bottom">
            <c:import url="/common/footer.jsp" />
            <%-- Import footer chung --%>
        </footer>

    </div>
</body>
</html>
