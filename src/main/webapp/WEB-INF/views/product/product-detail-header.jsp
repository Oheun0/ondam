<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url var="productSearchUrl" value="/product">
  <c:param name="action" value="search"/>
</c:url>

<header class="detail-header">
  <button type="button" class="detail-icon-btn" aria-label="뒤로가기">
    <span class="material-icons">arrow_back_ios_new</span>
  </button>

  <div class="detail-header-actions">
    <button type="button" class="detail-icon-btn" aria-label="홈">
      <span class="material-icons-outlined">home</span>
    </button>
    <a href="${productSearchUrl}" class="detail-icon-btn detail-icon-btn--link" aria-label="검색">
      <span class="material-icons-outlined">search</span>
    </a>
    <button type="button" class="detail-icon-btn cart-icon-wrap" aria-label="장바구니">
      <span class="material-icons-outlined">shopping_cart</span>
      <span class="cart-badge">3</span>
    </button>
  </div>
</header>