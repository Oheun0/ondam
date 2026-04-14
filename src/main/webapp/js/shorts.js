// === 전역 변수 ===
window.isGlobalMuted = false;
window.currentUnitPrice = 0;
window.currentProductNo = null;
window.selectedOptionNo = null;
window.quantity = 1;

window.COLOR_SIZE_MAP = {};
window.OPTION_NO_MAP = {};
window.OPTION_STOCK_MAP = {};
window.currentOptions = [];
window.currentShareMeta = {};

// === 바텀 시트 닫기 함수 ===
window.closePurchaseSheet = function() {
    document.body.style.overflow = ""; 
    var dim = document.getElementById("detailSheetDim");
    var sheet = document.getElementById("detailOptionSheet");
    var colorPanel = document.getElementById("colorOptionPanel");
    var sizePanel = document.getElementById("sizeOptionPanel");
    
    if (dim) dim.classList.add("hidden");
    if (sheet) sheet.classList.add("hidden");
    if (colorPanel) colorPanel.classList.add("hidden");
    if (sizePanel) sizePanel.classList.add("hidden");
    
    var colorToggle = document.getElementById("colorToggleBtn");
    var sizeToggle = document.getElementById("sizeToggleBtn");
    if (colorToggle) colorToggle.setAttribute("aria-expanded", "false");
    if (sizeToggle) sizeToggle.setAttribute("aria-expanded", "false");
};

