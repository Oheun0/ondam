package com.ondam.user.controller;

import com.ondam.common.controller.Controller;
import com.ondam.notification.service.NotificationService;
import com.ondam.user.dto.UserDTO;
import com.ondam.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginController implements Controller {

	private UserService userService;
	private NotificationService notificationService;

	public LoginController() {
		userService = new UserService();
		notificationService = new NotificationService();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();
		String action = request.getParameter("action");

		if (method.equals("GET")) {
			if ("reactivate".equals(action)) {
				String userId = request.getParameter("userId");
				
				if (userId != null && !userId.trim().isEmpty()) {
					boolean isSuccess = userService.reactivateUser(userId); 
					
					if (isSuccess) {
						com.ondam.user.dao.UserDAO userDAO = new com.ondam.user.dao.UserDAO();
						UserDTO reactivatedUser = userDAO.getUserId(userId);
						
						if (reactivatedUser != null) {
							HttpSession session = request.getSession();
							session.setAttribute("loginUser", reactivatedUser);
							
							int unreadCount = notificationService.getUnreadCount(reactivatedUser.getUserNo());
							session.setAttribute("unreadCount", unreadCount);

							com.ondam.cart.service.CartService cartService = new com.ondam.cart.service.CartService();
							int totalQty = cartService.refreshCartTotalQuantity(reactivatedUser.getUserNo());
							session.setAttribute("cartCount", totalQty);
						}
						return "redirect:/main";
					}
				}
				return "redirect:/login?error=reactivate_fail";
			}
			return "user/login";
		}
		if (method.equals("POST")) {
			String userId = request.getParameter("userId");
			String userPwd = request.getParameter("userPwd");

			UserDTO loginUser = userService.login(userId, userPwd);

			if (loginUser != null) {
				if (loginUser.getIsActive() == 1) {
					return "redirect:/login?status=withdrawn&targetId=" + userId;
				} else {
					HttpSession session = request.getSession();
					session.setAttribute("loginUser", loginUser);
					
					int unreadCount = notificationService.getUnreadCount(loginUser.getUserNo());
					session.setAttribute("unreadCount", unreadCount);

					com.ondam.cart.service.CartService cartService = new com.ondam.cart.service.CartService();
					int totalQty = cartService.refreshCartTotalQuantity(loginUser.getUserNo());
					session.setAttribute("cartCount", totalQty);
					
					return "redirect:/main";
				}
			} else {
				request.setAttribute("오류", "아이디 또는 비밀번호가 일치하지 않습니다.");
				return "user/login";
			}
		}
		return null;
	}
}