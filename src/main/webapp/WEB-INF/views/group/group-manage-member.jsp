<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    request.setAttribute("bottomNav", "group");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>멤버 보기</title>

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/group.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/wallet.css">
   <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet"/>
</head>
<body data-context-path="${pageContext.request.contextPath}">
  <div class="app-shell app-shell--group app-shell--group-manage">
    
    <div class="top-header-cluster">
      <jsp:include page="../layout/header.jsp" />
    </div>

    <main class="main-content group-main">
      <section class="group-page group-manage-page">
        <div class="wallet-top">
          <a href="${pageContext.request.contextPath}/group/group.jsp" class="back-btn">
            <span class="material-icons">arrow_back_ios</span>
            <span>뒤로가기</span>
          </a>
        </div>

        <!-- 상단 안내 -->
        <div class="group-manage-head">
          <h2 class="group-manage-title">연결된 내 사람을 볼 수 있어요</h2>
          <p class="group-manage-desc">
            멤버를 확인하고, 도움이 필요한 <br> 내 사람의 배송지를 함께 수정할 수 있어요
          </p>
        </div>

        <!-- 그룹 요약 카드 -->
        <div class="group-summary-card">
          <div class="group-summary-top">
            <div>
              <p class="group-summary-label">내 사람 그룹</p>
              <h3 class="group-summary-name">우리 내 사람</h3>
            </div>
            <span class="group-role-badge">멤버</span>
          </div>

          <div class="group-summary-meta">
            <div class="group-summary-item">
              <span class="group-summary-key">현재 인원</span>
              <strong class="group-summary-value">3명 / 4명</strong>
            </div>
            <div class="group-summary-item">
              <span class="group-summary-key">그룹장</span>
              <strong class="group-summary-value">김지현님</strong>
            </div>
          </div>
        </div>

        <!-- 멤버 목록 -->
        <div class="group-manage-section">
          <div class="group-section-head">
            <h3 class="group-section-title">연결된 멤버</h3>
          </div>

          <div class="group-member-list">

            <!-- 그룹장 -->
            <article class="group-member-card group-member-card--manage">
              <div class="member-thumb-wrap">
                <img src="${pageContext.request.contextPath}/images/profile/test.jpg" alt="성연수 프로필" class="member-thumb">
              </div>

              <div class="member-content">
                <div class="member-name-row">
                  <span class="member-name">성연수</span>
                  <span class="manage-badge manage-badge--owner">그룹장</span>
                </div>


                <div class="member-btn-row member-btn-row--manage">
                  <a href="#" class="member-btn member-btn--help member-btn--solo-row">도움주기</a>
                </div>
              </div>
            </article>

            <!-- 나 -->
            <article class="group-member-card group-member-card--manage">
              <div class="member-thumb-wrap">
                <img src="${pageContext.request.contextPath}/images/profile/default-profile.png" alt="김남준 프로필" class="member-thumb">
              </div>

              <div class="member-content">
                <div class="member-name-row">
                  <span class="member-name">김남준</span>
                  <span class="manage-badge manage-badge--me">나</span>
                </div>

              </div>
            </article>

            <!-- 도움 대상 -->
            <article class="group-member-card group-member-card--manage">
              <div class="member-thumb-wrap">
                <img src="${pageContext.request.contextPath}/images/profile/default-profile.png" alt="김가빈 프로필" class="member-thumb">
              </div>

              <div class="member-content">
                <div class="member-name-row">
                  <span class="member-name">김가빈</span>
                  <span class="manage-badge manage-badge--help">도움 가능</span>
                </div>


                <div class="member-btn-row member-btn-row--manage">
                  <a href="#" class="member-btn member-btn--soft member-btn--solo-row">회원 정보 수정 도와주기</a>
                </div>
              </div>
            </article>
          </div>
        </div>
        
		<!-- 연결 관리 -->
		<div class="group-manage-section">
		  <div class="group-section-head">
		    <h3 class="group-section-title">연결 관리</h3>
		  </div>
		
		  <div class="pending-member-card">
		    <div class="pending-member-info">
		      <strong class="pending-member-name">현재 3명 / 4명</strong>
		      <p class="pending-member-desc">
		        이 그룹에 연결되어 있어요
		      </p>
		    </div>
		
		    <div class="pending-member-actions pending-member-actions--column">
		      <a href="#"
		         class="group-empty-btn group-empty-btn--secondary group-empty-btn--danger-outline">
		        그룹 나가기
		      </a>
		    </div>
		  </div>
		</div>
      </section>

    <jsp:include page="../layout/bottomNav.jsp" />
  </div>
  <script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>
</body>
</html>