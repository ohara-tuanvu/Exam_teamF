<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">科目情報変更 - 得点管理システム</c:param>

    <c:param name="content">

        <section class="me-4">

            <!-- タイトル -->
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                科目情報変更
            </h2>

            <!-- =============================== -->
            <!-- 更新完了メッセージ（message あり） -->
            <!-- =============================== -->
            <c:if test="${not empty message}">
                <div class="d-flex justify-content-center mt-4">
                    <div class="card shadow-sm border-0" style="max-width: 600px; width: 100%;">
                        <div class="card-body text-center py-5">

                            <!-- Icon -->
                            <div class="mb-3">
                                <span class="text-success" style="font-size: 3rem;">✔</span>
                            </div>

                            <h4 class="text-success fw-bold mb-3">${message}</h4>

                            <p class="text-muted mb-4">
                                科目情報が正常に更新されました。
                            </p>

                            <a href="SubjectList.action" class="btn btn-secondary px-4">
                                戻る
                            </a>

                        </div>
                    </div>
                </div>
            </c:if>

            <!-- =============================== -->
            <!-- 更新フォーム（message なし） -->
            <!-- =============================== -->
            <c:if test="${empty message}">
                <div class="d-flex justify-content-center mt-4">
                    <div class="card shadow-sm border-0" style="max-width: 650px; width: 100%;">
                        <div class="card-body py-4 px-4">

                            <form action="SubjectUpdateExecute.action" method="post">

                                <!-- 科目コード -->
                                <div class="mb-3 w-50">
                                    <label class="form-label">科目コード</label>
                                    <input class="form-control bg-light" type="text" name="cd"
                                           value="${subject.cd}" readonly>
                                </div>

                                <!-- 科目名 -->
                                <div class="mb-3 w-50">
                                    <label class="form-label" for="subject-name-input">科目名</label>
                                    <input class="form-control" type="text" id="subject-name-input" name="name"
                                           value="${subject.name}" required placeholder="科目名を入力してください">
                                    <div class="text-danger small">${errors.get("name")}</div>
                                </div>

                                <!-- ボタン -->
                                <div class="mt-4">
                                    <button type="submit" class="btn btn-primary px-4">変更</button>
                                </div>

                                <div class="mt-3">
                                    <a href="SubjectList.action">戻る</a>
                                </div>

                            </form>

                        </div>
                    </div>
                </div>
            </c:if>

        </section>

    </c:param>
</c:import>
