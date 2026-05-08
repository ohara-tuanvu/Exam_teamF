<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">

    <c:param name="title">
        クラス削除完了
    </c:param>

    <c:param name="scripts">

        <style>

            .glass-block {
                background: rgba(255,255,255,0.65);
                backdrop-filter: blur(6px);
                border: 1px solid #e5e5e5;
                border-radius: 8px;
                padding: 30px;
                box-shadow: 0 2px 6px rgba(0,0,0,0.05);
            }

        </style>

    </c:param>

    <c:param name="content">

        <section class="me-4">

            <h2 class="mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                クラス削除完了
            </h2>

            <div class="glass-block mx-4 text-center">

                <div class="alert alert-success">
                    クラスを削除しました。
                </div>

                <a href="ClassList.action"
                   class="btn btn-primary mt-2">

                    クラス一覧へ

                </a>

            </div>

        </section>

    </c:param>

</c:import>