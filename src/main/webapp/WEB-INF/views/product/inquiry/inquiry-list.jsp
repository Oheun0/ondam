<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>문의내역</title>

  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-detail.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/inquiry.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/inquiry-write.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/share-modal.css">
  
</head>
<body class="inquiry-list-page" data-context-path="${pageContext.request.contextPath}">
  <div class="detail-shell">
    <div class="detail-page-inner detail-page-inner--sticky-header inquiry-list-inner" id="inquiryListPageRoot">
      <div class="inquiry-list-sticky-head">
        <div class="inquiry-list-header-wrap">
          <jsp:include page="/WEB-INF/views/layout/back-header.jsp"/>
          <h1 class="inquiry-list-header-title">문의내역</h1>
        </div>
      </div>

      <main class="inquiry-list-main">
        <!-- 상단 안내 문구 -->
        <p class="inquiry-list-desc">답변이 완료된 문의는 30일 후 자동으로 사라져요</p>
        <c:choose>
          <%-- 1. 문의 내역이 비어있을 때 --%>
          <c:when test="${empty inquiryList}">
            <div class="inquiry-list-empty" role="status" aria-label="문의 없음">
              <div class="inquiry-list-empty-icon" aria-hidden="true">
                <span class="material-symbols-outlined">edit_document</span>
              </div>
              <p class="inquiry-list-empty-title">등록한 문의가 없어요</p>
              <p class="inquiry-list-empty-sub">궁금한 점이 있다면 상품 문의를 남겨보세요</p>
            </div>
          </c:when>

          <%-- 2. 문의 내역이 있을 때 --%>
          <c:otherwise>
            <div class="inquiry-list-cards" aria-label="문의 목록">
              <c:forEach var="inquiry" items="${inquiryList}">
                <article class="inquiry-list-card" 
                         data-inquiry-card="${inquiry.inquiryNo}" 
                         data-inquiry-status="${inquiry.inquiryStatus == 0 ? 'waiting' : 'done'}">
                  
                  <div class="inquiry-list-top">
                    <div class="inquiry-list-top__left">
                      <div class="inquiry-list-thumb-wrap">
                        <img src="${pageContext.request.contextPath}/uploads/products/${inquiry.productImage}" 
                             alt="${inquiry.productName}" class="inquiry-list-thumb" width="72" height="72" loading="lazy"/>
                      </div>
                      <div class="inquiry-list-product-meta">
                        <p class="inquiry-list-brand">${inquiry.productBrand}</p>
                        <p class="inquiry-list-name">${inquiry.productName}</p>
                      </div>
                    </div>

                    <div class="inquiry-list-menu-wrap">
                      <c:if test="${inquiry.inquiryStatus == 0}">
                        <button type="button"
                                class="inquiry-list-menu-btn"
                                aria-label="더보기 메뉴"
                                aria-haspopup="true"
                                aria-expanded="false"
                                id="inquiryMenuBtn${inquiry.inquiryNo}">
                          <span class="material-icons" aria-hidden="true">more_horiz</span>
                        </button>
                        
                        <div class="inquiry-list-dropdown hidden" id="inquiryDropdown${inquiry.inquiryNo}" role="menu" aria-hidden="true">
                          <button type="button" class="inquiry-list-dropdown__item" data-menu-action="edit" data-id="${inquiry.inquiryNo}" role="menuitem">수정하기</button>
                          <button type="button" class="inquiry-list-dropdown__item inquiry-list-dropdown__item--danger" data-menu-action="delete" data-id="${inquiry.inquiryNo}" role="menuitem">삭제하기</button>
                        </div>
                      </c:if>
                    </div>
                  </div>

                  <div class="inquiry-list-divider" aria-hidden="true"></div>

                  <div class="inquiry-list-body">
                    <p class="inquiry-list-question">${inquiry.inquiryContent}</p>
					  <p class="inquiry-list-meta">
					    <c:choose>
					      <c:when test="${inquiry.isNameHidden == 1}">
					        익명
					      </c:when>
					      <c:otherwise>
					        ${sessionScope.loginUser.userName}
					      </c:otherwise>
					    </c:choose>
					    | ${inquiry.createdAt}
					  <c:if test="${inquiry.isSecret == 1}">
					      <span class="material-icons" 
					            style="font-size: 14px; vertical-align: middle; color: #aaa; margin-left: 2px;">lock</span>
					    </c:if>
					  </p>

                    <c:choose>
                      <c:when test="${inquiry.inquiryStatus == 0}">
                        <div class="inquiry-list-answer-wait" role="status">아직 답변 전이에요</div>
                      </c:when>
                      <c:otherwise>
                        <div class="inquiry-list-answer-card" aria-label="판매자 답변">
                          <p class="inquiry-list-answer-label">판매자 답변 | ${inquiry.answeredAt}</p>
                          <p class="inquiry-list-answer-body">${inquiry.answerContent}</p>
                        </div>
                      </c:otherwise>
                    </c:choose>
                  </div>
                </article>
              </c:forEach>
            </div>
          </c:otherwise>
        </c:choose>
      </main>
    </div>
  </div>
<div class="inquiry-write-modal hidden" id="inquiryDeleteModal" role="dialog" aria-modal="true">
  <div class="inquiry-write-dim" data-modal-dismiss="delete"></div>
  
  <div class="inquiry-write-modal-card">
    <p class="inquiry-write-modal-message">문의를 삭제하시겠습니까?</p>
    
    <p class="inquiry-write-modal-sub">삭제된 내용은 복구할 수 없어요.</p>
    
    <div class="inquiry-write-modal-actions inquiry-write-modal-actions--double">
      <button type="button" class="inquiry-write-modal-btn inquiry-write-modal-btn--ghost" data-modal-dismiss="delete">취소</button>
      
      <button type="button" class="inquiry-write-modal-btn" id="confirmDeleteBtn" 
              style="background: #D84C33; color: #fff;">삭제하기</button>
    </div>
  </div>
</div>
  <script src="${pageContext.request.contextPath}/js/inquiry-list.js"></script>
</body>
</html>
