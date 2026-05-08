<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">

    <c:param name="title">
        クラス削除
    </c:param>

    <c:param name="scripts">

        <style>

            .glass-block {
                background: rgba(255,255,255,0.65);
                backdrop-filter: blur(6px);
                border: 1px solid #e5e5e5;
                border-radius: 8px;
                padding: 25px;
                box-shadow: 0 2px 6px rgba(0,0,0,0.05);
            }

            h2 {
                background: rgba(255,255,255,0.55) !important;
                backdrop-filter: blur(4px);
                border-radius: 6px;
            }

        </style>

    </c:param>

    <c:param name="content">

        <section class="me-4">

            <!-- Title -->
            <h2 class="mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                クラス削除
            </h2>

            <!-- Delete card -->
            <div class="glass-block mx-4">

                <p class="mb-4">
                    以下のクラスを削除しますか？
                </p>

                <table class="table">

                    <tr>
                        <th style="width: 180px;">
                            クラス番号
                        </th>

                        <td>
                            ${classNum.class_num}
                        </td>
                    </tr>

                </table>

                <!-- Buttons -->
                <form action="ClassDeleteExecute.action"
                      method="post">

                    <input type="hidden"
                           name="classNum"
                           value="${classNum.class_num}">

                    <button type="submit"
                            class="btn btn-danger">

                        削除

                    </button>

                    <a href="ClassList.action"
                       class="btn btn-secondary ms-2">

                        戻る

                    </a>

                </form>

            </div>

        </section>

    </c:param>

</c:import>