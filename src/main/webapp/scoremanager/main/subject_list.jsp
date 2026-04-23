<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">科目管理 - 得点管理システム</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目管理</h2>
            
            <div class="my-2 text-end px-4">
                <a href="SubjectCreate.action" class="btn btn-success">新規登録</a>
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
                                    </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-info mx-3">科目情報が存在しませんでした。</div>
                </c:otherwise>
            </c:choose>
            
            <div class="mt-3">
                <a href="Menu.action">戻る</a>
            </div>
        </section>
    </c:param>
</c:import>