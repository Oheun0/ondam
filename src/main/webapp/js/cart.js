(function () {
  "use strict";

  function formatKRW(n) {
    var num = typeof n === "number" && !isNaN(n) ? Math.round(n) : 0;
    return num.toLocaleString("ko-KR");
  }

  function parseOptionLine(text) {
    if (!text || typeof text !== "string") {
      return { color: "빨간색", size: "95", qty: 1 };
    }
    var parts = text.split("/").map(function (s) {
      return s.trim();
    });
    var qty = 1;
    if (parts.length >= 3) {
      var m = String(parts[2]).match(/(\d+)/);
      if (m) qty = parseInt(m[1], 10) || 1;
    }
    return {
      color: parts[0] || "빨간색",
      size: parts[1] || "95",
      qty: qty,
    };
  }

  function qsa(root, sel) {
    return Array.prototype.slice.call(root.querySelectorAll(sel));
  }

  document.addEventListener("DOMContentLoaded", function () {
    var root = document.getElementById("cartPageRoot");
    var mainList = document.getElementById("cartMainList");
    var filledWrap = document.getElementById("cartFilledWrap");
    var jsEmpty = document.getElementById("cartJsEmpty");
    var selectAll = document.getElementById("cartSelectAll");
    var removeSoldoutBtn = document.getElementById("cartRemoveSoldoutBtn");
    var removeSelectedBtn = document.getElementById("cartRemoveSelectedBtn");
    var orderCountEl = document.getElementById("cartOrderCount");
    var orderBtn = document.getElementById("cartOrderSubmitBtn");
    var backBtn = document.getElementById("cartBackBtn");

    var sheetDim = document.getElementById("cartSheetDim");
    var sheet = document.getElementById("cartOptionSheet");
    var colorToggle = document.getElementById("cartColorToggleBtn");
    var sizeToggle = document.getElementById("cartSizeToggleBtn");
    var colorPanel = document.getElementById("cartColorOptionPanel");
    var sizePanel = document.getElementById("cartSizeOptionPanel");
    var selectedColorText = document.getElementById("cartSelectedColorText");
    var selectedSizeText = document.getElementById("cartSelectedSizeText");
    var minusQtyBtn = document.getElementById("cartMinusQtyBtn");
    var plusQtyBtn = document.getElementById("cartPlusQtyBtn");
    var qtyValueEl = document.getElementById("cartQtyValue");
    var sheetOrderCount = document.getElementById("cartSheetOrderCount");
    var sheetOrderTotal = document.getElementById("cartSheetOrderTotal");
    var cancelSheetBtn = document.getElementById("cartSheetCancelBtn");
    var applySheetBtn = document.getElementById("cartSheetApplyBtn");

    if (backBtn) {
      backBtn.addEventListener("click", function () {
        if (window.history.length > 1) {
          window.history.back();
        } else {
          var p = document.body.getAttribute("data-context-path") || "";
          window.location.href = p + "/main";
        }
      });
    }

    if (!mainList || !selectAll) return;

    var currentArticle = null;
    var unitSale = 0;
    var unitOrig = 0;
    var discounted = false;
    var sheetQty = 1;

    function updateOptionButton(article) {
      if (!article) return;
      var optionText = article.getAttribute("data-option") || "";
      var btn = article.querySelector(".cart-item__option-btn");
      if (btn) {
        btn.textContent = optionText;
        btn.setAttribute("aria-label", "옵션 변경하기: " + optionText);
      }
    }

    function getItems() {
      return qsa(mainList, ".cart-item");
    }

    function syncSelectAll() {
      var items = getItems();
	  var boxes = items.map(function (el) {
	      return el.querySelector(".cart-item__checkbox");
	  }).filter(function(cb) {
	      return cb && !cb.disabled;
	  });
      if (!boxes.length) {
        selectAll.checked = false;
        selectAll.indeterminate = false;
        return;
      }
      var allOn = boxes.every(function (cb) {
        return cb.checked;
      });
      var anyOn = boxes.some(function (cb) {
        return cb.checked;
      });
      selectAll.checked = allOn;
      selectAll.indeterminate = anyOn && !allOn;
    }

	function selectedLineCount() {
	    return getItems().filter(function (el) {
	        var cb = el.querySelector(".cart-item__checkbox");
	        return cb && cb.checked && !cb.disabled;
	    }).length;
	}

    function updateOrderBar() {
      if (orderCountEl) orderCountEl.textContent = String(selectedLineCount());
      if (orderBtn) orderBtn.disabled = selectedLineCount() === 0;
    }
	
	if (orderBtn) {
	    orderBtn.addEventListener("click", function () {
	        var checkedItems = getItems().filter(function (item) {
	            var cb = item.querySelector(".cart-item__checkbox");
	            return cb && cb.checked;
	        });

	        if (checkedItems.length === 0) return;

	        var ctx = document.body.getAttribute("data-context-path") || "";
	        var form = document.createElement("form");
	        form.method = "POST";
	        form.action = ctx + "/payment";

	        checkedItems.forEach(function (item) {
	            var input = document.createElement("input");
	            input.type  = "hidden";
	            input.name  = "cartItemNo";
	            input.value = item.getAttribute("data-cart-id");
	            form.appendChild(input);
	        });

	        document.body.appendChild(form);
	        form.submit();
	    });
	}

    function syncOptionPanelsAria() {
      if (colorToggle && colorPanel) {
        colorToggle.setAttribute(
          "aria-expanded",
          colorPanel.classList.contains("hidden") ? "false" : "true"
        );
      }
      if (sizeToggle && sizePanel) {
        sizeToggle.setAttribute(
          "aria-expanded",
          sizePanel.classList.contains("hidden") ? "false" : "true"
        );
      }
    }

    function closeOptionPanels() {
      if (colorPanel) colorPanel.classList.add("hidden");
      if (sizePanel) sizePanel.classList.add("hidden");
      syncOptionPanelsAria();
    }

    function togglePanel(panel, other) {
      if (!panel) return;
      var willOpen = panel.classList.contains("hidden");
      if (other) other.classList.add("hidden");
      panel.classList.toggle("hidden", !willOpen);
      syncOptionPanelsAria();
    }

    function updateSheetSummary() {
      var line = unitSale * sheetQty;
      if (sheetOrderCount) sheetOrderCount.textContent = "총 " + sheetQty + "개";
      if (sheetOrderTotal) sheetOrderTotal.textContent = formatKRW(line) + "원";
      if (minusQtyBtn) minusQtyBtn.disabled = sheetQty <= 1;
    }

    function setActiveOptionRow(container, attrName, value) {
      if (!container) return;
      qsa(container, ".detail-option-row").forEach(function (row) {
        var v = row.getAttribute(attrName);
        row.classList.toggle("active", v === value);
      });
    }

    function openOptionSheet(article) {
      if (!article || article.getAttribute("data-soldout") === "true") return;
      currentArticle = article;
      unitSale = parseInt(article.getAttribute("data-unit-price"), 10) || 0;
      unitOrig = parseInt(article.getAttribute("data-original-price"), 10) || unitSale;
      discounted = article.getAttribute("data-discounted") === "true";

      var optText =
        article.getAttribute("data-option") ||
        (article.querySelector(".cart-item__option") &&
          article.querySelector(".cart-item__option").textContent) ||
        "";
      var parsed = parseOptionLine(optText);
      sheetQty = parsed.qty;

      if (selectedColorText) selectedColorText.textContent = parsed.color;
      if (selectedSizeText) selectedSizeText.textContent = parsed.size;
      if (qtyValueEl) qtyValueEl.textContent = String(sheetQty);

      setActiveOptionRow(colorPanel, "data-color", parsed.color);
      setActiveOptionRow(sizePanel, "data-size", parsed.size);

      closeOptionPanels();
      updateSheetSummary();

      if (sheetDim) {
        sheetDim.classList.remove("hidden");
        sheetDim.setAttribute("aria-hidden", "false");
      }
      if (sheet) {
        sheet.classList.remove("hidden");
      }
      document.body.style.overflow = "hidden";
    }

    function closeOptionSheet() {
      if (sheetDim) {
        sheetDim.classList.add("hidden");
        sheetDim.setAttribute("aria-hidden", "true");
      }
      if (sheet) sheet.classList.add("hidden");
      document.body.style.overflow = "";
      closeOptionPanels();
      currentArticle = null;
    }

	function removeArticle(article) {
	    if (!article) return;

	    var cartItemNo = article.getAttribute("data-cart-id");
	    var ctx = document.body.getAttribute("data-context-path") || "";

	    // 서버 삭제 요청
	    fetch(ctx + "/cart?action=delete&cartItemNo=" + cartItemNo, {
	        method: "GET"
	    }).catch(function (err) {
	        console.error("삭제 실패:", err);
	    });

	    // DOM 제거 (기존 로직 유지)
	    var li = article.closest("li");
	    if (li) {
	        var ul = li.parentElement;
	        li.remove();
	        if (ul && !ul.querySelector("li")) {
	            var group = ul.closest(".cart-brand-group");
	            if (group) group.remove();
	        }
	    }
	    afterListChange();
	}

    function afterListChange() {
      if (getItems().length === 0) {
        if (filledWrap) filledWrap.classList.add("hidden");
        if (jsEmpty) jsEmpty.classList.remove("hidden");
        if (root) root.classList.add("cart-page-inner--empty-only");
      } else {
        syncSelectAll();
        updateOrderBar();
      }
    }

	function applyOptionToArticle() {
	    if (!currentArticle) return;
	    var color = selectedColorText ? selectedColorText.textContent.trim() : "";
	    var size = selectedSizeText ? selectedSizeText.textContent.trim() : "";
	    var optionText = color + " / " + size + " / " + sheetQty + "개";

	    var cartItemNo = currentArticle.getAttribute("data-cart-id");
	    var productNo = currentArticle.getAttribute("data-product-no");
	    var ctx = document.body.getAttribute("data-context-path") || "";

	    // ✅ 서버에서 색상+사이즈로 productOptionNo 조회 후 업데이트
	    fetch(ctx + "/product?action=getOptions&productNo=" + productNo)
	        .then(function(res) { return res.json(); })
	        .then(function(options) {
	            var matched = options.find(function(opt) {
	                return opt.optionColor === color && opt.optionSize === size;
	            });
	            if (!matched) {
	                alert("해당 옵션을 찾을 수 없습니다.");
	                return;
	            }

				var formData = new URLSearchParams();
				formData.append("action", "updateOption");
				formData.append("cartItemNo", cartItemNo);
				formData.append("productOptionNo", matched.productOptionNo);
				formData.append("quantity", String(sheetQty));

				fetch(ctx + "/cart", {
				    method: "POST",
				    headers: {
				        "Content-Type": "application/x-www-form-urlencoded"
				    },
				    body: formData.toString()
				}).then(function() {
				    // location.replace()를 쓰면 뒤로가기 기록이 남지 않습니다!
				    window.location.replace(ctx + "/cart?action=list");
				}).catch(function(err) {
				    console.error("옵션 변경 실패:", err);
				    alert("옵션 변경 중 오류가 발생했습니다.");
				});
	        });

	    closeOptionSheet();
	}

	/* 전체 선택 */
	selectAll.addEventListener("change", function () {
	    var on = selectAll.checked;
	    getItems().forEach(function (item) {
	        var cb = item.querySelector(".cart-item__checkbox");
	        if (cb && !cb.disabled) cb.checked = on;  // disabled 제외
	    });
	    selectAll.indeterminate = false;
	    updateOrderBar();
	});

    mainList.addEventListener("change", function (e) {
      if (e.target && e.target.classList.contains("cart-item__checkbox")) {
        syncSelectAll();
        updateOrderBar();
      }
    });

    /* 개별 삭제 */
    mainList.addEventListener("click", function (e) {
      var rm = e.target.closest(".cart-item__remove");
      if (rm) {
        var art = rm.closest(".cart-item");
        removeArticle(art);
        return;
      }
      var optBtn = e.target.closest(".cart-item__option-btn");
      if (optBtn && !optBtn.disabled) {
        var article = optBtn.closest(".cart-item");
        openOptionSheet(article);
      }
    });

    if (removeSelectedBtn) {
      removeSelectedBtn.addEventListener("click", function () {
        var snapshot = getItems().filter(function (item) {
          var cb = item.querySelector(".cart-item__checkbox");
          return cb && cb.checked;
        });
        snapshot.forEach(function (item) {
          removeArticle(item);
        });
      });
    }

    if (removeSoldoutBtn) {
      removeSoldoutBtn.addEventListener("click", function () {
        var snapshot = qsa(mainList, '.cart-item[data-soldout="true"]');
        snapshot.forEach(function (item) {
          removeArticle(item);
        });
      });
    }

    /* 시트 */
    if (colorToggle && colorPanel) {
      colorToggle.addEventListener("click", function () {
        togglePanel(colorPanel, sizePanel);
      });
    }
    if (sizeToggle && sizePanel) {
      sizeToggle.addEventListener("click", function () {
        togglePanel(sizePanel, colorPanel);
      });
    }

    if (colorPanel) {
      colorPanel.addEventListener("click", function (e) {
        var row = e.target.closest(".detail-option-row[data-color]");
        if (!row) return;
        var c = row.getAttribute("data-color");
        if (selectedColorText) selectedColorText.textContent = c;
        setActiveOptionRow(colorPanel, "data-color", c);
      });
    }

    if (sizePanel) {
      sizePanel.addEventListener("click", function (e) {
        var row = e.target.closest(".detail-option-row[data-size]");
        if (!row) return;
        var s = row.getAttribute("data-size");
        if (selectedSizeText) selectedSizeText.textContent = s;
        setActiveOptionRow(sizePanel, "data-size", s);
      });
    }

    if (plusQtyBtn) {
      plusQtyBtn.addEventListener("click", function () {
        sheetQty += 1;
        if (qtyValueEl) qtyValueEl.textContent = String(sheetQty);
        updateSheetSummary();
      });
    }

    if (minusQtyBtn) {
      minusQtyBtn.addEventListener("click", function () {
        if (sheetQty <= 1) return;
        sheetQty -= 1;
        if (qtyValueEl) qtyValueEl.textContent = String(sheetQty);
        updateSheetSummary();
      });
    }

    if (cancelSheetBtn) cancelSheetBtn.addEventListener("click", closeOptionSheet);
    if (applySheetBtn) applySheetBtn.addEventListener("click", applyOptionToArticle);

    if (sheetDim) {
      sheetDim.addEventListener("click", closeOptionSheet);
    }

    document.addEventListener("keydown", function (e) {
      if (e.key === "Escape" && sheet && !sheet.classList.contains("hidden")) {
        closeOptionSheet();
      }
    });

    syncSelectAll();
    updateOrderBar();
    syncOptionPanelsAria();

    // 초기 렌더: 버튼에 옵션 표시 (JSP가 다를 때도 안전하게 동기화)
    getItems().forEach(function (item) {
      updateOptionButton(item);
    });
  });
})();
