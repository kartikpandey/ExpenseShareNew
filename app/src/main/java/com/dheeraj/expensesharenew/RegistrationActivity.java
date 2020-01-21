package com.dheeraj.expensesharenew;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dheeraj.expensesharenew.userinfo.UserDetailsActivity;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationActivity extends AppCompatActivity {

    @BindView(R.id.tvSignText)
    TextView tvSignText;

    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.etPass)
    EditText etPass;

    @BindView(R.id.etConfirmPass)
    EditText etConfirmPass;

    @BindView(R.id.btnSignUp)
    Button btnSignUp;

    private String email, password, confirmPass;

    private FirebaseAuth mfFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        mfFirebaseAuth = FirebaseAuth.getInstance();
    }

    boolean isValidated() {

        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

        email = etEmail.getText().toString();
        password = etPass.getText().toString();
        confirmPass = etConfirmPass.getText().toString();

        if (email.isEmpty()) {
            etEmail.setError(getResources().getString(R.string.enter_email));
            etEmail.requestFocus();
            return false;
        }
        if(!email.matches(regex)){
            etEmail.setError(getResources().getString(R.string.enter_correct_email));
            etEmail.requestFocus();
            return false;
        }
        if (password.isEmpty()) {
            etPass.setError(getResources().getString(R.string.enter_pass));
            etPass.requestFocus();
            return false;
        }
        if(password.length()<8){
            etPass.setError(getResources().getString(R.string.password_limit));
            etPass.requestFocus();
            return false;
        }
        if (confirmPass.isEmpty()) {
            etConfirmPass.setError(getResources().getString(R.string.enter_confirm_pass));
            etConfirmPass.requestFocus();
            return false;
        }
        if (!password.equals(confirmPass)) {
            etConfirmPass.setError(getResources().getString(R.string.pass_not_match));
            etConfirmPass.requestFocus();
            return false;
        }
        return true;
    }

    @OnClick(R.id.btnSignUp)
    void onBtnSignUpClick() {
        if(isValidated()) {
            Utils.showProgress(this, "Registring User", "Please Wait...");
            mfFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    Utils.hideProgress();
                    startActivity(new Intent(RegistrationActivity.this, UserDetailsActivity.class));
                    finish();
                }else{
                    Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    Utils.hideProgress();
                }
            });
        }
    }

    @OnClick(R.id.tvSignText)
    void onTvSignTextClick() {
        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
        finish();
    }
}
