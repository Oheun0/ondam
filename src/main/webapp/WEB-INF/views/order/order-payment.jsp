<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>주문 / 결제</title>

  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-detail.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/order-payment.css">
</head>
<body class="order-payment-page" data-context-path="${pageContext.request.contextPath}">
  <div class="detail-shell">
    <div class="detail-page-inner detail-page-inner--sticky-header order-payment-inner" id="orderPaymentRoot">
      <div class="order-payment-sticky-head">
        <div class="order-payment-header-wrap">
          <jsp:include page="/WEB-INF/views/layout/back-header.jsp"/>
          <h1 class="order-payment-header-title">주문 / 결제</h1>
        </div>
      </div>

      <main class="order-payment-main" aria-label="주문 및 결제">
        <!-- 주문 상품 요약 -->
        <section class="op-card op-order-summary" aria-label="주문 상품 요약">
          <div class="op-order-summary__top">
            <h2 class="op-card-title">주문 상품 <span class="op-strong">2개</span></h2>
          </div>
          <p class="op-order-summary__line">
            <span class="op-strong">봄 니트 가디건 1개</span> 외 <span class="op-strong">1건</span>
          </p>
        </section>

        <!-- 배송지 변경하기 버튼 클릭 시 회원정보 수정 - 배송지수정으로 -->
        <section class="op-card op-ship-card" aria-label="배송지">
          <div class="op-card-head">
            <div class="op-card-head__left">
              <h2 class="op-card-title">배송지</h2>
              <span class="op-badge op-badge--muted" aria-label="기본배송지">기본배송지</span>
            </div>
            <button type="button" class="op-link-btn" aria-label="배송지 변경하기">배송지 변경하기</button>
          </div>
          <div class="op-ship-info">
            <p class="op-ship-who">
              <span class="op-strong">김지현</span>
              <span class="op-ship-sep" aria-hidden="true">|</span>
              <span class="op-strong">010-1234-5678</span>
            </p>
            <p class="op-ship-addr">(47323) 부산광역시 부산진구 가야대로 123, 101호</p>
          </div>

          <!-- 배송 요청사항: 입력창(읽기 전용) 클릭 → 오버레이 선택창 (페이지 안 밀림) -->
          <div class="op-ship-request">
            <button type="button"
                    class="op-select-field"
                    id="opDeliveryFieldBtn"
                    aria-haspopup="listbox"
                    aria-expanded="false"
                    aria-controls="opDeliveryPanel">
              <span class="op-select-field__text" id="opDeliveryFieldText">배송 시 요청사항을 선택해주세요</span>
              <span class="material-icons op-select-field__chev" aria-hidden="true">expand_more</span>
            </button>
            <div class="op-custom-wrap hidden" id="opDeliveryCustomWrap">
              <label class="sr-only" for="opDeliveryCustomInput">요청사항 직접 입력</label>
              <input type="text"
                     id="opDeliveryCustomInput"
                     class="op-custom-input"
                     placeholder="원하시는 요청사항을 적어주세요"
                     maxlength="60"
                     autocomplete="off"/>
              <p class="op-custom-hint">최대 20자까지 입력할 수 있어요</p> <!-- db에따라 변경해주세요 -->
            </div>
          </div>
        </section>

        <!-- 쿠폰 적용 -->
        <section class="op-card op-accordion" aria-label="쿠폰 적용하기">
          <div class="op-acc-header" aria-hidden="true">
            <span class="op-acc-label">쿠폰 적용하기</span>
          </div>
          <div class="op-selected-display" aria-live="polite">
            <p class="op-selected-display__label">적용된 쿠폰</p>
            <button type="button"
                    class="op-selected-display__box op-coupon-toggle"
                    id="opCouponMirrorBtn"
                    aria-expanded="false"
                    aria-controls="opCouponPanel">
              <span class="op-selected-display__box--stack" id="opCouponMirror">
                <span class="op-selected-display__main" id="opCouponMirrorTitle">사용 안 함</span>
                <span class="op-selected-display__sub hidden" id="opCouponMirrorDesc"></span>
              </span>
              <span class="material-icons op-coupon-toggle__chev" aria-hidden="true">expand_more</span>
            </button>
          </div>
          <div class="op-acc-panel hidden" id="opCouponPanel" role="region" aria-label="쿠폰 선택">
            <p class="op-sub-toggle" id="opCouponCountText">사용 가능 쿠폰 2장</p>
            <div class="op-coupon-list" role="radiogroup" aria-label="쿠폰 목록">
              <button type="button"
                      class="op-coupon-card"
                      data-coupon-id="welcome10"
                      data-coupon-title="신규 회원 전용"
                      data-coupon-desc="10% 할인"
                      role="radio"
                      aria-checked="false">
                <span class="op-coupon-title">신규 회원 전용</span>
                <span class="op-coupon-desc">10% 할인</span>
              </button>
              <button type="button"
                      class="op-coupon-card"
                      data-coupon-id="spring3000"
                      data-coupon-title="봄맞이 기획전 참여 브랜드"
                      data-coupon-desc="20,000원 이상 구매 시 3,000원 할인"
                      role="radio"
                      aria-checked="false">
                <span class="op-coupon-title">봄맞이 기획전 참여 브랜드</span>
                <span class="op-coupon-desc">20,000원 이상 구매 시 3,000원 할인</span>
              </button>
            </div>
          </div>
        </section>

        <!-- 결제수단 : 회원가입할 때 지정한 결제수단이 기본값, 주문 기록이 있다면 이전에 사용한 결제수단으로 체크되어있음 -->
        <section class="op-card op-payment-methods" aria-label="결제수단">
          <div class="op-card-head op-card-head--simple">
            <h2 class="op-card-title">결제수단</h2>
          </div>
          <div class="op-pay-grid" id="opPayGrid" role="radiogroup" aria-label="결제수단 선택">
            <button type="button" class="op-pay-btn" data-pay="wallet" role="radio" aria-checked="false">함께지갑</button>
            <div class="op-pay-extra hidden" id="opWalletExtra" aria-live="polite">
              <p class="op-wallet-line op-strong">가족과 함께 쓰는 지갑이에요</p>
              <p class="op-wallet-line">현재 잔액 <span class="op-strong" id="opWalletBalance">100,000원</span></p>
            </div>
            <button type="button" class="op-pay-btn" data-pay="card" role="radio" aria-checked="false">카드 결제</button>
            <button type="button" class="op-pay-btn" data-pay="transfer" role="radio" aria-checked="false">계좌이체</button>
          </div>
        </section>

        <!-- 최종 결제 금액 -->
        <section class="op-card op-price-card" aria-label="최종 결제 금액">
          <div class="op-card-head op-card-head--simple">
            <h2 class="op-card-title">최종 결제 금액</h2>
          </div>
          <dl class="op-price-list" aria-label="금액 상세">
            <div class="op-price-row">
              <dt class="op-price-label">총 상품 금액</dt>
              <dd class="op-price-value" id="opTotalProduct">80,000원</dd>
            </div>
            <div class="op-price-row">
              <dt class="op-price-label">상품 할인</dt>
              <dd class="op-price-value op-price-value--minus" id="opProductDiscount">-5,000원</dd>
            </div>
            <div class="op-price-row">
              <dt class="op-price-label">쿠폰 할인</dt>
              <dd class="op-price-value op-price-value--minus" id="opCouponDiscount">-3,000원</dd>
            </div>
            <div class="op-price-row op-price-row--ship">
              <dt class="op-price-label">배송비</dt>
              <dd class="op-price-value op-price-value--ship">
              	<!-- 배송비 0원일 경우에 무료배송 뱃지 추가 -->
                <span class="op-badge op-badge--free" id="opFreeShipBadge">무료배송</span>
                <span id="opShippingFee">0원</span>
              </dd>
            </div>
          </dl>
        </section>

        <!-- 하단 바 때문에 여유 -->
        <div class="op-bottom-spacer" aria-hidden="true"></div>
      </main>

      <!-- 배송 요청사항 선택 패널(오버레이) : 카드 간격 영향 없도록 main 밖에 둠 -->
      <div class="op-acc-panel hidden" id="opDeliveryPanel" role="listbox" aria-label="배송 요청사항 선택">
        <div class="op-toggle-grid" role="presentation">
          <button type="button" class="op-toggle-btn" data-delivery="배송 전에 꼭 연락주세요." role="option" aria-selected="false">배송 전에 꼭 연락주세요.</button>
          <button type="button" class="op-toggle-btn" data-delivery="부재 시 경비실에 맡겨주세요." role="option" aria-selected="false">부재 시 경비실에 맡겨주세요.</button>
          <button type="button" class="op-toggle-btn" data-delivery="집 앞에 놓아주세요" role="option" aria-selected="false">집 앞에 놓아주세요.</button>
          <button type="button" class="op-toggle-btn" data-delivery="집으로 직접 배송해주세요." role="option" aria-selected="false">집으로 직접 배송해주세요.</button>
          <button type="button" class="op-toggle-btn" data-delivery="배송 전에 꼭 연락주세요." role="option" aria-selected="false">배송 전에 꼭 연락주세요.</button>
          <button type="button" class="op-toggle-btn" data-delivery="직접 입력" data-delivery-custom="true" role="option" aria-selected="false">직접 입력</button>
        </div>
      </div>

      <!-- 하단 고정 결제바 -->
      <div class="op-paybar" id="opPayBar" role="contentinfo" aria-label="결제">
        <div class="op-paybar__row">
          <span class="op-paybar__label">총 결제 금액</span>
          <div class="op-paybar__amounts">
            <span class="op-paybar__orig hidden" id="opPaybarOrig">88,000원</span>
            <span class="op-paybar__final" id="opPaybarFinal">72,000원</span>
          </div>
        </div>
        <button type="button" class="op-paybar__btn" id="opPaySubmitBtn">총 72,000원 결제하기</button>
      </div>
    </div>
  </div>

  <!-- 함께지갑: 내 사람 미연동 안내 모달 (더미로 지금은 함께지갑 결제 시 기본 노출됨) -->
  <div class="op-modal hidden" id="opWalletConnectModal" role="dialog" aria-modal="true" aria-labelledby="opWalletConnectTitle">
    <div class="op-modal-dim" data-op-modal-dismiss></div>
    <div class="op-modal-card">
      <h2 class="op-modal-title" id="opWalletConnectTitle">아직 함께지갑을 사용할 수 없어요</h2>
      <p class="op-modal-message">
        내 사람을 연결하고 다시 시도해 주세요
      </p>
      <div class="op-modal-actions op-modal-actions--double">
        <button type="button" class="op-modal-btn op-modal-btn--ghost" data-op-modal-dismiss>닫기</button>
        <button type="button" class="op-modal-btn op-modal-btn--primary" id="opWalletConnectGoBtn">내 사람 연결하기</button>
      </div>
    </div>
  </div>

  <%--
    함께지갑 잔액 부족 안내 모달
    - 사용 시점: 함께지갑으로 결제 시, (연동 완료 후) 잔액 < 결제 금액일 때
    - JS: order-payment.js 의 openWalletInsufficientModal() / closeWalletInsufficientModal()
    - 현재 결제 버튼에는 연결(미연동) 모달만 연결되어 있음(데모). 잔액 체크 붙일 때 위 함수 호출.
  --%>
  <div class="op-modal hidden" id="opWalletInsufficientModal" role="dialog" aria-modal="true" aria-labelledby="opWalletInsufficientTitle">
    <div class="op-modal-dim" data-op-wallet-insufficient-dismiss></div>
    <div class="op-modal-card">
      <h2 class="op-modal-title" id="opWalletInsufficientTitle">함께지갑 잔액이 부족해요</h2>
      <p class="op-modal-message">
        잔액을 충전하거나 다른 결제 수단을 선택해 주세요
      </p>
      <div class="op-modal-actions op-modal-actions--double">
        <button type="button" class="op-modal-btn op-modal-btn--ghost" data-op-wallet-insufficient-dismiss>닫기</button>
        <button type="button" class="op-modal-btn op-modal-btn--primary" id="opWalletChargeGoBtn">충전하러 가기</button>
      </div>
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/js/order-payment.js"></script>
</body>
</html>
