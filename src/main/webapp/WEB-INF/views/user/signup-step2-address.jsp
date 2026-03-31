<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>회원가입 - 배송 정보</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/auth.css">
</head>
<body>
  <div class="auth-page">
    <div class="auth-wrap">

      <div class="auth-card">
        <span class="page-step">2 / 3</span>
        <h1 class="page-title">받으실 주소를 알려주세요</h1>
        <p class="page-desc">상품을 받을 주소를 입력해주세요</p>

        <form action="<%=request.getContextPath()%>/user/signupStep2Proc.do" method="post">

          <!-- 상단 체크 옵션 -->
          <div class="form-group">
            <div class="chip-wrap top-check-wrap">
              <label class="chip">
                <input type="checkbox" name="isDefault" value="1" checked>
                <span>아래 주소를 기본 배송지로 저장하기</span>
              </label>

              <label class="chip">
                <input type="checkbox" name="loadPrevInfo" value="1" checked>
                <span>이전에 입력한 정보 불러오기</span>
              </label>
            </div>
          </div>

          <!-- 배송지 이름 -->
          <div class="form-group">
            <label class="form-label" for="addressName">배송지 이름</label>
            <input type="text" id="addressName" name="addressName" class="input" value="우리 집">
          </div>

          <!-- 받는 분 이름 -->
          <div class="form-group">
            <label class="form-label" for="receiverName">받는 분 이름</label>
            <input type="text" id="receiverName" name="receiverName" class="input" placeholder="받는 분 이름을 입력하세요">
          </div>

          <!-- 연락처 -->
          <div class="form-group">
            <label class="form-label" for="receiverTel">연락처</label>
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

          <!-- 우편번호 -->
          <div class="form-group">
            <label class="form-label" for="userZipcode">우편번호</label>
            <div class="input-row">
              <input type="text" id="userZipcode" name="userZipcode" class="input" placeholder="우편번호">
              <button type="button" class="btn-check">우편번호 조회</button>
            </div>
          </div>

          <!-- 기본 주소 및 상세 주소 -->
          <div class="form-group">
		  	<label class="form-label">주소</label>
		  	<input type="text" id="userAddress" name="userAddress" class="input" placeholder="주소를 입력하세요">	
		  	<input type="text" id="userDetailAddress" name="userDetailAddress"
		         class="input" placeholder="상세 주소를 입력하세요" style="margin-top: 8px;">
		  </div>

          <div class="btn-row">
            <button type="button" class="btn btn-outline" onclick="history.back()">이전</button>
            <button type="submit" class="btn btn-primary">다음</button>
          </div>

        </form>
      </div>

    </div>
  </div>
</body>
</html>