<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setAttribute("bottomNav", "home");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<meta name="context-path" content="${pageContext.request.contextPath}">
	<title>온담 | 카테고리</title>
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/category.css">
</head>
<body data-context-path="${pageContext.request.contextPath}">
<div class="app-shell app-shell--category">
	<div class="top-header-cluster">
		<jsp:include page="../layout/header.jsp" />
	</div>
	<div class="top-search-cluster">
		<jsp:include page="../layout/searchBar.jsp" />
	</div>

	<main class="category-main">
		<div class="category-top-tabs" role="tablist" aria-label="카테고리 보기 방식">
			<button type="button" class="top-tab active" data-tab="situation" id="tabSituation" role="tab" aria-selected="true">상황으로 보기</button>
			<button type="button" class="top-tab" data-tab="type" id="tabType" role="tab" aria-selected="false">종류로 보기</button>
		</div>

		<!-- 상황으로 보기: 정사각 썸네일 + 텍스트 세로 리스트 -->
		<div class="category-content tab-content active" id="situation-content" role="tabpanel" aria-labelledby="tabSituation">
			<div class="category-layout">
				<div class="category-sidebar">
					<button type="button" class="sidebar-item situation-side active" data-target="daily">일상 생활</button>
					<button type="button" class="sidebar-item situation-side" data-target="special">특별한 날</button>
					<button type="button" class="sidebar-item situation-side" data-target="hobby">취미·여가</button>
					<button type="button" class="sidebar-item situation-side" data-target="gift">선물하기</button>
				</div>
				<div class="category-detail">
					<div class="detail-group active" id="daily">
						<div class="detail-section">
							<h2 class="detail-heading">🏠 집에서 입어요</h2>
							<ul class="situation-rows">
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=14" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/home-comfy.jpg" alt=""></span><span class="situation-label">집에서 편하게 입는 옷</span></a></li>
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=15" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/home-sleep.jpg" alt=""></span><span class="situation-label">잠잘 때 입는 옷</span></a></li>
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=16" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/home-housework.jpg" alt=""></span><span class="situation-label">집안일 할 때 입는 옷</span></a></li>
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=17" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/home-light.jpg" alt=""></span><span class="situation-label">가볍게 입는 옷</span></a></li>
							</ul>
						</div>
						<div class="detail-section">
							<h2 class="detail-heading">🚶‍♂️ 외출할 때 입어요</h2>
							<ul class="situation-rows">
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=18" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/out-light.jpg" alt=""></span><span class="situation-label">가볍게 나갈 때 입는 옷</span></a></li>
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=19" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/out-meeting.jpg" alt=""></span><span class="situation-label">사람 만날 때 입는 옷</span></a></li>
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=20" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/out-weather.jpg" alt=""></span><span class="situation-label">날씨에 맞는 옷</span></a></li>
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=21" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/out-walking.jpg" alt=""></span><span class="situation-label">오래 걸어도 편한 옷</span></a></li>
							</ul>
						</div>
						<div class="detail-section">
							<h2 class="detail-heading">🏥 병원 갈 때 입어요</h2>
							<ul class="situation-rows">
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=22" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/hospital-easywear.jpg" alt=""></span><span class="situation-label">입고 벗기 쉬운 옷</span></a></li>
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=23" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/hospital-checkup.jpg" alt=""></span><span class="situation-label">검사 받기 편한 옷</span></a></li>
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=24" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/hospital-comfy.jpg" alt=""></span><span class="situation-label">편안한 옷</span></a></li>
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=25" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/hospital-warm.jpg" alt=""></span><span class="situation-label">따뜻한 옷</span></a></li>
							</ul>
						</div>
						<div class="detail-section">
							<h2 class="detail-heading">🛌 편하게 입어요</h2>
							<ul class="situation-rows">
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=26" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/comfort-stretch.jpg" alt=""></span><span class="situation-label">신축성 좋은 옷</span></a></li>
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=27" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/comfort-soft.jpg" alt=""></span><span class="situation-label">부드러운 옷</span></a></li>
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=28" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/comfort-loose.jpg" alt=""></span><span class="situation-label">넉넉한 옷</span></a></li>
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=29" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/comfort-skin.jpg" alt=""></span><span class="situation-label">피부에 자극 없는 옷</span></a></li>
							</ul>
						</div>
					</div>

					<div class="detail-group" id="special">
						<div class="detail-section">
							<h2 class="detail-heading">🎉 행사 갈 때 입어요</h2>
							<ul class="situation-rows">
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=30" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/event-wedding.jpg" alt=""></span><span class="situation-label">결혼식 갈 때</span></a></li>
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=31" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/event-funeral.jpg" alt=""></span><span class="situation-label">장례식 갈 때</span></a></li>
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=32" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/event-reunion.jpg" alt=""></span><span class="situation-label">동창회 갈 때</span></a></li>
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=33" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/event-meeting.jpg" alt=""></span><span class="situation-label">모임/행사 갈 때</span></a></li>
							</ul>
						</div>
						<div class="detail-section">
							<h2 class="detail-heading">🎓 중요한 날 입어요</h2>
							<ul class="situation-rows">
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=34" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/import-graduate.jpg" alt=""></span><span class="situation-label">입학식 / 졸업식</span></a></li>
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=35" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/import-family.jpg" alt=""></span><span class="situation-label">가족 행사</span></a></li>
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=36" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/import-anniversary.jpg" alt=""></span><span class="situation-label">기념일</span></a></li>
							</ul>
						</div>
					</div>

					<div class="detail-group" id="hobby">
						<div class="detail-section">
							<h2 class="detail-heading">⛰️ 운동·야외 활동</h2>
							<ul class="situation-rows">
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=37" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/active-hike.jpg" alt=""></span><span class="situation-label">등산할 때</span></a></li>
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=38" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/active-golf.jpg" alt=""></span><span class="situation-label">골프 칠 때</span></a></li>
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=39" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/active-swim.jpg" alt=""></span><span class="situation-label">수영할 때</span></a></li>
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=40" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/active-bike.jpg" alt=""></span><span class="situation-label">자전거 탈 때</span></a></li>
							</ul>
						</div>
						<div class="detail-section">
							<h2 class="detail-heading">🌿 여가 활동</h2>
							<ul class="situation-rows">
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=41" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/hobby-travel.jpg" alt=""></span><span class="situation-label">여행 갈 때</span></a></li>
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=42" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/hobby-fishing.jpg" alt=""></span><span class="situation-label">낚시할 때</span></a></li>
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=43" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/hobby-farm.jpg" alt=""></span><span class="situation-label">텃밭/원예 할 때</span></a></li>
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=44" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/hobby-goout.jpg" alt=""></span><span class="situation-label">편하게 놀러 갈 때</span></a></li>
							</ul>
						</div>
					</div>

					<div class="detail-group" id="gift">
						<div class="detail-section">
							<h2 class="detail-heading">🎁 선물하기 좋아요</h2>
							<ul class="situation-rows">
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=45" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/gift-parents.jpg" alt=""></span><span class="situation-label">부모님께 드리기 좋은 옷</span></a></li>
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=46" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/gift-gdparents.jpg" alt=""></span><span class="situation-label">할머니·할아버지 옷</span></a></li>
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=47" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/gift-gdchildren.jpg" alt=""></span><span class="situation-label">손주 옷</span></a></li>
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=48" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/gift-freesize.jpg" alt=""></span><span class="situation-label">사이즈 넉넉한 옷</span></a></li>
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=49" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/gift-anyone.jpg" alt=""></span><span class="situation-label">누구나 입기 좋은 옷</span></a></li>
								<li><a href="${pageContext.request.contextPath}/product?action=list&situationNo=50" class="situation-row"><span class="situation-thumb" aria-hidden="true"><img src="${pageContext.request.contextPath}/images/category/gift-popular.jpg" alt=""></span><span class="situation-label">인기 많은 옷</span></a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- 종류로 보기: 2열 그리드 + 정사각 썸네일 -->
		<div class="category-content tab-content" id="type-content" role="tabpanel" aria-labelledby="tabType">
    <div class="category-layout">
        <div class="category-sidebar">
            <button type="button" class="sidebar-item type-side active" data-type="top">윗옷</button>
            <button type="button" class="sidebar-item type-side" data-type="bottom">아랫옷</button>
            <button type="button" class="sidebar-item type-side" data-type="outer">겉옷</button>
            <button type="button" class="sidebar-item type-side" data-type="set">한 벌 옷</button>
        </div>
        <div class="category-detail">
            <div class="type-group active" id="cat-top">
                <div class="detail-section">
                    <h2 class="detail-heading">윗옷</h2>
                    <div class="detail-row two-column">
                        <a href="${pageContext.request.contextPath}/product?action=list&categoryNo=5" class="detail-card detail-card--type">
                            <div class="detail-thumb detail-thumb--square" aria-hidden="true">
                                <img src="${pageContext.request.contextPath}/images/category/type-top-short.jpg" alt="">
                            </div>
                            <p>반팔</p>
                        </a>
                        <a href="${pageContext.request.contextPath}/product?action=list&categoryNo=6" class="detail-card detail-card--type">
                            <div class="detail-thumb detail-thumb--square" aria-hidden="true">
                                <img src="${pageContext.request.contextPath}/images/category/type-top-long.jpg" alt="">
                            </div>
                            <p>긴팔</p>
                        </a>
                        <a href="${pageContext.request.contextPath}/product?action=list&categoryNo=7" class="detail-card detail-card--type">
                            <div class="detail-thumb detail-thumb--square" aria-hidden="true">
                                <img src="${pageContext.request.contextPath}/images/category/type-top-knit.jpg" alt="">
                            </div>
                            <p>니트</p>
                        </a>
                        <a href="${pageContext.request.contextPath}/product?action=list&categoryNo=8" class="detail-card detail-card--type">
                            <div class="detail-thumb detail-thumb--square" aria-hidden="true">
                                <img src="${pageContext.request.contextPath}/images/category/type-top-shirt.jpg" alt="">
                            </div>
                            <p>셔츠</p>
                        </a>
                        <a href="${pageContext.request.contextPath}/product?action=list&categoryNo=9" class="detail-card detail-card--type">
                            <div class="detail-thumb detail-thumb--square" aria-hidden="true">
                                <img src="${pageContext.request.contextPath}/images/category/type-top-vest.jpg" alt="">
                            </div>
                            <p>조끼</p>
                        </a>
                    </div>
                </div>
            </div>

            <div class="type-group" id="cat-bottom">
                <div class="detail-section">
                    <h2 class="detail-heading">아랫옷</h2>
                    <div class="detail-row two-column">
                        <a href="${pageContext.request.contextPath}/product?action=list&categoryNo=10" class="detail-card detail-card--type">
                            <div class="detail-thumb detail-thumb--square" aria-hidden="true">
                                <img src="${pageContext.request.contextPath}/images/category/type-bottom-long.jpg" alt="">
                            </div>
                            <p>긴바지</p>
                        </a>
                        <a href="${pageContext.request.contextPath}/product?action=list&categoryNo=11" class="detail-card detail-card--type">
                            <div class="detail-thumb detail-thumb--square" aria-hidden="true">
                                <img src="${pageContext.request.contextPath}/images/category/type-bottom-short.jpg" alt="">
                            </div>
                            <p>반바지</p>
                        </a>
                        <a href="${pageContext.request.contextPath}/product?action=list&categoryNo=12" class="detail-card detail-card--type">
                            <div class="detail-thumb detail-thumb--square" aria-hidden="true">
                                <img src="${pageContext.request.contextPath}/images/category/type-bottom-skirt.jpg" alt="">
                            </div>
                            <p>치마</p>
                        </a>
                    </div>
                </div>
            </div>

            <div class="type-group" id="cat-outer">
                <div class="detail-section">
                    <h2 class="detail-heading">겉옷</h2>
                    <div class="detail-row two-column">
                        <a href="${pageContext.request.contextPath}/product?action=list&categoryNo=13" class="detail-card detail-card--type">
                            <div class="detail-thumb detail-thumb--square" aria-hidden="true">
                                <img src="${pageContext.request.contextPath}/images/category/type-outer-cardigan.jpg" alt="">
                            </div>
                            <p>가디건</p>
                        </a>
                        <a href="${pageContext.request.contextPath}/product?action=list&categoryNo=14" class="detail-card detail-card--type">
                            <div class="detail-thumb detail-thumb--square" aria-hidden="true">
                                <img src="${pageContext.request.contextPath}/images/category/type-outer-jumper.jpg" alt="">
                            </div>
                            <p>점퍼</p>
                        </a>
                        <a href="${pageContext.request.contextPath}/product?action=list&categoryNo=15" class="detail-card detail-card--type">
                            <div class="detail-thumb detail-thumb--square" aria-hidden="true">
                                <img src="${pageContext.request.contextPath}/images/category/type-outer-coat.jpg" alt="">
                            </div>
                            <p>코트</p>
                        </a>
                        <a href="${pageContext.request.contextPath}/product?action=list&categoryNo=16" class="detail-card detail-card--type">
                            <div class="detail-thumb detail-thumb--square" aria-hidden="true">
                                <img src="${pageContext.request.contextPath}/images/category/type-outer-windbreaker.jpg" alt="">
                            </div>
                            <p>바람막이</p>
                        </a>
                    </div>
                </div>
            </div>

            <div class="type-group" id="cat-set">
                <div class="detail-section">
                    <h2 class="detail-heading">한 벌 옷</h2>
                    <div class="detail-row two-column">
                        <a href="${pageContext.request.contextPath}/product?action=list&categoryNo=17" class="detail-card detail-card--type">
                            <div class="detail-thumb detail-thumb--square" aria-hidden="true">
                                <img src="${pageContext.request.contextPath}/images/category/type-set-dress.jpg" alt="">
                            </div>
                            <p>원피스</p>
                        </a>
                        <a href="${pageContext.request.contextPath}/product?action=list&categoryNo=18" class="detail-card detail-card--type">
                            <div class="detail-thumb detail-thumb--square" aria-hidden="true">
                                <img src="${pageContext.request.contextPath}/images/category/type-set-two-piece.jpg" alt="">
                            </div>
                            <p>세트 옷</p>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</main>

	<jsp:include page="../layout/bottomNav.jsp" />
</div>
<script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>
<script src="${pageContext.request.contextPath}/js/category.js"></script>
</body>
</html>