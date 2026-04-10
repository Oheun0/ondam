package com.ondam.seller.controller;

import com.ondam.common.controller.Controller;
import com.ondam.seller.service.SellerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SellerResetPwFormController implements Controller {

    private final SellerService sellerService = new SellerService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String method = request.getMethod();
        HttpSession session = request.getSession();
        
        // 보안 검사: 1단계를 거치지 않고 강제로 주소를 쳐서 들어온 경우 쫓아냅니다.
        String targetId = (String) session.getAttribute("resetTargetId");
        if (targetId == null) {
            return "redirect:/seller/auth/reset-password";
        }

        // GET: 새 비밀번호 설정 화면 띄우기
        if (method.equals("GET")) {
            return "seller/auth/reset-password-form"; 
        }

        // POST: 새 비밀번호 DB에 업데이트하기
        if (method.equals("POST")) {
            String newPw = request.getParameter("newPw");

            boolean result = sellerService.resetPassword(targetId, newPw);

            if (result) {
                // 성공하면 임시로 저장했던 세션 삭제하고 로그인 화면으로 보냄
                session.removeAttribute("resetTargetId");
                return "redirect:/seller/auth?reset=success"; 
            } else {
                request.setAttribute("resetFormError", "비밀번호 변경에 실패했습니다. 다시 시도해주세요.");
                return "seller/auth/reset-password-form";
            }
        }
        return null;
    }
}