<%-- 科目一覧画面（Trang danh sách môn học） --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<c:import url="/common/base.jsp">

    <c:param name="title">科目管理 - 得点管理システム</c:param>

    <c:param name="content">

        <!-- ⭐ CSS làm bảng nổi rõ nhưng vẫn thấy background -->
        <style>
            h2 {
                background: rgba(255,255,255,0.55) !important;
                backdrop-filter: blur(4px);
                border-radius: 6px;
            }

            table.table thead tr th {
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

            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                科目管理
            </h2>

            <div class="my-2 text-end px-4">
                <a href="SubjectCreate.action" class="btn btn-primary shadow-sm">
                    新規登録
                </a>
            </div>

            <c:choose>

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
                                <tr>
                                    <td>${subject.cd}</td>
                                    <td>${subject.name}</td>

                                    <td>
                                        <c:url var="updateUrl" value="SubjectUpdate.action">
                                            <c:param name="cd" value="${subject.cd}" />
                                        </c:url>
                                        <a href="${updateUrl}" class="btn btn-sm btn-primary">
                                            変更
                                        </a>
                                    </td>

                                    <td>
                                        <c:url var="deleteUrl" value="SubjectDelete.action">
                                            <c:param name="cd" value="${subject.cd}" />
                                        </c:url>
                                        <a href="${deleteUrl}" class="btn btn-sm btn-danger">
                                            削除
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>

                    </table>
                </c:when>

                <c:otherwise>
                    <div class="alert alert-info mx-3">
                        科目情報が存在しませんでした。
                    </div>
                </c:otherwise>

            </c:choose>

        </section>

    </c:param>
</c:import>
