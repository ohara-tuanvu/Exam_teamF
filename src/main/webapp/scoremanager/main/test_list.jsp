<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">成績参照</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">

        <section class="w-100 pb-3">

            <%-- 科目別検索結果か学生別検索結果かでタイトル切り替え --%>
            <c:choose>
                <c:when test="${searchType == 'sj' && not empty testList}">
                    <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-3">成績一覧（科目）</h2>
                </c:when>
                <c:when test="${searchType == 'st' && not empty testList}">
                    <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-3">成績一覧（学生）</h2>
                </c:when>
                <c:otherwise>
                    <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-3">成績参照</h2>
                </c:otherwise>
            </c:choose>

            <%-- 検索フォーム --%>
            <div class="border rounded p-3 mx-3 mb-3">

                <%-- 科目情報検索 --%>
                <form action="TestList.action" method="get">
                    <input type="hidden" name="f" value="sj" />
                    <div class="d-flex align-items-end gap-3 mb-2">
                        <div style="width:80px;"><label class="form-label fw-bold">科目情報</label></div>
                        <div>
                            <label class="form-label">入学年度</label>
                            <select class="form-select" name="f1">
                                <option value="">----</option>
                                <c:forEach var="year" items="${entYearList}">
                                    <option value="${year}" <c:if test="${searchType == 'sj' && year == f1}">selected</c:if>>${year}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div>
                            <label class="form-label">クラス</label>
                            <select class="form-select" name="f2">
                                <option value="">----</option>
                                <c:forEach var="cls" items="${classNumList}">
                                    <option value="${cls}" <c:if test="${searchType == 'sj' && cls == f2}">selected</c:if>>${cls}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div>
                            <label class="form-label">科目</label>
                            <select class="form-select" name="f3">
                                <option value="">--------</option>
                                <c:forEach var="subject" items="${subjectList}">
                                    <option value="${subject.cd}" <c:if test="${searchType == 'sj' && subject.cd == f3}">selected</c:if>>${subject.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div>
                            <button type="submit" class="btn btn-primary">検索</button>
                        </div>
                    </div>
                    <c:if test="${not empty errorSj}">
                        <div class="text-danger">${errorSj}</div>
                    </c:if>
                </form>

                <hr/>

                <%-- 学生情報検索 --%>
                <form action="TestList.action" method="get">
                    <input type="hidden" name="f" value="st" />
                    <div class="d-flex align-items-end gap-3">
                        <div style="width:80px;"><label class="form-label fw-bold">学生情報</label></div>
                        <div>
                            <label class="form-label">学生番号</label>
                            <input type="text" class="form-control" name="f4"
                                   maxlength="10" placeholder="学生番号を入力してください"
                                   value="${searchType == 'st' ? f4 : ''}" />
                        </div>
                        <div>
                            <button type="submit" class="btn btn-primary">検索</button>
                        </div>
                    </div>
                    <c:if test="${not empty errorSt}">
                        <div class="text-danger">${errorSt}</div>
                    </c:if>
                </form>

                <%-- hidden fields --%>
                <input type="hidden" name="f" value="${searchType}" />

            </div>

            <%-- 案内メッセージ --%>
            <c:if test="${empty testList && empty errorSj && empty errorSt}">
                <div class="text-primary mx-3">科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</div>
            </c:if>

            <%-- 科目別結果テーブル --%>
            <c:if test="${searchType == 'sj' && not empty testList}">
                <div class="mx-3 mb-2">
                    科目：<c:forEach var="s" items="${subjectList}"><c:if test="${s.cd == f3}">${s.name}</c:if></c:forEach>
                </div>
                <table class="table table-bordered mx-3 w-auto">
                    <thead class="table-light">
                        <tr>
                            <th>入学年度</th>
                            <th>クラス</th>
                            <th>学生番号</th>
                            <th>氏名</th>
                            <th>1回</th>
                            <th>2回</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%-- 学生ごとにグループ化して表示 --%>
                        <c:forEach var="test" items="${testList}">
                            <c:if test="${test.no == 1}">
                                <tr>
                                    <td>${test.student.entYear}</td>
                                    <td>${test.classNum}</td>
                                    <td>${test.student.no}</td>
                                    <td>${test.student.name}</td>
                                    <td>${test.point}</td>
                                    <td>-</td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <%-- 学生別結果テーブル --%>
            <c:if test="${searchType == 'st' && not empty testList}">
                <div class="mx-3 mb-2">
                    氏名：${student.name}（${student.no}）
                </div>
                <table class="table table-bordered mx-3 w-auto">
                    <thead class="table-light">
                        <tr>
                            <th>科目名</th>
                            <th>科目コード</th>
                            <th>回数</th>
                            <th>点数</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="test" items="${testList}">
                            <tr>
                                <td>${test.subject.name}</td>
                                <td>${test.subject.cd}</td>
                                <td>${test.no}</td>
                                <td>${test.point}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

        </section>

    </c:param>
</c:import>
