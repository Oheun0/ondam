/* global document, alert, console, window */
(function () {
  function $(id) { return document.getElementById(id); }
  function show(el) { if (el) el.classList.remove('hidden'); }
  function hide(el) { if (el) el.classList.add('hidden'); }
  function setText(el, text) { if (el) el.textContent = text; }

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
	var reviewId = card.getAttribute('data-review-id'); 
	    $('reviewPanel').setAttribute('data-current-review-id', reviewId);
		
    var author = card.getAttribute('data-author') || '-';
    var date = card.getAttribute('data-date') || '-';
    var orderNo = card.getAttribute('data-order-no') || '-';
    var option = card.getAttribute('data-option') || '-';
    var content = card.getAttribute('data-content') || '-';
    var rating = card.getAttribute('data-rating') || '-';
	var reply = card.getAttribute('data-reply') || '';
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
	      // 답변이 있으면 그 내용을 보여주고, 없으면 비워둡니다.
	      replyText.value = answered && reply !== 'null' ? reply : ''; 
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
        if (!p) return;
        var product = $('reviewProduct') ? $('reviewProduct').value : '';
        var rating = $('reviewRating') ? $('reviewRating').value : '';
        var period = $('reviewPeriod') ? $('reviewPeriod').value : '';
        var query = $('reviewQuery') ? $('reviewQuery').value : '';
        var contextPath = document.body.getAttribute('data-context-path') || '';

        location.href = contextPath + "/seller/review?action=list" 
                      + "&product=" + encodeURIComponent(product) 
                      + "&rating=" + encodeURIComponent(rating) 
                      + "&period=" + encodeURIComponent(period) 
                      + "&query=" + encodeURIComponent(query)
                      + "&page=" + p; 
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
        
        var reviewId = $('reviewPanel').getAttribute('data-current-review-id');
        var contextPath = document.body.getAttribute('data-context-path') || '';

        if (confirm('이 내용으로 답변을 등록(수정)하시겠습니까?')) {
            // 💡 JS로 몰래 POST 폼을 만들어서 컨트롤러로 쏩니다!
            var form = document.createElement('form');
            form.method = 'POST';
            form.action = contextPath + '/seller/review?action=reply';

            var noInput = document.createElement('input');
            noInput.type = 'hidden';
            noInput.name = 'reviewNo';
            noInput.value = reviewId;

            var contentInput = document.createElement('input');
            contentInput.type = 'hidden';
            contentInput.name = 'replyContent';
            contentInput.value = text;

            form.appendChild(noInput);
            form.appendChild(contentInput);
            document.body.appendChild(form);
            form.submit(); // 컨트롤러의 case "reply": 로 날아갑니다!
        }
      });
    }
})();

