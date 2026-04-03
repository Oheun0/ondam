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
    <title>내 사람 초대하기</title>

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/wallet.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/group.css">
</head>

<body data-context-path="${pageContext.request.contextPath}">
<div class="app-shell app-shell--group">

    <jsp:include page="../layout/header.jsp" />

    <main class="main-content group-main">
        <div class="group-page">

            <div class="wallet-top">
                <a href="${pageContext.request.contextPath}/group" class="back-btn">
                    <span class="material-icons">arrow_back_ios</span>
                    <span>뒤로가기</span>
                </a>
            </div>

            <section class="group-invite-head">
                <h1 class="group-invite-title">내 사람 초대하기</h1>
                <p class="group-invite-desc">가족, 친구, 보호자를 초대할 수 있어요</p>
            </section>
			
			<!-- 그룹명 설정 -->
				<c:if test="${isNameDefault}">
					<section class="group-invite-card">
						<p class="group-invite-label">내 사람 그룹명 설정</p>
						<div class="group-invite-code-row">
							<div class="group-invite-code-box" style="flex: 1;">
								<input type="text" id="groupNameInput" class="group-invite-code"
									maxlength="20"
									style="background: transparent; border: none; outline: none; width: 100%; font: inherit;">
							</div>
							<button type="button" class="group-copy-btn"
								onclick="updateGroupName()">확인</button>
						</div>
						<p class="group-invite-guide" id="groupNameMsg"></p>
					</section>
				</c:if>

				<section class="group-invite-card" style="margin-top: 20px;">
                <p class="group-invite-label">초대 코드</p>

                <div class="group-invite-code-row">
                    <div class="group-invite-code-box">
                        <span class="group-invite-code">${myGroup.familyInviteCode}</span>
                    </div>

						<button type="button" class="group-copy-btn"
							onclick="navigator.clipboard.writeText(document.querySelector('.group-invite-code').textContent.trim())">
							복사
						</button>
					</div>

                <p class="group-invite-guide">
                    초대 코드를 보내면 내 사람으로 연결할 수 있어요
                </p>
            </section>

        </div>
    </main>

    <jsp:include page="../layout/bottomNav.jsp" />

</div>
<script>
const contextPath = document.body.dataset.contextPath;

function updateGroupName() {
    const input = document.getElementById('groupNameInput');
    const msg = document.getElementById('groupNameMsg');
    const name = input.value.trim();

    if (!name) {
        msg.textContent = '그룹명을 입력해주세요.';
        msg.style.color = '#e53e3e';
        return;
    }

    fetch(contextPath + '/group?action=updateGroupName', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: 'familyName=' + encodeURIComponent(name)
    }).then(r => r.text()).then(result => {
        if (result === 'ok') {
            // 성공 시 섹션 자체를 숨김
            input.closest('section').style.display = 'none';
        } else {
            msg.textContent = '변경에 실패했어요. 다시 시도해주세요.';
            msg.style.color = '#e53e3e';
        }
    });
}
</script>
</body>
</html>