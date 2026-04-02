package com.ondam.notification.controller;

import java.util.Vector;

import com.ondam.common.controller.Controller;
import com.ondam.notification.dto.NotificationDTO;
import com.ondam.notification.service.NotificationService;
import com.ondam.user.dto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class NotificationController implements Controller {

	private NotificationService notificationService = new NotificationService();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) {
			return "redirect:/login";
		}

		String action = request.getParameter("action");
		if (action == null)
			action = "list";

		switch (action) {
		case "list":
			return list(request, response);
		case "markOneRead":
			return markOneRead(request, response);
		case "markAllRead":
			return markAllRead(request, response);
		case "deleteAll":
			return deleteAll(request, response);
		default:
			return "redirect:/notification";
		}
	}

	private String list(HttpServletRequest request, HttpServletResponse response) {
		UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
		Vector<NotificationDTO> vlist = notificationService.getNotificationList(loginUser.getUserNo());
		request.setAttribute("vlist", vlist);
		refreshUnreadCount(request);
		return "notification/list";
	}

//    private String markOneRead(HttpServletRequest request, HttpServletResponse response) {
//        String no = request.getParameter("notificationNo");
//        if (no != null) notificationService.markOneRead(Integer.parseInt(no));
//        return "redirect:/notification";
//    }

	private String markOneRead(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String no = request.getParameter("notificationNo");
		if (no == null)
			return null;

		int notificationNo = Integer.parseInt(no);
		notificationService.markOneRead(notificationNo);
		refreshUnreadCount(request);

		// 클릭한 알림 정보 조회 (notificationType, refNo 필요)
		NotificationDTO noti = notificationService.getNotificationByNo(notificationNo);

		response.setContentType("application/json; charset=UTF-8");
		if (noti != null) {
			response.getWriter().write("{\"ok\":true, \"notificationType\":" + noti.getNotificationType()
					+ ", \"refNo\":" + noti.getRefNo() + "}");
		} else {
			response.getWriter().write("{\"ok\":true, \"notificationType\":0, \"refNo\":0}");
		}
		return null;
	}

	private String markAllRead(HttpServletRequest request, HttpServletResponse response) {
		UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
		notificationService.markAllRead(loginUser.getUserNo());
		refreshUnreadCount(request);
		return "redirect:/notification";
	}

	private String deleteAll(HttpServletRequest request, HttpServletResponse response) {
		UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
		notificationService.removeAllNotification(loginUser.getUserNo());
		refreshUnreadCount(request);
		return "redirect:/notification";
	}

	// 공통 헬퍼 메서드
	private void refreshUnreadCount(HttpServletRequest request) {
		UserDTO loginUser = (UserDTO) request.getSession().getAttribute("loginUser");
		int count = notificationService.getUnreadCount(loginUser.getUserNo());
		request.getSession().setAttribute("unreadCount", count);
	}
}