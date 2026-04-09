package com.ondam.ai.controller;

import com.ondam.common.controller.Controller;
import com.ondam.user.dto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AiIntroController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 이 곳은 파이썬을 돌리지 않으므로 누르자마자 즉시 화면이 뜹니다.
    	
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");


        if (loginUser == null) {
            return "redirect:/login";
        }
    	
        request.setAttribute("bottomNav", "ai");
        return "ai/ai-intro"; 
    }
}