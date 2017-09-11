package rdesign.android.util;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import rdesign.android.R;

import static android.support.v4.content.ContextCompat.getColor;


/**
 * Created by saravillarreal on 9/4/17.
 */

public class AppUtil {
    private static final String TAG = "AppUtil";

    public static boolean isNetworkAvailable(Context context) {
        boolean isNetworkAvailable = Boolean.FALSE;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            isNetworkAvailable = Boolean.TRUE;
        }
        return isNetworkAvailable;
    }


    public static void showSnackbar(Context context, int id, View view) {
        try {
            Snackbar snackbar = Snackbar
                    .make(view, context.getString(id), Snackbar
                            .LENGTH_LONG);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(getColor(context, R.color.colorPrimary));
            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showAToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showAToast(Context context, Integer message) {
        Toast.makeText(context, context.getString(message), Toast.LENGTH_LONG).show();
    }

}
