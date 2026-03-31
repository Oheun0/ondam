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

		// GET 방식 처리 (로그인 화면 이동)
		if (method.equals("GET")) {
			return "user/login"; // /WEB-INF/views/user/login.jsp로 연결
		}

		// POST 방식 처리 (로그인 로직 수행)
		if (method.equals("POST")) {
			String userId = request.getParameter("userId");
			String userPwd = request.getParameter("userPwd");

			UserDTO loginUser = userService.login(userId, userPwd);

			if (loginUser != null) {
				HttpSession session = request.getSession();
				session.setAttribute("loginUser", loginUser);

				// 리다이렉트는 "redirect:" 접두사를 붙여 DispatcherServlet에 알림
				return "redirect:/main";
			} else {
				// 실패 시 에러 메시지를 담고 다시 로그인 페이지로
				request.setAttribute("오류", "아이디 또는 비밀번호가 일치하지 않습니다.");
				return "user/login";
			}
		}
		return null;
	}
}