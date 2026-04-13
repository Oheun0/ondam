<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>알림 설정</title>

<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/home.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/mypage.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/wallet.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/product-detail.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/notification-setting.css">
</head>
<body class="notification-setting-page"
	data-context-path="${pageContext.request.contextPath}">
	<div class="detail-shell">
		<div
			class="detail-page-inner detail-page-inner--sticky-header notification-setting-inner"
			id="notificationSettingRoot">
			<div class="notification-setting-sticky-head">
				<div class="notification-setting-header-wrap">
					<jsp:include page="/WEB-INF/views/layout/back-header.jsp" />
					<h1 class="notification-setting-header-title">알림 설정</h1>
				</div>
			</div>

			<main class="notification-setting-main" aria-label="알림 설정">
			<c:set var="en0" value="${empty settingMap[0] ? 1 : settingMap[0]}"/>
			<c:set var="en1" value="${empty settingMap[1] ? 1 : settingMap[1]}"/>
			<c:set var="en2" value="${empty settingMap[2] ? 1 : settingMap[2]}"/>
			<c:set var="en3" value="${empty settingMap[3] ? 1 : settingMap[3]}"/>
			<c:set var="en4" value="${empty settingMap[4] ? 1 : settingMap[4]}"/>
			<c:set var="en5" value="${empty settingMap[5] ? 1 : settingMap[5]}"/>
			<c:set var="en6" value="${empty settingMap[6] ? 1 : settingMap[6]}"/>
			<c:set var="en7" value="${empty settingMap[7] ? 1 : settingMap[7]}"/>
				<section class="ns-card" aria-label="알림 목록">
					<!-- 0: 내 사람 -->
					<button type="button"
				        class="ns-row ${en0 == 1 ? 'is-on' : ''}"
				        data-ns-key="0"
				        role="switch"
				        aria-checked="${en0 == 1 ? 'true' : 'false'}"
				        aria-label="내 사람 알림">
						<div class="ns-left">
							<p class="ns-title">내 사람 알림</p>
							<p class="ns-desc">가족 초대, 연결 등 내 사람 소식을 알려드려요</p>
						</div>
						<span class="ns-switch" aria-hidden="true"><span
							class="ns-switch__thumb"></span></span>
					</button>

					<!-- 1: 조르기 -->
					<button type="button"
				        class="ns-row ${en1 == 1 ? 'is-on' : ''}"
				        data-ns-key="1"
				        role="switch"
				        aria-checked="${en1 == 1 ? 'true' : 'false'}"
				        aria-label="조르기 알림">
						<div class="ns-left">
							<p class="ns-title">조르기 알림</p>
							<p class="ns-desc">가족이 상품을 사달라고 조르면 알려드려요</p>
						</div>
						<span class="ns-switch" aria-hidden="true"><span
							class="ns-switch__thumb"></span></span>
					</button>

					<!-- 2: 주문/배송 -->
					<button type="button"
				        class="ns-row ${en2 == 1 ? 'is-on' : ''}"
				        data-ns-key="2"
				        role="switch"
				        aria-checked="${en2 == 1 ? 'true' : 'false'}"
				        aria-label="주문/배송 알림">
						<div class="ns-left">
							<p class="ns-title">주문/배송 알림</p>
							<p class="ns-desc">주문 완료, 배송 시작, 도착 소식을 알려드려요</p>
						</div>
						<span class="ns-switch" aria-hidden="true"><span
							class="ns-switch__thumb"></span></span>
					</button>

					<!-- 3: 쿠폰 -->
					<button type="button"
				        class="ns-row ${en3 == 1 ? 'is-on' : ''}"
				        data-ns-key="3"
				        role="switch"
				        aria-checked="${en3 == 1 ? 'true' : 'false'}"
				        aria-label="쿠폰 알림">
						<div class="ns-left">
							<p class="ns-title">쿠폰 알림</p>
							<p class="ns-desc">새 쿠폰이 발급되면 알려드려요</p>
						</div>
						<span class="ns-switch" aria-hidden="true"><span
							class="ns-switch__thumb"></span></span>
					</button>

					<!-- 4: 배송지 수정 -->
					<button type="button"
				        class="ns-row ${en4 == 1 ? 'is-on' : ''}"
				        data-ns-key="4"
				        role="switch"
				        aria-checked="${en4 == 1 ? 'true' : 'false'}"
				        aria-label="배송지 수정 알림">
						<div class="ns-left">
							<p class="ns-title">배송지 수정 알림</p>
							<p class="ns-desc">가족이 내 배송지를 수정하면 알려드려요</p>
						</div>
						<span class="ns-switch" aria-hidden="true"><span
							class="ns-switch__thumb"></span></span>
					</button>

					<!-- 5: 함께 지갑 -->
					<button type="button"
				        class="ns-row ${en5 == 1 ? 'is-on' : ''}"
				        data-ns-key="5"
				        role="switch"
				        aria-checked="${en5 == 1 ? 'true' : 'false'}"
				        aria-label="함께 지갑 알림">
						<div class="ns-left">
							<p class="ns-title">함께 지갑 알림</p>
							<p class="ns-desc">함께 지갑에 입출금이 발생하면 알려드려요</p>
						</div>
						<span class="ns-switch" aria-hidden="true"><span
							class="ns-switch__thumb"></span></span>
					</button>
					
					<!-- 6: 선물 -->
					<button type="button"
				        class="ns-row ${en6 == 1 ? 'is-on' : ''}"
				        data-ns-key="6"
				        role="switch"
				        aria-checked="${en6 == 1 ? 'true' : 'false'}"
				        aria-label="선물 알림">
						<div class="ns-left">
							<p class="ns-title">선물 알림</p>
							<p class="ns-desc">선물이 오면 알려드려요</p>
						</div>
						<span class="ns-switch" aria-hidden="true"><span
							class="ns-switch__thumb"></span></span>
					</button>

					<!-- 7: 기타 -->
					<button type="button"
				        class="ns-row ${en7 == 1 ? 'is-on' : ''}"
				        data-ns-key="7"
				        role="switch"
				        aria-checked="${en7 == 1 ? 'true' : 'false'}"
				        aria-label="기타 알림">
						<div class="ns-left">
							<p class="ns-title">기타 알림</p>
							<p class="ns-desc">서비스 공지 및 이벤트 소식을 알려드려요</p>
						</div>
						<span class="ns-switch" aria-hidden="true"><span
							class="ns-switch__thumb"></span></span>
					</button>
				</section>
			</main>
		</div>
	</div>

	<script
		src="${pageContext.request.contextPath}/js/notification-setting.js"></script>
</body>
</html>