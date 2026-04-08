/* global document, console, alert */
(function () {
  function getValue(id) {
    var el = document.getElementById(id);
    return el ? el.value : '';
  }

  function logSearch(context) {
    var payload = {
      query: getValue('sellerProductQuery'),
      category: getValue('sellerProductCategory'),
      sale: getValue('sellerProductSale'),
      stock: getValue('sellerProductStock'),
      context: context || 'apply',
    };
    console.log('[SellerProductList] filter/search', payload);
    alert('검색/필터는 더미 동작입니다.\n\n' +
      '검색어: ' + payload.query + '\n' +
      '카테고리: ' + payload.category + '\n' +
      '판매 상태: ' + payload.sale + '\n' +
      '재고 상태: ' + payload.stock
    );
  }

  var applyBtn = document.getElementById('sellerProductApplyBtn');
  if (applyBtn) {
    applyBtn.addEventListener('click', function () {
      logSearch('apply');
    });
  }

  var newBtn = document.getElementById('sellerNewProductBtn');
  if (newBtn) {
    newBtn.addEventListener('click', function () {
      console.log('[SellerProductList] go to product form');
      window.location.href = (document.body.getAttribute('data-context-path') || '') + '/preview?page=seller/product/form';
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
      alert('페이지네이션은 더미 동작입니다. (선택: ' + page + ')');
      return;
    }

    var actionBtn = target.closest('[data-action]');
    if (!actionBtn) return;

    var action = actionBtn.getAttribute('data-action');
    if (!action) return;

    if (action === 'new-product') {
      window.location.href = (document.body.getAttribute('data-context-path') || '') + '/preview?page=seller/product/form';
      return;
    }

    var row = actionBtn.closest('tr[data-product-id]');
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
  if (queryEl) {
    queryEl.addEventListener('keydown', function (e) {
      if (e.key === 'Enter') {
        e.preventDefault();
        logSearch('enter');
      }
    });
  }
})();

