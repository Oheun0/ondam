<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<main class="detail-content">
  <section class="detail-image-section">
    <div class="detail-hero-wrap">
      <div class="detail-hero-scroll" id="detailImageScroll">
        <div class="detail-hero-slide">
          <img src="${pageContext.request.contextPath}/images/category/type-top-knit.jpg" alt="">
        </div>
        <div class="detail-hero-slide">
          <img src="${pageContext.request.contextPath}/images/category/type-top-knit.jpg" alt="">
        </div>
        <div class="detail-hero-slide">
          <img src="${pageContext.request.contextPath}/images/category/type-top-knit.jpg" alt="">
        </div>
      </div>
      <div class="detail-image-badges">
        <span class="product-badge product-badge--recommend">추천</span>
        <span class="product-badge product-badge--popular">인기</span>
      </div>
      <div class="detail-hero-indicator" id="detailImageIndicator" role="tablist" aria-label="상품 이미지">
        <button type="button" class="detail-hero-dot active" data-slide-index="0" aria-label="이미지 1" aria-current="true"></button>
        <button type="button" class="detail-hero-dot" data-slide-index="1" aria-label="이미지 2"></button>
        <button type="button" class="detail-hero-dot" data-slide-index="2" aria-label="이미지 3"></button>
      </div>
    </div>
  </section>

  <!-- 핵심 상품 정보 -->
  <section class="detail-info-card">
    <div class="brand-meta-row">
      <a href="#" class="detail-brand-link">A브랜드 <span class="material-icons">chevron_right</span></a>
      <div class="brand-wish-stat" aria-label="찜 1.7만">
        <span class="brand-wish-stat__icon" aria-hidden="true"><span class="material-symbols-outlined">favorite</span></span>
        <span class="brand-wish-stat__num">1.7만</span>
      </div>
    </div>

    <h1 class="detail-product-name">부드러운 라운드 니트 가디건</h1>

    <p class="detail-origin-price">78,000원</p>

    <div class="detail-price-row">
      <p class="detail-sale-price">39,000원</p>
      <span class="detail-discount-rate">20% 할인</span>
    </div>
  </section>

  <!-- AI 추천 가이드(판매자가 상품 등록시 AI api 통해 작성되는거 쉬운 옷설명) -->
  <section class="detail-guide-card">
    <h2 class="detail-section-title detail-section-title--with-icon">
      <span class="material-symbols-outlined detail-section-title-icon" aria-hidden="true">Thumb_Up</span>
      이런 점이 좋아요
    </h2>

    <p class="detail-guide-summary">부드럽고 가볍게 입기 좋은 옷이에요.</p>

    <div class="detail-guide-block">
      <h3>이런 분께 좋아요</h3>
      <ul>
        <li>집에서 편하게 입고 싶은 분</li>
        <li>외출할 때 가볍게 입고 싶은 분</li>
        <li>부드러운 옷을 찾는 분</li>
      </ul>
    </div>

    <div class="detail-guide-block">
      <h3>입기 편한 점</h3>
      <ul>
        <li>잘 늘어나서 움직이기 편해요</li>
        <li>입고 벗기 쉬운 편이에요</li>
        <li>몸을 조이지 않아요</li>
      </ul>
    </div>

    <div class="detail-guide-block">
      <h3>참고하면 좋아요</h3>
      <ul>
        <li>가볍게 걸치기 좋아요</li>
        <li>봄, 가을에 입기 좋아요</li>
      </ul>
    </div>
  </section>

  <!-- 사이즈 추천(구현은 api? 로직?) -->
  <section class="detail-size-recommend-card">
    <div class="size-recommend-head">
      <h2 class="detail-section-title size-recommend-heading">사이즈 추천</h2>
      <button type="button" class="size-recommend-btn" id="sizeRecommendBtn">나에게 맞는 사이즈 추천받기</button>
    </div>

    <div class="size-recommend-result hidden" id="sizeRecommendResult" aria-live="polite">
      <p class="size-recommend-main">김지현님에게는 100 사이즈를 추천드려요.</p>
      <p class="size-recommend-sub">아랫배가 나오신 편이라면 한 사이즈 크게 입으셔도 편해요.</p>
    </div>
  </section>

  <!-- 연관 추천 상품 10개 (더미: 화면 확인용 — 실데이터 연동 시 아래 주석의 c:forEach 로 교체) -->
  <section class="detail-related-card" aria-labelledby="detailRelatedHeading">
    <h2 class="detail-section-title" id="detailRelatedHeading">연관 추천 상품</h2>
    <div class="related-product-scroll" role="list">
      <article class="related-product-item" role="listitem">
        <a href="#" class="related-product-item__anchor">
          <div class="related-thumb-wrap">
            <img src="${pageContext.request.contextPath}/images/category/type-top-knit.jpg" alt="" class="related-thumb-img" loading="lazy" />
          </div>
          <div class="related-product-info">
            <span class="related-brand">A브랜드</span>
            <span class="related-name">부드러운 라운드 니트 가디건</span>
            <span class="related-price">39,000원</span>
            <span class="related-discount">20% 할인</span>
          </div>
        </a>
        <button type="button" class="related-wish-btn" aria-label="찜하기" aria-pressed="false">
          <span class="material-icons-outlined" aria-hidden="true">favorite_border</span>
        </button>
      </article>
      <article class="related-product-item" role="listitem">
        <a href="#" class="related-product-item__anchor">
          <div class="related-thumb-wrap">
            <img src="${pageContext.request.contextPath}/images/category/type-top-knit.jpg" alt="" class="related-thumb-img" loading="lazy" />
          </div>
          <div class="related-product-info">
            <span class="related-brand">온담</span>
            <span class="related-name">편하게 입는 면 혼방 긴팔 티셔츠 일이삼사오육칠팔</span>
            <span class="related-price">28,500원</span>
            <span class="related-discount">15% 할인</span>
          </div>
        </a>
        <button type="button" class="related-wish-btn" aria-label="찜하기" aria-pressed="false">
          <span class="material-icons-outlined" aria-hidden="true">favorite_border</span>
        </button>
      </article>
      <article class="related-product-item" role="listitem">
        <a href="#" class="related-product-item__anchor">
          <div class="related-thumb-wrap">
            <img src="${pageContext.request.contextPath}/images/category/type-top-knit.jpg" alt="" class="related-thumb-img" loading="lazy" />
          </div>
          <div class="related-product-info">
            <span class="related-brand">B라벨</span>
            <span class="related-name">가벼운 바람막이 점퍼</span>
            <span class="related-price">52,000원</span>
            <span class="related-discount">10% 할인</span>
          </div>
        </a>
        <button type="button" class="related-wish-btn" aria-label="찜하기" aria-pressed="false">
          <span class="material-icons-outlined" aria-hidden="true">favorite_border</span>
        </button>
      </article>
      <article class="related-product-item" role="listitem">
        <a href="#" class="related-product-item__anchor">
          <div class="related-thumb-wrap">
            <img src="${pageContext.request.contextPath}/images/category/type-top-knit.jpg" alt="" class="related-thumb-img" loading="lazy" />
          </div>
          <div class="related-product-info">
            <span class="related-brand">시니어웨어</span>
            <span class="related-name">허리 밴딩 편한 바지</span>
            <span class="related-price">31,900원</span>
            <span class="related-discount">25% 할인</span>
          </div>
        </a>
        <button type="button" class="related-wish-btn" aria-label="찜하기" aria-pressed="false">
          <span class="material-icons-outlined" aria-hidden="true">favorite_border</span>
        </button>
      </article>
      <article class="related-product-item" role="listitem">
        <a href="#" class="related-product-item__anchor">
          <div class="related-thumb-wrap">
            <img src="${pageContext.request.contextPath}/images/category/type-top-knit.jpg" alt="" class="related-thumb-img" loading="lazy" />
          </div>
          <div class="related-product-info">
            <span class="related-brand">C마켓</span>
            <span class="related-name">집에서 입기 좋은 조거 팬츠</span>
            <span class="related-price">24,000원</span>
            <span class="related-discount">5% 할인</span>
          </div>
        </a>
        <button type="button" class="related-wish-btn" aria-label="찜하기" aria-pressed="false">
          <span class="material-icons-outlined" aria-hidden="true">favorite_border</span>
        </button>
      </article>
      <%--
      실데이터 연동 시 더미 article 들을 지우고 아래만 사용 (relatedProducts, 최대 10건)
      <c:forEach var="p" items="${relatedProducts}" begin="0" end="9">
        <article class="related-product-item" role="listitem">
          <a href="${pageContext.request.contextPath}/product/detail?no=${p.productNo}" class="related-product-item__anchor">
            <div class="related-thumb-wrap">
              <img src="${pageContext.request.contextPath}${p.productImg}" alt="" class="related-thumb-img" loading="lazy" />
            </div>
            <div class="related-product-info">
              <span class="related-brand"><c:out value="${p.brandName}" /></span>
              <span class="related-name"><c:out value="${p.productName}" /></span>
              <span class="related-price"><fmt:formatNumber value="${p.salePrice}" type="number" groupingUsed="true" />원</span>
              <span class="related-discount"><c:out value="${p.discountRate}" />% 할인</span>
            </div>
          </a>
          <button type="button" class="related-wish-btn" aria-label="찜하기" aria-pressed="false">
            <span class="material-icons-outlined" aria-hidden="true">favorite_border</span>
          </button>
        </article>
      </c:forEach>
      --%>
    </div>
  </section>

  <!-- 탭 바 + 콘텐츠 (분리 구조) -->
  <div class="detail-tabs-wrap">
    <section class="detail-tab-bar-section" aria-label="상품 부가 정보 탭">
      <div class="detail-tab-bar" role="tablist">
        <button type="button" class="detail-tab-btn active" role="tab" id="detailTabBtnInfo" aria-selected="true" aria-controls="detailTabPanelInfo" data-detail-tab="info">
          상세 정보
        </button>
        <button type="button" class="detail-tab-btn" role="tab" id="detailTabBtnReview" aria-selected="false" aria-controls="detailTabPanelReview" data-detail-tab="review" tabindex="-1">
          후기
        </button>
        <button type="button" class="detail-tab-btn" role="tab" id="detailTabBtnInquiry" aria-selected="false" aria-controls="detailTabPanelInquiry" data-detail-tab="inquiry" tabindex="-1">
          문의하기
        </button>
      </div>
    </section>

    <section class="detail-tab-panels-section">
      <!-- 상세 정보 및 상세 사진 -->
      <div class="detail-tab-panel detail-tab-panel-card" id="detailTabPanelInfo" role="tabpanel" aria-labelledby="detailTabBtnInfo" data-detail-tab-panel="info" aria-hidden="false">
        <div class="detail-info-block">
          <h3>상품 설명</h3>
          <p>가볍고 부드러운 소재로 편하게 입기 좋은 니트 가디건입니다.</p>
        </div>

        <div class="detail-info-block">
          <h3>소재 / 세탁</h3>
          <p>폴리에스터 혼방 / 찬물 손세탁 권장</p>
        </div>

        <div class="detail-info-block">
          <h3>배송 안내</h3>
          <p>주문 후 2~3일 내 출고 예정입니다.</p>
        </div>
      </div>

      <!-- 후기 (본문은 product/review/review-list-panel.jsp 공유) -->
      <div class="detail-tab-panel hidden detail-tab-panel-card" id="detailTabPanelReview" role="tabpanel" aria-labelledby="detailTabBtnReview" data-detail-tab-panel="review" aria-hidden="true">
        <jsp:include page="/WEB-INF/views/product/review/review-list-panel.jsp">
          <jsp:param name="showMoreButton" value="true"/>
        </jsp:include>
      </div>

      <!-- 문의하기(공개 시 모두에게 보임, 비공개시 본인에게만 보임 -->
      <div class="detail-tab-panel hidden detail-tab-panel-card" id="detailTabPanelInquiry" role="tabpanel" aria-labelledby="detailTabBtnInquiry" data-detail-tab-panel="inquiry" aria-hidden="true">
        <div class="detail-inquiry-intro">
          <p class="detail-inquiry-intro-text">지금 보고 있는 상품이 궁금하신가요?</p>
          <button type="button" class="detail-inquiry-primary-btn">상품 문의하기</button>
        </div>

        <div class="detail-inquiry-list">
          <div class="detail-inquiry-card">
            <p class="detail-inquiry-q">해당 상품 재입고 언제 되나요?</p>
            <p class="detail-inquiry-meta">김온담 | 2025.06.01 | 공개</p>
            <div class="detail-inquiry-a">
              판매자 답변 : 안녕하십니까, 고객님 6월 20일 입고 예정입니다!
            </div>
          </div>

          <div class="detail-inquiry-card">
            <p class="detail-inquiry-q">세탁기에 돌려도 괜찮을까요?</p>
            <p class="detail-inquiry-meta">익명 | 2025.06.03 | 공개</p>
            <div class="detail-inquiry-a">
              판매자 답변 : 안녕하십니까, 고객님 찬물 세탁 코스로 단독 세탁을 권장드립니다.
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</main>
