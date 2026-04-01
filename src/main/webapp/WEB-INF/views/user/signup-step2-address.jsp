<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>회원가입 - 배송 정보</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css">
  <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
  <script> const ctxPath = "${pageContext.request.contextPath}";</script>
  <script defer src="${pageContext.request.contextPath}/js/auth.js?ver=5"></script>
</head>
<body>
  <div class="auth-page">
    <div class="auth-wrap">

      <div class="auth-card">
        <span class="page-step">2 / 3</span>
        <h1 class="page-title">받으실 주소를 알려주세요</h1>
        <p class="page-desc">상품을 받을 주소를 입력해주세요</p>

        <form action="${pageContext.request.contextPath}/signup-step2-address" method="post" onsubmit="return validate();">

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

          <div class="form-group">
            <label class="form-label" for="addressName">배송지 이름</label>
            <input type="text" id="addressName" name="addressName" class="input" value="우리 집">
            <span class="error-msg" id="err-addressName"></span>
          </div>

          <div class="form-group">
            <label class="form-label" for="receiverName">받는 분 이름</label>
            <input type="text" id="receiverName" name="receiverName" class="input" placeholder="받는 분 이름을 입력하세요">
            <span class="error-msg" id="err-receiverName"></span>
          </div>

          <div class="form-group">
            <label class="form-label" for="receiverTel">연락처</label>
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

          <div class="form-group">
            <label class="form-label" for="userZipcode">우편번호</label>
            <div class="input-row">
              <input type="text" id="userZipcode" name="userZipcode" class="input" placeholder="우편번호" readonly>
              <button type="button" class="btn-check" onclick="openPostcode()">우편번호 조회</button>
            </div>
            <span class="error-msg" id="err-userZipcode"></span>
          </div>

          <div class="form-group">
		  	<label class="form-label">주소</label>
		  	<input type="text" id="userAddress" name="userAddress" class="input" placeholder="주소를 입력하세요" readonly>	
		  	<input type="text" id="userDetailAddress" name="userDetailAddress"
		         class="input" placeholder="상세 주소를 입력하세요" style="margin-top: 8px;">
            <span class="error-msg" id="err-userAddress"></span>
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