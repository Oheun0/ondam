/* global document, alert, console */
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

  function validPw(pw) {
    var s = String(pw || '');
    if (s.length < 8) return false;
    var hasLetter = /[A-Za-z]/.test(s);
    var hasNumber = /\d/.test(s);
    var hasSpecial = /[^A-Za-z0-9]/.test(s);
    return hasLetter && hasNumber && hasSpecial;
  }

  var form = $('sellerResetPwForm2');
  if (!form) return;

  form.addEventListener('submit', function (e) {
    e.preventDefault();

    clearError('newPwError');
    clearError('newPw2Error');
    clearError('sellerFormError');

    var pw1 = ($('newPw') && $('newPw').value) ? $('newPw').value : '';
    var pw2 = ($('newPw2') && $('newPw2').value) ? $('newPw2').value : '';

    var ok = true;
    if (!pw1) {
      showError('newPwError', '새 비밀번호를 입력해 주세요.');
      ok = false;
    } else if (!validPw(pw1)) {
      showError('newPwError', '비밀번호는 8자 이상, 영문/숫자/특수문자를 포함해 주세요.');
      ok = false;
    }

    if (!pw2) {
      showError('newPw2Error', '비밀번호 확인을 입력해 주세요.');
      ok = false;
    } else if (pw1 && pw1 !== pw2) {
      showError('newPw2Error', '비밀번호가 일치하지 않습니다.');
      ok = false;
    }

    if (!ok) return;

    console.log('[SellerResetPwForm] change password success (dummy)');
    alert('비밀번호가 변경되었습니다. (더미)\n\n로그인 화면으로 이동합니다.');
    window.location.href = (document.body.getAttribute('data-context-path') || '') + '/seller/auth/login';
  });

  ['newPw', 'newPw2'].forEach(function (id) {
    var el = $(id);
    if (!el) return;
    el.addEventListener('input', function () {
      if (id === 'newPw') clearError('newPwError');
      if (id === 'newPw2') clearError('newPw2Error');
      clearError('sellerFormError');
    });
  });
})();

