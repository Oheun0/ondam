package com.ondam.notification.service;

import java.util.Vector;

import com.ondam.notification.dao.NotificationSettingDAO;
import com.ondam.notification.dto.NotificationSettingDTO;

public class NotificationSettingService {

	private NotificationSettingDAO dao;

	public NotificationSettingService() {
		this.dao = new NotificationSettingDAO();
	}

	public Vector<NotificationSettingDTO> getNotificationSettingList() {
		return dao.getNotificationSetting();
	}

	public boolean createNotificationSetting(NotificationSettingDTO dto) {
		return dao.insertNotificationSetting(dto);
	}

	public boolean modifyNotificationSetting(NotificationSettingDTO dto, int notificationSettingNo) {
		return dao.updateNotificationSetting(dto, notificationSettingNo);
	}

	public boolean removeNotificationSetting(int notificationSettingNo) {
		return dao.deleteNotificationSetting(notificationSettingNo);
	}
	
	public Vector<NotificationSettingDTO> getSettingsByUserNo(int userNo) {
		return dao.getSettingsByUserNo(userNo);
	}

	public boolean toggleSetting(int userNo, int notificationType, int isEnabled) {
		return dao.toggleSetting(userNo, notificationType, isEnabled);
	}
}