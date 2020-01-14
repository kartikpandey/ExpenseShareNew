package com.dheeraj.expensesharenew.groupinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.dheeraj.expensesharenew.BaseActivity;
import com.dheeraj.expensesharenew.CustomViews.CustomButton;
import com.dheeraj.expensesharenew.R;
import com.dheeraj.expensesharenew.Utils;
import com.dheeraj.expensesharenew.groupDetail.GroupDetailActivity;
import com.dheeraj.expensesharenew.groupdashboard.GroupDashboardActivity;
import com.dheeraj.expensesharenew.groupdashboard.GroupMember;
import com.dheeraj.expensesharenew.groupinfo.adapter.GroupInfoAdapter;
import com.dheeraj.expensesharenew.userinfo.UserInfoModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GroupInfoActivity extends BaseActivity {

    @BindView(R.id.recyclerViewMembers)
    RecyclerView recyclerViewMembers;

    @BindView(R.id.buttonAddMember)
    ImageButton buttonAddMember;

    GroupInfoAdapter groupInfoAdapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    static GroupInfoActivity groupInfoActivity;

    String mobNo = "";

    public static GroupInfoActivity getInstance() {
        return groupInfoActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);

        ButterKnife.bind(this);
        groupInfoActivity = this;
        if (GroupDetailActivity.groupDetail != null) {
            setGroupListData(GroupDetailActivity.groupDetail.getGroupMembersList());
        }
    }

    void setGroupListData(ArrayList<GroupMember> groupMembersList) {
        groupInfoAdapter = new GroupInfoAdapter(groupMembersList);
        recyclerViewMembers.setHasFixedSize(true);
        recyclerViewMembers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMembers.setAdapter(groupInfoAdapter);
    }

    @OnClick(R.id.buttonAddMember)
    void onButtonAddMemberClick() {
        createNewGroupDialog(this);
    }

    void createNewGroupDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_alert_add_member);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        CustomButton btnSendInvitation = dialog.findViewById(R.id.btnSendInvitation);
        AppCompatEditText etMemberMobile = dialog.findViewById(R.id.etMemberMobile);
        ImageView imgClose = dialog.findViewById(R.id.imgClose);

        btnSendInvitation.setOnClickListener(v -> {
            if (etMemberMobile.getText().toString().isEmpty()) {
                etMemberMobile.requestFocus();
                etMemberMobile.setError("Enter member's mobile number");
            } else {
                addMember(etMemberMobile.getText().toString());
                dialog.cancel();
            }
        });

        imgClose.setOnClickListener(v -> {
            dialog.cancel();
        });
        dialog.show();
    }

    void addMember(String mobNo) {
        Utils.showProgress(this, "Sending Invitation...", "");
        Query query = mdDatabaseReference.child(KeyUsersDetail)
                .orderByChild(KeyMobNo)
                .equalTo(mobNo);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Utils.hideProgress();
                if (dataSnapshot.getValue() != null) {
                    Log.d("memberData", dataSnapshot.toString());
                    if (!dataSnapshot.getValue().toString().isEmpty()) {
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            sendInvitation(dsp.child(KeyUID).getValue().toString());
                        }
                    } else {
                        Toast.makeText(GroupInfoActivity.this, "No user found with this mobile number!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(GroupInfoActivity.this, "No user found with this mobile number!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Utils.hideProgress();
                Toast.makeText(GroupInfoActivity.this, "Something went wrong.\nPlease try again.", Toast.LENGTH_LONG).show();
            }
        });
    }

    //{xkTxChY7WeYW4S8oeX4c1uFnhLq1={uID=xkTxChY7WeYW4S8oeX4c1uFnhLq1, gender=m, mobNo=9876543210, fName=atul, memberOfGroups={-LxqE-UVVFaq5DBXeBBD={groupId=-LxqE-UVVFaq5DBXeBBD, groupName=atul}}, lName=pandey}} }
    void sendInvitation(String memberUid) {
//        Toast.makeText(groupInfoActivity, memberUid, Toast.LENGTH_SHORT).show();
        String notificationId = database.getReference().push().getKey();
//        mdDatabaseReference
//                .child(KeyNotifications)
//                .child(memberUid)
//                .child(notificationId)
//                .setValue().addOnCompleteListener(task -> {
//            {
//                if (task.isSuccessful()) {
//                    Toast.makeText(this, "Information Saved", Toast.LENGTH_SHORT).show();
//                    Utils.hideProgress();
//                    startActivity(new Intent(this, GroupDashboardActivity.class));
//                    overridePendingTransition(0, 0);
//                    finishAffinity();
//                } else {
//                    Toast.makeText(this, task.getException().toString(), Toast.LENGTH_LONG).show();
//                    Utils.hideProgress();
//                }
//            }
//        });
    }
}
