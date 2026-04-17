<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  request.setAttribute("sellerActiveMenu", "shorts");
  request.setAttribute("sellerPageTitle", "새 쇼츠 등록");
%>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>새 쇼츠 등록 | 온담 파트너</title>

  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Material+Icons+Outlined&display=swap" rel="stylesheet">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-layout.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-product.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-shorts.css" />
</head>
<body class="seller-app" data-context-path="${pageContext.request.contextPath}">
  <div class="seller-layout">
    <jsp:include page="/WEB-INF/views/seller/layout/seller-sidebar.jsp" />

    <div class="seller-main-area">
      <jsp:include page="/WEB-INF/views/seller/layout/seller-header.jsp" />

      <main class="seller-content seller-shorts-form-page" aria-label="쇼츠 등록/수정">
        <header class="seller-shorts-form-head">
          <div>
            <h2 class="seller-shorts-title">새 쇼츠 등록</h2>
            <p class="seller-shorts-sub">영상과 썸네일, 연결 상품을 등록해 주세요</p>
          </div>
          <div class="seller-shorts-form-head-actions">
            <a class="seller-shorts-secondary-btn seller-shorts-link-btn" href="${pageContext.request.contextPath}/seller/shorts/list">
              <span class="material-icons-outlined" aria-hidden="true">arrow_back</span>
              목록으로
            </a>
          </div>
        </header>

        <form id="sellerShortsForm" class="seller-shorts-form" action="#" method="post" novalidate>
          <section class="seller-card seller-shorts-section" aria-label="기본 정보">
            <header class="seller-shorts-section-head">
              <div>
                <h3 class="seller-shorts-section-title">기본 정보</h3>
                <p class="seller-shorts-section-sub">쇼츠 제목은 영상 생성 시 자막으로 들어갑니다.</p>
              </div>
            </header>

            <div class="seller-shorts-grid seller-shorts-grid--2">
              <div class="seller-shorts-field">
                <label class="seller-shorts-label" for="shortsTitle">쇼츠 제목 (자막용) <span class="seller-shorts-required">*</span></label>
                <input id="shortsTitle" name="shortsTitle" class="seller-shorts-control" type="text" placeholder="예: 봄에 가볍게 입기 좋은 가디건" />
                <p class="seller-shorts-error hidden" id="shortsTitleError" aria-live="polite"></p>
              </div>

              <div class="seller-shorts-field">
                <label class="seller-shorts-label" for="productNo">연결 상품 <span class="seller-shorts-required">*</span></label>
                <select id="productNo" name="productNo" class="seller-shorts-control">
                  <option value="">연결 상품을 선택해 주세요</option>
                  <c:forEach var="prod" items="${productList}">
                    <option value="${prod.productNo}">${prod.productName}</option>
                  </c:forEach>
                </select>
                <p class="seller-shorts-error hidden" id="productNoError" aria-live="polite"></p>
              </div>

              <div class="seller-shorts-field" style="grid-column: span 2;">
                <label class="seller-shorts-label" for="shortsContent">쇼츠 내용</label>
                <textarea id="shortsContent" name="shortsContent" class="seller-product-textarea" rows="4" placeholder="상품에 대한 간단한 설명을 작성해 주세요 (최대 200자)"></textarea>
              </div>
            </div>
          </section>

          <section class="seller-card seller-shorts-section" aria-label="영상 파일">
            <header class="seller-shorts-section-head">
              <div>
                <h3 class="seller-shorts-section-title">수동 영상 업로드</h3>
                <p class="seller-shorts-section-sub">수동으로 숏폼을 등록할 경우 파일을 첨부해주세요</p>
              </div>
              <button type="button" class="seller-shorts-secondary-btn seller-shorts-auto-thumb-btn" id="aiVideoBtn">
                <span class="material-icons-outlined" aria-hidden="true">auto_awesome</span>
                영상 자동 생성
              </button>
            </header>

            <div class="seller-shorts-upload-row">
              <div class="seller-shorts-upload-box" id="videoBox" tabindex="0" role="button">
                <div class="seller-shorts-upload-icon" aria-hidden="true">
                  <span class="material-icons-outlined">smart_display</span>
                </div>
                <div class="seller-shorts-upload-text">
                  <div class="seller-shorts-upload-title">영상 파일 선택</div>
                  <div class="seller-shorts-upload-sub">mp4 형식의 세로형 영상을 권장해요</div>
                </div>
                <button type="button" class="seller-shorts-mini-btn seller-shorts-mini-btn--toggle" id="videoPickBtn">파일 선택</button>
              </div>
              <div class="seller-shorts-file-name" id="videoFileName">선택된 파일 없음</div>
              <p class="seller-shorts-error hidden" id="videoError" aria-live="polite"></p>
            </div>
          </section>

          <section class="seller-card seller-shorts-section" aria-label="썸네일 이미지">
            <header class="seller-shorts-section-head">
              <div>
                <h3 class="seller-shorts-section-title">썸네일 이미지 (자동 추출됨)</h3>
                <p class="seller-shorts-section-sub">영상을 업로드하면 썸네일이 자동 추출됩니다. 다른 이미지로 변경하고 싶을 때만 아래 버튼을 사용하세요.</p>
              </div>
            </header>

            <div class="seller-shorts-upload-row">
              <div class="seller-shorts-upload-box" id="thumbBox" tabindex="0" role="button">
                <div class="seller-shorts-upload-icon" aria-hidden="true">
                  <span class="material-icons-outlined">image</span>
                </div>
                <div class="seller-shorts-upload-text">
                  <div class="seller-shorts-upload-title">썸네일 수동 변경</div>
                </div>
                <button type="button" class="seller-shorts-mini-btn seller-shorts-mini-btn--toggle" id="thumbPickBtn">파일 선택</button>
              </div>
              <div class="seller-shorts-file-name" id="thumbFileName">선택된 파일 없음 (영상 등록 시 자동 추출)</div>
            </div>

            <div class="seller-shorts-thumb-preview" aria-label="썸네일 미리보기">
              <div class="seller-shorts-thumb-preview-inner" id="thumbPreviewBox">
                <img id="thumbPreviewImg" alt="썸네일 미리보기" class="hidden" />
                <div class="seller-shorts-thumb-preview-empty" id="thumbPreviewEmpty">
                  <span class="material-icons-outlined" aria-hidden="true">image</span>
                  <span>썸네일 미리보기</span>
                </div>
              </div>
            </div>
          </section>

          <div class="seller-shorts-form-actions">
            <button type="submit" class="seller-shorts-primary-btn" id="submitBtn">수동 영상 등록하기</button>
          </div>

          <p class="seller-shorts-form-error hidden" id="formError" aria-live="assertive"></p>
        </form>
      </main>

      <jsp:include page="/WEB-INF/views/seller/layout/seller-footer.jsp" />
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/js/seller/shorts-form.js"></script>
</body>
</html>