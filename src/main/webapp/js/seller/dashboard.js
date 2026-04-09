/* global document, console, alert */
(function () {
  function onAction(action) {
    // 더미 동작만: 실제 화면 이동/연동은 추후 연결
    var map = {
      'orders-today': '오늘 주문 목록(더미)',
      'ship-ready': '배송 준비 목록(더미)',
      'inquiries': '문의 관리(더미)',
      'reviews-new': '리뷰 관리(더미)',
      'new-product': '새 상품 등록(더미)',
      'new-shorts': '새 쇼츠 등록(더미)',
      'open-orders': '주문/배송 관리(더미)',
      'new-coupon': '쿠폰 등록(더미)',
    };

    var label = map[action] || ('action=' + action);
    console.log('[SellerDashboard]', label);
    alert(label + ' 기능은 아직 준비 중이에요.');
  }

  document.addEventListener('click', function (e) {
    var target = e.target;
    if (!target) return;

    var el = target.closest('[data-action]');
    if (!el) return;

    var action = el.getAttribute('data-action');
    if (!action) return;
    onAction(action);
  });
})();

