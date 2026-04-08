<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  String contentPage = (String) request.getAttribute("sellerContentPage");
  if (contentPage == null) contentPage = "/WEB-INF/views/seller/dashboard-content.jsp";

  String sellerName = (String) request.getAttribute("sellerName");
  if (sellerName == null) sellerName = "온담스토어";
%>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>온담 판매자센터</title>

  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Material+Icons+Outlined&display=swap" rel="stylesheet">

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
<body class="seller-app">
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

  <script>
    // 레이아웃 공통(더미) 동작: 헤더 버튼들
    (function () {
      var notifyBtn = document.getElementById('sellerHeaderNotifyBtn');
      var logoutBtn = document.getElementById('sellerHeaderLogoutBtn');
      if (notifyBtn) {
        notifyBtn.addEventListener('click', function () {
          alert('알림 기능은 아직 준비 중이에요.');
        });
      }
      if (logoutBtn) {
        logoutBtn.addEventListener('click', function () {
          alert('로그아웃은 아직 연동되지 않았어요. (더미)');
        });
      }
    })();
  </script>

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

