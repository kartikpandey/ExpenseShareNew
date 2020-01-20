package com.dheeraj.expensesharenew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dheeraj.expensesharenew.groupdashboard.GroupDashboardActivity;
import com.dheeraj.expensesharenew.groupdashboard.GroupModel;
import com.dheeraj.expensesharenew.groupinfo.model.InvitationModel;
import com.dheeraj.expensesharenew.notification.NotificationActivity;
import com.dheeraj.expensesharenew.userinfo.UserInfoModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;

public class BaseActivity extends AppCompatActivity {

    public static TextView textViewTitle;
    public static String KeyUsersDetail = "UsersDetail";
    public static String KeyMemberOfGroups = "memberOfGroups";
    public static String KeyGroupsList = "GroupsList";
    public static String KeyGroupMembersList = "groupMembersList";
    public static String KeyFName = "fName";
    public static String KeyLName = "lName";
    public static String KeyMobNo = "mobNo";
    public static String KeyGender = "gender";
    public static String KeyUID = "uID";
    public static String KeyGroupId = "groupId";
    public static String KeyGroupName = "groupName";
    public static String KeyMemberName = "memberName";
    public static String KeyMemberUid = "memberUid";
    public static String KeyMemberType = "memberType";
    public static String KeyNotifications = "notifications";
    public static String KeyNotificationType = "notificationType";
    public static String KeySenderName = "senderName";
    public static String KeySenderId = "senderId";
    public static String KeyMessage = "message";
    public static DatabaseReference mdDatabaseReference;

    public static UserInfoModel userInfoModel;
    public static ArrayList<InvitationModel> invitationModelArrayList;

    @BindView(R.id.textViewNotifyCount)
    TextView textViewNotifyCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        setActionBarData();
        mdDatabaseReference = FirebaseDatabase.getInstance().getReference();
        invitationModelArrayList = new ArrayList<>();
        if (userInfoModel != null) {
            if (userInfoModel.getuID() != null) {
                getNotifications();
            }
        }
    }

    public void setActionBarData() {
        LayoutInflater mInflater = LayoutInflater.from(this);
        View view = mInflater.inflate(R.layout.profile_action_bar_layout, null);
        textViewTitle = view.findViewById(R.id.textViewUserName);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(view);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
    }

    public void setTitle(String title) {
//        this.title = title;
        textViewTitle.setText(title);
    }

    public void imageButtonBackClick(View view) {
        finish();
    }

    public void imageButtonNotificationClick(View view) {
        startActivity(new Intent(this, NotificationActivity.class));
    }

    public void getNotifications() {
        mdDatabaseReference.child(KeyNotifications)
                .child(userInfoModel.getuID())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int notificationCount = 0;
                        if (dataSnapshot.getValue() != null) {
                            for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                notificationCount++;
                                String notificationType = dsp.child(KeyNotificationType).getValue().toString();
                                if (notificationType.equals(getResources().getString(R.string.value_invitation))) {
                                    invitationModelArrayList.add(new InvitationModel(notificationType,
                                            dsp.child(KeyGroupId).getValue().toString(),
                                            dsp.child(KeyGroupName).getValue().toString(),
                                            dsp.child(KeySenderName).getValue().toString(),
                                            dsp.child(KeySenderId).getValue().toString(),
                                            dsp.child(KeyMessage).getValue().toString()));
                                }
                            }
                        }
                        textViewNotifyCount.setText(notificationCount + "");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        databaseError.getMessage();
                    }
                });
    }
}
