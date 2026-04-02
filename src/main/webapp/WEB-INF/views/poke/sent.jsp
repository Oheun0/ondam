<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<body>
<h2>보낸 조르기 (임시)</h2>
<a href="${pageContext.request.contextPath}/poke">받은 조르기 보기</a>
<hr>

<table border="1" cellpadding="8">
    <tr>
        <th>pokeNo</th><th>productNo</th><th>받는사람(userNo)</th>
        <th>메시지</th><th>상태</th><th>취소</th>
    </tr>
    <c:choose>
        <c:when test="${empty sentList}">
            <tr><td colspan="6">보낸 조르기가 없습니다.</td></tr>
        </c:when>
        <c:otherwise>
            <c:forEach var="p" items="${sentList}">
            <tr>
                <td>${p.pokeNo}</td>
                <td>${p.productNo}</td>
                <td>${p.receiverNo}</td>
                <td>${p.pokeMsg}</td>
                <td>
                    <c:choose>
                        <c:when test="${p.sendState == 0}">대기중</c:when>
                        <c:when test="${p.sendState == 1}">수락됨</c:when>
                        <c:otherwise>거절됨</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:if test="${p.sendState == 0}">
                        <form method="post" action="${pageContext.request.contextPath}/poke">
                            <input type="hidden" name="action" value="cancel">
                            <input type="hidden" name="pokeNo" value="${p.pokeNo}">
                            <button type="submit">취소</button>
                        </form>
                    </c:if>
                </td>
            </tr>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</table>
</body>
</html>