<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <title>상품 상세</title>

  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,1,0" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-detail.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/poke.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/share-modal.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cart.css">
</head>
<body data-context-path="${pageContext.request.contextPath}"
      data-login-user="${not empty sessionScope.loginUser ? 'true' : ''}"
      data-kakao-js-key="${initParam.kakaoJavascriptKey}">
  <div id="option-toast" class="option-toast hidden" role="alert" aria-live="assertive" aria-hidden="true">
    <span class="material-icons option-toast__icon" aria-hidden="true">error</span>
    <span class="option-toast__text">먼저 색상과 사이즈를 골라주세요</span>
  </div>
  
  <div id="success-toast" class="option-toast hidden" role="status" aria-live="polite" aria-hidden="true"
	     style="position:fixed; top:20px; left:50%; transform:translateX(-50%); z-index:9999;
	            background:rgba(76, 175, 80, 0.9); color:#fff; padding:10px 20px; border-radius:20px;
	            display:flex; align-items:center; gap:8px; transition: opacity 0.3s ease;">
	  <span class="material-icons option-toast__icon" aria-hidden="true" style="color:#fff;">check_circle</span>
	  <span id="success-toast-text"></span>
	</div>
  
  <%-- 재고 부족 토스트 --%>
  <c:if test="${not empty sessionScope.errorMsg}">
    <div class="cart-error-toast" role="alert">
      <span class="material-icons">error_outline</span>
      ${sessionScope.errorMsg}
    </div>
    <% session.removeAttribute("errorMsg"); %>
  </c:if>

  <div class="detail-shell">
    <div class="detail-page-inner detail-page-inner--sticky-header">
      <jsp:include page="/WEB-INF/views/product/product-detail-header.jsp" />
      <jsp:include page="/WEB-INF/views/product/product-detail-info.jsp" />
    </div>

    <!-- 하단 고정 구매바 -->
    <div class="detail-bottom-bar">
      <button type="button" class="detail-bottom-btn secondary" id="openCartSheetBtn">장바구니 담기</button>
      <button type="button" class="detail-bottom-btn primary" id="openBuySheetBtn">구매하기</button>
    </div>

    <jsp:include page="/WEB-INF/views/product/product-detail-sheet.jsp" />
    <div class="detail-image-lightbox hidden" id="detailImageLightbox" role="dialog" aria-modal="true" aria-label="이미지 확대 보기">
      <button type="button" class="detail-image-lightbox-close" id="detailImageLightboxClose" aria-label="이미지 확대 닫기">
        <span class="material-icons-outlined" aria-hidden="true">close</span>
      </button>
      <div class="detail-image-lightbox-body">
        <div class="detail-image-lightbox-scroll" id="detailImageLightboxScroll" aria-label="확대 이미지 슬라이드"></div>
      </div>
    </div>
    <jsp:include page="/WEB-INF/views/poke/poke-modal.jsp" />
    <jsp:include page="/WEB-INF/views/gift/gift-modal.jsp" />
    <jsp:include page="/WEB-INF/views/product/share-modal.jsp" />
  </div>

  <script src="${pageContext.request.contextPath}/js/product-detail.js"></script>
  <script src="${pageContext.request.contextPath}/js/gift-modal.js"></script>
  <script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
</body>
</html>