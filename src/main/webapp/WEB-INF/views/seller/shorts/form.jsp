<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
            <a class="seller-shorts-secondary-btn seller-shorts-link-btn" href="${pageContext.request.contextPath}/preview?page=seller/shorts/list">
              <span class="material-icons-outlined" aria-hidden="true">arrow_back</span>
              목록으로
            </a>
          </div>
        </header>

        <form id="sellerShortsForm" class="seller-shorts-form" action="#" method="post" novalidate>
          <!-- 섹션 1: 기본 정보 -->
          <section class="seller-card seller-shorts-section" aria-label="기본 정보">
            <header class="seller-shorts-section-head">
              <div>
                <h3 class="seller-shorts-section-title">기본 정보</h3>
                <p class="seller-shorts-section-sub">쇼츠의 제목과 연결 상품을 설정해 주세요</p>
              </div>
            </header>

            <div class="seller-shorts-grid seller-shorts-grid--2">
              <div class="seller-shorts-field">
                <label class="seller-shorts-label" for="shortsTitle">쇼츠 제목 <span class="seller-shorts-required">*</span></label>
                <input id="shortsTitle" name="shortsTitle" class="seller-shorts-control" type="text" placeholder="예: 봄에 가볍게 입기 좋은 가디건" />
                <p class="seller-shorts-error hidden" id="shortsTitleError" aria-live="polite"></p>
              </div>

              <div class="seller-shorts-field">
                <label class="seller-shorts-label" for="productNo">연결 상품 <span class="seller-shorts-required">*</span></label>
                <select id="productNo" name="productNo" class="seller-shorts-control">
                  <option value="">연결 상품을 선택해 주세요</option>
                  <option value="P-1001">부드러운 라운드 니트 가디건</option>
                  <option value="P-1002">편안한 봄 니트 조끼</option>
                  <option value="P-1003">가벼운 데일리 셔츠</option>
                  <option value="P-2001">산뜻한 플라워 블라우스</option>
                </select>
                <p class="seller-shorts-error hidden" id="productNoError" aria-live="polite"></p>
              </div>

              <div class="seller-shorts-field">
                <label class="seller-shorts-label" for="isPublic">공개 상태 <span class="seller-shorts-required">*</span></label>
                <select id="isPublic" name="isPublic" class="seller-shorts-control">
                  <option value="">공개 상태를 선택해 주세요</option>
                  <option value="true">공개</option>
                  <option value="false">비공개</option>
                </select>
                <p class="seller-shorts-error hidden" id="isPublicError" aria-live="polite"></p>
              </div>

              <div class="seller-shorts-field">
                <label class="seller-shorts-label" for="priority">노출 우선순위</label>
                <select id="priority" name="priority" class="seller-shorts-control">
                  <option value="1">1 (우선)</option>
                  <option value="2">2</option>
                  <option value="3">3</option>
                  <option value="4">4</option>
                  <option value="5">5</option>
                </select>
                <p class="seller-shorts-helper">숫자가 낮을수록 더 먼저 노출되는 것으로 가정해요. (더미)</p>
              </div>
            </div>
          </section>

          <!-- 섹션 2: 영상 파일 -->
          <section class="seller-card seller-shorts-section" aria-label="영상 파일">
            <header class="seller-shorts-section-head">
              <div>
                <h3 class="seller-shorts-section-title">영상 파일</h3>
                <p class="seller-shorts-section-sub">사용자 쇼츠 화면에 재생될 영상을 등록해 주세요</p>
              </div>
              <button type="button" class="seller-shorts-secondary-btn seller-shorts-auto-thumb-btn" id="aiVideoBtn">
                <span class="material-icons-outlined" aria-hidden="true">auto_awesome</span>
                AI 영상 생성
              </button>
            </header>

            <div class="seller-shorts-upload-row">
              <div class="seller-shorts-upload-box" id="videoBox" tabindex="0" role="button" aria-label="영상 파일 선택(더미)">
                <div class="seller-shorts-upload-icon" aria-hidden="true">
                  <span class="material-icons-outlined">smart_display</span>
                </div>
                <div class="seller-shorts-upload-text">
                  <div class="seller-shorts-upload-title">영상 파일 선택</div>
                  <div class="seller-shorts-upload-sub">클릭하면 파일명을 더미로 채울 수 있어요</div>
                </div>
                <button type="button" class="seller-shorts-mini-btn seller-shorts-mini-btn--toggle" id="videoPickBtn">파일 선택</button>
              </div>
              <div class="seller-shorts-file-name" id="videoFileName">선택된 파일 없음</div>
              <p class="seller-shorts-error hidden" id="videoError" aria-live="polite"></p>
            </div>

            <div class="seller-shorts-guide">
              <div class="seller-shorts-guide-item">- mp4 형식의 세로형 영상을 권장해요</div>
              <div class="seller-shorts-guide-item">- 영상은 사용자 쇼츠 화면의 <strong>/uploads/shorts/</strong> 경로 기준으로 연결돼요</div>
            </div>

            <div class="seller-shorts-video-preview" aria-label="영상 미리보기(더미)">
              <div class="seller-shorts-video-placeholder">
                <span class="material-icons-outlined" aria-hidden="true">play_circle</span>
                <span>영상 미리보기 (더미)</span>
              </div>
            </div>
          </section>

          <!-- 섹션 3: 썸네일 -->
          <section class="seller-card seller-shorts-section" aria-label="썸네일 이미지">
            <header class="seller-shorts-section-head">
              <div>
                <h3 class="seller-shorts-section-title">썸네일 이미지</h3>
                <p class="seller-shorts-section-sub">쇼츠 목록과 미리보기 화면에 사용할 이미지를 등록해 주세요</p>
              </div>
              <button type="button" class="seller-shorts-secondary-btn seller-shorts-auto-thumb-btn" id="autoThumbBtn">
                <span class="material-icons-outlined" aria-hidden="true">auto_awesome</span>
                AI 썸네일 생성
              </button>
            </header>

            <div class="seller-shorts-upload-row">
              <div class="seller-shorts-upload-box" id="thumbBox" tabindex="0" role="button" aria-label="썸네일 파일 선택(더미)">
                <div class="seller-shorts-upload-icon" aria-hidden="true">
                  <span class="material-icons-outlined">image</span>
                </div>
                <div class="seller-shorts-upload-text">
                  <div class="seller-shorts-upload-title">썸네일 선택</div>
                  <div class="seller-shorts-upload-sub">선택된 파일명이 표시돼요</div>
                </div>
                <button type="button" class="seller-shorts-mini-btn seller-shorts-mini-btn--toggle" id="thumbPickBtn">파일 선택</button>
              </div>
              <div class="seller-shorts-file-name" id="thumbFileName">선택된 파일 없음</div>
            </div>

            <div class="seller-shorts-thumb-preview" aria-label="썸네일 미리보기(더미)">
              <div class="seller-shorts-thumb-preview-inner" id="thumbPreviewBox">
                <img id="thumbPreviewImg" alt="썸네일 미리보기" />
                <div class="seller-shorts-thumb-preview-empty" id="thumbPreviewEmpty">
                  <span class="material-icons-outlined" aria-hidden="true">image</span>
                  <span>썸네일 미리보기</span>
                </div>
              </div>
            </div>

          </section>

          <!-- 섹션 4: 연결 미리보기 -->
          <section class="seller-card seller-shorts-section" aria-label="연결 미리보기">
            <header class="seller-shorts-section-head">
              <div>
                <h3 class="seller-shorts-section-title">연결 미리보기</h3>
                <p class="seller-shorts-section-sub">사용자 화면에 어떻게 연결될지 확인해 보세요</p>
              </div>
            </header>

            <div class="seller-shorts-preview">
              <div class="seller-shorts-preview-media">
                <div class="seller-shorts-preview-thumb" aria-label="미리보기 썸네일(더미)">
                  <img id="previewThumbImg" alt="미리보기 썸네일" />
                  <div class="seller-shorts-preview-thumb-empty" id="previewThumbEmpty">
                    <span class="material-icons-outlined" aria-hidden="true">smart_display</span>
                    <span>썸네일/영상 자리 (9:16)</span>
                  </div>
                </div>
              </div>
              <div class="seller-shorts-preview-card" aria-label="미리보기 정보(더미)">
                <div class="seller-shorts-preview-row">
                  <span class="seller-shorts-preview-k">제목</span>
                  <span class="seller-shorts-preview-v" id="previewTitle">-</span>
                </div>
                <div class="seller-shorts-preview-row">
                  <span class="seller-shorts-preview-k">연결 상품</span>
                  <span class="seller-shorts-preview-v" id="previewProduct">-</span>
                </div>
                <div class="seller-shorts-preview-row">
                  <span class="seller-shorts-preview-k">공개 상태</span>
                  <span class="seller-shorts-preview-v" id="previewPublic">-</span>
                </div>
                <div class="seller-shorts-preview-row">
                  <span class="seller-shorts-preview-k">예상 노출</span>
                  <span class="seller-shorts-preview-v" id="previewPriority">우선순위 -</span>
                </div>
                <p class="seller-shorts-helper seller-shorts-helper--preview">
                  shorts.jsp의 <strong>shortsList</strong> 항목(videoFile, thumbFile, shortsTitle, productNo)에 연결된다고 가정한 더미 미리보기예요.
                </p>
              </div>
            </div>
          </section>

          <div class="seller-shorts-form-actions">
            <button type="button" class="seller-shorts-secondary-btn" id="tempSaveBtn">임시 저장</button>
            <button type="submit" class="seller-shorts-primary-btn" id="submitBtn">등록하기</button>
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

