package com.ondam.user.controller;

import java.io.IOException;

import com.ondam.user.dao.UserDAO;
import com.ondam.user.dto.UserDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/user/withdrawProcess")
public class WithdrawController extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
        
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/views/user/login.jsp");
            return;
        }

        String isSocial = request.getParameter("isSocial");
        String inputPwd = request.getParameter("userPwd");

        UserDAO dao = new UserDAO();
        boolean isPass = false;

        if ("Y".equals(isSocial)) {
            isPass = true; 
        } else {
            isPass = dao.checkPassword(loginUser.getUserNo(), inputPwd);
        }

        if (isPass) {
            int result = dao.withdrawUser(loginUser.getUserNo());
            
            if (result > 0) {
                session.invalidate();
                response.sendRedirect(request.getContextPath() + "/main?msg=withdraw_success");
            } else {
                response.sendRedirect(request.getContextPath() + "/mypage?error=db_fail");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/mypage?error=pwd_mismatch");
        }
    }
}