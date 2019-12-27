package com.dheeraj.expensesharenew.groupdashboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dheeraj.expensesharenew.R;
import com.dheeraj.expensesharenew.groupdashboard.GroupModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupDashboardAdapter extends RecyclerView.Adapter<GroupDashboardAdapter.ViewHolder> {

    private final List<GroupModel> groupModelList;
    private List<GroupModel> groupModelListFiltered;
    private Context context;
    private View view;
    String customerName;

    public GroupDashboardAdapter(List<GroupModel> groupModelList) {
        this.groupModelList = groupModelList;
        this.groupModelListFiltered = groupModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_dashboard_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textViewGroupName.setText(groupModelListFiltered.get(position).getGroupName());
    }

    @Override
    public int getItemCount() {
        return groupModelListFiltered.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;

        @BindView(R.id.textViewGroupName)
        TextView textViewGroupName;

        @BindView(R.id.relativeLayoutGroup)
        RelativeLayout relativeLayoutGroup;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
            relativeLayoutGroup.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return "";
        }

        @Override
        public void onClick(View v) {
            if (v == relativeLayoutGroup) {

            }
        }
    }

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    groupModelListFiltered = groupModelList;
//                } else {
//                    List<ScanLoadingPlanList> filteredList = new ArrayList<>();
//                    for (ScanLoadingPlanList row : groupModelList) {
//                        if (row.getLoadingPlanNo().equalsIgnoreCase(charSequence.toString())) {
//                            filteredList.add(row);
//                            groupModelListFiltered.remove(row);
//                            Gson gson = new Gson();
//                            String putExtraData = gson.toJson(filteredList.get(0));
//                            context.startActivity(new Intent(context, PickAndLoadStillageActivity.class).putExtra(Constants.SELECTED_STILLAGE, putExtraData));
//                        }
//                    }
//                    filteredList.addAll(groupModelListFiltered);
//                    groupModelListFiltered = filteredList;
//
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = groupModelListFiltered;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                groupModelListFiltered = (ArrayList<ScanLoadingPlanList>) filterResults.values;
//                notifyDataSetChanged();
//
//            }
//        };
//    }
}


