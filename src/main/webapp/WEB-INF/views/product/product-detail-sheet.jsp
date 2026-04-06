<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="detail-sheet-dim hidden" id="detailSheetDim"></div>

<div class="detail-sheet hidden" id="detailOptionSheet">
  <div class="detail-sheet-top">
    <div class="detail-sheet-handle"></div>
    <div class="detail-sheet-notice" role="note">
      <span class="material-symbols-outlined detail-sheet-notice__icon" aria-hidden="true">local_activity</span>
      <span class="detail-sheet-notice__text">장바구니에서 추가 할인 쿠폰을 확인해보세요.</span>
    </div>
  </div>

  <div class="detail-sheet-stage">
    <div class="detail-sheet-scroll">
      <div class="detail-sheet-section">
        <button type="button" class="detail-option-toggle" id="colorToggleBtn" aria-expanded="false" aria-controls="colorOptionPanel">
          <span>색상</span>
          <span class="detail-selected-value detail-selected-value--placeholder" id="selectedColorText">눌러서 선택하기</span>
          <span class="material-icons detail-option-toggle__chev" aria-hidden="true">expand_more</span>
        </button>
        <div class="detail-option-panel detail-option-panel--sheet hidden" id="colorOptionPanel" role="region" aria-label="색상 목록">
          <div class="detail-option-panel__scroller">
            <div class="detail-option-list" role="listbox" aria-label="색상">
              <button type="button" class="detail-option-row" data-color="검정" role="option" aria-selected="false">검정</button>
              <button type="button" class="detail-option-row" data-color="베이지" role="option" aria-selected="false">베이지</button>
              <button type="button" class="detail-option-row" data-color="하늘색" role="option" aria-selected="false">하늘색</button>
              <button type="button" class="detail-option-row" data-color="아이보리" role="option" aria-selected="false">아이보리</button>
              <button type="button" class="detail-option-row" data-color="네이비" role="option" aria-selected="false">네이비</button>
              <button type="button" class="detail-option-row" data-color="브라운" role="option" aria-selected="false">브라운</button>
              <button type="button" class="detail-option-row" data-color="그레이" role="option" aria-selected="false">그레이</button>
            </div>
          </div>
        </div>
      </div>

      <div class="detail-sheet-section">
        <button type="button" class="detail-option-toggle" id="sizeToggleBtn" aria-expanded="false" aria-controls="sizeOptionPanel">
          <span>사이즈</span>
          <span class="detail-selected-value detail-selected-value--placeholder" id="selectedSizeText">눌러서 선택하기</span>
          <span class="material-icons detail-option-toggle__chev" aria-hidden="true">expand_more</span>
        </button>
        <div class="detail-option-panel detail-option-panel--sheet hidden" id="sizeOptionPanel" role="region" aria-label="사이즈 목록">
          <div class="detail-option-panel__scroller">
            <div class="detail-option-list" role="listbox" aria-label="사이즈">
              <button type="button" class="detail-option-row" data-size="95" role="option" aria-selected="false">95</button>
              <button type="button" class="detail-option-row" data-size="100" role="option" aria-selected="false">100</button>
              <button type="button" class="detail-option-row" data-size="105" role="option" aria-selected="false">105</button>
              <button type="button" class="detail-option-row" data-size="110" role="option" aria-selected="false">110</button>
            </div>
          </div>
        </div>
      </div>

	  <!-- 조르기 모달: poke/poke-modal.jsp / 선물하기 모달: gift/gift-modal.jsp / 공유하기 모달: share-modal.jsp
	  조르기, 선물하기, 장바구니 담기, 구매하기는 모두 옵션 선택 필수! -->
      <div class="detail-action-grid">
        <button type="button" class="detail-action-item" id="sheetWishlistBtn" aria-pressed="false" aria-label="찜하기">
          <span class="detail-wish-icon material-icons-outlined" aria-hidden="true">favorite_border</span>
          <span>찜</span>
        </button>
        <button type="button" class="detail-action-item" id="openPokeFromSheetBtn">
          <span class="material-icons-outlined">volunteer_activism</span>
          <span>조르기</span>
        </button>
        <button type="button" class="detail-action-item" id="openGiftFromSheetBtn">
          <span class="material-icons-outlined">redeem</span>
          <span>선물하기</span>
        </button>
        <button type="button" class="detail-action-item" id="openShareFromSheetBtn">
          <span class="material-icons-outlined">share</span>
          <span>공유하기</span>
        </button>
      </div>

      <div class="detail-quantity-row detail-quantity-row--sheet">
        <span class="detail-quantity-label">수량</span>
        <div class="detail-quantity-box detail-qty-stepper">
          <button type="button" class="detail-qty-stepper__btn" id="minusQtyBtn" aria-label="수량 한 개 빼기" disabled>
            <span class="material-icons-outlined" aria-hidden="true">remove</span>
          </button>
          <span class="detail-qty-stepper__value" id="qtyValue">1</span>
          <button type="button" class="detail-qty-stepper__btn" id="plusQtyBtn" aria-label="수량 한 개 더하기">
            <span class="material-icons-outlined" aria-hidden="true">add</span>
          </button>
        </div>
      </div>

      <div class="detail-sheet-order-divider" role="presentation" aria-hidden="true"></div>
      <div class="detail-sheet-order-summary" id="detailSheetOrderSummary" data-unit-price="39000">
        <span class="detail-sheet-order-count" id="sheetOrderCount">총 1개</span>
        <span class="detail-sheet-order-total" id="sheetOrderTotal">39,000원</span>
      </div>
    </div>
  </div>

  <div class="detail-sheet-bottom">
    <button type="button" class="detail-sheet-btn secondary" id="sheetAddCartBtn">장바구니 담기</button>
    <button type="button" class="detail-sheet-btn primary" id="sheetBuyNowBtn">구매하기</button>
  </div>
</div>
