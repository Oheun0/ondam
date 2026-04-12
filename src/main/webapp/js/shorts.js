let isGlobalMuted = false;
let currentUnitPrice = 0;    
let currentAddPrice = 0;     
let currentOptions = [];     

document.addEventListener("DOMContentLoaded", function() {
    const videos = document.querySelectorAll('.shorts-video');

    const observerOptions = {
        root: document.querySelector('.shorts-wrapper'),
        rootMargin: '0px',
        threshold: 0.6 
    };

    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            const video = entry.target;
            
			if (entry.isIntersecting) {
				video.muted = isGlobalMuted; 
				video.play().catch(error => {
					video.muted = true; 
					video.play();
				});
			} else {
				video.pause();
				video.muted = true; 
				video.currentTime = 0; 
			}
		});
    }, observerOptions);

    videos.forEach(video => {
        observer.observe(video);
    });
});

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
    const allVideos = document.querySelectorAll('.shorts-video');
    allVideos.forEach(v => { v.muted = isGlobalMuted; });

    const allIcons = document.querySelectorAll('.muteIcon');
    const allTexts = document.querySelectorAll('.muteText');

    allIcons.forEach(icon => {
        icon.innerText = isGlobalMuted ? 'volume_off' : 'volume_up';
    });
    allTexts.forEach(text => {
        text.innerText = isGlobalMuted ? '소리 끔' : '소리 켬';
    });
}

function openPurchaseModal(productNo, productName, productPrice, imgFile) {
    const modal = document.getElementById('purchaseModalOverlay');
    modal.classList.add('show');    
    
	const joreugiProductNo = document.getElementById('joreugiProductNo');
    if(joreugiProductNo) {
        joreugiProductNo.value = productNo;
    }
	
    currentUnitPrice = parseInt(productPrice) || 0;
    currentAddPrice = 0; 
    
    document.getElementById('modalProductName').innerText = productName;
    document.getElementById('buyQty').innerText = '1';
    refreshTotalPrice(); 

    fetch(`${window.location.origin}/ondam/product?action=getOptions&productNo=${productNo}`)
        .then(response => response.json())
        .then(data => {
            currentOptions = data; 
            
            const sizeSelect = document.querySelector('select[name="optionSize"]');
            const colorSelect = document.querySelector('select[name="optionColor"]');

            sizeSelect.innerHTML = '<option value="">사이즈를 선택하세요</option>';
            colorSelect.innerHTML = '<option value="">색상을 선택하세요</option>';
            colorSelect.disabled = true;

            const sizes = new Set();
            data.forEach(opt => { if(opt.optionSize) sizes.add(opt.optionSize); });
            sizes.forEach(size => {
                 sizeSelect.innerHTML += `<option value="${size}">${size}</option>`;
            });

            sizeSelect.onchange = function() {
                updateColorOptions(this.value);
            };
        })
        .catch(err => console.error("옵션 로드 실패:", err));
}

function updateColorOptions(selectedSize) {
    const colorSelect = document.querySelector('select[name="optionColor"]');
    
    if (!selectedSize) {
        colorSelect.innerHTML = '<option value="">색상을 선택하세요</option>';
        colorSelect.disabled = true;
        currentAddPrice = 0;
        refreshTotalPrice();
        return;
    }

    const filteredOptions = currentOptions.filter(opt => opt.optionSize === selectedSize);

    colorSelect.innerHTML = '<option value="">색상을 선택하세요</option>';
    filteredOptions.forEach(opt => {
        const addPriceText = opt.optionAddPrice > 0 ? ` (+${opt.optionAddPrice.toLocaleString()}원)` : '';
        colorSelect.innerHTML += `<option value="${opt.optionColor}">${opt.optionColor}${addPriceText}</option>`;
    });

    colorSelect.disabled = false;

    colorSelect.onchange = function() {
        const selectedColor = this.value;
        const sizeSelect = document.querySelector('select[name="optionSize"]');
        const selectedSize = sizeSelect.value;

        const matchOpt = currentOptions.find(opt => opt.optionSize === selectedSize && opt.optionColor === selectedColor);
        
        if (matchOpt) {
            currentAddPrice = parseInt(matchOpt.optionAddPrice) || 0;
        } else {
            currentAddPrice = 0;
        }
        refreshTotalPrice(); 
    };
}

