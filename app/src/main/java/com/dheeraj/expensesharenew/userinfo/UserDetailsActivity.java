package com.dheeraj.expensesharenew.userinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.dheeraj.expensesharenew.groupDetail.GroupDetailActivity;
import com.dheeraj.expensesharenew.LoginActivity;
import com.dheeraj.expensesharenew.R;
import com.dheeraj.expensesharenew.Utils;
import com.dheeraj.expensesharenew.groupinfo.GroupInfoActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dheeraj.expensesharenew.BaseActivity.KeyMobNo;
import static com.dheeraj.expensesharenew.BaseActivity.KeyUsersDetail;

public class UserDetailsActivity extends AppCompatActivity {

    @BindView(R.id.etFname)
    EditText etFname;

    @BindView(R.id.etLname)
    EditText etLname;

    @BindView(R.id.etMobNo)
    EditText etMobNo;

    @BindView(R.id.btnSaveInfo)
    Button btnSaveInfo;

    @BindView(R.id.radioFemale)
    RadioButton radioFemale;

    @BindView(R.id.radioMale)
    RadioButton radioMale;

    private FirebaseAuth mfFirebaseAuth;
    private DatabaseReference mdDatabaseReference;
    private FirebaseDatabase mfireFirebaseDatabase;

    String fName, lName, mobNo, gender = "m";

    boolean isExists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        radioMale.setChecked(true);

        mfFirebaseAuth = FirebaseAuth.getInstance();

        mdDatabaseReference = FirebaseDatabase.getInstance().getReference().child(KeyUsersDetail);

//        mdDatabaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Log.d("DataSnapshot : ", dataSnapshot.toString()+"\n"+dataSnapshot.getValue());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        mdDatabaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                List notes = new ArrayList<>();
//                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
//                    ContactsContract.CommonDataKinds.Note note = noteDataSnapshot.getValue(ContactsContract.CommonDataKinds.Note.class);
//                    notes.add(note);
//                }
//            }
//        });
    }

    @OnClick({R.id.radioFemale, R.id.radioMale})
    public void onRadioButtonClicked(RadioButton radioButton) {
        boolean checked = radioButton.isChecked();
        switch (radioButton.getId()) {
            case R.id.radioFemale:
                if (checked) {
                    gender = "f";
                }
                break;
            case R.id.radioMale:
                if (checked) {
                    gender = "m";
                }
                break;
        }
    }

    boolean isValidated() {
        fName = etFname.getText().toString();
        lName = etLname.getText().toString();
        mobNo = etMobNo.getText().toString();

        if (fName.isEmpty()) {
            etFname.setError(getResources().getString(R.string.enter_fname));
            etFname.requestFocus();
            return false;
        }
        if (lName.isEmpty()) {
            etLname.setError(getResources().getString(R.string.enter_lname));
            etLname.requestFocus();
            return false;
        }
        if (mobNo.isEmpty()) {
            etMobNo.setError(getResources().getString(R.string.enter_mob));
            etMobNo.requestFocus();
            return false;
        }
        if (mobNo.length() != 10) {
            etMobNo.setError(getResources().getString(R.string.mob_limit));
            etMobNo.requestFocus();
            return false;
        }
        return true;
    }

    @OnClick(R.id.btnSaveInfo)
    void onBtnSaveInfoClick() {
        try {
            if (isValidated()) {
                FirebaseUser fbUser = mfFirebaseAuth.getCurrentUser();

                UserInfoModel userInfoModel = new UserInfoModel(fName, lName, mobNo, fbUser.getUid(), gender, null);

                Utils.showProgress(this, "Adding Profile Details", "");
                if (!isMobNoAlreadyExists(mobNo)) {
                    String uid = fbUser.getUid();
//                    mdDatabaseReference.push().setValue(userInfoModel);//multiple informations in with same user(instertion will add new information)
                    mdDatabaseReference.child(fbUser.getUid()).setValue(userInfoModel).addOnCompleteListener(task -> {
                        {
                            if (task.isSuccessful()) {
                                Toast.makeText(this, "Information Saved", Toast.LENGTH_SHORT).show();
                                Utils.hideProgress();
                                startActivity(new Intent(this, GroupDetailActivity.class));
                                overridePendingTransition(0, 0);
                                finishAffinity();
                            } else {
                                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                Utils.hideProgress();
                            }
                        }
                    });//single information with single user (instertion will update information)
                } else {
                    Utils.hideProgress();
                    Toast.makeText(this, "Mobile Number is already registered with another user.", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (
                Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong.\nPlease Try Again", Toast.LENGTH_LONG).show();
        }

    }

    boolean isMobNoAlreadyExists(String mobNo) {
        Query query = mdDatabaseReference.child(KeyUsersDetail)
                .orderByChild(KeyMobNo)
                .equalTo(mobNo);

        isExists = false;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    isExists = true;
                } else {
                    isExists = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return isExists;
    }

    public void signout(View view) {
        mfFirebaseAuth.signOut();
        startActivity(new Intent(this, LoginActivity.class));
    }
}
