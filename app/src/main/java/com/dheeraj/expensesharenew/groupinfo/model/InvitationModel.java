package com.dheeraj.expensesharenew.groupinfo.model;

public class InvitationModel {
    public String groupId, groupName, senderName, senderId, message, notificationType;

    public InvitationModel(String notificationType, String groupId, String groupName, String senderName, String senderId, String message) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.senderName = senderName;
        this.senderId = senderId;
        this.message = message;
        this.notificationType = notificationType;
    }
}
