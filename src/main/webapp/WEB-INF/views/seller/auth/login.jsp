<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <title>온담 판매자센터 | 로그인</title>

  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-auth.css">
</head>
<body class="seller-auth-page" data-context-path="${pageContext.request.contextPath}">
  <div class="seller-auth-shell">
    <main class="seller-auth-main" aria-labelledby="sellerAuthTitle">
      <header class="seller-auth-brand">
        <div class="seller-auth-brand__row" aria-hidden="true">
          <span class="seller-auth-mark">온담</span>
          <span class="seller-auth-brand__badge">판매자센터</span>
        </div>
        <h1 class="seller-auth-title" id="sellerAuthTitle">온담 판매자센터</h1>
        <p class="seller-auth-sub">판매자 전용 페이지입니다</p>
        <p class="seller-auth-sub seller-auth-sub--muted">아이디와 비밀번호로 로그인해 주세요</p>
      </header>

      <section class="seller-auth-card" aria-label="판매자 로그인">
        <form class="seller-auth-form" id="sellerLoginForm" action="#" method="post" novalidate>
          <div class="seller-auth-field">
            <label class="seller-auth-label" for="sellerId">판매자 아이디</label>
            <input
              type="text"
              id="sellerId"
              name="sellerId"
              class="input seller-auth-input"
              placeholder="아이디를 입력해 주세요"
              autocomplete="username"
              inputmode="text"
            >
            <p class="check-message error seller-auth-error hidden" id="sellerIdError" aria-live="polite"></p>
          </div>

          <div class="seller-auth-field">
            <label class="seller-auth-label" for="sellerPw">비밀번호</label>
            <input
              type="password"
              id="sellerPw"
              name="sellerPw"
              class="input seller-auth-input"
              placeholder="비밀번호를 입력해 주세요"
              autocomplete="current-password"
            >
            <p class="check-message error seller-auth-error hidden" id="sellerPwError" aria-live="polite"></p>
          </div>

          <div class="seller-auth-row seller-auth-row--between">
            <label class="seller-auth-check">
              <input type="checkbox" id="sellerRemember" name="sellerRemember">
              <span>로그인 상태 유지</span>
            </label>
          </div>

          <button type="submit" class="seller-auth-btn seller-auth-btn--primary" id="sellerLoginBtn">
            로그인
          </button>

          <p class="check-message error seller-auth-error seller-auth-error--form hidden" id="sellerFormError" aria-live="assertive"></p>
          <p class="seller-auth-helper" id="sellerLoginHelper">현재는 더미 화면입니다. 서버 연동은 추후 진행됩니다.</p>
        </form>
      </section>

      <nav class="seller-auth-links" aria-label="판매자 인증 링크">
        <a class="seller-auth-link" href="${pageContext.request.contextPath}/seller/auth/signup">회원가입</a>
        <span class="seller-auth-link-sep" aria-hidden="true">|</span>
        <a class="seller-auth-link" href="${pageContext.request.contextPath}/seller/auth/find-id">아이디 찾기</a>
        <span class="seller-auth-link-sep" aria-hidden="true">|</span>
        <a class="seller-auth-link" href="${pageContext.request.contextPath}/seller/auth/reset-password">비밀번호 재설정</a>
      </nav>
    </main>
  </div>

  <script src="${pageContext.request.contextPath}/js/seller/login.js"></script>
</body>
</html>
