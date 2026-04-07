document.addEventListener("DOMContentLoaded", function () {
  const categoryToggleBtn = document.getElementById("categoryToggleBtn");
  const categoryPanel = document.getElementById("categoryPanel");
  const categoryArrow = document.getElementById("categoryArrow");

  const modeTabs = document.querySelectorAll(".category-mode-tab");
  const typeCategoryView = document.getElementById("typeCategoryView");
  const situationCategoryView = document.getElementById("situationCategoryView");
  const categoryChips = document.querySelectorAll(".category-panel .category-chip");
  const situationSubTabs = document.querySelectorAll(".situation-sub-tab");

  const viewModeLabel = document.getElementById("viewModeLabel");
  const currentCategoryTitle = document.getElementById("currentCategoryTitle");

  const sortToggleBtn = document.getElementById("sortToggleBtn");
  const colorToggleBtn = document.getElementById("colorToggleBtn");
  const seasonToggleBtn = document.getElementById("seasonToggleBtn");
  const featureToggleBtn = document.getElementById("featureToggleBtn");

  const sortDropdown = document.getElementById("sortDropdown");
  const colorDropdown = document.getElementById("colorDropdown");
  const seasonDropdown = document.getElementById("seasonDropdown");
  const featureDropdown = document.getElementById("featureDropdown");

  const sortSelectedText = document.getElementById("sortSelectedText");
  const seasonSelectedText = document.getElementById("seasonSelectedText");
  const selectedFilterArea = document.getElementById("selectedFilterArea");
  const resetBtn = document.getElementById("resetBtn");

  const sortOptions = document.querySelectorAll("[data-sort]");
  const colorInputs = document.querySelectorAll('#colorDropdown input[type="checkbox"]');
  const seasonInputs = document.querySelectorAll('#seasonDropdown input[name="productSeason"]');
  const featureInputs = document.querySelectorAll('#featureDropdown input[name="clothesFeature"]');
  const categoryDim = document.getElementById("categoryDim");

  const situationTab = document.getElementById('productTabSituation');
  let currentViewMode = (situationTab && situationTab.classList.contains('active')) ? 'situation' : 'type';

  const titleEl = document.getElementById('currentCategoryTitle');
  let currentCategory = (titleEl && titleEl.textContent.trim()) ? titleEl.textContent.trim() : '';
  let currentSort = "전체";

  function clearFilterDropdownStyles(menu) {
    if (!menu) return;
    menu.style.top = "";
    menu.style.left = "";
    menu.style.right = "";
    menu.style.maxWidth = "";
    menu.style.width = "";
    menu.style.visibility = "";
  }

  function closeFilterDropdowns() {
    if (sortDropdown) {
      sortDropdown.classList.add("hidden");
      clearFilterDropdownStyles(sortDropdown);
    }
    if (colorDropdown) {
      colorDropdown.classList.add("hidden");
      clearFilterDropdownStyles(colorDropdown);
    }
    if (seasonDropdown) {
      seasonDropdown.classList.add("hidden");
      clearFilterDropdownStyles(seasonDropdown);
    }
    if (featureDropdown) {
      featureDropdown.classList.add("hidden");
      clearFilterDropdownStyles(featureDropdown);
    }
  }

  function positionFilterDropdown(btn, menu) {
    if (!btn || !menu || menu.classList.contains("hidden")) return;

    const rect = btn.getBoundingClientRect();
    const pad = 8;
    const vw = window.innerWidth;
    const vh = window.innerHeight;
    const fullBleed = window.matchMedia("(max-width: 768px)").matches;

    menu.style.visibility = "hidden";

    if (fullBleed) {
      menu.style.left = "0";
      menu.style.right = "0";
      menu.style.width = "100%";
      menu.style.maxWidth = "none";
    } else {
      const maxW = Math.min(330, vw - 16);
      menu.style.width = "";
      menu.style.right = "auto";
      menu.style.maxWidth = maxW + "px";
    }

    const mw = menu.offsetWidth;
    const mh = menu.offsetHeight;

    let top = rect.bottom + pad;
    if (top + mh > vh - pad) {
      top = rect.top - mh - pad;
    }
    if (top < pad) top = pad;

    menu.style.top = top + "px";

    if (!fullBleed) {
      let left = rect.left;
      if (left + mw > vw - pad) {
        left = Math.max(pad, vw - mw - pad);
      }
      if (left < pad) left = pad;
      menu.style.left = left + "px";
    }

    menu.style.visibility = "visible";
  }

  function openFilterDropdown(btn, menu) {
    if (!btn || !menu) return;
    const willOpen = menu.classList.contains("hidden");
    closeFilterDropdowns();
    if (!willOpen) return;
    menu.classList.remove("hidden");
    requestAnimationFrame(function () {
      positionFilterDropdown(btn, menu);
    });
  }

  function repositionOpenFilterMenus() {
    if (sortDropdown && !sortDropdown.classList.contains("hidden") && sortToggleBtn) {
      positionFilterDropdown(sortToggleBtn, sortDropdown);
    }
    if (colorDropdown && !colorDropdown.classList.contains("hidden") && colorToggleBtn) {
      positionFilterDropdown(colorToggleBtn, colorDropdown);
    }
    if (seasonDropdown && !seasonDropdown.classList.contains("hidden") && seasonToggleBtn) {
      positionFilterDropdown(seasonToggleBtn, seasonDropdown);
    }
    if (featureDropdown && !featureDropdown.classList.contains("hidden") && featureToggleBtn) {
      positionFilterDropdown(featureToggleBtn, featureDropdown);
    }
  }

  function updateSeasonButtonLabel() {
    if (!seasonSelectedText) return;
    const checked = document.querySelector('#seasonDropdown input[name="productSeason"]:checked');
    seasonSelectedText.textContent = checked ? checked.value : "계절";
  }

  function syncCategoryDimPosition() {
    if (!categoryDim || categoryDim.classList.contains("hidden")) return;
    if (!categoryPanel || categoryPanel.classList.contains("hidden")) return;
    const headerEl = document.querySelector(".product-header-section");
    const bottom = headerEl ? headerEl.getBoundingClientRect().bottom : 0;
    categoryDim.style.top = Math.max(0, Math.round(bottom)) + "px";
  }

  function clearCategoryDimPosition() {
    if (categoryDim) categoryDim.style.top = "";
  }

  function closeAllPanels() {
    if (categoryPanel) categoryPanel.classList.add("hidden");
    if (categoryDim) categoryDim.classList.add("hidden");
    clearCategoryDimPosition();
    if (categoryArrow) categoryArrow.classList.remove("open");
    closeFilterDropdowns();
  }

  function renderSelectedChips() {
    if (!selectedFilterArea) return;

    let html = `
      <button type="button" class="selected-chip selected-category-chip" data-type="category">
        ${currentCategory}
      </button>
    `;


    colorInputs.forEach((input) => {
      if (input.checked) {
        html += `
          <button type="button" class="selected-chip" data-type="color" data-value="${input.value}">
            ${input.value}
            <span class="material-icons">close</span>
          </button>
        `;
      }
    });

    const seasonChecked = document.querySelector('#seasonDropdown input[name="productSeason"]:checked');
    if (seasonChecked) {
      html += `
        <button type="button" class="selected-chip" data-type="season" data-value="${seasonChecked.value}">
          ${seasonChecked.value}
          <span class="material-icons">close</span>
        </button>
      `;
    }

    featureInputs.forEach((input) => {
      if (input.checked) {
        html += `
          <button type="button" class="selected-chip" data-type="feature" data-value="${input.value}">
            ${input.value}
            <span class="material-icons">close</span>
          </button>
        `;
      }
    });

    selectedFilterArea.innerHTML = html;
    updateSeasonButtonLabel();
  }
  
  function applyFilters() {
      const params = new URLSearchParams();
      params.set('action', 'list');

      // 카테고리 or 상황
      if (currentViewMode === 'situation') {
          params.set('situationName', currentCategory);
      } else {
          params.set('categoryName', currentCategory);
      }

      // 정렬
      if (currentSort && currentSort !== '전체') {
          params.set('sort', currentSort);
      }

      // 색상 (복수)
      colorInputs.forEach(input => {
          if (input.checked) params.append('color', input.value);
      });

      // 계절 (단수 라디오)
      const seasonChecked = document.querySelector('#seasonDropdown input[name="productSeason"]:checked');
      if (seasonChecked) params.set('season', seasonChecked.value);

      // 옷 특징 (복수)
      featureInputs.forEach(input => {
          if (input.checked) params.append('feature', input.value);
      });

      window.location.href = CONTEXT_PATH + '/product?' + params.toString();
  }

  function activateSituationSubTab(tabKey) {
    if (!tabKey || situationSubTabs.length === 0) return;
    situationSubTabs.forEach(function (el) {
      const on = el.dataset.situationTab === tabKey;
      el.classList.toggle("active", on);
      el.setAttribute("aria-selected", on ? "true" : "false");
    });
    document.querySelectorAll(".situation-sub-panel").forEach(function (el) {
      const on = el.dataset.situationPanel === tabKey;
      el.classList.toggle("active", on);
      el.setAttribute("aria-hidden", on ? "false" : "true");
    });
  }

  function updateCategoryView() {
    if (!viewModeLabel || !typeCategoryView || !situationCategoryView) return;

    if (currentViewMode === "type") {
      viewModeLabel.textContent = "종류로 보기";
      typeCategoryView.classList.add("active");
      situationCategoryView.classList.remove("active");
    } else {
      viewModeLabel.textContent = "상황으로 보기";
      typeCategoryView.classList.remove("active");
      situationCategoryView.classList.add("active");
    }
  }

  if (categoryToggleBtn && categoryPanel) {
    categoryToggleBtn.addEventListener("click", function (e) {
      e.stopPropagation();
      const willOpen = categoryPanel.classList.contains("hidden");
      closeAllPanels();

      if (willOpen) {
        categoryPanel.classList.remove("hidden");
        if (categoryDim) categoryDim.classList.remove("hidden");
        if (categoryArrow) categoryArrow.classList.add("open");
        syncCategoryDimPosition();
        requestAnimationFrame(function () {
          requestAnimationFrame(syncCategoryDimPosition);
        });
      }
    });
  }

  if (categoryDim) {
    categoryDim.addEventListener("click", function () {
      closeAllPanels();
    });
  }

  if (sortToggleBtn && sortDropdown) {
    sortToggleBtn.addEventListener("click", function (e) {
      e.stopPropagation();
      openFilterDropdown(sortToggleBtn, sortDropdown);
    });
  }

  if (colorToggleBtn && colorDropdown) {
    colorToggleBtn.addEventListener("click", function (e) {
      e.stopPropagation();
      openFilterDropdown(colorToggleBtn, colorDropdown);
    });
  }

  if (seasonToggleBtn && seasonDropdown) {
    seasonToggleBtn.addEventListener("click", function (e) {
      e.stopPropagation();
      openFilterDropdown(seasonToggleBtn, seasonDropdown);
    });
  }

  if (featureToggleBtn && featureDropdown) {
    featureToggleBtn.addEventListener("click", function (e) {
      e.stopPropagation();
      openFilterDropdown(featureToggleBtn, featureDropdown);
    });
  }

  function onScrollOrResizeForOverlays() {
    repositionOpenFilterMenus();
    syncCategoryDimPosition();
  }

  window.addEventListener("scroll", onScrollOrResizeForOverlays, true);
  window.addEventListener("resize", onScrollOrResizeForOverlays);

  document.addEventListener("click", function (e) {
    if (!e.target.closest(".product-header-section") && !e.target.closest(".product-filter-section")) {
      closeAllPanels();
    }
  });

  situationSubTabs.forEach(function (subTab) {
    subTab.addEventListener("click", function (e) {
      e.stopPropagation();
      const key = this.dataset.situationTab;
      if (key) activateSituationSubTab(key);
      requestAnimationFrame(function () {
        requestAnimationFrame(syncCategoryDimPosition);
      });
    });
  });

  modeTabs.forEach((tab) => {
    tab.addEventListener("click", function () {
      modeTabs.forEach((item) => {
        item.classList.remove("active");
        item.setAttribute("aria-selected", "false");
      });
      this.classList.add("active");
      this.setAttribute("aria-selected", "true");

      currentViewMode = this.dataset.viewMode;

      document.querySelectorAll(".category-panel .category-chip").forEach((chip) => chip.classList.remove("active"));

      if (currentViewMode === "type") {
        currentCategory = "";
      } else {
        activateSituationSubTab("daily");
        currentCategory = "";
      }

      if (currentCategoryTitle) currentCategoryTitle.textContent = currentCategory;
      updateCategoryView();
      renderSelectedChips();
      requestAnimationFrame(function () {
        requestAnimationFrame(syncCategoryDimPosition);
      });
    });
  });

  categoryChips.forEach((chip) => {
      chip.addEventListener("click", function () {
        const parentView = this.closest(".category-view");
        if (!parentView) return;

        parentView.querySelectorAll(".category-chip").forEach((item) => item.classList.remove("active"));
        this.classList.add("active");

        currentCategory = this.dataset.category;
        if (currentCategoryTitle) currentCategoryTitle.textContent = currentCategory;

        renderSelectedChips();
        closeAllPanels();

		// 추가
        const isInSituation = this.closest("#situationCategoryView") !== null;
        const url = isInSituation
          ? CONTEXT_PATH + "/product?action=list&situationName=" + encodeURIComponent(this.dataset.category)
          : CONTEXT_PATH + "/product?action=list&categoryName=" + encodeURIComponent(this.dataset.category);
        window.location.href = url;
      });
  });

  sortOptions.forEach((option) => {
      option.addEventListener("click", function () {
          sortOptions.forEach((item) => item.classList.remove("active"));
          this.classList.add("active");
          currentSort = this.dataset.sort;
          if (sortSelectedText) sortSelectedText.textContent = currentSort;
          if (sortDropdown) sortDropdown.classList.add("hidden");
          applyFilters(); // ← 추가
      });
  });

  colorInputs.forEach((input) => {
      input.addEventListener("change", function() {
          renderSelectedChips();
          applyFilters(); // ← 추가
      });
  });

  seasonInputs.forEach((input) => {
      input.addEventListener("change", function () {
          renderSelectedChips();
          if (seasonDropdown) {
              seasonDropdown.classList.add("hidden");
              clearFilterDropdownStyles(seasonDropdown);
          }
          applyFilters(); // ← 추가
      });
  });

  featureInputs.forEach((input) => {
      input.addEventListener("change", function() {
          renderSelectedChips();
          applyFilters(); // ← 추가
      });
  });

  if (selectedFilterArea) selectedFilterArea.addEventListener("click", function (e) {
    const chip = e.target.closest(".selected-chip");
    if (!chip) return;

    const type = chip.dataset.type;
    const value = chip.dataset.value;

    if (type === "color") {
      colorInputs.forEach((input) => {
        if (input.value === value) input.checked = false;
      });
    }

    if (type === "season") {
      seasonInputs.forEach((input) => {
        if (input.value === value) input.checked = false;
      });
    }

    if (type === "feature") {
      featureInputs.forEach((input) => {
        if (input.value === value) input.checked = false;
      });
    }

    renderSelectedChips();
	applyFilters();
  });

  if (resetBtn) resetBtn.addEventListener("click", function () {
      window.location.href = CONTEXT_PATH + '/product?action=list';
  });

  if (typeCategoryView && situationCategoryView) updateCategoryView();
  activateSituationSubTab("daily");
  
  if (currentViewMode === 'situation') {
        const activeChip = document.querySelector('#situationCategoryView .category-chip.active');
        if (activeChip) {
            const panel = activeChip.closest('.situation-sub-panel');
            if (panel) {
                activateSituationSubTab(panel.dataset.situationPanel);
            }
        }
    }

    renderSelectedChips();

  /* 상품 그리드 찜 토글 (카테고리 목록·검색 결과 — 찜 전용 페이지는 favorite-list.js) */
  if (document.body.classList.contains("product-list-page") && !document.body.classList.contains("favorite-list-page")) {
    var wishGrid = document.querySelector(".product-grid");
    if (wishGrid) {
      wishGrid.addEventListener("click", function (e) {
        var wishBtn = e.target.closest(".product-grid-wish-btn");
        if (!wishBtn || !wishGrid.contains(wishBtn)) return;
        e.preventDefault();
        e.stopPropagation();
        wishBtn.classList.toggle("is-active");
        var on = wishBtn.classList.contains("is-active");
        wishBtn.setAttribute("aria-pressed", on ? "true" : "false");
        wishBtn.setAttribute("aria-label", on ? "찜 해제" : "찜하기");
        var icon = wishBtn.querySelector("span.material-icons-outlined, span.material-icons");
        if (!icon) return;
        if (on) {
          icon.classList.remove("material-icons-outlined");
          icon.classList.add("material-icons");
          icon.textContent = "favorite";
        } else {
          icon.classList.remove("material-icons");
          icon.classList.add("material-icons-outlined");
          icon.textContent = "favorite_border";
        }
      });
    }
  }

  /* 하단 네비: 스크롤 내림(아래 방향) → 숨김, 스크롤 올림(위 방향) → 표시 */
  if (document.body.classList.contains("product-list-page")) {
    var bottomNav = document.querySelector(".bottom-nav");
    if (bottomNav) {
      var lastScrollY = window.scrollY || 0;
      var navCollapsed = false;
      var scrollThreshold = 6;

      function setBottomNavCollapsed(collapse) {
        if (collapse === navCollapsed) return;
        navCollapsed = collapse;
        bottomNav.classList.toggle("bottom-nav--collapsed", collapse);
      }

      function onListScroll() {
        var y = window.scrollY || 0;
        var delta = y - lastScrollY;
        lastScrollY = y;

        if (y < 16) {
          setBottomNavCollapsed(false);
          return;
        }
        if (delta > scrollThreshold) {
          setBottomNavCollapsed(true);
        } else if (delta < -scrollThreshold) {
          setBottomNavCollapsed(false);
        }
      }

      var scrollTicking = false;
      window.addEventListener(
        "scroll",
        function () {
          if (!scrollTicking) {
            window.requestAnimationFrame(function () {
              onListScroll();
              scrollTicking = false;
            });
            scrollTicking = true;
          }
        },
        { passive: true }
      );
    }
  }
});
