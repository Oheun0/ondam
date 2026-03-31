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

	document.addEventListener("DOMContentLoaded", function () {
		var btn = document.getElementById("headerCategoryMenu");
		if (!btn) return;
		btn.addEventListener("click", function (e) {
			e.preventDefault();
			window.location.href = categoryUrl();
		});
	});
})();
