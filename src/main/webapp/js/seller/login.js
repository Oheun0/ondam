/*login.js*/
/* 온담 파트너 로그인 (실제 서버 연동 버전) */
(function () {
  "use strict";

  function byId(id) {
    return document.getElementById(id);
  }

  function showError(el, msg) {
    if (!el) return;
    el.textContent = msg || "";
    el.classList.toggle("hidden", !msg);
  }

  function setFieldError(inputEl, errorEl, msg) {
    if (inputEl) {
      inputEl.classList.toggle("error-border", !!msg);
    }
    showError(errorEl, msg);
  }

  document.addEventListener("DOMContentLoaded", function () {
    var form = byId("sellerLoginForm");
    var idInput = byId("sellerId");
    var pwInput = byId("sellerPw");
    var idErr = byId("sellerIdError");
    var pwErr = byId("sellerPwError");
    var formErr = byId("sellerFormError");
    var loginBtn = byId("sellerLoginBtn");

    if (!form || !idInput || !pwInput) return;

    function clearFormError() {
      showError(formErr, "");
	  if (formErr) {
	      formErr.style.display = ""; // JSP에서 넣은 인라인 스타일 강제 삭제
	    }
    }

    idInput.addEventListener("input", function () {
      if (idInput.value.trim().length > 0) {
        setFieldError(idInput, idErr, "");
      }
    });

    pwInput.addEventListener("input", function () {
      if (pwInput.value.trim().length > 0) {
        setFieldError(pwInput, pwErr, "");
      }
    });

    form.addEventListener("submit", function (e) {
      // 1. 일단 기본 전송을 막고 빈칸 검사부터 시작
      e.preventDefault();
      clearFormError();

      var idVal = idInput.value.trim();
      var pwVal = pwInput.value.trim();

      var ok = true;
      if (!idVal) {
        setFieldError(idInput, idErr, "아이디를 입력해 주세요");
        ok = false;
      } else {
        setFieldError(idInput, idErr, "");
      }

      if (!pwVal) {
        setFieldError(pwInput, pwErr, "비밀번호를 입력해 주세요");
        ok = false;
      } else {
        setFieldError(pwInput, pwErr, "");
      }

      // 2. 검사 실패 시 여기서 중단
      if (!ok) return;

      // 3. 검사 통과 시: 중복 클릭을 막고 진짜 서버로 폼 전송!
      if (loginBtn) {
        loginBtn.disabled = true;
        loginBtn.textContent = "로그인 중...";
      }
      
      // 진짜 컨트롤러로 데이터를 날립니다.
      form.submit(); 
    });
  });
})();