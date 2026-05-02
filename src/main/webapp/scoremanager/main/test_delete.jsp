<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">成績削除確認</c:param>

    <c:param name="content">

        <!-- ⭐ CSS glass cho bảng + header + message -->
        <style>
            /* Header mờ nhẹ */
            h2 {
                background: rgba(255,255,255,0.55) !important;
                backdrop-filter: blur(4px);
                border-radius: 6px;
            }

            /* Thông báo xác nhận */
            .glass-msg {
                background: rgba(255,255,255,0.65);
                backdrop-filter: blur(6px);
                border: 1px solid #e5e5e5;
                border-radius: 8px;
                padding: 12px 18px;
                box-shadow: 0 2px 6px rgba(0,0,0,0.05);
            }

            /* Header bảng */
            table.table thead tr th,
            table.table tbody tr th {
                background: rgba(255,255,255,0.75);
                backdrop-filter: blur(4px);
            }

            /* Từng dòng bảng */
            table.table tbody tr td {
                background: rgba(255,255,255,0.60);
                backdrop-filter: blur(4px);
            }

            /* Hover */
            table.table tbody tr:hover td {
                background: rgba(240,247,255,0.85);
            }
        </style>

        <section class="w-100 pb-3">

            <!-- Tiêu đề -->
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-3">
                成績削除確認
            </h2>

            <!-- ⭐ Thông báo xác nhận -->
            <div class="mx-3 mb-3 glass-msg">
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
                <a class="btn btn-secondary" href="javascript:history.back()">戻る</a>
            </div>

        </section>

    </c:param>
</c:import>
