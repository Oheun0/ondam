<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    request.setAttribute("bottomNav", "shorts");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>온담 - 영상보기</title>

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/shorts.css">
</head>
<body>
<div class="app-shell">

    <div class="shorts-wrapper">
        <c:forEach var="shorts" items="${shortsList}" varStatus="status">
            <section class="shorts-container" data-index="${status.index}">
                <video class="shorts-video" loop muted playsinline ${status.first ? 'autoplay' : ''}>
                    <source src="${pageContext.request.contextPath}/upload/shorts/${shorts.videoFile}" type="video/mp4">
                </video>

                <aside class="side-actions">
                    <button class="action-btn" onclick="addToCart(${shorts.productNo})">
                        <span class="material-icons">shopping_cart</span>
                        <span>장바구니</span>
                    </button>
                    <button class="action-btn" onclick="toggleLike(${shorts.shortsNo})">
                        <span class="material-icons">favorite_border</span>
                        <span>찜</span>
                    </button>
                    <button class="action-btn" onclick="openJoreugi(${shorts.productNo})">
                        <span class="material-icons">pan_tool</span>
                        <span>조르기</span>
                    </button>
                    <button class="action-btn" onclick="openGift(${shorts.productNo})">
                        <span class="material-icons">card_giftcard</span>
                        <span>선물하기</span>
                    </button>
                    <button class="action-btn" onclick="location.href='${pageContext.request.contextPath}/product/detail?no=${shorts.productNo}'">
                        <span class="material-icons">search</span>
                        <span>상세보기</span>
                    </button>
                </aside>

                <section class="product-info-overlay">
                    <h2>${shorts.shortsTitle}</h2>
                    <p>${shorts.shortsContent}</p>
                </section>
            </section>
        </c:forEach>
        
        <jsp:include page="../layout/bottomNav.jsp" />
    </div>
    </div>
</body>
</html>