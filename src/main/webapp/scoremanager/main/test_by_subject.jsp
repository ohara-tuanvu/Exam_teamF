<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">科目別成績一覧</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">

        <section class="w-100 m-auto border pb-3">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-3">科目別成績一覧</h2>

            <form action="TestBySubject.action" method="get" class="mx-3 mb-3 d-flex align-items-center gap-2">
                <label class="fw-bold">科目：</label>
                <select class="form-select w-auto" name="subjectCd">
                    <option value="">-- 選択 --</option>
                    <c:forEach var="subject" items="${subjectList}">
                        <option value="${subject.cd}"
                            <c:if test="${subject.cd == selectedSubjectCd}">selected</c:if>>
                            ${subject.cd} - ${subject.name}
                        </option>
                    </c:forEach>
                </select>
                <input class="btn btn-primary btn-sm" type="submit" value="検索" />
            </form>

            <c:if test="${not empty selectedSubjectCd}">
                <table class="table table-bordered table-hover mx-auto w-95">
                    <thead class="table-light">
                        <tr>
                            <th>学生番号</th>
                            <th>学生名</th>
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
                                        <td><c:out value="${test.student.no}" /></td>
                                        <td><c:out value="${test.student.name}" /></td>
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
