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

      <!-- [수정] 헤더 레이아웃 재정렬 (버튼 우측 상단 고정, 텍스트 중앙 정렬) -->
      <header class="ai-rec-page-head" style="position: relative; text-align: center; padding-top: 10px;">
        <!-- 우측 상단 새로고침 버튼 -->
        <button type="button" onclick="window.location.reload();" style="position: absolute; top: 0; right: 0; background: none; border: none; cursor: pointer; color: #555; display: flex; align-items: center; padding: 4px;">
            <span class="material-icons">refresh</span>
            <span style="font-size: 13px; margin-left: 2px;">새로고침</span>
        </button>
        
        <!-- 중앙 타이틀 및 부제목 -->
        <h1 class="ai-rec-page-head__title" style="margin-bottom: 8px; justify-content: center; display: flex; align-items: center;">
            ✨ <span style="color: #ff6b6b; margin: 0 4px;">${userName}</span>님만을 위한 추천
        </h1>
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
                        <article class="ai-rec-card product-card" data-detail-href="${pageContext.request.contextPath}/product?action=detail&productNo=${dto.productNo}" data-product-no="${dto.productNo}">
                          
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
                                <span class="brand-wish-stat__num wish-count-num">${dto.productWishCount}</span>
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
      var contextPath = document.body.getAttribute('data-context-path'); // Context Path 가져오기

      if (!main) return;

      main.addEventListener("click", function (e) {
        var wishBtn = e.target.closest(".related-wish-btn.product-grid-wish-btn");
        
        // 1. 하트 버튼 클릭 시 처리 (AJAX 적용)
        if (wishBtn && main.contains(wishBtn)) {
          e.preventDefault();
          e.stopPropagation();
          
          var card = wishBtn.closest(".ai-rec-card");
          var productNo = card.getAttribute("data-product-no");
          var countSpan = card.querySelector(".wish-count-num"); // 숫자 표시 영역
          var icon = wishBtn.querySelector("span.material-icons-outlined, span.material-icons");

          // 서버로 찜 토글 요청 보내기
          fetch(contextPath + "/wish?action=toggle&productNo=" + productNo)
            .then(response => {
                if(!response.ok) throw new Error("Network response was not ok");
                return response.json(); // 서버에서 준 JSON 파싱
            })
            .then(data => {
                // 서버 응답(wished: true/false)에 따라 UI 업데이트
                var isWished = data.wished;
                
                wishBtn.classList.toggle("is-active", isWished);
                if (icon) {
                    icon.className = isWished ? "material-icons" : "material-icons-outlined";
                    icon.textContent = isWished ? "favorite" : "favorite_border";
                }
                
                // 숫자 증감 처리
                if(countSpan) {
                    var currentCount = parseInt(countSpan.textContent.replace(/,/g, '') || "0");
                    countSpan.textContent = isWished ? (currentCount + 1) : (currentCount > 0 ? currentCount - 1 : 0);
                }
            })
            .catch(error => {
                console.error("Wish toggle error:", error);
                alert("찜 처리 중 오류가 발생했습니다. 로그인을 확인해주세요.");
            });
            
          return; // 여기서 종료 (상세페이지 이동 방지)
        }

        // 2. 카드 클릭 시 상세 페이지 이동 처리
        var card = e.target.closest(".ai-rec-card[data-detail-href]");
        if (card && !e.target.closest("button") && !e.target.closest("a.ai-rec-brand-link")) {
          window.location.href = card.getAttribute("data-detail-href");
        }
      });
    })();
  </script>
</body>
</html>