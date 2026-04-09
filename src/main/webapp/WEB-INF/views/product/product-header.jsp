<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="product-header-section">
  <div class="product-topbar">
		<button type="button" class="detail-icon-btn" aria-label="뒤로가기"
		onclick="window.location.href = document.body.dataset.contextPath + '/category'">
		  <span class="material-icons">arrow_back_ios_new</span>
		</button>

		<div class="product-title-wrap">
      <button type="button" class="product-category-toggle" id="categoryToggleBtn">
        <span class="product-title-label" id="viewModeLabel">
          <c:choose>
            <c:when test="${currentViewMode == 'situation'}">상황으로 보기</c:when>
            <c:otherwise>종류로 보기</c:otherwise>
          </c:choose>
        </span>
        <span class="product-title-main">
          <span id="currentCategoryTitle">${currentCategoryName}</span>
          <span class="material-icons dropdown-arrow" id="categoryArrow">expand_more</span>
        </span>
      </button>
    </div>
  </div>

  <div class="category-panel hidden" id="categoryPanel">
    <div class="category-top-tabs product-category-panel-tabs" role="tablist" aria-label="카테고리 보기 방식">
      <button type="button"
              class="top-tab category-mode-tab <c:if test="${currentViewMode == 'situation'}">active</c:if>"
              data-view-mode="situation" id="productTabSituation"
              role="tab"
              aria-selected="${currentViewMode == 'situation'}"
              aria-controls="situationCategoryView">상황으로 보기</button>
      <button type="button"
              class="top-tab category-mode-tab <c:if test="${currentViewMode != 'situation'}">active</c:if>"
              data-view-mode="type" id="productTabType"
              role="tab"
              aria-selected="${currentViewMode != 'situation'}"
              aria-controls="typeCategoryView">종류로 보기</button>
    </div>

    <div class="category-panel-body">

      <!-- 종류로 보기 -->
      <div class="category-view <c:if test="${currentViewMode != 'situation'}">active</c:if>" id="typeCategoryView">
        <div class="category-group">
          <p class="category-group-title">윗옷</p>
          <div class="category-chip-list">
			  <button type="button" class="category-chip <c:if test="${not empty currentCategoryName and currentViewMode != 'situation' and currentCategoryName == '반팔'}">active</c:if>" data-category="반팔">반팔</button>
			  <button type="button" class="category-chip <c:if test="${not empty currentCategoryName and currentViewMode != 'situation' and currentCategoryName == '긴팔'}">active</c:if>" data-category="긴팔">긴팔</button>
			  <button type="button" class="category-chip <c:if test="${not empty currentCategoryName and currentViewMode != 'situation' and currentCategoryName == '니트'}">active</c:if>" data-category="니트">니트</button>
			  <button type="button" class="category-chip <c:if test="${not empty currentCategoryName and currentViewMode != 'situation' and currentCategoryName == '셔츠'}">active</c:if>" data-category="셔츠">셔츠</button>
			  <button type="button" class="category-chip <c:if test="${not empty currentCategoryName and currentViewMode != 'situation' and currentCategoryName == '조끼'}">active</c:if>" data-category="조끼">조끼</button>
			</div>
        </div>

        <div class="category-group">
          <p class="category-group-title">아랫옷</p>
          <div class="category-chip-list">
			  <button type="button" class="category-chip <c:if test="${not empty currentCategoryName and currentViewMode != 'situation' and currentCategoryName == '긴바지'}">active</c:if>" data-category="긴바지">긴바지</button>
			  <button type="button" class="category-chip <c:if test="${not empty currentCategoryName and currentViewMode != 'situation' and currentCategoryName == '반바지'}">active</c:if>" data-category="반바지">반바지</button>
			  <button type="button" class="category-chip <c:if test="${not empty currentCategoryName and currentViewMode != 'situation' and currentCategoryName == '치마'}">active</c:if>" data-category="치마">치마</button>
			</div>
        </div>

        <div class="category-group">
          <p class="category-group-title">겉옷</p>
          <div class="category-chip-list">
			  <button type="button" class="category-chip <c:if test="${not empty currentCategoryName and currentViewMode != 'situation' and currentCategoryName == '가디건'}">active</c:if>" data-category="가디건">가디건</button>
			  <button type="button" class="category-chip <c:if test="${not empty currentCategoryName and currentViewMode != 'situation' and currentCategoryName == '점퍼'}">active</c:if>" data-category="점퍼">점퍼</button>
			  <button type="button" class="category-chip <c:if test="${not empty currentCategoryName and currentViewMode != 'situation' and currentCategoryName == '코트'}">active</c:if>" data-category="코트">코트</button>
			  <button type="button" class="category-chip <c:if test="${not empty currentCategoryName and currentViewMode != 'situation' and currentCategoryName == '바람막이'}">active</c:if>" data-category="바람막이">바람막이</button>
			</div>
        </div>

        <div class="category-group">
          <p class="category-group-title">한 벌 옷</p>
          <div class="category-chip-list">
			  <button type="button" class="category-chip <c:if test="${not empty currentCategoryName and currentViewMode != 'situation' and currentCategoryName == '원피스'}">active</c:if>" data-category="원피스">원피스</button>
			  <button type="button" class="category-chip <c:if test="${not empty currentCategoryName and currentViewMode != 'situation' and currentCategoryName == '세트 옷'}">active</c:if>" data-category="세트 옷">세트 옷</button>
			</div>
        </div>
      </div>

      <!-- 상황으로 보기 -->
      <div class="category-view <c:if test="${currentViewMode == 'situation'}">active</c:if>" id="situationCategoryView">
        <div class="situation-sub-tabs" role="tablist" aria-label="상황 세부 분류">
          <button type="button" class="situation-sub-tab active" data-situation-tab="daily"
                  id="situationSubTabDaily" role="tab" aria-selected="true"
                  aria-controls="situationPanelDaily">일상 생활</button>
          <button type="button" class="situation-sub-tab" data-situation-tab="special"
                  id="situationSubTabSpecial" role="tab" aria-selected="false"
                  aria-controls="situationPanelSpecial">특별한 날</button>
          <button type="button" class="situation-sub-tab" data-situation-tab="hobby"
                  id="situationSubTabHobby" role="tab" aria-selected="false"
                  aria-controls="situationPanelHobby">취미 · 여가</button>
          <button type="button" class="situation-sub-tab" data-situation-tab="gift"
                  id="situationSubTabGift" role="tab" aria-selected="false"
                  aria-controls="situationPanelGift">선물하기</button>
        </div>

        <div class="situation-sub-panels">

          <!-- 1) 일상 생활 -->
          <div class="situation-sub-panel active" id="situationPanelDaily"
               data-situation-panel="daily" role="tabpanel" aria-labelledby="situationSubTabDaily">
            <div class="category-subsection">
              <p class="category-section-title">집에서 입어요</p>
              <div class="category-chip-list">
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '집에서 편하게 입는 옷'}">active</c:if>" data-category="집에서 편하게 입는 옷">집에서 편하게 입는 옷</button>
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '잠잘 때 입는 옷'}">active</c:if>" data-category="잠잘 때 입는 옷">잠잘 때 입는 옷</button>
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '집안일 할 때 입는 옷'}">active</c:if>" data-category="집안일 할 때 입는 옷">집안일 할 때 입는 옷</button>
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '가볍게 입는 옷'}">active</c:if>" data-category="가볍게 입는 옷">가볍게 입는 옷</button>
				</div>
            </div>
            <div class="category-subsection">
              <p class="category-section-title">외출할 때 입어요</p>
              <div class="category-chip-list">
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '가볍게 나갈 때 입는 옷'}">active</c:if>" data-category="가볍게 나갈 때 입는 옷">가볍게 나갈 때 입는 옷</button>
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '사람 만날 때 입는 옷'}">active</c:if>" data-category="사람 만날 때 입는 옷">사람 만날 때 입는 옷</button>
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '날씨에 맞는 옷'}">active</c:if>" data-category="날씨에 맞는 옷">날씨에 맞는 옷</button>
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '오래 걸어도 편한 옷'}">active</c:if>" data-category="오래 걸어도 편한 옷">오래 걸어도 편한 옷</button>
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '입고 벗기 쉬운 옷'}">active</c:if>" data-category="입고 벗기 쉬운 옷">입고 벗기 쉬운 옷</button>
				</div>
            </div>
            <div class="category-subsection">
              <p class="category-section-title">편하게 입어요</p>
              <div class="category-chip-list">
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '신축성 좋은 옷'}">active</c:if>" data-category="신축성 좋은 옷">신축성 좋은 옷</button>
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '부드러운 옷'}">active</c:if>" data-category="부드러운 옷">부드러운 옷</button>
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '넉넉한 옷'}">active</c:if>" data-category="넉넉한 옷">넉넉한 옷</button>
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '피부에 자극 없는 옷'}">active</c:if>" data-category="피부에 자극 없는 옷">피부에 자극 없는 옷</button>
				</div>
            </div>
          </div>

          <!-- 2) 특별한 날 -->
          <div class="situation-sub-panel" id="situationPanelSpecial"
               data-situation-panel="special" role="tabpanel" aria-labelledby="situationSubTabSpecial">
            <div class="category-subsection">
              <p class="category-section-title">행사 갈 때 입어요</p>
              <div class="category-chip-list">
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '결혼식 갈 때'}">active</c:if>" data-category="결혼식 갈 때">결혼식 갈 때</button>
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '장례식 갈 때'}">active</c:if>" data-category="장례식 갈 때">장례식 갈 때</button>
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '동창회 갈 때'}">active</c:if>" data-category="동창회 갈 때">동창회 갈 때</button>
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '모임/행사 갈 때'}">active</c:if>" data-category="모임/행사 갈 때">모임/행사 갈 때</button>
				</div>
            </div>
            <div class="category-subsection">
              <p class="category-section-title">중요한 날 입어요</p>
              <div class="category-chip-list">
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '입학식 / 졸업식'}">active</c:if>" data-category="입학식 / 졸업식">입학식 / 졸업식</button>
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '가족 행사'}">active</c:if>" data-category="가족 행사">가족 행사</button>
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '기념일'}">active</c:if>" data-category="기념일">기념일</button>
				</div>
            </div>
          </div>

          <!-- 3) 취미 · 여가 -->
          <div class="situation-sub-panel" id="situationPanelHobby"
               data-situation-panel="hobby" role="tabpanel" aria-labelledby="situationSubTabHobby">
            <div class="category-subsection">
              <p class="category-section-title">운동·야외 활동</p>
              <div class="category-chip-list">
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '등산할 때'}">active</c:if>" data-category="등산할 때">등산할 때</button>
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '골프 칠 때'}">active</c:if>" data-category="골프 칠 때">골프 칠 때</button>
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '수영할 때'}">active</c:if>" data-category="수영할 때">수영할 때</button>
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '자전거 탈 때'}">active</c:if>" data-category="자전거 탈 때">자전거 탈 때</button>
				</div>
            </div>
            <div class="category-subsection">
              <p class="category-section-title">여가 활동</p>
              <div class="category-chip-list">
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '여행 갈 때'}">active</c:if>" data-category="여행 갈 때">여행 갈 때</button>
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '낚시할 때'}">active</c:if>" data-category="낚시할 때">낚시할 때</button>
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '텃밭/원예 할 때'}">active</c:if>" data-category="텃밭/원예 할 때">텃밭/원예 할 때</button>
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '편하게 놀러 갈 때'}">active</c:if>" data-category="편하게 놀러 갈 때">편하게 놀러 갈 때</button>
				</div>
            </div>
          </div>

          <!-- 4) 선물하기 -->
          <div class="situation-sub-panel" id="situationPanelGift"
               data-situation-panel="gift" role="tabpanel" aria-labelledby="situationSubTabGift">
            <div class="category-subsection">
              <p class="category-section-title">선물하기 좋아요</p>
              <div class="category-chip-list">
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '부모님께 드리기 좋은 옷'}">active</c:if>" data-category="부모님께 드리기 좋은 옷">부모님께 드리기 좋은 옷</button>
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '할머니·할아버지 옷'}">active</c:if>" data-category="할머니·할아버지 옷">할머니·할아버지 옷</button>
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '손주 옷'}">active</c:if>" data-category="손주 옷">손주 옷</button>
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '사이즈 넉넉한 옷'}">active</c:if>" data-category="사이즈 넉넉한 옷">사이즈 넉넉한 옷</button>
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '누구나 입기 좋은 옷'}">active</c:if>" data-category="누구나 입기 좋은 옷">누구나 입기 좋은 옷</button>
				  <button type="button" class="category-chip <c:if test="${currentViewMode == 'situation' and currentCategoryName == '인기 많은 옷'}">active</c:if>" data-category="인기 많은 옷">인기 많은 옷</button>
				</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>
<script>
  const CONTEXT_PATH = '${pageContext.request.contextPath}';
</script>