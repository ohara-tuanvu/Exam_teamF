<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">成績削除完了</c:param>

    <c:param name="content">

        <section class="w-100 pb-3">

            <!-- Tiêu đề giống các màn hình khác -->
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-3">
                成績削除
            </h2>

            <!-- Hộp thông báo xanh lá giống StudentUpdateExecute -->
            <div class="mx-3 mb-3"
                 style="background-color:#d4edda; border:1px solid #c3e6cb;
                        border-radius:6px; padding:12px 20px; color:#155724;">
                成績情報の削除が完了しました。
            </div>

            <!-- Nút quay lại -->
            <div class="mx-3">
                <a href="TestList.action" class="btn btn-secondary">戻る</a>
            </div>

        </section>

    </c:param>
</c:import>
