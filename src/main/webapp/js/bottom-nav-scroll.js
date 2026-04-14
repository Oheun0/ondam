/* global window, document */
(function () {
  var bottomNav = document.querySelector(".bottom-nav");
  if (!bottomNav) return;

  var lastScrollY = window.scrollY || 0;
  var navCollapsed = false;
  var scrollThreshold = 6;

  function setBottomNavCollapsed(collapse) {
    if (collapse === navCollapsed) return;
    navCollapsed = collapse;
    bottomNav.classList.toggle("bottom-nav--collapsed", collapse);
  }

  function onScroll() {
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

  var ticking = false;
  window.addEventListener(
    "scroll",
    function () {
      if (!ticking) {
        window.requestAnimationFrame(function () {
          onScroll();
          ticking = false;
        });
        ticking = true;
      }
    },
    { passive: true }
  );
})();
