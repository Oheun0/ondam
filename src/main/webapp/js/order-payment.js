(function () {
  "use strict";

  function formatKRW(n) {
    var num = typeof n === "number" && !isNaN(n) ? Math.round(n) : 0;
    return num.toLocaleString("ko-KR") + "원";
  }

  function clamp(n, min, max) {
    return Math.max(min, Math.min(max, n));
  }

  document.addEventListener("DOMContentLoaded", function () {
    var root = document.getElementById("orderPaymentRoot");
    if (!root) return;

    // Dummy money
    var totalProduct = 80000;
    var productDiscount = 5000;
    var shippingFee = 0;
    var originalForPaybar = 88000; // 더미: 할인 전 표시용

    // State
    var selectedDelivery = "";
    var isDeliveryCustomMode = false;
    var selectedCouponId = "spring3000"; // 더미 기본값: -3,000원
    var selectedPay = "";

    // Elements
    var deliveryFieldBtn = document.getElementById("opDeliveryFieldBtn");
    var deliveryFieldText = document.getElementById("opDeliveryFieldText");
    var couponMirrorTitleEl = document.getElementById("opCouponMirrorTitle");
    var couponMirrorDescEl = document.getElementById("opCouponMirrorDesc");
    var couponMirrorBtn = document.getElementById("opCouponMirrorBtn");
    var couponPanel = document.getElementById("opCouponPanel");
    var deliveryPanel = document.getElementById("opDeliveryPanel");
    var deliveryCustomWrap = document.getElementById("opDeliveryCustomWrap");
    var deliveryCustomInput = document.getElementById("opDeliveryCustomInput");

    var totalProductEl = document.getElementById("opTotalProduct");
    var productDiscountEl = document.getElementById("opProductDiscount");
    var couponDiscountEl = document.getElementById("opCouponDiscount");
    var shippingFeeEl = document.getElementById("opShippingFee");
    var freeShipBadge = document.getElementById("opFreeShipBadge");

    var paybarOrigEl = document.getElementById("opPaybarOrig");
    var paybarFinalEl = document.getElementById("opPaybarFinal");
    var paybarBtn = document.getElementById("opPaySubmitBtn");

    var walletExtra = document.getElementById("opWalletExtra");
    var unknownHint = document.getElementById("opUnknownHint");
    var walletConnectModal = document.getElementById("opWalletConnectModal");
    var walletConnectGoBtn = document.getElementById("opWalletConnectGoBtn");
    // 함께지갑 잔액 부족 모달 (#opWalletInsufficientModal)
    // - 호출 시점(추후): 함께지갑 선택 + 결제 시, 연동 OK 이후 잔액(opWalletBalance 등) < 실제 결제금액이면 openWalletInsufficientModal()
    // - 지금은 결제 플로우에 연결하지 않음. UI만 두고 필요 시 위 조건에서 호출.
    var walletInsufficientModal = document.getElementById("opWalletInsufficientModal");
    var walletChargeGoBtn = document.getElementById("opWalletChargeGoBtn");

    var scrollLockY = 0;
    function lockBodyScroll() {
      // iOS 포함: 배경 스크롤 방지
      scrollLockY = window.scrollY || window.pageYOffset || 0;
      document.body.style.position = "fixed";
      document.body.style.top = "-" + scrollLockY + "px";
      document.body.style.left = "0";
      document.body.style.right = "0";
      document.body.style.width = "100%";
    }

    function unlockBodyScroll() {
      if (document.body.style.position !== "fixed") return;
      document.body.style.position = "";
      document.body.style.top = "";
      document.body.style.left = "";
      document.body.style.right = "";
      document.body.style.width = "";
      window.scrollTo(0, scrollLockY);
    }

    function setHidden(el, hidden) {
      if (!el) return;
      el.classList.toggle("hidden", !!hidden);
    }

    function openWalletConnectModal() {
      if (!walletConnectModal) return;
      setHidden(walletConnectModal, false);
      lockBodyScroll();
    }

    function closeWalletConnectModal() {
      if (!walletConnectModal) return;
      setHidden(walletConnectModal, true);
      unlockBodyScroll();
    }

    function openWalletInsufficientModal() {
      if (!walletInsufficientModal) return;
      setHidden(walletInsufficientModal, false);
      lockBodyScroll();
    }

    function closeWalletInsufficientModal() {
      if (!walletInsufficientModal) return;
      setHidden(walletInsufficientModal, true);
      unlockBodyScroll();
    }

    function setAccExpanded(accKey, expanded) {
      var toggle = root.querySelector('.op-acc-toggle[data-acc="' + accKey + '"]');
      var panelId = toggle && toggle.getAttribute("aria-controls");
      var panel = panelId ? document.getElementById(panelId) : null;
      if (!toggle || !panel) return;
      toggle.setAttribute("aria-expanded", expanded ? "true" : "false");
      panel.classList.toggle("hidden", !expanded);
    }

    function computeCouponDiscount() {
      if (!selectedCouponId) return 0;
      var base = Math.max(0, totalProduct - productDiscount);

      if (selectedCouponId === "welcome10") {
        // 데모용: 10%지만 화면 예시(-3,000원)에 맞춰 3,000원 캡
        var pct = Math.round(base * 0.1);
        return clamp(pct, 0, 3000);
      }

      if (selectedCouponId === "spring3000") {
        return base >= 20000 ? 3000 : 0;
      }

      return 0;
    }

    function updateMoneyUI() {
      var couponDiscount = computeCouponDiscount();
      var payable = Math.max(0, totalProduct - productDiscount - couponDiscount + shippingFee);

      if (totalProductEl) totalProductEl.textContent = formatKRW(totalProduct);
      if (productDiscountEl) productDiscountEl.textContent = "-" + formatKRW(productDiscount);
      if (couponDiscountEl) couponDiscountEl.textContent = "-" + formatKRW(couponDiscount);
      if (shippingFeeEl) shippingFeeEl.textContent = formatKRW(shippingFee);
      if (freeShipBadge) setHidden(freeShipBadge, shippingFee !== 0);

      // Paybar
      var showOrig = productDiscount + couponDiscount > 0;
      if (paybarOrigEl) {
        paybarOrigEl.textContent = formatKRW(originalForPaybar);
        setHidden(paybarOrigEl, !showOrig);
      }
      if (paybarFinalEl) paybarFinalEl.textContent = formatKRW(payable);
      if (paybarBtn) paybarBtn.textContent = "총 " + formatKRW(payable) + " 결제하기";
    }

    function updateDeliveryField() {
      if (!deliveryFieldBtn || !deliveryFieldText) return;
      if (isDeliveryCustomMode) {
        // 토글바 텍스트는 '직접 입력'으로 고정, 값은 입력창에만 유지
        deliveryFieldBtn.classList.add("is-filled");
        deliveryFieldText.textContent = "직접 입력";
        return;
      }

      var v = selectedDelivery || "";
      if (!v) {
        deliveryFieldBtn.classList.remove("is-filled");
        deliveryFieldText.textContent = "배송 시 요청사항을 선택해주세요";
        return;
      }

      deliveryFieldBtn.classList.add("is-filled");
      deliveryFieldText.textContent = v;
    }

    function setDeliverySelection(text, isCustom) {
      isDeliveryCustomMode = !!isCustom;
      selectedDelivery = text || "";
      if (!isCustom && deliveryCustomInput) {
        deliveryCustomInput.value = "";
      }

      setHidden(deliveryCustomWrap, !isCustom);
      updateDeliveryField();
      if (isCustom && deliveryCustomInput) {
        deliveryCustomInput.focus();
      }
    }

    function clearDeliveryDropdownStyles() {
      if (!deliveryPanel) return;
      deliveryPanel.style.top = "";
      deliveryPanel.style.left = "";
      deliveryPanel.style.right = "";
      deliveryPanel.style.maxWidth = "";
      deliveryPanel.style.width = "";
      deliveryPanel.style.visibility = "";
    }

    function closeDeliveryDropdown() {
      if (deliveryPanel) {
        deliveryPanel.classList.add("hidden");
        clearDeliveryDropdownStyles();
      }
      if (deliveryFieldBtn) deliveryFieldBtn.setAttribute("aria-expanded", "false");
      unlockBodyScroll();
    }

    function positionDeliveryDropdown() {
      if (!deliveryFieldBtn || !deliveryPanel || deliveryPanel.classList.contains("hidden")) return;

      var rect = deliveryFieldBtn.getBoundingClientRect();
      // 토글 필드와 패널 사이 간격 없음(붙여서 연결된 느낌)
      var fieldGap = 0;
      var viewportPad = 8;
      var vh = window.innerHeight;
      var fullBleed = window.matchMedia("(max-width: 768px)").matches;

      deliveryPanel.style.visibility = "hidden";

      // 입력창 가로폭과 동일하게
      var w = Math.max(200, Math.round(rect.width));
      deliveryPanel.style.width = w + "px";
      deliveryPanel.style.maxWidth = fullBleed ? "none" : w + "px";
      deliveryPanel.style.right = "auto";

      var mh = deliveryPanel.offsetHeight;

      var top = rect.bottom + fieldGap;
      if (top + mh > vh - viewportPad) {
        top = rect.top - mh - fieldGap;
      }
      if (top < viewportPad) top = viewportPad;
      deliveryPanel.style.top = Math.round(top) + "px";

      var left = Math.round(rect.left);
      // 모바일(430 wrap)에서도 입력창 기준으로 정렬되게 동일 처리
      deliveryPanel.style.left = left + "px";

      deliveryPanel.style.visibility = "visible";
    }

    function openDeliveryDropdown() {
      if (!deliveryFieldBtn || !deliveryPanel) return;
      var willOpen = deliveryPanel.classList.contains("hidden");
      closeDeliveryDropdown();
      if (!willOpen) return;
      deliveryPanel.classList.remove("hidden");
      deliveryFieldBtn.setAttribute("aria-expanded", "true");
      lockBodyScroll();
      requestAnimationFrame(positionDeliveryDropdown);
    }

    function updateCouponMirror() {
      if (!couponMirrorTitleEl) return;
      if (!selectedCouponId) {
        couponMirrorTitleEl.textContent = "사용 안 함";
        if (couponMirrorDescEl) {
          couponMirrorDescEl.textContent = "";
          setHidden(couponMirrorDescEl, true);
        }
        return;
      }
      var btn = root.querySelector('.op-coupon-card[data-coupon-id="' + selectedCouponId + '"]');
      var title = btn ? btn.getAttribute("data-coupon-title") || "" : "";
      var desc = btn ? btn.getAttribute("data-coupon-desc") || "" : "";
      couponMirrorTitleEl.textContent = title || "쿠폰";
      if (couponMirrorDescEl) {
        couponMirrorDescEl.textContent = desc;
        setHidden(couponMirrorDescEl, !desc);
      }
    }

    function setCouponPanelExpanded(expanded) {
      if (!couponPanel) return;
      setHidden(couponPanel, !expanded);
      if (couponMirrorBtn) couponMirrorBtn.setAttribute("aria-expanded", expanded ? "true" : "false");
    }

    function setCouponSelection(couponId) {
      selectedCouponId = couponId || "";
      var activeTitle = "";
      if (selectedCouponId) {
        var btn = root.querySelector('.op-coupon-card[data-coupon-id="' + selectedCouponId + '"]');
        if (btn) activeTitle = btn.getAttribute("data-coupon-title") || btn.textContent.trim();
      }
      updateCouponMirror();
      updateMoneyUI();
    }

    function setPaySelection(payKey) {
      selectedPay = payKey || "";
      var payButtons = Array.prototype.slice.call(root.querySelectorAll(".op-pay-btn"));
      payButtons.forEach(function (b) {
        var active = b.getAttribute("data-pay") === selectedPay;
        b.classList.toggle("is-active", active);
        b.setAttribute("aria-checked", active ? "true" : "false");
      });

      setHidden(walletExtra, selectedPay !== "wallet");
      setHidden(unknownHint, selectedPay !== "unknown");
    }

    // Accordion toggles
    root.addEventListener("click", function (e) {
      if (deliveryFieldBtn) {
        var deliveryClick = e.target.closest("#opDeliveryFieldBtn");
        if (deliveryClick) {
          e.stopPropagation();
          openDeliveryDropdown();
          return;
        }
      }

      // Delivery option
      var delBtn = e.target.closest(".op-toggle-btn[data-delivery]");
      if (delBtn && deliveryPanel && deliveryPanel.contains(delBtn)) {
        var all = Array.prototype.slice.call(deliveryPanel.querySelectorAll(".op-toggle-btn[data-delivery]"));
        all.forEach(function (b) {
          var active = b === delBtn;
          b.classList.toggle("is-active", active);
          b.setAttribute("aria-selected", active ? "true" : "false");
        });

        var isCustom = delBtn.getAttribute("data-delivery-custom") === "true";
        var text = delBtn.getAttribute("data-delivery") || delBtn.textContent.trim();
        if (isCustom) {
          setDeliverySelection("직접 입력", true);
        } else {
          setDeliverySelection(text, false);
        }
        closeDeliveryDropdown();
        return;
      }

      // Coupon select (single)
      var couponBtn = e.target.closest(".op-coupon-card[data-coupon-id]");
      if (couponBtn) {
        var list = Array.prototype.slice.call(root.querySelectorAll(".op-coupon-card[data-coupon-id]"));
        var already = couponBtn.classList.contains("is-active");
        list.forEach(function (b) {
          b.classList.remove("is-active");
          b.setAttribute("aria-checked", "false");
        });

        if (already) {
          setCouponSelection("");
        } else {
          couponBtn.classList.add("is-active");
          couponBtn.setAttribute("aria-checked", "true");
          setCouponSelection(couponBtn.getAttribute("data-coupon-id"));
        }
        setCouponPanelExpanded(false);
        return;
      }

      // Payment method
      var payBtn = e.target.closest(".op-pay-btn[data-pay]");
      if (payBtn) {
        setPaySelection(payBtn.getAttribute("data-pay"));
        return;
      }

      // Submit (no server)
      if (e.target && e.target.id === "opPaySubmitBtn") {
        // 더미: 내 사람 미연동 상태라고 가정 → 함께지갑이면 항상 모달 노출
        if (selectedPay === "wallet") {
          openWalletConnectModal();
          return;
        }
        var deliveryMsg = "선택 안 함";
        if (isDeliveryCustomMode) {
          deliveryMsg = (deliveryCustomInput && deliveryCustomInput.value.trim()) || "직접 입력(미입력)";
        } else if (selectedDelivery) {
          deliveryMsg = selectedDelivery;
        }
        var msg =
          "화면 설계용 데모입니다.\n\n" +
          "- 배송 요청사항: " +
          deliveryMsg +
          "\n- 쿠폰: " +
          (selectedCouponId || "사용 안 함") +
          "\n- 결제수단: " +
          (selectedPay || "선택 안 함");
        window.alert(msg);
      }
    });

    // Coupon mirror toggle (applied coupon box)
    if (couponMirrorBtn) {
      couponMirrorBtn.addEventListener("click", function (e) {
        e.preventDefault();
        e.stopPropagation();
        var expanded = couponMirrorBtn.getAttribute("aria-expanded") === "true";
        setCouponPanelExpanded(!expanded);
      });
    }

    document.addEventListener("click", function (e) {
      if (!deliveryPanel || deliveryPanel.classList.contains("hidden")) return;
      var insidePanel = deliveryPanel.contains(e.target);
      var onField = deliveryFieldBtn && deliveryFieldBtn.contains(e.target);
      if (!insidePanel && !onField) closeDeliveryDropdown();
    });

    document.addEventListener("keydown", function (e) {
      if (e.key === "Escape") {
        closeDeliveryDropdown();
        closeWalletConnectModal();
        closeWalletInsufficientModal();
      }
    });

    if (walletConnectModal) {
      walletConnectModal.addEventListener("click", function (e) {
        var dismiss = e.target && e.target.closest("[data-op-modal-dismiss]");
        if (dismiss) closeWalletConnectModal();
      });
    }

    if (walletConnectGoBtn) {
      walletConnectGoBtn.addEventListener("click", function (e) {
        e.preventDefault();
        closeWalletConnectModal();
        window.alert("내 사람 연결하기는 준비 중입니다.");
      });
    }

    if (walletInsufficientModal) {
      walletInsufficientModal.addEventListener("click", function (e) {
        var dismiss = e.target && e.target.closest("[data-op-wallet-insufficient-dismiss]");
        if (dismiss) closeWalletInsufficientModal();
      });
    }

    if (walletChargeGoBtn) {
      walletChargeGoBtn.addEventListener("click", function (e) {
        e.preventDefault();
        closeWalletInsufficientModal();
        // TODO: 함께지갑 충전 화면 URL로 이동
        window.alert("충전 페이지는 준비 중입니다.");
      });
    }

    window.addEventListener("resize", function () {
      positionDeliveryDropdown();
    });

    // Custom input live summary
    if (deliveryCustomInput) {
      deliveryCustomInput.addEventListener("input", function () {
        if (!deliveryCustomWrap || deliveryCustomWrap.classList.contains("hidden")) return;
        updateDeliveryField();
      });
    }

    // Init defaults
    updateMoneyUI();
    setDeliverySelection("", false);
    setCouponSelection(selectedCouponId);
    setCouponPanelExpanded(false);
    // 기본 쿠폰 UI highlight
    var defaultCoupon = root.querySelector('.op-coupon-card[data-coupon-id="' + selectedCouponId + '"]');
    if (defaultCoupon) {
      defaultCoupon.classList.add("is-active");
      defaultCoupon.setAttribute("aria-checked", "true");
    }
    setPaySelection("card");
  });
})();

