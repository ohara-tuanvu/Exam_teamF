<%-- メニューJSP: Trang menu chính --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> <%-- Cấu hình JSP UTF-8 --%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%> <%-- Import JSTL core --%>

<c:import url="/common/base.jsp"> <%-- Nhúng layout base.jsp --%>

    <c:param name="title"> <%-- Gửi tiêu đề trang sang base.jsp --%>
        得点管理システム
    </c:param>

    <c:param name="scripts"></c:param> <%-- Không có script riêng cho trang này --%>

    <c:param name="content"> <%-- Nội dung chính của trang menu --%>

        <section class="me-4"> <%-- Khung nội dung, margin-end 4 --%>

            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">
                メニュー
            </h2> <%-- Tiêu đề "メニュー" --%>

            <div class="row text-center px-4 fs-3 my-5"> <%-- Hàng chứa 4 ô menu --%>

                <div class="col d-flex align-items-center justify-content-center mx-2 rounded shadow"
                    style="height: 10rem; background-color: #dbb;">
                    <a href="StudentList.action">学生管理</a> <%-- Link đến danh sách sinh viên --%>
                </div>

                <div class="col d-flex align-items-center justify-content-center mx-2 rounded shadow"
                    style="height: 10rem; background-color: #bdb;">
                    <div>
                        <div class="">成績管理</div> <%-- Nhóm quản lý điểm --%>

                        <div class="">
                            <a href="TestRegist.action">成績登録</a> <%-- Link đăng ký điểm --%>
                        </div>

                        <div class="">
                            <a href="TestList.action">成績参照</a> <%-- Link xem danh sách điểm --%>
                        </div>
                    </div>
                </div>

                <div class="col d-flex align-items-center justify-content-center mx-2 rounded shadow"
                    style="height: 10rem; background-color: #bbd;">
                    <a href="SubjectList.action">科目管理</a> <%-- Link quản lý môn học --%>
                </div>

                <div class="col d-flex align-items-center justify-content-center mx-2 rounded shadow"
                    style="height: 10rem; background-color: #ddb;">
                    <a href="ClassList.action">クラス管理</a> <%-- Link quản lý lớp học --%>
                </div>

            </div>
        </section>

    </c:param>
</c:import>
