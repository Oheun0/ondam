<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="search-card">
    <form action="${pageContext.request.contextPath}/search" method="get" class="search-box">
        <span class="material-icons search-icon">search</span>
        <label for="mainSearch" class="sr-only">검색어 입력</label>
        <input
            type="text"
            id="mainSearch"
            name="keyword"
            class="search-input"
            placeholder="찾고 싶은 상품을 검색해보세요."
        >
        <button type="submit" class="search-btn" aria-label="검색하기">
            <span class="material-icons">arrow_forward_ios</span>
        </button>
    </form>
</div>