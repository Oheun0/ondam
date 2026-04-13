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
  var contextPath = document.body ? (document.body.getAttribute('data-context-path') || '') : '';

  var orderType = root.getAttribute('data-order-type'); // gift | poke | normal
  var wallet = root.getAttribute('data-wallet') === 'true';
  var orderNo = root.getAttribute('data-order-no');
  var orderItemNo = root.getAttribute('data-order-item-no');

  function getQueryParam(name) {
    try {
      return new URLSearchParams(window.location.search).get(name);
    } catch (e) {
      return null;
    }
  }
  if (!orderItemNo) {
    orderItemNo = getQueryParam('orderItemNo');
  }
  if (!orderNo) {
    orderNo = getQueryParam('orderNo');
  }

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
      ready: '배송 준비 중',
      shipping: '배송 중',
      done: '배송 완료',
    }[code] || code;
  }

  function mapShipmentStatusCode(code) {
    return {
      ready: 1,
      shipping: 2,
      done: 3,
    }[code] || 0;
  }

  function mapUiCodeFromShipmentStatus(status) {
    return {
      1: 'ready',
      2: 'shipping',
      3: 'done',
    }[status] || 'ready';
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
      if (!orderItemNo) {
        showError('odFormError', 'orderItemNo가 없어 상태 변경을 진행할 수 없습니다.');
        return;
      }

      var shipmentStatus = mapShipmentStatusCode(v);
      if (!shipmentStatus) {
        showError('odStatusError', '지원하지 않는 상태입니다.');
        return;
      }

      var params = new URLSearchParams();
      params.set('action', 'status');
      params.set('orderItemNo', orderItemNo);
      params.set('shipmentStatus', String(shipmentStatus));

      fetch(contextPath + '/seller/shipment', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
        body: params.toString(),
        credentials: 'same-origin',
      })
        .then(function (res) { return res.json(); })
        .then(function (json) {
          if (!(json && json.status === 'success')) {
            showError('odFormError', (json && json.message) ? json.message : '배송 상태 변경에 실패했습니다.');
            return;
          }

          var label = mapStatusLabel(v);
          if (badge) { setText(badge, label); applyBadgeStyle(badge, v); }
          if (currentBadge) { setText(currentBadge, label.replace('배송 ', '')); applyBadgeStyle(currentBadge, v); }

          if (historyList) {
            var li = document.createElement('li');
            li.className = 'seller-order-detail-history-item';
            li.innerHTML = '<span class="t">' + nowText() + '</span><span class="s">' + label + '</span>';
            historyList.appendChild(li);
          }

          alert('배송 상태가 변경되었습니다.\n\n' + label);
          console.log('[SellerOrderDetail] shipment status changed', {
            orderItemNo: orderItemNo,
            shipmentStatus: shipmentStatus,
            label: label,
          });
        })
        .catch(function () {
          showError('odFormError', '서버 통신 중 오류가 발생했습니다.');
        });
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
    if (!orderItemNo) {
      showError('odFormError', 'orderItemNo가 없어 송장 등록을 진행할 수 없습니다.');
      return;
    }

    var params = new URLSearchParams();
    params.set('action', 'register');
    params.set('orderItemNo', orderItemNo);
    params.set('carrierCode', carrierEl.value);
    params.set('trackingNo', trackingEl.value.trim());

    fetch(contextPath + '/seller/shipment', {
      method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
      body: params.toString(),
      credentials: 'same-origin',
    })
      .then(function (res) { return res.json(); })
      .then(function (json) {
        if (json && json.status === 'success') {
          alert('송장이 저장되었습니다.\n\n' + carrierEl.value + ' / ' + trackingEl.value.trim());
          console.log('[SellerOrderDetail] save invoice', {
            orderItemNo: orderItemNo,
            carrier: carrierEl.value,
            tracking: trackingEl.value.trim(),
          });
        } else {
          showError('odFormError', (json && json.message) ? json.message : '송장 저장에 실패했습니다.');
        }
      })
      .catch(function () {
        showError('odFormError', '서버 통신 중 오류가 발생했습니다.');
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
      if (applyStatusBtn) {
        applyStatusBtn.click();
      }
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

  function applyShipmentDataToUi(item) {
    if (!item) return;

    if (carrierEl && item.carrierCode) {
      carrierEl.value = item.carrierCode;
    }
    if (trackingEl && item.trackingNo) {
      trackingEl.value = item.trackingNo;
    }

    var uiCode = mapUiCodeFromShipmentStatus(item.shipmentStatus);
    var label = mapStatusLabel(uiCode);
    if (badge) { setText(badge, label); applyBadgeStyle(badge, uiCode); }
    if (currentBadge) { setText(currentBadge, label.replace('배송 ', '')); applyBadgeStyle(currentBadge, uiCode); }
    if (nextStatusEl) { nextStatusEl.value = uiCode; }
  }

  function fetchShipmentList() {
    var parsedOrderNo = parseInt(orderNo, 10);
    if (!parsedOrderNo) {
      return;
    }

    var params = new URLSearchParams();
    params.set('action', 'list');
    params.set('orderNo', String(parsedOrderNo));

    fetch(contextPath + '/seller/shipment?' + params.toString(), {
      method: 'GET',
      credentials: 'same-origin',
      headers: { 'Accept': 'application/json' },
    })
      .then(function (res) { return res.json(); })
      .then(function (json) {
        if (!(json && json.status === 'success' && Array.isArray(json.data))) {
          return;
        }

        var target = null;
        if (orderItemNo) {
          for (var i = 0; i < json.data.length; i++) {
            if (String(json.data[i].orderItemNo) === String(orderItemNo)) {
              target = json.data[i];
              break;
            }
          }
        }
        if (!target && json.data.length > 0) {
          target = json.data[0];
        }
        applyShipmentDataToUi(target);
      })
      .catch(function () {
        // 조회 실패 시 기존 수동 입력 UX 유지
      });
  }

  fetchShipmentList();

  if (!orderItemNo) {
    if (saveInvoiceBtn) saveInvoiceBtn.disabled = true;
    if (saveInvoiceBtn2) saveInvoiceBtn2.disabled = true;
    if (applyStatusBtn) applyStatusBtn.disabled = true;
    if (saveStatusBtn) saveStatusBtn.disabled = true;
    showError('odFormError', '주문 연동 전 단계입니다. orderItemNo 연결 후 송장/상태 변경이 활성화됩니다.');
  }
})();

