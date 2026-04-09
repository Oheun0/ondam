<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="poke-modal-dim hidden" id="pokeModalDim"></div>

<div class="poke-modal hidden" id="pokeModal">
  <div class="poke-modal-card">

    <h2 class="poke-modal-title">누구에게 알려드릴까요?</h2>

    <div class="poke-person-list">
      <c:choose>
        <c:when test="${not empty pokeMemberList}">
          <c:forEach var="m" items="${pokeMemberList}">
            <button type="button" class="poke-person-btn"
                    data-user-no="${m.userNo}"
                    data-name="${m.userName}">
              ${m.userName}님에게 조르기
            </button>
          </c:forEach>
        </c:when>
        <c:otherwise>
          <p style="text-align:center; color:#999; padding: 16px 0;">
            함께하는 사람이 없어요.<br>
            <a href="${pageContext.request.contextPath}/group?action=groupName">그룹 만들기</a>
          </p>
        </c:otherwise>
      </c:choose>
    </div>

    <div class="poke-modal-bottom">
      <button type="button" class="poke-bottom-btn cancel" id="closePokeModalBtn">
        취소
      </button>
      <button type="button" class="poke-bottom-btn confirm" id="confirmPokeBtn">
        조르기
      </button>
    </div>

  </div>
</div>