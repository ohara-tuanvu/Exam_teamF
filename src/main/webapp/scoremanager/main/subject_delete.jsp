<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

    <c:param name="title">科目情報削除 - 得点管理システム</c:param>

    <c:param name="content">

        <section class="me-4">

            <!-- タイトル -->
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4 text-danger">
                科目情報削除
            </h2>

            <div class="mt-4 px-4 d-flex justify-content-center">

                <div class="card shadow-sm border-0" style="max-width: 650px; width: 100%;">
                    <div class="card-body py-5">

                        <!-- =============================== -->
                        <!-- 削除完了（done = true） -->
                        <!-- =============================== -->
                        <c:if test="${done}">
                            <div class="text-center">

                                <!-- Icon -->
                                <div class="mb-3">
                                    <span class="text-success" style="font-size: 3rem;">✔</span>
                                </div>

                                <h4 class="text-success fw-bold mb-3">
                                    削除が完了しました。
                                </h4>

                                <p class="text-muted mb-4">
                                    科目情報は正常に削除されました。
                                </p>

                                <a href="SubjectList.action" class="btn btn-primary px-4">
                                    科目一覧へ戻る
                                </a>
                            </div>
                        </c:if>

                        <!-- =============================== -->
                        <!-- 削除前の確認画面（done = false） -->
                        <!-- =============================== -->
                        <c:if test="${!done}">

                            <!-- Icon -->
                            <div class="text-center mb-4">
                                <span class="text-warning" style="font-size: 3rem;">⚠</span>
                            </div>

                            <!-- メッセージ -->
                            <h4 class="text-center fw-bold mb-3 text-danger">
                                以下の科目を削除します。よろしいですか？
                            </h4>

                            <!-- 科目情報 -->
                            <table class="table table-bordered mt-4">
                                <tr>
                                    <th class="table-light w-25">科目コード</th>
                                    <td>${subject.cd}</td>
                                </tr>
                                <tr>
                                    <th class="table-light w-25">科目名</th>
                                    <td>${subject.name}</td>
                                </tr>
                            </table>

                            <!-- 削除実行フォーム -->
                            <form action="SubjectDeleteExecute.action" method="post" class="text-center mt-4">

                                <input type="hidden" name="cd" value="${subject.cd}">

                                <button type="submit" class="btn btn-danger px-4">
                                    削除
                                </button>

                                <a href="SubjectList.action" class="btn btn-outline-secondary ms-2 px-4">
                                    戻る
                                </a>

                            </form>

                        </c:if>

                    </div>
                </div>

            </div>
        </section>

    </c:param>

</c:import>
