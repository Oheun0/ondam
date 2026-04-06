<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="poke-modal-dim hidden" id="pokeModalDim"></div>

<div class="poke-modal hidden" id="pokeModal">
  <div class="poke-modal-card">
    
    <h2 class="poke-modal-title">누구에게 알려드릴까요?</h2>

    <div class="poke-person-list">
      <button type="button" class="poke-person-btn" data-name="김남준">
        김남준님에게 조르기
      </button>

      <button type="button" class="poke-person-btn" data-name="김가빈">
        김가빈님에게 조르기
      </button>

      <button type="button" class="poke-person-btn" data-name="김지현">
        김지현님에게 조르기
      </button>
    </div>

    <div class="poke-modal-bottom">
      <button type="button" class="poke-bottom-btn cancel" id="closePokeModalBtn">
        취소
      </button>

      <button type="button" class="poke-bottom-btn confirm" id="confirmPokeBtn">
        조르기
      </button>
    </div>

  </div>
</div>