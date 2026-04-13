/* aiSearch.js */

document.addEventListener('DOMContentLoaded', function() {
    const uploadZone = document.getElementById('uploadZone');
    const fileInp = document.getElementById('fileInp');
    const previewBox = document.getElementById('previewBox');
    const previewImg = document.getElementById('previewImg');

    // -----------------------------------------------------
    // ✨ [추가] 뒤로가기 시 기존 검색 결과 복구 로직
    // -----------------------------------------------------
	const navEntries = performance.getEntriesByType('navigation');
	    const isBackForward = navEntries.length > 0 && navEntries[0].type === 'back_forward';

	    const savedProducts = sessionStorage.getItem('aiSearchResults');
	    const savedImage = sessionStorage.getItem('aiSearchImage');

	    // 오직 '뒤로가기'로 왔을 때만 복구하고, 그 외(직접 진입)에는 삭제
	    if (isBackForward && savedProducts && savedImage) {
	        if (previewImg) previewImg.src = savedImage;
	        if (previewBox) previewBox.style.display = 'block';
	        renderResults(JSON.parse(savedProducts));
	    } else {
	        // 직접 메뉴를 눌러 들어왔거나 새로고침 시에는 저장된 내역을 비움
	        sessionStorage.removeItem('aiSearchResults');
	        sessionStorage.removeItem('aiSearchImage');
	    }

    if (uploadZone && fileInp) {
        uploadZone.addEventListener('click', function() {
            fileInp.value = ''; // 재검색 시 반응 없던 문제 해결용
            fileInp.click(); 
        });
    }

    if (fileInp) {
        fileInp.addEventListener('change', function(e) {
            const file = e.target.files[0];
            if (!file) return;

            const reader = new FileReader();
            reader.onload = function(ev) {
                const imgSrc = ev.target.result; // base64 이미지 데이터
                if (previewImg) previewImg.src = imgSrc;
                if (previewBox) previewBox.style.display = 'block';
                
                // 검색 실행 시 이미지 데이터도 같이 넘겨서 저장하게 함
                performSearch(file, imgSrc); 
            };
            reader.readAsDataURL(file);
        });
    }
});

/**
 * 서버 통신 함수
 */
function performSearch(file, imgSrc) { 
    const loading = document.getElementById('loading');
    const resultArea = document.getElementById('resultArea'); // ✨ 추가
    const resultGrid = document.getElementById('searchResultGrid');
    
    const formData = new FormData();
    formData.append("searchImage", file);

    // ✨ [수정] 검색 시작 시 결과 영역(빈 공간 포함)을 통째로 숨기고 로딩만 띄움!
    if (resultArea) resultArea.style.display = 'none'; 
    if (resultGrid) resultGrid.innerHTML = ''; 
    if (loading) loading.style.display = 'block';

    fetch(CONTEXT_PATH + '/aiSearch?action=search', {
        method: 'POST',
        body: formData
    })
    .then(res => res.json())
    .then(resData => {
        if (resData.status === 'success') {
            renderResults(resData.data);
            
            try {
                sessionStorage.setItem('aiSearchResults', JSON.stringify(resData.data));
                sessionStorage.setItem('aiSearchImage', imgSrc);
            } catch (e) {
                console.warn("이미지 용량 초과로 임시 저장이 불가할 수 있습니다.");
            }
        } else {
            alert('이미지 분석에 실패했습니다.');
            if (resultArea) resultArea.style.display = 'block'; // 실패 시 다시 보여줌
        }
    })
    .catch(err => {
        console.error("Search Error:", err);
        alert('서버와 통신 중 오류가 발생했습니다.');
        if (resultArea) resultArea.style.display = 'block'; // 에러 시 다시 보여줌
    })
    .finally(() => {
        if (loading) loading.style.display = 'none'; // 분석 끝나면 로딩 숨김
    });
}

/**
 * 결과 렌더링 함수
 */
