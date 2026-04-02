<%@ page contentType="text/html; charset=UTF-8"%>
<% request.setAttribute("bottomNav", "home"); %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>온담 - 비밀번호 재설정</title>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
<script>const ctxPath = "${pageContext.request.contextPath}";</script>
<script defer src="${pageContext.request.contextPath}/js/auth.js?ver=9"></script>
</head>
<body>
<div class="app-shell">
    <div class="auth-page">
        <div class="auth-wrap">
            <div class="auth-card">
                <h1 class="page-title">새 비밀번호 설정</h1>
                <p class="page-desc">새로 사용할 비밀번호를 입력해주세요</p>

                <form action="${pageContext.request.contextPath}/reset-pwd" method="post" onsubmit="return validate();">
                    <input type="hidden" name="userNo" value="${userNo}">

                    <div class="form-group">
                        <label class="form-label">새 비밀번호</label>
                        <input type="password" name="userPwd" id="userPwd" class="input" placeholder="새 비밀번호 입력">
                        <span class="error-msg" id="err-userPwd"></span>
                    </div>

                    <div class="form-group">
                        <label class="form-label">비밀번호 확인</label>
                        <input type="password" name="userPwdCheck" id="userPwdCheck" class="input" placeholder="비밀번호 확인 입력">
                        <span class="error-msg" id="err-userPwdCheck"></span>
                    </div>

                    <div class="btn-row" style="margin-top: 30px;">
                        <button type="submit" class="btn btn-primary" style="width: 100%;">비밀번호 변경하기</button>
                    </div>
                </form>
            </div></div>
        </div><jsp:include page="../layout/bottomNav.jsp" />
    </div><script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>
</body>
</html>