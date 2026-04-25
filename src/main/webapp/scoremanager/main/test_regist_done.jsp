<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- VI: Cấu hình trang JSP (ngôn ngữ Java, UTF-8)  
    JP: JSPページ設定（Java使用、UTF-8） --%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%-- VI: Sử dụng JSTL core tag  
    JP: JSTL Core タグライブラリの利用 --%>

<c:import url="/common/base.jsp">
<%-- VI: Import layout chung base.jsp  
    JP: 共通レイアウト base.jsp を読み込む --%>

    <c:param name="title">成績登録完了</c:param>
    <%-- VI: Tiêu đề trang (hiển thị trên header)  
        JP: ページタイトル（ヘッダーに表示） --%>

    <c:param name="scripts"></c:param>
    <%-- VI: Không truyền script bổ sung  
        JP: 追加スクリプトなし --%>

    <c:param name="content">
    <%-- VI: Nội dung chính của trang  
        JP: ページのメインコンテンツ --%>

        <section class="w-100 pb-3">
        <%-- VI: Khối nội dung toàn chiều rộng  
            JP: ページ全幅のセクション --%>

            <!-- ① 成績管理 (căn trái + nền xanh dương nhạt) -->
            <%-- VI: Tiêu đề "成績管理" căn trái, nền xanh nhạt  
                JP: 左寄せの「成績管理」タイトル（水色背景） --%>
            <h2 class="h3 mb-3 fw-normal py-2 px-4" style="background-color:#d0e7ff;">
                成績管理
            </h2>

            <!-- ② Khung xanh lá FULL WIDTH giống thanh xanh dương -->
            <%-- VI: Thông báo hoàn tất, màu xanh lá, căn giữa  
                JP: 登録完了メッセージ（緑色・中央寄せ） --%>
            <div class="alert alert-success text-center mx-4">
                登録が完了しました。
            </div>

            <!-- ③ Hai link căn giữa -->
            <%-- VI: Hai link "戻る" và "成績参照" căn giữa, cách nhau 1 khoảng  
                JP: 「戻る」「成績参照」リンクを中央に横並び配置 --%>
            <div class="mt-3 d-flex justify-content-center gap-4">

                <a href="TestRegist.action" class="text-primary fw-bold text-decoration-none">
                    戻る
                </a>
                <%-- VI: Quay lại màn hình nhập điểm  
                    JP: 成績登録画面へ戻るリンク --%>

                <a href="TestRef.action" class="text-primary fw-bold text-decoration-none">
                    成績参照
                </a>
                <%-- VI: Chuyển sang màn hình xem điểm  
                    JP: 成績参照画面へのリンク --%>

            </div>

        </section>

    </c:param>

</c:import>
