<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<body data-context-path="${pageContext.request.contextPath}"
data-login-user="${loginUser != null}">
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
						<a href="${pageContext.request.contextPath}/guide" class="banner-slide-link" aria-label="배너 1: 이용 가이드로 이동">
							<img src="${pageContext.request.contextPath}/images/banner/banner1.png" class="banner-img" alt="온담 이용 가이드 배너" width="800" height="400" decoding="async">
						</a>
					</div>
					<div class="banner-slide">
						<a href="${pageContext.request.contextPath}/group" class="banner-slide-link" aria-label="배너 2: 내 사람으로 이동">
							<img src="${pageContext.request.contextPath}/images/banner/banner2.png" class="banner-img" alt="온담 메인 배너 2" width="800" height="400" decoding="async">
						</a>
					</div>
					<div class="banner-slide">
						<a href="${pageContext.request.contextPath}/ai-intro" class="banner-slide-link" aria-label="배너 3: 옷 추천으로 이동">
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
		        <h2>최신 상품</h2>
		        <a href="${pageContext.request.contextPath}/product?action=list&categoryName=&sort=최신순">더 보기</a>
		    </div>
		
		    <%-- 1. 가로 스크롤 컨테이너는 반복문 '바깥'에 있어야 합니다! --%>
		    <div class="home-reco-scroll" role="list" aria-label="추천 상품 목록">
		        
		        <c:forEach var="p" items="${newProducts}">
		            <%-- 2. 여기서부터 하드코딩했던 <article> 구조를 그대로 사용합니다 --%>
		            <article class="related-product-item" role="listitem">
		                <a href="${pageContext.request.contextPath}/product?action=detail&productNo=${p.productNo}" class="related-product-item__anchor">
		                    <div class="related-thumb-wrap">
		                        <%-- 이미지 경로 주의! DB에 저장된 파일명을 불러옵니다 --%>
		                        <img src="${pageContext.request.contextPath}/uploads/products/${newThumbnailMap[p.productNo]}" 
		                             alt="${p.productName}" class="related-thumb-img" loading="lazy" />
		                    </div>
		                    <div class="related-product-info">
		                        <span class="related-brand">${p.productBrand}</span>
		                        <span class="related-name">${p.productName}</span>
		                        <span class="related-price">
		                            <fmt:formatNumber value="${p.productPrice}" pattern="#,###"/>원
		                        </span>
		                        
		                        <%-- 할인이 있을 때만 노출 --%>
		                        <c:if test="${p.productOriginPrice > p.productPrice}">
		                            <div class="home-reco-discount-row">
		                                <span class="home-reco-original">
		                                    <fmt:formatNumber value="${p.productOriginPrice}" pattern="#,###"/>원
		                                </span>
		                                <span class="related-discount">
		                                    <fmt:parseNumber var="discountRate" value="${(1 - p.productPrice / p.productOriginPrice) * 100}" integerOnly="true" />
		                                    ${discountRate}% 할인
		                                </span>
		                            </div>
		                        </c:if>
		                    </div>
		                </a>
		                
		                <%-- 찜 버튼 상태값 연동 (wishSet은 컨트롤러에서 넣어준다고 가정) --%>
		                <button type="button" 
		                        class="related-wish-btn ${wishSet.contains(p.productNo) ? 'is-active' : ''}" 
		                        aria-label="찜하기" 
		                        aria-pressed="${wishSet.contains(p.productNo) ? 'true' : 'false'}"
		                        data-product-no="${p.productNo}">
		                    <span class="${wishSet.contains(p.productNo) ? 'material-icons' : 'material-icons-outlined'}" aria-hidden="true">
		                        ${wishSet.contains(p.productNo) ? 'favorite' : 'favorite_border'}
		                    </span>
		                </button>
		            </article>
		        </c:forEach>
		
		    </div> <%-- .home-reco-scroll 끝 --%>
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