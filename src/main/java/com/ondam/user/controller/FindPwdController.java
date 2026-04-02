package com.ondam.user.controller;

import com.ondam.common.controller.Controller;
import com.ondam.user.dao.UserDAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FindPwdController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (request.getMethod().equals("GET")) {
            return "user/find-pwd";
        }

        String userId = request.getParameter("userId");
        String name = request.getParameter("userName");
        String phone = request.getParameter("phone1") + request.getParameter("phone2") + request.getParameter("phone3");

        UserDAO dao = new UserDAO();
        int userNo = dao.checkUserForPwdReset(userId, name, phone);

        if (userNo > 0) {
            request.setAttribute("userNo", userNo);
            return "user/reset-pwd"; 
        } else {
            request.setAttribute("errorMessage", "입력하신 정보와 일치하는 회원이 없습니다.");
            return "user/find-pwd";
        }
    }
}