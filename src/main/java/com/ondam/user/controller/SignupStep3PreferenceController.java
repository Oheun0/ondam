package com.ondam.user.controller;

import java.util.ArrayList;
import java.util.List;

import com.ondam.common.controller.Controller;
import com.ondam.user.dto.UserAddressDTO;
import com.ondam.user.dto.UserDTO;
import com.ondam.user.dto.UserHobbyDTO;
import com.ondam.user.dto.UserPreferColorDTO;
import com.ondam.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SignupStep3PreferenceController implements Controller {

    private UserService userService;

    public SignupStep3PreferenceController() {
        userService = new UserService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String method = request.getMethod();

        if (method.equals("GET")) {
            return "user/signup-step3-preference";
        }

        if (method.equals("POST")) {
            HttpSession session = request.getSession();
            UserDTO signupUser = (UserDTO) session.getAttribute("signupUser");
            UserAddressDTO signupAddress = (UserAddressDTO) session.getAttribute("signupAddress");

            if (signupUser == null || signupAddress == null) {
                return "redirect:/signup-step1-basic";
            }

            signupUser.setUserHeight(parseInt(request.getParameter("userHeight"), 0));
            signupUser.setUserWeight(parseInt(request.getParameter("userWeight"), 0));
            signupUser.setPreferPayment(parseInt(request.getParameter("preferPayment"), 0));

            signupUser.setSignupStep(3);
            signupUser.setSignUpCompleted(1);
            
            String[] colors = request.getParameterValues("userPreferColor");
            List<UserPreferColorDTO> colorList = new ArrayList<>();
            if (colors != null) {
                for (String color : colors) {
                    UserPreferColorDTO colorDTO = new UserPreferColorDTO();
                    colorDTO.setPreferColor(color);
                    colorList.add(colorDTO);
                }
            }

            String[] hobbies = request.getParameterValues("userHobby");
            List<UserHobbyDTO> hobbyList = new ArrayList<>();
            if (hobbies != null) {
                for (String hobby : hobbies) {
                    UserHobbyDTO hobbyDTO = new UserHobbyDTO();
                    hobbyDTO.setUserHobby(hobby);
                    hobbyList.add(hobbyDTO);
                }
            }

            int result = userService.insertCompleteSignup(signupUser, signupAddress, hobbyList, colorList);

            if (result > 0) {
                session.removeAttribute("signupUser");
                session.removeAttribute("signupAddress");
                return "redirect:/signup-complete";
            } else {
                request.setAttribute("errorMessage", "회원가입 처리 중 오류가 발생했습니다.");
                return "user/signup-step3-preference";
            }
        }
        return null;
    }

    // 형변환
    private int parseInt(String value, int defaultValue) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}