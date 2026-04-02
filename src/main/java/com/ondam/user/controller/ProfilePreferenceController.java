package com.ondam.user.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ondam.common.controller.Controller;
import com.ondam.user.dao.UserDAO;
import com.ondam.user.dao.UserHobbyDAO;
import com.ondam.user.dao.UserPreferColorDAO;
import com.ondam.user.dto.UserDTO;
import com.ondam.user.dto.UserHobbyDTO;
import com.ondam.user.dto.UserPreferColorDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ProfilePreferenceController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        HttpSession session = request.getSession();
        UserDTO sessionUser = (UserDTO) session.getAttribute("loginUser");

        if (sessionUser == null) {
            return "redirect:/login";
        }
        
        int userNo = sessionUser.getUserNo();
        
        UserDAO dao = new UserDAO();
        UserDTO userInfo = dao.getUserId(sessionUser.getUserId());
        request.setAttribute("prefUserHeight", userInfo.getUserHeight());
        request.setAttribute("prefUserWeight", userInfo.getUserWeight());
        request.setAttribute("loginUser", userInfo);
        
        UserHobbyDAO hobbyDao = new UserHobbyDAO();
        List<UserHobbyDTO> hobbyDTOs = hobbyDao.getHobbyList(userNo);
        List<String> hobbyNames = new ArrayList<>();
        
        for (UserHobbyDTO dto : hobbyDTOs) {
            hobbyNames.add(dto.getUserHobby());
        }
        request.setAttribute("userHobbyList", hobbyNames);
        
        UserPreferColorDAO colorDao = new UserPreferColorDAO();
        List<UserPreferColorDTO> colorDTOs = colorDao.getUserPreferColor(userNo); 
        
        StringBuilder sb = new StringBuilder();
        if (colorDTOs != null) {
            for (int i = 0; i < colorDTOs.size(); i++) {
                sb.append(colorDTOs.get(i).getPreferColor());
                if (i < colorDTOs.size() - 1) sb.append(",");
            }
        }
        request.setAttribute("prefPreferColor", sb.toString());
        
        return "mypage/profile-preference";
    }
}