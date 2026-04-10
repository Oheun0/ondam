package com.ondam.seller.controller;

import com.ondam.common.controller.Controller;
import com.ondam.seller.service.SellerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SellerFindIdController implements Controller {

    private final SellerService sellerService = new SellerService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String method = request.getMethod();

        // 1. 아이디 찾기 페이지 접속 (GET)
        if (method.equals("GET")) {
            return "seller/auth/find-id"; 
        }

        // 2. 아이디 찾기 폼 전송 (POST)
        if (method.equals("POST")) {
            String repName = request.getParameter("sellerManagerName");
            String email = request.getParameter("sellerEmail");

            // Service를 통해 DB 조회
            String sellerId = sellerService.findSellerId(repName, email);

            if (sellerId != null) {
                // 아이디가 존재하면 마스킹 처리 (뒤 4자리를 별표로)
                String maskedId;
                if (sellerId.length() <= 4) {
                    maskedId = sellerId.substring(0, 1) + "***";
                } else {
                    maskedId = sellerId.substring(0, sellerId.length() - 4) + "****";
                }
                // 결과를 request에 담아서 JSP로 전달
                request.setAttribute("foundId", maskedId);
            } else {
                // 일치하는 정보가 없을 경우 에러 메시지 전달
                request.setAttribute("findIdError", "일치하는 판매자 정보가 없습니다.");
            }
            
            // 결과를 가지고 다시 find-id 화면으로 포워딩
            return "seller/auth/find-id";
        }
        return null;
    }
}