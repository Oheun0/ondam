<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,500,0,0" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/group.css">
</head>

<body data-context-path="${pageContext.request.contextPath}">
<div class="app-shell">

    <jsp:include page="../layout/header.jsp" />

    <main class="main-content group-main">
        <div class="group-page">

            <section class="group-family-head">
                <h1 class="group-family-name">${myGroup.familyName}</h1>
                <p class="group-family-desc">내 사람들과 함께 쇼핑하고 선물할 수 있어요</p>
            </section>

            <section class="group-member-list">

                <!-- 나 -->
					<c:forEach var="m" items="${memberList}">
						<c:if test="${m.userNo == myMember.userNo}">
							<article class="group-member-card">
								<div class="member-thumb-wrap">
									<img
										src="${pageContext.request.contextPath}/images/profile/default-profile.png"
										alt="${m.userName} 프로필" class="member-thumb">
								</div>
								<div class="member-content">
									<div class="member-name-row">
										<span class="member-me-badge">나</span> <strong
											class="member-name">${m.userName}</strong>
									</div>
									<div class="member-btn-row">
										<a href="#" class="member-btn member-btn--gift"> <span
											class="material-symbols-outlined member-btn-gift-icon"
											aria-hidden="true">featured_seasonal_and_gifts</span> 선물하기
										</a> <a href="#" class="member-btn">찜 목록 보기</a>
									</div>
								</div>
							</article>
						</c:if>
					</c:forEach>
					<%-- <article class="group-member-card">
                    <div class="member-thumb-wrap">
                        <img src="${pageContext.request.contextPath}/images/profile/test.jpg" alt="성연수 프로필" class="member-thumb">
                    </div>

                    <div class="member-content">
                        <div class="member-name-row">
                            <span class="member-me-badge">나</span>
                            <strong class="member-name">성연수</strong>
                        </div>

                        <div class="member-btn-row">
                            <a href="#" class="member-btn member-btn--gift">
                                <span class="material-symbols-outlined member-btn-gift-icon" aria-hidden="true">featured_seasonal_and_gifts</span>
                                선물하기
                            </a>
                            <a href="#" class="member-btn">찜 목록 보기</a>
                        </div>
                    </div>
                </article> --%>
				
				<%-- 2. 나를 제외한 다른 멤버들 --%>
					<c:forEach var="m" items="${memberList}">
						<c:if test="${m.userNo != myMember.userNo}">
							<article class="group-member-card">
								<div class="member-thumb-wrap">
									<img
										src="${pageContext.request.contextPath}/images/profile/default-profile.png"
										alt="${m.userName} 프로필" class="member-thumb">
								</div>
								<div class="member-content">
									<div class="member-name-row">
										<strong class="member-name">${m.userName}</strong>
									</div>
									<div class="member-btn-row">
										<a href="#" class="member-btn member-btn--gift"> <span
											class="material-symbols-outlined member-btn-gift-icon"
											aria-hidden="true">featured_seasonal_and_gifts</span> 선물하기
										</a> <a href="#" class="member-btn">찜 목록 보기</a>
									</div>
								</div>
							</article>
						</c:if>
					</c:forEach>
					<%-- <!-- 멤버 1 -->
                <article class="group-member-card">
                    <div class="member-thumb-wrap">
                        <img src="${pageContext.request.contextPath}/images/profile/default-profile.png" alt="김남준 프로필" class="member-thumb">
                    </div>

                    <div class="member-content">
                        <div class="member-name-row">
                            <strong class="member-name">김남준</strong>
                        </div>

                        <div class="member-btn-row">
                            <a href="#" class="member-btn member-btn--gift">
                                <span class="material-symbols-outlined member-btn-gift-icon" aria-hidden="true">featured_seasonal_and_gifts</span>
                                선물하기
                            </a>
                            <a href="#" class="member-btn">찜 목록 보기</a>
                        </div>
                    </div>
                </article>

                <!-- 멤버 2 -->
                <article class="group-member-card">
                    <div class="member-thumb-wrap">
                        <img src="${pageContext.request.contextPath}/images/profile/default-profile.png" alt="김가빈 프로필" class="member-thumb">
                    </div>

                    <div class="member-content">
                        <div class="member-name-row">
                            <strong class="member-name">김가빈</strong>
                        </div>

                        <div class="member-btn-row">
                            <a href="#" class="member-btn member-btn--gift">
                                <span class="material-symbols-outlined member-btn-gift-icon" aria-hidden="true">featured_seasonal_and_gifts</span>
                                선물하기
                            </a>
                            <a href="#" class="member-btn">찜 목록 보기</a>
                        </div>
                    </div>
                </article>

                <!-- 멤버 3 -->
                <article class="group-member-card">
                    <div class="member-thumb-wrap">
                        <img src="${pageContext.request.contextPath}/images/profile/default-profile.png" alt="김지현 프로필" class="member-thumb">
                    </div>

                    <div class="member-content">
                        <div class="member-name-row">
                            <strong class="member-name">김지현</strong>
                        </div>

                        <div class="member-btn-row">
                            <a href="#" class="member-btn member-btn--gift">
                                <span class="material-symbols-outlined member-btn-gift-icon" aria-hidden="true">featured_seasonal_and_gifts</span>
                                선물하기
                            </a>
                            <a href="#" class="member-btn">찜 목록 보기</a>
                        </div>
                    </div>
                </article> --%>

            </section>
        </div>
    </main>

    <!-- 하단 고정 액션 버튼 -->
    <div class="group-fixed-actions">
        <a href="#" class="group-fixed-btn">멤버 관리</a>
        <!-- 그룹장일 때
        <a href="${pageContext.request.contextPath}/group/group-manage-owner.jsp" class="group-fixed-btn">멤버 관리</a> -->
        <!-- 일반 멤버일 때
        <a href="${pageContext.request.contextPath}/group/group-manage-member.jsp" class="group-fixed-btn">멤버 관리</a> -->
        <a href="${pageContext.request.contextPath}/wallet/wallet-manage.jsp" class="group-fixed-btn">지갑 관리</a>
        <a href="${pageContext.request.contextPath}/gift/gift-box.jsp" class="group-fixed-btn group-fixed-btn--gift-history">선물함</a>
    </div>

    <jsp:include page="../layout/bottomNav.jsp" />

</div>
</body>
</html>