// === 바텀 시트 열기 함수 ===
window.openPurchaseSheet = function(productNo, productPrice) {
    window.currentProductNo = productNo;
    window.currentUnitPrice = parseInt(productPrice) || 0;
    window.quantity = 1;
    window.selectedOptionNo = null;

    // 💡 찜(하트) 상태 동기화
    var sheetWishlistBtn = document.getElementById("sheetWishlistBtn");
    if (sheetWishlistBtn) {
        var sideIcon = document.querySelector('button[onclick*="toggleLike(this, ' + productNo + ')"] .material-icons');
        var isLiked = sideIcon && sideIcon.classList.contains("liked");
        var sheetWishIcon = sheetWishlistBtn.querySelector(".detail-wish-icon");

        if (isLiked) {
            sheetWishlistBtn.classList.add("detail-action-item--wish-on");
            sheetWishlistBtn.setAttribute("aria-pressed", "true");
            if (sheetWishIcon) {
                sheetWishIcon.classList.remove("material-icons-outlined");
                sheetWishIcon.classList.add("material-icons");
                sheetWishIcon.textContent = "favorite";
            }
        } else {
            sheetWishlistBtn.classList.remove("detail-action-item--wish-on");
            sheetWishlistBtn.setAttribute("aria-pressed", "false");
            if (sheetWishIcon) {
                sheetWishIcon.classList.remove("material-icons");
                sheetWishIcon.classList.add("material-icons-outlined");
                sheetWishIcon.textContent = "favorite_border";
            }
        }
    }

    var ctx = document.body.getAttribute("data-context-path") || "/ondam";
    
    fetch(ctx + "/product?action=getOptions&productNo=" + productNo)
        .then(function(res) { return res.json(); })
        .then(function(data) {
            window.currentOptions = data;
            window.COLOR_SIZE_MAP = {};
            window.OPTION_NO_MAP = {};
            window.OPTION_STOCK_MAP = {};
            
            data.forEach(function(opt) {
                if(!window.COLOR_SIZE_MAP[opt.optionColor]) window.COLOR_SIZE_MAP[opt.optionColor] = [];
                if(!window.COLOR_SIZE_MAP[opt.optionColor].includes(opt.optionSize)) window.COLOR_SIZE_MAP[opt.optionColor].push(opt.optionSize);
                
                var key = opt.optionColor + "__" + opt.optionSize;
                window.OPTION_NO_MAP[key] = opt.productOptionNo;
                window.OPTION_STOCK_MAP[key] = opt.optionStock;
            });

            var selectedColorText = document.getElementById("selectedColorText");
            var selectedSizeText = document.getElementById("selectedSizeText");
            
            if(selectedColorText) {
                selectedColorText.textContent = "눌러서 선택하기";
                selectedColorText.classList.add("detail-selected-value--placeholder");
            }
            if(selectedSizeText) {
                selectedSizeText.textContent = "눌러서 선택하기";
                selectedSizeText.classList.add("detail-selected-value--placeholder");
            }
            
            var hiddenProductNoEl = document.getElementById("hiddenProductNo");
            if (hiddenProductNoEl) hiddenProductNoEl.value = productNo;

            // 💡 [핵심 복원] 색상 버튼 생성
            var colorListContainer = document.querySelector("#colorOptionPanel .detail-option-list");
            var sizeListContainer = document.getElementById("sizeOptionList");
            
            // 처음 열 때는 사이즈 목록을 비워둡니다 (색상을 골라야 나타남)
            if (sizeListContainer) sizeListContainer.innerHTML = "";

            if(colorListContainer) {
                colorListContainer.innerHTML = "";
                Object.keys(window.COLOR_SIZE_MAP).forEach(function(color) {
                    var btn = document.createElement("button");
                    btn.type = "button";
                    btn.className = "detail-option-row";
                    btn.dataset.color = color;
                    btn.setAttribute("role", "option");
                    btn.textContent = color;
                    
                    btn.addEventListener("click", function() {
                        colorListContainer.querySelectorAll(".detail-option-row").forEach(function(b) { b.classList.remove("active"); });
                        this.classList.add("active");
                        
                        if(selectedColorText) {
                            selectedColorText.textContent = color;
                            selectedColorText.classList.remove("detail-selected-value--placeholder");
                        }
                        var colorOptionPanel = document.getElementById("colorOptionPanel");
                        if(colorOptionPanel) colorOptionPanel.classList.add("hidden");
                        
                        // 색상이 바뀌면 사이즈 선택 초기화
                        if(selectedSizeText) {
                            selectedSizeText.textContent = "눌러서 선택하기";
                            selectedSizeText.classList.add("detail-selected-value--placeholder");
                        }
                        window.selectedOptionNo = null;

                        // 💡 [핵심 복원] 선택한 색상에 맞는 사이즈 버튼만 동적 생성
                        if(sizeListContainer) {
                            sizeListContainer.innerHTML = "";
                            window.COLOR_SIZE_MAP[color].forEach(function(sz) {
                                var szBtn = document.createElement("button");
                                szBtn.type = "button";
                                szBtn.className = "detail-option-row";
                                szBtn.textContent = sz;
                                
                                szBtn.addEventListener("click", function() {
                                    sizeListContainer.querySelectorAll(".detail-option-row").forEach(function(b) { b.classList.remove("active"); });
                                    this.classList.add("active");
                                    if(selectedSizeText) {
                                        selectedSizeText.textContent = sz;
                                        selectedSizeText.classList.remove("detail-selected-value--placeholder");
                                    }
                                    var sizeOptionPanel = document.getElementById("sizeOptionPanel");
                                    if(sizeOptionPanel) sizeOptionPanel.classList.add("hidden");

                                    var optKey = color + "__" + sz;
                                    window.selectedOptionNo = window.OPTION_NO_MAP[optKey];
                                    var stock = window.OPTION_STOCK_MAP[optKey] || 0;
                                    
                                    var detailOptionSheet = document.getElementById("detailOptionSheet");
                                    if(detailOptionSheet) detailOptionSheet.setAttribute("data-option-stock", stock);
                                    
                                    if(document.getElementById("hiddenOptionNo")) document.getElementById("hiddenOptionNo").value = window.selectedOptionNo;

                                    var sheetBuyNowBtn = document.getElementById("sheetBuyNowBtn");
                                    var sheetAddCartBtn = document.getElementById("sheetAddCartBtn");
                                    
                                    if (stock === 0) {
                                        if(sheetBuyNowBtn) { sheetBuyNowBtn.disabled = true; sheetBuyNowBtn.textContent = "품절"; }
                                        if(sheetAddCartBtn) sheetAddCartBtn.disabled = true;
                                    } else {
                                        if(sheetBuyNowBtn) { sheetBuyNowBtn.disabled = false; sheetBuyNowBtn.textContent = "구매하기"; }
                                        if(sheetAddCartBtn) sheetAddCartBtn.disabled = false;
                                    }
                                    
                                    if(window.quantity > stock) window.quantity = Math.max(1, stock);
                                    window.updateSheetPriceUI();
                                });
                                sizeListContainer.appendChild(szBtn);
                            });
                        }
                        // 색상을 골랐으니 사이즈 패널을 자동으로 열어주는 센스 (선택사항)
                        // document.getElementById("sizeOptionPanel").classList.remove("hidden");
                        
                        window.updateSheetPriceUI();
                    });
                    colorListContainer.appendChild(btn);
                });
            }
            window.updateSheetPriceUI();

            document.body.style.overflow = "hidden";
            var dim = document.getElementById("detailSheetDim");
            var sheet = document.getElementById("detailOptionSheet");
            if(dim) dim.classList.remove("hidden");
            if(sheet) sheet.classList.remove("hidden");
        })
        .catch(function(err) { console.error("옵션 로드 에러:", err); });
};

