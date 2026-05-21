<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">成績参照</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">

        <!-- VI: CSS hiệu ứng kính mờ cho toàn bộ giao diện -->
        <!-- JP: 画面全体のガラス風デザイン用CSS -->
        <style>
            h2 {
                background: rgba(255,255,255,0.55) !important;
                backdrop-filter: blur(4px);
                border-radius: 6px;
            }

            .glass-filter {
                background: rgba(255,255,255,0.65);
                backdrop-filter: blur(6px);
                border: 1px solid #e5e5e5;
                border-radius: 10px;
                padding: 18px 20px;
                box-shadow: 0 2px 6px rgba(0,0,0,0.05);
            }

            table.table thead tr th {
                background: rgba(255,255,255,0.75);
                backdrop-filter: blur(4px);
                border-bottom: 2px solid #dcdcdc;
            }

            table.table tbody tr td {
                background: rgba(255,255,255,0.60);
                backdrop-filter: blur(4px);
                border-bottom: 1px solid #e5e5e5;
            }

            table.table tbody tr:hover td {
                background: rgba(240,247,255,0.85);
            }
        </style>

        <section class="w-100 pb-3">

            <!-- VI: Đổi tiêu đề theo loại tìm kiếm -->
            <!-- JP: 検索種別に応じてタイトルを切り替える -->
            <c:choose>
                <c:when test="${searchType == 'sj' && not empty testList}">
                    <h2 class="h3 mb-3 fw-normal py-2 px-3">成績一覧（科目）</h2>
                </c:when>
                <c:when test="${searchType == 'st' && not empty testList}">
                    <h2 class="h3 mb-3 fw-normal py-2 px-3">成績一覧（学生）</h2>
                </c:when>
                <c:otherwise>
                    <h2 class="h3 mb-3 fw-normal py-2 px-3">成績参照</h2>
                </c:otherwise>
            </c:choose>

            <!-- VI: Form tìm kiếm theo môn học và theo học sinh -->
            <!-- JP: 科目検索・学生検索の入力フォーム -->
            <div class="glass-filter mx-3 mb-3">

                <!-- VI: Form tìm kiếm theo môn học -->
                <!-- JP: 科目検索フォーム -->
                <form action="TestList.action" method="get">
                    <input type="hidden" name="f" value="sj" />

                    <div class="d-flex align-items-end gap-3 mb-2">
                        <div style="width:80px;"><label class="form-label fw-bold">科目情報</label></div>

                        <!-- VI: SUPERADMIN được chọn trường -->
                        <!-- JP: SUPERADMINのみ学校選択を表示 -->
                        <c:if test="${user.role == 2}">
                            <div>
                                <label class="form-label">学校</label>
                                <select class="form-select" name="school_cd">
                                    <option value="">----</option>
                                    <c:forEach var="sc" items="${schoolList}">
                                        <option value="${sc.cd}"
                                            <c:if test="${sc.cd == school_cd}">selected</c:if>>
                                            ${sc.name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </c:if>

                        <!-- VI: Chọn năm nhập học -->
                        <!-- JP: 入学年度選択 -->
                        <div>
                            <label class="form-label">入学年度</label>
                            <select class="form-select" name="f1">
                                <option value="">----</option>
                                <c:forEach var="year" items="${entYearList}">
                                    <option value="${year}" <c:if test="${searchType == 'sj' && year == f1}">selected</c:if>>${year}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <!-- VI: Chọn lớp -->
                        <!-- JP: クラス選択 -->
                        <div>
                            <label class="form-label">クラス</label>
                            <select class="form-select" name="f2">
                                <option value="">----</option>
                                <c:forEach var="cls" items="${classNumList}">
                                    <option value="${cls}" <c:if test="${searchType == 'sj' && cls == f2}">selected</c:if>>${cls}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <!-- VI: Chọn môn học -->
                        <!-- JP: 科目選択 -->
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
                        <div class="text-warning">${errorSj}</div>
                    </c:if>
                </form>

                <hr/>

                <!-- VI: Form tìm kiếm theo học sinh -->
                <!-- JP: 学生検索フォーム -->
                <form action="TestList.action" method="get">
                    <input type="hidden" name="f" value="st" />

                    <div class="d-flex align-items-end gap-3">
                        <div style="width:80px;"><label class="form-label fw-bold">学生情報</label></div>

                        <!-- VI: SUPERADMIN chọn trường -->
                        <!-- JP: SUPERADMINのみ学校選択を表示 -->
                        <c:if test="${user.role == 2}">
                            <div>
                                <label class="form-label">学校</label>
                                <select class="form-select" name="school_cd">
                                    <option value="">----</option>
                                    <c:forEach var="sc" items="${schoolList}">
                                        <option value="${sc.cd}"
                                            <c:if test="${sc.cd == school_cd}">selected</c:if>>
                                            ${sc.name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </c:if>

                        <!-- VI: Nhập mã số sinh viên -->
                        <!-- JP: 学生番号入力 -->
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
                        <div class="text-warning">${errorSt}</div>
                    </c:if>
                </form>

            </div>

            <!-- VI: Thông báo hướng dẫn ban đầu -->
            <!-- JP: 初期表示メッセージ -->
            <div class="mx-3 mt-3">
                <div class="alert alert-warning mx-3 mt-3">
                    科目情報を選択または学生情報を入力して検索ボタンをクリックしてください
                </div>
            </div>

            <!-- VI: Kết quả tìm kiếm theo môn học -->
            <!-- JP: 科目別検索結果 -->
            <c:if test="${searchType == 'sj' && not empty testList}">
                <div class="mx-3 mb-2">
                    科目：
                    <c:forEach var="s" items="${subjectList}">
                        <c:if test="${s.cd == f3}">${s.name}</c:if>
                    </c:forEach>
                </div>

                <table class="table table-bordered mx-3 w-auto">
                    <thead class="table-light">
                        <tr>
                            <c:if test="${user.role == 2}">
                                <th>学校</th>
                            </c:if>
                            <th>入学年度</th>
                            <th>クラス</th>
                            <th>学生番号</th>
                            <th>氏名</th>
                            <th>1回</th>
                            <th>2回</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="test" items="${testList}">
                            <c:if test="${test.no == 1}">
                                <tr>
                                    <c:if test="${user.role == 2}">
                                        <td>${test.school.name}</td>
                                    </c:if>
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

            <!-- VI: Kết quả tìm kiếm theo học sinh -->
            <!-- JP: 学生別検索結果 -->
            <c:if test="${searchType == 'st' && not empty testList}">
                <div class="mx-3 mb-2">
                    氏名：${student.name}（${student.no}）
                </div>

                <table class="table table-bordered mx-3 w-auto">
                    <thead class="table-light">
                        <tr>
                            <c:if test="${user.role == 2}">
                                <th>学校</th>
                            </c:if>
                            <th>科目名</th>
                            <th>科目コード</th>
                            <th>回数</th>
                            <th>点数</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="test" items="${testList}">
                            <tr>
                                <c:if test="${user.role == 2}">
                                    <td>${test.school.name}</td>
                                </c:if>
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
