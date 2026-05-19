<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style>
    .sidebar-nav {
        background: #ffffffee;
        border: 1px solid #e5e5e5;
        border-radius: 6px;
        padding: 0;
        min-height: 100vh;
        box-shadow: 0 2px 6px rgba(0,0,0,0.05);
    }
    .sidebar-nav .nav-item {
        padding: 12px 16px;
        margin: 0;
        border-bottom: 1px solid #ececec;
        font-weight: 600;
        color: #0a1a2f;
        transition: 0.15s;
    }
    .sidebar-nav .nav-item:last-child {
        border-bottom: none;
    }
    .sidebar-nav .nav-item a {
        text-decoration: none;
        color: #0a1a2f;
        display: block;
    }
    .sidebar-nav .nav-item:hover {
        background: #f0f7ff;
        color: #0a58ca;
    }
    .sidebar-nav .mx-3 {
        padding-left: 32px !important;
        font-size: 14px;
        opacity: 0.95;
        background: #fafafa;
    }
    .sidebar-nav .mx-3:hover {
        background: #e9f2ff;
    }
</style>

<%
    // Lấy user đang đăng nhập
    bean.Teacher loginUser = (bean.Teacher) session.getAttribute("user");
%>

<ul class="nav nav-pills flex-column mb-auto px-4 sidebar-nav">

    <li class="nav-item my-3">
        <a href="Menu.action">メニュー</a>
    </li>

    <li class="nav-item mb-3">
        <a href="StudentList.action">学生管理</a>
    </li>

    <li class="nav-item">成績管理</li>

    <li class="nav-item mx-3 mb-3">
        <a href="TestRegist.action">成績登録</a>
    </li>

    <li class="nav-item mx-3 mb-3">
        <a href="TestList.action">成績参照</a>
    </li>

    <li class="nav-item mb-3">
        <a href="SubjectList.action">科目管理</a>
    </li>

    <li class="nav-item mb-3">
        <a href="ClassList.action">クラス管理</a>
    </li>

    <%-- ⭐ 教員管理 chỉ hiển thị cho role 1 và 2 --%>
	<%-- ⭐ 教員管理 メニューは role=1 と role=2 のみ表示 --%>
    <% if (loginUser != null && (loginUser.getRole() == 1 || loginUser.getRole() == 2)) { %>

        <li class="nav-item mb-3">
        	<a href="TeacherList.action">教員管理</a>
    	</li>

        

    <% } %>

</ul>
