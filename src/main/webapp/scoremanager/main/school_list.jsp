<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

    <c:param name="title">学校一覧</c:param>

    <c:param name="content">

        <section class="me-4">

            <h2 class="mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                学校一覧
            </h2>

            <div class="text-end mb-3 px-4">
                <a href="SchoolCreate.action" class="btn btn-primary">新規登録</a>
            </div>

            <table class="table table-bordered mx-4">
                <thead>
                    <tr>
                        <th>学校コード</th>
                        <th>学校名</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="s" items="${school_list}">
                        <tr>
                            <td>${s.cd}</td>
                            <td>${s.name}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        </section>

    </c:param>

</c:import>
