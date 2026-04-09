<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>주문상세</title>

  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/wallet.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-detail.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cart.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/order-detail.css">
</head>
<body class="order-detail-page" data-context-path="${pageContext.request.contextPath}">
  <div class="detail-shell">
    <div class="detail-page-inner detail-page-inner--sticky-header order-detail-inner" id="orderDetailRoot">
      <div class="order-detail-sticky-head">
        <div class="order-detail-header-wrap">
          <jsp:include page="/WEB-INF/views/layout/back-header.jsp"/>
          <h1 class="order-detail-header-title">주문상세</h1>
        </div>
      </div>

     <main class="order-detail-main" aria-label="주문상세">
        <section class="od-card od-summary-card" aria-label="주문 정보">
          <dl class="od-kv">
            <div class="od-kv-row">
              <dt class="od-kv-key">주문번호</dt>
              <dd class="od-kv-val">${orderInfo.orderCode}</dd>
            </div>
            <div class="od-kv-row">
              <dt class="od-kv-key">결제날짜</dt>
              <dd class="od-kv-val">${orderInfo.orderDate}</dd>
            </div>
          </dl>
        </section>

        <section class="od-card" aria-label="주문 상품">
			  <c:set var="lastBrand" value="" />
			
			  <c:forEach var="product" items="${productList}">
			    <%-- 1. 새로운 브랜드가 나타나면 브랜드 헤더를 출력 --%>
			    <c:if test="${product.productBrand != lastBrand}">
			      <c:if test="${not empty lastBrand}">
			        </ul></section> </c:if>
			      
			      <section class="cart-brand-group od-brand-group">
			        <h2 class="cart-brand-group__title">${product.productBrand}</h2>
			        <ul class="cart-brand-group__list">
			      
			      <c:set var="lastBrand" value="${product.productBrand}" />
			    </c:if>
                <li>
                  <article class="cart-item od-item" aria-label="${product.snapProductName}">
                    <div class="cart-item__left">
                      <div class="cart-item__thumb-wrap">
                        <%-- DB에서 가져온 썸네일 이미지 적용 --%>
                        <img src="${pageContext.request.contextPath}/uploads/products/${product.productImage}" alt="${product.snapProductName}" class="cart-item__thumb" loading="lazy"/>
                      </div>
                    </div>

                    <div class="cart-item__body">
                      <p class="od-ship-status">
                        <c:choose>
                          <c:when test="${orderInfo.deliveryState == 0}">결제완료</c:when>
                          <c:when test="${orderInfo.deliveryState == 1}">배송준비중</c:when>
                          <c:when test="${orderInfo.deliveryState == 2}">배송중</c:when>
                          <c:when test="${orderInfo.deliveryState == 3}">배송완료</c:when>
                          <c:otherwise>주문접수</c:otherwise>
                        </c:choose>
                      </p>
                      <p class="cart-item__name">${product.snapProductName}</p>
                      <p class="cart-item__option">${product.snapOptionColor} / ${product.snapOptionSize}</p>
                      <div class="cart-item__bottom">
                        <div class="cart-item__price-block cart-item__price-block--plain">
                          <span class="cart-item__price-sale">
                            <fmt:formatNumber value="${product.snapProductPrice}" pattern="#,###"/>원
                          </span>
                          <span class="od-item-qty" style="font-size: 13px; color: #888; margin-left: 4px;">/ ${product.orderQuantity}개</span>
                        </div>
                      </div>
                    </div>

                    <div class="od-actions" aria-label="상품 액션">
                      <%-- 변경 후: 배송완료(3) 상태이면서, 작성된 리뷰가 없을 때(reviewNo == 0)만 노출 --%>
					<c:if test="${orderInfo.deliveryState == 3 and product.reviewNo == 0}">
					  <button type="button" class="od-action-btn od-action-btn--primary review-write-btn" data-order-item-no="${product.orderItemNo}">
					    리뷰 작성하고 쿠폰받기
					  </button>
					</c:if>
                      <button type="button" class="od-action-btn" 
						        onclick="location.href='${pageContext.request.contextPath}/inquiry?action=writeForm&productNo=${product.productNo}&orderNo=${orderInfo.orderNo}'">
						  상품 문의하기
						</button>
                    </div>
                  </article>
                </li>
              </c:forEach>

            </ul>
          </section>
        </section>

        <section class="od-card" aria-label="배송 정보">
          <h2 class="od-card-title">배송 정보</h2>
          <dl class="od-kv">
            <div class="od-kv-row od-kv-row--loose">
              <dt class="od-kv-key">받는 분</dt>
              <dd class="od-kv-val">${orderInfo.receiverName} | ${orderInfo.receiverTel}</dd>
            </div>
            <div class="od-kv-row od-kv-row--loose">
              <dt class="od-kv-key">주소</dt>
              <dd class="od-kv-val od-kv-val--wrap">${orderInfo.deliveryAddr}</dd>
            </div>
            <div class="od-kv-row od-kv-row--loose">
              <dt class="od-kv-key">요청사항</dt>
              <dd class="od-kv-val od-kv-val--wrap">
                <c:out value="${empty orderInfo.deliveryContent ? '없음' : orderInfo.deliveryContent}"/>
              </dd>
            </div>
          </dl>
        </section>

        <section class="od-card" aria-label="결제 정보">
          <h2 class="od-card-title">결제 정보</h2>
          <dl class="od-pay">
            <div class="od-pay-row">
              <dt class="od-pay-key">결제 방법</dt>
              <dd class="od-pay-val">
                <c:choose>
                  <c:when test="${orderInfo.paymentMethod == 0}">함께지갑</c:when>
                  <c:when test="${orderInfo.paymentMethod == 1}">카드결제</c:when>
                  <c:otherwise>기타결제</c:otherwise>
                </c:choose>
              </dd>
            </div>
            <div class="od-pay-row">
              <dt class="od-pay-key">총 상품 금액</dt>
              <dd class="od-pay-val"><fmt:formatNumber value="${orderInfo.orderPrice}" pattern="#,###"/>원</dd>
            </div>
            <div class="od-pay-row">
              <dt class="od-pay-key">쿠폰 할인</dt>
              <dd class="od-pay-val od-pay-val--minus">
                -<fmt:formatNumber value="${orderInfo.couponDiscount}" pattern="#,###"/>원
              </dd>
            </div>
            <div class="od-pay-row">
              <dt class="od-pay-key">지갑 사용액</dt>
              <dd class="od-pay-val od-pay-val--minus">
                -<fmt:formatNumber value="${orderInfo.walletUsedAmount}" pattern="#,###"/>원
              </dd>
            </div>
            <div class="od-pay-row od-pay-row--total">
              <dt class="od-pay-key">최종 결제금액</dt>
              <dd class="od-pay-val od-pay-val--total">
                <fmt:formatNumber value="${orderInfo.paymentAmount}" pattern="#,###"/>원
              </dd>
            </div>
          </dl>
        </section>

        <div class="od-bottom-spacer" aria-hidden="true"></div>
      </main>
    </div>
  </div>
    <script src="${pageContext.request.contextPath}/js/order-detail.js"></script>
</body>
</html>