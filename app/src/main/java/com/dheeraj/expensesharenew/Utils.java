package com.dheeraj.expensesharenew;

import android.app.Activity;

import com.kaopiz.kprogresshud.KProgressHUD;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {

    private static KProgressHUD kProgressHUD;

    private static Calendar calendar;
    private static SimpleDateFormat simpledateformat;

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
            } else {
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

    public static String getCurrentDate() {
        calendar = Calendar.getInstance();
        simpledateformat = new SimpleDateFormat("dd-MMM-yyyy");
        return simpledateformat.format(calendar.getTime());
    }

    public static String getCurrentTime(){
        calendar = Calendar.getInstance();
        simpledateformat = new SimpleDateFormat("hh:mm a");
        return simpledateformat.format(calendar.getTime());
    }


}
