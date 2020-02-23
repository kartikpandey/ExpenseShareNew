package com.dheeraj.expensesharenew.groupDetail;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dheeraj.expensesharenew.BaseActivity;
import com.dheeraj.expensesharenew.CustomViews.CustomButton;
import com.dheeraj.expensesharenew.LoginActivity;
import com.dheeraj.expensesharenew.R;
import com.dheeraj.expensesharenew.Utils;
import com.dheeraj.expensesharenew.groupDetail.adapter.GroupDetailAdapter;
import com.dheeraj.expensesharenew.groupDetail.adapter.SpinnerGroupListAdapter;
import com.dheeraj.expensesharenew.groupDetail.model.ExpenseModel;
import com.dheeraj.expensesharenew.groupdashboard.GroupDashboardActivity;
import com.dheeraj.expensesharenew.groupdashboard.model.GroupMember;
import com.dheeraj.expensesharenew.groupdashboard.model.GroupModel;
import com.dheeraj.expensesharenew.groupinfo.GroupInfoActivity;
import com.dheeraj.expensesharenew.userinfo.UserInfoModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class GroupDetailActivity extends BaseActivity {

    FirebaseAuth mfFirebaseAuth;
    static String groupId = "";
    public static GroupModel groupDetail;
    public static ArrayList<ExpenseModel> expenseList = new ArrayList<>();

    @BindView(R.id.spinnerGroupsList)
    Spinner spinnerGroupsList;

    @BindView(R.id.textViewTotalExpense)
    TextView textViewTotalExpense;

    @BindView(R.id.recyclerViewGroupDetail)
    RecyclerView recyclerViewGroupDetail;

    AppCompatEditText etGroupName;
    CustomButton btnCreateGroup;

    ArrayList<GroupModel> groupsList = new ArrayList<>();
    private GroupDetailAdapter groupDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);

        ButterKnife.bind(this);
        mfFirebaseAuth = FirebaseAuth.getInstance();
        userInfoModel = new UserInfoModel();
        getGroupsListData();
        groupDetail = new GroupModel();
