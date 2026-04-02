<%@ page contentType="text/html; charset=UTF-8" %>
<%
    // 인증 페이지에서는 보통 하단 메뉴에 불을 켜지 않거나, 'home'에 켭니다.
    request.setAttribute("bottomNav", "home"); 
%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>온담 회원가입</title>
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
          
        <div class="auth-card">
          <span class="page-step">시작하기</span>
          <h1 class="page-title">처음 한 번만 알려주세요</h1>
          <p class="page-desc">
            처음 한 번만 입력해두면 <br> 다음부터는 더 편하게 이용할 수 있어요.
          </p>

          <div class="btn-row">
            <button type="button" class="btn btn-kakao"
                    onclick="location.href='${pageContext.request.contextPath}/kakao-login'">
                    <img src="${pageContext.request.contextPath}/images/kakao.png" 
                         alt="카카오 로고" 
                         class="kakao-icon">
                  <span>카카오로 빠르게 시작하기</span>
            </button>
          </div>

          <div class="divider">또는</div>

          <button type="button" class="btn btn-primary"
                  onclick="location.href='${pageContext.request.contextPath}/signup-step1-basic'">
            일반 회원가입
          </button>

          <div class="quick-links">
            <a href="${pageContext.request.contextPath}/login">이미 계정이 있어요</a>
          </div>
        </div>
      </div>
    </div> <jsp:include page="../layout/bottomNav.jsp" />

  </div> <script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>
</body>
</html>