function refreshTotalPrice() {
    const qtySpan = document.getElementById('buyQty');
    const currentQty = parseInt(qtySpan.innerText) || 1;
    const totalPriceElement = document.querySelector('.total-price');

    if (totalPriceElement) {
        const finalPrice = (currentUnitPrice + currentAddPrice) * currentQty;
        totalPriceElement.innerText = finalPrice.toLocaleString() + "원";
    }
}

function updateQty(change) {
    const qtySpan = document.getElementById('buyQty');
    let currentQty = parseInt(qtySpan.innerText);
    currentQty += change;
    
    if (currentQty < 1) currentQty = 1; 
    qtySpan.innerText = currentQty;

    refreshTotalPrice(); 
}

function closePurchaseModal() {
    document.getElementById('purchaseModalOverlay').classList.remove('show');
}

function addToCart(productNo) { alert("장바구니에 담겼습니다!"); }

function shareShorts(shortsNo, title) {
    if (navigator.share) {
        navigator.share({ title: '온담 - ' + title, url: window.location.href });
    } else {
        alert("링크가 복사되었습니다.");
    }
}

function buyNow() {
    const sizeSelect = document.querySelector('select[name="optionSize"]');
    const colorSelect = document.querySelector('select[name="optionColor"]');

    if (!sizeSelect.value || !colorSelect.value) {
        alert('사이즈와 색상을 모두 선택해주세요.');
        return;
    }
    alert("결제 페이지로 이동합니다!");
}

function submitJoreugi() {
    const qty = document.getElementById('buyQty').innerText;
    const quantityInput = document.getElementById('joreugiQuantity');
    const optionInput = document.getElementById('joreugiOptionNo');
    const form = document.getElementById('joreugiForm');
    
    const sizeSelect = document.querySelector('select[name="optionSize"]');
    const colorSelect = document.querySelector('select[name="optionColor"]');

    if (!sizeSelect.value || !colorSelect.value) {
        alert('사이즈와 색상을 모두 선택해주세요.');
        return;
    }

    const matchOpt = currentOptions.find(opt => opt.optionSize === sizeSelect.value && opt.optionColor === colorSelect.value);

    if(quantityInput && form && matchOpt) {
        quantityInput.value = qty;
        optionInput.value = matchOpt.productOptionNo; 
        
        alert("선택하신 옵션으로 조르기 요청이 전송되었습니다!"); 
        form.submit();
    } else {
        console.error("옵션 정보 오류 또는 숨겨진 폼(joreugiForm)을 찾을 수 없습니다.");
    }
}

// [FIXED] 찜 상태 변경 (Optimistic UI 적용)
function toggleLike(buttonElement, productNo) {
    const icon = buttonElement.querySelector('.material-icons');
    const isCurrentlyLiked = icon.classList.contains('liked');
    const nextState = !isCurrentlyLiked;

    // 1. 화면 먼저 변경 (체감 속도 최적화)
    icon.classList.toggle('liked', nextState);
    icon.innerText = nextState ? 'favorite' : 'favorite_border';
    icon.style.transform = 'scale(1.2)';
    setTimeout(function() { icon.style.transform = 'scale(1)'; }, 200);

    // 2. 서버로 비동기 전송
    const formData = new URLSearchParams();
    formData.append('action', 'toggle');
    formData.append('productNo', productNo);

    fetch(window.location.origin + '/ondam/wish', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: formData.toString()
    })
    .then(function(response) {
        return response.json();
    })
    .then(function(result) {
        if (result.status === 'error') {
            // 미로그인 등 에러 시 원복
            alert(result.message);
            icon.classList.toggle('liked', isCurrentlyLiked);
            icon.innerText = isCurrentlyLiked ? 'favorite' : 'favorite_border';
        }
    })
    .catch(function(err) {
        console.error("찜 통신 에러:", err);
        alert('서버 통신 오류가 발생했습니다.');
        icon.classList.toggle('liked', isCurrentlyLiked);
        icon.innerText = isCurrentlyLiked ? 'favorite' : 'favorite_border';
    });
}