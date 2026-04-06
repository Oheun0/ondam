<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>나의 리뷰</title>

  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-detail.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/my-review.css">
</head>
<body data-context-path="${pageContext.request.contextPath}">
  <div class="detail-shell">
    <div id="reviewMyPageRoot" class="detail-page-inner detail-page-inner--sticky-header review-my-page">
      <div class="review-my-body">
        <div class="review-my-sticky-head">
          <div class="review-my-header-wrap">
            <jsp:include page="/WEB-INF/views/layout/back-header.jsp"/>
            <h1 class="review-my-header-title">나의 리뷰</h1>
          </div>

          <div class="review-my-tab-card">
            <div class="review-my-tab-bar" role="tablist" aria-label="나의 리뷰 구분">
              <button type="button"
                      class="review-my-tab-btn active"
                      role="tab"
                      id="reviewMyTabWrite"
                      aria-selected="true"
                      aria-controls="reviewMyPanelWrite"
                      data-review-my-tab="write">
                작성 가능한 후기
              </button>
              <button type="button"
                      class="review-my-tab-btn"
                      role="tab"
                      id="reviewMyTabWritten"
                      aria-selected="false"
                      aria-controls="reviewMyPanelWritten"
                      tabindex="-1"
                      data-review-my-tab="written">
                작성한 후기
              </button>
            </div>
          </div>
        </div>

        <div class="review-my-panels" id="reviewMyPanels">
          <div class="review-my-tab-panel"
               id="reviewMyPanelWrite"
               role="tabpanel"
               aria-labelledby="reviewMyTabWrite"
               data-review-my-panel="write"
               aria-hidden="false">
            <jsp:include page="/WEB-INF/views/product/review/review-write-list-panel.jsp"/>
          </div>
          <div class="review-my-tab-panel hidden"
               id="reviewMyPanelWritten"
               role="tabpanel"
               aria-labelledby="reviewMyTabWritten"
               data-review-my-panel="written"
               aria-hidden="true">
            <jsp:include page="/WEB-INF/views/product/review/review-written-list-panel.jsp"/>
          </div>
        </div>
      </div>
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/js/my-review.js"></script>
</body>
</html>
