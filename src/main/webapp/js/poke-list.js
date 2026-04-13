/**
 * 조르기 목록
 * - 뒤로가기
 * - 전체 선택 ↔ 개별 선택 동기화
 * - 하단 버튼 카운트 표시(선택된 개수)
 * - 선물하기 버튼 → order 페이지 이동
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
    var contextPath = document.body.dataset.contextPath || '';

    // ── 뒤로가기 ──────────────────────────────────────
    var backBtn = qs("#pokeListBackBtn");
    if (backBtn) {
      backBtn.addEventListener("click", function () {
        window.history.back();
      });
    }

    // ── 전체 선택 ──────────────────────────────────────
    var selectAll = qs("#pokeSelectAll");
    if (selectAll) {
      selectAll.addEventListener("change", function () {
        var items = qsa("#pokeMainList .cart-item__checkbox");
        items.forEach(function (cb) { cb.checked = selectAll.checked; });
        selectAll.indeterminate = false;
        syncCount();
      });
    }

    // ── 개별 선택 ──────────────────────────────────────
    qsa("#pokeMainList .cart-item__checkbox").forEach(function (cb) {
      cb.addEventListener("change", function () {
        syncSelectAllState();
        syncCount();
      });
    });

    // ── 선물하기 버튼 ───────────────────────────────────
	var giftBtn = qs("#pokeGiftSubmitBtn");
	if (giftBtn) {
	  giftBtn.addEventListener("click", function (e) {
	    e.preventDefault();

	    var checkedPokeNos = [];
	    qsa("#pokeMainList .poke-item").forEach(function (article) {
	      var checkbox = article.querySelector(".cart-item__checkbox");
	      if (checkbox && checkbox.checked) {
	        checkedPokeNos.push(article.dataset.pokeId);
	      }
	    });

	    if (checkedPokeNos.length === 0) {
	      alert('선물할 상품을 선택해주세요.');
	      return;
	    }

	    var form = document.createElement('form');
	    form.method = 'POST';
	    form.action = contextPath + '/poke?action=giftOrder';

	    checkedPokeNos.forEach(function (pokeNo) {
	      var input = document.createElement('input');
	      input.type  = 'hidden';
	      input.name  = 'pokeNo';
	      input.value = pokeNo;
	      form.appendChild(input);
	    });

	    document.body.appendChild(form);
	    form.submit();
	  });
	}
	
	// ── 삭제하기 버튼 ───────────────────────────────────
	var removeSelectedBtn = document.getElementById("pokeRemoveSelectedBtn");
	if (removeSelectedBtn) {
	  removeSelectedBtn.addEventListener("click", function () {
	    var ctx = document.body.getAttribute("data-context-path") || "";
	    var checked = Array.prototype.slice.call(
	      document.querySelectorAll("#pokeMainList .cart-item__checkbox:checked")
	    );
	    if (checked.length === 0) return;

	    var form = document.createElement("form");
	    form.method = "POST";
	    form.action = ctx + "/poke?action=deleteSelected&from=list";

	    checked.forEach(function (cb) {
	      var article = cb.closest(".poke-item");
	      if (!article) return;
	      var input = document.createElement("input");
	      input.type = "hidden";
	      input.name = "pokeNo";
	      input.value = article.getAttribute("data-poke-id");
	      form.appendChild(input);
	    });

	    document.body.appendChild(form);
	    form.submit();
	  });
	}

    // ── 초기화 ─────────────────────────────────────────
    syncSelectAllState();
    syncCount();
  });
})();