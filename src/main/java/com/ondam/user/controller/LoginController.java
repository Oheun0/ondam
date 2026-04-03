package com.ondam.user.controller;

import com.ondam.common.controller.Controller;
import com.ondam.user.dto.UserDTO;
import com.ondam.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginController implements Controller {

	private UserService userService;

	public LoginController() {
		userService = new UserService();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();

		if (method.equals("GET")) {
			return "user/login";
		}

		if (method.equals("POST")) {
			String userId = request.getParameter("userId");
			String userPwd = request.getParameter("userPwd");

			UserDTO loginUser = userService.login(userId, userPwd);

			if (loginUser != null) {
				HttpSession session = request.getSession();
				session.setAttribute("loginUser", loginUser);

				com.ondam.cart.service.CartService cartService = new com.ondam.cart.service.CartService();
			    int totalQty = cartService.refreshCartTotalQuantity(loginUser.getUserNo());
			    session.setAttribute("cartCount", totalQty);
			    
				return "redirect:/main";
			} else {
				request.setAttribute("오류", "아이디 또는 비밀번호가 일치하지 않습니다.");
				return "user/login";
			}
		}
		return null;
	}
}