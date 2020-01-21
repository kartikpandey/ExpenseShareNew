package com.dheeraj.expensesharenew.groupinfo.model;

public class InvitationModel {
    public String groupId, groupName, senderName, senderId, notificationId, notificationType;

    public InvitationModel(String notificationType, String groupId, String groupName, String senderName, String senderId, String notificationId) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.senderName = senderName;
        this.senderId = senderId;
        this.notificationId = notificationId;
        this.notificationType = notificationType;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String message) {
        this.notificationId = message;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }
}
