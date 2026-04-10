(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    var root = document.getElementById("couponListPageRoot");
    if (!root) return;
    var ctx = document.body.getAttribute("data-context-path") || "";

    var backBtn = document.getElementById("appBackHeaderBtn");
    if (backBtn) {
      backBtn.addEventListener("click", function () {
        if (window.history.length > 1) {
          window.history.back();
        } else {
          window.location.href = ctx + "/mypage";
        }
      });
    }

    var tabBtns = root.querySelectorAll(".coupon-list-tab-btn");
    var panels = root.querySelectorAll("[data-coupon-panel]");

    function setTab(tabId) {
      tabBtns.forEach(function (btn) {
        var on = btn.getAttribute("data-coupon-tab") === tabId;
        btn.classList.toggle("active", on);
        btn.setAttribute("aria-selected", on ? "true" : "false");
        btn.setAttribute("tabindex", on ? "0" : "-1");
      });
      panels.forEach(function (panel) {
        var on = panel.getAttribute("data-coupon-panel") === tabId;
        panel.classList.toggle("hidden", !on);
        panel.setAttribute("aria-hidden", on ? "false" : "true");
      });
    }

    tabBtns.forEach(function (btn) {
      btn.addEventListener("click", function () {
        var id = btn.getAttribute("data-coupon-tab");
        if (id) setTab(id);
      });
    });

    var sortToggleBtn = document.getElementById("couponSortToggleBtn");
    var sortDropdown = document.getElementById("couponSortDropdown");
    var sortSelectedText = document.getElementById("couponSortSelectedText");
    var sortOptions = sortDropdown ? sortDropdown.querySelectorAll(".filter-option") : [];
    var cardList = document.getElementById("couponAvailableCardList");

    function positionSortDropdown() {
      if (!sortToggleBtn || !sortDropdown || sortDropdown.classList.contains("hidden")) return;

      var rect = sortToggleBtn.getBoundingClientRect();
      var pad = 8;
      var vw = window.innerWidth;
      var vh = window.innerHeight;
      var fullBleed = window.matchMedia("(max-width: 768px)").matches;

      sortDropdown.style.visibility = "hidden";

      if (fullBleed) {
        sortDropdown.style.left = "0";
        sortDropdown.style.right = "0";
        sortDropdown.style.width = "100%";
        sortDropdown.style.maxWidth = "none";
      } else {
        var maxW = Math.min(330, vw - 16);
        sortDropdown.style.width = "";
        sortDropdown.style.right = "auto";
        sortDropdown.style.maxWidth = maxW + "px";
      }

      var mw = sortDropdown.offsetWidth;
      var mh = sortDropdown.offsetHeight;

      var top = rect.bottom + pad;
      if (top + mh > vh - pad) {
        top = rect.top - mh - pad;
      }
      if (top < pad) top = pad;

      sortDropdown.style.top = top + "px";

      if (!fullBleed) {
        var left = rect.left;
        if (left + mw > vw - pad) {
          left = Math.max(pad, vw - mw - pad);
        }
        if (left < pad) left = pad;
        sortDropdown.style.left = left + "px";
      }

      sortDropdown.style.visibility = "visible";
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
      if (!willOpen) {
        closeSortDropdown();
        return;
      }
      sortDropdown.classList.remove("hidden");
      sortToggleBtn.setAttribute("aria-expanded", "true");
      requestAnimationFrame(function () {
        positionSortDropdown();
      });
    }

    if (sortToggleBtn && sortDropdown) {
      sortToggleBtn.addEventListener("click", function (e) {
        e.stopPropagation();
        openSortDropdown();
      });
    }

	function applyCouponSort(mode) {
	      if (!cardList) return;
	      var attrByMode = {
	        discount: "data-coupon-sort-discount",
	        received: "data-coupon-sort-received",
	        expiry: "data-coupon-sort-expiry"
	      };
	      var attr = attrByMode[mode] || attrByMode.discount;
	      var items = Array.prototype.slice.call(cardList.querySelectorAll(".coupon-card"));
	      
	      items.sort(function (a, b) {
	        var va = a.getAttribute(attr);
	        var vb = b.getAttribute(attr);

	        if (mode === "expiry" || mode === "received") {
	          var cleanA = (va || "").split('.')[0]; 
	          var cleanB = (vb || "").split('.')[0];
	          var dateA = new Date(cleanA).getTime() || 0;
	          var dateB = new Date(cleanB).getTime() || 0;
	          
	          if (mode === "expiry") return dateA - dateB; // 임박순
	          return dateB - dateA; // 받은순
	        }
	        return (parseInt(vb, 10) || 0) - (parseInt(va, 10) || 0);
	      });

	      items.forEach(function (li) {
	        cardList.appendChild(li);
	      });
	    }

    sortOptions.forEach(function (opt) {
      opt.addEventListener("click", function (e) {
        e.stopPropagation();
        var sort = opt.getAttribute("data-coupon-sort");
        if (!sort) return;
        sortOptions.forEach(function (o) {
          o.classList.toggle("active", o.getAttribute("data-coupon-sort") === sort);
        });
        if (sortSelectedText) sortSelectedText.textContent = opt.textContent.trim();
        closeSortDropdown();
        applyCouponSort(sort);
      });
    });

    document.addEventListener("click", function (e) {
      if (e.target.closest(".filter-dropdown-wrap")) return;
      closeSortDropdown();
    });

    if (sortDropdown) {
      sortDropdown.addEventListener("click", function (e) {
        e.stopPropagation();
      });
    }

    window.addEventListener("resize", function () {
      if (sortDropdown && !sortDropdown.classList.contains("hidden")) positionSortDropdown();
    });

    window.addEventListener(
      "scroll",
      function () {
        if (sortDropdown && !sortDropdown.classList.contains("hidden")) positionSortDropdown();
      },
      true
    );

    var modal = document.getElementById("couponRegisterModal");
    var openRegisterBtn = document.getElementById("couponRegisterOpenBtn");
    var codeInput = document.getElementById("couponCodeInput");
    var submitBtn = document.getElementById("couponRegisterSubmitBtn");
    var toastEl = document.getElementById("coupon-error-toast");
    var toastTextEl = document.getElementById("couponErrorToastText");

    var toastActive = false;
    var toastDismissTimer;
    var toastAnimFallbackTimer;

    function showCouponErrorToast(msg) {
      if (!toastEl || toastActive) return;
      if (toastTextEl && msg) toastTextEl.textContent = msg;

      toastActive = true;
      clearTimeout(toastDismissTimer);
      clearTimeout(toastAnimFallbackTimer);

      toastEl.classList.remove("hidden", "option-toast--hiding", "option-toast--show");
      toastEl.setAttribute("aria-hidden", "false");

      requestAnimationFrame(function () {
        requestAnimationFrame(function () {
          toastEl.classList.add("option-toast--show");
        });
      });

      toastDismissTimer = setTimeout(function () {
        toastEl.classList.remove("option-toast--show");
        toastEl.classList.add("option-toast--hiding");

        var finished = false;
        function cleanup() {
          if (finished) return;
          finished = true;
          toastEl.removeEventListener("transitionend", onTransitionEnd);
          clearTimeout(toastAnimFallbackTimer);
          toastEl.classList.add("hidden");
          toastEl.classList.remove("option-toast--hiding");
          toastEl.setAttribute("aria-hidden", "true");
          toastActive = false;
        }

        function onTransitionEnd(ev) {
          if (ev.target !== toastEl) return;
          if (ev.propertyName !== "opacity" && ev.propertyName !== "transform") return;
          cleanup();
        }

        toastEl.addEventListener("transitionend", onTransitionEnd);
        toastAnimFallbackTimer = setTimeout(cleanup, 400);
      }, 2000);
    }

    function syncSubmitEnabled() {
      if (!submitBtn || !codeInput) return;
      submitBtn.disabled = codeInput.value.trim().length === 0;
    }

    function openModal() {
      if (!modal) return;
      modal.classList.remove("hidden");
      if (codeInput) {
        codeInput.value = "";
        codeInput.focus();
      }
      syncSubmitEnabled();
    }

    function closeModal() {
      if (!modal) return;
      modal.classList.add("hidden");
    }

    if (openRegisterBtn) {
      openRegisterBtn.addEventListener("click", function () {
        openModal();
      });
    }

    if (modal) {
      modal.querySelectorAll("[data-coupon-modal-dismiss]").forEach(function (el) {
        el.addEventListener("click", function () {
          closeModal();
        });
      });
    }

    if (codeInput) {
      codeInput.addEventListener("input", syncSubmitEnabled);
    }

	if (submitBtn && codeInput) {
	      submitBtn.addEventListener("click", function () {
	        var code = codeInput.value.trim();
	        if (!code) return;
	        submitBtn.disabled = true;

	        fetch(ctx + "/userCoupon?action=register&couponCode=" + encodeURIComponent(code))
	          .then(function(res) { return res.text(); })
	          .then(function(result) {
	            if (result === "SUCCESS") {
	              closeModal();
	              location.reload(); 
	            } else if (result === "NOT_FOUND") {
	              showCouponErrorToast("존재하지 않거나 만료된 쿠폰 코드입니다.");
	            } else if (result === "DUPLICATE") {
	              showCouponErrorToast("이미 등록된 쿠폰입니다.");
	            } else if (result === "LOGIN_REQUIRED") {
	              showCouponErrorToast("로그인이 필요합니다. 로그인 페이지로 이동합니다.");
	              setTimeout(function() {
	                window.location.href = ctx + "/login"; 
	              }, 1000); // 토스트를 읽을 시간을 약간 줍니다.
	            } else {
	              showCouponErrorToast("쿠폰 등록 중 오류가 발생했습니다.");
	            }
	          })
	          .catch(function(err) {
	            console.error("Fetch error:", err);
	            showCouponErrorToast("서버와의 통신이 원활하지 않습니다.");
	          })
	          .finally(function() {
	            submitBtn.disabled = false;
	          });
	      });
	    }
	  });
	})();