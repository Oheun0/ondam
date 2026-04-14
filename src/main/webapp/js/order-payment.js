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
	var totalProduct    = parseInt(root.getAttribute("data-total-product") || "0", 10);
	var productDiscount = parseInt(root.getAttribute("data-product-discount") || "0", 10);    var shippingFee = 0;
    var originalForPaybar = totalProduct;

    // State
    var selectedDelivery = "";
    var isDeliveryCustomMode = false;
	var selectedCouponId = "";
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
	var payGrid          = document.getElementById("opPayGrid");
	var preferPayment    = payGrid ? parseInt(payGrid.getAttribute("data-prefer") || "0", 10) : 0;
	var walletBalanceRaw = payGrid ? parseInt(payGrid.getAttribute("data-wallet-balance") || "0", 10) : 0;
	var familyNo         = payGrid ? parseInt(payGrid.getAttribute("data-family-no") || "0", 10) : 0;

	// 실제 잔액으로 교체
	var walletBalanceEl = document.getElementById("opWalletBalance");
	if (walletBalanceEl) {
	    walletBalanceEl.textContent = walletBalanceRaw.toLocaleString("ko-KR") + "원";
	}
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
	
	function closeWalletInsufficientModal() {
	    if (!walletInsufficientModal) return;
	    setHidden(walletInsufficientModal, true);
	    unlockBodyScroll();
	}

	// ↓ 여기에 배송지 변경 모달 코드 추가
	var addressModal     = document.getElementById("opAddressModal");
	var addressModalDim  = document.getElementById("opAddressModalDim");
	var addressCloseBtn  = document.getElementById("opAddressModalCloseBtn");
	var addressChangeBtn = document.querySelector(".op-link-btn[aria-label='배송지 변경하기']");

	function openAddressModal() {
	    if (!addressModal) return;
	    setHidden(addressModal, false);
	    lockBodyScroll();
	}

	function closeAddressModal() {
	    if (!addressModal) return;
	    setHidden(addressModal, true);
	    unlockBodyScroll();
	}

	if (addressChangeBtn) addressChangeBtn.addEventListener("click", openAddressModal);
	if (addressCloseBtn)  addressCloseBtn.addEventListener("click", closeAddressModal);
	if (addressModalDim)  addressModalDim.addEventListener("click", closeAddressModal);

	if (addressModal) {
	    addressModal.addEventListener("click", function (e) {
	        var item = e.target.closest(".op-address-item");
	        if (!item) return;

	        var name      = item.getAttribute("data-receiver-name");
	        var tel       = item.getAttribute("data-receiver-tel");
	        var addr      = item.getAttribute("data-address");
	        var detail    = item.getAttribute("data-detail");
	        var zipcode   = item.getAttribute("data-zipcode");
	        var isDefault = item.getAttribute("data-is-default") === "1";

	        var whoEl   = document.querySelector(".op-ship-who");
	        var addrEl  = document.querySelector(".op-ship-addr");
	        var badgeEl = document.querySelector(".op-card-head__left .op-badge--muted");

	        if (whoEl) {
	            whoEl.innerHTML =
	                '<span class="op-strong">' + name + '</span>' +
	                '<span class="op-ship-sep" aria-hidden="true">|</span>' +
	                '<span class="op-strong">' + tel + '</span>';
	        }
	        if (addrEl) {
	            addrEl.textContent = "(" + zipcode + ") " + addr + (detail ? ", " + detail : "");
	        }

	        // 기본배송지 뱃지 업데이트
	        if (badgeEl) {
	            setHidden(badgeEl, !isDefault);
	        }

	        closeAddressModal();
	    });
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

	    var btn = root.querySelector('.op-coupon-card[data-coupon-id="' + selectedCouponId + '"]');
	    if (!btn) return 0;

	    var discountType  = parseInt(btn.getAttribute("data-discount-type") || "0", 10);
	    var discountValue = parseInt(btn.getAttribute("data-discount-value") || "0", 10);
	    var minOrder      = parseInt(btn.getAttribute("data-min-order") || "0", 10);
	    var maxAttr       = btn.getAttribute("data-max-discount");
	    var maxDiscount   = (maxAttr && maxAttr !== "null") ? parseInt(maxAttr, 10) : null;

	    if (base < minOrder) return 0;
	    var discount = 0;
	    if (discountType === 0) {
	        discount = Math.round(base * (discountValue / 100));
	        if (maxDiscount !== null && maxDiscount > 0) {
	            discount = Math.min(discount, maxDiscount);
	        }
	    } else {
	        discount = discountValue;
	    }
	    return Math.min(discount, base);
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
	      if (selectedPay === "wallet") {
	          if (familyNo === 0) {
	              openWalletConnectModal();
	              return;
	          }
	          var couponDiscount = computeCouponDiscount();
	          var payable = Math.max(0, totalProduct - productDiscount - couponDiscount + shippingFee);
	          if (walletBalanceRaw < payable) {
	              openWalletInsufficientModal();
	              return;
	          }
	      }
		  // 실제 주문 submit
		      var contextPath = document.body.getAttribute("data-context-path") || "";
		      var form = document.createElement("form");
		      form.method = "POST";
		      form.action = contextPath + "/payment?action=submit";

		      // 배송지 정보 (현재 화면에 표시된 값)
		      var whoEl  = document.querySelector(".op-ship-who");
		      var addrEl = document.querySelector(".op-ship-addr");
		      var receiverName = "";
		      var receiverTel  = "";
		      if (whoEl) {
		          var strongs = whoEl.querySelectorAll(".op-strong");
		          if (strongs[0]) receiverName = strongs[0].textContent.trim();
		          if (strongs[1]) receiverTel  = strongs[1].textContent.trim();
		      }
		      var deliveryAddr = addrEl ? addrEl.textContent.trim() : "";

		      // 배송 요청사항
		      var deliveryContent = isDeliveryCustomMode
		          ? (deliveryCustomInput ? deliveryCustomInput.value.trim() : "")
		          : selectedDelivery;

		      // 결제 수단 매핑 (0:지갑 1:카드 2:계좌)
		      var payMethodMap = { wallet: 0, card: 1, transfer: 2 };
		      var payMethod = payMethodMap[selectedPay] !== undefined ? payMethodMap[selectedPay] : 1;

		      var couponDiscount = computeCouponDiscount();
		      var payable = Math.max(0, totalProduct - productDiscount - couponDiscount + shippingFee);

			  var fields = {
			      receiverName:    receiverName,
			      receiverTel:     receiverTel,
			      deliveryAddr:    deliveryAddr,
			      deliveryContent: deliveryContent,
			      paymentMethod:   payMethod,
			      selectedCouponId: selectedCouponId || "",
			      couponDiscount:  couponDiscount,
			      paymentAmount:   payable,
			      // ── 바로구매 / 장바구니 구분 ──
				  buyType:         (document.querySelector('input[name="buyType"]') || {}).value || "",
				  directProductNo: (document.querySelector('input[name="directProductNo"]') || {}).value || "",
				  directOptionNo:  (document.querySelector('input[name="directOptionNo"]') || {}).value || "",
				  directQuantity:  (document.querySelector('input[name="directQuantity"]') || {}).value || "",
				  isGift:     (document.querySelector('input[name="isGift"]') || {}).value || "",
				      receiverNo: (document.querySelector('input[name="receiverNo"]') || {}).value || ""
			  };

		      // cartItemNo 배열도 같이 전송 (hidden input 여러 개)
		      var checkedItems = document.querySelectorAll(".cart-item__checkbox:checked");
		      // order-payment.jsp에서 cartItemNo를 hidden으로 넣어두는 방식으로 처리
		      var cartItemNoInputs = document.querySelectorAll("input[name='cartItemNo']");
		      cartItemNoInputs.forEach(function(inp) {
		          var hidden = document.createElement("input");
		          hidden.type = "hidden";
		          hidden.name = "cartItemNo";
		          hidden.value = inp.value;
		          form.appendChild(hidden);
		      });

		      Object.keys(fields).forEach(function(key) {
		          var input = document.createElement("input");
		          input.type  = "hidden";
		          input.name  = key;
		          input.value = fields[key];
		          form.appendChild(input);
		      });

		      document.body.appendChild(form);
		      form.submit();
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
		closeAddressModal();
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
	        var contextPath = document.body.getAttribute("data-context-path") || "";
	        window.location.href = contextPath + "/wallet?action=charge";
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
	var preferPayMap = { 0: "card", 1: "card", 2: "transfer", 3: "wallet" };
	setPaySelection(preferPayMap[preferPayment] || "card");
  });
})();

