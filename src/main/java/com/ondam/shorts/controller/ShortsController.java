package com.ondam.shorts.controller;

import java.util.Vector;
import com.ondam.common.controller.Controller;
import com.ondam.shorts.service.ShortsService;
import com.ondam.user.dto.UserDTO;
import com.ondam.shorts.dto.ShortsDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ShortsController implements Controller {
    
    private final ShortsService shortsService = new ShortsService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/login";
        }
        // 비즈니스 로직(필터링, 셔플)은 Service가 처리하고 결과만 받아옴
        Vector<ShortsDTO> publicShorts = shortsService.getPublicAndShuffledShorts();
        
        request.setAttribute("shortsList", publicShorts);
            
        return "shorts/shorts";
    }
}