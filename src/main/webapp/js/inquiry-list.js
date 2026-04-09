/**
 * 문의내역 — 더보기 드롭다운 및 액션 처리 (순수 JS)
 */
(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    var root = document.getElementById("inquiryListPageRoot");
    if (!root) return;

    var ctx = document.body.getAttribute("data-context-path") || "";

    // 모달 관련 요소 (inquiry-write와 동일한 ID/클래스 체계)
    var deleteModal = document.getElementById("inquiryDeleteModal");
    var confirmDeleteBtn = document.getElementById("confirmDeleteBtn");
    var currentDeleteId = null; // 삭제할 번호를 기억하기 위한 변수

    // [1] 뒤로가기
    var backBtn = document.getElementById("appBackHeaderBtn");
    if (backBtn) {
      backBtn.addEventListener("click", function (e) {
        e.preventDefault();
        e.stopImmediatePropagation();
        window.location.href = ctx + "/mypage"; 
      });
    }

    // [2] 메뉴 및 모달 닫기 공통 함수
    function closeAllMenus() {
      root.querySelectorAll(".inquiry-list-dropdown").forEach(function (el) {
        el.classList.add("hidden");
        el.setAttribute("aria-hidden", "true");
      });
      root.querySelectorAll(".inquiry-list-menu-btn").forEach(function (btn) {
        btn.setAttribute("aria-expanded", "false");
      });
    }

    function openDeleteModal(inquiryNo) {
      currentDeleteId = inquiryNo; // 삭제할 번호 저장
      if (deleteModal) {
        deleteModal.classList.remove("hidden");
        deleteModal.setAttribute("aria-hidden", "false");
      }
    }

    function closeDeleteModal() {
      if (deleteModal) {
        deleteModal.classList.add("hidden");
        deleteModal.setAttribute("aria-hidden", "true");
        currentDeleteId = null; // 초기화
      }
    }

    // [3] 점 세 개(more_horiz) 메뉴 클릭 토글
    root.querySelectorAll(".inquiry-list-menu-btn").forEach(function (btn) {
      btn.addEventListener("click", function (e) {
        e.stopPropagation();
        var wrap = btn.closest(".inquiry-list-menu-wrap");
        var menu = wrap ? wrap.querySelector(".inquiry-list-dropdown") : null;
        if (!menu) return;
        
        var wasOpen = !menu.classList.contains("hidden");
        closeAllMenus();

        if (!wasOpen) {
          menu.classList.remove("hidden");
          menu.setAttribute("aria-hidden", "false");
          btn.setAttribute("aria-expanded", "true");
        }
      });
    });

    // [4] 드롭다운 내부 아이템(수정/삭제) 클릭 이벤트
    root.querySelectorAll(".inquiry-list-dropdown__item").forEach(function (item) {
      item.addEventListener("click", function (e) {
        e.preventDefault();
        var action = item.getAttribute("data-menu-action");
        var inquiryNo = item.getAttribute("data-id");
        closeAllMenus();

        if (action === "edit") {
          window.location.href = ctx + "/inquiry?action=editForm&inquiryNo=" + inquiryNo;
        } else if (action === "delete") {
          openDeleteModal(inquiryNo); 
        }
      });
    });

    // [5] 모달 닫기 이벤트 (딤 클릭 or 취소 버튼)
    document.querySelectorAll("[data-modal-dismiss='delete']").forEach(function (el) {
      el.addEventListener("click", closeDeleteModal);
    });

    // [6] 모달 내 '삭제하기'
    if (confirmDeleteBtn) {
      confirmDeleteBtn.addEventListener("click", function () {
        if (currentDeleteId) {
          window.location.replace(ctx + "/inquiry?action=delete&inquiryNo=" + currentDeleteId);
        }
      });
    }

    document.addEventListener("click", function (e) {
      if (!e.target.closest(".inquiry-list-menu-wrap")) {
        closeAllMenus();
      }
    });
  });
})();