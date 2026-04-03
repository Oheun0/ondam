<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
request.setAttribute("bottomNav", "mypage");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<title>지갑 관리</title>

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
	href="${pageContext.request.contextPath}/css/wallet.css">
</head>

<body data-context-path="${pageContext.request.contextPath}">
	<div class="app-shell">

		<jsp:include page="../layout/header.jsp" />

		<main class="main-content">
			<div class="wallet-page">
				<jsp:include page="../wallet/wallet-header.jsp" />

				<div class="wallet-section">
					<p class="wallet-desc">내 사람들과 함께 사용하는 지갑이에요</p>

					<div class="wallet-action-row">
						<a href="${pageContext.request.contextPath}/wallet?action=charge"
							class="wallet-action-btn charge"> 충전하기 </a> <a
							href="${pageContext.request.contextPath}/wallet?action=withdraw"
							class="wallet-action-btn withdraw"> 잔액 꺼내기 </a>
					</div>

					<div class="wallet-section-title history-title-row">
						<h2>최근 사용내역</h2>
						<a href="${pageContext.request.contextPath}/wallet?action=history"
							class="more-link">전체보기</a>
					</div>

					<div class="history-list">
						<c:choose>
							<c:when test="${empty recentList}">
								<p class="wallet-desc"
									style="text-align: center; padding: 20px 0;">사용내역이 없어요</p>
							</c:when>
							<c:otherwise>
								<c:forEach var="tx" items="${recentList}">
									<div class="history-item">
										<div class="history-text">
											<%-- transactionMemo가 있으면 우선 표시, 없으면 타입으로 표시 --%>
											<strong> 
												<c:choose>
													<c:when test="${not empty tx.transactionMemo}">${tx.transactionMemo}</c:when>
													<c:when test="${tx.transactionType == 0}">지갑 충전</c:when>
													<c:when test="${tx.transactionType == 1}">상품 결제</c:when>
													<c:when test="${tx.transactionType == 2}">잔액 꺼내기</c:when>
													<c:otherwise>기타</c:otherwise>
												</c:choose>
											</strong>
											<p>${tx.transactionDate}</p>
										</div>

										<div class="history-right">
											<c:choose>
												<c:when test="${tx.transactionType == 0}">
													<%-- 충전: 초록 + --%>
													<span class="history-amount plus"> +<fmt:formatNumber
															value="${tx.amount}" pattern="#,###" />원
													</span>
												</c:when>
												<c:otherwise>
													<%-- 사용, 잔액 꺼내기: 빨간 - --%>
													<span class="history-amount minus"> -<fmt:formatNumber
															value="${tx.amount}" pattern="#,###" />원
													</span>
												</c:otherwise>
											</c:choose>
											<span class="history-balance"> <fmt:formatNumber
													value="${tx.balanceSnapshot}" pattern="#,###" />원
											</span>
										</div>
									</div>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</main>

		<jsp:include page="../layout/bottomNav.jsp" />

	</div>
</body>
</html>