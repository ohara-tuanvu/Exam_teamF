<%-- 学生一覧画面（Trang danh sách sinh viên） --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<%@ taglib prefix="c" uri="jakarta.tags.core" %> 

<c:import url="/common/base.jsp"> 

    <c:param name="title">得点管理システム</c:param> 
    <c:param name="scripts"></c:param> 

    <c:param name="content"> 

        <!-- ⭐ CSS làm nổi từng dòng + filter block -->
        <style>
            /* Khối filter nổi rõ */
            #filter {
                background: rgba(255,255,255,0.65);
                backdrop-filter: blur(6px);
                border: 1px solid #e5e5e5;
                box-shadow: 0 2px 6px rgba(0,0,0,0.05);
            }

            /* Header bảng */
            table.table th {
                background: rgba(255,255,255,0.75);
                backdrop-filter: blur(4px);
                border-bottom: 2px solid #dcdcdc;
            }

            /* ⭐ Từng dòng bảng nổi rõ */
            table.table tbody tr {
                background: rgba(255,255,255,0.60);
                backdrop-filter: blur(4px);
                border-bottom: 1px solid #e5e5e5;
            }

            /* Hover nhẹ */
            table.table tbody tr:hover {
                background: rgba(240,247,255,0.85);
            }
        </style>

        <section class="me-4">

    <!-- Title -->
    <h2 class="mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
        クラス管理
    </h2>

    <!-- 新規登録 button -->
    <div class="my-3 text-end px-4">
        <a href="ClassCreate.action" class="btn btn-primary shadow-sm">
            新規登録
        </a>
    </div>

    <!-- Table -->
    <c:choose>

        <c:when test="${class_list.size() > 0}">

            <div class="px-3 mb-2">
                検索結果：${class_list.size()}件
            </div>

            <table class="table table-hover mx-3">

                <thead>
                    <tr>
                    <th>クラス番号</th>
                    <th class="text-end">操作</th>
                    </tr>
                </thead>

                <tbody>
                
                <c:forEach var="cls" items="${class_list}">
                <tr>
                
                <td>
                <span class="fw-semibold">
                ${cls.class_num}
                </span>
                </td>
                
                <td class="text-end">
                
                	<a class="btn btn-primary btn-sm me-3"
                	href="ClassUpdate.action?classNum=${cls.class_num}">
                変更
            	</a>

            	<a class="btn btn-danger btn-sm"
               		href="ClassDelete.action?classNum=${cls.class_num}">
                削除
            	</a>

        	</td>

    			</tr>

				</c:forEach>

			</tbody>

            </table>

        </c:when>

        <c:otherwise>

            <div class="alert alert-light border mx-3">
                クラス情報が存在しませんでした。
            </div>

        </c:otherwise>

    </c:choose>

</section>
    </c:param>
</c:import>
