<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    request.setAttribute("bottomNav", "mypage");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>잔액 꺼내기</title>

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

            <!-- 뒤로가기 -->
            <div class="wallet-top">
                <a href="${pageContext.request.contextPath}/wallet/wallet-manage.jsp" class="back-btn">
                    <span class="material-icons">chevron_left</span>
                    <span>뒤로가기</span>
                </a>
            </div>

            <!-- 제목 -->
            <div class="wallet-section-title">
                <h2>잔액 꺼내기</h2>
                <p>지갑에 있는 금액을 내 계좌로 보낼 수 있어요</p>
            </div>

            <!-- 계좌번호 -->
            <div class="input-box">
                <input type="text" placeholder="계좌번호를 입력하세요">
            </div>

            <!-- 은행 선택 -->
            <div class="input-box">
                <input type="text" placeholder="은행명을 입력하세요">
            </div>

            <!-- 금액 입력 + 전액 -->
            <div class="amount-row">
                <div class="amount-input">
                    <span class="won">₩</span>
                    <input type="text" placeholder="금액 입력">
                </div>
                <button class="all-btn">전액</button>
            </div>

            <!-- 버튼 -->
            <button class="charge-btn">잔액 꺼내기</button>

        </div>
    </main>

    <jsp:include page="../layout/bottomNav.jsp" />

</div>
</body>
</html>