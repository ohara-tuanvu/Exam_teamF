<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

    <c:param name="title">成績登録完了</c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">

        <section class="w-100 pb-3">

            <!-- タイトル -->
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                成績登録完了
            </h2>

            <!-- 完了メッセージ -->
            <div class="px-4">
                <p class="fs-5">成績の登録が完了しました。</p>

                <!-- 戻るリンク -->
                <a href="TestRegist.action" class="btn btn-primary mt-3">
                    成績登録画面へ戻る
                </a>
            </div>

        </section>

    </c:param>

</c:import>
