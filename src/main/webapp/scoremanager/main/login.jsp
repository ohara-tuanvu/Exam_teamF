<%-- ログインJSP: Trang login（ログイン画面） --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%-- Cấu hình JSP dùng UTF-8（JSPの文字コード設定 UTF-8） --%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%> <%-- Import JSTL core（JSTLコアタグライブラリをインポート） --%>

<c:import url="/common/base.jsp"> <%-- Nhúng layout base.jsp（共通レイアウト base.jsp を読み込む） --%>

    <c:param name="title"> <%-- Gửi biến title sang base.jsp（タイトルをbase.jspに渡す） --%>
        ログイン
    </c:param>

    <c:param name="scripts"> <%-- Gửi script sang base.jsp（スクリプトをbase.jspに渡す） --%>
        <script type="text/javascript">
            $(function() {  // Khi trang load xong（ページ読み込み完了時）
                $('#password-display').change(function() { // Khi tick checkbox（チェックボックス変更時）
                    if ($(this).prop('checked')) { // Nếu được tick（チェックされている場合）
                        $('#password-input').attr('type', 'text'); // Hiện mật khẩu（パスワードを表示）
                    } else {
                        $('#password-input').attr('type', 'password'); // Ẩn mật khẩu（パスワードを非表示）
                    }
                });
            });
        </script>
    </c:param>

    <c:param name="content"> <%-- Nội dung chính của trang（ページのメインコンテンツ） --%>

        <section class="w-75 text-center m-auto border pb-3"> <%-- Khung giao diện Bootstrap（Bootstrapのレイアウト枠） --%>

            <form action="<%= request.getContextPath() %>/scoremanager/LoginExecute.action" method="post"> 
                <%-- Form gửi POST đến LoginExecute.action（LoginExecute.actionへPOST送信） --%>

                <div id="wrap_box"> <%-- Bao toàn bộ form（フォーム全体を囲む） --%>

                    <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2">ログイン</h2> <%-- Tiêu đề（画面タイトル） --%>

                    <c:if test="${errors.size()>0}"> <%-- Nếu có lỗi（エラーがある場合） --%>
                        <div>
                            <ul>
                                <c:forEach var="error" items="${errors}"> <%-- Lặp qua danh sách lỗi（エラー一覧をループ処理） --%>
                                    <li>${error}</li> <%-- Hiển thị từng lỗi（各エラーを表示） --%>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:if>

                    <!-- ID -->
                    <div class="form-floating mx-5"> <%-- Ô nhập ID（ID入力欄） --%>
                        <input class="form-control px-5 fs-5" autocomplete="off"
                            id="id-input" maxlength="20" name="id"
                            placeholder="半角でご入力下さい" type="text" value="${id}" required /> 
                            <!-- value giữ lại ID khi nhập sai（入力エラー時にIDを保持） -->
                        <label>ＩＤ</label>
                    </div>

                    <!-- パスワード -->
                    <div class="form-floating mx-5 mt-3"> <%-- Ô nhập password（パスワード入力欄） --%>
                        <input class="form-control px-5 fs-5" autocomplete="off"
                            id="password-input" maxlength="20" name="password"
                            placeholder="20文字以内の半角英数字でご入力下さい"
                            type="password" required /> <!-- Mặc định ẩn mật khẩu（初期状態は非表示） -->
                        <label>パスワード</label>
                    </div>

                    <div class="form-check mt-3"> <%-- Checkbox hiện mật khẩu（パスワード表示チェックボックス） --%>
                        <label class="form-check-label" for="password-display">
                            <input class="form-check-input" id="password-display" type="checkbox" />
                            パスワードを表示 <!-- Hiện mật khẩu（パスワード表示） -->
                        </label>
                    </div>

                    <div class="mt-4"> <%-- Nút submit（ログインボタン） --%>
                        <input class="w-25 btn btn-lg btn-primary" type="submit" value="ログイン"/>
                    </div>

                </div>

            </form>
        </section>

    </c:param>
</c:import>