<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--
  Seller Notification Panel (global)
  - 모달이 아닌 우측 슬라이드 패널
  - JS가 더미 데이터로 리스트/상세를 렌더링
-->
<aside class="seller-notification-panel hidden" id="sellerNotificationPanel" aria-hidden="true" aria-label="알림 패널">
  <div class="seller-notification-panel__dim" id="sellerNotificationDim" aria-hidden="true"></div>
  <div class="seller-notification-panel__sheet" role="complementary" aria-label="알림 상세">
    <header class="seller-notification-panel__head">
      <div class="seller-notification-panel__head-left">
        <h3 class="seller-notification-panel__title">알림</h3>
        <button type="button" class="seller-notification-panel__allread" id="sellerNotificationAllReadBtn">모두 읽음 처리</button>
      </div>
      <button type="button" class="seller-notification-panel__close" id="sellerNotificationCloseBtn" aria-label="닫기">
        <span class="material-icons-outlined" aria-hidden="true">close</span>
      </button>
    </header>

    <!-- Tabs -->
    <nav class="seller-notification-tabs" aria-label="알림 필터">
      <button type="button" class="seller-notification-tab active" data-kind="all">전체</button>
      <button type="button" class="seller-notification-tab" data-kind="inquiry">문의</button>
      <button type="button" class="seller-notification-tab" data-kind="order">주문</button>
      <button type="button" class="seller-notification-tab" data-kind="review">리뷰</button>
    </nav>

    <div class="seller-notification-panel__body">
      <!-- List view -->
      <section class="seller-notification-view seller-notification-view--list" id="sellerNotificationListView" aria-label="알림 목록">
        <div class="seller-notification-list" id="sellerNotificationList"></div>
        <div class="seller-notification-empty hidden" id="sellerNotificationEmpty">
          <div class="seller-notification-empty__icon" aria-hidden="true">
            <span class="material-icons-outlined">notifications</span>
          </div>
          <div class="seller-notification-empty__title">알림이 없어요</div>
          <div class="seller-notification-empty__desc">새 알림이 생기면 여기에 표시됩니다</div>
        </div>
      </section>

      <!-- Detail view (방법 A: 내부 상세 전환) -->
      <section class="seller-notification-view seller-notification-view--detail hidden" id="sellerNotificationDetailView" aria-label="알림 상세">
        <div class="seller-notification-detail-head">
          <button type="button" class="seller-notification-back" id="sellerNotificationBackBtn">
            <span class="material-icons-outlined" aria-hidden="true">arrow_back</span>
            목록
          </button>
          <div class="seller-notification-detail-meta">
            <span class="seller-notification-kind-badge" id="sellerNotificationDetailKind">-</span>
            <span class="seller-notification-date" id="sellerNotificationDetailDate">-</span>
          </div>
        </div>

        <div class="seller-notification-detail" id="sellerNotificationDetail"></div>
      </section>
    </div>
  </div>
</aside>

