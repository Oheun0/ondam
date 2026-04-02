<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("bottomNav", "mypage");%>
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
                <a href="${pageContext.request.contextPath}/mypage" class="back-btn">
                    <span class="material-icons">chevron_left</span>
                </a>
                <div class="intro-text">
                    <h1>내 정보 수정하기</h1>
                    <p>배송지를 저장하고 관리할 수 있어요</p>
                </div>
            </div>
            <div class="step-tab-wrap">
                <a href="${pageContext.request.contextPath}/profile" class="step-tab">기본 정보</a>
				<a href="${pageContext.request.contextPath}/profile-address" class="step-tab active">배송지 관리</a>
				<a href="${pageContext.request.contextPath}/preference" class="step-tab">취향 정보</a>
            </div>
        </section>
	
        <c:forEach var="addr" items="${addressList}">
            <section class="edit-card">
                <div class="card-head">
                    <div>
                        <h2>${addr.addressName}</h2>
                        <c:if test="${addr.isDefault == 1}">
                            <p>기본으로 저장된 배송지예요</p>
                        </c:if>
                    </div>
                    <c:if test="${addr.isDefault == 1}">
                        <span class="status-badge active">기본</span>
                    </c:if>
                </div>

                <div class="address-info-list">
                    <div class="info-line">
                        <span>받는 분</span>
                        <strong>${addr.receiverName}</strong>
                    </div>
                    <div class="info-line">
                        <span>연락처</span>
                        <strong>${addr.receiverTel}</strong>
                    </div>
                    <div class="info-line address-line">
                        <span>주소</span>
                        <strong>(${addr.userZipcode}) ${addr.userAddress} ${addr.userDetailAddress}</strong>
                    </div>
                </div>

            <div class="address-button-row">
                    <c:if test="${addr.isDefault == 0}">
                        <button type="button" class="sub-action-btn">기본으로 설정</button>
                    </c:if>

                    <button type="button" class="sub-action-btn upload-btn" 
                            onclick="location.href='${pageContext.request.contextPath}/address/form?mode=edit&addressId=${addr.userAddressNo}'">
                        변경하기</button>
                    <button type="button" class="sub-action-btn reset-btn">삭제하기</button>
                </div>
            </section>
        </c:forEach>

        <c:choose>
            <c:when test="${addressList.size() < 3}">
                <section class="edit-card">
                    <div class="empty-state-box">
                        <span class="material-icons">add_location_alt</span>
                        <h2>새 배송지 추가</h2>
                        <p>배송지는 최대 3개까지 저장할 수 있어요</p>
                        <button type="button" class="save-btn" 
                                onclick="location.href='${pageContext.request.contextPath}/address/form?mode=add'">
                            배송지 추가하기
                        </button>
                    </div>
                </section>
            </c:when>
            <c:otherwise>
                <section class="edit-card">
                    <div class="empty-state-box">
                        <span class="material-icons">inventory_2</span>
                        <h2>배송지가 모두 등록되었어요</h2>
                        <p>새 배송지는 최대 3개까지만 저장할 수 있어요</p>
                    </div>
                </section>
            </c:otherwise>
        </c:choose>
    </main>

    <jsp:include page="../layout/bottomNav.jsp" />
</div>
<script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>
</body>
</html>