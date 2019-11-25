package com.dheeraj.expensesharenew;

import android.app.Activity;

import com.kaopiz.kprogresshud.KProgressHUD;

public class Utils {

    private static KProgressHUD kProgressHUD;

    public static void showProgress(Activity activity, String msg, String detail) {
        if (kProgressHUD != null) {
            kProgressHUD = null;
        }
        if (!activity.isFinishing()) {
            if (detail.isEmpty()) {
                kProgressHUD = KProgressHUD.create(activity)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel(msg)
                        .setCancellable(true)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.5f);
            }else{
                kProgressHUD = KProgressHUD.create(activity)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel(msg)
                        .setCancellable(true)
                        .setAnimationSpeed(2)
                        .setDetailsLabel(detail)
                        .setDimAmount(0.5f);
            }
            kProgressHUD.show();
        }

    }

    public static void hideProgress() {
        if (kProgressHUD != null) {
            kProgressHUD.dismiss();
        }
    }
}
