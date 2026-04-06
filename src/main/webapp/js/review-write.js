/**
 * 후기 작성 — 별점, 칩, 자동 문장, 사진 미리보기, 모달 (순수 JS)
 */
(function () {
  "use strict";

  var MAX_PHOTOS = 3;
  var MAX_REVIEW_CHARS = 1000;

  var COMBO_TEXT = {
    "tight|soft": "딱 맞고 소재도 부드러워서 좋아요",
    "tight|normal": "딱 맞고 소재도 무난해서 입기 좋아요",
    "tight|rough": "딱 맞지만 소재가 조금 거칠게 느껴졌어요",
    "comfy|soft": "편하게 맞고 소재도 부드러워서 좋아요",
    "comfy|normal": "편하게 맞고 소재도 무난해서 편하게 입기 좋아요",
    "comfy|rough": "편하게 맞지만 소재가 조금 거칠게 느껴졌어요",
    "loose|soft": "넉넉하게 맞고 소재도 부드러워서 편하게 입기 좋아요",
    "loose|normal": "넉넉하게 맞고 소재도 무난해서 편하게 입기 좋아요",
    "loose|rough": "넉넉하게 맞지만 소재가 조금 거칠게 느껴졌어요",
  };

  document.addEventListener("DOMContentLoaded", function () {
    var root = document.getElementById("reviewWritePageRoot");
    if (!root) return;

    var starRating = 0;
    var selectedFit = null;
    var selectedFabric = null;
    var photoSlots = [];

    var starsWrap = document.getElementById("reviewWriteStars");
    var ratingNum = document.getElementById("reviewWriteRatingNum");
    var textarea = document.getElementById("reviewWriteTextarea");
    var charCurrentEl = document.getElementById("reviewWriteCharCurrent");
    var fileInput = document.getElementById("reviewWriteFileInput");
    var uploadBtn = document.getElementById("reviewWriteUploadBtn");
    var previewList = document.getElementById("reviewWritePreviewList");
    var submitBtn = document.getElementById("reviewWriteSubmitBtn");

    var modalEmpty = document.getElementById("reviewWriteModalEmpty");
    var modalConfirm = document.getElementById("reviewWriteModalConfirm");

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

    function updateStarDisplay() {
      if (!starsWrap || !ratingNum) return;
      var btns = starsWrap.querySelectorAll(".review-write-star-btn");
      btns.forEach(function (btn) {
        var n = parseInt(btn.getAttribute("data-star"), 10);
        var icon = btn.querySelector(".detail-review-star");
        if (!icon) return;
        if (n <= starRating) {
          icon.classList.remove("detail-review-star--empty");
          icon.classList.add("detail-review-star--full");
        } else {
          icon.classList.remove("detail-review-star--full");
          icon.classList.add("detail-review-star--empty");
        }
      });
      if (starRating > 0) {
        ratingNum.textContent = starRating + "점";
        ratingNum.removeAttribute("hidden");
        ratingNum.setAttribute("aria-hidden", "false");
      } else {
        ratingNum.textContent = "";
        ratingNum.setAttribute("hidden", "");
        ratingNum.setAttribute("aria-hidden", "true");
      }
    }

    if (starsWrap) {
      starsWrap.querySelectorAll(".review-write-star-btn").forEach(function (btn) {
        btn.addEventListener("click", function () {
          var n = parseInt(btn.getAttribute("data-star"), 10);
          if (Number.isNaN(n)) return;
          starRating = n;
          updateStarDisplay();
        });
      });
    }

    function setChipGroup(groupName, value, clickedBtn) {
      root.querySelectorAll('.review-write-chip[data-chip-group="' + groupName + '"]').forEach(function (b) {
        var on = b === clickedBtn;
        b.classList.toggle("review-write-chip--active", on);
        b.setAttribute("aria-checked", on ? "true" : "false");
      });
      if (groupName === "fit") {
        selectedFit = value;
      } else if (groupName === "fabric") {
        selectedFabric = value;
      }
      applyComboToTextarea();
    }

    function updateCharCount() {
      if (!textarea || !charCurrentEl) return;
      var v = textarea.value;
      if (v.length > MAX_REVIEW_CHARS) {
        textarea.value = v.slice(0, MAX_REVIEW_CHARS);
        v = textarea.value;
      }
      charCurrentEl.textContent = String(v.length);
    }

    function applyComboToTextarea() {
      if (!textarea) return;
      if (!selectedFit || !selectedFabric) return;
      var key = selectedFit + "|" + selectedFabric;
      var text = COMBO_TEXT[key];
      if (text) {
        if (text.length > MAX_REVIEW_CHARS) {
          text = text.slice(0, MAX_REVIEW_CHARS);
        }
        textarea.value = text;
        updateCharCount();
      }
    }

    if (textarea) {
      textarea.addEventListener("input", updateCharCount);
      textarea.addEventListener("paste", function () {
        requestAnimationFrame(updateCharCount);
      });
    }

    root.querySelectorAll(".review-write-chip").forEach(function (chip) {
      chip.addEventListener("click", function () {
        var group = chip.getAttribute("data-chip-group");
        var val = chip.getAttribute("data-chip-value");
        if (!group || !val) return;
        setChipGroup(group, val, chip);
      });
    });

    function updateUploadButtonState() {
      if (!uploadBtn) return;
      var full = photoSlots.length >= MAX_PHOTOS;
      uploadBtn.disabled = full;
      uploadBtn.setAttribute("aria-disabled", full ? "true" : "false");
    }

    function renderPreviews() {
      if (!previewList) return;
      previewList.innerHTML = "";
      photoSlots.forEach(function (slot, index) {
        var item = document.createElement("div");
        item.className = "review-write-preview-item";
        var img = document.createElement("img");
        img.src = slot.url;
        img.alt = "첨부 이미지 미리보기";
        var rm = document.createElement("button");
        rm.type = "button";
        rm.className = "review-write-preview-remove";
        rm.setAttribute("aria-label", "사진 삭제");
        rm.setAttribute("data-photo-index", String(index));
        var x = document.createElement("span");
        x.className = "material-icons";
        x.textContent = "close";
        x.setAttribute("aria-hidden", "true");
        rm.appendChild(x);
        item.appendChild(img);
        item.appendChild(rm);
        previewList.appendChild(item);
      });

      updateUploadButtonState();

      previewList.querySelectorAll(".review-write-preview-remove").forEach(function (rm) {
        rm.addEventListener("click", function () {
          var idx = parseInt(rm.getAttribute("data-photo-index"), 10);
          if (Number.isNaN(idx) || !photoSlots[idx]) return;
          URL.revokeObjectURL(photoSlots[idx].url);
          photoSlots.splice(idx, 1);
          renderPreviews();
        });
      });
    }

    if (uploadBtn && fileInput) {
      uploadBtn.addEventListener("click", function () {
        if (uploadBtn.disabled) return;
        fileInput.click();
      });
      fileInput.addEventListener("change", function () {
        var files = fileInput.files;
        if (!files || !files.length) return;
        for (var i = 0; i < files.length; i++) {
          if (photoSlots.length >= MAX_PHOTOS) break;
          var f = files[i];
          if (!f.type || f.type.indexOf("image/") !== 0) continue;
          var url = URL.createObjectURL(f);
          photoSlots.push({ url: url, file: f });
        }
        fileInput.value = "";
        renderPreviews();
      });
      updateUploadButtonState();
    }

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
      if (emptyOk) {
        emptyOk.addEventListener("click", closeEmptyModalAndFocus);
      }
    }

    if (modalConfirm) {
      modalConfirm.querySelectorAll("[data-modal-dismiss='confirm']").forEach(function (dim) {
        dim.addEventListener("click", function () {
          closeModal(modalConfirm);
        });
      });
      var cancelBtn = modalConfirm.querySelector("[data-modal-action='confirm-cancel']");
      if (cancelBtn) {
        cancelBtn.addEventListener("click", function () {
          closeModal(modalConfirm);
        });
      }
      var submitModalBtn = modalConfirm.querySelector("[data-modal-action='confirm-submit']");
      if (submitModalBtn) {
        submitModalBtn.addEventListener("click", function () {
          var payload = {
            starRating: starRating,
            fit: selectedFit,
            fabric: selectedFabric,
            reviewText: textarea ? textarea.value.trim() : "",
            photoCount: photoSlots.length,
            photos: photoSlots.map(function (s) {
              return s.file ? s.file.name : "";
            }),
          };
          console.log("[review-write] 등록(임시)", payload);
          closeModal(modalConfirm);
        });
      }
    }

    document.addEventListener("keydown", function (e) {
      if (e.key !== "Escape") return;
      if (modalEmpty && !modalEmpty.classList.contains("hidden")) {
        closeEmptyModalAndFocus();
        e.preventDefault();
      } else if (modalConfirm && !modalConfirm.classList.contains("hidden")) {
        closeModal(modalConfirm);
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

    updateStarDisplay();
    updateCharCount();
    updateUploadButtonState();
  });
})();
