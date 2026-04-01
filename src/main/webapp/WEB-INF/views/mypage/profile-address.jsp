<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    request.setAttribute("bottomNav", "mypage");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>내 정보 수정 - 배송지 관리</title>

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
</head>
<body data-context-path="${pageContext.request.contextPath}">
<div class="app-shell">
    <div class="top-header-cluster">
        <jsp:include page="../layout/header.jsp" />
    </div>

    <main class="profile-page">
        <section class="profile-intro-card">
            <div class="profile-intro-top">
                <a href="${pageContext.request.contextPath}/mypage/mypage.jsp" class="back-btn">
                    <span class="material-icons">chevron_left</span>
                </a>
                <div class="intro-text">
                    <h1>내 정보 수정하기</h1>
                    <p>배송지를 저장하고 관리할 수 있어요</p>
                </div>
            </div>

            <div class="step-tab-wrap">
                <a href="${pageContext.request.contextPath}/mypage/profile.jsp" class="step-tab">기본 정보</a>
                <a href="${pageContext.request.contextPath}/mypage/profile-address.jsp" class="step-tab active">배송지 관리</a>
                <a href="${pageContext.request.contextPath}/mypage/profile-preference.jsp" class="step-tab">취향 정보</a>
            </div>
        </section>

        <section class="edit-card">
            <div class="card-head">
                <div>
                    <h2>우리집</h2>
                    <p>기본으로 저장된 배송지예요</p>
                </div>
                <span class="status-badge active">기본</span>
            </div>

            <div class="address-info-list">
                <div class="info-line">
                    <span>받는 분</span>
                    <strong>김지현</strong>
                </div>
                <div class="info-line">
                    <span>연락처</span>
                    <strong>010-1234-5678</strong>
                </div>
                <div class="info-line address-line">
                    <span>주소</span>
                    <strong>부산광역시 부산진구 엄광로 176, 101동 1203호</strong>
                </div>
            </div>

            <div class="address-button-row">
                <button type="button" class="sub-action-btn upload-btn">변경하기</button>
                <button type="button" class="sub-action-btn reset-btn">삭제하기</button>
            </div>
        </section>

        <section class="edit-card">
            <div class="card-head">
                <div>
                    <h2>손주집</h2>
                </div>
            </div>

            <div class="address-info-list">
                <div class="info-line">
                    <span>받는 분</span>
                    <strong>김지현</strong>
                </div>
                <div class="info-line">
                    <span>연락처</span>
                    <strong>010-9876-5432</strong>
                </div>
                <div class="info-line address-line">
                    <span>주소</span>
                    <strong>부산광역시 동구 중앙대로 100, 203호</strong>
                </div>
            </div>

            <div class="address-button-row">
                <button type="button" class="sub-action-btn">기본으로 설정</button>
                <button type="button" class="sub-action-btn upload-btn">변경하기</button>
                <button type="button" class="sub-action-btn reset-btn">삭제하기</button>
            </div>
        </section>

        <section class="edit-card">
            <div class="empty-state-box">
                <span class="material-icons">add_location_alt</span>
                <h2>새 배송지 추가</h2>
                <p>배송지는 최대 3개까지 저장할 수 있어요</p>
                <button type="button" class="save-btn">배송지 추가하기</button>
            </div>
        </section>

        <!-- 배송지가 3개 다 찼을 때 예시
        <section class="edit-card">
            <div class="empty-state-box">
                <span class="material-icons">inventory_2</span>
                <h2>배송지가 모두 등록되었어요</h2>
                <p>새 배송지는 최대 3개까지만 저장할 수 있어요</p>
            </div>
        </section>
        -->
    </main>

    <jsp:include page="../layout/bottomNav.jsp" />
</div>
<script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>
</body>
</html>