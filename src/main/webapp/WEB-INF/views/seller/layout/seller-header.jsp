<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  String title = (String) request.getAttribute("sellerPageTitle");
  if (title == null) title = "대시보드";
  String sellerName = (String) request.getAttribute("sellerName");
  if (sellerName == null) sellerName = "온담스토어";
%>
<header class="seller-header" role="banner">
  <div class="seller-header__left">
    <h1 class="seller-header__title"><%= title %></h1>
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

