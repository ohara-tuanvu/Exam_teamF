<%-- 科目登録画面（Trang đăng ký môn học） --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

    <c:param name="title">科目情報登録 - 得点管理システム</c:param>

    <c:param name="content">

        <!-- ⭐ CSS làm form nổi rõ nhưng vẫn thấy background -->
        <style>
            /* Header mờ nhẹ */
            h2 {
                background: rgba(255,255,255,0.55) !important;
                backdrop-filter: blur(4px);
                border-radius: 6px;
            }

            /* Form glass */
            .glass-block {
                background: rgba(255,255,255,0.65);
                backdrop-filter: blur(6px);
                border: 1px solid #e5e5e5;
                border-radius: 8px;
                padding: 25px;
                box-shadow: 0 2px 6px rgba(0,0,0,0.05);
                width: fit-content;
            }

            /* Input rõ hơn */
            .form-control, .form-select {
                background: rgba(255,255,255,0.85);
                border: 1px solid #ccc;
            }
        </style>

        <section class="me-4">

            <!-- タイトル -->
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                科目情報登録
            </h2>

            <!-- 入力フォーム -->
            <form action="SubjectCreateExecute.action" method="post" class="mt-4 px-4 glass-block">

                <!-- 科目コード -->
                <div class="mb-3 w-50">
                    <label class="form-label" for="subject-cd-input">科目コード</label>

                    <input class="form-control" type="text" id="subject-cd-input" name="cd" 
                           value="${cd}" placeholder="科目コードを入力してください">

                    <div class="text-danger small">${errors.get("cd")}</div>
                </div>

                <!-- 科目名 -->
                <div class="mb-3 w-50">
                    <label class="form-label" for="subject-name-input">科目名</label>

                    <input class="form-control" type="text" id="subject-name-input" name="name" 
                           value="${name}" placeholder="科目名を入力してください">

                    <div class="text-danger small">${errors.get("name")}</div>
                </div>

                <!-- 登録ボタン -->
                <div class="mt-4">
                    <button type="submit" class="btn btn-primary">登録</button>
                </div>
                
                <!-- 戻るリンク -->
                <div class="mt-3">
                    <a href="SubjectList.action">戻る</a>
                </div>

            </form>
        </section>

    </c:param>
</c:import>
