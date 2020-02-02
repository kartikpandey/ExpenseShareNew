package com.dheeraj.expensesharenew.groupinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dheeraj.expensesharenew.R;
import com.dheeraj.expensesharenew.groupdashboard.model.GroupMember;
import com.dheeraj.expensesharenew.groupinfo.GroupInfoActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupInfoAdapter extends RecyclerView.Adapter<GroupInfoAdapter.ViewHolder> {

    private final List<GroupMember> groupMembersList;
    private List<GroupMember> groupMembersListFiltered;
    private Context context;
    private View view;
    String customerName;

    public GroupInfoAdapter(ArrayList<GroupMember> groupMembersList) {
        this.groupMembersList = groupMembersList;
        this.groupMembersListFiltered = groupMembersList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_info_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textViewMemberName.setText(groupMembersListFiltered.get(position).getMemberName());
        if(groupMembersListFiltered.get(position).getMemberType().equals(GroupInfoActivity.getInstance().getResources().getString(R.string.admin))){
            holder.textViewMemberType.setVisibility(View.VISIBLE);
            holder.textViewMemberType.setText(groupMembersListFiltered.get(position).getMemberType());
        }else {
            holder.textViewMemberType.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return groupMembersListFiltered.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;

        @BindView(R.id.textViewMemberName)
        TextView textViewMemberName;

        @BindView(R.id.textViewMemberType)
        TextView textViewMemberType;

        @BindView(R.id.relativeLayoutMember)
        RelativeLayout relativeLayoutMember;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
            relativeLayoutMember.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return "";
        }

        @Override
        public void onClick(View v) {
            if (v == relativeLayoutMember) {
            }
        }
    }

}


