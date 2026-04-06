<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>온담 | 알림</title>

	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/notification.css">

	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
</head>
<body data-context-path="${pageContext.request.contextPath}">

<div class="app-shell app-shell--notification">

	<div class="top-header-cluster">
		<jsp:include page="../layout/header.jsp" />
	</div>

	<main class="notification-main">
		<section class="notification-page">

			<div class="notification-page-header">
				<div class="notification-actions">
					<!-- <button type="button" class="text-btn" id="markAllReadBtn">전체 읽음</button>
					<button type="button" class="text-btn danger" id="deleteAllBtn">전체 삭제</button> -->
						<form method="post"
							action="${pageContext.request.contextPath}/notification"
							style="display: inline;">
							<input type="hidden" name="action" value="markAllRead">
							<button type="submit" class="text-btn">전체 읽음</button>
						</form>
						<form method="post"
							action="${pageContext.request.contextPath}/notification"
							style="display: inline;">
							<input type="hidden" name="action" value="deleteAll">
							<button type="submit" class="text-btn danger">전체 삭제</button>
						</form>
					</div>
			</div>

				<div class="notification-list">
					<c:forEach var="dto" items="${vlist}">
						<a href="#"
							class="notification-item ${dto.isRead == 0 ? 'unread' : ''}"
							data-no="${dto.notificationNo}">
							<div class="notification-content">
								<p class="notification-text">${dto.notificationContent}</p>
								<span class="notification-time">${dto.createdAt}</span>
							</div> <c:if test="${dto.isRead == 0}">
								<span class="notification-dot"></span>
							</c:if>
						</a>
					</c:forEach>
				</div>

				<c:if test="${empty vlist}">
					<div class="notification-empty">
						<p>새로운 알림이 없어요.</p>
					</div>
				</c:if>

			</section>
	</main>

	<jsp:include page="../layout/bottomNav.jsp" />

</div>

<script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>
<script src="${pageContext.request.contextPath}/js/notification.js"></script>
</body>
</html>