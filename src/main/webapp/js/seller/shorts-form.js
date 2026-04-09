/* global document, alert, console */
(function () {
  function $(id) { return document.getElementById(id); }
  function show(el) { if (el) el.classList.remove('hidden'); }
  function hide(el) { if (el) el.classList.add('hidden'); }
  function setText(el, text) { if (el) el.textContent = text; }

  function showError(id, msg) {
    var el = $(id);
    if (!el) return;
    setText(el, msg);
    show(el);
  }
  function clearError(id) {
    var el = $(id);
    if (!el) return;
    setText(el, '');
    hide(el);
  }

  function contextPath() {
    return document.body.getAttribute('data-context-path') || '';
  }

  var form = $('sellerShortsForm');
  if (!form) return;

  // Basic fields
  var titleEl = $('shortsTitle');
  var productEl = $('productNo');
  var publicEl = $('isPublic');
  var priorityEl = $('priority');

  // Upload dummy states
  var videoFile = '';
  var thumbFile = '';

  var videoFileNameEl = $('videoFileName');
  var thumbFileNameEl = $('thumbFileName');

  var thumbPreviewImg = $('thumbPreviewImg');
  var thumbPreviewEmpty = $('thumbPreviewEmpty');
  var previewThumbImg = $('previewThumbImg');
  var previewThumbEmpty = $('previewThumbEmpty');

  function setThumbPreview(on) {
    if (on) {
      var src = contextPath() + '/images/category/out-weather.jpg';
      if (thumbPreviewImg) thumbPreviewImg.src = src;
      if (previewThumbImg) previewThumbImg.src = src;
      hide(thumbPreviewEmpty);
      hide(previewThumbEmpty);
    } else {
      if (thumbPreviewImg) thumbPreviewImg.removeAttribute('src');
      if (previewThumbImg) previewThumbImg.removeAttribute('src');
      show(thumbPreviewEmpty);
      show(previewThumbEmpty);
    }
  }

  function updatePreview() {
    var title = titleEl ? titleEl.value.trim() : '';
    var productText = productEl && productEl.selectedOptions && productEl.selectedOptions[0] ? productEl.selectedOptions[0].textContent : '';
    var pubVal = publicEl ? publicEl.value : '';
    var pubText = pubVal === 'true' ? '공개' : (pubVal === 'false' ? '비공개' : '-');
    var priority = priorityEl ? priorityEl.value : '';

    setText($('previewTitle'), title || '-');
    setText($('previewProduct'), (productEl && productEl.value) ? productText : '-');
    setText($('previewPublic'), pubText);
    setText($('previewPriority'), priority ? ('우선순위 ' + priority) : '우선순위 -');
  }

  function pickVideoDummy() {
    videoFile = 'spring-cardigan.mp4';
    if (videoFileNameEl) videoFileNameEl.textContent = videoFile;
    clearError('videoError');
    updatePreview();
    alert('영상 파일이 선택되었습니다. (더미)\n\n' + videoFile);
  }

  function pickThumbDummy(fromAuto) {
    thumbFile = fromAuto ? 'auto-thumb-from-video.jpg' : 'spring-cardigan-thumb.jpg';
    if (thumbFileNameEl) thumbFileNameEl.textContent = thumbFile;
    setThumbPreview(true);
    updatePreview();
    alert('썸네일이 선택되었습니다. (더미)\n\n' + thumbFile);
  }

  // Click handlers
  var videoBox = $('videoBox');
  var videoPickBtn = $('videoPickBtn');
  if (videoBox) videoBox.addEventListener('click', pickVideoDummy);
  if (videoPickBtn) videoPickBtn.addEventListener('click', function (e) { e.preventDefault(); pickVideoDummy(); });

  var thumbBox = $('thumbBox');
  var thumbPickBtn = $('thumbPickBtn');
  var autoThumbBtn = $('autoThumbBtn');
  var aiVideoBtn = $('aiVideoBtn');
  if (thumbBox) thumbBox.addEventListener('click', function () { pickThumbDummy(false); });
  if (thumbPickBtn) thumbPickBtn.addEventListener('click', function (e) { e.preventDefault(); pickThumbDummy(false); });
  if (autoThumbBtn) autoThumbBtn.addEventListener('click', function (e) { e.preventDefault(); pickThumbDummy(true); });
  if (aiVideoBtn) aiVideoBtn.addEventListener('click', function (e) {
    e.preventDefault();
    alert('AI 영상 생성은 아직 준비 중이에요. (더미)\n\n추후: 템플릿/스크립트 기반 자동 생성 흐름으로 연결될 예정입니다.');
    console.log('[SellerShortsForm] ai video generate (dummy)');
  });

  // Live preview updates
  [titleEl, productEl, publicEl, priorityEl].forEach(function (el) {
    if (!el) return;
    el.addEventListener('input', updatePreview);
    el.addEventListener('change', updatePreview);
  });
  updatePreview();
  setThumbPreview(false);

  function validateRequired() {
    var ok = true;
    clearError('shortsTitleError');
    clearError('productNoError');
    clearError('isPublicError');
    clearError('videoError');
    clearError('formError');

    var title = titleEl ? titleEl.value.trim() : '';
    var product = productEl ? productEl.value : '';
    var pub = publicEl ? publicEl.value : '';

    if (!title) { showError('shortsTitleError', '쇼츠 제목을 입력해 주세요.'); ok = false; }
    if (!product) { showError('productNoError', '연결 상품을 선택해 주세요.'); ok = false; }
    if (!pub) { showError('isPublicError', '공개 상태를 선택해 주세요.'); ok = false; }
    if (!videoFile) { showError('videoError', '영상 파일을 등록해 주세요.'); ok = false; }

    if (!ok) showError('formError', '필수 항목을 확인해 주세요.');
    return ok;
  }

  var tempBtn = $('tempSaveBtn');
  if (tempBtn) {
    tempBtn.addEventListener('click', function () {
      console.log('[SellerShortsForm] temp save (dummy)', {
        shortsTitle: titleEl ? titleEl.value : '',
        productNo: productEl ? productEl.value : '',
        isPublic: publicEl ? publicEl.value : '',
        priority: priorityEl ? priorityEl.value : '',
        videoFile: videoFile,
        thumbFile: thumbFile,
      });
      alert('임시 저장(더미) — 실제 저장은 아직 연동되지 않았어요.');
    });
  }

  form.addEventListener('submit', function (e) {
    e.preventDefault();
    if (!validateRequired()) return;

    console.log('[SellerShortsForm] submit (dummy)', {
      shortsTitle: titleEl ? titleEl.value : '',
      productNo: productEl ? productEl.value : '',
      isPublic: publicEl ? publicEl.value : '',
      priority: priorityEl ? priorityEl.value : '',
      videoFile: videoFile,
      thumbFile: thumbFile,
    });
    alert('등록하기(더미) — 콘솔에 입력값 요약을 남겼어요.');
  });

  // Clear errors on input
  if (titleEl) titleEl.addEventListener('input', function () { clearError('shortsTitleError'); clearError('formError'); });
  if (productEl) productEl.addEventListener('change', function () { clearError('productNoError'); clearError('formError'); });
  if (publicEl) publicEl.addEventListener('change', function () { clearError('isPublicError'); clearError('formError'); });
})();

