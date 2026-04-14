<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/review-write.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/share-modal.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/inquiry.css">

<div class="review-my-written-panel">
  <c:choose>
    <c:when test="${not empty writtenList}">
      <div class="review-my-intro review-my-intro--written">
        <p class="review-my-intro-line">총 ${fn:length(writtenList)}개의 후기를 작성했어요!</p>
      </div>

      <div class="review-my-written-list">
        <c:forEach var="review" items="${writtenList}" varStatus="status">
          <article class="review-my-written-card" data-review-my-card="${status.count}">
            <div class="review-my-written-top">
              <div class="review-my-written-top__left">
                <div class="review-my-written-thumb-wrap">
                  <c:set var="imgSrc" value="${empty review.productImg ? 'type-top-knit.jpg' : review.productImg}" />
                  
                  <img src="${pageContext.request.contextPath}/images/category/${imgSrc}" 
                       alt="" 
                       class="review-my-written-thumb" 
                       width="72" height="72" 
                       loading="lazy" 
                       onerror="this.src='${pageContext.request.contextPath}/images/category/type-top-knit.jpg'"/>
                </div>
                <div class="review-my-written-meta">
                  <p class="review-my-written-product">${review.snapProductName}</p>
                  <p class="review-my-written-option">${review.snapOptionColor} / ${review.snapOptionSize}</p>
                </div>
              </div>
              <div class="review-my-more-wrap">
                <button type="button" class="review-my-more-btn" aria-label="더보기 메뉴" aria-haspopup="true" aria-expanded="false" id="reviewMyMoreBtn${status.count}">
                  <span class="material-icons" aria-hidden="true">more_horiz</span>
                </button>
                <div class="review-my-dropdown hidden" id="reviewMyDropdown${status.count}" role="menu" aria-hidden="true">
                  <button type="button" class="review-my-dropdown__item" role="menuitem" 
                          onclick="location.href='${pageContext.request.contextPath}/review?action=updateForm&reviewNo=${review.reviewNo}'">
                      수정하기
                  </button>
                  <button type="button" class="review-my-dropdown__item review-my-dropdown__item--danger review-delete-btn" role="menuitem" data-review-no="${review.reviewNo}">
				삭제하기</button>
                </div>
              </div>
            </div>

            <div class="review-my-written-divider" aria-hidden="true"></div>

            <div class="review-my-written-review">
              <div class="review-my-written-review-head">
                <div class="detail-review-stars-row review-my-written-stars" aria-label="별점 ${review.reviewRating}점">
                  <span class="detail-review-stars" aria-hidden="true">
                    <c:forEach begin="1" end="5" var="i">
                      <span class="material-icons detail-review-star ${i <= review.reviewRating ? 'detail-review-star--full' : 'detail-review-star--empty'}">star</span>
                    </c:forEach>
                  </span>
                  <span class="detail-review-rating-num">${review.reviewRating}</span>
                </div>
                <time class="review-my-written-date">${fn:substring(review.createdAt, 0, 10)}</time>
              </div>
              <p class="review-my-written-body">
                ${review.reviewContent}
              </p>
              
             <c:if test="${not empty review.replyContent && review.replyContent != 'null'}">
				  <div class="inquiry-list-answer-card" aria-label="판매자 답변" style="margin-top: 16px;">
				    <p class="inquiry-list-answer-label">
				      판매자 답변 | ${not empty review.replyDate ? fn:substring(review.replyDate, 0, 10) : ''}
				    </p>
				    <p class="inquiry-list-answer-body">
				      ${review.replyContent}
				    </p>
				  </div>
				</c:if>
              
            </div>
          </article>
        </c:forEach>
      </div>
    </c:when>

    <c:otherwise>
      <div class="review-my-empty" role="status">
        <p class="review-my-empty__title">아직 작성한 후기가 없어요</p>
        <p class="review-my-empty__sub">첫 후기를 남겨보세요</p>
      </div>
    </c:otherwise>
  </c:choose>
</div> 

<div class="review-write-modal hidden" id="reviewWriteModalDelete" role="dialog" aria-modal="true" aria-labelledby="reviewWriteModalDeleteTitle">
  <div class="review-write-modal-dim" data-modal-dismiss="delete"></div>
  <div class="review-write-modal-card">
    <p class="review-write-modal-message" id="reviewWriteModalDeleteTitle">
      정말 이 후기를 삭제하시겠습니까?<br/>
      <span style="font-size: 13px; color: #999; font-weight: 400;">삭제된 후기는 복구할 수 없습니다.</span>
    </p>
    <div class="review-write-modal-actions review-write-modal-actions--double">
      <button type="button" class="review-write-modal-btn review-write-modal-btn--ghost" data-modal-action="delete-cancel">취소</button>
      <button type="button" class="review-write-modal-btn review-write-modal-btn--primary" id="reviewDeleteConfirmBtn" style="background-color: #ff4d4f; border-color: #ff4d4f;">삭제하기</button>
    </div>
  </div>
</div>
<script src="${pageContext.request.contextPath}/js/review-write.js"></script>