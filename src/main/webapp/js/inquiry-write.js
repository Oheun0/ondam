/**
 * 상품 문의하기 — 순수 JS
 */
(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    var ctx = document.body.getAttribute("data-context-path") || "";

    var form = document.getElementById("inquiryWriteForm");
    var textarea = document.getElementById("inquiryWriteTextarea");
    var submitBtn = document.getElementById("inquiryWriteSubmitBtn");
    var isSecretInput = document.getElementById("isSecret");
	var isNameHiddenInput = document.getElementById("isNameHidden");

    var modalEmpty = document.getElementById("inquiryWriteModalEmpty");
    var modalConfirm = document.getElementById("inquiryWriteModalConfirm");
    var modalDone = document.getElementById("inquiryWriteModalDone");

    var toggleBtns = document.querySelectorAll(".inquiry-write-toggle-btn");
    toggleBtns.forEach(function (btn) {
      btn.addEventListener("click", function () {
        var group = btn.getAttribute("data-toggle-group");
		var value = btn.getAttribute("data-toggle-value");
        if (group === "visibility") {
          // 비공개면 1, 공개면 0 세팅
          isSecretInput.value = btn.getAttribute("data-toggle-value") === "private" ? "1" : "0";
        }
		else if (group === "name") {
		      document.getElementById("isNameHidden").value = (value === "hide" ? "1" : "0");
		    }
        var siblings = document.querySelectorAll('.inquiry-write-toggle-btn[data-toggle-group="' + group + '"]');
        siblings.forEach(function (sib) {
          sib.classList.remove("inquiry-write-toggle-btn--active");
          sib.setAttribute("aria-checked", "false");
        });
        btn.classList.add("inquiry-write-toggle-btn--active");
        btn.setAttribute("aria-checked", "true");
      });
    });

    function closeModal(modalEl) {
      modalEl.classList.add("hidden");
      modalEl.setAttribute("aria-hidden", "true");
    }
    function openModal(modalEl) {
      modalEl.classList.remove("hidden");
      modalEl.setAttribute("aria-hidden", "false");
    }

    document.querySelectorAll("[data-modal-dismiss]").forEach(function (dim) {
      dim.addEventListener("click", function () {
        closeModal(dim.closest(".inquiry-write-modal"));
      });
    });
    document.querySelectorAll("[data-modal-action$='-cancel'], [data-modal-action$='-ok']").forEach(function (btn) {
      btn.addEventListener("click", function () {
        var action = btn.getAttribute("data-modal-action");
        if (action === "empty-ok") closeModal(modalEmpty);
        if (action === "confirm-cancel") closeModal(modalConfirm);

		if (action === "done-ok") {
		    var returnUrlEl = document.getElementById("returnUrl");
		    if (returnUrlEl && returnUrlEl.value !== "") {
		        window.location.replace(returnUrlEl.value);
		    } else {
		        var productNo = document.getElementById("productNo").value;
		        window.location.replace(ctx + "/product?action=detail&productNo=" + productNo);
		    }
		}
      });
    });

    if (submitBtn) {
      submitBtn.addEventListener("click", function () {
        if (textarea.value.trim() === "") {
          openModal(modalEmpty); // 글이 비었으면 알림창
        } else {
          openModal(modalConfirm); // 글이 있으면 진짜 등록할건지 물어봄
        }
      });
    }

    var finalSubmitBtn = document.querySelector("[data-modal-action='confirm-submit']");
    if (finalSubmitBtn) {
      finalSubmitBtn.addEventListener("click", function () {
        closeModal(modalConfirm);
		
		var inquiryNoEl = document.getElementById("inquiryNo");
		var inquiryNo = inquiryNoEl ? inquiryNoEl.value : "";
		var actionName = (inquiryNo && inquiryNo !== "") ? "edit" : "write";

        var formData = new FormData(form);
        var urlSearchParams = new URLSearchParams(formData);
		
        fetch(ctx + "/inquiry?action=" + actionName, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
          },
          body: urlSearchParams.toString()
        })
        .then(function(response) {
          return response.json();
        })
        .then(function(data) {
          if (data.success) {
            openModal(modalDone);
          } else {
            alert("문의 등록에 실패했습니다. 다시 시도해주세요.");
          }
        })
        .catch(function(error) {
          console.error("Error:", error);
          alert("서버 통신 오류가 발생했습니다.");
        });
      });
    }
  });
})();