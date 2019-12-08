package com.dheeraj.expensesharenew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dheeraj.expensesharenew.userinfo.UserDetailsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity {

    @BindView(R.id.linearLayoutParent)
    LinearLayout linearLayoutParent;

    @BindView(R.id.imageViewLogo)
    ImageView imageViewLogo;

    @BindView(R.id.tvHeading)
    TextView tvHeading;

    @BindView(R.id.tvSubHeading)
    TextView tvSubHeading;

    Animation animFadein;

    int timeoutSeconds = 3;

    private FirebaseAuth mfFirebaseAuth;

    private DatabaseReference mdDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        ButterKnife.bind(this);

        animFadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_fade_in);
        linearLayoutParent.setAnimation(animFadein);

        mfFirebaseAuth = FirebaseAuth.getInstance();
        mdDatabaseReference = FirebaseDatabase.getInstance().getReference().child("UsersDetail");

        setTimeOut();
    }

    void setTimeOut() {
        new Handler().postDelayed(() -> {
            if (mfFirebaseAuth.getCurrentUser() == null) {
                Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(i);
                this.finish();
            } else {
                mdDatabaseReference.child(mfFirebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            startActivity(new Intent(SplashScreenActivity.this, GroupDashboardActivity.class));
                            finishAffinity();
                        } else {
                            startActivity(new Intent(SplashScreenActivity.this, UserDetailsActivity.class));
                            finishAffinity();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        databaseError.getMessage();
                    }
                });
            }
        }, timeoutSeconds * 1000);
    }
}
