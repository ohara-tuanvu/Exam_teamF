<%-- ログインJSP: Trang login --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%-- Cấu hình JSP dùng UTF-8 --%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%> <%-- Import JSTL core --%>

<c:import url="/common/base.jsp"> <%-- Nhúng layout base.jsp --%>

    <c:param name="title"> <%-- Gửi biến title sang base.jsp --%>
        ログイン
    </c:param>

    <c:param name="scripts"> <%-- Gửi script sang base.jsp --%>
        <script type="text/javascript">
            $(function() {  // Khi trang load xong
                $('#password-display').change(function() { // Khi tick checkbox
                    if ($(this).prop('checked')) { // Nếu được tick
                        $('#password-input').attr('type', 'text'); // Hiện mật khẩu
                    } else {
                        $('#password-input').attr('type', 'password'); // Ẩn mật khẩu
                    }
                });
            });
        </script>
    </c:param>

    <c:param name="content"> <%-- Nội dung chính của trang --%>

        <section class="w-75 text-center m-auto border pb-3"> <%-- Khung giao diện Bootstrap --%>

            <form action="<%= request.getContextPath() %>/scoremanager/LoginExecute.action" method="post"> 
                <%-- Form gửi POST đến LoginExecute.action --%>

                <div id="wrap_box"> <%-- Bao toàn bộ form --%>

                    <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2">ログイン</h2> <%-- Tiêu đề --%>

                    <c:if test="${errors.size()>0}"> <%-- Nếu có lỗi --%>
                        <div>
                            <ul>
                                <c:forEach var="error" items="${errors}"> <%-- Lặp qua danh sách lỗi --%>
                                    <li>${error}</li> <%-- Hiển thị từng lỗi --%>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:if>

                    <!-- ID -->
                    <div class="form-floating mx-5"> <%-- Ô nhập ID --%>
                        <input class="form-control px-5 fs-5" autocomplete="off"
                            id="id-input" maxlength="20" name="id"
                            placeholder="半角でご入力下さい" type="text" value="${id}" required /> 
                            <!-- value giữ lại ID khi nhập sai -->
                        <label>ＩＤ</label>
                    </div>

                    <!-- パスワード -->
                    <div class="form-floating mx-5 mt-3"> <%-- Ô nhập password --%>
                        <input class="form-control px-5 fs-5" autocomplete="off"
                            id="password-input" maxlength="20" name="password"
                            placeholder="20文字以内の半角英数字でご入力下さい"
                            type="password" required /> <!-- Mặc định ẩn mật khẩu -->
                        <label>パスワード</label>
                    </div>

                    <div class="form-check mt-3"> <%-- Checkbox hiện mật khẩu --%>
                        <label class="form-check-label" for="password-display">
                            <input class="form-check-input" id="password-display" type="checkbox" />
                            パスワードを表示 <!-- Hiện mật khẩu -->
                        </label>
                    </div>

                    <div class="mt-4"> <%-- Nút submit --%>
                        <input class="w-25 btn btn-lg btn-primary" type="submit" value="ログイン"/>
                    </div>

                </div>

            </form>
        </section>

    </c:param>
</c:import>
