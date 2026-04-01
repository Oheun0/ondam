<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    request.setAttribute("bottomNav", "mypage");

    String mode = request.getParameter("mode");
    if (mode == null || (!mode.equals("add") && !mode.equals("edit"))) {
        mode = "add";
    }

    boolean isEdit = "edit".equals(mode);

    String pageTitle = isEdit ? "내 정보 수정하기" : "내 정보 수정하기";
    String pageDesc = isEdit ? "배송지를 저장하고 관리할 수 있어요" : "배송지를 저장하고 관리할 수 있어요";
    String cardTitle = isEdit ? "배송지 변경" : "배송지 추가";
    String saveLabel = isEdit ? "변경사항 저장하기" : "배송지 추가하기";

    /* edit일 때 예시 데이터 */
    String receiverName = isEdit ? "김지현" : "";
    String phone = isEdit ? "010-1234-5678" : "";
    String zipcode = isEdit ? "47340" : "";
    String address1 = isEdit ? "부산광역시 부산진구 엄광로 176" : "";
    String address2 = isEdit ? "101동 1203호" : "";
    boolean isDefault = isEdit;
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title><%= cardTitle %></title>

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
                <a href="${pageContext.request.contextPath}/mypage/profile-address.jsp" class="back-btn">
                    <span class="material-icons">chevron_left</span>
                </a>

                <div class="intro-text">
                    <h1><%= pageTitle %></h1>
                    <p><%= pageDesc %></p>
                </div>
            </div>

            <div class="step-tab-wrap">
                <a href="${pageContext.request.contextPath}/mypage/profile.jsp" class="step-tab">기본 정보</a>
                <a href="${pageContext.request.contextPath}/mypage/profile-address.jsp" class="step-tab active">배송지 관리</a>
                <a href="${pageContext.request.contextPath}/mypage/profile-preference.jsp" class="step-tab">취향 정보</a>
            </div>
        </section>

        <section class="edit-card">
            <div class="card-title-row">
                <h2><%= cardTitle %></h2>
                <p>
                    <% if (isEdit) { %>
                        배송지를 변경하고 기본 주소로 설정할 수 있어요
                    <% } else { %>
                        배송지는 최대 3개까지 저장 가능해요
                    <% } %>
                </p>
            </div>

            <form action="${pageContext.request.contextPath}/mypage/address/save" method="post" class="edit-form">
                <input type="hidden" name="mode" value="<%= mode %>">
                <% if (isEdit) { %>
                    <input type="hidden" name="addressId" value="1">
                <% } %>

                <div class="form-block">
                    <label for="receiverName" class="block-label">받는 분</label>
                    <input
                        type="text"
                        id="receiverName"
                        name="receiverName"
                        class="input-box"
                        value="<%= receiverName %>"
                        placeholder="받는 분 이름을 입력하세요">
                </div>

                <div class="form-block">
                    <label for="receiverPhone" class="block-label">연락처</label>
                    <input
                        type="text"
                        id="receiverPhone"
                        name="receiverPhone"
                        class="input-box"
                        value="<%= phone %>"
                        placeholder="연락처를 입력하세요">
                </div>

                <div class="form-block">
                    <label for="zipcode" class="block-label">우편번호</label>
                    <div class="address-inline-row">
                        <input
                            type="text"
                            id="zipcode"
                            name="zipcode"
                            class="input-box"
                            value="<%= zipcode %>"
                            placeholder="우편번호"
                            inputmode="numeric"
                            autocomplete="postal-code">
                        <button type="button" class="zip-lookup-btn">우편번호 조회</button>
                    </div>
                </div>

                <div class="form-block">
                    <label class="block-label" for="address1">주소</label>
                    <input
                        type="text"
                        id="address1"
                        name="address1"
                        class="input-box"
                        value="<%= address1 %>"
                        placeholder="주소를 입력하세요">
                    <input
                        type="text"
                        id="address2"
                        name="address2"
                        class="input-box input-box--stacked"
                        value="<%= address2 %>"
                        placeholder="상세 주소를 입력하세요">
                </div>

                <div class="form-block">
                    <label class="check-row">
                        <input type="checkbox" name="isDefault" <%= isDefault ? "checked" : "" %>>
                        <span>기본 배송지로 저장할게요</span>
                    </label>
                </div>

                <button type="submit" class="save-btn"><%= saveLabel %></button>

                <% if (isEdit) { %>
                <button type="button" class="secondary-full-btn">삭제하기</button>
                <% } %>
            </form>
        </section>
    </main>

    <jsp:include page="../layout/bottomNav.jsp" />
</div>
<script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>
</body>
</html>