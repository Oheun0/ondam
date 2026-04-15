<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<section class="seller-dashboard">
  <header class="seller-dashboard__top">
    <div>
      <h2 class="seller-dashboard__heading">대시보드</h2>
      <p class="seller-dashboard__sub">오늘의 판매 현황을 한눈에 확인해보세요</p>
    </div>
    <div class="seller-dashboard__date" aria-label="기준일">
      <span class="material-icons-outlined" aria-hidden="true">event</span>
      <span>${todayDateStr}</span>
    </div>
  </header>

  <div class="seller-dashboard__grid">
    <section class="seller-card seller-summary" aria-label="요약 현황">
      <div class="seller-summary__grid">
        <article class="seller-summary__item" data-action="orders-today">
          <div class="seller-summary__label">오늘 주문</div>
          <div class="seller-summary__value">${empty stats.todayOrderCount ? '0' : stats.todayOrderCount}<span class="seller-summary__unit">건</span></div>
        </article>
        <article class="seller-summary__item" data-action="ship-ready">
          <div class="seller-summary__label">배송 준비</div>
          <div class="seller-summary__value">${empty stats.shipReadyCount ? '0' : stats.shipReadyCount}<span class="seller-summary__unit">건</span></div>
        </article>
        <article class="seller-summary__item" data-action="inquiries">
          <div class="seller-summary__label">문의</div>
          <div class="seller-summary__value">${empty stats.inquiryCount ? '0' : stats.inquiryCount}<span class="seller-summary__unit">건</span></div>
        </article>
        <article class="seller-summary__item" data-action="reviews-new">
          <div class="seller-summary__label">신규 리뷰</div>
          <div class="seller-summary__value">${empty stats.reviewCount ? '0' : stats.reviewCount}<span class="seller-summary__unit">개</span></div>
        </article>
      </div>
    </section>

    <section class="seller-card seller-quick" aria-label="빠른 작업">
      <div class="seller-card__head">
        <h3 class="seller-card__title">빠른 작업</h3>
        <p class="seller-card__desc">자주 쓰는 작업을 빠르게 시작하세요</p>
      </div>
      <div class="seller-quick__grid">
        <button type="button" class="seller-quick__btn" data-action="new-product">
          <span class="material-icons-outlined" aria-hidden="true">add_box</span>
          <span>새 상품 등록</span>
        </button>
        <button type="button" class="seller-quick__btn" data-action="new-shorts">
          <span class="material-icons-outlined" aria-hidden="true">video_call</span>
          <span>새 쇼츠 등록</span>
        </button>
        <button type="button" class="seller-quick__btn" data-action="open-orders">
          <span class="material-icons-outlined" aria-hidden="true">receipt_long</span>
          <span>주문 확인하기</span>
        </button>
        <button type="button" class="seller-quick__btn" data-action="open-settlement">
          <span class="material-icons-outlined" aria-hidden="true">bar_chart</span>
          <span>매출 확인하기</span>
        </button>
      </div>
    </section>

    <section class="seller-card seller-orders" aria-label="최근 주문">
      <div class="seller-card__head seller-card__head--row">
        <div>
          <h3 class="seller-card__title">최근 주문</h3>
          <p class="seller-card__desc">선물/조르기/일반 주문을 한눈에 확인하세요</p>
        </div>
        <button type="button" class="seller-link-btn" data-action="open-orders">
          주문 관리로 이동
          <span class="material-icons-outlined" aria-hidden="true">chevron_right</span>
        </button>
      </div>

      <div class="seller-table-wrap">
        <table class="seller-table">
          <thead>
            <tr>
              <th scope="col">주문번호</th>
              <th scope="col">주문자</th>
              <th scope="col">주문유형</th>
              <th scope="col">결제수단</th>
              <th scope="col">상태</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="order" items="${recentOrders}">
        <tr>
          <td><a href="${pageContext.request.contextPath}/seller/order?action=detail&orderNo=${order.orderNo}">${order.orderNo}</a></td>
          <td>${order.receiverName}</td>
          <td>
            <c:choose>
              <c:when test="${order.orderType == 1}"><span class="seller-chip seller-chip--gift">선물</span></c:when>
              <c:when test="${order.orderType == 2}"><span class="seller-chip seller-chip--poke">조르기</span></c:when>
              <c:otherwise><span class="seller-chip">일반</span></c:otherwise>
            </c:choose>
          </td>
          <td>
             <span class="seller-chip ${order.paymentMethod == 0 ? 'seller-chip--wallet' : ''}">
               <c:choose>
                 <c:when test="${order.paymentMethod == 0}">함께지갑</c:when>
                 <c:when test="${order.paymentMethod == 1}">카드결제</c:when>
                 <c:otherwise>계좌이체</c:otherwise>
               </c:choose>
             </span>
          </td>
          <td>
            <c:set var="statusClass" value="${order.deliveryState == 0 ? 'paid' : order.deliveryState == 1 ? 'ready' : order.deliveryState == 2 ? 'shipping' : order.deliveryState == 3 ? 'done' : 'cancel'}" />
            <span class="seller-status seller-status--${statusClass}">
               <c:choose>
                 <c:when test="${order.deliveryState == 0}">결제 완료</c:when>
                 <c:when test="${order.deliveryState == 1}">배송 준비 중</c:when>
                 <c:when test="${order.deliveryState == 2}">배송 중</c:when>
                 <c:when test="${order.deliveryState == 3}">배송 완료</c:when>
                 <c:otherwise>취소됨</c:otherwise>
               </c:choose>
            </span>
          </td>
        </tr>
      </c:forEach>
      <c:if test="${empty recentOrders}">
        <tr><td colspan="5" style="text-align:center; padding: 40px 0;">최근 주문 내역이 없습니다.</td></tr>
      </c:if>
    </tbody>
  </table>
