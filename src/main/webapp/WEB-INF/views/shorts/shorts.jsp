<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    request.setAttribute("bottomNav", "shorts");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>온담 - 영상보기</title>

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/shorts.css">
</head>
<body>
<div class="app-shell">

    <div class="shorts-wrapper">
        <c:forEach var="shorts" items="${shortsList}" varStatus="status">
            <section class="shorts-container" data-index="${status.index}">
                <video class="shorts-video" loop muted playsinline ${status.first ? 'autoplay' : ''}>
                    <source src="${pageContext.request.contextPath}/upload/shorts/${shorts.videoFile}" type="video/mp4">
                </video>
                
                	<h2 class="shorts-top-title" 
				        onclick="event.stopPropagation(); openPurchaseModal('${shorts.productNo}', '${shorts.shortsTitle}');">
				        ${shorts.shortsTitle}
				    </h2>

                <aside class="side-actions">
                    <button class="action-btn" 
					            onclick="event.stopPropagation(); openPurchaseModal('${shorts.productNo}', '${shorts.shortsTitle}');">
					        <span class="material-icons">shopping_bag</span>
					        <span>구매하기</span>
				    </button>
                    <button class="action-btn" onclick="toggleLike(${shorts.shortsNo})">
                        <span class="material-icons">favorite_border</span>
                        <span>찜</span>
                    </button>
                    <button class="action-btn" onclick="openJoreugi(${shorts.productNo})">
                        <span class="material-icons">volunteer_activism</span>
                        <span>조르기</span>
                    </button>
                    <button class="action-btn" onclick="openGift(${shorts.productNo})">
                        <span class="material-icons">card_giftcard</span>
                        <span>선물하기</span>
                    </button>
                </aside>
                
                <div class="bottom-info-wrapper">
               <section class="shorts-product-card" onclick="location.href='${pageContext.request.contextPath}/product/detail?no=${shorts.productNo}'">
                    <div class="card-image">
                        <img src="https://via.placeholder.com/60?text=IMG" alt="상품 이미지">
                    </div>
                    <div class="card-info">
                        <span class="product-name">${shorts.shortsTitle}</span>
                        <span class="product-price">
                            <span class="discount">43%</span> 
                            <span class="price">39,000원</span> 
                        </span>
                    </div>
                    <div class="card-action">
                        <button class="more-btn">상세보기</button>
                    </div>
                </section>
              </div>
            </section>
        </c:forEach>
    </div><jsp:include page="../layout/bottomNav.jsp" />  				
  	<div id="purchaseModalOverlay" class="purchase-modal-overlay" onclick="closePurchaseModal()">
    <div class="purchase-modal" onclick="event.stopPropagation();">
        <div class="modal-header">
            <h3 id="modalProductName">상품이름</h3>
            <button class="close-btn" onclick="closePurchaseModal()">
                <span class="material-icons">close</span>
            </button>
        </div>
        
        <div class="modal-options">
            <div class="option-row">
                <label>색상</label>
                <select><option>색상을 선택하세요</option></select>
            </div>
            <div class="option-row">
                <label>사이즈</label>
                <select><option>사이즈를 선택하세요</option></select>
            </div>
            <div class="option-row">
                <label>개수</label>
                <div class="qty-control">
                    <button type="button" onclick="updateQty(-1)">-</button>
                    <span id="buyQty">1</span>
                    <button type="button" onclick="updateQty(1)">+</button>
                </div>
            </div>
            <div class="option-row price-row">
                <label>가격</label>
                <span class="total-price">0원</span>
            </div>
        </div>
        
        <div class="modal-actions">
            <button class="modal-icon-btn" onclick="openJoreugi()">
                <span class="material-icons">volunteer_activism</span>
                <span>조르기</span>
            </button>
            <button class="modal-icon-btn" onclick="openGift()">
                <span class="material-icons">card_giftcard</span>
                <span>선물</span>
            </button>
            <button class="modal-icon-btn" onclick="shareShorts()">
                <span class="material-icons">share</span>
                <span>공유</span>
            </button>
            <button class="modal-icon-btn" onclick="addToCart()">
                <span class="material-icons">shopping_cart</span>
                <span>장바구니</span>
            </button>
        </div>
        
        <button class="buy-now-btn" onclick="buyNow()">바로 구매하기</button>
    </div>
</div>
  </div> <script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>
  				<script src="${pageContext.request.contextPath}/js/shorts.js"></script>
</body>
</html>