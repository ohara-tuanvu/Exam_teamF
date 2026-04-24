<%-- 学生一覧画面（Trang danh sách sinh viên） --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%-- JSPの文字コード設定 UTF-8（Cấu hình JSP UTF-8） --%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %> 
<%-- JSTLコアタグライブラリをインポート（Import JSTL core） --%>

<c:import url="/common/base.jsp"> 
    <%-- 共通レイアウト base.jsp を読み込む（Nhúng layout base.jsp） --%>

    <c:param name="title">得点管理システム</c:param> 
    <%-- タイトルをbase.jspに渡す（Tiêu đề trang） --%>

    <c:param name="scripts"></c:param> 
    <%-- スクリプトなし（Không có script riêng） --%>

    <c:param name="content"> 
        <%-- ページのメインコンテンツ（Nội dung chính của trang） --%>

        <section class="me-4">
            <h2 class="mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                学生管理
            </h2>
            <%-- 画面タイトル（Tiêu đề trang） --%>

            <div class="my-2 text-end px-4">
                <a href="StudentCreate.action">新規登録</a>
                <%-- 学生新規登録画面へ遷移（Link tạo mới sinh viên） --%>
            </div>

            <form method="get"> 
                <%-- 検索条件フォーム（Form lọc danh sách sinh viên） --%>

                <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">

                    <!-- 入学年度 -->
                    <div class="col-4">
                        <label class="form-label" for="student-f1-select">入学年度</label>
                        <%-- 入学年度選択（Chọn năm nhập học） --%>

                        <select class="form-select" id="student-f1-select" name="f1">
                            <option value=""></option> <%-- 未選択（Option rỗng） --%>

                            <c:forEach var="year" items="${ent_year_set}">
                                <%-- 入学年度一覧ループ（Lặp danh sách năm nhập học） --%>

                                <option value="${year}" 
                                    <c:if test="${year==f1}">selected</c:if>>
                                    <%-- 選択値保持（Giữ lại giá trị đã chọn） --%>
                                    ${year}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- クラス -->
                    <div class="col-4">
                        <label class="form-label" for="student-f2-select">クラス</label>
                        <%-- クラス選択（Chọn lớp） --%>

                        <select class="form-select" id="student-f2-select" name="f2">
                            <option value=""></option>

                            <c:forEach var="num" items="${class_num_set}">
                                <%-- クラス一覧ループ（Lặp danh sách lớp） --%>

                                <option value="${num}" 
                                    <c:if test="${num==f2}">selected</c:if>>
                                    <%-- 選択値保持（Giữ lại lớp đã chọn） --%>
                                    ${num}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- 在学中 -->
                    <div class="col-2 form-check text-center">
                        <label class="form-check-label" for="student-f3-check">
                            在学中
                            <%-- 在学中フラグ（Checkbox lọc sinh viên đang học） --%>

                            <input class="form-check-input" type="checkbox" id="student-f3-check" name="f3" value="t"
                                <c:if test="${!empty f3}">checked</c:if>>
                                <%-- チェック状態保持（Nếu f3 có giá trị → checked） --%>
                        </label>
                    </div>

                    <!-- 検索ボタン -->
                    <div class="col-2 text-center">
                        <button class="btn btn-secondary" id="filter-button">絞込み</button>
                        <%-- 検索実行（Nút lọc） --%>
                    </div>

                    <!-- エラーメッセージ -->
                    <div class="mt-2 text-warning">${errors.get("f1")}</div>
                    <%-- 入学年度エラー表示（Hiển thị lỗi nhập năm nếu có） --%>

                </div>
            </form>

            <c:choose>
                <%-- 検索結果有無判定（Kiểm tra có kết quả hay không） --%>

                <c:when test="${students.size() > 0}">
                    <%-- データあり（Nếu có sinh viên） --%>

                    <div>検索結果: ${students.size()}件</div>
                    <%-- 件数表示（Hiển thị số lượng kết quả） --%>

                    <table class="table table-hover">
                        <tr>
                            <th>入学年度</th>
                            <th>学生番号</th>
                            <th>氏名</th>
                            <th>クラス</th>
                            <th class="text-center">在学中</th>
                        </tr>

                        <c:forEach var="student" items="${students}">
                            <%-- 学生一覧ループ（Lặp danh sách sinh viên） --%>

                            <tr>
                                <td>${student.entYear}</td> <%-- 入学年度（Năm nhập học） --%>
                                <td>${student.no}</td> <%-- 学生番号（Mã sinh viên） --%>
                                <td>${student.name}</td> <%-- 氏名（Tên） --%>
                                <td>${student.classNum}</td> <%-- クラス（Lớp） --%>

                                <td class="text-center">
                                    <c:choose>
                                        <c:when test="${student.attend}">○</c:when>
                                        <%-- 在学中（Nếu đang học） --%>

                                        <c:otherwise>×</c:otherwise>
                                        <%-- 非在学（Nếu nghỉ học） --%>
                                    </c:choose>
                                </td>

                                <td>
                                    <a href="StudentUpdate.action?no=${student.no}">変更</a>
                                    <%-- 学生更新画面へ遷移（Link sửa thông tin sinh viên） --%>
                                </td>

                            </tr>
                        </c:forEach>
                    </table>
                </c:when>

                <c:otherwise>
                    <%-- データなし（Nếu không có sinh viên） --%>
                    <div>学生情報が存在しませんでした。</div>
                </c:otherwise>

            </c:choose>

        </section>
    </c:param>
</c:import>