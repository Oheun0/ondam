<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<body data-context-path="${pageContext.request.contextPath}">
  <div class="search-shell">
    <div class="search-page-inner">
      <header class="search-page-header" role="banner">
        <button type="button" class="search-back-btn search-header-icon-btn" id="searchBackBtn" aria-label="뒤로가기">
          <span class="material-icons" aria-hidden="true">arrow_back_ios_new</span>
        </button>

        <form class="search-form" id="productSearchForm" action="#" method="get" autocomplete="off">
          <div class="search-input-wrap">
            <label class="sr-only" for="searchQueryInput">상품명 검색</label>
            <input type="search"
                   class="search-input"
                   id="searchQueryInput"
                   name="q"
                   placeholder="찾고 싶은 상품을 검색해보세요"
                   enterkeyhint="search"
                   maxlength="80" />
            <button type="submit" class="search-submit-btn search-header-icon-btn" aria-label="검색 실행">
              <span class="material-icons" aria-hidden="true">search</span>
            </button>
          </div>
        </form>
      </header>

      <main class="search-page-main" id="searchPageMain">
        <section class="recent-search-section" id="recentSearchSection" aria-labelledby="recentSearchTitle">
          <div class="search-section-head">
            <h2 class="search-section-title" id="recentSearchTitle">최근 검색어</h2>
            <button type="button" class="search-edit-btn" id="searchEditBtn" aria-pressed="false">
              편집
            </button>
          </div>
          <div class="recent-search-list" id="recentSearchList" role="list" aria-label="최근 검색어 목록"></div>
        </section>

        <%--
        최근 검색어 없음 UI (필요 시 아래 블록 주석 해제 후, 위 recent-search-section 은 숨기거나 JS에서 분기)
        <section class="recent-search-section recent-search-section--empty" aria-labelledby="recentSearchTitleEmpty">
          <div class="search-section-head">
            <h2 class="search-section-title" id="recentSearchTitleEmpty">최근 검색어</h2>
          </div>
          <p class="search-empty-msg" role="status">최근 검색어가 없어요</p>
        </section>
        --%>

        <section class="popular-search-section" aria-labelledby="popularSearchTitle">
          <h2 class="search-section-title popular-search-heading" id="popularSearchTitle">인기 검색어</h2>
          <ul class="popular-search-list" id="popularSearchList" role="list">
            <li role="listitem">
              <button type="button" class="popular-search-item" data-keyword="바람막이">
                <span class="popular-rank" aria-hidden="true">1</span>
                <span class="popular-keyword">바람막이</span>
              </button>
            </li>
            <li role="listitem">
              <button type="button" class="popular-search-item" data-keyword="자켓">
                <span class="popular-rank" aria-hidden="true">2</span>
                <span class="popular-keyword">자켓</span>
              </button>
            </li>
            <li role="listitem">
              <button type="button" class="popular-search-item" data-keyword="니트">
                <span class="popular-rank" aria-hidden="true">3</span>
                <span class="popular-keyword">니트</span>
              </button>
            </li>
            <li role="listitem">
              <button type="button" class="popular-search-item" data-keyword="청바지">
                <span class="popular-rank" aria-hidden="true">4</span>
                <span class="popular-keyword">청바지</span>
              </button>
            </li>
            <li role="listitem">
              <button type="button" class="popular-search-item" data-keyword="가디건">
                <span class="popular-rank" aria-hidden="true">5</span>
                <span class="popular-keyword">가디건</span>
              </button>
            </li>
            <li role="listitem">
              <button type="button" class="popular-search-item" data-keyword="셔츠">
                <span class="popular-rank" aria-hidden="true">6</span>
                <span class="popular-keyword">셔츠</span>
              </button>
            </li>
            <li role="listitem">
              <button type="button" class="popular-search-item" data-keyword="조끼">
                <span class="popular-rank" aria-hidden="true">7</span>
                <span class="popular-keyword">조끼</span>
              </button>
            </li>
            <li role="listitem">
              <button type="button" class="popular-search-item" data-keyword="슬랙스">
                <span class="popular-rank" aria-hidden="true">8</span>
                <span class="popular-keyword">슬랙스</span>
              </button>
            </li>
            <li role="listitem">
              <button type="button" class="popular-search-item" data-keyword="원피스">
                <span class="popular-rank" aria-hidden="true">9</span>
                <span class="popular-keyword">원피스</span>
              </button>
            </li>
            <li role="listitem">
              <button type="button" class="popular-search-item" data-keyword="운동화">
                <span class="popular-rank" aria-hidden="true">10</span>
                <span class="popular-keyword">운동화</span>
              </button>
            </li>
          </ul>
        </section>
      </main>
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/js/product-search.js"></script>
</body>
</html>
