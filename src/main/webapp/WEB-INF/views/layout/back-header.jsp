<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--
  공통 뒤로가기 전용 상단바 (좌측 버튼만). 스타일은 common.css .app-back-header
--%>
<header class="app-back-header" role="banner">
  <button type="button" class="app-back-header__btn" id="appBackHeaderBtn" aria-label="뒤로가기" onclick="history.back();">
    <span class="material-icons" aria-hidden="true">arrow_back_ios_new</span>
  </button>
</header>