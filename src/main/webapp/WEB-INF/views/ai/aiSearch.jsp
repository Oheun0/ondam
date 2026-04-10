<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <title>이미지로 찾기 | 온담 AI</title>
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/category.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-search.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product.css">
  <style>
    /* 기존 CSS와 조화를 이루는 추가 스타일 */
    .ai-search-container {
      background: var(--search-main-bg); /* 기존 변수 사용 */
      min-height: calc(100vh - 70px);
    }
    
    .upload-section {
      padding: 20px 16px;
      background: #fff;
    }

    .upload-card {
      width: 100%;
      aspect-ratio: 1 / 0.6;
      border: 1.5px dashed var(--search-chip-border);
      border-radius: 18px;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      background: #fafafa;
      cursor: pointer;
      transition: all 0.2s ease;
    }

    .upload-card:active {
      background: #f1f1f1;
      transform: scale(0.98);
    }

    .upload-card .material-icons {
      font-size: 40px;
      color: var(--search-sub);
      margin-bottom: 10px;
    }

    .upload-card p {
      font-size: 15px;
      font-weight: 600;
      color: var(--search-text);
    }

    .preview-box {
      margin-top: 20px;
      display: none;
      text-align: center;
    }

    #previewImg {
      max-width: 100%;
      border-radius: 12px;
      box-shadow: 0 4px 12px rgba(0,0,0,0.1);
      display: block; 
	  margin: 0 auto;
    }

    /* 로딩 애니메이션 */
    .loading-wrap {
      display: none;
      text-align: center;
      padding: 40px 0;
    }

    /* 결과 그리드 간격 조정 */
    .ai-result-list {
      padding: 16px;
    }
    /* aiSearch.jsp 내 스타일 추가 */
	.product-grid {
	  display: grid;
	  grid-template-columns: repeat(2, 1fr); /* 2열 고정 */
	  gap: 20px 12px; /* product.css의 표준 간격 */
	  padding: 16px;
	}
	
	.ai-score-tag {
	  /* AI 전용 태그가 기존 디자인을 해치지 않도록 설정 */
	  pointer-events: none;
	  text-shadow: 0 1px 2px rgba(0,0,0,0.3);
	}
	/* --- AI 검색 결과 리스트 보정 CSS --- */

	/* 1. 이미지 1:1 정방형 비율 고정 및 꽉 차게 만들기 */
	#searchResultGrid .product-thumb-wrap {
	    position: relative; /* 찜 버튼과 AI 뱃지의 위치 기준점 */
	    width: 100%;
	    aspect-ratio: 1 / 1; /* 사진을 1:1 비율로 강제 */
	    overflow: hidden;
	    border-radius: 8px; /* 기존 UI에 맞춰 모서리 둥글게 (필요시 수치 조절) */
	}
	
	#searchResultGrid .product-thumb {
	    width: 100%;
	    height: 100%;
	    object-fit: cover; /* 이미지가 찌그러지지 않고 영역에 예쁘게 꽉 차도록 설정 */
	    object-position: center;
	}
	
	/* 2. 찜 버튼을 사진 우측 상단으로 완벽히 올리기 */
	#searchResultGrid .product-grid-wish-btn {
	    position: absolute;
	    top: 8px;
	    right: 8px;
	    z-index: 10;
	    background: transparent;
	    border: none;
	    padding: 4px;
	}
	/* 로딩 애니메이션 스타일 */
	.loading-spinner {
	  width: 40px;
	  height: 40px;
	  border: 4px solid #f3f3f3;
	  border-top: 4px solid #333; /* 브랜드 메인 컬러가 있다면 #333 대신 var(--main-color) 등으로 변경 */
	  border-radius: 50%;
	  animation: spin 1s linear infinite;
	  margin: 0 auto;
	}
	
	@keyframes spin {
	  0% { transform: rotate(0deg); }
	  100% { transform: rotate(360deg); }
	}
	/* (기존 로딩 스피너 CSS 아래에 추가) */
	@keyframes slideUpFade {
	  from { 
	    opacity: 0; 
	    transform: translateY(20px); 
	  }
	  to { 
	    opacity: 1; 
	    transform: translateY(0); 
	  }
	}
