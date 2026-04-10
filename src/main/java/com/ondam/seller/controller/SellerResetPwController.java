package com.ondam.seller.controller;

import com.ondam.common.controller.Controller;
import com.ondam.seller.service.SellerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SellerResetPwController implements Controller {

    private final SellerService sellerService = new SellerService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String method = request.getMethod();

        // GET: 비밀번호 찾기(인증) 화면 띄우기
        if (method.equals("GET")) {
            return "seller/auth/reset-password"; 
        }

        // POST: 화면에서 넘어온 아이디/이메일 검증하기
        if (method.equals("POST")) {
            String sellerId = request.getParameter("sellerId");
            String sellerEmail = request.getParameter("sellerEmail");

            // Service를 통해 ID와 Email이 일치하는지 확인
            boolean isVerified = sellerService.verifySellerForReset(sellerId, sellerEmail);

            if (isVerified) {
                // 성공! 다음 페이지(비밀번호 변경창)에서 누구의 비밀번호를 바꿀지 알아야 하므로 세션에 잠시 저장
                HttpSession session = request.getSession();
                session.setAttribute("resetTargetId", sellerId); 
                
                return "redirect:/seller/auth/reset-password-form";
            } else {
                // 실패
                request.setAttribute("resetError", "가입된 정보가 없거나 이메일이 일치하지 않습니다.");
                return "seller/auth/reset-password";
            }
        }
        return null;
    }
}