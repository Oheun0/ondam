<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>온담 - 아이디 찾기 결과</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css">
</head>

<body>
	<div class="auth-page">
		<div class="auth-wrap">
			<div class="auth-card" style="text-align: center;">
				<h1 class="page-title">아이디를 찾았습니다!</h1>
				<p class="page-desc">입력하신 정보와 일치하는 아이디입니다.</p>

				<div style="background: #f8f9fa; padding: 30px; border-radius: 12px; margin: 25px 0; border: 1px dashed #ddd;">
					<span style="font-size: 1.2rem; color: #333; font-weight: 600;">아이디 : </span>
					<span style="font-size: 1.5rem; color: #2c3e50; font-weight: 800; letter-spacing: 1px;">
						${foundId}
					</span>
				</div>

				<div class="btn-row" style="flex-direction: column; gap: 10px;">
					<a href="${pageContext.request.contextPath}/login" class="btn btn-primary" style="width: 100%; text-decoration: none; display: flex; align-items: center; justify-content: center;">
						로그인하러 가기
					</a>
				</div>

				<div class="auth-link-wrap" style="margin-top: 25px;">
					<span style="color: #888; font-size: 0.9rem;">비밀번호가 기억나지 않으세요?</span><br>
					<a href="${pageContext.request.contextPath}/find-pwd" class="auth-link" style="margin-top: 8px; display: inline-block;">비밀번호 재설정하기</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>