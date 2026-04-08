(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    var root = document.getElementById("orderCompleteRoot");
    if (!root) return;

    var orderListBtn = document.getElementById("ocOrderListBtn");
    if (orderListBtn) {
      orderListBtn.addEventListener("click", function () {
        // 화면설계용 더미 동작 (실제 이동/검증 없음)
        window.alert("주문내역 페이지로 이동하는 기능은 준비 중입니다.");
      });
    }
  });
})();