window.openShareModalFromShorts = function(productNo, title, imgFile) {
    var ctx = document.body.getAttribute("data-context-path") || "";
    window.currentShareMeta = {
        title: title,
        description: "온담에서 추천하는 숏폼 영상",
        imageUrl: window.location.origin + ctx + "/uploads/products/" + imgFile,
        url: window.location.origin + ctx + "/product?action=detail&productNo=" + productNo
    };
    
    window.closePurchaseSheet();
    
    document.body.style.overflow = "hidden"; 
    var dim = document.getElementById("shareModalDim");
    var modal = document.getElementById("shareModal");
    if(dim) dim.classList.remove("hidden");
    if(modal) modal.classList.remove("hidden");
};

window.updateSheetPriceUI = function() {
    var qtyValue = document.getElementById("qtyValue");
    var minusQtyBtn = document.getElementById("minusQtyBtn");
    var hiddenQuantityEl = document.getElementById("hiddenQuantity");
    var sheetOrderCount = document.getElementById("sheetOrderCount");
    var sheetOrderTotal = document.getElementById("sheetOrderTotal");
    var selectedColorText = document.getElementById("selectedColorText");
    var selectedSizeText = document.getElementById("selectedSizeText");

    if (qtyValue) qtyValue.textContent = String(window.quantity);
    if (minusQtyBtn) minusQtyBtn.disabled = (window.quantity <= 1);
    if (hiddenQuantityEl) hiddenQuantityEl.value = window.quantity;
    
    if (sheetOrderCount && sheetOrderTotal) {
        sheetOrderCount.textContent = "총 " + window.quantity + "개";
        var addPrice = 0;
        if (selectedColorText && selectedSizeText) {
            var optKey = selectedColorText.textContent.trim() + "__" + selectedSizeText.textContent.trim();
            var optNo = window.OPTION_NO_MAP[optKey];
            if (optNo && window.currentOptions) {
                var match = window.currentOptions.find(function(o) { return o.productOptionNo === optNo; });
                if (match) addPrice = match.optionAddPrice;
            }
        }
        var finalPrice = (window.currentUnitPrice + addPrice) * window.quantity;
        sheetOrderTotal.textContent = finalPrice.toLocaleString("ko-KR") + "원";
    }
};

window.toggleVideoPlay = function(video) {
    video.muted = window.isGlobalMuted;
    if (video.paused) video.play(); else video.pause();
};

window.toggleGlobalMute = function() {
    window.isGlobalMuted = !window.isGlobalMuted;
    document.querySelectorAll('.shorts-video').forEach(function(v) { v.muted = window.isGlobalMuted; });
    document.querySelectorAll('.muteIcon').forEach(function(icon) { icon.innerText = window.isGlobalMuted ? 'volume_off' : 'volume_up'; });
    document.querySelectorAll('.muteText').forEach(function(text) { text.innerText = window.isGlobalMuted ? '소리 끔' : '소리 켬'; });
};

