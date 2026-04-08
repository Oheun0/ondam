<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">

<div id="withdrawOverlay" class="purchase-modal-overlay" style="display: none;" onclick="closeWithdraw()">
    <div class="purchase-modal" onclick="event.stopPropagation();">
        <div class="modal-header">
            <h3 style="color: #ff5252;">정말 온담을 떠나시겠어요?</h3>
            <button class="close-btn" onclick="closeWithdraw()">
                <span class="material-icons">close</span>
            </button>
        </div>
        
        <div class="modal-options" style="text-align: center; padding: 20px 0;">
            <p style="margin-bottom: 20px; color: #666; word-break: keep-all;">
                탈퇴하시면 모든 정보가 사라지며<br><strong>복구할 수 없습니다.</strong>
            </p>
            
            <form id="withdrawForm" action="${pageContext.request.contextPath}/user/withdrawProcess" method="POST">
                <c:if test="${not empty loginUser.userPwd}">
                    <input type="password" name="userPwd" id="withdrawPwd" class="input" placeholder="비밀번호를 입력해 주세요" 
                           style="width: 100%; margin-bottom: 5px;">
                    <div id="withdrawErrorMsg" class="check-message error" style="text-align: left; margin-bottom: 10px;"></div>
                </c:if>
                <input type="hidden" name="isSocial" value="${empty loginUser.userPwd ? 'Y' : 'N'}">
            </form>
        </div>
        
        <div class="modal-actions">
            <button class="buy-now-btn btn-cancel" onclick="closeWithdraw()">안 할래요</button>
            <button class="buy-now-btn btn-withdraw" onclick="submitWithdraw()">탈퇴하기</button>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/withdraw.js"></script>