<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("bottomNav", "mypage"); %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>${mode == 'edit' ? '배송지 변경' : '배송지 추가'}</title>

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
    
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>
<body data-context-path="${pageContext.request.contextPath}">
<div class="app-shell">
    <div class="top-header-cluster">
        <jsp:include page="../layout/header.jsp" />
    </div>

    <main class="profile-page">
        <section class="profile-intro-card">
            <div class="profile-intro-top">
                <c:choose>
				  <c:when test="${isHelperMode}">
				    <a href="${pageContext.request.contextPath}/profile-address?targetUserNo=${targetUserNo}" class="back-btn">
				  </c:when>
				  <c:otherwise>
				    <a href="${pageContext.request.contextPath}/profile-address" class="back-btn">
				  </c:otherwise>
				</c:choose>
                    <span class="material-icons">chevron_left</span>
                </a>

                <div class="intro-text">
                    <h1>내 정보 수정하기</h1>
                    <p>배송지를 저장하고 관리할 수 있어요</p>
                </div>
            </div>

            <div class="step-tab-wrap">
			  <c:choose>
			    <c:when test="${isHelperMode}">
			      <span class="step-tab"
      				style="color:#ccc; pointer-events:none; cursor:not-allowed;">기본 정보</span>
			      <a href="${pageContext.request.contextPath}/profile-address?targetUserNo=${targetUserNo}"
			         class="step-tab active">배송지 관리</a>
			      <span class="step-tab"
      				style="color:#ccc; pointer-events:none; cursor:not-allowed;">취향 정보</span>
			    </c:when>
			    <c:otherwise>
			      <a href="${pageContext.request.contextPath}/profile" class="step-tab">기본 정보</a>
			      <a href="${pageContext.request.contextPath}/profile-address" class="step-tab active">배송지 관리</a>
			      <a href="${pageContext.request.contextPath}/preference" class="step-tab">취향 정보</a>
			    </c:otherwise>
			  </c:choose>
			</div>
        </section>

        <section class="edit-card">
            <div class="card-title-row">
                <h2>${mode == 'edit' ? '배송지 변경' : '배송지 추가'}</h2>
                <p>${mode == 'edit' ? '배송지를 변경하고 기본 주소로 설정할 수 있어요' : '배송지는 최대 3개까지 저장 가능해요'}</p>
            </div>

            <form action="${pageContext.request.contextPath}/address/save" method="post" class="edit-form" id="addressForm">
                <input type="hidden" name="mode" value="${mode}">
                <c:if test="${isHelperMode}">
			        <input type="hidden" name="targetUserNo" value="${targetUserNo}">
			    </c:if>
                <c:if test="${mode == 'edit'}">
                    <input type="hidden" name="userAddressNo" value="${addrInfo.userAddressNo}">
                </c:if>
                
                <div class="form-block">
                    <label for="addressName" class="block-label">배송지명</label>
                    <input type="text" id="addressName" name="addressName" class="input-box input"
                           value="${addrInfo.addressName}" placeholder="배송지 이름을 입력하세요 (예: 우리집)">
                    <p class="error-msg" id="err-addressName"></p>
                </div>

                <div class="form-block">
                    <label for="receiverName" class="block-label">받는 분</label>
                    <input type="text" id="receiverName" name="receiverName" class="input-box input"
                           value="${addrInfo.receiverName}" placeholder="받는 분 이름을 입력하세요">
                    <p class="error-msg" id="err-receiverName"></p>
                </div>

                <div class="form-block">
                    <label for="receiverTel" class="block-label">연락처</label>
                    <input type="text" id="receiverTel" name="receiverTel" class="input-box input"
                           value="${addrInfo.receiverTel}" placeholder="연락처를 입력하세요">
                    <p class="error-msg" id="err-receiverTel"></p>
                </div>

                <div class="form-block">
                    <label for="userZipcode" class="block-label">우편번호</label>
                    <div class="address-inline-row">
                        <input type="text" id="userZipcode" name="userZipcode" class="input-box input"
                               value="${addrInfo.userZipcode}" placeholder="우편번호" readonly>
                        <button type="button" class="zip-lookup-btn" onclick="execDaumPostcode()">우편번호 조회</button>
                    </div>
                    <p class="error-msg" id="err-userZipcode"></p>
                </div>

                <div class="form-block">
                    <label class="block-label" for="userAddress">주소</label>
                    <input type="text" id="userAddress" name="userAddress" class="input-box input"
                           value="${addrInfo.userAddress}" placeholder="주소를 입력하세요" readonly>
                    <input type="text" id="userDetailAddress" name="userDetailAddress" class="input-box input input-box--stacked"
                           value="${addrInfo.userDetailAddress}" placeholder="상세 주소를 입력하세요">
                    <p class="error-msg" id="err-userDetailAddress"></p>
                </div>

                <div class="form-block">
                    <c:choose>
                        <c:when test="${mode == 'edit' && addrInfo.isDefault == 1}">
                            <div style="color: #D84C33; font-size: 15px; font-weight: 800; display: flex; align-items: center; gap: 6px; padding: 5px 0;">
                                <span class="material-icons" style="font-size: 20px;">verified</span>
                                현재 기본 배송지입니다.
                            </div>
                            <input type="hidden" name="isDefault" value="1">
                        </c:when>
                        <c:otherwise>
                            <label class="check-row">
                                <input type="checkbox" name="isDefault" value="1" 
                                       ${(mode == 'add' && empty addressList) || addrInfo.isDefault == 1 ? 'checked' : ''}>
                                <span>기본 배송지로 저장할게요</span>
                            </label>
                        </c:otherwise>
                    </c:choose>
                </div>

                <button type="button" class="save-btn" onclick="executeSubmit()">${mode == 'edit' ? '변경사항 저장하기' : '배송지 추가하기'}</button>
            </form>
        </section>
    </main>

    <jsp:include page="../layout/bottomNav.jsp" />
</div>

<script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>
<script src="${pageContext.request.contextPath}/js/address-form.js"></script>
</body>
</html>