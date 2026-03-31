<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>회원가입 - 취향 설정</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/auth.css">
  <script defer src="<%=request.getContextPath()%>/js/auth.js"></script>
</head>
<body>
  <div class="auth-page">
    <div class="auth-wrap">

      <div class="auth-card">
        <span class="page-step">3 / 3</span>
        <h1 class="page-title">취향을 알려주세요</h1>
        <p class="page-desc">자주 찾는 옷을 기준으로 추천해드려요</p>

        <form action="<%=request.getContextPath()%>/user/signupStep3Proc.do" method="post">

          <h2 class="section-title">몸에 잘 맞는 옷을 추천해드릴게요</h2>
          <p class="section-desc">키와 몸무게를 선택해주세요</p>

          <div class="form-group">
            <label class="form-label" for="userHeight">키</label>
            <select id="userHeight" name="userHeight" class="select">
              <option value="">선택하세요</option>
              <option value="145">145cm 이하</option>
              <option value="150">146~150cm</option>
              <option value="155">151~155cm</option>
              <option value="160">156~160cm</option>
              <option value="165">161~165cm</option>
              <option value="170">166~170cm</option>
              <option value="175">171~175cm</option>
              <option value="180">176cm 이상</option>
            </select>
          </div>

          <div class="form-group">
            <label class="form-label" for="userWeight">몸무게</label>
            <select id="userWeight" name="userWeight" class="select">
              <option value="">선택하세요</option>
              <option value="45">45kg 이하</option>
              <option value="50">46~50kg</option>
              <option value="55">51~55kg</option>
              <option value="60">56~60kg</option>
              <option value="65">61~65kg</option>
              <option value="70">66~70kg</option>
              <option value="75">71~75kg</option>
              <option value="80">76~80kg</option>
              <option value="85">81~85kg</option>
              <option value="90">86kg 이상</option>
            </select>
          </div>

          <h2 class="section-title">좋아하는 색상을 골라주세요</h2>
          <p class="section-desc">최대 3개 정도 고르면 추천이 더 쉬워져요</p>

          <div class="chip-wrap">
            <label class="chip"><input type="checkbox" name="userPreferColor" value="검정색"><span><i class="color-dot" style="background:#111111;"></i>검정색</span></label>
            <label class="chip"><input type="checkbox" name="userPreferColor" value="흰색"><span><i class="color-dot" style="background:#ffffff;"></i>흰색</span></label>
            <label class="chip"><input type="checkbox" name="userPreferColor" value="회색"><span><i class="color-dot" style="background:#8b8f94;"></i>회색</span></label>
            <label class="chip"><input type="checkbox" name="userPreferColor" value="고동색"><span><i class="color-dot" style="background:#5a3b2e;"></i>고동색</span></label>
            <label class="chip"><input type="checkbox" name="userPreferColor" value="연갈색"><span><i class="color-dot" style="background:#b88a60;"></i>연갈색</span></label>
            <label class="chip"><input type="checkbox" name="userPreferColor" value="자주색"><span><i class="color-dot" style="background:#7b1f52;"></i>자주색</span></label>
            <label class="chip"><input type="checkbox" name="userPreferColor" value="빨강색"><span><i class="color-dot" style="background:#d73333;"></i>빨강색</span></label>
            <label class="chip"><input type="checkbox" name="userPreferColor" value="연분홍색"><span><i class="color-dot" style="background:#f5c7d3;"></i>연분홍색</span></label>
            <label class="chip"><input type="checkbox" name="userPreferColor" value="노란색"><span><i class="color-dot" style="background:#f2d348;"></i>노란색</span></label>
            <label class="chip"><input type="checkbox" name="userPreferColor" value="남색"><span><i class="color-dot" style="background:#203864;"></i>남색</span></label>
            <label class="chip"><input type="checkbox" name="userPreferColor" value="하늘색"><span><i class="color-dot" style="background:#86c8f2;"></i>하늘색</span></label>
            <label class="chip"><input type="checkbox" name="userPreferColor" value="국방색"><span><i class="color-dot" style="background:#556b2f;"></i>국방색</span></label>
          </div>

          <h2 class="section-title">즐겨 하는 활동을 알려주세요</h2>
          <p class="section-desc">평소 자주 하는 활동을 알면 더 맞는 옷을 추천할 수 있어요</p>

          <div class="chip-wrap">
            <label class="chip"><input type="checkbox" name="userHobby" value="수영"><span>수영</span></label>
            <label class="chip"><input type="checkbox" name="userHobby" value="등산"><span>등산</span></label>
            <label class="chip"><input type="checkbox" name="userHobby" value="산책"><span>산책</span></label>
            <label class="chip"><input type="checkbox" name="userHobby" value="헬스"><span>헬스</span></label>
            <label class="chip"><input type="checkbox" name="userHobby" value="요가"><span>요가</span></label>
            <label class="chip"><input type="checkbox" name="userHobby" value="골프"><span>골프</span></label>
            <label class="chip"><input type="checkbox" name="userHobby" value="자전거"><span>자전거</span></label>
            <label class="chip"><input type="checkbox" name="userHobby" value="여행"><span>여행</span></label>
            <label class="chip"><input type="checkbox" name="userHobby" value="낚시"><span>낚시</span></label>
            <label class="chip"><input type="checkbox" name="userHobby" value="원예"><span>원예</span></label>
            <label class="chip"><input type="checkbox" name="userHobby" value="텃밭 가꾸기"><span>텃밭 가꾸기</span></label>
            <label class="chip"><input type="checkbox" name="userHobby" value="가벼운 외출"><span>가벼운 외출</span></label>
          </div>

          <h2 class="section-title">결제는 어떻게 하고 싶으세요?</h2>
          <p class="section-desc">다음부터 더 간편하게 선택할 수 있어요</p>

          <div class="option-grid">
          	<label class="option-card">
              <input type="radio" name="preferPayment" value="3">
              함께 지갑
            </label>
            <label class="option-card">
              <input type="radio" name="preferPayment" value="1">
              카드 결제
            </label>
            <label class="option-card">
              <input type="radio" name="preferPayment" value="2">
              계좌이체
            </label>
            <label class="option-card">
              <input type="radio" name="preferPayment" value="0">
              아직 잘 모르겠어요
            </label>
          </div>

          <div class="btn-row">
            <button type="button" class="btn btn-outline" onclick="history.back()">이전</button>
            <button type="submit" class="btn btn-primary">가입 완료</button>
          </div>
        </form>
      </div>

    </div>
  </div>
</body>
</html>