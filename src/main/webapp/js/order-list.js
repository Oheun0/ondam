(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    var root = document.getElementById("orderListRoot");
    if (!root) return;

    var contextPath = document.body.getAttribute("data-context-path") || "";

    // 뒤로가기 → 마이페이지로
    var backBtn = document.getElementById("appBackHeaderBtn");
    if (backBtn) {
      var newBtn = backBtn.cloneNode(true);
      newBtn.removeAttribute("onclick");
      backBtn.parentNode.replaceChild(newBtn, backBtn);
      newBtn.addEventListener("click", function () {
        window.location.href = contextPath + "/mypage";
      });
    }

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