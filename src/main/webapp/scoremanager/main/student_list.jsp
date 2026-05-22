<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="jakarta.tags.core" %> 

<c:import url="/common/base.jsp"> 

    <c:param name="title">得点管理システム</c:param> 
    <c:param name="scripts"></c:param> 

    <c:param name="content"> 

        <!-- VI: CSS làm nổi phần filter và từng dòng bảng -->
        <!-- JP: フィルター部分と表の行を見やすくするCSS -->
        <style>
            #filter {
                background: rgba(255,255,255,0.65);
                backdrop-filter: blur(6px);
                border: 1px solid #e5e5e5;
                box-shadow: 0 2px 6px rgba(0,0,0,0.05);
            }
            table.table th {
                background: rgba(255,255,255,0.75);
                backdrop-filter: blur(4px);
                border-bottom: 2px solid #dcdcdc;
            }
            table.table tbody tr {
                background: rgba(255,255,255,0.60);
                backdrop-filter: blur(4px);
                border-bottom: 1px solid #e5e5e5;
            }
            table.table tbody tr:hover {
                background: rgba(240,247,255,0.85);
            }
        </style>

        <section class="me-4">
            <h2 class="mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                学生管理
            </h2>

            <!-- VI: Nút tạo mới sinh viên -->
            <!-- JP: 新規学生登録ボタン -->
            <div class="my-2 text-end px-4">
                <a href="StudentCreate.action" class="btn btn-primary">新規登録</a>
            </div>

            <!-- VI: Form lọc danh sách sinh viên -->
            <!-- JP: 学生検索フィルターフォーム -->
            <form method="get"> 
                <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">

                    <!-- VI: Chọn trường (chỉ SUPERADMIN) -->
                    <!-- JP: 学校選択（SUPERADMINのみ表示） -->
                    <c:if test="${user.role == 2}">
                        <div class="col-4">
                            <label class="form-label">学校</label>
                            <select class="form-select" name="school_cd">
                                <option value=""></option>
                                <c:forEach var="sc" items="${school_list}">
                                    <option value="${sc.cd}"
                                        <c:if test="${sc.cd == school_cd}">selected</c:if>>
                                        ${sc.name}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </c:if>

                    <!-- VI: Năm nhập học -->
                    <!-- JP: 入学年度 -->
                    <div class="col-4">
                        <label class="form-label" for="student-f1-select">入学年度</label>
                        <select class="form-select" id="student-f1-select" name="f1">
                            <option value=""></option>
                            <c:forEach var="year" items="${ent_year_set}">
                                <option value="${year}" 
                                    <c:if test="${year==f1}">selected</c:if>>
                                    ${year}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- VI: Lớp -->
                    <!-- JP: クラス -->
                    <div class="col-4">
                        <label class="form-label" for="student-f2-select">クラス</label>
                        <select class="form-select" id="student-f2-select" name="f2">
                            <option value=""></option>
                            <c:forEach var="num" items="${class_num_set}">
                                <option value="${num}" 
                                    <c:if test="${num==f2}">selected</c:if>>
                                    ${num}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- VI: Trạng thái đang học -->
                    <!-- JP: 在学中チェック -->
                    <div class="col-2 form-check text-center">
                        <label class="form-check-label" for="student-f3-check">
                            在学中
                            <input class="form-check-input" type="checkbox" id="student-f3-check" name="f3" value="t"
                                <c:if test="${!empty f3}">checked</c:if>>
                        </label>
                    </div>

                    <!-- VI: Nút lọc -->
                    <!-- JP: 絞込みボタン -->
                    <div class="col-2 text-center">
                        <button class="btn btn-secondary" id="filter-button">絞込み</button>
                    </div>

                    <!-- VI: Hiển thị lỗi -->
                    <!-- JP: エラーメッセージ表示 -->
                    <div class="mt-2 text-warning">${errors.get("f1")}</div>

                </div>
            </form>

            <!-- VI: Kết quả tìm kiếm -->
            <!-- JP: 検索結果表示 -->
            <c:choose>

                <c:when test="${students.size() > 0}">
                    <div>検索結果: ${students.size()}件</div>

                    <!-- VI: Bảng danh sách sinh viên -->
                    <!-- JP: 学生一覧テーブル -->
                    <table class="table table-hover">
                        <tr>
                            <th>入学年度</th>
                            <th>学生番号</th>
                            <th>氏名</th>
                            <th>クラス</th>
                            <th class="text-center">在学中</th>
                        </tr>

                        <c:forEach var="student" items="${students}">
                            <tr>
                                <td>${student.entYear}</td>
                                <td>${student.no}</td>
                                <td>${student.name}</td>
                                <td>${student.classNum}</td>

                                <td class="text-center">
                                    <c:choose>
                                        <c:when test="${student.attend}">○</c:when>
                                        <c:otherwise>×</c:otherwise>
                                    </c:choose>
                                </td>

                                <!-- VI: Nút chỉnh sửa -->
                                <!-- JP: 変更ボタン -->
                                <td>
                                    <a class="btn btn-primary" href="StudentUpdate.action?no=${student.no}">変更</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:when>

                <c:otherwise>
                    <!-- VI: Không có dữ liệu -->
                    <!-- JP: データなし -->
                    <c:if test="${empty errors}">
                        <div>学生情報が存在しませんでした。</div>
                    </c:if>
                </c:otherwise>

            </c:choose>

        </section>
    </c:param>
</c:import>
