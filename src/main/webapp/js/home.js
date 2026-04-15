(function () {
    "use strict";

    // 공통 ContextPath 가져오기 함수
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
        /* 1. 카테고리 이동 로직 */
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

        /* 2. 배너 슬라이드 로직 */
        var track = document.getElementById("sliderTrack");
        var dotsWrap = document.getElementById("bannerDots");
        if (track) {
            var slides = track.querySelectorAll(".banner-slide");
            var total = slides.length;
            if (total > 0) {
                var stepPct = 100 / total;
                var index = 0;
                var intervalMs = 5000;

                var goTo = function (i) {
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
                };

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
            }
        }

        /* 3. [추가된 로직] 최신 상품 찜 버튼 이벤트 처리 */
        document.querySelectorAll(".related-wish-btn").forEach(function (btn) {
            btn.addEventListener("click", function (e) {
                e.preventDefault(); // 링크 이동 방지
                e.stopPropagation(); // 부모 클릭 이벤트 전파 방지 (상세페이지 이동 방지)

                var ctx = getContextPath(); // 기존 함수 활용
                var productNo = btn.getAttribute("data-product-no");

                // 로그인 체크
                if (!document.body.dataset.loginUser || document.body.dataset.loginUser === 'false') {
                    window.location.href = ctx + "/login";
                    return;
                }

                // 시각적 토글 (아이콘 & 클래스)
                var icon = btn.querySelector("span");
                var isWished = btn.classList.toggle("is-active");

                if (isWished) {
                    icon.className = "material-icons";
                    icon.textContent = "favorite";
                } else {
                    icon.className = "material-icons-outlined";
                    icon.textContent = "favorite_border";
                }

                // 서버에 찜 상태 전송
                fetch(ctx + "/wish?action=toggle&productNo=" + productNo, {
                    method: "POST"
                })
                .then(function (res) { return res.json(); })
                .then(function (data) {
                    var actualStatus = data.wished;
                    btn.classList.toggle("is-active", actualStatus);
                    if (actualStatus) {
                        icon.className = "material-icons";
                        icon.textContent = "favorite";
                    } else {
                        icon.className = "material-icons-outlined";
                        icon.textContent = "favorite_border";
                    }
                })
                .catch(function (err) {
                    console.error("찜하기 통신 에러:", err);
                    btn.classList.toggle("is-active", !isWished); // 에러 시 롤백
                });
            });
        });
    });
})();