package com.dheeraj.expensesharenew.groupdashboard;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;

import com.dheeraj.expensesharenew.BaseActivity;
import com.dheeraj.expensesharenew.CustomViews.CustomButton;
import com.dheeraj.expensesharenew.LoginActivity;
import com.dheeraj.expensesharenew.R;
import com.dheeraj.expensesharenew.Utils;
import com.dheeraj.expensesharenew.userinfo.UserInfoModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class GroupDashboardActivity extends BaseActivity {

    FirebaseAuth mfFirebaseAuth;
    private TextView textViewTitle;
    private String title;
    Gson gson = new Gson();
    UserInfoModel userInfoModel;
    AppCompatEditText etGroupName;
    CustomButton btnCreateGroup;

    private DatabaseReference mdDatabaseReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String groupKey;
    ArrayList<GroupMember> groupMembers;
    GroupModel groupModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_dashboard);
        setActionBarData();
        mdDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mfFirebaseAuth = FirebaseAuth.getInstance();
        getUserData();
    }

    private void setActionBarData() {
        LayoutInflater mInflater = LayoutInflater.from(this);
        View view = mInflater.inflate(R.layout.profile_action_bar_layout, null);
        textViewTitle = view.findViewById(R.id.textViewUserName);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(view);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
    }

    void getUserData() {
        mdDatabaseReference.child("UsersDetail").child(mfFirebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    gson = new Gson();
                    ArrayList<GroupModel> groupList = new ArrayList<>(); // Result will be holded Here
                    for(DataSnapshot dsp : dataSnapshot.getChildren()){
                        GroupModel groupModel = gson.fromJson(String.valueOf(dsp.getKey()), GroupModel.class); //add result into array list
                        groupList.add(groupModel); //add result into array list
                    }
//                    gson = new Gson();
//                    try{
//                        String data = dataSnapshot.getValue().toString();
//                        userInfoModel = gson.fromJson(data, UserInfoModel.class);
//                        setTitle(userInfoModel.getfName() + " " + userInfoModel.getlName());
//                    }catch (JsonSyntaxException jsonSyntaxException){
//                        jsonSyntaxException.printStackTrace();
//                    }
                } else {
                    Toast.makeText(GroupDashboardActivity.this, "Oops, some problem occured!\nPlease login try again", Toast.LENGTH_LONG).show();
                    mfFirebaseAuth.signOut();
                    startActivity(new Intent(GroupDashboardActivity.this, LoginActivity.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                databaseError.getMessage();
            }
        });
    }

    public void setTitle(String title) {
        this.title = title;
        textViewTitle.setText(this.title);
    }

    void createNewGroupDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_alert_new_group);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        btnCreateGroup = dialog.findViewById(R.id.btnCreateGroup);
        etGroupName = dialog.findViewById(R.id.etGroupName);
        ImageView imgClose = dialog.findViewById(R.id.imgClose);

        btnCreateGroup.setOnClickListener(v -> {
            if (etGroupName.getText().toString().isEmpty()) {
                etGroupName.requestFocus();
                etGroupName.setError("Enter group name");
            } else {
                createGroup();
                dialog.cancel();
            }
        });

        imgClose.setOnClickListener(v -> {
            dialog.cancel();
        });
        dialog.show();
    }

    void createGroup() {
        Utils.showProgress(this, "Creating Group", "Please Wait");

        groupMembers = new ArrayList<>();
        groupMembers.add(new GroupMember(userInfoModel.getuID(),
                userInfoModel.getfName() + " " + userInfoModel.getlName(),
                getResources().getString(R.string.admin)));
        groupKey = database.getReference(etGroupName.getText().toString()).push().getKey();

        groupModel = new GroupModel(groupKey, etGroupName.getText().toString(), groupMembers);

        mdDatabaseReference
                .child(getResources().getString(R.string.group_list_child_key))
                .child(groupKey)
                .setValue(groupModel).addOnCompleteListener(task -> {//multiple informations in with same user(instertion will add new information)
            {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Group Created", Toast.LENGTH_SHORT).show();
                    Utils.hideProgress();
                    addCreatedGroupToUserInfo(groupModel);
//                    addUserAsMember(groupKey);
                } else {
                    Toast.makeText(this, task.getException().toString(), Toast.LENGTH_LONG).show();
                    Utils.hideProgress();
                }
            }
        });//single information with single user (instertion will update information)
    }

    void addCreatedGroupToUserInfo(GroupModel groupModel){
        ArrayList<GroupModel> groupModels = new ArrayList<>();
        groupModels.add(groupModel);
        mdDatabaseReference.child("UsersDetail").child(userInfoModel.getuID()).child("memberOfGroups").setValue(groupModels);
    }
//memberOfGroups
    void addUserAsMember(String groupKey) {
        Utils.showProgress(this, "Creating Group", "Please Wait");

        groupMembers = new ArrayList<>();
        groupMembers.add(new GroupMember(userInfoModel.getuID(),
                userInfoModel.getfName() + " " + userInfoModel.getlName(),
                getResources().getString(R.string.admin)));

        mdDatabaseReference
                .child(getResources().getString(R.string.group_list_child_key))
                .child(groupKey)
                .child(getResources().getString(R.string.group_member_list_child))
                .setValue(groupMembers).addOnCompleteListener(task -> {//multiple informations in with same user(instertion will add new information)
            {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Member Added", Toast.LENGTH_SHORT).show();
                    Utils.hideProgress();
                } else {
                    Toast.makeText(this, task.getException().toString(), Toast.LENGTH_LONG).show();
                    Utils.hideProgress();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newGroup:
                createNewGroupDialog(this);
                return true;
            case R.id.setting:
                Toast.makeText(this, "Clicked On Settings", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.logout:
                mfFirebaseAuth.signOut();
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void imageButtonBackClick(View view) {
        finish();
    }

    public boolean saveToFile(String data) {

        File file = new File("sdcard", "text");
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            File gpxfile = new File(file, "sample");
            FileWriter writer = new FileWriter(gpxfile, true);
            writer.append(data + "\n");
            writer.flush();
            writer.close();
            Toast.makeText(GroupDashboardActivity.this, "Saved your text", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
        }
        return false;

    }

}
