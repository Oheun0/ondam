<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<main class="detail-content">

  <!-- 이미지 슬라이더 -->
  <section class="detail-image-section">
    <div class="detail-hero-wrap">
      <div class="detail-hero-scroll" id="detailImageScroll">
        <c:choose>
          <c:when test="${not empty images}">
            <c:forEach var="img" items="${images}">
              <div class="detail-hero-slide">
                <img src="${pageContext.request.contextPath}/uploads/products/${img}"
                     alt="${product.productName}" loading="lazy">
              </div>
            </c:forEach>
          </c:when>
          <c:otherwise>
            <div class="detail-hero-slide">
              <div class="product-thumb placeholder">이미지 없음</div>
            </div>
          </c:otherwise>
        </c:choose>
      </div>

      <c:if test="${product.wishCount >= 30 or product.saleCount >= 50}">
        <div class="detail-image-badges">
          <c:if test="${product.wishCount >= 30}">
            <span class="product-badge product-badge--recommend">추천</span>
          </c:if>
          <c:if test="${product.saleCount >= 50}">
            <span class="product-badge product-badge--popular">인기</span>
          </c:if>
        </div>
      </c:if>

      <div class="detail-hero-indicator" id="detailImageIndicator" role="tablist" aria-label="상품 이미지">
        <c:forEach var="img" items="${images}" varStatus="st">
          <button type="button"
                  class="detail-hero-dot ${st.first ? 'active' : ''}"
                  data-slide-index="${st.index}"
                  aria-label="이미지 ${st.index + 1}"
                  aria-current="${st.first ? 'true' : 'false'}"></button>
        </c:forEach>
      </div>
    </div>
  </section>

  <!-- 핵심 상품 정보 -->
  <section class="detail-info-card">
    <div class="brand-meta-row">
      <a href="#" class="detail-brand-link">
        ${product.productBrand}
        <span class="material-icons">chevron_right</span>
      </a>
    </div>

    <h1 class="detail-product-name">${product.productName}</h1>

    <c:if test="${product.productOriginPrice > product.productPrice}">
      <p class="detail-origin-price">
        <fmt:formatNumber value="${product.productOriginPrice}" pattern="#,###"/>원
      </p>
    </c:if>

    <div class="detail-price-row">
      <p class="detail-sale-price">
        <fmt:formatNumber value="${product.productPrice}" pattern="#,###"/>원
      </p>
      <c:if test="${product.productOriginPrice > product.productPrice}">
        <span class="detail-discount-rate">
          <fmt:formatNumber
            value="${(1 - product.productPrice / product.productOriginPrice) * 100}"
            pattern="#"/>% 할인
        </span>
      </c:if>
    </div>
  </section>

  <!-- AI 추천 가이드 — 현재 하드코딩 유지 (추후 AI API 연동 예정) -->
  <section class="detail-guide-card">
    <h2 class="detail-section-title detail-section-title--with-icon">
      <span class="material-symbols-outlined detail-section-title-icon" aria-hidden="true">Thumb_Up</span>
      이런 점이 좋아요
    </h2>
    <p class="detail-guide-summary">${product.productEx}</p>

    <div class="detail-guide-block">
      <h3>소재</h3>
      <ul><li>${product.productMaterial}</li></ul>
    </div>
    <c:if test="${not empty product.productFit}">
      <div class="detail-guide-block">
        <h3>핏</h3>
        <ul><li>${product.productFit}</li></ul>
      </div>
    </c:if>
    <c:if test="${not empty product.productThickness}">
      <div class="detail-guide-block">
        <h3>두께감</h3>
        <ul><li>${product.productThickness}</li></ul>
      </div>
    </c:if>
    <c:if test="${not empty product.productPattern}">
      <div class="detail-guide-block">
        <h3>패턴</h3>
        <ul><li>${product.productPattern}</li></ul>
      </div>
    </c:if>
  </section>

  <!-- 사이즈 추천 — 하드코딩 유지 (추후 로직 구현 예정) -->
  <section class="detail-size-recommend-card">
    <div class="size-recommend-head">
      <h2 class="detail-section-title size-recommend-heading">사이즈 추천</h2>
      <button type="button" class="size-recommend-btn" id="sizeRecommendBtn">나에게 맞는 사이즈 추천받기</button>
    </div>
    <div class="size-recommend-result hidden" id="sizeRecommendResult" aria-live="polite">
      <p class="size-recommend-main">추천 사이즈를 불러오는 중입니다.</p>
    </div>
  </section>

  <!-- 연관 추천 상품 — 추후 연동 예정, 현재 섹션만 유지 -->
  <section class="detail-related-card" aria-labelledby="detailRelatedHeading">
    <h2 class="detail-section-title" id="detailRelatedHeading">연관 추천 상품</h2>
    <div class="related-product-scroll" role="list">
      <%-- 추후 relatedProducts setAttribute 후 c:forEach로 교체 --%>
    </div>
  </section>

  <!-- 탭: 상세정보 / 후기 / 문의 — 구조 유지, 상세정보만 DTO 연동 -->
  <div class="detail-tabs-wrap">
    <section class="detail-tab-bar-section" aria-label="상품 부가 정보 탭">
      <div class="detail-tab-bar" role="tablist">
        <button type="button" class="detail-tab-btn active" role="tab"
                id="detailTabBtnInfo" aria-selected="true"
                aria-controls="detailTabPanelInfo" data-detail-tab="info">상세 정보</button>
        <button type="button" class="detail-tab-btn" role="tab"
                id="detailTabBtnReview" aria-selected="false"
                aria-controls="detailTabPanelReview" data-detail-tab="review" tabindex="-1">후기</button>
        <button type="button" class="detail-tab-btn" role="tab"
                id="detailTabBtnInquiry" aria-selected="false"
                aria-controls="detailTabPanelInquiry" data-detail-tab="inquiry" tabindex="-1">문의하기</button>
      </div>
    </section>

    <section class="detail-tab-panels-section">
      <div class="detail-tab-panel detail-tab-panel-card" id="detailTabPanelInfo"
           role="tabpanel" aria-labelledby="detailTabBtnInfo"
           data-detail-tab-panel="info" aria-hidden="false">
        <div class="detail-info-block">
          <h3>상품 설명</h3>
          <p>${product.productEx}</p>
        </div>
        <div class="detail-info-block">
          <h3>소재 / 세탁</h3>
          <p>${product.productMaterial}</p>
        </div>
        <div class="detail-info-block">
          <h3>배송 안내</h3>
          <p>주문 후 2~3일 내 출고 예정입니다.</p>
        </div>
      </div>

      <div class="detail-tab-panel hidden detail-tab-panel-card" id="detailTabPanelReview"
           role="tabpanel" aria-labelledby="detailTabBtnReview"
           data-detail-tab-panel="review" aria-hidden="true">
        <jsp:include page="/WEB-INF/views/product/review/review-list-panel.jsp">
          <jsp:param name="showMoreButton" value="true"/>
        </jsp:include>
      </div>

      <div class="detail-tab-panel hidden detail-tab-panel-card" id="detailTabPanelInquiry"
           role="tabpanel" aria-labelledby="detailTabBtnInquiry"
           data-detail-tab-panel="inquiry" aria-hidden="true">
        <div class="detail-inquiry-intro">
          <p class="detail-inquiry-intro-text">지금 보고 있는 상품이 궁금하신가요?</p>
          <button type="button" class="detail-inquiry-primary-btn">상품 문의하기</button>
        </div>
        <div class="detail-inquiry-list">
          <%-- 추후 inquiryList 연동 예정 --%>
        </div>
      </div>
    </section>
  </div>
</main>