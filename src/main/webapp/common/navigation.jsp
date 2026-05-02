<%-- サイドバー --%>
<%-- Sidebar (menu bên trái) dùng chung cho toàn hệ thống --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style>
    /* ===== Sidebar kiểu thống kê Nhật ===== */
    .sidebar-nav {
        background: #ffffffee; /* trắng mờ hơn */
        border: 1px solid #e5e5e5; /* viền mảnh giống gov.jp */
        border-radius: 6px;
        padding: 0;
        min-height: 100vh;
        box-shadow: 0 2px 6px rgba(0,0,0,0.05);
    }

    /* Từng item giống dạng bảng */
    .sidebar-nav .nav-item {
        padding: 12px 16px;
        margin: 0; /* bỏ margin để giống table */
        border-bottom: 1px solid #ececec; /* chia dòng */
        border-radius: 0; /* bỏ bo góc từng item */
        font-weight: 600;
        color: #0a1a2f;
        transition: 0.15s;
    }

    /* Dòng cuối không có border */
    .sidebar-nav .nav-item:last-child {
        border-bottom: none;
    }

    /* Link */
    .sidebar-nav .nav-item a {
        text-decoration: none;
        color: #0a1a2f;
        display: block;
    }

    /* Hover kiểu gov.jp */
    .sidebar-nav .nav-item:hover {
        background: #f0f7ff; /* xanh nhạt */
        color: #0a58ca;
    }

    /* Mục con */
    .sidebar-nav .mx-3 {
        padding-left: 32px !important;
        font-size: 14px;
        opacity: 0.95;
        background: #fafafa; /* giống submenu trong bảng */
    }

    /* Hover submenu */
    .sidebar-nav .mx-3:hover {
        background: #e9f2ff;
    }

    /* Active (nếu cần) */
    .sidebar-nav .nav-item.active {
        background: #d9eaff;
        border-left: 4px solid #0a1a2f;
    }
</style>

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

</ul>
