(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    var root = document.getElementById("notificationSettingRoot");
    if (!root) return;

    var contextPath = document.body.getAttribute("data-context-path") || "";
    var typeMapping = {
      "order": 1,
      "shipping": 2,
      "group": 3,
      "recommend": 4
    };
    root.addEventListener("click", function (e) {
      var row = e.target && e.target.closest(".ns-row[role='switch']");
      if (!row) return;
      var key = row.getAttribute("data-ns-key");
      var notificationType = typeMapping[key];
      if (!notificationType) {
        console.error("알 수 없는 알림 타입입니다:", key);
        return;
      }
      var checked = row.getAttribute("aria-checked") === "true";
      var next = !checked;
      var isEnabled = next ? 1 : 0;

      row.setAttribute("aria-checked", next ? "true" : "false");
      row.classList.toggle("is-on", next);

      var formData = new URLSearchParams();
      formData.append("notificationType", notificationType);
      formData.append("isEnabled", isEnabled);

      fetch(contextPath + "/notification/notification-setting?action=toggle", {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded"
        },
        body: formData.toString()
      })
      .then(function(response) {
        return response.json();
      })
      .then(function(data) {
        if (!data.ok) {
          alert(data.message || "설정 변경에 실패했습니다.");
          row.setAttribute("aria-checked", checked ? "true" : "false");
          row.classList.toggle("is-on", checked);
        } else {
          console.log("[notification-setting] 변경 성공:", key, "->", next ? "ON" : "OFF");
        }
      })
      .catch(function(error) {
        console.error("통신 에러:", error);
        alert("서버와 통신 중 오류가 발생했습니다.");
        row.setAttribute("aria-checked", checked ? "true" : "false");
        row.classList.toggle("is-on", checked);
      });
    });
  });
})();