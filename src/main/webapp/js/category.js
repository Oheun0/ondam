/**
 * 카테고리: 상단 탭 전환, 좌측 메뉴 ↔ 우측 패널, URL 쿼리(tab, type, group) 반영
 */
(function () {
	"use strict";

	function typeGroupId(typeKey) {
		var map = { top: "cat-top", bottom: "cat-bottom", outer: "cat-outer", set: "cat-set" };
		return map[typeKey] || "cat-top";
	}

	function showTopTab(name) {
		var isSituation = name === "situation";
		var tabs = document.querySelectorAll(".category-top-tabs .top-tab");
		var situationPanel = document.getElementById("situation-content");
		var typePanel = document.getElementById("type-content");

		tabs.forEach(function (btn) {
			var on = btn.getAttribute("data-tab") === name;
			btn.classList.toggle("active", on);
			btn.setAttribute("aria-selected", on ? "true" : "false");
		});

		if (situationPanel) {
			situationPanel.classList.toggle("active", isSituation);
		}
		if (typePanel) {
			typePanel.classList.toggle("active", !isSituation);
		}
	}

	function showSituationGroup(groupId) {
		var menus = document.querySelectorAll("#situation-content .situation-side");
		var groups = document.querySelectorAll("#situation-content .detail-group");
		menus.forEach(function (m) {
			var on = m.getAttribute("data-target") === groupId;
			m.classList.toggle("active", on);
		});
		groups.forEach(function (g) {
			g.classList.toggle("active", g.id === groupId);
		});
	}

	function showTypeGroup(typeKey) {
		var id = typeGroupId(typeKey);
		var menus = document.querySelectorAll("#type-content .type-side");
		var groups = document.querySelectorAll("#type-content .type-group");
		menus.forEach(function (m) {
			var on = m.getAttribute("data-type") === typeKey;
			m.classList.toggle("active", on);
		});
		groups.forEach(function (g) {
			g.classList.toggle("active", g.id === id);
		});
	}

	function applyQueryString() {
		var params = new URLSearchParams(window.location.search);
		var tab = params.get("tab") || "situation";
		var group = params.get("group") || "daily";
		var typeKey = params.get("type") || "top";

		if (tab === "type") {
			showTopTab("type");
			showTypeGroup(typeKey);
		} else {
			showTopTab("situation");
			if (["daily", "special", "hobby", "gift"].indexOf(group) >= 0) {
				showSituationGroup(group);
			} else {
				showSituationGroup("daily");
			}
		}
	}

	document.addEventListener("DOMContentLoaded", function () {
		document.querySelectorAll(".category-top-tabs .top-tab").forEach(function (tab) {
			tab.addEventListener("click", function () {
				var name = this.getAttribute("data-tab");
				if (name === "situation") {
					showTopTab("situation");
					showSituationGroup("daily");
				} else {
					showTopTab("type");
					showTypeGroup("top");
				}
			});
		});

		document.querySelectorAll("#situation-content .situation-side").forEach(function (btn) {
			btn.addEventListener("click", function () {
				var target = this.getAttribute("data-target");
				if (target) showSituationGroup(target);
			});
		});

		document.querySelectorAll("#type-content .type-side").forEach(function (btn) {
			btn.addEventListener("click", function () {
				var t = this.getAttribute("data-type");
				if (t) showTypeGroup(t);
			});
		});

		applyQueryString();
	});
})();
