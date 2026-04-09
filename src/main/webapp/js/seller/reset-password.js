/* global document, alert, console, window */
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

  var form = $('sellerResetPwForm');
  var sendBtn = $('sellerSendCodeBtn');
  var resendBtn = $('sellerResendBtn');
  var codeArea = $('sellerCodeArea');
  var resetActions = $('sellerResetPwActions');
  var verifyActions = $('sellerVerifyActions');

  function validateStep1() {
    var ok = true;
    var id = ($('sellerId') && $('sellerId').value) ? $('sellerId').value.trim() : '';
    var email = ($('sellerEmail') && $('sellerEmail').value) ? $('sellerEmail').value.trim() : '';

    clearError('sellerIdError');
    clearError('sellerEmailError');
    clearError('sellerFormError');

    if (!id) {
      showError('sellerIdError', '아이디를 입력해 주세요.');
      ok = false;
    }
    if (!email) {
      showError('sellerEmailError', '이메일을 입력해 주세요.');
      ok = false;
    } else if (!isEmail(email)) {
      showError('sellerEmailError', '이메일 형식을 확인해 주세요.');
      ok = false;
    }

    return { ok: ok, id: id, email: email };
  }

  function validateCode() {
    var code = ($('sellerCode') && $('sellerCode').value) ? $('sellerCode').value.trim() : '';
    clearError('sellerCodeError');
    clearError('sellerFormError');

    if (!code) {
      showError('sellerCodeError', '인증코드를 입력해 주세요.');
      return { ok: false, code: code };
    }
    return { ok: true, code: code };
  }

  function openCodeStep() {
    show(codeArea);
    hide(resetActions);
    show(verifyActions);
    alert('인증코드를 발송했어요. (더미)\n\n더미 코드: 123456');
  }

  function resetToStep1() {
    hide(codeArea);
    show(resetActions);
    hide(verifyActions);
    clearError('sellerCodeError');
    clearError('sellerFormError');
    var codeEl = $('sellerCode');
    if (codeEl) codeEl.value = '';
  }

  if (sendBtn) {
    sendBtn.addEventListener('click', function () {
      var v = validateStep1();
      if (!v.ok) return;

      // 더미 미가입/불일치 조건
      if (v.email.toLowerCase() === 'fail@ondam.com' || v.id.toLowerCase() === 'fail') {
        showError('sellerFormError', '입력 정보를 확인해 주세요.');
        return;
      }

      console.log('[SellerResetPw] send code (dummy)', v);
      openCodeStep();
    });
  }

  if (resendBtn) {
    resendBtn.addEventListener('click', function () {
      var v = validateStep1();
      if (!v.ok) return;
      console.log('[SellerResetPw] resend code (dummy)', v);
      alert('인증코드를 다시 발송했어요. (더미)\n\n더미 코드: 123456');
    });
  }

  if (form) {
    form.addEventListener('submit', function (e) {
      e.preventDefault();

      // 코드 단계가 열려있지 않으면 안내
      if (codeArea && codeArea.classList.contains('hidden')) {
        showError('sellerFormError', '먼저 인증코드를 받아주세요.');
        return;
      }

      var v1 = validateStep1();
      if (!v1.ok) return;

      var v2 = validateCode();
      if (!v2.ok) return;

      if (v2.code !== '123456') {
        showError('sellerFormError', '인증코드가 올바르지 않습니다.');
        return;
      }

      console.log('[SellerResetPw] verify success (dummy)', { id: v1.id, email: v1.email });
      alert('인증이 완료됐어요. (더미)\n\n다음 단계로 이동합니다.');

      // 실제 서버 연동 대신, 다음 화면으로 이동(더미)
      window.location.href = (document.body.getAttribute('data-context-path') || '') + '/seller/auth/reset-password-form';
    });
  }

  ['sellerId', 'sellerEmail', 'sellerCode'].forEach(function (id) {
    var el = $(id);
    if (!el) return;
    el.addEventListener('input', function () {
      if (id === 'sellerId') clearError('sellerIdError');
      if (id === 'sellerEmail') clearError('sellerEmailError');
      if (id === 'sellerCode') clearError('sellerCodeError');
      clearError('sellerFormError');
    });
  });

  // 뒤로 가기 등 대비: 화면 진입 시 기본 상태로
  resetToStep1();
})();

