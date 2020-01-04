package com.dheeraj.expensesharenew.groupinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.dheeraj.expensesharenew.BaseActivity;
import com.dheeraj.expensesharenew.R;
import com.dheeraj.expensesharenew.groupDetail.GroupDetailActivity;
import com.dheeraj.expensesharenew.groupdashboard.GroupMember;
import com.dheeraj.expensesharenew.groupdashboard.GroupModel;
import com.dheeraj.expensesharenew.groupdashboard.adapter.GroupDashboardAdapter;
import com.dheeraj.expensesharenew.groupinfo.adapter.GroupInfoAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupInfoActivity extends BaseActivity {

    @BindView(R.id.recyclerViewMembers)
    RecyclerView recyclerViewMembers;

    GroupInfoAdapter groupInfoAdapter;

    static GroupInfoActivity groupInfoActivity;

    public static GroupInfoActivity getInstance(){
        return groupInfoActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);

        ButterKnife.bind(this);
        groupInfoActivity = this;
        setGroupListData(GroupDetailActivity.groupDetail.getGroupMembersList());
    }

    void setGroupListData(ArrayList<GroupMember> groupMembersList) {
        groupInfoAdapter = new GroupInfoAdapter(groupMembersList);
        recyclerViewMembers.setHasFixedSize(true);
        recyclerViewMembers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMembers.setAdapter(groupInfoAdapter);
    }
}
