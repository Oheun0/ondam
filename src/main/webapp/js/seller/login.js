/* 온담 파트너 로그인 (더미 검증용) */
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
    }

    idInput.addEventListener("input", function () {
      if (idInput.value.trim().length > 0) {
        setFieldError(idInput, idErr, "");
      }
      clearFormError();
    });

    pwInput.addEventListener("input", function () {
      if (pwInput.value.trim().length > 0) {
        setFieldError(pwInput, pwErr, "");
      }
      clearFormError();
    });

    form.addEventListener("submit", function (e) {
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

      if (!ok) return;

      // 더미 인증 흐름 (실서버 연동 전)
      if (loginBtn) {
        loginBtn.disabled = true;
        var prev = loginBtn.textContent;
        loginBtn.textContent = "확인 중...";

        setTimeout(function () {
          // 예시: 특정 조합만 성공처럼 처리하고 나머지는 실패 메시지
          var success = idVal === "seller" && pwVal === "1234";

          if (success) {
            console.log("[SELLER LOGIN] dummy success", { sellerId: idVal });
            showError(formErr, "");
            alert("더미 로그인 성공(서버 연동 전)입니다.");
          } else {
            console.log("[SELLER LOGIN] dummy fail", { sellerId: idVal });
            showError(formErr, "아이디 또는 비밀번호가 올바르지 않습니다");
          }

          loginBtn.disabled = false;
          loginBtn.textContent = prev;
        }, 650);
      }
    });
  });
})();

