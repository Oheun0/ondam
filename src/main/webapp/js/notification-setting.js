(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    var root = document.getElementById("notificationSettingRoot");
    if (!root) return;

    root.addEventListener("click", function (e) {
      var row = e.target && e.target.closest(".ns-row[role='switch']");
      if (!row) return;

      var checked = row.getAttribute("aria-checked") === "true";
      var next = !checked;
      row.setAttribute("aria-checked", next ? "true" : "false");
      row.classList.toggle("is-on", next);

      // 더미: 실제 저장/연동 없음
      // console.log("[notification-setting] toggle", row.getAttribute("data-ns-key"), next);
    });
  });
})();

