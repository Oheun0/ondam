<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ondam.seller.dto.SellerDTO" %>
<%
  /* 우측 상단: 업체명(스토어명) 우선 → 없으면 담당자명 — 모든 판매자 화면 공통 */
  String sellerDisplayName = null;
  String vendorName = (String) session.getAttribute("vendorName");
  if (vendorName != null && !vendorName.trim().isEmpty()) {
    sellerDisplayName = vendorName.trim();
  }
  if (sellerDisplayName == null || sellerDisplayName.isEmpty()) {
    SellerDTO loginSeller = (SellerDTO) session.getAttribute("loginSeller");
    if (loginSeller != null && loginSeller.getSellerName() != null && !loginSeller.getSellerName().trim().isEmpty()) {
      sellerDisplayName = loginSeller.getSellerName().trim();
    }
  }
  if (sellerDisplayName == null || sellerDisplayName.isEmpty()) {
    sellerDisplayName = "판매자";
  }
%>
<header class="seller-header" role="banner">
  <div class="seller-header__left">
    <%-- 좌측은 비움(브랜드는 사이드바에 고정) --%>
  </div>

  <div class="seller-header__right">
    <div class="seller-header__seller" aria-label="판매자 정보">
      <span class="seller-header__seller-name"><%= sellerDisplayName %>님</span>
    </div>

    <button type="button" class="seller-header__icon-btn" id="sellerHeaderNotifyBtn" aria-label="알림">
      <span class="material-icons-outlined" aria-hidden="true">notifications</span>
    </button>

    <button type="button" class="seller-header__logout-btn" id="sellerHeaderLogoutBtn">
      로그아웃
    </button>
  </div>
</header>
<script>
  window.__ONDAM_CTX__ = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/js/seller/seller-header.js" defer></script>
