<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>주문상세</title>

  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/wallet.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-detail.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cart.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/order-detail.css">
</head>
<body class="order-detail-page" data-context-path="${pageContext.request.contextPath}">
  <div class="detail-shell">
    <div class="detail-page-inner detail-page-inner--sticky-header order-detail-inner" id="orderDetailRoot">
      <div class="order-detail-sticky-head">
        <div class="order-detail-header-wrap">
          <jsp:include page="/WEB-INF/views/layout/back-header.jsp"/>
          <h1 class="order-detail-header-title">주문상세</h1>
        </div>
      </div>

      <main class="order-detail-main" aria-label="주문상세">
        <!-- 상단 주문 정보 요약 -->
        <section class="od-card od-summary-card" aria-label="주문 정보">
          <dl class="od-kv">
            <div class="od-kv-row">
              <dt class="od-kv-key">주문번호</dt>
              <dd class="od-kv-val">20260407-0001</dd>
            </div>
            <div class="od-kv-row">
              <dt class="od-kv-key">결제날짜</dt>
              <dd class="od-kv-val">2026.04.07 14:30</dd>
            </div>
          </dl>
        </section>

        <!-- 상품 리스트 (브랜드별) -->
        <section class="od-card" aria-label="주문 상품">
          <section class="cart-brand-group od-brand-group" aria-label="온담 배송상품">
            <h2 class="cart-brand-group__title">온담 <span class="od-brand-count">1개</span></h2>
            <ul class="cart-brand-group__list">
              <li>
                <article class="cart-item od-item" aria-label="부드러운 라운드 니트 가디건">
                  <div class="cart-item__left">
                    <div class="cart-item__thumb-wrap">
                      <img src="${pageContext.request.contextPath}/images/category/type-top-knit.jpg" alt="" class="cart-item__thumb" loading="lazy"/>
                    </div>
                  </div>

                  <div class="cart-item__body">
                    <p class="od-ship-status">배송중</p>
                    <p class="cart-item__name">부드러운 라운드 니트 가디건</p>
                    <p class="cart-item__option">노란색 / 90</p>
                    <div class="cart-item__bottom">
                      <div class="cart-item__price-block">
                        <span class="cart-item__price-original">50,000원</span>
                        <span class="cart-item__price-sale">40,000원</span>
                      </div>
                    </div>
                  </div>

                  <div class="od-actions" aria-label="상품 액션">
                    <button type="button" class="od-action-btn">상품 문의하기</button>
                  </div>
                </article>
              </li>
            </ul>
          </section>

          <section class="cart-brand-group od-brand-group" aria-label="봄니트샵 배송상품">
            <h2 class="cart-brand-group__title">봄니트샵 <span class="od-brand-count">1개</span></h2>
            <ul class="cart-brand-group__list">
              <li>
                <article class="cart-item od-item" aria-label="편안한 봄 니트 조끼">
                  <div class="cart-item__left">
                    <div class="cart-item__thumb-wrap">
                      <img src="${pageContext.request.contextPath}/images/category/type-top-knit.jpg" alt="" class="cart-item__thumb" loading="lazy"/>
                    </div>
                  </div>

                  <div class="cart-item__body">
                    <p class="od-ship-status">구매확정</p>
                    <p class="cart-item__name">편안한 봄 니트 조끼</p>
                    <p class="cart-item__option">베이지 / 95</p>
                    <div class="cart-item__bottom">
                      <div class="cart-item__price-block cart-item__price-block--plain">
                        <span class="cart-item__price-sale">40,000원</span>
                      </div>
                    </div>
                  </div>

                  <div class="od-actions" aria-label="상품 액션">
                    <%-- 구매확정 상태가 아닐 경우 이 버튼 미노출(더미: 이 상품은 구매확정이라 노출)
                    배송완료 상태면 버튼명 "구매 확정하기" --%>
                    <button type="button" class="od-action-btn od-action-btn--primary">리뷰 작성하고 쿠폰받기</button>
                    <button type="button" class="od-action-btn">상품 문의하기</button>
                  </div>
                </article>
              </li>
            </ul>
          </section>
        </section>

        <!-- 배송 정보 -->
        <section class="od-card" aria-label="배송 정보">
          <h2 class="od-card-title">배송 정보</h2>
          <dl class="od-kv">
            <div class="od-kv-row od-kv-row--loose">
              <dt class="od-kv-key">받는 분</dt>
              <dd class="od-kv-val">김지현 | 010-1234-5678</dd>
            </div>
            <div class="od-kv-row od-kv-row--loose">
              <dt class="od-kv-key">주소</dt>
              <dd class="od-kv-val od-kv-val--wrap">(47323) 부산광역시 부산진구 가야대로 123, 101호</dd>
            </div>
            <div class="od-kv-row od-kv-row--loose">
              <dt class="od-kv-key">요청사항</dt>
              <dd class="od-kv-val od-kv-val--wrap">부재 시 경비실에 맡겨주세요</dd>
            </div>
          </dl>
        </section>

        <!-- 결제 정보 -->
        <section class="od-card" aria-label="결제 정보">
          <h2 class="od-card-title">결제 정보</h2>
          <dl class="od-pay">
            <div class="od-pay-row">
              <dt class="od-pay-key">결제 방법</dt>
              <dd class="od-pay-val">함께지갑</dd>
            </div>
            <div class="od-pay-row">
              <dt class="od-pay-key">총 상품 금액</dt>
              <dd class="od-pay-val">80,000원</dd>
            </div>
            <div class="od-pay-row">
              <dt class="od-pay-key">상품 할인</dt>
              <dd class="od-pay-val od-pay-val--minus">-5,000원</dd>
            </div>
            <div class="od-pay-row">
              <dt class="od-pay-key">쿠폰 할인</dt>
              <dd class="od-pay-val od-pay-val--minus">-3,000원</dd>
            </div>
            <div class="od-pay-row">
              <dt class="od-pay-key">배송비</dt>
              <dd class="od-pay-val">0원</dd>
            </div>
            <div class="od-pay-row od-pay-row--total">
              <dt class="od-pay-key">결제금액</dt>
              <dd class="od-pay-val od-pay-val--total">72,000원</dd>
            </div>
          </dl>
        </section>

        <div class="od-bottom-spacer" aria-hidden="true"></div>
      </main>
    </div>
  </div>
</body>
</html>

