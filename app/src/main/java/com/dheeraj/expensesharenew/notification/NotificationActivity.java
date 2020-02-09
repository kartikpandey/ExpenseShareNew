package com.dheeraj.expensesharenew.notification;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.dheeraj.expensesharenew.BaseActivity;
import com.dheeraj.expensesharenew.R;
import com.dheeraj.expensesharenew.Utils;
import com.dheeraj.expensesharenew.groupdashboard.model.GroupMember;
import com.dheeraj.expensesharenew.groupdashboard.model.GroupModel;
import com.dheeraj.expensesharenew.notification.adapter.NotificationAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends BaseActivity {

    @BindView(R.id.recyclerViewNotifications)
    RecyclerView recyclerViewNotifications;

    NotificationAdapter notificationAdapter;

    public static NotificationActivity instance;

    public static NotificationActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        instance = this;
        setTitle(getString(R.string.notifications));
//        setNotificationData();
        getNotifications(true);
    }

    public void setNotificationData() {
        notificationAdapter = new NotificationAdapter(notificationModelArrayList);
        recyclerViewNotifications.setHasFixedSize(true);
        recyclerViewNotifications.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewNotifications.setAdapter(notificationAdapter);
    }

    @Override
    public void imageButtonNotificationClick(View view) {

    }

    public void deleteNotification(String notificationId) {
        mdDatabaseReference.child(KeyNotifications)
                .child(userInfoModel.getuID())
                .child(notificationId)
                .removeValue();
        getNotifications(true);
    }

    public void acceptInvitation(String groupId, String groupName, String notificationId) {
        GroupMember groupMember = new GroupMember(userInfoModel.getuID(),
                userInfoModel.getfName() + " " + userInfoModel.getlName(),
                "Member");
        addMemberToGroup(groupId, groupMember);

        addGroupToUserDetail(groupId, groupName);

        changeNotificationStatus(notificationId);

    }

    void addMemberToGroup(String groupId, GroupMember groupMember) {
        mdDatabaseReference
                .child(KeyGroupsList)
                .child(groupId)
                .child(KeyGroupMembersList)
                .child(userInfoModel.getuID())
                .setValue(groupMember);
    }

    void addGroupToUserDetail(String groupId, String groupName) {
        GroupModel groupModel = new GroupModel(groupId, groupName, new ArrayList<>());
        mdDatabaseReference
                .child(KeyUsersDetail)
                .child(userInfoModel.getuID())
                .child(KeyMemberOfGroups)
                .child(groupId)
                .setValue(groupModel);
    }

    void changeNotificationStatus(String notificationId) {
        mdDatabaseReference.child(KeyNotifications)
                .child(userInfoModel.getuID())
                .child(notificationId)
                .child("notificationStatus").setValue("Accepted");
        getNotifications(true);
    }

    public void denyInvitation(String notificationId) {

    }
}
