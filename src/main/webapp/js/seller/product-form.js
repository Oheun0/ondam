/* global document, alert, console */
(function () {
  function $(id) { return document.getElementById(id); }
  function show(el) { if (el) el.classList.remove('hidden'); }
  function hide(el) { if (el) el.classList.add('hidden'); }
  function setText(el, text) { if (el) el.textContent = text; }

  function showError(id, msg) {
    var el = $(id);
    if (!el) return;
    setText(el, msg);
    show(el);
  }
  function clearError(id) {
    var el = $(id);
    if (!el) return;
    setText(el, '');
    hide(el);
  }

  function onlyNumberText(s) {
    return String(s || '').replace(/[^\d]/g, '');
  }
  function formatWon(n) {
    var num = Number(n || 0);
    if (!isFinite(num) || num <= 0) return '-';
    return num.toLocaleString('ko-KR') + '원';
  }

  var form = $('sellerProductForm');
  if (!form) return;

  var priceEl = $('price');
  var rateEl = $('discountRate');
  var saleEl = $('salePrice');
  var beforeEl = $('priceBefore');
  var afterEl = $('priceAfter');

  function updatePricePreview() {
    var price = Number(onlyNumberText(priceEl && priceEl.value));
    var rate = Number(onlyNumberText(rateEl && rateEl.value));
    var sale = Number(onlyNumberText(saleEl && saleEl.value));

    if (price > 0 && rate > 0 && rate <= 100) {
      var calc = Math.max(0, Math.round(price * (100 - rate) / 100));
      if (saleEl) saleEl.value = String(calc);
      sale = calc;
    }

    if (beforeEl) beforeEl.textContent = price > 0 ? formatWon(price) : '-';
    if (afterEl) afterEl.textContent = sale > 0 ? formatWon(sale) : '-';
  }

  if (priceEl) priceEl.addEventListener('input', updatePricePreview);
  if (rateEl) rateEl.addEventListener('input', updatePricePreview);
  if (saleEl) saleEl.addEventListener('input', updatePricePreview);
  updatePricePreview();

  // 이미지 더미
  var thumbAddBtn = $('thumbAddBtn');
  var thumbRemoveBtn = $('thumbRemoveBtn');
  var thumbPreview = $('thumbPreview');
  var thumbPreviewImg = $('thumbPreviewImg');
  var thumbBox = $('thumbBox');

  function setThumb(on) {
    if (on) {
      if (thumbPreviewImg) {
        thumbPreviewImg.src = (document.body.getAttribute('data-context-path') || '') + '/images/category/type-top-knit.jpg';
      }
      show(thumbPreview);
      clearError('thumbError');
    } else {
      if (thumbPreviewImg) thumbPreviewImg.removeAttribute('src');
      hide(thumbPreview);
    }
  }

  function addDetailThumb() {
    var wrap = $('detailThumbs');
    if (!wrap) return;
    var idx = wrap.children.length + 1;

    var row = document.createElement('div');
    row.className = 'seller-product-detail-thumb';
    row.setAttribute('data-idx', String(idx));
    row.innerHTML =
      '<img alt="상세 이미지 미리보기" src="' + (document.body.getAttribute('data-context-path') || '') + '/images/category/type-top-knit.jpg">' +
      '<div class="seller-product-detail-thumb-meta">' +
      '  <div class="seller-product-detail-thumb-title">상세 이미지 ' + idx + '</div>' +
      '  <div class="seller-product-detail-thumb-sub">정렬/삭제는 더미 동작</div>' +
      '</div>' +
      '<div class="seller-product-detail-thumb-actions">' +
      '  <button type="button" class="seller-mini-btn" data-detail-action="up">위</button>' +
      '  <button type="button" class="seller-mini-btn" data-detail-action="down">아래</button>' +
      '  <button type="button" class="seller-mini-btn seller-mini-btn--warn" data-detail-action="remove">삭제</button>' +
      '</div>';
    wrap.appendChild(row);
  }

  var detailAddBtn = $('detailAddBtn');
  var detailBox = $('detailBox');
  if (detailAddBtn) detailAddBtn.addEventListener('click', function (e) { e.preventDefault(); addDetailThumb(); });
  if (detailBox) detailBox.addEventListener('click', function () { addDetailThumb(); });

  if (thumbAddBtn) thumbAddBtn.addEventListener('click', function (e) { e.preventDefault(); setThumb(true); });
  if (thumbBox) thumbBox.addEventListener('click', function () { setThumb(true); });
  if (thumbRemoveBtn) thumbRemoveBtn.addEventListener('click', function (e) { e.preventDefault(); setThumb(false); });

  document.addEventListener('click', function (e) {
    var t = e.target;
    if (!t) return;
    var btn = t.closest('[data-detail-action]');
    if (!btn) return;
    var action = btn.getAttribute('data-detail-action');
    var row = btn.closest('.seller-product-detail-thumb');
    if (!row) return;
    var wrap = $('detailThumbs');
    if (!wrap) return;

    if (action === 'remove') {
      wrap.removeChild(row);
      return;
    }
    if (action === 'up' && row.previousElementSibling) {
      wrap.insertBefore(row, row.previousElementSibling);
      return;
    }
    if (action === 'down' && row.nextElementSibling) {
      wrap.insertBefore(row.nextElementSibling, row);
    }
  });

  // 태그 칩 선택
  document.addEventListener('click', function (e) {
    var t = e.target;
    if (!t) return;
    var chip = t.closest('.seller-product-chip');
    if (!chip) return;
    chip.classList.toggle('is-selected');
  });

  // 옵션 조합 생성
  var buildBtn = $('buildOptionsBtn');
  var optionBody = $('optionBody');

  function parseCommaList(v) {
    return String(v || '')
      .split(',')
      .map(function (s) { return s.trim(); })
      .filter(Boolean);
  }

  function getSelectedColors() {
    var nodes = document.querySelectorAll('input[name="productColor"]:checked');
    var list = [];
    nodes.forEach(function (n) {
      if (n && n.value) list.push(n.value);
    });
    return list;
  }

  function getSelectedSizes() {
    var nodes = document.querySelectorAll('input[name="productSize"]:checked');
    var list = [];
    nodes.forEach(function (n) {
      if (n && n.value) list.push(n.value);
    });
    return list;
  }

  function renderOptionRows(rows) {
    if (!optionBody) return;
    optionBody.innerHTML = '';
    if (!rows.length) {
      var tr = document.createElement('tr');
      tr.className = 'seller-product-option-empty';
      tr.innerHTML = '<td colspan="5">옵션 조합을 생성하면 여기에 표시돼요.</td>';
      optionBody.appendChild(tr);
      return;
    }
    rows.forEach(function (r, i) {
      var tr2 = document.createElement('tr');
      tr2.setAttribute('data-row', String(i));
      tr2.innerHTML =
        '<td>' + r.color + '</td>' +
        '<td>' + r.size + '</td>' +
        '<td><input class="seller-product-option-input" type="text" inputmode="numeric" value="' + r.stock + '" data-opt="stock"></td>' +
        '<td><label class="seller-product-check"><input type="checkbox" data-opt="soldout"> <span>품절</span></label></td>' +
        '<td><button type="button" class="seller-mini-btn seller-mini-btn--warn" data-opt-action="remove">삭제</button></td>';
      optionBody.appendChild(tr2);
    });
  }

  function buildOptions() {
    clearError('optionError');

    var colors = getSelectedColors();
    var sizes = getSelectedSizes();

    if (!colors.length || !sizes.length) {
      showError('optionError', '옵션 또는 재고 정보를 입력해 주세요.');
      renderOptionRows([]);
      return;
    }

    var rows = [];
    colors.forEach(function (c) {
      sizes.forEach(function (s) {
        rows.push({ color: c, size: s, stock: 10 });
      });
    });
    renderOptionRows(rows);
  }

  if (buildBtn) buildBtn.addEventListener('click', function () { buildOptions(); });
  document.addEventListener('change', function (e) {
    var t = e.target;
    if (!t) return;
    if (t && t.name === 'productColor') {
      clearError('optionError');
      clearError('formError');
    }
    if (t && t.name === 'productSize') {
      clearError('optionError');
      clearError('formError');
    }
  });

  document.addEventListener('click', function (e) {
    var t = e.target;
    if (!t) return;
    var btn = t.closest('[data-opt-action="remove"]');
    if (!btn) return;
    var tr = btn.closest('tr');
    if (!tr || !optionBody) return;
    optionBody.removeChild(tr);
  });

  // 필수 검증 + 제출/임시저장
  var tempBtn = $('tempSaveBtn');
  var submitBtn = $('submitBtn');

  function hasThumb() {
    return thumbPreview && !thumbPreview.classList.contains('hidden') && thumbPreviewImg && !!thumbPreviewImg.getAttribute('src');
  }
  function optionCount() {
    if (!optionBody) return 0;
    var trs = optionBody.querySelectorAll('tr');
    if (!trs || !trs.length) return 0;
    // empty row only?
    if (trs.length === 1 && trs[0].classList.contains('seller-product-option-empty')) return 0;
    return trs.length;
  }

  function validateRequired() {
    var ok = true;
    clearError('brandNameError');
    clearError('productNameError');
    clearError('categoryError');
    clearError('priceError');
    clearError('thumbError');
    clearError('optionError');
    clearError('formError');

    var brand = ($('brandName') && $('brandName').value) ? $('brandName').value.trim() : '';
    var name = ($('productName') && $('productName').value) ? $('productName').value.trim() : '';
    var situationCat = ($('situationCategory') && $('situationCategory').value) ? $('situationCategory').value : '';
    var typeCat = ($('typeCategory') && $('typeCategory').value) ? $('typeCategory').value : '';
    var price = Number(onlyNumberText(priceEl && priceEl.value));

    if (!brand) { showError('brandNameError', '브랜드명을 입력해 주세요.'); ok = false; }
    if (!name) { showError('productNameError', '상품명을 입력해 주세요.'); ok = false; }
    if (!situationCat || !typeCat) { showError('categoryError', '카테고리를 선택해 주세요.'); ok = false; }
    if (!price || price <= 0) { showError('priceError', '판매가를 입력해 주세요.'); ok = false; }
    if (!hasThumb()) { showError('thumbError', '대표 이미지를 등록해 주세요.'); ok = false; }
    if (optionCount() === 0) { showError('optionError', '옵션 또는 재고 정보를 입력해 주세요.'); ok = false; }

    if (!ok) {
      showError('formError', '필수 항목을 확인해 주세요.');
    }
    return ok;
  }

  function getSelectedSeason() {
    var el = document.querySelector('input[name="productSeason"]:checked');
    return el ? el.value : '';
  }

  function getSelectedFeatures() {
    var nodes = document.querySelectorAll('input[name="clothesFeature"]:checked');
    var list = [];
    nodes.forEach(function (n) {
      if (n && n.value) list.push(n.value);
    });
    return list;
  }

  // 필터 태그: 옷 특징 최대 3개 제한
  var featureHint = $('featureLimitHint');
  var MAX_FEATURES = 3;

  function updateFeatureHint() {
    if (!featureHint) return;
    var count = getSelectedFeatures().length;
    featureHint.textContent = count + '/' + MAX_FEATURES + ' 선택';
  }

  function enforceFeatureLimit(changedEl) {
    var list = getSelectedFeatures();
    if (list.length <= MAX_FEATURES) return true;
    // 초과 시 방금 체크한 것을 되돌림
    if (changedEl && changedEl.checked) changedEl.checked = false;
    alert('옷 특징은 최대 ' + MAX_FEATURES + '개까지 선택할 수 있어요.');
    return false;
  }

  document.addEventListener('change', function (e) {
    var t = e.target;
    if (!t) return;
    if (t && t.name === 'clothesFeature') {
      enforceFeatureLimit(t);
      updateFeatureHint();
    }
  });

  updateFeatureHint();

  if (tempBtn) {
    tempBtn.addEventListener('click', function () {
      console.log('[SellerProductForm] temp save (dummy)', {
        season: getSelectedSeason(),
        features: getSelectedFeatures(),
      });
      alert('임시 저장(더미) — 실제 저장은 아직 연동되지 않았어요.');
    });
  }

  form.addEventListener('submit', function (e) {
    e.preventDefault();
    if (!validateRequired()) return;

    console.log('[SellerProductForm] submit (dummy)', {
      brandName: ($('brandName') && $('brandName').value) || '',
      productName: ($('productName') && $('productName').value) || '',
      situationCategory: ($('situationCategory') && $('situationCategory').value) || '',
      typeCategory: ($('typeCategory') && $('typeCategory').value) || '',
      saleStatus: ($('saleStatus') && $('saleStatus').value) || '',
      price: ($('price') && $('price').value) || '',
      discountRate: ($('discountRate') && $('discountRate').value) || '',
      salePrice: ($('salePrice') && $('salePrice').value) || '',
      season: getSelectedSeason(),
      features: getSelectedFeatures(),
      optionRows: optionCount(),
    });

    alert('등록하기(더미) — 콘솔에 입력값 요약을 남겼어요.');
  });

  // 입력 시 에러 제거
  ['brandName', 'productName', 'situationCategory', 'typeCategory', 'price', 'discountRate', 'salePrice'].forEach(function (id) {
    var el = $(id);
    if (!el) return;
    el.addEventListener('input', function () {
      if (id === 'brandName') clearError('brandNameError');
      if (id === 'productName') clearError('productNameError');
      if (id === 'situationCategory' || id === 'typeCategory') clearError('categoryError');
      if (id === 'price') clearError('priceError');
      clearError('formError');
    });
    el.addEventListener('change', function () {
      if (id === 'situationCategory' || id === 'typeCategory') clearError('categoryError');
      clearError('formError');
    });
  });
})();

