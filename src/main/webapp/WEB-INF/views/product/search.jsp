<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>상품 검색</title>
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-search.css">
</head>
<body class="product-search-page" data-context-path="${pageContext.request.contextPath}">
  <div class="search-shell">
    <div class="search-page-inner">
      <jsp:include page="/WEB-INF/views/layout/back-searchBar.jsp"/>

      <main class="search-page-main" id="searchPageMain">

        <%-- 최근 검색어 --%>
        <section class="recent-search-section" id="recentSearchSection" aria-labelledby="recentSearchTitle">
          <div class="search-section-head">
            <h2 class="search-section-title" id="recentSearchTitle">최근 검색어</h2>
            <c:if test="${not empty recentSearchList}">
              <button type="button" class="search-edit-btn" id="searchEditBtn" aria-pressed="false">편집</button>
            </c:if>
          </div>
          <c:choose>
            <c:when test="${empty recentSearchList}">
              <p class="search-empty-msg" role="status">최근 검색어가 없어요</p>
            </c:when>
            <c:otherwise>
              <div class="recent-search-list" id="recentSearchList" role="list" aria-label="최근 검색어 목록">
				  <c:forEach var="item" items="${recentSearchList}">
				    <div class="recent-search-chip" role="listitem">
				      <button type="button" class="recent-search-chip-btn" data-keyword="${item.searchKeyword}">
				        <span class="recent-search-chip-text">${item.searchKeyword}</span>
				        <span class="recent-search-chip-x" aria-hidden="true">&times;</span>
				      </button>
				    </div>
				  </c:forEach>
				</div>
            </c:otherwise>
          </c:choose>
        </section>

        <%-- 인기 검색어 --%>
        <section class="popular-search-section" aria-labelledby="popularSearchTitle">
          <h2 class="search-section-title popular-search-heading" id="popularSearchTitle">인기 검색어</h2>
          <c:choose>
            <c:when test="${empty popularSearchList}">
              <p class="search-empty-msg" role="status">인기 검색어가 없어요</p>
            </c:when>
            <c:otherwise>
              <ul class="popular-search-list" id="popularSearchList" role="list">
                <c:forEach var="item" items="${popularSearchList}" varStatus="status">
                  <li role="listitem">
                    <button type="button" class="popular-search-item"
                            data-keyword="${item.searchKeyword}">
                      <span class="popular-rank" aria-hidden="true">${status.index + 1}</span>
                      <span class="popular-keyword">${item.searchKeyword}</span>
                    </button>
                  </li>
                </c:forEach>
              </ul>
            </c:otherwise>
          </c:choose>
        </section>

      </main>
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/js/product-search.js"></script>
</body>
</html>