window.toggleLike = function(buttonElement, productNo) {
    var icon = buttonElement.querySelector('.material-icons');
    var isCurrentlyLiked = icon.classList.contains('liked');
    var on = !isCurrentlyLiked;

    icon.classList.toggle('liked', on);
    icon.innerText = on ? 'favorite' : 'favorite_border';
    icon.style.transform = 'scale(1.2)';
    setTimeout(function() { icon.style.transform = 'scale(1)'; }, 200);

    var ctx = document.body.getAttribute("data-context-path") || "/ondam";
    if (!document.body.dataset.loginUser) return window.location.href = ctx + "/login";

    fetch(ctx + "/wish?action=toggle&productNo=" + productNo, { method: 'POST' })
    .then(function(r) { return r.json(); })
    .then(function(data) {
        if (data.status === 'error') {
            icon.classList.toggle('liked', isCurrentlyLiked);
            icon.innerText = isCurrentlyLiked ? 'favorite' : 'favorite_border';
        }
    }).catch(function(err) {
        icon.classList.toggle('liked', isCurrentlyLiked);
        icon.innerText = isCurrentlyLiked ? 'favorite' : 'favorite_border';
    });
};

// 💡 Z-index 강제 적용 토스트
window.showTopToast = function(message, type) {
    var el = document.getElementById("option-toast");
    if (!el) return;
    
    el.style.setProperty("z-index", "999999", "important"); 
    
    var textEl = el.querySelector(".option-toast__text");
    var iconEl = el.querySelector(".option-toast__icon");
    if(textEl) textEl.textContent = message;
    if(iconEl) iconEl.textContent = type === "success" ? "check_circle" : "error";
    
    el.classList.remove("hidden", "option-toast--success", "option-toast--error");
    el.classList.add(type === "success" ? "option-toast--success" : "option-toast--error");
    
    el.style.opacity = "1";
    setTimeout(function() { el.style.opacity = "0"; }, 1800);
    setTimeout(function() { el.classList.add("hidden"); }, 2100);
};

window.showOptionErrorToast = function() { window.showTopToast("먼저 색상과 사이즈를 골라주세요", "error"); };
window.showSuccessToast = function(msg) { window.showTopToast(msg, "success"); };
window.isOptionSelected = function() { 
    // 💡 [수정] 플레이스홀더 텍스트인지 직접 체크 (더 확실한 방어)
    var selectedColorText = document.getElementById("selectedColorText");
    var selectedSizeText = document.getElementById("selectedSizeText");
    
    if (!selectedColorText || !selectedSizeText) return false;
    
    return (
        !selectedColorText.classList.contains("detail-selected-value--placeholder") &&
        !selectedSizeText.classList.contains("detail-selected-value--placeholder") &&
        window.selectedOptionNo !== null
    );
};


