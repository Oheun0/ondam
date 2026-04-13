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
  var generateEasyDescBtn = $('generateEasyDescBtn');
  var editSituationNo = $('editSituationNo');
  var editCategoryNo = $('editCategoryNo');
  if (editSituationNo && $('situationCategory')) {
    $('situationCategory').value = editSituationNo.value;
  }
  if (editCategoryNo && $('typeCategory')) {
    $('typeCategory').value = editCategoryNo.value;
  }

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

  // 이미지 업로드
  var thumbAddBtn = $('thumbAddBtn');
  var thumbRemoveBtn = $('thumbRemoveBtn');
  var thumbPreview = $('thumbPreview');
  var thumbPreviewImg = $('thumbPreviewImg');
  var thumbBox = $('thumbBox');
  var thumbImageInput = $('thumbImageInput');
  var detailImageInput = $('detailImageInput');
  var detailThumbs = $('detailThumbs');
  var detailFiles = [];

  function setThumb(on) {
    if (on) {
      if (thumbPreviewImg && thumbImageInput && thumbImageInput.files && thumbImageInput.files[0]) {
        thumbPreviewImg.src = URL.createObjectURL(thumbImageInput.files[0]);
      }
      show(thumbPreview);
      clearError('thumbError');
    } else {
      if (thumbImageInput) thumbImageInput.value = '';
      if (thumbPreviewImg) thumbPreviewImg.removeAttribute('src');
      hide(thumbPreview);
    }
  }

  function renderDetailThumbs() {
    var wrap = detailThumbs;
    if (!wrap) return;
    wrap.innerHTML = '';
    detailFiles.forEach(function (file, index) {
      var idx = index + 1;
      var imgUrl = URL.createObjectURL(file);
      var name = (file && file.name) ? file.name : '상세 이미지';
      var safeName = name.replace(/</g, '&lt;').replace(/>/g, '&gt;');
      var isFirst = index === 0;
      var isLast = index === detailFiles.length - 1;
      var upDisabled = isFirst ? 'disabled' : '';
      var downDisabled = isLast ? 'disabled' : '';
      var sub = isFirst ? '첫 이미지는 상세 상단에 우선 노출돼요' : '상세 이미지 ' + idx;
      var subSafe = sub.replace(/</g, '&lt;').replace(/>/g, '&gt;');
  
      var row = document.createElement('div');
      row.className = 'seller-product-detail-thumb';
      row.setAttribute('data-idx', String(idx));
      row.setAttribute('data-file-idx', String(index));
      row.setAttribute('data-image-url', imgUrl);
      row.innerHTML =
        '<img alt="상세 이미지 미리보기" src="' + imgUrl + '">' +
        '<div class="seller-product-detail-thumb-meta">' +
        '  <div class="seller-product-detail-thumb-title">' + safeName + '</div>' +
        '  <div class="seller-product-detail-thumb-sub">' + subSafe + '</div>' +
        '</div>' +
        '<div class="seller-product-detail-thumb-actions">' +
        '  <button type="button" class="seller-mini-btn" data-detail-action="up" ' + upDisabled + '>위</button>' +
        '  <button type="button" class="seller-mini-btn" data-detail-action="down" ' + downDisabled + '>아래</button>' +
        '  <button type="button" class="seller-mini-btn seller-mini-btn--warn" data-detail-action="remove">삭제</button>' +
        '</div>';
      wrap.appendChild(row);
    });
  }

  function syncDetailInputFiles() {
    if (!detailImageInput) return;
    try {
      var dt = new DataTransfer();
      detailFiles.forEach(function (f) { dt.items.add(f); });
      detailImageInput.files = dt.files;
    } catch (e) {
      // input.files 재할당이 막힌 환경에서는 원본 선택 파일을 그대로 제출
      console.warn('detailImageInput sync fallback:', e);
    }
  }

  function addDetailFiles(fileList) {
    if (!fileList || !fileList.length) return;
    Array.prototype.forEach.call(fileList, function (file) {
      if (file && file.type && file.type.indexOf('image/') === 0) {
        detailFiles.push(file);
      }
    });
    syncDetailInputFiles();
    renderDetailThumbs();
  }

  function moveDetailFile(from, to) {
    if (from === to || from < 0 || to < 0 || from >= detailFiles.length || to >= detailFiles.length) return;
    var file = detailFiles.splice(from, 1)[0];
    detailFiles.splice(to, 0, file);
    syncDetailInputFiles();
    renderDetailThumbs();
  }

  function removeDetailFile(index) {
    if (index < 0 || index >= detailFiles.length) return;
    detailFiles.splice(index, 1);
    syncDetailInputFiles();
    renderDetailThumbs();
  }

  function addDetailThumb() {
    if (detailImageInput) detailImageInput.click();
  }

  if (detailImageInput) {
    detailImageInput.addEventListener('change', function () {
      addDetailFiles(detailImageInput.files);
    });
  }

  function revokeRowPreviewUrl(row) {
    if (!row) return;
    var url = row.getAttribute('data-image-url');
    if (url) {
      URL.revokeObjectURL(url);
    }
  }

  function clearAllDetailPreviewUrls() {
    if (!detailThumbs) return;
    var rows = detailThumbs.querySelectorAll('.seller-product-detail-thumb');
    rows.forEach(function (row) { revokeRowPreviewUrl(row); });
  }

  var detailAddBtn = $('detailAddBtn');
  var detailBox = $('detailBox');
  if (detailBox) detailBox.addEventListener('click', function (e) {
    if (e.target && e.target.closest('[data-detail-action]')) return;
    addDetailThumb();
  });

  if (thumbAddBtn) thumbAddBtn.addEventListener('click', function (e) {
    e.preventDefault();
    e.stopPropagation();
    if (thumbImageInput) thumbImageInput.click();
  });
  if (detailAddBtn) detailAddBtn.addEventListener('click', function (e) {
    e.preventDefault();
    e.stopPropagation();
    addDetailThumb();
  });
  if (thumbBox) thumbBox.addEventListener('click', function (e) {
    if (e.target && e.target.closest('#thumbAddBtn')) return;
    if (thumbImageInput) thumbImageInput.click();
  });
  if (thumbImageInput) thumbImageInput.addEventListener('change', function () {
    if (thumbImageInput.files && thumbImageInput.files[0]) setThumb(true);
  });
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

    var fileIdx = Number(row.getAttribute('data-file-idx'));
    if (action === 'remove') {
      revokeRowPreviewUrl(row);
      removeDetailFile(fileIdx);
      return;
    }
    if (action === 'up' && row.previousElementSibling) {
      moveDetailFile(fileIdx, fileIdx - 1);
      return;
    }
    if (action === 'down' && row.nextElementSibling) {
      moveDetailFile(fileIdx, fileIdx + 1);
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
    // 이미지는 현재 더미 UI라서 필수에서 제외

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

  if (generateEasyDescBtn) {
    generateEasyDescBtn.addEventListener('click', function () {
      var productNameEl = $('productName');
      var brandNameEl = $('brandName');
      var situationCategoryEl = $('situationCategory');
      var typeCategoryEl = $('typeCategory');
      var saleStatusEl = $('saleStatus');
      var productGenderEl = document.querySelector('input[name="productGender"]:checked');
      var priceInputEl = $('price');
      var salePriceInputEl = $('salePrice');
      var seasonEl = document.querySelector('input[name="productSeason"]:checked');
      var productExEl = $('productEx');
      var easyOneLineEl = $('easyOneLine');
      var easyForEl = $('easyFor');
      var easyComfortEl = $('easyComfort');
      var fitEl = $('productFit');
      var thicknessEl = $('productThickness');
      var materialEl = $('productMaterial');
      var patternEl = $('productPattern');

      var payload = {
        brandName: brandNameEl ? brandNameEl.value.trim() : '',
        productName: productNameEl ? productNameEl.value.trim() : '',
        situationCategory: situationCategoryEl ? situationCategoryEl.value : '',
        typeCategory: typeCategoryEl ? typeCategoryEl.value : '',
        saleStatus: saleStatusEl ? saleStatusEl.value : '',
        productGender: productGenderEl ? productGenderEl.value : '',
        price: priceInputEl ? onlyNumberText(priceInputEl.value) : '',
        salePrice: salePriceInputEl ? onlyNumberText(salePriceInputEl.value) : '',
        productEx: productExEl ? productExEl.value.trim() : '',
        productMaterial: materialEl ? materialEl.value.trim() : '',
        productPattern: patternEl ? patternEl.value.trim() : '',
        productFit: fitEl ? fitEl.value.trim() : '',
        productThickness: thicknessEl ? thicknessEl.value.trim() : '',
        productSeason: seasonEl ? seasonEl.value : ''
      };

      var requiredKeys = [
        'brandName', 'productName', 'situationCategory', 'typeCategory',
        'saleStatus', 'productGender', 'price', 'productEx',
        'productMaterial', 'productPattern', 'productFit', 'productThickness', 'productSeason'
      ];
      var missing = requiredKeys.some(function (k) { return !payload[k]; });
      if (missing) {
        alert('위의 상품 정보를 모두 입력한 후 설명 자동 생성을 실행해 주세요.');
        return;
      }

      generateEasyDescBtn.disabled = true;
      var originalHtml = generateEasyDescBtn.innerHTML;
      generateEasyDescBtn.textContent = '생성 중...';

      var formData = new URLSearchParams();
      Object.keys(payload).forEach(function (k) { formData.append(k, payload[k]); });

      fetch((document.body.getAttribute('data-context-path') || '') + '/seller/product/generate-easy-desc', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
        body: formData.toString()
      })
        .then(function (res) { return res.json(); })
        .then(function (data) {
          if (!data || !data.ok) {
            alert((data && data.message) ? data.message : '설명 자동 생성에 실패했어요.');
            return;
          }
          if (easyOneLineEl) easyOneLineEl.value = data.easyOneLine || '';
          if (easyForEl) easyForEl.value = data.easyFor || '';
          if (easyComfortEl) easyComfortEl.value = data.easyComfort || '';
        })
        .catch(function () {
          alert('설명 자동 생성 중 오류가 발생했어요.');
        })
        .finally(function () {
          generateEasyDescBtn.disabled = false;
          generateEasyDescBtn.innerHTML = originalHtml;
        });
    });
  }

  if (tempBtn) {
    tempBtn.addEventListener('click', function () {
      var saveModeEl = $('saveMode');
      if (saveModeEl) saveModeEl.value = 'temp';
      if (!validateRequired()) return;
      form.requestSubmit();
    });
  }

  if (submitBtn) {
    submitBtn.addEventListener('click', function () {
      var saveModeEl = $('saveMode');
      if (saveModeEl) saveModeEl.value = 'submit';
    });
  }

  function clearOptionHiddenInputs() {
    var olds = form.querySelectorAll('input[data-option-hidden="true"]');
    olds.forEach(function (el) { el.remove(); });
  }

  function appendOptionHidden(name, value) {
    var input = document.createElement('input');
    input.type = 'hidden';
    input.name = name;
    input.value = value;
    input.setAttribute('data-option-hidden', 'true');
    form.appendChild(input);
  }

  function serializeOptionsForSubmit() {
    clearOptionHiddenInputs();
    if (!optionBody) return 0;
    var rows = optionBody.querySelectorAll('tr:not(.seller-product-option-empty)');
    var count = 0;
    rows.forEach(function (tr) {
      var tds = tr.querySelectorAll('td');
      if (!tds || tds.length < 4) return;
      var color = (tds[0].textContent || '').trim();
      var size = (tds[1].textContent || '').trim();
      var stockInput = tr.querySelector('input[data-opt="stock"]');
      var soldoutInput = tr.querySelector('input[data-opt="soldout"]');
      var stock = Number(onlyNumberText(stockInput ? stockInput.value : '0'));
      if (soldoutInput && soldoutInput.checked) stock = 0;
      if (!color || !size) return;
      appendOptionHidden('optionColor', color);
      appendOptionHidden('optionSize', size);
      appendOptionHidden('optionStock', String(stock));
      count++;
    });
    return count;
  }

  form.addEventListener('submit', function (e) {
    var saveModeEl = $('saveMode');
    var mode = saveModeEl ? saveModeEl.value : 'submit';
    if (!validateRequired()) {
      e.preventDefault();
      return;
    }
    var optionRows = serializeOptionsForSubmit();
    if (mode !== 'temp' && optionRows === 0) {
      e.preventDefault();
      showError('optionError', '옵션 또는 재고 정보를 입력해 주세요.');
      showError('formError', '등록하기에는 최소 1개 옵션이 필요해요.');
      return;
    }
    if (mode !== 'temp' && !hasThumb()) {
      e.preventDefault();
      showError('thumbError', '대표 이미지를 등록해 주세요.');
      showError('formError', '등록하기에는 대표 이미지가 필요해요.');
      return;
    }
    if (saveModeEl) saveModeEl.value = 'submit';
  });

  window.addEventListener('beforeunload', function () {
    clearAllDetailPreviewUrls();
    if (thumbPreviewImg && thumbPreviewImg.src && thumbPreviewImg.src.indexOf('blob:') === 0) {
      URL.revokeObjectURL(thumbPreviewImg.src);
    }
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

