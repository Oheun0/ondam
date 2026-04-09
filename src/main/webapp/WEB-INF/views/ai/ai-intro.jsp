<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko" class="ai-rec-page">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <title>AI 맞춤 추천 | 온담</title>

  <!-- [해결!] 아이콘과 폰트를 불러오는 핵심 CSS 추가 -->
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,1,0" rel="stylesheet">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/inquiry-write.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ai-recommend-modal.css">
</head>
<body class="ai-rec-page" data-context-path="${pageContext.request.contextPath}">

  <div class="detail-shell">
    <!-- 담이 인사 & 로딩 모달 포함 -->
    <jsp:include page="ai-recommend-modal.jsp"/>

    <div class="app-shell">
      <div class="top-header-cluster">
        <jsp:include page="../layout/header.jsp"/>
      </div>
      
      <!-- 뒷배경 화면 (결과가 나오기 전이므로 비워둡니다) -->
      <main class="page-wrap ai-rec-main" style="background-color: #f9f9f9; min-height: 100vh;"></main>

      <jsp:include page="../layout/bottomNav.jsp"/>
    </div>
  </div>

  <!-- [해결!] 하단 바 기능(JS) 연결 추가 -->
  <script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>

  <script>
    document.addEventListener("DOMContentLoaded", function () {
      // 모달이 떠 있을 때 뒤쪽 화면이 스크롤되지 않게 고정
      document.body.style.overflow = "hidden";

      var introBtn = document.getElementById("aiRecModalIntroBtn");
      
      if(introBtn) {
          introBtn.addEventListener("click", function () {
            // 1. 버튼 클릭 시 인사 모달 숨기고 로딩 모달 띄우기
            document.getElementById("aiRecModalIntro").classList.add("hidden");
            document.getElementById("aiRecModalLoading").classList.remove("hidden");
            
            // 2. [수정됨] href 대신 replace를 사용해서 방문 기록에 intro를 남기지 않음!
            window.location.replace("${pageContext.request.contextPath}/ai-recommend"); 
          });
      }
    });
  </script>
</body>
</html>