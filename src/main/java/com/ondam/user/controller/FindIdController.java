package com.ondam.user.controller;

import com.ondam.common.controller.Controller;
import com.ondam.user.dao.UserDAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FindIdController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (request.getMethod().equals("GET")) {
            return "user/find-id";
        }

        String name = request.getParameter("userName");
        String phone = request.getParameter("phone1") + request.getParameter("phone2") + request.getParameter("phone3");

        UserDAO dao = new UserDAO();
        String foundId = dao.findUserId(name, phone);

        if (foundId != null) {
            request.setAttribute("foundId", foundId);
            return "user/find-id-result";
        } else {
            request.setAttribute("errorMessage", "일치하는 정보가 없습니다.");
            return "user/find-id";
        }
    }
}