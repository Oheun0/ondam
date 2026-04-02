<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    request.setAttribute("bottomNav", "mypage");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>충전하기</title>

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
                <div class="wallet-section-title">
                    <h2>충전하기</h2>
                    <p>내 사람들과 함께 쓸 금액을 충전할 수 있어요</p>
                </div>

                <div class="charge-input-box">
                    <span class="won">₩</span>
                    <input type="text" placeholder="충전할 금액을 입력하세요"><!-- 숫자만 입력 가능하게 -->
                </div>

                <div class="quick-amount">
                    <button type="button">+ 1만원</button>
                    <button type="button">+ 3만원</button>
                    <button type="button">+ 5만원</button>
                    <button type="button">+ 10만원</button>
                </div>

                <button type="button" class="charge-btn">충전하기</button>

                <div class="wallet-link-box">
                    <a href="${pageContext.request.contextPath}/wallet?action=history" class="wallet-link">
                        사용내역 보기
                    </a>
                </div>
            </div>
        </div>
    </main>

    <jsp:include page="../layout/bottomNav.jsp" />

</div>
</body>
</html>