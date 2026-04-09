/* global document, alert, console, window */
(function () {
  function $(id) { return document.getElementById(id); }
  function show(el) { if (el) el.classList.remove('hidden'); }
  function hide(el) { if (el) el.classList.add('hidden'); }
  function setText(el, text) { if (el) el.textContent = text; }

  function applyFilters() {
    var payload = {
      product: $('reviewProduct') ? $('reviewProduct').value : '',
      rating: $('reviewRating') ? $('reviewRating').value : '',
      period: $('reviewPeriod') ? $('reviewPeriod').value : '',
      query: $('reviewQuery') ? $('reviewQuery').value : '',
    };
    console.log('[SellerReview] filter/search (dummy)', payload);
    alert('필터/검색은 더미 동작입니다.\n\n' +
      '상품: ' + payload.product + '\n' +
      '평점: ' + payload.rating + '\n' +
      '기간: ' + payload.period + '\n' +
      '검색어: ' + payload.query
    );
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

  // Panel
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

  function fillPanelFromCard(card) {
    var author = card.getAttribute('data-author') || '-';
    var date = card.getAttribute('data-date') || '-';
    var orderNo = card.getAttribute('data-order-no') || '-';
    var option = card.getAttribute('data-option') || '-';
    var content = card.getAttribute('data-content') || '-';
    var rating = card.getAttribute('data-rating') || '-';
    var answered = card.getAttribute('data-answered') === 'true';

    // product name from visible card text
    var productEl = card.querySelector('.seller-review-product');
    var product = productEl ? productEl.textContent.trim() : '-';

    setText($('reviewPanelSub'), author + ' · ' + date);
    setText($('pdAuthor'), author);
    setText($('pdDate'), date);
    setText($('pdProduct'), product);
    setText($('pdOption'), option);
    setText($('pdOrderNo'), orderNo);
    setText($('pdRating'), rating + '.0 / 5');
    setText($('pdAnswered'), answered ? '답변 완료' : '미답변');
    setText($('pdContent'), content);

    // images
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

    // reply placeholder
    var replyText = $('replyText');
    if (replyText) {
      replyText.value = answered ? '소중한 후기 감사합니다. (더미 예시 답변)' : '';
    }
  }

  function markAnswered(card) {
    if (!card) return;
    card.setAttribute('data-answered', 'true');
    var badge = card.querySelector('.seller-review-badge');
    if (badge) {
      badge.classList.remove('seller-review-badge--todo');
      badge.classList.add('seller-review-badge--done');
      badge.textContent = '답변 완료';
    }
  }

  document.addEventListener('click', function (e) {
    var t = e.target;
    if (!t) return;

    var pageBtn = t.closest('.seller-review-page-btn');
    if (pageBtn) {
      var p = pageBtn.getAttribute('data-page');
      alert('페이지네이션은 더미 동작입니다. (선택: ' + p + ')');
      return;
    }

    var btn = t.closest('[data-action]');
    if (!btn) return;
    var action = btn.getAttribute('data-action');
    var card = btn.closest('.seller-review-card');
    if (!card) return;

    if (action === 'detail' || action === 'reply') {
      fillPanelFromCard(card);
      openPanel();
      return;
    }
  });

  // Reply actions
  var cancelBtn = $('replyCancelBtn');
  if (cancelBtn) cancelBtn.addEventListener('click', function () {
    var replyText = $('replyText');
    if (replyText) replyText.value = '';
    closePanel();
  });

  document.addEventListener('click', function (e) {
    var t = e.target;
    if (!t) return;
    var tpl = t.closest('.seller-review-template');
    if (!tpl) return;
    var msg = tpl.getAttribute('data-template') || '';
    var replyText = $('replyText');
    if (!replyText) return;
    replyText.value = msg;
    replyText.focus();
  });

  var submitBtn = $('replySubmitBtn');
  if (submitBtn) {
    submitBtn.addEventListener('click', function () {
      var replyText = $('replyText');
      var text = replyText ? replyText.value.trim() : '';
      if (!text) {
        alert('답변 내용을 입력해 주세요.');
        return;
      }
      console.log('[SellerReview] reply submit (dummy)', text);
      alert('답변이 등록되었습니다. (더미)');

      // 현재 열린 패널의 내용과 매칭되는 카드 찾기(간단 더미: author+date로)
      var sub = $('reviewPanelSub') ? $('reviewPanelSub').textContent : '';
      var parts = sub.split(' · ');
      var author = parts[0] || '';
      var date = parts[1] || '';
      var cards = document.querySelectorAll('.seller-review-card');
      cards.forEach(function (c) {
        if ((c.getAttribute('data-author') || '') === author && (c.getAttribute('data-date') || '') === date) {
          markAnswered(c);
        }
      });

      closePanel();
    });
  }
})();

