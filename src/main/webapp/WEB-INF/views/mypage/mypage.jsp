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
		    <c:choose>
                <%-- A. 가족에 가입되어 있는 경우 (지갑 정보 표시) --%>
                <c:when test="${hasFamily}">
                    <div class="wallet-top">
                        <div class="wallet-title-wrap">
                            <span class="material-icons wallet-icon">account_balance_wallet</span>
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
                            <fmt:formatNumber value="${wallet.balance}" pattern="#,###"/>원
                        </span>
                    </div>
                
                    <div class="wallet-button-row">
                        <a href="${pageContext.request.contextPath}/wallet?action=charge" class="wallet-button fill">충전하기</a>
                        <a href="${pageContext.request.contextPath}/wallet?action=history" class="wallet-button">사용 내역</a>
                    </div>
                </c:when>
                
                <%-- B. 가족에 가입되어 있지 않은 경우 (가입 유도 화면 표시) --%>
                <c:otherwise>
                    <div class="wallet-top" style="justify-content: center; padding-bottom: 0;">
                        <div class="wallet-title-wrap" style="flex-direction: column; align-items: center; gap: 8px;">
                            <span class="material-icons wallet-icon" style="font-size: 36px; color: #ccc;">group_add</span>
                            <strong style="color: #333; font-size: 16px;">아직 연결된 내 사람이 없어요</strong>
                        </div>
                    </div>
                
                    <div class="wallet-balance-row" style="justify-content: center; text-align: center; padding: 15px 0;">
                        <span class="wallet-balance-label" style="font-size: 14px; color: #666; line-height: 1.5;">
                            내 사람을 등록하고 온담의<br>함께 지갑 서비스를 이용해 보세요.
                        </span>
                    </div>
                
                    <div class="wallet-button-row">
                        <!-- /group 페이지로 이동하여 가족을 생성하거나 참여하도록 유도합니다. -->
                        <a href="${pageContext.request.contextPath}/group" class="wallet-button fill" style="width: 100%; text-align: center;">내 사람 만들기 / 참여하기</a>
                    </div>
                </c:otherwise>
            </c:choose>
		</section>

<section class="mypage-section" aria-label="메뉴">
            <div class="section-head">
                <h2>메뉴</h2>
            </div>

            <div class="menu-card">
                <a href="${pageContext.request.contextPath}/order/order-list" class="menu-item">
                    <div class="menu-left">
                        <span class="material-icons-outlined menu-row-icon" aria-hidden="true">local_shipping</span>
                        <strong class="menu-label">주문 / 배송</strong>
                    </div>
                    <span class="material-icons menu-arrow" aria-hidden="true">chevron_right</span>
                </a>

                <a href="${pageContext.request.contextPath}/wish" class="menu-item">
                    <div class="menu-left">
                        <span class="material-icons menu-row-icon" aria-hidden="true">favorite_border</span>
                        <strong class="menu-label">찜한 상품</strong>
                    </div>
                    <span class="material-icons menu-arrow" aria-hidden="true">chevron_right</span>
                </a>

                <a href="${pageContext.request.contextPath}/review?action=myList" class="menu-item">
                    <div class="menu-left">
                        <span class="material-icons-outlined menu-row-icon" aria-hidden="true">rate_review</span>
                        <strong class="menu-label">나의 후기</strong>
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
                        <strong class="menu-label">내 쿠폰</strong>
                    </div>
                    <span class="material-icons menu-arrow" aria-hidden="true">chevron_right</span>
                </a>

                <a href="${pageContext.request.contextPath}/support/inquiry" class="menu-item">
                    <div class="menu-left">
                        <span class="material-icons menu-row-icon" aria-hidden="true">support_agent</span>
                        <strong class="menu-label">문의내역</strong>
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
                <a href="${pageContext.request.contextPath}/notification/notification-setting" class="menu-item">
                    <div class="menu-left">
                        <span class="material-icons menu-row-icon" aria-hidden="true">notifications_none</span>
                        <strong class="menu-label">알림 설정</strong>
                    </div>
                    <span class="material-icons menu-arrow" aria-hidden="true">chevron_right</span>
                </a>

                <a href="javascript:void(0);" onclick="openWithdraw()" class="menu-item danger">
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
    
</div><jsp:include page="withdraw.jsp" />
<script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>
</body>
</html>
