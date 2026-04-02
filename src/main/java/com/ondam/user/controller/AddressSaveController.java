package com.ondam.user.controller;

import com.ondam.common.controller.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AddressSaveController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        // 1. 폼 데이터 받기
        String mode = request.getParameter("mode");
        String receiverName = request.getParameter("receiverName");
        String receiverPhone = request.getParameter("receiverPhone");
        String zipcode = request.getParameter("zipcode");
        String address1 = request.getParameter("address1");
        String address2 = request.getParameter("address2");
        String isDefault = request.getParameter("isDefault");

        // 2. 여기서 UserDAO를 호출하여 DB에 INSERT 또는 UPDATE를 수행합니다.
        // UserDAO dao = new UserDAO();
        // dao.saveAddress(...);

        // 3. 작업 완료 후 배송지 관리 화면으로 깔끔하게 리다이렉트 (새로고침 방지)
        return "redirect:/profile-address";
    }
}