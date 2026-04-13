var isGlobalMuted = false;
var currentUnitPrice = 0;    
var currentAddPrice = 0;     
var currentOptions = [];     
var selectedOptionNo = null; 
var currentProductNo = null; 

document.addEventListener("DOMContentLoaded", function() {
    // === 1. 비디오 재생/일시정지 옵저버 ===
    var videos = document.querySelectorAll('.shorts-video');
    var observerOptions = { 
        root: document.querySelector('.shorts-wrapper'), 
        rootMargin: '0px', 
        threshold: 0.6 
    };

    var observer = new IntersectionObserver(function(entries) {
        entries.forEach(function(entry) {
            var video = entry.target;
            if (entry.isIntersecting) {
                video.muted = isGlobalMuted; 
                var playPromise = video.play();
                if (playPromise !== undefined) {
                    playPromise.catch(function(e) { 
                        video.muted = true; 
                        video.play(); 
                    });
                }
            } else {
                video.pause();
                video.muted = true; 
                video.currentTime = 0; 
            }
        });
    }, observerOptions);

    videos.forEach(function(video) {
        observer.observe(video);
    });

    // === 2. 모달 내 대상자 선택 UI 바인딩 ===
    var pokeBtns = document.querySelectorAll(".poke-person-btn");
    pokeBtns.forEach(function(btn) {
        btn.addEventListener("click", function() {
            pokeBtns.forEach(function(b) { b.classList.remove("active"); });
            btn.classList.add("active");
        });
    });

    var giftBtns = document.querySelectorAll(".gift-person-btn");
    giftBtns.forEach(function(btn) {
        btn.addEventListener("click", function() {
            giftBtns.forEach(function(b) { b.classList.remove("active"); });
            btn.classList.add("active");
        });
    });

	// === 3. 조르기 확정 버튼 이벤트 (shorts.js 내부) ===
    var confirmPokeBtn = document.getElementById("confirmPokeBtn");
    if(confirmPokeBtn) {
        confirmPokeBtn.addEventListener("click", function() {
            var activeBtn = document.querySelector(".poke-person-btn.active");
            if (!activeBtn) {
                // alert 대신 에러 토스트 띄우기 (문구는 필요시 변경)
                document.querySelector("#option-toast .option-toast__text").innerText = "조를 대상을 선택해주세요.";
                showOptionErrorToast();
                return;
            }
            
            var receiverNo = activeBtn.getAttribute("data-user-no");
            
            // 폼에 값 세팅
            document.getElementById('joreugiReceiverNo').value = receiverNo;
            document.getElementById('joreugiProductNo').value = currentProductNo;
            document.getElementById('joreugiOptionNo').value = selectedOptionNo;
            document.getElementById('joreugiQuantity').value = document.getElementById('buyQty').innerText;

            // 💡 핵심 해결: 폼을 submit() 하지 않고 AJAX(Fetch)로 뒷단에서 몰래 보냅니다.
            var form = document.getElementById('joreugiForm');
            var formData = new URLSearchParams(new FormData(form));

			// getAttribute('action')을 써서 진짜 URL 문자열을 가져오도록 수정
            fetch(form.getAttribute('action'), {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: formData.toString()
            })
            .then(function(response) {
                // 전송 성공 시 화면 이동 없이 모달만 닫기
                var dim = document.getElementById("pokeModalDim");
                var modal = document.getElementById("pokeModal");
                if (dim) dim.classList.add("hidden");
                if (modal) modal.classList.add("hidden");

                // alert 대신 예쁜 성공 토스트 띄우기
                showSuccessToast("조르기 요청이 전송되었습니다❤️");
            })
            .catch(function(err) {
                console.error("조르기 전송 에러:", err);
            });
        });
    }

    // ---------------------------------------------
    // 코드 맨 아래쪽에 이 함수를 추가해 주세요
    // ---------------------------------------------
    var successToastActive = false;
    var successToastDismissTimer = null;

    function showSuccessToast(message) {
        var el = document.getElementById("success-toast");
        if (!el || successToastActive) return;

        document.getElementById("success-toast-text").innerText = message;
        successToastActive = true;
        clearTimeout(successToastDismissTimer);
        
        el.classList.remove("hidden", "option-toast--hiding");
        el.style.opacity = "1"; 

        successToastDismissTimer = setTimeout(function () {
            el.style.opacity = "0";
            setTimeout(function() {
                el.classList.add("hidden");
                successToastActive = false;
                // 문구 원래대로 원복 (option-toast용)
                document.querySelector("#option-toast .option-toast__text").innerText = "먼저 색상과 사이즈를 골라주세요";
            }, 300);
        }, 2000);
    }

    // === 4. 선물하기 확정 버튼 이벤트 ===
    var confirmGiftBtn = document.getElementById("confirmGiftBtn");
    if(confirmGiftBtn) {
        confirmGiftBtn.addEventListener("click", function() {
            var activeBtn = document.querySelector(".gift-person-btn.active");
            if (!activeBtn) {
                alert("선물할 대상을 선택해주세요.");
                return;
            }
            var receiverNo = activeBtn.getAttribute("data-user-no");
            var ctx = document.body.getAttribute("data-context-path") || "/ondam";
            var qty = document.getElementById('buyQty').innerText;
            
            location.href = ctx + "/payment?productNo=" + currentProductNo + "&productOptionNo=" + selectedOptionNo + "&quantity=" + qty + "&isGift=true&receiverNo=" + receiverNo;
        });
    }

    // 조르기 모달 닫기 이벤트 바인딩 (옵셔널 체이닝 에러 해결)
    var closePokeModalBtn = document.getElementById("closePokeModalBtn");
    if (closePokeModalBtn) {
        closePokeModalBtn.addEventListener("click", function() {
            var dim = document.getElementById("pokeModalDim");
            var modal = document.getElementById("pokeModal");
            if (dim) dim.classList.add("hidden");
            if (modal) modal.classList.add("hidden");
        });
    }

    // 선물하기 모달 닫기 이벤트 바인딩
    var closeGiftModalBtn = document.getElementById("closeGiftModalBtn");
    if (closeGiftModalBtn) {
        closeGiftModalBtn.addEventListener("click", function() {
            var dim = document.getElementById("giftModalDim");
            var modal = document.getElementById("giftModal");
            if (dim) dim.classList.add("hidden");
            if (modal) modal.classList.add("hidden");
        });
    }

}); // DOMContentLoaded End


