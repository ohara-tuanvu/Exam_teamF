<%-- 科目登録完了画面（Trang hoàn thành đăng ký môn học） --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

    <c:param name="title">
        科目登録完了 - 得点管理システム
    </c:param>

    <c:param name="content">

        <!-- ⭐ CSS làm card và header nổi rõ nhưng vẫn thấy background -->
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
                box-shadow: 0 2px 6px rgba(0,0,0,0.05);
            }
        </style>

        <section class="me-4">

            <!-- タイトル -->
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4 text-start">
                科目情報登録
            </h2>

            <!-- 完了メッセージカード -->
            <div class="d-flex justify-content-center mt-5">
                <div class="card shadow-sm border-0 glass-card" style="max-width: 600px; width: 100%;">
                    <div class="card-body text-center py-5">

                        <!-- Icon -->
                        <div class="mb-3">
                            <span class="text-success" style="font-size: 3rem;">✔</span>
                        </div>

                        <!-- メッセージ -->
                        <h4 class="text-success fw-bold mb-3">登録が完了しました。</h4>

                        <p class="text-muted mb-4">
                            新しい科目が正常にデータベースへ保存されました。
                        </p>

                        <!-- 戻るボタン -->
                        <a href="SubjectList.action" class="btn btn-primary px-4">
                            科目一覧へ戻る
                        </a>

                    </div>
                </div>
            </div>

        </section>

    </c:param>

</c:import>
