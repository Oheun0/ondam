(function () {
  // 캐시 갱신 확인용 로그 (F12 개발자 도구 콘솔에서 이 문구가 보여야 최신 버전이 적용된 것입니다)
  console.log("[Ondam] 최신 shorts-list.js 가 로드되었습니다.");

  var contextPath = document.body.getAttribute('data-context-path') || '';
  var API_URL = contextPath + '/seller/shorts/api';

  function $(id) { return document.getElementById(id); }

  // 1. [상단] 새 쇼츠 등록 버튼
  var newBtn = $('sellerNewShortsBtn');
  if (newBtn) {
    newBtn.addEventListener('click', function () {
      window.location.href = contextPath + '/seller/shorts/form';
    });
  }
  
  // 2. [빈 화면] 새 쇼츠 등록 버튼
  var newBtnEmpty = $('sellerNewShortsBtnEmpty');
  if (newBtnEmpty) {
    newBtnEmpty.addEventListener('click', function () {
      window.location.href = contextPath + '/seller/shorts/form';
    });
  }

  // 3. 카드 내부 액션 버튼 이벤트 위임
  document.addEventListener('click', function (e) {
    var t = e.target;
    if (!t) return;

    var actionEl = t.closest('[data-action]');
    if (!actionEl) return;

    var action = actionEl.getAttribute('data-action');
    var card = actionEl.closest('.seller-shorts-card');
    
    var productNo = card ? card.getAttribute('data-product') : '';
    var videoFile = card ? card.getAttribute('data-video') : '';

    // [기능 A] 영상 미리보기 (새 탭에서 열기)
    if (action === 'preview') {
      if (videoFile && videoFile !== 'null' && videoFile !== '') {
        window.open(contextPath + '/uploads/shorts/' + videoFile, '_blank');
      } else {
        alert('영상이 아직 생성 중이거나 파일이 존재하지 않습니다.');
      }
      return;
    }

    // [기능 B] 수정 화면으로 이동 (현재 등록 폼으로 연결)
    if (action === 'edit') {
      window.location.href = contextPath + '/seller/shorts/form?productNo=' + productNo;
      return;
    }

    // [기능 C] 연결 상품 상세 보기 (새 탭 열기)
    if (action === 'product') {
      window.open(contextPath + '/product?action=detail&productNo=' + productNo, '_blank');
      return;
    }

    // [기능 D] 쇼츠 완전 삭제 (DB 및 파일 삭제)
    if (action === 'delete') {
      if (!confirm('정말 이 숏폼 영상을 삭제하시겠습니까?\n서버의 영상 파일과 데이터가 영구적으로 삭제됩니다.')) return;

      var params = new URLSearchParams();
      params.append('action', 'delete');
      params.append('productNo', productNo);

      fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: params.toString()
      })
      .then(function(response) { return response.json(); })
      .then(function(result) {
        if (result.status === 'success') {
          alert('영상이 삭제되었습니다.');
          window.location.reload(); // 목록 페이지 새로고침
        } else {
          alert('삭제 실패: ' + result.message);
        }
      })
      .catch(function(err) {
        alert('서버 통신 오류가 발생했습니다.');
      });
      return;
    }

    // [기능 E] 공개 / 비공개 상태 즉각 토글
    if (action === 'toggle' && card) {
      var isPublic = card.getAttribute('data-public') === 'true';
      var nextState = !isPublic;

      var paramsToggle = new URLSearchParams();
      paramsToggle.append('action', 'toggle');
      paramsToggle.append('productNo', productNo);

      fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: paramsToggle.toString()
      })
      .then(function(response) { return response.json(); })
      .then(function(result) {
        if (result.status === 'success') {
          // 화면의 상태 속성 및 UI 즉시 업데이트 (새로고침 불필요)
          card.setAttribute('data-public', nextState ? 'true' : 'false');
          
          var badge = card.querySelector('.seller-shorts-badge');
          var toggleBtn = card.querySelector('[data-action="toggle"]');

          if (badge) {
            badge.classList.toggle('seller-shorts-badge--public', nextState);
            badge.classList.toggle('seller-shorts-badge--private', !nextState);
            badge.textContent = nextState ? '공개' : '비공개';
            badge.style.background = '';
            badge.style.color = '';
          }
          if (toggleBtn) {
            toggleBtn.textContent = nextState ? '비공개 전환' : '공개 전환';
          }
        } else {
          alert('상태 변경 실패: ' + result.message);
        }
      })
      .catch(function(err) {
        alert('서버 통신 오류가 발생했습니다.');
      });
      return;
    }
  });
})();