let isIdChecked = false;
document.addEventListener("DOMContentLoaded", function () {
  bindSelectableCards();
  bindUserIdInputReset();
  bindErrorReset();
  bindLoadPrevInfo();
});

function validate() {
	const checkList = [
	  { id: "userName", errId: "err-userName", msg: "이름을 입력해주세요." },
	  { id: "userNick", errId: "err-userNick", msg: "사용하실 닉네임을 입력해주세요." },
	  { id: "userId", errId: "err-userId", msg: "아이디를 입력해주세요." },
	  { id: "userPwd", errId: "err-userPwd", msg: "비밀번호를 입력해주세요." },
	  { id: "userPwdCheck", errId: "err-userPwdCheck", msg: "비밀번호를 한 번 더 입력해주세요." },
	  { id: "phone2", errId: "err-phone", msg: "휴대폰 번호를 모두 입력해주세요." },
	  { id: "phone3", errId: "err-phone", msg: "휴대폰 번호를 모두 입력해주세요." },
	  { id: "email1", errId: "err-email", msg: "이메일 주소를 입력해주세요." },
	  { id: "emailSelect", errId: "err-email", msg: "이메일 도메인을 선택해주세요." }, // emailSelect로 맞춤
	  { id: "birthYear", errId: "err-birth", msg: "생년월일을 모두 선택해주세요." },
	  { id: "birthMonth", errId: "err-birth", msg: "생년월일을 모두 선택해주세요." },
	  { id: "birthDay", errId: "err-birth", msg: "생년월일을 모두 선택해주세요." },
	  { id: "addressName", errId: "err-addressName", msg: "배송지 이름을 입력해주세요." },
      { id: "receiverName", errId: "err-receiverName", msg: "받는 분 이름을 입력해주세요." },
      { id: "userZipcode", errId: "err-userZipcode", msg: "우편번호를 검색해주세요." },
      { id: "userAddress", errId: "err-userAddress", msg: "주소를 입력해주세요." },
      { id: "userDetailAddress", errId: "err-userAddress", msg: "상세 주소를 입력해주세요." },
	  { id: "userHeight", errId: "err-userHeight", msg: "키를 선택해주세요." },
	  { id: "userWeight", errId: "err-userWeight", msg: "몸무게를 선택해주세요." }
	];

  for (let item of checkList) {
    const target = document.getElementById(item.id);
    const errMsg = document.getElementById(item.errId);

    if (!target) continue;

    if (target.value.trim() === "" || target.value === "년도" || target.value === "월" || target.value === "일") {

      hideAllErrors();

      if (errMsg) {
        errMsg.textContent = item.msg;
        errMsg.style.display = "block";
      }
      target.classList.add("error-border");

      target.scrollIntoView({ behavior: "smooth", block: "center" });
      setTimeout(() => { target.focus(); }, 500);

      return false;
    }
  }
  
    const userIdInput = document.getElementById("userId");
	const idCheckMsg = document.getElementById("idCheckMessage");

	if (userIdInput && idCheckMsg && !isIdChecked) {
	      hideAllErrors();
	      const errMsg = document.getElementById("err-userId");
	      if (errMsg) {
	        errMsg.textContent = "아이디 중복 확인을 진행해주세요.";
	        errMsg.style.display = "block";
	      }
	      userIdInput.classList.add("error-border");
	      userIdInput.scrollIntoView({ behavior: "smooth", block: "center" });
	      setTimeout(() => { userIdInput.focus(); }, 500);
	      return false;
	  }
  
  const pwd = document.getElementById("userPwd").value;
    const pwdCheck = document.getElementById("userPwdCheck").value;

    if (pwd !== pwdCheck) {
      hideAllErrors();
      const errMsg = document.getElementById("err-userPwdCheck");
      if (errMsg) {
        errMsg.textContent = "비밀번호가 일치하지 않습니다.";
        errMsg.style.display = "block";
      }
      const pwdCheckInput = document.getElementById("userPwdCheck");
      pwdCheckInput.classList.add("error-border");
      pwdCheckInput.scrollIntoView({ behavior: "smooth", block: "center" });
      setTimeout(() => { pwdCheckInput.focus(); }, 500);
      return false;
    }
	
	const paymentRadios = document.getElementsByName("preferPayment");
	  if (paymentRadios.length > 0) {
	    let isSelected = false;
	    for (let radio of paymentRadios) {
	      if (radio.checked) {
	        isSelected = true;
	        break;
	      }
	    }
	    
	    if (!isSelected) {
	      hideAllErrors();
	      const errMsg = document.getElementById("err-preferPayment");
	      if (errMsg) {
	        errMsg.textContent = "결제 수단을 선택해주세요.";
	        errMsg.style.display = "block";
	      }
	      const paymentGrid = document.getElementById("paymentGrid");
	      if (paymentGrid) {
	        paymentGrid.scrollIntoView({ behavior: "smooth", block: "center" });
	      }
	      return false;
	    }
	  }

	  return true;
	}

function hideAllErrors() {
  document.querySelectorAll(".error-msg").forEach(el => el.style.display = "none");
  document.querySelectorAll(".input, .select").forEach(el => el.classList.remove("error-border"));
}

function bindErrorReset() {
  const inputs = document.querySelectorAll(".input, .select");
  inputs.forEach(input => {
    input.addEventListener("input", function() {
      this.classList.remove("error-border");
      const group = this.closest(".form-group");
      if (group) {
        const msg = group.querySelector(".error-msg");
        if (msg) msg.style.display = "none";
      }
    });
  });
}

