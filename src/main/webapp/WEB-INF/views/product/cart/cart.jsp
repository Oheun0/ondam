<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 더미: 빈 장바구니 UI 테스트 시 true --%>
<c:set var="cartDummyEmpty" value="false"/>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>장바구니</title>

  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-detail.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cart.css">
</head>
<body class="cart-page" data-context-path="${pageContext.request.contextPath}">
  <div class="detail-shell">
    <div class="detail-page-inner detail-page-inner--sticky-header cart-page-inner" id="cartPageRoot">

      <header class="detail-header cart-header">
        <button type="button" class="detail-icon-btn" id="cartBackBtn" aria-label="뒤로가기">
          <span class="material-icons">arrow_back_ios_new</span>
        </button>
        <h1 class="cart-header__title">장바구니</h1>
        <div class="cart-header__actions">
          <a href="${pageContext.request.contextPath}/main" class="detail-icon-btn detail-icon-btn--link" aria-label="홈">
            <span class="material-icons-outlined">home</span>
          </a>
        </div>
      </header>

      <c:choose>
        <c:when test="${cartDummyEmpty}">
          <main class="cart-main cart-main--empty">
            <div class="cart-empty-card">
              <p class="cart-empty-title">장바구니에 상품이 없어요</p>
              <p class="cart-empty-sub">마음에 드는 상품을 담아보세요</p>
              <a href="${pageContext.request.contextPath}/main" class="cart-empty-cta">쇼핑하러 가기</a>
            </div>
          </main>
        </c:when>
        <c:otherwise>
          <div id="cartFilledWrap">
          <div class="cart-toolbar">
            <label class="cart-toolbar__select-all">
              <input type="checkbox" class="cart-toolbar__checkbox" id="cartSelectAll" checked aria-label="전체 선택"/>
              <span>전체 선택</span>
            </label>
            <div class="cart-toolbar__actions">
              <button type="button" class="cart-toolbar__link-btn" id="cartRemoveSoldoutBtn">품절 삭제</button>
              <span class="cart-toolbar__sep" aria-hidden="true">|</span>
              <button type="button" class="cart-toolbar__link-btn" id="cartRemoveSelectedBtn">선택 삭제</button>
            </div>
          </div>

          <main class="cart-main" id="cartMainList">
            <%-- 브랜드 0: 메가하우스 2종 --%>
            <section class="cart-brand-group" aria-labelledby="cartBrand0">
              <h2 class="cart-brand-group__title" id="cartBrand0">메가하우스 배송상품</h2>
              <ul class="cart-brand-group__list">
                <li>
                  <article class="cart-item"
                      data-cart-id="1"
                      data-unit-price="40000"
                      data-original-price="50000"
                      data-qty="1"
                      data-discounted="true"
                      data-option="빨간색 / 95 / 1개"
                      data-product-name="데일리 오버핏 후드"
                      data-brand="메가하우스"
                      data-image="${pageContext.request.contextPath}/images/category/type-top-knit.jpg">
                    <label class="cart-item__check">
                      <input type="checkbox" class="cart-item__checkbox" checked aria-label="상품 선택"/>
                    </label>
                    <button type="button" class="cart-item__remove" aria-label="상품 삭제">
                      <span class="material-icons-outlined" aria-hidden="true">close</span>
                    </button>
                    <div class="cart-item__left">
                      <div class="cart-item__thumb-wrap">
                        <img src="${pageContext.request.contextPath}/images/category/type-top-knit.jpg" alt="" class="cart-item__thumb" width="96" height="96" loading="lazy"/>
                      </div>
                    </div>
                    <button type="button" class="cart-item__option-btn" aria-label="옵션 변경하기: 빨간색 / 95 / 1개">빨간색 / 95 / 1개</button>
                    <div class="cart-item__body">
                      <p class="cart-item__brand">메가하우스</p>
                      <p class="cart-item__name">데일리 오버핏 후드데일리 오버핏</p>
                      <div class="cart-item__bottom">
                        <div class="cart-item__price-block">
                          <span class="cart-item__price-original">50,000원</span>
                          <span class="cart-item__price-sale">40,000원</span>
                        </div>
                      </div>
                    </div>
                  </article>
                </li>
                <li>
                  <article class="cart-item cart-item--soldout"
                      data-cart-id="2"
                      data-unit-price="29000"
                      data-original-price="29000"
                      data-qty="1"
                      data-discounted="false"
                      data-soldout="true"
                      data-option="네이비 / 100 / 1개"
                      data-product-name="스트라이프 긴팔 티셔츠"
                      data-brand="메가하우스"
                      data-image="${pageContext.request.contextPath}/images/category/type-top-knit.jpg">
                    <label class="cart-item__check">
                      <input type="checkbox" class="cart-item__checkbox" checked aria-label="상품 선택"/>
                    </label>
                    <button type="button" class="cart-item__remove" aria-label="상품 삭제">
                      <span class="material-icons-outlined" aria-hidden="true">close</span>
                    </button>
                    <div class="cart-item__left">
                      <div class="cart-item__thumb-wrap">
                        <img src="${pageContext.request.contextPath}/images/category/type-top-knit.jpg" alt="" class="cart-item__thumb" width="96" height="96" loading="lazy"/>
                        <span class="cart-item__soldout-badge">품절</span>
                      </div>
                    </div>
                    <button type="button" class="cart-item__option-btn" aria-label="옵션 변경하기: 네이비 / 100 / 1개" disabled>네이비 / 100 / 1개</button>
                    <div class="cart-item__body">
                      <p class="cart-item__brand">메가하우스</p>
                      <p class="cart-item__name">스트라이프 긴팔 티셔츠</p>
                      <div class="cart-item__bottom">
                        <div class="cart-item__price-block cart-item__price-block--plain">
                          <span class="cart-item__price-sale">29,000원</span>
                        </div>
                      </div>
                    </div>
                  </article>
                </li>
              </ul>
            </section>

            <%-- 브랜드 1: ABC 1종 --%>
            <section class="cart-brand-group" aria-labelledby="cartBrand1">
              <h2 class="cart-brand-group__title" id="cartBrand1">ABC브랜드 배송상품</h2>
              <ul class="cart-brand-group__list">
                <li>
                  <article class="cart-item"
                      data-cart-id="3"
                      data-unit-price="67500"
                      data-original-price="75000"
                      data-qty="2"
                      data-line-sale="135000"
                      data-line-original="150000"
                      data-discounted="true"
                      data-option="블랙 / 105 / 2개"
                      data-product-name="가벼운 방풍 재킷"
                      data-brand="ABC브랜드"
                      data-image="${pageContext.request.contextPath}/images/category/type-top-knit.jpg">
                    <label class="cart-item__check">
                      <input type="checkbox" class="cart-item__checkbox" checked aria-label="상품 선택"/>
                    </label>
                    <button type="button" class="cart-item__remove" aria-label="상품 삭제">
                      <span class="material-icons-outlined" aria-hidden="true">close</span>
                    </button>
                    <div class="cart-item__left">
                      <div class="cart-item__thumb-wrap">
                        <img src="${pageContext.request.contextPath}/images/category/type-top-knit.jpg" alt="" class="cart-item__thumb" width="96" height="96" loading="lazy"/>
                      </div>
                    </div>
                    <button type="button" class="cart-item__option-btn" aria-label="옵션 변경하기: 블랙 / 105 / 2개">블랙 / 105 / 2개</button>
                    <div class="cart-item__body">
                      <p class="cart-item__brand">ABC브랜드</p>
                      <p class="cart-item__name">가벼운 방풍 재킷</p>
                      <div class="cart-item__bottom">
                        <div class="cart-item__price-block">
                          <span class="cart-item__price-original">150,000원</span>
                          <span class="cart-item__price-sale">135,000원</span>
                        </div>
                      </div>
                    </div>
                  </article>
                </li>
              </ul>
            </section>
          </main>

          <div class="cart-order-bar" id="cartOrderBar">
            <button type="button" class="cart-order-bar__btn detail-sheet-btn primary" id="cartOrderSubmitBtn">
              총 <span id="cartOrderCount">3</span>개 상품 주문하기
            </button>
          </div>
          </div>

          <main class="cart-main cart-main--empty hidden" id="cartJsEmpty" aria-live="polite">
            <div class="cart-empty-card">
              <p class="cart-empty-title">장바구니에 상품이 없어요</p>
              <p class="cart-empty-sub">마음에 드는 상품을 담아보세요</p>
              <a href="${pageContext.request.contextPath}/main" class="cart-empty-cta">쇼핑하러 가기</a>
            </div>
          </main>
        </c:otherwise>
      </c:choose>
    </div>
  </div>

  <%-- 옵션 변경 바텀시트 (상세 시트 구조, 알림·찜 등 제거) --%>
  <div class="detail-sheet-dim hidden" id="cartSheetDim" aria-hidden="true"></div>
  <div class="detail-sheet cart-option-sheet hidden" id="cartOptionSheet" role="dialog" aria-modal="true" aria-labelledby="cartSheetTitle">
    <div class="detail-sheet-top cart-option-sheet__top">
      <div class="detail-sheet-handle"></div>
      <h2 class="cart-option-sheet__title" id="cartSheetTitle">옵션 변경하기</h2>
    </div>

    <div class="detail-sheet-stage">
      <div class="detail-sheet-scroll">
        <div class="detail-sheet-section">
          <button type="button" class="detail-option-toggle" id="cartColorToggleBtn" aria-expanded="false" aria-controls="cartColorOptionPanel">
            <span>색상</span>
            <span class="detail-selected-value" id="cartSelectedColorText">빨간색</span>
            <span class="material-icons detail-option-toggle__chev" aria-hidden="true">expand_more</span>
          </button>
          <div class="detail-option-panel detail-option-panel--sheet hidden" id="cartColorOptionPanel" role="region">
            <div class="detail-option-panel__scroller">
              <div class="detail-option-list" role="listbox">
                <button type="button" class="detail-option-row active" data-color="빨간색" role="option">빨간색</button>
                <button type="button" class="detail-option-row" data-color="네이비" role="option">네이비</button>
                <button type="button" class="detail-option-row" data-color="블랙" role="option">블랙</button>
                <button type="button" class="detail-option-row" data-color="베이지" role="option">베이지</button>
              </div>
            </div>
          </div>
        </div>

        <div class="detail-sheet-section">
          <button type="button" class="detail-option-toggle" id="cartSizeToggleBtn" aria-expanded="false" aria-controls="cartSizeOptionPanel">
            <span>사이즈</span>
            <span class="detail-selected-value" id="cartSelectedSizeText">95</span>
            <span class="material-icons detail-option-toggle__chev" aria-hidden="true">expand_more</span>
          </button>
          <div class="detail-option-panel detail-option-panel--sheet hidden" id="cartSizeOptionPanel" role="region">
            <div class="detail-option-panel__scroller">
              <div class="detail-option-list" role="listbox">
                <button type="button" class="detail-option-row active" data-size="95" role="option">95</button>
                <button type="button" class="detail-option-row" data-size="100" role="option">100</button>
                <button type="button" class="detail-option-row" data-size="105" role="option">105</button>
                <button type="button" class="detail-option-row" data-size="110" role="option">110</button>
              </div>
            </div>
          </div>
        </div>

        <div class="detail-quantity-row detail-quantity-row--sheet">
          <span class="detail-quantity-label">수량</span>
          <div class="detail-quantity-box detail-qty-stepper">
            <button type="button" class="detail-qty-stepper__btn" id="cartMinusQtyBtn" aria-label="수량 한 개 빼기">
              <span class="material-icons-outlined" aria-hidden="true">remove</span>
            </button>
            <span class="detail-qty-stepper__value" id="cartQtyValue">1</span>
            <button type="button" class="detail-qty-stepper__btn" id="cartPlusQtyBtn" aria-label="수량 한 개 더하기">
              <span class="material-icons-outlined" aria-hidden="true">add</span>
            </button>
          </div>
        </div>

        <div class="detail-sheet-order-divider" aria-hidden="true"></div>
        <div class="detail-sheet-order-summary" id="cartSheetOrderSummary" data-unit-price="40000">
          <span class="detail-sheet-order-count" id="cartSheetOrderCount">총 1개</span>
          <span class="detail-sheet-order-total" id="cartSheetOrderTotal">40,000원</span>
        </div>
      </div>
    </div>

    <div class="detail-sheet-bottom">
      <button type="button" class="detail-sheet-btn secondary" id="cartSheetCancelBtn">취소</button>
      <button type="button" class="detail-sheet-btn primary" id="cartSheetApplyBtn">변경하기</button>
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/js/cart.js"></script>
</body>
</html>
