// 전역 변수 설정
let isGlobalMuted = false;
let currentUnitPrice = 0;    // 상품 기본가
let currentAddPrice = 0;     // 선택된 옵션의 추가 금액
let currentOptions = [];     // 현재 상품의 전체 옵션 데이터 세트

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
				// 다음 영상으로 진입 시 전역 설정값 적용
				video.muted = isGlobalMuted; 
				video.play().catch(error => {
					video.muted = true; 
					video.play();
				});
			} else {
				// 화면에서 벗어나면 정지 및 초기화
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

/**
 * 영상 재생/일시정지 토글
 */
function toggleVideoPlay(video) {
    video.muted = isGlobalMuted;
    if (video.paused) {
        video.play();
    } else {
        video.pause();
    }
}

/**
 * 전역 음소거 토글
 */
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

/**
 * 구매 모달 열기 및 데이터 초기화
 */
function openPurchaseModal(productNo, productName, productPrice, imgFile) {
    const modal = document.getElementById('purchaseModalOverlay');
    modal.classList.add('show');    
    
    // 기본 데이터 세팅
    currentUnitPrice = parseInt(productPrice) || 0;
    currentAddPrice = 0; // 모달 열 때 추가금 초기화
    
    document.getElementById('modalProductName').innerText = productName;
    document.getElementById('buyQty').innerText = '1';
    refreshTotalPrice(); // 초기 가격 표시

    // 백엔드 컨트롤러 호출 (action=getOptions)
    fetch(`${window.location.origin}/ondam/product?action=getOptions&productNo=${productNo}`)
        .then(response => response.json())
        .then(data => {
            currentOptions = data; // 필터링용 데이터 저장
            
            const sizeSelect = document.querySelector('select[name="optionSize"]');
            const colorSelect = document.querySelector('select[name="optionColor"]');

            // 셀렉트 박스 초기화
            sizeSelect.innerHTML = '<option value="">사이즈를 선택하세요</option>';
            colorSelect.innerHTML = '<option value="">색상을 선택하세요</option>';
            colorSelect.disabled = true;

            // 사이즈 목록 생성 (중복 제거)
            const sizes = new Set();
            data.forEach(opt => { if(opt.optionSize) sizes.add(opt.optionSize); });
            sizes.forEach(size => {
                 sizeSelect.innerHTML += `<option value="${size}">${size}</option>`;
            });

            // 사이즈 변경 이벤트
            sizeSelect.onchange = function() {
                updateColorOptions(this.value);
            };
        })
        .catch(err => console.error("옵션 로드 실패:", err));
}

/**
 * 선택한 사이즈에 맞는 색상 목록 업데이트
 */
function updateColorOptions(selectedSize) {
    const colorSelect = document.querySelector('select[name="optionColor"]');
    
    if (!selectedSize) {
        colorSelect.innerHTML = '<option value="">색상을 선택하세요</option>';
        colorSelect.disabled = true;
        currentAddPrice = 0;
        refreshTotalPrice();
        return;
    }

    // 해당 사이즈의 데이터만 필터링
    const filteredOptions = currentOptions.filter(opt => opt.optionSize === selectedSize);

    colorSelect.innerHTML = '<option value="">색상을 선택하세요</option>';
    filteredOptions.forEach(opt => {
        const addPriceText = opt.optionAddPrice > 0 ? ` (+${opt.optionAddPrice.toLocaleString()}원)` : '';
        colorSelect.innerHTML += `<option value="${opt.optionColor}">${opt.optionColor}${addPriceText}</option>`;
    });

    colorSelect.disabled = false;

    // 색상 변경 시 추가 금액 업데이트
    colorSelect.onchange = function() {
        const selectedColor = this.value;
        const sizeSelect = document.querySelector('select[name="optionSize"]');
        const selectedSize = sizeSelect.value;

        // DB 행(Row) 찾기
        const matchOpt = currentOptions.find(opt => opt.optionSize === selectedSize && opt.optionColor === selectedColor);
        
        if (matchOpt) {
            currentAddPrice = parseInt(matchOpt.optionAddPrice) || 0;
        } else {
            currentAddPrice = 0;
        }
        refreshTotalPrice(); 
    };
}

/**
 * 실시간 총 가격 계산 및 화면 표시
 */
function refreshTotalPrice() {
    const qtySpan = document.getElementById('buyQty');
    const currentQty = parseInt(qtySpan.innerText) || 1;
    const totalPriceElement = document.querySelector('.total-price');

    if (totalPriceElement) {
        // 공식: (기본가 + 옵션 추가금) * 수량
        const finalPrice = (currentUnitPrice + currentAddPrice) * currentQty;
        totalPriceElement.innerText = finalPrice.toLocaleString() + "원";
    }
}

/**
 * 수량 변경 (+, -)
 */
function updateQty(change) {
    const qtySpan = document.getElementById('buyQty');
    let currentQty = parseInt(qtySpan.innerText);
    currentQty += change;
    
    if (currentQty < 1) currentQty = 1; 
    qtySpan.innerText = currentQty;

    refreshTotalPrice(); // 가격 갱신 호출
}

// 모달 닫기
function closePurchaseModal() {
    document.getElementById('purchaseModalOverlay').classList.remove('show');
}

// 기타 액션 함수들 (동일)
function addToCart(productNo) { alert("장바구니에 담겼습니다!"); }
function toggleLike(shortsNo) { alert("찜했습니다!"); }
function shareShorts(shortsNo, title) {
    if (navigator.share) {
        navigator.share({ title: '온담 - ' + title, url: window.location.href });
    } else {
        alert("링크가 복사되었습니다.");
    }
}
function buyNow() { alert("결제 페이지로 이동합니다!"); }