function bindSelectableCards() {
  var optionCards = document.querySelectorAll(".option-card");

  optionCards.forEach(function (card) {
    card.addEventListener("click", function () {
      var input = card.querySelector("input");
      if (!input) return;

      if (input.type === "radio") {
        var groupName = input.name;

        document.querySelectorAll('input[name="' + groupName + '"]').forEach(function (radio) {
          var parentCard = radio.closest(".option-card");
          if (parentCard) {
            parentCard.classList.remove("active");
          }
        });

        input.checked = true;
        card.classList.add("active");
      } else if (input.type === "checkbox") {
        input.checked = !input.checked;

        if (input.checked) {
          card.classList.add("active");
        } else {
          card.classList.remove("active");
        }
      }
    });
  });
}

function checkUserId() {
  var userIdInput = document.getElementById("userId");
  var msg = document.getElementById("idCheckMessage");
  var errMsg = document.getElementById("err-userId");

  if (!userIdInput || !msg) return;
  
  userIdInput.classList.remove("error-border");
    if (errMsg) {
      errMsg.style.display = "none";
    }

  var userId = userIdInput.value.trim();

  msg.style.display = "block";
  msg.classList.remove("success", "error");

  if (userId === "") {
    msg.classList.add("error");
    msg.textContent = "아이디를 먼저 입력해주세요.";
    return;
  }

  if (userId.length < 4) {
    msg.classList.add("error");
    msg.textContent = "아이디는 4자 이상 입력해주세요.";
    return;
  }

  fetch(ctxPath + "/check-userid?userId=" + userId)
        .then(response => {
          if (!response.ok) {
            throw new Error("서버 응답 에러");
          }
          return response.text();
        })
		.then(data => {
		    if (data.trim() === "duplicate") {
		        msg.classList.add("error");
		        msg.textContent = "이미 사용 중인 아이디예요.";
		        isIdChecked = false;
		    } else {
		        msg.classList.add("success");
		        msg.textContent = "사용할 수 있는 아이디예요.";
		        isIdChecked = true;
		    }
		})
        .catch(error => {
          console.error("중복확인 통신 실패:", error);
          msg.classList.add("error");
          msg.textContent = "서버 통신 오류가 발생했습니다.";
        });
        
  }

function bindUserIdInputReset() {
  var userIdInput = document.getElementById("userId");
  
  var msg = document.getElementById("idCheckMessage");

  if (!userIdInput || !msg) return;

  userIdInput.addEventListener("input", function () {
	isIdChecked = false;
    msg.style.display = "none";
    msg.textContent = "";
    msg.classList.remove("success", "error");
  });
}

function openPostcode() {
  new daum.Postcode({
    oncomplete: function(data) {
      var addr = ''; 

      if (data.userSelectedType === 'R') {
        addr = data.roadAddress;
      } else {
        addr = data.jibunAddress;
      }

      document.getElementById('userZipcode').value = data.zonecode;
      document.getElementById("userAddress").value = addr;

      document.getElementById('userZipcode').classList.remove("error-border");
      document.getElementById('userAddress').classList.remove("error-border");
      const errAddr = document.getElementById('err-userAddress');
      const errZip = document.getElementById('err-userZipcode');
      if (errAddr) errAddr.style.display = "none";
      if (errZip) errZip.style.display = "none";

      document.getElementById("userDetailAddress").focus();
    }
  }).open();
}

//이전 정보 불러오기
function bindLoadPrevInfo() {
  const loadPrevCheckbox = document.querySelector("input[name='loadPrevInfo']");
  const receiverNameInput = document.getElementById("receiverName");
  const phone1Select = document.getElementById("phone1");
  const phone2Input = document.getElementById("phone2");
  const phone3Input = document.getElementById("phone3");

  if (!loadPrevCheckbox || !receiverNameInput) return; 
  function applyPrevInfo() {
    if (typeof prevUserInfo === 'undefined') return;

    if (loadPrevCheckbox.checked) {
      receiverNameInput.value = prevUserInfo.name;
      phone1Select.value = prevUserInfo.phone1;
      phone2Input.value = prevUserInfo.phone2;
      phone3Input.value = prevUserInfo.phone3;
      receiverNameInput.classList.remove("error-border");
      phone2Input.classList.remove("error-border");
      phone3Input.classList.remove("error-border");
    } else {
      receiverNameInput.value = "";
      phone1Select.value = "010";
      phone2Input.value = "";
      phone3Input.value = "";
    }
  }
  loadPrevCheckbox.addEventListener("change", applyPrevInfo);
  applyPrevInfo();
}

document.addEventListener("DOMContentLoaded", function() {
	const ctxPath = document.body.dataset.contextPath || "";
    const urlParams = new URLSearchParams(window.location.search);
    const modal = document.getElementById('reactivateModal');

	if (!modal) return;

    if (urlParams.get('status') === 'withdrawn') {
        modal.classList.remove('hidden');
    }

    function closeModal() {
        modal.classList.add('hidden');
        window.history.replaceState({}, document.title, window.location.pathname);
    }
    
	const btnCancel = document.getElementById('btnCancelReactivate');
	    const btnDim = document.getElementById('btnCancelDim');
	    const btnReactivate = document.getElementById('btnReactivate');

	    if (btnCancel) btnCancel.addEventListener('click', closeModal);
	    if (btnDim) btnDim.addEventListener('click', closeModal);

	    if (btnReactivate) {
	        btnReactivate.addEventListener('click', function() {
	            const targetId = urlParams.get('targetId');
	            if(targetId) {
	               location.href = ctxPath + "/login?action=reactivate&userId=" + targetId;
      	  }
 	   });
	}
});