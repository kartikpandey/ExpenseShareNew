package com.dheeraj.expensesharenew;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dheeraj.expensesharenew.userinfo.UserDetailsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.etPass)
    EditText etPass;

    @BindView(R.id.btnSignIn)
    Button btnSignIn;

    private String email, password;

    private FirebaseAuth mfFirebaseAuth;
    private DatabaseReference mdDatabaseReference;
    private boolean isUserInfoExists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        ButterKnife.bind(this);

        mfFirebaseAuth = FirebaseAuth.getInstance();

        mdDatabaseReference = FirebaseDatabase.getInstance().getReference().child("UsersDetail");

    }

    boolean isValidated() {

        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

        email = etEmail.getText().toString();
        password = etPass.getText().toString();

        if (email.isEmpty()) {
            etEmail.setError(getResources().getString(R.string.enter_email));
            etEmail.requestFocus();
            return false;
        }
        if (!email.matches(regex)) {
            etEmail.setError(getResources().getString(R.string.enter_correct_email));
            etEmail.requestFocus();
            return false;
        }
        if (password.isEmpty()) {
            etPass.setError(getResources().getString(R.string.enter_pass));
            etPass.requestFocus();
            return false;
        }
        return true;
    }

    @OnClick(R.id.btnSignIn)
    void onBtnSignUpClick() {
        if (isValidated()) {
            Utils.showProgress(this, "Please Wait...", "");
            mfFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("User Name : ", mfFirebaseAuth.getCurrentUser().getEmail());
                    Toast.makeText(this, "Login Successfull", Toast.LENGTH_SHORT).show();
                    Utils.hideProgress();
                    mdDatabaseReference.child(mfFirebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {
                                startActivity(new Intent(LoginActivity.this, GroupDashboardActivity.class));
                                overridePendingTransition(0, 0);
                                finishAffinity();
                            } else {
                                startActivity(new Intent(LoginActivity.this, UserDetailsActivity.class));
                                overridePendingTransition(0, 0);
                                finishAffinity();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            databaseError.getMessage();
                            isUserInfoExists = false;
                        }
                    });
//                    if (isUserInfoExists) {
//                        startActivity(new Intent(LoginActivity.this, GroupDashboardActivity.class));
//                        finish();
//                    } else {
//                        startActivity(new Intent(LoginActivity.this, UserDetailsActivity.class));
//                        finish();
//                    }

                } else {
                    Toast.makeText(this, task.getException().toString(), Toast.LENGTH_LONG).show();
                    Utils.hideProgress();
                }
            });
        }
    }

//    boolean checkUserInfo() {
//        mdDatabaseReference.child(mfFirebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Log.d("DataSnapShot : ", dataSnapshot.toString());
//                if (dataSnapshot.getValue() != null) {
//                    isUserInfoExists = true;
//                } else {
//                    isUserInfoExists = false;
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                databaseError.getMessage();
//                isUserInfoExists = false;
//            }
//        });
//        return isUserInfoExists;
//    }

    @OnClick(R.id.tvSignUpText)
    void onTvSignupClick() {
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        finish();
    }
}
