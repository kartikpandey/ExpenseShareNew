package com.dheeraj.expensesharenew.groupDetail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.dheeraj.expensesharenew.BaseActivity;
import com.dheeraj.expensesharenew.LoginActivity;
import com.dheeraj.expensesharenew.R;
import com.dheeraj.expensesharenew.groupdashboard.GroupDashboardActivity;
import com.dheeraj.expensesharenew.groupdashboard.GroupMember;
import com.dheeraj.expensesharenew.groupdashboard.GroupModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class GroupDetailActivity extends BaseActivity {

    static String groupId = "";
    static GroupModel groupModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);

        ButterKnife.bind(this);
        groupModel = new GroupModel();
        groupId = getIntent().getStringExtra("GroupId");
        getGroupData(groupId);
    }

    void getGroupData(String groupId) {
        mdDatabaseReference.child(KeyGroupsList).child(groupId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {

                    groupModel.setGroupId(dataSnapshot.child(KeyGroupId).getValue().toString());
                    groupModel.setGroupName(dataSnapshot.child(KeyGroupName).getValue().toString());
                    setTitle(groupModel.getGroupName());

                    ArrayList<GroupMember> groupMembersList = new ArrayList<>();
                    for (DataSnapshot dsp : dataSnapshot.child(KeyGroupMembersList).getChildren()) {
                        groupMembersList.add(new GroupMember(
                                dsp.child(KeyMemberUid).getValue().toString(),
                                dsp.child(KeyMemberName).getValue().toString(),
                                dsp.child(KeyMemberType).getValue().toString()));
                    }
                    groupModel.setGroupMembersList(groupMembersList);
//                    setGroupListData(groupList);
                } else {
                    Toast.makeText(GroupDetailActivity.this, "Oops, some problem occured!\nPlease Contact Administrator", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                databaseError.getMessage();
            }
        });

    }

//    void addGroupMember(String groupKey, GroupMember groupMember){
//        mdDatabaseReference
//                .child(getResources().getString(R.string.group_list_child_key))
//                .child(groupKey)
//                .child("groupMembersList")
//                .child(userInfoModel.getuID())
//                .setValue(groupMember);
//    }
}
