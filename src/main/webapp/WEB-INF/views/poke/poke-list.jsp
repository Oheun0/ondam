<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>조르기 목록</title>
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-detail.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cart.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/poke.css">
</head>
<body class="poke-list-page" data-context-path="${pageContext.request.contextPath}">
  <div class="detail-shell">
    <div class="detail-page-inner detail-page-inner--sticky-header cart-page-inner poke-list-inner" id="pokeListRoot">

      <header class="detail-header cart-header poke-list-header">
        <button type="button" class="detail-icon-btn" id="pokeListBackBtn" aria-label="뒤로가기">
          <span class="material-icons">arrow_back_ios_new</span>
        </button>
        <h1 class="cart-header__title">조르기 목록</h1>
        <div class="cart-header__actions">
          <a href="${pageContext.request.contextPath}/main" class="detail-icon-btn detail-icon-btn--link" aria-label="홈">
            <span class="material-icons-outlined">home</span>
          </a>
        </div>
      </header>

      <div class="poke-list-guide-card" role="note" aria-label="조르기 목록 안내">
        <p class="poke-list-guide-title">나에게 온 조르기 목록이에요</p>
        <p class="poke-list-guide-sub">원하시는 걸 골라 선물해보세요</p>
      </div>

      <%-- 목록 없을 때 --%>
      <c:if test="${empty receivedList}">
        <div class="group-empty-card" style="text-align:center; padding:60px 20px;">
          <p style="font-size:1rem; color:#999;">아직 조르기가 없어요 😅</p>
        </div>
      </c:if>

      <%-- 목록 있을 때 --%>
      <c:if test="${not empty receivedList}">
      <div id="pokeListFilledWrap">

        <div class="cart-toolbar poke-list-toolbar">
          <label class="cart-toolbar__select-all">
            <input type="checkbox" class="cart-toolbar__checkbox" id="pokeSelectAll" checked aria-label="전체 선택"/>
            <span>전체 선택</span>
          </label>
        </div>

        <main class="cart-main poke-list-main" id="pokeMainList">
          <section class="cart-brand-group">
            <ul class="cart-brand-group__list">

              <c:forEach var="poke" items="${receivedList}">
                <c:set var="product" value="${productMap[poke.productNo]}" />
                <c:set var="img"     value="${imageMap[poke.productNo]}" />
                <li>
                  <article class="cart-item poke-item"
			         data-poke-id="${poke.pokeNo}"
			         data-product-no="${poke.productNo}"
			         data-option-no="${poke.productOptionNo}"
			         data-quantity="${poke.pokeQuantity}"
			         data-sender-no="${poke.senderNo}">

                    <label class="cart-item__check">
                      <input type="checkbox" class="cart-item__checkbox" checked
                             aria-label="${product.productName} 선택"/>
                    </label>

                    <div class="cart-item__left">
                      <div class="cart-item__thumb-wrap">
                        <img src="${pageContext.request.contextPath}/uploads/products/${img}"
                             class="cart-item__thumb"
                             alt="${product.productName}"
                             width="96" height="96" loading="lazy"
                             onerror="this.src='${pageContext.request.contextPath}/images/no-image.png'; this.onerror=null;"/>
                      </div>
                    </div>

                    <div class="cart-item__body">
                      <p class="cart-item__brand">${product.productBrand}</p>
                      <p class="cart-item__name">${product.productName}</p>

                      <%-- 수량 --%>
                      <p style="font-size:0.85rem; color:#888;">${poke.pokeQuantity}개</p>

                      <%-- 조르기 메시지 --%>
                      <c:if test="${not empty poke.pokeMsg}">
                        <p style="font-size:0.85rem; color:#888;">💬 ${poke.pokeMsg}</p>
                      </c:if>

                      <%-- 가격 --%>
                      <div class="cart-item__bottom">
                        <div class="cart-item__price-block">
                          <c:if test="${product.productOriginPrice > product.productPrice}">
                            <span class="cart-item__price-original">
                              <fmt:formatNumber value="${product.productOriginPrice}" pattern="#,###"/>원
                            </span>
                          </c:if>
                          <span class="cart-item__price-sale">
                            <fmt:formatNumber value="${product.productPrice}" pattern="#,###"/>원
                          </span>
                        </div>
                      </div>

                      <%-- 상태 배지 --%>
                      <c:choose>
                        <c:when test="${poke.sendState == 0}">
                          <span class="poke-state-badge poke-state--pending">대기중</span>
                        </c:when>
                        <c:when test="${poke.sendState == 1}">
                          <span class="poke-state-badge poke-state--accepted">수락됨</span>
                        </c:when>
                        <c:when test="${poke.sendState == 2}">
                          <span class="poke-state-badge poke-state--rejected">거절됨</span>
                        </c:when>
                        <c:when test="${poke.sendState == 3}">
                          <span class="poke-state-badge poke-state--expired">만료됨</span>
                        </c:when>
                      </c:choose>
                    </div>

                  </article>
                </li>
              </c:forEach>

            </ul>
          </section>
        </main>

        <div class="cart-order-bar poke-order-bar" id="pokeOrderBar">
          <button type="button"
                  class="cart-order-bar__btn detail-sheet-btn primary"
                  id="pokeGiftSubmitBtn">
            총 <span id="pokeGiftCount">${receivedList.size()}</span>개 상품 선물하기
          </button>
        </div>

      </div>
      </c:if>

    </div>
  </div>

  <script src="${pageContext.request.contextPath}/js/poke-list.js"></script>
</body>
</html>