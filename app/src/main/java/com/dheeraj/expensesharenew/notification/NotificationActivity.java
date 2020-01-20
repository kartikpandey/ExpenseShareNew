package com.dheeraj.expensesharenew.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.dheeraj.expensesharenew.BaseActivity;
import com.dheeraj.expensesharenew.R;
import com.dheeraj.expensesharenew.groupdashboard.adapter.GroupDashboardAdapter;
import com.dheeraj.expensesharenew.notification.adapter.NotificationAdapter;

import butterknife.BindView;

public class NotificationActivity extends BaseActivity {

    @BindView(R.id.recyclerViewGroups)
    RecyclerView recyclerViewGroups;

    NotificationAdapter notificationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        setNotificationData();
    }

    void setNotificationData() {
        notificationAdapter = new NotificationAdapter(invitationModelArrayList);
        recyclerViewGroups.setHasFixedSize(true);
        recyclerViewGroups.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewGroups.setAdapter(notificationAdapter);
    }
}
