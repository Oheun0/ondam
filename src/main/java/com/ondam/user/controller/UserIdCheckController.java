package com.ondam.user.controller;

import java.io.PrintWriter;
import com.ondam.common.controller.Controller;
import com.ondam.user.dao.UserDAO;
import com.ondam.user.dto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserIdCheckController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");

        UserDAO userDAO = new UserDAO();
        UserDTO user = userDAO.getUserId(userId); 

        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        if (user != null) {
            out.print("duplicate"); 
        } else {
            out.print("available"); 
        }
        
        out.flush();
        out.close();

        return null; 
    }
}