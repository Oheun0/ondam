<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  // 대시보드 "페이지 엔트리" (레이아웃 템플릿에 콘텐츠를 꽂아 렌더링)
  request.setAttribute("sellerName", "온담스토어");
  request.setAttribute("sellerPageTitle", "대시보드");
  request.setAttribute("sellerActiveMenu", "dashboard");
  request.setAttribute("sellerContentPage", "/WEB-INF/views/seller/dashboard-content.jsp");
  request.setAttribute("sellerExtraCss", "/css/seller/seller-dashboard.css");
  request.setAttribute("sellerExtraJs", "/js/seller/dashboard.js");
%>
<jsp:include page="/WEB-INF/views/seller/layout/seller-main-layout.jsp" />