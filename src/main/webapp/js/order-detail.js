// order-detail.js
window.addEventListener('pageshow', function(event) {
  // 뒤로가기로 페이지에 도달했을 경우 무조건 새로고침!
  if (event.persisted || (window.performance && window.performance.navigation.type === 2)) {
      window.location.reload();
  }
});

document.addEventListener("DOMContentLoaded", function () {
  var contextPath = document.body.getAttribute("data-context-path") || "";

  var backBtn = document.getElementById("appBackHeaderBtn");
  if (backBtn) {
    var newBtn = backBtn.cloneNode(true);
    newBtn.removeAttribute("onclick");
    backBtn.parentNode.replaceChild(newBtn, backBtn);
    newBtn.addEventListener("click", function () {
      window.location.href = contextPath + "/order/order-list";
    });
  }

  root.addEventListener("click", function (e) {
    var btn = e.target.closest(".review-write-btn");
    if (btn) {
      var orderItemNo = btn.getAttribute("data-order-item-no");
      if (orderItemNo) {
        // 현재 주소를 인코딩해서 returnUrl 이라는 이름으로 보냄
        var returnUrl = encodeURIComponent(window.location.href);
        window.location.href = contextPath + "/review?action=writeForm&orderItemNo=" + orderItemNo + "&returnUrl=" + returnUrl;
      }
    }
  });
});