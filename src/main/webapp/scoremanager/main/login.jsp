<%-- ログインJSP: Trang login（ログイン画面） --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

    <c:param name="title">ログイン</c:param>

    <c:param name="scripts">
        <script type="text/javascript">
            $(function() {
                $('#password-display').change(function() {
                    if ($(this).prop('checked')) {
                        $('#password-input').attr('type', 'text');
                    } else {
                        $('#password-input').attr('type', 'password');
                    }
                });
            });
        </script>

        <!-- ⭐ CSS glass cho login -->
        <style>
            /* Header mờ nhẹ */
            h2 {
                background: rgba(255,255,255,0.55) !important;
                backdrop-filter: blur(4px);
                border-radius: 6px;
            }

            /* Khối login glass */
            #wrap_box {
                background: rgba(255,255,255,0.65);
                backdrop-filter: blur(6px);
                border: 1px solid #e5e5e5;
                border-radius: 10px;
                padding: 30px 25px;
                box-shadow: 0 2px 6px rgba(0,0,0,0.05);
                width: 70%;
                margin: auto;
            }

            /* Input rõ hơn */
            .form-control {
                background: rgba(255,255,255,0.85);
            }
        </style>
    </c:param>

    <c:param name="content">

        <section class="w-75 text-center m-auto border pb-3">

            <form action="<%= request.getContextPath() %>/scoremanager/LoginExecute.action" method="post">

                <div id="wrap_box">

                    <h2 class="h3 mb-3 fw-normal py-2">ログイン</h2>

                    <c:if test="${errors.size()>0}">
                        <div>
                            <ul>
                                <c:forEach var="error" items="${errors}">
                                    <li>${error}</li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:if>

                    <!-- ID -->
                    <div class="form-floating mx-5">
                        <input class="form-control px-5 fs-5" autocomplete="off"
                            id="id-input" maxlength="20" name="id"
                            placeholder="半角でご入力下さい" type="text" value="${id}" required />
                        <label>ＩＤ</label>
                    </div>

                    <!-- パスワード -->
                    <div class="form-floating mx-5 mt-3">
                        <input class="form-control px-5 fs-5" autocomplete="off"
                            id="password-input" maxlength="20" name="password"
                            placeholder="20文字以内の半角英数字でご入力下さい"
                            type="password" required />
                        <label>パスワード</label>
                    </div>

                    <div class="form-check mt-3">
                        <label class="form-check-label" for="password-display">
                            <input class="form-check-input" id="password-display" type="checkbox" />
                            パスワードを表示
                        </label>
                    </div>

                    <div class="mt-4">
                        <input class="btn btn-lg btn-primary" type="submit" value="ログイン"/>
                    </div>

                </div>

            </form>
        </section>

    </c:param>
</c:import>