/* --- 이중 여백 방지 및 원본(category.css)과 동일한 간격 적용 --- */
/* --- 이중 여백 방지 및 하단 네비게이션 바 겹침 해결 --- */
	#resultArea, 
	#resultArea .product-list-section {
	    padding: 0 !important; 
	    margin: 0 !important;
	}
	
	#resultArea .product-content {
	    /* 좌, 우, 상단은 0으로 밀어버리고, 하단(bottom)만 90px을 주어 네비게이션 바 공간을 확보합니다! */
	    padding: 0 0 90px 0 !important; 
	    margin: 0 !important;
	}
	
	#searchResultGrid {
	    padding: 20px 16px !important; 
	    gap: 20px 12px !important; 
	}
  </style>
</head>
<!-- aiSearch.jsp 상단 body 태그 수정 -->
<body class="product-list-page" 
      data-context-path="${pageContext.request.contextPath}" 
      data-login-user="${not empty sessionScope.loginUser ? 'true' : ''}">
  <div class="search-shell">
    <div class="search-page-inner">
      <!-- 상단바: 기존 검색바 디자인 활용 (뒤로가기 포함) -->
      <header class="search-page-header">
        <button type="button" class="search-header-icon-btn" onclick="history.back()">
          <span class="material-icons">chevron_left</span>
        </button>
        <h1 class="search-section-title" style="flex:1; text-align:center; margin-right:42px;">이미지로 찾기</h1>
      </header>

      <main class="ai-search-container">
        <!-- 업로드 영역 -->
		<section class="upload-section">
		  <!-- 클릭 이벤트가 발생할 카드 -->
		  <div class="upload-card" id="uploadZone">
		    <span class="material-icons-outlined">photo_camera</span>
		    <p>사진을 찍거나 업로드하세요</p>
		    <!-- 실제 파일 탐색기를 여는 input (ID를 fileInp로 통일) -->
		    <input type="file" id="fileInp" name="searchImage" accept="image/*" style="display: none;">
		  </div>
		  
		  <!-- 미리보기 영역 -->
		  <div class="preview-box" id="previewBox" style="display:none;">
		    <p style="text-align:left; margin-bottom:10px; font-weight:700;">선택된 이미지</p>
		    <img id="previewImg" src="" alt="미리보기" style="max-width:100%;">
		  </div>
		</section>

		<!-- 결과 목록 영역: 일반 상품 리스트 디자인 적용 -->
		<div class="product-page-inner" id="resultArea">
		  <main class="product-content" style="padding-top: 0; padding-bottom: 90px;">
		    <section class="product-list-section">
		      <div class="product-grid" id="searchResultGrid">
		          <!-- 초기 안내 문구 -->
		          <div class="search-empty-msg" id="guideMsg" style="grid-column: span 2; padding: 50px 0; text-align: center; color: #999;">
		            <span class="material-icons" style="font-size: 48px; display: block; margin-bottom: 10px;">image_search</span>
		            사진을 업로드하면 유사한 상품을 찾아드려요.
		          </div>
		      </div>
		    </section>
		  </main>
		</div>
		
		<!-- 로딩 상태 (위의 결과 영역과 완전히 분리되어 작동합니다) -->
		<div id="loading" style="display:none; text-align:center; padding:50px 0;">
		  <div class="loading-spinner"></div>
		  <p style="margin-top:15px; color:#666; font-weight:500;">AI가 상품을 분석 중입니다...</p>
		</div>
      </main>
    </div>
  </div>

  <jsp:include page="/WEB-INF/views/layout/bottomNav.jsp"/>

  <script>const CONTEXT_PATH = '${pageContext.request.contextPath}';</script>
  <script src="${pageContext.request.contextPath}/js/aiSearch.js"></script>
</body>
</html>