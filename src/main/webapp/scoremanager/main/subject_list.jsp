<%-- 科目一覧画面（Trang danh sách môn học） --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- JSPの文字コード設定 UTF-8（Cấu hình JSP UTF-8） --%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%-- JSTLコアタグライブラリをインポート（Import JSTL core） --%>

<c:import url="/common/base.jsp">
<%-- 共通レイアウト base.jsp を読み込む（Nhúng layout base.jsp） --%>

    <c:param name="title">科目管理 - 得点管理システム</c:param>
    <%-- ページタイトル（Tiêu đề trang） --%>

    <c:param name="content">
    <%-- メインコンテンツ（Nội dung chính） --%>

        <section class="me-4">

            <!-- タイトル -->
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                科目管理
            </h2>
            <%-- 画面タイトル（Tiêu đề quản lý môn học） --%>

            <!-- 新規登録ボタン -->
            <div class="my-2 text-end px-4">
                <a href="SubjectCreate.action" class="btn btn-success">
                    新規登録
                </a>
            </div>
            <%-- 新規科目登録画面へ遷移（Đi tới trang đăng ký môn học mới） --%>

            <!-- データ表示 -->
            <c:choose>

                <%-- データあり（Có dữ liệu） --%>
                <c:when test="${not empty subjects}">
                    <table class="table table-hover mt-3">

                        <thead>
                            <tr class="table-light">
                                <th>科目コード</th>
                                <th>科目名</th>
                                <th></th>
                                <th></th>
                            </tr>
                        </thead>

                        <tbody>
                            <c:forEach var="subject" items="${subjects}">
                            <%-- 科目一覧をループ表示（Lặp danh sách môn học） --%>

                                <tr>
                                    <td>${subject.cd}</td>
                                    <td>${subject.name}</td>

                                    <td>
                                        <a href="SubjectUpdate.action?cd=${subject.cd}" class="btn btn-sm btn-primary">
                                            変更
                                        </a>
                                    </td>
                                    <%-- 更新画面へ遷移（Chuyển đến trang sửa） --%>

                                    <td>
                                        <a href="SubjectDelete.action?cd=${subject.cd}" class="btn btn-sm btn-danger">
                                            削除
                                        </a>
                                    </td>
                                    <%-- 削除確認画面へ遷移（Chuyển đến trang xác nhận xóa） --%>

                                </tr>

                            </c:forEach>
                        </tbody>

                    </table>
                </c:when>

                <%-- データなし（Không có dữ liệu） --%>
                <c:otherwise>
                    <div class="alert alert-info mx-3">
                        科目情報が存在しませんでした。
                    </div>
                </c:otherwise>

            </c:choose>

            <!-- 戻るリンク -->
            <div class="mt-3">
                <a href="Menu.action">戻る</a>
            </div>
            <%-- メニュー画面へ戻る（Quay lại menu） --%>

        </section>

    </c:param>
</c:import>