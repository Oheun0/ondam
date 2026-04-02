<%@ page contentType="text/html; charset=UTF-8"%>
<% request.setAttribute("bottomNav", "home"); %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>온담 - 아이디 찾기 결과</title>
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
				<header class="auth-top-header">
				    <button type="button" class="btn-back" onclick="history.back()" aria-label="뒤로가기">
				        <span class="material-icons">arrow_back_ios_new</span>
				    </button>
				</header>
				
				<div class="auth-card text-center">
					<h1 class="page-title">아이디를 찾았습니다!</h1>
					<p class="page-desc">입력하신 정보와 일치하는 아이디입니다.</p>

					<div class="result-box">
						<span class="result-label">아이디 : </span>
						<span class="result-value">${foundId}</span>
					</div>

					<div class="btn-row stacked">
						<a href="${pageContext.request.contextPath}/login" class="btn btn-primary btn-full">
							로그인하러 가기
						</a>
					</div>

					<div class="auth-link-wrap spaced">
						<span class="link-help-text">비밀번호가 기억나지 않으세요?</span><br>
						<a href="${pageContext.request.contextPath}/find-pwd" class="auth-link action-link">비밀번호 재설정하기</a>
					</div>
				</div>
			</div>
		</div>
		
		<jsp:include page="../layout/bottomNav.jsp" />
	</div>
	<script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>
</body>
</html>