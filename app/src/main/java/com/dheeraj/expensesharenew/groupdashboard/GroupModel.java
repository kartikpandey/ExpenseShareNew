package com.dheeraj.expensesharenew.groupdashboard;

import java.util.ArrayList;

public class GroupModel {
    String groupId, groupName;
    ArrayList<GroupMember> groupMembersList;

    public GroupModel() {
    }

    public GroupModel(String groupId, String groupName, ArrayList<GroupMember> groupMembersList) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupMembersList = groupMembersList;
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

    public ArrayList<GroupMember> getGroupMembersList() {
        return groupMembersList;
    }

    public void setGroupMembersList(ArrayList<GroupMember> groupMembersList) {
        this.groupMembersList = groupMembersList;
    }
}
