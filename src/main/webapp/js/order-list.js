(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    var contextPath = document.body.getAttribute("data-context-path") || "";

    var backBtn = document.querySelector("#appBackHeaderBtn, .app-back-header__btn, .app-back-btn, .back-btn");
    
    if (backBtn) {
      var newBtn = backBtn.cloneNode(true);
      newBtn.removeAttribute("onclick");
      backBtn.parentNode.replaceChild(newBtn, backBtn);
      newBtn.addEventListener("click", function (e) {
        e.preventDefault(); // 기본 동작 막기
        window.location.href = contextPath + "/mypage"; 
      });
    }

    // 주문 상세 페이지 이동
    var root = document.getElementById("orderListRoot");
    if (root) {
      root.addEventListener("click", function (e) {
        var btn = e.target.closest(".ol-detail-btn[data-order-id]");
        if (!btn) return; // 버튼이 아니면 무시

        var orderNo = btn.getAttribute("data-order-id");
        if (orderNo) {
          window.location.href = contextPath + "/order/order-detail?orderNo=" + orderNo;
        }
      });
    }   
  });
})();