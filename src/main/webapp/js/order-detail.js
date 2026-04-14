/* global document, window, encodeURIComponent */
(function () {
  "use strict";

  window.addEventListener('pageshow', function(event) {
    if (event.persisted || (window.performance && window.performance.navigation.type === 2)) {
        window.location.reload();
    }
  });

  document.addEventListener("DOMContentLoaded", function () {
    var contextPath = document.body.getAttribute("data-context-path") || "";

    var root = document.getElementById("orderDetailRoot");
    if (!root) return;

    var backBtn = document.getElementById("appBackHeaderBtn");
    if (backBtn) {
      var newBtn = backBtn.cloneNode(true);
      newBtn.removeAttribute("onclick");
      backBtn.parentNode.replaceChild(newBtn, backBtn);
      newBtn.addEventListener("click", function () {
        window.location.href = contextPath + "/order/order-list";
      });
    }

    root.addEventListener("click", function (e) {
      var btn = e.target.closest(".review-write-btn");
      if (btn) {
        var orderItemNo = btn.getAttribute("data-order-item-no");
        if (orderItemNo) {
          var returnUrl = encodeURIComponent(window.location.href);
          window.location.href = contextPath + "/review?action=writeForm&orderItemNo=" + orderItemNo + "&returnUrl=" + returnUrl;
        }
      }
    });

  });
})();