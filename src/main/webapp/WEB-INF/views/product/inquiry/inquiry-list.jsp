<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>문의내역</title>

  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-detail.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/inquiry.css">
</head>
<body class="inquiry-list-page" data-context-path="${pageContext.request.contextPath}">
  <div class="detail-shell">
    <div class="detail-page-inner detail-page-inner--sticky-header inquiry-list-inner" id="inquiryListPageRoot">
      <div class="inquiry-list-sticky-head">
        <div class="inquiry-list-header-wrap">
          <jsp:include page="/WEB-INF/views/layout/back-header.jsp"/>
          <h1 class="inquiry-list-header-title">문의내역</h1>
        </div>
      </div>

      <main class="inquiry-list-main">
        <!-- 상단 안내 문구 -->
        <p class="inquiry-list-desc">답변이 완료된 문의는 30일 후 자동으로 사라져요</p>

        <div class="inquiry-list-cards" aria-label="문의 목록">
          <!-- 카드 1: 답변 대기 -->
          <article class="inquiry-list-card" data-inquiry-card="1" data-inquiry-status="waiting">
            <div class="inquiry-list-top">
              <div class="inquiry-list-top__left">
                <div class="inquiry-list-thumb-wrap">
                  <img src="${pageContext.request.contextPath}/images/category/type-top-knit.jpg" alt="" class="inquiry-list-thumb" width="72" height="72" loading="lazy"/>
                </div>
                <div class="inquiry-list-product-meta">
                  <p class="inquiry-list-brand">A브랜드</p>
                  <p class="inquiry-list-name">포근한 데일리 니트 가디건</p>
                </div>
              </div>

              <div class="inquiry-list-menu-wrap">
                <button type="button"
                        class="inquiry-list-menu-btn"
                        aria-label="더보기 메뉴"
                        aria-haspopup="true"
                        aria-expanded="false"
                        id="inquiryMenuBtn1">
                  <span class="material-icons" aria-hidden="true">more_horiz</span>
                </button>
                <div class="inquiry-list-dropdown hidden" id="inquiryDropdown1" role="menu" aria-hidden="true">
                  <button type="button" class="inquiry-list-dropdown__item" data-menu-action="edit" role="menuitem">수정하기</button>
                  <button type="button" class="inquiry-list-dropdown__item inquiry-list-dropdown__item--danger" role="menuitem">삭제하기</button>
                </div>

                <%-- 답변 완료 상태 메뉴 (지금은 주석 처리)
                <div class="inquiry-list-dropdown hidden" id="inquiryDropdownDone1" role="menu" aria-hidden="true">
                  <button type="button" class="inquiry-list-dropdown__item inquiry-list-dropdown__item--danger" role="menuitem">삭제하기</button>
                </div>
                --%>
              </div>
            </div>

            <div class="inquiry-list-divider" aria-hidden="true"></div>

            <div class="inquiry-list-body">
              <p class="inquiry-list-question">소재가 두꺼운 편인가요?</p>
              <p class="inquiry-list-meta">김지현 | 2025.03.12</p>

              <div class="inquiry-list-answer-wait" role="status">아직 답변 전이에요</div>
            </div>
          </article>

          <!-- 카드 2: 답변 완료(디자인 확인용) -->
          <article class="inquiry-list-card" data-inquiry-card="2" data-inquiry-status="done">
            <div class="inquiry-list-top">
              <div class="inquiry-list-top__left">
                <div class="inquiry-list-thumb-wrap">
                  <img src="${pageContext.request.contextPath}/images/category/type-top-knit.jpg" alt="" class="inquiry-list-thumb" width="72" height="72" loading="lazy"/>
                </div>
                <div class="inquiry-list-product-meta">
                  <p class="inquiry-list-brand">A브랜드</p>
                  <p class="inquiry-list-name">포근한 데일리 니트 가디건</p>
                </div>
              </div>

              <div class="inquiry-list-menu-wrap">
                <button type="button"
                        class="inquiry-list-menu-btn"
                        aria-label="더보기 메뉴"
                        aria-haspopup="true"
                        aria-expanded="false"
                        id="inquiryMenuBtn2">
                  <span class="material-icons" aria-hidden="true">more_horiz</span>
                </button>
                <div class="inquiry-list-dropdown hidden" id="inquiryDropdown2" role="menu" aria-hidden="true">
                  <%-- 답변 완료 상태: 수정 불가 (삭제만 노출) --%>
                  <button type="button" class="inquiry-list-dropdown__item inquiry-list-dropdown__item--danger" role="menuitem">삭제하기</button>
                </div>
              </div>
            </div>
            <div class="inquiry-list-divider" aria-hidden="true"></div>

            <div class="inquiry-list-body">
              <p class="inquiry-list-question">세탁하면 줄어들까요?</p>
              <p class="inquiry-list-meta">익명 | 2025.03.08</p>

              <div class="inquiry-list-answer-card" aria-label="판매자 답변">
                <p class="inquiry-list-answer-label">판매자 답변 | 2025.03.08</p>
                <p class="inquiry-list-answer-body">찬물 세탁을 권장드리고, 건조기는 피하시면 줄어듦이 거의 없어요.</p>
              </div>
            </div>
          </article>
        </div>

        <!-- 사용자가 등록한 문의가 없을 때 상단바 아래에 해당 카드만 표시
        <div class="inquiry-list-empty" role="status" aria-label="문의 없음">
          <div class="inquiry-list-empty-icon" aria-hidden="true">
            <span class="material-symbols-outlined">edit_document</span>
          </div>
          <p class="inquiry-list-empty-title">등록한 문의가 없어요</p>
          <p class="inquiry-list-empty-sub">궁금한 점이 있다면 상품 문의를 남겨보세요</p>
        </div>
       -->
      </main>
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/js/inquiry-list.js"></script>
</body>
</html>
