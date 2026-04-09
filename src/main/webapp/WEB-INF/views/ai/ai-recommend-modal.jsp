<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 1번 모달: 담이 인사 -->
<div class="inquiry-write-modal ai-rec-intro-modal" id="aiRecModalIntro" role="dialog" aria-modal="true" aria-labelledby="aiRecModalIntroTitle">
  <div class="inquiry-write-dim" aria-hidden="true"></div>
  <div class="inquiry-write-modal-card ai-rec-modal-card--intro">
    <img src="${pageContext.request.contextPath}/images/character/dami-default.png"
         alt=""
         class="ai-rec-modal-hero-img"
         width="280"
         height="280"
         decoding="async">
    <p class="inquiry-write-modal-message ai-rec-modal-intro-text" id="aiRecModalIntroTitle">
      <!-- 하드코딩 제거: 세션에서 로그인한 유저 이름을 가져옵니다 -->
      <span style="color: #ff6b6b; font-weight: bold;">${sessionScope.loginUser.userName}</span>님에게 어울리는 옷을<br>
      담이가 골라드릴게요
    </p>
    <div class="inquiry-write-modal-actions inquiry-write-modal-actions--single">
      <button type="button" class="inquiry-write-modal-btn inquiry-write-modal-btn--primary ai-rec-modal-intro-cta" id="aiRecModalIntroBtn">
        맞춤 상품 추천받기
      </button>
    </div>
  </div>
</div>

<!-- 2번 모달: 진짜 로딩 화면으로 쓸 마법 모달 -->
<div class="inquiry-write-modal ai-rec-loading-modal hidden" id="aiRecModalLoading" role="dialog" aria-modal="true" aria-live="polite" aria-labelledby="aiRecModalLoadingTitle">
  <div class="inquiry-write-dim" aria-hidden="true"></div>
  <div class="inquiry-write-modal-card ai-rec-modal-card--loading">
    <img src="${pageContext.request.contextPath}/images/character/dami-magic.png"
         alt=""
         class="ai-rec-modal-magic-img"
         width="240"
         height="240"
         decoding="async"
         id="aiRecModalMagicImg">
    <p class="inquiry-write-modal-message ai-rec-modal-loading-text" id="aiRecModalLoadingTitle">
      담이가 어울리는 옷을 찾고 있어요 ...<br>
      <span style="font-size: 13px; color: #888; font-weight: normal; margin-top: 5px; display: inline-block;">(AI 모델이 실시간으로 분석 중입니다✨)</span>
    </p>
  </div>
</div>