/**
 * 나의 리뷰 — 탭 전환, 뒤로가기, 작성한 후기 더보기 드롭다운 (순수 JS)
 */
(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    var root = document.getElementById("reviewMyPageRoot");
    if (!root) return;

    var backBtn = document.getElementById("appBackHeaderBtn");
    if (backBtn) {
      backBtn.addEventListener("click", function (e) {
        e.preventDefault(); 
        
        var ctx = document.body.getAttribute("data-context-path") || "";
        window.location.href = ctx + "/mypage";
      });
    }

    var tabBtns = root.querySelectorAll(".review-my-tab-btn");
    var panels = root.querySelectorAll("[data-review-my-panel]");

    function setTab(tabId) {
      tabBtns.forEach(function (btn) {
        var on = btn.getAttribute("data-review-my-tab") === tabId;
        btn.classList.toggle("active", on);
        btn.setAttribute("aria-selected", on ? "true" : "false");
        btn.setAttribute("tabindex", on ? "0" : "-1");
      });
      panels.forEach(function (panel) {
        var on = panel.getAttribute("data-review-my-panel") === tabId;
        panel.classList.toggle("hidden", !on);
        panel.setAttribute("aria-hidden", on ? "false" : "true");
      });
    }

    tabBtns.forEach(function (btn) {
      btn.addEventListener("click", function () {
        var id = btn.getAttribute("data-review-my-tab");
        if (id) setTab(id);
      });
    });

    function closeAllMenus() {
      root.querySelectorAll(".review-my-dropdown").forEach(function (el) {
        el.classList.add("hidden");
        el.setAttribute("aria-hidden", "true");
      });
      root.querySelectorAll(".review-my-more-btn").forEach(function (b) {
        b.setAttribute("aria-expanded", "false");
      });
    }

    root.querySelectorAll(".review-my-more-btn").forEach(function (btn) {
      btn.addEventListener("click", function (e) {
        e.stopPropagation();
        var wrap = btn.closest(".review-my-more-wrap");
        var menu = wrap ? wrap.querySelector(".review-my-dropdown") : null;
        if (!menu) return;
        var wasOpen = !menu.classList.contains("hidden");
        closeAllMenus();
        if (!wasOpen) {
          menu.classList.remove("hidden");
          menu.setAttribute("aria-hidden", "false");
          btn.setAttribute("aria-expanded", "true");
        }
      });
    });

    document.addEventListener("click", function (e) {
      if (e.target.closest(".review-my-more-wrap")) return;
      closeAllMenus();
    });

    root.querySelectorAll(".review-my-dropdown__item").forEach(function (item) {
      item.addEventListener("click", function () {
        closeAllMenus();
      });
    });
  });
})();
