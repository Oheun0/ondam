<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  상품 검색 상단바: 뒤로가기 + 검색 입력 + 검색 버튼
  스타일: css/product-search.css (.search-page-header 등)
  초기값: jsp:include 시 <jsp:param name="searchQuery" value="..."/> (없으면 빈칸)
--%>
<header class="search-page-header" role="banner">
  <button type="button" class="search-back-btn search-header-icon-btn" id="searchBackBtn" aria-label="뒤로가기">
    <span class="material-icons" aria-hidden="true">arrow_back_ios_new</span>
  </button>

  <form class="search-form" id="productSearchForm" action="${pageContext.request.contextPath}/preview" method="get" autocomplete="off">
    <input type="hidden" name="page" value="product/search-result"/>
    <div class="search-input-wrap">
      <label class="sr-only" for="searchQueryInput">상품명 검색</label>
      <input type="search"
             class="search-input"
             id="searchQueryInput"
             name="q"
             placeholder="찾고 싶은 상품을 검색해보세요"
             enterkeyhint="search"
             maxlength="80"
             value="<c:out value='${param.searchQuery}'/>"/>
      <button type="submit" class="search-submit-btn search-header-icon-btn" aria-label="검색 실행">
        <span class="material-icons" aria-hidden="true">search</span>
      </button>
    </div>
  </form>
</header>
