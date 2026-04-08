(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    var root = document.getElementById("orderListRoot");
    if (!root) return;

    root.addEventListener("click", function (e) {
      var btn = e.target && e.target.closest(".ol-detail-btn[data-order-id]");
      if (!btn) return;
      var id = btn.getAttribute("data-order-id") || "";
      // 더미 동작: 실제 이동/서버 연동 없음
      window.alert("상세보기(더미)\n주문번호: " + id);
    });
  });
})();

