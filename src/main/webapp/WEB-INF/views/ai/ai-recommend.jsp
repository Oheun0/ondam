<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
  request.setAttribute("bottomNav", "ai");
%>
<!DOCTYPE html>
<html lang="ko" class="ai-rec-page">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <title>오늘의 추천 상품 | 온담</title>
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,1,0" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-detail.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/poke.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/share-modal.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/inquiry-write.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ai-recommend.css">
</head>
<body class="ai-rec-page" data-context-path="${pageContext.request.contextPath}">
  <div id="option-toast" class="option-toast hidden" role="alert" aria-live="assertive" aria-hidden="true">
    <span class="material-icons option-toast__icon" aria-hidden="true">error</span>
    <span class="option-toast__text">먼저 색상과 사이즈를 골라주세요</span>
  </div>

  <div class="detail-shell">
  <div class="app-shell">
    <div class="top-header-cluster">
      <jsp:include page="../layout/header.jsp"/>
    </div>

    <main class="page-wrap ai-rec-main" id="aiRecMain">
      <div id="aiRecContent" class="ai-rec-content" aria-hidden="false">

      <header class="ai-rec-page-head">
        <h1 class="ai-rec-page-head__title">✨ <span style="color: #ff6b6b;">${userName}</span>님만을 위한 추천</h1>
        <p class="ai-rec-page-head__line">계절 정보와 체형, 취향을</p>
        <p class="ai-rec-page-head__line">분석하여 고른 상품입니다!</p>
        <p class="ai-rec-page-head__line ai-rec-page-head__line--sub">매일 새롭게 업데이트 됩니다</p>
      </header>

      <ul class="ai-rec-list" id="aiRecList">
        <c:choose>
            <c:when test="${not empty aiRecList and aiRecList[0].productNo > 0}">
                <c:forEach var="dto" items="${aiRecList}">
                    <c:if test="${dto.productNo > 0}">
                      <li>
                        <!-- [핵심 수정] 상세페이지 링크를 프로젝트 규칙(product?action=detail...)에 맞게 수정했습니다 -->
                        <article class="ai-rec-card product-card" data-detail-href="${pageContext.request.contextPath}/product?action=detail&productNo=${dto.productNo}">
                          
                          <p class="ai-rec-card__phrase" style="${not empty dto.targetName ? 'color: #6c5ce7; background-color: #f3f0ff;' : ''}">
                               ${dto.phrase}
                          </p>
                          
                          <div class="product-thumb-wrap ai-rec-thumb-wrap" style="position: relative;">
                            <c:if test="${not empty dto.targetName}">
                                <div style="position: absolute; top: 12px; right: 12px; background: #6c5ce7; color: white; padding: 6px 14px; border-radius: 20px; font-size: 12px; z-index: 10; font-weight: bold; box-shadow: 0 2px 4px rgba(0,0,0,0.2);">
                                    🎁 For. ${dto.targetName}
                                </div>
                            </c:if>

                            <div class="ai-rec-thumb-link">
                              <img class="ai-rec-thumb-img" src="${pageContext.request.contextPath}/uploads/products/${dto.imgFile}" alt="${dto.productName}" loading="lazy" decoding="async">
                            </div>
                            
                            <c:choose>
                              <c:when test="${dto.wishActive}">
                                <button type="button" class="related-wish-btn product-grid-wish-btn is-active"><span class="material-icons">favorite</span></button>
                              </c:when>
                              <c:otherwise>
                                <button type="button" class="related-wish-btn product-grid-wish-btn"><span class="material-icons-outlined">favorite_border</span></button>
                              </c:otherwise>
                            </c:choose>
                          </div>
                          
                          <div class="product-body ai-rec-product-body">
                            <div class="brand-meta-row">
                              <a href="#" class="ai-rec-brand-link">${dto.productBrand}<span class="material-icons">chevron_right</span></a>
                              <div class="brand-wish-stat">
                                <span class="brand-wish-stat__icon"><span class="material-icons">favorite</span></span>
                                <span class="brand-wish-stat__num">${dto.productWishCount}</span>
                              </div>
                            </div>
                            
                            <h3 class="product-name">${dto.productName}</h3>

                            <div style="margin-bottom: 10px;">
                                <c:choose>
                                    <c:when test="${dto.productGender == 1}"><span style="background: #e3f2fd; color: #1976d2; padding: 3px 8px; border-radius: 4px; font-size: 11px; font-weight: bold;">남성용</span></c:when>
                                    <c:when test="${dto.productGender == 2}"><span style="background: #fce4ec; color: #c2185b; padding: 3px 8px; border-radius: 4px; font-size: 11px; font-weight: bold;">여성용</span></c:when>
                                    <c:otherwise><span style="background: #f5f5f5; color: #616161; padding: 3px 8px; border-radius: 4px; font-size: 11px; font-weight: bold;">남녀공용</span></c:otherwise>
                                </c:choose>
                            </div>

                            <c:choose>
                              <c:when test="${dto.productOriginPrice > dto.productPrice}">
                                <p class="ai-rec-origin-price"><fmt:formatNumber value="${dto.productOriginPrice}" type="number"/>원</p>
                                <div class="ai-rec-price-row">
                                  <span class="ai-rec-sale-price"><fmt:formatNumber value="${dto.productPrice}" type="number"/>원</span>
                                  <span class="ai-rec-discount-rate"><fmt:formatNumber value="${(dto.productOriginPrice - dto.productPrice) / dto.productOriginPrice * 100}" pattern="0"/>% 할인</span>
                                </div>
                              </c:when>
                              <c:otherwise>
                                <div class="product-price-row ai-rec-price-row--single">
                                  <span class="product-price"><fmt:formatNumber value="${dto.productPrice}" type="number"/>원</span>
                                </div>
                              </c:otherwise>
                            </c:choose>
                          </div>
                          
                          <div class="ai-rec-card__actions">
                            <button type="button" class="detail-bottom-btn secondary" data-open-detail-option-sheet>장바구니 담기</button>
                            <button type="button" class="detail-bottom-btn primary" data-open-detail-option-sheet>구매하기</button>
                          </div>
                        </article>
                      </li>
                    </c:if>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div style="text-align: center; padding: 60px 20px; color: #888;">
                    <span class="material-icons-outlined" style="font-size: 48px; margin-bottom:10px;">inventory_2</span>
                    <h3 style="font-size: 16px;">조건에 맞는 추천 상품이 없습니다.</h3>
                </div>
            </c:otherwise>
        </c:choose>
      </ul>
      </div>
    </main>

    <jsp:include page="../layout/bottomNav.jsp"/>
  </div>
  
  <jsp:include page="/WEB-INF/views/product/product-detail-sheet.jsp"/>
  <jsp:include page="/WEB-INF/views/poke/poke-modal.jsp"/>
  <jsp:include page="/WEB-INF/views/gift/gift-modal.jsp"/>
  <jsp:include page="/WEB-INF/views/product/share-modal.jsp"/>
  </div>

  <script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>
  <script src="${pageContext.request.contextPath}/js/product-detail.js"></script>
  <script>
    (function () {
      "use strict";
      var main = document.getElementById("aiRecMain");
      if (!main) return;

      main.addEventListener("click", function (e) {
        var wishBtn = e.target.closest(".related-wish-btn.product-grid-wish-btn");
        if (wishBtn && main.contains(wishBtn)) {
          e.preventDefault();
          e.stopPropagation();
          var on = !wishBtn.classList.contains("is-active");
          wishBtn.classList.toggle("is-active", on);
          var icon = wishBtn.querySelector("span.material-icons-outlined, span.material-icons");
          if (icon) {
            icon.className = on ? "material-icons" : "material-icons-outlined";
            icon.textContent = on ? "favorite" : "favorite_border";
          }
          return;
        }

        var card = e.target.closest(".ai-rec-card[data-detail-href]");
        if (card && !e.target.closest("button") && !e.target.closest("a.ai-rec-brand-link")) {
          // 클릭 시 data-detail-href에 적힌 경로로 정상 이동합니다!
          window.location.href = card.getAttribute("data-detail-href");
        }
      });
    })();
  </script>
</body>
</html>