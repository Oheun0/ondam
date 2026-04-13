document.addEventListener("DOMContentLoaded", function () {
	const markAllReadBtn = document.getElementById("markAllReadBtn");
	const deleteAllBtn = document.getElementById("deleteAllBtn");
	const notificationList = document.querySelector(".notification-list");
	const notificationEmpty = document.querySelector(".notification-empty");
	const contextPath = document.body.dataset.contextPath || "";

	function updateEmptyState() {
		const items = document.querySelectorAll(".notification-item");
		if (items.length === 0) {
			if (notificationList) notificationList.style.display = "none";
			if (notificationEmpty) notificationEmpty.style.display = "block";
		} else {
			if (notificationList) notificationList.style.display = "flex";
			if (notificationEmpty) notificationEmpty.style.display = "none";
		}
	}

	if (markAllReadBtn) {
		markAllReadBtn.addEventListener("click", function () {
			const unreadItems = document.querySelectorAll(".notification-item.unread");
			unreadItems.forEach(function (item) {
				item.classList.remove("unread");
			});
		});
	}

	if (deleteAllBtn) {
		deleteAllBtn.addEventListener("click", function () {
			const ok = confirm("알림을 모두 삭제할까요?");
			if (!ok) return;

			if (notificationList) {
				notificationList.innerHTML = "";
			}

			updateEmptyState();
		});
	}

	const items = document.querySelectorAll(".notification-item");
	items.forEach(function (item) {
	    item.addEventListener("click", function (e) {
	        e.preventDefault();
	        const no = this.dataset.no;
	        
	        // UI 업데이트
	        this.classList.remove("unread");
	        const dot = this.querySelector(".notification-dot");
	        if (dot) dot.remove();

	        // DB 반영 + 응답 처리
	        fetch(contextPath + "/notification?action=markOneRead&notificationNo=" + no, {
	            method: "POST"
	        })
	        .then(res => res.json())
			.then(data => {
			    switch (data.notificationType) {
			        case 0: // 가족 관련
			            location.href = contextPath + "/group";
			            break;
			        case 1: // 조르기
			            location.href = contextPath + "/poke?action=list&pokeNo=" + data.refNo;
			            break;
			        case 2: // 주문/배송
						location.href = contextPath + "/order/order-detail?orderNo=" + data.refNo;
						break;
			        case 3: // 쿠폰
					case 4: // 배송지 수정
				        location.href = contextPath + "/profile-address";
					        break;
					case 5: // 함께 지갑
						location.href = contextPath + "/wallet";
						break;
					case 6: // 선물
						location.href = contextPath + "/gift";
					    break;
					case 7: // 기타
			        default:
			            location.reload();
			            break;
			    }
			});
	    });
	});
	/*items.forEach(function (item) {
		item.addEventListener("click", function () {
			this.classList.remove("unread");
		});
	});*/

	updateEmptyState();
});