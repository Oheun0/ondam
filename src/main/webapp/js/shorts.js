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
                video.play().catch(error => {
                    console.log("브라우저 자동재생 정책으로 인해 막힘:", error);
                });
            } else {
                video.pause();
                video.currentTime = 0; 
            }
        });
    }, observerOptions);

    videos.forEach(video => {
        observer.observe(video);
    });
});

// 장바구니 담기
function addToCart(productNo) {
    console.log("장바구니 추가 버튼 클릭, 상품번호:", productNo);
    // TODO: 나중에 fetch나 ajax를 이용해 장바구니 서블릿으로 데이터를 보냅니다.
    alert("장바구니에 담겼습니다! (상품번호: " + productNo + ")");
}

// 찜하기 (좋아요)
function toggleLike(shortsNo) {
    console.log("찜 버튼 클릭, 쇼츠번호:", shortsNo);
    // TODO: 하트 아이콘 색상을 빨간색으로 바꾸고, DB에 찜 내역을 저장합니다.
    alert("이 영상을 찜했습니다!");
}

// 조르기
function openJoreugi(productNo) {
    console.log("조르기 버튼 클릭, 상품번호:", productNo);
    // TODO: 조르기 모달창 띄우기
}

// 선물하기
function openGift(productNo) {
    console.log("선물하기 버튼 클릭, 상품번호:", productNo);
    // TODO: 선물하기 모달창 띄우기 또는 페이지 이동
}

// 공유하기 (모바일 기본 공유창 띄우기)
function shareShorts(shortsNo, title) {
    if (navigator.share) {
        navigator.share({
            title: '온담 - ' + title,
            text: '이 영상 한번 봐봐! 완전 추천해.',
            // 실제 접속 가능한 상품 상세 페이지 주소를 만들어줍니다.
            url: window.location.origin + '/ondam/product/detail?no=' + shortsNo
        }).catch(console.error);
    } else {
        // PC 접속 등 공유 API를 지원하지 않는 경우의 예외 처리
        alert("공유하기 기능은 모바일 기기에서 지원됩니다.\n(링크: " + window.location.origin + "/ondam/product/detail?no=" + shortsNo + ")");
    }
}

// 설명란 펼치기/접기 토글 함수
function toggleDescription(element) {
    element.classList.toggle('expanded');
}

function openPurchaseModal(productNo, productName) {
    // 1. 모달 띄우기
    const modal = document.getElementById('purchaseModalOverlay');
    modal.classList.add('show');
    
    // 2. 모달 안의 상품명 변경
    document.getElementById('modalProductName').innerText = productName;
    
    // 3. 수량 초기화
    document.getElementById('buyQty').innerText = '1';
    
	const joreugiProductNo = document.getElementById('joreugiProductNo');
	    if(joreugiProductNo) {
	        joreugiProductNo.value = productNo;
	    }
    // (선택) 여기에 나중에 fetch/ajax로 해당 상품의 가격, 색상, 사이즈 데이터를 불러오는 로직 추가
}

// 모달 닫기
function closePurchaseModal() {
    document.getElementById('purchaseModalOverlay').classList.remove('show');
}

// 개수 조절 버튼 (+, -)
function updateQty(change) {
    const qtySpan = document.getElementById('buyQty');
    let currentQty = parseInt(qtySpan.innerText);
    currentQty += change;
    
    if (currentQty < 1) currentQty = 1; // 1개 밑으로는 안 내려가게 방어
    qtySpan.innerText = currentQty;
}

// 구매하기 버튼
function buyNow() {
    alert("결제 페이지로 이동합니다!");
    // location.href = '/ondam/order?productNo=...';
}

function submitJoreugi() {
    // 1. 모달에서 사용자가 선택한 '수량' 가져오기
    let qty = document.getElementById('buyQty').innerText;
    
    // 2. 폼 내부 요소 찾기
    const quantityInput = document.getElementById('joreugiQuantity');
    const form = document.getElementById('joreugiForm');
    
    // 3. 폼에 수량 값 넣고 전송하기
    if(quantityInput && form) {
        quantityInput.value = qty;
        
        // 옵션 선택(select box)의 value를 가져오는 로직도 나중에 이 부분에 추가하시면 됩니다.
        // ex) document.getElementById('joreugiOptionNo').value = 선택한옵션값;

        alert("선택하신 옵션으로 조르기 요청이 전송되었습니다!"); 
        form.submit();
    } else {
        console.error("숨겨진 조르기 폼(joreugiForm)을 찾을 수 없습니다. JSP 파일을 확인해 주세요.");
    }
}

// 찜
function toggleLike(buttonElement, shortsNo) {
    const icon = buttonElement.querySelector('.material-icons');
    const isLiked = icon.classList.toggle('liked');
    
    if (isLiked) {
        // 찜을 한 상태
        icon.innerText = 'favorite'; // 꽉 찬 하트 아이콘으로 텍스트 변경
        console.log("찜 추가됨! 쇼츠번호:", shortsNo);
        // TODO: 나중에 fetch/ajax로 DB에 찜 추가 내역 전송
    } else {
        // 찜을 취소한 상태
        icon.innerText = 'favorite_border'; // 빈 하트 아이콘으로 텍스트 변경
        console.log("찜 취소됨! 쇼츠번호:", shortsNo);
        // TODO: 나중에 fetch/ajax로 DB에서 찜 삭제 내역 전송
    }

    setTimeout(() => {
        icon.style.transform = 'scale(1)';
    }, 200);
}