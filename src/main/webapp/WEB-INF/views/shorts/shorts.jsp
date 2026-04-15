<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,1,0" rel="stylesheet">
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/shorts.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/poke.css"> 
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-detail.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/share-modal.css">
</head>

<body data-context-path="${pageContext.request.contextPath}" 
      data-login-user="${not empty sessionScope.loginUser ? 'true' : ''}"
      data-kakao-js-key="${initParam.kakaoJavascriptKey}">

<div id="option-toast" class="option-toast hidden" role="alert" aria-live="assertive" aria-hidden="true">
  <span class="material-icons option-toast__icon" aria-hidden="true">error</span>
  <span class="option-toast__text">먼저 색상과 사이즈를 골라주세요</span>
</div>

<div id="success-toast" class="option-toast hidden" role="status" aria-live="polite" aria-hidden="true"
     style="position:fixed; top:20px; left:50%; transform:translateX(-50%); z-index:999999;
            background:rgba(76, 175, 80, 0.9); color:#fff; padding:10px 20px; border-radius:20px;
            display:flex; align-items:center; gap:8px; transition: opacity 0.3s ease;">
  <span class="material-icons option-toast__icon" aria-hidden="true" style="color:#fff;">check_circle</span>
  <span id="success-toast-text"></span>
</div>

<div class="app-shell">
    <div class="shorts-wrapper">
        <c:choose>
            <c:when test="${not empty shortsList}">
                <c:forEach var="shorts" items="${shortsList}" varStatus="status">
                    <section class="shorts-container" data-index="${status.index}">
                        <video class="shorts-video" loop playsinline onclick="toggleVideoPlay(this)" ${status.first ? 'autoplay' : ''}>
                            <source src="${pageContext.request.contextPath}/uploads/shorts/${shorts.videoFile}" type="video/mp4">
                        </video>
                        
                        <h2 class="shorts-top-title" 
                            onclick="event.stopPropagation(); openPurchaseSheet('${shorts.productNo}', '${shorts.productPrice}');">
                            ${shorts.shortsTitle}
                        </h2>

                        <aside class="side-actions">
                            <button class="action-btn" onclick="event.stopPropagation(); openPurchaseSheet('${shorts.productNo}', '${shorts.productPrice}');">
                                <span class="material-icons">shopping_bag</span>
                                <span>구매</span>
                            </button>
                            
                            <button class="action-btn" onclick="toggleLike(this, ${shorts.productNo})">
                                <c:set var="isLiked" value="${not empty wishSet and wishSet.contains(shorts.productNo)}" />
                                <span class="material-icons ${isLiked ? 'liked' : ''}">${isLiked ? 'favorite' : 'favorite_border'}</span>
                                <span>찜</span>
                            </button>
                            
                            <button class="action-btn" onclick="event.stopPropagation(); openShareModalFromShorts('${shorts.productNo}', '${shorts.shortsTitle}', '${shorts.imgFile}');">
                                <span class="material-icons">share</span>
                                <span>공유</span>
                            </button>
                            
                            <button class="action-btn mute-btn" onclick="event.stopPropagation(); toggleGlobalMute();">
                                <span class="material-icons muteIcon">volume_up</span>
                                <span class="muteText">소리 켬</span>
                            </button>
                        </aside>
                        
                        <div class="bottom-info-wrapper">
                            <section class="shorts-product-card" onclick="location.href='${pageContext.request.contextPath}/product?action=detail&productNo=${shorts.productNo}'">
                                <div class="card-image">
                                    <img src="${pageContext.request.contextPath}/uploads/products/${shorts.imgFile}" onerror="this.src='${pageContext.request.contextPath}/images/no-image.png'" alt="상품 이미지">
                                </div>
                                <div class="card-info">
                                    <span class="product-name">${shorts.productName}</span>
                                    <span class="product-price">
                                        <c:if test="${shorts.discountRate > 0}">
                                            <span class="discount">${shorts.discountRate}%</span> 
                                        </c:if> 
                                        <span class="price"><fmt:formatNumber value="${shorts.productPrice}" type="number"/>원</span> 
                                    </span>
                                </div>
                                <div class="card-action">
                                    <button class="more-btn">상세보기</button>
                                </div>
                            </section>
                        </div>
                    </section>
                </c:forEach>
            </c:when>

            <c:otherwise>
                <div class="no-shorts-container">
                    <article class="no-shorts-card">
                        <div class="no-shorts-icon-box">
                            <span class="material-icons" aria-hidden="true">videocam_off</span>
                        </div>
                        <h2>영상이 없어요</h2>
                        <p>현재 등록된 추천 영상이 없습니다.</p>
                        <a href="${pageContext.request.contextPath}/main" class="go-home-btn">쇼핑하러 가기</a>
                    </article>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    
    <jsp:include page="../layout/bottomNav.jsp" />                  
    
    <jsp:include page="/WEB-INF/views/product/product-detail-sheet.jsp" />
    <jsp:include page="/WEB-INF/views/poke/poke-modal.jsp" />
    <jsp:include page="/WEB-INF/views/gift/gift-modal.jsp" />
    <jsp:include page="/WEB-INF/views/product/share-modal.jsp" />
</div> 

<input type="hidden" id="trueFamilyNoForShorts" value="${not empty pokeMemberList ? pokeMemberList[0].familyNo : 0}">

<script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>
<script src="${pageContext.request.contextPath}/js/shorts.js"></script>
<script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
  
</body>
</html>