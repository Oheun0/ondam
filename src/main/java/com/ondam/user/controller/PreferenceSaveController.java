package com.ondam.user.controller;

import com.ondam.common.controller.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PreferenceSaveController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        String saveScope = request.getParameter("saveScope");

        if ("body".equals(saveScope)) {
            String userHeight = request.getParameter("userHeight");
            String userWeight = request.getParameter("userWeight");
            
            // DB 업데이트 로직 (dao.updateBodyInfo...)
            
        } else if ("preference".equals(saveScope)) {
            String[] colors = request.getParameterValues("userPreferColor");
            String[] hobbies = request.getParameterValues("userHobby");
            
            // DB 업데이트 로직 (dao.updateTasteInfo...)
        }

        return "redirect:/preference";
    }
}