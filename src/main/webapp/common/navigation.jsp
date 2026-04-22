<%-- サイドバー --%>
<%-- Sidebar (menu bên trái) dùng chung cho toàn hệ thống --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- Thiết lập JSP sử dụng UTF-8 --%>

<ul class="nav nav-pills flex-column mb-auto px-4">
    <%-- ul: danh sách menu dạng cột (vertical) --%>
    <%-- nav nav-pills: Bootstrap style cho menu dạng pill --%>
    <%-- flex-column: xếp theo chiều dọc --%>
    <%-- mb-auto: margin-bottom auto để đẩy footer xuống --%>
    <%-- px-4: padding ngang --%>

    <li class="nav-item my-3">
        <a href="Menu.action">メニュー</a>
        <%-- Link về màn hình Menu chính --%>
    </li>

    <li class="nav-item mb-3">
        <a href="StudentList.action">学生管理</a>
        <%-- Link đến danh sách học sinh --%>
    </li>

    <li class="nav-item">成績管理</li>
    <%-- Tiêu đề nhóm menu (không phải link) --%>

    <li class="nav-item mx-3 mb-3">
        <a href="TestRegist.action">成績登録</a>
        <%-- Link đến màn hình đăng ký điểm --%>
    </li>

    <li class="nav-item mx-3 mb-3">
        <a href="TestList.action">成績参照</a>
        <%-- Link đến màn hình xem danh sách điểm --%>
    </li>

    <li class="nav-item mb-3">
        <a href="SubjectList.action">科目管理</a>
        <%-- Link đến danh sách môn học --%>
    </li>

    <li class="nav-item mb-3">
        <a href="ClassList.action">クラス管理</a>
        <%-- Link đến danh sách lớp học --%>
    </li>
</ul>
