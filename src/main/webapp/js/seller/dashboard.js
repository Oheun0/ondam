/* global document, console, alert */
document.addEventListener("DOMContentLoaded", function () {
  var ctx = document.body.getAttribute("data-context-path") || "";

  var urlMap = {
    'orders-today': ctx + '/seller/order?action=list',
    'ship-ready':   ctx + '/seller/order?action=list&status=ready',
    'inquiries':    ctx + '/seller/inquiry?action=list',
    'reviews-new':  ctx + '/seller/review?action=list',
    'new-product':  ctx + '/seller/product?action=writeForm',
    'new-shorts':   ctx + '/seller/shorts?action=form',
    'open-orders':  ctx + '/seller/order?action=list',
    'new-coupon':   ctx + '/seller/coupon?action=form'
  };

  document.body.addEventListener('click', function (e) {
    var el = e.target.closest('[data-action]');
    if (!el) return; // 없으면 무시

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