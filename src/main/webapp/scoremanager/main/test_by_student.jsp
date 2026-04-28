<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">学生別成績一覧</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">

        <section class="w-100 m-auto border pb-3">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-3">学生別成績一覧</h2>

            <form action="TestByStudent.action" method="get" class="mx-3 mb-3 d-flex align-items-center gap-2">
                <label class="fw-bold">学生：</label>
                <select class="form-select w-auto" name="studentNo">
                    <option value="">-- 選択 --</option>
                    <c:forEach var="student" items="${studentList}">
                        <option value="${student.no}"
                            <c:if test="${student.no == selectedStudentNo}">selected</c:if>>
                            ${student.no} - ${student.name}
                        </option>
                    </c:forEach>
                </select>
                <input class="btn btn-primary btn-sm" type="submit" value="検索" />
            </form>

            <c:if test="${not empty selectedStudentNo}">
                <table class="table table-bordered table-hover mx-auto w-95">
                    <thead class="table-light">
                        <tr>
                            <th>科目コード</th>
                            <th>科目名</th>
                            <th>回数</th>
                            <th>点数</th>
                            <th>クラス</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty testList}">
                                <tr><td colspan="5" class="text-center">成績データがありません。</td></tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="test" items="${testList}">
                                    <tr>
                                        <td><c:out value="${test.subject.cd}" /></td>
                                        <td><c:out value="${test.subject.name}" /></td>
                                        <td><c:out value="${test.no}" /></td>
                                        <td><c:out value="${test.point}" /></td>
                                        <td><c:out value="${test.classNum}" /></td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </c:if>

            <div class="mt-3 mx-3">
                <a href="TestList.action">戻る</a>
            </div>
        </section>

    </c:param>
</c:import>
