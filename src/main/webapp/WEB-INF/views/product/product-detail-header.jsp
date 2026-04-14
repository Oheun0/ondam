<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 검색은 /search 로 (구버전 /product?action=search 는 ProductController 미처리 → redirect:/product 만 됨) --%>
<header class="detail-header">
  <button type="button" class="detail-icon-btn" aria-label="뒤로가기"
        onclick="history.length > 1 ? history.back() : (window.location.href = document.body.dataset.contextPath + '/main')">
  <span class="material-icons">arrow_back_ios_new</span>
</button>

  <div class="detail-header-actions">
    <button type="button" class="detail-icon-btn" aria-label="홈"
        onclick="window.location.href = document.body.dataset.contextPath + '/main'">
  	<span class="material-icons-outlined">home</span>
	</button>
    <a href="${pageContext.request.contextPath}/search?q=" class="detail-icon-btn detail-icon-btn--link" aria-label="검색">
      <span class="material-icons-outlined">search</span>
    </a>
    <a href="${pageContext.request.contextPath}/cart?action=list"
       class="detail-icon-btn detail-icon-btn--link cart-icon-wrap"
       aria-label="장바구니">
      <span class="material-icons-outlined">shopping_cart</span>
      <c:if test="${sessionScope.cartCount > 0}">
        <span class="cart-badge">${sessionScope.cartCount}</span>
      </c:if>
    </a>
  </div>
</header>