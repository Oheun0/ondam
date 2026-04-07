/**
 * 상품 문의 작성 — 설정 토글, 모달 제어 (순수 JS)
 */
(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    var root = document.getElementById("inquiryWritePageRoot");
    if (!root) return;

    var textarea = document.getElementById("inquiryWriteTextarea");
    var submitBtn = document.getElementById("inquiryWriteSubmitBtn");

    var modalEmpty = document.getElementById("inquiryWriteModalEmpty");
    var modalConfirm = document.getElementById("inquiryWriteModalConfirm");
    var modalDone = document.getElementById("inquiryWriteModalDone");

    var selectedVisibility = "public";
    var selectedName = "show";

    var backBtn = document.getElementById("appBackHeaderBtn");
    if (backBtn) {
      backBtn.addEventListener("click", function () {
        if (window.history.length > 1) {
          window.history.back();
        } else {
          var ctx = document.body.getAttribute("data-context-path") || "";
          window.location.href = ctx + "/main";
        }
      });
    }

    function setToggle(groupName, value, clickedBtn) {
      root.querySelectorAll('.inquiry-write-toggle-btn[data-toggle-group="' + groupName + '"]').forEach(function (b) {
        var on = b === clickedBtn;
        b.classList.toggle("inquiry-write-toggle-btn--active", on);
        b.setAttribute("aria-checked", on ? "true" : "false");
      });
      if (groupName === "visibility") selectedVisibility = value;
      if (groupName === "name") selectedName = value;
    }

    root.querySelectorAll(".inquiry-write-toggle-btn").forEach(function (btn) {
      btn.addEventListener("click", function () {
        var group = btn.getAttribute("data-toggle-group");
        var value = btn.getAttribute("data-toggle-value");
        if (!group || !value) return;
        setToggle(group, value, btn);
      });
    });

    function openModal(el) {
      if (!el) return;
      el.classList.remove("hidden");
      document.body.style.overflow = "hidden";
    }

    function closeModal(el) {
      if (!el) return;
      el.classList.add("hidden");
      document.body.style.overflow = "";
    }

    function closeEmptyModalAndFocus() {
      closeModal(modalEmpty);
      if (textarea) {
        textarea.focus();
        try {
          var len = textarea.value.length;
          textarea.setSelectionRange(len, len);
        } catch (e) {
          /* ignore */
        }
      }
    }

    if (modalEmpty) {
      modalEmpty.querySelectorAll("[data-modal-dismiss='empty']").forEach(function (dim) {
        dim.addEventListener("click", closeEmptyModalAndFocus);
      });
      var emptyOk = modalEmpty.querySelector("[data-modal-action='empty-ok']");
      if (emptyOk) emptyOk.addEventListener("click", closeEmptyModalAndFocus);
    }

    if (modalConfirm) {
      modalConfirm.querySelectorAll("[data-modal-dismiss='confirm']").forEach(function (dim) {
        dim.addEventListener("click", function () {
          closeModal(modalConfirm);
        });
      });
      var cancelBtn = modalConfirm.querySelector("[data-modal-action='confirm-cancel']");
      if (cancelBtn) cancelBtn.addEventListener("click", function () {
        closeModal(modalConfirm);
      });
      var submitModalBtn = modalConfirm.querySelector("[data-modal-action='confirm-submit']");
      if (submitModalBtn) submitModalBtn.addEventListener("click", function () {
        var payload = {
          inquiryText: textarea ? textarea.value.trim() : "",
          visibility: selectedVisibility,
          name: selectedName,
        };
        console.log("[inquiry-write] 등록(임시)", payload);
        closeModal(modalConfirm);
        openModal(modalDone);
      });
    }

    if (modalDone) {
      modalDone.querySelectorAll("[data-modal-dismiss='done']").forEach(function (dim) {
        dim.addEventListener("click", function () {
          closeModal(modalDone);
        });
      });
      var doneOk = modalDone.querySelector("[data-modal-action='done-ok']");
      if (doneOk) doneOk.addEventListener("click", function () {
        closeModal(modalDone);
      });
    }

    document.addEventListener("keydown", function (e) {
      if (e.key !== "Escape") return;
      if (modalDone && !modalDone.classList.contains("hidden")) {
        closeModal(modalDone);
        e.preventDefault();
        return;
      }
      if (modalConfirm && !modalConfirm.classList.contains("hidden")) {
        closeModal(modalConfirm);
        e.preventDefault();
        return;
      }
      if (modalEmpty && !modalEmpty.classList.contains("hidden")) {
        closeEmptyModalAndFocus();
        e.preventDefault();
      }
    });

    if (submitBtn) {
      submitBtn.addEventListener("click", function () {
        var text = textarea ? textarea.value.trim() : "";
        if (!text) {
          openModal(modalEmpty);
          return;
        }
        openModal(modalConfirm);
      });
    }
  });
})();

