package com.ondam.user.controller;

import com.ondam.common.controller.Controller;
import com.ondam.user.dao.UserAddressDAO;
import com.ondam.user.dto.UserAddressDTO;
import com.ondam.user.dto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AddressFormController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }
        String mode = request.getParameter("mode");
        if (mode == null) {
            mode = "add";
        }
        
        if ("edit".equals(mode)) {
            String addressIdStr = request.getParameter("addressId");
            
            if (addressIdStr != null && !addressIdStr.isEmpty()) {
                int addressNo = Integer.parseInt(addressIdStr);

                UserAddressDAO dao = new UserAddressDAO();
                UserAddressDTO addrInfo = dao.getAddressByNo(addressNo);

                request.setAttribute("addrInfo", addrInfo);
            }
        }
        request.setAttribute("mode", mode);
        
        // 배송지 수정 도와주기에서 추가하는 코드 시작
        String targetUserNoParam = request.getParameter("targetUserNo");
        boolean isHelperMode = false;
        int targetUserNo;

        if (targetUserNoParam != null && !targetUserNoParam.isEmpty()) {
            targetUserNo = Integer.parseInt(targetUserNoParam);
            isHelperMode = true;
        } else {
            targetUserNo = loginUser.getUserNo();
        }

        request.setAttribute("isHelperMode", isHelperMode);
        request.setAttribute("targetUserNo", targetUserNo);
        // 배송지 수정에서 도와주는 부분 코드 끝
        
        return "mypage/profile-address-form"; 
    }
}