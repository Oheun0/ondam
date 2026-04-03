<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    request.setAttribute("bottomNav", "group");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <title>멤버 관리</title>

  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/group.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/wallet.css">
</head>
<body data-context-path="${pageContext.request.contextPath}">
  <div class="app-shell app-shell--group app-shell--group-manage">

    <jsp:include page="../layout/header.jsp" />

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
          <h2 class="group-manage-title">내 사람을 관리할 수 있어요</h2>
          <p class="group-manage-desc">
            멤버를 확인하고 초대하거나, 도움이 필요한 <br> 내 사람의 정보를 함께 수정할 수 있어요
          </p>
        </div>

        <!-- 그룹 요약 카드 -->
        <div class="group-summary-card">
          <div class="group-summary-top">
            <div>
              <p class="group-summary-label">내 사람 그룹</p>
              <h3 class="group-summary-name">김씨네 패밀리</h3>
            </div>
            <span class="group-role-badge group-role-badge--owner">그룹장</span>
          </div>

          <div class="group-summary-meta">
            <div class="group-summary-item">
              <span class="group-summary-key">현재 인원</span>
              <strong class="group-summary-value">3명 / 4명</strong>
            </div>
            <div class="group-summary-item">
              <span class="group-summary-key">초대 가능</span>
              <strong class="group-summary-value">1명 더 초대 가능해요</strong>
            </div>
          </div>
        </div>

        <!-- 멤버 목록 -->
        <div class="group-manage-section">
          <div class="group-section-head">
            <h3 class="group-section-title">연결된 멤버</h3>
          </div>

          <div class="group-member-list">

            <!-- 나 -->
            <article class="group-member-card group-member-card--manage">
              <div class="member-thumb-wrap">
                <img src="${pageContext.request.contextPath}/images/profile/test.jpg" alt="성연수 프로필" class="member-thumb">
              </div>

              <div class="member-content">
                <div class="member-name-row">
                  <span class="member-name">성연수</span>
                  <span class="manage-badge manage-badge--me">나</span>
                  <span class="manage-badge manage-badge--owner">그룹장</span>
                </div>

				<div class="member-btn-row member-btn-row--manage">
                 <a href="#" class="member-btn member-btn--soft member-btn--solo-row">그룹명 수정하기</a>
				</div>
              </div>
            </article>

            <!-- 멤버 1 -->
            <article class="group-member-card group-member-card--manage">
              <div class="member-thumb-wrap">
                <img src="${pageContext.request.contextPath}/images/profile/default-profile.png" alt="김남준 프로필" class="member-thumb">
              </div>

              <div class="member-content">
                <div class="member-name-row">
                  <span class="member-name">김남준</span>
                  <span class="manage-badge manage-badge--help">도움 가능</span>
                </div>

                <div class="member-btn-row member-btn-row--manage">
                  <a href="#" class="member-btn member-btn--soft">그룹장 넘기기</a>
                  <a href="#" class="member-btn member-btn--soft">연결 끊기</a>
                  <a href="#" class="member-btn member-btn--soft member-btn--solo-row">회원 정보 수정 도와주기</a>
                  <a href="#" class="member-btn member-btn--soft member-btn--solo-row">도움주기 취소하기</a>
                  <!-- 회원 정보 수정 도와주기 버튼 클릭 시 해당 사람의 내 정보 수정하기 화면으로 이동(제목만 OOO님의 정보 수정하기로 변경) -->
                </div>
              </div>
            </article>

            <!-- 멤버 2 -->
            <article class="group-member-card group-member-card--manage">
              <div class="member-thumb-wrap">
                <img src="${pageContext.request.contextPath}/images/profile/default-profile.png" alt="김가빈 프로필" class="member-thumb">
              </div>

              <div class="member-content">
                <div class="member-name-row">
                  <span class="member-name">김가빈</span>
                </div>

                <div class="member-btn-row member-btn-row--manage">
                  <a href="#" class="member-btn member-btn--soft">그룹장 넘기기</a>
                  <a href="#" class="member-btn">연결 끊기</a>
                  <a href="#" class="member-btn member-btn--help member-btn--solo-row">도움주기</a>
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
		        내 사람을 1명 더 초대할 수 있어요
		      </p>
		    </div>
		
		    <div class="pending-member-actions pending-member-actions--column">
		      <a href="${pageContext.request.contextPath}/group/group-invite.jsp"
		         class="group-empty-btn group-empty-btn--primary">
		        내 사람 초대하기
		      </a>
		      <a href="#"
		         class="group-empty-btn group-empty-btn--secondary group-empty-btn--danger-outline">
		        그룹 해산하기
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