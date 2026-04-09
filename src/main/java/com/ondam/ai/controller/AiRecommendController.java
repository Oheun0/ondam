package com.ondam.ai.controller;

import java.io.File;
import java.util.Vector;
import com.ondam.ai.dto.AiRecommendDTO;
import com.ondam.ai.service.AiRecommendService;
import com.ondam.common.controller.Controller;
import com.ondam.user.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AiRecommendController implements Controller {
    private final AiRecommendService recService = new AiRecommendService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");

      

        if (loginUser == null) {
            return "redirect:/login";
        }

        String realPath = request.getServletContext().getRealPath("/");
        String scriptPath = realPath + "scripts" + File.separator + "shop_recommend.py";
        


        // 서비스 호출 및 결과 로그
        Vector<AiRecommendDTO> aiRecList = recService.getTodayRecommendations(loginUser.getUserNo(), scriptPath);
        
        

        request.setAttribute("aiRecList", aiRecList);
        request.setAttribute("userName", loginUser.getUserName());

        return "ai/ai-recommend";
    }
}