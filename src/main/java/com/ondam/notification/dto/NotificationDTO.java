package com.ondam.notification.dto;

public class NotificationDTO {

    private int notificationNo;
    private int userNo;
    private int notificationType;
    private String notificationContent;
    private int isRead;
    private int refNo;
    private String createdAt;

    public NotificationDTO() {}

    public NotificationDTO(int notificationNo, int userNo, int notificationType, String notificationContent,
                           int isRead, int refNo, String createdAt) {
        this.notificationNo = notificationNo;
        this.userNo = userNo;
        this.notificationType = notificationType;
        this.notificationContent = notificationContent;
        this.isRead = isRead;
        this.refNo = refNo;
        this.createdAt = createdAt;
    }

    public int getNotificationNo() {
        return notificationNo;
    }

    public void setNotificationNo(int notificationNo) {
        this.notificationNo = notificationNo;
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

    public String getNotificationContent() {
        return notificationContent;
    }

    public void setNotificationContent(String notificationContent) {
        this.notificationContent = notificationContent;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public int getRefNo() {
        return refNo;
    }

    public void setRefNo(int refNo) {
        this.refNo = refNo;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
