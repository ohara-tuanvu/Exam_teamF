<%-- メニュー画面（Trang menu chính） --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<style>
    /* ===== Card menu giữa đẹp hơn ===== */
    .menu-card {
        background: #ffffffdd !important;
        backdrop-filter: blur(6px);
        -webkit-backdrop-filter: blur(6px);
        border-radius: 14px !important;
        box-shadow: 0 4px 12px rgba(0,0,0,0.08);
        transition: 0.25s;
        border: none !important;
        height: 10rem;
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
    }

    .menu-card:hover {
        transform: translateY(-6px);
        box-shadow: 0 10px 24px rgba(0,0,0,0.15);
        background: #f0f6ff !important;
    }

    .menu-card a {
        text-decoration: none;
        color: #0a1a2f;
        font-weight: 700;
        font-size: 22px;
        display: block;
    }

    /* ===== Nhóm 成績管理 ===== */
    .menu-card-group {
        background: #ffffffdd !important;
        backdrop-filter: blur(6px);
        -webkit-backdrop-filter: blur(6px);
        border-radius: 14px !important;
        box-shadow: 0 4px 12px rgba(0,0,0,0.08);
        transition: 0.25s;
        border: none !important;
        height: 10rem;
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
        padding: 20px;
    }

    .menu-card-group:hover {
        transform: translateY(-6px);
        box-shadow: 0 10px 24px rgba(0,0,0,0.15);
        background: #f0f6ff !important;
    }
    
    .menu-title {
    font-size: 24px;
    font-weight: 700;
    margin-bottom: 8px;
	}

	.menu-card-group a {
    text-decoration: none;
    color: #0a1a2f;
    font-weight: 600;
    display: block;
    margin-top: 4px;
    font-size: 18px;
	}
		
</style>

<c:import url="/common/base.jsp">

    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">

        <section class="me-4">

            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                メニュー
            </h2>

            <div class="row text-center px-4 fs-3 my-5">

                <!-- 学生管理 -->
                <div class="col d-flex align-items-center justify-content-center mx-2 rounded shadow menu-card">
                    <a href="StudentList.action">学生管理</a>
                </div>

                <!-- 成績管理 -->
                <div class="col d-flex align-items-center justify-content-center mx-2 rounded shadow menu-card-group">
                    <div>
                        <div class="menu-title">成績管理</div>
                        <div><a href="TestRegist.action">成績登録</a></div>
                        <div><a href="TestList.action">成績参照</a></div>
                    </div>
                </div>

                <!-- 科目管理 -->
                <div class="col d-flex align-items-center justify-content-center mx-2 rounded shadow menu-card">
                    <a href="SubjectList.action">科目管理</a>
                </div>

                <!-- クラス管理 -->
                <div class="col d-flex align-items-center justify-content-center mx-2 rounded shadow menu-card">
                    <a href="ClassList.action">クラス管理</a>
                </div>

            </div>
        </section>

    </c:param>
</c:import>
