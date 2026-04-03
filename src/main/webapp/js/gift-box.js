(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    var bar = document.querySelector(".gift-box-tab-bar");
    if (!bar) return;

    var tabs = bar.querySelectorAll(".top-tab");
    var received = document.getElementById("gift-received-panel");
    var sent = document.getElementById("gift-sent-panel");

    tabs.forEach(function (btn) {
      btn.addEventListener("click", function () {
        var name = btn.getAttribute("data-tab");
        tabs.forEach(function (b) {
          var on = b === btn;
          b.classList.toggle("active", on);
          b.setAttribute("aria-selected", on ? "true" : "false");
        });
        if (received) {
          var showReceived = name === "received";
          received.classList.toggle("active", showReceived);
          if (showReceived) {
            received.removeAttribute("hidden");
          } else {
            received.setAttribute("hidden", "");
          }
        }
        if (sent) {
          var showSent = name === "sent";
          sent.classList.toggle("active", showSent);
          if (showSent) {
            sent.removeAttribute("hidden");
          } else {
            sent.setAttribute("hidden", "");
          }
        }
      });
    });
  });
})();
