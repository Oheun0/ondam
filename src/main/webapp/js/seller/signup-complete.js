/* 온담 파트너 회원가입 완료 로직 */
(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    // 1. 페이지 로드 시 카드 나타나기 애니메이션 적용
    var card = document.getElementById("sellerCompleteCard");
    if (card) {
      card.classList.add("seller-auth-complete-card--in");
    }

    // 2. 로그인하기 버튼 클릭 이벤트
    var btn = document.getElementById("sellerGoLoginBtn");
    if (!btn) return;

    btn.addEventListener("click", function () {
      // body에 심어둔 context-path를 읽어서 정확한 로그인 URL로 이동
      var ctx = document.body.getAttribute("data-context-path") || "";
      window.location.href = ctx + "/seller/auth";
    });
  });
})();