<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 더미: 사용 가능 쿠폰 없음 UI 테스트 시 value 를 true 로 변경 (서버 모델과 무관) --%>
<c:set var="couponDummyAvailableEmpty" value="false"/>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>내 쿠폰</title>

  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-detail.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/review-write.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/coupon-list.css">
</head>
<body class="coupon-list-page" data-context-path="${pageContext.request.contextPath}">
  <div class="detail-shell">
    <div id="couponListPageRoot" class="detail-page-inner detail-page-inner--sticky-header coupon-list-page-inner">
      <div class="coupon-list-body">
        <div class="coupon-list-sticky-head">
          <div class="coupon-list-header-wrap">
            <jsp:include page="/WEB-INF/views/layout/back-header.jsp"/>
            <h1 class="coupon-list-header-title">내 쿠폰</h1>
          </div>

          <div class="coupon-list-tab-card">
            <div class="coupon-list-tab-bar" role="tablist" aria-label="쿠폰 구분">
              <button type="button"
                      class="coupon-list-tab-btn active"
                      role="tab"
                      id="couponTabAvailable"
                      aria-selected="true"
                      aria-controls="couponPanelAvailable"
                      data-coupon-tab="available">
                사용 가능
              </button>
              <button type="button"
                      class="coupon-list-tab-btn"
                      role="tab"
                      id="couponTabPast"
                      aria-selected="false"
                      aria-controls="couponPanelPast"
                      tabindex="-1"
                      data-coupon-tab="past">
                지난 쿠폰
              </button>
            </div>
          </div>
        </div>

        <div class="coupon-list-panels">
          <%-- 사용 가능 --%>
          <div class="coupon-list-tab-panel"
               id="couponPanelAvailable"
               role="tabpanel"
               aria-labelledby="couponTabAvailable"
               data-coupon-panel="available"
               aria-hidden="false">

            <c:if test="${!couponDummyAvailableEmpty}">
              <div class="coupon-list-intro">
                <p class="coupon-list-intro-line">사용 가능한 쿠폰이 3장 있어요!</p>
                <p class="coupon-list-intro-sub">주문할 때 자동으로 적용돼요</p>
              </div>
            </c:if>

            <%-- 쿠폰 유무와 관계없이 항상 노출 --%>
            <div class="coupon-list-toolbar">
              <div class="coupon-list-toolbar-left">
                <div class="filter-dropdown-wrap">
                  <button type="button" class="filter-dropdown-btn" id="couponSortToggleBtn" aria-haspopup="listbox" aria-expanded="false">
                    <span id="couponSortSelectedText">할인율 높은순</span>
                    <span class="material-icons" aria-hidden="true">expand_more</span>
                  </button>
                  <div class="filter-dropdown-menu hidden" id="couponSortDropdown" role="listbox">
                    <button type="button" class="filter-option active" data-coupon-sort="discount" role="option">할인율 높은순</button>
                    <button type="button" class="filter-option" data-coupon-sort="received" role="option">받은순</button>
                    <button type="button" class="filter-option" data-coupon-sort="expiry" role="option">임박순</button>
                  </div>
                </div>
              </div>
              <button type="button" class="coupon-list-register-btn" id="couponRegisterOpenBtn">
                <span aria-hidden="true">+</span> 쿠폰 등록
              </button>
            </div>

            <c:choose>
              <c:when test="${couponDummyAvailableEmpty}">
                <div class="coupon-list-empty-hero section-box" role="status">
                  <p class="coupon-list-empty-title">아직 쿠폰이 없어요</p>
                  <p class="coupon-list-empty-sub">이벤트나 가입 혜택을 받아보세요</p>
                  <a href="${pageContext.request.contextPath}/main" class="coupon-list-empty-cta">쇼핑하러 가기</a>
                </div>
              </c:when>
              <c:otherwise>
                <ul class="coupon-list-cards" id="couponAvailableCardList" aria-label="사용 가능한 쿠폰">
                  <c:forEach var="cid" begin="0" end="2">
                    <c:choose>
                      <c:when test="${cid == 0}">
                        <li class="coupon-card"
                            data-coupon-sort-discount="10"
                            data-coupon-sort-received="2"
                            data-coupon-sort-expiry="1778428800000">
                          <div class="coupon-card__title-row">
                            <h3 class="coupon-card__title">10% 할인</h3>
                            <span class="coupon-card__urgent-inline" aria-label="만료 임박">
                              <span class="material-icons-outlined coupon-card__urgent-icon" aria-hidden="true">alarm</span>
                              <span class="coupon-card__urgent-text">오늘까지 사용 가능!</span>
                            </span>
                          </div>
                          <p class="coupon-card__desc">모든 상품 사용 가능</p>
                          <p class="coupon-card__date">2026년 4월 8일까지</p>
                          <a href="<c:url value="/product"/>" class="coupon-card__action">적용 상품 보러가기</a>
                        </li>
                      </c:when>
                      <c:when test="${cid == 1}">
                        <li class="coupon-card"
                            data-coupon-sort-discount="8"
                            data-coupon-sort-received="1"
                            data-coupon-sort-expiry="1778601600000">
                          <div class="coupon-card__title-row">
                            <h3 class="coupon-card__title">20,000원 이상 구매 시 3,000원 할인</h3>
                          </div>
                          <p class="coupon-card__desc">봄맞이 기획전 참여 브랜드 상품 적용 가능</p>
                          <p class="coupon-card__date">2026년 4월 10일까지</p>
                          <a href="<c:url value="/product"/>" class="coupon-card__action">적용 상품 보러가기</a>
                        </li>
                      </c:when>
                      <c:when test="${cid == 2}">
                        <li class="coupon-card"
                            data-coupon-sort-discount="6"
                            data-coupon-sort-received="3"
                            data-coupon-sort-expiry="1779033600000">
                          <div class="coupon-card__title-row">
                            <h3 class="coupon-card__title">5,000원 할인</h3>
                          </div>
                          <p class="coupon-card__desc">신규 회원 전용</p>
                          <p class="coupon-card__desc">일부 카테고리 제외</p>
                          <p class="coupon-card__date">2026년 4월 15일까지</p>
                          <a href="<c:url value="/product"/>" class="coupon-card__action">적용 상품 보러가기</a>
                        </li>
                      </c:when>
                    </c:choose>
                  </c:forEach>
                </ul>
              </c:otherwise>
            </c:choose>
          </div>

          <%-- 지난 쿠폰 (더미: JSTL forTokens) --%>
          <div class="coupon-list-tab-panel hidden"
               id="couponPanelPast"
               role="tabpanel"
               aria-labelledby="couponTabPast"
               data-coupon-panel="past"
               aria-hidden="true">
            <ul class="coupon-list-cards coupon-list-cards--past" aria-label="지난 쿠폰">
              <c:forEach var="pid" begin="0" end="1">
                <c:choose>
                  <c:when test="${pid == 0}">
                    <li class="coupon-card coupon-card--past">
                      <h3 class="coupon-card__title">15% 할인</h3>
                      <p class="coupon-card__desc">겨울 시즌 한정</p>
                      <p class="coupon-card__date coupon-card__date--past">
                        2026년 3월 1일까지<span class="coupon-card__past-label"> · 기간 만료</span>
                      </p>
                    </li>
                  </c:when>
                  <c:when test="${pid == 1}">
                    <li class="coupon-card coupon-card--past">
                      <h3 class="coupon-card__title">배송비 무료</h3>
                      <p class="coupon-card__desc">3만 원 이상 구매 시</p>
                      <p class="coupon-card__date coupon-card__date--past">
                        2026년 2월 20일까지<span class="coupon-card__past-label"> · 기간 만료</span>
                      </p>
                    </li>
                  </c:when>
                </c:choose>
              </c:forEach>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="review-write-modal coupon-register-modal hidden" id="couponRegisterModal" role="dialog" aria-modal="true" aria-labelledby="couponRegisterModalTitle">
    <div class="review-write-modal-dim" data-coupon-modal-dismiss></div>
    <div class="review-write-modal-card coupon-register-modal__card">
      <h2 class="coupon-register-modal__title" id="couponRegisterModalTitle">쿠폰 등록하기</h2>
      <label class="coupon-register-modal__label" for="couponCodeInput">쿠폰 코드 입력</label>
      <input type="text" class="coupon-register-modal__input" id="couponCodeInput" name="couponCode" autocomplete="off" placeholder="쿠폰 코드를 입력해 주세요"/>
      <div class="review-write-modal-actions review-write-modal-actions--double coupon-register-modal__actions">
        <button type="button" class="review-write-modal-btn review-write-modal-btn--ghost" data-coupon-modal-dismiss>취소</button>
        <button type="button" class="review-write-modal-btn review-write-modal-btn--primary" id="couponRegisterSubmitBtn" disabled>등록하기</button>
      </div>
    </div>
  </div>

  <!-- 쿠폰 코드가 aaa가 아니면 무조건 에러 뜨도록 해놓음 -->	
  <div id="coupon-error-toast" class="option-toast hidden coupon-toast" role="alert" aria-live="assertive" aria-hidden="true">
    <span class="material-icons option-toast__icon" aria-hidden="true">error</span>
    <span class="option-toast__text" id="couponErrorToastText">존재하지 않거나 만료된 쿠폰입니다</span>
  </div>

  <script src="${pageContext.request.contextPath}/js/coupon-list.js"></script>
</body>
</html>
