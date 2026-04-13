<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  String contentPage = (String) request.getAttribute("sellerContentPage");
  if (contentPage == null) contentPage = "/WEB-INF/views/seller/dashboard-content.jsp";
%>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>온담 파트너</title>

  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Material+Icons+Outlined&display=swap" rel="stylesheet">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-layout.css" />
  <%
    String extraCss = (String) request.getAttribute("sellerExtraCss");
    if (extraCss != null && !extraCss.trim().isEmpty()) {
  %>
  <link rel="stylesheet" href="${pageContext.request.contextPath}<%= extraCss %>" />
  <%
    }
  %>
</head>
<body class="seller-app" data-context-path="${pageContext.request.contextPath}">
  <div class="seller-layout">
    <jsp:include page="/WEB-INF/views/seller/layout/seller-sidebar.jsp" />

    <div class="seller-main-area">
      <jsp:include page="/WEB-INF/views/seller/layout/seller-header.jsp" />

      <main class="seller-content" id="sellerContent" aria-label="판매자 메인 콘텐츠">
        <jsp:include page="<%= contentPage %>" />
      </main>

      <jsp:include page="/WEB-INF/views/seller/layout/seller-footer.jsp" />
    </div>
  </div>

  <%
    String extraJs = (String) request.getAttribute("sellerExtraJs");
    if (extraJs != null && !extraJs.trim().isEmpty()) {
  %>
  <script src="${pageContext.request.contextPath}<%= extraJs %>"></script>
  <%
    }
  %>
</body>
</html>

