/* global document */
(function () {
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

  function isEmail(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(String(email || '').trim());
  }

  var form = $('sellerFindIdForm');

  function validate() {
    var ok = true;
    var name = ($('sellerManagerName') && $('sellerManagerName').value) ? $('sellerManagerName').value.trim() : '';
    var email = ($('sellerEmail') && $('sellerEmail').value) ? $('sellerEmail').value.trim() : '';

    clearError('sellerManagerNameError');
    clearError('sellerEmailError');
    // JS 검사 시 기존 서버 에러나 결과창도 일단 숨김
    clearError('sellerFindIdFormError');
    hide($('sellerFindIdResult'));

    if (!name) {
      showError('sellerManagerNameError', '담당자명을 입력해 주세요.');
      ok = false;
    }
    if (!email) {
      showError('sellerEmailError', '이메일을 입력해 주세요.');
      ok = false;
    } else if (!isEmail(email)) {
      showError('sellerEmailError', '이메일 형식을 확인해 주세요.');
      ok = false;
    }
    return ok;
  }

  if (form) {
    form.addEventListener('submit', function (e) {
      var ok = validate();
      if (!ok) {
        // 검사 실패 시에만 서버 전송 막기
        e.preventDefault(); 
      }
      // 통과하면 e.preventDefault()가 안 걸렸으므로 자연스럽게 서버로 전송됨!
    });
  }

  ['sellerManagerName', 'sellerEmail'].forEach(function (id) {
    var el = $(id);
    if (!el) return;
    el.addEventListener('input', function () {
      clearError(id + 'Error');
      clearError('sellerFindIdFormError');
    });
  });
})();