package com.ondam.notification.controller;

import java.util.Vector;
import com.ondam.common.controller.Controller;
import com.ondam.notification.dto.NotificationSettingDTO;
import com.ondam.notification.service.NotificationSettingService;
import com.ondam.user.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class NotificationSettingController implements Controller {

	private NotificationSettingService settingService = new NotificationSettingService();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) {
			return "redirect:/login";
		}

		String action = request.getParameter("action");
		
		if (action == null) {
			return viewSettings(request, response);
		}

		if ("toggle".equals(action)) {
			return toggleSetting(request, response);
		}

		return "redirect:/mypage";
	}

	private String viewSettings(HttpServletRequest request, HttpServletResponse response) {
		UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
		Vector<NotificationSettingDTO> settings = settingService.getSettingsByUserNo(loginUser.getUserNo());
		request.setAttribute("settings", settings);

		return "notification/notification-setting"; 
	}

	private String toggleSetting(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
		String typeParam = request.getParameter("notificationType");
		String enabledParam = request.getParameter("isEnabled");
		
		response.setContentType("application/json; charset=UTF-8");
		
		if (typeParam != null && enabledParam != null) {
			int notificationType = Integer.parseInt(typeParam);
			int isEnabled = Integer.parseInt(enabledParam);
			
			boolean isSuccess = settingService.toggleSetting(loginUser.getUserNo(), notificationType, isEnabled);
			
			if (isSuccess) {
				response.getWriter().write("{\"ok\":true, \"message\":\"설정이 변경되었습니다.\"}");
			} else {
				response.getWriter().write("{\"ok\":false, \"message\":\"변경 실패\"}");
			}
		} else {
			response.getWriter().write("{\"ok\":false, \"message\":\"잘못된 요청입니다.\"}");
		}
		
		return null;
	}
}