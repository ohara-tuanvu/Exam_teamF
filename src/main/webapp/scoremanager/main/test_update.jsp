<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">成績変更</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">

        <section class="w-75 m-auto border pb-3">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-3">成績変更</h2>

            <c:if test="${not empty errors}">
                <ul class="text-danger mx-5">
                    <c:forEach var="err" items="${errors}">
                        <li>${err}</li>
                    </c:forEach>
                </ul>
            </c:if>

            <form action="TestUpdateExecute.action" method="post">
                <input type="hidden" name="studentNo" value="<c:out value='${test.student.no}'/>" />
                <input type="hidden" name="subjectCd" value="<c:out value='${test.subject.cd}'/>" />
                <input type="hidden" name="no"        value="<c:out value='${test.no}'/>" />

                <table class="table table-bordered mx-5 w-auto">
                    <tr>
                        <th>学生番号</th>
                        <td><c:out value="${test.student.no}" /></td>
                    </tr>
                    <tr>
                        <th>学生名</th>
                        <td><c:out value="${test.student.name}" /></td>
                    </tr>
                    <tr>
                        <th>科目</th>
                        <td><c:out value="${test.subject.name}" /></td>
                    </tr>
                    <tr>
                        <th>回数</th>
                        <td><c:out value="${test.no}" /></td>
                    </tr>
                    <tr>
                        <th>点数</th>
                        <td>
                            <input class="form-control" type="number" name="point"
                                   min="0" max="100" value="<c:out value='${test.point}'/>" />
                        </td>
                    </tr>
                    <tr>
                        <th>クラス</th>
                        <td><c:out value="${test.classNum}" /></td>
                    </tr>
                </table>

                <div class="mt-4 text-center">
                    <input class="btn btn-warning w-25" type="submit" value="変更" />
                </div>
                <div class="mt-3 text-center">
                    <a href="TestList.action">戻る</a>
                </div>
            </form>
        </section>

    </c:param>
</c:import>
