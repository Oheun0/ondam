var successToastActive = false;
var successToastDismissTimer = null;

function showSuccessToast(message) {
  var el = document.getElementById("success-toast");
  if (!el || successToastActive) return;

  document.getElementById("success-toast-text").innerText = message;
  successToastActive = true;

  el.style.setProperty("display", "flex", "important"); // force overrides display
  el.style.opacity = "1";
  el.style.visibility = "visible";
  el.setAttribute("aria-hidden", "false");

  clearTimeout(successToastDismissTimer);
  successToastDismissTimer = setTimeout(function () {
    el.style.opacity = "0";
    setTimeout(function () {
      el.style.setProperty("display", "none", "important");
      el.setAttribute("aria-hidden", "true");
      successToastActive = false;
    }, 300);
  }, 2000);
}

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
  const shareKakaoBtn = document.getElementById("shareKakaoBtn");
  const shareCopyLinkBtn = document.getElementById("shareCopyLinkBtn");
  const shareMoreBtn = document.getElementById("shareMoreBtn");

  const sheetAddCartBtn = document.getElementById("sheetAddCartBtn");
  const sheetBuyNowBtn = document.getElementById("sheetBuyNowBtn");
  const detailImageLightbox = document.getElementById("detailImageLightbox");
  const detailImageLightboxScroll = document.getElementById("detailImageLightboxScroll");
  const detailImageLightboxClose = document.getElementById("detailImageLightboxClose");

  let quantity = 1;

  var optionToastActive = false;
  var optionToastDismissTimer = null;
  var optionToastAnimFallbackTimer = null;

  function showTopToast(message, type) {
    var el = document.getElementById("option-toast");
    if (!el) return;
    var textEl = el.querySelector(".option-toast__text");
    var iconEl = el.querySelector(".option-toast__icon");

    optionToastActive = true;
    clearTimeout(optionToastDismissTimer);
    clearTimeout(optionToastAnimFallbackTimer);
    el.classList.remove("option-toast--success", "option-toast--error");
    el.classList.add(type === "success" ? "option-toast--success" : "option-toast--error");
    if (textEl) textEl.textContent = message || "";
    if (iconEl) iconEl.textContent = type === "success" ? "check_circle" : "error";

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
    }, 1800);
  }

  function isOptionSelected() {
    if (!selectedColorText || !selectedSizeText) return false;
    return (
      !selectedColorText.classList.contains("detail-selected-value--placeholder") &&
      !selectedSizeText.classList.contains("detail-selected-value--placeholder")
    );
  }

  function showOptionErrorToast() {
    showTopToast("먼저 색상과 사이즈를 골라주세요", "error");
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

  function openImageLightbox(startIndex) {
    if (!detailImageLightbox || !detailImageLightboxScroll) return;
    var heroImages = Array.prototype.slice.call(document.querySelectorAll(".detail-hero-slide img"));
    if (!heroImages.length) return;
    detailImageLightboxScroll.innerHTML = "";
    heroImages.forEach(function (img, idx) {
      var slide = document.createElement("div");
      slide.className = "detail-image-lightbox-slide";
      var clone = document.createElement("img");
      clone.src = img.getAttribute("src");
      clone.alt = img.getAttribute("alt") || ("확대 이미지 " + (idx + 1));
      slide.appendChild(clone);
      detailImageLightboxScroll.appendChild(slide);
    });
    document.body.style.overflow = "hidden";
    detailImageLightbox.classList.remove("hidden");
    requestAnimationFrame(function () {
      var width = detailImageLightboxScroll.offsetWidth || window.innerWidth || 1;
      var targetLeft = Math.max(0, startIndex) * width;
      detailImageLightboxScroll.scrollTo({ left: targetLeft, behavior: "auto" });
    });
  }

  function closeImageLightbox() {
    if (!detailImageLightbox || !detailImageLightboxScroll) return;
    detailImageLightbox.classList.add("hidden");
    detailImageLightboxScroll.innerHTML = "";
    document.body.style.overflow = "";
  }

  document.querySelectorAll(".detail-hero-slide img").forEach(function (img, idx) {
    img.addEventListener("click", function () {
      openImageLightbox(idx);
    });
  });

  if (detailImageLightboxClose) {
    detailImageLightboxClose.addEventListener("click", function (e) {
      e.preventDefault();
      closeImageLightbox();
    });
  }

  if (detailImageLightbox) {
    detailImageLightbox.addEventListener("click", function (e) {
      if (e.target === detailImageLightbox) {
        closeImageLightbox();
      }
    });
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

      // 초기 상태는 이제 JSP에서 처리하므로, JS에서는 클릭 이벤트만 확실히 잡아주면 됩니다.
      sheetWishlistBtn.addEventListener("click", function () {
          var ctx = document.body.getAttribute("data-context-path") || "";
          if (!document.body.dataset.loginUser) {
              window.location.href = ctx + "/login";
              return;
          }

          // 시각적 토글
          var on = sheetWishlistBtn.classList.toggle("detail-action-item--wish-on");
          sheetWishlistBtn.setAttribute("aria-pressed", on ? "true" : "false");
          
          if (sheetWishIcon) {
              if (on) {
                  sheetWishIcon.classList.replace("material-icons-outlined", "material-icons");
                  sheetWishIcon.textContent = "favorite";
              } else {
                  sheetWishIcon.classList.replace("material-icons", "material-icons-outlined");
                  sheetWishIcon.textContent = "favorite_border";
              }
          }

          // 서버 통신 (기존 코드 유지)
          fetch(ctx + "/wish?action=toggle&productNo=" + PRODUCT_NO, { method: "POST" })
              .then(function(r) { return r.json(); })
              .then(function(data) {
                  // 서버 결과에 맞춰 최종 동기화 (data.wished 값 사용)
              });
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

      // set selected option values into form
      document.getElementById("pokeProductNo").value       = document.getElementById("hiddenProductNo").value;
      document.getElementById("pokeProductOptionNo").value = document.getElementById("hiddenOptionNo").value;
      document.getElementById("pokeQuantity").value        = document.getElementById("hiddenQuantity").value;
      document.getElementById("pokeFamilyNo").value        = document.getElementById("hiddenFamilyNo").value;

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
      sheetAddCartBtn.addEventListener("click", function (e) {
          if (!isOptionSelected()) {
              e.preventDefault();
              e.stopPropagation();
              showOptionErrorToast();
              return;
          }

          var color    = selectedColorText.textContent.trim();
          var size     = selectedSizeText.textContent.trim();
          var optionNo = OPTION_NO_MAP[color + "__" + size];

          if (!optionNo) {
              showOptionErrorToast();
              return;
          }

          var ctx = document.body.getAttribute("data-context-path") || "";
          var form = document.createElement("form");
          form.method = "POST";
          form.action = ctx + "/cart?action=add";

          [["productNo", PRODUCT_NO],
           ["productOptionNo", optionNo],
           ["quantity", quantity]].forEach(function (pair) {
              var input = document.createElement("input");
              input.type = "hidden";
              input.name = pair[0];
              input.value = pair[1];
              form.appendChild(input);
          });

          document.body.appendChild(form);
          form.submit();
      });
  }

  if (sheetBuyNowBtn) {
      sheetBuyNowBtn.addEventListener("click", function (e) {
          if (!isOptionSelected()) {
              e.preventDefault();
              e.stopPropagation();
              showOptionErrorToast();
              return;
          }

          var color    = selectedColorText.textContent.trim();
          var size     = selectedSizeText.textContent.trim();
          var optionNo = OPTION_NO_MAP[color + "__" + size];

          if (!optionNo) {
              showOptionErrorToast();
              return;
          }

          var ctx = document.body.getAttribute("data-context-path") || "";
          var form = document.createElement("form");
          form.method = "GET";  // payment page uses GET parameters
          form.action = ctx + "/payment";

          [
              ["productNo",        PRODUCT_NO],
              ["productOptionNo",  optionNo],
              ["quantity",         quantity]
          ].forEach(function (pair) {
              var input = document.createElement("input");
              input.type  = "hidden";
              input.name  = pair[0];
              input.value = pair[1];
              form.appendChild(input);
          });

          document.body.appendChild(form);
          form.submit();
      });
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

  function getShareMeta() {
    var titleEl = document.querySelector(".detail-product-name");
    var brandEl = document.querySelector(".detail-brand-link");
    var priceEl = document.querySelector(".detail-sale-price");
    var firstImg = document.querySelector(".detail-hero-slide img");
    var imageSrc = firstImg ? firstImg.getAttribute("src") : "";
    if (imageSrc && imageSrc.indexOf("http") !== 0) {
      imageSrc = window.location.origin + imageSrc;
    }
    return {
      title: titleEl ? titleEl.textContent.trim() : document.title,
      description: ((brandEl ? brandEl.textContent.trim() : "") + " " + (priceEl ? priceEl.textContent.trim() : "")).trim(),
      imageUrl: imageSrc,
      url: window.location.href
    };
  }

  function shareViaKakao() {
    var kakaoKey = document.body.getAttribute("data-kakao-js-key") || "";
    if (!window.Kakao || !kakaoKey) {
      showTopToast("카카오 공유 설정이 아직 없어요.", "error");
      return;
    }
    try {
      if (!window.Kakao.isInitialized()) {
        window.Kakao.init(kakaoKey);
      }
      var meta = getShareMeta();
      window.Kakao.Share.sendDefault({
        objectType: "feed",
        content: {
          title: meta.title || "온담 상품",
          description: meta.description || "온담에서 상품을 확인해보세요.",
          imageUrl: meta.imageUrl || (window.location.origin + (document.body.getAttribute("data-context-path") || "") + "/images/logo.png"),
          link: {
            mobileWebUrl: meta.url,
            webUrl: meta.url
          }
        },
        buttons: [
          {
            title: "상품 보러가기",
            link: {
              mobileWebUrl: meta.url,
              webUrl: meta.url
            }
          }
        ]
      });
    } catch (e) {
      showTopToast("카카오톡 공유를 실행하지 못했어요.", "error");
    }
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
      showTopToast("링크를 복사했어요.", "success");
    });
  }

  if (shareKakaoBtn) {
    shareKakaoBtn.addEventListener("click", function () {
      shareViaKakao();
    });
  }

  if (shareMoreBtn) {
    shareMoreBtn.addEventListener("click", function () {
      var meta = getShareMeta();
      if (navigator.share) {
        navigator
          .share({
            title: meta.title || document.title,
            text: meta.description || "",
            url: meta.url,
          })
          .catch(function () {
            /* user canceled */
          });
        return;
      }
      copyShareUrlToClipboard();
      showTopToast("공유를 지원하지 않아 링크를 복사했어요.", "success");
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

          // render sizes dynamically
          var selectedColor = this.dataset.color;
          var sizes = COLOR_SIZE_MAP[selectedColor] || [];
          var sizeList = document.getElementById("sizeOptionList");
          if (sizeList) {
              sizeList.innerHTML = "";
              sizes.forEach(function (sz) {
                  var btn = document.createElement("button");
                  btn.type = "button";
                  btn.className = "detail-option-row";
                  btn.setAttribute("data-size", sz);
                  btn.setAttribute("role", "option");
                  btn.setAttribute("aria-selected", "false");
                  btn.textContent = sz;
                  sizeList.appendChild(btn);

				  btn.addEventListener("click", function () {
				      sizeList.querySelectorAll(".detail-option-row").forEach(function (b) {
				          b.classList.remove("active");
				          b.setAttribute("aria-selected", "false");
				      });
				      btn.classList.add("active");
				      btn.setAttribute("aria-selected", "true");
				      selectedSizeText.textContent = sz;
				      selectedSizeText.classList.remove("detail-selected-value--placeholder");
				      sizeOptionPanel.classList.add("hidden");
				      syncSheetOptionPanels();

				      // sync stock and option number
					  var optKey = selectedColor + "__" + sz;
  				      var stock = OPTION_STOCK_MAP[optKey] !== undefined ? OPTION_STOCK_MAP[optKey] : 9999;
  				      detailOptionSheet.setAttribute("data-option-stock", stock);
  					  
  					  var optionNo = OPTION_NO_MAP[optKey];
  					      var hiddenOptionNoEl = document.getElementById("hiddenOptionNo");
  					      if (hiddenOptionNoEl && optionNo) {
  					          hiddenOptionNoEl.value = optionNo;
  					      }

  				      // disable actions if out of stock
  				      if (stock === 0) {
  				          if (sheetBuyNowBtn) {
  				              sheetBuyNowBtn.disabled = true;
  				              sheetBuyNowBtn.textContent = "품절";
  				          }
  				          if (sheetAddCartBtn) {
  				              sheetAddCartBtn.disabled = true;
  				          }
  				      } else {
  				          if (sheetBuyNowBtn) {
  				              sheetBuyNowBtn.disabled = false;
  				              sheetBuyNowBtn.textContent = "구매하기";
  				          }
  				          if (sheetAddCartBtn) {
  				              sheetAddCartBtn.disabled = false;
  				          }
  				      }

  				      // clamp quantity to available stock
  				      if (quantity > stock) {
  				          quantity = Math.max(1, stock);
  				      }
					  
					  syncQtyStepper();
  				  });
              });
          }

          // reset size when color changes
          selectedSizeText.textContent = "눌러서 선택하기";
          selectedSizeText.classList.add("detail-selected-value--placeholder");
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

      // 💡 [핵심 추가] 현재 선택된 옵션의 색상/사이즈를 확인하여 추가 금액을 가져옵니다.
      var addPrice = 0;
      if (selectedColorText && selectedSizeText) {
          var optKey = selectedColorText.textContent.trim() + "__" + selectedSizeText.textContent.trim();
          // JSP에서 넘겨준 OPTION_ADD_PRICE_MAP이 존재하고, 해당 키의 값이 있으면
          if (typeof OPTION_ADD_PRICE_MAP !== 'undefined' && OPTION_ADD_PRICE_MAP[optKey] !== undefined) {
              addPrice = parseInt(OPTION_ADD_PRICE_MAP[optKey], 10);
          }
      }

      sheetOrderCount.textContent = "총 " + quantity + "개";
      // 💡 [수식 수정] (기본가격 + 추가가격) * 수량 으로 계산!
      sheetOrderTotal.textContent = formatWon((unit + addPrice) * quantity);
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
	
	var hiddenQuantityEl = document.getElementById("hiddenQuantity");
	    if (hiddenQuantityEl) {
	        hiddenQuantityEl.value = quantity;
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
          var maxStock = parseInt(detailOptionSheet.getAttribute("data-option-stock") || "9999", 10);
          if (quantity >= maxStock) return;
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

  // JS 파일 내 sizeRecommendBtn 이벤트 리스너 부분
  if (sizeRecommendBtn && sizeRecommendResult) {
    sizeRecommendBtn.addEventListener("click", function () {
      // 1. 버튼 상태 변경 (중복 클릭 방지)
      sizeRecommendBtn.disabled = true;
      sizeRecommendBtn.textContent = "추천 받는 중...";
      
      // 2. 결과창 보여주기
      sizeRecommendResult.classList.remove("hidden");
      const textEl = document.getElementById("sizeRecommendText");
      if (textEl) textEl.textContent = "나에게 딱 맞는 사이즈를 계산 중입니다...";

      var ctx = document.body.getAttribute("data-context-path") || "";

	  fetch(ctx + "/size-recommend?productNo=" + PRODUCT_NO, { 
	    method: "GET",
	    credentials: "include"
	  })
      .then(function (r) { return r.json(); })
      .then(function (data) {
        if (textEl) {
          if (data.ok) {
            // 성공 시: AI가 준 답변을 그대로 화면에 출력!
            textEl.textContent = data.result;
          } else {
            // 실패 시: 에러 메시지 출력
            textEl.textContent = data.message || "추천 결과를 불러올 수 없습니다.";
          }
        }
      })
      .catch(function (err) {
        if (textEl) textEl.textContent = "서버 통신 오류가 발생했습니다.";
        console.error(err);
      })
      .finally(function () {
        // 3. 버튼 복구
        sizeRecommendBtn.disabled = false;
        sizeRecommendBtn.textContent = "나에게 맞는 사이즈 추천받기";
      });
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
  
  var confirmPokeBtn = document.getElementById("confirmPokeBtn");
  if (confirmPokeBtn) {
    confirmPokeBtn.addEventListener("click", function () {
      var selected = document.querySelector(".poke-person-btn.active");
      if (!selected) {
        showTopToast("조르기를 보낼 사람을 선택해주세요.", "error");
        return;
      }

      document.getElementById("pokeReceiverNo").value = selected.dataset.userNo;
      document.getElementById("pokeMsgHidden").value  = document.getElementById("pokeMsgInput").value;

      var pokeForm = document.getElementById("pokeForm");
      if (!pokeForm) return;
      var formData = new FormData(pokeForm);
      var body = new URLSearchParams();
      formData.forEach(function (value, key) {
        body.append(key, value == null ? "" : String(value));
      });

      confirmPokeBtn.disabled = true;
      fetch(pokeForm.action, {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8" },
        body: body.toString()
      })
      .then(function (res) {
        if (!res.ok) {
          throw new Error("poke request failed");
        }
        closePokeModal();
        var input = document.getElementById("pokeMsgInput");
        if (input) input.value = "";
        clearPokePersonSelection();
        showTopToast("조르기 요청을 보냈어요", "success");
      })
      .catch(function () {
        showTopToast("조르기 요청이 실패됐어요", "error");
      })
      .finally(function () {
        confirmPokeBtn.disabled = false;
      });
    });
  }

  document.addEventListener("keydown", function (e) {
    if (e.key === "Escape" && detailImageLightbox && !detailImageLightbox.classList.contains("hidden")) {
      closeImageLightbox();
    }
  });

});
