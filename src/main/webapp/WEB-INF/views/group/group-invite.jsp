<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    request.setAttribute("bottomNav", "group");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>내 사람 초대하기</title>

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/wallet.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/group.css">
</head>

<body data-context-path="${pageContext.request.contextPath}">
<div class="app-shell app-shell--group">

    <jsp:include page="../layout/header.jsp" />

    <main class="main-content group-main">
        <div class="group-page">

            <div class="wallet-top">
                <a href="${pageContext.request.contextPath}/group" class="back-btn">
                    <span class="material-icons">arrow_back_ios</span>
                    <span>뒤로가기</span>
                </a>
            </div>

            <section class="group-invite-head">
                <h1 class="group-invite-title">내 사람 초대하기</h1>
                <p class="group-invite-desc">가족, 친구, 보호자를 초대할 수 있어요</p>
            </section>

            <section class="group-invite-card">
                <p class="group-invite-label">초대 코드</p>

                <div class="group-invite-code-row">
                    <div class="group-invite-code-box">
                        <span class="group-invite-code">${myGroup.familyInviteCode}</span>
                    </div>

                    <button type="button" class="group-copy-btn">
                        복사
                    </button>
                </div>

                <p class="group-invite-guide">
                    초대 코드를 보내면 내 사람으로 연결할 수 있어요
                </p>
            </section>

        </div>
    </main>

    <jsp:include page="../layout/bottomNav.jsp" />

</div>
</body>
</html>