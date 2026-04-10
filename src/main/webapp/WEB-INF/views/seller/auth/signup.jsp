<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <title>온담 파트너 | 회원가입</title>

  <!-- 우편번호 API 라이브러리 -->
  <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-auth.css">
</head>
<body class="seller-auth-page seller-auth-page--signup" data-context-path="${pageContext.request.contextPath}">
  <div class="seller-auth-shell">
    <main class="seller-auth-main seller-auth-main--signup" aria-labelledby="sellerSignupTitle">
      <header class="seller-auth-brand seller-auth-brand--signup">
        <div class="seller-auth-brand__logo" aria-hidden="true">
          <img src="${pageContext.request.contextPath}/images/logo/logo_4.svg" alt="" class="seller-auth-logo" width="140" height="44" decoding="async">
        </div>
        <h1 class="seller-auth-title" id="sellerSignupTitle">판매자 회원가입</h1>
        <p class="seller-auth-sub">판매자 정보를 입력하면 운영 화면을 사용할 수 있어요</p>
        <p class="seller-auth-sub seller-auth-sub--muted">관리자 승인은 2~3영업일 이내 완료돼요</p>
      </header>

      <section class="seller-auth-card seller-auth-card--signup" aria-label="판매자 회원가입 입력">
        <form class="seller-auth-form" id="sellerSignupForm" 
		      action="${pageContext.request.contextPath}/seller/auth/signup" 
		      method="post" novalidate>
          
          <!-- 섹션 1. 사업자/기본 정보 -->
          <section class="seller-auth-section" aria-labelledby="sellerSignupSectionBiz">
            <h2 class="seller-auth-section-title" id="sellerSignupSectionBiz">사업자/기본 정보</h2>

            <div class="seller-auth-field">
              <label class="seller-auth-label" for="storeName">상호명(스토어명) <span class="seller-auth-required" aria-hidden="true">*</span></label>
              <input type="text" id="storeName" name="storeName" class="input seller-auth-input" placeholder="상호명을 입력해 주세요" autocomplete="organization">
              <p class="check-message error seller-auth-error hidden" id="storeNameError"></p>
            </div>

            <div class="seller-auth-field">
              <label class="seller-auth-label" for="managerName">담당자명 <span class="seller-auth-required" aria-hidden="true">*</span></label>
              <input type="text" id="managerName" name="managerName" class="input seller-auth-input" placeholder="담당자명을 입력해 주세요" autocomplete="name">
              <p class="check-message error seller-auth-error hidden" id="managerNameError"></p>
            </div>

            <div class="seller-auth-field">
			  <label class="seller-auth-label" for="bizNo">사업자등록번호 <span class="seller-auth-required" aria-hidden="true">*</span></label>
			  <input type="text" id="bizNo" name="bizNo" class="input seller-auth-input" placeholder="사업자등록번호 10자리를 입력해 주세요" inputmode="numeric" autocomplete="off" maxlength="10">
			  <p class="check-message error seller-auth-error hidden" id="bizNoError"></p>
			</div>
          </section>

          <!-- 섹션 2. 계정 정보 -->
          <section class="seller-auth-section" aria-labelledby="sellerSignupSectionAccount">
            <h2 class="seller-auth-section-title" id="sellerSignupSectionAccount">계정 정보</h2>

            <div class="seller-auth-field">
              <label class="seller-auth-label" for="sellerId">판매자 아이디 <span class="seller-auth-required" aria-hidden="true">*</span></label>
              <input type="text" id="sellerId" name="sellerId" class="input seller-auth-input" placeholder="아이디를 입력해 주세요" autocomplete="username" maxlength="20">
              <p class="check-message error seller-auth-error hidden" id="sellerIdError"></p>
            </div>

            <div class="seller-auth-field">
              <label class="seller-auth-label" for="sellerPw">비밀번호 <span class="seller-auth-required" aria-hidden="true">*</span></label>
              <input type="password" id="sellerPw" name="sellerPw" class="input seller-auth-input" placeholder="비밀번호를 입력해 주세요" autocomplete="new-password">
              <p class="check-message error seller-auth-error hidden" id="sellerPwError"></p>
              <p class="seller-auth-hint">8자 이상, 영문/숫자/특수문자를 포함해 주세요</p>
            </div>

            <div class="seller-auth-field">
              <label class="seller-auth-label" for="sellerPw2">비밀번호 확인 <span class="seller-auth-required" aria-hidden="true">*</span></label>
              <input type="password" id="sellerPw2" name="sellerPw2" class="input seller-auth-input" placeholder="비밀번호를 다시 입력해 주세요" autocomplete="new-password">
              <p class="check-message error seller-auth-error hidden" id="sellerPw2Error"></p>
            </div>
          </section>

          <!-- 섹션 3. 연락/주소 -->
          <section class="seller-auth-section seller-auth-section--contact" aria-labelledby="sellerSignupSectionContact">
            <h2 class="seller-auth-section-title" id="sellerSignupSectionContact">연락/주소</h2>

            <div class="seller-auth-field">
              <label class="seller-auth-label" for="phone">연락처(휴대폰) <span class="seller-auth-required" aria-hidden="true">*</span></label>
              <input type="tel" id="phone" name="phone" class="input seller-auth-input" placeholder="휴대폰 번호를 입력해 주세요" autocomplete="tel">
              <p class="check-message error seller-auth-error hidden" id="phoneError"></p>
            </div>

            <div class="seller-auth-field">
              <label class="seller-auth-label" for="email">이메일 <span class="seller-auth-required" aria-hidden="true">*</span></label>
              <input type="email" id="email" name="email" class="input seller-auth-input" placeholder="이메일을 입력해 주세요" autocomplete="email">
              <p class="check-message error seller-auth-error hidden" id="emailError"></p>
            </div>

            <div class="seller-auth-divider" role="presentation" aria-hidden="true"></div>

            <h3 class="seller-auth-subtitle">출고지(기본 배송지)</h3>

            <div class="seller-auth-field seller-auth-field--zip">
              <label class="seller-auth-label" for="shipZip">출고지 우편번호 <span class="seller-auth-required" aria-hidden="true">*</span></label>
              <div class="seller-auth-inline-row">
                <input type="text" id="shipZip" name="shipZip" class="input seller-auth-input" placeholder="우편번호" inputmode="numeric" autocomplete="postal-code" readonly>
                <button type="button" class="seller-auth-mini-btn" id="shipZipBtn">우편번호 찾기</button>
              </div>
              <p class="check-message error seller-auth-error hidden" id="shipZipError"></p>
            </div>

            <div class="seller-auth-field">
              <label class="seller-auth-label" for="shipAddr1">출고지 주소 <span class="seller-auth-required" aria-hidden="true">*</span></label>
              <input type="text" id="shipAddr1" name="shipAddr1" class="input seller-auth-input" placeholder="주소를 입력해 주세요" autocomplete="street-address" readonly>
              <p class="check-message error seller-auth-error hidden" id="shipAddr1Error"></p>
            </div>

            <div class="seller-auth-field">
              <label class="seller-auth-label" for="shipAddr2">출고지 상세주소 <span class="seller-auth-required" aria-hidden="true">*</span></label>
              <input type="text" id="shipAddr2" name="shipAddr2" class="input seller-auth-input" placeholder="상세주소를 입력해 주세요" autocomplete="address-line2">
              <p class="check-message error seller-auth-error hidden" id="shipAddr2Error"></p>
            </div>

            <div class="seller-auth-divider" role="presentation" aria-hidden="true"></div>

            <div class="seller-auth-check-row">
              <label class="seller-auth-check seller-auth-check--strong">
                <input type="checkbox" id="sameReturnAddr">
                <span>반품지는 출고지와 같아요</span>
              </label>
            </div>

            <h3 class="seller-auth-subtitle">반품지</h3>

            <div class="seller-auth-field seller-auth-field--zip">
              <label class="seller-auth-label" for="returnZip">반품지 우편번호 <span class="seller-auth-required" aria-hidden="true">*</span></label>
              <div class="seller-auth-inline-row">
                <input type="text" id="returnZip" name="returnZip" class="input seller-auth-input" placeholder="우편번호" inputmode="numeric" autocomplete="postal-code" readonly>
                <button type="button" class="seller-auth-mini-btn" id="returnZipBtn">우편번호 찾기</button>
              </div>
              <p class="check-message error seller-auth-error hidden" id="returnZipError"></p>
            </div>

            <div class="seller-auth-field">
              <label class="seller-auth-label" for="returnAddr1">반품지 주소 <span class="seller-auth-required" aria-hidden="true">*</span></label>
              <input type="text" id="returnAddr1" name="returnAddr1" class="input seller-auth-input" placeholder="주소를 입력해 주세요" autocomplete="street-address" readonly>
              <p class="check-message error seller-auth-error hidden" id="returnAddr1Error"></p>
            </div>

            <div class="seller-auth-field">
              <label class="seller-auth-label" for="returnAddr2">반품지 상세주소 <span class="seller-auth-required" aria-hidden="true">*</span></label>
              <input type="text" id="returnAddr2" name="returnAddr2" class="input seller-auth-input" placeholder="상세주소를 입력해 주세요" autocomplete="address-line2">
              <p class="check-message error seller-auth-error hidden" id="returnAddr2Error"></p>
            </div>
          </section>

          <!-- 섹션 4. 약관 동의 -->
          <section class="seller-auth-section seller-auth-section--terms" aria-labelledby="sellerSignupSectionTerms">
            <h2 class="seller-auth-section-title" id="sellerSignupSectionTerms">약관 동의</h2>

            <div class="seller-auth-terms-box" role="group" aria-label="약관 동의">
              <label class="seller-auth-check seller-auth-check--term seller-auth-check--all">
                <input type="checkbox" id="termsAll">
                <span>전체 동의</span>
              </label>
              <div class="seller-auth-terms-divider" aria-hidden="true"></div>

              <label class="seller-auth-check seller-auth-check--term">
                <input type="checkbox" class="terms-item terms-required" id="termsService">
                <span>이용약관 동의(필수)</span>
              </label>
              <label class="seller-auth-check seller-auth-check--term">
                <input type="checkbox" class="terms-item terms-required" id="termsPrivacy">
                <span>개인정보 수집 및 이용 동의(필수)</span>
              </label>
              <label class="seller-auth-check seller-auth-check--term">
                <input type="checkbox" class="terms-item terms-required" id="termsPolicy">
                <span>배송/정산 정책 동의(필수)</span>
              </label>
              <label class="seller-auth-check seller-auth-check--term">
                <input type="checkbox" class="terms-item" id="termsMarketing">
                <span>이벤트/마케팅 수신 동의(선택)</span>
              </label>
            </div>

            <p class="check-message error seller-auth-error hidden" id="termsError"></p>
          </section>

          <div class="seller-auth-actions">
            <p class="check-message error seller-auth-error--form ${empty signupError and empty sessionScope.signupError ? 'hidden' : ''}" 
               id="sellerSignupError" style="color: red; margin-bottom: 12px; ${(not empty signupError or not empty sessionScope.signupError) ? 'display: block;' : ''}">
              ${not empty sessionScope.signupError ? sessionScope.signupError : signupError}
              <c:remove var="signupError" scope="session" />
            </p>
            
            <button type="submit" class="seller-auth-btn seller-auth-btn--primary" id="sellerSignupBtn">가입하기</button>
            <a href="${pageContext.request.contextPath}/seller/auth/login" class="seller-auth-btn seller-auth-btn--ghost">로그인으로</a>
          </div>

          <p class="seller-auth-footer-text">
            이미 계정이 있으신가요?
            <a class="seller-auth-link seller-auth-link--inline" href="${pageContext.request.contextPath}/seller/auth/login">로그인</a>
          </p>
        </form>
      </section>
    </main>
  </div>

  <script src="${pageContext.request.contextPath}/js/seller/signup.js"></script>
</body>
</html>