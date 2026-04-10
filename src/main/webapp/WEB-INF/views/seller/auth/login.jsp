<!-- login.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <title>온담 파트너 | 로그인</title>

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
        <h1 class="seller-auth-title" id="sellerAuthTitle">온담 파트너</h1>
        <p class="seller-auth-sub">판매자 전용 페이지입니다</p>
        <p class="seller-auth-sub seller-auth-sub--muted">아이디와 비밀번호로 로그인해 주세요</p>
      </header>

      <section class="seller-auth-card" aria-label="판매자 로그인">
        <form class="seller-auth-form" id="sellerLoginForm" 
		      action="${pageContext.request.contextPath}/seller/auth?action=login" 
		      method="post" novalidate>
		  
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
		
			<p class="check-message error seller-auth-error seller-auth-error--form ${empty loginError and empty sessionScope.loginError ? 'hidden' : ''}" 
			   id="sellerFormError" aria-live="assertive" 
			   style="color: red; ${(not empty loginError or not empty sessionScope.loginError) ? 'display: block;' : 'display: none;'}">
			   
			  ${not empty sessionScope.loginError ? sessionScope.loginError : loginError}
			  
			  <%-- 이제 상단에 선언문을 추가했으므로 세션 값이 정상적으로 삭제됩니다 --%>
			  <c:remove var="loginError" scope="session" />
			</p>
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
