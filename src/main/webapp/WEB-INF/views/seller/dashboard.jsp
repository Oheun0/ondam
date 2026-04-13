<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /* Preview 등에서 컨트롤러 없이 열릴 때 기본값 */
  if (request.getAttribute("sellerContentPage") == null) {
    request.setAttribute("sellerPageTitle", "대시보드");
    request.setAttribute("sellerActiveMenu", "dashboard");
    request.setAttribute("sellerContentPage", "/WEB-INF/views/seller/dashboard-content.jsp");
    request.setAttribute("sellerExtraCss", "/css/seller/seller-dashboard.css");
    request.setAttribute("sellerExtraJs", "/js/seller/dashboard.js");
  }
%>
<jsp:include page="/WEB-INF/views/seller/layout/seller-main-layout.jsp" />
