<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    request.setAttribute("bottomNav", "group");
    /* 상대가 선물을 보낸 경우에만 '고마움 표시하기' 노출 (연동 시 서버 값으로 대체) */
    boolean showThanksAfterPeerGift = true;
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>선물하기</title>

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/group.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/wallet.css">
</head>

<body data-context-path="${pageContext.request.contextPath}">
<div class="app-shell app-shell--group">

    <jsp:include page="../layout/header.jsp" />

    <main class="main-content group-main">
        <div class="group-page gift-chat-page">

            <div class="wallet-top gift-chat-wallet-top">
                <a href="${pageContext.request.contextPath}/group/group-empty.jsp" class="back-btn">
                    <span class="material-icons">arrow_back_ios</span>
                    <span>뒤로가기</span>
                </a>
                <a href="#" class="gift-chat-poke-link">
				  <span class="material-icons">volunteer_activism</span>
				  조르기 목록보기
				</a>
            </div>

            <div class="topbar-center topbar-user">
                <span class="topbar-username">성연수님</span>
            </div>

            <!-- =====================
                 아무 내역 없을 때
            ===================== -->
            <!-- 
            <div class="group-empty-card">
              <h3 class="group-empty-title">아직 주고받은 선물이 없어요</h3>
              <p class="group-empty-desc">
                상대의 조르기 목록을 통해<br>
                내 사람이 갖고 싶은 상품을 선물해보세요
              </p>
            </div>
            -->

            <!-- =====================
                 채팅 영역
            ===================== -->
            <div class="gift-chat-wrap">
				
				<!-- 날짜 -->
			    <div class="gift-date-divider">
			        <span>2026년 4월 3일 금요일</span>
			    </div>
				
	            <!-- 받은 선물 -->
				<div class="gift-bubble gift-bubble--left">
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
				
				        <div class="gift-notice-box">
				            <p class="gift-notice-text">
				                4월 12일까지 배송지를 입력해 주세요<br>
				                입력하지 않으면 선물이 취소됩니다.
				            </p>
				        </div>
				
				        <a href="${pageContext.request.contextPath}/gift/gift-box.jsp"
				           class="gift-go-btn">
				            선물함 가기
				        </a>
				        <!-- 아래 버튼 클릭 시 고마움 카드 중 랜덤으로 1개가 보내짐 1번 보내면 그 이후는 버튼 비활성화 -->
				        <a class="gift-go-btn gift-go-btn--secondary">
					        고마움 표시하기
					    </a>
				       
				    </div>
				    <p class="gift-time">오후 2:10</p>
				</div>
				
				<!-- 날짜 -->
			    <div class="gift-date-divider">
			        <span>2026년 4월 4일 토요일</span>
			    </div>
				
                <!-- 내가 보낸 선물 -->
				<div class="gift-bubble gift-bubble--right">
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
				                4월 12일까지 배송지를 입력해 주세요<br>
				                입력하지 않으면 선물이 취소됩니다.
				            </p>
				        </div>
				
				        <a href="${pageContext.request.contextPath}/gift/gift-box.jsp"
				           class="gift-go-btn">
				            선물 상태 보기
				        </a>
				    </div>
				    <p class="gift-time">오후 2:12</p>
				</div>

                <!-- 감사 카드 -->
				<div class="gift-bubble gift-bubble--left">
				    <div class="thanks-card">
				
				        <img src="${pageContext.request.contextPath}/images/gift/thanks_card_01.png"
				             class="gift-card-img"
				             alt="감사 카드">
				
				        <p class="gift-meta">성연수님이 감사 카드를 보냈어요</p>
				
				        <div class="gift-notice-box">
				            <p class="gift-notice-text">
				                고마워요. 정말 마음에 들어요 😊
				            </p>
				        </div>
				
				    </div>
				    <p class="gift-time">오후 2:15</p>
				</div>

            </div>

        </div>

    </main>

    <jsp:include page="../layout/bottomNav.jsp" />

</div>
<script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>
</body>
</html>
