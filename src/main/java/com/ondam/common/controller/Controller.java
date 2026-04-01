package com.ondam.common.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface Controller {
    /**
     * @return 이동할 뷰의 이름 또는 리다이렉트 경로  (예: "user/login") ("redirect:/main")
     * @throws Exception 모든 예외를 상위(DispatcherServlet)로 던져서 공통 처리
     */
    String execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}