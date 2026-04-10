<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <title>봄 기획전</title>

  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-detail.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/spring-sale.css">
</head>
<body class="spring-sale-page" data-context-path="${pageContext.request.contextPath}">
  <c:url value="/product" var="productSearchUrl">
    <c:param name="action" value="search"/>
  </c:url>
  <div class="detail-shell">
    <div class="detail-page-inner detail-page-inner--sticky-header spring-sale-inner" id="springSalePageRoot">
      <header class="detail-header spring-sale-header">
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
          <button type="button" class="detail-icon-btn cart-icon-wrap" aria-label="장바구니"
                  onclick="window.location.href = document.body.dataset.contextPath + '/cart?action=list'">
            <span class="material-icons-outlined">shopping_cart</span>
            <c:if test="${sessionScope.cartCount > 0}">
			      <span class="cart-badge">${sessionScope.cartCount}</span>
			  </c:if>
			</button>
        </div>
      </header>

      <div class="spring-sale-scroll">
        <section class="spring-sale-hero" aria-label="봄 기획전 배너">
          <img
            src="${pageContext.request.contextPath}/images/promo/spring-sale.png"
            alt="봄 기획전 배너"
            class="spring-sale-hero-img"
            decoding="async"
            loading="eager"
          >
        </section>
		<!-- 필터, 검색없이 바로 봄 기획전 상품 모아놓은 그리드 -->
        <jsp:include page="/WEB-INF/views/product/product-grid.jsp"/>
      </div>
    </div>
  </div>
</body>
</html>