function renderResults(products) {
    const resultArea = document.getElementById('resultArea'); // ✨ 추가
    const resultGrid = document.getElementById('searchResultGrid');
    
    if (!resultGrid) return;

    resultGrid.innerHTML = '';
    
    // ✨ [수정] 결과가 나왔을 때 부모 영역 전체를 다시 화면에 나타나게 함!
    if (resultArea) resultArea.style.display = 'block';

    if (!products || products.length === 0) {
        resultGrid.innerHTML = `
            <div class="product-empty" style="grid-column: span 2; text-align: center; padding: 50px 0;">
                <p>해당 조건의 상품이 없습니다.</p>
            </div>`;
        return;
    }

    let html = '';
    products.forEach((p, index) => {
        const formattedPrice = Number(p.productPrice).toLocaleString();
        const brandText = p.productBrand ? p.productBrand : `온담 AI 추천 #${p.rank}`;

        let discountHtml = '';
        if (p.productOriginPrice && p.productOriginPrice > p.productPrice) {
            const discountRate = Math.round((1 - p.productPrice / p.productOriginPrice) * 100);
            discountHtml = `<span class="product-discount">${discountRate}% 할인</span>`;
        }

        // 도미노처럼 쭈르륵 뜨는 애니메이션 속성 유지
        html += `
        <article class="product-card"
            data-product-no="${p.productNo}"
            onclick="if(!event.target.closest('.product-grid-wish-btn')) { location.href='${CONTEXT_PATH}/product?action=detail&productNo=${p.productNo}'; }"
            style="cursor:pointer; opacity: 0; animation: slideUpFade 0.4s ease forwards; animation-delay: ${index * 0.05}s;">

          <div class="product-thumb-wrap">
            <img class="product-thumb"
                 src="${CONTEXT_PATH}/${p.imgPath}"
                 alt="${p.productName}"
                 loading="lazy" width="300" height="300">

            <!-- 찜 버튼 -->
            <button type="button"
                    class="related-wish-btn product-grid-wish-btn"
                    data-product-no="${p.productNo}"
                    aria-pressed="false" aria-label="찜하기">
              <span class="material-icons-outlined" aria-hidden="true">favorite_border</span>
            </button>
          </div>

          <div class="product-body">
            <div class="brand-meta-row">
              <p class="product-brand">${brandText}</p>
            </div>
            <h3 class="product-name">${p.productName}</h3>
            <div class="product-price-row">
              <span class="product-price">${formattedPrice}원</span>
              ${discountHtml}
            </div>
          </div>

        </article>`;
    });

    resultGrid.innerHTML = html;
}
// 찜 이벤트 위임 (searchResultGrid가 부모)
const resultGridEl = document.getElementById('searchResultGrid');
if (resultGridEl) {
    resultGridEl.addEventListener('click', function(e) {
        const wishBtn = e.target.closest('.product-grid-wish-btn');
        if (wishBtn) {
            e.preventDefault();
            e.stopPropagation();
            handleWishToggle(wishBtn);
        }
    });
}

function handleWishToggle(wishBtn) {
    const ctx = document.body.dataset.contextPath;
    const isLogin = document.body.dataset.loginUser === 'true';

    if (!isLogin) {
        window.location.href = ctx + "/login";
        return;
    }

    const productNo = wishBtn.dataset.productNo;
    const isActive = wishBtn.classList.contains('is-active');

    wishBtn.classList.toggle('is-active', !isActive);
    wishBtn.innerHTML = !isActive 
        ? '<span class="material-icons">favorite</span>' 
        : '<span class="material-icons-outlined">favorite_border</span>';

    fetch(ctx + "/wish?action=toggle&productNo=" + productNo, { method: "POST" })
    .then(r => r.json())
    .then(data => {
        wishBtn.classList.toggle('is-active', data.wished);
        wishBtn.innerHTML = data.wished 
            ? '<span class="material-icons">favorite</span>' 
            : '<span class="material-icons-outlined">favorite_border</span>';
    });
}