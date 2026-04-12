<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  request.setAttribute("sellerActiveMenu", "shorts");
  request.setAttribute("sellerPageTitle", "쇼츠 관리");
%>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>쇼츠 관리 | 온담 파트너</title>

  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Material+Icons+Outlined&display=swap" rel="stylesheet">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-layout.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-shorts.css" />
</head>
<body class="seller-app" data-context-path="${pageContext.request.contextPath}">
  <div class="seller-layout">
    <jsp:include page="/WEB-INF/views/seller/layout/seller-sidebar.jsp" />

    <div class="seller-main-area">
      <jsp:include page="/WEB-INF/views/seller/layout/seller-header.jsp" />

      <main class="seller-content seller-shorts-page" aria-label="쇼츠 관리">
        <header class="seller-shorts-head">
          <div>
            <h2 class="seller-shorts-title">쇼츠 관리</h2>
            <p class="seller-shorts-sub">등록된 쇼츠를 확인하고 공개 상태를 관리할 수 있어요</p>
          </div>
          <div class="seller-shorts-head-actions">
            <button type="button" class="seller-shorts-primary-btn" id="sellerNewShortsBtn">
              <span class="material-icons-outlined" aria-hidden="true">add</span>
              새 쇼츠 등록
            </button>
          </div>
        </header>

        <section class="seller-shorts-list" aria-label="쇼츠 목록">
          <c:choose>
            <c:when test="${empty shortsList}">
              <section class="seller-card seller-shorts-empty" aria-label="빈 상태">
                <div class="seller-shorts-empty-inner">
                  <div class="seller-shorts-empty-icon" aria-hidden="true">
                    <span class="material-icons-outlined">smart_display</span>
                  </div>
                  <h3 class="seller-shorts-empty-title">아직 등록된 쇼츠가 없어요</h3>
                  <p class="seller-shorts-empty-desc">새 쇼츠를 등록해 상품을 더 잘 보여주세요</p>
                  <button type="button" class="seller-shorts-primary-btn" id="sellerNewShortsBtnEmpty">새 쇼츠 등록</button>
                </div>
              </section>
            </c:when>
            <c:otherwise>
              <c:forEach var="shorts" items="${shortsList}">
                <article class="seller-shorts-card" data-shorts-no="${shorts.shortsNo}" data-public="${shorts.shortsState == 1 ? 'true' : 'false'}" data-product="${shorts.productNo}" data-video="${shorts.videoFile}">
                  <div class="seller-shorts-thumb" aria-label="쇼츠 썸네일">
                    <img src="${pageContext.request.contextPath}/uploads/shorts/${shorts.thumbnailImg}" alt="쇼츠 썸네일" onerror="this.src='${pageContext.request.contextPath}/images/default_thumb.jpg'">
                    <button type="button" class="seller-shorts-thumb-btn" data-action="preview" aria-label="미리보기">
                      <span class="material-icons-outlined" aria-hidden="true">play_circle</span>
                    </button>
                  </div>
                  <div class="seller-shorts-meta">
                    <div class="seller-shorts-title-row">
                      <h3 class="seller-shorts-card-title"><c:out value="${shorts.shortsTitle}" default="제목 없음"/></h3>
                      <c:if test="${shorts.shortsState == 1}">
                        <span class="seller-shorts-badge seller-shorts-badge--public">공개</span>
                      </c:if>
                      <c:if test="${shorts.shortsState == 0}">
                        <span class="seller-shorts-badge" style="background:#ffc107;color:#000;">생성 중</span>
                      </c:if>
                      <c:if test="${shorts.shortsState == -1}">
                        <span class="seller-shorts-badge" style="background:#dc3545;color:#fff;">생성 실패</span>
                      </c:if>
                      <c:if test="${shorts.shortsState == 2}">
                        <span class="seller-shorts-badge seller-shorts-badge--private">비공개</span>
                      </c:if>
                    </div>
                    <div class="seller-shorts-subrows">
                      <div class="seller-shorts-subrow"><span class="seller-shorts-k">연결 상품</span><span class="seller-shorts-v">${shorts.productName}</span></div>
                      <div class="seller-shorts-subrow"><span class="seller-shorts-k">업로드일</span><span class="seller-shorts-v">${shorts.createdAt}</span></div>
                    </div>
                  </div>
                  <div class="seller-shorts-right">
                    <div class="seller-shorts-metrics" aria-label="성과">
                      <div class="seller-shorts-metric"><span class="seller-shorts-metric-k">찜</span><span class="seller-shorts-metric-v">${shorts.wishCount}</span></div>
                    </div>
                    <div class="seller-shorts-actions" aria-label="관리">
                      <button type="button" class="seller-shorts-mini-btn seller-shorts-mini-btn--toggle" data-action="toggle">
                        ${shorts.shortsState == 1 ? '비공개 전환' : '공개 전환'}
                      </button>
                      <button type="button" class="seller-shorts-mini-btn" data-action="product">상품 보기</button>
                      <button type="button" class="seller-shorts-mini-btn" data-action="delete" style="color:red; border-color:red;">삭제</button>
                    </div>
                  </div>
                </article>
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </section>

      </main>

      <jsp:include page="/WEB-INF/views/seller/layout/seller-footer.jsp" />
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/js/seller/shorts-list.js"></script>
</body>
</html>