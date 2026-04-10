package com.ondam.common.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PreviewController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String page = request.getParameter("page");

        // page 값이 없으면 기본 홈 화면으로 이동
        if (page == null || page.trim().isEmpty()) {
            return "home/home";
        }

        page = page.trim();

        // 보안상 이상한 경로 차단
        if (page.contains("..") || page.startsWith("/") || page.endsWith(".jsp")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 preview 경로입니다.");
            return null;
        }

        // 필요하면 여기서 테스트용 더미 데이터 미리 넣을 수 있음
        request.setAttribute("previewMode", true);

        /* 화면만 볼 때: 검색 결과 JSP에 searchQuery 전달 (q 없으면 기본 "니트") */
        if ("product/search-result".equals(page)) {
            String q = request.getParameter("q");
            if (q == null || q.trim().isEmpty()) {
                q = "니트";
            } else {
                q = q.trim();
            }
            request.setAttribute("searchQuery", q);
        }

        // 예시 더미 데이터
        request.setAttribute("mockUserName", "김지현");
        request.setAttribute("mockInviteCode", "ABCD-1234");
        request.setAttribute("mockGroupName", "내 사람");
        request.setAttribute("mockWalletBalance", 50000);

        // page 파라미터 값을 그대로 논리 뷰 이름으로 사용
        // 예: group/group-empty -> /WEB-INF/views/group/group-empty.jsp
        return page;
    }
}