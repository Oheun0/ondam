/* global document, alert */
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

  function maskId(id) {
    var s = String(id || '');
    if (s.length <= 4) return s[0] ? (s[0] + '***') : '****';
    return s.slice(0, Math.max(1, s.length - 4)) + '****';
  }

  var form = $('sellerFindIdForm');
  var resultBox = $('sellerFindIdResult');
  var resultValue = $('sellerFindIdValue');

  function validate() {
    var ok = true;
    var name = ($('sellerManagerName') && $('sellerManagerName').value) ? $('sellerManagerName').value.trim() : '';
    var email = ($('sellerEmail') && $('sellerEmail').value) ? $('sellerEmail').value.trim() : '';

    clearError('sellerManagerNameError');
    clearError('sellerEmailError');
    clearError('sellerFindIdFormError');
    hide(resultBox);

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

    return { ok: ok, name: name, email: email };
  }

  if (form) {
    form.addEventListener('submit', function (e) {
      e.preventDefault();
      var v = validate();
      if (!v.ok) return;

      // 더미 실패 조건
      if (v.email.toLowerCase() === 'fail@ondam.com') {
        showError('sellerFindIdFormError', '입력 정보를 확인해 주세요.');
        return;
      }

      // 더미 성공 결과
      var dummyId = 'ondam_seller01';
      setText(resultValue, maskId(dummyId));
      show(resultBox);
      alert('아이디 찾기 성공(더미) — 아래에서 아이디를 확인해 주세요.');
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

