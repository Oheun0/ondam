/* 온담 판매자센터 회원가입 (프론트 검증 전용, 서버 연동 없음) */
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
    if (inputEl) inputEl.classList.toggle("error-border", !!msg);
    showError(errorEl, msg);
  }

  function onlyDigits(s) {
    return (s || "").replace(/[^\d]/g, "");
  }

  function isValidBizNo(v) {
    return /^\d{10}$/.test(onlyDigits(v));
  }

  function isValidSellerId(v) {
    return /^[a-zA-Z0-9]{4,20}$/.test(v || "");
  }

  function isValidEmail(v) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(v || "");
  }

  function isValidPassword(v) {
    // 8자 이상 + 영문/숫자/특수문자 포함
    if (!v || v.length < 8) return false;
    var hasLetter = /[a-zA-Z]/.test(v);
    var hasNumber = /\d/.test(v);
    var hasSpecial = /[^a-zA-Z0-9]/.test(v);
    return hasLetter && hasNumber && hasSpecial;
  }

  document.addEventListener("DOMContentLoaded", function () {
    var form = byId("sellerSignupForm");
    if (!form) return;

    var storeName = byId("storeName");
    var managerName = byId("managerName");
    var bizNo = byId("bizNo");
    var sellerId = byId("sellerId");
    var sellerPw = byId("sellerPw");
    var sellerPw2 = byId("sellerPw2");
    var phone = byId("phone");
    var email = byId("email");

    var shipZip = byId("shipZip");
    var shipAddr1 = byId("shipAddr1");
    var shipAddr2 = byId("shipAddr2");
    var returnZip = byId("returnZip");
    var returnAddr1 = byId("returnAddr1");
    var returnAddr2 = byId("returnAddr2");

    var sameReturnAddr = byId("sameReturnAddr");

    var termsAll = byId("termsAll");
    var termsItems = Array.prototype.slice.call(document.querySelectorAll(".terms-item"));
    var termsRequired = Array.prototype.slice.call(document.querySelectorAll(".terms-required"));

    var storeNameError = byId("storeNameError");
    var managerNameError = byId("managerNameError");
    var bizNoError = byId("bizNoError");
    var sellerIdError = byId("sellerIdError");
    var sellerPwError = byId("sellerPwError");
    var sellerPw2Error = byId("sellerPw2Error");
    var phoneError = byId("phoneError");
    var emailError = byId("emailError");
    var shipZipError = byId("shipZipError");
    var shipAddr1Error = byId("shipAddr1Error");
    var shipAddr2Error = byId("shipAddr2Error");
    var returnZipError = byId("returnZipError");
    var returnAddr1Error = byId("returnAddr1Error");
    var returnAddr2Error = byId("returnAddr2Error");
    var termsError = byId("termsError");

    function syncReturnDisabled(disabled) {
      [returnZip, returnAddr1, returnAddr2].forEach(function (el) {
        if (!el) return;
        el.disabled = disabled;
        el.setAttribute("aria-disabled", disabled ? "true" : "false");
      });
    }

    function copyShipToReturn() {
      if (!shipZip || !shipAddr1 || !shipAddr2) return;
      if (returnZip) returnZip.value = shipZip.value;
      if (returnAddr1) returnAddr1.value = shipAddr1.value;
      if (returnAddr2) returnAddr2.value = shipAddr2.value;
    }

    if (sameReturnAddr) {
      sameReturnAddr.addEventListener("change", function () {
        var on = !!sameReturnAddr.checked;
        if (on) {
          copyShipToReturn();
          syncReturnDisabled(true);
          setFieldError(returnZip, returnZipError, "");
          setFieldError(returnAddr1, returnAddr1Error, "");
          setFieldError(returnAddr2, returnAddr2Error, "");
        } else {
          syncReturnDisabled(false);
        }
      });
    }

    [shipZip, shipAddr1, shipAddr2].forEach(function (el) {
      if (!el) return;
      el.addEventListener("input", function () {
        if (sameReturnAddr && sameReturnAddr.checked) {
          copyShipToReturn();
        }
      });
    });

    function syncTermsAllFromItems() {
      if (!termsAll) return;
      var allChecked = termsItems.length > 0 && termsItems.every(function (c) { return c.checked; });
      termsAll.checked = allChecked;
    }

    function syncItemsFromTermsAll() {
      if (!termsAll) return;
      var on = termsAll.checked;
      termsItems.forEach(function (c) { c.checked = on; });
    }

    if (termsAll) {
      termsAll.addEventListener("change", function () {
        syncItemsFromTermsAll();
        showError(termsError, "");
      });
    }

    termsItems.forEach(function (c) {
      c.addEventListener("change", function () {
        syncTermsAllFromItems();
        showError(termsError, "");
      });
    });

    function validateRequiredText(inputEl, errorEl, msg) {
      var v = (inputEl && inputEl.value || "").trim();
      if (!v) {
        setFieldError(inputEl, errorEl, msg);
        return false;
      }
      setFieldError(inputEl, errorEl, "");
      return true;
    }

    function validate() {
      var ok = true;

      ok = validateRequiredText(storeName, storeNameError, "상호명을 입력해 주세요.") && ok;
      ok = validateRequiredText(managerName, managerNameError, "담당자명을 입력해 주세요.") && ok;

      var bizVal = (bizNo && bizNo.value || "").trim();
      if (!bizVal) {
        setFieldError(bizNo, bizNoError, "사업자등록번호를 입력해 주세요.");
        ok = false;
      } else if (!isValidBizNo(bizVal)) {
        setFieldError(bizNo, bizNoError, "숫자 10자리로 입력해 주세요.");
        ok = false;
      } else {
        setFieldError(bizNo, bizNoError, "");
      }

      var idVal = (sellerId && sellerId.value || "").trim();
      if (!idVal) {
        setFieldError(sellerId, sellerIdError, "아이디를 입력해 주세요.");
        ok = false;
      } else if (!isValidSellerId(idVal)) {
        setFieldError(sellerId, sellerIdError, "아이디는 영문/숫자 4~20자로 입력해 주세요.");
        ok = false;
      } else if (idVal.toLowerCase() === "admin") {
        setFieldError(sellerId, sellerIdError, "이미 사용 중인 아이디입니다.");
        ok = false;
      } else {
        setFieldError(sellerId, sellerIdError, "");
      }

      var pwVal = (sellerPw && sellerPw.value || "");
      if (!pwVal.trim()) {
        setFieldError(sellerPw, sellerPwError, "비밀번호를 입력해 주세요.");
        ok = false;
      } else if (!isValidPassword(pwVal)) {
        setFieldError(sellerPw, sellerPwError, "비밀번호는 8자 이상, 영문/숫자/특수문자를 포함해 주세요.");
        ok = false;
      } else {
        setFieldError(sellerPw, sellerPwError, "");
      }

      var pw2Val = (sellerPw2 && sellerPw2.value || "");
      if (!pw2Val.trim()) {
        setFieldError(sellerPw2, sellerPw2Error, "비밀번호를 다시 입력해 주세요.");
        ok = false;
      } else if (pwVal && pw2Val && pwVal !== pw2Val) {
        setFieldError(sellerPw2, sellerPw2Error, "비밀번호가 일치하지 않습니다.");
        ok = false;
      } else {
        setFieldError(sellerPw2, sellerPw2Error, "");
      }

      ok = validateRequiredText(phone, phoneError, "연락처를 입력해 주세요.") && ok;

      var emailVal = (email && email.value || "").trim();
      if (!emailVal) {
        setFieldError(email, emailError, "이메일을 입력해 주세요.");
        ok = false;
      } else if (!isValidEmail(emailVal)) {
        setFieldError(email, emailError, "이메일 형식을 확인해 주세요.");
        ok = false;
      } else if (emailVal.toLowerCase() === "test@ondam.com") {
        setFieldError(email, emailError, "이미 등록된 이메일입니다.");
        ok = false;
      } else {
        setFieldError(email, emailError, "");
      }

      ok = validateRequiredText(shipZip, shipZipError, "출고지 우편번호를 입력해 주세요.") && ok;
      ok = validateRequiredText(shipAddr1, shipAddr1Error, "출고지 주소를 입력해 주세요.") && ok;
      ok = validateRequiredText(shipAddr2, shipAddr2Error, "출고지 상세주소를 입력해 주세요.") && ok;

      var returnDisabled = sameReturnAddr && sameReturnAddr.checked;
      if (!returnDisabled) {
        ok = validateRequiredText(returnZip, returnZipError, "반품지 우편번호를 입력해 주세요.") && ok;
        ok = validateRequiredText(returnAddr1, returnAddr1Error, "반품지 주소를 입력해 주세요.") && ok;
        ok = validateRequiredText(returnAddr2, returnAddr2Error, "반품지 상세주소를 입력해 주세요.") && ok;
      } else {
        setFieldError(returnZip, returnZipError, "");
        setFieldError(returnAddr1, returnAddr1Error, "");
        setFieldError(returnAddr2, returnAddr2Error, "");
      }

      var requiredOk = termsRequired.every(function (c) { return c.checked; });
      if (!requiredOk) {
        showError(termsError, "필수 약관에 동의해 주세요.");
        ok = false;
      } else {
        showError(termsError, "");
      }

      return ok;
    }

    // 입력 시 즉시 에러 해제 (간단)
    [
      [storeName, storeNameError],
      [managerName, managerNameError],
      [bizNo, bizNoError],
      [sellerId, sellerIdError],
      [sellerPw, sellerPwError],
      [sellerPw2, sellerPw2Error],
      [phone, phoneError],
      [email, emailError],
      [shipZip, shipZipError],
      [shipAddr1, shipAddr1Error],
      [shipAddr2, shipAddr2Error],
      [returnZip, returnZipError],
      [returnAddr1, returnAddr1Error],
      [returnAddr2, returnAddr2Error]
    ].forEach(function (pair) {
      var inputEl = pair[0];
      var errorEl = pair[1];
      if (!inputEl) return;
      inputEl.addEventListener("input", function () {
        setFieldError(inputEl, errorEl, "");
        if (inputEl === sellerPw2 && sellerPw && sellerPw.value !== sellerPw2.value) {
          // 입력 중엔 메시지 없이, 제출에서만 강하게
          return;
        }
      });
    });

    // 숫자 입력 정리
    if (bizNo) {
      bizNo.addEventListener("input", function () {
        bizNo.value = onlyDigits(bizNo.value).slice(0, 10);
      });
    }

    // 우편번호 버튼은 더미
    var shipZipBtn = byId("shipZipBtn");
    var returnZipBtn = byId("returnZipBtn");
    function dummyZipSearch(target) {
      if (!target) return;
      alert("우편번호 찾기(더미)입니다. 추후 주소 API를 연결하세요.");
      target.focus();
    }
    if (shipZipBtn) shipZipBtn.addEventListener("click", function () { dummyZipSearch(shipZip); });
    if (returnZipBtn) returnZipBtn.addEventListener("click", function () { dummyZipSearch(returnZip); });

    form.addEventListener("submit", function (e) {
      e.preventDefault();
      var ok = validate();
      if (!ok) {
        // 첫 에러로 스크롤
        var first = form.querySelector(".error-border");
        if (first && typeof first.scrollIntoView === "function") {
          first.scrollIntoView({ behavior: "smooth", block: "center" });
          first.focus();
        }
        return;
      }

      console.log("[SELLER SIGNUP] dummy payload", {
        storeName: storeName && storeName.value,
        managerName: managerName && managerName.value,
        bizNo: bizNo && bizNo.value,
        sellerId: sellerId && sellerId.value,
        phone: phone && phone.value,
        email: email && email.value
      });

      alert("더미 회원가입 검증 통과(서버 연동 전)입니다.");
    });
  });
})();

