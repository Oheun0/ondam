package com.ondam.user.controller;

import java.io.IOException;

import com.ondam.user.dto.UserDTO;
import com.ondam.user.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserService userService;
    
    public LoginController() {
    	userService = new UserService();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId= request.getParameter("userId");
        String userPwd= request.getParameter("userPwd");
        
        UserDTO loginUser = userService.login(userId, userPwd);
        if(loginUser != null) {
        	HttpSession session = request.getSession();
        	session.setAttribute("loginUser", loginUser);
        	
        	//추후 main 컨트롤러 만들면 거기로 매핑
        	response.sendRedirect(request.getContextPath()+"/main");
        }else {
        	request.setAttribute("오류", "아이디 또는 비밀번호가 일치하지 않습니다.");
        	request.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(request, response);
        }
    }
}