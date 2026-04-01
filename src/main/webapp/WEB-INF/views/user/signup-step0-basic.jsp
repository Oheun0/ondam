<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>회원가입 - 기본 정보</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css">
<script defer src="${pageContext.request.contextPath}/js/auth.js"></script>
</head>

<body>
	<div class="auth-page">
		<div class="auth-wrap">

			<div class="auth-card">
				<span class="page-step">1 / 3</span>
				<h1 class="page-title">기본 정보를 알려주세요</h1>
				<p class="page-desc">간단한 정보부터 차근차근 입력해볼게요</p>

				<form action="${pageContext.request.contextPath}/signup-step0-basic"
					method="post">

					<!-- 닉네임 -->
					<div class="form-group">
						<label class="form-label">닉네임</label> <input type="text"
							name="userNick" class="input" placeholder="부를 이름을 입력하세요">
					</div>
					
					<!-- 휴대폰 -->
					<div class="form-group">
						<label class="form-label">휴대폰 번호</label>

						<div class="input-row">
							<select class="select" style="max-width: 110px;" name="phone1">
								<option selected>010</option>
								<option>011</option>
								<option>016</option>
								<option>017</option>
								<option>018</option>
								<option>019</option>
							</select> <input type="text" name="phone2" class="input"
								placeholder="1234"> <input type="text" name="phone3"
								class="input" placeholder="5678">
						</div>
					</div>

					<!-- 이메일 -->
					<div class="form-group">
						<label class="form-label">이메일</label>

						<div class="input-row">
							<input type="text" name="email1" class="input"
								placeholder="ondam"> <span
								style="display: flex; align-items: center;">@</span> <select
								class="select" name="email2" id="emailSelect">
								<option value="">선택하기</option>
								<option value="naver.com">naver.com</option>
								<option value="gmail.com">gmail.com</option>
								<option value="daum.net">daum.net</option>
								<option value="kakao.com">kakao.com</option>
								<option value="nate.com">nate.com</option>
							</select>
						</div>
					</div>

					<!-- 생년월일 -->
					<div class="form-group">
						<label class="form-label">생년월일</label>

						<div class="input-row">
							<select class="select" name="birthYear">
								<option>년도</option>
								<%
								for (int i = 2024; i >= 1930; i--) {
								%>
								<option value="<%=i%>"><%=i%></option>
								<%
								}
								%>
							</select> <select class="select" name="birthMonth">
								<option>월</option>
								<%
								for (int i = 1; i <= 12; i++) {
								%>
								<option value="<%=i%>"><%=i%></option>
								<%
								}
								%>
							</select> <select class="select" name="birthDay">
								<option>일</option>
								<%
								for (int i = 1; i <= 31; i++) {
								%>
								<option value="<%=i%>"><%=i%></option>
								<%
								}
								%>
							</select>
						</div>
					</div>

					<!-- 성별 -->
					<div class="form-group">
						<label class="form-label">성별</label>

						<div class="option-grid">
							<label class="option-card active"> <input type="radio"
								name="userGender" value="0" checked> 선택 안 함
							</label> <label class="option-card"> <input type="radio"
								name="userGender" value="1"> 여성
							</label> <label class="option-card"> <input type="radio"
								name="userGender" value="2"> 남성
							</label>
						</div>
					</div>

					<!-- 가입 이유 -->
					<div class="form-group">
						<label class="form-label">온담을 사용하는 이유</label>

						<div class="option-grid">

							<label class="option-card active"> <input type="radio"
								name="joinReason" value="1" checked> 내 옷을 직접 고르려고요
							</label> <label class="option-card"> <input type="radio"
								name="joinReason" value="2"> 가족 선물을 고르려고요
							</label> <label class="option-card"> <input type="radio"
								name="joinReason" value="3"> 편하게 옷을 보고 싶어요
							</label> <label class="option-card"> <input type="radio"
								name="joinReason" value="4"> 추천을 받아보고 싶어요
							</label>

						</div>
					</div>

					<!-- 버튼 -->
					<div class="btn-row">
						<button type="button" class="btn btn-outline"
							onclick="history.back()">이전</button>
						<button type="submit" class="btn btn-primary">다음</button>
					</div>

				</form>
			</div>

		</div>
	</div>
</body>
</html>