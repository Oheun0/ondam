package com.ondam.user.controller;

import com.ondam.common.controller.Controller;
import com.ondam.user.dao.UserDAO;
import com.ondam.user.dto.UserDTO;
import com.ondam.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class PreferenceSaveController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/login";
        }

        int userNo = loginUser.getUserNo();
        String saveScope = request.getParameter("saveScope");

        if ("body".equals(saveScope)) {
            String heightStr = request.getParameter("userHeight");
            String weightStr = request.getParameter("userWeight");

            int userHeight = (heightStr != null && !heightStr.isEmpty()) ? Integer.parseInt(heightStr) : 0;
            int userWeight = (weightStr != null && !weightStr.isEmpty()) ? Integer.parseInt(weightStr) : 0;
            
            UserDAO userDao = new UserDAO();
            userDao.updateBodyInfo(userNo, userHeight, userWeight);
            
        } else if ("preference".equals(saveScope)) {
            String[] colors = request.getParameterValues("userPreferColor");
            String[] hobbies = request.getParameterValues("userHobby");
            
            UserService userService = new UserService();
            userService.updateUserPreferences(userNo, colors, hobbies);
        }
        return "redirect:/preference";
    }
}