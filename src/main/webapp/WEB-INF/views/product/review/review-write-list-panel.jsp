<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="review-my-write-panel">
  <div class="review-my-intro review-my-intro--write">
    <p class="review-my-intro-line">작성 가능한 후기가 총 3개 있어요</p>
    <p class="review-my-intro-sub">후기를 작성하면 할인 쿠폰을 드려요</p>
  </div>

  <div class="review-my-write-list">
    <article class="review-my-write-card">
      <div class="review-my-write-card__main">
        <div class="review-my-write-card__thumb-wrap">
          <img src="${pageContext.request.contextPath}/images/category/type-top-knit.jpg" alt="" class="review-my-write-card__thumb" width="96" height="96" loading="lazy"/>
        </div>
        <div class="review-my-write-card__info">
          <p class="review-my-write-brand">온담</p>
          <p class="review-my-write-name">부드러운 라운드 니트 가디건</p>
          <p class="review-my-write-option">노란색 / 90</p>
        </div>
      </div>
      <button type="button" class="review-my-write-btn">후기 작성하기</button>
    </article>

    <article class="review-my-write-card">
      <div class="review-my-write-card__main">
        <div class="review-my-write-card__thumb-wrap">
          <img src="${pageContext.request.contextPath}/images/category/comfort-loose.jpg" alt="" class="review-my-write-card__thumb" width="96" height="96" loading="lazy"/>
        </div>
        <div class="review-my-write-card__info">
          <p class="review-my-write-brand">시니어웨어</p>
          <p class="review-my-write-name">편하게 입는 면 혼방 긴팔 티셔츠</p>
          <p class="review-my-write-option">베이지 / 100</p>
        </div>
      </div>
      <button type="button" class="review-my-write-btn">후기 작성하기</button>
    </article>

    <article class="review-my-write-card">
      <div class="review-my-write-card__main">
        <div class="review-my-write-card__thumb-wrap">
          <img src="${pageContext.request.contextPath}/images/category/type-top-knit.jpg" alt="" class="review-my-write-card__thumb" width="96" height="96" loading="lazy"/>
        </div>
        <div class="review-my-write-card__info">
          <p class="review-my-write-brand">B라벨</p>
          <p class="review-my-write-name">가벼운 바람막이 점퍼 일이삼사오</p>
          <p class="review-my-write-option">네이비 / 105</p>
        </div>
      </div>
      <button type="button" class="review-my-write-btn">후기 작성하기</button>
    </article>
  </div>

  <%--
  작성 가능한 후기 없음 (필요 시 아래 주석 해제, 위 review-my-write-list 블록은 숨기거나 제거)
  <div class="review-my-empty" role="status">
    <p class="review-my-empty__title">작성할 수 있는 후기가 없어요</p>
    <p class="review-my-empty__sub">상품을 구매하면 이곳에서 후기를 남길 수 있어요</p>
  </div>
  --%>
</div>
