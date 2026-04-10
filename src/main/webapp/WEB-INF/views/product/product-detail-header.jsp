<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url var="productSearchUrl" value="/product">
  <c:param name="action" value="search"/>
</c:url>

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
    <a href="${productSearchUrl}" class="detail-icon-btn detail-icon-btn--link" aria-label="검색">
      <span class="material-icons-outlined">search</span>
    </a>
    <button type="button" class="detail-icon-btn cart-icon-wrap" aria-label="장바구니">
  <span class="material-icons-outlined">shopping_cart</span>
  <c:if test="${sessionScope.cartCount > 0}">
    <span class="cart-badge">${sessionScope.cartCount}</span>
  </c:if>
</button>
  </div>
</header>