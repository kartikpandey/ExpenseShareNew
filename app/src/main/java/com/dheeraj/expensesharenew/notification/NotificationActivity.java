package com.dheeraj.expensesharenew.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dheeraj.expensesharenew.BaseActivity;
import com.dheeraj.expensesharenew.R;
import com.dheeraj.expensesharenew.Utils;
import com.dheeraj.expensesharenew.groupdashboard.GroupMember;
import com.dheeraj.expensesharenew.groupdashboard.GroupModel;
import com.dheeraj.expensesharenew.groupdashboard.adapter.GroupDashboardAdapter;
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
//        setNotificationData();
        getNotifications(true);
    }

    public void setNotificationData() {
        notificationAdapter = new NotificationAdapter(invitationModelArrayList);
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
                .removeValue().addOnCompleteListener(task -> {
            {
                Utils.hideProgress();
                if (task.isSuccessful()) {
                } else {
                }
            }
        });

    }

    public void acceptInvitation(String groupId, String groupName) {
        GroupMember groupMember = new GroupMember(userInfoModel.getuID(),
                userInfoModel.getfName() + " " + userInfoModel.getlName(),
                "Member");
        addMemberToGroup(groupId, groupMember);

        addGroupToUserDetail(groupId, groupName);

    }

    void addMemberToGroup(String groupId, GroupMember groupMember) {
        mdDatabaseReference
                .child(KeyGroupsList)
                .child(groupId)
                .child(KeyGroupMembersList)
                .child(userInfoModel.getuID())
                .setValue(groupMember);
    }

    void addGroupToUserDetail(String groupId, String groupName){
        GroupModel groupModel = new GroupModel(groupId,groupName, new ArrayList<>());
        mdDatabaseReference
                .child(KeyUsersDetail)
                .child(userInfoModel.getuID())
                .child(KeyMemberOfGroups)
                .child(groupId)
                .setValue(groupModel);
    }

    public void denyInvitation(String notificationId) {

    }
}