</div>
    </section>

    <section class="seller-card seller-ops" aria-label="운영 현황">
      <div class="seller-card__head">
        <h3 class="seller-card__title">쇼츠/상품 운영 현황</h3>
        <p class="seller-card__desc">사용자 탐색에 노출되는 상태를 요약해요</p>
      </div>
      <div class="seller-ops__list">
        <div class="seller-ops__item">
          <span class="seller-ops__label">현재 공개 중인 쇼츠</span>
          <span class="seller-ops__value">${empty stats.activeShortsCount ? '0' : stats.activeShortsCount}개</span>
        </div>
        <div class="seller-ops__item">
          <span class="seller-ops__label">품절 임박 상품 (재고 5개 이하)</span>
          <span class="seller-ops__value">${empty stats.lowStockCount ? '0' : stats.lowStockCount}개</span>
        </div>
      </div>
    </section>

    <section class="seller-card seller-notice" aria-label="공지 및 안내">
      <div class="seller-card__head">
        <h3 class="seller-card__title">공지 / 안내</h3>
        <p class="seller-card__desc">온담 서비스 특성을 반영한 운영 팁이에요</p>
      </div>
      <ul class="seller-notice__list">
        <li class="seller-notice__item">
          <span class="material-icons-outlined" aria-hidden="true">tips_and_updates</span>
          <span>쇼츠를 등록하면 사용자 탐색 화면에서 더 잘 보여요.</span>
        </li>
        <li class="seller-notice__item">
          <span class="material-icons-outlined" aria-hidden="true">sync_alt</span>
          <span>배송 상태를 변경하면 사용자 주문/배송 화면에 바로 반영돼요.</span>
        </li>
        <li class="seller-notice__item">
          <span class="material-icons-outlined" aria-hidden="true">auto_awesome</span>
          <span>AI 추천에 활용될 수 있도록 상품 태그를 꼼꼼히 입력해 주세요.</span>
        </li>
      </ul>
    </section>
  </div>
</section>