(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    var ctx = document.body.getAttribute("data-context-path") || "";

    /* 뒤로가기 */
    var backBtn = document.getElementById("pokeSentBackBtn");
    if (backBtn) {
      backBtn.addEventListener("click", function () {
        if (window.history.length > 1) history.back();
        else location.href = ctx + "/group";
      });
    }

    var sentList    = document.getElementById("pokeSentList");
    var selectAll   = document.getElementById("pokeSentSelectAll");
    var removeBtn   = document.getElementById("pokeSentRemoveSelectedBtn");

    if (!sentList || !selectAll) return;

    function getItems() {
      return Array.prototype.slice.call(sentList.querySelectorAll(".poke-item"));
    }

    function getCheckboxes() {
      return getItems().map(function (el) {
        return el.querySelector(".cart-item__checkbox");
      }).filter(Boolean);
    }

    function syncSelectAll() {
      var boxes = getCheckboxes();
      if (!boxes.length) { selectAll.checked = false; selectAll.indeterminate = false; return; }
      var allOn = boxes.every(function (cb) { return cb.checked; });
      var anyOn = boxes.some(function  (cb) { return cb.checked; });
      selectAll.checked = allOn;
      selectAll.indeterminate = anyOn && !allOn;
    }

    /* 전체 선택 */
    selectAll.addEventListener("change", function () {
      getCheckboxes().forEach(function (cb) { cb.checked = selectAll.checked; });
      selectAll.indeterminate = false;
    });

    sentList.addEventListener("change", function (e) {
      if (e.target && e.target.classList.contains("cart-item__checkbox")) syncSelectAll();
    });

    /* 선택 삭제 */
    if (removeBtn) {
      removeBtn.addEventListener("click", function () {
        var checked = getCheckboxes().filter(function (cb) { return cb.checked; });
        if (checked.length === 0) return;

        var form = document.createElement("form");
        form.method = "POST";
        form.action = ctx + "/poke?action=deleteSelected&from=sent";

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

    syncSelectAll();
  });
})();