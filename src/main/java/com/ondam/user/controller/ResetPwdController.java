package com.ondam.user.controller;

import com.ondam.common.controller.Controller;
import com.ondam.user.dao.UserDAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ResetPwdController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (request.getMethod().equals("GET")) {
            return "redirect:/find-pwd";
        }

        int userNo = Integer.parseInt(request.getParameter("userNo"));
        String newPwd = request.getParameter("userPwd");

        UserDAO dao = new UserDAO();
        int result = dao.updatePassword(userNo, newPwd);

        if (result > 0) {
            return "user/login"; 
        } else {
            request.setAttribute("errorMessage", "비밀번호 변경에 실패했습니다. 다시 시도해주세요.");
            return "user/reset-pwd";
        }
    }
}
