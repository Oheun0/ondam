<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
            <a class="seller-order-detail-back" href="${pageContext.request.contextPath}/seller/order?action=list">
              <span class="material-icons-outlined" aria-hidden="true">arrow_back</span>
              목록으로
            </a>
          </div>
        </header>

        <c:set var="statusData" value="${detail.deliveryState == 0 ? 'paid' : detail.deliveryState == 1 ? 'ready' : detail.deliveryState == 2 ? 'shipping' : detail.deliveryState == 3 ? 'done' : 'cancel'}" />
        <c:set var="typeData" value="${detail.orderType == 0 ? 'normal' : detail.orderType == 1 ? 'poke' : 'gift'}" />
        
        <section class="seller-card seller-order-detail-summary" id="orderDetailRoot"
          data-order-no="${detail.orderNo}"
          data-order-type="${typeData}"
          data-wallet="${detail.paymentMethod == 0 ? 'true' : 'false'}"
          data-status="${statusData}"
          aria-label="주문 요약">
          
          <div class="seller-order-detail-summary-grid">
            <div class="seller-order-detail-kv">
              <div class="seller-order-detail-k">주문번호</div>
              <div class="seller-order-detail-v" id="odOrderNo">${detail.orderNo}</div>
            </div>
            <div class="seller-order-detail-kv">
              <div class="seller-order-detail-k">주문일시</div>
              <div class="seller-order-detail-v">${detail.orderDate}</div>
            </div>
            <div class="seller-order-detail-kv">
              <div class="seller-order-detail-k">현재 상태</div>
              <div class="seller-order-detail-v">
                <span class="seller-order-badge seller-order-badge--${statusData}" id="odStatusBadge">
                  ${detail.deliveryState == 0 ? '결제완료' : detail.deliveryState == 1 ? '배송 준비 중' : detail.deliveryState == 2 ? '배송 중' : detail.deliveryState == 3 ? '배송 완료' : '취소'}
                </span>
              </div>
            </div>
            <div class="seller-order-detail-kv">
              <div class="seller-order-detail-k">주문 유형</div>
              <div class="seller-order-detail-v">
                <c:choose>
                    <c:when test="${detail.orderType == 0}"><span class="seller-order-type" id="odTypePill">일반</span></c:when>
                    <c:when test="${detail.orderType == 1}"><span class="seller-order-type seller-order-type--poke" id="odTypePill">💬 조르기</span></c:when>
                    <c:when test="${detail.orderType == 2}"><span class="seller-order-type seller-order-type--gift" id="odTypePill">🎁 선물</span></c:when>
                </c:choose>
              </div>
            </div>
            <div class="seller-order-detail-kv">
              <div class="seller-order-detail-k">결제수단</div>
              <div class="seller-order-detail-v" id="odPayment">
                ${detail.paymentMethod == 0 ? '함께지갑 결제' : detail.paymentMethod == 1 ? '카드 결제' : '계좌이체'}
              </div>
            </div>
          </div>
        </section>

        <section class="seller-card seller-order-detail-section" aria-label="주문 상품">
          <header class="seller-order-detail-section-head">
            <h3 class="seller-order-detail-section-title">주문 상품</h3>
          </header>

          <div class="seller-order-detail-items">
            <c:forEach var="item" items="${detail.itemList}">
              <div class="seller-order-detail-item">
                <img class="seller-order-thumb" 
				     src="${pageContext.request.contextPath}/uploads/products/${item.productImage}" 
				     alt="상품 이미지" 
				     onerror="this.src='${pageContext.request.contextPath}/images/no-image.png'">
                <div class="seller-order-detail-item-meta">
                  <div class="seller-order-detail-item-name">${item.productName}</div>
                  <div class="seller-order-detail-item-sub">옵션: ${empty item.optionColor ? '기본' : item.optionColor} / ${empty item.optionSize ? 'FREE' : item.optionSize}</div>
                  <div class="seller-order-detail-item-sub">수량: ${item.quantity}개</div>
                </div>
                <div class="seller-order-detail-item-price"><fmt:formatNumber value="${item.price}" pattern="#,###"/>원</div>
              </div>
            </c:forEach>
          </div>
        </section>

        <section class="seller-card seller-order-detail-section" aria-label="배송 상태 처리">
          <header class="seller-order-detail-section-head seller-order-detail-section-head--row">
            <div>
              <h3 class="seller-order-detail-section-title">배송 상태 처리</h3>
              <p class="seller-order-detail-section-sub">현재 상태를 확인하고 필요 시 변경해 주세요</p>
            </div>
            <div class="seller-order-detail-current">
              <span class="seller-order-detail-current-k">현재</span>
              <span class="seller-order-badge seller-order-badge--${statusData}" id="odCurrentBadge">
                ${detail.deliveryState == 0 ? '결제완료' : detail.deliveryState == 1 ? '준비중' : detail.deliveryState == 2 ? '배송중' : detail.deliveryState == 3 ? '완료' : '취소'}
              </span>
            </div>
          </header>

          <div class="seller-order-detail-status-row">
            <div class="seller-order-detail-field">
              <label class="seller-order-detail-label" for="odNextStatus">상태 변경</label>
              <select id="odNextStatus" class="seller-order-detail-control">
                <option value="">변경할 상태를 선택해 주세요</option>
                <option value="paid" ${statusData == 'paid' ? 'selected' : ''}>결제완료</option>
                <option value="ready" ${statusData == 'ready' ? 'selected' : ''}>배송 준비 중</option>
                <option value="shipping" ${statusData == 'shipping' ? 'selected' : ''}>배송 중</option>
                <option value="done" ${statusData == 'done' ? 'selected' : ''}>배송 완료</option>
                <option value="cancel" ${statusData == 'cancel' ? 'selected' : ''}>취소</option>
              </select>
              <p class="seller-order-detail-error hidden" id="odStatusError" aria-live="polite"></p>
            </div>
            <button type="button" class="seller-order-btn seller-order-btn--primary" id="odApplyStatusBtn">상태 변경</button>
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
                <option value="CJ" ${detail.courier == 'CJ' ? 'selected' : ''}>CJ대한통운</option>
                <option value="LOTTE" ${detail.courier == 'LOTTE' ? 'selected' : ''}>롯데택배</option>
                <option value="HANJIN" ${detail.courier == 'HANJIN' ? 'selected' : ''}>한진택배</option>
                <option value="POST" ${detail.courier == 'POST' ? 'selected' : ''}>우체국택배</option>
              </select>
              <p class="seller-order-detail-error hidden" id="odCarrierError" aria-live="polite"></p>
            </div>

            <div class="seller-order-detail-field">
              <label class="seller-order-detail-label" for="odTracking">송장번호</label>
              <input id="odTracking" class="seller-order-detail-control" type="text" value="${detail.trackingNo}" placeholder="송장번호를 입력해 주세요">
              <p class="seller-order-detail-error hidden" id="odTrackingError" aria-live="polite"></p>
            </div>

            <div class="seller-order-detail-field seller-order-detail-field--btn">
              <button type="button" class="seller-order-btn seller-order-btn--primary" id="odSaveInvoiceBtn">송장 저장</button>
            </div>
          </div>

          <div class="seller-order-detail-addr">
            <div class="seller-order-detail-addr-row"><span class="k">수령인</span><span class="v">${detail.receiverName}</span></div>
            <div class="seller-order-detail-addr-row"><span class="k">연락처</span><span class="v">${detail.receiverTel}</span></div>
            <div class="seller-order-detail-addr-row"><span class="k">주소</span><span class="v">${detail.deliveryAddr}</span></div>
            <div class="seller-order-detail-addr-row"><span class="k">요청사항</span><span class="v">${empty detail.deliveryContent ? '-' : detail.deliveryContent}</span></div>
          </div>
        </section>

        <section class="seller-card seller-order-detail-section seller-order-extra-card hidden" id="odGiftCard" aria-label="선물 주문 정보">
          <header class="seller-order-detail-section-head">
            <h3 class="seller-order-detail-section-title">선물 주문 정보</h3>
          </header>
          <div class="seller-order-detail-extra">
            <p class="seller-order-detail-note">선물 주문은 수령인 배송지 기준으로 발송돼요.</p>
          </div>
        </section>

        <section class="seller-card seller-order-detail-section seller-order-extra-card hidden" id="odPokeCard" aria-label="조르기 주문 정보">
          <header class="seller-order-detail-section-head">
            <h3 class="seller-order-detail-section-title">조르기 주문 정보</h3>
          </header>
          <div class="seller-order-detail-extra">
             <p class="seller-order-detail-note">조르기 기반으로 생성된 주문입니다.</p>
          </div>
        </section>

        <section class="seller-card seller-order-detail-section seller-order-extra-card hidden" id="odWalletCard" aria-label="함께지갑 결제 정보">
          <header class="seller-order-detail-section-head">
            <h3 class="seller-order-detail-section-title">함께지갑 결제 정보</h3>
          </header>
          <div class="seller-order-detail-extra">
            <div class="seller-order-detail-addr-row"><span class="k">결제 방식</span><span class="v">함께지갑 결제</span></div>
            <p class="seller-order-detail-note">함께지갑으로 결제된 주문입니다.</p>
          </div>
        </section>

        <div class="seller-order-detail-actions">
          <button type="button" class="seller-order-btn seller-order-btn--primary" id="odSaveStatusBtn">배송 상태 저장</button>
          <button type="button" class="seller-order-btn" id="odSaveInvoiceBtn2">송장 저장</button>
          <button type="button" class="seller-order-btn seller-order-btn--danger" id="odCancelBtn">주문 취소</button>
          <a class="seller-order-btn seller-order-btn--ghost" href="${pageContext.request.contextPath}/seller/order?action=list">목록으로</a>
        </div>

        <p class="seller-order-detail-error seller-order-detail-error--form hidden" id="odFormError" aria-live="assertive"></p>
      </main>

      <jsp:include page="/WEB-INF/views/seller/layout/seller-footer.jsp" />
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/js/seller/order-detail.js"></script>
</body>
</html>