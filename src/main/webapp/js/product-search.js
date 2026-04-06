/**
 * 상품 검색 페이지 — 순수 DOM API (jQuery 미사용)
 * - 최근 검색어 편집/삭제, 인기 검색어 클릭, 폼 검색(더미)
 */
(function () {
  "use strict";

  var MAX_RECENT = 10;
  /* 최신 검색어가 배열 앞쪽(왼쪽 칩) */
  var recentKeywords = [
    "니트",
    "청바지",
    "가디건",
    "바람막이",
    "셔츠",
    "조끼",
    "슬랙스",
    "원피스",
    "운동화",
    "자켓",
  ];

  var sectionEl = document.getElementById("recentSearchSection");
  var listEl = document.getElementById("recentSearchList");
  var editBtn = document.getElementById("searchEditBtn");
  var formEl = document.getElementById("productSearchForm");
  var inputEl = document.getElementById("searchQueryInput");
  var backBtn = document.getElementById("searchBackBtn");

  var editing = false;

  function normalizeQuery(s) {
    return String(s || "")
      .replace(/\s+/g, " ")
      .trim();
  }

  function renderRecent() {
    if (!listEl) return;
    listEl.innerHTML = "";

    recentKeywords.forEach(function (kw) {
      var chip = document.createElement("div");
      chip.className = "recent-search-chip";
      chip.setAttribute("role", "listitem");

      var btn = document.createElement("button");
      btn.type = "button";
      btn.className = "recent-search-chip-btn";
      btn.dataset.keyword = kw;

      if (editing) {
        btn.classList.add("is-editing");
        btn.setAttribute("aria-label", kw + " 삭제");
        var textSpan = document.createElement("span");
        textSpan.className = "recent-search-chip-text";
        textSpan.textContent = kw;
        var xSpan = document.createElement("span");
        xSpan.className = "recent-search-chip-x";
        xSpan.setAttribute("aria-hidden", "true");
        xSpan.textContent = "\u00D7";
        btn.appendChild(textSpan);
        btn.appendChild(xSpan);
      } else {
        btn.setAttribute("aria-label", kw + " 검색");
        btn.textContent = kw;
      }

      chip.appendChild(btn);
      listEl.appendChild(chip);
    });
  }

  function setEditing(next) {
    editing = !!next;
    if (sectionEl) {
      sectionEl.classList.toggle("is-editing", editing);
    }
    if (editBtn) {
      editBtn.textContent = editing ? "완료" : "편집";
      editBtn.setAttribute("aria-pressed", editing ? "true" : "false");
    }
    renderRecent();
  }

  function addRecent(keyword) {
    var k = normalizeQuery(keyword);
    if (!k) return;
    var idx = recentKeywords.indexOf(k);
    if (idx !== -1) {
      recentKeywords.splice(idx, 1);
    }
    recentKeywords.unshift(k);
    if (recentKeywords.length > MAX_RECENT) {
      recentKeywords.length = MAX_RECENT;
    }
    renderRecent();
  }

  /** 결과 페이지 없음: 콘솔 로그 + 최근 검색어 반영 */
  function runSearch(raw) {
    if (!inputEl) return;
    var q = normalizeQuery(raw !== undefined ? raw : inputEl.value);
    if (!q) return;
    inputEl.value = q;
    addRecent(q);
    if (typeof console !== "undefined" && console.log) {
      console.log("[상품 검색 더미]", q);
    }
  }

  if (editBtn && sectionEl) {
    editBtn.addEventListener("click", function () {
      setEditing(!editing);
    });
  }

  if (listEl) {
    listEl.addEventListener("click", function (e) {
      var chipBtn = e.target.closest(".recent-search-chip-btn");
      if (!chipBtn) return;
      e.preventDefault();
      var key = chipBtn.dataset.keyword;
      if (editing) {
        recentKeywords = recentKeywords.filter(function (x) {
          return x !== key;
        });
        renderRecent();
        return;
      }
      runSearch(key);
    });
  }

  if (formEl) {
    formEl.addEventListener("submit", function (e) {
      e.preventDefault();
      runSearch(inputEl ? inputEl.value : "");
    });
  }

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

  var popularList = document.getElementById("popularSearchList");
  if (popularList) {
    popularList.addEventListener("click", function (e) {
      var item = e.target.closest(".popular-search-item");
      if (!item) return;
      var kw = item.dataset.keyword;
      if (kw) runSearch(kw);
    });
  }

  /* 엔터 검색은 form submit 으로 처리 (중복 실행 방지) */

  renderRecent();
})();
