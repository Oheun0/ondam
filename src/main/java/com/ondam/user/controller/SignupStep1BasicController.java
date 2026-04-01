package com.ondam.user.controller;

import com.ondam.common.controller.Controller;
import com.ondam.user.dto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SignupStep1BasicController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String method = request.getMethod();

        if (method.equals("GET")) {
            return "user/signup-step1-basic"; 
        }

        if (method.equals("POST")) {
            UserDTO signupUser = new UserDTO();

            signupUser.setUserName(request.getParameter("userName"));
            signupUser.setUserNick(request.getParameter("userNick"));
            signupUser.setUserId(request.getParameter("userId"));
            signupUser.setUserPwd(request.getParameter("userPwd"));

            String phone1 = request.getParameter("phone1");
            String phone2 = request.getParameter("phone2");
            String phone3 = request.getParameter("phone3");
            if (phone1 != null && phone2 != null && phone3 != null) {
                signupUser.setUserPhoneNumber(phone1 + "-" + phone2 + "-" + phone3);
            }

            String email1 = request.getParameter("email1");
            String email2 = request.getParameter("email2");
            if (email1 != null && email2 != null && !email1.isEmpty()) {
                signupUser.setUserEmail(email1 + "@" + email2);
            }

            String year = request.getParameter("birthYear");
            String month = request.getParameter("birthMonth");
            String day = request.getParameter("birthDay");

            if (year != null && !year.equals("년도") && 
                month != null && !month.equals("월") && 
                day != null && !day.equals("일")) {

                month = month.length() == 1 ? "0" + month : month;
                day = day.length() == 1 ? "0" + day : day;
                
                signupUser.setUserBirth(year + "-" + month + "-" + day);
            }

            int userGender = parseInt(request.getParameter("userGender"), 0);
            int joinReason = parseInt(request.getParameter("joinReason"), 1);
            
            signupUser.setUserGender(userGender);
            signupUser.setJoinReason(joinReason);

            signupUser.setSignupStep(1);

            HttpSession session = request.getSession();
            session.setAttribute("signupUser", signupUser);

            return "redirect:/signup-step2-address";
        }
        return null;
    }


    // 형변환
    private int parseInt(String value, int defaultValue) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}