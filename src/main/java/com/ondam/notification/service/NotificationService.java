package com.ondam.notification.service;

import java.util.Vector;

import com.ondam.notification.dao.NotificationDAO;
import com.ondam.notification.dto.NotificationDTO;

public class NotificationService {

	private NotificationDAO dao;

	public NotificationService() {
		this.dao = new NotificationDAO();
	}

	public Vector<NotificationDTO> getNotificationList(int userNo) {
		return dao.getNotification(userNo);
	}

	public boolean createNotification(NotificationDTO dto) {
		return dao.insertNotification(dto);
	}

	public boolean modifyNotification(NotificationDTO dto, int notificationNo) {
		return dao.updateNotification(dto, notificationNo);
	}

	public boolean removeNotification(int notificationNo) {
		return dao.deleteNotification(notificationNo);
	}
}

