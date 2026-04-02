package com.ondam.user.controller;

import com.ondam.common.controller.Controller;
import com.ondam.user.dto.UserAddressDTO;
import com.ondam.user.dto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SignupStep2AddressController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String method = request.getMethod();

        if (method.equals("GET")) {
            return "user/signup-step2-address";
        }

        if (method.equals("POST")) {
            HttpSession session = request.getSession();
            
            UserDTO signupUser = (UserDTO) session.getAttribute("signupUser");
            if (signupUser == null) {
                return "redirect:/signup-step1-basic";
            }

            UserAddressDTO signupAddress = new UserAddressDTO();

            signupAddress.setAddressName(request.getParameter("addressName"));
            signupAddress.setReceiverName(request.getParameter("receiverName"));
            signupAddress.setUserZipcode(request.getParameter("userZipcode"));
            signupAddress.setUserAddress(request.getParameter("userAddress"));
            signupAddress.setUserDetailAddress(request.getParameter("userDetailAddress"));

            String phone1 = request.getParameter("phone1");
            String phone2 = request.getParameter("phone2");
            String phone3 = request.getParameter("phone3");
            if (phone1 != null && phone2 != null && phone3 != null) {
                signupAddress.setReceiverTel(phone1 + "-" + phone2 + "-" + phone3);
            }

            String isDefaultStr = request.getParameter("isDefault");
            signupAddress.setIsDefault(isDefaultStr != null ? 1 : 0);

            signupUser.setSignupStep(2);
            session.setAttribute("signupUser", signupUser);

            session.setAttribute("signupAddress", signupAddress);

            return "redirect:/signup-step3-preference";
        }
        return null;
    }
}