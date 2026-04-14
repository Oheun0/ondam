<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
        <div class="detail-review-toolbar">
          <span class="detail-review-total" id="detailReviewTotal">
            <c:choose>
              <c:when test="${not empty reviewList}">총 ${fn:length(reviewList)}개</c:when>
              <c:otherwise>총 0개</c:otherwise>
            </c:choose>
          </span>
          
          <div class="detail-review-sort" role="group" aria-label="리뷰 정렬">
            <button type="button" class="detail-review-sort-btn active" data-sort="popular" id="detailReviewSortPopular" aria-pressed="true">인기순</button>
            <span class="detail-review-sort-sep" aria-hidden="true">|</span>
            <button type="button" class="detail-review-sort-btn" data-sort="recent" id="detailReviewSortRecent" aria-pressed="false">최신순</button>
          </div>
        </div>

        <div class="detail-review-list">
          <c:choose>
            <%-- 1. 리뷰가 있을 때 (반복문으로 출력) --%>
            <c:when test="${not empty reviewList}">
              <c:forEach var="review" items="${reviewList}">
                <article class="detail-review-card">
                  <div class="detail-review-card__top">
                    <div class="detail-review-author">
                      <img class="detail-review-avatar" src="${pageContext.request.contextPath}/images/profile/default-profile.png" width="52" height="52" alt="프로필" />
                      <div class="detail-review-author-meta">
                        <div class="detail-review-name-row">
                          <%-- 작성자 이름 및 작성일 --%>
                          <span class="detail-review-nickname">${review.userName != null ? review.userName : '익명'}</span>
                          <time class="detail-review-date" datetime="${review.createdAt}">${review.createdAt}</time>
                        </div>
                        
                        <div class="detail-review-stars-row" aria-label="별점 ${review.reviewRating}점">
                          <span class="detail-review-stars" aria-hidden="true">
                            <%-- DB에 저장된 별점만큼 꽉 찬 별을, 나머지는 빈 별을 그립니다 --%>
                            <c:forEach begin="1" end="5" var="i">
                              <c:choose>
                                <c:when test="${i <= review.reviewRating}">
                                  <span class="material-icons detail-review-star detail-review-star--full">star</span>
                                </c:when>
                                <c:otherwise>
                                  <span class="material-icons detail-review-star detail-review-star--empty">star</span>
                                </c:otherwise>
                              </c:choose>
                            </c:forEach>
                          </span>
                          <span class="detail-review-rating-num">${review.reviewRating}</span>
                        </div>
                      </div>
                    </div>
                    <c:choose>
                    <c:when test="${not empty sessionScope.loginUser and sessionScope.loginUser.userNo == review.userNo}">
                      <%--내 글일 때: 버튼 비활성화--%>
                      <button type="button" class="detail-review-help-btn" disabled 
                              style="opacity: 0.5; cursor: not-allowed;" 
                              title="내가 작성한 후기입니다.">
                        <span class="material-icons" aria-hidden="true">thumb_up</span>
                        <span class="detail-review-help-count">${review.reviewHelpful}</span>
                      </button>
                    </c:when>
                    <c:otherwise>
                      <%--남의 글일 때--%>
                      <button type="button" class="detail-review-help-btn" data-review-no="${review.reviewNo}" aria-label="도움이 돼요 누르기">
                        <span class="material-icons" aria-hidden="true">thumb_up</span>
                        <span class="detail-review-help-count">${review.reviewHelpful}</span>
                      </button>
                    </c:otherwise>
                  </c:choose>
                  </div>
                  
                  <%-- 리뷰 본문 --%>
                  <p class="detail-review-body">${review.reviewContent}</p>
                  <%-- 구매한 옵션 --%>
                  <div class="detail-review-purchase-tag">${review.snapOptionColor} / ${review.snapOptionSize}</div>
                  
                  <%-- 리뷰 사진 목록 (이미지 DTO 리스트가 있다면 출력) --%>
                  <c:if test="${not empty review.imageList}">
                    <div class="detail-review-photos">
                      <c:forEach var="img" items="${review.imageList}">
                        <img src="${pageContext.request.contextPath}/upload/review/${img.reviewImg}" alt="리뷰 사진">
                      </c:forEach>
                    </div>
                  </c:if>
                  
                  <button type="button" class="detail-review-cart-btn">해당 옵션으로 장바구니에 담기</button>
                </article>
              </c:forEach>
            </c:when>

            <%-- 2. 리뷰가 한 개도 없을 때 --%>
            <c:otherwise>
              <div class="detail-review-empty" role="status" aria-live="polite">
                <p class="detail-review-empty__text">
                  이 상품을 먼저 사용해 본 분의<br />
                  이야기를 기다리고 있어요
                </p>
                </div>
            </c:otherwise>
          </c:choose>
        </div>

        <c:if test="${param.showMoreButton == 'true'}">
          <c:url var="reviewsAllUrl" value="/preview">
            <c:param name="page" value="product/review/all"/>
          </c:url>
          <a href="${reviewsAllUrl}" class="detail-review-more-btn">후기 전체보기</a>
        </c:if>
