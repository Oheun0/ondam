<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header class="fixed-area">
    <div class="top-bar">
        <div class="top-left">
            <button type="button" class="icon-btn" id="headerCategoryMenu" aria-label="카테고리 메뉴 열기">
                <span class="material-icons">menu</span>
            </button>
        </div>

        <a href="${pageContext.request.contextPath}/main" class="logo-box" aria-label="온담 홈으로 이동">

            <img src="${pageContext.request.contextPath}/images/logo/logo_1.svg" alt="온담 로고" class="logo-img">
        </a>

        <div class="top-right">
			<a href="${pageContext.request.contextPath}/notification"
				class="icon-btn badge-wrap" aria-label="알림 보기"> <span
				class="material-icons-outlined">notifications</span> <c:choose>
					<c:when test="${sessionScope.unreadCount >= 99}">
						<span class="badge">99+</span>
					</c:when>
					<c:when test="${sessionScope.unreadCount > 0}">
						<span class="badge">${sessionScope.unreadCount}</span>
					</c:when>
					<%-- 0이면 badge 자체 미출력 --%>
				</c:choose>
			</a> <a href="${pageContext.request.contextPath}/cart" class="icon-btn badge-wrap" aria-label="장바구니 보기">
                <span class="material-icons-outlined">shopping_cart</span>
                <c:if test="${sessionScope.cartCount > 0}">
                    <span class="badge">${sessionScope.cartCount}</span>
                </c:if>
            </a>
        </div>
    </div>
</header>

<script> //즉시 변경한 갯수 반영되도록 비동기 스크립트 적용
function updateCartBadgeSilently() {
    // 💡 1. JSP 태그를 직접 써서 어느 페이지에서든 경로를 확실하게 잡습니다.
    var ctx = "${pageContext.request.contextPath}";
    
    // 💡 2. 브라우저 캐시 방지용 난수(현재 시간) 생성
    var timestamp = new Date().getTime();
    
    // 💡 3. 캐시를 절대 쓰지 못하게 강력한 옵션을 걸어서 요청합니다.
    fetch(ctx + "/cart?action=getCartCount&t=" + timestamp, {
        cache: 'no-store' 
    })
    .then(function(res) { return res.json(); })
    .then(function(data) {
        var count = data.count;
        
        // 헤더 장바구니 업데이트
        var headerCartLnk = document.querySelector('a[href*="/cart"].badge-wrap');
        if (headerCartLnk) {
            var badge = headerCartLnk.querySelector('.badge');
            if (count > 0) {
                if (badge) badge.textContent = count;
                else headerCartLnk.insertAdjacentHTML('beforeend', '<span class="badge">' + count + '</span>');
            } else {
                if (badge) badge.remove();
            }
        }
        
        // 기획전/상세페이지 장바구니 업데이트
        var detailCartBtns = document.querySelectorAll('.cart-icon-wrap');
        detailCartBtns.forEach(function(btn) {
            var badge = btn.querySelector('.cart-badge');
            if (count > 0) {
                if (badge) badge.textContent = count;
                else btn.insertAdjacentHTML('beforeend', '<span class="cart-badge">' + count + '</span>');
            } else {
                if (badge) badge.remove();
            }
        });
    })
    .catch(function(err) { console.error("장바구니 개수 동기화 실패:", err); });
}

window.addEventListener('pageshow', function(event) {
    if (event.persisted || (window.performance && window.performance.navigation.type === 2)) {
        updateCartBadgeSilently();
    }
});
</script>