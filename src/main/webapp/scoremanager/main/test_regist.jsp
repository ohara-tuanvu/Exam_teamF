<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">成績登録</c:param>

    <c:param name="scripts">

        <!-- VI: Hàm kiểm tra điểm nhập vào (0–100) -->
        <!-- JP: 点数入力チェック（0〜100） -->
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

        <!-- VI: CSS hiệu ứng kính mờ cho giao diện -->
        <!-- JP: 画面用ガラス風デザインCSS -->
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

            .glass-warning {
                background: rgba(255,250,220,0.75);
                backdrop-filter: blur(4px);
                border: 1px solid #e6d87a;
                border-radius: 8px;
                padding: 12px 20px;
                color: #7a6a00;
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

            .form-control {
                background: rgba(255,255,255,0.85);
            }
        </style>

        <!-- VI: Ẩn nút tăng giảm của input number -->
        <!-- JP: number入力の矢印を非表示 -->
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

            <!-- VI: Tiêu đề màn hình -->
            <!-- JP: 画面タイトル -->
            <h2 class="h3 mb-3 fw-normal py-2 px-3">成績管理</h2>

            <!-- VI: Form tìm kiếm điều kiện nhập điểm -->
            <!-- JP: 成績登録の検索フォーム -->
            <form action="TestRegist.action" method="get" class="glass-filter mx-3 mb-3">
                <div class="d-flex align-items-end gap-3">

                    <!-- VI: SUPERADMIN được chọn trường -->
                    <!-- JP: SUPERADMINのみ学校選択を表示 -->
                    <c:if test="${user.role == 2}">
                        <div>
                            <label class="form-label">学校</label>
                            <select class="form-select" name="school_cd">
                                <option value="">--------</option>
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
                            <option value="">--------</option>
                            <c:forEach var="year" items="${entYearList}">
                                <option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- VI: Chọn lớp -->
                    <!-- JP: クラス選択 -->
                    <div>
                        <label class="form-label">クラス</label>
                        <select class="form-select" name="f2">
                            <option value="">--------</option>
                            <c:forEach var="cls" items="${classNumList}">
                                <option value="${cls}" <c:if test="${cls == f2}">selected</c:if>>${cls}</option>
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
                                <option value="${subject.cd}" <c:if test="${subject.cd == f3}">selected</c:if>>${subject.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- VI: Chọn lần thi -->
                    <!-- JP: 回数選択 -->
                    <div>
                        <label class="form-label">回数</label>
                        <select class="form-select" name="f4">
                            <option value="">--------</option>
                            <c:forEach var="n" items="${noList}">
                                <option value="${n}" <c:if test="${n == f4}">selected</c:if>>${n}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- VI: Nút tìm kiếm -->
                    <!-- JP: 検索ボタン -->
                    <div>
                        <button type="submit" class="btn btn-primary">検索</button>
                    </div>
                </div>
            </form>

            <!-- VI: Hiển thị lỗi nhập điều kiện -->
            <!-- JP: 入力エラー表示 -->
            <c:if test="${not empty errors}">
                <c:forEach var="err" items="${errors}">
                    <div class="text-warning mx-3">${err}</div>
                </c:forEach>
            </c:if>

            <!-- VI: Cảnh báo khi không tìm thấy dữ liệu -->
            <!-- JP: データ未取得時の警告表示 -->
            <c:if test="${not empty searchError}">
                <div class="glass-warning mx-3 mb-3">
                    ${searchError}
                </div>
            </c:if>

            <!-- VI: Bảng nhập điểm -->
            <!-- JP: 成績入力テーブル -->
            <c:if test="${not empty studentList}">
                <form action="TestRegistExecute.action" method="post">

                    <!-- VI: Giữ lại điều kiện tìm kiếm -->
                    <!-- JP: 検索条件の保持 -->
                    <input type="hidden" name="f1" value="${f1}" />
                    <input type="hidden" name="f2" value="${f2}" />
                    <input type="hidden" name="f3" value="${f3}" />
                    <input type="hidden" name="f4" value="${f4}" />
                    <input type="hidden" name="school_cd" value="${school_cd}" />

                    <!-- VI: Hiển thị môn học và lần thi -->
                    <!-- JP: 科目名と回数の表示 -->
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

                                <!-- VI: SUPERADMIN xem được trường -->
                                <!-- JP: SUPERADMINのみ学校列を表示 -->
                                <c:if test="${user.role == 2}">
                                    <th>学校</th>
                                </c:if>

                                <th>入学年度</th>
                                <th>クラス</th>
                                <th>学生番号</th>
                                <th>氏名</th>
                                <th>点数</th>
                                <th>削除</th>
                            </tr>
                        </thead>

                        <tbody>
                            <c:forEach var="student" items="${studentList}">
                                <tr>

                                    <!-- VI: SUPERADMIN hiển thị trường -->
                                    <!-- JP: SUPERADMIN用の学校表示 -->
                                    <c:if test="${user.role == 2}">
                                        <td>${student.school.name}</td>
                                    </c:if>

                                    <td>${student.entYear}</td>
                                    <td>${student.classNum}</td>
                                    <td>${student.no}</td>
                                    <td>${student.name}</td>

                                    <!-- VI: Ô nhập điểm -->
                                    <!-- JP: 点数入力欄 -->
                                    <td>
                                        <input type="number" class="form-control" style="width:100px;"
                                               name="point_${student.no}"
                                               value="${pointMap[student.no]}"
                                               oninput="checkPoint(this)" />
                                        <div class="text-warning small point-error" style="display:none;">
                                            0〜100の範囲で入力してください
                                        </div>
                                    </td>

                                    <!-- VI: Nút xóa điểm -->
                                    <!-- JP: 点数削除ボタン -->
                                    <td>
                                        <c:if test="${not empty pointMap[student.no]}">
                                            <a class="btn btn-danger btn-sm"
                                               href="TestDelete.action?studentNo=${student.no}&subjectCd=${f3}&no=${f4}">
                                                削除
                                            </a>
                                        </c:if>
                                    </td>

                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <!-- VI: Nút lưu điểm -->
                    <!-- JP: 登録ボタン -->
                    <div class="mx-3">
                        <button type="submit" class="btn btn-primary">登録して終了</button>
                    </div>

                </form>
            </c:if>

        </section>

    </c:param>
</c:import>
