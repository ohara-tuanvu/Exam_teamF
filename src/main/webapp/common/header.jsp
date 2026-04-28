<%-- ヘッダー --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<div class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-dark text-decoration-none">
	<h1 class="fs-1">得点管理システム</h1>
</div>
<c:if test="${user.isAuthenticated()}">
	<div class="nav align-self-end d-flex align-items-center gap-2">
    <span class="nav-item px-2">${user.getName()}様</span>

    <a class="btn btn-outline-danger btn-sm"
       href="Logout.action"
       onclick="return confirm('ログアウトしますか？');">
       ログアウト
    </a>
</div>
</c:if>