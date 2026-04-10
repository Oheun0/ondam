<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <title>온담 파트너 | 아이디 찾기</title>

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
        <h1 class="seller-auth-title" id="sellerAuthTitle">아이디 찾기</h1>
        <p class="seller-auth-sub">가입 시 입력한 정보로 아이디를 확인할 수 있어요</p>
      </header>

		<section class="seller-auth-card" aria-label="판매자 아이디 찾기">
	        <!-- 💡 [수정 1] action 주소 변경 (서버로 전송) -->
	        <form class="seller-auth-form" id="sellerFindIdForm" action="${pageContext.request.contextPath}/seller/auth/find-id" method="post" novalidate>
	          <div class="seller-auth-field">
	            <label class="seller-auth-label" for="sellerManagerName">담당자명</label>
	            <!-- 💡 [수정 2] 실패해도 입력한 값이 날아가지 않게 value 추가 -->
	            <input type="text" id="sellerManagerName" name="sellerManagerName" class="input seller-auth-input" placeholder="담당자명을 입력해 주세요" value="${param.sellerManagerName}" autocomplete="name">
	            <p class="check-message error seller-auth-error hidden" id="sellerManagerNameError"></p>
	          </div>
	
	          <div class="seller-auth-field">
	            <label class="seller-auth-label" for="sellerEmail">이메일</label>
	            <input type="email" id="sellerEmail" name="sellerEmail" class="input seller-auth-input" placeholder="이메일을 입력해 주세요" value="${param.sellerEmail}" autocomplete="email" inputmode="email">
	            <p class="check-message error seller-auth-error hidden" id="sellerEmailError"></p>
	          </div>
	
	          <div class="seller-auth-actions">
	            <button type="submit" class="seller-auth-btn seller-auth-btn--primary" id="sellerFindIdBtn">아이디 찾기</button>
	            <a class="seller-auth-btn seller-auth-btn--ghost" href="${pageContext.request.contextPath}/seller/auth">로그인으로</a>
	          </div>
	
	          <!-- 💡 [수정 3] Controller에서 보낸 에러 메시지 띄우기 -->
	          <p class="check-message error seller-auth-error seller-auth-error--form ${empty findIdError ? 'hidden' : ''}" id="sellerFindIdFormError" aria-live="assertive">
	            ${findIdError}
	          </p>
	
	          <!-- 💡 [수정 4] Controller에서 보낸 아이디(foundId)가 있을 때만 이 박스를 표시 -->
	          <div class="seller-auth-result-box ${empty foundId ? 'hidden' : ''}" id="sellerFindIdResult" aria-live="polite">
	            <div class="seller-auth-result-head">
	              <span class="material-icons-outlined" aria-hidden="true">verified</span>
	              <strong>아이디 확인</strong>
	            </div>
	            <div class="seller-auth-result-body">
	              <span class="seller-auth-result-label">아이디</span>
	              <span class="seller-auth-result-value" id="sellerFindIdValue">${foundId}</span>
	            </div>
	            <p class="seller-auth-helper seller-auth-helper--result">보안을 위해 아이디 일부는 마스킹되어 표시돼요.</p>
	          </div>
	        </form>
	      </section>

      <nav class="seller-auth-links" aria-label="판매자 인증 링크">
        <a class="seller-auth-link seller-auth-link--inline" href="${pageContext.request.contextPath}/seller/auth/reset-password">비밀번호를 잊으셨나요? 비밀번호 재설정</a>
      </nav>
    </main>
  </div>

  <script src="${pageContext.request.contextPath}/js/seller/find-id.js"></script>
</body>
</html>

