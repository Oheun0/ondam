<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>받은 조르기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
</head>
<body>
<h2>받은 조르기 (임시)</h2>
<a href="${pageContext.request.contextPath}/poke?action=sent">보낸 조르기 보기</a>

<%-- 조르기 보내기 폼 (상품 상세 JSP 완성 전까지 임시 사용) --%>
<hr>
<h3>조르기 보내기 테스트</h3>
<form method="post" action="${pageContext.request.contextPath}/poke">
    <input type="hidden" name="action" value="send">
    productNo: <input type="text" name="productNo" value="1"><br>
    receiverNo: <input type="text" name="receiverNo" value="2"><br>
    familyNo: <input type="text" name="familyNo" value="1"><br>
    pokeMsg: <input type="text" name="pokeMsg" value="사줘!"><br>
    productOptionNo: <input type="number" name="productOptionNo" value="1"><br>
    pokeQuantity: <input type="number" name="pokeQuantity" value="1"><br>
    <button type="submit">조르기 전송</button>
</form>
<hr>

<table border="1" cellpadding="8">
    <tr>
        <th>pokeNo</th><th>productNo</th><th>보낸사람(userNo)</th>
        <th>메시지</th><th>상태</th><th>처리</th>
    </tr>
    <c:choose>
        <c:when test="${empty receivedList}">
            <tr><td colspan="6">받은 조르기가 없습니다.</td></tr>
        </c:when>
        <c:otherwise>
            <c:forEach var="p" items="${receivedList}">
            <tr>
                <td>${p.pokeNo}</td>
                <td>${p.productNo}</td>
                <td>${p.senderNo}</td>
                <td>${p.pokeMsg}</td>
                <td>
                    <c:choose>
                        <c:when test="${p.sendState == 0}">대기중</c:when>
                        <c:when test="${p.sendState == 1}">수락</c:when>
                        <c:otherwise>거절</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:if test="${p.sendState == 0}">
                        <form method="post" action="${pageContext.request.contextPath}/poke" style="display:inline">
                            <input type="hidden" name="action" value="respond">
                            <input type="hidden" name="pokeNo" value="${p.pokeNo}">
                            <button type="submit" name="respondAction" value="accept">수락</button>
                            <button type="submit" name="respondAction" value="reject">거절</button>
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