<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--
  그룹 해산 / 그룹 나가기 확인 모달 (마크업만)
  그룹 화면: <jsp:include page="/WEB-INF/views/group/group-modal.jsp"/>
  기본 hidden — 열기/닫기는 해당 페이지 JS에서 처리
  딤·취소(계속 사용하기): data-group-modal-dismiss
--%>

<!-- 그룹장: 그룹 해산하기 -->
<div class="group-modal hidden" id="groupModalDissolve" role="dialog" aria-modal="true" aria-labelledby="groupModalDissolveTitle">
  <div class="group-modal-dim" data-group-modal-dismiss aria-hidden="true"></div>
  <div class="group-modal-card">
    <h2 class="group-modal-title" id="groupModalDissolveTitle">정말 해산하시겠어요?</h2>
    <div class="group-modal-body">
      <p class="group-modal-line">해산하면 함께했던 내 사람 연결이</p>
      <p class="group-modal-line">모두 해제되고, 함께지갑을 사용할 수 없어요</p>
    </div>
    <div class="group-modal-note" role="note">
      <p class="group-modal-note-line">함께지갑에 남은 금액이 있다면</p>
      <p class="group-modal-note-line">먼저 꺼낸 후 해산해 주세요</p>
    </div>
    <div class="group-modal-links" aria-label="해산 전 안내 링크">
      <a href="#" class="group-modal-link">잔액 꺼내고 해산하기</a>
      <a href="#" class="group-modal-link">그룹장 넘기고 해산하기</a>
    </div>
    <div class="group-modal-actions group-modal-actions--double">
      <button type="button" class="group-modal-btn group-modal-btn--ghost" data-group-modal-dismiss>계속 사용하기</button>
      <button type="button" class="group-modal-btn group-modal-btn--danger">해산하기</button>
    </div>
  </div>
</div>

<!-- 그룹 멤버: 나가기 -->
<div class="group-modal hidden" id="groupModalLeave" role="dialog" aria-modal="true" aria-labelledby="groupModalLeaveTitle">
  <div class="group-modal-dim" data-group-modal-dismiss aria-hidden="true"></div>
  <div class="group-modal-card">
    <h2 class="group-modal-title" id="groupModalLeaveTitle">정말 나가시겠어요?</h2>
    <div class="group-modal-body">
      <p class="group-modal-line">내 사람 연결이 해제되고</p>
      <p class="group-modal-line">함께지갑도 사용할 수 없어요</p>
    </div>
    <div class="group-modal-actions group-modal-actions--double">
      <button type="button" class="group-modal-btn group-modal-btn--ghost" data-group-modal-dismiss>계속 사용하기</button>
      <button type="button" class="group-modal-btn group-modal-btn--danger">나가기</button>
    </div>
  </div>
</div>
