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