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

  var orderType = root.getAttribute('data-order-type');
  var wallet = root.getAttribute('data-wallet') === 'true';

  // 조건 카드 노출
  if (orderType === 'gift') show($('odGiftCard'));
  if (orderType === 'poke') show($('odPokeCard'));
  if (wallet) show($('odWalletCard'));

  var nextStatusEl = $('odNextStatus');
  var applyStatusBtn = $('odApplyStatusBtn');
  var historyList = $('odHistoryList');

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
        var targetUrl = contextPath + "/seller/order?action=updateStatus&orderNo=" + orderNo + "&status=" + selectedStatus;
        
        window.location.href = targetUrl;
      }
    });
  }

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

    var orderNo = root.getAttribute('data-order-no');
    var carrier = carrierEl.value;
    var tracking = trackingEl.value.trim();

    if (confirm('송장 정보를 저장하시겠습니까?\n(' + carrier + ' : ' + tracking + ')')) {
        var contextPath = document.body.getAttribute('data-context-path') || '';
        var targetUrl = contextPath + "/seller/order?action=updateInvoice&orderNo=" + orderNo + "&carrier=" + encodeURIComponent(carrier) + "&tracking=" + encodeURIComponent(tracking);
        
        window.location.href = targetUrl;
    }
  }

  var saveInvoiceBtn = $('odSaveInvoiceBtn');
  var saveInvoiceBtn2 = $('odSaveInvoiceBtn2'); // 하단에 있는 두 번째 송장 저장 버튼
  if (saveInvoiceBtn) saveInvoiceBtn.addEventListener('click', saveInvoice);
  if (saveInvoiceBtn2) saveInvoiceBtn2.addEventListener('click', saveInvoice);

  //하단 배송 상태 저장 버튼
    var saveStatusBtn = $('odSaveStatusBtn');
    if (saveStatusBtn) {
      saveStatusBtn.addEventListener('click', function () {
        var topBtn = $('odApplyStatusBtn');
        if (topBtn) {
            topBtn.click(); 
        }
      });
    }

  //취소 버튼
    var cancelBtn = $('odCancelBtn');
    if (cancelBtn) {
      cancelBtn.addEventListener('click', function () {
        var orderNo = root.getAttribute('data-order-no');
        
        if (confirm('정말로 이 주문을 취소하시겠습니까?\n(취소 후에는 복구할 수 없습니다.)')) {
          var contextPath = document.body.getAttribute('data-context-path') || '';
          var targetUrl = contextPath + "/seller/order?action=updateStatus&orderNo=" + orderNo + "&status=cancel";
          window.location.href = targetUrl;
        }
      });
    }

  if (nextStatusEl) nextStatusEl.addEventListener('change', function () { clearError('odStatusError'); clearError('odFormError'); });
  if (carrierEl) carrierEl.addEventListener('change', function () { clearError('odCarrierError'); clearError('odFormError'); });
  if (trackingEl) trackingEl.addEventListener('input', function () { clearError('odTrackingError'); clearError('odFormError'); });
})();