<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, bean.*" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

    <c:param name="title">成績管理</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">

        <section class="w-100 pb-3">

            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                成績管理
            </h2>

            <!-- ============================
                 検索フォーム
            ============================ -->
            <form action="TestRegist.action" method="get" class="px-4">

                <div class="row g-3">

                    <!-- 入学年度 -->
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
                    <div class="col-auto d-flex align-items-end">
                        <button type="submit" class="btn btn-primary">検索</button>
                    </div>

                </div>

            </form>

            <hr>

            <!-- ============================
                 成績入力テーブル（検索後）
            ============================ -->
            <%
                List<Test> testList = (List<Test>) request.getAttribute("testList");
                Integer errorIndex = (Integer) request.getAttribute("errorIndex");
                String errorMessage = (String) request.getAttribute("errorMessage");

                if (testList != null && !testList.isEmpty()) {
            %>

            <form action="TestRegistExecute.action" method="post" class="px-4">

                <!-- 科目名 + 回数 -->
                <h4 class="mb-3">
                    科目：<%= request.getAttribute("subject_name") %>
                    （<%= request.getParameter("no") %>回目）
                </h4>

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

                    <tr>
                        <td><%= t.getStudent().getEntYear() %></td>
                        <td><%= t.getClassNum() %></td>

                        <!-- 学生番号 -->
                        <td>
                            <%= no %>
                            <input type="hidden" name="student_no_<%=no%>" value="<%=no%>">
                        </td>

                        <!-- 氏名 -->
                        <td><%= t.getStudent().getName() %></td>

                        <!-- 点数 -->
                        <td>
                            <input type="text"
                                   name="point_<%=no%>"
                                   value="<%= t.getPoint() %>"
                                   maxlength="3"
                                   class="form-control w-50 d-inline">

                            <% if (errorIndex != null && errorIndex == rowIndex) { %>
                                <div style="color: orange; font-size: 12px;">
                                    <%= errorMessage %>
                                </div>
                            <% } %>
                        </td>
                    </tr>

                    <%
                            rowIndex++;
                        }
                    %>

                </table>

                <!-- hidden: 科目コード + 回数 -->
                <input type="hidden" name="subject_cd" value="<%= request.getParameter("subject_cd") %>">
                <input type="hidden" name="no" value="<%= request.getParameter("no") %>">

                <!-- ============================
                     登録ボタン（修正版）
                ============================ -->
                <div class="text-center mt-3">
                    <button type="submit"
                            class="btn btn-success px-5"
                            style="min-width: 200px;">
                        登録して終了
                    </button>
                </div>

            </form>

            <%
                }
            %>

        </section>

    </c:param>

</c:import>
