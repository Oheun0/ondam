// === address-form.js ===

// 1. 다음 우편번호 API
function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            document.getElementById('userZipcode').value = data.zonecode;
            document.getElementById('userAddress').value = data.address;
            
            // 우편번호 입력 완료 시 에러 테두리 제거
            document.getElementById('userZipcode').classList.remove("error-border");
            document.getElementById('userAddress').classList.remove("error-border");
            const errZip = document.getElementById("err-userZipcode");
            if (errZip) errZip.style.display = "none";

            document.getElementById('userDetailAddress').focus();
        }
    }).open();
}

// 2. 폼 제출 전 유효성 검사 (빈 칸 확인 및 스크롤)
function validateForm() {
    const checkList = [
        { id: "addressName", errId: "err-addressName", msg: "배송지 이름을 입력해 주세요." },
        { id: "receiverName", errId: "err-receiverName", msg: "받는 분 이름을 입력해 주세요." },
        { id: "receiverTel", errId: "err-receiverTel", msg: "연락처를 입력해 주세요." },
        { id: "userZipcode", errId: "err-userZipcode", msg: "우편번호 조회를 통해 주소를 입력해 주세요." },
        { id: "userDetailAddress", errId: "err-userDetailAddress", msg: "상세 주소를 입력해 주세요." }
    ];

    // 검사 전 기존 에러 모두 숨기기
    hideAllErrors();

    for (let item of checkList) {
        const target = document.getElementById(item.id);
        const errMsg = document.getElementById(item.errId);

        if (!target) continue;

        if (target.value.trim() === "") {
            // 에러 메시지 띄우기
            if (errMsg) {
                errMsg.textContent = item.msg;
                errMsg.style.display = "block";
            }
            // 입력창 빨간 테두리 추가
            target.classList.add("error-border");

            // 해당 요소로 스르륵 스크롤 올리기
            target.scrollIntoView({ behavior: "smooth", block: "center" });

            // 스크롤 이동 후 커서 깜빡이게 포커스
            setTimeout(() => { target.focus(); }, 400);

            return false; // 폼 제출 중단
        }
    }
    return true; 
}

// 3. 모든 에러 표시 초기화 함수
function hideAllErrors() {
    document.querySelectorAll(".error-msg").forEach(el => el.style.display = "none");
    document.querySelectorAll(".input-box").forEach(el => el.classList.remove("error-border"));
}

// 버튼 클릭 시 실행되는 강제 제출 함수
function executeSubmit() {
    // validateForm()이 true(빈 칸 없음)일 때만 진짜로 서버에 전송!
    if (validateForm()) {
        document.getElementById("addressForm").submit();
    }
}

// 5. 페이지가 로드되면 실행할 기본 설정 (빨간 테두리 없애기 등)
document.addEventListener("DOMContentLoaded", function() {
    // 사용자가 다시 입력을 시작하면 빨간 테두리 없애기
    document.querySelectorAll(".input-box").forEach(input => {
        input.addEventListener("input", function() {
            this.classList.remove("error-border");
            const group = this.closest(".form-block");
            if (group) {
                const msg = group.querySelector(".error-msg");
                if (msg) msg.style.display = "none";
            }
        });
    });
});