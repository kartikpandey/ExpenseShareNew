package com.dheeraj.expensesharenew.groupdashboard;

public class GroupMember {
    String memberUid, memberName, memberType;

    public GroupMember(String memberUid, String memberName, String memberType) {
        this.memberUid = memberUid;
        this.memberName = memberName;
        this.memberType = memberType;
    }

    public String getMemberUid() {
        return memberUid;
    }

    public void setMemberUid(String memberUid) {
        this.memberUid = memberUid;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }
}
