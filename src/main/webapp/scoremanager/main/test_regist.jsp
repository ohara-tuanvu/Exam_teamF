<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- VI: Cấu hình JSP (Java + UTF-8)  
    JP: JSPページ設定（Java・UTF-8） --%>

<%@ page import="java.util.*, bean.*" %>
<%-- VI: Import List, Test, Subject, Student...  
    JP: List や Bean クラスをインポート --%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%-- VI: JSTL core tag  
    JP: JSTL Core タグライブラリ --%>

<c:import url="/common/base.jsp">
<%-- VI: Import layout chung base.jsp  
    JP: 共通レイアウト base.jsp を読み込む --%>

    <c:param name="title">成績管理</c:param>
    <%-- VI: Tiêu đề trang  
        JP: ページタイトル --%>

    <c:param name="scripts"></c:param>
    <%-- VI: Không dùng script bổ sung  
        JP: 追加スクリプトなし --%>

    <c:param name="content">
    <%-- VI: Nội dung chính của trang  
        JP: ページのメインコンテンツ --%>

        <section class="w-100 pb-3">
        <%-- VI: Khối nội dung toàn chiều rộng  
            JP: ページ全幅のセクション --%>

            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                成績管理
            </h2>
            <%-- VI: Tiêu đề màn hình  
                JP: 画面タイトル --%>

            <!-- ============================
                 検索フォーム
            ============================ -->
            <%-- VI: Form tìm kiếm điều kiện  
                JP: 検索条件フォーム --%>
            <form action="TestRegist.action" method="get" class="px-4">

                <div class="row g-3">

                    <!-- 入学年度 -->
                    <%-- VI: Dropdown chọn năm nhập học  
                        JP: 入学年度の選択 --%>
                    <div class="col-auto">
                        <label class="form-label">入学年度</label>
                        <select name="ent_year" class="form-select" style="width:140px;">
                            <option value="">選択してください</option>
                            <%
                                List<Integer> entYearList = (List<Integer>) request.getAttribute("entYearList");
                                if (entYearList != null) {
                                    for (int y : entYearList) {
                            %>
                                <option value="<%=y%>"><%=y%></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>

                    <!-- クラス -->
                    <%-- VI: Dropdown chọn lớp  
                        JP: クラス選択 --%>
                    <div class="col-auto">
                        <label class="form-label">クラス</label>
                        <select name="class_num" class="form-select" style="width:140px;">
                            <option value="">選択してください</option>
                            <%
                                List<String> classList = (List<String>) request.getAttribute("classList");
                                if (classList != null) {
                                    for (String c : classList) {
                            %>
                                <option value="<%=c%>"><%=c%></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>

                    <!-- 科目 -->
                    <%-- VI: Dropdown chọn môn học  
                        JP: 科目選択 --%>
                    <div class="col-auto">
                        <label class="form-label">科目</label>
                        <select name="subject_cd" class="form-select" style="width:140px;">
                            <option value="">選択してください</option>
                            <%
                                List<Subject> subjectList = (List<Subject>) request.getAttribute("subjectList");
                                if (subjectList != null) {
                                    for (Subject s : subjectList) {
                            %>
                                <option value="<%=s.getCd()%>"><%=s.getName()%></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>

                    <!-- 回数 -->
                    <%-- VI: Dropdown chọn số lần kiểm tra  
                        JP: テスト回数の選択 --%>
                    <div class="col-auto">
                        <label class="form-label">回数</label>
                        <select name="no" class="form-select" style="width:140px;">
                            <option value="">選択してください</option>
                            <%
                                List<Integer> noList = (List<Integer>) request.getAttribute("noList");
                                if (noList != null) {
                                    for (int n : noList) {
                            %>
                                <option value="<%=n%>"><%=n%></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>

                    <!-- Button -->
                    <%-- VI: Nút tìm kiếm  
                        JP: 検索ボタン --%>
                    <div class="col-auto d-flex align-items-end">
                        <button type="submit" class="btn btn-primary">検索</button>
                    </div>

                </div>

            </form>

            <hr>

            <!-- ============================
                 成績入力テーブル（検索後）
            ============================ -->
            <%-- VI: Bảng nhập điểm (sau khi tìm kiếm)  
                JP: 検索後の成績入力テーブル --%>

            <%
                List<Test> testList = (List<Test>) request.getAttribute("testList");
                Integer errorIndex = (Integer) request.getAttribute("errorIndex");
                String errorMessage = (String) request.getAttribute("errorMessage");
                List<String> errorList = (List<String>) request.getAttribute("errorList");
            %>

            <!-- ⭐ Chỉ hiển thị khi đã bấm 検索 nhưng không có kết quả -->
            <%-- JP: 検索したが結果が0件の場合のみ表示 --%>
            <c:if test="${testList != null && testList.isEmpty()}">
                <div class="alert alert-warning w-50 mx-4 text-center">
                    該当する成績はありません。
                </div>
            </c:if>

            <%
                if (testList != null && !testList.isEmpty()) {
            %>

            <form action="TestRegistExecute.action" method="post" class="px-4">

                <h4 class="mb-3">
                    科目：<%= request.getAttribute("subject_name") %>
                    （<%= request.getParameter("no") %>回目）
                </h4>
                <%-- VI: Hiển thị tên môn + số lần kiểm tra  
                    JP: 科目名 + 回数の表示 --%>

                <table class="table table-bordered">
                    <tr>
                        <th>入学年度</th>
                        <th>クラス</th>
                        <th>学生番号</th>
                        <th>氏名</th>
                        <th>点数</th>
                    </tr>

                    <%
                        int rowIndex = 0;
                        for (Test t : testList) {
                            String no = t.getStudent().getNo();
                    %>

                    <tr class="<%= (errorIndex != null && errorIndex == rowIndex) ? "table-warning" : "" %>">
                    <%-- VI: Nếu dòng có lỗi → tô vàng  
                        JP: エラー行は黄色ハイライト --%>

                        <td><%= t.getStudent().getEntYear() %></td>
                        <td><%= t.getClassNum() %></td>

                        <td>
                            <%= no %>
                            <input type="hidden" name="student_no_<%=no%>" value="<%=no%>">
                            <%-- VI: Hidden để giữ student_no  
                                JP: student_no を保持する hidden --%>
                        </td>

                        <td><%= t.getStudent().getName() %></td>

                        <td>
                            <input type="text"
                                   name="point_<%=no%>"
                                   value="<%= request.getParameter("point_" + no) == null 
                                            ? t.getPoint() 
                                            : request.getParameter("point_" + no) %>"
                                   maxlength="3"
                                   class="form-control w-50 d-inline">
                            <%-- VI: Input điểm (giữ lại giá trị khi lỗi)  
                                JP: 点数入力（エラー時は再表示） --%>

                            <% if (errorIndex != null && errorIndex == rowIndex && errorList != null && !errorList.isEmpty()) { %>
                                <div style="color: orange; font-size: 12px;">
                                    <%= errorList.get(0) %>
                                </div>
                            <% } %>
                            <%-- VI: Hiển thị lỗi dưới ô nhập điểm  
                                JP: 点数欄の下にエラーメッセージ表示 --%>

                        </td>
                    </tr>

                    <%
                            rowIndex++;
                        }
                    %>

                </table>

                <input type="hidden" name="subject_cd" value="<%= request.getParameter("subject_cd") %>">
                <input type="hidden" name="no" value="<%= request.getParameter("no") %>">
                <%-- VI: Giữ lại subject_cd và no khi submit  
                    JP: subject_cd と no を保持 --%>

                <div class="text-center mt-3">
                    <button type="submit"
                            class="btn btn-success px-5"
                            style="min-width: 200px;">
                        登録して終了
                    </button>
                    <%-- VI: Nút đăng ký và kết thúc  
                        JP: 登録して終了ボタン --%>
                </div>

            </form>

            <%
                }
            %>

        </section>

    </c:param>

</c:import>
