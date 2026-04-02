package com.ondam.user.controller;

import com.ondam.common.controller.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AddressFormController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        String mode = request.getParameter("mode");
        
        if ("edit".equals(mode)) {
            String addressId = request.getParameter("addressId");
            // 💡 나중에는 여기서 addressId로 DB를 조회해서
            // 기존 주소 정보를 request.setAttribute()로 담아 화면에 뿌려줍니다!
        }
        return "mypage/profile-address-form"; 
    }
}