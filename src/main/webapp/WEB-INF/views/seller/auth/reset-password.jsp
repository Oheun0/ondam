<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <title>온담 파트너 | 비밀번호 재설정</title>

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
        <div class="seller-auth-brand__logo" aria-hidden="true">
          <img src="${pageContext.request.contextPath}/images/logo/logo_4.svg" alt="" class="seller-auth-logo" width="140" height="44" decoding="async">
        </div>
        <h1 class="seller-auth-title" id="sellerAuthTitle">비밀번호 재설정</h1>
        <p class="seller-auth-sub">본인 확인 후 비밀번호를 변경할 수 있어요</p>
      </header>

      <section class="seller-auth-card" aria-label="비밀번호 재설정 요청">
        <form class="seller-auth-form" id="sellerResetPwForm" action="#" method="post" novalidate>
          <div class="seller-auth-field">
            <label class="seller-auth-label" for="sellerId">판매자 아이디</label>
            <input
              type="text"
              id="sellerId"
              name="sellerId"
              class="input seller-auth-input"
              placeholder="아이디를 입력해 주세요"
              autocomplete="username"
            >
            <p class="check-message error seller-auth-error hidden" id="sellerIdError" aria-live="polite"></p>
          </div>

          <div class="seller-auth-field">
            <label class="seller-auth-label" for="sellerEmail">가입 이메일</label>
            <input
              type="email"
              id="sellerEmail"
              name="sellerEmail"
              class="input seller-auth-input"
              placeholder="이메일을 입력해 주세요"
              autocomplete="email"
              inputmode="email"
            >
            <p class="check-message error seller-auth-error hidden" id="sellerEmailError" aria-live="polite"></p>
          </div>

          <div class="seller-auth-code-row hidden" id="sellerCodeArea">
            <div class="seller-auth-field seller-auth-field--code">
              <label class="seller-auth-label" for="sellerCode">인증코드</label>
              <input
                type="text"
                id="sellerCode"
                name="sellerCode"
                class="input seller-auth-input"
                placeholder="인증코드를 입력해 주세요"
                inputmode="numeric"
              >
              <p class="check-message error seller-auth-error hidden" id="sellerCodeError" aria-live="polite"></p>
              <p class="seller-auth-helper seller-auth-helper--code" id="sellerCodeHelper">
                더미 코드: <strong>123456</strong> (실제 발송/검증은 아직 연동되지 않았어요)
              </p>
            </div>
          </div>

          <div class="seller-auth-actions" id="sellerResetPwActions">
            <button type="button" class="seller-auth-btn seller-auth-btn--primary" id="sellerSendCodeBtn">인증코드 받기</button>
            <a class="seller-auth-btn seller-auth-btn--ghost" href="${pageContext.request.contextPath}/seller/auth/login">로그인으로</a>
          </div>

          <div class="seller-auth-actions hidden" id="sellerVerifyActions">
            <button type="submit" class="seller-auth-btn seller-auth-btn--primary" id="sellerVerifyBtn">인증하기</button>
            <button type="button" class="seller-auth-btn seller-auth-btn--ghost" id="sellerResendBtn">인증코드 다시 받기</button>
          </div>

          <p class="check-message error seller-auth-error seller-auth-error--form hidden" id="sellerFormError" aria-live="assertive"></p>
        </form>
      </section>

      <nav class="seller-auth-links" aria-label="판매자 인증 링크">
        <a class="seller-auth-link seller-auth-link--inline" href="${pageContext.request.contextPath}/seller/auth/find-id">아이디를 잊으셨나요? 아이디 찾기</a>
      </nav>
    </main>
  </div>

  <script src="${pageContext.request.contextPath}/js/seller/reset-password.js"></script>
</body>
</html>

