<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="ko_KR" />

<%
    request.setAttribute("bottomNav", "group");

    java.util.Calendar cal = java.util.Calendar.getInstance();
    request.setAttribute("todayDate", cal.getTime());
    cal.add(java.util.Calendar.DATE, -1);
    request.setAttribute("yesterdayDate", cal.getTime());
%>

<fmt:formatDate value="${todayDate}"     pattern="yyyy-MM-dd" var="todayStr" />
<fmt:formatDate value="${yesterdayDate}" pattern="yyyy-MM-dd" var="yesterdayStr" />

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>선물하기</title>

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/group.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/wallet.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/gift.css">
</head>

<body data-context-path="${pageContext.request.contextPath}">
<div class="app-shell app-shell--group">

    <jsp:include page="../layout/header.jsp" />

    <main class="main-content group-main">
        <div class="group-page gift-chat-page">

            <div class="wallet-top gift-chat-wallet-top">
                <a href="${pageContext.request.contextPath}/group" class="back-btn">
                    <span class="material-icons">arrow_back_ios</span>
                    <span>뒤로가기</span>
                </a>
                <a href="${pageContext.request.contextPath}/poke?action=list&fromNo=${otherNo}" 
				   class="gift-chat-poke-link">
				    <span class="material-icons">volunteer_activism</span>
				    조르기 목록보기
				</a>
            </div>

            <div class="topbar-center topbar-user">
                <span class="topbar-username">${otherName}님</span>
            </div>

            <!-- =====================
                 아무 내역 없을 때
            ===================== -->
            <c:if test="${empty chatList}">
                <div class="group-empty-card">
                    <h3 class="group-empty-title">아직 주고받은 선물이 없어요</h3>
                    <p class="group-empty-desc">
                        상대의 조르기 목록을 통해<br>
                        내 사람이 갖고 싶은 상품을 선물해보세요
                    </p>
                </div>
            </c:if>

            <!-- =====================
                 채팅 영역
            ===================== -->
            <c:if test="${not empty chatList}">
            <div class="gift-chat-wrap">

                <c:set var="prevDate" value="" />

                <c:forEach var="chat" items="${chatList}">

                    <%-- 날짜 파싱 --%>
                    <fmt:parseDate value="${chat.sentAt}" pattern="yyyy-MM-dd HH:mm:ss" var="parsedDate" />
                    <fmt:formatDate value="${parsedDate}" pattern="yyyy-MM-dd" var="currentDateStr" />

                    <%-- 날짜 구분선 --%>
                    <c:if test="${currentDateStr ne prevDate}">
                        <c:choose>
                            <c:when test="${currentDateStr eq todayStr}">
                                <c:set var="displayDate" value="오늘" />
                            </c:when>
                            <c:when test="${currentDateStr eq yesterdayStr}">
                                <c:set var="displayDate" value="어제" />
                            </c:when>
                            <c:otherwise>
                                <fmt:formatDate value="${parsedDate}" pattern="yyyy년 MM월 dd일" var="displayDate" />
                            </c:otherwise>
                        </c:choose>
                        <div class="gift-date-divider">
                            <span>${displayDate}</span>
                        </div>
                        <c:set var="prevDate" value="${currentDateStr}" />
                    </c:if>

                    <%-- ========================
                         chatType=0 : 선물 카드
                    ======================== --%>
                    <c:if test="${chat.chatType == 0}">
                        <c:set var="gift" value="${giftMap[chat.giftNo]}" />

                        <div class="gift-bubble ${chat.senderNo == myUserNo ? 'gift-bubble--right' : 'gift-bubble--left'}">
                            <div class="gift-card">
                                <img src="${pageContext.request.contextPath}/images/gift/${chat.cardImg}"
                                     class="gift-card-img"
                                     alt="선물 카드">

                                <p class="gift-meta">
                                    <c:choose>
                                        <c:when test="${chat.senderNo == myUserNo}">${otherName}님에게 선물을 보냈어요!</c:when>
                                        <c:otherwise>${otherName}님이 선물을 보냈어요!</c:otherwise>
                                    </c:choose>
                                </p>

                                <div class="gift-product-summary">
                                    <div class="gift-product-thumb-wrap">
                                        <img src="${pageContext.request.contextPath}/uploads/products/${gift.productImg}"
                                             class="gift-product-thumb"
                                             alt="상품 이미지"
                                             onerror="this.src='${pageContext.request.contextPath}/images/no-image.png'; this.onerror=null;">
                                    </div>
                                    <div class="gift-product-info">
                                        <p class="gift-product-brand">${gift.productBrand}</p>
                                        <p class="gift-product-name">${gift.productName}</p>
                                    </div>
                                </div>

                                <div class="gift-notice-box">
                                    <p class="gift-notice-text">
                                        <c:choose>
                                            <c:when test="${gift.giftState == 0}">
                                                <c:choose>
                                                    <c:when test="${chat.senderNo != myUserNo}">
                                                        배송지를 아직 입력하지 않았어요.<br>선물함에서 확인해보세요!
                                                    </c:when>
                                                    <c:otherwise>
                                                        상대방이 아직 배송지를 입력하지 않았어요.
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:when test="${gift.giftState == 1}">배송지가 입력되었어요 ✅</c:when>
                                            <c:when test="${gift.giftState == 2}">선물이 거절되었어요.</c:when>
                                            <c:when test="${gift.giftState == 3}">배송지 입력 기한이 만료되었어요.</c:when>
                                        </c:choose>
                                    </p>
                                </div>

                                <a href="${pageContext.request.contextPath}/gift"
                                   class="gift-go-btn">
                                    <c:choose>
                                        <c:when test="${chat.senderNo == myUserNo}">선물 상태 보기</c:when>
                                        <c:otherwise>선물함 가기</c:otherwise>
                                    </c:choose>
                                </a>

                                <%-- 고마움 표시하기: 내가 받은 선물일 때만 노출 --%>
                                <c:if test="${chat.senderNo != myUserNo}">
                                    <c:if test="${thanksMap[gift.giftNo] == null}">
                                        <a href="${pageContext.request.contextPath}/gift?action=thanks&giftNo=${gift.giftNo}&otherNo=${otherNo}"
                                           class="gift-go-btn gift-go-btn--secondary">
                                            고마움 표시하기
                                        </a>
                                    </c:if>
                                </c:if>

                            </div>
                            <fmt:formatDate value="${parsedDate}" pattern="a h:mm" var="timeStr" />
                            <p class="gift-time">${timeStr}</p>
                        </div>
                    </c:if>

                    <%-- ========================
                         chatType=1 : 감사 카드
                    ======================== --%>
                    <c:if test="${chat.chatType == 1}">
                        <div class="gift-bubble ${chat.senderNo == myUserNo ? 'gift-bubble--right' : 'gift-bubble--left'}">
                            <div class="thanks-card">

                                <img src="${pageContext.request.contextPath}/images/gift/${chat.cardImg}"
                                     class="gift-card-img"
                                     alt="감사 카드">

                                <p class="gift-meta">
                                    <c:choose>
                                        <c:when test="${chat.senderNo == myUserNo}">${otherName}님에게 감사 카드를 보냈어요</c:when>
                                        <c:otherwise>${otherName}님이 감사 카드를 보냈어요</c:otherwise>
                                    </c:choose>
                                </p>

                                <div class="gift-notice-box">
                                    <p class="gift-notice-text">
                                        고마워요. 정말 마음에 들어요 😊
                                    </p>
                                </div>

                            </div>
                            <fmt:formatDate value="${parsedDate}" pattern="a h:mm" var="timeStr" />
                            <p class="gift-time">${timeStr}</p>
                        </div>
                    </c:if>

                </c:forEach>

            </div>
            </c:if>

        </div>

    </main>

    <jsp:include page="../layout/bottomNav.jsp" />

</div>
<script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>
</body>
</html>