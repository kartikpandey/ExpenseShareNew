package com.dheeraj.expensesharenew.CustomViews;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;

import com.dheeraj.expensesharenew.R;

import static androidx.core.content.ContextCompat.getDrawable;

public class CustomButton extends AppCompatButton {
    @RequiresApi(api = Build.VERSION_CODES.M)
    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackground(getResources().getDrawable(R.drawable.button_ripple_effect));
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        setForeground(getDrawable(context, outValue.resourceId));

    }
}