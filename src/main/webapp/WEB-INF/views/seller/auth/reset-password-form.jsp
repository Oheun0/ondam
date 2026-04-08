<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <title>온담 판매자센터 | 새 비밀번호 설정</title>

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
        <h1 class="seller-auth-title" id="sellerAuthTitle">새 비밀번호 설정</h1>
        <p class="seller-auth-sub">새 비밀번호를 입력해 주세요</p>
      </header>

      <section class="seller-auth-card" aria-label="새 비밀번호 설정">
        <form class="seller-auth-form" id="sellerResetPwForm2" action="#" method="post" novalidate>
          <div class="seller-auth-field">
            <label class="seller-auth-label" for="newPw">새 비밀번호</label>
            <input
              type="password"
              id="newPw"
              name="newPw"
              class="input seller-auth-input"
              placeholder="새 비밀번호를 입력해 주세요"
              autocomplete="new-password"
            >
            <p class="check-message error seller-auth-error hidden" id="newPwError" aria-live="polite"></p>
            <p class="seller-auth-helper seller-auth-helper--rule">
              비밀번호는 8자 이상, 영문/숫자/특수문자를 포함해 주세요
            </p>
          </div>

          <div class="seller-auth-field">
            <label class="seller-auth-label" for="newPw2">새 비밀번호 확인</label>
            <input
              type="password"
              id="newPw2"
              name="newPw2"
              class="input seller-auth-input"
              placeholder="새 비밀번호를 다시 입력해 주세요"
              autocomplete="new-password"
            >
            <p class="check-message error seller-auth-error hidden" id="newPw2Error" aria-live="polite"></p>
          </div>

          <div class="seller-auth-actions">
            <button type="submit" class="seller-auth-btn seller-auth-btn--primary" id="sellerChangePwBtn">비밀번호 변경</button>
            <a class="seller-auth-btn seller-auth-btn--ghost" href="${pageContext.request.contextPath}/seller/auth/login">로그인으로</a>
          </div>

          <p class="check-message error seller-auth-error seller-auth-error--form hidden" id="sellerFormError" aria-live="assertive"></p>
        </form>
      </section>
    </main>
  </div>

  <script src="${pageContext.request.contextPath}/js/seller/reset-password-form.js"></script>
</body>
</html>

