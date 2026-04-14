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
  var contextPath = document.body ? (document.body.getAttribute('data-context-path') || '') : '';

  var orderType = root.getAttribute('data-order-type');
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

          var root = document.getElementById('orderDetailRoot');
          var orderNo = root.getAttribute('data-order-no');
          var selectedStatus = nextStatusEl ? nextStatusEl.value : '';

          if (!selectedStatus) {
            showError('odStatusError', '변경할 배송 상태를 선택해 주세요.');
            return;
          }

		  var checkedBoxes = document.querySelectorAll('.item-checkbox:checked');
		            if (checkedBoxes.length === 0) {
		              showError('odStatusError', '상태를 변경할 상품을 하나 이상 체크해 주세요.');

		              var itemCards = document.querySelectorAll('.seller-order-detail-item');
		              itemCards.forEach(function(card) {
		                  card.style.transition = "all 0.3s ease";
		                  card.style.boxShadow = "0 0 15px rgba(255, 77, 79, 0.4)";
		                  card.style.borderColor = "#ff4d4f";
		              });
		              if (itemCards.length > 0) {
		                  itemCards[0].scrollIntoView({ behavior: 'smooth', block: 'center' });
		              }
		              setTimeout(function() {
		                  itemCards.forEach(function(card) {
		                      card.style.boxShadow = "";
		                      card.style.borderColor = "";
		                  });
		              }, 1500);

		              return;
		            }
		var hasNoInvoice = false;
		var itemNos = [];
		        checkedBoxes.forEach(function(box) {
		            var state = box.getAttribute('data-state');
		            var tracking = box.getAttribute('data-tracking');
		            if (state === '0' && (!tracking || tracking.trim() === '')) {
		                hasNoInvoice = true;
		            }
		            itemNos.push(box.value);
		        });
				
				if (hasNoInvoice) {
				            showError('odTrackingError', '송장번호를 먼저 저장해야 상태를 변경할 수 있습니다.');
				            if (trackingEl) {
				                trackingEl.focus();
				                trackingEl.style.backgroundColor = "#fff2f0";
				                setTimeout(function() { trackingEl.style.backgroundColor = ""; }, 1000);
				            }
				            return;
				          }
						
        var itemNosString = itemNos.join(',');

		if (confirm('선택한 ' + checkedBoxes.length + '개 상품의 배송 상태를 변경하시겠습니까?')) {
		          var contextPath = document.body.getAttribute('data-context-path') || '';
		          var targetUrl = contextPath + "/seller/order?action=updateItemStatus&orderNo=" + orderNo + "&status=" + selectedStatus + "&itemNos=" + itemNosString;
		          
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
			      var checkedBoxes = document.querySelectorAll('.item-checkbox:checked');
			      if (checkedBoxes.length === 0) {
			        showError('odFormError', '송장을 저장할 상품을 하나 이상 체크해 주세요.');
			        return;
			      }

			      var orderNo = root.getAttribute('data-order-no');
			      var carrier = carrierEl.value;
			      var tracking = trackingEl.value.trim();
			      var itemNos = [];
			      checkedBoxes.forEach(function(box) {
			          itemNos.push(box.value);
			      });
			      var itemNosString = itemNos.join(',');
			      if (confirm('송장 정보를 저장하시겠습니까?\n(저장 시 자동으로 [배송 준비 중] 상태로 넘어갑니다.)')) {
			          var contextPath = document.body.getAttribute('data-context-path') || '';
			          var targetUrl = contextPath + "/seller/order?action=updateItemInvoice" 
			                        + "&orderNo=" + orderNo 
			                        + "&carrier=" + encodeURIComponent(carrier) 
			                        + "&tracking=" + encodeURIComponent(tracking)
			                        + "&itemNos=" + itemNosString
			                        + "&status=ready"; 
			          
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

document.addEventListener('DOMContentLoaded', function() {
    const checkAll = document.getElementById('checkAll');
    const itemCheckboxes = document.querySelectorAll('.item-checkbox');
    const itemCards = document.querySelectorAll('.seller-order-detail-item');
    const courierSelect = document.getElementById('odCarrier');
    const trackingInput = document.getElementById('odTracking');

    // 전체 선택 / 해제 로직
    if (checkAll) {
        checkAll.addEventListener('change', function() {
            itemCheckboxes.forEach(cb => {
                cb.checked = this.checked;
            });
            syncDeliveryInfo(); // 상태 업데이트
        });
    }

    //개별 체크박스 변경 시 감시
    itemCheckboxes.forEach(cb => {
        cb.addEventListener('change', function() {
            // 전체 선택 박스 상태 동기화
            checkAll.checked = [...itemCheckboxes].every(c => c.checked);
            syncDeliveryInfo();
        });
    });

    // 카드 클릭 시 체크박스 토글 (체크박스 직접 클릭은 기존 동작 유지)
    itemCards.forEach(card => {
        card.addEventListener('click', function (e) {
            if (e.target.closest('input, button, a, select, textarea, label')) return;
            const checkbox = card.querySelector('.item-checkbox');
            if (!checkbox) return;
            checkbox.checked = !checkbox.checked;
            checkbox.dispatchEvent(new Event('change', { bubbles: true }));
        });
    });

    //체크된 상품 수에 따라 입력창 채우기
    function syncDeliveryInfo() {
        const checked = document.querySelectorAll('.item-checkbox:checked');

        if (checked.length === 1) {
            // 1개만 선택된 경우: 해당 상품의 정보를 가져와서 채움
            const courier = checked[0].getAttribute('data-courier') || "";
            const tracking = checked[0].getAttribute('data-tracking') || "";
            
            courierSelect.value = courier;
            trackingInput.value = tracking;
        } else {
            // 0개이거나 2개 이상 선택된 경우: 초기화
            courierSelect.value = "";
            trackingInput.value = "";
        }
    }
});