<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="wallet-top">
    <c:choose>
        <c:when test="${not empty param.action}">
            <a href="javascript:history.back();" class="back-btn">
                <span class="material-icons">arrow_back_ios</span>
                <span>뒤로가기</span>
            </a>
        </c:when>

        <c:otherwise>
            <a href="#" id="walletMainBackBtn" class="back-btn">
                <span class="material-icons">arrow_back_ios</span>
                <span>뒤로가기</span>
            </a>
            
            <script>
                document.addEventListener("DOMContentLoaded", function() {
                    const backBtn = document.getElementById('walletMainBackBtn');
                    if (backBtn) {
                        backBtn.addEventListener('click', function(e) {
                            e.preventDefault();
                            const urlParams = new URLSearchParams(window.location.search);
                            const fromWhere = urlParams.get('from');

                            if (fromWhere === 'mypage') {
                                window.location.href = '${pageContext.request.contextPath}/mypage';
                            } else {
                                window.location.href = '${pageContext.request.contextPath}/group'; 
                            }
                        });
                    }
                });
            </script>
        </c:otherwise>
    </c:choose>
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