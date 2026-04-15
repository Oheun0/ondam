<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
  request.setAttribute("sellerActiveMenu", "review");
  request.setAttribute("sellerPageTitle", "리뷰 관리");
%>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>리뷰 관리 | 온담 파트너</title>

  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Material+Icons+Outlined&display=swap" rel="stylesheet">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-layout.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-review.css" />
</head>
<body class="seller-app" data-context-path="${pageContext.request.contextPath}">
  <div class="seller-layout">
    <jsp:include page="/WEB-INF/views/seller/layout/seller-sidebar.jsp" />

    <div class="seller-main-area">
      <jsp:include page="/WEB-INF/views/seller/layout/seller-header.jsp" />

      <main class="seller-content seller-review-page" aria-label="리뷰 관리">
        <header class="seller-review-head">
          <div>
            <h2 class="seller-review-title">리뷰 관리</h2>
            <p class="seller-review-sub">등록된 리뷰를 확인하고 응대할 수 있어요</p>
          </div>
        </header>

        <section class="seller-review-summary" aria-label="요약">
          <div class="seller-review-summary-grid">
            <div class="seller-review-summary-card">
              <div class="seller-review-summary-label">전체 리뷰</div>
              <div class="seller-review-summary-value">${summary.totalCnt}<span class="seller-review-summary-unit">개</span></div>
            </div>
            <div class="seller-review-summary-card">
              <div class="seller-review-summary-label">평균 평점</div>
              <div class="seller-review-summary-value">${summary.avgRating}<span class="seller-review-summary-unit">점</span></div>
            </div>
            <div class="seller-review-summary-card">
              <div class="seller-review-summary-label">미답변 리뷰</div>
              <div class="seller-review-summary-value">${summary.noReplyCnt}<span class="seller-review-summary-unit">개</span></div>
            </div>
            <div class="seller-review-summary-card">
              <div class="seller-review-summary-label">이번 주 신규</div>
              <div class="seller-review-summary-value">${summary.newThisWeek}<span class="seller-review-summary-unit">개</span></div>
            </div>
          </div>
        </section>

        <section class="seller-card seller-review-toolbar" aria-label="검색 및 필터">
          <div class="seller-review-filters">
            <div class="seller-review-filter">
              <label class="seller-review-filter-label" for="reviewProduct">상품</label>
              <select id="reviewProduct" class="seller-review-select">
				  <option value="all" ${paramProduct == 'all' ? 'selected' : ''}>전체 상품</option>
				  <c:forEach var="p" items="${vendorProductList}">
				    <option value="${p.productNo}" ${paramProduct == p.productNo.toString() ? 'selected' : ''}>
					  ${p.productName}
					</option>
				  </c:forEach>
				</select>
            </div>

            <div class="seller-review-filter">
			  <label class="seller-review-filter-label" for="reviewRating">평점</label>
			  <select id="reviewRating" class="seller-review-select">
			    <option value="all" ${paramRating == 'all' || empty paramRating ? 'selected' : ''}>전체 평점</option>
			    <option value="5" ${paramRating == '5' ? 'selected' : ''}>5점</option>
			    <option value="4" ${paramRating == '4' ? 'selected' : ''}>4점</option>
			    <option value="3" ${paramRating == '3' ? 'selected' : ''}>3점</option>
			    <option value="2" ${paramRating == '2' ? 'selected' : ''}>2점 이하</option>
			  </select>
			</div>

            <div class="seller-review-filter">
			  <label class="seller-review-filter-label" for="reviewPeriod">기간</label>
			  <select id="reviewPeriod" class="seller-review-select">
			    <option value="all" ${paramPeriod == 'all' || empty paramPeriod ? 'selected' : ''}>전체 기간</option>
			    <option value="7d" ${paramPeriod == '7d' ? 'selected' : ''}>최근 7일</option>
			    <option value="30d" ${paramPeriod == '30d' ? 'selected' : ''}>최근 30일</option>
			    <option value="3m" ${paramPeriod == '3m' ? 'selected' : ''}>최근 3개월</option>
			  </select>
			</div>

            <div class="seller-review-filter seller-review-filter--search">
              <label class="seller-review-filter-label" for="reviewQuery">검색</label>
              <div class="seller-review-input-wrap">
                <span class="material-icons-outlined" aria-hidden="true">search</span>
                <input id="reviewQuery" class="seller-review-input" type="text" 
     					  value="${paramQuery}" placeholder="리뷰 내용 또는 작성자로 검색해 주세요" />
              </div>
            </div>

            <div class="seller-review-filter seller-review-filter--btn">
              <button type="button" class="seller-review-secondary-btn" id="reviewApplyBtn">필터 적용</button>
            </div>
          </div>
        </section>

        <section class="seller-review-list" aria-label="리뷰 목록">
          <c:choose>
            <c:when test="${empty reviewList}">
              <section class="seller-card seller-review-empty" aria-label="빈 상태">
                <div class="seller-review-empty-inner">
                  <div class="seller-review-empty-icon" aria-hidden="true"><span class="material-icons-outlined">rate_review</span></div>
                  <h3 class="seller-review-empty-title">조건에 맞는 리뷰가 없어요</h3>
                </div>
              </section>
            </c:when>
            <c:otherwise>
              <c:forEach var="review" items="${reviewList}">
                <article class="seller-review-card" 
                  data-review-id="${review.reviewNo}" 
                  data-answered="${review.answered}" 
                  data-rating="${review.reviewRating}" 
                  data-product="${review.productName}"
                  data-author="${review.authorName}" 
                  data-date="${review.createdAt}" 
                  data-order-no="${review.orderNo}" 
                  data-option="${review.optionInfo}"
                  data-content="${review.reviewContent}"
                  data-reply="${review.replyContent}">
                  
                  <header class="seller-review-card-head">
                    <div class="seller-review-who">
                      <strong class="seller-review-author">${review.authorName}</strong>
                      <span class="seller-review-sep">/</span>
                      <span class="seller-review-date">${review.createdAt}</span>
                    </div>
                    <div class="seller-review-right">
                      <div class="seller-review-rating" aria-label="평점 ${review.reviewRating}점">
                        <span class="seller-review-stars" aria-hidden="true">
                          <c:forEach begin="1" end="${review.reviewRating}">★</c:forEach><c:forEach begin="1" end="${5 - review.reviewRating}">☆</c:forEach>
                        </span>
                        <span class="seller-review-score">${review.reviewRating}.0</span>
                      </div>
                      <c:choose>
                        <c:when test="${review.answered}">
                          <span class="seller-review-badge seller-review-badge--done">답변 완료</span>
                        </c:when>
                        <c:otherwise>
                          <span class="seller-review-badge seller-review-badge--todo">미답변</span>
                        </c:otherwise>
                      </c:choose>
                    </div>
                  </header>

                  <div class="seller-review-product">${review.productName}</div>
                  <p class="seller-review-content">${review.reviewContent}</p>
                  
                  <c:if test="${not empty review.reviewImages}">
                    <div class="seller-review-images" aria-label="리뷰 이미지">
                      <c:forEach var="img" items="${review.reviewImages}">
                        <img src="${pageContext.request.contextPath}/uploads/reviews/${img}" alt="리뷰 이미지" onerror="this.src='${pageContext.request.contextPath}/images/no-image.png'">
                      </c:forEach>
                    </div>
                  </c:if>

                  <div class="seller-review-meta">
                    <div class="seller-review-meta-row"><span class="k">주문번호</span><span class="v">${review.orderNo}</span></div>
                    <div class="seller-review-meta-row"><span class="k">옵션</span><span class="v">${review.optionInfo}</span></div>
                  </div>

                  <div class="seller-review-actions">
                    <button type="button" class="seller-review-btn ${review.answered ? '' : 'seller-review-btn--primary'}" data-action="reply">
                      ${review.answered ? '답변 보기' : '답변 달기'}
                    </button>
                  </div>
                </article>
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </section>

        <div class="seller-review-pagination" aria-label="페이지네이션">
		  <c:forEach var="i" begin="${startPage}" end="${endPage}">
		    <button type="button" class="seller-review-page-btn ${i == currentPage ? 'active' : ''}" data-page="${i}">
		      ${i}
		    </button>
		  </c:forEach>
		</div>

        <section class="seller-card seller-review-empty" aria-label="빈 상태" hidden>
          <div class="seller-review-empty-inner">
            <div class="seller-review-empty-icon" aria-hidden="true">
              <span class="material-icons-outlined">rate_review</span>
            </div>
            <h3 class="seller-review-empty-title">아직 등록된 리뷰가 없어요</h3>
            <p class="seller-review-empty-desc">리뷰가 작성되면 여기에 표시됩니다</p>
          </div>
        </section>
      </main>

      <!-- 상세/응대 패널 -->
      <aside class="seller-review-panel hidden" id="reviewPanel" aria-label="리뷰 상세 패널" aria-hidden="true">
        <div class="seller-review-panel__dim" id="reviewPanelDim" aria-hidden="true"></div>
        <div class="seller-review-panel__sheet" role="dialog" aria-modal="true" aria-labelledby="reviewPanelTitle">
          <header class="seller-review-panel__head">
            <div>
              <h3 class="seller-review-panel__title" id="reviewPanelTitle">리뷰 상세</h3>
              <p class="seller-review-panel__sub" id="reviewPanelSub">-</p>
            </div>
            <button type="button" class="seller-review-panel__close" id="reviewPanelClose" aria-label="닫기">
              <span class="material-icons-outlined" aria-hidden="true">close</span>
            </button>
          </header>

          <div class="seller-review-panel__body">
            <div class="seller-review-panel__info">
              <div class="row"><span class="k">작성자</span><span class="v" id="pdAuthor">-</span></div>
              <div class="row"><span class="k">작성일</span><span class="v" id="pdDate">-</span></div>
              <div class="row"><span class="k">상품</span><span class="v" id="pdProduct">-</span></div>
              <div class="row"><span class="k">옵션</span><span class="v" id="pdOption">-</span></div>
              <div class="row"><span class="k">주문번호</span><span class="v" id="pdOrderNo">-</span></div>
              <div class="row"><span class="k">평점</span><span class="v" id="pdRating">-</span></div>
              <div class="row"><span class="k">상태</span><span class="v" id="pdAnswered">-</span></div>
            </div>

            <section class="seller-review-panel__content" aria-label="리뷰 본문">
              <h4 class="seller-review-panel__h4">리뷰 내용</h4>
              <p class="seller-review-panel__text" id="pdContent">-</p>
              <div class="seller-review-panel__images" id="pdImages" aria-label="리뷰 이미지"></div>
            </section>

            <section class="seller-review-panel__reply" aria-label="리뷰 응대">
              <h4 class="seller-review-panel__h4">답변 달기</h4>
              <div class="seller-review-reply-box">
                <textarea id="replyText" class="seller-review-reply-text" rows="4" placeholder="짧고 친절하게 답변해 주세요"></textarea>
                <div class="seller-review-reply-templates">
                  <button type="button" class="seller-review-template" data-template="소중한 후기 감사합니다.">후기 감사</button>
                  <button type="button" class="seller-review-template" data-template="만족하셨다니 정말 기쁩니다.">만족 감사</button>
                  <button type="button" class="seller-review-template" data-template="불편을 드려 죄송합니다. 더 좋은 상품으로 보답하겠습니다.">사과/개선</button>
                </div>
                <div class="seller-review-reply-actions">
                  <button type="button" class="seller-review-secondary-btn" id="replyCancelBtn">취소</button>
                  <button type="button" class="seller-review-primary-btn" id="replySubmitBtn">답변 등록</button>
                </div>
                <p class="seller-review-panel__hint">답변 등록은 더미 동작이며 실제 저장은 아직 연동되지 않았어요.</p>
              </div>
            </section>
          </div>
        </div>
      </aside>

      <jsp:include page="/WEB-INF/views/seller/layout/seller-footer.jsp" />
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/js/seller/review-list.js"></script>
</body>
</html>

