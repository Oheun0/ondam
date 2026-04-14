(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    var root = document.getElementById("favoriteListPageRoot");
    if (!root) return;

    var backBtn = document.getElementById("appBackHeaderBtn");
    if (backBtn) {
      backBtn.removeAttribute("onclick");
      backBtn.onclick = null;
      backBtn.addEventListener("click", function (e) {
        e.preventDefault();
        var ctx = document.body.getAttribute("data-context-path") || "";
        var referrer = document.referrer;
        if (referrer && referrer.indexOf(window.location.host) !== -1) {
          window.history.back();
        } else {
          window.location.href = ctx + "/mypage";
        }
      });
    }

    var sortToggleBtn = document.getElementById("favoriteSortToggleBtn");
    var sortDropdown = document.getElementById("favoriteSortDropdown");
    var sortSelectedText = document.getElementById("favoriteSortSelectedText");
    var sortOptions = root.querySelectorAll("#favoriteSortDropdown .filter-option");
    var partChips = root.querySelectorAll(".favorite-part-chip");
    var isHelperMode = document.body.getAttribute("data-helper-mode") === "true";

    // ✅ 추가: 현재 URL에서 targetUserNo 꺼내기
    var urlParams = new URLSearchParams(window.location.search);
    var targetUserNo = urlParams.get("targetUserNo") || "";

    // ✅ 추가: URL 빌더 함수
    function buildWishUrl(sort, part) {
      var ctx = document.body.getAttribute("data-context-path") || "";
      var url = ctx + "/wish?action=list&sort=" + encodeURIComponent(sort);
      if (targetUserNo) url += "&targetUserNo=" + encodeURIComponent(targetUserNo);
      if (part) url += "&part=" + encodeURIComponent(part);
      return url;
    }

    var grid = root.querySelector(".product-grid");
    if (!grid) return;

    root.querySelectorAll(".product-grid-wish-btn").forEach(function (btn) {
      btn.classList.add("is-active");
      btn.setAttribute("aria-pressed", "true");
      btn.setAttribute("aria-label", "찜 해제");
      var icon = btn.querySelector("span.material-icons-outlined, span.material-icons");
      if (!icon) return;
      icon.classList.remove("material-icons-outlined");
      icon.classList.add("material-icons");
      icon.textContent = "favorite";
      if (isHelperMode) {
        btn.disabled = true;
        btn.style.pointerEvents = "none";
        btn.style.opacity = "0.5";
      }
    });

    var cards = function () {
      return Array.prototype.slice.call(grid.querySelectorAll(".product-card"));
    };

    var currentSort = "담은순";
    var selectedParts = new Set();

    root.querySelectorAll(".product-grid-wish-btn").forEach(function (btn) {
      btn.addEventListener("click", function (e) {
        e.stopPropagation();
      });
    });

    grid.addEventListener("click", function (e) {
      var wishBtn = e.target.closest(".product-grid-wish-btn");
      if (!wishBtn || !grid.contains(wishBtn)) return;
      e.preventDefault();
      e.stopPropagation();
      if (isHelperMode) return;
      wishBtn.classList.toggle("is-active");
      var on = wishBtn.classList.contains("is-active");
      wishBtn.setAttribute("aria-pressed", on ? "true" : "false");
      wishBtn.setAttribute("aria-label", on ? "찜 해제" : "찜하기");
      var icon = wishBtn.querySelector("span.material-icons-outlined, span.material-icons");
      if (!icon) return;
      if (on) {
        icon.classList.remove("material-icons-outlined");
        icon.classList.add("material-icons");
        icon.textContent = "favorite";
      } else {
        icon.classList.remove("material-icons");
        icon.classList.add("material-icons-outlined");
        icon.textContent = "favorite_border";
      }
    });

    function positionFilterDropdown(btn, menu) {
      if (!btn || !menu || menu.classList.contains("hidden")) return;
      var rect = btn.getBoundingClientRect();
      var pad = 8;
      var vw = window.innerWidth;
      var vh = window.innerHeight;
      var fullBleed = window.matchMedia("(max-width: 768px)").matches;
      menu.style.visibility = "hidden";
      if (fullBleed) {
        menu.style.left = "0";
        menu.style.right = "0";
        menu.style.width = "100%";
        menu.style.maxWidth = "none";
      } else {
        var maxW = Math.min(330, vw - 16);
        menu.style.width = "";
        menu.style.right = "auto";
        menu.style.maxWidth = maxW + "px";
      }
      var mw = menu.offsetWidth;
      var mh = menu.offsetHeight;
      var top = rect.bottom + pad;
      if (top + mh > vh - pad) top = rect.top - mh - pad;
      if (top < pad) top = pad;
      menu.style.top = top + "px";
      if (!fullBleed) {
        var left = rect.left;
        if (left + mw > vw - pad) left = Math.max(pad, vw - mw - pad);
        if (left < pad) left = pad;
        menu.style.left = left + "px";
      }
      menu.style.visibility = "visible";
    }

    function closeSortDropdown() {
      if (!sortDropdown) return;
      sortDropdown.classList.add("hidden");
      sortDropdown.style.top = "";
      sortDropdown.style.left = "";
      sortDropdown.style.right = "";
      sortDropdown.style.maxWidth = "";
      sortDropdown.style.width = "";
      sortDropdown.style.visibility = "";
      if (sortToggleBtn) sortToggleBtn.setAttribute("aria-expanded", "false");
    }

    function openSortDropdown() {
      if (!sortToggleBtn || !sortDropdown) return;
      var willOpen = sortDropdown.classList.contains("hidden");
      if (!willOpen) { closeSortDropdown(); return; }
      sortDropdown.classList.remove("hidden");
      sortToggleBtn.setAttribute("aria-expanded", "true");
      requestAnimationFrame(function () {
        positionFilterDropdown(sortToggleBtn, sortDropdown);
      });
    }

    function parseCardMeta(card) {
      var wish = parseInt(card.getAttribute("data-favorite-wish-order"), 10);
      var popular = parseInt(card.getAttribute("data-favorite-popular"), 10);
      var price = parseInt(card.getAttribute("data-favorite-price"), 10);
      var dateMs = parseInt(card.getAttribute("data-favorite-date-ms"), 10);
      var part = card.getAttribute("data-favorite-part") || "";
      return {
        wish: isNaN(wish) ? 0 : wish,
        popular: isNaN(popular) ? 0 : popular,
        price: isNaN(price) ? 0 : price,
        dateMs: isNaN(dateMs) ? 0 : dateMs,
        part: part
      };
    }

    function applyFilters() {}

    if (sortToggleBtn && sortDropdown) {
      sortToggleBtn.addEventListener("click", function (e) {
        e.stopPropagation();
        openSortDropdown();
      });
    }

    // ✅ 수정: buildWishUrl 사용
    sortOptions.forEach(function (opt) {
      opt.addEventListener("click", function (e) {
        e.stopPropagation();
        var sort = opt.getAttribute("data-sort");
        if (!sort) return;
        closeSortDropdown();
        location.replace(buildWishUrl(sort, null));
      });
    });

    // ✅ 수정: buildWishUrl 사용
    partChips.forEach(function (chip) {
      chip.addEventListener("click", function () {
        var part = chip.getAttribute("data-part");
        if (!part) return;
        var sort = document.getElementById("favoriteSortSelectedText").textContent.trim();
        if (chip.classList.contains("active")) {
          location.replace(buildWishUrl(sort, null));
        } else {
          location.replace(buildWishUrl(sort, part));
        }
      });
    });

    document.addEventListener("click", function (e) {
      if (e.target.closest(".filter-dropdown-wrap")) return;
      closeSortDropdown();
    });

    if (sortDropdown) {
      sortDropdown.addEventListener("click", function (e) { e.stopPropagation(); });
    }

    window.addEventListener("resize", function () {
      if (sortDropdown && !sortDropdown.classList.contains("hidden") && sortToggleBtn) {
        positionFilterDropdown(sortToggleBtn, sortDropdown);
      }
    });

    window.addEventListener("scroll", function () {
      if (sortDropdown && !sortDropdown.classList.contains("hidden") && sortToggleBtn) {
        positionFilterDropdown(sortToggleBtn, sortDropdown);
      }
    }, true);

    applyFilters();
  });
})();