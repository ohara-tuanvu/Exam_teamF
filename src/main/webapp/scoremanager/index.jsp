<%-- indexリダイレクトページ: Trang index chỉ dùng để redirect --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- Cấu hình JSP UTF-8 -->

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Khai báo HTML UTF-8 -->

<title></title> <!-- Không cần tiêu đề -->

<script>
// ログインページにリダイレクト
// Khi mở index.jsp → tự động chuyển sang Login.action

location.href="<%= request.getContextPath() %>/scoremanager/main/Login.action";
// request.getContextPath() đảm bảo đúng context khi deploy
// /scoremanager/main/Login.action → gọi LoginAction trong package scoremanager.main
</script>

</head>

<body>
<!-- コメント追加_vu-->
</body>

</html>
