<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- 오전/오후 표시를 한국어로 강제하기 위한 설정 --%>
<fmt:setLocale value="ko_KR" />

<%
    request.setAttribute("bottomNav", "group");

    // [추가됨] 자바 로직으로 오늘과 어제 날짜를 구해서 JSP에서 쓸 수 있게 넘겨줍니다.
    java.util.Calendar cal = java.util.Calendar.getInstance();
    request.setAttribute("todayDate", cal.getTime()); // 오늘
    
    cal.add(java.util.Calendar.DATE, -1);
    request.setAttribute("yesterdayDate", cal.getTime()); // 어제
%>

<%-- "yyyy-MM-dd" 형태 --%>
<fmt:formatDate value="${todayDate}" pattern="yyyy-MM-dd" var="todayStr" />
<fmt:formatDate value="${yesterdayDate}" pattern="yyyy-MM-dd" var="yesterdayStr" />

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>선물함</title>

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/category.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/wallet.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/group.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/gift.css">
</head>

<body data-context-path="${pageContext.request.contextPath}">
<div class="app-shell app-shell--gift">

    <jsp:include page="../layout/header.jsp" />

    <main class="main-content gift-main">
        <div class="gift-page">

			<c:if test="${not empty param.from}">
                <c:set var="giftOrigin" value="${param.from}" scope="session" />
            </c:if>

            <c:choose>
                <c:when test="${sessionScope.giftOrigin == 'mypage'}">
                    <c:set var="backLink" value="/mypage" />
                </c:when>
                <c:otherwise>
                    <c:set var="backLink" value="/group" />
                </c:otherwise>
            </c:choose>
            
            <div class="wallet-top">
                <a href="${pageContext.request.contextPath}${backLink}" class="back-btn">
                    <span class="material-icons">arrow_back_ios</span>
                    <span>뒤로가기</span>
                </a>
            </div>

            <!-- 상단 탭 메뉴 (JS 기반) -->
            <div class="category-top-tabs gift-box-tab-bar" role="tablist" aria-label="선물함 보기">
                <button type="button" class="top-tab active" data-tab="received" id="tabGiftReceived" role="tab" aria-selected="true" aria-controls="gift-received-panel">받은 선물</button>
                <button type="button" class="top-tab" data-tab="sent" id="tabGiftSent" role="tab" aria-selected="false" aria-controls="gift-sent-panel">보낸 선물</button>
            </div>

            <!-- 1. 받은 선물 패널 -->
            <div class="tab-content active gift-box-tab-panel" id="gift-received-panel" role="tabpanel" aria-labelledby="tabGiftReceived">
                <section class="gift-box-section">
                    <div class="gift-chat-wrap gift-chat-wrap--box">
                        
                        <c:if test="${empty receivedList}">
                            <div class="gift-empty-card">
                                <div class="gift-empty-icon"><span class="material-icons">redeem</span></div>
                                <h3 class="gift-empty-title">아직 받은 선물이 없어요</h3>
                                <p class="gift-empty-desc">내 사람이 보낸 선물이 여기에 보여요</p>
                            </div>
                        </c:if>

                        <%-- 날짜 그룹화를 위해 이전 날짜를 기억할 변수를 초기화합니다. --%>
                        <c:set var="prevDate" value="" />
                        
                        <c:forEach var="gift" items="${receivedList}">
                            <%-- 1. DB의 sentAt 문자열을 Date 객체로 파싱 --%>
                            <fmt:parseDate value="${gift.sentAt}" pattern="yyyy-MM-dd HH:mm:ss" var="parsedDate" />
                            <%-- 2. 년-월-일만 추출 (구분선 비교용) --%>
                            <fmt:formatDate value="${parsedDate}" pattern="yyyy-MM-dd" var="currentDateStr" />

                            <%-- 3. 이전 날짜와 현재 날짜가 다를 때만 구분선을 출력합니다. --%>
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
                                
                                <%-- 다음 바퀴에서 비교하기 위해 prevDate를 업데이트 --%>
                                <c:set var="prevDate" value="${currentDateStr}" />
                            </c:if>

                            <div class="gift-bubble gift-bubble--center">
                                <div class="gift-card">
                                    <img src="${pageContext.request.contextPath}/images/gift/${empty gift.cardImg ? 'gift-card1.png' : gift.cardImg}" class="gift-card-img" alt="선물 카드">

                                    <p class="gift-meta">${gift.senderName}님이 선물을 보냈어요!</p>

                                    <div class="gift-product-summary">
                                        <div class="gift-product-thumb-wrap">
                                            <img src="${pageContext.request.contextPath}/uploads/products/${gift.productImg}" class="gift-product-thumb" alt="상품 이미지"
                                            onerror="this.src='${pageContext.request.contextPath}/images/no-image.png'; this.onerror=null;">
                                        </div>
                                        <div class="gift-product-info">
                                            <p class="gift-product-brand">${gift.productBrand}</p>
                                            <p class="gift-product-name">${gift.productName}</p>
                                            <c:if test="${not empty gift.giftMsg}">
                                                <p class="gift-msg" style="color:#666; font-style:italic;">"${gift.giftMsg}"</p>
                                            </c:if>
                                        </div>
                                    </div>

                                    <c:choose>
                                        <c:when test="${gift.giftState == 0}">
    <div class="gift-address-box" id="display-address-${gift.giftNo}">
        <p class="gift-address-label">선택된 배송지 : ${gift.receiverAddressName}</p>
        <p class="gift-address-text">${gift.receiverName} · ${gift.receiverPhoneNumber}<br>
        (${gift.receiverZipcode}) ${gift.receiverAddress} ${gift.receiverDetailAddress}</p>
    </div>
    
    <%-- 링크 대신 모달을 여는 버튼으로 변경 --%>
    <button type="button" class="gift-go-btn gift-go-btn--secondary" onclick="openAddressModal('${gift.giftNo}')">다른 배송지 선택하기</button>
                                            <div class="gift-action-row">
											    <c:choose>
											        <%-- 배송지가 하나도 없는 경우 --%>
											        <c:when test="${gift.addressNo == -1}">
											            <a href="javascript:void(0);" 
											               onclick="alert('기본 배송지가 없습니다. 배송지를 먼저 등록해주세요!'); location.href='${pageContext.request.contextPath}/profile-address';"
											               class="gift-action-btn gift-action-btn--accept">배송지 등록 후 수락</a>
											        </c:when>
											        
											        <%-- 배송지가 있는 경우 (기본 배송지 번호를 포함시킴) --%>
											        <c:otherwise>
											            <a href="${pageContext.request.contextPath}/gift?action=accept&giftNo=${gift.giftNo}&addressNo=${gift.addressNo}" 
											               id="accept-btn-${gift.giftNo}" 
											               class="gift-action-btn gift-action-btn--accept">수락하기</a>
											        </c:otherwise>
											    </c:choose>
											    
											    <a href="${pageContext.request.contextPath}/gift?action=reject&giftNo=${gift.giftNo}" 
											       class="gift-action-btn gift-action-btn--reject">거절하기</a>
											</div>
                                        </c:when>
                                        <c:when test="${gift.giftState == 1}">
                                            <div class="gift-state-box gift-state-box--success">선물을 받았어요!</div>
                                        </c:when>
                                        <c:when test="${gift.giftState == 2}">
                                            <div class="gift-state-box gift-state-box--reject">선물을 거절했어요</div>
                                        </c:when>
                                    </c:choose>
                                </div>
                                
                                <%-- 4. "오전/오후 H시 mm분" 형태 --%>
                                <fmt:formatDate value="${parsedDate}" pattern="a h시 mm분" var="timeStr" />
                                <p class="gift-time">${timeStr}</p>
                            </div>
                        </c:forEach>

                    </div>
                </section>
            </div>

            <!-- 2. 보낸 선물 패널 -->
            <div class="tab-content gift-box-tab-panel" id="gift-sent-panel" role="tabpanel" aria-labelledby="tabGiftSent" hidden>
                <section class="gift-box-section">
                    <div class="gift-chat-wrap gift-chat-wrap--box">
                        
                        <c:if test="${empty sentList}">
                            <div class="gift-empty-card">
                                <div class="gift-empty-icon"><span class="material-icons">redeem</span></div>
                                <h3 class="gift-empty-title">아직 보낸 선물이 없어요</h3>
                                <p class="gift-empty-desc">내 사람에게 첫 선물을 보내보세요</p>
                            </div>
                        </c:if>

                        <%-- 보낸 선물에서도 동일하게 prevDate를 초기화하고 날짜를 묶어줍니다. --%>
                        <c:set var="prevDate" value="" />
                        
                        <c:forEach var="gift" items="${sentList}">
                            <fmt:parseDate value="${gift.sentAt}" pattern="yyyy-MM-dd HH:mm:ss" var="parsedDate" />
                            <fmt:formatDate value="${parsedDate}" pattern="yyyy-MM-dd" var="currentDateStr" />

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

                            <div class="gift-bubble gift-bubble--center">
                                <div class="gift-card">
                                    <img src="${pageContext.request.contextPath}/images/gift/${empty gift.cardImg ? 'gift-card1.png' : gift.cardImg}" class="gift-card-img" alt="선물 카드">

                                    <p class="gift-meta">${gift.receiverName}님에게 선물을 보냈어요!</p>

                                    <div class="gift-product-summary">
                                        <div class="gift-product-thumb-wrap">
                                            <img src="${pageContext.request.contextPath}/uploads/products/${gift.productImg}" class="gift-product-thumb" alt="상품 이미지">
                                        </div>
                                        <div class="gift-product-info">
                                            <p class="gift-product-brand">${gift.productBrand}</p>
                                            <p class="gift-product-name">${gift.productName}</p>
                                        </div>
                                    </div>

                                    <div class="gift-notice-box">
                                        <p class="gift-notice-text">
                                            <c:choose>
                                                <c:when test="${gift.giftState == 0}">상대가 아직 선물을 확인하지 않았어요.</c:when>
                                                <c:when test="${gift.giftState == 1}">상대가 배송지를 확인했어요!<br>현재 선물이 배송 준비 중이에요.</c:when>
                                                <c:when test="${gift.giftState == 2}">상대가 선물을 거절하여 취소 처리됩니다.</c:when>
                                            </c:choose>
                                        </p>
                                    </div>
                                </div>
                                
                                <%-- 시간 표시 --%>
                                <fmt:formatDate value="${parsedDate}" pattern="a h시 mm분" var="timeStr" />
                                <p class="gift-time">${timeStr}</p>
                            </div>
                        </c:forEach>

                    </div>
                </section>
            </div>

        </div>
    </main>
	<jsp:include page="gift-address.jsp" />
    <jsp:include page="../layout/bottomNav.jsp" />
</div>
<script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>
<script src="${pageContext.request.contextPath}/js/gift-box.js"></script>
</body>
</html>