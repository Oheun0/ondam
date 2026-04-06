<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="wallet-top">
    <a href="#" class="back-btn" onclick="history.back(); return false;"> 
        <span class="material-icons">arrow_back_ios</span>
        <span>뒤로가기</span>
    </a>
</div>

<div class="balance-card">
    <p>현재 잔액</p>
    <strong>
        <c:choose>
            <c:when test="${not empty wallet}">
                <fmt:formatNumber value="${wallet.balance}" pattern="#,###"/>원
            </c:when>
            <c:otherwise>0원</c:otherwise>
        </c:choose>
    </strong>
</div>