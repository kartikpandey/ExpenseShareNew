package com.dheeraj.expensesharenew;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    public static String KeyGroupId= "groupId";
    public static String KeyGroupName= "groupName";
    public static String KeyMemberName= "memberName";
    public static String KeyMemberUid= "memberUid";
    public static String KeyMemberType= "memberType";
    public static DatabaseReference mdDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        setActionBarData();
        mdDatabaseReference = FirebaseDatabase.getInstance().getReference();
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
}