// === 비디오 재생 제어 ===
function toggleVideoPlay(video) {
    video.muted = isGlobalMuted;
    if (video.paused) {
        video.play();
    } else {
        video.pause();
    }
}

function toggleGlobalMute() {
    isGlobalMuted = !isGlobalMuted;
    var allVideos = document.querySelectorAll('.shorts-video');
    allVideos.forEach(function(v) { v.muted = isGlobalMuted; });

    var allIcons = document.querySelectorAll('.muteIcon');
    var allTexts = document.querySelectorAll('.muteText');

    allIcons.forEach(function(icon) {
        icon.innerText = isGlobalMuted ? 'volume_off' : 'volume_up';
    });
    allTexts.forEach(function(text) {
        text.innerText = isGlobalMuted ? '소리 끔' : '소리 켬';
    });
}


// === 토스트 메시지 ===
var optionToastActive = false;
var optionToastDismissTimer = null;

function showOptionErrorToast() {
    var el = document.getElementById("option-toast");
    if (!el || optionToastActive) return;

    optionToastActive = true;
    clearTimeout(optionToastDismissTimer);
    
    el.classList.remove("hidden", "option-toast--hiding");
    el.style.opacity = "1"; 

    optionToastDismissTimer = setTimeout(function () {
        el.style.opacity = "0";
        setTimeout(function() {
            el.classList.add("hidden");
            optionToastActive = false;
        }, 300);
    }, 2000);
}


