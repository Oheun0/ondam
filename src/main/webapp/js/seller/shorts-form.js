(function () {
  var contextPath = document.body.getAttribute('data-context-path') || '';
  var API_URL = contextPath + '/seller/shorts/api'; 

  function $(id) { return document.getElementById(id); }
  function show(el) { if (el) el.classList.remove('hidden'); }
  function hide(el) { if (el) el.classList.add('hidden'); }
  function setText(el, text) { if (el) el.textContent = text; }

  function showError(id, msg) {
    var el = $(id);
    if (!el) return;
    setText(el, msg);
    show(el);
  }
  function clearError(id) {
    var el = $(id);
    if (!el) return;
    setText(el, '');
    hide(el);
  }

  var form = $('sellerShortsForm');
  if (!form) return;

  var titleEl = $('shortsTitle');
  var productEl = $('productNo');
  var contentEl = $('shortsContent');
  
  var videoFileObj = null;
  var thumbBase64Data = null; 

  var videoFileNameEl = $('videoFileName');
  var thumbFileNameEl = $('thumbFileName');
  var thumbPreviewImg = $('thumbPreviewImg');
  var thumbPreviewEmpty = $('thumbPreviewEmpty');

  var realVideoInput = document.createElement('input');
  realVideoInput.type = 'file';
  realVideoInput.accept = 'video/mp4,video/x-m4v,video/*';
  realVideoInput.style.display = 'none';
  document.body.appendChild(realVideoInput);

  var realThumbInput = document.createElement('input');
  realThumbInput.type = 'file';
  realThumbInput.accept = 'image/jpeg,image/png,image/jpg';
  realThumbInput.style.display = 'none';
  document.body.appendChild(realThumbInput);

  function setThumbPreview(src) {
    if (src) {
      if (thumbPreviewImg) { thumbPreviewImg.src = src; show(thumbPreviewImg); }
      hide(thumbPreviewEmpty);
    } else {
      if (thumbPreviewImg) { thumbPreviewImg.removeAttribute('src'); hide(thumbPreviewImg); }
      show(thumbPreviewEmpty);
    }
  }

  // [최종 수정] 렌더링 지연 방지 로직이 추가된 자동 썸네일 추출 함수
  function generateThumbnailFromVideo(file) {
    var video = document.createElement('video');
    video.src = URL.createObjectURL(file);
    video.crossOrigin = 'anonymous';
    video.muted = true;
    video.playsInline = true;
    video.preload = 'auto'; // 메타데이터 우선 로드

    video.addEventListener('loadedmetadata', function() {
      // 영상의 정중앙 지점 계산
      var midTime = video.duration / 2;
      video.currentTime = midTime; 
      console.log("[Log] 썸네일 추출 시도 시점: " + midTime.toFixed(2) + "초");
    });

    video.addEventListener('seeked', function() {
      // [핵심] seeked 직후 바로 그리면 검은 화면일 확률이 높음. 
      // 0.3초의 대기 시간을 주어 브라우저가 프레임을 완전히 디코딩하게 함.
      setTimeout(function() {
        var canvas = document.createElement('canvas');
        canvas.width = video.videoWidth;
        canvas.height = video.videoHeight;
        var ctx = canvas.getContext('2d');
        
        // 캔버스에 그리기 전 비디오 상태 강제 갱신을 위해 play/pause 트릭
        video.play().then(function() {
            video.pause();
            ctx.drawImage(video, 0, 0, canvas.width, canvas.height);

            // 픽셀 검사 (중앙 1x1 픽셀이 검정색인지 확인)
            var pixel = ctx.getImageData(canvas.width / 2, canvas.height / 2, 1, 1).data;
            if (pixel[0] === 0 && pixel[1] === 0 && pixel[2] === 0) {
                console.warn("[Warn] 추출된 프레임이 검정색입니다. (R:0, G:0, B:0)");
            }

            thumbBase64Data = canvas.toDataURL('image/jpeg', 0.85);

            if (thumbFileNameEl) thumbFileNameEl.textContent = "(자동 추출 완료) " + file.name.replace(/\.[^/.]+$/, "") + "_thumb.jpg";
            setThumbPreview(thumbBase64Data);

            // 메모리 해제
            URL.revokeObjectURL(video.src);
        });
      }, 300); 
    });

    video.addEventListener('error', function() {
        console.error("비디오 파일 로드 중 오류가 발생했습니다.");
        URL.revokeObjectURL(video.src);
    });
  }

  realVideoInput.addEventListener('change', function(e) {
    if (e.target.files.length > 0) {
      videoFileObj = e.target.files[0];
      if (videoFileNameEl) videoFileNameEl.textContent = videoFileObj.name;
      clearError('videoError');
      generateThumbnailFromVideo(videoFileObj);
    }
  });

  realThumbInput.addEventListener('change', function(e) {
    if (e.target.files.length > 0) {
      var file = e.target.files[0];
      if (thumbFileNameEl) thumbFileNameEl.textContent = file.name;
      var reader = new FileReader();
      reader.onload = function(event) {
        thumbBase64Data = event.target.result;
        setThumbPreview(thumbBase64Data);
      };
      reader.readAsDataURL(file);
    }
  });

  $('videoBox').addEventListener('click', function() { realVideoInput.click(); });
  $('videoPickBtn').addEventListener('click', function(e) { e.preventDefault(); realVideoInput.click(); });
  $('thumbBox').addEventListener('click', function() { realThumbInput.click(); });
  $('thumbPickBtn').addEventListener('click', function(e) { e.preventDefault(); realThumbInput.click(); });

  var aiVideoBtn = $('aiVideoBtn');
  if (aiVideoBtn) {
    aiVideoBtn.addEventListener('click', function (e) {
      e.preventDefault();
      var pNo = productEl.value;
      var title = titleEl.value.trim();
      var content = contentEl ? contentEl.value.trim() : '';
      if (!pNo) { alert('먼저 연결할 상품을 선택해주세요.'); return; }
      if (!title) { alert('영상 자막으로 들어갈 쇼츠 제목을 입력해주세요.'); return; }
      if (!confirm("'" + title + "' 자막으로 AI 영상을 생성하시겠습니까?")) return;
      var params = new URLSearchParams();
      params.append('action', 'generate');
      params.append('productNo', pNo);
      params.append('shortsTitle', title);     
      params.append('shortsContent', content); 
      fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: params.toString()
      })
      .then(function(response) { return response.json(); })
      .then(function(result) {
        if (result.status === 'success') {
          alert('영상 생성이 시작되었습니다. 목록 화면으로 이동합니다.');
          window.location.href = contextPath + '/seller/shorts/list';
        } else {
          alert('오류: ' + result.message);
        }
      });
    });
  }

  function validateRequired() {
    var ok = true;
    clearError('shortsTitleError');
    clearError('productNoError');
    clearError('videoError');
    clearError('formError');
    if (!titleEl.value.trim()) { showError('shortsTitleError', '쇼츠 제목을 입력해 주세요.'); ok = false; }
    if (!productEl.value) { showError('productNoError', '연결 상품을 선택해 주세요.'); ok = false; }
    if (!videoFileObj) { showError('videoError', '수동 등록 시 영상 파일을 등록해 주세요.'); ok = false; }
    if (!ok) showError('formError', '필수 항목을 확인해 주세요.');
    return ok;
  }

  form.addEventListener('submit', function (e) {
    e.preventDefault();
    if (!validateRequired()) return;
    var submitBtn = $('submitBtn');
    submitBtn.disabled = true;
    submitBtn.textContent = '업로드 중...';
    var formData = new FormData();
    formData.append('action', 'upload');
    formData.append('productNo', productEl.value);
    formData.append('shortsTitle', titleEl.value.trim());
    formData.append('shortsContent', contentEl ? contentEl.value.trim() : ''); 
    formData.append('videoFile', videoFileObj);
    if (thumbBase64Data) {
      formData.append('thumbnailBase64', thumbBase64Data);
    }
    fetch(API_URL, {
      method: 'POST',
      body: formData 
    })
    .then(function(response) { return response.json(); })
    .then(function(result) {
      if (result.status === 'success') {
        alert('업로드가 완료되었습니다.');
        window.location.href = contextPath + '/seller/shorts/list';
      } else {
        alert('업로드 실패: ' + result.message);
        submitBtn.disabled = false;
        submitBtn.textContent = '수동 영상 등록하기';
      }
    });
  });

  var elList = [titleEl, productEl];
  for (var i = 0; i < elList.length; i++) {
    if (elList[i]) {
      (function(el) {
        el.addEventListener('input', function() { clearError(el.id + 'Error'); });
        el.addEventListener('change', function() { clearError(el.id + 'Error'); });
      })(elList[i]);
    }
  }
})();