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

  var root = $('orderDetailRoot');
  if (!root) return;

  var orderType = root.getAttribute('data-order-type'); // gift | poke | normal
  var wallet = root.getAttribute('data-wallet') === 'true';

  // 조건 카드 노출(더미)
  if (orderType === 'gift') show($('odGiftCard'));
  if (orderType === 'poke') show($('odPokeCard'));
  if (wallet) show($('odWalletCard'));

  var nextStatusEl = $('odNextStatus');
  var applyStatusBtn = $('odApplyStatusBtn');
  var historyList = $('odHistoryList');

  var badge = $('odStatusBadge');
  var currentBadge = $('odCurrentBadge');

  function mapStatusLabel(code) {
    return {
      paid: '결제완료',
      ready: '배송 준비 중',
      shipping: '배송 중',
      done: '배송 완료',
      cancel: '취소',
    }[code] || code;
  }

  function applyBadgeStyle(el, code) {
    if (!el) return;
    el.classList.remove(
      'seller-order-badge--paid',
      'seller-order-badge--ready',
      'seller-order-badge--shipping',
      'seller-order-badge--done',
      'seller-order-badge--cancel'
    );
    var cls = {
      paid: 'seller-order-badge--paid',
      ready: 'seller-order-badge--ready',
      shipping: 'seller-order-badge--shipping',
      done: 'seller-order-badge--done',
      cancel: 'seller-order-badge--cancel',
    }[code];
    if (cls) el.classList.add(cls);
  }

  function nowText() {
    var d = new Date();
    var yyyy = d.getFullYear();
    var mm = String(d.getMonth() + 1).padStart(2, '0');
    var dd = String(d.getDate()).padStart(2, '0');
    var hh = String(d.getHours()).padStart(2, '0');
    var mi = String(d.getMinutes()).padStart(2, '0');
    return yyyy + '.' + mm + '.' + dd + ' ' + hh + ':' + mi;
  }

  if (applyStatusBtn) {
    applyStatusBtn.addEventListener('click', function () {
      clearError('odStatusError');
      clearError('odFormError');

      var v = nextStatusEl ? nextStatusEl.value : '';
      if (!v) {
        showError('odStatusError', '변경할 상태를 선택해 주세요.');
        return;
      }

      // 배지 변경(더미)
      var label = mapStatusLabel(v);
      if (badge) { setText(badge, label); applyBadgeStyle(badge, v); }
      if (currentBadge) { setText(currentBadge, label.replace('배송 ', '')); applyBadgeStyle(currentBadge, v); }

      // 이력 추가(더미)
      if (historyList) {
        var li = document.createElement('li');
        li.className = 'seller-order-detail-history-item';
        li.innerHTML = '<span class="t">' + nowText() + '</span><span class="s">' + label + '</span>';
        historyList.appendChild(li);
      }

      alert('상태가 변경되었습니다. (더미)\n\n' + label);
      console.log('[SellerOrderDetail] status changed (dummy)', v);
    });
  }

  // 송장 저장(더미)
  var carrierEl = $('odCarrier');
  var trackingEl = $('odTracking');
  function validateInvoice() {
    clearError('odCarrierError');
    clearError('odTrackingError');
    clearError('odFormError');
    var ok = true;
    var c = carrierEl ? carrierEl.value : '';
    var t = trackingEl ? trackingEl.value.trim() : '';
    if (!c) { showError('odCarrierError', '택배사를 선택해 주세요.'); ok = false; }
    if (!t) { showError('odTrackingError', '송장번호를 입력해 주세요.'); ok = false; }
    if (!ok) showError('odFormError', '필수 항목을 확인해 주세요.');
    return ok;
  }

  function saveInvoice() {
    if (!validateInvoice()) return;
    alert('송장이 저장되었습니다. (더미)\n\n' + (carrierEl.value) + ' / ' + trackingEl.value.trim());
    console.log('[SellerOrderDetail] save invoice (dummy)', {
      carrier: carrierEl.value,
      tracking: trackingEl.value.trim(),
    });
  }

  var saveInvoiceBtn = $('odSaveInvoiceBtn');
  var saveInvoiceBtn2 = $('odSaveInvoiceBtn2');
  if (saveInvoiceBtn) saveInvoiceBtn.addEventListener('click', saveInvoice);
  if (saveInvoiceBtn2) saveInvoiceBtn2.addEventListener('click', saveInvoice);

  // 하단 버튼(더미)
  var saveStatusBtn = $('odSaveStatusBtn');
  if (saveStatusBtn) {
    saveStatusBtn.addEventListener('click', function () {
      alert('배송 상태 저장(더미) — 실제 저장은 아직 연동되지 않았어요.');
      console.log('[SellerOrderDetail] save status (dummy)');
    });
  }

  var cancelBtn = $('odCancelBtn');
  if (cancelBtn) {
    cancelBtn.addEventListener('click', function () {
      alert('주문 취소는 더미 동작입니다.');
      console.log('[SellerOrderDetail] cancel order (dummy)');
    });
  }

  if (nextStatusEl) nextStatusEl.addEventListener('change', function () { clearError('odStatusError'); clearError('odFormError'); });
  if (carrierEl) carrierEl.addEventListener('change', function () { clearError('odCarrierError'); clearError('odFormError'); });
  if (trackingEl) trackingEl.addEventListener('input', function () { clearError('odTrackingError'); clearError('odFormError'); });
})();