//        groupId = getIntent().getStringExtra("GroupId");
//        getGroupData(groupId);
    }

    void getGroupsListData() {
        mdDatabaseReference
                .child(KeyUsersDetail)
                .child(Objects.requireNonNull(mfFirebaseAuth.getCurrentUser()).getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {

                            userInfoModel.setfName(dataSnapshot.child(KeyFName).getValue().toString());
                            userInfoModel.setlName(dataSnapshot.child(KeyLName).getValue().toString());
                            userInfoModel.setMobNo(dataSnapshot.child(KeyMobNo).getValue().toString());
                            userInfoModel.setGender(dataSnapshot.child(KeyGender).getValue().toString());
                            userInfoModel.setuID(dataSnapshot.child(KeyUID).getValue().toString());

//                            setTitle(userInfoModel.getfName() + " " + userInfoModel.getlName());

                            ArrayList<GroupModel> groupList = new ArrayList<>();
                            for (DataSnapshot dsp : dataSnapshot.child(KeyMemberOfGroups).getChildren()) {

                                groupList.add(new GroupModel(
                                        dsp.child(KeyGroupId).getValue().toString(),
                                        dsp.child(KeyGroupName).getValue().toString(),
                                        new ArrayList<>())); //add result into array list
                            }
                            userInfoModel.setGroupList(groupList);
                            groupsList = groupList;
                            setSpinnerGroupListData(groupList);
                            getNotifications(false);
                        } else {
                            Toast.makeText(GroupDetailActivity.this, "Oops, some problem occured!\nPlease try login again", Toast.LENGTH_LONG).show();
                            mfFirebaseAuth.signOut();
                            startActivity(new Intent(GroupDetailActivity.this, LoginActivity.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        databaseError.getMessage();
                    }
                });
    }

    void getUserData() {
        mdDatabaseReference
                .child(KeyUsersDetail)
                .child(mfFirebaseAuth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {

                            userInfoModel.setfName(dataSnapshot.child(KeyFName).getValue().toString());
                            userInfoModel.setlName(dataSnapshot.child(KeyLName).getValue().toString());
                            userInfoModel.setMobNo(dataSnapshot.child(KeyMobNo).getValue().toString());
                            userInfoModel.setGender(dataSnapshot.child(KeyGender).getValue().toString());
                            userInfoModel.setuID(dataSnapshot.child(KeyUID).getValue().toString());

                            setTitle(userInfoModel.getfName() + " " + userInfoModel.getlName());

                            ArrayList<GroupModel> groupList = new ArrayList<>();
                            for (DataSnapshot dsp : dataSnapshot.child(KeyMemberOfGroups).getChildren()) {

                                groupList.add(new GroupModel(
                                        dsp.child(KeyGroupId).getValue().toString(),
                                        dsp.child(KeyGroupName).getValue().toString(),
                                        new ArrayList<>())); //add result into array list
                            }
                            userInfoModel.setGroupList(groupList);
                            getNotifications(false);
                        } else {
                            Toast.makeText(GroupDetailActivity.this, "Oops, some problem occured!\nPlease try login again", Toast.LENGTH_LONG).show();
                            mfFirebaseAuth.signOut();
                            startActivity(new Intent(GroupDetailActivity.this, LoginActivity.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        databaseError.getMessage();
                    }
                });
    }

    private void setSpinnerGroupListData(ArrayList<GroupModel> groupList) {
        SpinnerGroupListAdapter groupListAdapter = new SpinnerGroupListAdapter(this, R.layout.spinner_layout, groupList);
        spinnerGroupsList.setAdapter(groupListAdapter);
    }

    void getGroupData(String groupId) {
        mdDatabaseReference.child(KeyGroupsList).child(groupId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {

                    groupDetail.setGroupId(dataSnapshot.child(KeyGroupId).getValue().toString());
                    groupDetail.setGroupName(dataSnapshot.child(KeyGroupName).getValue().toString());
//                    setTitle(groupDetail.getGroupName());

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

    @OnItemSelected(R.id.spinnerGroupsList)
    void onItemSelected(int position) {
        textViewTitle.setText(groupsList.get(position).getGroupName());
        getGroupData(groupsList.get(position).getGroupId());
        getExpenses(groupsList.get(position).getGroupId());
//        Toast.makeText(this, "position: " + position, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.imageViewGroupInfo)
    void onGroupInfoClick() {
        startActivity(new Intent(this, GroupInfoActivity.class));
    }

    @SuppressLint("SetTextI18n")
    void setGroupDetailListData(ArrayList<ExpenseModel> expenseList) {
        float totalExp = 0;
        for (ExpenseModel exp : expenseList) {
            try {
                totalExp += Float.parseFloat(exp.getExpenseAmount());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        textViewTotalExpense.setText(totalExp + "");
        groupDetailAdapter = new GroupDetailAdapter(expenseList);
        recyclerViewGroupDetail.setHasFixedSize(true);
        recyclerViewGroupDetail.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewGroupDetail.setAdapter(groupDetailAdapter);
    }

//    void addGroupMember(String groupKey, GroupMember groupMember){
//        mdDatabaseReference
//                .child(getResources().getString(R.string.group_list_child_key))
//                .child(groupKey)
//                .child("groupMembersList")
//                .child(userInfoModel.getuID())
//                .setValue(groupMember);
//    }

    @OnClick(R.id.buttonAddExp)
    void onButtonAddExpClick() {
        addNewExpenseDialog(this);
    }

    void addNewExpenseDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.new_expense_dialog_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        CustomButton buttonAddExp = dialog.findViewById(R.id.buttonAddExp);
        AppCompatEditText editTextAmount = dialog.findViewById(R.id.editTextAmount);
        AppCompatEditText editTextParticular = dialog.findViewById(R.id.editTextParticular);
        ImageView imgClose = dialog.findViewById(R.id.imgClose);
        editTextParticular.requestFocus();

        buttonAddExp.setOnClickListener(v -> {
            if (Objects.requireNonNull(editTextAmount.getText()).toString().isEmpty()) {
                editTextAmount.requestFocus();
                editTextAmount.setError("Enter amount");
            } else if (Objects.requireNonNull(editTextParticular.getText()).toString().isEmpty()) {
                editTextParticular.requestFocus();
                editTextParticular.setError("Enter particular");
            } else {
                saveExpense(new ExpenseModel(
                        Utils.getCurrentDate() + " " + Utils.getCurrentTime(),
                        userInfoModel.getuID(),
                        userInfoModel.getfName() + " " + userInfoModel.getlName(),
                        groupDetail.groupId,
                        Objects.requireNonNull(editTextAmount.getText()).toString(),
                        Objects.requireNonNull(editTextParticular.getText()).toString()));
                dialog.cancel();
            }
        });

        imgClose.setOnClickListener(v -> {
            dialog.cancel();
        });
        dialog.show();
    }

    void saveExpense(ExpenseModel expense) {
        String expenseId = FirebaseDatabase.getInstance().getReference().push().getKey();
        mdDatabaseReference
                .child(KeyExpenseDetail)
                .child(expense.getGroupId())
                .child(expenseId)
                .setValue(expense).addOnCompleteListener(task -> {
            {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Expense Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    void getExpenses(String groupId) {
        mdDatabaseReference.child(KeyExpenseDetail)
                .child(groupId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                expenseList.clear();
                if (dataSnapshot.getValue() != null) {
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        dsp.getValue();
                        expenseList.add(dsp.getValue(ExpenseModel.class));
                    }
                } else {
                    Toast.makeText(GroupDetailActivity.this, "No expenses yet!", Toast.LENGTH_LONG).show();
                }
                setGroupDetailListData(expenseList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                databaseError.getMessage();
            }
        });
    }

    void createNewGroupDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_alert_new_group);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        btnCreateGroup = dialog.findViewById(R.id.btnCreateGroup);
        etGroupName = dialog.findViewById(R.id.etGroupName);
        ImageView imgClose = dialog.findViewById(R.id.imgClose);

        btnCreateGroup.setOnClickListener(v -> {
            if (etGroupName.getText().toString().isEmpty()) {
                etGroupName.requestFocus();
                etGroupName.setError("Enter group name");
            } else {
                createGroup();
                dialog.cancel();
            }
        });

        imgClose.setOnClickListener(v -> {
            dialog.cancel();
        });
        dialog.show();
    }

    void createGroup() {
        Utils.showProgress(this, "Creating Group", "Please Wait");
        ArrayList<GroupMember> groupMembers = new ArrayList<>();
        String groupKey = FirebaseDatabase.getInstance().getReference(etGroupName.getText().toString()).push().getKey();
        GroupModel groupModel = new GroupModel(groupKey, etGroupName.getText().toString(), groupMembers);
        mdDatabaseReference
                .child(KeyGroupsList)
                .child(groupKey)
                .setValue(groupModel).addOnCompleteListener(task -> {//multiple informations in with same user(instertion will add new information)
            {
                if (task.isSuccessful()) {
                    Toast.makeText(this, getResources().getString(R.string.group_created), Toast.LENGTH_SHORT).show();
                    Utils.hideProgress();

                    addGroupMember(groupKey, new GroupMember(userInfoModel.getuID(),
                            userInfoModel.getfName() + " " + userInfoModel.getlName(),
                            getResources().getString(R.string.admin)));


                    addCreatedGroupToUserInfo(groupModel);
//                    addUserAsMember(groupKey);
                } else {
                    Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    Utils.hideProgress();
                }
            }
        });//single information with single user (instertion will update information)
    }

    void addGroupMember(String groupKey, GroupMember groupMember) {
        mdDatabaseReference
                .child(KeyGroupsList)
                .child(groupKey)
                .child(KeyGroupMembersList)
                .child(userInfoModel.getuID())
                .setValue(groupMember);
    }

    void addCreatedGroupToUserInfo(GroupModel groupModel) {
        groupModel.groupMembersList = new ArrayList<>();
        mdDatabaseReference.child(KeyUsersDetail)
                .child(userInfoModel.getuID())
                .child(KeyMemberOfGroups)
                .child(groupModel.getGroupId())
                .setValue(groupModel);
        getUserData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.group_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newGroup:
                createNewGroupDialog(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
