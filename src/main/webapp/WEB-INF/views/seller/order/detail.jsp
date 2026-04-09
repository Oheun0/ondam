<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  request.setAttribute("sellerActiveMenu", "order");
  request.setAttribute("sellerPageTitle", "주문 상세");
%>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>주문 상세 | 온담 파트너</title>

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

      <main class="seller-content seller-order-detail-page" aria-label="주문 상세">
        <header class="seller-order-detail-head">
          <div>
            <h2 class="seller-order-title">주문 상세</h2>
            <p class="seller-order-sub">주문 정보와 배송 상태를 확인해 주세요</p>
          </div>
          <div>
            <a class="seller-order-detail-back" href="${pageContext.request.contextPath}/preview?page=seller/order/list">
              <span class="material-icons-outlined" aria-hidden="true">arrow_back</span>
              목록으로
            </a>
          </div>
        </header>

        <section class="seller-card seller-order-detail-summary" id="orderDetailRoot"
          data-order-no="20260408-0001"
          data-order-type="gift"
          data-wallet="true"
          data-status="ready"
          aria-label="주문 요약">
          <div class="seller-order-detail-summary-grid">
            <div class="seller-order-detail-kv">
              <div class="seller-order-detail-k">주문번호</div>
              <div class="seller-order-detail-v" id="odOrderNo">20260408-0001</div>
            </div>
            <div class="seller-order-detail-kv">
              <div class="seller-order-detail-k">주문일시</div>
              <div class="seller-order-detail-v">2026.04.08 14:30</div>
            </div>
            <div class="seller-order-detail-kv">
              <div class="seller-order-detail-k">현재 상태</div>
              <div class="seller-order-detail-v">
                <span class="seller-order-badge seller-order-badge--ready" id="odStatusBadge">배송 준비 중</span>
              </div>
            </div>
            <div class="seller-order-detail-kv">
              <div class="seller-order-detail-k">주문 유형</div>
              <div class="seller-order-detail-v">
                <span class="seller-order-type seller-order-type--gift" id="odTypePill">🎁 선물</span>
              </div>
            </div>
            <div class="seller-order-detail-kv">
              <div class="seller-order-detail-k">결제수단</div>
              <div class="seller-order-detail-v" id="odPayment">함께지갑 결제</div>
            </div>
          </div>
        </section>

        <section class="seller-card seller-order-detail-section" aria-label="주문 상품">
          <header class="seller-order-detail-section-head">
            <h3 class="seller-order-detail-section-title">주문 상품</h3>
          </header>

          <div class="seller-order-detail-items">
            <div class="seller-order-detail-item">
              <img class="seller-order-thumb" src="${pageContext.request.contextPath}/images/category/outer-cardigan.jpg" alt="상품 이미지">
              <div class="seller-order-detail-item-meta">
                <div class="seller-order-detail-item-name">부드러운 라운드 니트 가디건</div>
                <div class="seller-order-detail-item-sub">옵션: 아이보리 / 95</div>
                <div class="seller-order-detail-item-sub">수량: 1개</div>
              </div>
              <div class="seller-order-detail-item-price">39,000원</div>
            </div>

            <div class="seller-order-detail-item">
              <img class="seller-order-thumb" src="${pageContext.request.contextPath}/images/category/top-knit.jpg" alt="상품 이미지">
              <div class="seller-order-detail-item-meta">
                <div class="seller-order-detail-item-name">편안한 봄 니트 조끼</div>
                <div class="seller-order-detail-item-sub">옵션: 베이지 / FREE</div>
                <div class="seller-order-detail-item-sub">수량: 1개</div>
              </div>
              <div class="seller-order-detail-item-price">29,000원</div>
            </div>
          </div>
        </section>

        <section class="seller-card seller-order-detail-section" aria-label="배송 상태 처리">
          <header class="seller-order-detail-section-head seller-order-detail-section-head--row">
            <div>
              <h3 class="seller-order-detail-section-title">배송 상태 처리</h3>
              <p class="seller-order-detail-section-sub">현재 상태를 확인하고 필요 시 변경해 주세요 (더미)</p>
            </div>
            <div class="seller-order-detail-current">
              <span class="seller-order-detail-current-k">현재</span>
              <span class="seller-order-badge seller-order-badge--ready" id="odCurrentBadge">준비중</span>
            </div>
          </header>

          <div class="seller-order-detail-status-row">
            <div class="seller-order-detail-field">
              <label class="seller-order-detail-label" for="odNextStatus">상태 변경</label>
              <select id="odNextStatus" class="seller-order-detail-control">
                <option value="">변경할 상태를 선택해 주세요</option>
                <option value="paid">결제완료</option>
                <option value="ready">배송 준비 중</option>
                <option value="shipping">배송 중</option>
                <option value="done">배송 완료</option>
                <option value="cancel">취소</option>
              </select>
              <p class="seller-order-detail-error hidden" id="odStatusError" aria-live="polite"></p>
            </div>
            <button type="button" class="seller-order-btn seller-order-btn--primary" id="odApplyStatusBtn">상태 변경</button>
          </div>

          <div class="seller-order-detail-history" aria-label="상태 변경 이력(더미)">
            <h4 class="seller-order-detail-history-title">상태 변경 이력</h4>
            <ul class="seller-order-detail-history-list" id="odHistoryList">
              <li class="seller-order-detail-history-item"><span class="t">2026.04.08 14:30</span><span class="s">결제완료</span></li>
              <li class="seller-order-detail-history-item"><span class="t">2026.04.08 18:10</span><span class="s">배송 준비 중</span></li>
              <li class="seller-order-detail-history-item"><span class="t">2026.04.09 09:20</span><span class="s">배송 중</span></li>
            </ul>
          </div>
        </section>

        <section class="seller-card seller-order-detail-section" aria-label="배송 정보">
          <header class="seller-order-detail-section-head">
            <h3 class="seller-order-detail-section-title">배송 정보</h3>
          </header>

          <div class="seller-order-detail-ship-grid">
            <div class="seller-order-detail-field">
              <label class="seller-order-detail-label" for="odCarrier">택배사</label>
              <select id="odCarrier" class="seller-order-detail-control">
                <option value="">택배사를 선택해 주세요</option>
                <option value="CJ">CJ대한통운</option>
                <option value="LOTTE">롯데택배</option>
                <option value="HANJIN">한진택배</option>
                <option value="POST">우체국택배</option>
              </select>
              <p class="seller-order-detail-error hidden" id="odCarrierError" aria-live="polite"></p>
            </div>

            <div class="seller-order-detail-field">
              <label class="seller-order-detail-label" for="odTracking">송장번호</label>
              <input id="odTracking" class="seller-order-detail-control" type="text" placeholder="송장번호를 입력해 주세요">
              <p class="seller-order-detail-error hidden" id="odTrackingError" aria-live="polite"></p>
            </div>

            <div class="seller-order-detail-field seller-order-detail-field--btn">
              <button type="button" class="seller-order-btn seller-order-btn--primary" id="odSaveInvoiceBtn">송장 저장</button>
            </div>
          </div>

          <div class="seller-order-detail-addr">
            <div class="seller-order-detail-addr-row"><span class="k">수령인</span><span class="v">김지현</span></div>
            <div class="seller-order-detail-addr-row"><span class="k">연락처</span><span class="v">010-1234-5678</span></div>
            <div class="seller-order-detail-addr-row"><span class="k">주소</span><span class="v">부산광역시 부산진구 가야대로 123, 101호</span></div>
            <div class="seller-order-detail-addr-row"><span class="k">요청사항</span><span class="v">문 앞에 놓아주세요</span></div>
          </div>
        </section>

        <section class="seller-card seller-order-detail-section seller-order-extra-card hidden" id="odGiftCard" aria-label="선물 주문 정보">
          <header class="seller-order-detail-section-head">
            <h3 class="seller-order-detail-section-title">선물 주문 정보</h3>
          </header>
          <div class="seller-order-detail-extra">
            <div class="seller-order-detail-addr-row"><span class="k">수령인</span><span class="v">김가빈</span></div>
            <div class="seller-order-detail-addr-row"><span class="k">메시지</span><span class="v">생각나서 보내드렸어요</span></div>
            <p class="seller-order-detail-note">선물 주문은 수령인 배송지 기준으로 발송돼요.</p>
          </div>
        </section>

        <section class="seller-card seller-order-detail-section seller-order-extra-card hidden" id="odPokeCard" aria-label="조르기 주문 정보">
          <header class="seller-order-detail-section-head">
            <h3 class="seller-order-detail-section-title">조르기 주문 정보</h3>
          </header>
          <div class="seller-order-detail-extra">
            <div class="seller-order-detail-addr-row"><span class="k">요청자</span><span class="v">성연수</span></div>
            <div class="seller-order-detail-addr-row"><span class="k">요청 시각</span><span class="v">2026.04.07 20:10</span></div>
            <div class="seller-order-detail-addr-row"><span class="k">요청 메시지</span><span class="v">가볍게 외출할 때 입고 싶어요</span></div>
          </div>
        </section>

        <section class="seller-card seller-order-detail-section seller-order-extra-card hidden" id="odWalletCard" aria-label="함께지갑 결제 정보">
          <header class="seller-order-detail-section-head">
            <h3 class="seller-order-detail-section-title">함께지갑 결제 정보</h3>
          </header>
          <div class="seller-order-detail-extra">
            <div class="seller-order-detail-addr-row"><span class="k">결제 주체</span><span class="v">김지현</span></div>
            <div class="seller-order-detail-addr-row"><span class="k">결제 방식</span><span class="v">함께지갑 결제</span></div>
            <p class="seller-order-detail-note">함께지갑으로 결제된 주문입니다.</p>
          </div>
        </section>

        <div class="seller-order-detail-actions">
          <button type="button" class="seller-order-btn seller-order-btn--primary" id="odSaveStatusBtn">배송 상태 저장</button>
          <button type="button" class="seller-order-btn" id="odSaveInvoiceBtn2">송장 저장</button>
          <button type="button" class="seller-order-btn seller-order-btn--danger" id="odCancelBtn">주문 취소</button>
          <a class="seller-order-btn seller-order-btn--ghost" href="${pageContext.request.contextPath}/preview?page=seller/order/list">목록으로</a>
        </div>

        <p class="seller-order-detail-error seller-order-detail-error--form hidden" id="odFormError" aria-live="assertive"></p>
      </main>

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
  <script src="${pageContext.request.contextPath}/js/seller/order-detail.js"></script>
</body>
</html>

