/* global document, alert, console, window */
(function () {
  "use strict";

  function $(id) { return document.getElementById(id); }
  function show(el) { if (el) el.classList.remove('hidden'); }
  function hide(el) { if (el) el.classList.add('hidden'); }
  function setText(el, text) { if (el) el.textContent = text; }

  // 1. 필터 적용 로직
  function applyFilters() {
    var product = $('reviewProduct') ? $('reviewProduct').value : '';
    var rating = $('reviewRating') ? $('reviewRating').value : '';
    var period = $('reviewPeriod') ? $('reviewPeriod').value : '';
    var query = $('reviewQuery') ? $('reviewQuery').value : '';
    var contextPath = document.body.getAttribute('data-context-path') || '';

    location.href = contextPath + "/seller/review?action=list" 
                  + "&product=" + encodeURIComponent(product) 
                  + "&rating=" + encodeURIComponent(rating) 
                  + "&period=" + encodeURIComponent(period) 
                  + "&query=" + encodeURIComponent(query);
  }

  var applyBtn = $('reviewApplyBtn');
  if (applyBtn) applyBtn.addEventListener('click', applyFilters);

  var queryEl = $('reviewQuery');
  if (queryEl) {
    queryEl.addEventListener('keydown', function (e) {
      if (e.key === 'Enter') {
        e.preventDefault();
        applyFilters();
      }
    });
  }

  // 2. 패널 제어 로직 (상세보기/답변창)
  var panel = $('reviewPanel');
  var dim = $('reviewPanelDim');
  var closeBtn = $('reviewPanelClose');

  function openPanel() {
    if (!panel) return;
    panel.classList.remove('hidden');
    panel.setAttribute('aria-hidden', 'false');
    document.body.style.overflow = 'hidden';
  }

  function closePanel() {
    if (!panel) return;
    panel.classList.add('hidden');
    panel.setAttribute('aria-hidden', 'true');
    document.body.style.overflow = '';
  }

  if (dim) dim.addEventListener('click', closePanel);
  if (closeBtn) closeBtn.addEventListener('click', closePanel);
  document.addEventListener('keydown', function (e) {
    if (e.key === 'Escape') closePanel();
  });

  // 3. 카드 데이터를 패널에 채우는 함수 (핵심!)
  function fillPanelFromCard(card) {
    var reviewId = card.getAttribute('data-review-id'); 
    var author   = card.getAttribute('data-author') || '-';
    var date     = card.getAttribute('data-date') || '-';
    var orderNo  = card.getAttribute('data-order-no') || '-';
    var option   = card.getAttribute('data-option') || '-';
    var content  = card.getAttribute('data-content') || '-';
    var rating   = card.getAttribute('data-rating') || '-';
    var reply    = card.getAttribute('data-reply') || '';
    var answered = card.getAttribute('data-answered') === 'true';

    var productEl = card.querySelector('.seller-review-product');
    var product   = productEl ? productEl.textContent.trim() : '-';

    // 상세 텍스트 세팅
    $('reviewPanel').setAttribute('data-current-review-id', reviewId);
    setText($('reviewPanelSub'), author + ' · ' + date);
    setText($('pdAuthor'), author);
    setText($('pdDate'), date);
    setText($('pdProduct'), product);
    setText($('pdOption'), option);
    setText($('pdOrderNo'), orderNo);
    setText($('pdRating'), rating + '.0 / 5');
    setText($('pdAnswered'), answered ? '답변 완료' : '미답변');
    setText($('pdContent'), content);

    // 이미지 처리
    var pdImages = $('pdImages');
    if (pdImages) {
      pdImages.innerHTML = '';
      var imgs = card.querySelectorAll('.seller-review-images img');
      imgs.forEach(function (img) {
        var el = document.createElement('img');
        el.src = img.getAttribute('src');
        el.alt = '리뷰 이미지';
        pdImages.appendChild(el);
      });
    }

    // 💡 답변 수정 방지 및 UI 제어
    var replyText   = $('replyText');
    var submitBtn   = $('replySubmitBtn');
    var templateBox = document.querySelector('.seller-review-reply-templates');
    var hintText    = document.querySelector('.seller-review-panel__hint');

    if (answered) {
      if (replyText) {
        replyText.value = (reply && reply !== 'null') ? reply : ''; 
        replyText.readOnly = true;
        replyText.style.backgroundColor = "#f5f5f5";
        replyText.style.color = "#666";
      }
      hide(submitBtn);
      hide(templateBox);
      if (hintText) setText(hintText, "이미 답변이 완료된 리뷰는 수정할 수 없습니다.");
    } else {
      if (replyText) {
        replyText.value = '';
        replyText.readOnly = false;
        replyText.style.backgroundColor = "";
        replyText.style.color = "";
      }
      show(submitBtn);
      show(templateBox);
      if (hintText) setText(hintText, "짧고 친절하게 답변해 주세요.");
    }
  }

  // 4. 클릭 이벤트 리스너 (위임 방식)
  document.addEventListener('click', function (e) {
    var t = e.target;
    if (!t) return;

    // 페이지네이션 클릭
    var pageBtn = t.closest('.seller-review-page-btn');
    if (pageBtn) {
      var p = pageBtn.getAttribute('data-page');
      if (!p) return;
      var contextPath = document.body.getAttribute('data-context-path') || '';
      location.href = contextPath + "/seller/review?action=list&page=" + p; 
      return;
    }

    // 답변 달기/보기 클릭
    var btn = t.closest('[data-action]');
    if (!btn) return;
    var action = btn.getAttribute('data-action');
    var card = btn.closest('.seller-review-card');
    if (!card) return;

    if (action === 'reply' || action === 'detail') {
      fillPanelFromCard(card);
      openPanel();
    }
  });

  // 5. 답변 관련 액션
  var cancelBtn = $('replyCancelBtn');
  if (cancelBtn) {
    cancelBtn.addEventListener('click', function () {
      closePanel();
    });
  }

  // 템플릿 클릭 시 텍스트 채우기
  document.addEventListener('click', function (e) {
    var tpl = e.target.closest('.seller-review-template');
    if (!tpl) return;
    
    var replyText = $('replyText');
    if (replyText && !replyText.readOnly) {
      replyText.value = tpl.getAttribute('data-template') || '';
      replyText.focus();
    }
  });

  // 답변 제출
  var submitBtn = $('replySubmitBtn');
  if (submitBtn) {
    submitBtn.addEventListener('click', function () {
      var replyText = $('replyText');
      var text = replyText ? replyText.value.trim() : '';
      if (!text) {
        alert('답변 내용을 입력해 주세요.');
        return;
      }
      
      var reviewId = $('reviewPanel').getAttribute('data-current-review-id');
      var contextPath = document.body.getAttribute('data-context-path') || '';

      if (confirm('이 내용으로 답변을 등록하시겠습니까?')) {
        var form = document.createElement('form');
        form.method = 'POST';
        form.action = contextPath + '/seller/review?action=reply';

        var noInput = document.createElement('input');
        noInput.type = 'hidden'; noInput.name = 'reviewNo'; noInput.value = reviewId;
        var contentInput = document.createElement('input');
        contentInput.type = 'hidden'; contentInput.name = 'replyContent'; contentInput.value = text;

        form.appendChild(noInput);
        form.appendChild(contentInput);
        document.body.appendChild(form);
        form.submit();
      }
    });
  }
})();