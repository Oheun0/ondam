/* global document, alert, console, window */
(function () {
  function $(id) { return document.getElementById(id); }

  function getValue(id) {
    var el = $(id);
    return el ? el.value : '';
  }

  function applyFilters() {
    var payload = {
      query: getValue('shortsQuery'),
      public: getValue('shortsPublic'),
      period: getValue('shortsPeriod'),
      product: getValue('shortsProduct'),
    };
    console.log('[SellerShortsList] filter/search (dummy)', payload);
    alert('검색/필터는 더미 동작입니다.\n\n' +
      '검색어: ' + payload.query + '\n' +
      '공개 상태: ' + payload.public + '\n' +
      '기간: ' + payload.period + '\n' +
      '연결 상품: ' + payload.product
    );
  }

  var applyBtn = $('shortsApplyBtn');
  if (applyBtn) applyBtn.addEventListener('click', applyFilters);

  var queryEl = $('shortsQuery');
  if (queryEl) {
    queryEl.addEventListener('keydown', function (e) {
      if (e.key === 'Enter') {
        e.preventDefault();
        applyFilters();
      }
    });
  }

  var newBtn = $('sellerNewShortsBtn');
  if (newBtn) {
    newBtn.addEventListener('click', function () {
      console.log('[SellerShortsList] go to shorts form');
      window.location.href = (document.body.getAttribute('data-context-path') || '') + '/preview?page=seller/shorts/form';
    });
  }

  function togglePublic(card) {
    var isPublic = card.getAttribute('data-public') === 'true';
    var next = !isPublic;
    card.setAttribute('data-public', next ? 'true' : 'false');

    var badge = card.querySelector('.seller-shorts-badge');
    var toggleBtn = card.querySelector('[data-action="toggle"]');

    if (badge) {
      badge.classList.toggle('seller-shorts-badge--public', next);
      badge.classList.toggle('seller-shorts-badge--private', !next);
      badge.textContent = next ? '공개' : '비공개';
    }
    if (toggleBtn) {
      toggleBtn.textContent = next ? '비공개 전환' : '공개 전환';
    }
    alert('공개 상태가 변경되었습니다. (더미)\n\n' + (next ? '공개' : '비공개'));
    console.log('[SellerShortsList] toggle public (dummy)', {
      shortsNo: card.getAttribute('data-shorts-no'),
      public: next,
    });
  }

  document.addEventListener('click', function (e) {
    var t = e.target;
    if (!t) return;

    var pageBtn = t.closest('.seller-shorts-page-btn');
    if (pageBtn) {
      var page = pageBtn.getAttribute('data-page');
      console.log('[SellerShortsList] pagination (dummy)', page);
      alert('페이지네이션은 더미 동작입니다. (선택: ' + page + ')');
      return;
    }

    var actionEl = t.closest('[data-action]');
    if (!actionEl) return;

    var action = actionEl.getAttribute('data-action');
    var card = actionEl.closest('.seller-shorts-card');
    var shortsNo = card ? card.getAttribute('data-shorts-no') : '(unknown)';
    var productNo = card ? card.getAttribute('data-product') : '(unknown)';

    if (action === 'preview') {
      alert('미리보기는 더미 동작입니다.\n\n쇼츠: ' + shortsNo);
      return;
    }
    if (action === 'edit') {
      window.location.href = (document.body.getAttribute('data-context-path') || '') + '/preview?page=seller/shorts/form&shortsNo=' + encodeURIComponent(shortsNo);
      return;
    }
    if (action === 'toggle' && card) {
      togglePublic(card);
      return;
    }
    if (action === 'product') {
      alert('연결 상품 보기(더미)\n\n상품: ' + productNo);
      console.log('[SellerShortsList] open product (dummy)', productNo);
      return;
    }
    if (action === 'new-shorts') {
      window.location.href = (document.body.getAttribute('data-context-path') || '') + '/preview?page=seller/shorts/form';
      return;
    }
  });
})();

