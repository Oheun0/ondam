<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    request.setAttribute("bottomNav", "mypage");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>온담 내 정보</title>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage.css">
</head>
<body data-context-path="${pageContext.request.contextPath}">
<div class="app-shell">
    <div class="top-header-cluster">
        <jsp:include page="../layout/header.jsp" />
    </div>

    <main class="page-wrap mypage-wrap">
        <section class="mypage-card profile-card" aria-label="내 정보 상단 영역">
            <div class="profile-row">
                <div class="profile-image-box">
                    <img
    src="${pageContext.request.contextPath}/images/profile/${loginUser.userProfileImg != null ? loginUser.userProfileImg : 'default-profile.png'}"
    alt="사용자 프로필 사진"
    class="profile-image"
    onerror="this.src='${pageContext.request.contextPath}/images/profile/default-profile.png'; this.onerror=null;">
                    <div class="profile-fallback" style="display:none;">
                        <span class="material-icons">person</span>
                    </div>
                </div>

                <div class="profile-copy">
                    <h1>${loginUser.userName}님, 안녕하세요</h1>
                    <p>
                        내 정보와 주문, 쿠폰, 선물함을<br>
                        한곳에서 쉽게 볼 수 있어요.
                    </p>
                </div>
            </div>

            <div class="profile-button-row">
                <a href="${pageContext.request.contextPath}/logout" class="pill-button">로그아웃</a>
                <a href="${pageContext.request.contextPath}/profile" class="pill-button">내 정보 수정</a>
            </div>
        </section>

        <section class="mypage-card wallet-card" aria-label="함께 지갑">
		    <div class="wallet-top">
		        <div class="wallet-title-wrap">
		            <span class="material-icons-outlined wallet-icon" aria-hidden="true">wallet</span>
		            <strong>함께지갑</strong>
		        </div>
		
		        <a href="${pageContext.request.contextPath}/wallet" class="wallet-link">
		            자세히보기
		            <span class="material-icons">chevron_right</span>
		        </a>
		    </div>
		
		    <div class="wallet-balance-row">
		        <span class="wallet-balance-label">현재 잔액</span>
		        <span class="wallet-balance">
					<c:choose>
			            <c:when test="${not empty wallet}">
			                <fmt:formatNumber value="${wallet.balance}" pattern="#,###"/>원
			            </c:when>
			            <c:otherwise>0원</c:otherwise>
					</c:choose>
		        </span>
		    </div>
		
		    <div class="wallet-button-row">
		        <a href="${pageContext.request.contextPath}/wallet/charge" class="wallet-button fill">충전하기</a>
		        <a href="${pageContext.request.contextPath}/wallet/history" class="wallet-button">사용 내역</a>
		    </div>
		</section>

        <section class="mypage-section" aria-label="메뉴">
            <div class="section-head">
                <h2>메뉴</h2>
            </div>

            <div class="menu-card">
                <a href="${pageContext.request.contextPath}/order/list" class="menu-item">
                    <div class="menu-left">
                        <span class="material-icons-outlined menu-row-icon" aria-hidden="true">local_shipping</span>
                        <strong class="menu-label">주문 / 배송</strong>
                    </div>
                    <span class="material-icons menu-arrow" aria-hidden="true">chevron_right</span>
                </a>

                <a href="${pageContext.request.contextPath}/product/wishlist" class="menu-item">
                    <div class="menu-left">
                        <span class="material-icons menu-row-icon" aria-hidden="true">favorite_border</span>
                        <strong class="menu-label">찜한 상품</strong>
                    </div>
                    <span class="material-icons menu-arrow" aria-hidden="true">chevron_right</span>
                </a>

                <a href="${pageContext.request.contextPath}/review/write" class="menu-item">
                    <div class="menu-left">
                        <span class="material-icons-outlined menu-row-icon" aria-hidden="true">rate_review</span>
                        <strong class="menu-label">후기 작성</strong>
                    </div>
                    <span class="material-icons menu-arrow" aria-hidden="true">chevron_right</span>
                </a>

                <a href="${pageContext.request.contextPath}/gift/box" class="menu-item">
                    <div class="menu-left">
                        <span class="material-icons menu-row-icon" aria-hidden="true">redeem</span>
                        <strong class="menu-label">선물함</strong>
                    </div>
                    <span class="material-icons menu-arrow" aria-hidden="true">chevron_right</span>
                </a>

                <a href="${pageContext.request.contextPath}/coupon/list" class="menu-item">
                    <div class="menu-left">
                        <span class="material-icons-outlined menu-row-icon" aria-hidden="true">local_activity</span>
                        <strong class="menu-label">보유 쿠폰</strong>
                    </div>
                    <span class="material-icons menu-arrow" aria-hidden="true">chevron_right</span>
                </a>

                <a href="${pageContext.request.contextPath}/support/inquiry" class="menu-item">
                    <div class="menu-left">
                        <span class="material-icons menu-row-icon" aria-hidden="true">support_agent</span>
                        <strong class="menu-label">문의하기</strong>
                    </div>
                    <span class="material-icons menu-arrow" aria-hidden="true">chevron_right</span>
                </a>
            </div>
        </section>

        <section class="mypage-section" aria-label="설정">
            <div class="section-head">
                <h2>설정</h2>
            </div>

            <div class="menu-card">
                <a href="${pageContext.request.contextPath}/notification/settings" class="menu-item">
                    <div class="menu-left">
                        <span class="material-icons menu-row-icon" aria-hidden="true">notifications_none</span>
                        <strong class="menu-label">알림 설정</strong>
                    </div>
                    <span class="material-icons menu-arrow" aria-hidden="true">chevron_right</span>
                </a>

                <a href="${pageContext.request.contextPath}/mypage/withdraw" class="menu-item danger">
                    <div class="menu-left">
                        <span class="material-icons menu-row-icon" aria-hidden="true">person_remove</span>
                        <strong class="menu-label">탈퇴하기</strong>
                    </div>
                    <span class="material-icons menu-arrow" aria-hidden="true">chevron_right</span>
                </a>
            </div>
        </section>
    </main>

    <jsp:include page="../layout/bottomNav.jsp" />
</div>
<script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>
</body>
</html>
