<%-- 学生一覧画面（Trang danh sách sinh viên） --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<%@ taglib prefix="c" uri="jakarta.tags.core" %> 

<c:import url="/common/base.jsp"> 

    <c:param name="title">得点管理システム</c:param> 
    <c:param name="scripts"></c:param> 

    <c:param name="content"> 

        <!-- ⭐ CSS làm nổi từng dòng + filter block -->
        <style>
            /* Khối filter nổi rõ */
            #filter {
                background: rgba(255,255,255,0.65);
                backdrop-filter: blur(6px);
                border: 1px solid #e5e5e5;
                box-shadow: 0 2px 6px rgba(0,0,0,0.05);
            }

            /* Header bảng */
            table.table th {
                background: rgba(255,255,255,0.75);
                backdrop-filter: blur(4px);
                border-bottom: 2px solid #dcdcdc;
            }

            /* ⭐ Từng dòng bảng nổi rõ */
            table.table tbody tr {
                background: rgba(255,255,255,0.60);
                backdrop-filter: blur(4px);
                border-bottom: 1px solid #e5e5e5;
            }

            /* Hover nhẹ */
            table.table tbody tr:hover {
                background: rgba(240,247,255,0.85);
            }
        </style>

        <section class="me-4">

    <!-- Title -->
    <h2 class="mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
        クラス管理
    </h2>

    <!-- 新規登録 button -->
    <div class="my-2 text-end px-4">
        <a href="ClassCreate.action" class="btn btn-primary shadow-sm">
            ＋ 新規登録
        </a>
    </div>

    <!-- Filter -->
    <form method="get">

        <div class="row border mx-3 mb-3 py-3 align-items-end rounded"
             id="filter">

            <!-- 入学年度 -->
            <div class="col-4">
                <label class="form-label" for="class-f1-select">
                    入学年度
                </label>

                <select class="form-select"
                        id="class-f1-select"
                        name="f1">

                    <option value="">-- 選択してください --</option>

                    <c:forEach var="year" items="${ent_year_set}">
                        <option value="${year}"
                            <c:if test="${year == f1}">
                                selected
                            </c:if>>
                            ${year}
                        </option>
                    </c:forEach>

                </select>
            </div>

            <!-- クラス -->
            <div class="col-4">
                <label class="form-label" for="class-f2-select">
                    クラス
                </label>

                <select class="form-select"
                        id="class-f2-select"
                        name="f2">

                    <option value="">-- 選択してください --</option>

                    <c:forEach var="num" items="${class_num_set}">
                        <option value="${num}"
                            <c:if test="${num == f2}">
                                selected
                            </c:if>>
                            ${num}
                        </option>
                    </c:forEach>

                </select>
            </div>

            <!-- Search -->
            <div class="col-2">
                <button class="btn btn-secondary w-100"
                        id="filter-button">

                    絞込み

                </button>
            </div>

        </div>
    </form>

    <!-- Table -->
    <c:choose>

        <c:when test="${classes.size() > 0}">

            <div class="px-3 mb-2">
                検索結果：${classes.size()}件
            </div>

            <table class="table table-hover mx-3">

                <thead>
                    <tr>
                        <th>入学年度</th>
                        <th>クラス番号</th>
                        <th>学生数</th>
                        <th></th>
                    </tr>
                </thead>

                <tbody>

                    <c:forEach var="cls" items="${classes}">

                        <tr>

                            <td>${cls.entYear}</td>

                            <td>
                                <span class="fw-semibold">
                                    ${cls.classNum}
                                </span>
                            </td>

                            <td>${cls.studentCount}</td>

                            <td class="text-end">

                                <a class="btn btn-sm btn-outline-primary"
                                   href="ClassUpdate.action?classNum=${cls.classNum}">
                                    変更
                                </a>

                                <a class="btn btn-sm btn-outline-danger"
                                   href="ClassDelete.action?classNum=${cls.classNum}">
                                    削除
                                </a>

                            </td>

                        </tr>

                    </c:forEach>

                </tbody>

            </table>

        </c:when>

        <c:otherwise>

            <div class="alert alert-light border mx-3">
                クラス情報が存在しませんでした。
            </div>

        </c:otherwise>

    </c:choose>

</section>
    </c:param>
</c:import>
