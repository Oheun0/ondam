<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    request.setAttribute("bottomNav", "group");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>내 사람</title>

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/group.css">
</head>

<body data-context-path="${pageContext.request.contextPath}">
<div class="app-shell app-shell--group">

    <jsp:include page="../layout/header.jsp" />

    <main class="main-content group-main">
        <div class="group-page">

            <section class="group-family-head">
                <h1 class="group-family-name">내 사람</h1>
                <p class="group-family-desc">내 사람들과 함께 쇼핑하고 선물할 수 있어요</p>
            </section>

            <section class="group-empty-card">
                <div class="group-empty-icon">
                    <span class="material-icons">groups</span>
                </div>

                <h2 class="group-empty-title">아직 연결된 내 사람이 없어요</h2>
                <p class="group-empty-desc">내 사람을 초대하거나 받은 초대 코드를 통해 <br> 연결할 수 있어요</p>

                <div class="group-empty-btns">
			    <a href="${pageContext.request.contextPath}/group/group-invite.jsp"
			       class="group-empty-btn group-empty-btn--primary">
			        내 사람 초대하기
			    </a>
			
			    <a href="${pageContext.request.contextPath}/group/group-join.jsp"
			       class="group-empty-btn group-empty-btn--secondary">
			        초대 코드 입력하기
			    </a>
			</div>
            </section>

        </div>
    </main>

    <jsp:include page="../layout/bottomNav.jsp" />

</div>
</body>
</html>