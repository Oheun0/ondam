<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  // 하단바 활성 탭(더미): 필요 시 변경
  request.setAttribute("bottomNav", "home");
%>
<c:set var="dummyOrderItemCount" value="2"/>
<c:set var="dummyOrderSummary" value="봄 니트 가디건 1개 외 1건"/>
<c:set var="dummyOrderNo" value="20260407-0001"/>
<c:set var="dummyOrderAt" value="2026.04.07 14:30"/>
<c:set var="dummyPayTotal" value="72,000원"/>
<c:set var="dummyPayMethod" value="함께지갑"/>
<c:set var="dummyReceiverName" value="김지현"/>
<c:set var="dummyReceiverPhone" value="010-1234-5678"/>
<c:set var="dummyAddress" value="(47323) 부산광역시 부산진구 가야대로 123, 101호"/>
<c:set var="dummyDeliveryRequest" value="부재 시 경비실에 맡겨주세요"/>
<c:set var="dummyWalletAfterBalance" value="18,000원"/>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <title>주문 완료</title>

  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/wallet.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/order-complete.css">
</head>
<body class="order-complete-page" data-context-path="${pageContext.request.contextPath}">
<div class="app-shell" id="orderCompleteRoot">
  <div class="top-header-cluster">
    <jsp:include page="../layout/header.jsp" />
  </div>

  <main class="page-wrap order-complete-wrap" aria-label="주문 완료">
    <!-- 완료 카드 -->
    <section class="section-box oc-hero" aria-label="주문 완료 안내">
      <div class="oc-hero-icon" aria-hidden="true">
        <span class="material-symbols-outlined oc-hero-symbol">order_approve</span>
      </div>
      <h1 class="oc-hero-title">주문이 완료되었어요</h1>
      <p class="oc-hero-sub">배송이 시작되면 알려드릴게요</p>
    </section>

    <!-- 주문 요약 -->
    <section class="section-box oc-card" aria-label="주문 요약">
      <div class="oc-card-head">
        <h2 class="oc-card-title">주문 요약</h2>
      </div>

      <div class="oc-order-line">
        <p class="oc-order-count">주문 상품 <span class="oc-strong">${dummyOrderItemCount}개</span></p>
        <p class="oc-order-desc">${dummyOrderSummary}</p>
      </div>

      <dl class="oc-kv" aria-label="주문 정보">
        <div class="oc-kv-row">
          <dt class="oc-kv-key">주문번호</dt>
          <dd class="oc-kv-val">${dummyOrderNo}</dd>
        </div>
        <div class="oc-kv-row">
          <dt class="oc-kv-key">주문일시</dt>
          <dd class="oc-kv-val">${dummyOrderAt}</dd>
        </div>
        <div class="oc-kv-row">
          <dt class="oc-kv-key">총 결제 금액</dt>
          <dd class="oc-kv-val oc-kv-val--strong">${dummyPayTotal}</dd>
        </div>
        <div class="oc-kv-row">
          <dt class="oc-kv-key">결제수단</dt>
          <dd class="oc-kv-val">${dummyPayMethod}</dd>
        </div>
      </dl>
    </section>

    <!-- 배송 정보 -->
    <section class="section-box oc-card" aria-label="배송 정보">
      <div class="oc-card-head">
        <h2 class="oc-card-title">배송 정보</h2>
      </div>

      <div class="oc-ship-block">
        <p class="oc-ship-who">
          <span class="oc-strong">${dummyReceiverName}</span>
          <span class="oc-ship-sep" aria-hidden="true">|</span>
          <span class="oc-strong">${dummyReceiverPhone}</span>
        </p>
        <p class="oc-ship-addr">${dummyAddress}</p>
        <c:if test="${not empty dummyDeliveryRequest}">
          <p class="oc-ship-req"><span class="oc-ship-req__label">배송 요청사항</span> <span class="oc-ship-req__val">${dummyDeliveryRequest}</span></p>
        </c:if>
      </div>
    </section>

    <!-- 함께지갑 안내 (결제수단이 함께지갑일 때만) -->
    <c:if test="${dummyPayMethod eq '함께지갑'}">
      <section class="oc-note" aria-label="함께지갑 안내">
        <p class="oc-note-line">함께지갑에서 결제 금액이 차감되었어요</p>
        <p class="oc-note-line oc-note-line--balance">
          결제 후 잔액 <span class="oc-strong">${dummyWalletAfterBalance}</span>
        </p>
      </section>
    </c:if>

    <!-- 하단 버튼 -->
    <div class="oc-actions" aria-label="다음 동작">
      <button type="button" class="oc-primary-btn" id="ocOrderListBtn">주문내역 보기</button>
    </div>
  </main>

  <jsp:include page="../layout/bottomNav.jsp" />
</div>

<script src="${pageContext.request.contextPath}/js/order-complete.js"></script>
</body>
</html>

