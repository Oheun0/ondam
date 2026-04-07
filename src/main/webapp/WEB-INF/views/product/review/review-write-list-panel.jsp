<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="review-my-write-panel">
  <c:choose>
    <c:when test="${not empty writeableList}">
      <div class="review-my-intro review-my-intro--write">
        <p class="review-my-intro-line">작성 가능한 후기가 총 ${fn:length(writeableList)}개 있어요</p>
        <p class="review-my-intro-sub">후기를 작성하면 할인 쿠폰을 드려요</p>
      </div>

      <div class="review-my-write-list">
        <c:forEach var="item" items="${writeableList}">
          <article class="review-my-write-card">
            <div class="review-my-write-card__main">
              <div class="review-my-write-card__thumb-wrap">
                <c:set var="imgSrc" value="${empty item.productImg ? 'type-top-knit.jpg' : item.productImg}" />
                <img src="${pageContext.request.contextPath}/images/category/${imgSrc}" 
                     alt="" 
                     class="review-my-write-card__thumb" 
                     width="96" height="96" 
                     loading="lazy"
                     onerror="this.src='${pageContext.request.contextPath}/images/category/type-top-knit.jpg'"/>
              </div>
              <div class="review-my-write-card__info">
                <p class="review-my-write-brand">온담</p> <p class="review-my-write-name">${item.snapProductName}</p>
                <p class="review-my-write-option">${item.snapOptionColor} / ${item.snapOptionSize}</p>
              </div>
            </div>
            
            <button type="button" class="review-my-write-btn" 
                    onclick="location.href='${pageContext.request.contextPath}/review?action=writeForm&orderItemNo=${item.orderItemNo}'">
                후기 작성하기
            </button>
          </article>
        </c:forEach>
      </div>
    </c:when>

    <c:otherwise>
      <div class="review-my-empty" role="status">
        <p class="review-my-empty__title">작성할 수 있는 후기가 없어요</p>
        <p class="review-my-empty__sub">상품을 구매하면 이곳에서 후기를 남길 수 있어요</p>
      </div>
    </c:otherwise>
  </c:choose>
</div>