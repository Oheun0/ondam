<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
          <a href="${pageContext.request.contextPath}/group" class="back-btn">
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
              <h3 class="group-summary-name">${myGroup.familyName}</h3>
            </div>
            <span class="group-role-badge group-role-badge--owner">그룹장</span>
          </div>

          <div class="group-summary-meta">
            <div class="group-summary-item">
              <span class="group-summary-key">현재 인원</span>
              <strong class="group-summary-value">${memberList.size()}명 / 4명</strong>
            </div>
            <div class="group-summary-item">
              <span class="group-summary-key">초대 가능</span>
              <c:choose>
                <c:when test="${memberList.size() >= 4}">
                  <strong class="group-summary-value">더 이상 초대할 수 없어요</strong>
                </c:when>
                <c:otherwise>
                  <strong class="group-summary-value">${4 - memberList.size()}명 더 초대 가능해요</strong>
                </c:otherwise>
              </c:choose>
            </div>
          </div>
        </div>

        <!-- 멤버 목록 -->
		<div class="group-manage-section">
		  <div class="group-section-head">
		    <h3 class="group-section-title">연결된 멤버</h3>
		  </div>
		
		  <div class="group-member-list">
		    <c:forEach var="m" items="${memberList}">
		
		      <%-- 이 멤버에 대해 내가 이미 도움 주고 있는지 확인 --%>
		      <c:set var="isHelping" value="${helpeeSet.contains(m.userNo)}" />
		
		      <article class="group-member-card group-member-card--manage"
		               data-helpee-userno="${m.userNo}"
		               data-family-no="${myGroup.familyNo}">
		        <div class="member-thumb-wrap">
		          <img src="${pageContext.request.contextPath}/images/profile/${not empty m.userProfileImg ? m.userProfileImg : 'default-profile.png'}"
				     alt="${m.userName} 프로필" class="member-thumb"
				     onerror="this.src='${pageContext.request.contextPath}/images/profile/default-profile.png'">
		        </div>
		
		        <div class="member-content">
		          <div class="member-name-row">
		            <span class="member-name">${m.userName}</span>
		
		            <c:if test="${m.userNo == myMember.userNo}">
		              <span class="manage-badge manage-badge--me">나</span>
		            </c:if>
		            <c:if test="${m.familyAuth == 1}">
		              <span class="manage-badge manage-badge--owner">그룹장</span>
		            </c:if>
		
		            <%-- 도움 가능 배지: DB에 이미 있으면 바로 표시 --%>
		            <c:if test="${m.userNo != myMember.userNo}">
		              <span class="manage-badge manage-badge--help help-badge"
		                    style="${isHelping ? '' : 'display:none;'}">도움 가능</span>
		            </c:if>
		          </div>
		
		          <c:if test="${m.userNo != myMember.userNo}">
		            <div class="member-btn-row member-btn-row--manage">
		              <a href="${pageContext.request.contextPath}/group?action=changeOwner&familyMemberNo=${m.familyMemberNo}"
		                 class="member-btn member-btn--soft">그룹장 넘기기</a>
		              <a href="${pageContext.request.contextPath}/group?action=memberDelete&familyMemberNo=${m.familyMemberNo}"
		                 class="member-btn member-btn--soft">연결 끊기</a>
		
		              <%-- 도움 주기 버튼: DB 상태에 따라 초기 렌더링 분기 --%>
		              <c:choose>
		                <c:when test="${isHelping}">
		                  <%-- 이미 돕고 있는 상태 --%>
		                  <a href="${pageContext.request.contextPath}/profile-address?targetUserNo=${m.userNo}"
		                     class="member-btn member-btn--soft member-btn--solo-row help-toggle-btn">
		                     배송지 수정 도와주기</a>
		                  <a href="#"
		                     class="member-btn member-btn--soft member-btn--solo-row help-cancel-btn"
		                     onclick="cancelHelp(this.closest('.group-member-card')); return false;">도움 주기 취소</a>
		                </c:when>
		                <c:otherwise>
		                  <%-- 아직 안 돕고 있는 상태 --%>
		                  <a href="#"
		                     class="member-btn member-btn--help member-btn--solo-row help-toggle-btn"
		                     onclick="toggleHelp(this); return false;">도움 주기</a>
		                </c:otherwise>
		              </c:choose>
		            </div>
		          </c:if>
		        </div>
		      </article>
		    </c:forEach>
		  </div>
		</div>

        <!-- 연결 관리 -->
		<div class="group-manage-section">
		  <div class="group-section-head">
		    <h3 class="group-section-title">연결 관리</h3>
		  </div>
		
		  <div class="pending-member-card">
		    <div class="pending-member-info">
		      <strong class="pending-member-name">현재 ${memberList.size()}명 / 4명</strong>
		      <c:choose>
                <c:when test="${memberList.size() >= 4}">
                  <p class="pending-member-desc">더 이상 초대할 수 없어요!</p>
                </c:when>
                <c:otherwise>
                  <p class="pending-member-desc">내 사람을 ${4 - memberList.size()}명 더 초대할 수 있어요</p>
                </c:otherwise>
              </c:choose>
		    </div>
		
		    <div class="pending-member-actions pending-member-actions--column">
			  <c:choose>
			    <c:when test="${memberList.size() >= 4}">
			        <a class="group-empty-btn group-empty-btn--primary"
			           style="opacity:0.5; cursor:not-allowed; pointer-events:none;">
			            내 사람 초대하기
			        </a>
			    </c:when>
			    <c:otherwise>
			        <a href="${pageContext.request.contextPath}/group?action=invite"
			           class="group-empty-btn group-empty-btn--primary">
			            내 사람 초대하기
			        </a>
			    </c:otherwise>
			</c:choose>
		      	<button type="button" id="btnDissolve"
				        class="group-empty-btn group-empty-btn--secondary group-empty-btn--danger-outline">
				  그룹 해산하기
				</button>
		    </div>
		  </div>
		</div>
		
		</section>
    
    <jsp:include page="../layout/bottomNav.jsp" />
    <jsp:include page="/WEB-INF/views/group/group-modal.jsp"/>
  </div>
	<script>
	const contextPath = document.body.dataset.contextPath;
	
	function toggleHelp(btn) {
	    const card = btn.closest('.group-member-card');
	    const helpeeUserNo = card.dataset.helpeeUserno;
	    const familyNo = card.dataset.familyNo;
	
	    fetch(contextPath + '/group?action=helpAdd', {
	        method: 'POST',
	        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
	        body: 'helpeeUserNo=' + helpeeUserNo + '&familyNo=' + familyNo
	    }).then(r => {
	        if (r.ok) applyHelpState(card);
	    });
	}
	
	function cancelHelp(card) {
	    const helpeeUserNo = card.dataset.helpeeUserno;
	    const familyNo = card.dataset.familyNo;
	
	    fetch(contextPath + '/group?action=helpCancel', {
	        method: 'POST',
	        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
	        body: 'helpeeUserNo=' + helpeeUserNo + '&familyNo=' + familyNo
	    }).then(r => {
	        if (r.ok) applyCancelState(card);
	    });
	}
	
	function applyHelpState(card) {
		  const badge = card.querySelector('.help-badge');
		  const btnRow = card.querySelector('.member-btn-row--manage');
		  const helpBtn = card.querySelector('.help-toggle-btn');
		  const helpeeUserNo = card.dataset.helpeeUserno;
		
		  helpBtn.textContent = '배송지 수정 도와주기';
		  helpBtn.classList.remove('member-btn--help');
		  helpBtn.classList.add('member-btn--soft');
		  helpBtn.href = contextPath + '/profile-address?targetUserNo=' + helpeeUserNo;
		  helpBtn.onclick = null;
		
		  if (badge) badge.style.display = 'inline-flex';
		
		  if (!card.querySelector('.help-cancel-btn')) {
		    const cancelBtn = document.createElement('a');
		    cancelBtn.href = '#';
		    cancelBtn.className = 'member-btn member-btn--soft member-btn--solo-row help-cancel-btn';
		    cancelBtn.textContent = '도움 주기 취소';
		    cancelBtn.addEventListener('click', e => { e.preventDefault(); cancelHelp(card); });
		    btnRow.appendChild(cancelBtn);
		  }
		}
		
		function applyCancelState(card) {
		  const badge = card.querySelector('.help-badge');
		  const cancelBtn = card.querySelector('.help-cancel-btn');
		  const helpBtn = card.querySelector('.help-toggle-btn');
		
		  if (badge) badge.style.display = 'none';
		  if (cancelBtn) cancelBtn.remove();
		
		  helpBtn.textContent = '도움 주기';
		  helpBtn.classList.remove('member-btn--soft');
		  helpBtn.classList.add('member-btn--help');
		  helpBtn.href = '#';
		  helpBtn.onclick = function() { toggleHelp(this); return false; };
		}
	
	const dissolveModal = document.getElementById('groupModalDissolve');

	// 모달 열기
	document.getElementById('btnDissolve').addEventListener('click', function () {
	    dissolveModal.classList.remove('hidden');
	});

	// 딤 + 계속 사용하기 → 닫기
	dissolveModal.querySelectorAll('[data-group-modal-dismiss]').forEach(function (el) {
	    el.addEventListener('click', function () {
	        dissolveModal.classList.add('hidden');
	    });
	});

	// 해산하기 확인 → delete 액션
	dissolveModal.querySelector('.group-modal-btn--danger').addEventListener('click', function () {
	    window.location.href = contextPath + '/group?action=delete&familyNo=${myGroup.familyNo}';
	});
</script>
	<script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>
</body>
</html>