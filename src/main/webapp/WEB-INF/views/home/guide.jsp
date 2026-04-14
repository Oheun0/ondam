<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("bottomNav", "home"); %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<title>이용 가이드 | 온담</title>
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/guide.css">
</head>
<body class="guide-page" data-context-path="${pageContext.request.contextPath}">
<div class="app-shell">
	<div class="top-header-cluster">
		<jsp:include page="../layout/header.jsp" />
	</div>

	<main class="page-wrap guide-page-main" role="main">
		<div class="guide-svg-wrap">
			<img src="${pageContext.request.contextPath}/images/user-guide.svg"
			     class="guide-svg-img"
			     alt="온담 이용 가이드"
			     decoding="async"
			     loading="eager">
		</div>
		<div class="guide-actions">
			<a href="${pageContext.request.contextPath}/main" class="guide-home-btn">홈에서 시작하기</a>
		</div>
	</main>

	<jsp:include page="../layout/bottomNav.jsp" />
</div>
<script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>
</body>
</html>
