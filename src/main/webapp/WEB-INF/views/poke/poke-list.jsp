<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <p class="poke-list-guide-title">성연수님이 마음에 들어한 상품이에요</p>
        <p class="poke-list-guide-sub">원하시는 걸 골라 선물해보세요</p>
      </div>

      <div id="pokeListFilledWrap">
        <div class="cart-toolbar poke-list-toolbar">
          <label class="cart-toolbar__select-all">
            <input type="checkbox" class="cart-toolbar__checkbox" id="pokeSelectAll" checked aria-label="전체 선택"/>
            <span>전체 선택</span>
          </label>
          <%-- 품절 삭제 | 선택 삭제 없음 (요구사항) --%>
        </div>

        <main class="cart-main poke-list-main" id="pokeMainList">
          <%-- 더미 3개: 선물 대상자가 좋아한 상품 목록(표시용) --%>
          <section class="cart-brand-group" aria-labelledby="pokeBrand0">
            <h2 class="cart-brand-group__title" id="pokeBrand0">메가하우스 배송상품</h2>
            <ul class="cart-brand-group__list">
              <li>
                <article class="cart-item poke-item"
                    data-poke-id="1">
                  <label class="cart-item__check">
                    <input type="checkbox" class="cart-item__checkbox" checked aria-label="상품 선택"/>
                  </label>
                  <div class="cart-item__left">
                    <div class="cart-item__thumb-wrap">
                      <img src="${pageContext.request.contextPath}/images/category/type-top-knit.jpg" alt="" class="cart-item__thumb" width="96" height="96" loading="lazy"/>
                    </div>
                  </div>
                  <button type="button" class="cart-item__option-btn" aria-label="옵션(표시용): 빨간색 / 95 / 1개" disabled>빨간색 / 95 / 1개</button>
                  <div class="cart-item__body">
                    <p class="cart-item__brand">메가하우스</p>
                    <p class="cart-item__name">데일리 오버핏 후드</p>
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
                <article class="cart-item poke-item"
                    data-poke-id="2">
                  <label class="cart-item__check">
                    <input type="checkbox" class="cart-item__checkbox" checked aria-label="상품 선택"/>
                  </label>
                  <div class="cart-item__left">
                    <div class="cart-item__thumb-wrap">
                      <img src="${pageContext.request.contextPath}/images/category/type-bottom-long.jpg" alt="" class="cart-item__thumb" width="96" height="96" loading="lazy"/>
                    </div>
                  </div>
                  <button type="button" class="cart-item__option-btn" aria-label="옵션(표시용): 네이비 / 100 / 1개" disabled>네이비 / 100 / 1개</button>
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

          <section class="cart-brand-group" aria-labelledby="pokeBrand1">
            <h2 class="cart-brand-group__title" id="pokeBrand1">ABC브랜드 배송상품</h2>
            <ul class="cart-brand-group__list">
              <li>
                <article class="cart-item poke-item"
                    data-poke-id="3">
                  <label class="cart-item__check">
                    <input type="checkbox" class="cart-item__checkbox" checked aria-label="상품 선택"/>
                  </label>
                  <div class="cart-item__left">
                    <div class="cart-item__thumb-wrap">
                      <img src="${pageContext.request.contextPath}/images/category/type-outer-coat.jpg" alt="" class="cart-item__thumb" width="96" height="96" loading="lazy"/>
                    </div>
                  </div>
                  <button type="button" class="cart-item__option-btn" aria-label="옵션(표시용): 블랙 / 105 / 2개" disabled>블랙 / 105 / 2개</button>
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

        <div class="cart-order-bar poke-order-bar" id="pokeOrderBar">
          <button type="button" class="cart-order-bar__btn detail-sheet-btn primary" id="pokeGiftSubmitBtn" aria-label="선물하기(표시용)">
            총 <span id="pokeGiftCount">3</span>개 상품 선물하기
          </button>
        </div>
      </div>
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/js/poke-list.js"></script>
</body>
</html>

