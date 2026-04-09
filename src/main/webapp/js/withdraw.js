document.addEventListener("DOMContentLoaded", function() {
    const pwdInput = document.getElementById('withdrawPwd');
    const errorMsg = document.getElementById('withdrawErrorMsg');
    const modal = document.getElementById('withdrawModal');
    const cancelBtn = document.getElementById('withdrawCancelBtn');
    const submitBtn = document.getElementById('withdrawSubmitBtn');

    if (pwdInput) {
        pwdInput.addEventListener('input', function() {
            if (errorMsg) {
                errorMsg.style.display = 'none';
                errorMsg.innerText = '';
            }
            pwdInput.classList.remove('error-border');
        });
    }

    if (modal) {
        modal.querySelectorAll("[data-withdraw-dismiss]").forEach(function (dim) {
            dim.addEventListener("click", function () {
                closeWithdraw();
            });
        });
    }

    if (cancelBtn) {
        cancelBtn.addEventListener("click", function () {
            closeWithdraw();
        });
    }

    if (submitBtn) {
        submitBtn.addEventListener("click", function () {
            submitWithdraw();
        });
    }
});

function openWithdraw() {
    const modal = document.getElementById('withdrawModal');
    if (modal) {
        modal.classList.remove('hidden');
        modal.setAttribute('aria-hidden', 'false');
    }
    
    const pwdInput = document.getElementById('withdrawPwd');
    const errorMsg = document.getElementById('withdrawErrorMsg');

    if (pwdInput) {
        pwdInput.value = '';
        pwdInput.classList.remove('error-border');
    }
    if (errorMsg) {
        errorMsg.innerText = '';
        errorMsg.style.display = 'none';
    }
}

function closeWithdraw() {
    const modal = document.getElementById('withdrawModal');
    if (modal) {
        modal.classList.add('hidden');
        modal.setAttribute('aria-hidden', 'true');
    }
}

function submitWithdraw() {
    const pwdInput = document.getElementById('withdrawPwd');
    const errorMsg = document.getElementById('withdrawErrorMsg');
    const form = document.getElementById('withdrawForm');

    if (pwdInput) {
        const pwd = pwdInput.value;
        if (pwd.trim() === '') {
            errorMsg.innerText = '비밀번호를 입력해 주세요.';
            errorMsg.style.display = 'block';
            pwdInput.classList.add('error-border');
            pwdInput.focus();
            return;
        }
    }
    
    const formData = new URLSearchParams(new FormData(form));

    fetch(form.action, {
        method: 'POST',
        body: formData,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    })
    .then(response => {
        if (response.redirected) {
            const finalUrl = response.url;
            
            if (finalUrl.includes('error=pwd_mismatch')) {
                // 💡 비밀번호가 틀렸을 때: 모달은 그대로 두고 에러만 띄움!
                if (errorMsg) {
                    errorMsg.innerText = '비밀번호가 일치하지 않습니다.';
                    errorMsg.style.display = 'block';
                }
                if (pwdInput) {
                    pwdInput.classList.add('error-border'); // 배경과 테두리 붉게 변경
                    pwdInput.focus();
                    pwdInput.value = ''; // 틀렸으니 다시 치라고 비워줌
                }
            } else if (finalUrl.includes('withdraw_success')) {
                // 탈퇴 성공 시 메인 페이지로 이동
                window.location.href = finalUrl;
            } else {
                // 기타 다른 경로로 이동될 때
                window.location.href = finalUrl;
            }
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('처리 중 통신 오류가 발생했습니다.');
    });
}