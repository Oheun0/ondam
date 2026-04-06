<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <div class="detail-review-toolbar">
          <span class="detail-review-total" id="detailReviewTotal">총 7개</span>
          <div class="detail-review-sort" role="group" aria-label="리뷰 정렬">
            <button type="button" class="detail-review-sort-btn active" data-sort="popular" id="detailReviewSortPopular" aria-pressed="true">인기순</button>
            <span class="detail-review-sort-sep" aria-hidden="true">|</span>
            <button type="button" class="detail-review-sort-btn" data-sort="recent" id="detailReviewSortRecent" aria-pressed="false">최신순</button>
          </div>
        </div>

        <div class="detail-review-list">
          <article class="detail-review-card">
            <div class="detail-review-card__top">
              <div class="detail-review-author">
                <img class="detail-review-avatar" src="${pageContext.request.contextPath}/images/profile/default-profile.png" width="52" height="52" alt="김남준 님 프로필" />
                <div class="detail-review-author-meta">
                  <div class="detail-review-name-row">
                    <span class="detail-review-nickname">김남준</span>
                    <time class="detail-review-date" datetime="2025-03-12">2025.03.12</time>
                  </div>
                  <div class="detail-review-stars-row" aria-label="별점 5점">
                    <span class="detail-review-stars" aria-hidden="true">
                      <span class="material-icons detail-review-star detail-review-star--full">star</span>
                      <span class="material-icons detail-review-star detail-review-star--full">star</span>
                      <span class="material-icons detail-review-star detail-review-star--full">star</span>
                      <span class="material-icons detail-review-star detail-review-star--full">star</span>
                      <span class="material-icons detail-review-star detail-review-star--full">star</span>
                    </span>
                    <span class="detail-review-rating-num">5</span>
                  </div>
                </div>
              </div>
              <button type="button" class="detail-review-help-btn" aria-label="도움이 돼요 누르기">
                <span class="material-icons" aria-hidden="true">thumb_up</span>
                <span class="detail-review-help-count">3</span>
              </button>
            </div>
            <p class="detail-review-body">부드럽고 핏이 넉넉해서 집에서 자주 입어요.</p>
            <div class="detail-review-purchase-tag">노란색 / 100</div>
            <div class="detail-review-photos">
              <img src="${pageContext.request.contextPath}/images/category/comfort-loose.jpg" alt="리뷰 첨부 사진 1">
              <img src="${pageContext.request.contextPath}/images/category/comfort-loose.jpg" alt="리뷰 첨부 사진 2">
              <img src="${pageContext.request.contextPath}/images/category/comfort-loose.jpg" alt="리뷰 첨부 사진 3">
            </div>
            <button type="button" class="detail-review-cart-btn">해당 옵션으로 장바구니에 담기</button>
          </article>

          <article class="detail-review-card">
            <div class="detail-review-card__top">
              <div class="detail-review-author">
                <img class="detail-review-avatar" src="${pageContext.request.contextPath}/images/profile/default-profile.png" width="52" height="52" alt="이서연 님 프로필" />
                <div class="detail-review-author-meta">
                  <div class="detail-review-name-row">
                    <span class="detail-review-nickname">이서연</span>
                    <time class="detail-review-date" datetime="2025-03-08">2025.03.08</time>
                  </div>
                  <div class="detail-review-stars-row" aria-label="별점 4점">
                    <span class="detail-review-stars" aria-hidden="true">
                      <span class="material-icons detail-review-star detail-review-star--full">star</span>
                      <span class="material-icons detail-review-star detail-review-star--full">star</span>
                      <span class="material-icons detail-review-star detail-review-star--full">star</span>
                      <span class="material-icons detail-review-star detail-review-star--full">star</span>
                      <span class="material-icons detail-review-star detail-review-star--empty">star</span>
                    </span>
                    <span class="detail-review-rating-num">4</span>
                  </div>
                </div>
              </div>
              <button type="button" class="detail-review-help-btn" aria-label="도움이 돼요 누르기">
                <span class="material-icons" aria-hidden="true">thumb_up</span>
                <span class="detail-review-help-count">12</span>
              </button>
            </div>
            <p class="detail-review-body">색감이 사진과 비슷하고 세탁 후에도 늘어남이 적어요.</p>
            <div class="detail-review-purchase-tag">베이지 / 105</div>
            <div class="detail-review-photos">
              <img src="${pageContext.request.contextPath}/images/category/comfort-loose.jpg" alt="리뷰 첨부 사진 1">
              <img src="${pageContext.request.contextPath}/images/category/comfort-loose.jpg" alt="리뷰 첨부 사진 2">
            </div>
            <button type="button" class="detail-review-cart-btn">해당 옵션으로 장바구니에 담기</button>
          </article>

          <article class="detail-review-card">
            <div class="detail-review-card__top">
              <div class="detail-review-author">
                <img class="detail-review-avatar" src="${pageContext.request.contextPath}/images/profile/default-profile.png" width="52" height="52" alt="박민수 님 프로필" />
                <div class="detail-review-author-meta">
                  <div class="detail-review-name-row">
                    <span class="detail-review-nickname">박민수</span>
                    <time class="detail-review-date" datetime="2025-02-28">2025.02.28</time>
                  </div>
                  <div class="detail-review-stars-row" aria-label="별점 3점">
                    <span class="detail-review-stars" aria-hidden="true">
                      <span class="material-icons detail-review-star detail-review-star--full">star</span>
                      <span class="material-icons detail-review-star detail-review-star--full">star</span>
                      <span class="material-icons detail-review-star detail-review-star--full">star</span>
                      <span class="material-icons detail-review-star detail-review-star--empty">star</span>
                      <span class="material-icons detail-review-star detail-review-star--empty">star</span>
                    </span>
                    <span class="detail-review-rating-num">3</span>
                  </div>
                </div>
              </div>
              <button type="button" class="detail-review-help-btn" aria-label="도움이 돼요 누르기">
                <span class="material-icons" aria-hidden="true">thumb_up</span>
                <span class="detail-review-help-count">7</span>
              </button>
            </div>
            <p class="detail-review-body">어머니 선물로 드렸는데 사이즈 잘 맞으셨어요.</p>
            <div class="detail-review-purchase-tag">그레이 / 110</div>
            <div class="detail-review-photos">
              <img src="${pageContext.request.contextPath}/images/category/comfort-loose.jpg" alt="리뷰 첨부 사진 1">
              <img src="${pageContext.request.contextPath}/images/category/comfort-loose.jpg" alt="리뷰 첨부 사진 2">
              <img src="${pageContext.request.contextPath}/images/category/comfort-loose.jpg" alt="리뷰 첨부 사진 3">
            </div>
            <button type="button" class="detail-review-cart-btn">해당 옵션으로 장바구니에 담기</button>
          </article>

          <article class="detail-review-card">
            <div class="detail-review-card__top">
              <div class="detail-review-author">
                <img class="detail-review-avatar" src="${pageContext.request.contextPath}/images/profile/default-profile.png" width="52" height="52" alt="최유진 님 프로필" />
                <div class="detail-review-author-meta">
                  <div class="detail-review-name-row">
                    <span class="detail-review-nickname">최유진</span>
                    <time class="detail-review-date" datetime="2025-02-20">2025.02.20</time>
                  </div>
                  <div class="detail-review-stars-row" aria-label="별점 5점">
                    <span class="detail-review-stars" aria-hidden="true">
                      <span class="material-icons detail-review-star detail-review-star--full">star</span>
                      <span class="material-icons detail-review-star detail-review-star--full">star</span>
                      <span class="material-icons detail-review-star detail-review-star--full">star</span>
                      <span class="material-icons detail-review-star detail-review-star--full">star</span>
                      <span class="material-icons detail-review-star detail-review-star--full">star</span>
                    </span>
                    <span class="detail-review-rating-num">5</span>
                  </div>
                </div>
              </div>
              <button type="button" class="detail-review-help-btn" aria-label="도움이 돼요 누르기">
                <span class="material-icons" aria-hidden="true">thumb_up</span>
                <span class="detail-review-help-count">1</span>
              </button>
            </div>
            <p class="detail-review-body">가볍게 걸치기 좋아 데일리로 입기 좋습니다.</p>
            <div class="detail-review-purchase-tag">네이비 / 100</div>
            <div class="detail-review-photos">
              <img src="${pageContext.request.contextPath}/images/category/comfort-loose.jpg" alt="리뷰 첨부 사진 1">
              <img src="${pageContext.request.contextPath}/images/category/comfort-loose.jpg" alt="리뷰 첨부 사진 2">
            </div>
            <button type="button" class="detail-review-cart-btn">해당 옵션으로 장바구니에 담기</button>
          </article>

          <article class="detail-review-card">
            <div class="detail-review-card__top">
              <div class="detail-review-author">
                <img class="detail-review-avatar" src="${pageContext.request.contextPath}/images/profile/default-profile.png" width="52" height="52" alt="정하늘 님 프로필" />
                <div class="detail-review-author-meta">
                  <div class="detail-review-name-row">
                    <span class="detail-review-nickname">정하늘</span>
                    <time class="detail-review-date" datetime="2025-02-10">2025.02.10</time>
                  </div>
                  <div class="detail-review-stars-row" aria-label="별점 4점">
                    <span class="detail-review-stars" aria-hidden="true">
                      <span class="material-icons detail-review-star detail-review-star--full">star</span>
                      <span class="material-icons detail-review-star detail-review-star--full">star</span>
                      <span class="material-icons detail-review-star detail-review-star--full">star</span>
                      <span class="material-icons detail-review-star detail-review-star--full">star</span>
                      <span class="material-icons detail-review-star detail-review-star--empty">star</span>
                    </span>
                    <span class="detail-review-rating-num">4</span>
                  </div>
                </div>
              </div>
              <button type="button" class="detail-review-help-btn" aria-label="도움이 돼요 누르기">
                <span class="material-icons" aria-hidden="true">thumb_up</span>
                <span class="detail-review-help-count">5</span>
              </button>
            </div>
            <p class="detail-review-body">봄에 입기 딱 좋은 두께감이에요.</p>
            <div class="detail-review-purchase-tag">아이보리 / 95</div>
            <div class="detail-review-photos">
              <img src="${pageContext.request.contextPath}/images/category/comfort-loose.jpg" alt="리뷰 첨부 사진 1">
              <img src="${pageContext.request.contextPath}/images/category/comfort-loose.jpg" alt="리뷰 첨부 사진 2">
              <img src="${pageContext.request.contextPath}/images/category/comfort-loose.jpg" alt="리뷰 첨부 사진 3">
            </div>
            <button type="button" class="detail-review-cart-btn">해당 옵션으로 장바구니에 담기</button>
          </article>

          <article class="detail-review-card">
            <div class="detail-review-card__top">
              <div class="detail-review-author">
                <img class="detail-review-avatar" src="${pageContext.request.contextPath}/images/profile/default-profile.png" width="52" height="52" alt="한지우 님 프로필" />
                <div class="detail-review-author-meta">
                  <div class="detail-review-name-row">
                    <span class="detail-review-nickname">한지우</span>
                    <time class="detail-review-date" datetime="2025-01-22">2025.01.22</time>
                  </div>
                  <div class="detail-review-stars-row" aria-label="별점 5점">
                    <span class="detail-review-stars" aria-hidden="true">
                      <span class="material-icons detail-review-star detail-review-star--full">star</span>
                      <span class="material-icons detail-review-star detail-review-star--full">star</span>
                      <span class="material-icons detail-review-star detail-review-star--full">star</span>
                      <span class="material-icons detail-review-star detail-review-star--full">star</span>
                      <span class="material-icons detail-review-star detail-review-star--full">star</span>
                    </span>
                    <span class="detail-review-rating-num">5</span>
                  </div>
                </div>
              </div>
              <button type="button" class="detail-review-help-btn" aria-label="도움이 돼요 누르기">
                <span class="material-icons" aria-hidden="true">thumb_up</span>
                <span class="detail-review-help-count">9</span>
              </button>
            </div>
            <p class="detail-review-body">소재가 부드럽고 목 부분이 답답하지 않아 좋아요.</p>
            <div class="detail-review-purchase-tag">블랙 / 100</div>
            <div class="detail-review-photos">
              <img src="${pageContext.request.contextPath}/images/category/comfort-loose.jpg" alt="리뷰 첨부 사진 1">
              <img src="${pageContext.request.contextPath}/images/category/comfort-loose.jpg" alt="리뷰 첨부 사진 2">
            </div>
            <button type="button" class="detail-review-cart-btn">해당 옵션으로 장바구니에 담기</button>
          </article>

          <article class="detail-review-card">
            <div class="detail-review-card__top">
              <div class="detail-review-author">
                <img class="detail-review-avatar" src="${pageContext.request.contextPath}/images/profile/default-profile.png" width="52" height="52" alt="오수빈 님 프로필" />
                <div class="detail-review-author-meta">
                  <div class="detail-review-name-row">
                    <span class="detail-review-nickname">오수빈</span>
                    <time class="detail-review-date" datetime="2025-01-05">2025.01.05</time>
                  </div>
                  <div class="detail-review-stars-row" aria-label="별점 4점">
                    <span class="detail-review-stars" aria-hidden="true">
                      <span class="material-icons detail-review-star detail-review-star--full">star</span>
                      <span class="material-icons detail-review-star detail-review-star--full">star</span>
                      <span class="material-icons detail-review-star detail-review-star--full">star</span>
                      <span class="material-icons detail-review-star detail-review-star--full">star</span>
                      <span class="material-icons detail-review-star detail-review-star--empty">star</span>
                    </span>
                    <span class="detail-review-rating-num">4</span>
                  </div>
                </div>
              </div>
              <button type="button" class="detail-review-help-btn" aria-label="도움이 돼요 누르기">
                <span class="material-icons" aria-hidden="true">thumb_up</span>
                <span class="detail-review-help-count">2</span>
              </button>
            </div>
            <p class="detail-review-body">배송도 빠르고 포장 상태도 깔끔했습니다.</p>
            <div class="detail-review-purchase-tag">브라운 / 105</div>
            <div class="detail-review-photos">
              <img src="${pageContext.request.contextPath}/images/category/comfort-loose.jpg" alt="리뷰 첨부 사진 1">
            </div>
            <button type="button" class="detail-review-cart-btn">해당 옵션으로 장바구니에 담기</button>
          </article>
        </div>

        <!--
        리뷰가 없을 때: 아래 주석을 해제한 뒤, 위의 <div class="detail-review-list"> … </div> 와
        전체보기 링크(또는 버튼)는 주석 처리하거나 제거하면 됩니다.

        <div class="detail-review-empty" role="status" aria-live="polite">
          <p class="detail-review-empty__text">
            이 상품을 먼저 사용해 본 분의<br />
            이야기를 기다리고 있어요
          </p>
          <button type="button" class="detail-review-empty__btn">리뷰 생기면 알림받기</button>
        </div>
        -->

        <c:if test="${param.showMoreButton == 'true'}">
          <c:url var="reviewsAllUrl" value="/product">
            <c:param name="action" value="reviews"/>
            <c:if test="${not empty product}">
              <c:param name="productNo" value="${product.productNo}"/>
            </c:if>
          </c:url>
          <a href="${reviewsAllUrl}" class="detail-review-more-btn">후기 전체보기</a>
        </c:if>
