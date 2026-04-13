<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>후기 작성하기</title>

  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-detail.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/review-write.css">
</head>
<body class="review-write-page" data-context-path="${pageContext.request.contextPath}">
  <div class="detail-shell">
    <div class="detail-page-inner detail-page-inner--sticky-header review-write-inner" id="reviewWritePageRoot">
      <div class="review-write-sticky-head">
        <div class="review-write-header-wrap">
          <jsp:include page="/WEB-INF/views/layout/back-header.jsp"/>
          <h1 class="review-write-header-title">
              <c:choose>
                  <c:when test="${not empty reviewDTO}">후기 수정하기</c:when>
                  <c:otherwise>후기 작성하기</c:otherwise>
              </c:choose>
          </h1>
        </div>
      </div>

      <main class="review-write-main">
        <!-- 1. 상품 요약 -->
        <section class="review-write-card review-write-product-card" aria-label="주문 상품">
          <c:set var="targetInfo" value="${not empty reviewDTO ? reviewDTO : itemInfo}" />
          
          <div class="review-write-product-card__inner">
            <div class="review-write-product-thumb-wrap">
			  <c:set var="imgSrc" value="${empty targetInfo.productImg ? 'type-top-knit.jpg' : targetInfo.productImg}" />
			  
			  <img src="${pageContext.request.contextPath}/uploads/products/${imgSrc}" 
			       alt="${targetInfo.snapProductName}" 
			       class="review-write-product-thumb" 
			       width="78" height="78" 
			       loading="lazy" 
			       onerror="this.src='${pageContext.request.contextPath}/images/category/type-top-knit.jpg'"/>
			</div>
            <div class="review-write-product-meta">
              <p class="review-write-product-name">${targetInfo.snapProductName}</p>
              <p class="review-write-product-option">${targetInfo.snapOptionColor} / ${targetInfo.snapOptionSize}</p>
            </div>
          </div>
        </section>

        <!-- 2. 별점 -->
        <section class="review-write-card review-write-rating-card" aria-labelledby="reviewWriteRatingHeading">
          <h2 class="review-write-section-title" id="reviewWriteRatingHeading">이 상품은 어떠셨나요?</h2>
          <p class="review-write-section-hint">마음에 드셨나요? 별점으로 알려주세요</p>
          <div class="review-write-rating-row">
            <div class="review-write-stars" id="reviewWriteStars" role="group" aria-label="별점 선택">
              <button type="button" class="review-write-star-btn" data-star="1" aria-label="1점">
                <span class="material-icons detail-review-star detail-review-star--empty" aria-hidden="true">star</span>
              </button>
              <button type="button" class="review-write-star-btn" data-star="2" aria-label="2점">
                <span class="material-icons detail-review-star detail-review-star--empty" aria-hidden="true">star</span>
              </button>
              <button type="button" class="review-write-star-btn" data-star="3" aria-label="3점">
                <span class="material-icons detail-review-star detail-review-star--empty" aria-hidden="true">star</span>
              </button>
              <button type="button" class="review-write-star-btn" data-star="4" aria-label="4점">
                <span class="material-icons detail-review-star detail-review-star--empty" aria-hidden="true">star</span>
              </button>
              <button type="button" class="review-write-star-btn" data-star="5" aria-label="5점">
                <span class="material-icons detail-review-star detail-review-star--empty" aria-hidden="true">star</span>
              </button>
            </div>
            <p id="starErrorMsg" style="display: none; color: #ff4d4f; font-size: 13px; margin-top: 8px;">
              별점을<br>선택해주세요.
              </p>
            <span class="review-write-rating-num" id="reviewWriteRatingNum" aria-live="polite" hidden aria-hidden="true"></span>
          </div>
        </section>

        <!-- 3. 선택형 질문 -->
        <section class="review-write-card" aria-label="간단 설문">
          <div class="review-write-chip-block">
            <h2 class="review-write-chip-question" id="reviewWriteFitLegend">착용감은 어떠셨나요?</h2>
            <div class="review-write-chip-group" role="radiogroup" aria-labelledby="reviewWriteFitLegend">
              <button type="button" class="review-write-chip" data-chip-group="fit" data-chip-value="tight" role="radio" aria-checked="false">딱 맞아요</button>
              <button type="button" class="review-write-chip" data-chip-group="fit" data-chip-value="comfy" role="radio" aria-checked="false">편하게 맞아요</button>
              <button type="button" class="review-write-chip" data-chip-group="fit" data-chip-value="loose" role="radio" aria-checked="false">넉넉해요</button>
            </div>
          </div>
          <div class="review-write-chip-block review-write-chip-block--spaced">
            <h2 class="review-write-chip-question" id="reviewWriteFabricLegend">소재는 어떠셨나요?</h2>
            <div class="review-write-chip-group" role="radiogroup" aria-labelledby="reviewWriteFabricLegend">
              <button type="button" class="review-write-chip" data-chip-group="fabric" data-chip-value="soft" role="radio" aria-checked="false">부드러워요</button>
              <button type="button" class="review-write-chip" data-chip-group="fabric" data-chip-value="normal" role="radio" aria-checked="false">보통이에요</button>
              <button type="button" class="review-write-chip" data-chip-group="fabric" data-chip-value="rough" role="radio" aria-checked="false">조금 거칠어요</button>
            </div>
          </div>
        </section>

        <!-- 4. 자유 후기 -->
        <section class="review-write-card" aria-labelledby="reviewWriteTextHeading">
          <h2 class="review-write-section-title" id="reviewWriteTextHeading">후기를 자유롭게 남겨주세요</h2>
          <label class="sr-only" for="reviewWriteTextarea">후기 내용</label>
          <div class="review-write-textarea-wrap">
            <textarea id="reviewWriteTextarea"
                      class="review-write-textarea"
                      name="reviewBody"
                      rows="4"
                      maxlength="1000"
                      required
                      autocomplete="off"
                      placeholder="예) 입었을 때 편하고 따뜻해서 좋아요"><c:if test="${not empty reviewDTO}">${reviewDTO.reviewContent}</c:if></textarea>
            <p class="review-write-char-count" id="reviewWriteCharCount" aria-live="polite">
              <span class="review-write-char-count__num" id="reviewWriteCharCurrent">0</span><span class="review-write-char-count__suffix"> / 1,000</span>
            </p>
          </div>
        </section>

        <!-- 5. 사진 (추가 가능한 사진 최대 개수 3~5개?)-->
        <section class="review-write-card review-write-upload-card" aria-label="사진 첨부">
  <h2 class="review-write-upload-title">
    <span class="material-icons-outlined review-write-upload-title__icon" aria-hidden="true">photo_camera</span>
    사진을 추가할 수 있어요 (선택)
  </h2>
  <input type="file"
         id="reviewWriteFileInput"
         class="review-write-file-input"
         accept="image/*"
         multiple
         aria-hidden="true"
         tabindex="-1"/>
  <button type="button" class="review-write-upload-btn" id="reviewWriteUploadBtn">사진 첨부하기</button>
  
  <div class="review-write-preview-list" id="reviewWritePreviewList" aria-live="polite">
    <c:if test="${not empty imageList}">
      <c:forEach var="img" items="${imageList}">
        <div class="review-write-preview-item existing-image" data-img-no="${img.reviewImgNo}">
          <img src="${pageContext.request.contextPath}/uploads/reviews/${img.reviewImg}" alt="기존 후기 사진">
          <button type="button" class="review-write-preview-remove" aria-label="사진 삭제">
            <span class="material-icons">close</span>
          </button>
        </div>
      </c:forEach>
    </c:if>
  </div>
