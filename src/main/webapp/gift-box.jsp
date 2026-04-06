<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    request.setAttribute("bottomNav", "group");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>선물함</title>

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/category.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/wallet.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/group.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/gift.css">
</head>

<body data-context-path="${pageContext.request.contextPath}">
<div class="app-shell app-shell--gift">

    <%-- <jsp:include page="../layout/header.jsp" /> --%>

    <main class="main-content gift-main">
        <div class="gift-page">

            <div class="wallet-top">
                <a href="${pageContext.request.contextPath}/group/group.jsp" class="back-btn">
                    <span class="material-icons">arrow_back_ios</span>
                    <span>뒤로가기</span>
                </a>
            </div>

            <div class="category-top-tabs gift-box-tab-bar" role="tablist" aria-label="선물함 보기">
                <button type="button" class="top-tab active" data-tab="received" id="tabGiftReceived" role="tab" aria-selected="true" aria-controls="gift-received-panel">받은 선물</button>
                <button type="button" class="top-tab" data-tab="sent" id="tabGiftSent" role="tab" aria-selected="false" aria-controls="gift-sent-panel">보낸 선물</button>
            </div>

            <!-- 보낸 선물이 없을 때 이 블록 사용 -->
			<!--
            <div class="gift-empty-card">
                <div class="gift-empty-icon">
                    <span class="material-icons">redeem</span>
                </div>
                <h3 class="gift-empty-title">아직 보낸 선물이 없어요</h3>
                <p class="gift-empty-desc">
                    내 사람에게 첫 선물을 보내보세요
                </p>
            </div>
         	-->
         	<!-- 받은 선물이 없을 때 이 블록 사용 -->
            <!--
            <div class="gift-empty-card">
                <div class="gift-empty-icon">
                    <span class="material-icons">redeem</span>
                </div>
                <h3 class="gift-empty-title">아직 받은 선물이 없어요</h3>
                <p class="gift-empty-desc">
                    내 사람이 보낸 선물이 여기에 보여요
                </p>
            </div>
            -->

			<!-- 받은 선물이 있을 때 수락/거절 -->
            <div class="tab-content active gift-box-tab-panel" id="gift-received-panel" role="tabpanel" aria-labelledby="tabGiftReceived">
            <section class="gift-box-section">
                <div class="gift-chat-wrap gift-chat-wrap--box">

                    <div class="gift-date-divider">
                        <span>오늘</span>
                    </div>

                    <div class="gift-bubble gift-bubble--center">
                        <div class="gift-card">
                            <img src="${pageContext.request.contextPath}/images/gift/gift_card_01.png"
                                 class="gift-card-img"
                                 alt="선물 카드">

                            <p class="gift-meta">성연수님이 선물을 보냈어요!</p>

                            <div class="gift-product-summary">
                                <div class="gift-product-thumb-wrap">
                                    <img src="${pageContext.request.contextPath}/uploads/products/test-product.jpg"
                                         class="gift-product-thumb"
                                         alt="상품 이미지">
                                </div>

                                <div class="gift-product-info">
                                    <p class="gift-product-name">포근한 케이블 니트 가디건</p>
                                </div>
                            </div>

                            <div class="gift-address-box">
                                <p class="gift-address-label">기본 배송지</p>
                                <p class="gift-address-text">김지현 · 010-1234-5678<br>
                                (47323) 부산광역시 부산진구 가야대로 123, 101호</p>
                            </div>

                            <a href="${pageContext.request.contextPath}/mypage/profile-address.jsp"
                               class="gift-go-btn gift-go-btn--secondary">
                                배송지 관리 페이지로 이동하기
                            </a>

                            <div class="gift-action-row">
                                <a href="#" class="gift-action-btn gift-action-btn--accept">수락하기</a>
                                <a href="#" class="gift-action-btn gift-action-btn--reject">거절하기</a>
                            </div>
                        </div>

                        <p class="gift-time">오후 2:10</p>
                    </div>
             
             <!-- 수락/거절 선택 시 구매 확성까지만 띄움 그 이후는 자동 삭제-->
             <div class="gift-bubble gift-bubble--center">
                <div class="gift-card">
                    <img src="${pageContext.request.contextPath}/images/gift/gift_card_01.png"
                         class="gift-card-img"
                         alt="선물 카드">

                    <p class="gift-meta">성연수님이 선물을 보냈어요!</p>

                    <div class="gift-product-summary">
                        <div class="gift-product-thumb-wrap">
                            <img src="${pageContext.request.contextPath}/uploads/products/test-product.jpg"
                                 class="gift-product-thumb"
                                 alt="상품 이미지">
                        </div>

                        <div class="gift-product-info">
                            <p class="gift-product-name">포근한 케이블 니트 가디건</p>
                        </div>
                    </div>

                    <div class="gift-address-box">
                        <p class="gift-address-label">기본 배송지</p>
                        <p class="gift-address-text">김지현 · 010-1234-5678<br>
                        (47323) 부산광역시 부산진구 가야대로 123, 101호</p>
                    </div>

                    <div class="gift-state-box gift-state-box--success">
					    선물을 받았어요!
					</div>
					<!--  
					<div class="gift-state-box gift-state-box--reject">
				    	선물을 거절했어요
					</div>
					-->
                </div>


                <p class="gift-time">오후 2:10</p>
            </div>
        </div>
            </section>
            </div>

            <div class="tab-content gift-box-tab-panel" id="gift-sent-panel" role="tabpanel" aria-labelledby="tabGiftSent" hidden>
            <section class="gift-box-section">
                <div class="gift-chat-wrap gift-chat-wrap--box">

                    <div class="gift-date-divider">
                        <span>어제</span>
                    </div>

                    <div class="gift-bubble gift-bubble--center">
                        <div class="gift-card">
                            <img src="${pageContext.request.contextPath}/images/gift/gift_card_02.png"
                                 class="gift-card-img"
                                 alt="선물 카드">

                            <p class="gift-meta">성연수님에게 선물을 보냈어요!</p>

                            <div class="gift-product-summary">
                                <div class="gift-product-thumb-wrap">
                                    <img src="${pageContext.request.contextPath}/uploads/products/test-product.jpg"
                                         class="gift-product-thumb"
                                         alt="상품 이미지">
                                </div>

                                <div class="gift-product-info">
                                    <p class="gift-product-name">포근한 케이블 니트 가디건</p>
                                </div>
                            </div>

                            <div class="gift-notice-box">
                                <p class="gift-notice-text">
                                    상대가 배송지를 확인했어요<br>
                                    현재 선물이 배송 준비 중이에요.
                                </p>
                            </div>
                        </div>

                        <p class="gift-time">오전 11:24</p>
                    </div>

                </div>
            </section>
            </div>

        </div>
    </main>

    <%-- <jsp:include page="../layout/bottomNav.jsp" /> --%>
</div>
<script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>
<script src="${pageContext.request.contextPath}/js/gift-box.js"></script>
</body>
</html>