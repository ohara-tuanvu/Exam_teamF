<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

    <c:param name="title">教員管理 - 得点管理システム</c:param>
    <%-- Tiêu đề trang: Quản lý giáo viên --%>
    <%-- ページタイトル：教員管理 --%>

    <c:param name="content">

        <!-- ⭐ CSS theo style của bạn -->
        <!-- ⭐ あなたのスタイルに合わせた CSS -->
        <style>
            table.table th {
                background: rgba(255,255,255,0.75);
                backdrop-filter: blur(4px);
                border-bottom: 2px solid #dcdcdc;
            }

            table.table tbody tr {
                background: rgba(255,255,255,0.60);
                backdrop-filter: blur(4px);
                border-bottom: 1px solid #e5e5e5;
            }

            table.table tbody tr:hover {
                background: rgba(240,247,255,0.85);
            }
        </style>

        <section class="me-4">

            <h2 class="mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                教員管理
                <%-- Tiêu đề lớn của trang --%>
                <%-- 画面の見出し --%>
            </h2>

            <div class="my-3 text-end px-4">
                <a href="TeacherCreate.action" class="btn btn-primary shadow-sm">
                    新規登録
                </a>
                <%-- Nút đăng ký giáo viên mới --%>
                <%-- 新規教員登録ボタン --%>
            </div>

            <c:choose>

                <c:when test="${teacher_list.size() > 0}">
                    <%-- Nếu có dữ liệu giáo viên --%>
                    <%-- 教員データが存在する場合 --%>

                    <div class="px-3 mb-2">
                        検索結果：${teacher_list.size()}件
                        <%-- Hiển thị số lượng kết quả --%>
                        <%-- 検索結果件数を表示 --%>
                    </div>

                    <table class="table table-hover mx-3">
                        <%-- Bảng danh sách giáo viên --%>
                        <%-- 教員一覧テーブル --%>

                        <thead>
                            <tr>
                                <th>教員ID</th>
                                <th>氏名</th>
                                <th>権限</th>
                                <th class="text-end">操作</th>
                            </tr>
                        </thead>

                        <tbody>

                            <c:forEach var="t" items="${teacher_list}">
                                <tr>

                                    <td class="fw-semibold">${t.id}</td>
                                    <%-- ID giáo viên --%>
                                    <%-- 教員ID --%>

                                    <td>${t.name}</td>
                                    <%-- Tên giáo viên --%>
                                    <%-- 教員氏名 --%>

                                    <td>
                                        <c:choose>
                                            <c:when test="${t.role == 1}">
                                                管理者
                                            </c:when>
                                            <c:otherwise>
                                                教員
                                            </c:otherwise>
                                        </c:choose>
                                        <%-- Hiển thị quyền: 1 = quản trị, 0 = giáo viên --%>
                                        <%-- 権限表示：1=管理者、0=教員 --%>
                                    </td>

                                    <td class="text-end">

                                        <a class="btn btn-primary btn-sm me-3"
                                           href="TeacherUpdate.action?id=${t.id}">
                                            変更
                                        </a>
                                        <%-- Nút chỉnh sửa giáo viên --%>
                                        <%-- 教員情報変更ボタン --%>

                                        <c:if test="${t.id != user.id}">
                                            <%-- Không cho phép tự xóa chính mình --%>
                                            <%-- 自分自身のアカウントは削除不可 --%>

                                            <a class="btn btn-danger btn-sm"
                                               href="TeacherDelete.action?id=${t.id}">
                                                削除
                                            </a>
                                            <%-- Nút xóa giáo viên --%>
                                            <%-- 教員削除ボタン --%>
                                        </c:if>

                                    </td>

                                </tr>
                            </c:forEach>

                        </tbody>

                    </table>

                </c:when>

                <c:otherwise>
                    <%-- Không có dữ liệu giáo viên --%>
                    <%-- 教員データが存在しない場合 --%>

                    <div class="alert alert-light border mx-3">
                        教員情報が存在しませんでした。
                        <%-- Thông báo không có dữ liệu --%>
                        <%-- データなしメッセージ --%>
                    </div>

                </c:otherwise>

            </c:choose>

        </section>

    </c:param>

</c:import>
