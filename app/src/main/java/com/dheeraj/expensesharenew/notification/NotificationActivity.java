package com.dheeraj.expensesharenew.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.dheeraj.expensesharenew.BaseActivity;
import com.dheeraj.expensesharenew.R;

import butterknife.BindView;

public class NotificationActivity extends BaseActivity {

    @BindView(R.id.recyclerViewGroups)
    RecyclerView recyclerViewGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
    }

    void setNotificationData(){

    }
}
