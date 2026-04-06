/**
 * 공통: 상단바 카테고리(햄버거) 메뉴 → 카테고리 화면 이동 (contextPath 반영)
 */
(function () {
	"use strict";

	function getContextPath() {
		var b = document.body;
		if (b && b.getAttribute("data-context-path") != null) {
			return b.getAttribute("data-context-path") || "";
		}
		var m = document.querySelector('meta[name="context-path"]');
		return m ? m.getAttribute("content") || "" : "";
	}

	function categoryUrl() {
		return getContextPath() + "/category";
	}

	function isOnCategoryPage() {
		var ctx = getContextPath();
		var path = window.location.pathname || "";
		return path === (ctx + "/category");
	}

	document.addEventListener("DOMContentLoaded", function () {
		var btn = document.getElementById("headerCategoryMenu");
		if (!btn) return;
		btn.addEventListener("click", function (e) {
			e.preventDefault();
			// 카테고리 화면에서 다시 누르면 닫히듯이 이전 화면으로
			if (isOnCategoryPage()) {
				if (window.history.length > 1) {
					window.history.back();
				} else {
					window.location.href = getContextPath() + "/main";
				}
				return;
			}
			window.location.href = categoryUrl();
		});
	});
})();