// === DOM 로드 후 이벤트 바인딩 ===
document.addEventListener("DOMContentLoaded", function() {
    var videos = document.querySelectorAll('.shorts-video');
    var observerOptions = { root: document.querySelector('.shorts-wrapper'), rootMargin: '0px', threshold: 0.6 };
    var observer = new IntersectionObserver(function(entries) {
        entries.forEach(function(entry) {
            var video = entry.target;
            if (entry.isIntersecting) {
                video.muted = window.isGlobalMuted; 
                var playPromise = video.play();
                if (playPromise !== undefined) playPromise.catch(function(e) { video.muted = true; video.play(); });
            } else {
                video.pause();
                video.muted = true; 
                video.currentTime = 0; 
            }
        });
    }, observerOptions);
    videos.forEach(function(v) { observer.observe(v); });

    var detailSheetDim = document.getElementById("detailSheetDim");
    if (detailSheetDim) detailSheetDim.addEventListener("click", window.closePurchaseSheet);
    
    var colorToggleBtn = document.getElementById("colorToggleBtn");
    var sizeToggleBtn = document.getElementById("sizeToggleBtn");
    var colorOptionPanel = document.getElementById("colorOptionPanel");
    var sizeOptionPanel = document.getElementById("sizeOptionPanel");

    // 💡 [수정] 옵션 패널 열고 닫기 로직 방어 (사이즈는 색상이 골라져야 열림)
    if (colorToggleBtn) {
        colorToggleBtn.addEventListener("click", function () {
            var willOpen = colorOptionPanel.classList.contains("hidden");
            colorOptionPanel.classList.add("hidden");
            if(sizeOptionPanel) sizeOptionPanel.classList.add("hidden");
            
            if (willOpen) colorOptionPanel.classList.remove("hidden");
            if (colorToggleBtn) colorToggleBtn.setAttribute("aria-expanded", willOpen ? "true" : "false");
        });
    }

    if (sizeToggleBtn) {
        sizeToggleBtn.addEventListener("click", function () {
            // 색상을 먼저 고르지 않았으면 에러 토스트 띄우기
            var selectedColorText = document.getElementById("selectedColorText");
            if (selectedColorText && selectedColorText.classList.contains("detail-selected-value--placeholder")) {
                window.showTopToast("먼저 색상을 골라주세요", "error");
                return;
            }

            var willOpen = sizeOptionPanel.classList.contains("hidden");
            if(colorOptionPanel) colorOptionPanel.classList.add("hidden");
            sizeOptionPanel.classList.add("hidden");
            
            if (willOpen) sizeOptionPanel.classList.remove("hidden");
            if (sizeToggleBtn) sizeToggleBtn.setAttribute("aria-expanded", willOpen ? "true" : "false");
        });
    }

    var minusQtyBtn = document.getElementById("minusQtyBtn");
    var plusQtyBtn = document.getElementById("plusQtyBtn");
    if (minusQtyBtn) {
        minusQtyBtn.addEventListener("click", function () {
            if (window.quantity <= 1) return;
            window.quantity -= 1;
            window.updateSheetPriceUI();
        });
    }
    if (plusQtyBtn) {
        plusQtyBtn.addEventListener("click", function () {
            var detailOptionSheet = document.getElementById("detailOptionSheet");
            var maxStock = parseInt(detailOptionSheet.getAttribute("data-option-stock") || "9999", 10);
            if (window.quantity >= maxStock) {
                window.showTopToast("선택하신 옵션의 최대 재고수량입니다.", "error");
                return;
            }
            window.quantity += 1;
            window.updateSheetPriceUI();
        });
    }

    // === 장바구니 / 구매하기 ===
    var sheetAddCartBtn = document.getElementById("sheetAddCartBtn");
    if (sheetAddCartBtn) {
        sheetAddCartBtn.addEventListener("click", function (e) {
            if (!window.isOptionSelected()) return window.showOptionErrorToast();
            var ctx = document.body.getAttribute("data-context-path") || "/ondam";
            var form = document.createElement("form");
            form.method = "POST";
            form.action = ctx + "/cart?action=add";
            [["productNo", window.currentProductNo], ["productOptionNo", window.selectedOptionNo], ["quantity", window.quantity]].forEach(function(pair) {
                var input = document.createElement("input");
                input.type = "hidden"; input.name = pair[0]; input.value = pair[1];
                form.appendChild(input);
            });
            document.body.appendChild(form);
            form.submit();
        });
    }

    var sheetBuyNowBtn = document.getElementById("sheetBuyNowBtn");
    if (sheetBuyNowBtn) {
        sheetBuyNowBtn.addEventListener("click", function (e) {
            if (!window.isOptionSelected()) return window.showOptionErrorToast();
            var ctx = document.body.getAttribute("data-context-path") || "/ondam";
            var form = document.createElement("form");
            form.method = "GET";
            form.action = ctx + "/payment";
            [["productNo", window.currentProductNo], ["productOptionNo", window.selectedOptionNo], ["quantity", window.quantity]].forEach(function(pair) {
                var input = document.createElement("input");
                input.type = "hidden"; input.name = pair[0]; input.value = pair[1];
                form.appendChild(input);
            });
            document.body.appendChild(form);
            form.submit();
        });
    }
    
    // 바텀 시트 안의 "찜하기" 버튼 로직 연동
    var sheetWishlistBtn = document.getElementById("sheetWishlistBtn");
    if (sheetWishlistBtn) {
        sheetWishlistBtn.addEventListener("click", function() {
            if (!window.currentProductNo) return;
            
            var sideBtn = document.querySelector('button[onclick*="toggleLike(this, ' + window.currentProductNo + ')"]');
            if (sideBtn) {
                window.toggleLike(sideBtn, window.currentProductNo);
                
                var isCurrentlyLiked = sheetWishlistBtn.classList.contains("detail-action-item--wish-on");
                var on = !isCurrentlyLiked;
                var sheetWishIcon = sheetWishlistBtn.querySelector(".detail-wish-icon");
                
                sheetWishlistBtn.classList.toggle("detail-action-item--wish-on", on);
                sheetWishlistBtn.setAttribute("aria-pressed", on ? "true" : "false");
                if (sheetWishIcon) {
                    if (on) {
                        sheetWishIcon.classList.remove("material-icons-outlined");
                        sheetWishIcon.classList.add("material-icons");
                        sheetWishIcon.textContent = "favorite";
                    } else {
                        sheetWishIcon.classList.remove("material-icons");
                        sheetWishIcon.classList.add("material-icons-outlined");
                        sheetWishIcon.textContent = "favorite_border";
                    }
                }
            }
        });
    }

    // 조르기 오픈
    var openPokeFromSheetBtn = document.getElementById("openPokeFromSheetBtn");
    if (openPokeFromSheetBtn) {
        openPokeFromSheetBtn.addEventListener("click", function (e) {
            if (!window.isOptionSelected()) {
                e.preventDefault();
                e.stopPropagation();
                window.showOptionErrorToast();
                return;
            }
            
            document.getElementById("pokeProductNo").value = window.currentProductNo;
            document.getElementById("pokeProductOptionNo").value = window.selectedOptionNo;
            document.getElementById("pokeQuantity").value = window.quantity;
            document.getElementById("pokeFamilyNo").value = document.getElementById("trueFamilyNoForShorts").value;

            window.closePurchaseSheet();
            document.body.style.overflow = "hidden";
            var dim = document.getElementById("pokeModalDim");
            var modal = document.getElementById("pokeModal");
            if(dim) dim.classList.remove("hidden");
            if(modal) modal.classList.remove("hidden");
        });
    }

    // 선물 오픈
    var openGiftFromSheetBtn = document.getElementById("openGiftFromSheetBtn");
    if (openGiftFromSheetBtn) {
        openGiftFromSheetBtn.addEventListener("click", function (e) {
            if (!window.isOptionSelected()) {
                e.preventDefault();
                e.stopPropagation();
                window.showOptionErrorToast();
                return;
            }
            window.closePurchaseSheet();
            document.body.style.overflow = "hidden";
            var dim = document.getElementById("giftModalDim");
            var modal = document.getElementById("giftModal");
            if(dim) dim.classList.remove("hidden");
            if(modal) modal.classList.remove("hidden");
        });
    }

    // 조르기 확정 전송 (Fetch)
    var confirmPokeBtn = document.getElementById("confirmPokeBtn");
    if (confirmPokeBtn) {
        confirmPokeBtn.addEventListener("click", function () {
            var selected = document.querySelector(".poke-person-btn.active");
            if (!selected) return window.showTopToast("조르기를 보낼 사람을 선택해주세요.", "error");

            document.getElementById("pokeReceiverNo").value = selected.dataset.userNo;
            document.getElementById("pokeMsgHidden").value = document.getElementById("pokeMsgInput").value;

            var pokeForm = document.getElementById("pokeForm");
            var formData = new FormData(pokeForm);
            var body = new URLSearchParams();
            formData.forEach(function(value, key) { body.append(key, value); });

            confirmPokeBtn.disabled = true;
            fetch(pokeForm.action, {
                method: "POST",
                headers: { "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8" },
                body: body.toString()
            })
            .then(function(res) {
                if (!res.ok) throw new Error("poke failed");
                var dim = document.getElementById("pokeModalDim");
                var modal = document.getElementById("pokeModal");
                if(dim) dim.classList.add("hidden");
                if(modal) modal.classList.add("hidden");
                document.body.style.overflow = "";
                document.getElementById("pokeMsgInput").value = "";
                document.querySelectorAll(".poke-person-btn").forEach(function(b) { b.classList.remove("active"); });
                window.showTopToast("조르기 요청을 보냈어요", "success");
            })
            .catch(function() { window.showTopToast("조르기 요청이 실패됐어요", "error"); })
            .finally(function() { confirmPokeBtn.disabled = false; });
        });
    }

    // 선물 확정 전송
    var confirmGiftBtn = document.getElementById("confirmGiftBtn");
    if(confirmGiftBtn) {
        confirmGiftBtn.addEventListener("click", function() {
            var activeBtn = document.querySelector(".gift-person-btn.active");
            if (!activeBtn) return window.showTopToast("선물할 대상을 선택해주세요.", "error");
            var ctx = document.body.getAttribute("data-context-path") || "/ondam";
            location.href = ctx + "/payment?productNo=" + window.currentProductNo + "&productOptionNo=" + window.selectedOptionNo + "&quantity=" + window.quantity + "&isGift=true&receiverNo=" + activeBtn.getAttribute("data-user-no");
        });
    }

    // 공유 버튼
    var openShareFromSheetBtn = document.getElementById("openShareFromSheetBtn");
    if (openShareFromSheetBtn) {
        openShareFromSheetBtn.addEventListener("click", function () {
            window.closePurchaseSheet();
            var titleEl = document.querySelector(".detail-product-name");
            window.openShareModalFromShorts(window.currentProductNo, titleEl ? titleEl.textContent : "온담 추천 상품", "logo.png");
        });
    }

    var shareCopyLinkBtn = document.getElementById("shareCopyLinkBtn");
    if (shareCopyLinkBtn) {
        shareCopyLinkBtn.addEventListener("click", function() {
            var url = window.currentShareMeta.url || window.location.href;
            if (navigator.clipboard && navigator.clipboard.writeText) {
                navigator.clipboard.writeText(url);
            } else {
                var ta = document.createElement("textarea");
                ta.value = url; document.body.appendChild(ta); ta.select(); document.execCommand("copy"); document.body.removeChild(ta);
            }
            window.showTopToast("링크를 복사했어요.", "success");
        });
    }

    var shareKakaoBtn = document.getElementById("shareKakaoBtn");
    if (shareKakaoBtn) {
        shareKakaoBtn.addEventListener("click", function() {
            var kakaoKey = document.body.getAttribute("data-kakao-js-key");
            if (!window.Kakao || !kakaoKey) return window.showTopToast("카카오 공유 설정이 아직 없어요.", "error");
            try {
                if (!window.Kakao.isInitialized()) window.Kakao.init(kakaoKey);
                window.Kakao.Share.sendDefault({
                    objectType: "feed",
                    content: {
                        title: window.currentShareMeta.title,
                        description: window.currentShareMeta.description,
                        imageUrl: window.currentShareMeta.imageUrl,
                        link: { mobileWebUrl: window.currentShareMeta.url, webUrl: window.currentShareMeta.url }
                    },
                    buttons: [{ title: "상품 보러가기", link: { mobileWebUrl: window.currentShareMeta.url, webUrl: window.currentShareMeta.url } }]
                });
            } catch(e) { window.showTopToast("카카오톡 공유를 실행하지 못했어요.", "error"); }
        });
    }

    var shareMoreBtn = document.getElementById("shareMoreBtn");
    if (shareMoreBtn) {
        shareMoreBtn.addEventListener("click", function() {
            if (navigator.share) {
                navigator.share({ title: window.currentShareMeta.title, url: window.currentShareMeta.url }).catch(function(){});
            } else {
                window.showTopToast("공유를 지원하지 않아 링크를 복사해주세요.", "error");
            }
        });
    }

    // 모달 닫기
    var modalIds = ["pokeModal", "giftModal", "shareModal"];
    modalIds.forEach(function(modalId) {
        var modalDim = document.getElementById(modalId + "Dim");
        var modalEl = document.getElementById(modalId);
        
        if (modalDim) {
            modalDim.addEventListener("click", function() {
                modalDim.classList.add("hidden");
                if (modalEl) modalEl.classList.add("hidden");
                document.body.style.overflow = ""; 
            });
        }
        
        if (modalEl) {
            modalEl.addEventListener("click", function(e) {
                if (e.target === modalEl) {
                    modalEl.classList.add("hidden");
                    if (modalDim) modalDim.classList.add("hidden");
                    document.body.style.overflow = "";
                }
            });
        }
    });

    var closeBtns = document.querySelectorAll("#closePokeModalBtn, #closeGiftModalBtn");
    closeBtns.forEach(function(btn) {
        btn.addEventListener("click", function() {
            document.querySelectorAll(".poke-modal, .poke-modal-dim").forEach(function(m) { m.classList.add("hidden"); });
            document.body.style.overflow = "";
        });
    });
});