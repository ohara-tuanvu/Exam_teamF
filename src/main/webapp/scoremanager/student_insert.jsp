<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

    <c:param name="title">学生新規登録 - 得点管理システム</c:param>

    <c:param name="content">

        <style>
            .glass-block {
                background: rgba(255,255,255,0.65);
                backdrop-filter: blur(6px);
                border: 1px solid #e5e5e5;
                border-radius: 8px;
                padding: 25px;
                box-shadow: 0 2px 6px rgba(0,0,0,0.05);
            }

            .glass-success {
                background: rgba(200,255,200,0.7);
                backdrop-filter: blur(4px);
                border: 1px solid #b2e5b2;
                padding: 12px;
                border-radius: 6px;
                font-weight: 600;
            }

            .input-error {
                color: red;
                font-size: 0.9rem;
                margin-top: 4px;
            }
        </style>

        <section class="me-4">

            <!-- 成功メッセージ -->
            <c:if test="${not empty message}">
                <div class="glass-success mx-5 mt-2">
                    ${message}
                </div>

                <div class="mt-3 text-center">
                    <a href="StudentList.action" class="btn btn-secondary w-25">戻る</a>
                </div>
            </c:if>

            <!-- 入力フォーム -->
            <c:if test="${empty message}">

                <form method="post" class="glass-block mx-5">

                    <!-- ⭐ SUPERADMIN のみ学校選択 -->
                    <c:if test="${user.role == 2}">
                        <div class="form-floating mt-3">
                            <select class="form-select" name="school_cd">
                                <option value=""></option>

                                <c:forEach var="s" items="${school_list}">
                                    <option value="${s.cd}"
                                        <c:if test="${s.cd == school_cd}">selected</c:if>>
                                        ${s.name}
                                    </option>
                                </c:forEach>
                            </select>

                            <label>学校</label>

                            <c:if test="${not empty errors.school}">
                                <div class="input-error">${errors.school}</div>
                            </c:if>
                        </div>
                    </c:if>

                    <!-- 入学年度 -->
                    <div class="form-floating mt-3">
                        <select class="form-select" id="entYear" name="entYear">
                            <option value=""></option>

                            <c:forEach var="year" items="${ent_year_set}">
                                <option value="${year}"
                                    <c:if test="${year == entYear}">selected</c:if>>
                                    ${year}
                                </option>
                            </c:forEach>
                        </select>

                        <label>入学年度</label>

                        <c:if test="${not empty errors.entYear}">
                            <div class="input-error">${errors.entYear}</div>
                        </c:if>
                    </div>

                    <!-- 学生番号 -->
                    <div class="form-floating mt-3">
                        <input class="form-control" type="text" name="no" maxlength="10"
                               value="${no}" placeholder="学生番号を入力してください" />

                        <label>学生番号</label>

                        <c:if test="${not empty error}">
                            <div class="input-error">${error}</div>
                        </c:if>

                        <c:if test="${not empty errors.no}">
                            <div class="input-error">${errors.no}</div>
                        </c:if>
                    </div>

                    <!-- 氏名 -->
                    <div class="form-floating mt-3">
                        <input class="form-control" type="text" name="name" maxlength="20"
                               value="${name}" placeholder="氏名を入力してください" />

                        <label>氏名</label>

                        <c:if test="${not empty errors.name}">
                            <div class="input-error">${errors.name}</div>
                        </c:if>
                    </div>

                    <!-- クラス -->
                    <div class="form-floating mt-3">
                        <select class="form-select" name="classNum">
                            <option value=""></option>

                            <c:forEach var="num" items="${class_num_set}">
                                <option value="${num}"
                                    <c:if test="${num == classNum}">selected</c:if>>
                                    ${num}
                                </option>
                            </c:forEach>
                        </select>

                        <label>クラス</label>

                        <c:if test="${not empty errors.classNum}">
                            <div class="input-error">${errors.classNum}</div>
                        </c:if>
                    </div>

                    <!-- 在学中 -->
                    <div class="form-check mt-3">
                        <input class="form-check-input" type="checkbox" name="isAttend" value="t"
                               <c:if test="${isAttend == 't'}">checked</c:if> />

                        <label class="form-check-label">在学中</label>
                    </div>

                    <!-- ボタン -->
                    <div class="mt-4 text-center">
                        <button formaction="StudentAddTemp.action" class="btn btn-warning w-25 me-2">追加</button>
                        <button formaction="StudentMultiInsertExecute.action" class="btn btn-primary w-25">登録</button>
                    </div>

                    <div class="mt-3 text-center">
                        <a href="StudentList.action" class="btn btn-outline-secondary ms-1 px-4">戻る</a>
                    </div>

                </form>

                <!-- 追加済み学生一覧 -->
                <c:if test="${not empty temp_students}">
                    <div class="mx-5 mt-4">
                        <h4>追加済みの学生一覧</h4>

                        <table class="table table-bordered mt-2">
                            <tr>
                                <th>学生番号</th>
                                <th>氏名</th>
                                <th>入学年度</th>
                                <th>クラス</th>

                                <!-- ⭐ SUPERADMIN のみ学校列 -->
                                <c:if test="${user.role == 2}">
                                    <th>学校</th>
                                </c:if>

                                <th>在学中</th>
                                <th>削除</th>
                            </tr>

                            <c:forEach var="s" items="${temp_students}" varStatus="st">
                                <tr>
                                    <td>${s.no}</td>
                                    <td>${s.name}</td>
                                    <td>${s.entYear}</td>
                                    <td>${s.classNum}</td>

                                    <!-- ⭐ SUPERADMIN のみ学校名 -->
                                    <c:if test="${user.role == 2}">
                                        <td>${s.school.name}</td>
                                    </c:if>

                                    <td><c:if test="${s.attend}">○</c:if></td>

                                    <td>
                                        <form method="post" action="StudentTempDelete.action" style="display:inline;">
                                            <input type="hidden" name="index" value="${st.index}">
                                            <button class="btn btn-danger btn-sm">削除</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </c:if>

            </c:if>

        </section>

    </c:param>

</c:import>
