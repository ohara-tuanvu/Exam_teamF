<%-- 科目登録完了画面（Trang hoàn thành đăng ký môn học） --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- JSPの文字コード設定 UTF-8（Cấu hình JSP UTF-8） --%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%-- JSTLコアタグライブラリをインポート（Import JSTL core） --%>

<c:import url="/common/base.jsp">
<%-- 共通レイアウト base.jsp を読み込む（Nhúng layout base.jsp） --%>

	<c:param name="title">
		科目登録完了 - 得点管理システム
	</c:param>
	<%-- タイトル設定（Tiêu đề trang） --%>

	<c:param name="content">
	<%-- ページのメインコンテンツ（Nội dung chính của trang） --%>

		<section class="me-4 text-center">

			<!-- タイトル -->
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4 text-start">
				科目情報登録
			</h2>
			<%-- 画面タイトル（Tiêu đề màn hình） --%>

			<!-- 完了メッセージ -->
			<div class="my-5">
				<h3 class="h4 mb-4">登録が完了しました。</h3>
				<%-- 登録成功メッセージ（Thông báo đăng ký thành công） --%>

				<p class="text-muted">
					新しい科目が正常にデータベースへ保存されました。
				</p>
				<%-- 補足説明（Mô tả bổ sung） --%>
			</div>

			<!-- ナビゲーション -->
			<div class="mt-4">
				<a href="SubjectList.action" class="btn btn-primary px-4">
					科目一覧へ戻る
				</a>
				<%-- 科目一覧画面へ遷移（Quay lại danh sách môn học） --%>
			</div>

		</section>
	</c:param>
</c:import>