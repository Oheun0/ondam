/*reset-password.js*/
/* global document, alert, window */
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

  var ctx = (document.body && document.body.getAttribute('data-context-path')) || '';
  var sendCodeUrl = ctx + '/seller/auth/reset-password/send-code';

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

  function postSendCode() {
    var v = validateStep1();
    if (!v.ok) {
      return Promise.resolve({ httpOk: false, data: { ok: false, message: '', validationFailed: true } });
    }

    var body = new URLSearchParams();
    body.set('sellerId', v.id);
    body.set('sellerEmail', v.email);

    return fetch(sendCodeUrl, {
      method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
      body: body.toString(),
      credentials: 'same-origin'
    }).then(function (res) {
      return res.text().then(function (text) {
        var data;
        try {
          data = text ? JSON.parse(text) : {};
        } catch (e) {
          data = {
            ok: false,
            message: '서버 응답을 해석할 수 없습니다.(' + res.status + ') 톰캣을 재시작하고 WEB-INF/lib·web.xml을 확인하세요.'
          };
        }
        return { httpOk: res.ok, data: data };
      });
    }).catch(function () {
      return { httpOk: false, data: { ok: false, message: '네트워크 오류가 났습니다.' } };
    });
  }

  if (sendBtn) {
    sendBtn.addEventListener('click', function () {
      sendBtn.disabled = true;
      postSendCode().then(function (r) {
        sendBtn.disabled = false;
        if (r.data && r.data.validationFailed) {
          return;
        }
        if (r.data && r.data.ok) {
          openCodeStep();
          alert(r.data.message || '인증코드를 보냈어요.');
        } else {
          showError('sellerFormError', (r.data && r.data.message) ? r.data.message : '요청에 실패했습니다.');
        }
      });
    });
  }

  if (resendBtn) {
    resendBtn.addEventListener('click', function () {
      resendBtn.disabled = true;
      postSendCode().then(function (r) {
        resendBtn.disabled = false;
        if (r.data && r.data.validationFailed) {
          return;
        }
        if (r.data && r.data.ok) {
          alert(r.data.message || '인증코드를 다시 보냈어요.');
        } else {
          showError('sellerFormError', (r.data && r.data.message) ? r.data.message : '요청에 실패했습니다.');
        }
      });
    });
  }

  if (form) {
    form.addEventListener('submit', function (e) {
      if (codeArea && codeArea.classList.contains('hidden')) {
        e.preventDefault();
        showError('sellerFormError', '먼저 인증코드를 받아주세요.');
        return;
      }

      var v1 = validateStep1();
      if (!v1.ok) { e.preventDefault(); return; }

      var v2 = validateCode();
      if (!v2.ok) { e.preventDefault(); return; }
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

  resetToStep1();
})();
