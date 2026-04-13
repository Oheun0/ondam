/* global document, console, window */
(function () {
  var filterForm = document.getElementById('sellerProductFilterForm');

  var applyBtn = document.getElementById('sellerProductApplyBtn');
  if (applyBtn && filterForm) {
    applyBtn.addEventListener('click', function () {
      filterForm.requestSubmit();
    });
  }

  var newBtn = document.getElementById('sellerNewProductBtn');
  if (newBtn) {
    newBtn.addEventListener('click', function () {
      console.log('[SellerProductList] go to product form');
      window.location.href = (document.body.getAttribute('data-context-path') || '') + '/seller/product/form';
    });
  }

  document.addEventListener('change', function (e) {
    var t = e.target;
    if (!t) return;
    if (
      t.id === 'sellerProductCategory' ||
      t.id === 'sellerProductSale' ||
      t.id === 'sellerProductStock'
    ) {
      console.log('[SellerProductList] filter changed', t.id, t.value);
    }
  });

  document.addEventListener('click', function (e) {
    var target = e.target;
    if (!target) return;

    var pageBtn = target.closest('.seller-page-btn');
    if (pageBtn) {
      var page = pageBtn.getAttribute('data-page');
      console.log('[SellerProductList] pagination', page);
      return;
    }

    var actionBtn = target.closest('[data-action]');
    if (!actionBtn) return;

    var action = actionBtn.getAttribute('data-action');
    if (!action) return;
    var row = actionBtn.closest('tr[data-product-id]');

    if (action === 'new-product') {
      window.location.href = (document.body.getAttribute('data-context-path') || '') + '/seller/product/form';
      return;
    }
    if (action === 'edit') {
      var editProductNo = actionBtn.getAttribute('data-product-no');
      if (!editProductNo && row) editProductNo = row.getAttribute('data-product-no');
      if (!editProductNo) return;
      window.location.href = (document.body.getAttribute('data-context-path') || '') + '/seller/product/edit?productNo=' + encodeURIComponent(editProductNo);
      return;
    }

    var productId = row ? row.getAttribute('data-product-id') : '(unknown)';

    console.log('[SellerProductList] action', action, 'productId=', productId);

    var msg = {
      edit: '수정',
      hide: '숨김',
      soldout: '품절 처리',
      reopen: '재등록',
      show: '판매중 전환',
    }[action] || action;

    alert('더미 동작입니다.\n\n상품: ' + productId + '\n액션: ' + msg);
  });

  var queryEl = document.getElementById('sellerProductQuery');
  if (queryEl && filterForm) {
    queryEl.addEventListener('keydown', function (e) {
      if (e.key === 'Enter') {
        e.preventDefault();
        filterForm.requestSubmit();
      }
    });
  }
})();

