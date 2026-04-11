<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
  request.setAttribute("sellerActiveMenu", "order");
  request.setAttribute("sellerPageTitle", "주문 관리");
%>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>주문 관리 | 온담 파트너</title>

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
          <c:if test="${empty orderList}">
		    <article class="seller-order-empty">
		      <div class="seller-order-empty-inner">
		        <div class="seller-order-empty-icon" aria-hidden="true">
		          <span class="material-icons-outlined">receipt_long</span>
		        </div>
		        <h3 class="seller-order-empty-title">주문 내역이 없어요</h3>
		        <p class="seller-order-empty-desc">주문이 발생하면 여기에 표시됩니다</p>
		      </div>
		    </article>
		  </c:if>
		  <c:forEach var="order" items="${orderList}">
		    <c:set var="statusData" value="" />
		    <c:set var="statusClass" value="" />
		    <c:set var="statusText" value="" />
		    
		    <c:choose>
		      <c:when test="${order.deliveryState == 0}">
		        <c:set var="statusData" value="paid"/>
		        <c:set var="statusClass" value="seller-order-badge--paid"/>
		        <c:set var="statusText" value="결제완료"/>
		      </c:when>
		      <c:when test="${order.deliveryState == 1}">
		        <c:set var="statusData" value="ready"/>
		        <c:set var="statusClass" value="seller-order-badge--ready"/>
		        <c:set var="statusText" value="준비중"/>
		      </c:when>
		      <c:when test="${order.deliveryState == 2}">
		        <c:set var="statusData" value="shipping"/>
		        <c:set var="statusClass" value="seller-order-badge--shipping"/>
		        <c:set var="statusText" value="배송중"/>
		      </c:when>
		      <c:when test="${order.deliveryState == 3}">
		        <c:set var="statusData" value="done"/>
		        <c:set var="statusClass" value="seller-order-badge--done"/>
		        <c:set var="statusText" value="완료"/>
		      </c:when>
		      <c:otherwise>
		        <c:set var="statusData" value="cancel"/>
		        <c:set var="statusClass" value="seller-order-badge--cancel"/>
		        <c:set var="statusText" value="취소"/>
		      </c:otherwise>
		    </c:choose>
		
		    <article class="seller-order-card" data-status="${statusData}" data-order-no="${order.orderNo}">
		      <header class="seller-order-header">
		        <div class="seller-order-no">${order.orderNo}</div>
		        <div class="seller-order-date">${order.orderDate}</div>
		      </header>
		
		      <div class="seller-order-body">
		        <div class="seller-order-product">
		          <img class="seller-order-thumb" src="${pageContext.request.contextPath}/images/product/${order.repProductImage}" alt="상품 이미지" onerror="this.src='${pageContext.request.contextPath}/images/default_thumb.png'">
		          
		          <div class="seller-order-product-meta">
		            <div class="seller-order-product-name">${order.repProductName}</div>
		            <div class="seller-order-product-sub">총 수량: ${order.totalQuantity}개</div>
		          </div>
		        </div>
		
		        <div class="seller-order-meta">
		          <div class="seller-order-meta-row">
		            <span class="seller-order-meta-k">결제</span>
		            <span class="seller-order-meta-v">
		              <c:choose>
		                <c:when test="${order.paymentMethod == 0}">함께지갑 결제</c:when>
		                <c:when test="${order.paymentMethod == 1}">카드 결제</c:when>
		                <c:when test="${order.paymentMethod == 2}">계좌이체</c:when>
		                <c:otherwise>기타</c:otherwise>
		              </c:choose>
		            </span>
		          </div>
		          <div class="seller-order-meta-row">
		            <span class="seller-order-meta-k">유형</span>
		            <span class="seller-order-meta-v">
		              <c:choose>
		                <c:when test="${order.orderType == 0}"><span class="seller-order-type">일반</span></c:when>
		                <c:when test="${order.orderType == 1}"><span class="seller-order-type seller-order-type--poke">💬 조르기</span></c:when>
		                <c:when test="${order.orderType == 2}"><span class="seller-order-type seller-order-type--gift">🎁 선물</span></c:when>
		              </c:choose>
		            </span>
		          </div>
		          <div class="seller-order-meta-row">
		            <span class="seller-order-meta-k">요청사항</span>
		            <span class="seller-order-meta-v seller-order-request">
		              ${empty order.deliveryContent ? '-' : order.deliveryContent}
		            </span>
		          </div>
		        </div>
		
		        <div class="seller-order-side">
		          <div class="seller-order-badges">
		            <span class="seller-order-badge ${statusClass}">${statusText}</span>
		          </div>
		          
		          <div class="seller-order-actions">
		            <button type="button" class="seller-order-btn" data-action="detail">상세보기</button>
		            <c:if test="${statusData == 'paid'}">
		              <button type="button" class="seller-order-btn seller-order-btn--primary" data-action="ready">준비 처리</button>
		            </c:if>
		            <c:if test="${statusData == 'ready'}">
		              <button type="button" class="seller-order-btn seller-order-btn--primary" data-action="shipStart">배송 시작</button>
		            </c:if>
		            <c:if test="${statusData == 'shipping'}">
		              <button type="button" class="seller-order-btn seller-order-btn--primary" data-action="shipDone">배송 완료</button>
		            </c:if>
		          </div>
		        </div>
		      </div>
		    </article>
		  </c:forEach>
		</section>

        <div class="seller-order-pagination" aria-label="페이지네이션(더미)">
          <button type="button" class="seller-order-page-btn" data-page="prev">이전</button>
          <button type="button" class="seller-order-page-btn active" data-page="1">1</button>
          <button type="button" class="seller-order-page-btn" data-page="2">2</button>
          <button type="button" class="seller-order-page-btn" data-page="3">3</button>
          <button type="button" class="seller-order-page-btn" data-page="next">다음</button>
        </div>
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

