<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <title>상품 목록</title>
  <!-- 카테고리를 통해 상품 목록에 들어갔을 때 -->

  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,1,0" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/category.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product.css">
</head>
<body class="product-list-page" data-context-path="${pageContext.request.contextPath}">
  <div class="product-shell">
    <div class="product-page-inner product-page-inner--sticky-header">
      <jsp:include page="/WEB-INF/views/product/product-header.jsp" />
      <jsp:include page="/WEB-INF/views/product/product-filter.jsp" />
      <jsp:include page="/WEB-INF/views/product/product-grid.jsp" />
    </div>

    <jsp:include page="../layout/bottomNav.jsp" />

  <script src="${pageContext.request.contextPath}/js/product-list.js"></script>
</body>
</html>