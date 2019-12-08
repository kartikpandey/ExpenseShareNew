package com.dheeraj.expensesharenew;

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

import com.dheeraj.expensesharenew.CustomViews.CustomButton;
import com.dheeraj.expensesharenew.userinfo.UserInfoModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;

public class GroupDashboardActivity extends BaseActivity {

    FirebaseAuth mfFirebaseAuth;
    private TextView textViewTitle;
    private String title;
    Gson gson = new Gson();
    UserInfoModel userInfoModel;

    private DatabaseReference mdDatabaseReference;

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
                    userInfoModel = gson.fromJson(dataSnapshot.getValue().toString(), UserInfoModel.class);
                    setTitle(userInfoModel.getfName() + " " + userInfoModel.getlName());
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

        CustomButton btnCreateGroup = dialog.findViewById(R.id.btnCreateGroup);
        AppCompatEditText etGroupName = dialog.findViewById(R.id.etGroupName);
        ImageView imgClose = dialog.findViewById(R.id.imgClose);

        btnCreateGroup.setOnClickListener(v -> {
            if (etGroupName.getText().toString().isEmpty()) {
                etGroupName.requestFocus();
                etGroupName.setError("Enter group name");
            } else {
                GroupModel groupModel = new GroupModel("Group_"+1, etGroupName.getText().toString());
                createGroup(groupModel);
                dialog.cancel();
            }
        });

        imgClose.setOnClickListener(v -> {
            dialog.cancel();
        });
        dialog.show();
    }

    void createGroup(GroupModel groupModel) {
        FirebaseUser fbUser = mfFirebaseAuth.getCurrentUser();

        Utils.showProgress(this, "Creating Group", "Please Wait");

        String uid = fbUser.getUid();
//        mdDatabaseReference.child(fbUser.getUid()).setValue(userInfoModel).addOnCompleteListener(task -> {
        mdDatabaseReference.push().setValue(groupModel).addOnCompleteListener(task -> {//multiple informations in with same user(instertion will add new information)
            {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Group Created", Toast.LENGTH_SHORT).show();
                    Utils.hideProgress();
                } else {
                    Toast.makeText(this, task.getException().toString(), Toast.LENGTH_LONG).show();
                    Utils.hideProgress();
                }
            }
        });//single information with single user (instertion will update information)
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
