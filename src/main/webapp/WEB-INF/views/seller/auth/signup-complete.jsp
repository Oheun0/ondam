<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  // 더미 표시(서버 연동 전). 쿼리스트링으로 들어오면 우선 사용: ?storeName=...&sellerId=...
  String storeName = request.getParameter("storeName");
  if (storeName == null || storeName.trim().isEmpty()) storeName = "온담스토어";
  storeName = storeName.trim();

  String sellerId = request.getParameter("sellerId");
  if (sellerId == null || sellerId.trim().isEmpty()) sellerId = "ondam_seller";
  sellerId = sellerId.trim();

  String maskedSellerId = sellerId;
  if (sellerId.length() >= 4) {
    maskedSellerId = sellerId.substring(0, Math.min(6, sellerId.length())) + "****";
  } else {
    maskedSellerId = "****";
  }
%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <title>온담 파트너 | 회원가입 완료</title>

  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-auth.css">
</head>
<body class="seller-auth-page seller-auth-page--complete" data-context-path="${pageContext.request.contextPath}">
  <div class="seller-auth-shell">
    <main class="seller-auth-main seller-auth-main--complete" aria-labelledby="sellerCompleteTitle">
      <header class="seller-auth-brand seller-auth-brand--complete">
        <div class="seller-auth-brand__logo" aria-hidden="true">
          <img src="${pageContext.request.contextPath}/images/logo/logo_4.svg" alt="" class="seller-auth-logo" width="140" height="44" decoding="async">
        </div>
        <h1 class="seller-auth-title" id="sellerCompleteTitle">회원가입 완료</h1>
        <p class="seller-auth-sub">판매자 계정이 생성되었습니다</p>
        <p class="seller-auth-sub seller-auth-sub--muted">로그인 후 상품과 쇼츠를 등록해 보세요</p>
      </header>

      <section class="seller-auth-card seller-auth-complete-card" id="sellerCompleteCard" aria-label="가입 완료 안내">
        <div class="seller-auth-complete-icon" aria-hidden="true">
          <span class="material-icons">check_circle</span>
        </div>

        <h2 class="seller-auth-complete-title">가입이 완료되었어요</h2>
        <p class="seller-auth-complete-desc">아래 정보를 확인한 뒤 로그인해 주세요.</p>

        <div class="seller-auth-summary-card" aria-label="가입 정보 요약">
          <div class="seller-auth-summary-row">
            <span class="seller-auth-summary-label">상호명</span>
            <span class="seller-auth-summary-value"><%= storeName %></span>
          </div>
          <div class="seller-auth-summary-row">
            <span class="seller-auth-summary-label">판매자 아이디</span>
            <span class="seller-auth-summary-value"><%= maskedSellerId %></span>
          </div>
        </div>

        <div class="seller-auth-next-card" aria-label="다음 단계 안내">
          <h3 class="seller-auth-next-title">다음으로 해보세요</h3>
          <ul class="seller-auth-next-list">
            <li>배송지와 반품지를 확인해 주세요</li>
            <li>첫 상품을 등록해 보세요</li>
            <li>쇼츠 영상을 올리면 더 쉽게 상품을 보여줄 수 있어요</li>
          </ul>
        </div>

        <div class="seller-auth-actions seller-auth-actions--complete">
          <button type="button" class="seller-auth-btn seller-auth-btn--primary" id="sellerGoLoginBtn">
            로그인하기
          </button>
        </div>
      </section>
    </main>
  </div>

  <script src="${pageContext.request.contextPath}/js/seller/signup-complete.js"></script>
</body>
</html>

