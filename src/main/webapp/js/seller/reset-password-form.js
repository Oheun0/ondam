/*reset-password-form.js*/
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
      // e.preventDefault(); <-- 💡 주석 처리 하거나 삭제!

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

      if (!ok) {
        e.preventDefault(); // 에러가 있을 때만 서버 전송을 막습니다.
        return;
      }

      // 에러가 없으면 alert 띄울 필요 없이 폼이 서버(Controller)로 제출됩니다!
      // 아래에 있던 alert와 window.location.href 로직은 삭제합니다.
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

