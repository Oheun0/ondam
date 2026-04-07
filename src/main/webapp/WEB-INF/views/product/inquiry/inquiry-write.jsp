<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>상품 문의하기</title>

  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-detail.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/inquiry-write.css">
</head>
<body class="inquiry-write-page" data-context-path="${pageContext.request.contextPath}">
  <div class="detail-shell">
    <div class="detail-page-inner detail-page-inner--sticky-header inquiry-write-inner" id="inquiryWritePageRoot">
      <div class="inquiry-write-sticky-head">
        <div class="inquiry-write-header-wrap">
          <jsp:include page="/WEB-INF/views/layout/back-header.jsp"/>
          <h1 class="inquiry-write-header-title">상품 문의하기</h1>
        </div>
      </div>

      <main class="inquiry-write-main">
        <!-- 1. 상품 정보 카드 -->
        <section class="inquiry-write-card inquiry-write-product-card" aria-label="문의 상품">
          <div class="inquiry-write-product-card__inner">
            <div class="inquiry-write-product-thumb-wrap">
              <img src="${pageContext.request.contextPath}/images/category/type-top-knit.jpg" alt="" class="inquiry-write-product-thumb" width="65" height="65" loading="lazy"/>
            </div>
            <div class="inquiry-write-product-meta">
              <p class="inquiry-write-brand">A브랜드</p>
              <p class="inquiry-write-name">포근한 데일리 니트 가디건</p>
            </div>
          </div>
        </section>

        <!-- 2. 안내 문구 -->
        <section class="inquiry-write-card inquiry-write-guide" aria-label="안내">
          <h2 class="inquiry-write-title">궁금한 점을 남겨주세요</h2>
          <p class="inquiry-write-desc">
            배송, 사이즈, 재질처럼 궁금한 내용을 편하게 적어주세요 판매자가 확인 후 답변해드려요
          </p>
        </section>

        <!-- 3. 문의 입력창 -->
        <section class="inquiry-write-card" aria-labelledby="inquiryWriteTextHeading">
          <h2 class="inquiry-write-title" id="inquiryWriteTextHeading">문의 내용</h2>
          <label class="sr-only" for="inquiryWriteTextarea">문의 내용</label>
          <textarea id="inquiryWriteTextarea"
                    class="inquiry-write-textarea"
                    name="inquiryBody"
                    rows="4"
                    required
                    autocomplete="off"
                    placeholder="예) 소재가 두꺼운 편인가요?"></textarea>
        </section>

        <!-- 4. 문의하기 설정 -->
        <section class="inquiry-write-card inquiry-write-setting" aria-label="문의하기 설정">
          <h2 class="inquiry-write-title">공개범위</h2>

          <div class="inquiry-write-setting-block">
            <p class="inquiry-write-setting-desc">
              <span class="inquiry-write-setting-desc__label">공개</span>
              <span class="inquiry-write-setting-desc__sep" aria-hidden="true">|</span>
              <span class="inquiry-write-setting-desc__text">다른 사람도 볼 수 있어요</span>
            </p>
            <p class="inquiry-write-setting-desc inquiry-write-setting-desc--sub">
              <span class="inquiry-write-setting-desc__label">비공개</span>
              <span class="inquiry-write-setting-desc__sep" aria-hidden="true">|</span>
              <span class="inquiry-write-setting-desc__text">나와 판매자만 볼 수 있어요</span>
            </p>

            <div class="inquiry-write-toggle-group" role="radiogroup" aria-label="공개 여부 선택">
              <button type="button" class="inquiry-write-toggle-btn inquiry-write-toggle-btn--active" data-toggle-group="visibility" data-toggle-value="public" role="radio" aria-checked="true">공개</button>
              <button type="button" class="inquiry-write-toggle-btn" data-toggle-group="visibility" data-toggle-value="private" role="radio" aria-checked="false">비공개</button>
            </div>
          </div>
		  
          <div class="inquiry-write-setting-block inquiry-write-setting-block--spaced">
          <h2 class="inquiry-write-title">이름 표시</h2>
            <div class="inquiry-write-toggle-group" role="radiogroup" aria-label="이름 표시 선택">
              <button type="button" class="inquiry-write-toggle-btn inquiry-write-toggle-btn--active" data-toggle-group="name" data-toggle-value="show" role="radio" aria-checked="true">이름 보이기</button>
              <button type="button" class="inquiry-write-toggle-btn" data-toggle-group="name" data-toggle-value="hide" role="radio" aria-checked="false">이름 숨기기</button>
            </div>
          </div>
        </section>
      </main>
    </div>
  </div>

  <!-- 5. 하단 버튼 -->
  <div class="inquiry-write-submit-bar">
    <button type="button" class="inquiry-write-submit-btn" id="inquiryWriteSubmitBtn">문의 등록하기</button>
  </div>

  <!-- 6. 입력 누락 안내 모달 -->
  <div class="inquiry-write-modal hidden" id="inquiryWriteModalEmpty" role="dialog" aria-modal="true" aria-labelledby="inquiryWriteModalEmptyTitle">
    <div class="inquiry-write-dim" data-modal-dismiss="empty"></div>
    <div class="inquiry-write-modal-card">
      <p class="inquiry-write-modal-message" id="inquiryWriteModalEmptyTitle">아직 문의 내용이 작성되지 않았어요</p>
      <div class="inquiry-write-modal-actions inquiry-write-modal-actions--single">
        <button type="button" class="inquiry-write-modal-btn inquiry-write-modal-btn--primary" data-modal-action="empty-ok">확인</button>
      </div>
    </div>
  </div>

  <!-- 7. 등록 확인 모달 -->
  <div class="inquiry-write-modal hidden" id="inquiryWriteModalConfirm" role="dialog" aria-modal="true" aria-labelledby="inquiryWriteModalConfirmTitle">
    <div class="inquiry-write-dim" data-modal-dismiss="confirm"></div>
    <div class="inquiry-write-modal-card">
      <p class="inquiry-write-modal-message" id="inquiryWriteModalConfirmTitle">문의를 등록할까요?</p>
      <div class="inquiry-write-modal-actions inquiry-write-modal-actions--double">
        <button type="button" class="inquiry-write-modal-btn inquiry-write-modal-btn--ghost" data-modal-action="confirm-cancel">취소</button>
        <button type="button" class="inquiry-write-modal-btn inquiry-write-modal-btn--primary" data-modal-action="confirm-submit">등록하기</button>
      </div>
    </div>
  </div>

  <!-- 8. 등록 완료 모달 -->
  <div class="inquiry-write-modal hidden" id="inquiryWriteModalDone" role="dialog" aria-modal="true" aria-labelledby="inquiryWriteModalDoneTitle">
    <div class="inquiry-write-dim" data-modal-dismiss="done"></div>
    <div class="inquiry-write-modal-card">
      <p class="inquiry-write-modal-message" id="inquiryWriteModalDoneTitle">문의가 등록되었어요</p>
      <p class="inquiry-write-modal-sub">답변이 등록되면 알려드릴게요</p>
      <div class="inquiry-write-modal-actions inquiry-write-modal-actions--single">
        <button type="button" class="inquiry-write-modal-btn inquiry-write-modal-btn--primary" data-modal-action="done-ok">확인</button>
      </div>
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/js/inquiry-write.js"></script>
</body>
</html>
