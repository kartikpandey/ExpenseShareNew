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
import com.dheeraj.expensesharenew.groupdashboard.adapter.GroupDashboardAdapter;
import com.dheeraj.expensesharenew.notification.adapter.NotificationAdapter;

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

    public void acceptInvitation(){

    }


}
