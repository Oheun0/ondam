/* global document, console, alert, window */
document.addEventListener("DOMContentLoaded", function () {
  var ctx = document.body.getAttribute("data-context-path") || "";
  var urlMap = {
    'orders-today': ctx + '/seller/order?action=list&filter=today',
    'ship-ready':   ctx + '/seller/order?action=list&status=ready',
    'inquiries':    ctx + '/seller/inquiry?action=list',
    'reviews-new':  ctx + '/seller/review?action=list',
    'new-product':  ctx + '/seller/product?action=writeForm',
    'new-shorts':   ctx + '/seller/shorts?action=form',
    'open-orders':  ctx + '/seller/order?action=list',
    'new-coupon':   ctx + '/seller/coupon?action=form',
    'open-settlement': ctx + '/preview?page=seller/settlement/list' 
  };

  // 이벤트 위임을 통한 클릭 이벤트 처리
  document.body.addEventListener('click', function (e) {
    var el = e.target.closest('[data-action]');
    if (!el) return;

    var action = el.getAttribute('data-action');
    var targetUrl = urlMap[action];

    if (targetUrl) {
      window.location.href = targetUrl;
    } else {
      console.log('[SellerDashboard] No URL mapped for:', action);
      alert('해당 기능은 아직 준비 중이에요.');
    }
  });
});