<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<body>

	<c:choose>
		<%-- sendState == 0 : 수락/거절 UI --%>
		<c:when test="${poke.sendState == 0}">
			<h2>${poke.pokeMsg}(상품 No. ${poke.productNo})</h2>
			<div style="background: #f8f9fa; padding: 15px; margin-bottom: 20px; border-radius: 8px;">
				<p><strong>[요청 옵션 정보]</strong></p>
				<ul>
					<li>옵션 번호: ${poke.productOptionNo}</li>
					<li>요청 수량: ${poke.pokeQuantity}개</li>
				</ul>
				<small style="color: gray;">* 실제 서비스에서는 옵션 번호 대신 색상/사이즈 글자가 조인되어 출력됩니다.</small>
			</div>
			<form method="post" action="${pageContext.request.contextPath}/poke">
				<input type="hidden" name="action" value="respond"> <input
					type="hidden" name="pokeNo" value="${poke.pokeNo}">
				<button type="submit" name="respondAction" value="accept">수락</button>
				<button type="submit" name="respondAction" value="reject">거절</button>
			</form>
		</c:when>

		<%-- sendState == 1 : 이미 수락 --%>
		<c:when test="${poke.sendState == 1}">
			<p>이미 수락한 조르기입니다.</p>
		</c:when>

		<%-- sendState == 2 : 이미 거절 --%>
		<c:when test="${poke.sendState == 2}">
			<p>이미 거절한 조르기입니다.</p>
		</c:when>

		<%-- sendState == 3 : 만료 --%>
		<c:otherwise>
			<p>만료된 조르기입니다.</p>
		</c:otherwise>
	</c:choose>

	<a href="${pageContext.request.contextPath}/poke">목록으로</a>
</body>
</html>