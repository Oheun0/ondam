<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>회원가입 완료</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/auth.css">
</head>
<body>
  <div class="auth-page">
    <div class="auth-wrap">
		
      <div class="auth-card" style="text-align:center;">
        <div class="brand-logo">
		  <img src="<%=request.getContextPath()%>/images/logo.svg" alt="온담 로고">
		</div>
        <h1 class="page-title">이제 모든 준비가 끝났어요</h1>
        <p class="page-desc">
          편하게 둘러보시고 <br> 마음에 드는 옷을 골라보세요!
        </p>

        <div class="btn-row">
          <button type="button" class="btn btn-primary"
                  onclick="location.href='${pageContext.request.contextPath}/main'">
            온담 시작하기
          </button>
        </div>
      </div>

    </div>
  </div>
</body>
</html>