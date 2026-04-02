<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    request.setAttribute("bottomNav", "mypage");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>지갑 관리</title>

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/wallet.css">
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
                    <a href="${pageContext.request.contextPath}/wallet?action=charge" class="wallet-action-btn charge">
                        충전하기
                    </a>
                    <a href="${pageContext.request.contextPath}/wallet?action=withdraw" class="wallet-action-btn withdraw">
                        잔액 꺼내기
                    </a>
                </div>

                <div class="wallet-section-title history-title-row">
                    <h2>최근 사용내역</h2> <!-- 최근 사용내역 3개 카드만 보여줌 -->
                    <a href="${pageContext.request.contextPath}/wallet?action=history" class="more-link">전체보기</a>
                </div>

                <div class="history-list">
                    <div class="history-item">
                        <div class="history-text">
                            <strong>지갑 충전</strong>
                            <p>2026.04.01 14:20</p>
                        </div>

                        <div class="history-right">
                            <span class="history-amount plus">+50,000원</span>
                            <span class="history-balance">50,000원</span>
                        </div>
                    </div>

                    <div class="history-item">
                        <div class="history-text">
                            <strong>상품 결제</strong>
                            <p>2026.03.31 18:40</p>
                        </div>

                        <div class="history-right">
                            <span class="history-amount minus">-29,900원</span>
                            <span class="history-balance">0원</span>
                        </div>
                    </div>

                    <div class="history-item">
                        <div class="history-text">
                            <strong>주문 취소 환불</strong>
                            <p>2026.03.30 09:10</p>
                        </div>

                        <div class="history-right">
                            <span class="history-amount plus">+12,000원</span>
                            <span class="history-balance">17,900원</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <jsp:include page="../layout/bottomNav.jsp" />

</div>
</body>
</html>