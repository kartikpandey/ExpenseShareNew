package com.dheeraj.expensesharenew.userinfo;

import com.dheeraj.expensesharenew.GroupModel;

import java.util.ArrayList;

public class UserInfoModel {
    private String fName, lName, mobNo, uID, gender;
    private ArrayList<GroupModel> groupList;

    public UserInfoModel() {
    }

    public UserInfoModel(String fName, String lName, String mobNo, String uID, String gender, ArrayList<GroupModel> groupList) {
        this.fName = fName;
        this.lName = lName;
        this.mobNo = mobNo;
        this.uID = uID;
        this.gender = gender;
        this.groupList = groupList;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