</section>
      </main>
    </div>
  </div>

  <div class="review-write-submit-bar">
    <button type="button" class="review-write-submit-btn" id="reviewWriteSubmitBtn">후기 등록하기</button>
  </div>

  <!-- 미작성 안내 -->
  <div class="review-write-modal hidden" id="reviewWriteModalEmpty" role="dialog" aria-modal="true" aria-labelledby="reviewWriteModalEmptyTitle">
    <div class="review-write-modal-dim" data-modal-dismiss="empty"></div>
    <div class="review-write-modal-card">
      <p class="review-write-modal-message" id="reviewWriteModalEmptyTitle">아직 후기가 작성되지 않았어요</p>
      <div class="review-write-modal-actions review-write-modal-actions--single">
        <button type="button" class="review-write-modal-btn review-write-modal-btn--primary" data-modal-action="empty-ok">확인</button>
      </div>
    </div>
  </div>

  <!-- 등록 확인 -->
  <div class="review-write-modal hidden" id="reviewWriteModalConfirm" role="dialog" aria-modal="true" aria-labelledby="reviewWriteModalConfirmTitle">
    <div class="review-write-modal-dim" data-modal-dismiss="confirm"></div>
    <div class="review-write-modal-card">
      <p class="review-write-modal-message" id="reviewWriteModalConfirmTitle">후기를 등록할까요?</p>
      <div class="review-write-modal-actions review-write-modal-actions--double">
        <button type="button" class="review-write-modal-btn review-write-modal-btn--ghost" data-modal-action="confirm-cancel">취소</button>
        <button type="button" class="review-write-modal-btn review-write-modal-btn--primary" data-modal-action="confirm-submit">등록하기</button>
      </div>
    </div>
  </div>
  
  <!-- 추가해야 할 모달 : 별점 미기재, (가능하다면 모바일에서 사진 첨부하기 선택 시 -> 사진 촬영하기 / 보관함에서 선택)  -->
	<c:set var="isUpdate" value="${not empty reviewDTO}" />

  <form id="realSubmitForm" 
      action="${pageContext.request.contextPath}/review?action=${isUpdate ? 'update' : 'write'}" 
      method="post" 
      enctype="multipart/form-data" 
      style="display:none;">
      
    <c:choose>
        <c:when test="${isUpdate}">
            <input type="hidden" name="reviewNo" value="${reviewDTO.reviewNo}">
            <input type="hidden" id="initRating" value="${reviewDTO.reviewRating}">
        </c:when>
        <c:otherwise>
            <input type="hidden" name="orderItemNo" value="${orderItemNo}">
        </c:otherwise>
    </c:choose>

    <input type="hidden" name="reviewRating" id="hiddenRating">
    <input type="hidden" name="reviewContent" id="hiddenContent">
    <input type="hidden" name="isBodyPublic" value="1">
    <input type="hidden" name="deleteImgNos" id="deleteImgNos" value="">
    <input type="hidden" id="returnUrl" value="${param.returnUrl}">
    </form>
  <script src="${pageContext.request.contextPath}/js/review-write.js"></script>
</body>
</html>
