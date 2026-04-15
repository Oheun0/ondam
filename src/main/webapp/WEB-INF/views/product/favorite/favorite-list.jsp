<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>찜한 상품</title>
  <!-- 찜한 목록들, 찜 해제하면 새로고침하기 전까지 유지(바로삭제x) 새로고침하면 그리드에서 없어짐
  찜 실수로 해제했을 때 다시 바로 추가할 수 있도록 하기 위함 -->

  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-detail.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/favorite-list.css">
</head>
<body class="favorite-list-page" 
      data-context-path="${pageContext.request.contextPath}"
      data-helper-mode="${isHelperMode}">
  <%
    // product-grid.jsp 빈 상태 문구를 찜 전용으로 교체하기 위한 플래그
    request.setAttribute("productEmptyVariant", "favorite");
  %>
  <div class="detail-shell">
    <div class="detail-page-inner favorite-list-inner" id="favoriteListPageRoot">
      <div class="favorite-list-sticky-head">
        <div class="favorite-list-header-wrap">
          <jsp:include page="/WEB-INF/views/layout/back-header.jsp"/>
          <h1 class="favorite-list-title">찜한 상품</h1>
          <div class="favorite-list-header-actions">
            <button type="button" class="detail-icon-btn cart-icon-wrap" aria-label="장바구니" 
			        onclick="location.href='${pageContext.request.contextPath}/cart'">
			  <span class="material-icons-outlined" aria-hidden="true">shopping_cart</span>
			  <c:if test="${sessionScope.cartCount > 0}">
			    <span class="cart-badge">${sessionScope.cartCount}</span>
			  </c:if>
			</button>
          </div>
        </div>
      </div>

      <div class="favorite-list-scroll">
        <section class="favorite-filter-section" aria-label="정렬 및 종류 필터">
          <div class="favorite-filter-row">
            <div class="filter-dropdown-wrap">
              <button type="button" class="filter-dropdown-btn" id="favoriteSortToggleBtn" aria-haspopup="listbox" aria-expanded="false">
				 <span id="favoriteSortSelectedText">${empty currentSort ? '담은순' : currentSort}</span>
				 <span class="material-icons" aria-hidden="true">expand_more</span>
			  </button>
              <div class="filter-dropdown-menu hidden" id="favoriteSortDropdown" role="listbox" aria-labelledby="favoriteSortToggleBtn">
				  <button type="button" class="filter-option ${empty currentSort || currentSort == '담은순' ? 'active' : ''}" data-sort="담은순" role="option">담은순</button>
				  <button type="button" class="filter-option ${currentSort == '인기순' ? 'active' : ''}" data-sort="인기순" role="option">인기순</button>
				  <button type="button" class="filter-option ${currentSort == '최신순' ? 'active' : ''}" data-sort="최신순" role="option">최신순</button>
				  <button type="button" class="filter-option ${currentSort == '가격 낮은순' ? 'active' : ''}" data-sort="가격 낮은순" role="option">가격 낮은순</button>
				  <button type="button" class="filter-option ${currentSort == '가격 높은순' ? 'active' : ''}" data-sort="가격 높은순" role="option">가격 높은순</button>
				</div>
            </div>

            <div class="favorite-part-chips" role="group" aria-label="옷 종류">
              	<button type="button" class="favorite-part-chip ${currentPart == '윗옷' ? 'active' : ''}" data-part="윗옷">윗옷</button>
				<button type="button" class="favorite-part-chip ${currentPart == '아랫옷' ? 'active' : ''}" data-part="아랫옷">아랫옷</button>
				<button type="button" class="favorite-part-chip ${currentPart == '겉옷' ? 'active' : ''}" data-part="겉옷">겉옷</button>
				<button type="button" class="favorite-part-chip ${currentPart == '한 벌 옷' ? 'active' : ''}" data-part="한 벌 옷">한 벌 옷</button>
            </div>
          </div>
        </section>

        <jsp:include page="/WEB-INF/views/product/product-grid.jsp"/>
      </div>
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/js/favorite-list.js"></script>
</body>
</html>