// === 구매 모달 (BottomSheet) ===
function openPurchaseModal(productNo, productName, productPrice, imgFile) {
    var modal = document.getElementById('purchaseModalOverlay');
    if (modal) modal.classList.add('show');    
    
    currentProductNo = productNo;
    currentUnitPrice = parseInt(productPrice) || 0;
    currentAddPrice = 0; 
    selectedOptionNo = null;
    
    document.getElementById('modalProductName').innerText = productName;
    document.getElementById('buyQty').innerText = '1';
    refreshTotalPrice(); 

    var ctx = document.body.getAttribute("data-context-path") || "/ondam";
    var fetchUrl = window.location.origin + ctx + "/product?action=getOptions&productNo=" + productNo;
    
    fetch(fetchUrl)
        .then(function(response) { return response.json(); })
        .then(function(data) {
            currentOptions = data; 
            var sizeSelect = document.querySelector('select[name="optionSize"]');
            var colorSelect = document.querySelector('select[name="optionColor"]');

            if(sizeSelect && colorSelect) {
                sizeSelect.innerHTML = '<option value="">사이즈를 선택하세요</option>';
                colorSelect.innerHTML = '<option value="">색상을 선택하세요</option>';
                colorSelect.disabled = true;

                var sizes = [];
                data.forEach(function(opt) { 
                    if(opt.optionSize && sizes.indexOf(opt.optionSize) === -1) {
                        sizes.push(opt.optionSize);
                    } 
                });
                sizes.forEach(function(size) { 
                    sizeSelect.innerHTML += '<option value="' + size + '">' + size + '</option>'; 
                });

                sizeSelect.onchange = function() { 
                    updateColorOptions(this.value); 
                };
            }
        })
        .catch(function(err) { console.error("옵션 로드 실패:", err); });
}

function updateColorOptions(selectedSize) {
    var colorSelect = document.querySelector('select[name="optionColor"]');
    
    if (!selectedSize) {
        colorSelect.innerHTML = '<option value="">색상을 선택하세요</option>';
        colorSelect.disabled = true;
        currentAddPrice = 0; 
        selectedOptionNo = null; 
        refreshTotalPrice(); 
        return;
    }

    var filteredOptions = currentOptions.filter(function(opt) {
        return opt.optionSize === selectedSize;
    });
    
    colorSelect.innerHTML = '<option value="">색상을 선택하세요</option>';
    
    filteredOptions.forEach(function(opt) {
        var addPriceText = opt.optionAddPrice > 0 ? " (+" + opt.optionAddPrice.toLocaleString() + "원)" : "";
        colorSelect.innerHTML += '<option value="' + opt.optionColor + '">' + opt.optionColor + addPriceText + '</option>';
    });

    colorSelect.disabled = false;
    
    colorSelect.onchange = function() {
        var selectedColor = this.value;
        var sizeSelect = document.querySelector('select[name="optionSize"]');
        var selectedSizeVal = sizeSelect ? sizeSelect.value : "";
        
        var matchOpt = null;
        for (var i = 0; i < currentOptions.length; i++) {
            if (currentOptions[i].optionSize === selectedSizeVal && currentOptions[i].optionColor === selectedColor) {
                matchOpt = currentOptions[i];
                break;
            }
        }
        
        if (matchOpt) {
            currentAddPrice = parseInt(matchOpt.optionAddPrice) || 0;
            selectedOptionNo = matchOpt.productOptionNo; 
        } else {
            currentAddPrice = 0; 
            selectedOptionNo = null;
        }
        refreshTotalPrice(); 
    };
}

function refreshTotalPrice() {
    var qtySpan = document.getElementById('buyQty');
    var currentQty = parseInt(qtySpan.innerText) || 1;
    var finalPrice = (currentUnitPrice + currentAddPrice) * currentQty;
    var totalPriceElement = document.querySelector('.total-price');
    if (totalPriceElement) {
        totalPriceElement.innerText = finalPrice.toLocaleString() + "원";
    }
}

