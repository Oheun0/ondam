/* global window, document */
(function () {
  "use strict";

  function basePath() {
    if (typeof window.__ONDAM_CTX__ === "string") {
      return window.__ONDAM_CTX__;
    }
    var b = document.body;
    return (b && b.getAttribute("data-context-path")) || "";
  }

  function bind() {
    var notifyBtn = document.getElementById("sellerHeaderNotifyBtn");
    var logoutBtn = document.getElementById("sellerHeaderLogoutBtn");
    if (notifyBtn) {
      notifyBtn.addEventListener("click", function () {
        alert("알림 기능은 아직 준비 중이에요.");
      });
    }
    if (logoutBtn) {
      logoutBtn.addEventListener("click", function () {
        window.location.href = basePath() + "/seller/auth?action=logout";
      });
    }
  }

  if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", bind);
  } else {
    bind();
  }
})();
