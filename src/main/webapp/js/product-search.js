/**
 * 상품 검색 상단바 + 검색 홈(최근/인기) — 순수 DOM API
 * 검색 실행 시 /preview?page=product/search-result&q= 로 이동 (ProductController 없이 화면 미리보기)
 */
(function () {
  "use strict";

  var MAX_RECENT = 10;

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
  
  function setEditing(next) {
    editing = !!next;
    if (sectionEl) {
      sectionEl.classList.toggle("is-editing", editing);
    }
    if (editBtn) {
      editBtn.textContent = editing ? "완료" : "편집";
      editBtn.setAttribute("aria-pressed", editing ? "true" : "false");
    }
    
    // 버튼들에도 클래스를 토글해줍니다 (CSS 선택자 대응)
    var chipBtns = document.querySelectorAll(".recent-search-chip-btn");
    chipBtns.forEach(function(btn) {
      btn.classList.toggle("is-editing", editing);
    });
  }

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
	"/search?q=" + encodeURIComponent(q);
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
              // DOM 즉시 제거
              var chip = chipBtn.closest(".recent-search-chip");
              if (chip) chip.remove();

              // DB 삭제
              fetch(getCtx() + "/search?action=deleteRecent&q=" + encodeURIComponent(key), {
                  method: "POST"
              }).catch(function () {
                  // 실패해도 DOM은 이미 지워진 상태 유지
              });
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
          // 검색 결과 페이지 → 검색 입력 화면으로
          if (isSearchResult) {
              window.location.href = getCtx() + "/search";
              return;
          }
          // 검색 입력 화면 → 이전 페이지(홈/카테고리)로
          window.location.href = getCtx() + "/main";
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
})();