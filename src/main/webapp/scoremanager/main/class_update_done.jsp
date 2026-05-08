<%-- クラス更新完了画面 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">

    <c:param name="title">クラス変更完了</c:param>

    <c:param name="scripts">

        <style>

            /* Fade animation */
            .fade-in {
                animation: fadeIn 0.4s ease-in-out;
            }

            @keyframes fadeIn {
                from {
                    opacity: 0;
                    transform: translateY(10px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }

            /* Glass card */
            .done-card {
                background: rgba(255,255,255,0.72);
                backdrop-filter: blur(6px);
                border: 1px solid #e5e5e5;
                border-radius: 12px;
                box-shadow: 0 4px 12px rgba(0,0,0,0.06);
            }

        </style>

    </c:param>

    <c:param name="content">

        <section class="me-4 fade-in">

            <!-- Title -->
            <h2 class="mb-4 fw-normal bg-secondary bg-opacity-10 py-2 px-4 rounded">
                クラス変更完了
            </h2>

            <!-- Success card -->
            <div class="d-flex justify-content-center">

                <div class="done-card p-5 text-center" style="min-width: 350px;">

                    <!-- Icon -->
                    <div class="mb-3" style="font-size: 48px;">
                        ✅
                    </div>

                    <!-- Message -->
                    <h4 class="mb-3">
                        更新が完了しました
                    </h4>

                    <p class="text-muted mb-4">
                        クラス情報が正常に更新されました。
                    </p>

                    <!-- Buttons -->
                    <div class="d-flex justify-content-center gap-2">

                        <a href="${pageContext.request.contextPath}/scoremanager/main/ClassList.action"
                           class="btn btn-secondary">

                            一覧へ戻る

                        </a>

                        <a href="${pageContext.request.contextPath}/scoremanager/main/Menu.action"
                           class="btn btn-primary">

                            メニューへ

                        </a>

                    </div>

                </div>

            </div>

        </section>

    </c:param>

</c:import>