function updateQty(change) {
    var qtySpan = document.getElementById('buyQty');
    var currentQty = parseInt(qtySpan.innerText) + change;
    if (currentQty < 1) currentQty = 1; 
    qtySpan.innerText = currentQty;
    refreshTotalPrice(); 
}

function closePurchaseModal() {
    var overlay = document.getElementById('purchaseModalOverlay');
    if (overlay) overlay.classList.remove('show');
}


// === 조르기 / 선물하기 / 기타 액션 ===
function openPokeFromShorts() {
    if (!selectedOptionNo) { 
        showOptionErrorToast(); 
        return; 
    }
    closePurchaseModal(); 
    var dim = document.getElementById("pokeModalDim");
    var modal = document.getElementById("pokeModal");
    if (dim && modal) {
        dim.classList.remove("hidden");
        modal.classList.remove("hidden");
    }
}

function openGiftFromShorts() {
    if (!selectedOptionNo) { 
        showOptionErrorToast(); 
        return; 
    }
    closePurchaseModal(); 
    var dim = document.getElementById("giftModalDim");
    var modal = document.getElementById("giftModal");
    if (dim && modal) {
        dim.classList.remove("hidden");
        modal.classList.remove("hidden");
    }
}

function buyNow() {
    if (!selectedOptionNo) { 
        showOptionErrorToast(); 
        return; 
    }
    var ctx = document.body.getAttribute("data-context-path") || "/ondam";
    var qty = document.getElementById('buyQty').innerText;
    location.href = ctx + "/payment?productNo=" + currentProductNo + "&productOptionNo=" + selectedOptionNo + "&quantity=" + qty;
}

function addToCart() {
    if (!selectedOptionNo) { 
        showOptionErrorToast(); 
        return; 
    }
    var ctx = document.body.getAttribute("data-context-path") || "/ondam";
    var qty = document.getElementById('buyQty').innerText;

    var form = document.createElement("form");
    form.method = "POST";
    form.action = ctx + "/cart?action=add";
    
    var params = [
        ["productNo", currentProductNo], 
        ["productOptionNo", selectedOptionNo], 
        ["quantity", qty]
    ];
    
    params.forEach(function(pair) {
        var input = document.createElement("input");
        input.type = "hidden"; 
        input.name = pair[0]; 
        input.value = pair[1];
        form.appendChild(input);
    });
    
    document.body.appendChild(form);
    form.submit();
}

function shareShorts() {
    if (navigator.share) {
        navigator.share({ 
            title: '온담 추천영상', 
            url: window.location.href 
        }).catch(function(err) {
            console.log("공유 취소 또는 에러:", err);
        });
    } else {
        alert("기기에서 공유하기를 지원하지 않습니다. 링크를 복사해주세요.");
    }
}

function toggleLike(buttonElement, productNo) {
    var icon = buttonElement.querySelector('.material-icons');
    var isCurrentlyLiked = icon.classList.contains('liked');
    var nextState = !isCurrentlyLiked;

    if (nextState) {
        icon.classList.add('liked');
        icon.innerText = 'favorite';
    } else {
        icon.classList.remove('liked');
        icon.innerText = 'favorite_border';
    }
    icon.style.transform = 'scale(1.2)';
    setTimeout(function() { icon.style.transform = 'scale(1)'; }, 200);

    var formData = "action=toggle&productNo=" + productNo;
    var ctx = document.body.getAttribute("data-context-path") || "/ondam";
    
    fetch(ctx + "/wish", {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: formData
    })
    .then(function(response) { return response.json(); })
    .then(function(result) {
        if (result.status === 'error') {
            alert(result.message);
            if (isCurrentlyLiked) {
                icon.classList.add('liked');
                icon.innerText = 'favorite';
            } else {
                icon.classList.remove('liked');
                icon.innerText = 'favorite_border';
            }
        }
    })
    .catch(function(err) {
        console.error("찜 통신 에러:", err);
        if (isCurrentlyLiked) {
            icon.classList.add('liked');
            icon.innerText = 'favorite';
        } else {
            icon.classList.remove('liked');
            icon.innerText = 'favorite_border';
        }
    });
}