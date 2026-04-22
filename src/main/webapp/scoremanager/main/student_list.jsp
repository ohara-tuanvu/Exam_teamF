<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%-- Cấu hình JSP UTF-8 --%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %> 
<%-- Import JSTL core --%>

<c:import url="/common/base.jsp"> 
    <%-- Nhúng layout base.jsp --%>

    <c:param name="title">得点管理システム</c:param> 
    <%-- Tiêu đề trang --%>

    <c:param name="scripts"></c:param> 
    <%-- Không có script riêng --%>

    <c:param name="content"> 
        <%-- Nội dung chính của trang --%>

        <section class="me-4">
            <h2 class="mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生管理</h2>
            <%-- Tiêu đề trang --%>

            <div class="my-2 text-end px-4">
                <a href="StudentCreate.action">新規登録</a>
                <%-- Link tạo mới sinh viên --%>
            </div>

            <form method="get"> 
                <%-- Form lọc danh sách sinh viên --%>

                <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">

                    <div class="col-4">
                        <label class="form-label" for="student-f1-select">入学年度</label>
                        <%-- Chọn năm nhập học --%>

                        <select class="form-select" id="student-f1-select" name="f1">
                            <option value=""></option> <%-- Option rỗng --%>

                            <c:forEach var="year" items="${ent_year_set}">
                                <%-- Lặp danh sách năm nhập học --%>

                                <option value="${year}" 
                                    <c:if test="${year==f1}">selected</c:if>>
                                    <%-- Giữ lại giá trị đã chọn --%>
                                    ${year}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-4">
                        <label class="form-label" for="student-f2-select">クラス</label>
                        <%-- Chọn lớp --%>

                        <select class="form-select" id="student-f2-select" name="f2">
                            <option value=""></option>

                            <c:forEach var="num" items="${class_num_set}">
                                <%-- Lặp danh sách lớp --%>

                                <option value="${num}" 
                                    <c:if test="${num==f2}">selected</c:if>>
                                    <%-- Giữ lại lớp đã chọn --%>
                                    ${num}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-2 form-check text-center">
                        <label class="form-check-label" for="student-f3-check">在学中
                            <%-- Checkbox lọc sinh viên đang học --%>

                            <input class="form-check-input" type="checkbox" id="student-f3-check" name="f3" value="t"
                                <c:if test="${!empty f3}">checked</c:if>>
                                <%-- Nếu f3 có giá trị → checked --%>
                        </label>
                    </div>

                    <div class="col-2 text-center">
                        <button class="btn btn-secondary" id="filter-button">絞込み</button>
                        <%-- Nút lọc --%>
                    </div>

                    <div class="mt-2 text-warning">${errors.get("f1")}</div>
                    <%-- Hiển thị lỗi nhập năm nếu có --%>

                </div>
            </form>

            <c:choose>
                <%-- Kiểm tra có kết quả hay không --%>

                <c:when test="${students.size() > 0}">
                    <%-- Nếu có sinh viên --%>

                    <div>検索結果: ${students.size()}件</div>
                    <%-- Hiển thị số lượng kết quả --%>

                    <table class="table table-hover">
                        <tr>
                            <th>入学年度</th>
                            <th>学生番号</th>
                            <th>氏名</th>
                            <th>クラス</th>
                            <th class="text-center">在学中</th>
                        </tr>

                        <c:forEach var="student" items="${students}">
                            <%-- Lặp danh sách sinh viên --%>

                            <tr>
                                <td>${student.entYear}</td> <%-- Năm nhập học --%>
                                <td>${student.no}</td> <%-- Mã sinh viên --%>
                                <td>${student.name}</td> <%-- Tên --%>
                                <td>${student.classNum}</td> <%-- Lớp --%>

                                <td class="text-center">
                                    <c:choose>
                                        <c:when test="${student.isAttend()}">○</c:when>
                                        <%-- Nếu đang học → ○ --%>

                                        <c:otherwise>×</c:otherwise>
                                        <%-- Nếu nghỉ học → × --%>
                                    </c:choose>
                                </td>

                                <td>
                                    <a href="StudentUpdate.action?no=${student.no}">変更</a>
                                    <%-- Link sửa thông tin sinh viên --%>
                                </td>
                                
                    
                                
                            </tr>
                        </c:forEach>
                    </table>
                </c:when>

                <c:otherwise>
                    <%-- Nếu không có sinh viên --%>
                    <div>学生情報が存在しませんでした。</div>
                </c:otherwise>

            </c:choose>

        </section>
    </c:param>
</c:import>
