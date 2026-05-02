<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">成績削除完了</c:param>

    <c:param name="content">

        <!-- ⭐ CSS làm card hoàn thành dạng glass -->
        <style>
            /* Header mờ nhẹ */
            h2 {
                background: rgba(255,255,255,0.55) !important;
                backdrop-filter: blur(4px);
                border-radius: 6px;
            }

            /* Card glass */
            .glass-card {
                background: rgba(255,255,255,0.65);
                backdrop-filter: blur(6px);
                border: 1px solid #e5e5e5;
                border-radius: 10px;
                padding: 18px 22px;
                box-shadow: 0 2px 6px rgba(0,0,0,0.05);
                color: #155724;
            }
        </style>

        <section class="w-100 pb-3">

            <!-- Tiêu đề -->
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-3">
                成績削除
            </h2>

            <!-- ⭐ Hộp thông báo dạng glass -->
            <div class="mx-3 mb-3 glass-card">
                成績情報の削除が完了しました。
            </div>

            <!-- Nút quay lại -->
            <div class="mx-3">
                <a href="TestList.action" class="btn btn-secondary">戻る</a>
            </div>

        </section>

    </c:param>
</c:import>
