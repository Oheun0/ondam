<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="poke-modal-dim hidden" id="giftModalDim"></div>

<div class="poke-modal hidden" id="giftModal">
  <div class="poke-modal-card">

    <h2 class="poke-modal-title">누구에게 선물할까요?</h2>

    <div class="poke-person-list">
      <button type="button" class="gift-person-btn" data-name="김남준">
        김남준님에게 선물하기
      </button>

      <button type="button" class="gift-person-btn" data-name="김가빈">
        김가빈님에게 선물하기
      </button>

      <button type="button" class="gift-person-btn" data-name="김지현">
        김지현님에게 선물하기
      </button>
    </div>

    <div class="poke-modal-bottom">
      <button type="button" class="poke-bottom-btn cancel" id="closeGiftModalBtn">
        취소
      </button>

      <button type="button" class="poke-bottom-btn confirm" id="confirmGiftBtn">
        선물하기
      </button>
    </div>

  </div>
</div>
