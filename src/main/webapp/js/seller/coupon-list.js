/* global document, alert, console */
(function () {
  function $(id) { return document.getElementById(id); }
  function show(el) { if (el) el.hidden = false; }
  function hide(el) { if (el) el.hidden = true; }
  function trim(v) { return (v || '').toString().trim(); }

  var form = $('couponCreateForm');
  var nameEl = $('couponName');
  var typeEl = $('discountType');
  var valueEl = $('discountValue');
  var unitEl = $('discountUnit');
  var minEl = $('minOrderAmount');
  var startEl = $('startDate');
  var endEl = $('endDate');
  var resetBtn = $('couponResetBtn');

  var errName = $('errName');
  var errDiscount = $('errDiscount');
  var errDate = $('errDate');

  function setDiscountUI() {
    if (!typeEl || !valueEl || !unitEl) return;
    var isRate = typeEl.value === 'rate';
    unitEl.textContent = isRate ? '%' : '원';
    valueEl.placeholder = isRate ? '할인율을 입력해 주세요' : '할인 금액을 입력해 주세요';
  }

  function isValidDateRange() {
    if (!startEl || !endEl) return true;
    if (!startEl.value || !endEl.value) return false;
    return new Date(startEl.value) <= new Date(endEl.value);
  }

  function validate() {
    var ok = true;
    hide(errName); hide(errDiscount); hide(errDate);

    if (!nameEl || !trim(nameEl.value)) { show(errName); ok = false; }
    if (!valueEl || !trim(valueEl.value)) { show(errDiscount); ok = false; }
    if (!startEl || !startEl.value || !endEl || !endEl.value || !isValidDateRange()) { show(errDate); ok = false; }

    return ok;
  }

  if (typeEl) typeEl.addEventListener('change', function () {
    setDiscountUI();
    console.log('[SellerCoupon] discountType change (dummy)', typeEl.value);
  });
  setDiscountUI();

  if (resetBtn) {
    resetBtn.addEventListener('click', function () {
      if (form) form.reset();
      setDiscountUI();
      hide(errName); hide(errDiscount); hide(errDate);
      alert('입력값이 초기화되었습니다. (더미)');
    });
  }

  if (form) {
    form.addEventListener('submit', function (e) {
      e.preventDefault();
      if (!validate()) {
        alert('필수 입력값을 확인해 주세요.');
        return;
      }

      var payload = {
        couponName: trim(nameEl ? nameEl.value : ''),
        discountType: typeEl ? typeEl.value : '',
        discountValue: trim(valueEl ? valueEl.value : ''),
        minOrderAmount: trim(minEl ? minEl.value : ''),
        startDate: startEl ? startEl.value : '',
        endDate: endEl ? endEl.value : '',
        targetType: $('targetType') ? $('targetType').value : '',
        couponDesc: trim($('couponDesc') ? $('couponDesc').value : ''),
      };
      console.log('[SellerCoupon] create (dummy)', payload);
      alert('쿠폰이 생성되었습니다. (더미)\n\n' +
        '쿠폰명: ' + payload.couponName + '\n' +
        '할인: ' + (payload.discountType === 'rate' ? payload.discountValue + '%' : payload.discountValue + '원') + '\n' +
        '기간: ' + payload.startDate + ' ~ ' + payload.endDate
      );
      form.reset();
      setDiscountUI();
    });
  }

  document.addEventListener('click', function (e) {
    var btn = e.target.closest('[data-action]');
    if (!btn) return;
    var action = btn.getAttribute('data-action');
    var tr = btn.closest('tr[data-coupon-id]');
    var id = tr ? tr.getAttribute('data-coupon-id') : '(unknown)';

    if (action === 'edit') {
      console.log('[SellerCoupon] edit (dummy)', id);
      alert('쿠폰 수정은 아직 연동되지 않았어요. (더미)\n쿠폰ID: ' + id);
      return;
    }
    if (action === 'end') {
      console.log('[SellerCoupon] end (dummy)', id);
      alert('쿠폰 종료 처리(더미)\n쿠폰ID: ' + id);
      return;
    }
    if (action === 'view') {
      console.log('[SellerCoupon] view (dummy)', id);
      alert('쿠폰 상세 보기(더미)\n쿠폰ID: ' + id);
    }
  });

  var emptyCreateBtn = $('couponEmptyCreateBtn');
  if (emptyCreateBtn) {
    emptyCreateBtn.addEventListener('click', function () {
      alert('상단의 "새 쿠폰 생성" 폼으로 쿠폰을 만들어 주세요. (더미)');
      window.scrollTo({ top: 0, behavior: 'smooth' });
    });
  }

  document.addEventListener('click', function (e) {
    var pageBtn = e.target.closest('.seller-coupon-page-btn');
    if (!pageBtn) return;
    var p = pageBtn.getAttribute('data-page');
    alert('페이지네이션은 더미 동작입니다. (선택: ' + p + ')');
  });
})();

