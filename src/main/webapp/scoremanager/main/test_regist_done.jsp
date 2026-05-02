<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">成績登録完了</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">

        <!-- ⭐ CSS glass cho header + card -->
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
                font-weight: 500;
            }
        </style>

        <section class="w-100 pb-3">

            <!-- Tiêu đề -->
            <h2 class="h3 mb-3 fw-normal py-2 px-3">成績管理</h2>

            <!-- ⭐ Thông báo hoàn thành dạng glass -->
            <div class="mx-3 mb-3 glass-card">
                登録が完了しました。
            </div>

            <!-- ⭐ GIỮ NGUYÊN 100% NHƯ BẠN YÊU CẦU -->
            <div class="mx-3 mt-3">
                <a href="TestRegist.action">戻る</a>
                &nbsp;&nbsp;&nbsp;
                <a href="TestList.action">成績参照</a>
            </div>

        </section>

    </c:param>
</c:import>
