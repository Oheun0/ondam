<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>알림 설정</title>

  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/wallet.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-detail.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/notification-setting.css">
</head>
<body class="notification-setting-page" data-context-path="${pageContext.request.contextPath}">
  <div class="detail-shell">
    <div class="detail-page-inner detail-page-inner--sticky-header notification-setting-inner" id="notificationSettingRoot">
      <div class="notification-setting-sticky-head">
        <div class="notification-setting-header-wrap">
          <jsp:include page="/WEB-INF/views/layout/back-header.jsp"/>
          <h1 class="notification-setting-header-title">알림 설정</h1>
        </div>
      </div>

      <main class="notification-setting-main" aria-label="알림 설정">
        <section class="ns-card" aria-label="알림 목록">
          <button type="button"
                  class="ns-row is-on"
                  data-ns-key="order"
                  role="switch"
                  aria-checked="true"
                  aria-label="주문 알림">
            <div class="ns-left">
              <p class="ns-title">주문 알림</p>
              <p class="ns-desc">주문이 완료되면 알려드려요</p>
            </div>
            <span class="ns-switch" aria-hidden="true"><span class="ns-switch__thumb"></span></span>
          </button>

          <button type="button"
                  class="ns-row is-on"
                  data-ns-key="shipping"
                  role="switch"
                  aria-checked="true"
                  aria-label="배송 알림">
            <div class="ns-left">
              <p class="ns-title">배송 알림</p>
              <p class="ns-desc">배송이 시작되고 도착하면 알려드려요</p>
            </div>
            <span class="ns-switch" aria-hidden="true"><span class="ns-switch__thumb"></span></span>
          </button>

          <button type="button"
                  class="ns-row"
                  data-ns-key="group"
                  role="switch"
                  aria-checked="false"
                  aria-label="내 사람 알림">
            <div class="ns-left">
              <p class="ns-title">내 사람 알림</p>
              <p class="ns-desc">가족이 결제하거나 요청하면 알려드려요</p>
            </div>
            <span class="ns-switch" aria-hidden="true"><span class="ns-switch__thumb"></span></span>
          </button>

          <button type="button"
                  class="ns-row"
                  data-ns-key="recommend"
                  role="switch"
                  aria-checked="false"
                  aria-label="추천 알림">
            <div class="ns-left">
              <p class="ns-title">추천 알림</p>
              <p class="ns-desc">어울리는 옷을 추천해드려요</p>
            </div>
            <span class="ns-switch" aria-hidden="true"><span class="ns-switch__thumb"></span></span>
          </button>
        </section>

        <p class="ns-hint" aria-label="안내">
          * 화면 설계용 더미입니다. 실제 알림 구성대로 내용 추가/수정 부탁드립니다.
        </p>
      </main>
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/js/notification-setting.js"></script>
</body>
</html>

