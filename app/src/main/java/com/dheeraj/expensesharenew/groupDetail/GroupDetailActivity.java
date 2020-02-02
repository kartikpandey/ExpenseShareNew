package com.dheeraj.expensesharenew.groupDetail;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dheeraj.expensesharenew.BaseActivity;
import com.dheeraj.expensesharenew.R;
import com.dheeraj.expensesharenew.groupdashboard.model.GroupMember;
import com.dheeraj.expensesharenew.groupdashboard.model.GroupModel;
import com.dheeraj.expensesharenew.groupinfo.GroupInfoActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class GroupDetailActivity extends BaseActivity {

    static String groupId = "";
    public static GroupModel groupDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);

        ButterKnife.bind(this);
        groupDetail = new GroupModel();
        groupId = getIntent().getStringExtra("GroupId");
        getGroupData(groupId);
    }

    void getGroupData(String groupId) {
        mdDatabaseReference.child(KeyGroupsList).child(groupId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {

                    groupDetail.setGroupId(dataSnapshot.child(KeyGroupId).getValue().toString());
                    groupDetail.setGroupName(dataSnapshot.child(KeyGroupName).getValue().toString());
                    setTitle(groupDetail.getGroupName());

                    ArrayList<GroupMember> groupMembersList = new ArrayList<>();
                    for (DataSnapshot dsp : dataSnapshot.child(KeyGroupMembersList).getChildren()) {
                        groupMembersList.add(new GroupMember(
                                dsp.child(KeyMemberUid).getValue().toString(),
                                dsp.child(KeyMemberName).getValue().toString(),
                                dsp.child(KeyMemberType).getValue().toString()));
                    }
                    groupDetail.setGroupMembersList(groupMembersList);
//                    setGroupListData(groupList);
                } else {
                    Toast.makeText(GroupDetailActivity.this, "No expenses yet!", Toast.LENGTH_LONG).show();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.group_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.groupInfo:
                startActivity(new Intent(this, GroupInfoActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
