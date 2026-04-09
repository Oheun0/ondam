<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  request.setAttribute("sellerActiveMenu", "shorts");
  request.setAttribute("sellerPageTitle", "쇼츠 관리");
%>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>쇼츠 관리 | 온담 판매자센터</title>

  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Material+Icons+Outlined&display=swap" rel="stylesheet">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-layout.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-shorts.css" />
</head>
<body class="seller-app" data-context-path="${pageContext.request.contextPath}">
  <div class="seller-layout">
    <jsp:include page="/WEB-INF/views/seller/layout/seller-sidebar.jsp" />

    <div class="seller-main-area">
      <jsp:include page="/WEB-INF/views/seller/layout/seller-header.jsp" />

      <main class="seller-content seller-shorts-page" aria-label="쇼츠 관리">
        <header class="seller-shorts-head">
          <div>
            <h2 class="seller-shorts-title">쇼츠 관리</h2>
            <p class="seller-shorts-sub">등록된 쇼츠를 확인하고 공개 상태를 관리할 수 있어요</p>
          </div>
          <div class="seller-shorts-head-actions">
            <button type="button" class="seller-shorts-primary-btn" id="sellerNewShortsBtn">
              <span class="material-icons-outlined" aria-hidden="true">add</span>
              새 쇼츠 등록
            </button>
          </div>
        </header>

        <section class="seller-shorts-summary" aria-label="요약">
          <div class="seller-shorts-summary-grid">
            <div class="seller-shorts-summary-card">
              <div class="seller-shorts-summary-label">전체 쇼츠</div>
              <div class="seller-shorts-summary-value">12<span class="seller-shorts-summary-unit">개</span></div>
            </div>
            <div class="seller-shorts-summary-card">
              <div class="seller-shorts-summary-label">공개 중</div>
              <div class="seller-shorts-summary-value">8<span class="seller-shorts-summary-unit">개</span></div>
            </div>
            <div class="seller-shorts-summary-card">
              <div class="seller-shorts-summary-label">비공개</div>
              <div class="seller-shorts-summary-value">4<span class="seller-shorts-summary-unit">개</span></div>
            </div>
          </div>
        </section>

        <section class="seller-card seller-shorts-toolbar" aria-label="검색 및 필터">
          <div class="seller-shorts-filters">
            <div class="seller-shorts-filter">
              <label class="seller-shorts-filter-label" for="shortsQuery">검색</label>
              <div class="seller-shorts-input-wrap">
                <span class="material-icons-outlined" aria-hidden="true">search</span>
                <input id="shortsQuery" class="seller-shorts-input" type="text" placeholder="쇼츠 제목으로 검색해 주세요" />
              </div>
            </div>

            <div class="seller-shorts-filter">
              <label class="seller-shorts-filter-label" for="shortsPublic">공개 상태</label>
              <select id="shortsPublic" class="seller-shorts-select">
                <option value="all">전체 상태</option>
                <option value="public">공개</option>
                <option value="private">비공개</option>
              </select>
            </div>

            <div class="seller-shorts-filter">
              <label class="seller-shorts-filter-label" for="shortsPeriod">기간</label>
              <select id="shortsPeriod" class="seller-shorts-select">
                <option value="all">전체 기간</option>
                <option value="7d">최근 7일</option>
                <option value="30d">최근 30일</option>
                <option value="3m">최근 3개월</option>
              </select>
            </div>

            <div class="seller-shorts-filter">
              <label class="seller-shorts-filter-label" for="shortsProduct">연결 상품</label>
              <select id="shortsProduct" class="seller-shorts-select">
                <option value="all">전체 상품</option>
                <option value="P-1001">부드러운 라운드 니트 가디건</option>
                <option value="P-1002">편안한 봄 니트 조끼</option>
                <option value="P-1003">가벼운 데일리 셔츠</option>
                <option value="P-2001">산뜻한 플라워 블라우스</option>
              </select>
            </div>

            <div class="seller-shorts-filter seller-shorts-filter--btn">
              <button type="button" class="seller-shorts-secondary-btn" id="shortsApplyBtn">필터 적용</button>
            </div>
          </div>
        </section>

        <section class="seller-shorts-list" aria-label="쇼츠 목록">
          <article class="seller-shorts-card" data-shorts-no="S-240408-0001" data-public="true" data-product="P-1001">
            <div class="seller-shorts-thumb" aria-label="쇼츠 썸네일(더미)">
              <img src="${pageContext.request.contextPath}/images/category/out-weather.jpg" alt="쇼츠 썸네일">
              <button type="button" class="seller-shorts-thumb-btn" data-action="preview" aria-label="미리보기(더미)">
                <span class="material-icons-outlined" aria-hidden="true">play_circle</span>
              </button>
            </div>
            <div class="seller-shorts-meta">
              <div class="seller-shorts-title-row">
                <h3 class="seller-shorts-card-title">봄에 가볍게 입기 좋은 가디건</h3>
                <span class="seller-shorts-badge seller-shorts-badge--public">공개</span>
              </div>
              <div class="seller-shorts-subrows">
                <div class="seller-shorts-subrow"><span class="seller-shorts-k">연결 상품</span><span class="seller-shorts-v">부드러운 라운드 니트 가디건</span></div>
                <div class="seller-shorts-subrow"><span class="seller-shorts-k">업로드일</span><span class="seller-shorts-v">2026.04.08</span></div>
                <div class="seller-shorts-subrow"><span class="seller-shorts-k">쇼츠번호</span><span class="seller-shorts-v">S-240408-0001</span></div>
              </div>
            </div>
            <div class="seller-shorts-right">
              <div class="seller-shorts-metrics" aria-label="성과(더미)">
                <div class="seller-shorts-metric"><span class="seller-shorts-metric-k">조회</span><span class="seller-shorts-metric-v">1,240</span></div>
                <div class="seller-shorts-metric"><span class="seller-shorts-metric-k">찜</span><span class="seller-shorts-metric-v">86</span></div>
                <div class="seller-shorts-metric"><span class="seller-shorts-metric-k">구매</span><span class="seller-shorts-metric-v">14</span></div>
              </div>
              <div class="seller-shorts-actions" aria-label="관리">
                <button type="button" class="seller-shorts-mini-btn" data-action="edit">수정</button>
                <button type="button" class="seller-shorts-mini-btn seller-shorts-mini-btn--toggle" data-action="toggle">비공개 전환</button>
                <button type="button" class="seller-shorts-mini-btn" data-action="product">연결 상품 보기</button>
              </div>
            </div>
          </article>

          <article class="seller-shorts-card" data-shorts-no="S-240405-0007" data-public="false" data-product="P-1002">
            <div class="seller-shorts-thumb" aria-label="쇼츠 썸네일(더미)">
              <img src="${pageContext.request.contextPath}/images/category/comfort-soft.jpg" alt="쇼츠 썸네일">
              <button type="button" class="seller-shorts-thumb-btn" data-action="preview" aria-label="미리보기(더미)">
                <span class="material-icons-outlined" aria-hidden="true">play_circle</span>
              </button>
            </div>
            <div class="seller-shorts-meta">
              <div class="seller-shorts-title-row">
                <h3 class="seller-shorts-card-title">편하게 입기 좋은 니트 조끼 코디</h3>
                <span class="seller-shorts-badge seller-shorts-badge--private">비공개</span>
              </div>
              <div class="seller-shorts-subrows">
                <div class="seller-shorts-subrow"><span class="seller-shorts-k">연결 상품</span><span class="seller-shorts-v">편안한 봄 니트 조끼</span></div>
                <div class="seller-shorts-subrow"><span class="seller-shorts-k">업로드일</span><span class="seller-shorts-v">2026.04.05</span></div>
                <div class="seller-shorts-subrow"><span class="seller-shorts-k">쇼츠번호</span><span class="seller-shorts-v">S-240405-0007</span></div>
              </div>
            </div>
            <div class="seller-shorts-right">
              <div class="seller-shorts-metrics" aria-label="성과(더미)">
                <div class="seller-shorts-metric"><span class="seller-shorts-metric-k">조회</span><span class="seller-shorts-metric-v">420</span></div>
                <div class="seller-shorts-metric"><span class="seller-shorts-metric-k">찜</span><span class="seller-shorts-metric-v">22</span></div>
                <div class="seller-shorts-metric"><span class="seller-shorts-metric-k">구매</span><span class="seller-shorts-metric-v">3</span></div>
              </div>
              <div class="seller-shorts-actions" aria-label="관리">
                <button type="button" class="seller-shorts-mini-btn" data-action="edit">수정</button>
                <button type="button" class="seller-shorts-mini-btn seller-shorts-mini-btn--toggle" data-action="toggle">공개 전환</button>
                <button type="button" class="seller-shorts-mini-btn" data-action="product">연결 상품 보기</button>
              </div>
            </div>
          </article>

          <article class="seller-shorts-card" data-shorts-no="S-240402-0012" data-public="true" data-product="P-1003">
            <div class="seller-shorts-thumb" aria-label="쇼츠 썸네일(더미)">
              <img src="${pageContext.request.contextPath}/images/category/out-light.jpg" alt="쇼츠 썸네일">
              <button type="button" class="seller-shorts-thumb-btn" data-action="preview" aria-label="미리보기(더미)">
                <span class="material-icons-outlined" aria-hidden="true">play_circle</span>
              </button>
            </div>
            <div class="seller-shorts-meta">
              <div class="seller-shorts-title-row">
                <h3 class="seller-shorts-card-title">가벼운 데일리 셔츠로 산뜻한 하루</h3>
                <span class="seller-shorts-badge seller-shorts-badge--public">공개</span>
              </div>
              <div class="seller-shorts-subrows">
                <div class="seller-shorts-subrow"><span class="seller-shorts-k">연결 상품</span><span class="seller-shorts-v">가벼운 데일리 셔츠</span></div>
                <div class="seller-shorts-subrow"><span class="seller-shorts-k">업로드일</span><span class="seller-shorts-v">2026.04.02</span></div>
                <div class="seller-shorts-subrow"><span class="seller-shorts-k">쇼츠번호</span><span class="seller-shorts-v">S-240402-0012</span></div>
              </div>
            </div>
            <div class="seller-shorts-right">
              <div class="seller-shorts-metrics" aria-label="성과(더미)">
                <div class="seller-shorts-metric"><span class="seller-shorts-metric-k">조회</span><span class="seller-shorts-metric-v">980</span></div>
                <div class="seller-shorts-metric"><span class="seller-shorts-metric-k">찜</span><span class="seller-shorts-metric-v">61</span></div>
                <div class="seller-shorts-metric"><span class="seller-shorts-metric-k">구매</span><span class="seller-shorts-metric-v">9</span></div>
              </div>
              <div class="seller-shorts-actions" aria-label="관리">
                <button type="button" class="seller-shorts-mini-btn" data-action="edit">수정</button>
                <button type="button" class="seller-shorts-mini-btn seller-shorts-mini-btn--toggle" data-action="toggle">비공개 전환</button>
                <button type="button" class="seller-shorts-mini-btn" data-action="product">연결 상품 보기</button>
              </div>
            </div>
          </article>

          <article class="seller-shorts-card" data-shorts-no="S-240328-0004" data-public="true" data-product="P-2001">
            <div class="seller-shorts-thumb" aria-label="쇼츠 썸네일(더미)">
              <img src="${pageContext.request.contextPath}/images/category/event-meeting.jpg" alt="쇼츠 썸네일">
              <button type="button" class="seller-shorts-thumb-btn" data-action="preview" aria-label="미리보기(더미)">
                <span class="material-icons-outlined" aria-hidden="true">play_circle</span>
              </button>
            </div>
            <div class="seller-shorts-meta">
              <div class="seller-shorts-title-row">
                <h3 class="seller-shorts-card-title">산뜻한 플라워 블라우스로 분위기 업</h3>
                <span class="seller-shorts-badge seller-shorts-badge--public">공개</span>
              </div>
              <div class="seller-shorts-subrows">
                <div class="seller-shorts-subrow"><span class="seller-shorts-k">연결 상품</span><span class="seller-shorts-v">산뜻한 플라워 블라우스</span></div>
                <div class="seller-shorts-subrow"><span class="seller-shorts-k">업로드일</span><span class="seller-shorts-v">2026.03.28</span></div>
                <div class="seller-shorts-subrow"><span class="seller-shorts-k">쇼츠번호</span><span class="seller-shorts-v">S-240328-0004</span></div>
              </div>
            </div>
            <div class="seller-shorts-right">
              <div class="seller-shorts-metrics" aria-label="성과(더미)">
                <div class="seller-shorts-metric"><span class="seller-shorts-metric-k">조회</span><span class="seller-shorts-metric-v">2,410</span></div>
                <div class="seller-shorts-metric"><span class="seller-shorts-metric-k">찜</span><span class="seller-shorts-metric-v">130</span></div>
                <div class="seller-shorts-metric"><span class="seller-shorts-metric-k">구매</span><span class="seller-shorts-metric-v">24</span></div>
              </div>
              <div class="seller-shorts-actions" aria-label="관리">
                <button type="button" class="seller-shorts-mini-btn" data-action="edit">수정</button>
                <button type="button" class="seller-shorts-mini-btn seller-shorts-mini-btn--toggle" data-action="toggle">비공개 전환</button>
                <button type="button" class="seller-shorts-mini-btn" data-action="product">연결 상품 보기</button>
              </div>
            </div>
          </article>
        </section>

        <div class="seller-shorts-pagination" aria-label="페이지네이션(더미)">
          <button type="button" class="seller-shorts-page-btn" data-page="prev">이전</button>
          <button type="button" class="seller-shorts-page-btn active" data-page="1">1</button>
          <button type="button" class="seller-shorts-page-btn" data-page="2">2</button>
          <button type="button" class="seller-shorts-page-btn" data-page="3">3</button>
          <button type="button" class="seller-shorts-page-btn" data-page="next">다음</button>
        </div>

        <section class="seller-card seller-shorts-empty" aria-label="빈 상태(더미)" hidden>
          <div class="seller-shorts-empty-inner">
            <div class="seller-shorts-empty-icon" aria-hidden="true">
              <span class="material-icons-outlined">smart_display</span>
            </div>
            <h3 class="seller-shorts-empty-title">아직 등록된 쇼츠가 없어요</h3>
            <p class="seller-shorts-empty-desc">새 쇼츠를 등록해 상품을 더 잘 보여주세요</p>
            <button type="button" class="seller-shorts-primary-btn" data-action="new-shorts">새 쇼츠 등록</button>
          </div>
        </section>
      </main>

      <jsp:include page="/WEB-INF/views/seller/layout/seller-footer.jsp" />
    </div>
  </div>

  <script>
    // 레이아웃 공통(더미) 동작: 헤더 버튼들
    (function () {
      var notifyBtn = document.getElementById('sellerHeaderNotifyBtn');
      var logoutBtn = document.getElementById('sellerHeaderLogoutBtn');
      if (notifyBtn) {
        notifyBtn.addEventListener('click', function () {
          alert('알림 기능은 아직 준비 중이에요.');
        });
      }
      if (logoutBtn) {
        logoutBtn.addEventListener('click', function () {
          alert('로그아웃은 아직 연동되지 않았어요. (더미)');
        });
      }
    })();
  </script>
  <script src="${pageContext.request.contextPath}/js/seller/shorts-list.js"></script>
</body>
</html>

