<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  String active = (String) request.getAttribute("sellerActiveMenu");
  if (active == null) active = "dashboard";
%>
<aside class="seller-sidebar" aria-label="파트너 메뉴">
  <div class="seller-sidebar__brand" aria-label="온담 파트너">
    <div class="seller-sidebar__logo">
      <img src="${pageContext.request.contextPath}/images/logo/logo_3.svg" alt="온담" class="seller-sidebar__logo-img" width="92" height="28" decoding="async">
      <span class="seller-sidebar__badge">파트너</span>
    </div>
  </div>

  <nav class="seller-sidebar__nav" aria-label="판매자 메뉴">
    <a class="seller-nav-item <%= "dashboard".equals(active) ? "active" : "" %>" href="${pageContext.request.contextPath}/seller/dashboard">
      <span class="material-icons-outlined seller-nav-icon" aria-hidden="true">space_dashboard</span>
      <span class="seller-nav-label">대시보드</span>
    </a>
    
    <a class="seller-nav-item <%= "product".equals(active) ? "active" : "" %>" href="${pageContext.request.contextPath}/preview?page=seller/product/list">
      <span class="material-icons-outlined seller-nav-icon" aria-hidden="true">inventory_2</span>
      <span class="seller-nav-label">상품 관리</span>
    </a>
    
    <a class="seller-nav-item <%= "shorts".equals(active) ? "active" : "" %>" href="${pageContext.request.contextPath}/seller/shorts/list">
      <span class="material-icons-outlined seller-nav-icon" aria-hidden="true">smart_display</span>
      <span class="seller-nav-label">쇼츠 관리</span>
    </a>
    
    <a class="seller-nav-item <%= "order".equals(active) ? "active" : "" %>" href="${pageContext.request.contextPath}/seller/order?action=list">
      <span class="material-icons-outlined seller-nav-icon" aria-hidden="true">local_shipping</span>
      <span class="seller-nav-label">주문 / 배송 관리</span>
    </a>
    
    <a class="seller-nav-item <%= "review".equals(active) ? "active" : "" %>" href="${pageContext.request.contextPath}/preview?page=seller/review/list">
      <span class="material-icons-outlined seller-nav-icon" aria-hidden="true">rate_review</span>
      <span class="seller-nav-label">리뷰 관리</span>
    </a>
    
    <a class="seller-nav-item <%= "settlement".equals(active) ? "active" : "" %>" href="${pageContext.request.contextPath}/preview?page=seller/settlement/list">
      <span class="material-icons-outlined seller-nav-icon" aria-hidden="true">bar_chart</span>
      <span class="seller-nav-label">정산 · 매출</span>
    </a>
    <a class="seller-nav-item <%= "setting".equals(active) ? "active" : "" %>" href="${pageContext.request.contextPath}/seller/settings">

      <span class="material-icons-outlined seller-nav-icon" aria-hidden="true">settings</span>
      <span class="seller-nav-label">설정</span>
    </a>
  </nav>
</aside>