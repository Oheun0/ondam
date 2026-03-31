/**
 * 홈: 배너 슬라이드, 전체 보기·옷 종류 칩 → 카테고리 이동
 */
(function () {
	"use strict";

	function getContextPath() {
		var b = document.body;
		if (b && b.getAttribute("data-context-path") != null) {
			return b.getAttribute("data-context-path") || "";
		}
		return "";
	}

	function categoryUrl(params) {
		var base = getContextPath() + "/category";
		if (params && typeof params === "string") {
			return base + (params.charAt(0) === "?" ? params : "?" + params);
		}
		return base;
	}

	document.addEventListener("DOMContentLoaded", function () {
		var allView = document.getElementById("homeCategoryAllView");
		if (allView) {
			allView.addEventListener("click", function (e) {
				e.preventDefault();
				window.location.href = categoryUrl("");
			});
		}

		document.querySelectorAll(".home-category-chip[data-category-tab]").forEach(function (el) {
			el.addEventListener("click", function (e) {
				e.preventDefault();
				var tab = el.getAttribute("data-category-tab");
				var type = el.getAttribute("data-category-type");
				var q = "tab=" + encodeURIComponent(tab || "type");
				if (type) q += "&type=" + encodeURIComponent(type);
				window.location.href = categoryUrl(q);
			});
		});

		/* 배너 슬라이드 */
		var track = document.getElementById("sliderTrack");
		var dotsWrap = document.getElementById("bannerDots");
		if (!track) return;
		var slides = track.querySelectorAll(".banner-slide");
		var total = slides.length;
		if (total === 0) return;
		var stepPct = 100 / total;
		var index = 0;
		var intervalMs = 5000;

		function goTo(i) {
			index = (i + total) % total;
			track.style.transform = "translateX(-" + index * stepPct + "%)";
			if (dotsWrap) {
				var dots = dotsWrap.querySelectorAll(".banner-dot");
				for (var d = 0; d < dots.length; d++) {
					var on = d === index;
					dots[d].classList.toggle("active", on);
					dots[d].setAttribute("aria-selected", on ? "true" : "false");
				}
			}
		}

		if (dotsWrap) {
			dotsWrap.addEventListener("click", function (e) {
				var btn = e.target.closest(".banner-dot");
				if (!btn || !dotsWrap.contains(btn)) return;
				var i = parseInt(btn.getAttribute("data-index"), 10);
				if (!isNaN(i)) goTo(i);
			});
		}

		setInterval(function () {
			goTo(index + 1);
		}, intervalMs);
	});
})();
