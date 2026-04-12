/* global document, alert, console, confirm, window */
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

  // 조건 카드 노출
  if (orderType === 'gift') show($('odGiftCard'));
  if (orderType === 'poke') show($('odPokeCard'));
  if (wallet) show($('odWalletCard'));

  var nextStatusEl = $('odNextStatus');
  var applyStatusBtn = $('odApplyStatusBtn');
  var historyList = $('odHistoryList');

  // 💡 [진짜 백엔드 연동] 상태 변경 버튼 클릭 이벤트
  if (applyStatusBtn) {
    applyStatusBtn.addEventListener('click', function () {
      clearError('odStatusError');
      clearError('odFormError');

      var orderNo = root.getAttribute('data-order-no');
      var selectedStatus = nextStatusEl ? nextStatusEl.value : '';

      if (!selectedStatus) {
        showError('odStatusError', '변경할 배송 상태를 선택해 주세요.');
        return;
      }

      if (confirm('정말로 배송 상태를 변경하시겠습니까?')) {
        var contextPath = document.body.getAttribute('data-context-path') || '';
        // 컨트롤러(SellerOrderController)의 updateStatus 로직으로 이동!
        var targetUrl = contextPath + "/seller/order?action=updateStatus&orderNo=" + orderNo + "&status=" + selectedStatus;
        
        window.location.href = targetUrl;
      }
    });
  }

  // --- 아래는 아직 백엔드 연결 안 된 송장/취소 더미 기능들 --- //

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
  }

  var saveInvoiceBtn = $('odSaveInvoiceBtn');
  var saveInvoiceBtn2 = $('odSaveInvoiceBtn2');
  if (saveInvoiceBtn) saveInvoiceBtn.addEventListener('click', saveInvoice);
  if (saveInvoiceBtn2) saveInvoiceBtn2.addEventListener('click', saveInvoice);

  // 하단 버튼(더미)
  var saveStatusBtn = $('odSaveStatusBtn');
  if (saveStatusBtn) {
    saveStatusBtn.addEventListener('click', function () {
      alert('배송 상태 저장(더미) — 실제 저장은 위쪽 [상태 변경] 버튼을 이용해 주세요.');
    });
  }

  var cancelBtn = $('odCancelBtn');
  if (cancelBtn) {
    cancelBtn.addEventListener('click', function () {
      alert('주문 취소는 아직 준비 중입니다.');
    });
  }

  if (nextStatusEl) nextStatusEl.addEventListener('change', function () { clearError('odStatusError'); clearError('odFormError'); });
  if (carrierEl) carrierEl.addEventListener('change', function () { clearError('odCarrierError'); clearError('odFormError'); });
  if (trackingEl) trackingEl.addEventListener('input', function () { clearError('odTrackingError'); clearError('odFormError'); });
})();