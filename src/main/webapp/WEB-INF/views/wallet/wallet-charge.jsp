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

                <form id="chargeForm"
                      action="${pageContext.request.contextPath}/wallet?action=chargeSubmit"
                      method="post">
                    <div class="charge-input-box">
                        <span class="won">₩</span>
                        <input type="text" id="amountInput" name="amount"
                               placeholder="충전할 금액을 입력하세요"
                               autocomplete="off">
                    </div>

                    <div class="quick-amount">
                        <button type="button" onclick="addAmount(10000)">+ 1만원</button>
                        <button type="button" onclick="addAmount(30000)">+ 3만원</button>
                        <button type="button" onclick="addAmount(50000)">+ 5만원</button>
                        <button type="button" onclick="addAmount(100000)">+ 10만원</button>
                    </div>

                    <button type="submit" class="charge-btn">충전하기</button>
                </form>
                
                <div class="wallet-link-box">
                    <a href="${pageContext.request.contextPath}/wallet?action=history" class="wallet-link">
                    </a>
                </div>
            </div>
        </div>
    </main>

    <jsp:include page="../layout/bottomNav.jsp" />

</div>
<script>
	const input = document.getElementById('amountInput');
	
	// 숫자만 입력, 천 단위 콤마 표시
	input.addEventListener('input', function () {
	    let raw = this.value.replace(/[^0-9]/g, '');
	    this.value = raw ? Number(raw).toLocaleString() : '';
	});
	
	// 빠른 금액 버튼 — 기존 금액에 더하기
	function addAmount(amount) {
	    let current = parseInt(input.value.replace(/,/g, '')) || 0;
	    input.value = (current + amount).toLocaleString();
	}
	
	// 폼 제출 시 콤마 제거하고 숫자만 전송
	document.getElementById('chargeForm').addEventListener('submit', function () {
	    input.value = input.value.replace(/,/g, '');
	});
</script>
</body>
</html>