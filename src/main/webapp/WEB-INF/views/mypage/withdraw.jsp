<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="inquiry-write-modal hidden" id="withdrawModal" role="dialog" aria-modal="true" aria-labelledby="withdrawModalTitle">
  <div class="inquiry-write-dim" data-withdraw-dismiss aria-hidden="true"></div>
  <div class="inquiry-write-modal-card">
    <p class="inquiry-write-modal-message" id="withdrawModalTitle">정말 온담을 떠나시겠어요?</p>

    <form id="withdrawForm" action="${pageContext.request.contextPath}/user/withdrawProcess" method="POST">
      <c:if test="${not empty loginUser.userPwd}">
        <input type="password"
               name="userPwd"
               id="withdrawPwd"
               class="input"
               placeholder="비밀번호를 입력해 주세요"
               autocomplete="current-password">
        <div id="withdrawErrorMsg" class="check-message error"></div>
      </c:if>
      <input type="hidden" name="isSocial" value="${empty loginUser.userPwd ? 'Y' : 'N'}">
    </form>

    <p class="inquiry-write-modal-sub withdraw-inner-ment">
      탈퇴하시면 모든 정보가 사라지며<br>
      <strong>복구할 수 없습니다.</strong>
    </p>

    <div class="inquiry-write-modal-actions inquiry-write-modal-actions--double">
      <button type="button" class="inquiry-write-modal-btn inquiry-write-modal-btn--ghost" id="withdrawCancelBtn">안 할래요</button>
      <button type="button" class="inquiry-write-modal-btn inquiry-write-modal-btn--primary" id="withdrawSubmitBtn">탈퇴하기</button>
    </div>
  </div>
</div>
<script src="${pageContext.request.contextPath}/js/withdraw.js"></script>