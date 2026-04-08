<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--
  AI 추천 진입 인트로 모달 (inquiry-write 확인 모달과 동일 구조·클래스)
  1) 담이 인사 + 추천받기 버튼 → 2) 로딩(바운스) 2초 → 본문 표시
--%>

<!-- 1번 모달 -->
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
      성연수님에게 어울리는 옷을<br>
      담이가 골라드릴게요
    </p>
    <div class="inquiry-write-modal-actions inquiry-write-modal-actions--single">
      <button type="button" class="inquiry-write-modal-btn inquiry-write-modal-btn--primary ai-rec-modal-intro-cta" id="aiRecModalIntroBtn">
        오늘의 맞춤 상품 추천받기
      </button>
    </div>
  </div>
</div>

<!-- 2번 모달 (버튼 없음, 2초 후 자동으로 본문 표시) -->
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
      담이가 어울리는 옷을 찾고 있어요 ...
    </p>
  </div>
</div>
