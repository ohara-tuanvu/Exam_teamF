<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

    <c:param name="title">教員削除完了</c:param>

    <c:param name="content">

        <section class="me-4">

            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                教員削除
            </h2>

            <div class="alert alert-success mx-5 mt-4 text-center">
                削除完了しました。
            </div>

            <div class="text-center mt-4">
                <a href="TeacherList.action" class="btn btn-secondary px-4">
                    戻る
                </a>
            </div>

        </section>

    </c:param>

</c:import>
