<%@ page contentType="text/html; charset=UTF-8"%>
<% request.setAttribute("bottomNav", "home"); %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>온담 - 아이디 찾기</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">

<script> const ctxPath = "${pageContext.request.contextPath}";</script>
<script defer src="${pageContext.request.contextPath}/js/auth.js?ver=7"></script>
</head>

<body>
	<div class="app-shell">
		
		<div class="auth-page">
			<div class="auth-wrap">
				<div class="auth-card">
					<h1 class="page-title">아이디 찾기</h1>
					<p class="page-desc">가입할 때 입력한 이름과 번호를 알려주세요</p>
					
					<%String errorMsg = (String)request.getAttribute("errorMessage");
				    if(errorMsg != null) {%>
				    <div class="error-alert">
				        <%= errorMsg %>
				    </div>
				    <% } %>

					<form action="${pageContext.request.contextPath}/find-id" method="post" onsubmit="return validate();">
						<div class="form-group">
							<label class="form-label">이름</label> 
							<input type="text" name="userName" id="userName" class="input" placeholder="이름을 입력하세요">
							<span class="error-msg" id="err-userName"></span>
						</div>
						
						<div class="form-group">
							<label class="form-label">휴대폰 번호</label>
							<div class="input-row">
								<select class="select" style="max-width: 110px;" name="phone1" id="phone1">
									<option selected>010</option>
									<option>011</option>
									<option>016</option>
									<option>017</option>
									<option>018</option>
									<option>019</option>
								</select>
								<input type="text" name="phone2" id="phone2" class="input" placeholder="1234">
								<input type="text" name="phone3" id="phone3" class="input" placeholder="5678">
							</div>
							<span class="error-msg" id="err-phone"></span>
						</div>

						<div class="btn-row" style="margin-top: 30px;">
							<button type="button" class="btn btn-outline" onclick="history.back()">이전</button>
							<button type="submit" class="btn btn-primary">아이디 찾기</button>
						</div>

						<div class="auth-link-wrap" style="margin-top: 20px; text-align: center;">
							<a href="${pageContext.request.contextPath}/find-pwd" class="auth-link">비밀번호를 잊으셨나요?</a>
						</div>
					</form>
				</div>
			</div>
		</div> <jsp:include page="../layout/bottomNav.jsp" />
		
	</div> <script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>
</body>
</html>