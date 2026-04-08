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
		    <p class="ol-summary-value">${shippingCount}</p> </div>
		  <div class="ol-summary-item">
		    <p class="ol-summary-label">배송완료</p>
		    <p class="ol-summary-value">${deliveredCount}</p> </div>
		  <div class="ol-summary-item">
		    <p class="ol-summary-label">취소/반품</p>
		    <p class="ol-summary-value">${cancelCount}</p> </div>
		</section>

        <c:choose>
          <%-- 1. 주문 내역이 하나도 없을 때 --%>
          <c:when test="${empty orderList}">
            <section class="ol-empty section-box" aria-label="주문 없음">
              <p class="ol-empty-title">아직 주문한 상품이 없어요</p>
              <p class="ol-empty-sub">마음에 드는 상품을 주문해 보세요</p>
              <a href="${pageContext.request.contextPath}/main" class="ol-empty-btn">쇼핑하러 가기</a>
            </section>
          </c:when>
          
          <%-- 2. 주문 내역이 있을 때 --%>
          <c:otherwise>
            <section class="ol-list" aria-label="주문 목록">
              
              <%-- 주문 목록을 하나씩 꺼냅니다 --%>
              <c:forEach var="order" items="${orderList}">
                <article class="ol-card" aria-label="주문 ${order.orderDate}">
                  <div class="ol-card-top">
                    <%-- DB의 주문 날짜 출력 --%>
                    <p class="ol-date">${order.orderDate}</p>
                    <%-- JS가 인식할 수 있도록 진짜 주문 번호(orderNo)를 심어줍니다! --%>
                    <button type="button" class="ol-detail-btn" data-order-id="${order.orderNo}">상세보기</button>
                  </div>

                  <div class="ol-items" role="list" aria-label="주문 상품">
                    <%-- 
                      컨트롤러에서 넘긴 HashMap에서 현재 주문번호를 키값으로 사용해 해당 주문의 상품 리스트만 쏙 빼옵니다.
                    --%>
                    <c:forEach var="product" items="${orderProductMap[order.orderNo]}">
                      <div class="ol-item" role="listitem">
                        <div class="ol-thumb">
                          <%-- 이미지 폴더 경로 추후 확인--%>
						<img src="${pageContext.request.contextPath}/uploads/products/${product.productImage}" alt="${product.snapProductName}" loading="lazy"/>
                        </div>
                        <div class="ol-info">
                          <%-- 배송 상태--%>
                          <p class="ol-item-status">
                            <c:choose>
                              <c:when test="${order.deliveryState == 0}">결제완료</c:when>
                              <c:when test="${order.deliveryState == 1}">배송준비중</c:when>
                              <c:when test="${order.deliveryState == 2}">배송중</c:when>
                              <c:when test="${order.deliveryState == 3}">배송완료</c:when>
                              <c:otherwise>주문접수</c:otherwise>
                            </c:choose>
                          </p>
                          <%-- 스냅샷으로 저장된 상품명, 옵션(색상/사이즈) 출력 --%>
                          <p class="ol-name">${product.snapProductName}</p>
                          <p class="ol-opt">${product.snapOptionColor} / ${product.snapOptionSize}</p>
                        </div>
                      </div>
                    </c:forEach> </div>
                </article>
              </c:forEach> </section>
          </c:otherwise>
        </c:choose>

        <div class="ol-bottom-spacer" aria-hidden="true"></div>
      </main>
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/js/order-list.js"></script>
</body>
</html>

