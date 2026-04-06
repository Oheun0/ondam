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
<title>사용내역</title>

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
					<div class="wallet-section-title">
						<h2>사용내역</h2>
						<p>충전하고 사용한 내역을 확인할 수 있어요</p>
					</div>

					<div class="history-list">
						<c:choose>
							<c:when test="${empty historyList}">
								<p class="wallet-desc"
									style="text-align: center; padding: 20px 0;">사용내역이 없어요</p>
							</c:when>
							<c:otherwise>
								<c:forEach var="tx" items="${historyList}">
									<div class="history-item">
										<div class="history-text">
											<strong>
											    <c:choose>
													<c:when test="${not empty tx.transactionMemo}">${tx.transactionMemo}</c:when>
													<c:when test="${tx.transactionType == 0}">지갑 충전</c:when>
													<c:when test="${tx.transactionType == 1}">상품 결제</c:when>
													<c:when test="${tx.transactionType == 2}">잔액 꺼내기</c:when>
													<c:otherwise>기타</c:otherwise>
												</c:choose>
											</strong>
										    <p class="tx-username">${tx.userName}</p>
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