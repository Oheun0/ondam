package com.ondam.user.controller;

import com.ondam.common.controller.Controller;
import com.ondam.user.dao.UserDAO;
import com.ondam.user.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ProfileController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        HttpSession session = request.getSession();

        UserDTO sessionUser = (UserDTO) session.getAttribute("loginUser");

        if (sessionUser == null) {
            return "redirect:/login"; 
        }

        String userId = sessionUser.getUserId();

        UserDAO dao = new UserDAO();
        UserDTO userInfo = dao.getUserId(userId); 

        request.setAttribute("loginUser", userInfo); 
        
        return "mypage/profile";
    }
}