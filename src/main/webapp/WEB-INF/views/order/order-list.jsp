<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  // 하단바 활성 탭(더미): 필요 시 변경
  request.setAttribute("bottomNav", "home");
%>
<%-- 더미: 주문이 없는 화면 확인 시 true 로 변경 --%>
<c:set var="orderDummyEmpty" value="false"/>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>주문 / 배송</title>

  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-detail.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/order-list.css">
</head>
<body class="order-list-page" data-context-path="${pageContext.request.contextPath}">
  <div class="detail-shell">
    <div class="detail-page-inner detail-page-inner--sticky-header order-list-inner" id="orderListRoot">
      <div class="order-list-sticky-head">
        <div class="order-list-header-wrap">
          <jsp:include page="/WEB-INF/views/layout/back-header.jsp"/>
          <h1 class="order-list-header-title">주문 / 배송</h1>
        </div>
      </div>

      <main class="order-list-main" aria-label="주문 및 배송 목록">
        <!-- 상단 상태 요약 -->
        <section class="ol-summary-card" aria-label="주문 상태 요약">
          <div class="ol-summary-item">
            <p class="ol-summary-label">배송중</p>
            <p class="ol-summary-value">1</p>
          </div>
          <div class="ol-summary-item">
            <p class="ol-summary-label">배송완료</p>
            <p class="ol-summary-value">2</p>
          </div>
          <div class="ol-summary-item">
            <p class="ol-summary-label">취소/반품</p>
            <p class="ol-summary-value">0</p>
          </div>
        </section>

        <c:choose>
          <c:when test="${orderDummyEmpty}">
            <!-- 빈 상태 -->
            <section class="ol-empty section-box" aria-label="주문 없음">
              <p class="ol-empty-title">아직 주문한 상품이 없어요</p>
              <p class="ol-empty-sub">마음에 드는 상품을 주문해 보세요</p>
              <a href="${pageContext.request.contextPath}/main" class="ol-empty-btn">쇼핑하러 가기</a>
            </section>
          </c:when>
          <c:otherwise>
            <!-- 주문 카드 리스트 -->
            <section class="ol-list" aria-label="주문 목록">
              <!-- 주문 1 (최신) -->
              <article class="ol-card" aria-label="주문 2026.04.07">
                <div class="ol-card-top">
                  <p class="ol-date">2026.04.07</p>
                  <button type="button" class="ol-detail-btn" data-order-id="20260407-0001">상세보기</button>
                </div>

                <div class="ol-items" role="list" aria-label="주문 상품">
                  <div class="ol-item" role="listitem">
                    <div class="ol-thumb">
                      <img src="${pageContext.request.contextPath}/images/category/type-top-knit.jpg" alt="" loading="lazy"/>
                    </div>
                    <div class="ol-info">
                      <p class="ol-item-status">배송중</p>
                      <p class="ol-name">부드러운 라운드 니트 가디건</p>
                      <p class="ol-opt">노란색 / 90</p>
                    </div>
                  </div>
                  <div class="ol-item" role="listitem">
                    <div class="ol-thumb">
                      <img src="${pageContext.request.contextPath}/images/category/type-top-knit.jpg" alt="" loading="lazy"/>
                    </div>
                    <div class="ol-info">
                      <p class="ol-item-status">주문완료</p>
                      <p class="ol-name">편안한 봄 니트 조끼</p>
                      <p class="ol-opt">베이지 / 95</p>
                    </div>
                  </div>
                </div>
              </article>

              <!-- 주문 2 -->
              <article class="ol-card" aria-label="주문 2026.04.05">
                <div class="ol-card-top">
                  <p class="ol-date">2026.04.05</p>
                  <button type="button" class="ol-detail-btn" data-order-id="20260405-0003">상세보기</button>
                </div>

                <div class="ol-items" role="list" aria-label="주문 상품">
                  <div class="ol-item" role="listitem">
                    <div class="ol-thumb">
                      <img src="${pageContext.request.contextPath}/images/category/type-top-knit.jpg" alt="" loading="lazy"/>
                    </div>
                    <div class="ol-info">
                      <p class="ol-item-status">배송완료</p>
                      <p class="ol-name">봄 니트 가디건</p>
                      <p class="ol-opt">아이보리 / 95</p>
                    </div>
                  </div>
                </div>
              </article>
            </section>
          </c:otherwise>
        </c:choose>

        <div class="ol-bottom-spacer" aria-hidden="true"></div>
      </main>
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/js/order-list.js"></script>
</body>
</html>

