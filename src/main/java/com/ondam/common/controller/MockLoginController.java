package com.ondam.common.controller;

import com.ondam.user.dto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MockLoginController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // DB에 있는 테스트 유저 (userNo=1, 박성현) 세션에 박아두기
        UserDTO mockUser = new UserDTO();
        mockUser.setUserNo(1);
        mockUser.setUserName("박성현");

        request.getSession().setAttribute("loginUser", mockUser);

        return "redirect:/main";
    }
}