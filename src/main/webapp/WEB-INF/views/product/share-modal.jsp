<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="poke-modal-dim hidden" id="shareModalDim"></div>

<div class="poke-modal hidden" id="shareModal">
  <div class="poke-modal-card share-modal-card">
    <h2 class="poke-modal-title share-modal-title">공유하기</h2>

    <div class="share-modal-options">
      <button type="button" class="share-option-btn" id="shareKakaoBtn">
        <span class="share-option-icon-wrap" aria-hidden="true">
          <img src="${pageContext.request.contextPath}/images/kakao.png" alt="" class="share-option-icon share-option-icon--kakao" width="24" height="24" />
        </span>
        <span class="share-option-label">카카오톡으로 공유하기</span>
      </button>

      <button type="button" class="share-option-btn" id="shareCopyLinkBtn">
        <span class="share-option-icon-wrap" aria-hidden="true">
          <span class="material-icons-outlined share-option-icon">content_copy</span>
        </span>
        <span class="share-option-label">링크 복사하기</span>
      </button>
		
	  <!-- 더보기 모바일로 선택 시 모바일 전용 공유창 -->
      <button type="button" class="share-option-btn" id="shareMoreBtn">
        <span class="share-option-icon-wrap" aria-hidden="true">
          <span class="material-icons-outlined share-option-icon">more_horiz</span>
        </span>
        <span class="share-option-label">더보기</span>
      </button>
    </div>
  </div>
</div>
