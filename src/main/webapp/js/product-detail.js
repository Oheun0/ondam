document.addEventListener("DOMContentLoaded", function () {
  const openCartSheetBtn = document.getElementById("openCartSheetBtn");
  const openBuySheetBtn = document.getElementById("openBuySheetBtn");
  const detailSheetDim = document.getElementById("detailSheetDim");
  const detailOptionSheet = document.getElementById("detailOptionSheet");

  const colorToggleBtn = document.getElementById("colorToggleBtn");
  const sizeToggleBtn = document.getElementById("sizeToggleBtn");
  const colorOptionPanel = document.getElementById("colorOptionPanel");
  const sizeOptionPanel = document.getElementById("sizeOptionPanel");

  const selectedColorText = document.getElementById("selectedColorText");
  const selectedSizeText = document.getElementById("selectedSizeText");

  const minusQtyBtn = document.getElementById("minusQtyBtn");
  const plusQtyBtn = document.getElementById("plusQtyBtn");
  const qtyValue = document.getElementById("qtyValue");
  const detailSheetOrderSummary = document.getElementById("detailSheetOrderSummary");
  const sheetOrderCount = document.getElementById("sheetOrderCount");
  const sheetOrderTotal = document.getElementById("sheetOrderTotal");

  const sizeRecommendBtn = document.getElementById("sizeRecommendBtn");
  const sizeRecommendResult = document.getElementById("sizeRecommendResult");

  const openPokeFromSheetBtn = document.getElementById("openPokeFromSheetBtn");
  const pokeModalDim = document.getElementById("pokeModalDim");
  const pokeModal = document.getElementById("pokeModal");
  const closePokeModalBtn = document.getElementById("closePokeModalBtn");

  const openGiftFromSheetBtn = document.getElementById("openGiftFromSheetBtn");
  const giftModalDim = document.getElementById("giftModalDim");
  const giftModal = document.getElementById("giftModal");
  const closeGiftModalBtn = document.getElementById("closeGiftModalBtn");

  const openShareFromSheetBtn = document.getElementById("openShareFromSheetBtn");
  const shareModalDim = document.getElementById("shareModalDim");
  const shareModal = document.getElementById("shareModal");
  const shareCopyLinkBtn = document.getElementById("shareCopyLinkBtn");
  const shareMoreBtn = document.getElementById("shareMoreBtn");

  const sheetAddCartBtn = document.getElementById("sheetAddCartBtn");
  const sheetBuyNowBtn = document.getElementById("sheetBuyNowBtn");

  let quantity = 1;

  var optionToastActive = false;
  var optionToastDismissTimer = null;
  var optionToastAnimFallbackTimer = null;

  function isOptionSelected() {
    if (!selectedColorText || !selectedSizeText) return false;
    return (
      !selectedColorText.classList.contains("detail-selected-value--placeholder") &&
      !selectedSizeText.classList.contains("detail-selected-value--placeholder")
    );
  }

  function showOptionErrorToast() {
    var el = document.getElementById("option-toast");
    if (!el || optionToastActive) return;

    optionToastActive = true;
    clearTimeout(optionToastDismissTimer);
    clearTimeout(optionToastAnimFallbackTimer);

    el.classList.remove("hidden", "option-toast--hiding", "option-toast--show");
    el.setAttribute("aria-hidden", "false");

    requestAnimationFrame(function () {
      requestAnimationFrame(function () {
        el.classList.add("option-toast--show");
      });
    });

    optionToastDismissTimer = setTimeout(function () {
      el.classList.remove("option-toast--show");
      el.classList.add("option-toast--hiding");

      var finished = false;
      function cleanup() {
        if (finished) return;
        finished = true;
        el.removeEventListener("transitionend", onTransitionEnd);
        clearTimeout(optionToastAnimFallbackTimer);
        el.classList.add("hidden");
        el.classList.remove("option-toast--hiding");
        el.setAttribute("aria-hidden", "true");
        optionToastActive = false;
      }

      function onTransitionEnd(e) {
        if (e.target !== el) return;
        if (e.propertyName !== "opacity" && e.propertyName !== "transform") return;
        cleanup();
      }

      el.addEventListener("transitionend", onTransitionEnd);
      optionToastAnimFallbackTimer = setTimeout(cleanup, 400);
    }, 2000);
  }

  const detailCarouselSyncs = [];

  function bindImageCarousel(scrollEl, indicatorEl) {
    if (!scrollEl || !indicatorEl) return;
    const dots = indicatorEl.querySelectorAll(".detail-hero-dot");
    if (dots.length === 0) return;

    function syncDots() {
      const w = scrollEl.offsetWidth;
      if (w <= 0) return;
      const i = Math.min(dots.length - 1, Math.max(0, Math.round(scrollEl.scrollLeft / w)));
      dots.forEach(function (dot, idx) {
        const on = idx === i;
        dot.classList.toggle("active", on);
        dot.setAttribute("aria-current", on ? "true" : "false");
      });
    }

    scrollEl.addEventListener("scroll", syncDots, { passive: true });
    window.addEventListener("resize", syncDots);
    dots.forEach(function (dot) {
      dot.addEventListener("click", function () {
        const idx = parseInt(dot.getAttribute("data-slide-index"), 10);
        if (Number.isNaN(idx)) return;
        const w = scrollEl.offsetWidth;
        scrollEl.scrollTo({ left: idx * w, behavior: "smooth" });
      });
    });

    detailCarouselSyncs.push(syncDots);
    syncDots();
  }

  bindImageCarousel(document.getElementById("detailImageScroll"), document.getElementById("detailImageIndicator"));

  function syncSheetOptionPanels() {
    if (!colorOptionPanel || !sizeOptionPanel) return;
    var colorOpen = !colorOptionPanel.classList.contains("hidden");
    var sizeOpen = !sizeOptionPanel.classList.contains("hidden");
    if (colorToggleBtn) {
      colorToggleBtn.setAttribute("aria-expanded", colorOpen ? "true" : "false");
    }
    if (sizeToggleBtn) {
      sizeToggleBtn.setAttribute("aria-expanded", sizeOpen ? "true" : "false");
    }
  }

  function openSheet() {
    document.body.style.overflow = "hidden";
    detailSheetDim.classList.remove("hidden");
    detailOptionSheet.classList.remove("hidden");
    requestAnimationFrame(function () {
      requestAnimationFrame(function () {
        detailCarouselSyncs.forEach(function (fn) {
          fn();
        });
        syncSheetOptionPanels();
      });
    });
  }

  function closeSheet() {
    document.body.style.overflow = "";
    detailSheetDim.classList.add("hidden");
    detailOptionSheet.classList.add("hidden");
    if (colorOptionPanel) colorOptionPanel.classList.add("hidden");
    if (sizeOptionPanel) sizeOptionPanel.classList.add("hidden");
    syncSheetOptionPanels();
  }

  function clearPokePersonSelection() {
    document.querySelectorAll(".poke-person-btn").forEach(function (btn) {
      btn.classList.remove("active");
      btn.setAttribute("aria-selected", "false");
    });
  }

  function clearGiftPersonSelection() {
    document.querySelectorAll(".gift-person-btn").forEach(function (btn) {
      btn.classList.remove("active");
      btn.setAttribute("aria-selected", "false");
    });
  }

  function openPokeModal() {
    closeGiftModal();
    closeShareModal();
    closeSheet();
    if (!pokeModalDim || !pokeModal) return;
    clearPokePersonSelection();
    document.body.style.overflow = "hidden";
    pokeModalDim.classList.remove("hidden");
    pokeModal.classList.remove("hidden");
  }

  function closePokeModal() {
    if (pokeModalDim) pokeModalDim.classList.add("hidden");
    if (pokeModal) pokeModal.classList.add("hidden");
    document.body.style.overflow = "";
  }

  function openGiftModal() {
    closePokeModal();
    closeSheet();
    if (!giftModalDim || !giftModal) return;
    clearGiftPersonSelection();
    document.body.style.overflow = "hidden";
    giftModalDim.classList.remove("hidden");
    giftModal.classList.remove("hidden");
  }

  function closeGiftModal() {
    if (giftModalDim) giftModalDim.classList.add("hidden");
    if (giftModal) giftModal.classList.add("hidden");
    document.body.style.overflow = "";
  }

  function openShareModal() {
    closePokeModal();
    closeGiftModal();
    closeSheet();
    if (!shareModalDim || !shareModal) return;
    document.body.style.overflow = "hidden";
    shareModalDim.classList.remove("hidden");
    shareModal.classList.remove("hidden");
  }

  function closeShareModal() {
    if (shareModalDim) shareModalDim.classList.add("hidden");
    if (shareModal) shareModal.classList.add("hidden");
    document.body.style.overflow = "";
  }

  if (openCartSheetBtn) {
    openCartSheetBtn.addEventListener("click", openSheet);
  }

  if (openBuySheetBtn) {
    openBuySheetBtn.addEventListener("click", openSheet);
  }

  document.querySelectorAll("[data-open-detail-option-sheet]").forEach(function (btn) {
    btn.addEventListener("click", function (e) {
      e.preventDefault();
      openSheet();
    });
  });

  if (detailSheetDim) {
    detailSheetDim.addEventListener("click", closeSheet);
  }

  const sheetWishlistBtn = document.getElementById("sheetWishlistBtn");
  if (sheetWishlistBtn) {
    var sheetWishIcon = sheetWishlistBtn.querySelector(".detail-wish-icon");
    sheetWishlistBtn.addEventListener("click", function () {
      var on = sheetWishlistBtn.classList.toggle("detail-action-item--wish-on");
      sheetWishlistBtn.setAttribute("aria-pressed", on ? "true" : "false");
      sheetWishlistBtn.setAttribute("aria-label", on ? "찜 해제" : "찜하기");
      if (!sheetWishIcon) return;
      if (on) {
        sheetWishIcon.classList.remove("material-icons-outlined");
        sheetWishIcon.classList.add("material-icons");
        sheetWishIcon.textContent = "favorite";
      } else {
        sheetWishIcon.classList.remove("material-icons");
        sheetWishIcon.classList.add("material-icons-outlined");
        sheetWishIcon.textContent = "favorite_border";
      }
    });
  }

  if (openPokeFromSheetBtn) {
    openPokeFromSheetBtn.addEventListener("click", function (e) {
      if (!isOptionSelected()) {
        e.preventDefault();
        e.stopPropagation();
        showOptionErrorToast();
        return;
      }
      openPokeModal();
    });
  }

  if (openGiftFromSheetBtn) {
    openGiftFromSheetBtn.addEventListener("click", function (e) {
      if (!isOptionSelected()) {
        e.preventDefault();
        e.stopPropagation();
        showOptionErrorToast();
        return;
      }
      openGiftModal();
    });
  }

  function onSheetCartOrBuyClick(e) {
    if (!isOptionSelected()) {
      e.preventDefault();
      e.stopPropagation();
      showOptionErrorToast();
      return;
    }
  }

  if (sheetAddCartBtn) {
    sheetAddCartBtn.addEventListener("click", onSheetCartOrBuyClick);
  }

  if (sheetBuyNowBtn) {
    sheetBuyNowBtn.addEventListener("click", onSheetCartOrBuyClick);
  }

  if (openShareFromSheetBtn) {
    openShareFromSheetBtn.addEventListener("click", function () {
      openShareModal();
    });
  }

  if (pokeModalDim) {
    pokeModalDim.addEventListener("click", closePokeModal);
  }

  if (pokeModal) {
    pokeModal.addEventListener("click", function (e) {
      if (e.target === pokeModal) {
        closePokeModal();
      }
    });
  }

  if (closePokeModalBtn) {
    closePokeModalBtn.addEventListener("click", closePokeModal);
  }

  if (giftModalDim) {
    giftModalDim.addEventListener("click", closeGiftModal);
  }

  if (giftModal) {
    giftModal.addEventListener("click", function (e) {
      if (e.target === giftModal) {
        closeGiftModal();
      }
    });
  }

  if (closeGiftModalBtn) {
    closeGiftModalBtn.addEventListener("click", closeGiftModal);
  }

  if (shareModalDim) {
    shareModalDim.addEventListener("click", closeShareModal);
  }

  if (shareModal) {
    shareModal.addEventListener("click", function (e) {
      if (e.target === shareModal) {
        closeShareModal();
      }
    });
  }

  function copyShareUrlToClipboard() {
    var url = window.location.href;
    if (navigator.clipboard && navigator.clipboard.writeText) {
      navigator.clipboard.writeText(url).catch(function () {
        fallbackCopyText(url);
      });
      return;
    }
    fallbackCopyText(url);
  }

  function fallbackCopyText(text) {
    var ta = document.createElement("textarea");
    ta.value = text;
    ta.setAttribute("readonly", "");
    ta.style.position = "fixed";
    ta.style.left = "-9999px";
    document.body.appendChild(ta);
    ta.select();
    try {
      document.execCommand("copy");
    } catch (err) {
      /* ignore */
    }
    document.body.removeChild(ta);
  }

  if (shareCopyLinkBtn) {
    shareCopyLinkBtn.addEventListener("click", function () {
      copyShareUrlToClipboard();
    });
  }

  if (shareMoreBtn) {
    shareMoreBtn.addEventListener("click", function () {
      if (navigator.share) {
        navigator
          .share({
            title: document.title,
            url: window.location.href,
          })
          .catch(function () {
            /* 사용자 취소 등 */
          });
      }
    });
  }

  document.querySelectorAll(".poke-person-btn").forEach(function (btn) {
    btn.setAttribute("aria-selected", "false");
    btn.addEventListener("click", function () {
      document.querySelectorAll(".poke-person-btn").forEach(function (b) {
        b.classList.remove("active");
        b.setAttribute("aria-selected", "false");
      });
      btn.classList.add("active");
      btn.setAttribute("aria-selected", "true");
    });
  });

  document.querySelectorAll(".gift-person-btn").forEach(function (btn) {
    btn.setAttribute("aria-selected", "false");
    btn.addEventListener("click", function () {
      document.querySelectorAll(".gift-person-btn").forEach(function (b) {
        b.classList.remove("active");
        b.setAttribute("aria-selected", "false");
      });
      btn.classList.add("active");
      btn.setAttribute("aria-selected", "true");
    });
  });

  function blurAfterToggle(btn) {
    requestAnimationFrame(function () {
      requestAnimationFrame(function () {
        if (btn && typeof btn.blur === "function") {
          btn.blur();
        }
      });
    });
  }

  if (colorToggleBtn) {
    colorToggleBtn.addEventListener("click", function () {
      var willOpen = colorOptionPanel.classList.contains("hidden");
      colorOptionPanel.classList.add("hidden");
      sizeOptionPanel.classList.add("hidden");
      if (willOpen) colorOptionPanel.classList.remove("hidden");
      syncSheetOptionPanels();
      blurAfterToggle(colorToggleBtn);
    });
  }

  if (sizeToggleBtn) {
    sizeToggleBtn.addEventListener("click", function () {
      var willOpen = sizeOptionPanel.classList.contains("hidden");
      colorOptionPanel.classList.add("hidden");
      sizeOptionPanel.classList.add("hidden");
      if (willOpen) sizeOptionPanel.classList.remove("hidden");
      syncSheetOptionPanels();
      blurAfterToggle(sizeToggleBtn);
    });
  }

  document.querySelectorAll("[data-color]").forEach((button) => {
    button.addEventListener("click", function () {
      document.querySelectorAll("[data-color]").forEach((item) => {
        item.classList.remove("active");
        item.setAttribute("aria-selected", "false");
      });
      this.classList.add("active");
      this.setAttribute("aria-selected", "true");
      selectedColorText.textContent = this.dataset.color;
      selectedColorText.classList.remove("detail-selected-value--placeholder");
      colorOptionPanel.classList.add("hidden");
      syncSheetOptionPanels();
    });
  });

  document.querySelectorAll("[data-size]").forEach((button) => {
    button.addEventListener("click", function () {
      document.querySelectorAll("[data-size]").forEach((item) => {
        item.classList.remove("active");
        item.setAttribute("aria-selected", "false");
      });
      this.classList.add("active");
      this.setAttribute("aria-selected", "true");
      selectedSizeText.textContent = this.dataset.size;
      selectedSizeText.classList.remove("detail-selected-value--placeholder");
      sizeOptionPanel.classList.add("hidden");
      syncSheetOptionPanels();
    });
  });

  syncSheetOptionPanels();

  function formatWon(amount) {
    return amount.toLocaleString("ko-KR") + "원";
  }

  function syncSheetOrderSummary() {
    if (!sheetOrderCount || !sheetOrderTotal) return;
    var unit = 39000;
    if (detailSheetOrderSummary) {
      var raw = parseInt(detailSheetOrderSummary.getAttribute("data-unit-price"), 10);
      if (!Number.isNaN(raw) && raw >= 0) {
        unit = raw;
      }
    }
    sheetOrderCount.textContent = "총 " + quantity + "개";
    sheetOrderTotal.textContent = formatWon(unit * quantity);
  }

  function syncQtyStepper() {
    if (qtyValue) {
      qtyValue.textContent = String(quantity);
    }
    if (minusQtyBtn) {
      var atMin = quantity <= 1;
      minusQtyBtn.disabled = atMin;
      minusQtyBtn.setAttribute("aria-disabled", atMin ? "true" : "false");
    }
    syncSheetOrderSummary();
  }

  if (minusQtyBtn) {
    minusQtyBtn.addEventListener("click", function () {
      if (quantity <= 1) return;
      quantity -= 1;
      syncQtyStepper();
    });
  }

  if (plusQtyBtn) {
    plusQtyBtn.addEventListener("click", function () {
      quantity += 1;
      syncQtyStepper();
    });
  }

  syncQtyStepper();

  const detailTabBtns = document.querySelectorAll(".detail-tab-btn");
  const detailTabPanels = document.querySelectorAll("[data-detail-tab-panel]");

  function setDetailTab(tabId) {
    detailTabBtns.forEach(function (btn) {
      var on = btn.getAttribute("data-detail-tab") === tabId;
      btn.classList.toggle("active", on);
      btn.setAttribute("aria-selected", on ? "true" : "false");
      btn.setAttribute("tabindex", on ? "0" : "-1");
    });
    detailTabPanels.forEach(function (panel) {
      var on = panel.getAttribute("data-detail-tab-panel") === tabId;
      panel.classList.toggle("hidden", !on);
      panel.setAttribute("aria-hidden", on ? "false" : "true");
    });
  }

  detailTabBtns.forEach(function (btn) {
    btn.addEventListener("click", function () {
      var tabId = btn.getAttribute("data-detail-tab");
      if (!tabId) return;
      setDetailTab(tabId);
    });
  });

  document.querySelectorAll(".detail-review-help-btn").forEach(function (btn) {
    var countEl = btn.querySelector(".detail-review-help-count");
    if (!countEl) return;
    btn.addEventListener("click", function () {
      var n = parseInt(countEl.textContent, 10);
      if (Number.isNaN(n)) n = 0;
      countEl.textContent = String(n + 1);
    });
  });

  document.querySelectorAll(".detail-review-sort-btn").forEach(function (btn) {
    btn.addEventListener("click", function () {
      var sort = btn.getAttribute("data-sort");
      document.querySelectorAll(".detail-review-sort-btn").forEach(function (b) {
        var on = b.getAttribute("data-sort") === sort;
        b.classList.toggle("active", on);
        b.setAttribute("aria-pressed", on ? "true" : "false");
      });
    });
  });

  if (sizeRecommendBtn && sizeRecommendResult) {
    sizeRecommendBtn.addEventListener("click", function () {
      sizeRecommendBtn.classList.add("hidden");
      sizeRecommendResult.classList.remove("hidden");
    });
  }

  var appBackHeaderBtn = document.getElementById("appBackHeaderBtn");
  if (appBackHeaderBtn) {
    appBackHeaderBtn.addEventListener("click", function () {
      if (window.history.length > 1) {
        window.history.back();
      } else {
        var ctx = document.body.getAttribute("data-context-path") || "";
        window.location.href = ctx + "/main";
      }
    });
  }

  var relatedCard = document.querySelector(".detail-related-card");
  if (relatedCard) {
    relatedCard.addEventListener("click", function (e) {
      var wishBtn = e.target.closest(".related-wish-btn");
      if (!wishBtn || !relatedCard.contains(wishBtn)) return;
      e.preventDefault();
      e.stopPropagation();
      var on = !wishBtn.classList.contains("is-active");
      wishBtn.classList.toggle("is-active", on);
      wishBtn.setAttribute("aria-pressed", on ? "true" : "false");
      wishBtn.setAttribute("aria-label", on ? "찜 해제" : "찜하기");
      var icon = wishBtn.querySelector("span.material-icons-outlined, span.material-icons");
      if (icon) {
        if (on) {
          icon.className = "material-icons";
          icon.textContent = "favorite";
        } else {
          icon.className = "material-icons-outlined";
          icon.textContent = "favorite_border";
        }
      }
    });
  }
});