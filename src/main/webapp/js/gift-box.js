(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    /* 1. 탭 전환 로직 (기존 코드 유지) */
    var bar = document.querySelector(".gift-box-tab-bar");
    if (bar) {
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
            if (showReceived) received.removeAttribute("hidden");
            else received.setAttribute("hidden", "");
          }
          if (sent) {
            var showSent = name === "sent";
            sent.classList.toggle("active", showSent);
            if (showSent) sent.removeAttribute("hidden");
            else sent.setAttribute("hidden", "");
          }
        });
      });
    }

    /* 2. 배송지 선택 모달 로직 (추가됨) */
    var modal = document.getElementById("opAddressModal");
    var currentGiftNo = null; // 현재 어떤 선물의 배송지를 바꾸고 있는지 저장

    // 모달 열기 함수 (전역 범위로 빼고 싶다면 window.openAddressModal로 설정)
    window.openAddressModal = function(giftNo) {
      currentGiftNo = giftNo;
      if (modal) modal.classList.remove("hidden");
    };

    // 모달 닫기 함수
    window.closeAddressModal = function() {
      if (modal) modal.classList.add("hidden");
      currentGiftNo = null;
    };

    // 닫기 버튼 및 배경 클릭 이벤트
    var closeBtn = document.getElementById("opAddressModalCloseBtn");
    var dim = document.getElementById("opAddressModalDim");
    [closeBtn, dim].forEach(function(el) {
      if (el) el.addEventListener("click", window.closeAddressModal);
    });

    // 배송지 아이템 클릭 이벤트
    var addressItems = document.querySelectorAll(".op-address-item");
    addressItems.forEach(function(item) {
      item.addEventListener("click", function() {
        var addrNo = this.getAttribute("data-no");
        var name = this.getAttribute("data-receiver-name");
        var tel = this.getAttribute("data-receiver-tel");
        var addr = this.getAttribute("data-address");
        var detail = this.getAttribute("data-detail");
        var zip = this.getAttribute("data-zipcode");
		var addressName = this.getAttribute("data-address-name");

        // A. 화면상의 주소 정보 업데이트
        var targetBox = document.getElementById("display-address-" + currentGiftNo);
        if (targetBox) {
			var labelEl = targetBox.querySelector(".gift-address-label");
			      if (labelEl) {
			        labelEl.textContent = "선택된 배송지 : " + addressName;
			      }
			
          var textEl = targetBox.querySelector(".gift-address-text");
          if (textEl) {
            textEl.innerHTML = name + " · " + tel + "<br>(" + zip + ") " + addr + " " + (detail || "");
          }
        }

        // B. '수락하기' 버튼의 URL 업데이트 (addressNo 파라미터 교체)
        var acceptBtn = document.getElementById("accept-btn-" + currentGiftNo);
        if (acceptBtn) {
          var originalHref = acceptBtn.getAttribute("href");
          // 기존에 addressNo가 붙어있다면 제거하고 새로 붙임
          var baseUrl = originalHref.split("&addressNo=")[0];
          acceptBtn.setAttribute("href", baseUrl + "&addressNo=" + addrNo);
        }

        window.closeAddressModal();
      });
    });
  });
})();