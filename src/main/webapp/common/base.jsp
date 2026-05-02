<%-- 共通テンプレート --%>
<%-- Đây là template chung dùng cho tất cả các trang JSP trong hệ thống --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap CSS -->
<link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
    rel="stylesheet"
    integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
    crossorigin="anonymous">

<title>${param.title}</title>

<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>

${param.scripts}

<!-- ⭐ Background không mất trên/dưới + radial gradient -->
<style>
    body {
        background: url("<%=request.getContextPath()%>/images/bgr.png")
                    no-repeat top center fixed;
        background-size: contain;
        background-color: #f2f2f2;
        position: relative;
    }

    /* ⭐ MỨC MỜ TỐI ƯU — chỉ chỉnh phần này */
    body::before {
        content: "";
        position: fixed;
        inset: 0;
        background: radial-gradient(
            circle,
            rgba(255,255,255,0.80) 0%,   /* tăng từ 0.75 → 0.80 */
            rgba(255,255,255,0.95) 70%  /* tăng từ 0.92 → 0.95 */
        );
        z-index: -1;
    }

    /* ⭐ Làm chữ trong bảng LOGIN rõ hơn */
    .login-title {
        font-size: 2rem;
        font-weight: 700;
        color: #222;
        text-shadow: 0 2px 6px rgba(0,0,0,0.15);
        text-align: center;
        margin-bottom: 1.5rem;
    }

    .login-card label {
        font-weight: 600;
        color: #222;
        text-shadow: 0 1px 3px rgba(0,0,0,0.1);
    }

    .login-card .form-control {
        background: rgba(255,255,255,0.9);
        border: 1px solid #777;
        font-weight: 500;
    }

    .login-footer {
        text-align: center;
        color: #333;
        font-size: 0.95rem;
        margin-top: 1.5rem;
        text-shadow: 0 1px 3px rgba(0,0,0,0.15);
    }
</style>

</head>

<body>
    <div id="wrapper" class="container">
        <header
            class="d-flex flex-wrap justify-content-center py-3 px-5 mb-4 border-bottom border-2 bg-primary bg-opacity-10 bg-gradient">
            <c:import url="/common/header.jsp" />
        </header>

        <div class="row justify-content-center">
            <c:choose>
                <c:when test="${user.isAuthenticated()}">
                    <nav class="col-3" style="height:40rem;">
                        <c:import url="/common/navigation.jsp" />
                    </nav>

                    <main class="col-9 border-start">
                        ${param.content}
                    </main>
                </c:when>

                <c:otherwise>
                    <main class="col-8">
                        ${param.content}
                    </main>
                </c:otherwise>
            </c:choose>
        </div>

        <footer class="py-2 my-4 bg-dark bg-opacity-10 border-top border-3 align-bottom">
            <c:import url="/common/footer.jsp" />
        </footer>

    </div>
</body>
</html>
