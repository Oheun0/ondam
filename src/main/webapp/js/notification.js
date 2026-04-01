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
	        this.classList.remove("unread");
	        const dot = this.querySelector(".notification-dot");
	        if (dot) dot.remove();

	        // DB 반영
	        fetch(contextPath + "/notification?action=markOneRead&notificationNo=" + no, {
	            method: "POST"
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