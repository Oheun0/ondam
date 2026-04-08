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

  window.addEventListener('pageshow', function(event) {
      if (event.persisted || (window.performance && window.performance.navigation.type === 2)) {
          window.location.reload();
      }
  });
  
  document.addEventListener("DOMContentLoaded", function () {
	// 삭제할 리뷰 번호를 임시 저장할 변수
	    var reviewNoToDelete = null; 
	    var modalDelete = document.getElementById("reviewWriteModalDelete");

	    document.body.addEventListener("click", function (e) {
	      var deleteBtn = e.target.closest(".review-delete-btn");
	      if (!deleteBtn) return;
	      reviewNoToDelete = deleteBtn.getAttribute("data-review-no");
	      if (!reviewNoToDelete) return;
	      if (typeof openModal === "function") {
	        openModal(modalDelete);
	      } else {
	        modalDelete.classList.remove("hidden");
	        document.body.style.overflow = "hidden";
	      }
	    });
	    if (modalDelete) {
	      modalDelete.addEventListener("click", function (e) {
	        if (e.target.closest("[data-modal-dismiss='delete']") || e.target.closest("[data-modal-action='delete-cancel']")) {
	          modalDelete.classList.add("hidden");
	          document.body.style.overflow = "";
	          reviewNoToDelete = null;
	        }
	      });
	      var confirmBtn = document.getElementById("reviewDeleteConfirmBtn");
	      if (confirmBtn) {
	        confirmBtn.addEventListener("click", function () {
	          if (reviewNoToDelete) {
	            var ctx = document.body.getAttribute("data-context-path") || "";
	            window.location.href = ctx + "/review?action=delete&reviewNo=" + reviewNoToDelete;
	          }
	        });
	      }
	    }
		
		var backBtn = document.getElementById("appBackHeaderBtn");
		    if (backBtn) {
		      backBtn.addEventListener("click", function (e) {
		        e.preventDefault();
		        e.stopImmediatePropagation();

		        var isWritePage = document.getElementById("reviewWritePageRoot");

		        if (isWritePage) {
		          if (window.history.length > 1) {
		            window.history.back(); 
		          } else {
		            var returnUrlEl = document.getElementById("returnUrl");
		            window.location.replace(returnUrlEl ? returnUrlEl.value : (ctx + "/review?action=myList"));
		          }
		        } else {
		          var ctx = document.body.getAttribute("data-context-path") || "";
		          window.location.href = ctx + "/mypage";
		        }
		      }, true); 
		    }
		
    var root = document.getElementById("reviewWritePageRoot");
    if (!root) return;

    var starRating = 0;
	var initRatingEl = document.getElementById("initRating");
	    if (initRatingEl && initRatingEl.value) {
	        starRating = parseInt(initRatingEl.value, 10) || 0;
	    }
    var selectedFit = null;
    var selectedFabric = null;
    var photoSlots = [];
	var lastAutoText = "";

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
		  starsWrap.classList.remove("err");
		  var starError = document.getElementById("starErrorMsg");
		            if (starError) starError.style.display = "none";
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
	      var newAutoText = COMBO_TEXT[key];
	      
	      if (newAutoText) {
	        var currentText = textarea.value;
	        if (currentText.trim() === "" || currentText === lastAutoText) {
	          textarea.value = newAutoText;
	        } 
	        else {
	          if (lastAutoText && currentText.indexOf(lastAutoText) !== -1) {
	            textarea.value = currentText.replace(lastAutoText, newAutoText);
	          } else if (currentText.indexOf(newAutoText) === -1) {
	            var hasNewLine = currentText.slice(-1) === "\n";
	            textarea.value = currentText + (hasNewLine ? "" : "\n") + newAutoText;
	          }
	        }

	        if (textarea.value.length > MAX_REVIEW_CHARS) {
	          textarea.value = textarea.value.slice(0, MAX_REVIEW_CHARS);
	        }
	        lastAutoText = newAutoText;
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
	  previewList.querySelectorAll(".new-preview").forEach(el => el.remove());

	  photoSlots.forEach(function (slot, index) {
	    var item = document.createElement("div");
	    item.className = "review-write-preview-item new-preview"; // new-preview 클래스 추가
	    
	    var img = document.createElement("img");
	    img.src = slot.url;
	    img.alt = "첨부 이미지 미리보기";
	    
	    var rm = document.createElement("button");
	    rm.type = "button";
	    rm.className = "review-write-preview-remove";
	    rm.setAttribute("aria-label", "사진 삭제");
	    rm.setAttribute("data-photo-index", String(index)); // 인덱스 저장
	    
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
	      var formData = new FormData();
		  var orderItemEl = document.getElementsByName("orderItemNo")[0];
		  var orderItemNo = orderItemEl ? orderItemEl.value : "";

		  var reviewEl = document.getElementsByName("reviewNo")[0];
		  var reviewNo = reviewEl ? reviewEl.value : "";
	      
	      if (reviewNo) formData.append("reviewNo", reviewNo);
	      if (orderItemNo) formData.append("orderItemNo", orderItemNo);
	      
	      formData.append("reviewRating", starRating);
	      formData.append("reviewContent", textarea ? textarea.value.trim() : "");
	      formData.append("isBodyPublic", "1");
		  //삭제할 사진 번호들도 formData에 챙겨서 보냄
		  var deleteInput = document.getElementById("deleteImgNos");
		          if (deleteInput && deleteInput.value) {
		              formData.append("deleteImgNos", deleteInput.value);
		          }
	      photoSlots.forEach(function (slot) {
	        // 서버에서 'reviewPhotos'라는 이름으로 받음
	        formData.append("reviewPhotos", slot.file);
	      });
	      var actionUrl = document.getElementById('realSubmitForm').action;

		  fetch(actionUrl, {
		          method: 'POST',
		          body: formData
		        })
		        .then(function(response) {
		          if (response.ok) {
		            var ctx = document.body.getAttribute("data-context-path") || "";
		            var returnUrlEl = document.getElementById("returnUrl");
		            
		            if (returnUrlEl && returnUrlEl.value) {
		              if (window.history.length > 1) {
		                window.history.back();
		              } else {
		                window.location.replace(returnUrlEl.value);
		              }
		            } else {
		              window.location.replace(ctx + "/review?action=myList&tab=written");
		            }
		          } else {
		            alert("후기 등록에 실패했습니다.");
		          }
		        })
	      .catch(function(error) {
	        console.error("Error:", error);
	        alert("네트워크 오류가 발생했습니다.");
	      });

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
	        if (starRating === 0) {
	          if (starsWrap) {
	            starsWrap.classList.add("err"); 
	            starsWrap.scrollIntoView({ behavior: 'smooth', block: 'center' }); 
	          }
			  var starError = document.getElementById("starErrorMsg");
			            if (starError) starError.style.display = "block";
	          return;
	        } if (!text) {
	          openModal(modalEmpty);
	          return;
	        }
	        openModal(modalConfirm);
	      });
	    }

	// 사진 삭제 처리
	if (previewList) {
	  previewList.addEventListener("click", function (e) {
	    var rmBtn = e.target.closest(".review-write-preview-remove");
	    if (!rmBtn) return;
	    var item = rmBtn.closest(".review-write-preview-item");
	    if (!item) return;

	    var imgNo = item.getAttribute("data-img-no");
	    if (imgNo) {
	      var deleteInput = document.getElementById("deleteImgNos");
	      if (deleteInput) {
	        var vals = deleteInput.value ? deleteInput.value.split(",") : [];
	        if (!vals.includes(imgNo)) {
	          vals.push(imgNo);
	          deleteInput.value = vals.join(",");
	        }
	      }
	      item.remove();
	      console.log("DB 삭제 대상:", deleteInput.value);
	    } 
	    else {
	      var idx = parseInt(rmBtn.getAttribute("data-photo-index"), 10);
	      if (!Number.isNaN(idx) && photoSlots[idx]) {
	        URL.revokeObjectURL(photoSlots[idx].url); // 메모리 해제
	        photoSlots.splice(idx, 1);
	        renderPreviews();
	      }
	    }
	  });
	}
    updateStarDisplay();
    updateCharCount();
    updateUploadButtonState();
		
	//수정 화면에서 이전 선택 불러오기
	if (textarea && textarea.value) {
	      var initText = textarea.value;
	      for (var key in COMBO_TEXT) {
	        if (initText.indexOf(COMBO_TEXT[key]) !== -1) {
	          var parts = key.split("|");
	          selectedFit = parts[0];
	          selectedFabric = parts[1]; 
	          lastAutoText = COMBO_TEXT[key];

	          var fitChip = root.querySelector('.review-write-chip[data-chip-group="fit"][data-chip-value="' + selectedFit + '"]');
	          var fabricChip = root.querySelector('.review-write-chip[data-chip-group="fabric"][data-chip-value="' + selectedFabric + '"]');
	          
	          if (fitChip) {
	            fitChip.classList.add("review-write-chip--active");
	            fitChip.setAttribute("aria-checked", "true");
	          }
	          if (fabricChip) {
	            fabricChip.classList.add("review-write-chip--active");
	            fabricChip.setAttribute("aria-checked", "true");
	          }
	          break;
	        }
	      }
	    }
  });
})();
