<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<section class="product-filter-section">
  <div class="product-filter-row">
    <!-- 정렬 -->
    <div class="filter-dropdown-wrap">
      <button type="button" class="filter-dropdown-btn" id="sortToggleBtn">
        <span id="sortSelectedText">전체</span>
        <span class="material-icons">expand_more</span>
      </button>

      <div class="filter-dropdown-menu hidden" id="sortDropdown">
        <button type="button" class="filter-option active" data-sort="전체">전체</button>
        <button type="button" class="filter-option" data-sort="인기순">인기순</button>
        <button type="button" class="filter-option" data-sort="최신순">최신순</button>
        <button type="button" class="filter-option" data-sort="가격 낮은순">가격 낮은순</button>
        <button type="button" class="filter-option" data-sort="가격 높은순">가격 높은순</button>
      </div>
    </div>

    <!-- 색상 -->
    <div class="filter-dropdown-wrap">
      <button type="button" class="filter-dropdown-btn" id="colorToggleBtn">
        <span>색상</span>
        <span class="material-icons">expand_more</span>
      </button>

      <div class="filter-dropdown-menu color-dropdown hidden" id="colorDropdown">
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
          <label class="chip"><input type="checkbox" name="color" value="기타"><span><i class="color-dot rainbow"></i>기타색</span></label>
        </div>
      </div>
    </div>

    <!-- 계절 -->
    <div class="filter-dropdown-wrap">
      <button type="button" class="filter-dropdown-btn" id="seasonToggleBtn">
        <span id="seasonSelectedText">계절</span>
        <span class="material-icons">expand_more</span>
      </button>

      <div class="filter-dropdown-menu season-dropdown hidden" id="seasonDropdown">
        <p class="filter-dropdown-heading">계절</p>
        <div class="season-options" role="radiogroup" aria-label="계절">
          <label class="season-pill"><input type="radio" name="productSeason" value="따뜻해요"><span>따뜻해요</span></label>
          <label class="season-pill"><input type="radio" name="productSeason" value="시원해요"><span>시원해요</span></label>
          <label class="season-pill"><input type="radio" name="productSeason" value="사계절 입어요"><span>사계절 입어요</span></label>
        </div>
      </div>
    </div>

    <!-- 옷 특징 -->
    <div class="filter-dropdown-wrap">
      <button type="button" class="filter-dropdown-btn" id="featureToggleBtn">
        <span>옷 특징</span>
        <span class="material-icons">expand_more</span>
      </button>

      <div class="filter-dropdown-menu feature-dropdown hidden" id="featureDropdown">
        <p class="filter-dropdown-heading">옷 특징</p>
        <div class="feature-list" id="featureCheckboxList">
          <label class="feature-option"><input type="checkbox" name="clothesFeature" value="주머니 있어요"><span>주머니 있어요</span></label>
          <label class="feature-option"><input type="checkbox" name="clothesFeature" value="단추 있는 옷이에요"><span>단추 있는 옷이에요</span></label>
          <label class="feature-option"><input type="checkbox" name="clothesFeature" value="지퍼 있는 옷이에요"><span>지퍼 있는 옷이에요</span></label>
          <label class="feature-option"><input type="checkbox" name="clothesFeature" value="허리가 편해요"><span>허리가 편해요</span></label>
          <label class="feature-option"><input type="checkbox" name="clothesFeature" value="세탁하기 쉬워요"><span>세탁하기 쉬워요</span></label>
          <label class="feature-option"><input type="checkbox" name="clothesFeature" value="바람을 막아줘요"><span>바람을 막아줘요</span></label>
        </div>
      </div>
    </div>

  </div>

  <div class="product-selected-filters">
    <div class="product-selected-scroll" id="selectedFilterArea">
      <button type="button" class="selected-chip selected-category-chip" data-type="category">
        상의
        <span class="material-icons">close</span>
      </button>
    </div>
    <button type="button" class="reset-btn" id="resetBtn">초기화</button>
  </div>
</section>