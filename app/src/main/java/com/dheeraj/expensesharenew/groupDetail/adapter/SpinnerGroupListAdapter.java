package com.dheeraj.expensesharenew.groupDetail.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dheeraj.expensesharenew.R;
import com.dheeraj.expensesharenew.groupdashboard.model.GroupModel;

import java.util.ArrayList;

public class SpinnerGroupListAdapter extends ArrayAdapter<GroupModel> {

    LayoutInflater inflater;
    ArrayList<GroupModel> groupList;
    Context context;

    public SpinnerGroupListAdapter(Activity activity, int resouceId, ArrayList<GroupModel> list) {
        super(activity, resouceId, list);
        inflater = activity.getLayoutInflater();
        this.groupList = list;
        this.context = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GroupModel groupModel = getItem(position);
        @SuppressLint("ViewHolder")
        View view = View.inflate(context, R.layout.spinner_layout, null);
        TextView txtTitle = view.findViewById(R.id.text1);
        txtTitle.setText(groupModel.getGroupName());
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view;
        view = View.inflate(context, R.layout.spinner_layout, null);
        final TextView textView = view.findViewById(R.id.text1);
        textView.setText(groupList.get(position).getGroupName());

        return view;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}