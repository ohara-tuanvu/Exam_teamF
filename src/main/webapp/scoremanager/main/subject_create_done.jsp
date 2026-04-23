<%-- 科目登録完了JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<%-- common/base.jsp をインポートして全体のレイアウトを統一 --%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		科目登録完了 - 得点管理システム
	</c:param>

	<c:param name="content">
		<section class="me-4 text-center">
			<%-- タイトルバー --%>
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4 text-start">
				科目情報登録
			</h2>

			<%-- 完了メッセージ --%>
			<div class="my-5">
				<h3 class="h4 mb-4">登録が完了しました。</h3>
				<p class="text-muted">
					新しい科目が正常にデータベースへ保存されました。
				</p>
			</div>

			<%-- ナビゲーションボタン --%>
			<div class="mt-4">
				<%-- SỬA TẠI ĐÂY: Chuyển từ SubjectCreate.action sang SubjectList.action --%>
				<a href="SubjectList.action" class="btn btn-primary px-4">
					科目一覧へ戻る
				</a>
			</div>
		</section>
	</c:param>
</c:import>