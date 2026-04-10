<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- 파라미터 받기 및 기본값 설정 --%>
<c:set var="storeName" value="${empty param.storeName ? '온담 파트너스' : param.storeName}" />
<c:set var="sellerId" value="${empty param.sellerId ? 'ondam_seller' : param.sellerId}" />

<%-- 아이디 마스킹 처리 로직 (앞 최대 6자리 노출 + ****) --%>
<c:choose>
  <c:when test="${fn:length(sellerId) >= 4}">
    <c:set var="maskLen" value="${fn:length(sellerId) > 6 ? 6 : fn:length(sellerId)}" />
    <c:set var="maskedSellerId" value="${fn:substring(sellerId, 0, maskLen)}****" />
  </c:when>
  <c:otherwise>
    <c:set var="maskedSellerId" value="****" />
  </c:otherwise>
</c:choose>

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
            <!-- JSTL의 c:out을 사용하여 XSS 방어 -->
            <span class="seller-auth-summary-value"><c:out value="${storeName}"/></span>
          </div>
          <div class="seller-auth-summary-row">
            <span class="seller-auth-summary-label">판매자 아이디</span>
            <span class="seller-auth-summary-value"><c:out value="${maskedSellerId}"/></span>
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