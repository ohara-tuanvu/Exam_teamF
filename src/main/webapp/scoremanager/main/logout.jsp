<%-- ログアウト画面（Trang logout） --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="content">

        <!-- ⭐ CSS glass -->
        <style>
            /* Header mờ nhẹ */
            h2 {
                background: rgba(255,255,255,0.55) !important;
                backdrop-filter: blur(4px);
                border-radius: 6px;
            }

            /* Khối glass */
            #wrap_box {
                background: rgba(255,255,255,0.65);
                backdrop-filter: blur(6px);
                border: 1px solid #e5e5e5;
                border-radius: 10px;
                padding: 25px;
                width: 60%;
                margin: auto;
                box-shadow: 0 2px 6px rgba(0,0,0,0.05);
            }

            /* Thông báo thành công */
            .glass-success {
                background: rgba(212, 237, 218, 0.75);
                backdrop-filter: blur(4px);
                border: 1px solid #c3e6cb;
                border-radius: 8px;
                padding: 12px 20px;
                color: #155724;
                text-align: center;
                font-weight: 500;
            }
        </style>

        <div id="wrap_box">

            <h2 class="h3 mb-3 fw-normal py-2">
                ログアウト
            </h2>

            <!-- ⭐ Thông báo dạng glass-success -->
            <div class="glass-success mb-3">
                ログアウトしました
            </div>

            <!-- ⭐ Giữ nguyên link login -->
            <div class="text-center">
                <a href="${pageContext.request.contextPath}/scoremanager/main/login.jsp">
                    ログイン
                </a>
            </div>

        </div>

    </c:param>
</c:import>
