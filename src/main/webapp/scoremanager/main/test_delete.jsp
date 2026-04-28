<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">成績削除確認</c:param>

    <c:param name="content">

        <section class="w-100 pb-3">

            <!-- Tiêu đề giống các màn hình khác -->
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-3">
                成績削除確認
            </h2>

            <div class="mx-3 mb-3">
                以下の成績を削除します。よろしいですか？
            </div>

            <!-- Bảng thông tin -->
            <table class="table table-bordered mx-3 w-auto">
                <tbody>
                    <tr>
                        <th>学生番号</th>
                        <td>${test.student.no}</td>
                    </tr>
                    <tr>
                        <th>氏名</th>
                        <td>${test.student.name}</td>
                    </tr>
                    <tr>
                        <th>科目</th>
                        <td>${test.subject.name}</td>
                    </tr>
                    <tr>
                        <th>回数</th>
                        <td>${test.no}</td>
                    </tr>
                    <tr>
                        <th>点数</th>
                        <td>${test.point}</td>
                    </tr>
                </tbody>
            </table>

            <!-- Nút thao tác -->
            <div class="mx-3 mt-3 d-flex gap-3">

                <!-- Nút xóa -->
                <a class="btn btn-danger"
                   href="TestDeleteExecute.action?studentNo=${test.student.no}&subjectCd=${test.subject.cd}&no=${test.no}">
                    削除する
                </a>

                <!-- Nút quay lại -->
                <a class="btn btn-secondary" href="TestRegist.action">戻る</a>
            </div>

        </section>

    </c:param>
</c:import>
