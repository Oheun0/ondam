package com.ondam.user.controller;

import com.ondam.common.controller.Controller; 
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SignupStartController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "user/signup-start"; 
    }
}