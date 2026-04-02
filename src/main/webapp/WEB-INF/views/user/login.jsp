<%@ page contentType="text/html; charset=UTF-8" %>
<% request.setAttribute("bottomNav", "home"); %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>온담 로그인</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
</head>
<body>
<div class="app-shell">
  <div class="auth-page">
    <div class="auth-wrap">

      <div class="brand-box">
        <div class="brand-logo">
		  <img src="<%=request.getContextPath()%>/images/logo.svg" alt="온담 로고">
		</div>
        <h1 class="brand-title">온담 시작하기</h1>
        <p class="brand-desc">편하게 보고, 쉽게 고르는 옷</p>
      </div>

      <div class="auth-card">
        <button type="button" class="btn btn-kakao"
                onclick="location.href='${pageContext.request.contextPath}/kakao-login'">
          <img src="<%=request.getContextPath()%>/images/kakao.png" 
		       alt="카카오 로고" 
		       class="kakao-icon">
		
		  <span>카카오톡 로그인</span>
        </button>

        <div class="divider">또는</div>

        <form action="${pageContext.request.contextPath}/login" method="post">
          <div class="form-group">
            <label class="form-label" for="userId">아이디</label>
            <input type="text" id="userId" name="userId" class="input" placeholder="아이디를 입력하세요">
          </div>

          <div class="form-group">
            <label class="form-label" for="userPwd">비밀번호</label>
            <input type="password" id="userPwd" name="userPwd" class="input" placeholder="비밀번호를 입력하세요">
          </div>

          <button type="submit" class="btn btn-primary">로그인</button>
        </form>

        <div class="quick-links">
          <a href="${pageContext.request.contextPath}/find-id">아이디 찾기</a>
          <span>|</span>
         <a href="${pageContext.request.contextPath}/find-pwd">비밀번호 찾기</a>
          <span>|</span>
          <a href="${pageContext.request.contextPath}/signup-start">회원가입</a>
        </div>
      </div>

      <div class="info-box">
        지인과 함께 쇼핑하는 즐거움을 느껴보세요!<br>
        카카오로 시작하면 더 빠르게 이용할 수 있어요.
      </div></div>
    </div><jsp:include page="../layout/bottomNav.jsp" />
  </div> <script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>
</body>
</html>