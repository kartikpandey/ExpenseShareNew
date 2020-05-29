package com.dheeraj.expensesharenew;

import android.app.Activity;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.ServerValue;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class Utils {

    private static KProgressHUD kProgressHUD;

    private static Calendar calendar;
    private static SimpleDateFormat simpledateformat;

    static String timeInMillis = "";
    public static final String TIME_SERVER = "time-a.nist.gov";

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
        String time = "";
        try {
            time = new GetNetworkTime().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        calendar  = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(time));
        simpledateformat = new SimpleDateFormat("dd-MMM-yyyy");
        return simpledateformat.format(calendar.getTime());
    }

    public static String getCurrentMonthAndYear() {
        String time = "";
        try {
            time = new GetNetworkTime().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        calendar  = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(time));
        simpledateformat = new SimpleDateFormat("MMM yyyy");
//        Log.d("Date", simpledateformat.format(calendar.getTime()));
        return simpledateformat.format(calendar.getTime());
    }


    public static String getCurrentTime() {
        String time = "";
        try {
            time = new GetNetworkTime().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        calendar  = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(time));
        simpledateformat = new SimpleDateFormat("hh:mm a");
        return simpledateformat.format(calendar.getTime());
    }

    private static class GetNetworkTime extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                NTPUDPClient timeClient = new NTPUDPClient();
                InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
                TimeInfo timeInfo = timeClient.getTime(inetAddress);
                return timeInfo.getMessage().getReceiveTimeStamp().getTime() + "";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }


        @Override
        protected void onPostExecute(String result) {
            timeInMillis = result;
        }
    }
}
