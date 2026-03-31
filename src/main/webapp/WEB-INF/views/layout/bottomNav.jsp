<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String bottomNav = (String) request.getAttribute("bottomNav");
	if (bottomNav == null) bottomNav = "home";
%>
<nav class="bottom-nav" aria-label="하단 메뉴">
    <a href="${pageContext.request.contextPath}/main" class="nav-item <%= "home".equals(bottomNav) ? "active" : "" %>" aria-label="홈">

        <span class="material-icons nav-icon">home</span>
        <span class="nav-label">홈</span>
    </a>
    <a href="${pageContext.request.contextPath}/shorts" class="nav-item <%= "shorts".equals(bottomNav) ? "active" : "" %>" aria-label="쇼츠">
        <span class="material-icons nav-icon">smart_display</span>
        <span class="nav-label">영상보기</span>
    </a>
    <a href="${pageContext.request.contextPath}/ai" class="nav-item <%= "ai".equals(bottomNav) ? "active" : "" %>" aria-label="AI 추천">
        <span class="material-icons nav-icon">auto_awesome</span>
        <span class="nav-label">옷추천</span>
    </a>
    <a href="${pageContext.request.contextPath}/group" class="nav-item <%= "group".equals(bottomNav) ? "active" : "" %>" aria-label="내 사람">
        <span class="material-icons nav-icon">diversity_1</span>
        <span class="nav-label">내 사람</span>
    </a>
    <a href="${pageContext.request.contextPath}/mypage" class="nav-item <%= "mypage".equals(bottomNav) ? "active" : "" %>" aria-label="마이페이지">
        <span class="material-icons nav-icon">person</span>
        <span class="nav-label">내 정보</span>
    </a>
</nav>
