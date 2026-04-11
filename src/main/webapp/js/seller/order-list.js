/* global document, alert, console, window */
(function () {
  function setActiveTab(tab) {
    var tabs = document.querySelectorAll('.seller-order-tab');
    tabs.forEach(function (t) {
      t.classList.remove('active');
      t.removeAttribute('aria-current');
    });
    tab.classList.add('active');
    tab.setAttribute('aria-current', 'true');
  }

  function filterByStatus(status) {
    var cards = document.querySelectorAll('.seller-order-card');
    cards.forEach(function (c) {
      var s = c.getAttribute('data-status');
      var show = (status === 'all') || (s === status);
      c.classList.toggle('hidden', !show);
    });
    console.log('[SellerOrderList] tab filter (dummy)', status);
  }

  document.addEventListener('click', function (e) {
    var t = e.target;
    if (!t) return;

    var tab = t.closest('.seller-order-tab');
    if (tab) {
      var status = tab.getAttribute('data-status') || 'all';
      setActiveTab(tab);
      filterByStatus(status);
      return;
    }

    var pageBtn = t.closest('.seller-order-page-btn');
    if (pageBtn) {
      var p = pageBtn.getAttribute('data-page');
      console.log('[SellerOrderList] pagination (dummy)', p);
      alert('페이지네이션은 더미 동작입니다. (선택: ' + p + ')');
      return;
    }

    var btn = t.closest('[data-action]');
    if (!btn) return;
    var action = btn.getAttribute('data-action');
    var card = btn.closest('.seller-order-card');
    var orderNo = card ? card.getAttribute('data-order-no') : '(unknown)';

    console.log('[SellerOrderList] action (dummy)', action, orderNo);
	if (action === 'detail') {
	  window.location.href = (document.body.getAttribute('data-context-path') || '') + '/seller/order?action=detail&orderNo=' + encodeURIComponent(orderNo);
	  return;
	}
    if (action === 'shipStart') {
      alert('배송 시작 처리(더미)\n\n주문번호: ' + orderNo);
      return;
    }
    if (action === 'shipDone') {
      alert('배송 완료 처리(더미)\n\n주문번호: ' + orderNo);
      return;
    }
    if (action === 'ready') {
      alert('준비 처리(더미)\n\n주문번호: ' + orderNo);
      return;
    }
  });

  // 초기: 전체 표시
  filterByStatus('all');
})();

