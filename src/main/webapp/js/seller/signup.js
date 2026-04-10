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
    if (!v || v.length < 8) return false;
    var hasLetter = /[a-zA-Z]/.test(v);
    var hasNumber = /\d/.test(v);
    var hasSpecial = /[^a-zA-Z0-9]/.test(v);
    return hasLetter && hasNumber && hasSpecial;
  }

  // 실제 주소 검색 API 함수 (daum 객체 사용)
  function openZipSearch(zipEl, addr1El, addr2El) {
    new daum.Postcode({
      oncomplete: function (data) {
        var fullAddr = data.userSelectedType === 'R' ? data.roadAddress : data.jibunAddress;
        zipEl.value = data.zonecode;
        addr1El.value = fullAddr;
        if (addr2El) addr2El.focus();

        // 값이 입력되었음을 알리는 이벤트 발생 (에러 메시지 삭제용)
        zipEl.dispatchEvent(new Event('input'));
        addr1El.dispatchEvent(new Event('input'));
      }
    }).open();
  }

  document.addEventListener("DOMContentLoaded", function () {
    var form = byId("sellerSignupForm");
    if (!form) return;

    var storeName = byId("storeName"), managerName = byId("managerName"), bizNo = byId("bizNo");
    var sellerId = byId("sellerId"), sellerPw = byId("sellerPw"), sellerPw2 = byId("sellerPw2");
    var phone = byId("phone"), email = byId("email");
    var shipZip = byId("shipZip"), shipAddr1 = byId("shipAddr1"), shipAddr2 = byId("shipAddr2");
    var returnZip = byId("returnZip"), returnAddr1 = byId("returnAddr1"), returnAddr2 = byId("returnAddr2");
    var sameReturnAddr = byId("sameReturnAddr");
    var signupBtn = byId("sellerSignupBtn");
	var bizType = byId("bizType");
	
	
    var termsAll = byId("termsAll");
    var termsItems = Array.prototype.slice.call(document.querySelectorAll(".terms-item"));
    var termsRequired = Array.prototype.slice.call(document.querySelectorAll(".terms-required"));
    var termsError = byId("termsError");

    // 우편번호 버튼 클릭 이벤트 연결 (딱 한 번만 실행되도록 수정)
    var shipZipBtn = byId("shipZipBtn");
    var returnZipBtn = byId("returnZipBtn");

    if (shipZipBtn) {
      shipZipBtn.onclick = function() {
        openZipSearch(shipZip, shipAddr1, shipAddr2);
      };
    }

    if (returnZipBtn) {
      returnZipBtn.onclick = function() {
        openZipSearch(returnZip, returnAddr1, returnAddr2);
      };
    }

	
	if (bizNo != null) {
	    bizNo.addEventListener("input", function () {
	      // 1. 숫자 이외의 문자 제거 및 10자리 제한
	      this.value = this.value.replace(/[^0-9]/g, '').slice(0, 10);
	      
	      // 2. 정확히 10자리가 채워졌을 때만 분석 실행
	      if (this.value.length === 10) {
	        // 인덱스 3번부터 2자리 추출 (가운데 2자리)
	        var midCode = parseInt(this.value.substring(3, 5), 10);
	        
	        // 국세청 기준: 가운데 숫자가 81~89이면 '법인사업자' (그 외는 개인)
	        if (midCode >= 81 && midCode <= 89) {
	          bizType.value = "2";
	        } else {
	          bizType.value = "1";
	        }
	      } else {
	        // 10자리가 안 되면 자동인식 칸을 다시 비움
	        bizType.value = "";
	      }
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
        var isSame = sameReturnAddr.checked;
        [returnZip, returnAddr1, returnAddr2].forEach(el => { if (el) el.disabled = isSame; });
        if (isSame) copyShipToReturn();
      });
    }

    [shipZip, shipAddr1, shipAddr2].forEach(el => {
      if (el) el.addEventListener("input", function () { if (sameReturnAddr && sameReturnAddr.checked) copyShipToReturn(); });
    });

    if (termsAll) {
      termsAll.addEventListener("change", function () {
        var on = termsAll.checked;
        termsItems.forEach(c => c.checked = on);
        showError(termsError, "");
      });
    }

    termsItems.forEach(c => c.addEventListener("change", function () {
      termsAll.checked = termsItems.every(item => item.checked);
      showError(termsError, "");
    }));

    function validate() {
      var ok = true;
      if (!storeName.value.trim()) { setFieldError(storeName, byId("storeNameError"), "상호명을 입력해 주세요."); ok = false; }
      if (!managerName.value.trim()) { setFieldError(managerName, byId("managerNameError"), "담당자명을 입력해 주세요."); ok = false; }
      if (!isValidBizNo(bizNo.value)) { setFieldError(bizNo, byId("bizNoError"), "사업자번호 10자리를 입력해 주세요."); ok = false; }
      if (!isValidSellerId(sellerId.value)) { setFieldError(sellerId, byId("sellerIdError"), "영문/숫자 4~20자로 입력해 주세요."); ok = false; }
      if (!isValidPassword(sellerPw.value)) { setFieldError(sellerPw, byId("sellerPwError"), "8자 이상 영문/숫자/특수문자를 포함해 주세요."); ok = false; }
      if (sellerPw.value !== sellerPw2.value) { setFieldError(sellerPw2, byId("sellerPw2Error"), "비밀번호가 일치하지 않습니다."); ok = false; }
      if (!phone.value.trim()) { setFieldError(phone, byId("phoneError"), "연락처를 입력해 주세요."); ok = false; }
      if (!isValidEmail(email.value)) { setFieldError(email, byId("emailError"), "올바른 이메일 형식이 아닙니다."); ok = false; }
      if (!shipZip.value.trim()) { setFieldError(shipZip, byId("shipZipError"), "출고지 주소를 검색해 주세요."); ok = false; }
      
      if (!(sameReturnAddr && sameReturnAddr.checked)) {
          if (!returnZip.value.trim()) { setFieldError(returnZip, byId("returnZipError"), "반품지 주소를 검색해 주세요."); ok = false; }
      }

      if (!termsRequired.every(c => c.checked)) { showError(termsError, "필수 약관에 동의해 주세요."); ok = false; }
      return ok;
    }

	form.addEventListener("submit", function (e) {
	      e.preventDefault();
	      if (!validate()) {
	        var first = form.querySelector(".error-border");
	        if (first) first.scrollIntoView({ behavior: "smooth", block: "center" });
	        return;
	      }
	      
	      // 💡 [추가된 핵심 코드] 
	      // disabled 된 폼은 서버로 전송되지 않으므로, 제출 직전에 강제로 활성화해줍니다.
	      [returnZip, returnAddr1, returnAddr2].forEach(function(el) { 
	        if (el) el.disabled = false; 
	      });

	      if (signupBtn) {
	        signupBtn.disabled = true;
	        signupBtn.textContent = "가입 처리 중...";
	      }
	      form.submit();
	    });

    form.querySelectorAll("input").forEach(el => {
      el.addEventListener("input", function () {
        el.classList.remove("error-border");
        var err = byId(el.id + "Error");
        if (err) err.classList.add("hidden");
      });
    });
  });
})();