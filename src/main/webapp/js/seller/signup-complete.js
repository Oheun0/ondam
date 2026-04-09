/* 온담 판매자센터 회원가입 완료 (더미) */
(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    var card = document.getElementById("sellerCompleteCard");
    if (card) {
      card.classList.add("seller-auth-complete-card--in");
    }

    var btn = document.getElementById("sellerGoLoginBtn");
    if (!btn) return;

    btn.addEventListener("click", function () {
      var ctx = document.body.getAttribute("data-context-path") || "";
      console.log("[SELLER SIGNUP COMPLETE] go login");
      window.location.href = ctx + "/seller/auth/login";
    });
  });
})();

