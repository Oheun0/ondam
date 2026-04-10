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
						<a href="${pageContext.request.contextPath}/main" class="banner-slide-link" aria-label="배너 1: 홈으로 이동">
							<img src="${pageContext.request.contextPath}/images/banner/banner1.png" class="banner-img" alt="온담 메인 배너 1" width="800" height="400" decoding="async">
						</a>
					</div>
					<div class="banner-slide">
						<a href="${pageContext.request.contextPath}/group" class="banner-slide-link" aria-label="배너 2: 내 사람으로 이동">
							<img src="${pageContext.request.contextPath}/images/banner/banner2.png" class="banner-img" alt="온담 메인 배너 2" width="800" height="400" decoding="async">
						</a>
					</div>
					<div class="banner-slide">
						<a href="${pageContext.request.contextPath}/ai" class="banner-slide-link" aria-label="배너 3: 옷 추천으로 이동">
							<img src="${pageContext.request.contextPath}/images/banner/banner3.png" class="banner-img" alt="온담 메인 배너 3" width="800" height="400" decoding="async">
						</a>
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
                <h2>어떤 옷을 찾으세요?</h2>
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
                 <a href="${pageContext.request.contextPath}/ai-intro" class="shortcut-card">

                    <div class="shortcut-text">
                        <div class="shortcut-title-row">
                            <span class="shortcut-icon" aria-hidden="true"><span class="material-icons">auto_awesome</span></span>
                            <strong>옷 추천</strong>
                        </div>
                        <p>잘 맞는 옷 추천받기</p>
                    </div>
                </a>
                <a href="${pageContext.request.contextPath}/shorts" class="shortcut-card">

                    <div class="shortcut-text">
                        <div class="shortcut-title-row">
                            <span class="shortcut-icon" aria-hidden="true"><span class="material-icons">smart_display</span></span>
                            <strong>영상보기</strong>
                        </div>
                        <p>넘기며 옷 구경하기</p>
                    </div>
                </a>

                <a href="${pageContext.request.contextPath}/wish" class="shortcut-card">

                    <div class="shortcut-text">
                        <div class="shortcut-title-row">
                            <span class="shortcut-icon" aria-hidden="true"><span class="material-icons">favorite</span></span>
                            <strong>찜한 상품</strong>
                        </div>
                        <p>찜한 옷 다시보기</p>
                    </div>
                </a>

                <a href="${pageContext.request.contextPath}/group" class="shortcut-card">

                    <div class="shortcut-text">
                        <div class="shortcut-title-row">
                            <span class="shortcut-icon" aria-hidden="true"><span class="material-icons">diversity_1</span></span>
                            <strong>내 사람</strong>
                        </div>
                        <p>함께 보고 선물하기</p>
                    </div>
                </a>
            </div>
        </section>

        <section class="section-box banner-card">
            <strong>지금 인기 있는 봄 옷을 모아봤어요</strong>
            <p>가볍게 입기 좋은 옷을 한눈에 볼 수 있어요.</p>
           <a href="${pageContext.request.contextPath}/main?action=spring-sale" class="banner-link">
                기획전 보기
                <span class="material-icons" style="font-size:18px;">chevron_right</span>
            </a>
        </section>

        <section class="section-box">
            <div class="section-title">
                <h2>추천 상품</h2>
                <a href="#">더 보기</a>
            </div>
            <!-- 상품 카드: product-detail '연관 추천 상품' 톤으로 통일 -->
            <div class="home-reco-scroll" role="list" aria-label="추천 상품 목록">
                <article class="related-product-item" role="listitem">
                    <a href="#" class="related-product-item__anchor">
                        <div class="related-thumb-wrap">
                            <img src="${pageContext.request.contextPath}/images/category/comfort-soft.jpg" alt="" class="related-thumb-img" loading="lazy" />
                        </div>
                        <div class="related-product-info">
                            <span class="related-brand">ANDAR</span>
                            <span class="related-name">부드러운 라운드 니트 가디건</span>
                            <span class="related-price">39,000원</span>
                            <div class="home-reco-discount-row">
                                <span class="home-reco-original">48,750원</span>
                                <span class="related-discount">20% 할인</span>
                            </div>
                        </div>
                    </a>
                    <button type="button" class="related-wish-btn" aria-label="찜하기" aria-pressed="false">
                        <span class="material-icons-outlined" aria-hidden="true">favorite_border</span>
                    </button>
                </article>

                <article class="related-product-item" role="listitem">
                    <a href="#" class="related-product-item__anchor">
                        <div class="related-thumb-wrap">
                            <img src="${pageContext.request.contextPath}/images/category/out-walking.jpg" alt="" class="related-thumb-img" loading="lazy" />
                        </div>
                        <div class="related-product-info">
                            <span class="related-brand">온담</span>
                            <span class="related-name">편하게 입는 면 혼방 긴팔 티셔츠</span>
                            <span class="related-price">28,500원</span>
                            <div class="home-reco-discount-row">
                                <span class="home-reco-original">33,500원</span>
                                <span class="related-discount">15% 할인</span>
                            </div>
                        </div>
                    </a>
                    <button type="button" class="related-wish-btn" aria-label="찜하기" aria-pressed="false">
                        <span class="material-icons-outlined" aria-hidden="true">favorite_border</span>
                    </button>
                </article>

                <article class="related-product-item" role="listitem">
                    <a href="#" class="related-product-item__anchor">
                        <div class="related-thumb-wrap">
                            <img src="${pageContext.request.contextPath}/images/category/home-light.jpg" alt="" class="related-thumb-img" loading="lazy" />
                        </div>
                        <div class="related-product-info">
                            <span class="related-brand">B라벨</span>
                            <span class="related-name">가볍게 걸치기 좋은 바람막이 점퍼</span>
                            <span class="related-price">52,000원</span>
                            <div class="home-reco-discount-row">
                                <span class="home-reco-original">57,800원</span>
                                <span class="related-discount">10% 할인</span>
                            </div>
                        </div>
                    </a>
                    <button type="button" class="related-wish-btn" aria-label="찜하기" aria-pressed="false">
                        <span class="material-icons-outlined" aria-hidden="true">favorite_border</span>
                    </button>
                </article>

                <article class="related-product-item" role="listitem">
                    <a href="#" class="related-product-item__anchor">
                        <div class="related-thumb-wrap">
                            <img src="${pageContext.request.contextPath}/images/category/comfort-stretch.jpg" alt="" class="related-thumb-img" loading="lazy" />
                        </div>
                        <div class="related-product-info">
                            <span class="related-brand">시니어웨어</span>
                            <span class="related-name">허리 밴딩 편한 바지</span>
                            <span class="related-price">31,900원</span>
                            <div class="home-reco-discount-row">
                                <span class="home-reco-original">42,500원</span>
                                <span class="related-discount">25% 할인</span>
                            </div>
                        </div>
                    </a>
                    <button type="button" class="related-wish-btn" aria-label="찜하기" aria-pressed="false">
                        <span class="material-icons-outlined" aria-hidden="true">favorite_border</span>
                    </button>
                </article>

                <article class="related-product-item" role="listitem">
                    <a href="#" class="related-product-item__anchor">
                        <div class="related-thumb-wrap">
                            <img src="${pageContext.request.contextPath}/images/category/out-meeting.jpg" alt="" class="related-thumb-img" loading="lazy" />
                        </div>
                        <div class="related-product-info">
                            <span class="related-brand">C마켓</span>
                            <span class="related-name">집에서 입기 좋은 조거 팬츠</span>
                            <span class="related-price">24,000원</span>
                            <div class="home-reco-discount-row">
                                <span class="home-reco-original">25,300원</span>
                                <span class="related-discount">5% 할인</span>
                            </div>
                        </div>
                    </a>
                    <button type="button" class="related-wish-btn" aria-label="찜하기" aria-pressed="false">
                        <span class="material-icons-outlined" aria-hidden="true">favorite_border</span>
                    </button>
                </article>
            </div>
        </section>

        <section class="section-box">
            <div class="section-title">
                <h2>영상으로 보기</h2>
            </div>
            <div class="shorts-preview">
                <div class="shorts-thumb" aria-label="영상 썸네일">
                    <img src="${pageContext.request.contextPath}/images/home-shorts.jpg" alt="" class="shorts-thumb-img" loading="lazy"/>
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
                    <p>편하게 시작할 수 있도록 이용가이드를 준비했어요</p>
                    <span class="notice-date">안내</span>
                </div>
                <div class="notice-item">
                    <span class="notice-dot"></span>
                    <p>함께 지갑에 내 사람이 넣어준 금액으로 쉽게 결제할 수 있어요</p>
                    <span class="notice-date">안내</span>
                </div>
            </div>
        </section>
    </main>

    <jsp:include page="../layout/bottomNav.jsp" />
</div>
<script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>
<script src="${pageContext.request.contextPath}/js/home.js"></script>
<script src="${pageContext.request.contextPath}/js/home-reco-wish.js"></script>
</body>
</html>