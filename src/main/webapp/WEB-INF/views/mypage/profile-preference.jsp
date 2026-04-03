<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%
    request.setAttribute("bottomNav", "mypage");

    int selHeight = 0;
    int selWeight = 0;
    Set<String> colorSet = new HashSet<>();
    Set<String> hobbySet = new HashSet<>();

    Object oh = request.getAttribute("prefUserHeight");
    if (oh instanceof Number) {
        selHeight = ((Number) oh).intValue();
    }
    Object ow = request.getAttribute("prefUserWeight");
    if (ow instanceof Number) {
        selWeight = ((Number) ow).intValue();
    }
    Object oc = request.getAttribute("prefPreferColor");
    if (oc instanceof String) {
        String cs = (String) oc;
        if (!cs.isEmpty()) {
            for (String s : cs.split("[,，]")) {
                if (s != null) {
                    String t = s.trim();
                    if (!t.isEmpty()) {
                        colorSet.add(t);
                    }
                }
            }
        }
    }
    Object hobbyAttr = request.getAttribute("userHobbyList");
    if (hobbyAttr instanceof Collection) {
        for (Object o : (Collection<?>) hobbyAttr) {
            if (o != null) {
                String h = o.toString().trim();
                if (!h.isEmpty()) {
                    hobbySet.add(h);
                }
            }
        }
    }

    final String[][] COLOR_OPTS = {
        {"검정색", "#111111"},
        {"흰색", "#ffffff"},
        {"회색", "#8b8f94"},
        {"고동색", "#5a3b2e"},
        {"연갈색", "#b88a60"},
        {"자주색", "#7b1f52"},
        {"빨강색", "#d73333"},
        {"연분홍색", "#f5c7d3"},
        {"노란색", "#f2d348"},
        {"남색", "#203864"},
        {"하늘색", "#86c8f2"},
        {"국방색", "#556b2f"}
    };

    final String[] HOBBY_OPTS = {
        "수영", "등산", "산책", "헬스", "요가", "골프", "자전거", "여행",
        "낚시", "원예", "텃밭 가꾸기", "가벼운 외출"
    };

    final int[] HEIGHT_VALS = {145, 150, 155, 160, 165, 170, 175, 180};
    final String[] HEIGHT_LABELS = {
        "145cm 이하", "146~150cm", "151~155cm", "156~160cm",
        "161~165cm", "166~170cm", "171~175cm", "176cm 이상"
    };

    final int[] WEIGHT_VALS = {45, 50, 55, 60, 65, 70, 75, 80, 85, 90};
    final String[] WEIGHT_LABELS = {
        "45kg 이하", "46~50kg", "51~55kg", "56~60kg", "61~65kg",
        "66~70kg", "71~75kg", "76~80kg", "81~85kg", "86kg 이상"
    };
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>내 정보 수정 - 취향 정보</title>

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
                    <p>나의 취향 정보를 수정할 수 있어요</p>
                </div>
            </div>
			<div class="step-tab-wrap">
			    <a href="${pageContext.request.contextPath}/profile" class="step-tab">기본 정보</a>
			    <a href="${pageContext.request.contextPath}/profile-address" class="step-tab">배송지 관리</a>
			    <a href="${pageContext.request.contextPath}/preference" class="step-tab active">취향 정보</a>
			</div>
        </section>

        <form action="${pageContext.request.contextPath}/profile/preference" method="post" class="profile-pref-form profile-pref-form--body">
            <input type="hidden" name="saveScope" value="body">

            <section class="edit-card">
                <div class="card-title-row">
                    <h2>체형 정보</h2>
                    <p>체형에 맞는 추천을 받아볼 수 있어요</p>
                </div>

                <div class="edit-form">
                    <div class="form-block">
                        <label class="block-label" for="userHeight">키 (cm)</label>
                        <select id="userHeight" name="userHeight" class="input-box">
                            <option value="">선택하세요</option>
                            <% for (int i = 0; i < HEIGHT_VALS.length; i++) {
                                int v = HEIGHT_VALS[i];
                                boolean sel = (selHeight == v);
                            %>
                            <option value="<%= v %>"<%= sel ? " selected" : "" %>><%= HEIGHT_LABELS[i] %></option>
                            <% } %>
                        </select>
                    </div>

                    <div class="form-block">
                        <label class="block-label" for="userWeight">몸무게</label>
                        <select id="userWeight" name="userWeight" class="input-box">
                            <option value="">선택하세요</option>
                            <% for (int i = 0; i < WEIGHT_VALS.length; i++) {
                                int v = WEIGHT_VALS[i];
                                boolean sel = (selWeight == v);
                            %>
                            <option value="<%= v %>"<%= sel ? " selected" : "" %>><%= WEIGHT_LABELS[i] %></option>
                            <% } %>
                        </select>
                    </div>
                </div>
            </section>

            <button type="submit" class="save-btn">체형 정보 저장하기</button>
        </form>

        <form action="${pageContext.request.contextPath}/profile/preference" method="post" class="profile-pref-form profile-pref-form--taste">
            <input type="hidden" name="saveScope" value="preference">

            <section class="edit-card">
                <div class="card-title-row">
                    <h2>취향 정보</h2>
                </div>

                <div class="edit-form">
                    <p class="pref-subsection-label">좋아하는 색상을 골라주세요</p>
                    <p class="pref-subsection-desc">최대 3개 정도 고르면 추천이 더 쉬워져요</p>
                    <div class="chip-group pref-chip-group">
                        <% for (String[] row : COLOR_OPTS) {
                            String c = row[0];
                            String dot = row[1];
                            boolean on = colorSet.contains(c);
                        %>
                        <label class="choice-chip">
                            <input type="checkbox" name="userPreferColor" value="<%= c %>"<%= on ? " checked" : "" %>>
                            <span><i class="pref-color-dot" style="background:<%= dot %>;"></i><%= c %></span>
                        </label>
                        <% } %>
                    </div>

                    <p class="pref-subsection-label pref-subsection-label--spaced">즐겨 하는 활동을 알려주세요</p>
                    <p class="pref-subsection-desc">평소 자주 하는 활동을 선택하면 <br> 싱황에 어울리는 옷을 추천해드려요</p>
                    <div class="chip-group">
                        <% for (String h : HOBBY_OPTS) {
                            boolean on = hobbySet.contains(h);
                        %>
                        <label class="choice-chip">
                            <input type="checkbox" name="userHobby" value="<%= h %>"<%= on ? " checked" : "" %>>
                            <span><%= h %></span>
                        </label>
                        <% } %>
                    </div>
                </div>
            </section>

            <button type="submit" class="save-btn">취향 정보 저장하기</button>
        </form>
    </main>

    <jsp:include page="../layout/bottomNav.jsp" />
</div>
<script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>
</body>
</html>
