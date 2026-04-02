<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    request.setAttribute("bottomNav", "group");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>초대 코드 입력하기</title>

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/group.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/wallet.css">
</head>

<body data-context-path="${pageContext.request.contextPath}">
<div class="app-shell app-shell--group">

    <jsp:include page="../layout/header.jsp" />

    <main class="main-content group-main">
        <div class="group-page">

            <div class="wallet-top">
                <a href="${pageContext.request.contextPath}/group?action=list" class="back-btn">
                    <span class="material-icons">arrow_back_ios</span>
                    <span>뒤로가기</span>
                </a>
            </div>

            <section class="group-invite-head">
                <h1 class="group-invite-title">초대 코드 입력하기</h1>
                <p class="group-invite-desc">받은 초대 코드를 입력하면 <br> 내 사람으로 연결할 수 있어요</p>
            </section>

            <section class="edit-card">
                <form class="edit-form" action="#" method="post">
                    <div class="form-block">
                        <label for="inviteCode" class="block-label">초대 코드</label>
                        <input
                            type="text"
                            id="inviteCode"
                            name="inviteCode"
                            class="input-box"
                            placeholder="초대 코드를 입력하세요"
                            maxlength="64"
                            autocomplete="off">
                    </div>

                    <button type="submit" class="save-btn">연결하기</button>

                    <p class="group-invite-guide">
                        초대 코드를 입력하면 같은 그룹으로 연결돼요
                    </p>
                </form>
            </section>

        </div>
    </main>

    <jsp:include page="../layout/bottomNav.jsp" />

</div>
<script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>
</body>
</html>