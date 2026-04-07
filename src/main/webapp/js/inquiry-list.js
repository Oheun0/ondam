/**
 * 문의내역 — 더보기 드롭다운 (순수 JS)
 */
(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    var root = document.getElementById("inquiryListPageRoot");
    if (!root) return;

    var backBtn = document.getElementById("appBackHeaderBtn");
    if (backBtn) {
      backBtn.addEventListener("click", function () {
        if (window.history.length > 1) {
          window.history.back();
        } else {
          var ctx = document.body.getAttribute("data-context-path") || "";
          window.location.href = ctx + "/main";
        }
      });
    }

    function closeAllMenus() {
      root.querySelectorAll(".inquiry-list-dropdown").forEach(function (el) {
        el.classList.add("hidden");
        el.setAttribute("aria-hidden", "true");
      });
      root.querySelectorAll(".inquiry-list-menu-btn").forEach(function (btn) {
        btn.setAttribute("aria-expanded", "false");
      });
    }

    root.querySelectorAll(".inquiry-list-menu-btn").forEach(function (btn) {
      btn.addEventListener("click", function (e) {
        e.stopPropagation();
        var card = btn.closest(".inquiry-list-card");
        var wrap = btn.closest(".inquiry-list-menu-wrap");
        var menu = wrap ? wrap.querySelector(".inquiry-list-dropdown") : null;
        if (!menu) return;
        var wasOpen = !menu.classList.contains("hidden");
        closeAllMenus();
        if (!wasOpen) {
          var status = card ? card.getAttribute("data-inquiry-status") : "waiting";
          var editItem = menu.querySelector('[data-menu-action="edit"]');
          if (editItem) {
            editItem.style.display = status === "done" ? "none" : "";
          }
          menu.classList.remove("hidden");
          menu.setAttribute("aria-hidden", "false");
          btn.setAttribute("aria-expanded", "true");
        }
      });
    });

    document.addEventListener("click", function (e) {
      if (e.target.closest(".inquiry-list-menu-wrap")) return;
      closeAllMenus();
    });

    root.querySelectorAll(".inquiry-list-dropdown__item").forEach(function (item) {
      item.addEventListener("click", function () {
        closeAllMenus();
      });
    });
  });
})();

