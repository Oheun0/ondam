<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  String sellerName = (String) request.getAttribute("sellerName");
  if (sellerName == null) sellerName = "온담스토어";
%>
<header class="seller-header" role="banner">
  <div class="seller-header__left">
    <%-- 좌측은 비움(브랜드는 사이드바에 고정) --%>
  </div>

  <div class="seller-header__right">
    <div class="seller-header__seller" aria-label="판매자 정보">
      <span class="seller-header__seller-name"><%= sellerName %>님</span>
    </div>

    <button type="button" class="seller-header__icon-btn" id="sellerHeaderNotifyBtn" aria-label="알림">
      <span class="material-icons-outlined" aria-hidden="true">notifications</span>
    </button>

    <button type="button" class="seller-header__logout-btn" id="sellerHeaderLogoutBtn">
      로그아웃
    </button>
  </div>
</header>

