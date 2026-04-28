<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">成績登録</c:param>
    <c:param name="scripts">
        <script>
            function checkPoint(input) {
                var val = parseInt(input.value);
                var msg = input.nextElementSibling;
                if (input.value !== '' && (isNaN(val) || val < 0 || val > 100)) {
                    msg.style.display = 'block';
                } else {
                    msg.style.display = 'none';
                }
            }
        </script>
        <style>
            input[type=number]::-webkit-inner-spin-button,
            input[type=number]::-webkit-outer-spin-button {
                -webkit-appearance: none;
                margin: 0;
            }
            input[type=number] { -moz-appearance: textfield; }
        </style>
    </c:param>

    <c:param name="content">

        <section class="w-100 pb-3">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-3">成績管理</h2>

            <!-- 検索フォーム -->
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
                        <button type="submit" class="btn btn-primary">検索</button>
                    </div>
                </div>
            </form>

            <!-- エラー表示 -->
            <c:if test="${not empty errors}">
                <c:forEach var="err" items="${errors}">
                    <div class="text-danger mx-3">${err}</div>
                </c:forEach>
            </c:if>

            <c:if test="${not empty searchError}">
                <div style="background-color:#fdf8e1; border:1px solid #e6d87a; border-radius:6px; padding:12px 20px; color:#7a6a00; margin: 0 12px;">
                    ${searchError}
                </div>
            </c:if>

            <!-- 成績入力テーブル -->
            <c:if test="${not empty studentList}">
                <form action="TestRegistExecute.action" method="post">
                    <input type="hidden" name="f1" value="${f1}" />
                    <input type="hidden" name="f2" value="${f2}" />
                    <input type="hidden" name="f3" value="${f3}" />
                    <input type="hidden" name="f4" value="${f4}" />

                    <div class="mx-3 mb-2 fw-bold">
                        科目：
                        <c:forEach var="s" items="${subjectList}">
                            <c:if test="${s.cd == f3}">${s.name}</c:if>
                        </c:forEach>
                        （${f4}回）
                    </div>

                    <table class="table table-bordered mx-3 w-auto">
                        <thead class="table-light">
                            <tr>
                                <th>入学年度</th>
                                <th>クラス</th>
                                <th>学生番号</th>
                                <th>氏名</th>
                                <th>点数</th>
                                <th>削除</th> <!-- ★ THÊM -->
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
                                               name="point_${student.no}"
                                               value="${pointMap[student.no]}"
                                               oninput="checkPoint(this)" />
                                        <div class="text-warning small point-error" style="display:none;">
                                            0〜100の範囲で入力してください
                                        </div>
                                    </td>

                                    <!-- ★ 削除リンク（点数が存在する場合のみ表示） -->
                                    <td>
                                        <c:if test="${not empty pointMap[student.no]}">
                                            <a href="TestDelete.action?studentNo=${student.no}&subjectCd=${f3}&no=${f4}">
                                                削除
                                            </a>
                                        </c:if>
                                    </td>

                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <div class="mx-3">
                        <button type="submit" class="btn btn-primary">登録して終了</button>
                    </div>
                </form>
            </c:if>

        </section>

    </c:param>
</c:import>
