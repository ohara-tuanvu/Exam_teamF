<%-- ログアウト画面（Trang logout） --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> <%-- JSPの文字コード設定 UTF-8（Cấu hình JSP UTF-8） --%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%> <%-- JSTLコアタグライブラリをインポート（Import JSTL core） --%>

<c:import url="/common/base.jsp"> <%-- 共通レイアウト base.jsp を読み込む（Nhúng layout base.jsp） --%>

    <c:param name="title"> <%-- タイトルをbase.jspに渡す（Gửi tiêu đề trang sang base.jsp） --%>
        得点管理システム
    </c:param>

    <c:param name="content">

    <div class="d-flex justify-content-center align-items-center" style="min-height: 60vh;">

        <div class="card border-0 shadow-sm p-4 text-center" style="min-width: 320px; max-width: 400px;">

            <!-- Title -->
            <h2 class="h4 mb-3">ログアウト</h2>

            <!-- Success message -->
            <div class="alert alert-success mb-4">
            ✅ ログアウトしました
            </div>

            <!-- Login button -->
            <a class="btn btn-primary w-100"
            href="javascript:void(0);"
            onclick="fadeOutAndRedirect('${pageContext.request.contextPath}/scoremanager/main/login.jsp')">
            ログイン画面へ
            </a>

        </div>
    </div>

</c:param>
	<c:param name="scripts">
    <style>
        body {
            opacity: 0;
            transition: opacity 0.4s ease-in-out;
        }
        body.fade-in {
            opacity: 1;
        }
        body.fade-out {
            opacity: 0;
        }
    </style>

    <script>
        // Fade IN when page loads
        window.addEventListener("load", function () {
            document.body.classList.add("fade-in");
        });

        // Fade OUT then redirect
        function fadeOutAndRedirect(url) {
            document.body.classList.remove("fade-in");
            document.body.classList.add("fade-out");

            setTimeout(function () {
                window.location.href = url;
            }, 400);
        }
    </script>
</c:param>	
</c:import>