<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>온담 홈</title>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
</head>
<body data-context-path="${pageContext.request.contextPath}">
<div class="app-shell">
	<div class="top-header-cluster">
		<jsp:include page="../layout/header.jsp" />
	</div>
	<div class="top-search-cluster">
		<jsp:include page="../layout/searchBar.jsp" />
	</div>

	<main class="page-wrap">
		<section class="section-box banner-slider" aria-label="추천 배너">
			<div class="slider-viewport">
				<div class="slider-track" id="sliderTrack">
					<div class="banner-slide">
						<img src="${pageContext.request.contextPath}/images/banner1.png" class="banner-img" alt="온담 메인 배너 1" width="800" height="400" decoding="async">
					</div>
					<div class="banner-slide">
						<img src="${pageContext.request.contextPath}/images/banner2.png" class="banner-img" alt="온담 메인 배너 2" width="800" height="400" decoding="async">
					</div>
					<div class="banner-slide">
						<img src="${pageContext.request.contextPath}/images/banner3.png" class="banner-img" alt="온담 메인 배너 3" width="800" height="400" decoding="async">
					</div>
				</div>
				<div class="banner-dots" id="bannerDots" role="tablist" aria-label="배너 위치">
					<button type="button" class="banner-dot active" data-index="0" aria-label="배너 1" aria-selected="true"></button>
					<button type="button" class="banner-dot" data-index="1" aria-label="배너 2" aria-selected="false"></button>
					<button type="button" class="banner-dot" data-index="2" aria-label="배너 3" aria-selected="false"></button>
				</div>
			</div>
		</section>

        <section class="section-box" id="homeClothesSection">
            <div class="section-title">
                <h2>옷 종류</h2>
                <a href="${pageContext.request.contextPath}/category" id="homeCategoryAllView" class="section-title-link">전체 보기</a>
            </div>
            <div class="category-list">
                <a href="${pageContext.request.contextPath}/category" class="category-chip home-category-chip" data-category-tab="type" data-category-type="top">
                    <img src="${pageContext.request.contextPath}/images/category/type-top-knit.jpg" class="home-category-img" alt="윗옷" onerror="this.style.display='none'">
                    <span>윗옷</span>
                </a>
                <a href="${pageContext.request.contextPath}/category" class="category-chip home-category-chip" data-category-tab="type" data-category-type="bottom">
                    <img src="${pageContext.request.contextPath}/images/category/type-bottom-long.jpg" class="home-category-img" alt="아랫옷" onerror="this.style.display='none'">
                    <span>아랫옷</span>
                </a>
                <a href="${pageContext.request.contextPath}/category" class="category-chip home-category-chip" data-category-tab="type" data-category-type="outer">
                    <img src="${pageContext.request.contextPath}/images/category/type-outer-coat.jpg" class="home-category-img" alt="겉옷" onerror="this.style.display='none'">
                    <span>겉옷</span>
                </a>
                <a href="${pageContext.request.contextPath}/category" class="category-chip home-category-chip" data-category-tab="type" data-category-type="set">

                    <img src="${pageContext.request.contextPath}/images/category/type-set-dress.jpg" class="home-category-img" alt="한 벌 옷" onerror="this.style.display='none'">
                    <span>한 벌 옷</span>
                </a>
            </div>
        </section>

        <section class="section-box">
            <div class="section-title">
                <h2>자주 쓰는 메뉴</h2>
            </div>
            <div class="shortcut-grid">
                <a href="#" class="shortcut-card">
                    <span class="shortcut-icon"><span class="material-icons">auto_awesome</span></span>
                    <div class="shortcut-text">
                        <strong>옷 추천</strong>
                        <p>잘 맞는 옷 추천받기</p>
                    </div>
                </a>
                <a href="#" class="shortcut-card">
                    <span class="shortcut-icon"><span class="material-icons">smart_display</span></span>
                    <div class="shortcut-text">
                        <strong>영상보기</strong>
                        <p>넘기면서 옷 보기</p>
                    </div>
                </a>
                <a href="#" class="shortcut-card">
                    <span class="shortcut-icon"><span class="material-icons">favorite_border</span></span>
                    <div class="shortcut-text">
                        <strong>찜한 상품</strong>
                        <p>옷 찜하고 다시 보기</p>
                    </div>
                </a>
                <a href="#" class="shortcut-card">
                    <span class="shortcut-icon"><span class="material-icons">diversity_1</span></span>
                    <div class="shortcut-text">
                        <strong>내 사람</strong>
                        <p>함께 보고 선물하기</p>
                    </div>
                </a>
            </div>
        </section>

        <section class="section-box banner-card">
            <strong>지금 인기 있는 여름 옷을 모아봤어요</strong>
            <p>가볍게 입기 좋은 옷을 한눈에 볼 수 있어요.</p>
            <a href="#" class="banner-link">
                기획전 보기
                <span class="material-icons" style="font-size:18px;">chevron_right</span>
            </a>
        </section>

        <section class="section-box">
            <div class="section-title">
                <h2>추천 상품</h2>
                <a href="#">더 보기</a>
            </div>
            <div class="product-scroll">
                <a href="#" class="product-card">
                    <div class="product-thumb">
                        <span class="material-icons" style="font-size:42px;">image</span>
                    </div>
                    <div class="product-body">
                        <span class="product-tag">편안한 옷</span>
                        <p class="product-name">부드러운 카디건</p>
                        <p class="product-price">39,000원</p>
                        <p class="product-sub">입기 편한 기본 스타일</p>
                    </div>
                </a>
                <a href="#" class="product-card">
                    <div class="product-thumb">
                        <span class="material-icons" style="font-size:42px;">image</span>
                    </div>
                    <div class="product-body">
                        <span class="product-tag">가벼운 옷</span>
                        <p class="product-name">허리 편한 밴딩 바지</p>
                        <p class="product-price">29,000원</p>
                        <p class="product-sub">하루 종일 편안해요</p>
                    </div>
                </a>
                <a href="#" class="product-card">
                    <div class="product-thumb">
                        <span class="material-icons" style="font-size:42px;">image</span>
                    </div>
                    <div class="product-body">
                        <span class="product-tag">밝은 색</span>
                        <p class="product-name">화사한 셔츠</p>
                        <p class="product-price">34,000원</p>
                        <p class="product-sub">얼굴이 환해 보여요</p>
                    </div>
                </a>
            </div>
        </section>

        <section class="section-box">
            <div class="section-title">
                <h2>영상으로 보기</h2>
            </div>
            <div class="shorts-preview">
                <div class="shorts-thumb">
                    <div class="play-badge">
                        <span class="material-icons">play_arrow</span>
                    </div>
                </div>
                <div class="shorts-info">
                    <strong>영상을 통해 나에게 맞는<br>옷을 골라봐요</strong>
                    <p>짧은 영상으로 옷의 특징을 쉽게 확인해보세요.</p>
                    <a href="${pageContext.request.contextPath}/shorts" class="shorts-btn">영상 보러 가기</a>
                </div>
            </div>
        </section>

        <section class="section-box">
            <div class="section-title">
                <h2>알려드려요</h2>
            </div>
            <div class="notice-list">
                <div class="notice-item">
                    <span class="notice-dot"></span>
                    <p>신규 가입 쿠폰이 준비되어 있어요</p>
                    <span class="notice-date">오늘</span>
                </div>
                <div class="notice-item">
                    <span class="notice-dot"></span>
                    <p>함께 지갑을 통해 쉽게 옷을 구매할 수 있어요</p>
                    <span class="notice-date">안내</span>
                </div>
            </div>
        </section>
    </main>

    <jsp:include page="../layout/bottomNav.jsp" />
</div>
<script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>
<script src="${pageContext.request.contextPath}/js/home.js"></script>
</body>
</html>