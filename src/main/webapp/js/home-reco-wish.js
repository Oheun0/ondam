/**
 * 홈 추천 상품 — 찜(하트) 토글 (순수 JS)
 */
(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    var root = document.querySelector(".home-reco-scroll");
    if (!root) return;

    root.addEventListener("click", function (e) {
      var wishBtn = e.target.closest(".related-wish-btn");
      if (!wishBtn || !root.contains(wishBtn)) return;
      e.preventDefault();
      e.stopPropagation();

      var on = !wishBtn.classList.contains("is-active");
      wishBtn.classList.toggle("is-active", on);
      wishBtn.setAttribute("aria-pressed", on ? "true" : "false");
      wishBtn.setAttribute("aria-label", on ? "찜 해제" : "찜하기");

      var icon = wishBtn.querySelector("span.material-icons-outlined, span.material-icons");
      if (icon) {
        if (on) {
          icon.className = "material-icons";
          icon.textContent = "favorite";
        } else {
          icon.className = "material-icons-outlined";
          icon.textContent = "favorite_border";
        }
      }
    });
  });
})();

