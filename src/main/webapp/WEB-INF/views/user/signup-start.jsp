<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>온담 회원가입</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/auth.css">

</head>
<body>
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
  </div>
</body>
</html>