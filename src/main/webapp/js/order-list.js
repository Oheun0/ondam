(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    var root = document.getElementById("orderListRoot");
    if (!root) return;

    var contextPath = document.body.getAttribute("data-context-path") || "";

    root.addEventListener("click", function (e) {
      var btn = e.target && e.target.closest(".ol-detail-btn[data-order-id]");
      if (!btn) return;
      
      var orderNo = btn.getAttribute("data-order-id") || "";
      if (orderNo) {
        window.location.href = contextPath + "/order/order-detail?orderNo=" + orderNo;
      }
    });
  });
})();