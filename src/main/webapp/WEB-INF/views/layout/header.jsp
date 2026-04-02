<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<header class="fixed-area">
    <div class="top-bar">
        <div class="top-left">
            <button type="button" class="icon-btn" id="headerCategoryMenu" aria-label="카테고리 메뉴 열기">
                <span class="material-icons">menu</span>
            </button>
        </div>

        <a href="${pageContext.request.contextPath}/main" class="logo-box" aria-label="온담 홈으로 이동">

            <img src="${pageContext.request.contextPath}/images/logo.svg" alt="온담 로고" class="logo-img">
        </a>

        <div class="top-right">
            <a href="${pageContext.request.contextPath}/notification" 
			   class="icon-btn badge-wrap" 
			   aria-label="알림 보기">
                <span class="material-icons-outlined">notifications</span>
                <c:if test="${sessionScope.unreadCount > 0}">
					<span class="badge">${sessionScope.unreadCount}</span>
				</c:if>
			</a>
            <a href="${pageContext.request.contextPath}/cart" class="icon-btn badge-wrap" aria-label="장바구니 보기">
                <span class="material-icons-outlined">shopping_cart</span>
                <span class="badge">1</span>
            </a>
        </div>
    </div>
</header>
