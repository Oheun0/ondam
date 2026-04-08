<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  request.setAttribute("sellerActiveMenu", "review");
  request.setAttribute("sellerPageTitle", "리뷰 관리");
%>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>리뷰 관리 | 온담 판매자센터</title>

  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Material+Icons+Outlined&display=swap" rel="stylesheet">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-layout.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-review.css" />
</head>
<body class="seller-app" data-context-path="${pageContext.request.contextPath}">
  <div class="seller-layout">
    <jsp:include page="/WEB-INF/views/seller/layout/seller-sidebar.jsp" />

    <div class="seller-main-area">
      <jsp:include page="/WEB-INF/views/seller/layout/seller-header.jsp" />

      <main class="seller-content seller-review-page" aria-label="리뷰 관리">
        <header class="seller-review-head">
          <div>
            <h2 class="seller-review-title">리뷰 관리</h2>
            <p class="seller-review-sub">등록된 리뷰를 확인하고 응대할 수 있어요</p>
          </div>
        </header>

        <section class="seller-review-summary" aria-label="요약">
          <div class="seller-review-summary-grid">
            <div class="seller-review-summary-card">
              <div class="seller-review-summary-label">전체 리뷰</div>
              <div class="seller-review-summary-value">128<span class="seller-review-summary-unit">개</span></div>
            </div>
            <div class="seller-review-summary-card">
              <div class="seller-review-summary-label">평균 평점</div>
              <div class="seller-review-summary-value">4.6<span class="seller-review-summary-unit">점</span></div>
            </div>
            <div class="seller-review-summary-card">
              <div class="seller-review-summary-label">미답변 리뷰</div>
              <div class="seller-review-summary-value">12<span class="seller-review-summary-unit">개</span></div>
            </div>
            <div class="seller-review-summary-card">
              <div class="seller-review-summary-label">이번 주 신규</div>
              <div class="seller-review-summary-value">8<span class="seller-review-summary-unit">개</span></div>
            </div>
          </div>
        </section>

        <section class="seller-card seller-review-toolbar" aria-label="검색 및 필터">
          <div class="seller-review-filters">
            <div class="seller-review-filter">
              <label class="seller-review-filter-label" for="reviewProduct">상품</label>
              <select id="reviewProduct" class="seller-review-select">
                <option value="all">전체 상품</option>
                <option value="P-1001">부드러운 라운드 니트 가디건</option>
                <option value="P-1002">편안한 봄 니트 조끼</option>
                <option value="P-1003">가벼운 데일리 셔츠</option>
                <option value="P-2001">산뜻한 플라워 블라우스</option>
              </select>
            </div>

            <div class="seller-review-filter">
              <label class="seller-review-filter-label" for="reviewRating">평점</label>
              <select id="reviewRating" class="seller-review-select">
                <option value="all">전체 평점</option>
                <option value="5">5점</option>
                <option value="4">4점</option>
                <option value="3">3점</option>
                <option value="2">2점 이하</option>
              </select>
            </div>

            <div class="seller-review-filter">
              <label class="seller-review-filter-label" for="reviewPeriod">기간</label>
              <select id="reviewPeriod" class="seller-review-select">
                <option value="all">전체 기간</option>
                <option value="7d">최근 7일</option>
                <option value="30d">최근 30일</option>
                <option value="3m">최근 3개월</option>
              </select>
            </div>

            <div class="seller-review-filter seller-review-filter--search">
              <label class="seller-review-filter-label" for="reviewQuery">검색</label>
              <div class="seller-review-input-wrap">
                <span class="material-icons-outlined" aria-hidden="true">search</span>
                <input id="reviewQuery" class="seller-review-input" type="text" placeholder="리뷰 내용 또는 작성자로 검색해 주세요" />
              </div>
            </div>

            <div class="seller-review-filter seller-review-filter--btn">
              <button type="button" class="seller-review-secondary-btn" id="reviewApplyBtn">필터 적용</button>
            </div>
          </div>
        </section>

        <section class="seller-review-list" aria-label="리뷰 목록">
          <article class="seller-review-card" data-review-id="R-0001" data-answered="false" data-rating="5" data-product="P-1001"
            data-author="김지현" data-date="2026.04.08" data-order-no="20260408-0001" data-option="아이보리 / 95"
            data-content="생각보다 부드럽고 입기 편했어요. 색도 화면이랑 비슷해서 만족합니다. 또 구매할게요!"
          >
            <header class="seller-review-card-head">
              <div class="seller-review-who">
                <strong class="seller-review-author">김지현</strong>
                <span class="seller-review-sep">/</span>
                <span class="seller-review-date">2026.04.08</span>
              </div>
              <div class="seller-review-right">
                <div class="seller-review-rating" aria-label="평점 5점">
                  <span class="seller-review-stars" data-stars="5" aria-hidden="true">★★★★★</span>
                  <span class="seller-review-score">5.0</span>
                </div>
                <span class="seller-review-badge seller-review-badge--todo">미답변</span>
              </div>
            </header>

            <div class="seller-review-product">부드러운 라운드 니트 가디건</div>
            <p class="seller-review-content">
              생각보다 부드럽고 입기 편했어요. 색도 화면이랑 비슷해서 만족합니다. 또 구매할게요!
            </p>
            <div class="seller-review-images" aria-label="리뷰 이미지">
              <img src="${pageContext.request.contextPath}/images/category/comfort-soft.jpg" alt="리뷰 이미지 1">
              <img src="${pageContext.request.contextPath}/images/category/out-light.jpg" alt="리뷰 이미지 2">
            </div>

            <div class="seller-review-meta">
              <div class="seller-review-meta-row"><span class="k">주문번호</span><span class="v">20260408-0001</span></div>
              <div class="seller-review-meta-row"><span class="k">옵션</span><span class="v">아이보리 / 95</span></div>
            </div>

            <div class="seller-review-actions">
              <button type="button" class="seller-review-btn" data-action="detail">상세보기</button>
              <button type="button" class="seller-review-btn seller-review-btn--primary" data-action="reply">답변 달기</button>
            </div>
          </article>

          <article class="seller-review-card" data-review-id="R-0002" data-answered="true" data-rating="4" data-product="P-1002"
            data-author="성연수" data-date="2026.04.06" data-order-no="20260406-0009" data-option="베이지 / FREE"
            data-content="가볍고 편해서 좋아요. 다만 단추가 조금 더 크면 좋겠어요."
          >
            <header class="seller-review-card-head">
              <div class="seller-review-who">
                <strong class="seller-review-author">성연수</strong>
                <span class="seller-review-sep">/</span>
                <span class="seller-review-date">2026.04.06</span>
              </div>
              <div class="seller-review-right">
                <div class="seller-review-rating" aria-label="평점 4점">
                  <span class="seller-review-stars" data-stars="4" aria-hidden="true">★★★★☆</span>
                  <span class="seller-review-score">4.0</span>
                </div>
                <span class="seller-review-badge seller-review-badge--done">답변 완료</span>
              </div>
            </header>
            <div class="seller-review-product">편안한 봄 니트 조끼</div>
            <p class="seller-review-content">
              가볍고 편해서 좋아요. 다만 단추가 조금 더 크면 좋겠어요.
            </p>
            <div class="seller-review-meta">
              <div class="seller-review-meta-row"><span class="k">주문번호</span><span class="v">20260406-0009</span></div>
              <div class="seller-review-meta-row"><span class="k">옵션</span><span class="v">베이지 / FREE</span></div>
            </div>
            <div class="seller-review-actions">
              <button type="button" class="seller-review-btn" data-action="detail">상세보기</button>
              <button type="button" class="seller-review-btn" data-action="reply">답변 보기</button>
            </div>
          </article>

          <article class="seller-review-card" data-review-id="R-0003" data-answered="false" data-rating="3" data-product="P-1003"
            data-author="김가빈" data-date="2026.04.02" data-order-no="20260407-0048" data-option="남색 / 100"
            data-content="핏은 괜찮은데 생각보다 얇아요. 봄에는 좋을 것 같아요."
          >
            <header class="seller-review-card-head">
              <div class="seller-review-who">
                <strong class="seller-review-author">김가빈</strong>
                <span class="seller-review-sep">/</span>
                <span class="seller-review-date">2026.04.02</span>
              </div>
              <div class="seller-review-right">
                <div class="seller-review-rating" aria-label="평점 3점">
                  <span class="seller-review-stars" data-stars="3" aria-hidden="true">★★★☆☆</span>
                  <span class="seller-review-score">3.0</span>
                </div>
                <span class="seller-review-badge seller-review-badge--todo">미답변</span>
              </div>
            </header>
            <div class="seller-review-product">가벼운 데일리 셔츠</div>
            <p class="seller-review-content">
              핏은 괜찮은데 생각보다 얇아요. 봄에는 좋을 것 같아요.
            </p>
            <div class="seller-review-images" aria-label="리뷰 이미지">
              <img src="${pageContext.request.contextPath}/images/category/out-walking.jpg" alt="리뷰 이미지 1">
            </div>
            <div class="seller-review-meta">
              <div class="seller-review-meta-row"><span class="k">주문번호</span><span class="v">20260407-0048</span></div>
              <div class="seller-review-meta-row"><span class="k">옵션</span><span class="v">남색 / 100</span></div>
            </div>
            <div class="seller-review-actions">
              <button type="button" class="seller-review-btn" data-action="detail">상세보기</button>
              <button type="button" class="seller-review-btn seller-review-btn--primary" data-action="reply">답변 달기</button>
            </div>
          </article>

          <article class="seller-review-card" data-review-id="R-0004" data-answered="false" data-rating="5" data-product="P-2001"
            data-author="박민준" data-date="2026.03.28" data-order-no="20260407-0032" data-option="연분홍색 / 90"
            data-content="선물로 받았는데 너무 예쁘고 촉감이 좋아요. 부모님도 만족하셨어요."
          >
            <header class="seller-review-card-head">
              <div class="seller-review-who">
                <strong class="seller-review-author">박민준</strong>
                <span class="seller-review-sep">/</span>
                <span class="seller-review-date">2026.03.28</span>
              </div>
              <div class="seller-review-right">
                <div class="seller-review-rating" aria-label="평점 5점">
                  <span class="seller-review-stars" data-stars="5" aria-hidden="true">★★★★★</span>
                  <span class="seller-review-score">5.0</span>
                </div>
                <span class="seller-review-badge seller-review-badge--todo">미답변</span>
              </div>
            </header>
            <div class="seller-review-product">산뜻한 플라워 블라우스</div>
            <p class="seller-review-content">
              선물로 받았는데 너무 예쁘고 촉감이 좋아요. 부모님도 만족하셨어요.
            </p>
            <div class="seller-review-meta">
              <div class="seller-review-meta-row"><span class="k">주문번호</span><span class="v">20260407-0032</span></div>
              <div class="seller-review-meta-row"><span class="k">옵션</span><span class="v">연분홍색 / 90</span></div>
            </div>
            <div class="seller-review-actions">
              <button type="button" class="seller-review-btn" data-action="detail">상세보기</button>
              <button type="button" class="seller-review-btn seller-review-btn--primary" data-action="reply">답변 달기</button>
            </div>
          </article>
        </section>

        <div class="seller-review-pagination" aria-label="페이지네이션(더미)">
          <button type="button" class="seller-review-page-btn" data-page="prev">이전</button>
          <button type="button" class="seller-review-page-btn active" data-page="1">1</button>
          <button type="button" class="seller-review-page-btn" data-page="2">2</button>
          <button type="button" class="seller-review-page-btn" data-page="3">3</button>
          <button type="button" class="seller-review-page-btn" data-page="next">다음</button>
        </div>

        <section class="seller-card seller-review-empty" aria-label="빈 상태(더미)" hidden>
          <div class="seller-review-empty-inner">
            <div class="seller-review-empty-icon" aria-hidden="true">
              <span class="material-icons-outlined">rate_review</span>
            </div>
            <h3 class="seller-review-empty-title">아직 등록된 리뷰가 없어요</h3>
            <p class="seller-review-empty-desc">리뷰가 작성되면 여기에 표시됩니다</p>
          </div>
        </section>
      </main>

      <!-- 상세/응대 패널 -->
      <aside class="seller-review-panel hidden" id="reviewPanel" aria-label="리뷰 상세 패널" aria-hidden="true">
        <div class="seller-review-panel__dim" id="reviewPanelDim" aria-hidden="true"></div>
        <div class="seller-review-panel__sheet" role="dialog" aria-modal="true" aria-labelledby="reviewPanelTitle">
          <header class="seller-review-panel__head">
            <div>
              <h3 class="seller-review-panel__title" id="reviewPanelTitle">리뷰 상세</h3>
              <p class="seller-review-panel__sub" id="reviewPanelSub">-</p>
            </div>
            <button type="button" class="seller-review-panel__close" id="reviewPanelClose" aria-label="닫기">
              <span class="material-icons-outlined" aria-hidden="true">close</span>
            </button>
          </header>

          <div class="seller-review-panel__body">
            <div class="seller-review-panel__info">
              <div class="row"><span class="k">작성자</span><span class="v" id="pdAuthor">-</span></div>
              <div class="row"><span class="k">작성일</span><span class="v" id="pdDate">-</span></div>
              <div class="row"><span class="k">상품</span><span class="v" id="pdProduct">-</span></div>
              <div class="row"><span class="k">옵션</span><span class="v" id="pdOption">-</span></div>
              <div class="row"><span class="k">주문번호</span><span class="v" id="pdOrderNo">-</span></div>
              <div class="row"><span class="k">평점</span><span class="v" id="pdRating">-</span></div>
              <div class="row"><span class="k">상태</span><span class="v" id="pdAnswered">-</span></div>
            </div>

            <section class="seller-review-panel__content" aria-label="리뷰 본문">
              <h4 class="seller-review-panel__h4">리뷰 내용</h4>
              <p class="seller-review-panel__text" id="pdContent">-</p>
              <div class="seller-review-panel__images" id="pdImages" aria-label="리뷰 이미지"></div>
            </section>

            <section class="seller-review-panel__reply" aria-label="리뷰 응대">
              <h4 class="seller-review-panel__h4">답변 달기</h4>
              <div class="seller-review-reply-box">
                <textarea id="replyText" class="seller-review-reply-text" rows="4" placeholder="짧고 친절하게 답변해 주세요"></textarea>
                <div class="seller-review-reply-templates">
                  <button type="button" class="seller-review-template" data-template="소중한 후기 감사합니다.">후기 감사</button>
                  <button type="button" class="seller-review-template" data-template="만족하셨다니 정말 기쁩니다.">만족 감사</button>
                  <button type="button" class="seller-review-template" data-template="불편을 드려 죄송합니다. 더 좋은 상품으로 보답하겠습니다.">사과/개선</button>
                </div>
                <div class="seller-review-reply-actions">
                  <button type="button" class="seller-review-secondary-btn" id="replyCancelBtn">취소</button>
                  <button type="button" class="seller-review-primary-btn" id="replySubmitBtn">답변 등록</button>
                </div>
                <p class="seller-review-panel__hint">답변 등록은 더미 동작이며 실제 저장은 아직 연동되지 않았어요.</p>
              </div>
            </section>
          </div>
        </div>
      </aside>

      <jsp:include page="/WEB-INF/views/seller/layout/seller-footer.jsp" />
    </div>
  </div>

  <script>
    (function () {
      var notifyBtn = document.getElementById('sellerHeaderNotifyBtn');
      var logoutBtn = document.getElementById('sellerHeaderLogoutBtn');
      if (notifyBtn) notifyBtn.addEventListener('click', function () { alert('알림 기능은 아직 준비 중이에요.'); });
      if (logoutBtn) logoutBtn.addEventListener('click', function () { alert('로그아웃은 아직 연동되지 않았어요. (더미)'); });
    })();
  </script>
  <script src="${pageContext.request.contextPath}/js/seller/review-list.js"></script>
</body>
</html>

