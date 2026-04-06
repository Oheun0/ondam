<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="review-my-written-panel">
  <div class="review-my-intro review-my-intro--written">
    <p class="review-my-intro-line">총 2개의 리뷰를 작성했어요!</p>
  </div>

  <div class="review-my-written-list">
    <article class="review-my-written-card" data-review-my-card="1">
      <div class="review-my-written-top">
        <div class="review-my-written-top__left">
          <div class="review-my-written-thumb-wrap">
            <img src="${pageContext.request.contextPath}/images/category/type-top-knit.jpg" alt="" class="review-my-written-thumb" width="72" height="72" loading="lazy"/>
          </div>
          <div class="review-my-written-meta">
            <p class="review-my-written-product">부드러운 라운드 니트 가디건</p>
            <p class="review-my-written-option">노란색 / 90</p>
          </div>
        </div>
        <div class="review-my-more-wrap">
          <button type="button" class="review-my-more-btn" aria-label="더보기 메뉴" aria-haspopup="true" aria-expanded="false" id="reviewMyMoreBtn1">
            <span class="material-icons" aria-hidden="true">more_horiz</span>
          </button>
          <div class="review-my-dropdown hidden" id="reviewMyDropdown1" role="menu" aria-hidden="true">
            <button type="button" class="review-my-dropdown__item" role="menuitem">수정하기</button>
            <button type="button" class="review-my-dropdown__item review-my-dropdown__item--danger" role="menuitem">삭제하기</button>
          </div>
        </div>
      </div>

      <div class="review-my-written-divider" aria-hidden="true"></div>

      <div class="review-my-written-review">
        <div class="review-my-written-review-head">
          <div class="detail-review-stars-row review-my-written-stars" aria-label="별점 5점">
            <span class="detail-review-stars" aria-hidden="true">
              <span class="material-icons detail-review-star detail-review-star--full">star</span>
              <span class="material-icons detail-review-star detail-review-star--full">star</span>
              <span class="material-icons detail-review-star detail-review-star--full">star</span>
              <span class="material-icons detail-review-star detail-review-star--full">star</span>
              <span class="material-icons detail-review-star detail-review-star--full">star</span>
            </span>
            <span class="detail-review-rating-num">5</span>
          </div>
          <time class="review-my-written-date" datetime="2025-03-12">2025.03.12</time>
        </div>
        <p class="review-my-written-body">
          부드럽고 핏이 넉넉해서 집에서 자주 입어요. 색도 사진과 비슷하고 배송도 빨랐습니다.
          여러 줄로 작성한 후기도 전체가 보이도록 표시합니다.
        </p>
      </div>
    </article>

    <article class="review-my-written-card" data-review-my-card="2">
      <div class="review-my-written-top">
        <div class="review-my-written-top__left">
          <div class="review-my-written-thumb-wrap">
            <img src="${pageContext.request.contextPath}/images/category/comfort-loose.jpg" alt="" class="review-my-written-thumb" width="72" height="72" loading="lazy"/>
          </div>
          <div class="review-my-written-meta">
            <p class="review-my-written-product">편하게 입는 면 혼방 긴팔 티셔츠</p>
            <p class="review-my-written-option">베이지 / 100</p>
          </div>
        </div>
        <div class="review-my-more-wrap">
          <button type="button" class="review-my-more-btn" aria-label="더보기 메뉴" aria-haspopup="true" aria-expanded="false" id="reviewMyMoreBtn2">
            <span class="material-icons" aria-hidden="true">more_horiz</span>
          </button>
          <div class="review-my-dropdown hidden" id="reviewMyDropdown2" role="menu" aria-hidden="true">
            <button type="button" class="review-my-dropdown__item" role="menuitem">수정하기</button>
            <button type="button" class="review-my-dropdown__item review-my-dropdown__item--danger" role="menuitem">삭제하기</button>
          </div>
        </div>
      </div>

      <div class="review-my-written-divider" aria-hidden="true"></div>

      <div class="review-my-written-review">
        <div class="review-my-written-review-head">
          <div class="detail-review-stars-row review-my-written-stars" aria-label="별점 4점">
            <span class="detail-review-stars" aria-hidden="true">
              <span class="material-icons detail-review-star detail-review-star--full">star</span>
              <span class="material-icons detail-review-star detail-review-star--full">star</span>
              <span class="material-icons detail-review-star detail-review-star--full">star</span>
              <span class="material-icons detail-review-star detail-review-star--full">star</span>
              <span class="material-icons detail-review-star detail-review-star--empty">star</span>
            </span>
            <span class="detail-review-rating-num">4</span>
          </div>
          <time class="review-my-written-date" datetime="2025-03-08">2025.03.08</time>
        </div>
        <p class="review-my-written-body">
          세탁 후에도 늘어남이 적고 부모님께 선물드리기 좋았어요.
        </p>
      </div>
    </article>
  </div>

  <%--
  작성한 후기 없음 (필요 시 아래 주석 해제, 위 review-my-written-list 는 숨기거나 제거)
  <div class="review-my-empty" role="status">
    <p class="review-my-empty__title">아직 작성한 후기가 없어요</p>
    <p class="review-my-empty__sub">첫 후기를 남겨보세요</p>
  </div>
  --%>
</div>
