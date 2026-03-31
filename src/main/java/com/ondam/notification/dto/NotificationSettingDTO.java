package com.ondam.notification.dto;

public class NotificationSettingDTO {

    private int notificationSettingNo;
    private int userNo;
    private int notificationType;
    private int isEnabled;

    public NotificationSettingDTO() {}

    public NotificationSettingDTO(int notificationSettingNo, int userNo, int notificationType,
                                  int isEnabled) {
        this.notificationSettingNo = notificationSettingNo;
        this.userNo = userNo;
        this.notificationType = notificationType;
        this.isEnabled = isEnabled;
    }

    public int getNotificationSettingNo() {
        return notificationSettingNo;
    }

    public void setNotificationSettingNo(int notificationSettingNo) {
        this.notificationSettingNo = notificationSettingNo;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public int getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(int notificationType) {
        this.notificationType = notificationType;
    }

    public int getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(int isEnabled) {
        this.isEnabled = isEnabled;
    }
}
