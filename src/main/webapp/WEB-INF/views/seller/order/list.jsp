<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  request.setAttribute("sellerActiveMenu", "order");
  request.setAttribute("sellerPageTitle", "주문 관리");
%>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>주문 관리 | 온담 판매자센터</title>

  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Material+Icons+Outlined&display=swap" rel="stylesheet">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-layout.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-order.css" />
</head>
<body class="seller-app" data-context-path="${pageContext.request.contextPath}">
  <div class="seller-layout">
    <jsp:include page="/WEB-INF/views/seller/layout/seller-sidebar.jsp" />

    <div class="seller-main-area">
      <jsp:include page="/WEB-INF/views/seller/layout/seller-header.jsp" />

      <main class="seller-content seller-order-page" aria-label="주문 관리">
        <header class="seller-order-head">
          <div>
            <h2 class="seller-order-title">주문 관리</h2>
            <p class="seller-order-sub">주문 상태를 확인하고 배송을 처리할 수 있어요</p>
          </div>
        </header>

        <section class="seller-order-summary" aria-label="요약">
          <div class="seller-order-summary-grid">
            <div class="seller-order-summary-card">
              <div class="seller-order-summary-label">오늘 주문</div>
              <div class="seller-order-summary-value">12<span class="seller-order-summary-unit">건</span></div>
            </div>
            <div class="seller-order-summary-card">
              <div class="seller-order-summary-label">배송중</div>
              <div class="seller-order-summary-value">5<span class="seller-order-summary-unit">건</span></div>
            </div>
            <div class="seller-order-summary-card">
              <div class="seller-order-summary-label">취소</div>
              <div class="seller-order-summary-value">1<span class="seller-order-summary-unit">건</span></div>
            </div>
          </div>
        </section>

        <nav class="seller-order-tabs" aria-label="주문 상태 탭">
          <button type="button" class="seller-order-tab active" data-status="all" aria-current="true">전체</button>
          <button type="button" class="seller-order-tab" data-status="paid">결제완료</button>
          <button type="button" class="seller-order-tab" data-status="ready">준비중</button>
          <button type="button" class="seller-order-tab" data-status="shipping">배송중</button>
          <button type="button" class="seller-order-tab" data-status="done">완료</button>
          <button type="button" class="seller-order-tab" data-status="cancel">취소</button>
        </nav>

        <section class="seller-order-list" aria-label="주문 목록">
          <article class="seller-order-card" data-status="ready" data-order-no="20260408-0001">
            <header class="seller-order-header">
              <div class="seller-order-no">20260408-0001</div>
              <div class="seller-order-date">2026.04.08</div>
            </header>

            <div class="seller-order-body">
              <div class="seller-order-product">
                <img class="seller-order-thumb" src="${pageContext.request.contextPath}/images/category/outer-cardigan.jpg" alt="상품 이미지">
                <div class="seller-order-product-meta">
                  <div class="seller-order-product-name">부드러운 라운드 니트 가디건 <span class="seller-order-more">외 1건</span></div>
                  <div class="seller-order-product-sub">수량: 2개</div>
                </div>
              </div>

              <div class="seller-order-meta">
                <div class="seller-order-meta-row">
                  <span class="seller-order-meta-k">결제</span>
                  <span class="seller-order-meta-v">함께지갑 결제</span>
                </div>
                <div class="seller-order-meta-row">
                  <span class="seller-order-meta-k">유형</span>
                  <span class="seller-order-meta-v"><span class="seller-order-type seller-order-type--gift">🎁 선물</span></span>
                </div>
                <div class="seller-order-meta-row">
                  <span class="seller-order-meta-k">요청사항</span>
                  <span class="seller-order-meta-v seller-order-request">문 앞에 놓아주세요</span>
                </div>
              </div>

              <div class="seller-order-side">
                <div class="seller-order-badges">
                  <span class="seller-order-badge seller-order-badge--ready">준비중</span>
                </div>
                <div class="seller-order-actions">
                  <button type="button" class="seller-order-btn" data-action="detail">상세보기</button>
                  <button type="button" class="seller-order-btn seller-order-btn--primary" data-action="shipStart">배송 시작</button>
                </div>
              </div>
            </div>
          </article>

          <article class="seller-order-card" data-status="paid" data-order-no="20260408-0002">
            <header class="seller-order-header">
              <div class="seller-order-no">20260408-0002</div>
              <div class="seller-order-date">2026.04.08</div>
            </header>

            <div class="seller-order-body">
              <div class="seller-order-product">
                <img class="seller-order-thumb" src="${pageContext.request.contextPath}/images/category/top-shirts.jpg" alt="상품 이미지">
                <div class="seller-order-product-meta">
                  <div class="seller-order-product-name">가벼운 데일리 셔츠</div>
                  <div class="seller-order-product-sub">수량: 1개</div>
                </div>
              </div>

              <div class="seller-order-meta">
                <div class="seller-order-meta-row">
                  <span class="seller-order-meta-k">결제</span>
                  <span class="seller-order-meta-v">카드 결제</span>
                </div>
                <div class="seller-order-meta-row">
                  <span class="seller-order-meta-k">유형</span>
                  <span class="seller-order-meta-v"><span class="seller-order-type">일반</span></span>
                </div>
                <div class="seller-order-meta-row">
                  <span class="seller-order-meta-k">요청사항</span>
                  <span class="seller-order-meta-v seller-order-request">전화 주세요</span>
                </div>
              </div>

              <div class="seller-order-side">
                <div class="seller-order-badges">
                  <span class="seller-order-badge seller-order-badge--paid">결제완료</span>
                </div>
                <div class="seller-order-actions">
                  <button type="button" class="seller-order-btn" data-action="detail">상세보기</button>
                  <button type="button" class="seller-order-btn seller-order-btn--primary" data-action="ready">준비 처리</button>
                </div>
              </div>
            </div>
          </article>

          <article class="seller-order-card" data-status="shipping" data-order-no="20260407-0048">
            <header class="seller-order-header">
              <div class="seller-order-no">20260407-0048</div>
              <div class="seller-order-date">2026.04.07</div>
            </header>

            <div class="seller-order-body">
              <div class="seller-order-product">
                <img class="seller-order-thumb" src="${pageContext.request.contextPath}/images/category/bottom-long.jpg" alt="상품 이미지">
                <div class="seller-order-product-meta">
                  <div class="seller-order-product-name">가볍게 입는 데일리 팬츠</div>
                  <div class="seller-order-product-sub">수량: 1개</div>
                </div>
              </div>

              <div class="seller-order-meta">
                <div class="seller-order-meta-row">
                  <span class="seller-order-meta-k">결제</span>
                  <span class="seller-order-meta-v">카드 결제</span>
                </div>
                <div class="seller-order-meta-row">
                  <span class="seller-order-meta-k">유형</span>
                  <span class="seller-order-meta-v"><span class="seller-order-type seller-order-type--poke">💬 조르기</span></span>
                </div>
                <div class="seller-order-meta-row">
                  <span class="seller-order-meta-k">요청사항</span>
                  <span class="seller-order-meta-v seller-order-request">경비실에 맡겨주세요</span>
                </div>
              </div>

              <div class="seller-order-side">
                <div class="seller-order-badges">
                  <span class="seller-order-badge seller-order-badge--shipping">배송중</span>
                </div>
                <div class="seller-order-actions">
                  <button type="button" class="seller-order-btn" data-action="detail">상세보기</button>
                  <button type="button" class="seller-order-btn seller-order-btn--primary" data-action="shipDone">배송 완료</button>
                </div>
              </div>
            </div>
          </article>

          <article class="seller-order-card" data-status="done" data-order-no="20260407-0032">
            <header class="seller-order-header">
              <div class="seller-order-no">20260407-0032</div>
              <div class="seller-order-date">2026.04.07</div>
            </header>

            <div class="seller-order-body">
              <div class="seller-order-product">
                <img class="seller-order-thumb" src="${pageContext.request.contextPath}/images/category/oneset-onepiece.jpg" alt="상품 이미지">
                <div class="seller-order-product-meta">
                  <div class="seller-order-product-name">봄날 산책 원피스 세트</div>
                  <div class="seller-order-product-sub">수량: 1개</div>
                </div>
              </div>

              <div class="seller-order-meta">
                <div class="seller-order-meta-row">
                  <span class="seller-order-meta-k">결제</span>
                  <span class="seller-order-meta-v">함께지갑 결제</span>
                </div>
                <div class="seller-order-meta-row">
                  <span class="seller-order-meta-k">유형</span>
                  <span class="seller-order-meta-v"><span class="seller-order-type">일반</span></span>
                </div>
                <div class="seller-order-meta-row">
                  <span class="seller-order-meta-k">요청사항</span>
                  <span class="seller-order-meta-v seller-order-request">-</span>
                </div>
              </div>

              <div class="seller-order-side">
                <div class="seller-order-badges">
                  <span class="seller-order-badge seller-order-badge--done">완료</span>
                </div>
                <div class="seller-order-actions">
                  <button type="button" class="seller-order-btn" data-action="detail">상세보기</button>
                </div>
              </div>
            </div>
          </article>

          <article class="seller-order-card" data-status="cancel" data-order-no="20260406-0009">
            <header class="seller-order-header">
              <div class="seller-order-no">20260406-0009</div>
              <div class="seller-order-date">2026.04.06</div>
            </header>

            <div class="seller-order-body">
              <div class="seller-order-product">
                <img class="seller-order-thumb" src="${pageContext.request.contextPath}/images/category/top-knit.jpg" alt="상품 이미지">
                <div class="seller-order-product-meta">
                  <div class="seller-order-product-name">편안한 봄 니트 조끼</div>
                  <div class="seller-order-product-sub">수량: 1개</div>
                </div>
              </div>

              <div class="seller-order-meta">
                <div class="seller-order-meta-row">
                  <span class="seller-order-meta-k">결제</span>
                  <span class="seller-order-meta-v">계좌이체</span>
                </div>
                <div class="seller-order-meta-row">
                  <span class="seller-order-meta-k">유형</span>
                  <span class="seller-order-meta-v"><span class="seller-order-type seller-order-type--gift">🎁 선물</span></span>
                </div>
                <div class="seller-order-meta-row">
                  <span class="seller-order-meta-k">요청사항</span>
                  <span class="seller-order-meta-v seller-order-request">취소 요청 (더미)</span>
                </div>
              </div>

              <div class="seller-order-side">
                <div class="seller-order-badges">
                  <span class="seller-order-badge seller-order-badge--cancel">취소</span>
                </div>
                <div class="seller-order-actions">
                  <button type="button" class="seller-order-btn" data-action="detail">상세보기</button>
                </div>
              </div>
            </div>
          </article>
        </section>

        <div class="seller-order-pagination" aria-label="페이지네이션(더미)">
          <button type="button" class="seller-order-page-btn" data-page="prev">이전</button>
          <button type="button" class="seller-order-page-btn active" data-page="1">1</button>
          <button type="button" class="seller-order-page-btn" data-page="2">2</button>
          <button type="button" class="seller-order-page-btn" data-page="3">3</button>
          <button type="button" class="seller-order-page-btn" data-page="next">다음</button>
        </div>

        <section class="seller-card seller-order-empty" aria-label="빈 상태(더미)" hidden>
          <div class="seller-order-empty-inner">
            <div class="seller-order-empty-icon" aria-hidden="true">
              <span class="material-icons-outlined">receipt_long</span>
            </div>
            <h3 class="seller-order-empty-title">주문 내역이 없어요</h3>
            <p class="seller-order-empty-desc">주문이 발생하면 여기에 표시됩니다</p>
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
  <script src="${pageContext.request.contextPath}/js/seller/order-list.js"></script>
</body>
</html>

