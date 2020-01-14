package com.dheeraj.expensesharenew.groupinfo.model;

public class InvitationModel {
    public String groupId, groupName, senderName, senderId;

    public InvitationModel(String groupId, String groupName, String senderName, String senderId) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.senderName = senderName;
        this.senderId = senderId;
    }
}
