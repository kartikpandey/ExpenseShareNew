package com.dheeraj.expensesharenew.notification.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.dheeraj.expensesharenew.CustomViews.CustomButton;
import com.dheeraj.expensesharenew.R;
import com.dheeraj.expensesharenew.groupdashboard.GroupModel;
import com.dheeraj.expensesharenew.groupinfo.model.InvitationModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    public final ArrayList<InvitationModel> invitationModelArrayList;
    private List<GroupModel> groupModelList;
    private ArrayList<InvitationModel> invitationModelArrayListFiltered;
    private Context context;
    private View view;
    String customerName;

    public NotificationAdapter(ArrayList<InvitationModel> invitationModelArrayList) {
        this.invitationModelArrayList = invitationModelArrayList;
        this.invitationModelArrayListFiltered = invitationModelArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textNotificationHeading.setText(invitationModelArrayListFiltered.get(position).getNotificationType());
        holder.textInviteMessage.setText(invitationModelArrayListFiltered.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return invitationModelArrayListFiltered.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;

        @BindView(R.id.relativeLayoutInvitation)
        RelativeLayout relativeLayoutInvitation;

        @BindView(R.id.textNotificationHeading)
        AppCompatTextView textNotificationHeading;

        @BindView(R.id.textInviteMessage)
        AppCompatTextView textInviteMessage;

        @BindView(R.id.buttonDecline)
        CustomButton buttonDecline;

        @BindView(R.id.buttonAccept)
        CustomButton buttonAccept;

        @BindView(R.id.imageDelete)
        AppCompatImageView imageDelete;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
            imageDelete.setOnClickListener(this);
            buttonDecline.setOnClickListener(this);
            buttonAccept.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return "";
        }

        @Override
        public void onClick(View view) {
            if (buttonAccept.equals(view)) {

            } else if (buttonDecline.equals(view)) {
            } else if (imageDelete.equals(view)) {
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
