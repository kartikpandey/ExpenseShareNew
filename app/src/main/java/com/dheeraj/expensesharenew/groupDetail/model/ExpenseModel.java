package com.dheeraj.expensesharenew.groupDetail.model;

public class ExpenseModel {

    private String expenseDate, userId, userName, groupId, expenseAmount, expenseParticular;

    public ExpenseModel() {
    }

    public ExpenseModel(String expenseDate, String userId, String userName, String groupId, String expenseAmount, String expenseParticular) {
        this.expenseDate = expenseDate;
        this.userId = userId;
        this.userName = userName;
        this.groupId = groupId;
        this.expenseAmount = expenseAmount;
        this.expenseParticular = expenseParticular;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(String expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getExpenseParticular() {
        return expenseParticular;
    }

    public void setExpenseParticular(String expenseParticular) {
        this.expenseParticular = expenseParticular;
    }
}
