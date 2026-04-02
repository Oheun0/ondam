<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>온담 - 비밀번호 찾기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css">
<script>const ctxPath = "${pageContext.request.contextPath}";</script>
<script defer src="${pageContext.request.contextPath}/js/auth.js?ver=8"></script>
</head>
<body>
	<div class="auth-page">
		<div class="auth-wrap">
			<div class="auth-card">
				<h1 class="page-title">비밀번호 찾기</h1>
				<p class="page-desc">비밀번호를 재설정하기 위해 정보를 입력해주세요</p>

				<!-- 에러 알림창 -->
				<% String errorMsg = (String)request.getAttribute("errorMessage");
				   if(errorMsg != null) { %>
					<div class="error-alert"><%= errorMsg %></div>
				<% } %>

				<form action="${pageContext.request.contextPath}/find-pwd" method="post" onsubmit="return validate();">
					<div class="form-group">
						<label class="form-label">아이디</label>
						<input type="text" name="userId" id="userId" class="input" placeholder="아이디를 입력하세요">
						<span class="error-msg" id="err-userId"></span>
					</div>

					<div class="form-group">
						<label class="form-label">이름</label>
						<input type="text" name="userName" id="userName" class="input" placeholder="이름을 입력하세요">
						<span class="error-msg" id="err-userName"></span>
					</div>

					<div class="form-group">
						<label class="form-label">휴대폰 번호</label>
						<div class="input-row">
							<input type="text" name="phone1" id="phone1" class="input" value="010" style="max-width: 80px;">
							<input type="text" name="phone2" id="phone2" class="input" placeholder="1234">
							<input type="text" name="phone3" id="phone3" class="input" placeholder="5678">
						</div>
						<span class="error-msg" id="err-phone"></span>
					</div>

					<div class="btn-row" style="margin-top: 30px;">
						<button type="button" class="btn btn-outline" onclick="history.back()">이전</button>
						<button type="submit" class="btn btn-primary">다음</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>