package com.dheeraj.expensesharenew.groupDetail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dheeraj.expensesharenew.BaseActivity;
import com.dheeraj.expensesharenew.R;

import butterknife.ButterKnife;

public class GroupDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);

        ButterKnife.bind(this);
    }
}
