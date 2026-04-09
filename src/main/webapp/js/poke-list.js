/**
 * 조르기 목록(표시용)
 * - 뒤로가기
 * - 전체 선택 ↔ 개별 선택 동기화
 * - 하단 버튼 카운트 표시(선택된 개수)
 */
(function () {
  "use strict";

  function qs(sel, root) {
    return (root || document).querySelector(sel);
  }

  function qsa(sel, root) {
    return Array.prototype.slice.call((root || document).querySelectorAll(sel));
  }

  function countChecked(items) {
    return items.filter(function (el) { return el && el.checked; }).length;
  }

  function syncCount() {
    var items = qsa("#pokeMainList .cart-item__checkbox");
    var countEl = qs("#pokeGiftCount");
    if (!countEl) return;
    countEl.textContent = String(countChecked(items));
  }

  function syncSelectAllState() {
    var all = qs("#pokeSelectAll");
    if (!all) return;

    var items = qsa("#pokeMainList .cart-item__checkbox");
    if (items.length === 0) {
      all.checked = false;
      all.indeterminate = false;
      return;
    }

    var checked = countChecked(items);
    all.checked = checked === items.length;
    all.indeterminate = checked > 0 && checked < items.length;
  }

  document.addEventListener("DOMContentLoaded", function () {
    var backBtn = qs("#pokeListBackBtn");
    if (backBtn) {
      backBtn.addEventListener("click", function () {
        window.history.back();
      });
    }

    var selectAll = qs("#pokeSelectAll");
    if (selectAll) {
      selectAll.addEventListener("change", function () {
        var items = qsa("#pokeMainList .cart-item__checkbox");
        items.forEach(function (cb) { cb.checked = selectAll.checked; });
        selectAll.indeterminate = false;
        syncCount();
      });
    }

    qsa("#pokeMainList .cart-item__checkbox").forEach(function (cb) {
      cb.addEventListener("change", function () {
        syncSelectAllState();
        syncCount();
      });
    });

    var giftBtn = qs("#pokeGiftSubmitBtn");
    if (giftBtn) {
      giftBtn.addEventListener("click", function (e) {
        // 표시용: 동작 없음
        e.preventDefault();
      });
    }

    syncSelectAllState();
    syncCount();
  });
})();

