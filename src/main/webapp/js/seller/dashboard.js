/* global document, console, alert */
(function () {
  function onAction(action) {
    var ctx = document.body.getAttribute("data-context-path") || "";
	var urlMap = {
	      'orders-today': ctx + '/seller/order?action=list&filter=today',
	      'ship-ready':   ctx + '/seller/order?action=list&status=ready',
	      'inquiries':    ctx + '/seller/inquiry?action=list',
	      'reviews-new':  ctx + '/seller/review?action=list',
	      'new-product':  ctx + '/seller/product?action=form',
	      'new-shorts':   ctx + '/seller/shorts?action=form',
	      'open-orders':  ctx + '/seller/order?action=list',
	      'new-coupon':   ctx + '/seller/coupon?action=form'
	    };

		var targetUrl = urlMap[action];

		    if (targetUrl) {
		      window.location.href = targetUrl; // 진짜 페이지로 이동!
		    } else {
		      console.log('[SellerDashboard] No URL mapped for:', action);
		      alert('해당 기능은 아직 준비 중이에요.');
		    }
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

