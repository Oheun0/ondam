<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>검색: <c:out value="${searchQuery}"/></title>

  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,1,0" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/category.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-search.css">
</head>
<body class="product-list-page search-result-page" data-context-path="${pageContext.request.contextPath}" data-login-user="${not empty sessionScope.loginUser ? 'true' : ''}">
  <div class="product-shell">
    <div class="product-page-inner product-page-inner--sticky-header search-result-inner">
      <jsp:include page="/WEB-INF/views/layout/back-searchBar.jsp">
        <jsp:param name="searchQuery" value="${searchQuery}"/>
      </jsp:include>

      <jsp:include page="/WEB-INF/views/product/product-filter.jsp"/>
      <jsp:include page="/WEB-INF/views/product/product-grid.jsp"/>
    </div>

    <jsp:include page="/WEB-INF/views/layout/bottomNav.jsp"/>
  </div>

  <script>const CONTEXT_PATH = '${pageContext.request.contextPath}';</script>
  <script src="${pageContext.request.contextPath}/js/product-list.js"></script>
  <script src="${pageContext.request.contextPath}/js/product-search.js"></script>
</body>
</html>