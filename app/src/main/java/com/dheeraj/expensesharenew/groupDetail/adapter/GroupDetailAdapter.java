package com.dheeraj.expensesharenew.groupDetail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.dheeraj.expensesharenew.R;
import com.dheeraj.expensesharenew.groupDetail.model.ExpenseModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupDetailAdapter extends RecyclerView.Adapter<GroupDetailAdapter.ViewHolder> {

    private List<ExpenseModel> expenseModelList;
    private Context context;
    private View view;
    String customerName;

    public GroupDetailAdapter(List<ExpenseModel> groupModelList) {
        this.expenseModelList = groupModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_layout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textViewDate.setText(expenseModelList.get(position).getExpenseDate());
        holder.textViewUserName.setText(expenseModelList.get(position).getUserName());
        holder.textViewParticular.setText(expenseModelList.get(position).getExpenseParticular());
        holder.textViewAmount.setText(expenseModelList.get(position).getExpenseAmount());
    }

    @Override
    public int getItemCount() {
        return expenseModelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        @BindView(R.id.textViewDate)
        AppCompatTextView textViewDate;

        @BindView(R.id.textViewUserName)
        AppCompatTextView textViewUserName;

        @BindView(R.id.textViewParticular)
        AppCompatTextView textViewParticular;

        @BindView(R.id.textViewAmount)
        AppCompatTextView textViewAmount;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

    }
}
