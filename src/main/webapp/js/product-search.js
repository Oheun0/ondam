/**
 * 상품 검색 상단바 + 검색 홈(최근/인기) — 순수 DOM API
 * 검색 실행 시 /preview?page=product/search-result&q= 로 이동 (ProductController 없이 화면 미리보기)
 */
(function () {
  "use strict";

  var MAX_RECENT = 10;
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

  var body = document.body;
  var isSearchHome = body.classList.contains("product-search-page");
  var isSearchResult = body.classList.contains("search-result-page");

  var sectionEl = document.getElementById("recentSearchSection");
  var listEl = document.getElementById("recentSearchList");
  var editBtn = document.getElementById("searchEditBtn");
  var formEl = document.getElementById("productSearchForm");
  var inputEl = document.getElementById("searchQueryInput");
  var backBtn = document.getElementById("searchBackBtn");

  var editing = false;

  function getCtx() {
    return body.getAttribute("data-context-path") || "";
  }

  function normalizeQuery(s) {
    return String(s || "")
      .replace(/\s+/g, " ")
      .trim();
  }

  function goToSearchResult(raw) {
    if (!inputEl && raw === undefined) return;
    var q = normalizeQuery(raw !== undefined ? raw : inputEl.value);
    if (!q) return;
    window.location.href =
      getCtx() +
      "/preview?page=product/search-result&q=" +
      encodeURIComponent(q);
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
      if (isSearchHome) {
        goToSearchResult(key);
      }
    });
  }

  if (formEl) {
    formEl.addEventListener("submit", function (e) {
      e.preventDefault();
      if (isSearchHome || isSearchResult) {
        goToSearchResult();
        return;
      }
    });
  }

  if (backBtn) {
    backBtn.addEventListener("click", function () {
      if (window.history.length > 1) {
        window.history.back();
      } else {
        window.location.href = getCtx() + "/main";
      }
    });
  }

  var popularList = document.getElementById("popularSearchList");
  if (popularList && isSearchHome) {
    popularList.addEventListener("click", function (e) {
      var item = e.target.closest(".popular-search-item");
      if (!item) return;
      var kw = item.dataset.keyword;
      if (kw) goToSearchResult(kw);
    });
  }

  if (listEl) {
    renderRecent();
  }
})();

