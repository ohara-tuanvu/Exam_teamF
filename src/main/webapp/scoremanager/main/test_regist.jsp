<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">成績登録</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">

        <section class="w-100 pb-3">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-3">成績管理</h2>

            <form action="TestRegist.action" method="get" class="border rounded p-3 mx-3 mb-3">
                <div class="d-flex align-items-end gap-3">
                    <div>
                        <label class="form-label">入学年度</label>
                        <select class="form-select" name="f1">
                            <option value="">--------</option>
                            <c:forEach var="year" items="${entYearList}">
                                <option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div>
                        <label class="form-label">クラス</label>
                        <select class="form-select" name="f2">
                            <option value="">--------</option>
                            <c:forEach var="cls" items="${classNumList}">
                                <option value="${cls}" <c:if test="${cls == f2}">selected</c:if>>${cls}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div>
                        <label class="form-label">科目</label>
                        <select class="form-select" name="f3">
                            <option value="">--------</option>
                            <c:forEach var="subject" items="${subjectList}">
                                <option value="${subject.cd}" <c:if test="${subject.cd == f3}">selected</c:if>>${subject.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div>
                        <label class="form-label">回数</label>
                        <select class="form-select" name="f4">
                            <option value="">--------</option>
                            <c:forEach var="n" items="${noList}">
                                <option value="${n}" <c:if test="${n == f4}">selected</c:if>>${n}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div>
                        <button type="submit" class="btn btn-secondary">検索</button>
                    </div>
                </div>
            </form>

            <c:if test="${not empty errors}">
                <c:forEach var="err" items="${errors}">
                    <div class="text-danger mx-3">${err}</div>
                </c:forEach>
            </c:if>

            <c:if test="${not empty studentList}">
                <form action="TestRegistExecute.action" method="post">
                    <input type="hidden" name="f1" value="${f1}" />
                    <input type="hidden" name="f2" value="${f2}" />
                    <input type="hidden" name="f3" value="${f3}" />
                    <input type="hidden" name="f4" value="${f4}" />

                    <div class="mx-3 mb-2 fw-bold">
                        科目：<c:forEach var="s" items="${subjectList}"><c:if test="${s.cd == f3}">${s.name}</c:if></c:forEach>（${f4}回）
                    </div>

                    <table class="table table-bordered mx-3 w-auto">
                        <thead class="table-light">
                            <tr>
                                <th>入学年度</th>
                                <th>クラス</th>
                                <th>学生番号</th>
                                <th>氏名</th>
                                <th>点数</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="student" items="${studentList}">
                                <tr>
                                    <td>${student.entYear}</td>
                                    <td>${student.classNum}</td>
                                    <td>${student.no}</td>
                                    <td>${student.name}</td>
                                    <td>
                                        <input type="number" class="form-control" style="width:100px;"
                                               name="point_${student.no}" min="0" max="100"
                                               value="${pointMap[student.no]}" />
                                        <div class="text-warning small">0〜100の範囲で入力してください</div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <div class="mx-3">
                        <button type="submit" class="btn btn-secondary">登録して終了</button>
                    </div>
                </form>
            </c:if>

        </section>

    </c:param>
</c:import>
