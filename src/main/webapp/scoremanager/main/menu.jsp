<%-- メニュー画面（Trang menu chính） --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> <%-- JSPの文字コード設定 UTF-8（Cấu hình JSP UTF-8） --%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%> <%-- JSTLコアタグライブラリをインポート（Import JSTL core） --%>

<c:import url="/common/base.jsp"> <%-- 共通レイアウト base.jsp を読み込む（Nhúng layout base.jsp） --%>

    <c:param name="title"> <%-- タイトルをbase.jspに渡す（Gửi tiêu đề trang sang base.jsp） --%>
        得点管理システム
    </c:param>

    <c:param name="scripts"></c:param> <%-- スクリプトなし（Không có script riêng cho trang này） --%>

    <c:param name="content"> <%-- ページのメインコンテンツ（Nội dung chính của trang menu） --%>

        <section class="me-4"> <%-- レイアウト枠（Khung nội dung, margin-end 4） --%>

            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                メニュー
            </h2> <%-- 画面タイトル（Tiêu đề "メニュー"） --%>

            <div class="row text-center px-4 fs-3 my-5"> <%-- メニュー項目一覧（Hàng chứa các ô menu） --%>

                <!-- 学生管理 -->
                <div class="col d-flex align-items-center justify-content-center mx-2 rounded shadow"
                    style="height: 10rem; background-color: #dbb;">
                    <a href="StudentList.action">学生管理</a>
                    <%-- 学生一覧画面へ遷移（Link đến danh sách sinh viên） --%>
                </div>

                <!-- 成績管理 -->
                <div class="col d-flex align-items-center justify-content-center mx-2 rounded shadow"
                    style="height: 10rem; background-color: #bdb;">
                    <div>
                        <div>成績管理</div> <%-- 成績管理メニュー（Nhóm quản lý điểm） --%>

                        <div>
                            <a href="TestRegist.action">成績登録</a>
                            <%-- 成績登録画面へ遷移（Link đăng ký điểm） --%>
                        </div>

                        <div>
                            <a href="TestList.action">成績参照</a>
                            <%-- 成績一覧画面へ遷移（Link xem danh sách điểm） --%>
                        </div>
                    </div>
                </div>

                <!-- 科目管理 -->
                <div class="col d-flex align-items-center justify-content-center mx-2 rounded shadow"
                    style="height: 10rem; background-color: #bbd;">
                    <a href="SubjectList.action">科目管理</a>
                    <%-- 科目一覧画面へ遷移（Link quản lý môn học） --%>
                </div>

                <!-- クラス管理 -->
                <div class="col d-flex align-items-center justify-content-center mx-2 rounded shadow"
                    style="height: 10rem; background-color: #ddb;">
                    <a href="ClassList.action">クラス管理</a>
                    <%-- クラス一覧画面へ遷移（Link quản lý lớp học） --%>
                </div>

            </div>
        </section>

    </c:param>
</c:import>