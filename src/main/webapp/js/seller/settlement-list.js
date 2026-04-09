/* global document, alert, console, window */
(function () {
  function $(id) { return document.getElementById(id); }
  function pad2(n) { return String(n).padStart(2, '0'); }
  function fmtDate(d) {
    return d.getFullYear() + '-' + pad2(d.getMonth() + 1) + '-' + pad2(d.getDate());
  }
  function setDateRange(start, end) {
    var s = $('startDate');
    var e = $('endDate');
    if (s) s.value = fmtDate(start);
    if (e) e.value = fmtDate(end);
  }

  // Period preset → dates
  var preset = $('periodPreset');
  if (preset) {
    preset.addEventListener('change', function () {
      var now = new Date();
      var start = new Date(now);
      var end = new Date(now);

      if (preset.value === 'today') {
        // keep today
      } else if (preset.value === '7d') {
        start.setDate(start.getDate() - 6);
      } else if (preset.value === '30d') {
        start.setDate(start.getDate() - 29);
      } else if (preset.value === 'month') {
        start = new Date(now.getFullYear(), now.getMonth(), 1);
        end = new Date(now.getFullYear(), now.getMonth() + 1, 0);
      } else if (preset.value === 'custom') {
        console.log('[Settlement] custom period selected (dummy)');
        return;
      }
      setDateRange(start, end);
      console.log('[Settlement] preset change (dummy)', preset.value);
    });
  }
  // initial default: this month
  if (preset) {
    preset.value = 'month';
    var now0 = new Date();
    setDateRange(new Date(now0.getFullYear(), now0.getMonth(), 1), new Date(now0.getFullYear(), now0.getMonth() + 1, 0));
  }

  // Filter / search (dummy)
  var searchBtn = $('settlementSearchBtn');
  if (searchBtn) {
    searchBtn.addEventListener('click', function () {
      var payload = {
        preset: preset ? preset.value : '',
        startDate: $('startDate') ? $('startDate').value : '',
        endDate: $('endDate') ? $('endDate').value : '',
        status: $('settleStatus') ? $('settleStatus').value : '',
        payMethod: $('payMethod') ? $('payMethod').value : '',
      };
      console.log('[Settlement] search (dummy)', payload);
      alert('조회는 더미 동작입니다.\n\n' +
        '기간: ' + payload.startDate + ' ~ ' + payload.endDate + '\n' +
        '정산 상태: ' + payload.status + '\n' +
        '결제수단: ' + payload.payMethod
      );
    });
  }

  // Download (dummy)
  var downloadBtn = $('settlementDownloadBtn');
  if (downloadBtn) {
    downloadBtn.addEventListener('click', function () {
      console.log('[Settlement] download (dummy)');
      alert('정산 내역 다운로드는 아직 연동되지 않았어요. (더미)');
    });
  }

  // Pagination (dummy)
  document.addEventListener('click', function (e) {
    var pageBtn = e.target.closest('.seller-settlement-page-btn');
    if (!pageBtn) return;
    var p = pageBtn.getAttribute('data-page');
    alert('페이지네이션은 더미 동작입니다. (선택: ' + p + ')');
  });

  // Detail modal
  var modal = $('settleModal');
  var dim = $('settleModalDim');
  var closeBtn = $('settleModalClose');

  function openModal() {
    if (!modal) return;
    modal.classList.remove('hidden');
    modal.setAttribute('aria-hidden', 'false');
    document.body.style.overflow = 'hidden';
  }
  function closeModal() {
    if (!modal) return;
    modal.classList.add('hidden');
    modal.setAttribute('aria-hidden', 'true');
    document.body.style.overflow = '';
  }

  function fmtWon(nStr) {
    var n = Number(String(nStr).replace(/,/g, '').replace(/[^0-9.-]/g, ''));
    if (Number.isNaN(n)) return '-';
    var sign = n < 0 ? '-' : '';
    var abs = Math.abs(n);
    return sign + abs.toLocaleString('ko-KR') + '원';
  }

  function badgeText(status) {
    if (status === 'done') return '정산 완료';
    if (status === 'pending') return '정산 예정';
    if (status === 'refund') return '환불 포함';
    if (status === 'cancel') return '취소 포함';
    return '전체';
  }

  function fillModalFromRow(tr) {
    if (!tr) return;
    var id = tr.getAttribute('data-settle-id') || '-';
    var period = tr.getAttribute('data-period') || '-';
    var gross = tr.getAttribute('data-gross') || '-';
    var refund = tr.getAttribute('data-refund') || '-';
    var target = tr.getAttribute('data-target') || '-';
    var card = tr.getAttribute('data-card') || '0';
    var bank = tr.getAttribute('data-bank') || '0';
    var wallet = tr.getAttribute('data-wallet') || '0';
    var status = tr.getAttribute('data-status') || '-';
    var date = tr.getAttribute('data-date') || '-';

    var sub = $('settleModalSub');
    if (sub) sub.textContent = id + ' · ' + period;

    if ($('dPeriod')) $('dPeriod').textContent = period;
    if ($('dStatus')) $('dStatus').textContent = badgeText(status);
    if ($('dGross')) $('dGross').textContent = fmtWon(gross);
    if ($('dRefund')) $('dRefund').textContent = fmtWon(refund);
    if ($('dTarget')) $('dTarget').textContent = fmtWon(target);
    if ($('dDate')) $('dDate').textContent = date;

    if ($('dCard')) $('dCard').textContent = fmtWon(card);
    if ($('dBank')) $('dBank').textContent = fmtWon(bank);
    if ($('dWallet')) $('dWallet').textContent = fmtWon(wallet);
  }

  if (dim) dim.addEventListener('click', closeModal);
  if (closeBtn) closeBtn.addEventListener('click', closeModal);
  document.addEventListener('keydown', function (e) {
    if (e.key === 'Escape') closeModal();
  });

  document.addEventListener('click', function (e) {
    var btn = e.target.closest('[data-action="view"]');
    if (!btn) return;
    var tr = btn.closest('tr[data-settle-id]');
    if (!tr) return;
    console.log('[Settlement] view detail (dummy)', tr.getAttribute('data-settle-id'));
    fillModalFromRow(tr);
    openModal();
  });

  // Empty CTA (dummy)
  var goOrderBtn = $('goOrderBtn');
  if (goOrderBtn) {
    goOrderBtn.addEventListener('click', function () {
      alert('주문 관리 화면으로 이동(더미)\n\npreview?page=seller/order/list');
      // 실제 이동은 더미로 남김
      window.location.href = (document.body.getAttribute('data-context-path') || '') + '/preview?page=seller/order/list';
    });
  }
})();

