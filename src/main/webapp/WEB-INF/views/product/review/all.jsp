<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>상품 후기</title>

  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-detail.css">
</head>
<body data-context-path="${pageContext.request.contextPath}">
  <div class="detail-shell">
    <div class="detail-page-inner">
      <jsp:include page="/WEB-INF/views/layout/back-header.jsp"/>

      <main class="detail-content">
        <div class="detail-tab-panels-section">
          <section class="detail-tab-panel-card" aria-label="상품 후기 전체">
            <jsp:include page="/WEB-INF/views/product/review/review-list-panel.jsp">
              <jsp:param name="showMoreButton" value="false"/>
            </jsp:include>
          </section>
        </div>
      </main>
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/js/product-detail.js"></script>
</body>
</html>
