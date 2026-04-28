<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">成績登録完了</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">

        <section class="w-100 pb-3">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-3">成績管理</h2>

            <div class="alert alert-success mx-3">登録が完了しました</div>

            <div class="mx-3 mt-3">
                <a href="TestRegist.action">戻る</a>
                &nbsp;&nbsp;&nbsp;
                <a href="TestList.action">成績参照</a>
            </div>
        </section>

    </c:param>
</c:import>
